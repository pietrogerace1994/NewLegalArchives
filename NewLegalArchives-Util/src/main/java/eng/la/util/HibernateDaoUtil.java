package eng.la.util;
 
import org.hibernate.Criteria; 
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class HibernateDaoUtil {

	public static void aggiungiLogicaPermessi(DetachedCriteria criteria, String nomeClasse) {
		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		
		Criterion assegnazione = null;

		System.out.println("Verifico permessi come: " + currentSessionUtil.getUserId()); 
		criteria.createAlias("autorizzazioni", "autorizzazioni",  DetachedCriteria.INNER_JOIN);
		criteria.createAlias("autorizzazioni.utente", "utente", DetachedCriteria.LEFT_JOIN);
		criteria.createAlias("autorizzazioni.gruppoUtente", "gruppoUtente",  DetachedCriteria.LEFT_JOIN);
		criteria.add(Restrictions.isNull("autorizzazioni.dataCancellazione"));
		criteria.add(Restrictions.eq("autorizzazioni.nomeClasse", nomeClasse));
	 
		if(currentSessionUtil.getCollMatricole() != null){
			criteria.createAlias("autorizzazioni.utenteForResp", "utenteForResp", DetachedCriteria.LEFT_JOIN);
			assegnazione = Restrictions.disjunction()
					.add(Restrictions.eq("utente.useridUtil", currentSessionUtil.getUserId()))
					.add(Restrictions.in("gruppoUtente.codice", currentSessionUtil.getRolesCode()))
					.add(Restrictions.in("utenteForResp.matricolaUtil", currentSessionUtil.getCollMatricole()));
		}
		else{
			assegnazione = Restrictions.disjunction()
					.add(Restrictions.eq("utente.useridUtil", currentSessionUtil.getUserId()))
					.add(Restrictions.in("gruppoUtente.codice", currentSessionUtil.getRolesCode()));
		} 
		
	/*	criteria.add(Restrictions.or(
				Restrictions.and( Restrictions.eq("utente.useridUtil", currentSessionUtil.getUserId()),Restrictions.isNull("autorizzazioni.gruppoUtente") ),
				Restrictions.and( Restrictions.in("gruppoUtente.codice", currentSessionUtil.getRolesCode()),Restrictions.isNull("autorizzazioni.utente")))
		); 
*/
	 	criteria.add(assegnazione);
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
	}

	public static void aggiungiLogicaPermessi(DetachedCriteria criteria, String nomeClasse,
			String tipoPermesso) {
		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		
		Criterion assegnazione = null;

		System.out.println("Verifico permessi come: " + currentSessionUtil.getUserId()); 
		criteria.createAlias("autorizzazioni", "autorizzazioni",  DetachedCriteria.INNER_JOIN);
		criteria.createAlias("autorizzazioni.utente", "utente", DetachedCriteria.LEFT_JOIN);
		criteria.createAlias("autorizzazioni.gruppoUtente", "gruppoUtente",  DetachedCriteria.LEFT_JOIN);
		criteria.createAlias("autorizzazioni.tipoAutorizzazione", "tipoAutorizzazione" );
		criteria.add(Restrictions.isNull("autorizzazioni.dataCancellazione"));
		criteria.add(Restrictions.eq("autorizzazioni.nomeClasse", nomeClasse));
		criteria.add(Restrictions.ilike("tipoAutorizzazione.codGruppoLingua", tipoPermesso, MatchMode.ANYWHERE));
		 
		if(currentSessionUtil.getCollMatricole() != null){
			criteria.createAlias("autorizzazioni.utenteForResp", "utenteForResp", DetachedCriteria.LEFT_JOIN);
			assegnazione = Restrictions.disjunction()
					.add(Restrictions.eq("utente.useridUtil", currentSessionUtil.getUserId()))
					.add(Restrictions.in("gruppoUtente.codice", currentSessionUtil.getRolesCode()))
					.add(Restrictions.in("utenteForResp.matricolaUtil", currentSessionUtil.getCollMatricole()));
		}
		else{
			assegnazione = Restrictions.disjunction()
					.add(Restrictions.eq("utente.useridUtil", currentSessionUtil.getUserId()))
					.add(Restrictions.in("gruppoUtente.codice", currentSessionUtil.getRolesCode()));
		} 
		
	/*	criteria.add(Restrictions.or(
				Restrictions.and( Restrictions.eq("utente.useridUtil", currentSessionUtil.getUserId()),Restrictions.isNull("autorizzazioni.gruppoUtente") ),
				Restrictions.and( Restrictions.in("gruppoUtente.codice", currentSessionUtil.getRolesCode()),Restrictions.isNull("autorizzazioni.utente")))
		); 
*/
	 	criteria.add(assegnazione);
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	}
	
	public static void aggiungiLogicaPermessi2(DetachedCriteria criteria,String nomeClasse,String tipoPermesso,String matricolaOwner) {
		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		
		Criterion assegnazione = null;

		System.out.println("Verifico permessi come: " + currentSessionUtil.getUserId()); 
		criteria.createAlias("autorizzazioni", "autorizzazioni",  DetachedCriteria.INNER_JOIN);
		criteria.createAlias("autorizzazioni.utente", "utente", DetachedCriteria.LEFT_JOIN);
		criteria.createAlias("autorizzazioni.gruppoUtente", "gruppoUtente",  DetachedCriteria.LEFT_JOIN);
		criteria.createAlias("autorizzazioni.tipoAutorizzazione", "tipoAutorizzazione" );
		criteria.add(Restrictions.isNull("autorizzazioni.dataCancellazione"));
		criteria.add(Restrictions.eq("autorizzazioni.nomeClasse", nomeClasse));
		criteria.add(Restrictions.ilike("tipoAutorizzazione.codGruppoLingua", tipoPermesso, MatchMode.ANYWHERE));
		criteria.add(Restrictions.ilike("autorizzazioni.utente.matricolaUtil", matricolaOwner, MatchMode.ANYWHERE)); 
		
		if(currentSessionUtil.getCollMatricole() != null){
			criteria.createAlias("autorizzazioni.utenteForResp", "utenteForResp", DetachedCriteria.LEFT_JOIN);
			assegnazione = Restrictions.disjunction()
					.add(Restrictions.eq("utente.useridUtil", currentSessionUtil.getUserId()))
					.add(Restrictions.in("gruppoUtente.codice", currentSessionUtil.getRolesCode()))
					.add(Restrictions.in("utenteForResp.matricolaUtil", currentSessionUtil.getCollMatricole()));
		}
		else{
			assegnazione = Restrictions.disjunction()
					.add(Restrictions.eq("utente.useridUtil", currentSessionUtil.getUserId()))
					.add(Restrictions.in("gruppoUtente.codice", currentSessionUtil.getRolesCode()));
		} 
		
	/*	criteria.add(Restrictions.or(
				Restrictions.and( Restrictions.eq("utente.useridUtil", currentSessionUtil.getUserId()),Restrictions.isNull("autorizzazioni.gruppoUtente") ),
				Restrictions.and( Restrictions.in("gruppoUtente.codice", currentSessionUtil.getRolesCode()),Restrictions.isNull("autorizzazioni.utente")))
		); 
*/
	 	criteria.add(assegnazione);
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	}

}
