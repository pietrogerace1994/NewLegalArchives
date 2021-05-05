package eng.la.persistence;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.TipologiaSchedaFr;

@Component("tipologiaSchedaFrDAO")
public class TipologiaSchedaFrDAOImpl extends HibernateDaoSupport implements TipologiaSchedaFrDAO, CostantiDAO {

	@Autowired
	public TipologiaSchedaFrDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<TipologiaSchedaFr> leggi(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipologiaSchedaFr.class).addOrder(Order.asc("descrizione"));
		criteria.add( Restrictions.eq("lang", lingua) );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<TipologiaSchedaFr> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public TipologiaSchedaFr leggi(String code, String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( TipologiaSchedaFr.class )
				.add( Restrictions.eq("lang", lingua) )
				.add( Restrictions.eq("codGruppoLingua", code) );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		TipologiaSchedaFr tipologiaSchedaFr = (TipologiaSchedaFr) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return tipologiaSchedaFr;
	}
}