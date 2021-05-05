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

import eng.la.model.RischioSoccombenza;

@Component("rischioSoccombenzaDAO")
public class RischioSoccombenzaDAOImpl extends HibernateDaoSupport implements RischioSoccombenzaDAO, CostantiDAO {

	@Autowired
	public RischioSoccombenzaDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<RischioSoccombenza> leggi(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(RischioSoccombenza.class).addOrder(Order.asc("descrizione"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add( Restrictions.eq("lang", lingua) );
		@SuppressWarnings("unchecked")
		List<RischioSoccombenza> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public RischioSoccombenza leggi(String code, String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( RischioSoccombenza.class )
				.add( Restrictions.eq("lang", lingua) )
				.add( Restrictions.eq("codGruppoLingua", code) );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		RischioSoccombenza rischioSoccombenza = (RischioSoccombenza) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return rischioSoccombenza;
	}
}