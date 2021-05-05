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

import eng.la.model.PrimoLivelloAttribuzioni;


@Component("primoLivelloAttribuzioniDAO")
public class PrimoLivelloAttribuzioniDAOImpl extends HibernateDaoSupport implements PrimoLivelloAttribuzioniDAO {

	@Autowired
	public PrimoLivelloAttribuzioniDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<PrimoLivelloAttribuzioni> leggi(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(PrimoLivelloAttribuzioni.class).addOrder(Order.asc("descrizione"));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));	
		@SuppressWarnings("unchecked")
		List<PrimoLivelloAttribuzioni> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	@Override
	public PrimoLivelloAttribuzioni leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(PrimoLivelloAttribuzioni.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		PrimoLivelloAttribuzioni primoLivelloAttribuzioni = (PrimoLivelloAttribuzioni) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return primoLivelloAttribuzioni;
	}

}
