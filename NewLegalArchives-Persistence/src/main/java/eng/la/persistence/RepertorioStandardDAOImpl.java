package eng.la.persistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.RepertorioStandard;
import eng.la.model.Societa;
import eng.la.model.aggregate.RepertorioStandardAggregate;
import eng.la.model.filter.RepertorioStandardFilter;

@Component("repertorioStandardDAO")
public class RepertorioStandardDAOImpl extends HibernateDaoSupport implements RepertorioStandardDAO, CostantiDAO {


	@Autowired
	public RepertorioStandardDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public List<RepertorioStandard> leggi() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RepertorioStandard.class).addOrder(Order.asc("nome"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<RepertorioStandard> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	@Override
	public List<String> findSocieta() throws Throwable {
		List<String> list = null;
		List<String> societa = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(RepertorioStandard.class).addOrder(Order.asc("societa"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.isNotNull("societa"));
		criteria.setProjection(Projections.distinct(Projections.property("societa")));
		list =  getHibernateTemplate().findByCriteria(criteria);
		DetachedCriteria criteria2 = DetachedCriteria.forClass(Societa.class).addOrder(Order.asc("nome"));
		criteria2.add(Restrictions.isNull("dataCancellazione"));
		criteria2.setProjection(Projections.distinct(Projections.property("nome")));
		societa =  getHibernateTemplate().findByCriteria(criteria2);
		if (list == null) {
			list = new LinkedList<String>();
		}
		if (list !=null && list.size()==0) {
			list = new LinkedList<String>();
		}
		list.addAll(societa);
		list = new ArrayList(new HashSet(list));
		Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
		return list;
	}


	@Override
	public List<RepertorioStandard> leggi(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RepertorioStandard.class).addOrder(Order.asc("nome"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("lang", lingua));

		@SuppressWarnings("unchecked")
		List<RepertorioStandard> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public RepertorioStandard leggi(String codice, String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RepertorioStandard.class)
				.add(Restrictions.eq("codGruppoLingua", codice)).add(Restrictions.eq("lang", lingua));

		criteria.add(Restrictions.isNull("dataCancellazione"));

		@SuppressWarnings("unchecked")
		RepertorioStandard repertorioStandard = (RepertorioStandard) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return repertorioStandard;
	}

	@Override
	public Long conta(String nome) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RepertorioStandard.class);
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
	public void cancella(long idRepertorioStandard) throws Throwable {
		RepertorioStandard voDB = leggi(idRepertorioStandard);
		voDB.setDataCancellazione(new Date());
		getHibernateTemplate().update(voDB);
	}

	@Override
	public void modifica(RepertorioStandard vo) throws Throwable {
		getHibernateTemplate().update(vo);
	}

	@Override
	public List<RepertorioStandard> cerca(String nome) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RepertorioStandard.class).addOrder(Order.asc("nome"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if (nome != null && nome.trim().length() > 0) {
			criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
		}

		@SuppressWarnings("unchecked")
		List<RepertorioStandard> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public RepertorioStandard inserisci(RepertorioStandard vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}

	@Override
	public List<RepertorioStandard> cerca(String nome, int elementiPerPagina, int numeroPagina, String ordinamento,
			String ordinamentoDirezione) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RepertorioStandard.class);
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
		List<RepertorioStandard> lista = getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento, elementiPerPagina);
		return lista;
	}

	@Override
	public RepertorioStandard leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RepertorioStandard.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		RepertorioStandard repertorioStandard = (RepertorioStandard) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return repertorioStandard;
	}

	@Override
	public RepertorioStandardAggregate searchPagedRepertorioStandard(RepertorioStandardFilter filter) throws Throwable {
		
		List<RepertorioStandard> returnList = null;
		RepertorioStandardAggregate out = new RepertorioStandardAggregate();
		if(filter != null){
			DetachedCriteria criteria = DetachedCriteria.forClass(RepertorioStandard.class); 
			if(filter.getOrder() != null && filter.getOrder().equalsIgnoreCase("asc")){
				criteria.addOrder(Order.asc("nome"));
			}else{
				criteria.addOrder(Order.desc("nome"));
			}
					
			criteria.add(Restrictions.eq("lingua", filter.getLingua()));
			criteria.add(Restrictions.isNull("dataCancellazione"));
			
			if(filter.getNome() != null && !filter.getNome().isEmpty()){
				criteria.add(Restrictions.ilike("nome", filter.getNome(),MatchMode.ANYWHERE));
			}
			
			if(filter.getNota() != null && !filter.getNota().isEmpty()){
				criteria.add(Restrictions.ilike("nota", filter.getNota(),MatchMode.ANYWHERE));
			}

			if(filter.getSocieta() != null){
				criteria.add(Restrictions.eq("societa", filter.getSocieta()));
			}

			if(filter.getPosizioneOrganizzativa() != null  && filter.getPosizioneOrganizzativa().longValue()>0){
				criteria.add(Restrictions.eq("posizioneOrganizzativa.id", filter.getPosizioneOrganizzativa()));
			}
			if(filter.getPrimoLivelloAttribuzioni() != null  && filter.getPrimoLivelloAttribuzioni().longValue()>0){
				criteria.add(Restrictions.eq("primoLivelloAttribuzioni.id", filter.getPrimoLivelloAttribuzioni()));
			}
			if(filter.getSecondoLivelloAttribuzioni() != null  && filter.getSecondoLivelloAttribuzioni().longValue()>0){
				criteria.add(Restrictions.eq("secondoLivelloAttribuzioni.id", filter.getSecondoLivelloAttribuzioni()));
			}
			
//			HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_PROCURE);

			
			Long numeroTotaleElementi = countRepertorioStandard(filter);
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
			.add(Projections.property("nome"), "nome"))
			.setResultTransformer(Transformers.aliasToBean(RepertorioStandard.class)); 


			
			@SuppressWarnings("unchecked")
			List<RepertorioStandard> lista = getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento, indicePrimoElemento+elementiPerPagina);
			if( lista != null ){
				returnList  = new ArrayList<RepertorioStandard>();
				int index = 0;
				for( RepertorioStandard f : lista ){
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
	
	public Long countRepertorioStandard(RepertorioStandardFilter filter)  throws Throwable{
		
		DetachedCriteria criteria = DetachedCriteria.forClass(RepertorioStandard.class); 
				
		criteria.add(Restrictions.eq("lingua", filter.getLingua()));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		
		criteria.add(Restrictions.eq("lingua", filter.getLingua()));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		
		if(filter.getNome() != null && !filter.getNome().isEmpty()){
			criteria.add(Restrictions.ilike("nome", filter.getNome(),MatchMode.ANYWHERE));
		}
		
		if(filter.getNota() != null && !filter.getNota().isEmpty()){
			criteria.add(Restrictions.ilike("nota", filter.getNota(),MatchMode.ANYWHERE));
		}

		if(filter.getSocieta() != null){
			criteria.add(Restrictions.eq("societa", filter.getSocieta()));
		}

		if(filter.getPosizioneOrganizzativa() != null  && filter.getPosizioneOrganizzativa().longValue()>0){
			criteria.add(Restrictions.eq("posizioneOrganizzativa.id", filter.getPosizioneOrganizzativa()));
		}
		if(filter.getPrimoLivelloAttribuzioni() != null  && filter.getPrimoLivelloAttribuzioni().longValue()>0){
			criteria.add(Restrictions.eq("primoLivelloAttribuzioni.id", filter.getPrimoLivelloAttribuzioni()));
		}
		if(filter.getSecondoLivelloAttribuzioni() != null  && filter.getSecondoLivelloAttribuzioni().longValue()>0){
			criteria.add(Restrictions.eq("secondoLivelloAttribuzioni.id", filter.getSecondoLivelloAttribuzioni()));
		}
//		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_PROCURE);
		
		criteria.setProjection(Projections.distinct(Projections.countDistinct("id")));
		 
		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}

	@Override
	public List<String> getCodGruppoLinguaList(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( RepertorioStandard.class );
		criteria.setProjection(Projections.distinct(Projections.property("codGruppoLingua")));
		criteria.add(Restrictions.eq("lingua", lingua.toUpperCase()));
		@SuppressWarnings("unchecked")
		List<String> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

}
