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

import eng.la.model.TipoScadenza;

@Component("tipoScadenzaDAO")
public class TipoScadenzaDAOImpl extends HibernateDaoSupport implements TipoScadenzaDAO {
	
	@Autowired
	public TipoScadenzaDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<TipoScadenza> leggi(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoScadenza.class).addOrder(Order.asc("nome"));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<TipoScadenza> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	@Override
	public TipoScadenza leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoScadenza.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		TipoScadenza tipoScadenza = (TipoScadenza) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return tipoScadenza;
	}
}