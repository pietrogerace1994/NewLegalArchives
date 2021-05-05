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

import eng.la.model.TipoSocieta;


@Component("tipoSocietaDAO")
public class TipoSocietaDAOImpl extends HibernateDaoSupport implements TipoSocietaDAO {

	@Autowired
	public TipoSocietaDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

//	@Cacheable("tipoSocietaCacheLeggi")
	@Override
	public List<TipoSocieta> leggi(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoSocieta.class).addOrder(Order.asc("descrizione"));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));	
		@SuppressWarnings("unchecked")
		List<TipoSocieta> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
//	@Cacheable("tipoSocietaCacheLeggiDaId")
	@Override
	public TipoSocieta leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoSocieta.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		TipoSocieta tipoSocieta = (TipoSocieta) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return tipoSocieta;
	}

}
