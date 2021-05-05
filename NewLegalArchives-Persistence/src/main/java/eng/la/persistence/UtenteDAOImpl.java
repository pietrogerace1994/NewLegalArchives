package eng.la.persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.AttoWf;
import eng.la.model.Configurazione;
import eng.la.model.GruppoUtente;
import eng.la.model.RUtenteFascicolo;
import eng.la.model.RUtenteGruppo;
import eng.la.model.StepWf;
import eng.la.model.Utente;

/**
 * <h1>Classe DAO d'implemtazione delle operazioni su base dati, 
 * per entit� Utentef</h1>
 * La classe DAO espone le operazioni di lettura/scrittura sulla base dati per
 * l'entit� Utente.
 * 
 * @author ACER
 */
@SuppressWarnings("unchecked")
@Component("utenteDAO")
public class UtenteDAOImpl extends HibernateDaoSupport implements UtenteDAO, CostantiDAO{

	@Autowired
	public UtenteDAOImpl(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	/**
	 * Metodo di lettura dell'utente dalla matricola.
	 * <p>
	 * @param matricola: matricola dell'utente
	 * @return ritorna l'oggetto model popolato con i dati inseriti.
	 * @exception Throwable
	 */
	@Override
	public Utente leggiUtenteDaMatricola(String matricola) throws Throwable {
	
		DetachedCriteria criteria = DetachedCriteria.forClass( Utente.class ).add( Restrictions.eq("matricolaUtil", matricola) );
		Utente utente = (Utente) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
				
		return utente;
	}
	
	/**
	 * Metodo di lettura dell'utente dallo userId.
	 * <p>
	 * @param userId: userId dell'utente
	 * @return ritorna l'oggetto model popolato con i dati inseriti.
	 * @exception Throwable
	 */
	@Override
	public Utente leggiUtenteDaUserId(String userId) throws Throwable {

		DetachedCriteria criteria = DetachedCriteria.forClass( Utente.class ).add( Restrictions.eq("useridUtil", userId) );
		Utente utente = (Utente) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
		return utente;
	}

	/**
	 * Metodo di lettura del responsabile top della gerarchia.
	 * <p>
	 * @return ritorna l'oggetto model popolato con i dati inseriti.
	 * @exception Throwable
	 */
	@Override
	public Utente leggiResponsabileTop() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Configurazione.class ).add( Restrictions.eq("cdKey", CostantiDAO.KEY_TOP_RESPONSABILE) );
		
		Configurazione configurazione = (Configurazione) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );	

		DetachedCriteria criteriaUtente = DetachedCriteria.forClass( Utente.class ).add( Restrictions.eq("matricolaUtil", configurazione.getCdValue()) );
		
		Utente topResponsabile = (Utente) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteriaUtente) );	
		return topResponsabile;
	}

	
	/**
	 * Metodo di lettura della catena di responsabili dell'utente in input.
	 * <p>
	 * @param matricola: matricola dell'utente
	 * @return ritorna la lista degli oggetti model recuperati.
	 * @exception Throwable
	 */
	@Override
	public List<Utente> leggiResponsabili(String matricola) throws Throwable {
		List<Utente> responsabiliList = new ArrayList<Utente>(0);
		StringBuffer stringaSql = new StringBuffer();
	    stringaSql.append("select matricola_resp_util_level1, matricola_resp_util_level2, ");
	    stringaSql.append("matricola_resp_util_level3, matricola_resp_util_level4, ");
	    stringaSql.append("matricola_resp_util_level5, matricola_resp_util_level6 ");
	    stringaSql.append("FROM vw_responsabile ");
	    stringaSql.append("WHERE matricola_util = '");
	    stringaSql.append(matricola);
	    stringaSql.append("' ORDER BY nominativo_util");
	    responsabiliList = getHibernateTemplate().execute(new HibernateCallback<List<Utente>>() {
			@Override
			public List<Utente> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery sqlQuery = session.createSQLQuery(stringaSql.toString());
				List<Object> queryResult = sqlQuery.list();
				List<Utente> respList = new ArrayList<Utente>();
				Object row = queryResult.get(0);
				Object[] fields = (Object[])row;
				for (int level = 0; level <= 5; level ++){
					if(fields[level] != null && !((String)fields[level]).isEmpty()){
						DetachedCriteria criteria = DetachedCriteria.forClass( Utente.class ).add( Restrictions.eq("matricolaUtil", (String)fields[level]) );
						Utente responsabile = (Utente) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
						respList.add((Utente) responsabile);
					}		
				
				}
				return respList;
			}
		});
	    if(responsabiliList.size() > 0)
	    	return responsabiliList;
	    else
	    	return null;
	}

	/**
	 * Metodo di lettura degli utenti di cui l'utente in input risulti responsabile di qualsiasi livello.
	 * <p>
	 * @param matricola: matricola dell'utente
	 * @return ritorna la lista degli oggetti model recuperati.
	 * @exception Throwable
	 */
	@Override
	public List<Utente> leggiCollaboratori(String matricola) throws Throwable {
		List<Utente> collaboratoribiliList = new ArrayList<Utente>(0);
		StringBuffer stringaSql = new StringBuffer();
	    stringaSql.append("SELECT matricola_util from VW_RESPONSABILE where ");
	    stringaSql.append("matricola_resp_util_level1 = '");
	    stringaSql.append(matricola);
	    stringaSql.append("' OR matricola_resp_util_level2 = '");
	    stringaSql.append(matricola);
	    stringaSql.append("' OR matricola_resp_util_level3 = '");
	    stringaSql.append(matricola);
	    stringaSql.append("' OR matricola_resp_util_level4 = '");
	    stringaSql.append(matricola);
	    stringaSql.append("' OR matricola_resp_util_level5 = '");
	    stringaSql.append(matricola);
	    stringaSql.append("' OR matricola_resp_util_level6 = '");
	    stringaSql.append(matricola);
	    stringaSql.append("'");
	    stringaSql.append(" ORDER BY nominativo_util");
	    
	    collaboratoribiliList = getHibernateTemplate().execute(new HibernateCallback<List<Utente>>() {
			@Override
			public List<Utente> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery sqlQuery = session.createSQLQuery(stringaSql.toString());
				List<String> queryResult = sqlQuery.list();
				List<Utente> collList = new ArrayList<Utente>();
				for (String currentMatricola:queryResult){
					DetachedCriteria criteria = DetachedCriteria.forClass( Utente.class ).add( Restrictions.eq("matricolaUtil", currentMatricola) );
					Utente collaboratore = (Utente) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
					collList.add((Utente) collaboratore);
				}	
				
				return collList;
			}
		});
	    if(collaboratoribiliList.size() > 0)
	    	return collaboratoribiliList;
	    else
	    	return null;
	}

	/**
	 * Metodo di lettura degli utenti di cui l'utente in input risulti responsabile diretto (esclusi gli operatori di segreteria).
	 * <p>
	 * @param matricola: matricola dell'utente
	 * @return ritorna la lista degli oggetti model recuperati.
	 * @exception Throwable
	 */
	@Override
	public List<Utente> leggiCollaboratoriDiretti(String matricola) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Utente.class )
				.addOrder(Order.asc("nominativoUtil"));
		criteria.add(Restrictions.eq("matricolaRespUtil", matricola));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		List<Utente> lista = getHibernateTemplate().findByCriteria(criteria);
		List<Utente> listaFiltrata = new ArrayList<Utente>();
		for (Utente utente:lista){
			if(!leggiSeOperatoreSegreteria(utente))
					listaFiltrata.add(utente);
		}
		return listaFiltrata;
	}
	
	/**
	 * Metodo di lettura degli utenti di cui l'utente in input risulti responsabile diretto.
	 * <p>
	 * @param matricola: matricola dell'utente
	 * @return ritorna la lista degli oggetti model recuperati.
	 * @exception Throwable
	 */
	@Override
	public List<Utente> leggiCollaboratoriResponsabili(String matricola) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( Utente.class )
				.addOrder(Order.asc("nominativoUtil"));
		criteria.add(Restrictions.eq("matricolaRespUtil", matricola));
		criteria.add(Restrictions.eq("responsabileUtil", "Y"));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		List<Utente> lista = getHibernateTemplate().findByCriteria(criteria);	
		return lista;
	}
	
	/**
	 * Metodo di lettura dei legali internii di cui l'utente in input risulti responsabile diretto.
	 * <p>
	 * @param matricola: matricola dell'utente
	 * @return ritorna la lista degli oggetti model recuperati.
	 * @exception Throwable
	 */
	@Override
	public List<Utente> leggiCollaboratoriLegaliInterni(String matricola) throws Throwable {
		List<Utente> listLI = new ArrayList<Utente>();

		DetachedCriteria criteria = DetachedCriteria.forClass( Utente.class );
		criteria.add(Restrictions.eq("matricolaRespUtil", matricola));
		List<Utente> utentiSottoposti = (List<Utente>) getHibernateTemplate().findByCriteria(criteria);

		if(utentiSottoposti != null && !utentiSottoposti.isEmpty()){

			for(Utente utente : utentiSottoposti){
				try {
					if(leggiSeLegaleInterno(utente)){
						listLI.add((Utente) utente);
					}
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
		return listLI;
	}

	/**
	 * Metodo di verifica se l'utente individuato dalla matricola in input sia un responsabile foglia.
	 * <p>
	 * @param matricola: matricola dell'utente
	 * @return true se responsabile foglia, false in caso contrario.
	 * @exception Throwable
	 */
	@Override
	public boolean leggiSeResponsabileFoglia(String matricola) throws Throwable {
		Utente utente = leggiUtenteDaMatricola(matricola);
		if(utente.getResponsabileUtil() == null || utente.getResponsabileUtil().equalsIgnoreCase(Character.toString(CostantiDAO.NO_CHAR)))
			return false;
		else{
			List<Utente> responsabili = leggiCollaboratoriResponsabili(matricola);
			if(responsabili.size() > 0)
				return false;
			else
				return true;
		}
		
	}
	
	/**
	 * Metodo di verifica se l'utente individuato dalla matricola in input sia un General Counsel.
	 * <p>
	 * @param matricola: matricola dell'utente
	 * @return true se General Counsel, false in caso contrario.
	 * @exception Throwable
	 */
	@Override
	public boolean leggiSeGeneralCounsel(String matricola) throws Throwable {
		Utente utente = leggiUtenteDaMatricola(matricola);
		if(utente.getResponsabileUtil() == null || utente.getResponsabileUtil().equalsIgnoreCase(Character.toString(CostantiDAO.NO_CHAR)))
			return false;
		else{
			Utente responsabile = leggiResponsabileTop();
			if(utente.getMatricolaRespUtil().equalsIgnoreCase(responsabile.getMatricolaUtil()))
				return true;
			else
				return false;
		}
		
	}
	
	/**
	 * Metodo di verifica se l'utente individuato dalla matricola in input sia un responsabile privo di collaboratori.
	 * <p>
	 * @param matricola: matricola dell'utente
	 * @return true se responsabile foglia, false in caso contrario.
	 * @exception Throwable
	 */
	@Override
	public boolean leggiSeResponsabileSenzaCollaboratori(Utente utente) throws Throwable {
		if(utente.getResponsabileUtil() == null || utente.getResponsabileUtil().equalsIgnoreCase(Character.toString(CostantiDAO.NO_CHAR)))
			return false;
		else{
			List<Utente> responsabili = leggiCollaboratoriDiretti(utente.getMatricolaUtil());
			if(responsabili.size() > 0)
				return false;
			else
				return true;
		}
		
	}
	
	/**
	 * Metodo di verifica se l'utente individuato dalla matricola in input sia un amministratore.
	 * <p>
	 * @param matricola: matricola dell'utente
	 * @return true se responsabile foglia, false in caso contrario.
	 * @exception Throwable
	 */
	@Override
	public boolean leggiSeAmministratore(Utente utente) throws Throwable {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(GruppoUtente.class);
		criteria.add(Restrictions.eq("codice", CostantiDAO.GRUPPO_AMMINISTRATORE));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		GruppoUtente gruppo = (GruppoUtente) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		List<String> matricoleList = new ArrayList<String>(0);
		StringBuffer stringaSql = new StringBuffer();
	    stringaSql.append("Select matricola_utente");
	    stringaSql.append(" FROM r_utente_gruppo");
	    stringaSql.append(" WHERE matricola_utente = '");
	    stringaSql.append(utente.getMatricolaUtil());
	    stringaSql.append("'");
	    stringaSql.append(" AND ID_GRUPPO_UTENTE = ");
	    stringaSql.append(gruppo.getId());
	    matricoleList = getHibernateTemplate().execute(new HibernateCallback<List<String>>() {
			@Override
			public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery sqlQuery = session.createSQLQuery(stringaSql.toString());
				List<String> queryResult = sqlQuery.list();
				List<String> collList = new ArrayList<String>();
				for (String currentMatricola:queryResult){
					collList.add(currentMatricola);
				}	
				
				return collList;
			}
		});
	    if(matricoleList.size() > 0)
	    	return true;
	    else
	    	return false;
	}
	
	
	
	@Override
	public boolean isAmministrativo(Utente utente) throws Throwable {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(GruppoUtente.class);
		criteria.add(Restrictions.eq("codice", CostantiDAO.GRUPPO_AMMINISTRATIVO));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		GruppoUtente gruppo = (GruppoUtente) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		List<String> matricoleList = new ArrayList<String>(0);
		StringBuffer stringaSql = new StringBuffer();
	    stringaSql.append("Select matricola_utente");
	    stringaSql.append(" FROM r_utente_gruppo");
	    stringaSql.append(" WHERE matricola_utente = '");
	    stringaSql.append(utente.getMatricolaUtil());
	    stringaSql.append("'");
	    stringaSql.append(" AND ID_GRUPPO_UTENTE = ");
	    stringaSql.append(gruppo.getId());
	    matricoleList = getHibernateTemplate().execute(new HibernateCallback<List<String>>() {
			@Override
			public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery sqlQuery = session.createSQLQuery(stringaSql.toString());
				List<String> queryResult = sqlQuery.list();
				List<String> collList = new ArrayList<String>();
				for (String currentMatricola:queryResult){
					collList.add(currentMatricola);
				}	
				
				return collList;
			}
		});
	    if(matricoleList.size() > 0)
	    	return true;
	    else
	    	return false;
	}
	
	@Override
	public boolean isGestorePresidioNormativo(Utente utente) throws Throwable {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(GruppoUtente.class);
		criteria.add(Restrictions.eq("codice", CostantiDAO.GESTORE_ARCHIVIO_PRESIDIO_NORMATIVO));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		GruppoUtente gruppo = (GruppoUtente) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		List<String> matricoleList = new ArrayList<String>(0);
		StringBuffer stringaSql = new StringBuffer();
	    stringaSql.append("Select matricola_utente");
	    stringaSql.append(" FROM r_utente_gruppo");
	    stringaSql.append(" WHERE matricola_utente = '");
	    stringaSql.append(utente.getMatricolaUtil());
	    stringaSql.append("'");
	    stringaSql.append(" AND ID_GRUPPO_UTENTE = ");
	    stringaSql.append(gruppo.getId());
	    matricoleList = getHibernateTemplate().execute(new HibernateCallback<List<String>>() {
			@Override
			public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery sqlQuery = session.createSQLQuery(stringaSql.toString());
				List<String> queryResult = sqlQuery.list();
				List<String> collList = new ArrayList<String>();
				for (String currentMatricola:queryResult){
					collList.add(currentMatricola);
				}	
				
				return collList;
			}
		});
	    if(matricoleList.size() > 0)
	    	return true;
	    else
	    	return false;
	}
	
	@Override
	public List<Utente> leggiUtenti() throws Throwable { 
		DetachedCriteria criteria = DetachedCriteria.forClass( Utente.class );
		criteria.setFetchMode("RUtenteGruppos", FetchMode.JOIN);
		criteria.addOrder(Order.asc("nominativoUtil"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@Override
	public List<Utente> leggiUtenti(boolean tutti) throws Throwable { 
		DetachedCriteria criteria = DetachedCriteria.forClass( Utente.class );
		if(tutti==false)
			criteria.add(Restrictions.isNull("dataCancellazione"));
		criteria.setFetchMode("RUtenteGruppos", FetchMode.JOIN);
		criteria.addOrder(Order.asc("nominativoUtil"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	/**
	 * Metodo di verifica se l'utente individuato dalla matricola in input sia un legale interno.
	 * <p>
	 * @param matricola: matricola dell'utente
	 * @return true se responsabile foglia, false in caso contrario.
	 * @exception Throwable
	 */
	@Override
	public boolean leggiSeLegaleInterno(Utente utente) throws Throwable {
		
		//se � responsabile non � legale interno
		if(utente.getResponsabileUtil() != null && utente.getResponsabileUtil().equalsIgnoreCase(Character.toString(CostantiDAO.YES_CHAR)))
			return false;
		
		else{
			DetachedCriteria criteria = DetachedCriteria.forClass(GruppoUtente.class);
			criteria.add(Restrictions.eq("codice", CostantiDAO.GESTORE_FASCICOLI));
			criteria.add(Restrictions.isNull("dataCancellazione"));
			GruppoUtente gruppo = (GruppoUtente) DataAccessUtils
					.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
			List<String> matricoleList = new ArrayList<String>(0);
			StringBuffer stringaSql = new StringBuffer();
		    stringaSql.append("Select matricola_utente");
		    stringaSql.append(" FROM r_utente_gruppo");
		    stringaSql.append(" WHERE matricola_utente = '");
		    stringaSql.append(utente.getMatricolaUtil());
		    stringaSql.append("'");
		    stringaSql.append(" AND ID_GRUPPO_UTENTE = ");
		    stringaSql.append(gruppo.getId());
		    matricoleList = getHibernateTemplate().execute(new HibernateCallback<List<String>>() {
				@Override
				public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
					SQLQuery sqlQuery = session.createSQLQuery(stringaSql.toString());
					List<String> queryResult = sqlQuery.list();
					List<String> collList = new ArrayList<String>();
					for (String currentMatricola:queryResult){
						collList.add(currentMatricola);
					}	
					
					return collList;
				}
			});
		    if(matricoleList.size() > 0)
		    	return true;
		    else
		    	return false;
		}
	}
	
	/**
	 * Metodo di verifica se l'utente individuato dalla matricola in input sia un operatore di segreteria.
	 * <p>
	 * @param matricola: matricola dell'utente
	 * @return true se responsabile foglia, false in caso contrario.
	 * @exception Throwable
	 */
	@Override
	public boolean leggiSeOperatoreSegreteria(Utente utente) throws Throwable {
		
		//se � responsabile non � legale interno
		if(utente.getResponsabileUtil() != null && utente.getResponsabileUtil().equalsIgnoreCase(Character.toString(CostantiDAO.YES_CHAR)))
			return false;
		
		else{
			DetachedCriteria criteria = DetachedCriteria.forClass(GruppoUtente.class);
			criteria.add(Restrictions.eq("codice", CostantiDAO.OPERATORE_SEGRETERIA));
			criteria.add(Restrictions.isNull("dataCancellazione"));
			GruppoUtente gruppo = (GruppoUtente) DataAccessUtils
					.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
			List<String> matricoleList = new ArrayList<String>(0);
			StringBuffer stringaSql = new StringBuffer();
		    stringaSql.append("Select matricola_utente");
		    stringaSql.append(" FROM r_utente_gruppo");
		    stringaSql.append(" WHERE matricola_utente = '");
		    stringaSql.append(utente.getMatricolaUtil());
		    stringaSql.append("'");
		    stringaSql.append(" AND ID_GRUPPO_UTENTE = ");
		    stringaSql.append(gruppo.getId());
		    matricoleList = getHibernateTemplate().execute(new HibernateCallback<List<String>>() {
				@Override
				public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
					SQLQuery sqlQuery = session.createSQLQuery(stringaSql.toString());
					List<String> queryResult = sqlQuery.list();
					List<String> collList = new ArrayList<String>();
					for (String currentMatricola:queryResult){
						collList.add(currentMatricola);
					}	
					
					return collList;
				}
			});
		    if(matricoleList.size() > 0)
		    	return true;
		    else
		    	return false;
		}
		
	}
	
	/**
	 * Recupera l'assegnatario corrente dello step.
	 * @param idStep: identificativo dello step corrente
	 * @return oggetto model utente popolato con i dati inseriti.
	 * @exception Throwable
	 */
	
	//DARIO C ***********************************************************************
	@Override
	public Utente leggiAssegnatarioCorrenteStandard(long idStep)throws Throwable{
		return leggiAssegnatarioCorrenteStandard(idStep,"");
	}
	//*******************************************************************************	
	
	@Override
	public Utente leggiAssegnatarioCorrenteStandard(long idStep,String matricolaDestinatario)throws Throwable{
		//DARIO C ***** Aggiunto parametro matricolaDestinatario
		
		DetachedCriteria criteria = DetachedCriteria.forClass( StepWf.class ).add( Restrictions.eq("id", idStep) );
		StepWf step  = (StepWf) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );	
		
		if(step.getConfigurazioneStepWf().getTipoAssegnazione() != null){
			
			//se l'assegnazione � manuale
			if(step.getConfigurazioneStepWf().getTipoAssegnazione().equalsIgnoreCase(CostantiDAO.ASSEGNAZIONE_MANUALE))
				return step.getConfigurazioneStepWf().getUtente();
			
			//altrimenti o � di tipo responsabile, o di tipo primo responsabile
			else if(step.getConfigurazioneStepWf().getTipoAssegnazione().equalsIgnoreCase(CostantiDAO.ASSEGNAZIONE_RESPONSABILE) || 
					step.getConfigurazioneStepWf().getTipoAssegnazione().equalsIgnoreCase(CostantiDAO.ASSEGNAZIONE_PRIMO_RIPORTO)){
				
				DetachedCriteria criteriaUtente = DetachedCriteria.forClass( Utente.class );
				
				//DARIO C**************************************************************************************************************************
				//criteriaUtente.add(Restrictions.eq("matricolaUtil", step.getUtente().getMatricolaRespUtil()));
				matricolaDestinatario = matricolaDestinatario.trim().length()!=0 ? matricolaDestinatario : step.getUtente().getMatricolaRespUtil();
				criteriaUtente.add(Restrictions.eq("matricolaUtil", matricolaDestinatario));
				//*********************************************************************************************************************************
				
				Utente assegnatario  = (Utente) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteriaUtente) );	
				
				return assegnatario;
			}
		}
		return null;
	}
	
	@Override
	public GruppoUtente leggiGruppoAssegnatarioCorrenteStandard(long idStep)throws Throwable{
		DetachedCriteria criteria = DetachedCriteria.forClass( StepWf.class ).add( Restrictions.eq("id", idStep) );
		StepWf step  = (StepWf) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );	
		
		//se l'assegnazione � manuale
		if(step.getConfigurazioneStepWf().getTipoAssegnazione() != null){
			
			if(step.getConfigurazioneStepWf().getTipoAssegnazione().equalsIgnoreCase(CostantiDAO.ASSEGNAZIONE_SEGRETERIA))
				return step.getConfigurazioneStepWf().getGruppoUtente();
		}
		
		return null;
	}
	
	@Override
	public boolean isAssegnatarioManualeCorrenteStandard(long idStep)throws Throwable{
		DetachedCriteria criteria = DetachedCriteria.forClass( StepWf.class ).add( Restrictions.eq("id", idStep) );

		StepWf step  = (StepWf) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );	
		
		//se l'assegnazione � manuale
		if(step.getConfigurazioneStepWf().getTipoAssegnazione().equalsIgnoreCase(CostantiDAO.ASSEGNAZIONE_MANUALE))
			return true;
		
		//altrimenti o � di tipo responsabile, o di tipo primo responsabile
		else if(step.getConfigurazioneStepWf().getTipoAssegnazione().equalsIgnoreCase(CostantiDAO.ASSEGNAZIONE_RESPONSABILE) || 
				step.getConfigurazioneStepWf().getTipoAssegnazione().equalsIgnoreCase(CostantiDAO.ASSEGNAZIONE_PRIMO_RIPORTO) || 
				step.getConfigurazioneStepWf().getTipoAssegnazione().equalsIgnoreCase(CostantiDAO.ASSEGNAZIONE_OWNER)){
			return false;
		}
			//blocco il ciclo esterno;
		return true;
	}
	
	
	
	/**
	 * Recupera l'assegnatario corrente dello step.
	 * @param idWorkflow: identificativo del workflow corrente
	 * @return oggetto model utente popolato con i dati inseriti.
	 * @exception Throwable
	 */
	@Override
	public Utente leggiAssegnatarioCorrenteManualeAtto(long idWorkflow)throws Throwable{
		
		DetachedCriteria criteria = DetachedCriteria.forClass( AttoWf.class ).add( Restrictions.eq("id", idWorkflow) );

		AttoWf attoWf  = (AttoWf) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );	
		
		DetachedCriteria criteriaUtente = DetachedCriteria.forClass( Utente.class );
		criteriaUtente.add(Restrictions.eq("matricolaUtil", attoWf.getUtenteAssegnatario()));
		
		Utente assegnatario  = (Utente) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteriaUtente) );	
		
		return assegnatario;

	}
	
	/**
	 * Recupera l'assegnatario corrente dello step.
	 * @param idWorkflow: identificativo del workflow corrente
	 * @return oggetto model utente popolato con i dati inseriti.
	 * @exception Throwable
	 */
	@Override
	public Utente leggiAssegnatarioCorrenteOwnerAtto(long idStep)throws Throwable{
		
		DetachedCriteria criteria = DetachedCriteria.forClass( StepWf.class ).add( Restrictions.eq("id", idStep) );

		StepWf stepWf  = (StepWf) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );	
		
		DetachedCriteria criteriaUtente = DetachedCriteria.forClass( Utente.class );
		criteriaUtente.add(Restrictions.eq("useridUtil", stepWf.getUtente().getUseridUtil()));
		
		Utente assegnatario  = (Utente) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteriaUtente) );	
		
		return assegnatario;

	}
	
	/**
	 * Recupera l'assegnatario corrente dello step in caso di assegnazione a responsabile
	 * @param idWorkflow: identificativo del workflow corrente
	 * @return oggetto model utente popolato con i dati inseriti.
	 * @exception Throwable
	 */
	@Override
	public Utente leggiAssegnatarioCorrenteResponsabileAtto(long idWorkflow)throws Throwable{
		
		DetachedCriteria criteria = DetachedCriteria.forClass( AttoWf.class ).add( Restrictions.eq("id", idWorkflow) );

		AttoWf attoWf  = (AttoWf) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );	
		
		DetachedCriteria criteriaUtente = DetachedCriteria.forClass( Utente.class );
		criteriaUtente.add(Restrictions.eq("useridUtil", attoWf.getUtenteAssegnatario()));
		
		Utente assegnatario  = (Utente) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteriaUtente) );	
		
		return assegnatario;

	}
	
	
	public List<Utente> getUtentiGC() throws Throwable{
		
		//DARIO ************************************************************************
				
//		DetachedCriteria criteria = DetachedCriteria.forClass( Configurazione.class ).add( Restrictions.eq("cdKey", CostantiDAO.KEY_TOP_RESPONSABILE) );
//		
//		Configurazione configurazione = (Configurazione) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );	
//
//		DetachedCriteria criteriaUtenti = DetachedCriteria.forClass( Utente.class )
//				.addOrder(Order.asc("nominativoUtil"));
//		criteriaUtenti.add( Restrictions.eq("matricolaRespUtil", configurazione.getCdValue()) );
//		criteriaUtenti.add(Restrictions.eq("responsabileUtil", Character.toString(CostantiDAO.YES_CHAR)));
//		List<Utente> lista =(List<Utente>) getHibernateTemplate().findByCriteria(criteriaUtenti);	
//		return lista;
		
		return getAssegnatariResponsabiliHead();
		//********************************************************************************
	}
		/**
		 * Metodo di lettura degli utenti che hanno preso parte attiva ad un workflow.
		 * <p>
		 * @param idWorkflow identificatico del workflow
		 * @param classeWf la tipologia di workflow
		 * @return ritorna la lista degli oggetti model recuperati.
		 * @exception Throwable
		 */
		@Override
		public List<Utente> leggiAttoriWorkflow(long idWorkflow, String classeWf) throws Throwable {
						
			List<Utente> lista = null;
			
			DetachedCriteria criteriaUtenti = DetachedCriteria.forClass( StepWf.class );
			criteriaUtenti.createAlias("utente", "utente");
			
			switch (classeWf){
			case CostantiDAO.AUTORIZZAZIONE_COLLEGIO_ARBITRALE:
			case CostantiDAO.AUTORIZZAZIONE_INCARICO: 
				criteriaUtenti.createAlias("incaricoWf", "incaricoWf");
				criteriaUtenti.add(Restrictions.eq("incaricoWf.id", idWorkflow));
				break;
			case CostantiDAO.AUTORIZZAZIONE_INS_PROFESSIONISTA_ESTERNO_IN_ELENCO:
				criteriaUtenti.createAlias("professionistaEsternoWf", "professionistaEsternoWf");
				criteriaUtenti.add(Restrictions.eq("professionistaEsternoWf.id", idWorkflow));
				break;
			case CostantiDAO.AUTORIZZAZIONE_PROFORMA:
				criteriaUtenti.createAlias("proformaWf", "proformaWf");
				criteriaUtenti.add(Restrictions.eq("proformaWf.id", idWorkflow));
				break;
			case CostantiDAO.CHIUSURA_FASCICOLO:
				criteriaUtenti.createAlias("fascicoloWf", "fascicoloWf");
				criteriaUtenti.add(Restrictions.eq("fascicoloWf.id", idWorkflow));
				break;
			case CostantiDAO.REGISTRAZIONE_ATTO:
				criteriaUtenti.createAlias("attoWf", "attoWf");
				criteriaUtenti.add(Restrictions.eq("attoWf.id", idWorkflow));
				break;
			default:
				break;
			}
			ProjectionList projectionList = Projections.projectionList();
			ProjectionList projectionList2 = Projections.projectionList();
			projectionList2.add(Projections.distinct(projectionList.add(Projections.property("utente"), "utente")));
			criteriaUtenti.setProjection(projectionList2);
//			commento la riga in quanto non voglio la lista delle entit� StepWf, ma solo quella delle entit� Utente
//			criteriaUtenti.setResultTransformer(Transformers.aliasToBean(StepWf.class));  
			lista =(List<Utente>) getHibernateTemplate().findByCriteria(criteriaUtenti);
			return lista;
		
		}

		/**
		 * Metodo di lettura degli utenti con ruolo amministrativo.
		 * <p>
		 * @return ritorna la lista degli oggetti model recuperati.
		 * @exception Throwable
		 */
		@Override
		public List<Utente> leggiUtentiAmministrativi() throws Throwable {
			
			List<Utente> lista =  new ArrayList<Utente>();
			DetachedCriteria criteria = DetachedCriteria.forClass(RUtenteGruppo.class);
			
			criteria.createAlias("gruppoUtente", "gruppoUtente");
			criteria.add(Restrictions.eq("gruppoUtente.codice", CostantiDAO.GRUPPO_AMMINISTRATIVO));
			criteria.add(Restrictions.isNull("dataCancellazione"));
			List<RUtenteGruppo> listaRelazione =(List<RUtenteGruppo>) getHibernateTemplate().findByCriteria(criteria);	
			
			for (RUtenteGruppo relazione:listaRelazione){
				lista.add(relazione.getUtente());
			}	
			return lista;
		}
		
		/**
		 * Metodo di lettura degli utenti con ruolo di amministratore.
		 * <p>
		 * @return ritorna la lista degli oggetti model recuperati.
		 * @exception Throwable
		 */
		@Override
		public List<Utente> leggiUtentiAmministratori() throws Throwable {
			
			List<Utente> lista =  new ArrayList<Utente>();
			DetachedCriteria criteria = DetachedCriteria.forClass(RUtenteGruppo.class);
			
			criteria.createAlias("gruppoUtente", "gruppoUtente");
			criteria.add(Restrictions.eq("gruppoUtente.codice", CostantiDAO.GRUPPO_AMMINISTRATORE));
			criteria.add(Restrictions.isNull("dataCancellazione"));
			List<RUtenteGruppo> listaRelazione =(List<RUtenteGruppo>) getHibernateTemplate().findByCriteria(criteria);	
			
			for (RUtenteGruppo relazione:listaRelazione){
				lista.add(relazione.getUtente());
			}	
			return lista;
		}
		
		@Override
		public List<Utente> leggiUtentiGestoriPresidioNormativo() throws Throwable {
			
			List<Utente> lista =  new ArrayList<Utente>();
			DetachedCriteria criteria = DetachedCriteria.forClass(RUtenteGruppo.class);
			
			criteria.createAlias("gruppoUtente", "gruppoUtente");
			criteria.add(Restrictions.eq("gruppoUtente.codice", CostantiDAO.GESTORE_ARCHIVIO_PRESIDIO_NORMATIVO));
			criteria.add(Restrictions.isNull("dataCancellazione"));
			List<RUtenteGruppo> listaRelazione =(List<RUtenteGruppo>) getHibernateTemplate().findByCriteria(criteria);	
			
			for (RUtenteGruppo relazione:listaRelazione){
				lista.add(relazione.getUtente());
			}	
			return lista;
		}
		
		
		//riassegnazione > Legale Interno Owner(combo degli owner dei fascioli)
		@Override
		public List<Utente> getListaLegaleInternoOwnerFascicolo(long idFascicolo) throws Throwable {
			
			List<Utente> utenteList = new ArrayList<Utente>(0);
			StringBuffer stringaSql = new StringBuffer();
			stringaSql.append("select distinct r.matricola_util ");
			stringaSql.append("from R_UTENTE_FASCICOLO r ");
			stringaSql.append("where r.data_cancellazione is null");
			if(idFascicolo>0){
			stringaSql.append(" and r.id_fascicolo=");
			stringaSql.append(idFascicolo);
			}
			utenteList = getHibernateTemplate().execute(new HibernateCallback<List<Utente>>() {
				@Override
				public List<Utente> doInHibernate(Session session) throws HibernateException, SQLException {
					SQLQuery sqlQuery = session.createSQLQuery(stringaSql.toString());
					List<String> queryResult = sqlQuery.list();
					List<Utente> utenteList = new ArrayList<Utente>();					
					for (String matricolaUtil:queryResult){
						DetachedCriteria criteria = DetachedCriteria.forClass( Utente.class ).add( Restrictions.eq("matricolaUtil", matricolaUtil) );
						Utente utente = (Utente) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
						utenteList.add((Utente) utente);
					}		
					return utenteList;
				}
			});
		    if(utenteList.size() > 0)
		    	return utenteList;
		    else
		    	return null;
		}

		

		//riassegnazione > prende tutti gli utenti(al di fuori degli amministrativi e amministratori)
		@Override
		public List<Utente> getListaUtentiNotAmmistrativiNotAmministratore() throws Throwable {
			
			List<Utente> utenteList = new ArrayList<Utente>(0);
			StringBuffer stringaSql = new StringBuffer();
			stringaSql.append("select distinct rg.matricola_utente ");
			stringaSql.append("from r_utente_gruppo rg  ");
			stringaSql.append("where rg.id_gruppo_utente not in(select gu.id from gruppo_utente gu where gu.codice in('LEG_ARC_AMMINISTRATORE','LEG_ARC_AMMINISTRATIVO')) ");
			stringaSql.append("and rg.data_cancellazione is null");
			
			utenteList = getHibernateTemplate().execute(new HibernateCallback<List<Utente>>() {
				@Override
				public List<Utente> doInHibernate(Session session) throws HibernateException, SQLException {
					SQLQuery sqlQuery = session.createSQLQuery(stringaSql.toString());
					List<String> queryResult = sqlQuery.list();
					List<Utente> utenteList = new ArrayList<Utente>();					
					for (String matricolaUtil:queryResult){
						DetachedCriteria criteria = DetachedCriteria.forClass( Utente.class ).add( Restrictions.eq("matricolaUtil", matricolaUtil) );
						Utente utente = (Utente) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
						utenteList.add((Utente) utente);
					}		
					return utenteList;
				}
			});
		    if(utenteList.size() > 0)
		    	return utenteList;
		    else
		    	return null;
		}

		//DARIO************************************************************************************************
		
		@Override
		public Utente leggiTopHead() throws Throwable {

			
			List<Utente> utenteList = new ArrayList<Utente>(0);

			String stringaSql = "select u.matricola_util from gruppo_utente a, r_utente_gruppo b, utente u "+
			"where a.id=b.id_gruppo_utente and b.matricola_utente=u.matricola_util "+ 
			"and a.codice='LEG_ARC_GESTOREFASCICOLI' and a.data_cancellazione is null "+ 
			"and u.data_cancellazione is null "+
			"and not (u.responsabile_util is null or u.responsabile_util = 'N') "+
			"and u.matricola_resp_util=(select cd_value from configurazione where cd_key='TOP_RESPONSABILE') "+
			"and u.matricola_util <> u.matricola_resp_util "+
			"and u.matricola_util=(select cd_value from configurazione where cd_key='TOP_HEAD')";

			
			utenteList = getHibernateTemplate().execute(new HibernateCallback<List<Utente>>() {
				@Override
				public List<Utente> doInHibernate(Session session) throws HibernateException, SQLException {
					SQLQuery sqlQuery = session.createSQLQuery(stringaSql);
					List<String> queryResult = sqlQuery.list();
					List<Utente> utenteList = new ArrayList<Utente>();					
					for (String matricolaUtil:queryResult){
						DetachedCriteria criteria = DetachedCriteria.forClass( Utente.class ).add( Restrictions.eq("matricolaUtil", matricolaUtil) );
						Utente utente = (Utente) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
						utenteList.add((Utente) utente);
					}		
					return utenteList;
				}
			});
		    if(utenteList.size() > 0)
		    	return utenteList.get(0);
		    else
		    	return null;
			
			
		}
		
		
		@Override
		public List<Utente> getAssegnatariResponsabileTop() throws Throwable {
			
			List<Utente> utenteList = new ArrayList<Utente>(0);

			String stringaSql = "select matricola_util from utente " + 
			"where matricola_util = (select cd_value from configurazione where cd_key='TOP_RESPONSABILE') " + 
			"and data_cancellazione is null";
			
			
			utenteList = getHibernateTemplate().execute(new HibernateCallback<List<Utente>>() {
				@Override
				public List<Utente> doInHibernate(Session session) throws HibernateException, SQLException {
					SQLQuery sqlQuery = session.createSQLQuery(stringaSql);
					List<String> queryResult = sqlQuery.list();
					List<Utente> utenteList = new ArrayList<Utente>();					
					for (String matricolaUtil:queryResult){
						DetachedCriteria criteria = DetachedCriteria.forClass( Utente.class ).add( Restrictions.eq("matricolaUtil", matricolaUtil) );
						Utente utente = (Utente) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
						utenteList.add((Utente) utente);
					}		
					return utenteList;
				}
			});
		    if(utenteList.size() > 0)
		    	return utenteList;
		    else
		    	return null;
			
		}
		
		@Override
		public List<Utente> getAssegnatariLegaliInterni() throws Throwable {
			
			List<Utente> utenteList = new ArrayList<Utente>(0);

			String stringaSql = "select u.matricola_util from gruppo_utente a, r_utente_gruppo b, utente u " + 
			"where a.id=b.id_gruppo_utente and b.matricola_utente=u.matricola_util " + 
			"and a.codice='LEG_ARC_GESTOREFASCICOLI' and a.data_cancellazione is null " +
			"and u.data_cancellazione is null and (u.responsabile_util is null or u.responsabile_util = 'N') " +
			"order by u.nominativo_util";
			
			
			utenteList = getHibernateTemplate().execute(new HibernateCallback<List<Utente>>() {
				@Override
				public List<Utente> doInHibernate(Session session) throws HibernateException, SQLException {
					SQLQuery sqlQuery = session.createSQLQuery(stringaSql);
					List<String> queryResult = sqlQuery.list();
					List<Utente> utenteList = new ArrayList<Utente>();					
					for (String matricolaUtil:queryResult){
						DetachedCriteria criteria = DetachedCriteria.forClass( Utente.class ).add( Restrictions.eq("matricolaUtil", matricolaUtil) );
						Utente utente = (Utente) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
						utenteList.add((Utente) utente);
					}		
					return utenteList;
				}
			});
		    if(utenteList.size() > 0)
		    	return utenteList;
		    else
		    	return null;
			
		}
		
		@Override
		public List<Utente> getAssegnatariResponsabiliOverHead() throws Throwable {
			
			List<Utente> utenteList = new ArrayList<Utente>(0);

			
			String stringaSql = "select u.matricola_util from gruppo_utente a, r_utente_gruppo b, utente u " +
			"where a.id=b.id_gruppo_utente and b.matricola_utente=u.matricola_util "+ 
			"and a.codice='LEG_ARC_GESTOREFASCICOLI' and a.data_cancellazione is null "+ 
			"and u.data_cancellazione is null " +
			"and not (u.responsabile_util is null or u.responsabile_util = 'N') " +
			"and (" +
			"u.matricola_util=(select cd_value from configurazione where cd_key='TOP_RESPONSABILE') " +
			"or "+
			"u.matricola_resp_util=(select cd_value from configurazione where cd_key='TOP_RESPONSABILE') " +
			"and u.matricola_util=(select cd_value from configurazione where cd_key='TOP_HEAD')"+
			") order by u.nominativo_util";
			
			utenteList = getHibernateTemplate().execute(new HibernateCallback<List<Utente>>() {
				@Override
				public List<Utente> doInHibernate(Session session) throws HibernateException, SQLException {
					SQLQuery sqlQuery = session.createSQLQuery(stringaSql);
					List<String> queryResult = sqlQuery.list();
					List<Utente> utenteList = new ArrayList<Utente>();					
					for (String matricolaUtil:queryResult){
						DetachedCriteria criteria = DetachedCriteria.forClass( Utente.class ).add( Restrictions.eq("matricolaUtil", matricolaUtil) );
						Utente utente = (Utente) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
						utenteList.add((Utente) utente);
					}		
					return utenteList;
				}
			});
		    if(utenteList.size() > 0)
		    	return utenteList;
		    else
		    	return null;
			
		}
		
		@Override
		public List<Utente> getAssegnatariResponsabiliHead() throws Throwable {
			
			List<Utente> utenteList = new ArrayList<Utente>(0);

			
			String stringaSql ="select u.matricola_util from gruppo_utente a, r_utente_gruppo b, utente u " +
			"where a.id=b.id_gruppo_utente and b.matricola_utente=u.matricola_util " +
			"and a.codice='LEG_ARC_GESTOREFASCICOLI' and a.data_cancellazione is null " + 
			"and u.data_cancellazione is null " +
			"and not (u.responsabile_util is null or u.responsabile_util = 'N') " +
			"and u.matricola_resp_util=(select cd_value from configurazione where cd_key='TOP_RESPONSABILE') " +
			"and u.matricola_util <> u.matricola_resp_util order by u.nominativo_util";
			
			utenteList = getHibernateTemplate().execute(new HibernateCallback<List<Utente>>() {
				@Override
				public List<Utente> doInHibernate(Session session) throws HibernateException, SQLException {
					SQLQuery sqlQuery = session.createSQLQuery(stringaSql);
					List<String> queryResult = sqlQuery.list();
					List<Utente> utenteList = new ArrayList<Utente>();					
					for (String matricolaUtil:queryResult){
						DetachedCriteria criteria = DetachedCriteria.forClass( Utente.class ).add( Restrictions.eq("matricolaUtil", matricolaUtil) );
						Utente utente = (Utente) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
						utenteList.add((Utente) utente);
					}		
					return utenteList;
				}
			});
		    if(utenteList.size() > 0)
		    	return utenteList;
		    else
		    	return null;
			
		}
		
		@Override
		public List<Utente> getAssegnatariResponsabiliHeadAndUnderHead() throws Throwable {
			
			List<Utente> utenteList = new ArrayList<Utente>(0);

			String stringaSql ="select u.matricola_util from gruppo_utente a, r_utente_gruppo b, utente u "+
			"where a.id=b.id_gruppo_utente and b.matricola_utente=u.matricola_util "+
			"and a.codice='LEG_ARC_GESTOREFASCICOLI' and a.data_cancellazione is null "+ 
			"and u.data_cancellazione is null "+
			"and not (u.responsabile_util is null or u.responsabile_util = 'N') "+
			"and u.matricola_util <> (select cd_value from configurazione where cd_key='TOP_RESPONSABILE') " +
			"order by u.nominativo_util";
			
			utenteList = getHibernateTemplate().execute(new HibernateCallback<List<Utente>>() {
				@Override
				public List<Utente> doInHibernate(Session session) throws HibernateException, SQLException {
					SQLQuery sqlQuery = session.createSQLQuery(stringaSql);
					List<String> queryResult = sqlQuery.list();
					List<Utente> utenteList = new ArrayList<Utente>();					
					for (String matricolaUtil:queryResult){
						DetachedCriteria criteria = DetachedCriteria.forClass( Utente.class ).add( Restrictions.eq("matricolaUtil", matricolaUtil) );
						Utente utente = (Utente) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
						utenteList.add((Utente) utente);
					}		
					return utenteList;
				}
			});
		    if(utenteList.size() > 0)
		    	return utenteList;
		    else
		    	return null;
			
		}
		@Override
		public List<Utente> getAssegnatariUnderHead() throws Throwable {
			
			List<Utente> utenteList = new ArrayList<Utente>(0);

			String stringaSql ="select u.matricola_util from gruppo_utente a, r_utente_gruppo b, utente u "+
			"where a.id=b.id_gruppo_utente and b.matricola_utente=u.matricola_util "+ 
			"and a.codice='LEG_ARC_GESTOREFASCICOLI' and a.data_cancellazione is null "+ 
			"and u.data_cancellazione is null "+
			"and (select cd_value from configurazione where cd_key='TOP_RESPONSABILE') not in (u.matricola_resp_util,u.matricola_util) " + 
			"order by u.nominativo_util";
			
			utenteList = getHibernateTemplate().execute(new HibernateCallback<List<Utente>>() {
				@Override
				public List<Utente> doInHibernate(Session session) throws HibernateException, SQLException {
					SQLQuery sqlQuery = session.createSQLQuery(stringaSql);
					List<String> queryResult = sqlQuery.list();
					List<Utente> utenteList = new ArrayList<Utente>();					
					for (String matricolaUtil:queryResult){
						DetachedCriteria criteria = DetachedCriteria.forClass( Utente.class ).add( Restrictions.eq("matricolaUtil", matricolaUtil) );
						Utente utente = (Utente) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
						utenteList.add((Utente) utente);
					}		
					return utenteList;
				}
			});
		    if(utenteList.size() > 0)
		    	return utenteList;
		    else
		    	return null;
			
		}
		//******************************************************************************************************
		
		//MASSIMO CARUSO****************************************************************************************
		
		/**
		 * Restituisce l'elenco di utenti Head e GC, escludendo l'utente
		 * corrispondente alla matricola passata in input.
		 * @param matricola la matricola dell'utente da non aggiungere nella lista.
		 * @return la lista di utenti.
		 * @author MASSIMO CARUSO
		 */
		public List<Utente> getAssegnatariHead(String matricola){

			List<Utente> utenteList = new ArrayList<Utente>(0);
			
			String stringaSql ="select u.matricola_util from gruppo_utente a, r_utente_gruppo b, utente u "+
			"where a.id=b.id_gruppo_utente and b.matricola_utente=u.matricola_util "+ 
			"and a.codice='LEG_ARC_GESTOREFASCICOLI' and a.data_cancellazione is null "+ 
			"and u.data_cancellazione is null "+
			"and not (u.responsabile_util is null or u.responsabile_util = 'N') "+
			"and u.matricola_resp_util=(select cd_value from LEG_ARC.CONFIGURAZIONE where cd_key='TOP_RESPONSABILE') "+
			"and u.matricola_util <> u.matricola_resp_util "+ 
			"and u.matricola_util <> '"+matricola+"' "+
			"and u.assente = 'F' "+
			"order by u.nominativo_util";
			
			utenteList = getHibernateTemplate().execute(new HibernateCallback<List<Utente>>() {
				@Override
				public List<Utente> doInHibernate(Session session) throws HibernateException, SQLException {
					SQLQuery sqlQuery = session.createSQLQuery(stringaSql);
					List<String> queryResult = sqlQuery.list();
					List<Utente> utenteList = new ArrayList<Utente>();					
					for (String matricolaUtil:queryResult){
						DetachedCriteria criteria = DetachedCriteria.forClass( Utente.class ).add( Restrictions.eq("matricolaUtil", matricolaUtil) );
						Utente utente = (Utente) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
						utenteList.add((Utente) utente);
					}
					//aggiungo GC (PIETRO GERACE)
					String gc = "0910004812";
					/*try {
						gc = getAssegnatariResponsabileTop().get(0).getMatricolaUtil();
					} catch (Throwable e) {
						e.printStackTrace();
						gc = "0910005307";
					}*/
					DetachedCriteria criteria = DetachedCriteria.forClass( Utente.class ).add( Restrictions.eq("matricolaUtil", gc) );
					Utente utente = (Utente) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
					utenteList.add((Utente) utente);
		
					return utenteList;
				}
			});
		    if(utenteList.size() > 0)
		    	return utenteList;
		    else
		    	return null;
		}
		
		/**
		 * Restituisce l'elenco di utenti Head, Manager e Team, escludendo l'utente
		 * corrispondente alla matricola passata in input.
		 * @param matricola la matricola dell'utente da non aggiungere nella lista
		 * @return la lista di utenti.
		 * @author MASSIMO CARUSO
		 */
		public List<Utente> getAssegnatariAttoHead(String matricola){
			List<Utente> utenteList = new ArrayList<Utente>(0);

			String stringaSql ="select u.matricola_util from gruppo_utente a, r_utente_gruppo b, utente u "+
			"where a.id=b.id_gruppo_utente and b.matricola_utente=u.matricola_util "+ 
			"and a.codice='LEG_ARC_GESTOREFASCICOLI' and a.data_cancellazione is null "+ 
			"and u.data_cancellazione is null "+
			"and u.assente = 'F' "+
			"and (select cd_value from configurazione where cd_key='TOP_RESPONSABILE') not in (u.matricola_resp_util,u.matricola_util) " + 
			"order by u.nominativo_util";
			
			utenteList = getHibernateTemplate().execute(new HibernateCallback<List<Utente>>() {
				@Override
				public List<Utente> doInHibernate(Session session) throws HibernateException, SQLException {
					SQLQuery sqlQuery = session.createSQLQuery(stringaSql);
					List<String> queryResult = sqlQuery.list();
					List<Utente> utenteList = new ArrayList<Utente>();					
					for (String matricolaUtil:queryResult){
						DetachedCriteria criteria = DetachedCriteria.forClass( Utente.class ).add( Restrictions.eq("matricolaUtil", matricolaUtil) );
						Utente utente = (Utente) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
						utenteList.add((Utente) utente);
					}		
					return utenteList;
				}
			});
		    if(utenteList.size() > 0)
		    	return utenteList;
		    else
		    	return null;
		}
		
		/**
		 * Restituisce l'elenco di utenti Manager e Head, escludendo l'utente
		 * corrispondente alla matricola passata in input.
		 * @param matricola la matricola dell'utente da non aggiungere nella lista
		 * @return la lista di utenti.
		 * @author MASSIMO CARUSO
		 */
		public List<Utente> getAssegnatariManager(String matricola){
			List<Utente> utenteList = new ArrayList<Utente>(0);

			String stringaSql ="select u.matricola_util from gruppo_utente a, r_utente_gruppo b, utente u "+
					"where a.id=b.id_gruppo_utente and b.matricola_utente=u.matricola_util "+
					"and a.codice='LEG_ARC_GESTOREFASCICOLI' and a.data_cancellazione is null "+ 
					"and u.data_cancellazione is null "+
					"and not (u.responsabile_util is null or u.responsabile_util = 'N') "+
					"and u.matricola_util <> (select cd_value from configurazione where cd_key='TOP_RESPONSABILE') " +
					"and u.matricola_util <> '"+matricola+"' "+
					"and u.assente = 'F' "+
					"order by u.nominativo_util";
			
			utenteList = getHibernateTemplate().execute(new HibernateCallback<List<Utente>>() {
				@Override
				public List<Utente> doInHibernate(Session session) throws HibernateException, SQLException {
					SQLQuery sqlQuery = session.createSQLQuery(stringaSql);
					List<String> queryResult = sqlQuery.list();
					List<Utente> utenteList = new ArrayList<Utente>();					
					for (String matricolaUtil:queryResult){
						DetachedCriteria criteria = DetachedCriteria.forClass( Utente.class ).add( Restrictions.eq("matricolaUtil", matricolaUtil) );
						Utente utente = (Utente) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
						utenteList.add((Utente) utente);
					}		
					return utenteList;
				}
			});
		    if(utenteList.size() > 0)
		    	return utenteList;
		    else
		    	return null;
		}
		
		/**
		 * Restituisce l'elenco di utenti Manager e Team, escludendo l'utente
		 * corrispondente alla matricola passata in input.
		 * @param matricola la matricola dell'utente da non aggiungere nella lista.
		 * @return la lista di utenti.
		 * @author MASSIMO CARUSO
		 */
		public List<Utente> getAssegnatariAttoManager(String matricola){
			List<Utente> utenteList = new ArrayList<Utente>(0);

			String stringaSql ="select u.matricola_util from gruppo_utente a, r_utente_gruppo b, utente u "+
			"where a.id=b.id_gruppo_utente and b.matricola_utente=u.matricola_util "+ 
			"and a.codice='LEG_ARC_GESTOREFASCICOLI' and a.data_cancellazione is null "+ 
			"and u.data_cancellazione is null "+
			"and (select cd_value from configurazione where cd_key='TOP_RESPONSABILE') not in (u.matricola_resp_util,u.matricola_util) " + 
			"and u.matricola_util <> '"+matricola+"' "+
			"and u.assente = 'F' "+
			"order by u.nominativo_util";
			
			utenteList = getHibernateTemplate().execute(new HibernateCallback<List<Utente>>() {
				@Override
				public List<Utente> doInHibernate(Session session) throws HibernateException, SQLException {
					SQLQuery sqlQuery = session.createSQLQuery(stringaSql);
					List<String> queryResult = sqlQuery.list();
					List<Utente> utenteList = new ArrayList<Utente>();					
					for (String matricolaUtil:queryResult){
						DetachedCriteria criteria = DetachedCriteria.forClass( Utente.class ).add( Restrictions.eq("matricolaUtil", matricolaUtil) );
						Utente utente = (Utente) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
						utenteList.add((Utente) utente);
					}		
					return utenteList;
				}
			});
		    if(utenteList.size() > 0)
		    	return utenteList;
		    else
		    	return null;
		}
		
		/**
		 * Restituisce l'elenco di utenti Head e Manager.
		 * @return la lista di utenti.
		 * @author MASSIMO CARUSO
		 */
		public List<Utente> getAssegnatariTeam(){
			List<Utente> utenteList = new ArrayList<Utente>(0);

			String stringaSql ="select u.matricola_util from gruppo_utente a, r_utente_gruppo b, utente u "+
			"where a.id=b.id_gruppo_utente and b.matricola_utente=u.matricola_util "+
			"and a.codice='LEG_ARC_GESTOREFASCICOLI' and a.data_cancellazione is null "+ 
			"and u.data_cancellazione is null "+
			"and not (u.responsabile_util is null or u.responsabile_util = 'N') "+
			"and u.matricola_util <> (select cd_value from configurazione where cd_key='TOP_RESPONSABILE') " +
			"and u.assente = 'F' "+
			"order by u.nominativo_util";
			
			utenteList = getHibernateTemplate().execute(new HibernateCallback<List<Utente>>() {
				@Override
				public List<Utente> doInHibernate(Session session) throws HibernateException, SQLException {
					SQLQuery sqlQuery = session.createSQLQuery(stringaSql);
					List<String> queryResult = sqlQuery.list();
					List<Utente> utenteList = new ArrayList<Utente>();					
					for (String matricolaUtil:queryResult){
						DetachedCriteria criteria = DetachedCriteria.forClass( Utente.class ).add( Restrictions.eq("matricolaUtil", matricolaUtil) );
						Utente utente = (Utente) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
						utenteList.add((Utente) utente);
					}		
					return utenteList;
				}
			});
		    if(utenteList.size() > 0)
		    	return utenteList;
		    else
		    	return null;
		}
		
		
		/**
		 * Restituisce l'elenco di utenti con grado Head.
		 * @return la lista di utenti head.
		 * @author MASSIMO CARUSO
		 */
		public List<Utente> getHeads(){
			List<Utente> utenteList = new ArrayList<Utente>(0);

			
			String stringaSql ="select u.matricola_util from gruppo_utente a, r_utente_gruppo b, utente u "+
			"where a.id=b.id_gruppo_utente and b.matricola_utente=u.matricola_util "+
			"and a.codice='LEG_ARC_GESTOREFASCICOLI' and a.data_cancellazione is null "+
			"and u.data_cancellazione is null "+
			"and not (u.responsabile_util is null or u.responsabile_util = 'N') "+
			"and u.matricola_resp_util=(select cd_value from configurazione where cd_key='TOP_RESPONSABILE') "+
			"and u.matricola_util <> u.matricola_resp_util "+ 
			"order by u.nominativo_util";
			
			utenteList = getHibernateTemplate().execute(new HibernateCallback<List<Utente>>() {
				@Override
				public List<Utente> doInHibernate(Session session) throws HibernateException, SQLException {
					SQLQuery sqlQuery = session.createSQLQuery(stringaSql);
					List<String> queryResult = sqlQuery.list();
					List<Utente> utenteList = new ArrayList<Utente>();					
					for (String matricolaUtil:queryResult){
						DetachedCriteria criteria = DetachedCriteria.forClass( Utente.class ).add( Restrictions.eq("matricolaUtil", matricolaUtil) );
						Utente utente = (Utente) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
						utenteList.add((Utente) utente);
					}		
					return utenteList;
				}
			});
		    if(utenteList.size() > 0)
		    	return utenteList;
		    else
		    	return null;
		}
		
		/**
		 * Restituisce l'elenco di utenti Manager.
		 * @return la lista di utenti Manager.
		 * @author MASSIMO CARUSO
		 */
		public List<Utente> getManagers(){
			List<Utente> utenteList = new ArrayList<Utente>(0);

			String stringaSql ="select u.matricola_util from gruppo_utente a, r_utente_gruppo b, utente u "+
			"where a.id=b.id_gruppo_utente and b.matricola_utente=u.matricola_util "+ 
			"and a.codice='LEG_ARC_GESTOREFASCICOLI' and a.data_cancellazione is null "+ 
			"and u.data_cancellazione is null "+
			"and not (u.responsabile_util is null or u.responsabile_util = 'N') "+
			"and u.matricola_util <> (select cd_value from configurazione where cd_key='TOP_RESPONSABILE') "+
			"and u.matricola_resp_util <> (select cd_value from configurazione where cd_key='TOP_RESPONSABILE') " + 
			"order by u.nominativo_util";
			
			utenteList = getHibernateTemplate().execute(new HibernateCallback<List<Utente>>() {
				@Override
				public List<Utente> doInHibernate(Session session) throws HibernateException, SQLException {
					SQLQuery sqlQuery = session.createSQLQuery(stringaSql);
					List<String> queryResult = sqlQuery.list();
					List<Utente> utenteList = new ArrayList<Utente>();					
					for (String matricolaUtil:queryResult){
						DetachedCriteria criteria = DetachedCriteria.forClass( Utente.class ).add( Restrictions.eq("matricolaUtil", matricolaUtil) );
						Utente utente = (Utente) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
						utenteList.add((Utente) utente);
					}		
					return utenteList;
				}
			});
		    if(utenteList.size() > 0)
		    	return utenteList;
		    else
		    	return null;
		}
		
		/**
		 * Restituisce l'elenco di utenti Team.
		 * @return la lista di utenti Team.
		 * @author MASSIMO CARUSO
		 */
		public List<Utente> getTeams(){
			List<Utente> utenteList = new ArrayList<Utente>(0);

			String stringaSql ="select u.matricola_util from gruppo_utente a, r_utente_gruppo b, utente u "+
			"where a.id=b.id_gruppo_utente and b.matricola_utente=u.matricola_util "+ 
			"and a.codice='LEG_ARC_GESTOREFASCICOLI' and a.data_cancellazione is null "+ 
			"and u.data_cancellazione is null "+
			"and (u.responsabile_util is null or u.responsabile_util = 'N') "+
			"and (select cd_value from configurazione where cd_key='TOP_RESPONSABILE') not in (u.matricola_resp_util,u.matricola_util) " + 
			"order by u.nominativo_util";
			
			utenteList = getHibernateTemplate().execute(new HibernateCallback<List<Utente>>() {
				@Override
				public List<Utente> doInHibernate(Session session) throws HibernateException, SQLException {
					SQLQuery sqlQuery = session.createSQLQuery(stringaSql);
					List<String> queryResult = sqlQuery.list();
					List<Utente> utenteList = new ArrayList<Utente>();					
					for (String matricolaUtil:queryResult){
						DetachedCriteria criteria = DetachedCriteria.forClass( Utente.class ).add( Restrictions.eq("matricolaUtil", matricolaUtil) );
						Utente utente = (Utente) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
						utenteList.add((Utente) utente);
					}		
					return utenteList;
				}
			});
		    if(utenteList.size() > 0)
		    	return utenteList;
		    else
		    	return null;
		}
		
		/**
		 * Verifica se l'utente associato alla matricola in input � un 
		 * utente Head.
		 * @param matricola la matricola dell'utente.
		 * @return true se l'utente � un membro Head, false altrimenti.
		 * @author MASSIMO CARUSO
		 */
		public boolean isHead(String matricola){
			List<Utente> heads = getHeads();
			for(Utente user : heads){
				if(user.getMatricolaUtil().equals(matricola))
					return true;
			}
			return false;
		}
		
		/**
		 * Verifica se l'utente associato alla matricola in input � un 
		 * utente Manager.
		 * @param matricola la matricola dell'utente.
		 * @return true se l'utente � un membro Manager, false altrimenti.
		 * @author MASSIMO CARUSO
		 */
		public boolean isManager(String matricola){
			List<Utente> managers = getManagers();
			for(Utente user : managers){
				if(user.getMatricolaUtil().equals(matricola))
					return true;
			}
			return false;
		}
		
		/**
		 * Verifica se l'utente associato alla matricola in input � un 
		 * utente Team.
		 * @param matricola la matricola dell'utente.
		 * @return true se l'utente � un membro Team, false altrimenti.
		 * @author MASSIMO CARUSO
		 */
		public boolean isTeam(String matricola){
			List<Utente> teams = getTeams();
			for(Utente user : teams){
				if(user.getMatricolaUtil().equals(matricola))
					return true;
			}
			return false;
		}
		//******************************************************************************************************
		

		public Integer updateRUtenteFascicoloAggiornaDataCancellazione(long idFascicolo) throws Throwable {
			    Integer updateData= new Integer(0);
				StringBuffer stringaSql = new StringBuffer();
				stringaSql.append("update r_utente_fascicolo ruf ");
				stringaSql.append("SET ruf.data_cancellazione=? ");
				stringaSql.append("where ruf.data_cancellazione is null ");
				stringaSql.append("and ruf.id_fascicolo=");
				stringaSql.append(idFascicolo);
				updateData = getHibernateTemplate().execute(new HibernateCallback<Integer>() {
					@Override
					public Integer doInHibernate(Session session) throws HibernateException, SQLException {
						SQLQuery sqlQuery = session.createSQLQuery(stringaSql.toString());
						sqlQuery.setDate(0, new Date());
						
						int updated = sqlQuery.executeUpdate();			
						 
						return new Integer(updated);
					}
					 
				});
			   
			    return 	 updateData;
			    
			}
		
		

		

		public void updateRUtenteFascicoloDataCancellazione(RUtenteFascicolo vo) throws Throwable{
			getHibernateTemplate().update(vo);
		} 
		
		public void insertRUtenteFascicolo(RUtenteFascicolo vo) throws Throwable {
			getHibernateTemplate().save(vo);
		    
		}

		@Override
		public void eliminaRUtenteGruppo(RUtenteGruppo vo) {
			getHibernateTemplate().delete(vo);
		}

		@Override
		public void insertRUtenteGruppo(RUtenteGruppo vo) {
			getHibernateTemplate().save(vo);
		}
		
		@Override
		public Utente modificaPresenzaUtente(String userId, String assente) throws Throwable {
			Utente utente=this.leggiUtenteDaUserId(userId);
			if(utente!=null){
			utente.setAssente(assente);
			getHibernateTemplate().update(utente);
			}
			return utente;
		}
		
		@Override
		public List<String> estraiListaIndirizziMail(List<String> userId)throws Throwable{
			DetachedCriteria criteria = DetachedCriteria.forClass( Utente.class );
			
			criteria.add(Restrictions.in("useridUtil", userId));
			
			criteria.setProjection(Projections.distinct(Projections.property("emailUtil")));
			List<String> lista = getHibernateTemplate().findByCriteria(criteria);
			return lista;
		}

		@Override
		public List<Utente> leggiUtentiAlesocr() throws Throwable {
			
			List<Utente> lista =  new ArrayList<Utente>();
			DetachedCriteria criteria = DetachedCriteria.forClass(RUtenteGruppo.class);
			
			criteria.createAlias("gruppoUtente", "gruppoUtente");
			criteria.add(Restrictions.eq("gruppoUtente.codice", CostantiDAO.LEG_ARC_GESTORE_ARCHIVIO_PROCURE));
			criteria.add(Restrictions.isNull("dataCancellazione"));
			List<RUtenteGruppo> listaRelazione =(List<RUtenteGruppo>) getHibernateTemplate().findByCriteria(criteria);	
			
			for (RUtenteGruppo relazione:listaRelazione){
				lista.add(relazione.getUtente());
			}	
			return lista;
		}
		
		@Override
		public List<Utente> leggiUtentiOperatoriSegreteria() throws Throwable {
			
			List<Utente> lista =  new ArrayList<Utente>();
			DetachedCriteria criteria = DetachedCriteria.forClass(RUtenteGruppo.class);
			
			criteria.createAlias("gruppoUtente", "gruppoUtente");
			criteria.add(Restrictions.eq("gruppoUtente.codice", CostantiDAO.OPERATORE_SEGRETERIA));
			criteria.add(Restrictions.isNull("dataCancellazione"));
			List<RUtenteGruppo> listaRelazione =(List<RUtenteGruppo>) getHibernateTemplate().findByCriteria(criteria);	
			
			for (RUtenteGruppo relazione:listaRelazione){
				lista.add(relazione.getUtente());
			}	
			return lista;
		}
		
		@Override
		public List<Utente> leggiUtentiGestoriFascicoli() throws Throwable {
			
			List<Utente> lista =  new ArrayList<Utente>();
			DetachedCriteria criteria = DetachedCriteria.forClass(RUtenteGruppo.class);
			
			criteria.createAlias("gruppoUtente", "gruppoUtente");
			criteria.add(Restrictions.eq("gruppoUtente.codice", CostantiDAO.GESTORE_FASCICOLI));
			criteria.add(Restrictions.isNull("dataCancellazione"));
			List<RUtenteGruppo> listaRelazione =(List<RUtenteGruppo>) getHibernateTemplate().findByCriteria(criteria);	
			
			for (RUtenteGruppo relazione:listaRelazione){
				lista.add(relazione.getUtente());
			}	
			return lista;
		}
		
		/**
		 * Metodo di lettura degli utenti appartenenti al gruppo in input.
		 * <p>
		 * @return ritorna la lista degli oggetti model recuperati.
		 * @exception Throwable
		 */
		@Override
		public List<Utente> leggiUtentiDaGruppo(GruppoUtente gruppo) throws Throwable {
			
			List<Utente> lista =  new ArrayList<Utente>();
			DetachedCriteria criteria = DetachedCriteria.forClass(RUtenteGruppo.class);
			
			criteria.createAlias("gruppoUtente", "gruppoUtente");
			criteria.add(Restrictions.eq("gruppoUtente.codice", gruppo.getCodice()));
			criteria.add(Restrictions.isNull("dataCancellazione"));
			List<RUtenteGruppo> listaRelazione =(List<RUtenteGruppo>) getHibernateTemplate().findByCriteria(criteria);	
			
			for (RUtenteGruppo relazione:listaRelazione){
				lista.add(relazione.getUtente());
			}	
			return lista;
		}

		@Override
		public Utente findUtenteByNominativo(String nominativoUtil) throws Throwable {
			Utente ut = null;
			DetachedCriteria criteria = DetachedCriteria.forClass(Utente.class);
			criteria.add(Restrictions.eq("nominativoUtil", nominativoUtil));
			List listaResult =  getHibernateTemplate().findByCriteria(criteria);
			if (listaResult!=null && listaResult.size()>0) {
				ut = (Utente) listaResult.get(0);
			}
			return ut;
		}

		
}
