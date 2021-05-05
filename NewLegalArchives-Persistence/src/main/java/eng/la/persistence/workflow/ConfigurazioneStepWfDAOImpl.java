package eng.la.persistence.workflow;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import eng.la.model.ClasseWf;
import eng.la.model.ConfigurazioneStepWf;
import eng.la.persistence.CostantiDAO;
import eng.la.util.SpringUtil;

/**
 * <h1>Classe DAO d'implemtazione delle operazioni su base dati, 
 * per entità ConfigurazioneStepWf</h1>
 * La classe DAO espone le operazioni di lettura/scrittura sulla base dati per
 * l'entità ConfigurazioneStepWf.
 * 
 * @author ACER
 */
@SuppressWarnings("unchecked")
@Component("configurazioneStepWfDAO")
public class ConfigurazioneStepWfDAOImpl extends HibernateDaoSupport implements ConfigurazioneStepWfDAO{

	@Autowired
	public ConfigurazioneStepWfDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}

	/**
	 * Metodo di lettura dello step corrente del workflow.
	 * <p>
	 * @param idObject: identificativo dell'istanza di oggetto corrente
	 * @param idClasseWf: identificativo della classe del workflow
	 * @param lingua: lingua di ricerca
	 * @return ritorna l'oggetto model ConfigurazioneStepWf popolato con i dati inseriti.
	 * @exception Throwable
	 */
	@Override
	public ConfigurazioneStepWf leggiConfigurazioneStepCorrente(long idObject, long idClasseWf, String lingua) throws Throwable {
		// TODO Auto-generated method stub
		List<ConfigurazioneStepWf> configurazioneList = new ArrayList<ConfigurazioneStepWf>(0);
		// si costruisce la stringa SQL
	    StringBuffer stringaSql = new StringBuffer();
	    stringaSql.append("SELECT c.id ");
	    stringaSql.append("FROM configurazione_step_wf c, step_wf s, fascicolo_wf fw, fascicolo f, stato_wf st ");
	    stringaSql.append("WHERE fw.id_fascicolo = f.ID ");
	    stringaSql.append("AND fw.id_stato_wf = st.id ");
	    stringaSql.append("AND st.cod_gruppo_lingua = '");
	    stringaSql.append(String.valueOf(CostantiDAO.STATO_WF_IN_CORSO));
	    stringaSql.append("' AND st.lang = '");
	    stringaSql.append(lingua);
	    stringaSql.append("' AND s.id_fascicolo_wf = fw.ID ");
	    stringaSql.append("AND s.data_chiusura IS NULL ");
	    stringaSql.append("AND s.id_configurazione_step_wf = c.ID ");
	    stringaSql.append("AND c.lang = '");
	    stringaSql.append(CostantiDAO.LINGUA_ITALIANA);
	    stringaSql.append("' AND c.id_classe_wf = ");
	    stringaSql.append(String.valueOf(idClasseWf));
	    stringaSql.append("  AND c.data_fine_validita is null ");
	    stringaSql.append(" AND f.ID = ");
	    stringaSql.append(String.valueOf(idObject));
	    configurazioneList = getHibernateTemplate().execute(new HibernateCallback<List<ConfigurazioneStepWf>>() {
			@Override
			public List<ConfigurazioneStepWf> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery sqlQuery = session.createSQLQuery(stringaSql.toString());
				List<BigDecimal> queryResult = sqlQuery.list();
				List<ConfigurazioneStepWf> confList = new ArrayList<ConfigurazioneStepWf>();					
				for (BigDecimal id:queryResult){
					DetachedCriteria criteria = DetachedCriteria.forClass( ConfigurazioneStepWf.class ).add( Restrictions.eq("id", id.longValue()) );
					ConfigurazioneStepWf configurazione = (ConfigurazioneStepWf) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
					confList.add((ConfigurazioneStepWf) configurazione);
				}		
				return confList;
			}
		});
	    if(configurazioneList.size() > 0)
	    	return configurazioneList.get(0);
	    else
	    	return null;
	
	}
	
		
	/**
	 * Metodo di lettura del successivo step del workflow.
	 * <p>
	 * @param idClasse: identificativo della classe del workflow
	 * @param stateFromEffettivo: nome dello step corrente del workflow o equivalentemente dello stato dell'istanza
	 * @param respPrimoRiporto: booleano che indica se il responsabile dell'assegnatario attuale dello step è di primo riporto
	 * @param lingua: lingua di ricerca
	 * @return ritorna l'oggetto model ConfigurazioneStepWf popolato con i dati inseriti.
	 * @exception Throwable
	 */
	@Override
	public ConfigurazioneStepWf leggiConfigurazioneStepSuccessivo(long idClasseWf, String stateFromEffettivo, Boolean respPrimoRiporto, String lingua) throws Throwable {
		// TODO Auto-generated method stub
		
		DetachedCriteria criteria = DetachedCriteria.forClass( ConfigurazioneStepWf.class )
				.addOrder(Order.asc("nomeStep"));
		if( stateFromEffettivo != null && stateFromEffettivo.trim().length() > 0 ){
			criteria.add( Restrictions.eq("stateFrom", stateFromEffettivo) ) ;
		}
		criteria.add( Restrictions.isNull("dataFineValidita")) ;
		criteria.createAlias("classeWf", "classeWf");
		criteria.add(Restrictions.eq("classeWf.id", idClasseWf));
		criteria.add( Restrictions.ilike("numeroAzioneStep", '%' + CostantiDAO.SUFF_AVANZAMENTO_WF, MatchMode.ANYWHERE) ) ;
		criteria.add( Restrictions.eq("lang", lingua) ) ;
		List<ConfigurazioneStepWf> lista = getHibernateTemplate().findByCriteria(criteria);	
		if(lista.size() == 0)
			return null;
		if(lista.size() > 1 ){
			if(respPrimoRiporto)
				return lista.get(0);
			else
				return lista.get(1);
		}
		else
			return lista.get(0);
	}
		
//	/**
//	 * Metodo di lettura del primo step del workflow.
//	 * <p>
//	 * @param idClasse: identificativo della classe del workflow
//	 * @param primoRiporto: booleano che indica se il creatore dello step è di primo riporto
//	 * @param respPrimoRiporto: booleano che indica se il responsabile del creatore dello step è di primo riporto
//	 * @param matricola: matricola del creatore dello step
//	 * @return ritorna l'oggetto model ConfigurazioneStepWf popolato con i dati inseriti.
//	 * @exception Throwable
//	 */
//	@Override
//	public ConfigurazioneStepWf leggiConfigurazionePrimoStep(long idClasseWf, Boolean primoRiporto, Boolean respPrimoRiporto, String matricola) throws Throwable {
//		// TODO Auto-generated method stub
//		DetachedCriteria criteria = DetachedCriteria.forClass( ConfigurazioneStepWf.class )
//				.addOrder(Order.asc("nomeStep"));
//		criteria.add( Restrictions.isNull("dataFineValidita")) ;
//		criteria.createAlias("classeWf", "classeWf");
//		criteria.add(Restrictions.eq("classeWf.id", idClasseWf));
//		criteria.add( Restrictions.eq("lang", CostantiDAO.LINGUA_ITALIANA) ) ;
//		
//		if(primoRiporto) //in caso di primo riporto salto direttamente al terzo step
//			criteria.add(Restrictions.eq("nomeStep", CostantiDAO.PRIMO_NUMERO_AZIONE_PRIMO_LIVELLO) ) ;
//		else if(respPrimoRiporto) // nel caso in cui il diretto responsabile sia primo riporto salto direttamente al secondo step
//			criteria.add(Restrictions.eq("nomeStep", CostantiDAO.PRIMO_NUMERO_AZIONE_RESP_PRIMO_LIVELLO) ) ;
//		else
//			criteria.add(Restrictions.eq("nomeStep", CostantiDAO.PRIMO_NUMERO_AZIONE) ) ;
//		
//		ConfigurazioneStepWf configurazione = (ConfigurazioneStepWf) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );
//		
//		// se sono l'assegnatario dello step di destinazione salto direttamente allo step successivo
//		if(configurazione.getTipoAssegnazione().equalsIgnoreCase(CostantiDAO.ASSEGNAZIONE_MANUALE) && configurazione.getUtente().getMatricolaUtil().equalsIgnoreCase(matricola))
//			return leggiConfigurazioneStepSuccessivo(idClasseWf, configurazione.getStateTo(), respPrimoRiporto);
//		else
//			return configurazione;
//	}
	
	/**
	 * Metodo di lettura dello step del workflow.
	 * <p>
	 * @param idClasse: identificativo della classe del workflow
	 * @param primoRiporto: booleano che indica se il creatore dello step è di primo riporto
	 * @param respPrimoRiporto: booleano che indica se il responsabile del creatore dello step è di primo riporto
	 * @param matricola: matricola del creatore dello step
	 * @param lingua: lingua di ricerca
	 * @return ritorna l'oggetto model ConfigurazioneStepWf popolato con i dati inseriti.
	 * @exception Throwable
	 */
	@Override
	public ConfigurazioneStepWf leggiConfigurazioneStep(long idClasseWf, Boolean primoRiporto, Boolean respPrimoRiporto, Boolean topResponsabile, String matricola, String lingua) throws Throwable {
		// TODO Auto-generated method stub
		DetachedCriteria criteria = DetachedCriteria.forClass( ConfigurazioneStepWf.class )
				.addOrder(Order.asc("nomeStep"));
		criteria.add( Restrictions.isNull("dataFineValidita")) ;
		criteria.createAlias("classeWf", "classeWf");
		criteria.add(Restrictions.eq("classeWf.id", idClasseWf));
		criteria.add( Restrictions.eq("lang", lingua) ) ;
		
		if(topResponsabile)
			criteria.add(Restrictions.isNull("tipoAssegnazione")) ;
		else if(primoRiporto) //in caso di primo riporto salto direttamente al terzo step
			criteria.add(Restrictions.eq("nomeStep", CostantiDAO.PRIMO_NUMERO_AZIONE_PRIMO_LIVELLO) ) ;
		else if(respPrimoRiporto) // nel caso in cui il diretto responsabile sia primo riporto salto direttamente al secondo step
			criteria.add(Restrictions.eq("nomeStep", CostantiDAO.PRIMO_NUMERO_AZIONE_RESP_PRIMO_LIVELLO) ) ;
		else
			criteria.add(Restrictions.eq("nomeStep", CostantiDAO.PRIMO_NUMERO_AZIONE) ) ;
		
		ConfigurazioneStepWf configurazione = (ConfigurazioneStepWf) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );
		if(topResponsabile)
			return configurazione;
		// se sono l'assegnatario dello step di destinazione salto direttamente allo step successivo
		if(configurazione.getTipoAssegnazione().equalsIgnoreCase(CostantiDAO.ASSEGNAZIONE_MANUALE) && configurazione.getUtente().getMatricolaUtil().equalsIgnoreCase(matricola))
			return leggiConfigurazioneStepSuccessivo(idClasseWf, configurazione.getStateTo(), respPrimoRiporto, lingua);
		else
			return configurazione;
	}

	/**
	 * Metodo di lettura del primo step manuale a carico dell'utente.
	 * <p>
	 * @param idClasse: identificativo della classe del workflow
	 * @param matricola: matricola del creatore dello step
	 * @param lingua: lingua di ricerca
	 * @return ritorna l'oggetto model ConfigurazioneStepWf popolato con i dati inseriti.
	 * @exception Throwable
	 */
	@Override
	public ConfigurazioneStepWf leggiConfigurazioneManuale(long idClasseWf,String matricola, String lingua)
			throws Throwable {

		DetachedCriteria criteria = DetachedCriteria.forClass( ConfigurazioneStepWf.class )
				.addOrder(Order.desc("nomeStep"));
		criteria.add( Restrictions.isNull("dataFineValidita")) ;
		criteria.createAlias("classeWf", "classeWf");
		criteria.add(Restrictions.eq("classeWf.id", idClasseWf));
		criteria.add( Restrictions.eq("lang", lingua) ) ;
		criteria.add(Restrictions.eq("tipoAssegnazione", CostantiDAO.ASSEGNAZIONE_MANUALE));
		criteria.createAlias("utente", "utente");
		criteria.add(Restrictions.eq("utente.matricolaUtil", matricola));
		
		List<ConfigurazioneStepWf> lista = getHibernateTemplate().findByCriteria(criteria);	
		if(lista.size() == 0)
			return null;
		else
			return lista.get(0);
	}
	
	/**
	 * Metodo di lettura del primo step del workflow chiusura fascicolo.
	 * <p>
	 * @param lingua: lingua di ricerca
	 * @return ritorna l'oggetto model ConfigurazioneStepWf popolato con i dati inseriti.
	 * @exception Throwable
	 */
	@Override
	public ConfigurazioneStepWf leggiConfigurazionePrimoStepFascicolo( String lingua) throws Throwable {

		// ricavo l'identificativo della classe
		DetachedCriteria criteria = DetachedCriteria.forClass(ClasseWf.class);
		criteria.add(Restrictions.eq("codice", CostantiDAO.CHIUSURA_FASCICOLO));
		criteria.add(Restrictions.isNull("dataCancellazione"));
		ClasseWf classeWf = (ClasseWf) DataAccessUtils
				.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		
		DetachedCriteria criteriaConf = DetachedCriteria.forClass( ConfigurazioneStepWf.class )
				.createAlias("classeWf", "classeWf");
		criteriaConf.add(Restrictions.eq("classeWf.id", classeWf.getId()));
		criteriaConf.add( Restrictions.eq("lang", lingua) ) ;
		criteriaConf.add( Restrictions.isNull("dataFineValidita")) ;
		criteriaConf.add(Restrictions.eq("nomeStep", CostantiDAO.PRIMO_NUMERO_AZIONE) ) ;
		
		return (ConfigurazioneStepWf) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );
		
		}
	
	/**
	 * Metodo di lettura dello step successivo a quello passato in input.
	 * <p>
	 * @param configurazione: configurazione corrente
	 * @param primoRiporto: booleano che indica se il creatore dello step è di primo riporto
	 * @param respPrimoRiporto: booleano che indica se il responsabile del creatore dello step è di primo riporto
	 * @param lingua: lingua di ricerca
	 * @return ritorna l'oggetto model ConfigurazioneStepWf popolato con i dati inseriti.
	 * @exception Throwable
	 */
	@Override
	public ConfigurazioneStepWf leggiConfigurazioneSuccessivaStandard(ConfigurazioneStepWf configurazione, boolean primoRiporto, boolean respPrimoRiporto, String lingua) throws Throwable {

		DetachedCriteria criteria = DetachedCriteria.forClass( ConfigurazioneStepWf.class )
				.addOrder(Order.asc("nomeStep"));
		criteria.add( Restrictions.isNull("dataFineValidita")) ;
		criteria.createAlias("classeWf", "classeWf");
		criteria.add(Restrictions.eq("classeWf.id", configurazione.getClasseWf().getId()));
		criteria.add( Restrictions.ilike("numeroAzioneStep", '%' + CostantiDAO.SUFF_AVANZAMENTO_WF, MatchMode.ANYWHERE) ) ;
		criteria.add( Restrictions.eq("lang", lingua) ) ;
		
		//se sono in uno step che prevede ricorsione, tra gli step successivi c'è anche il corrente
		if(configurazione.getNomeStep().indexOf(CostantiDAO.ASSEGNAZIONE_RICORSIVA) > 0)
			criteria.add( Restrictions.ge("nomeStep", configurazione.getNomeStep()) ) ;
		
		//altrimenti no
		else
			criteria.add( Restrictions.gt("nomeStep", configurazione.getNomeStep()) ) ;
		
		if(primoRiporto){ //se sono un primo riporto il passo successivo non potrà essere ad un responsabile né ad un primo riporto
			criteria.add( Restrictions.ne("tipoAssegnazione", CostantiDAO.ASSEGNAZIONE_RESPONSABILE));
			criteria.add( Restrictions.ne("tipoAssegnazione", CostantiDAO.ASSEGNAZIONE_PRIMO_RIPORTO));
			criteria.add( Restrictions.ne("tipoAssegnazione", CostantiDAO.ASSEGNAZIONE_SEGRETERIA));
		}
		
		else if(respPrimoRiporto) // se il mio responsabile è primo riporto il passo successivo non può essere ricorsivo
			criteria.add(Restrictions.not(Restrictions.like("nomeStep", "%ASSEGNAZIONE_RICORSIVA%")));
		
		
		List<ConfigurazioneStepWf> lista = getHibernateTemplate().findByCriteria(criteria);	
		
		if(lista.size() > 1 ){
			if(respPrimoRiporto)
				return lista.get(0);
			else
				return lista.get(1);
		}
		else if(lista.size() == 1)
			return lista.get(0);
		else
			return null;
	}
	
	/**
	 * Metodo di lettura dello step successivo a quello passato in input.
	 * <p>
	 * @param configurazione: configurazione corrente
	 * @param primoRiporto: booleano che indica se il creatore dello step è di primo riporto
	 * @param respPrimoRiporto: booleano che indica se il responsabile del creatore dello step è di primo riporto
	 * @param lingua: lingua di ricerca
	 * @return ritorna l'oggetto model ConfigurazioneStepWf popolato con i dati inseriti.
	 * @exception Throwable
	 */
	@Override
	public ConfigurazioneStepWf leggiConfigurazioneSuccessivaStandard(ConfigurazioneStepWf configurazione, boolean primoRiporto, boolean respPrimoRiporto, boolean amministratore, String lingua) throws Throwable {

		DetachedCriteria criteria = DetachedCriteria.forClass( ConfigurazioneStepWf.class )
				.addOrder(Order.asc("nomeStep"));
		criteria.add( Restrictions.isNull("dataFineValidita")) ;
		criteria.createAlias("classeWf", "classeWf");
		criteria.add(Restrictions.eq("classeWf.id", configurazione.getClasseWf().getId()));
		criteria.add( Restrictions.ilike("numeroAzioneStep", '%' + CostantiDAO.SUFF_AVANZAMENTO_WF, MatchMode.ANYWHERE) ) ;
		criteria.add( Restrictions.eq("lang", lingua) ) ;
		
		//se sono in uno step che prevede ricorsione, tra gli step successivi c'è anche il corrente
		if(configurazione.getNomeStep().indexOf(CostantiDAO.ASSEGNAZIONE_RICORSIVA) > 0)
			criteria.add( Restrictions.ge("nomeStep", configurazione.getNomeStep()) ) ;
		
		//altrimenti no
		else
			criteria.add( Restrictions.gt("nomeStep", configurazione.getNomeStep()) ) ;
		
		if(primoRiporto){ //se sono un primo riporto il passo successivo non potrà essere ad un responsabile né ad un primo riporto
			criteria.add( Restrictions.ne("tipoAssegnazione", CostantiDAO.ASSEGNAZIONE_RESPONSABILE));
			criteria.add( Restrictions.ne("tipoAssegnazione", CostantiDAO.ASSEGNAZIONE_PRIMO_RIPORTO));
		}
		
		else if(respPrimoRiporto) // se il mio responsabile è primo riporto il passo successivo non può essere ricorsivo
			criteria.add(Restrictions.not(Restrictions.like("nomeStep", "%ASSEGNAZIONE_RICORSIVA%")));
		
		if(amministratore){ // se fa parte dei verificatori
			if(!respPrimoRiporto){
				criteria.add(Restrictions.not(Restrictions.like("nomeStep", "%ASSEGNAZIONE_RICORSIVA%")));
			}
			if(!primoRiporto){ //se sono un primo riporto il passo successivo non potrà essere ad un responsabile né ad un primo riporto
				criteria.add( Restrictions.ne("tipoAssegnazione", CostantiDAO.ASSEGNAZIONE_RESPONSABILE));
				criteria.add( Restrictions.ne("tipoAssegnazione", CostantiDAO.ASSEGNAZIONE_SEGRETERIA));
				criteria.add( Restrictions.ne("tipoAssegnazione", CostantiDAO.ASSEGNAZIONE_PRIMO_RIPORTO));
			}
		}
		
		List<ConfigurazioneStepWf> lista = getHibernateTemplate().findByCriteria(criteria);	
		
		if(lista.size() > 1 ){
			if(respPrimoRiporto || primoRiporto)
				return lista.get(0);
			else
				return lista.get(1);
		}
		else if(lista.size() == 1)
			return lista.get(0);
		else
			return null;
	}
	
	/**
	 * Metodo di lettura del primo step in base alla classe.
	 * <p>
	 * @param idClasse: identificativo della classe del workflow
	 * @param lingua: lingua di ricerca
	 * @return ritorna l'oggetto model ConfigurazioneStepWf popolato con i dati inseriti.
	 * @exception Throwable
	 */
	@Override
	public ConfigurazioneStepWf leggiConfigurazioneStepNumeroUno(long idClasseWf, String lingua) throws Throwable {
		// TODO Auto-generated method stub
		
		DetachedCriteria criteria = DetachedCriteria.forClass( ConfigurazioneStepWf.class );
		criteria.add( Restrictions.isNull("dataFineValidita")) ;
		criteria.createAlias("classeWf", "classeWf");
		criteria.add(Restrictions.eq("classeWf.id", idClasseWf));
		criteria.add( Restrictions.eq("nomeStep", CostantiDAO.PRIMO_NUMERO_AZIONE) ) ;
		criteria.add( Restrictions.eq("lang", lingua) ) ;
		return (ConfigurazioneStepWf) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
	}
	
	/**
	 * Metodo di lettura del primo step in base alla classe.
	 * <p>
	 * @param id: identificativo della configurazione
	 * @return ritorna l'oggetto model ConfigurazioneStepWf popolato con i dati inseriti.
	 * @exception Throwable
	 */
	@Override
	public ConfigurazioneStepWf leggiConfigurazione(long id) throws Throwable {
		// TODO Auto-generated method stub
		
		DetachedCriteria criteria = DetachedCriteria.forClass( ConfigurazioneStepWf.class );
		criteria.add( Restrictions.eq("id", id)) ;
		return (ConfigurazioneStepWf) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
	}

	/**
	 * Metodo di lettura della configurazione in base a parametri di input.
	 * <p>
	 * @param idClasse: identificativo della classe del workflow
	 * @param stateFrom: step di provenienza
	 * @param stateTo: step di assegnazione
	 * @param direzione: avanzamento o rifiuto
	 * @param lingua: lingua di ricerca
	 * @return ritorna l'oggetto model ConfigurazioneStepWf popolato con i dati inseriti.
	 * @exception Throwable
	 */
	@Override
	public ConfigurazioneStepWf leggiConfigurazioneStep(long idClasseWf, String stateFrom, String stateTo,
			String direzione, String lingua) throws Throwable {

		DetachedCriteria criteria = DetachedCriteria.forClass( ConfigurazioneStepWf.class );
		criteria.add( Restrictions.isNull("dataFineValidita")) ;
		criteria.createAlias("classeWf", "classeWf");
		criteria.add(Restrictions.eq("classeWf.id", idClasseWf));
		criteria.add( Restrictions.eq("stateFrom", stateFrom) ) ;
		criteria.add( Restrictions.eq("stateTo", stateTo) ) ;
		criteria.add( Restrictions.eq("lang", lingua) ) ;
		criteria.add( Restrictions.ilike("numeroAzioneStep", '%' + direzione, MatchMode.ANYWHERE) ) ;
		return (ConfigurazioneStepWf) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
	}

	/**
	 * Metodo di lettura della configurazione nella lingua corrente a partire da quella italiana.
	 * <p>
	 * @param code: codice lingua
	 * @param lingua: lingua di ricerca
	 * @return ritorna l'oggetto model ConfigurazioneStepWf popolato con i dati inseriti.
	 * @exception Throwable
	 */
	@Override
	public ConfigurazioneStepWf leggiConfigurazioneTradotta(String code, String lingua) throws Throwable {

		DetachedCriteria criteria = DetachedCriteria.forClass( ConfigurazioneStepWf.class );
		criteria.add( Restrictions.isNull("dataFineValidita")) ;
		criteria.add( Restrictions.eq("codGruppoLingua", code) ) ;
		criteria.add( Restrictions.eq("lang", lingua) ) ;
		return (ConfigurazioneStepWf) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		

	}
	
	@Override
	public List<ConfigurazioneStepWf> leggiConfigurazioni(long idClasseWf,String idMatricolaUtil,String lingua,String tipoAssegnazione) throws Throwable {

		DetachedCriteria criteria = DetachedCriteria.forClass( ConfigurazioneStepWf.class );
		
		criteria.add( Restrictions.isNull("dataFineValidita")) ;
		
		criteria.add( Restrictions.isNotNull("notificaMailTo")) ;
		
		criteria.createAlias("classeWf", "classeWf");
		criteria.add(Restrictions.eq("classeWf.id", idClasseWf));
		
		if(idMatricolaUtil!=null) {
			criteria.createAlias("utente", "utente");
			criteria.add(Restrictions.eq("utente.matricolaUtil", idMatricolaUtil));
		}
		criteria.add(Restrictions.eq("tipoAssegnazione", tipoAssegnazione));
		criteria.add( Restrictions.eq("lang", lingua) ) ;
		
		criteria.setFetchMode("utente", FetchMode.JOIN);
		
		List<ConfigurazioneStepWf> lista = getHibernateTemplate().findByCriteria(criteria);	
		return lista;
	}
	
	@Override
	public ConfigurazioneStepWf leggiConfigurazione(long idClasseWf,String idMatricolaUtil,String lingua,String tipoAssegnazione) throws Throwable {

		DetachedCriteria criteria = DetachedCriteria.forClass( ConfigurazioneStepWf.class );
		
		criteria.add( Restrictions.isNull("dataFineValidita")) ;
		
		criteria.createAlias("classeWf", "classeWf");
		criteria.add(Restrictions.eq("classeWf.id", idClasseWf));
		
		if(idMatricolaUtil!=null) {
			criteria.createAlias("utente", "utente");
			criteria.add(Restrictions.eq("utente.matricolaUtil", idMatricolaUtil));
		}
		criteria.add(Restrictions.eq("tipoAssegnazione", tipoAssegnazione));
		criteria.add( Restrictions.eq("lang", lingua) ) ;
		
		criteria.setFetchMode("utente", FetchMode.JOIN);
		
		return (ConfigurazioneStepWf) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
	}
	
	@Override
	public ConfigurazioneStepWf leggiAutorizzatore() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( ConfigurazioneStepWf.class );
		criteria.add( Restrictions.isNull("dataFineValidita")) ;
		
		criteria.createAlias("classeWf", "classeWf");
		criteria.add(Restrictions.eq("classeWf.id", (long)1));
		
		criteria.createAlias("utente", "utente");

		criteria.add(Restrictions.eq("tipoAssegnazione", CostantiDAO.ASSEGNAZIONE_MANUALE));
		criteria.add( Restrictions.eq("lang", CostantiDAO.LINGUA_ITALIANA) ) ;
		criteria.add(Restrictions.eq("stateTo", CostantiDAO.INCARICO_STATO_ATTESA_AUTORIZZAZIONE));
		criteria.setFetchMode("utente", FetchMode.JOIN);
		
		return (ConfigurazioneStepWf) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
	}
	
	@Override
	public ConfigurazioneStepWf leggiAutorizzatoreRigaIdWf(long numWf) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( ConfigurazioneStepWf.class );
		criteria.add( Restrictions.isNull("dataFineValidita")) ;
		
		criteria.createAlias("classeWf", "classeWf");
		criteria.add(Restrictions.eq("classeWf.id", (long)numWf));
		
		criteria.createAlias("utente", "utente");

		criteria.add(Restrictions.eq("tipoAssegnazione", CostantiDAO.ASSEGNAZIONE_MANUALE));
		criteria.add( Restrictions.eq("lang", CostantiDAO.LINGUA_ITALIANA) ) ;
		criteria.add(Restrictions.eq("stateTo", CostantiDAO.INCARICO_STATO_ATTESA_AUTORIZZAZIONE));
		criteria.setFetchMode("utente", FetchMode.JOIN);
		
		ConfigurazioneStepWf cswf= (ConfigurazioneStepWf) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );
		return cswf;
	}
	
	@Override
	public ConfigurazioneStepWf leggiApprovatoreRigaIdWf(long numWf) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( ConfigurazioneStepWf.class );
		criteria.add( Restrictions.isNull("dataFineValidita")) ;
		
		criteria.createAlias("classeWf", "classeWf");
		criteria.add(Restrictions.eq("classeWf.id", (long)numWf));
		
		criteria.createAlias("utente", "utente");

		criteria.add(Restrictions.eq("tipoAssegnazione", CostantiDAO.ASSEGNAZIONE_MANUALE));
		criteria.add( Restrictions.eq("lang", CostantiDAO.LINGUA_ITALIANA) ) ;
		criteria.add(Restrictions.eq("stateTo", CostantiDAO.IN_ATTESA_DI_APPROVAZIONE_IN_SECONDA_FIRMA));
		criteria.setFetchMode("utente", FetchMode.JOIN);
		
		ConfigurazioneStepWf cswf= (ConfigurazioneStepWf) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );
		return cswf;
	}
	
	@Override
	public ConfigurazioneStepWf leggiApprovatore() throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( ConfigurazioneStepWf.class );
		criteria.add( Restrictions.isNull("dataFineValidita")) ;
		
		criteria.createAlias("classeWf", "classeWf");
		criteria.add(Restrictions.eq("classeWf.id", (long)1));
		
		criteria.createAlias("utente", "utente");

		criteria.add(Restrictions.eq("tipoAssegnazione", CostantiDAO.ASSEGNAZIONE_MANUALE));
		criteria.add( Restrictions.eq("lang", CostantiDAO.LINGUA_ITALIANA) ) ;
		criteria.add(Restrictions.eq("stateTo", CostantiDAO.IN_ATTESA_DI_APPROVAZIONE_IN_SECONDA_FIRMA));
		
		criteria.setFetchMode("utente", FetchMode.JOIN);
		
		return (ConfigurazioneStepWf) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
	}
	
	@Override
	public void aggiornaMatricolaById(Long id, String matricola) throws Throwable {
		DataSource ds = null;
		Connection c = null;
		PreparedStatement st = null;
		try {
			String query = "UPDATE CONFIGURAZIONE_STEP_WF SET ID_MATRICOLA_UTIL = '"+ matricola +"' WHERE id = "+id ;
			ds = (DataSource) SpringUtil.getBean("dataSource");
			c = ds.getConnection();
			st = c.prepareStatement(query);
			st.executeUpdate();
		} catch(Throwable e) {
			e.printStackTrace();
		} finally {
			try { if( st != null ) st.close();} catch(Throwable e){}
			try { if( c != null ) c.close();} catch(Throwable e){}
		}
	}
		
	@Override
	public void aggiornaEmailById(Long id, String email) throws Throwable {
		DataSource ds = null;
		Connection c = null;
		PreparedStatement st = null;
		try {
			String query = "UPDATE CONFIGURAZIONE_STEP_WF SET NOTIFICA_MAIL_TO = '"+ email +"' WHERE id = "+id ;
			ds = (DataSource) SpringUtil.getBean("dataSource");
			c = ds.getConnection();
			st = c.prepareStatement(query);
			st.executeUpdate();
		} catch(Throwable e) {
			e.printStackTrace();
		} finally {
			try { if( st != null ) st.close();} catch(Throwable e){}
			try { if( c != null ) c.close();} catch(Throwable e){}
		}
	}
}
