package eng.la.persistence;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.FetchMode;
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

import eng.la.model.BeautyContest;
import eng.la.model.RBeautyContestMateria;
import eng.la.model.RBeautyContestProfessionistaEsterno;
import eng.la.util.DateUtil;
import eng.la.util.HibernateDaoUtil;

@Component("beautyContestDAO")
public class BeautyContestDAOImpl extends HibernateDaoSupport implements BeautyContestDAO, CostantiDAO{


	@Autowired
	public BeautyContestDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<BeautyContest> leggi() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( BeautyContest.class ).addOrder(Order.asc("titolo"));
		criteria.add(Restrictions.isNull("dataCancellazione")); 
		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_BEAUTY_CONTEST);
		@SuppressWarnings("unchecked")
		List<BeautyContest> lista = getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
	}

	@Override
	public BeautyContest leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( BeautyContest.class ).add( Restrictions.eq("id", id) );
		criteria.add(Restrictions.isNull("dataCancellazione")); 
		criteria.setFetchMode("RBeautyContestMaterias", FetchMode.JOIN);
		criteria.setFetchMode("RBeautyContestProfessionistaEsternos", FetchMode.JOIN);
		@SuppressWarnings("unchecked")
		BeautyContest beautyContest = (BeautyContest) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return beautyContest; 
	}
	
	@Override
	public BeautyContest leggiTutti(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( BeautyContest.class ).add( Restrictions.eq("id", id) );
		@SuppressWarnings("unchecked")
		BeautyContest beautyContest = (BeautyContest) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return beautyContest; 
	}
	
	@Override
	public BeautyContest leggi(long id, FetchMode fetchMode) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(BeautyContest.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.setFetchMode("RBeautyContestMaterias", fetchMode);
		criteria.setFetchMode("RBeautyContestProfessionistaEsternos", fetchMode);
		criteria.setFetchMode("nazione", fetchMode);
		criteria.setFetchMode("vincitore", fetchMode);
		criteria.setFetchMode("statoBeautyContest", fetchMode);
		@SuppressWarnings("unchecked")
		BeautyContest beautyContest = (BeautyContest) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));

		return beautyContest;
	}
	
	@Override
	public List<BeautyContest> cerca(String titolo, String dal, String al, String statoBeautyContestCode, String centroDiCosto, 
			int elementiPerPagina, int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable {
		Long numeroTotaleElementi = conta(titolo, dal, al, statoBeautyContestCode, centroDiCosto);
		elementiPerPagina = (int) (elementiPerPagina > numeroTotaleElementi ? numeroTotaleElementi : elementiPerPagina);
		DetachedCriteria criteria = DetachedCriteria.forClass( BeautyContest.class );
		criteria.createAlias("statoBeautyContest", "statoBeautyContest");
		criteria.add(Restrictions.isNull("dataCancellazione"));
		
		if( ordinamento == null){
			criteria.addOrder(Order.asc("titolo"));
		}else{
			if(ordinamentoDirezione == null ||  ordinamentoDirezione.equalsIgnoreCase("ASC") ){
				if( ordinamento.equals("titolo") ){		

					criteria.addOrder(Order.asc("titolo"));
				}
				else if( ordinamento.equals("stato") ){ 
					criteria.addOrder(Order.asc("statoBeautyContest.descrizione")); 

				}
				else{
					criteria.addOrder(Order.asc(ordinamento));
				}
			}else{
				if( ordinamento.equals("titolo") ){			
					criteria.addOrder(Order.desc("titolo"));
				}
				else if( ordinamento.equals("stato") ){  
					criteria.addOrder(Order.desc("statoBeautyContest.descrizione")); 

				}else{
					criteria.addOrder(Order.desc(ordinamento));
				} 
			}
		}
		
		if( titolo != null && titolo.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("titolo", titolo, MatchMode.ANYWHERE));
		}

		if( dal != null && DateUtil.isData(dal) ){
			criteria.add(Restrictions.ge("dataEmissione", DateUtil.toDate(dal)));
		}

		if( al != null && DateUtil.isData(al) ){
			criteria.add(Restrictions.lt("dataEmissione", DateUtil.getDataOra(al+" - 23:59:59")));
		}

		if( statoBeautyContestCode != null && statoBeautyContestCode.trim().length() > 0 ){
			criteria.add( Restrictions.eq("statoBeautyContest.codGruppoLingua", statoBeautyContestCode));
		}

		if( centroDiCosto != null && centroDiCosto.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("centroDiCosto", URLDecoder.decode(centroDiCosto,"UTF-8"), MatchMode.ANYWHERE) ) ;
		}  

		int indicePrimoElemento = elementiPerPagina * (numeroPagina-1); 
		if( numeroTotaleElementi < indicePrimoElemento ){
			indicePrimoElemento = 0;
		} 

		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_BEAUTY_CONTEST);

		criteria.setProjection(Projections.projectionList()
				.add(Projections.distinct(Projections.property("id")))
				.add(Projections.property("id"), "id")
				.add(Projections.property("dataEmissione"), "dataEmissione")
				.add(Projections.property("dataChiusura"), "dataChiusura")
				.add(Projections.property("statoBeautyContest"), "statoBeautyContest")
				.add(Projections.property("statoBeautyContest.descrizione"))
				.add(Projections.property("centroDiCosto"), "centroDiCosto")
				.add(Projections.property("legaleInterno"), "legaleInterno"))

		.setResultTransformer(Transformers.aliasToBean(BeautyContest.class));

		@SuppressWarnings("unchecked")
		List<BeautyContest> lista = getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento, indicePrimoElemento+elementiPerPagina );

		List<BeautyContest> listaRitorno  = null;

		if( lista != null ){
			listaRitorno  = new ArrayList<BeautyContest>();
			int index = 0;
			for( BeautyContest i : lista ){
				if( index < elementiPerPagina){	
					listaRitorno.add( leggi( i.getId() ));					
				}
				index++;
			}
		}
		return listaRitorno;  
	}

	@Override
	public Long conta(String titolo, String dal, String al, String statoBeautyContestCode, String centroDiCosto) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(BeautyContest.class);

		criteria.add(Restrictions.isNull("dataCancellazione"));

		if( statoBeautyContestCode != null && statoBeautyContestCode.trim().length() > 0 ){
			criteria.createAlias("statoBeautyContest", "statoBeautyContest");
			criteria.add( Restrictions.eq("statoBeautyContest.codGruppoLingua", statoBeautyContestCode) );
		}

		if( titolo != null && titolo.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("titolo", titolo, MatchMode.ANYWHERE));
		}

		if( dal != null && DateUtil.isData(dal) ){
			criteria.add(Restrictions.ge("dataEmissione", DateUtil.toDate(dal)));
		}

		if( al != null && DateUtil.isData(al) ){
			criteria.add(Restrictions.lt("dataEmissione", DateUtil.getDataOra(al+" - 23:59:59")));
		}

		if( centroDiCosto != null && centroDiCosto.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("centroDiCosto", centroDiCosto, MatchMode.ANYWHERE) ) ;
		}

		HibernateDaoUtil.aggiungiLogicaPermessi(criteria, NOME_CLASSE_BEAUTY_CONTEST);

		criteria.setProjection(Projections.distinct(Projections.countDistinct("id")));

		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}


	@Override
	public BeautyContest inserisci(BeautyContest vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}

	@Override
	public void modifica(BeautyContest vo) throws Throwable {
		vo.setDataModifica(new Date());
		getHibernateTemplate().update(vo); 
	}

	@Override
	public void cancella(long id) throws Throwable {
		BeautyContest vo = leggi(id);
		vo.setDataCancellazione(new Date());	
		getHibernateTemplate().update(vo);
		
		if(vo.getRBeautyContestMaterias() != null && !vo.getRBeautyContestMaterias().isEmpty()){
			
			Set<RBeautyContestMateria> rBeautyContestMaterias = vo.getRBeautyContestMaterias();
			for(RBeautyContestMateria rBeautyContestMateria : rBeautyContestMaterias){
				
				rBeautyContestMateria.setDataCancellazione(new Date());
				getHibernateTemplate().update(rBeautyContestMateria); 
			}
		}
		
		if(vo.getRBeautyContestProfessionistaEsternos() != null && !vo.getRBeautyContestProfessionistaEsternos().isEmpty()){
			
			Set<RBeautyContestProfessionistaEsterno> rBeautyContestProfessionistaEsternos = vo.getRBeautyContestProfessionistaEsternos();
			for(RBeautyContestProfessionistaEsterno rBeautyContestProfessionistaEsterno : rBeautyContestProfessionistaEsternos){
				
				rBeautyContestProfessionistaEsterno.setDataCancellazione(new Date());
				getHibernateTemplate().update(rBeautyContestProfessionistaEsterno); 
			}
		}
	}
	
	@Override
	public void inserisciBeautyContestMateria(RBeautyContestMateria vo) throws Throwable {
		getHibernateTemplate().save(vo);
	}
	
	@Override
	public void cancellaBeautyContestMaterie(long beautyContestId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RBeautyContestMateria.class);
		criteria.createAlias("beautyContest", "beautyContest");
		criteria.add(Restrictions.eq("beautyContest.id", beautyContestId));
		@SuppressWarnings("unchecked")
		List<RBeautyContestMateria> lista = (List<RBeautyContestMateria>)getHibernateTemplate().findByCriteria(criteria);
		if (lista != null && lista.size() > 0) {
			for (RBeautyContestMateria vo : lista) {
				getHibernateTemplate().delete(vo);
			}
		}
		getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
	}
	
	@Override
	public void inserisciBeautyContestProfessionistaEsterno(RBeautyContestProfessionistaEsterno vo) throws Throwable {
		getHibernateTemplate().save(vo);
	}
	
	@Override
	public void cancellaBeautyContestProfessionistiEsterni(long beautyContestId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RBeautyContestProfessionistaEsterno.class);
		criteria.createAlias("beautyContest", "beautyContest");
		criteria.add(Restrictions.eq("beautyContest.id", beautyContestId));
		@SuppressWarnings("unchecked")
		List<RBeautyContestProfessionistaEsterno> lista = (List<RBeautyContestProfessionistaEsterno>)getHibernateTemplate().findByCriteria(criteria);
		if (lista != null && lista.size() > 0) {
			for (RBeautyContestProfessionistaEsterno vo : lista) {
				getHibernateTemplate().delete(vo);
			}
		}
		getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
	}
	
}
