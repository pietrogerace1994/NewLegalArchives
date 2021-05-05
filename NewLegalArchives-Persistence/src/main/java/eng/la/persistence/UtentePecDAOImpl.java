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

import eng.la.model.UtentePec;

/**
 * <h1>Classe DAO d'implemtazione delle operazioni su base dati, per entità
 * UtentePec</h1> La classe DAO espone le operazioni di lettura/scrittura
 * sulla base dati per l'entità UtentePec.
 * 
 * @author ACER
 */
@SuppressWarnings("unchecked")
@Component("utentePecDAO")
public class UtentePecDAOImpl extends HibernateDaoSupport implements UtentePecDAO, CostantiDAO {

	@Autowired
	public UtentePecDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<UtentePec> leggi(String userId) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(UtentePec.class);
		criteria.add(Restrictions.like("userId", "%"+userId+"%"));
		criteria.add(Restrictions.eq("cancellato", 0));
		criteria.add(Restrictions.in("stato", new Integer[]{EMAIL_PEC_NON_GESTITA}));
		criteria.addOrder(Order.desc("data"));
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@Override
	public void riportaPec(Long id)throws Throwable {
		UtentePec vo = leggi(id);
		vo.setStato(EMAIL_PEC_NON_GESTITA);
		getHibernateTemplate().update(vo);
	}
	
	@Override
	public void annullaPec(Long id)throws Throwable {
		UtentePec vo = leggi(id);
		vo.setStato(EMAIL_PEC_ANNULLATA);
		getHibernateTemplate().update(vo);
	}

	@Override
	public void trasformaPec(Long id)throws Throwable {
		UtentePec vo = leggi(id);
		vo.setStato(EMAIL_PEC_TRASFORMATA_IN_ATTO);
		getHibernateTemplate().update(vo);
	}
	
	@Override
	public void spostProtPec(Long id)throws Throwable {
		UtentePec vo = leggi(id);
		vo.setStato(EMAIL_PEC_SPOSTATA_IN_ARCH_PROT);
		getHibernateTemplate().update(vo);
	}
	
	@Override
	public void inviaAltriUffPec(Long id, String utenteAltriUff, String emailAltriUff)throws Throwable {
		UtentePec vo = leggi(id);
		vo.setStato(EMAIL_PEC_INVIATA_AD_ALTRI_UFFICI);
		vo.setEmailInvioAltriUffici(emailAltriUff);
		vo.setUtenteInvioAltriUffici(utenteAltriUff);
		getHibernateTemplate().update(vo);
	}
	
	@Override
	public UtentePec leggi(Long id) {
		DetachedCriteria criteria = DetachedCriteria.forClass(UtentePec.class).add(Restrictions.eq("id", id));
		UtentePec utentePec = (UtentePec) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));

		return utentePec;
	}
	
	
}
