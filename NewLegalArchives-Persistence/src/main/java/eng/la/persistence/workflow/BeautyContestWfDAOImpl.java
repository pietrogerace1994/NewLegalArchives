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

import eng.la.model.BeautyContestWf;
import eng.la.persistence.CostantiDAO;

@SuppressWarnings("unchecked")
@Component("beautyContestWfDAO")
public class BeautyContestWfDAOImpl extends HibernateDaoSupport implements BeautyContestWfDAO, CostantiDAO {

	
	@Autowired
	public BeautyContestWfDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	/**
	 * Avvia il workflow.
	 *
	 * @param vo: oggetto BeautyContestWf da salvare
	 * @return oggetto BeautyContestWf salvato
	 */
	@Override
	public BeautyContestWf avviaWorkflow(BeautyContestWf vo) {
		getHibernateTemplate().save(vo);
		return vo;
	}
	
	/**
	 * Recupera l'istanza di workflow.
	 * @param id identificativo del workflow
	 * @return oggetto BeautyContestWf
	 */
	public BeautyContestWf leggiWorkflow(long id) throws Throwable{
		DetachedCriteria criteria = DetachedCriteria.forClass( BeautyContestWf.class ).add( Restrictions.eq("id", id) );
		BeautyContestWf BeautyContestWf = (BeautyContestWf) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return BeautyContestWf;
	}
	
	/**
	 * Aggiorna il workflow modificando solo i dati aggiornabili.
	 * @param vo: il workflow
	 *            
	 */
	@Override
	public void modifica(BeautyContestWf vo) throws Throwable {
		getHibernateTemplate().update(vo); 
	}
	
	/**
	 * Recupera l'istanza attiva di workflow.
	 * @param idBeautyContest identificativo del bc
	 * @return oggetto BeautyContestWf
	 */
	@Override
	public BeautyContestWf leggiWorkflowInCorso(long idBeautyContest) throws Throwable{
		DetachedCriteria criteriaWf = DetachedCriteria.forClass( BeautyContestWf.class )
				.addOrder(Order.desc("dataCreazione"));
		criteriaWf.add(Restrictions.isNull("dataChiusura"));
		criteriaWf.createAlias("beautyContest", "beautyContest");
		criteriaWf.add(Restrictions.eq("beautyContest.id", idBeautyContest));
		criteriaWf.createAlias("statoWf", "statoWf");
		criteriaWf.add(Restrictions.eq("statoWf.codGruppoLingua", CostantiDAO.STATO_WF_IN_CORSO));
		criteriaWf.add(Restrictions.eq("statoWf.lang", CostantiDAO.LINGUA_ITALIANA));
		
		List<BeautyContestWf> lista = getHibernateTemplate().findByCriteria(criteriaWf);	
		if(lista.size() > 0)
			return lista.get(0);
		else
			return null;
	}
	
	/**
	 * Recupera l'ultima istanza terminata di workflow.
	 * @param idBeautyContest identificativo del bc
	 * @return oggetto BeautyContestWf
	 */
	@Override
	public BeautyContestWf leggiUltimoWorkflowTerminato(long idBeautyContest) throws Throwable{
		DetachedCriteria criteriaWf = DetachedCriteria.forClass( BeautyContestWf.class )
				.addOrder(Order.desc("dataCreazione"));
		criteriaWf.add(Restrictions.isNotNull("dataChiusura"));
		criteriaWf.createAlias("beautyContest", "beautyContest");
		criteriaWf.add(Restrictions.eq("beautyContest.id", idBeautyContest));
		criteriaWf.createAlias("statoWf", "statoWf");
		criteriaWf.add(Restrictions.eq("statoWf.codGruppoLingua", CostantiDAO.STATO_WF_COMPLETATO));
		criteriaWf.add(Restrictions.eq("statoWf.lang", CostantiDAO.LINGUA_ITALIANA));
		
		List<BeautyContestWf> lista = getHibernateTemplate().findByCriteria(criteriaWf);	
		if(lista.size() > 0)
			return lista.get(0);
		else
			return null;
	}
	
	/**
	 * Recupera l'ultima istanza rifiutata di workflow.
	 * @param idBeautyContest identificativo del bc
	 * @return oggetto BeautyContestWf
	 */
	@Override
	public BeautyContestWf leggiUltimoWorkflowRifiutato(long idBeautyContest) throws Throwable{
		DetachedCriteria criteriaWf = DetachedCriteria.forClass( BeautyContestWf.class )
				.addOrder(Order.desc("dataCreazione"));
		criteriaWf.add(Restrictions.isNotNull("dataChiusura"));
		criteriaWf.createAlias("beautyContest", "beautyContest");
		criteriaWf.add(Restrictions.eq("beautyContest.id", idBeautyContest));
		criteriaWf.createAlias("statoWf", "statoWf");
		criteriaWf.add(Restrictions.eq("statoWf.codGruppoLingua", CostantiDAO.STATO_WF_RIFIUTATO));
		criteriaWf.add(Restrictions.eq("statoWf.lang", CostantiDAO.LINGUA_ITALIANA));
		
		List<BeautyContestWf> lista = getHibernateTemplate().findByCriteria(criteriaWf);	
		if(lista.size() > 0)
			return lista.get(0);
		else
			return null;
	}
	@Override
	public BeautyContestWf leggiWorkflowdaBeautyContest(long idBeautyContest) {
		DetachedCriteria criteriaWf = DetachedCriteria.forClass( BeautyContestWf.class )
				.addOrder(Order.desc("dataCreazione"));
		criteriaWf.createAlias("beautyContest", "beautyContest");
		criteriaWf.add(Restrictions.eq("beautyContest.id", idBeautyContest));
		
		List<BeautyContestWf> lista = getHibernateTemplate().findByCriteria(criteriaWf);	
		if(lista.size() > 0)
			return lista.get(0);
		else
			return null;
	}


}
