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

import eng.la.model.SecondoLivelloAttribuzioni;


@Component("secondoLivelloAttribuzioniDAO")
public class SecondoLivelloAttribuzioniDAOImpl extends HibernateDaoSupport implements SecondoLivelloAttribuzioniDAO {

	@Autowired
	public SecondoLivelloAttribuzioniDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<SecondoLivelloAttribuzioni> leggi(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(SecondoLivelloAttribuzioni.class).addOrder(Order.asc("descrizione"));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));	
		@SuppressWarnings("unchecked")
		List<SecondoLivelloAttribuzioni> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	@Override
	public SecondoLivelloAttribuzioni leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(SecondoLivelloAttribuzioni.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		SecondoLivelloAttribuzioni secondoLivelloAttribuzioni = (SecondoLivelloAttribuzioni) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return secondoLivelloAttribuzioni;
	}

}
