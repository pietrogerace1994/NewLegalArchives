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

import eng.la.model.CategoriaTessere;


@Component("categoriaTessereDAO")
public class CategoriaTessereDAOImpl extends HibernateDaoSupport implements CategoriaTessereDAO {

	@Autowired
	public CategoriaTessereDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<CategoriaTessere> leggi(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(CategoriaTessere.class).addOrder(Order.asc("descrizione"));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));	
		@SuppressWarnings("unchecked")
		List<CategoriaTessere> lista = (List<CategoriaTessere>)getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	@Override
	public CategoriaTessere leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(CategoriaTessere.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		CategoriaTessere categoriaTessere = (CategoriaTessere) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return categoriaTessere;
	}

}
