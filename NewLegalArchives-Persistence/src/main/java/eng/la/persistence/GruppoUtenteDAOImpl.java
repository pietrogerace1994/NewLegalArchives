package eng.la.persistence;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.GruppoUtente;
import eng.la.model.RUtenteGruppo;

/**
 * <h1>Classe DAO d'implemtazione delle operazioni su base dati, 
 * per entità GruppoUtente</h1>
 * La classe DAO espone le operazioni di lettura/scrittura sulla base dati per
 * l'entità GruppoUtente.
 * 
 * @author ACER
 */
@SuppressWarnings("unchecked")
@Component("gruppoUtenteDAO")
public class GruppoUtenteDAOImpl extends HibernateDaoSupport implements GruppoUtenteDAO, CostantiDAO{
	@Autowired
	public GruppoUtenteDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	/**
	 * Metodo di lettura dei gruppi dell'utente dalla matricola.
	 * <p>
	 * @param matricola: matricola dell'utente
	 * @return ritorna la lista di oggetti model popolati con i dati inseriti.
	 * @exception Throwable
	 */
	@Override
	public List<GruppoUtente> leggiGruppiUtente(String matricola) throws Throwable {

		List<GruppoUtente> gruppoList = new ArrayList<GruppoUtente>(0);
		
		DetachedCriteria criteria = DetachedCriteria.forClass(RUtenteGruppo.class);
		
		criteria.createAlias("utente", "utente");
		criteria.add(Restrictions.eq("utente.matricolaUtil", matricola));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		List<RUtenteGruppo> listaRelazione =(List<RUtenteGruppo>) getHibernateTemplate().findByCriteria(criteria);	
		
		 if(listaRelazione.size() > 0){
			 for (RUtenteGruppo relazione:listaRelazione){
				 gruppoList.add(relazione.getGruppoUtente());
				}	
			 return gruppoList;
		 }
	    else
	    	return null;
	}

	@Override
	public GruppoUtente getGruppoUtente(String codice) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass(GruppoUtente.class);
		criteria.add(Restrictions.eq("codice", codice));
		criteria.add(Restrictions.eq("lang", "IT"));
		GruppoUtente gruppoUtente = (GruppoUtente) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));

		return gruppoUtente;
	}
	

}
