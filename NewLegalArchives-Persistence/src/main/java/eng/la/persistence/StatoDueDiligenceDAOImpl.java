package eng.la.persistence;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.StatoDueDiligence;

@Component("statoDueDiligenceDAO")
public class StatoDueDiligenceDAOImpl extends HibernateDaoSupport implements StatoDueDiligenceDAO {
	
	@Autowired
	public StatoDueDiligenceDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	
	@Override
	public List<StatoDueDiligence> leggi(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( StatoDueDiligence.class ).addOrder(Order.asc("descrizione"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("lang", lingua));
		@SuppressWarnings("unchecked")
		List<StatoDueDiligence> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista; 
	}
	
	@Override
	public StatoDueDiligence readStatoDueDiligenceByFilter(String lingua, String codGruppoLingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( StatoDueDiligence.class );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.eq("codGruppoLingua", codGruppoLingua));
		@SuppressWarnings("unchecked")
		List<StatoDueDiligence> list = getHibernateTemplate().findByCriteria(criteria);
		return list.get(0); 
	}
	
} 