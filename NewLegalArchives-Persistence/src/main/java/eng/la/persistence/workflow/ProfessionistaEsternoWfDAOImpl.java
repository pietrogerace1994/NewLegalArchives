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

import eng.la.model.ProfessionistaEsternoWf;
import eng.la.persistence.CostantiDAO;

@SuppressWarnings("unchecked")
@Component("professionistaEsternoWfDAO")
public class ProfessionistaEsternoWfDAOImpl extends HibernateDaoSupport implements ProfessionistaEsternoWfDAO, CostantiDAO {

	
	@Autowired
	public ProfessionistaEsternoWfDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	/**
	 * Avvia il workflow.
	 *
	 * @param vo: oggetto ProfessionistaEsternoWf da salvare
	 * @return oggetto ProfessionistaEsternoWf salvato
	 */
	@Override
	public ProfessionistaEsternoWf avviaWorkflow(ProfessionistaEsternoWf vo) {
		getHibernateTemplate().save(vo);
		return vo;
	}
	
	/**
	 * Recupera l'istanza di workflow.
	 * @param id identificativo del workflow
	 * @return oggetto ProfessionistaEsternoWf
	 */
	public ProfessionistaEsternoWf leggiWorkflow(long id) throws Throwable{
		DetachedCriteria criteria = DetachedCriteria.forClass( ProfessionistaEsternoWf.class ).add( Restrictions.eq("id", id) );
		ProfessionistaEsternoWf professionistaEsternoWf = (ProfessionistaEsternoWf) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
				
		return professionistaEsternoWf;

	}
	
	/**
	 * Aggiorna il workflow modificando solo i dati aggiornabili.
	 * @param vo: il workflow
	 *            
	 */
	@Override
	public void modifica(ProfessionistaEsternoWf vo) throws Throwable {
		getHibernateTemplate().update(vo); 
	}

	/**
	 * Recupera l'istanza attiva di workflow.
	 * @param idProfessionista identificativo del professionista
	 * @return oggetto ProfessionistaEsternoWf
	 */
	@Override
	public ProfessionistaEsternoWf leggiWorkflowInCorso(long idProfessionista) throws Throwable{
		DetachedCriteria criteriaWf = DetachedCriteria.forClass( ProfessionistaEsternoWf.class )
				.addOrder(Order.desc("dataCreazione"));
		criteriaWf.add(Restrictions.isNull("dataChiusura"));
		criteriaWf.createAlias("professionistaEsterno", "professionistaEsterno");
		criteriaWf.add(Restrictions.eq("professionistaEsterno.id", idProfessionista));
		criteriaWf.createAlias("statoWf", "statoWf");
		criteriaWf.add(Restrictions.eq("statoWf.codGruppoLingua", CostantiDAO.STATO_WF_IN_CORSO));
		criteriaWf.add(Restrictions.eq("statoWf.lang", CostantiDAO.LINGUA_ITALIANA));
		
		List<ProfessionistaEsternoWf> lista = getHibernateTemplate().findByCriteria(criteriaWf);	
		if(lista.size() > 0)
			return lista.get(0);
		else
			return null;
	}
	
	/**
	 * Recupera l'ultima istanza terminata di workflow.
	 * @param idProfessionista identificativo del professionista
	 * @return oggetto ProfessionistaEsternoWf
	 */
	@Override
	public ProfessionistaEsternoWf leggiUltimoWorkflowTerminato(long idProfessionista) throws Throwable{
		DetachedCriteria criteriaWf = DetachedCriteria.forClass( ProfessionistaEsternoWf.class )
				.addOrder(Order.desc("dataCreazione"));
		criteriaWf.add(Restrictions.isNotNull("dataChiusura"));
		criteriaWf.createAlias("professionistaEsterno", "professionistaEsterno");
		criteriaWf.add(Restrictions.eq("professionistaEsterno.id", idProfessionista));
		criteriaWf.createAlias("statoWf", "statoWf");
		criteriaWf.add(Restrictions.eq("statoWf.codGruppoLingua", CostantiDAO.STATO_WF_COMPLETATO));
		criteriaWf.add(Restrictions.eq("statoWf.lang", CostantiDAO.LINGUA_ITALIANA));
		
		List<ProfessionistaEsternoWf> lista = getHibernateTemplate().findByCriteria(criteriaWf);	
		if(lista.size() > 0)
			return lista.get(0);
		else
			return null;
	}
	
	/**
	 * Recupera l'ultima istanza terminata di workflow.
	 * @param idProfessionista identificativo del professionista
	 * @return oggetto ProfessionistaEsternoWf
	 */
	@Override
	public ProfessionistaEsternoWf leggiUltimoWorkflowRifiutato(long idProfessionista) throws Throwable{
		DetachedCriteria criteriaWf = DetachedCriteria.forClass( ProfessionistaEsternoWf.class )
				.addOrder(Order.desc("dataCreazione"));
		criteriaWf.add(Restrictions.isNotNull("dataChiusura"));
		criteriaWf.createAlias("professionistaEsterno", "professionistaEsterno");
		criteriaWf.add(Restrictions.eq("professionistaEsterno.id", idProfessionista));
		criteriaWf.createAlias("statoWf", "statoWf");
		criteriaWf.add(Restrictions.eq("statoWf.codGruppoLingua", CostantiDAO.STATO_WF_RIFIUTATO));
		criteriaWf.add(Restrictions.eq("statoWf.lang", CostantiDAO.LINGUA_ITALIANA));
		
		List<ProfessionistaEsternoWf> lista = getHibernateTemplate().findByCriteria(criteriaWf);	
		if(lista.size() > 0)
			return lista.get(0);
		else
			return null;
	}


}
