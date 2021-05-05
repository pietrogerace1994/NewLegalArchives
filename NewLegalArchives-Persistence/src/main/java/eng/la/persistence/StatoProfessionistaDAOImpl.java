package eng.la.persistence;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.StatoProfessionista;

@Component("statoProfessionistaDAO")
public class StatoProfessionistaDAOImpl extends HibernateDaoSupport implements StatoProfessionistaDAO {
	
	@Autowired
	public StatoProfessionistaDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}

	@Cacheable(value="statoProfessionistaCacheLeggiPerLingua") 
	@Override
	public List<StatoProfessionista> leggi(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( StatoProfessionista.class ).addOrder(Order.asc("descrizione"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("lang", lingua));
		@SuppressWarnings("unchecked")
		List<StatoProfessionista> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista; 
	} 
	
	@Cacheable(value="statoProfessionistaCacheLeggiPerCodiceLingua") 
	@Override
	public StatoProfessionista leggi(String codice, String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( StatoProfessionista.class )
				.add( Restrictions.eq("lang", lingua) )
				.add( Restrictions.eq("codGruppoLingua", codice) );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		@SuppressWarnings("unchecked")
		StatoProfessionista statoProfessionista = (StatoProfessionista) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return statoProfessionista; 
	}

} 