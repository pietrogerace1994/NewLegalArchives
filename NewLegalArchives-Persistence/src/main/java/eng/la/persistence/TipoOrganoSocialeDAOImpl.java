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

import eng.la.model.TipoOrganoSociale;


@Component("tipoOrganoSocialeDAO")
public class TipoOrganoSocialeDAOImpl extends HibernateDaoSupport implements TipoOrganoSocialeDAO {

	@Autowired
	public TipoOrganoSocialeDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<TipoOrganoSociale> leggi(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoOrganoSociale.class).addOrder(Order.asc("descrizione"));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.isNull("dataCancellazione"));	
		@SuppressWarnings("unchecked")
		List<TipoOrganoSociale> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	@Override
	public TipoOrganoSociale leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoOrganoSociale.class).add(Restrictions.eq("id", id));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		TipoOrganoSociale categoriaTessere = (TipoOrganoSociale) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return categoriaTessere;
	}

}
