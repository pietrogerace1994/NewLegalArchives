package eng.la.persistence;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.TipologiaRichiesta;

@Component("tipoRichiestaAutGiudiziariaDAO")
public class TipoRichiestaAutGiudiziariaDAOImpl extends HibernateDaoSupport implements TipoRichiestaAutGiudiziariaDAO {
	
	@Autowired
	public TipoRichiestaAutGiudiziariaDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	
	@Override
	public List<TipologiaRichiesta> leggi(String lingua) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( TipologiaRichiesta.class ).addOrder(Order.asc("nome"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.add(Restrictions.eq("lang", lingua));
		@SuppressWarnings("unchecked")
		List<TipologiaRichiesta> lista = getHibernateTemplate().findByCriteria(criteria);
		return lista; 
	}
	
} 