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

import eng.la.model.FascicoloWf;
import eng.la.persistence.CostantiDAO;

@SuppressWarnings("unchecked")
@Component("fascicoloWfDAO")
public class FascicoloWfDAOImpl extends HibernateDaoSupport implements FascicoloWfDAO, CostantiDAO {

	
	@Autowired
	public FascicoloWfDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	/**
	 * Avvia il workflow.
	 *
	 * @param vo: oggetto FascicoloWf da salvare
	 * @return oggetto FascicoloWf salvato
	 */
	@Override
	public FascicoloWf avviaWorkflow(FascicoloWf vo) {
		getHibernateTemplate().save(vo);
		return vo;
	}

	/**
	 * Recupera l'istanza di workflow.
	 * @param id identificativo del workflow
	 * @return oggetto FascicoloWf
	 */
	public FascicoloWf leggiWorkflow(long id) throws Throwable{
		DetachedCriteria criteria = DetachedCriteria.forClass( FascicoloWf.class ).add( Restrictions.eq("id", id) );
		FascicoloWf fascicoloWf = (FascicoloWf) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
				
		return fascicoloWf;

	}
	
	/**
	 * Aggiorna il workflow modificando solo i dati aggiornabili.
	 * @param vo: il workflow
	 *            
	 */
	@Override
	public void modifica(FascicoloWf vo) throws Throwable {
		getHibernateTemplate().update(vo); 
		getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
	}
	
	/**
	 * Recupera l'istanza attiva di workflow.
	 * @param idFascicolo identificativo del fascicolo
	 * @return oggetto FascicoloWf
	 */
	@Override
	public FascicoloWf leggiWorkflowInCorso(long idFascicolo) throws Throwable{
		DetachedCriteria criteriaWf = DetachedCriteria.forClass(FascicoloWf.class )
				.addOrder(Order.desc("dataCreazione"));
		criteriaWf.add(Restrictions.isNull("dataChiusura"));
		criteriaWf.createAlias("fascicolo", "fascicolo");
		criteriaWf.add(Restrictions.eq("fascicolo.id", idFascicolo));
		criteriaWf.createAlias("statoWf", "statoWf");
		criteriaWf.add(Restrictions.eq("statoWf.codGruppoLingua", CostantiDAO.STATO_WF_IN_CORSO));
		criteriaWf.add(Restrictions.eq("statoWf.lang", CostantiDAO.LINGUA_ITALIANA));
		
		List<FascicoloWf> lista = getHibernateTemplate().findByCriteria(criteriaWf);	

		if(lista.size() > 0)
			return lista.get(0);
		else
			return null;

	}
	
	/**
	 * Recupera l'ultima istanza terminata di workflow.
	 * @param idFascicolo identificativo del fascicolo
	 * @return oggetto FascicoloWf
	 */
	@Override
	public FascicoloWf leggiUltimoWorkflowTerminato(long idFascicolo) throws Throwable{
		DetachedCriteria criteriaWf = DetachedCriteria.forClass( FascicoloWf.class )
				.addOrder(Order.desc("dataCreazione"));
		criteriaWf.add(Restrictions.isNotNull("dataChiusura"));
		criteriaWf.createAlias("fascicolo", "fascicolo");
		criteriaWf.add(Restrictions.eq("fascicolo.id", idFascicolo));
		criteriaWf.createAlias("statoWf", "statoWf");
		criteriaWf.add(Restrictions.eq("statoWf.codGruppoLingua", CostantiDAO.STATO_WF_COMPLETATO));
		criteriaWf.add(Restrictions.eq("statoWf.lang", CostantiDAO.LINGUA_ITALIANA));
		
		List<FascicoloWf> lista = getHibernateTemplate().findByCriteria(criteriaWf);	
		if(lista.size() > 0)
			return lista.get(0);
		else
			return null;
	}

	/**
	 * Recupera l'ultima istanza terminata di workflow.
	 * @param idFascicolo identificativo del fascicolo
	 * @return oggetto FascicoloWf
	 */
	@Override
	public FascicoloWf leggiUltimoWorkflowRifiutato(long idFascicolo) throws Throwable{
		DetachedCriteria criteriaWf = DetachedCriteria.forClass( FascicoloWf.class )
				.addOrder(Order.desc("dataCreazione"));
		criteriaWf.add(Restrictions.isNotNull("dataChiusura"));
		criteriaWf.createAlias("fascicolo", "fascicolo");
		criteriaWf.add(Restrictions.eq("fascicolo.id", idFascicolo));
		criteriaWf.createAlias("statoWf", "statoWf");
		criteriaWf.add(Restrictions.eq("statoWf.codGruppoLingua", CostantiDAO.STATO_WF_RIFIUTATO));
		criteriaWf.add(Restrictions.eq("statoWf.lang", CostantiDAO.LINGUA_ITALIANA));
		
		List<FascicoloWf> lista = getHibernateTemplate().findByCriteria(criteriaWf);	
		if(lista.size() > 0)
			return lista.get(0);
		else
			return null;
	}



}
