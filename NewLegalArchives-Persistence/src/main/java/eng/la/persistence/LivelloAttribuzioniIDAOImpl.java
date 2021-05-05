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

import eng.la.model.LivelloAttribuzioniI;

@Component("livelloAttribuzioniIDAO")
public class LivelloAttribuzioniIDAOImpl extends HibernateDaoSupport implements LivelloAttribuzioniIDAO, CostantiDAO {

	@Autowired
	public LivelloAttribuzioniIDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<LivelloAttribuzioniI> leggi(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(LivelloAttribuzioniI.class).addOrder(Order.asc("descrizione"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add( Restrictions.eq("lang", lingua) );
		@SuppressWarnings("unchecked")
		List<LivelloAttribuzioniI> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public LivelloAttribuzioniI leggi(String code, String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( LivelloAttribuzioniI.class )
				.add( Restrictions.eq("lang", lingua) )
				.add( Restrictions.eq("codGruppoLingua", code) );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		LivelloAttribuzioniI livelloAttribuzioniI = (LivelloAttribuzioniI) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return livelloAttribuzioniI;
	}

	@Override
	public LivelloAttribuzioniI findLivelloAttribuzioniIByPk(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( LivelloAttribuzioniI.class )
				.add( Restrictions.eq("id", id) );
		@SuppressWarnings("unchecked")
		LivelloAttribuzioniI livelloAttribuzioniI = (LivelloAttribuzioniI) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return livelloAttribuzioniI;
	}
}