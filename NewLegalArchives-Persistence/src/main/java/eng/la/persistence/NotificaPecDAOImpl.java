package eng.la.persistence;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.NotificaPec;

/**
 * <h1>Classe DAO d'implemtazione delle operazioni su base dati, per entità
 * NotificaPec</h1> La classe DAO espone le operazioni di lettura/scrittura
 * sulla base dati per l'entità NotificaPec.
 * 
 * @author ACER
 */
@SuppressWarnings("unchecked")
@Component("notificaPecDAO")
public class NotificaPecDAOImpl extends HibernateDaoSupport implements NotificaPecDAO, CostantiDAO {

	@Autowired
	public NotificaPecDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public void inserisci(NotificaPec notificaPec) throws Throwable {
		getHibernateTemplate().save(notificaPec);
	}

	@Override
	public void aggiorna(NotificaPec notificaPec) throws Throwable {
		getHibernateTemplate().update(notificaPec);
	}

	@Override
	public void cancellaAltre(Long id, Long idUtentePec) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(NotificaPec.class);
		criteria.add(Restrictions.eq("utentePec.id", idUtentePec));
		criteria.add(Restrictions.ne("id", id));
		criteria.add(Restrictions.eq("cancellato", 0));
		criteria.add(Restrictions.eq("stato", 0));
		List<NotificaPec> list = getHibernateTemplate().findByCriteria(criteria);
		
		for (NotificaPec notificaPec : list) {
			notificaPec.setCancellato(1);
			notificaPec.setDataCancellazione(new Date());
			getHibernateTemplate().update(notificaPec);
		}
	}
	
	@Override
	public List<NotificaPec> leggi(String matricola) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(NotificaPec.class);
		criteria.add(Restrictions.eq("utente.matricolaUtil", matricola));
		criteria.add(Restrictions.eq("stato", 0));
		criteria.add(Restrictions.eq("cancellato", 0));
		criteria.addOrder(Order.desc("dataNotifica"));
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@Override
	public NotificaPec leggiById(Long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(NotificaPec.class);
		criteria.add(Restrictions.eq("id", id));
		return  (NotificaPec) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));	
	}

}
