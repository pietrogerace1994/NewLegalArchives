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

import eng.la.model.LivelloAttribuzioniII;

@Component("livelloAttribuzioniIIDAO")
public class LivelloAttribuzioniIIDAOImpl extends HibernateDaoSupport implements LivelloAttribuzioniIIDAO, CostantiDAO {

	@Autowired
	public LivelloAttribuzioniIIDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<LivelloAttribuzioniII> leggi(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(LivelloAttribuzioniII.class).addOrder(Order.asc("descrizione"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add( Restrictions.eq("lang", lingua) );
		@SuppressWarnings("unchecked")
		List<LivelloAttribuzioniII> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public LivelloAttribuzioniII leggi(String code, String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( LivelloAttribuzioniII.class )
				.add( Restrictions.eq("lang", lingua) )
				.add( Restrictions.eq("codGruppoLingua", code) );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		LivelloAttribuzioniII livelloAttribuzioniII = (LivelloAttribuzioniII) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return livelloAttribuzioniII;
	}

	@Override
	public LivelloAttribuzioniII findLivelloAttribuzioniIIByPk(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( LivelloAttribuzioniII.class )
				.add( Restrictions.eq("id", id) );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		LivelloAttribuzioniII livelloAttribuzioniII = (LivelloAttribuzioniII) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return livelloAttribuzioniII;
	}
}