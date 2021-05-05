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

import eng.la.model.TerzoChiamatoCausa;

@Component("terzoChiamatoInCausaDAO")
public class TerzoChiamatoInCausaDAOImpl extends HibernateDaoSupport implements TerzoChiamatoInCausaDAO, CostantiDAO {

	@Autowired
	public TerzoChiamatoInCausaDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public void inserisciTerzoChiamatoInCausa(TerzoChiamatoCausa terzoChiamatoCausa) throws Throwable {
		getHibernateTemplate().save(terzoChiamatoCausa);
	}

	@Override
	public List<TerzoChiamatoCausa> leggiPerAutocomplete(String term, String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TerzoChiamatoCausa.class)
				.addOrder(Order.asc("nome"));

		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.ilike("nome", term, MatchMode.ANYWHERE));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		List<TerzoChiamatoCausa> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public TerzoChiamatoCausa leggi(long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(TerzoChiamatoCausa.class);
		criteria.add(Restrictions.eq("id", id)); 
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		TerzoChiamatoCausa terzoChiamatoCausa = (TerzoChiamatoCausa) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return terzoChiamatoCausa;
	}

}
