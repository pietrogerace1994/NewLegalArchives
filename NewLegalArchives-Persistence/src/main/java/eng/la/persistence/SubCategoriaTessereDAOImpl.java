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

import eng.la.model.SubCategoriaTessere;


@Component("subcategoriaTessereDAO")
public class SubCategoriaTessereDAOImpl extends HibernateDaoSupport implements SubCategoriaTessereDAO {

	@Autowired
	public SubCategoriaTessereDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<SubCategoriaTessere> leggi(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(SubCategoriaTessere.class).addOrder(Order.asc("descrizione"));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));	
		@SuppressWarnings("unchecked")
		List<SubCategoriaTessere> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	@Override
	public SubCategoriaTessere leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(SubCategoriaTessere.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		SubCategoriaTessere categoriaTessere = (SubCategoriaTessere) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return categoriaTessere;
	}

}
