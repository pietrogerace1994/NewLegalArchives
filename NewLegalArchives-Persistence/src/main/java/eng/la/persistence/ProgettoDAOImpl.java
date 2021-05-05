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

import eng.la.model.Progetto;
import eng.la.model.aggregate.ProgettoAggregate;
import eng.la.model.filter.ProgettoFilter;
import eng.la.util.HibernateDaoUtil;

@Component("progettoDAO")
public class ProgettoDAOImpl extends HibernateDaoSupport implements ProgettoDAO, CostantiDAO {

	@Autowired
	public ProgettoDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public List<Progetto> leggi() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Progetto.class).addOrder(Order.asc("nome"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<Progetto> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public List<Progetto> leggi(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Progetto.class).addOrder(Order.asc("nome"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("lang", lingua));

		@SuppressWarnings("unchecked")
		List<Progetto> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	@Override
	public Progetto leggi(String codice, String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Progetto.class)
				.add(Restrictions.eq("codGruppoLingua", codice)).add(Restrictions.eq("lang", lingua));

		criteria.add(Restrictions.isNull("dataCancellazione"));

		@SuppressWarnings("unchecked")
		Progetto progetto = (Progetto) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return progetto;
	}

	@Override
	public Long conta(String nome) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Progetto.class);
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
	public void cancella(long idProgetto) throws Throwable {
		Progetto voDB = leggi(idProgetto);
		voDB.setDataCancellazione(new Date());
		getHibernateTemplate().update(voDB);
	}

	@Override
	public void modifica(Progetto vo) throws Throwable {
		getHibernateTemplate().update(vo);
	}

	@Override
	public List<Progetto> cerca(String nome) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Progetto.class).addOrder(Order.asc("nome"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if (nome != null && nome.trim().length() > 0) {
			criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
		}

		@SuppressWarnings("unchecked")
		List<Progetto> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public Progetto inserisci(Progetto vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}

	@Override
	public List<Progetto> cerca(String nome, int elementiPerPagina, int numeroPagina, String ordinamento,
			String ordinamentoDirezione) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Progetto.class);
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
		List<Progetto> lista = getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento, elementiPerPagina);
		return lista;
	}

	@Override
	public Progetto leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Progetto.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		Progetto progetto = (Progetto) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return progetto;
	}
	
	public Progetto leggiConPermessi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Progetto.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_PROGETTO);
		@SuppressWarnings("unchecked")
		Progetto progetto = (Progetto) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return progetto;
	}

	@Override
	public ProgettoAggregate searchPagedProject(ProgettoFilter filter) throws Throwable {
		
		List<Progetto> returnList = null;
		ProgettoAggregate out = new ProgettoAggregate();
		if(filter != null){
			DetachedCriteria criteria = DetachedCriteria.forClass(Progetto.class); 
			if(filter.getOrder() != null && filter.getOrder().equalsIgnoreCase("asc")){
				criteria.addOrder(Order.asc("dataCreazione"));
			}else{
				criteria.addOrder(Order.desc("dataCreazione"));
			}
					
			criteria.add(Restrictions.isNull("dataCancellazione"));
			
			if(filter.getDataCreazioneDal() != null){
				criteria.add(Restrictions.ge("dataCreazione", filter.getDataCreazioneDal()));
			}
			if(filter.getDataCreazioneAl() != null){
				criteria.add(Restrictions.le("dataCreazione", filter.getDataCreazioneAl()));
			}
			if(filter.getDataChiusuraDal() != null){
				criteria.add(Restrictions.ge("dataChiusura", filter.getDataChiusuraDal()));
			}
			if(filter.getDataChiusuraAl() != null){
				criteria.add(Restrictions.le("dataChiusura", filter.getDataChiusuraAl()));
			}
			
			if(filter.getOggetto() != null && !filter.getOggetto().isEmpty()){
				criteria.add(Restrictions.ilike("oggetto", filter.getOggetto(), MatchMode.ANYWHERE));
			}
			if(filter.getNome() != null && !filter.getNome().isEmpty()){
				criteria.add(Restrictions.ilike("nome", filter.getNome(), MatchMode.ANYWHERE));
			}
			if(filter.getDescrizione() != null && !filter.getDescrizione().isEmpty()){
				criteria.add(Restrictions.ilike("descrizione", filter.getDescrizione(), MatchMode.ANYWHERE));
			}
			
//			if (tipoPermesso == null) {
				HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_PROGETTO);
//			} else {
//				HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_FASCICOLO, tipoPermesso);
//			}

			
			Long numeroTotaleElementi = countProject(filter);
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
			.add(Projections.property("dataCreazione"), "dataCreazione") )
			.setResultTransformer(Transformers.aliasToBean(Progetto.class)); 
//			criteria.setProjection(Projections.distinct(Projections.countDistinct("id")));

			
			@SuppressWarnings("unchecked")
			List<Progetto> lista = getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento, indicePrimoElemento+elementiPerPagina);
			if( lista != null ){
				returnList  = new ArrayList<Progetto>();
				int index = 0;
				for( Progetto f : lista ){
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
	
	public Long countProject(ProgettoFilter filter)  throws Throwable{
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Progetto.class); 
				
		criteria.add(Restrictions.isNull("dataCancellazione"));
		
		if(filter.getDataCreazioneDal() != null){
			criteria.add(Restrictions.ge("dataCreazione", filter.getDataCreazioneDal()));
		}
		if(filter.getDataCreazioneAl() != null){
			criteria.add(Restrictions.le("dataCreazione", filter.getDataCreazioneAl()));
		}
		if(filter.getDataChiusuraDal() != null){
			criteria.add(Restrictions.ge("dataChiusura", filter.getDataChiusuraDal()));
		}
		if(filter.getDataChiusuraAl() != null){
			criteria.add(Restrictions.le("dataChiusura", filter.getDataChiusuraAl()));
		}
		
		if(filter.getOggetto() != null && !filter.getOggetto().isEmpty()){
			criteria.add(Restrictions.ilike("oggetto", filter.getOggetto(), MatchMode.ANYWHERE));
		}
		if(filter.getNome() != null && !filter.getNome().isEmpty()){
			criteria.add(Restrictions.ilike("nome", filter.getNome(), MatchMode.ANYWHERE));
		}
		if(filter.getDescrizione() != null && !filter.getDescrizione().isEmpty()){
			criteria.add(Restrictions.ilike("descrizione", filter.getDescrizione(), MatchMode.ANYWHERE));
		}
		
//		if (tipoPermesso == null) {
			HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_PROGETTO);
//		} else {
//			HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_FASCICOLO, tipoPermesso);
//		}
		
		criteria.setProjection(Projections.distinct(Projections.countDistinct("id")));
		 
		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}

}
