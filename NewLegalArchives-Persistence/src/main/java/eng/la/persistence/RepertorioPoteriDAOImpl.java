package eng.la.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.RepertorioPoteri;
import eng.la.model.aggregate.RepertorioPoteriAggregate;
import eng.la.model.filter.RepertorioPoteriFilter;

@Component("repertorioPoteriDAO")
public class RepertorioPoteriDAOImpl extends HibernateDaoSupport implements RepertorioPoteriDAO, CostantiDAO {


	@Autowired
	public RepertorioPoteriDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public List<RepertorioPoteri> leggi() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RepertorioPoteri.class).addOrder(Order.asc("nome"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<RepertorioPoteri> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public List<RepertorioPoteri> leggi(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RepertorioPoteri.class).addOrder(Order.asc("nome"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("lang", lingua));

		@SuppressWarnings("unchecked")
		List<RepertorioPoteri> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public RepertorioPoteri leggi(String codice, String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RepertorioPoteri.class)
				.add(Restrictions.eq("codGruppoLingua", codice)).add(Restrictions.eq("lang", lingua));

		criteria.add(Restrictions.isNull("dataCancellazione"));

		@SuppressWarnings("unchecked")
		RepertorioPoteri repertorioPoteri = (RepertorioPoteri) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return repertorioPoteri;
	}

	@Override
	public Long conta(String nome) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RepertorioPoteri.class);
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if (nome != null && nome.trim().length() > 0) {
			criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
		}

		criteria.setProjection(Projections.rowCount());

		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}

	@Override
	public void cancella(long idRepertorioPoteri) throws Throwable {
		RepertorioPoteri voDB = leggi(idRepertorioPoteri);
		voDB.setDataCancellazione(new Date());
		getHibernateTemplate().update(voDB);
	}

	@Override
	public void modifica(RepertorioPoteri vo) throws Throwable {
		getHibernateTemplate().update(vo);
	}

	@Override
	public List<RepertorioPoteri> cerca(String nome) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RepertorioPoteri.class).addOrder(Order.asc("nome"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if (nome != null && nome.trim().length() > 0) {
			criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
		}

		@SuppressWarnings("unchecked")
		List<RepertorioPoteri> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public RepertorioPoteri inserisci(RepertorioPoteri vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}

	@Override
	public List<RepertorioPoteri> cerca(String nome, int elementiPerPagina, int numeroPagina, String ordinamento,
			String ordinamentoDirezione) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RepertorioPoteri.class);
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if (ordinamento == null) {
			criteria.addOrder(Order.asc("nome"));
		} else {
			if (ordinamentoDirezione == null || ordinamentoDirezione.equalsIgnoreCase("ASC")) {
				criteria.addOrder(Order.asc(ordinamento));
			} else {
				criteria.addOrder(Order.desc(ordinamento));
			}
		}

		if (nome != null && nome.trim().length() > 0) {
			criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
		}

		int indicePrimoElemento = elementiPerPagina * (numeroPagina - 1);
		Long numeroTotaleElementi = conta(nome);
		if (numeroTotaleElementi < indicePrimoElemento) {
			indicePrimoElemento = 0;
		}

		@SuppressWarnings("unchecked")
		List<RepertorioPoteri> lista = getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento, elementiPerPagina);
		return lista;
	}

	@Override
	public RepertorioPoteri leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RepertorioPoteri.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		RepertorioPoteri repertorioPoteri = (RepertorioPoteri) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return repertorioPoteri;
	}

	@Override
	public RepertorioPoteriAggregate searchPagedRepertorioPoteri(RepertorioPoteriFilter filter) throws Throwable {
		
		List<RepertorioPoteri> returnList = null;
		RepertorioPoteriAggregate out = new RepertorioPoteriAggregate();
		if(filter != null){
			DetachedCriteria criteria = DetachedCriteria.forClass(RepertorioPoteri.class); 
			if(filter.getOrder() != null && filter.getOrder().equalsIgnoreCase("asc")){
				criteria.addOrder(Order.asc("descrizione"));
			}else{
				criteria.addOrder(Order.desc("descrizione"));
			}
					
			criteria.add(Restrictions.eq("lingua", filter.getLingua()));
			criteria.add(Restrictions.isNull("dataCancellazione"));
			
			if(filter.getCodice() != null && !filter.getCodice().isEmpty()){
				criteria.add(Restrictions.ilike("codice", filter.getCodice(),MatchMode.ANYWHERE));
			}
			
			if(filter.getDescrizione() != null  && !filter.getDescrizione().isEmpty()){
				criteria.add(Restrictions.ilike("descrizione", filter.getDescrizione(),MatchMode.ANYWHERE));
			}

			if(filter.getTesto() != null   && !filter.getTesto().isEmpty()){
				criteria.add(Restrictions.ilike("testo", filter.getTesto(),MatchMode.ANYWHERE));
			}

			if(filter.getIdCategoria() != null && filter.getIdCategoria().longValue()>0){
				criteria.createAlias("categoria", "categoria");
				criteria.add(Restrictions.eq("categoria.id", filter.getIdCategoria()));
			}
			if(filter.getIdSubcategoria() != null  && filter.getIdSubcategoria().longValue()>0){
				criteria.createAlias("subcategoria", "subcategoria");
				criteria.add(Restrictions.eq("subcategoria.id", filter.getIdSubcategoria()));
			}
			
//			HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_PROCURE);

			
			Long numeroTotaleElementi = countRepertorioPoteri(filter);
			int elementiPerPagina = filter.getNumElementiPerPagina();
			elementiPerPagina = (int) (elementiPerPagina > numeroTotaleElementi ? numeroTotaleElementi : elementiPerPagina);

			int numeroPagina = filter.getNumeroPagina();
			int indicePrimoElemento = elementiPerPagina * (numeroPagina - 1);
			
			out.setNumeroTotaleElementi(numeroTotaleElementi);
			
			elementiPerPagina = (int) (elementiPerPagina > numeroTotaleElementi ? numeroTotaleElementi : elementiPerPagina);
			if (numeroTotaleElementi < indicePrimoElemento) {
				indicePrimoElemento = 0;
			}
			
			// HibernateDaoUtil.aggiungiLogicaPermessi(criteria, CostantiDAO.NOME_CLASSE_ATTO); 
			criteria.setProjection(Projections.projectionList()
			.add(Projections.distinct(Projections.property("id")))
			.add(Projections.property("id"), "id")
			.add(Projections.property("descrizione"), "descrizione"))
			.setResultTransformer(Transformers.aliasToBean(RepertorioPoteri.class)); 


			
			@SuppressWarnings("unchecked")
			List<RepertorioPoteri> lista = getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento, indicePrimoElemento+elementiPerPagina);
			if( lista != null ){
				returnList  = new ArrayList<RepertorioPoteri>();
				int index = 0;
				for( RepertorioPoteri f : lista ){
					if( index < elementiPerPagina){
						returnList.add(leggi(f.getId()));					
					}
					index++;
				}
			}
		}
		
		out.setList(returnList);
		return out;
	}
	
	public Long countRepertorioPoteri(RepertorioPoteriFilter filter)  throws Throwable{
		
		DetachedCriteria criteria = DetachedCriteria.forClass(RepertorioPoteri.class); 
				
		criteria.add(Restrictions.eq("lingua", filter.getLingua()));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		
		if(filter.getCodice() != null && !filter.getCodice().isEmpty()){
			criteria.add(Restrictions.ilike("codice", filter.getCodice(),MatchMode.ANYWHERE));
		}
		
		if(filter.getDescrizione() != null  && !filter.getDescrizione().isEmpty()){
			criteria.add(Restrictions.ilike("descrizione", filter.getDescrizione(),MatchMode.ANYWHERE));
		}

		if(filter.getTesto() != null   && !filter.getTesto().isEmpty()){
			criteria.add(Restrictions.ilike("testo", filter.getTesto(),MatchMode.ANYWHERE));
		}

		if(filter.getIdCategoria() != null && filter.getIdCategoria().longValue()>0){
			criteria.createAlias("categoria", "categoria");
			criteria.add(Restrictions.eq("categoria.id", filter.getIdCategoria()));
		}
		if(filter.getIdSubcategoria() != null  && filter.getIdSubcategoria().longValue()>0){
			criteria.createAlias("subcategoria", "subcategoria");
			criteria.add(Restrictions.eq("subcategoria.id", filter.getIdSubcategoria()));
		}
		
//		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_PROCURE);
		
		criteria.setProjection(Projections.distinct(Projections.countDistinct("id")));
		 
		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}

	@Override
	public List<String> getCodGruppoLinguaList(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( RepertorioPoteri.class );
		criteria.setProjection(Projections.distinct(Projections.property("codGruppoLingua")));
		criteria.add(Restrictions.eq("lingua", lingua.toUpperCase()));
		@SuppressWarnings("unchecked")
		List<String> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

}
