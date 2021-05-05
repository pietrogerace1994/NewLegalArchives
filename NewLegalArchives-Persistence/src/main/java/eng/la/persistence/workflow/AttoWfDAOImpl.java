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

import eng.la.model.AttoWf;
import eng.la.persistence.CostantiDAO;

@SuppressWarnings("unchecked")
@Component("attoWfDAO")
public class AttoWfDAOImpl extends HibernateDaoSupport implements AttoWfDAO, CostantiDAO {

	
	@Autowired
	public AttoWfDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}

	/**
	 * Avvia il workflow.
	 *
	 * @param vo: oggetto AttoWf da salvare
	 * @return oggetto AttoWf salvato
	 */
	@Override
	public AttoWf avviaWorkflow(AttoWf vo) {
		getHibernateTemplate().save(vo);
		return vo;
	}

	/**
	 * Recupera l'istanza di workflow.
	 * @param id identificativo del workflow
	 * @return oggetto AttoWf
	 */
	public AttoWf leggiWorkflow(long id) throws Throwable{
		DetachedCriteria criteria = DetachedCriteria.forClass( AttoWf.class ).add( Restrictions.eq("id", id) );
		AttoWf attoWf = (AttoWf) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
				
		return attoWf;

	}
	
	/**
	 * Aggiorna il workflow modificando solo i dati aggiornabili.
	 * @param vo: il workflow
	 *            
	 */
	@Override
	public void modifica(AttoWf vo) throws Throwable {
		getHibernateTemplate().update(vo); 
	}

	/**
	 * Recupera l'istanza attiva di workflow.
	 * @param idAtto identificativo dell'atto
	 * @return oggetto AttoWf
	 */
	@Override
	public AttoWf leggiWorkflowInCorso(long idAtto) throws Throwable{
		DetachedCriteria criteriaWf = DetachedCriteria.forClass( AttoWf.class )
				.addOrder(Order.desc("dataCreazione"));
		criteriaWf.add(Restrictions.isNull("dataChiusura"));
		criteriaWf.createAlias("atto", "atto");
		criteriaWf.add(Restrictions.eq("atto.id", idAtto));
		criteriaWf.createAlias("statoWf", "statoWf");
		criteriaWf.add(Restrictions.eq("statoWf.codGruppoLingua", CostantiDAO.STATO_WF_IN_CORSO));
		criteriaWf.add(Restrictions.eq("statoWf.lang", CostantiDAO.LINGUA_ITALIANA));
		
		List<AttoWf> lista = getHibernateTemplate().findByCriteria(criteriaWf);	
		if(lista.size() > 0)
			return lista.get(0);
		else
			return null;
	}
	
	public AttoWf leggiWorkflowAtto(long idAtto) throws Throwable{
		DetachedCriteria criteriaWf = DetachedCriteria.forClass( AttoWf.class )
				.addOrder(Order.desc("dataCreazione"));
		criteriaWf.add(Restrictions.isNull("dataChiusura"));
		criteriaWf.createAlias("atto", "atto");
		criteriaWf.add(Restrictions.eq("atto.id", idAtto));
		
		List<AttoWf> lista = getHibernateTemplate().findByCriteria(criteriaWf);	
		if(lista.size() > 0)
			return lista.get(0);
		else
			return null;
	}
	
	/**
	 * Recupera l'ultima istanza terminata di workflow.
	 * @param idAtto identificativo dell'atto
	 * @return oggetto AttoWf
	 */
	@Override
	public AttoWf leggiUltimoWorkflowTerminato(long idAtto) throws Throwable{
		DetachedCriteria criteriaWf = DetachedCriteria.forClass( AttoWf.class )
				.addOrder(Order.desc("dataCreazione"));
		criteriaWf.add(Restrictions.isNotNull("dataChiusura"));
		criteriaWf.createAlias("atto", "atto");
		criteriaWf.add(Restrictions.eq("atto.id", idAtto));
		criteriaWf.createAlias("statoWf", "statoWf");
		criteriaWf.add(Restrictions.eq("statoWf.codGruppoLingua", CostantiDAO.STATO_WF_COMPLETATO));
		criteriaWf.add(Restrictions.eq("statoWf.lang", CostantiDAO.LINGUA_ITALIANA));
		
		List<AttoWf> lista = getHibernateTemplate().findByCriteria(criteriaWf);	
		if(lista.size() > 0)
			return lista.get(0);
		else
			return null;
	}
	
	/**
	 * Recupera l'ultima istanza rifiutata di workflow.
	 * @param idAtto identificativo dell'atto
	 * @return oggetto AttoWf
	 */
	@Override
	public AttoWf leggiUltimoWorkflowRifiutato(long idAtto) throws Throwable{
		DetachedCriteria criteriaWf = DetachedCriteria.forClass( AttoWf.class )
				.addOrder(Order.desc("dataCreazione"));
		criteriaWf.add(Restrictions.isNotNull("dataChiusura"));
		criteriaWf.createAlias("atto", "atto");
		criteriaWf.add(Restrictions.eq("atto.id", idAtto));
		criteriaWf.createAlias("statoWf", "statoWf");
		criteriaWf.add(Restrictions.eq("statoWf.codGruppoLingua", CostantiDAO.STATO_WF_RIFIUTATO));
		criteriaWf.add(Restrictions.eq("statoWf.lang", CostantiDAO.LINGUA_ITALIANA));
		
		List<AttoWf> lista = getHibernateTemplate().findByCriteria(criteriaWf);	
		if(lista.size() > 0)
			return lista.get(0);
		else
			return null;
	}

		@Override
	public AttoWf leggiWorkflowDaAtto(long idAtto) {
		DetachedCriteria criteriaWf = DetachedCriteria.forClass( AttoWf.class )
				.addOrder(Order.desc("dataCreazione"));
		//criteriaWf.add(Restrictions.isNull("dataChiusura"));
		criteriaWf.createAlias("atto", "atto");
		criteriaWf.add(Restrictions.eq("atto.id", idAtto));
		List<AttoWf> lista = getHibernateTemplate().findByCriteria(criteriaWf);	
		if(lista.size() > 0)
			return lista.get(0);
		else
			return null;
	}
	

}
