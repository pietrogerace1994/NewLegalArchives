package eng.la.persistence;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.TipoProfessionista;

@Component("tipoProfessionistaDAO")
public class TipoProfessionistaDAOImpl extends HibernateDaoSupport implements TipoProfessionistaDAO {
	
	@Autowired
	public TipoProfessionistaDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}

	@Cacheable(value="tipoProfessionistaCacheLeggiPerLingua") 
	@Override
	public List<TipoProfessionista> leggi(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( TipoProfessionista.class ).addOrder(Order.asc("descrizione"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("lang", lingua));
		@SuppressWarnings("unchecked")
		List<TipoProfessionista> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista; 
	} 

	@Cacheable(value="tipoProfessionistaLeggiCacheById")
	@Override
	public TipoProfessionista leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( TipoProfessionista.class ).add( Restrictions.eq("id", id) );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		TipoProfessionista tipoProfessionista = (TipoProfessionista) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return tipoProfessionista; 
	}
	
	@Cacheable(value="tipoProfessionistaCacheLeggiPerCodiceLingua") 
	@Override
	public TipoProfessionista leggi(String codice, String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( TipoProfessionista.class )
				.add( Restrictions.eq("lang", lingua) )
				.add( Restrictions.eq("codGruppoLingua", codice) );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		TipoProfessionista tipoProfessionista = (TipoProfessionista) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return tipoProfessionista; 
	}
	
} 