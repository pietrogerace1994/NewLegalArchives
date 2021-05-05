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

import eng.la.model.CategoriaContest;

@Component("categoriaContestDAO")
public class CategoriaContestDAOImpl extends HibernateDaoSupport implements CategoriaContestDAO {
	
	@Autowired
	public CategoriaContestDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<CategoriaContest> leggi(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( CategoriaContest.class ).addOrder(Order.asc("descrizione"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("lang", lingua));
		@SuppressWarnings("unchecked")
		List<CategoriaContest> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista; 
	} 
	
	@Override
	public CategoriaContest leggi(String codice, String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( CategoriaContest.class )
				.add( Restrictions.eq("lang", lingua) )
				.add( Restrictions.eq("codGruppoLingua", codice) );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		CategoriaContest categoriaContest = (CategoriaContest) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return categoriaContest; 
	}

} 