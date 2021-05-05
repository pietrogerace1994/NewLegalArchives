package eng.la.persistence;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.StudioLegale;

@Component("studioLegaleDAO")
public class StudioLegaleDAOImpl extends HibernateDaoSupport implements StudioLegaleDAO{

	@Autowired
	public StudioLegaleDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public List<StudioLegale> leggi() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( StudioLegale.class ).addOrder(Order.asc("denominazione"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<StudioLegale> lista = getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
	}

	@Override
	public StudioLegale leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( StudioLegale.class ).add( Restrictions.eq("id", id) );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		StudioLegale studioLegale = (StudioLegale) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return studioLegale; 
	}

	@Override
	public List<StudioLegale> cerca(String denominazione, long nazioneId, String codiceSap) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( StudioLegale.class ).addOrder(Order.asc("denominazione"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		if( nazioneId > 0 ){
			criteria.createAlias("nazione", "nazione");
			criteria.add( Restrictions.eq("nazione.id", nazioneId) );
		}
		
		if( denominazione != null && denominazione.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("denominazione", denominazione, MatchMode.ANYWHERE) ) ;
		}
		
		if( codiceSap != null && codiceSap.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("codiceSap", codiceSap, MatchMode.ANYWHERE) ) ;
		}
		 
		@SuppressWarnings("unchecked")
		List<StudioLegale> lista = getHibernateTemplate().findByCriteria(criteria);		
		return lista; 
	}
	

	@Override
	public List<StudioLegale> cerca(String denominazione, long nazioneId, String codiceSap, int elementiPerPagina,
			int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( StudioLegale.class );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		
		if( ordinamento == null){
			criteria.addOrder(Order.asc("denominazione"));
		}else{
			if(ordinamentoDirezione == null ||  ordinamentoDirezione.equalsIgnoreCase("ASC") ){
				criteria.addOrder(Order.asc(ordinamento));
			}else{
				criteria.addOrder(Order.desc(ordinamento));
			}
		}
		
		if( nazioneId > 0 ){
			criteria.createAlias("nazione", "nazione");
			criteria.add( Restrictions.eq("nazione.id", nazioneId) );
		}
		
		if( denominazione != null && denominazione.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("denominazione", denominazione, MatchMode.ANYWHERE) ) ;
		}
		
		if( codiceSap != null && codiceSap.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("codiceSap", codiceSap, MatchMode.ANYWHERE) ) ;
		}
		 
		int indicePrimoElemento = elementiPerPagina * (numeroPagina-1); 
		Long numeroTotaleElementi = conta(denominazione, nazioneId, codiceSap);
		if( numeroTotaleElementi < indicePrimoElemento ){
			indicePrimoElemento = 0;
		}
		
		@SuppressWarnings("unchecked")
		List<StudioLegale> lista = getHibernateTemplate().findByCriteria(criteria, indicePrimoElemento, elementiPerPagina);	
		return lista; 
	}
	
	
	@Override
	public Long conta(String denominazione, long nazioneId, String codiceSap) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(StudioLegale.class);
		criteria.add(Restrictions.isNull("dataCancellazione"));
		
		if( nazioneId > 0 ){
			criteria.createAlias("nazione", "nazione");
			criteria.add( Restrictions.eq("nazione.id", nazioneId) );
		}
		
		if( denominazione != null && denominazione.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("denominazione", denominazione, MatchMode.ANYWHERE) ) ;
		}
		
		if( codiceSap != null && codiceSap.trim().length() > 0 ){
			criteria.add( Restrictions.ilike("codiceSap", codiceSap, MatchMode.ANYWHERE) ) ;
		}

		criteria.setProjection(Projections.rowCount());

		@SuppressWarnings("unchecked")
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}

	@Override
	public StudioLegale inserisci(StudioLegale vo) throws Throwable {
		getHibernateTemplate().save(vo);
		return vo;
	}

	@Override
	public StudioLegale modifica(StudioLegale vo) throws Throwable {
		getHibernateTemplate().update(vo); 
		return vo;
	}

	@Override
	public void cancella(long id) throws Throwable {
		StudioLegale vo = leggi(id);
		vo.setDataCancellazione(new Date());
		getHibernateTemplate().update(vo); 
	}

}
