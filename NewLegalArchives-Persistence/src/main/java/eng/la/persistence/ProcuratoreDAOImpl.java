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

import eng.la.model.Procuratore;

@Component("procuratoreDAO")
public class ProcuratoreDAOImpl extends HibernateDaoSupport implements ProcuratoreDAO, CostantiDAO{

	@Autowired
	public ProcuratoreDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public List<Procuratore> leggi(boolean tutti) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Procuratore.class ).addOrder(Order.asc("nominativo"));
		if( !tutti ){
			criteria.add(Restrictions.isNull("dataCancellazione"));		
		}
		@SuppressWarnings("unchecked")
		List<Procuratore> lista = getHibernateTemplate().findByCriteria(criteria);
		 
		return lista; 

	}

	@Override
	public Procuratore leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Procuratore.class ).add( Restrictions.eq("id", id) );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		Procuratore procuratore = (Procuratore) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return procuratore;
	}

}
