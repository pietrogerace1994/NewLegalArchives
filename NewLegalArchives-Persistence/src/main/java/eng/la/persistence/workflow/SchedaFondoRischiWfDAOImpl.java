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

import eng.la.model.SchedaFondoRischiWf;
import eng.la.persistence.CostantiDAO;

@SuppressWarnings("unchecked")
@Component("schedaFondoRischiWfDAO")
public class SchedaFondoRischiWfDAOImpl extends HibernateDaoSupport implements SchedaFondoRischiWfDAO, CostantiDAO {

	
	@Autowired
	public SchedaFondoRischiWfDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	/**
	 * Avvia il workflow.
	 *
	 * @param vo: oggetto IncaricoWf da salvare
	 * @return oggetto SchedaFondoRischiWf salvato
	 */
	@Override
	public SchedaFondoRischiWf avviaWorkflow(SchedaFondoRischiWf vo) {
		getHibernateTemplate().save(vo);
		return vo;
	}
	
	/**
	 * Recupera l'istanza di workflow.
	 * @param id identificativo del workflow
	 * @return oggetto SchedaFondoRischiWf
	 */
	public SchedaFondoRischiWf leggiWorkflow(long id) throws Throwable{
		DetachedCriteria criteria = DetachedCriteria.forClass( SchedaFondoRischiWf.class ).add( Restrictions.eq("id", id) );
		SchedaFondoRischiWf schedaFondoRischiWf = (SchedaFondoRischiWf) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
				
		return schedaFondoRischiWf;
	}
	
	/**
	 * Aggiorna il workflow modificando solo i dati aggiornabili.
	 * @param vo: il workflow
	 *            
	 */
	@Override
	public void modifica(SchedaFondoRischiWf vo) throws Throwable {
		getHibernateTemplate().update(vo); 
	}
	
	/**
	 * Recupera l'istanza attiva di workflow.
	 * @param idIncarico identificativo dell'incarico
	 * @return oggetto IncaricoWf
	 */
	@Override
	public SchedaFondoRischiWf leggiWorkflowInCorso(long idSchedaFondoRischi) throws Throwable{
		DetachedCriteria criteriaWf = DetachedCriteria.forClass( SchedaFondoRischiWf.class )
				.addOrder(Order.desc("dataCreazione"));
		criteriaWf.add(Restrictions.isNull("dataChiusura"));
		criteriaWf.createAlias("schedaFondoRischi", "schedaFondoRischi");
		criteriaWf.add(Restrictions.eq("schedaFondoRischi.id", idSchedaFondoRischi));
		criteriaWf.createAlias("statoWf", "statoWf");
		criteriaWf.add(Restrictions.eq("statoWf.codGruppoLingua", CostantiDAO.STATO_WF_IN_CORSO));
		criteriaWf.add(Restrictions.eq("statoWf.lang", CostantiDAO.LINGUA_ITALIANA));
		
		List<SchedaFondoRischiWf> lista = getHibernateTemplate().findByCriteria(criteriaWf);	
		if(lista.size() > 0)
			return lista.get(0);
		else
			return null;
	}
	
	/**
	 * Recupera l'ultima istanza terminata di workflow.
	 * @param idIncarico identificativo dell'incarico
	 * @return oggetto SchedaFondoRischiWf
	 */
	@Override
	public SchedaFondoRischiWf leggiUltimoWorkflowTerminato(long idSchedaFondoRischi) throws Throwable{
		DetachedCriteria criteriaWf = DetachedCriteria.forClass( SchedaFondoRischiWf.class )
				.addOrder(Order.desc("dataCreazione"));
		criteriaWf.add(Restrictions.isNotNull("dataChiusura"));
		criteriaWf.createAlias("schedaFondoRischi", "schedaFondoRischi");
		criteriaWf.add(Restrictions.eq("schedaFondoRischi.id", idSchedaFondoRischi));
		criteriaWf.createAlias("statoWf", "statoWf");
		criteriaWf.add(Restrictions.eq("statoWf.codGruppoLingua", CostantiDAO.STATO_WF_COMPLETATO));
		criteriaWf.add(Restrictions.eq("statoWf.lang", CostantiDAO.LINGUA_ITALIANA));
		
		List<SchedaFondoRischiWf> lista = getHibernateTemplate().findByCriteria(criteriaWf);	
		if(lista.size() > 0)
			return lista.get(0);
		else
			return null;
	}
	
	/**
	 * Recupera l'ultima istanza rifiutata di workflow.
	 * @param idIncarico identificativo dell'incarico
	 * @return oggetto SchedaFondoRischiWf
	 */
	@Override
	public SchedaFondoRischiWf leggiUltimoWorkflowRifiutato(long idSchedaFondoRischi) throws Throwable{
		DetachedCriteria criteriaWf = DetachedCriteria.forClass( SchedaFondoRischiWf.class )
				.addOrder(Order.desc("dataCreazione"));
		criteriaWf.add(Restrictions.isNotNull("dataChiusura"));
		criteriaWf.createAlias("schedaFondoRischi", "schedaFondoRischi");
		criteriaWf.add(Restrictions.eq("schedaFondoRischi.id", idSchedaFondoRischi));
		criteriaWf.createAlias("statoWf", "statoWf");
		criteriaWf.add(Restrictions.eq("statoWf.codGruppoLingua", CostantiDAO.STATO_WF_RIFIUTATO));
		criteriaWf.add(Restrictions.eq("statoWf.lang", CostantiDAO.LINGUA_ITALIANA));
		
		List<SchedaFondoRischiWf> lista = getHibernateTemplate().findByCriteria(criteriaWf);	
		if(lista.size() > 0)
			return lista.get(0);
		else
			return null;
	}
	
	@Override
	public SchedaFondoRischiWf leggiWorkflowdaIncarico(long idSchedaFondoRischi) {
		DetachedCriteria criteriaWf = DetachedCriteria.forClass( SchedaFondoRischiWf.class )
				.addOrder(Order.desc("dataCreazione"));
		criteriaWf.createAlias("schedaFondoRischi", "schedaFondoRischi");
		criteriaWf.add(Restrictions.eq("schedaFondoRischi.id", idSchedaFondoRischi));
		
		List<SchedaFondoRischiWf> lista = getHibernateTemplate().findByCriteria(criteriaWf);	
		if(lista.size() > 0)
			return lista.get(0);
		else
			return null;
	}


}
