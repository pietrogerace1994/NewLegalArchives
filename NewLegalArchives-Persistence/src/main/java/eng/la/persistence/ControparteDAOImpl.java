package eng.la.persistence;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.Controparte;

@Component("controparteDAO")
public class ControparteDAOImpl extends HibernateDaoSupport implements ControparteDAO, CostantiDAO {

	@Autowired
	public ControparteDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public void inserisciControparte(Controparte controparte) throws Throwable {
		getHibernateTemplate().save(controparte);
	}

	@Override
	public Controparte leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Controparte.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.createAlias("fascicolo", "fascicolo");
		criteria.add(Restrictions.eq("fascicolo.id", id));
		@SuppressWarnings("unchecked")
		Controparte controparte = (Controparte) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));

		return controparte;
	}

	@Override
	public List<Controparte> leggi(String term, String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Controparte.class).addOrder(Order.asc("nome"));

		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.ilike("nome", term, MatchMode.ANYWHERE)); 
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<Controparte> lista = (List<Controparte>)getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	@Override
	public List<Controparte> leggiDaFascicolo(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Controparte.class).addOrder(Order.asc("nome"));
 
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<Controparte> lista = (List<Controparte>)getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	public List<Controparte> getControparteDaIdFascicolo(long idFascicolo) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(Controparte.class).addOrder(Order.asc("nome"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.createAlias("fascicolo", "fascicolo");
		criteria.add(Restrictions.eq("fascicolo.id", idFascicolo));
		@SuppressWarnings("unchecked")
		List<Controparte> lista = (List<Controparte>)getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

}
