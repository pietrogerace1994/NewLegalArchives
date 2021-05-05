package eng.la.persistence;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.StatoRichAutGiud;

@Component("statoRichiestaAutGiudiziariaDAO")
public class StatoRichiestaAutGiudiziariaDAOImpl extends HibernateDaoSupport implements StatoRichiestaAutGiudiziariaDAO {
	
	@Autowired
	public StatoRichiestaAutGiudiziariaDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	
	@Override
	public List<StatoRichAutGiud> leggi(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( StatoRichAutGiud.class ).addOrder(Order.asc("descrizione"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("lang", lingua));
		@SuppressWarnings("unchecked")
		List<StatoRichAutGiud> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista; 
	}
	
	@Override
	public StatoRichAutGiud readStatoRichAutGiudByFilter(String lingua, String codGruppoLingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( StatoRichAutGiud.class );
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("lang", lingua));
		criteria.add(Restrictions.eq("codGruppoLingua", codGruppoLingua));
		@SuppressWarnings("unchecked")
		List<StatoRichAutGiud> list = getHibernateTemplate().findByCriteria(criteria);
		return list.get(0); 
	}
	
} 