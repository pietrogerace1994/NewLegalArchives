package eng.la.persistence;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.Lingua;

@Component("linguaDAO")
public class LinguaDAOImpl extends HibernateDaoSupport implements LinguaDAO {

	@Autowired
	public LinguaDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<Lingua> leggi() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Lingua.class)
				.addOrder(Order.asc("id"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<Lingua> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

}
