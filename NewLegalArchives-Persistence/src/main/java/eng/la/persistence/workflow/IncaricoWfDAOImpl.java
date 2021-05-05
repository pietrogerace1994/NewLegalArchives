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

import eng.la.model.IncaricoWf;
import eng.la.persistence.CostantiDAO;

@SuppressWarnings("unchecked")
@Component("incaricoWfDAO")
public class IncaricoWfDAOImpl extends HibernateDaoSupport implements IncaricoWfDAO, CostantiDAO {

	
	@Autowired
	public IncaricoWfDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	/**
	 * Avvia il workflow.
	 *
	 * @param vo: oggetto IncaricoWf da salvare
	 * @return oggetto IncaricoWf salvato
	 */
	@Override
	public IncaricoWf avviaWorkflow(IncaricoWf vo) {
		getHibernateTemplate().save(vo);
		return vo;
	}
	
	/**
	 * Recupera l'istanza di workflow.
	 * @param id identificativo del workflow
	 * @return oggetto IncaricoWf
	 */
	public IncaricoWf leggiWorkflow(long id) throws Throwable{
		DetachedCriteria criteria = DetachedCriteria.forClass( IncaricoWf.class ).add( Restrictions.eq("id", id) );
		IncaricoWf incaricoWf = (IncaricoWf) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
				
		return incaricoWf;

	}
	
	/**
	 * Aggiorna il workflow modificando solo i dati aggiornabili.
	 * @param vo: il workflow
	 *            
	 */
	@Override
	public void modifica(IncaricoWf vo) throws Throwable {
		getHibernateTemplate().update(vo); 
	}
	
	/**
	 * Recupera l'istanza attiva di workflow.
	 * @param idIncarico identificativo dell'incarico
	 * @return oggetto IncaricoWf
	 */
	@Override
	public IncaricoWf leggiWorkflowInCorso(long idIncarico) throws Throwable{
		DetachedCriteria criteriaWf = DetachedCriteria.forClass( IncaricoWf.class )
				.addOrder(Order.desc("dataCreazione"));
		criteriaWf.add(Restrictions.isNull("dataChiusura"));
		criteriaWf.createAlias("incarico", "incarico");
		criteriaWf.add(Restrictions.eq("incarico.id", idIncarico));
		criteriaWf.createAlias("statoWf", "statoWf");
		criteriaWf.add(Restrictions.eq("statoWf.codGruppoLingua", CostantiDAO.STATO_WF_IN_CORSO));
		criteriaWf.add(Restrictions.eq("statoWf.lang", CostantiDAO.LINGUA_ITALIANA));
		
		List<IncaricoWf> lista = getHibernateTemplate().findByCriteria(criteriaWf);	
		if(lista.size() > 0)
			return lista.get(0);
		else
			return null;
	}
	
	/**
	 * Recupera l'ultima istanza terminata di workflow.
	 * @param idIncarico identificativo dell'incarico
	 * @return oggetto IncaricoWf
	 */
	@Override
	public IncaricoWf leggiUltimoWorkflowTerminato(long idIncarico) throws Throwable{
		DetachedCriteria criteriaWf = DetachedCriteria.forClass( IncaricoWf.class )
				.addOrder(Order.desc("dataCreazione"));
		criteriaWf.add(Restrictions.isNotNull("dataChiusura"));
		criteriaWf.createAlias("incarico", "incarico");
		criteriaWf.add(Restrictions.eq("incarico.id", idIncarico));
		criteriaWf.createAlias("statoWf", "statoWf");
		criteriaWf.add(Restrictions.eq("statoWf.codGruppoLingua", CostantiDAO.STATO_WF_COMPLETATO));
		criteriaWf.add(Restrictions.eq("statoWf.lang", CostantiDAO.LINGUA_ITALIANA));
		
		List<IncaricoWf> lista = getHibernateTemplate().findByCriteria(criteriaWf);	
		if(lista.size() > 0)
			return lista.get(0);
		else
			return null;
	}
	
	/**
	 * Recupera l'ultima istanza rifiutata di workflow.
	 * @param idIncarico identificativo dell'incarico
	 * @return oggetto IncaricoWf
	 */
	@Override
	public IncaricoWf leggiUltimoWorkflowRifiutato(long idIncarico) throws Throwable{
		DetachedCriteria criteriaWf = DetachedCriteria.forClass( IncaricoWf.class )
				.addOrder(Order.desc("dataCreazione"));
		criteriaWf.add(Restrictions.isNotNull("dataChiusura"));
		criteriaWf.createAlias("incarico", "incarico");
		criteriaWf.add(Restrictions.eq("incarico.id", idIncarico));
		criteriaWf.createAlias("statoWf", "statoWf");
		criteriaWf.add(Restrictions.eq("statoWf.codGruppoLingua", CostantiDAO.STATO_WF_RIFIUTATO));
		criteriaWf.add(Restrictions.eq("statoWf.lang", CostantiDAO.LINGUA_ITALIANA));
		
		List<IncaricoWf> lista = getHibernateTemplate().findByCriteria(criteriaWf);	
		if(lista.size() > 0)
			return lista.get(0);
		else
			return null;
	}
	@Override
	public IncaricoWf leggiWorkflowdaIncarico(long idIncarico) {
		DetachedCriteria criteriaWf = DetachedCriteria.forClass( IncaricoWf.class )
				.addOrder(Order.desc("dataCreazione"));
		criteriaWf.createAlias("incarico", "incarico");
		criteriaWf.add(Restrictions.eq("incarico.id", idIncarico));
		
		List<IncaricoWf> lista = getHibernateTemplate().findByCriteria(criteriaWf);	
		if(lista.size() > 0)
			return lista.get(0);
		else
			return null;
	}


}
