package eng.la.persistence;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.Autorizzazione;
import eng.la.util.CurrentSessionUtil;
import eng.la.util.SpringUtil;

/**
 * <h1>Classe DAO d'implemtazione delle operazioni su base dati, per entità
 * Autorizzazione</h1> La classe DAO espone le operazioni di lettura/scrittura
 * sulla base dati per l'entità Autorizzazione.
 * 
 * @author ACER
 */
@SuppressWarnings("unchecked")
@Component("autorizzazioneDAO")
public class AutorizzazioneDAOImpl extends HibernateDaoSupport implements AutorizzazioneDAO, CostantiDAO {

	@Autowired
	public AutorizzazioneDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}


	@Override
	public Autorizzazione leggiAutorizzazioneUtenteCorrente(long idEntita, String nomeClasse) throws Throwable {

		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");

		DetachedCriteria criteria = DetachedCriteria.forClass(Autorizzazione.class);

		Criterion assegnazione = null;

		criteria.createAlias("tipoAutorizzazione", "tipoAutorizzazione");
		criteria.createAlias("utente", "utente", DetachedCriteria.LEFT_JOIN);

		criteria.createAlias("gruppoUtente", "gruppoUtente", DetachedCriteria.LEFT_JOIN);

		criteria.addOrder(Order.desc("tipoAutorizzazione.codGruppoLingua"));

		if (currentSessionUtil.getCollMatricole() != null) {
			criteria.createAlias("utenteForResp", "utenteForResp", DetachedCriteria.LEFT_JOIN);
			assegnazione = Restrictions.disjunction()
					.add(Restrictions.eq("utente.useridUtil", currentSessionUtil.getUserId()))
					.add(Restrictions.in("gruppoUtente.codice", currentSessionUtil.getRolesCode()))
					.add(Restrictions.in("utenteForResp.matricolaUtil", currentSessionUtil.getCollMatricole()));
		} else {
			assegnazione = Restrictions.disjunction()
					.add(Restrictions.eq("utente.useridUtil", currentSessionUtil.getUserId()))
					.add(Restrictions.in("gruppoUtente.codice", currentSessionUtil.getRolesCode()));
		}

		criteria.add(assegnazione);
		criteria.add(Restrictions.eq("nomeClasse", nomeClasse));
		criteria.add(Restrictions.eq("idEntita", idEntita));
		criteria.add(Restrictions.isNull("dataCancellazione"));

		List<Autorizzazione> gruppoAutorizzazioni = getHibernateTemplate().findByCriteria(criteria);
		if (gruppoAutorizzazioni.size() > 0)
			return gruppoAutorizzazioni.get(0);
		else
			return null;
	}

	@Override
	public List<Autorizzazione> leggiAutorizzazioni(long idEntita, String nomeClasse) throws Throwable { 

		DetachedCriteria criteria = DetachedCriteria.forClass(Autorizzazione.class);
  
		criteria.createAlias("tipoAutorizzazione", "tipoAutorizzazione");
		criteria.createAlias("utente", "utente"); 

		criteria.addOrder(Order.asc("utente.nominativoUtil"));
   
		criteria.add(Restrictions.eq("nomeClasse", nomeClasse));
		criteria.add(Restrictions.eq("idEntita", idEntita));

		List<Autorizzazione> autorizzazioni = getHibernateTemplate().findByCriteria(criteria);
		  
		return autorizzazioni;
	}
	
	@Override
	public List<Autorizzazione> leggiAutorizzazioni2(long idEntita, String nomeClasse) throws Throwable { 
		DetachedCriteria criteria = DetachedCriteria.forClass(Autorizzazione.class);
		criteria.add(Restrictions.eq("nomeClasse", nomeClasse));
		criteria.add(Restrictions.eq("idEntita", idEntita));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		List<Autorizzazione> autorizzazioni = getHibernateTemplate().findByCriteria(criteria);
		return autorizzazioni;
	}
	
	@Override
	public void inserisci(Autorizzazione autorizzazione) throws Throwable {
		getHibernateTemplate().save(autorizzazione);
	}
	
	@Override
	public void cancellaAutorizzazioni(Long idEntita, String nomeClasse, String ownerUserId) throws Throwable { 
		DetachedCriteria criteria = DetachedCriteria.forClass(Autorizzazione.class);
		criteria.createAlias("utente", "utente");
		criteria.add(Restrictions.eq("idEntita", idEntita));
		criteria.add(Restrictions.eq("nomeClasse", nomeClasse));
		criteria.add(Restrictions.isNull("gruppoUtente"));
		criteria.add(Restrictions.isNull("utenteForResp"));
		criteria.add(Restrictions.ne("utente.useridUtil", ownerUserId));
		List<Autorizzazione> listaPermessi = getHibernateTemplate().findByCriteria(criteria);
		getHibernateTemplate().deleteAll(listaPermessi);
		
	}
	
	@Override
	public void cancellaAutorizzazione(Long idEntita, String nomeClasse, String userId) throws Throwable { 
		DetachedCriteria criteria = DetachedCriteria.forClass(Autorizzazione.class);
		criteria.createAlias("utente", "utente");
		criteria.add(Restrictions.eq("idEntita", idEntita));
		criteria.add(Restrictions.eq("nomeClasse", nomeClasse));
		criteria.add(Restrictions.isNull("gruppoUtente"));
		criteria.add(Restrictions.isNull("utenteForResp"));
		criteria.add(Restrictions.eq("utente.useridUtil", userId));
		List<Autorizzazione> listaPermessi = getHibernateTemplate().findByCriteria(criteria);
		getHibernateTemplate().deleteAll(listaPermessi);
		
	}
	
	/**
	 * Elimina le autorizzazioni legate ad un cambio owner (le dirette e quelle delegate ai responsabili).
	 * @param idEntita identificativo del fascicolo
	 * @param ownerUserId userId del vecchio owner
	 * @return void
	 */
	@Override
	public void cancellaAutorizzazioniPerCambioOwner(Long idFascicolo,String ownerUserId) throws Throwable { 
		DetachedCriteria criteria = DetachedCriteria.forClass(Autorizzazione.class);
		criteria.createAlias("utente", "utente", DetachedCriteria.LEFT_JOIN);
		criteria.createAlias("utenteForResp", "utenteForResp", DetachedCriteria.LEFT_JOIN);
		
		criteria.add(Restrictions.eq("idEntita", idFascicolo));
		criteria.add(Restrictions.eq("nomeClasse", CostantiDAO.FASCICOLO.toUpperCase()));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		
		Criterion assegnazione = Restrictions.disjunction()
				.add(Restrictions.eq("utente.useridUtil", ownerUserId))
				.add(Restrictions.eq("utenteForResp.matricolaUtil", ownerUserId));
		criteria.add(assegnazione);
		List<Autorizzazione> listaPermessi = getHibernateTemplate().findByCriteria(criteria);
		getHibernateTemplate().deleteAll(listaPermessi);
		
		
	}
	
	@Override
	public List<String> leggiAutorizzati(List<Long> idEntitas, String nomeClasse) throws Throwable { 
		DetachedCriteria criteria = DetachedCriteria.forClass(Autorizzazione.class);
		criteria.createAlias("utente", "utente", DetachedCriteria.LEFT_JOIN);
		
		criteria.add(Restrictions.eq("nomeClasse", nomeClasse));
		criteria.add(Restrictions.in("idEntita", idEntitas));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		
		criteria.createAlias("tipoAutorizzazione", "tipoAutorizzazione", DetachedCriteria.INNER_JOIN);
		criteria.add(Restrictions.eq("tipoAutorizzazione.codGruppoLingua",CostantiDAO.TIPO_AUTORIZZAZIONE_UTENTE_SCRITTURA));
		
		criteria.setProjection(Projections.distinct(Projections.property("utente.emailUtil")));
		List<String> autorizzazioni = getHibernateTemplate().findByCriteria(criteria);
		return autorizzazioni;
	}
}
