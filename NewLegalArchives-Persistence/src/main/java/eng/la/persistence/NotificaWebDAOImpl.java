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

import eng.la.model.NotificaWeb;

/**
 * <h1>Classe DAO d'implemtazione delle operazioni su base dati, per entità
 * NotificaWeb</h1> La classe DAO espone le operazioni di lettura/scrittura
 * sulla base dati per l'entità NotificaWeb.
 * 
 * @author ACER
 */
@SuppressWarnings("unchecked")
@Component("notificaWebDAO")
public class NotificaWebDAOImpl extends HibernateDaoSupport implements NotificaWebDAO, CostantiDAO {

	@Autowired
	public NotificaWebDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public void inserisci(NotificaWeb notificaWeb) throws Throwable {
		getHibernateTemplate().save(notificaWeb);
	}

	@Override
	public void aggiorna(NotificaWeb notificaWeb) throws Throwable {
		getHibernateTemplate().update(notificaWeb);
	}

	@Override
	public List<NotificaWeb> leggi(String matricola) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(NotificaWeb.class);
		criteria.add(Restrictions.eq("matricolaDest.matricolaUtil", matricola));
		criteria.add(Restrictions.isNull("dataLettura"));
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@Override
	public List<NotificaWeb> leggiPerInvioMail(String matricola,String keyMessage) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(NotificaWeb.class);
		criteria.add(Restrictions.eq("matricolaDest.matricolaUtil", matricola));
		criteria.add(Restrictions.eq("keyMessage",keyMessage));
		criteria.add(Restrictions.isNull("dataInvioMail"));
		criteria.addOrder(Order.asc("id"));	
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public NotificaWeb leggiById(Long id) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(NotificaWeb.class);
		criteria.add(Restrictions.eq("id", id));
		return  (NotificaWeb) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));	
	}

}
