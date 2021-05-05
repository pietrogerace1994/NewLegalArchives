package eng.la.persistence.workflow;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.ProformaWf;
import eng.la.persistence.CostantiDAO;

@SuppressWarnings("unchecked")
@Component("proformaWfDAO")
public class ProformaWfDAOImpl extends HibernateDaoSupport implements ProformaWfDAO, CostantiDAO {

	
	@Autowired
	public ProformaWfDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	/**
	 * Avvia il workflow.
	 *
	 * @param vo: oggetto ProformaWf da salvare
	 * @return oggetto ProformaWf salvato
	 */
	@Override
	public ProformaWf avviaWorkflow(ProformaWf vo) {
		getHibernateTemplate().save(vo);
		return vo;
	}
	
	/**
	 * Recupera l'istanza di workflow.
	 * @param id identificativo del workflow
	 * @return oggetto ProformaWf
	 */
	public ProformaWf leggiWorkflow(long id) throws Throwable{
		DetachedCriteria criteria = DetachedCriteria.forClass( ProformaWf.class ).add( Restrictions.eq("id", id) );
		ProformaWf proformaWf = (ProformaWf) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
				
		return proformaWf;

	}
	
	/**
	 * Aggiorna il workflow modificando solo i dati aggiornabili.
	 * @param vo: il workflow
	 *            
	 */
	@Override
	public void modifica(ProformaWf vo) throws Throwable {
		getHibernateTemplate().update(vo); 
	}
	
	/**
	 * Recupera l'istanza attiva di workflow.
	 * @param idProforma identificativo del proforma
	 * @return oggetto ProformaWf
	 */
	@Override
	public ProformaWf leggiWorkflowInCorso(long idProforma) throws Throwable{
		DetachedCriteria criteriaWf = DetachedCriteria.forClass( ProformaWf.class )
				.addOrder(Order.desc("dataCreazione"));
		criteriaWf.add(Restrictions.isNull("dataChiusura"));
		criteriaWf.createAlias("proforma", "proforma");
		criteriaWf.add(Restrictions.eq("proforma.id", idProforma));
		criteriaWf.createAlias("statoWf", "statoWf");
		criteriaWf.add(Restrictions.eq("statoWf.codGruppoLingua", CostantiDAO.STATO_WF_IN_CORSO));
		criteriaWf.add(Restrictions.eq("statoWf.lang", CostantiDAO.LINGUA_ITALIANA));
		
		List<ProformaWf> lista = getHibernateTemplate().findByCriteria(criteriaWf);	
		if(lista.size() > 0)
			return lista.get(0);
		else
			return null;
	}
	
	/**
	 * Recupera l'ultima istanza terminata di workflow.
	 * @param idProforma identificativo del proforma
	 * @return oggetto ProformaWf
	 */
	@Override
	public ProformaWf leggiUltimoWorkflowTerminato(long idProforma) throws Throwable{
		DetachedCriteria criteriaWf = DetachedCriteria.forClass( ProformaWf.class )
				.addOrder(Order.desc("dataCreazione"));
		criteriaWf.add(Restrictions.isNotNull("dataChiusura"));
		criteriaWf.createAlias("proforma", "proforma");
		criteriaWf.add(Restrictions.eq("proforma.id", idProforma));
		criteriaWf.createAlias("statoWf", "statoWf");
		criteriaWf.add(Restrictions.eq("statoWf.codGruppoLingua", CostantiDAO.STATO_WF_COMPLETATO));
		criteriaWf.add(Restrictions.eq("statoWf.lang", CostantiDAO.LINGUA_ITALIANA));
		
		List<ProformaWf> lista = getHibernateTemplate().findByCriteria(criteriaWf);	
		if(lista.size() > 0)
			return lista.get(0);
		else
			return null;
	}

	/**
	 * Recupera l'ultima istanza rifiutata di workflow.
	 * @param idProforma identificativo del proforma
	 * @return oggetto ProformaWf
	 */
	@Override
	public ProformaWf leggiUltimoWorkflowRifiutato(long idProforma) throws Throwable{
		DetachedCriteria criteriaWf = DetachedCriteria.forClass( ProformaWf.class )
				.addOrder(Order.desc("dataCreazione"));
		criteriaWf.add(Restrictions.isNotNull("dataChiusura"));
		criteriaWf.createAlias("proforma", "proforma");
		criteriaWf.add(Restrictions.eq("proforma.id", idProforma));
		criteriaWf.createAlias("statoWf", "statoWf");
		criteriaWf.add(Restrictions.eq("statoWf.codGruppoLingua", CostantiDAO.STATO_WF_RIFIUTATO));
		criteriaWf.add(Restrictions.eq("statoWf.lang", CostantiDAO.LINGUA_ITALIANA));
		
		List<ProformaWf> lista = getHibernateTemplate().findByCriteria(criteriaWf);	
		if(lista.size() > 0)
			return lista.get(0);
		else
			return null;
	}
	@Override
	public ProformaWf leggiWorkflowDaProforma(long idProforma) {
		DetachedCriteria criteriaWf = DetachedCriteria.forClass( ProformaWf.class )
				.addOrder(Order.desc("dataCreazione"));
		criteriaWf.createAlias("proforma", "proforma");
		criteriaWf.add(Restrictions.eq("proforma.id", idProforma));
		
		List<ProformaWf> lista = getHibernateTemplate().findByCriteria(criteriaWf);	
		if(lista.size() > 0)
			return lista.get(0);
		else
			return null;
	}


}
