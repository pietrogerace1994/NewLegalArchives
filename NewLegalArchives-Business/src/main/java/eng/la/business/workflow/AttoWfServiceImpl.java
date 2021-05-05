package eng.la.business.workflow;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import eng.la.business.BaseService;
import eng.la.model.AbstractEntity;
import eng.la.model.Atto;
import eng.la.model.AttoWf;
import eng.la.model.ClasseWf;
import eng.la.model.ConfigurazioneStepWf;
import eng.la.model.StatoAtto;
import eng.la.model.StepWf;
import eng.la.model.Utente;
import eng.la.model.view.AttoWfView;
import eng.la.persistence.AnagraficaStatiTipiDAO;
import eng.la.persistence.AttoDAO;
import eng.la.persistence.CostantiDAO;
import eng.la.persistence.UtenteDAO;
import eng.la.persistence.workflow.AttoWfDAO;
import eng.la.persistence.workflow.ConfigurazioneStepWfDAO;
import eng.la.persistence.workflow.StepWfDAO;
import eng.la.util.exceptions.LAException;

/**
 * <h1>Classe di business AttoWfServiceImpl </h1>
 * Classe preposta alla gestione delle operazione di scrittura
 * lettura sulla base dati attraverso l'uso delle classi DAO 
 * di pertinenza all'operazione.
 * <p>
 * @author Silvana Di Perna
 * @version 1.0
 * @since 2016-07-18
 */
@Service("attoWfService")
public class AttoWfServiceImpl extends BaseService implements AttoWfService {
	
	@Autowired
	private AnagraficaStatiTipiDAO anagraficaStatiTipiDAO;
 
	public AnagraficaStatiTipiDAO getAnagraficaStatiTipiDAO() {
		return anagraficaStatiTipiDAO;
	}
	
	@Autowired
	private ConfigurazioneStepWfDAO configurazioneStepWfDAO;
 
	public ConfigurazioneStepWfDAO getConfigurazioneStepWfDAO() {
		return configurazioneStepWfDAO;
	}
	
	@Autowired
	private AttoWfDAO attoWfDAO;
 
	public AttoWfDAO getAttoWfDAO() {
		return attoWfDAO;
	}
	
	@Autowired
	private StepWfDAO stepWfDAO;
 
	public StepWfDAO getStepWfDAO() {
		return stepWfDAO;
	}
	
	@Autowired
	private AttoDAO attoDAO;
 
	public AttoDAO getAttoDAO() {
		return attoDAO;
	}
	
	@Autowired
	private UtenteDAO utenteDAO;
 
	public UtenteDAO getUtenteDAO() {
		return utenteDAO;
	}
	
	@Override
	protected Class<AttoWf> leggiClassVO() {
		return AttoWf.class;
	}

	@Override
	protected Class<AttoWfView> leggiClassView() {
		return AttoWfView.class;
	}

	/**
	 * Crea un nuovo workflow e lo pone in stato "IN CORSO"
	 *
	 * @param idAtto identificativo dell'atto correlato
	 * @param matricolaUtenteAssegnatario matricola dell'utente assegnatario
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean avviaWorkflow(long idAtto, String matricolaUtenteAssegnatario, String userIdUtenteConnesso) throws Throwable {
		
		Timestamp dataCreazione = new Timestamp(System.currentTimeMillis());
		
		AttoWf attoWf = null;
		ClasseWf classeWf = null;
		ConfigurazioneStepWf configurazione = null;
		AttoWf attoWfSalvato = null;
		Atto atto = null;
		Utente utenteConnesso = null;
		
		//recupero l'atto
		atto = attoDAO.leggi(idAtto);
		
		/**
		 * Se l'atto è nello stato validare, lo cambio in stato bozza per eseguire 
		 * il normale iter di assegnamento
		 * @author MASSIMO CARUSO
		 */
		if(atto.getStatoAtto().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.ATTO_STATO_DA_VALIDARE)){
			StatoAtto to_set = attoDAO.getStatoAttoPerLingua("IT", CostantiDAO.ATTO_STATO_BOZZA);
			atto.setStatoAtto(to_set);
			attoDAO.modifica(atto);
		}
		
		//se l'atto non è in stato Bozza genero l'eccezione
		if(!atto.getStatoAtto().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.ATTO_STATO_BOZZA))
			throw new LAException("Lo stato " + atto.getStatoAtto().getDescrizione() + " dell'atto non è compatibile con l'operazione richiesta", "errore.proforma.stato");

		
		//recupero l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);

		//creo l'occorrenza AttoWf
		attoWf = new AttoWf();
		attoWf.setAtto(atto);
		attoWf.setUtenteCreazione(userIdUtenteConnesso);
		attoWf.setDataCreazione(dataCreazione);
		attoWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_IN_CORSO, CostantiDAO.LINGUA_ITALIANA));
		attoWf.setUtenteAssegnatario(matricolaUtenteAssegnatario);
		attoWf.setLang(CostantiDAO.LINGUA_ITALIANA);
		
		classeWf =  anagraficaStatiTipiDAO.leggiClasseWf(CostantiDAO.REGISTRAZIONE_ATTO);	
		
		//recupero lo step di partenza
		configurazione = configurazioneStepWfDAO.leggiConfigurazioneStepNumeroUno(classeWf.getId(), "IT");
		
		//creo l'istanza di workflow
		attoWfSalvato = attoWfDAO.avviaWorkflow(attoWf);  
		
		//creo lo step
		creaStepWf(attoWfSalvato,configurazione, dataCreazione,utenteConnesso, StringUtils.EMPTY);
		
		
		//aggiorno l'atto
		atto.setStatoAtto(anagraficaStatiTipiDAO.leggiStatoAtto(configurazione.getStateTo(), CostantiDAO.LINGUA_ITALIANA));
		atto.setDataUltimaModifica(dataCreazione);

		atto.setOperation(AbstractEntity.UPDATE_OPERATION);
		atto.setOperationTimestamp(new Date());
		attoDAO.modifica(atto);
		
		return attoWfSalvato.getStatoWf().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.STATO_WF_IN_CORSO);
	}
	
	/**
	 * Chiude il workflow e pone l'atto in stato registrato o inviato ad altri uffici
	 *
	 * @param idAttoWf identificativo del workflow
	 * @param stateTo stato identificativo (registrato o inviato ad altri uffici)
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'avanzamento è andato a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean chiudiAtto(long idAttoWf, String stateTo,String userIdUtenteConnesso) throws Throwable {

		Timestamp dataRegistrazione = new Timestamp(System.currentTimeMillis());
		AttoWf attoWf = null;
		StepWf stepCorrente = null;
		Atto attoCorrelato = null;
		Utente utenteConnesso = null;
		
		//recupero il workflow
		attoWf = attoWfDAO.leggiWorkflow(idAttoWf);
		
		//se il workflow non è in stato In corso genero l'eccezione
		if(!attoWf.getStatoWf().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.STATO_WF_IN_CORSO))
			throw new LAException("Lo stato " + attoWf.getStatoWf().getDescrizione() + " del workflow non è compatibile con l'operazione richiesta", "errore.workflow.stato");


		
		//recupero l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
		
		
		// recupero lo step corrente
		stepCorrente = stepWfDAO.leggiStepCorrente(idAttoWf, CostantiDAO.REGISTRAZIONE_ATTO);
		
		//aggiorno l'occorrenza AttoWf
		attoWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_COMPLETATO, CostantiDAO.LINGUA_ITALIANA));
		attoWf.setDataChiusura(dataRegistrazione);
		
		attoWfDAO.modifica(attoWf);
		
		//chiudo lo step corrente
		stepCorrente.setDataChiusura(dataRegistrazione);
		stepCorrente.setUtenteChiusura(utenteConnesso.getMatricolaUtil());
		stepWfDAO.modifica(stepCorrente);
		
		//recupero l'atto
		attoCorrelato = attoWf.getAtto();
		attoCorrelato.setStatoAtto(anagraficaStatiTipiDAO.leggiStatoAtto(stateTo, CostantiDAO.LINGUA_ITALIANA));
		attoCorrelato.setDataUltimaModifica(dataRegistrazione);
		if(stateTo.equals(CostantiDAO.ATTO_STATO_REGISTRATO)){
			attoCorrelato.setDataRegistrazione(dataRegistrazione);
			attoCorrelato.setOwner(userIdUtenteConnesso);
		}
		attoCorrelato.setOperation(AbstractEntity.UPDATE_OPERATION);
		attoCorrelato.setOperationTimestamp(new Date());
		attoDAO.modifica(attoCorrelato);
		
		return attoWf.getStatoWf().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.STATO_WF_COMPLETATO);
	}
	
	/**
	  * Fa avanzare un workflow 
	 *
	 * @param idAttoWf identificativo del workflow
	 * @param matricolaAssegnatario matricola dell'utente assegnatario
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'avanzamento è andato a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean avanzaWorkflow(long idAttoWf, String matricolaAssegnatario, String userIdUtenteConnesso) throws Throwable {
		
		//aggiorno l'occorrenza ProformaWf
		Timestamp dataAvanzamento = new Timestamp(System.currentTimeMillis());
		
		boolean responsabileFoglia = false;
		boolean modificaStatoAtto = false;
		boolean modificaConfigurazione = false;
		
		AttoWf attoWf = null;
		Atto attoCorrelato = null;
		StepWf stepCorrente = null;
		Utente utenteConnesso = null;

		ConfigurazioneStepWf configurazioneStart = null;
		ConfigurazioneStepWf configurazione = null;
		
		String stateTo = StringUtils.EMPTY;
		
		attoWf = attoWfDAO.leggiWorkflow(idAttoWf);
		
		//se il workflow non è in stato In corso genero l'eccezione
		if(!attoWf.getStatoWf().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.STATO_WF_IN_CORSO))
			throw new LAException("Lo stato " + attoWf.getStatoWf().getDescrizione() + " del workflow non è compatibile con l'operazione richiesta", "errore.workflow.stato");

		
		//recupero l'atto
		attoCorrelato = attoWf.getAtto();
		
		// recupero lo step corrente
		stepCorrente = stepWfDAO.leggiStepCorrente(idAttoWf, CostantiDAO.REGISTRAZIONE_ATTO);
		
		//recupero l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
		
		//chiudo lo step corrente
		stepCorrente.setDataChiusura(dataAvanzamento);
		stepCorrente.setUtenteChiusura(utenteConnesso.getMatricolaUtil());
		stepWfDAO.modifica(stepCorrente);
				
		
		//recupero la configurazione attuale
		configurazioneStart = configurazioneStepWfDAO.leggiConfigurazione(stepCorrente.getConfigurazioneStepWf().getId());
		
		// recupero la profilazione dell'utente connesso
		responsabileFoglia = utenteDAO.leggiSeResponsabileFoglia(utenteConnesso.getMatricolaUtil());
		
		//calcolo lo step da mandare in assegnazione
		switch(configurazioneStart.getStateTo()){
			
			case CostantiDAO.ATTO_STATO_ATTESA_REGISTRAZIONE:
				
				if(responsabileFoglia){ //caso in cui un general councel non abbia sottoposti responsabili
					configurazione = configurazioneStepWfDAO.leggiConfigurazioneStep(configurazioneStart.getClasseWf().getId(), CostantiDAO.ATTO_STATO_ATTESA_PRESA_IN_CARICO, CostantiDAO.ATTO_STATO_ATTESA_PRESA_IN_CARICO_LEG, CostantiDAO.SUFF_AVANZAMENTO_WF, CostantiDAO.LINGUA_ITALIANA);
					stateTo = CostantiDAO.ATTO_STATO_ATTESA_PRESA_IN_CARICO_LEG;
				}
				else{
					configurazione = configurazioneStepWfDAO.leggiConfigurazioneStep(configurazioneStart.getClasseWf().getId(), CostantiDAO.ATTO_STATO_ATTESA_REGISTRAZIONE, CostantiDAO.ATTO_STATO_ATTESA_PRESA_IN_CARICO, CostantiDAO.SUFF_AVANZAMENTO_WF, CostantiDAO.LINGUA_ITALIANA);
					stateTo = CostantiDAO.ATTO_STATO_ATTESA_PRESA_IN_CARICO;
				}
				modificaStatoAtto = true;
				modificaConfigurazione = true;
				break;
				
			case CostantiDAO.ATTO_STATO_ATTESA_PRESA_IN_CARICO:
				if(responsabileFoglia){
					configurazione = configurazioneStepWfDAO.leggiConfigurazioneStep(configurazioneStart.getClasseWf().getId(), CostantiDAO.ATTO_STATO_ATTESA_PRESA_IN_CARICO, CostantiDAO.ATTO_STATO_ATTESA_PRESA_IN_CARICO_LEG, CostantiDAO.SUFF_AVANZAMENTO_WF, CostantiDAO.LINGUA_ITALIANA);
					stateTo = CostantiDAO.ATTO_STATO_ATTESA_PRESA_IN_CARICO_LEG;
					modificaStatoAtto = true;
					modificaConfigurazione = true;
				
				} 
//				else{//non cambiano né stato dell'atto, né configurazione
					
				break;
				
			case CostantiDAO.ATTO_STATO_BOZZA:
				configurazione = configurazioneStepWfDAO.leggiConfigurazioneStep(configurazioneStart.getClasseWf().getId(), CostantiDAO.ATTO_STATO_BOZZA, CostantiDAO.ATTO_STATO_ATTESA_REGISTRAZIONE, CostantiDAO.SUFF_AVANZAMENTO_WF, CostantiDAO.LINGUA_ITALIANA);
				stateTo = CostantiDAO.ATTO_STATO_ATTESA_REGISTRAZIONE;
				
				modificaStatoAtto = true;
				modificaConfigurazione = true;
				break;
			default:
				break;

		}	
		//creo lo step
		if(modificaConfigurazione)
			creaStepWf(attoWf,configurazione, dataAvanzamento, utenteConnesso, StringUtils.EMPTY);
		else
			creaStepWf(attoWf,configurazioneStart, dataAvanzamento, utenteConnesso, StringUtils.EMPTY);

		//aggiorno l'atto se modificato
		if(modificaStatoAtto){
			attoCorrelato.setStatoAtto(anagraficaStatiTipiDAO.leggiStatoAtto(stateTo, CostantiDAO.LINGUA_ITALIANA));
			//attoCorrelato.setDataUltimaModifica(dataAvanzamento);

			attoCorrelato.setOperation(AbstractEntity.UPDATE_OPERATION);
			attoCorrelato.setOperationTimestamp(new Date());
			attoDAO.modifica(attoCorrelato);
		}
		
		//aggiorno l'occorrenza AttoWf
		attoWf.setUtenteAssegnatario(matricolaAssegnatario);
		attoWfDAO.modifica(attoWf);
		
		return (stepCorrente.getDataChiusura() != null);
	}
	
	/**
	  * Fa retrocedere un workflow 
	 *
	 * @param idAttoWf identificativo del workflow
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @param motivoRifiuto motivo del rifiuto
	 * @return true se il rifiuto è andato a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean rifiutaWorkflow(long idAttoWf, String userIdUtenteConnesso, String motivoRifiuto) throws Throwable {
		
		//aggiorno l'occorrenza AttoWf
		Timestamp dataAvanzamento = new Timestamp(System.currentTimeMillis());
		
		boolean primoRiporto = false;
		boolean respPrimoRiporto = false;

		boolean modificaStatoAtto = false;
		boolean modificaConfigurazione = false;
		boolean chiudiAtto = false;
		
		AttoWf attoWf = null;
		Atto attoCorrelato = null;
		StepWf stepCorrente = null;
		Utente responsabileTop = null;
		Utente responsabileDiretto = null;
		Utente utenteConnesso = null;
		ConfigurazioneStepWf configurazioneStart = null;
		ConfigurazioneStepWf configurazione = null;
		
		String stateTo = StringUtils.EMPTY;
		
		attoWf = attoWfDAO.leggiWorkflow(idAttoWf);
		
		//se il workflow non è in stato In corso genero l'eccezione
		if(!attoWf.getStatoWf().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.STATO_WF_IN_CORSO))
			throw new LAException("Lo stato " + attoWf.getStatoWf().getDescrizione() + " del workflow non è compatibile con l'operazione richiesta", "errore.workflow.stato");

		
		//recupero l'atto
		attoCorrelato = attoWf.getAtto();
		
		// recupero lo step corrente
		stepCorrente = stepWfDAO.leggiStepCorrente(idAttoWf, CostantiDAO.REGISTRAZIONE_ATTO);
		
		//recupero l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
		
		// recupero la profilazione dell'utente connesso
		responsabileTop = utenteDAO.leggiResponsabileTop();
					
		if(utenteConnesso.getMatricolaRespUtil().equalsIgnoreCase(responsabileTop.getMatricolaUtil())){
				primoRiporto = true;
				respPrimoRiporto = true;
		}
		else{
			responsabileDiretto = utenteDAO.leggiUtenteDaMatricola(utenteConnesso.getMatricolaRespUtil());
			if(responsabileDiretto.getMatricolaRespUtil().equalsIgnoreCase(responsabileTop.getMatricolaUtil()))
				respPrimoRiporto = true;
		}
		
		//chiudo lo step corrente
		stepCorrente.setDataChiusura(dataAvanzamento);
		stepCorrente.setUtenteChiusura(utenteConnesso.getMatricolaUtil());
		stepCorrente.setMotivoRifiuto(motivoRifiuto);
		stepWfDAO.modifica(stepCorrente);
				
		
		//recupero la configurazione attuale
		configurazioneStart = configurazioneStepWfDAO.leggiConfigurazione(stepCorrente.getConfigurazioneStepWf().getId());
		
		
		
		if(utenteConnesso.getMatricolaRespUtil().equalsIgnoreCase(responsabileTop.getMatricolaUtil()))
			primoRiporto = true;

		//calcolo lo step da mandare in assegnazione
		switch(configurazioneStart.getStateTo()){
			case CostantiDAO.ATTO_STATO_ATTESA_REGISTRAZIONE:
				chiudiAtto = true;
				break;

			case CostantiDAO.ATTO_STATO_ATTESA_PRESA_IN_CARICO:
				if(primoRiporto){//lo step corretto dovrebbe essere di attesa registrazione, quindi lo step di rifiuto va in bozza
								 //evidentemente c'è stato un cambio di gerarchia
					
					configurazione = configurazioneStepWfDAO.leggiConfigurazioneStep(configurazioneStart.getClasseWf().getId(), CostantiDAO.ATTO_STATO_ATTESA_REGISTRAZIONE, CostantiDAO.ATTO_STATO_BOZZA, CostantiDAO.SUFF_RIFIUTO_WF, CostantiDAO.LINGUA_ITALIANA);
					stateTo = CostantiDAO.ATTO_STATO_BOZZA;
					modificaStatoAtto = true;
					modificaConfigurazione = true;
					break;
				} 
				else if(respPrimoRiporto){//il responsabile è un General Counsel, quindi lo step torna in stato di attesa registrazione

					configurazione = configurazioneStepWfDAO.leggiConfigurazioneStep(configurazioneStart.getClasseWf().getId(), CostantiDAO.ATTO_STATO_ATTESA_PRESA_IN_CARICO, CostantiDAO.ATTO_STATO_ATTESA_REGISTRAZIONE, CostantiDAO.SUFF_RIFIUTO_WF, CostantiDAO.LINGUA_ITALIANA);
					stateTo = CostantiDAO.ATTO_STATO_ATTESA_REGISTRAZIONE;
					modificaStatoAtto = true;
					modificaConfigurazione = true;
					break;
				}
			case CostantiDAO.ATTO_STATO_ATTESA_PRESA_IN_CARICO_LEG:
				if(respPrimoRiporto){//il responsabile è un General Counsel, quindi lo step torna in stato di attesa registrazione

					configurazione = configurazioneStepWfDAO.leggiConfigurazioneStep(configurazioneStart.getClasseWf().getId(), CostantiDAO.ATTO_STATO_ATTESA_PRESA_IN_CARICO, CostantiDAO.ATTO_STATO_ATTESA_REGISTRAZIONE, CostantiDAO.SUFF_RIFIUTO_WF, CostantiDAO.LINGUA_ITALIANA);
					stateTo = CostantiDAO.ATTO_STATO_ATTESA_REGISTRAZIONE;
					modificaStatoAtto = true;
					modificaConfigurazione = true;
					
				}
				else{
					configurazione = configurazioneStepWfDAO.leggiConfigurazioneStep(configurazioneStart.getClasseWf().getId(), CostantiDAO.ATTO_STATO_ATTESA_PRESA_IN_CARICO_LEG, CostantiDAO.ATTO_STATO_ATTESA_PRESA_IN_CARICO, CostantiDAO.SUFF_RIFIUTO_WF, CostantiDAO.LINGUA_ITALIANA);
					stateTo = CostantiDAO.ATTO_STATO_ATTESA_PRESA_IN_CARICO;
					modificaStatoAtto = true;
					modificaConfigurazione = true;
				}
//				else{//altrimenti non cambia né lo stato dell'atto, né la configurazione dello step

					
				break;
	
			default:
				break;

		}
		// creo lo step
		// traccio anche nello step corrente il motivo rifiuto per poi svuotarlo (in caso di successiva approvazione)
		// o sovrascriverlo (in caso di successivo ulteriore rifiuto)
		if(chiudiAtto){
			//aggiorno l'occorrenza AttoWf
			attoWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_RIFIUTATO, CostantiDAO.LINGUA_ITALIANA));
			attoWf.setDataChiusura(dataAvanzamento);
			attoWfDAO.modifica(attoWf);
			
			//aggiorno l'atto
			attoCorrelato.setStatoAtto(anagraficaStatiTipiDAO.leggiStatoAtto(CostantiDAO.ATTO_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA));
			//attoCorrelato.setDataUltimaModifica(dataAvanzamento);

			attoCorrelato.setOperation(AbstractEntity.UPDATE_OPERATION);
			attoCorrelato.setOperationTimestamp(new Date());
			attoDAO.modifica(attoCorrelato);
		}
		else
		{
			if(modificaConfigurazione) 
				creaStepWf(attoWf,configurazione, dataAvanzamento, utenteConnesso, motivoRifiuto);
			else
				creaStepWf(attoWf,configurazioneStart, dataAvanzamento, utenteConnesso, motivoRifiuto);
	
			//aggiorno l'atto se modificato
			if(modificaStatoAtto){
				attoCorrelato.setStatoAtto(anagraficaStatiTipiDAO.leggiStatoAtto(stateTo, CostantiDAO.LINGUA_ITALIANA));
				attoCorrelato.setDataUltimaModifica(dataAvanzamento);

				attoCorrelato.setOperation(AbstractEntity.UPDATE_OPERATION);
				attoCorrelato.setOperationTimestamp(new Date());
				attoDAO.modifica(attoCorrelato);
			}
		}
		
		return (stepCorrente.getDataChiusura() != null);
	}
	
	/**
	  * Fa retrocedere un workflow 
	 *
	 * @param idAttoWf identificativo del workflow
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @param motivoRifiuto motivo del rifiuto
	 * @return true se il rifiuto è andato a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
//	@Override
	public boolean rifiutaWorkflow_old(long idAttoWf, String userIdAssegnatario, String userIdUtenteConnesso, String motivoRifiuto) throws Throwable {
		
		//aggiorno l'occorrenza AttoWf
		Timestamp dataAvanzamento = new Timestamp(System.currentTimeMillis());
		
		boolean primoRiporto = false;
		boolean respPrimoRiporto = false;

		boolean modificaStatoAtto = false;
		boolean modificaConfigurazione = false;
		
		AttoWf attoWf = null;
		Atto attoCorrelato = null;
		StepWf stepCorrente = null;
		Utente responsabileTop = null;
		Utente responsabileDiretto = null;
		Utente utenteConnesso = null;
		ConfigurazioneStepWf configurazioneStart = null;
		ConfigurazioneStepWf configurazione = null;
		
		String stateTo = StringUtils.EMPTY;
		
		attoWf = attoWfDAO.leggiWorkflow(idAttoWf);
		
		//recupero l'atto
		attoCorrelato = attoWf.getAtto();
		
		// recupero lo step corrente
		stepCorrente = stepWfDAO.leggiStepCorrente(idAttoWf, CostantiDAO.REGISTRAZIONE_ATTO);
		
		//recupero l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
		
		// recupero la profilazione dell'utente connesso
		responsabileTop = utenteDAO.leggiResponsabileTop();
					
		if(utenteConnesso.getMatricolaRespUtil().equalsIgnoreCase(responsabileTop.getMatricolaUtil())){
				primoRiporto = true;
				respPrimoRiporto = true;
		}
		else{
			responsabileDiretto = utenteDAO.leggiUtenteDaMatricola(utenteConnesso.getMatricolaRespUtil());
			if(responsabileDiretto.getMatricolaRespUtil().equalsIgnoreCase(responsabileTop.getMatricolaUtil()))
				respPrimoRiporto = true;
		}
		
		//chiudo lo step corrente
		stepCorrente.setDataChiusura(dataAvanzamento);
		stepCorrente.setUtenteChiusura(utenteConnesso.getMatricolaUtil());
		stepCorrente.setMotivoRifiuto(motivoRifiuto);
		stepWfDAO.modifica(stepCorrente);
				
		
		//recupero la configurazione attuale
		configurazioneStart = configurazioneStepWfDAO.leggiConfigurazione(stepCorrente.getConfigurazioneStepWf().getId());
		
		
		
		if(utenteConnesso.getMatricolaRespUtil().equalsIgnoreCase(responsabileTop.getMatricolaUtil()))
			primoRiporto = true;

		//calcolo lo step da mandare in assegnazione
		switch(configurazioneStart.getStateTo()){
			case CostantiDAO.ATTO_STATO_ATTESA_REGISTRAZIONE:
				configurazione = configurazioneStepWfDAO.leggiConfigurazioneStep(configurazioneStart.getClasseWf().getId(), CostantiDAO.ATTO_STATO_ATTESA_REGISTRAZIONE, CostantiDAO.ATTO_STATO_BOZZA, CostantiDAO.SUFF_RIFIUTO_WF, CostantiDAO.LINGUA_ITALIANA);
				stateTo = CostantiDAO.ATTO_STATO_BOZZA;
				modificaStatoAtto = true;
				modificaConfigurazione = true;

			case CostantiDAO.ATTO_STATO_ATTESA_PRESA_IN_CARICO:
				if(primoRiporto){//lo step corretto dovrebbe essere di attesa registrazione, quindi lo step di rifiuto va in bozza
								 //evidentemente c'è stato un cambio di gerarchia
					
					configurazione = configurazioneStepWfDAO.leggiConfigurazioneStep(configurazioneStart.getClasseWf().getId(), CostantiDAO.ATTO_STATO_ATTESA_REGISTRAZIONE, CostantiDAO.ATTO_STATO_BOZZA, CostantiDAO.SUFF_RIFIUTO_WF, CostantiDAO.LINGUA_ITALIANA);
					stateTo = CostantiDAO.ATTO_STATO_BOZZA;
					modificaStatoAtto = true;
					modificaConfigurazione = true;
				} 
				else if(respPrimoRiporto){//il responsabile è un General Counsel, quindi lo step torna in stato di attesa registrazione

					configurazione = configurazioneStepWfDAO.leggiConfigurazioneStep(configurazioneStart.getClasseWf().getId(), CostantiDAO.ATTO_STATO_ATTESA_PRESA_IN_CARICO, CostantiDAO.ATTO_STATO_ATTESA_REGISTRAZIONE, CostantiDAO.SUFF_RIFIUTO_WF, CostantiDAO.LINGUA_ITALIANA);
					stateTo = CostantiDAO.ATTO_STATO_ATTESA_REGISTRAZIONE;
					modificaStatoAtto = true;
					modificaConfigurazione = true;
				}
			case CostantiDAO.ATTO_STATO_ATTESA_PRESA_IN_CARICO_LEG:
				if(respPrimoRiporto){//il responsabile è un General Counsel, quindi lo step torna in stato di attesa registrazione

					configurazione = configurazioneStepWfDAO.leggiConfigurazioneStep(configurazioneStart.getClasseWf().getId(), CostantiDAO.ATTO_STATO_ATTESA_PRESA_IN_CARICO, CostantiDAO.ATTO_STATO_ATTESA_REGISTRAZIONE, CostantiDAO.SUFF_RIFIUTO_WF, CostantiDAO.LINGUA_ITALIANA);
					stateTo = CostantiDAO.ATTO_STATO_ATTESA_REGISTRAZIONE;
					modificaStatoAtto = true;
					modificaConfigurazione = true;
				}
				else{
					configurazione = configurazioneStepWfDAO.leggiConfigurazioneStep(configurazioneStart.getClasseWf().getId(), CostantiDAO.ATTO_STATO_ATTESA_PRESA_IN_CARICO_LEG, CostantiDAO.ATTO_STATO_ATTESA_PRESA_IN_CARICO, CostantiDAO.SUFF_RIFIUTO_WF, CostantiDAO.LINGUA_ITALIANA);
					stateTo = CostantiDAO.ATTO_STATO_ATTESA_PRESA_IN_CARICO;
					modificaStatoAtto = true;
					modificaConfigurazione = true;
				}
//				else{//altrimenti non cambia né lo stato dell'atto, né la configurazione dello step

					
				break;
	
			default:
				break;

		}
		// creo lo step
		// traccio anche nello step corrente il motivo rifiuto per poi svuotarlo (in caso di successiva approvazione)
		// o sovrascriverlo (in caso di successivo ulteriore rifiuto)
		if(modificaConfigurazione) 
			creaStepWf(attoWf,configurazione, dataAvanzamento, utenteConnesso, motivoRifiuto);
		else
			creaStepWf(attoWf,configurazioneStart, dataAvanzamento, utenteConnesso, motivoRifiuto);

		//aggiorno l'atto se modificato
		if(modificaStatoAtto){
			attoCorrelato.setStatoAtto(anagraficaStatiTipiDAO.leggiStatoAtto(stateTo, CostantiDAO.LINGUA_ITALIANA));
			attoCorrelato.setDataUltimaModifica(dataAvanzamento);

			attoCorrelato.setOperation(AbstractEntity.UPDATE_OPERATION);
			attoCorrelato.setOperationTimestamp(new Date());
			attoDAO.modifica(attoCorrelato);
		}
		
		//aggiorno l'occorrenza AttoWf
		attoWf.setUtenteAssegnatario(userIdAssegnatario);
		attoWfDAO.modifica(attoWf);
		
		return (stepCorrente.getDataChiusura() != null);
	}
	
	
	
	/**
	 * Crea un nuovo steo workflow
	 *
	 * @param attoWf workflow correlato
	 * @param configurazioneStepWf configurazione associata
	 * @param dataCorrente data corrente
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @throws Throwable eccezione
	 */
	private void creaStepWf(AttoWf attoWf, ConfigurazioneStepWf configurazioneStepWf, Timestamp dataCorrente, Utente utenteConnesso, String motivazioneRifiuto) throws Throwable {
		StepWf stepWf = new StepWf();
		stepWf.setDataCreazione(dataCorrente);
		stepWf.setConfigurazioneStepWf(configurazioneStepWf);
		stepWf.setAttoWf(attoWf);
		stepWf.setUtente(utenteConnesso);
		stepWf.setLang(CostantiDAO.LINGUA_ITALIANA);
		if(!motivazioneRifiuto.equals(StringUtils.EMPTY))
			stepWf.setMotivoRifiutoStepPrecedente(motivazioneRifiuto);
		stepWfDAO.creaStepWf(stepWf);
	}

	/**
	 * Metodo di lettura del workflow in corso
	 * <p>
	 * @param idAtto: identificativo dell'atto
	 * @return oggetto AttoWfView.
	 * @exception Throwable
	 */
	@Override
	public AttoWfView leggiWorkflowInCorso(long idAtto) throws Throwable {
		AttoWf wf = attoWfDAO.leggiWorkflowInCorso(idAtto);
		if(wf != null)
			return (AttoWfView) convertiVoInView(wf);
		else
			return null;
	}
	
	/**
	 * Metodo di lettura dell'ultimo workflow terminato
	 * <p>
	 * @param idAtto: identificativo dell'atto
	 * @return oggetto AttoWfView.
	 * @exception Throwable
	 */
	@Override
	public AttoWfView leggiUltimoWorkflowTerminato(long idAtto) throws Throwable {
		AttoWf wf = attoWfDAO.leggiUltimoWorkflowTerminato(idAtto);
		if(wf != null)
		return (AttoWfView) convertiVoInView(wf);
		else
			return null;
	}
	
	/**
	 * Metodo di lettura dell'ultimo workflow rifiutato
	 * <p>
	 * @param idAtto: identificativo dell'atto
	 * @return oggetto AttoWfView.
	 * @exception Throwable
	 */
	@Override
	public AttoWfView leggiUltimoWorkflowRifiutato(long idAtto) throws Throwable {
		AttoWf wf = attoWfDAO.leggiUltimoWorkflowRifiutato(idAtto);
		if(wf != null)
		return (AttoWfView) convertiVoInView(wf);
		else
			return null;
	}
	
	
	/**
	 * Metodo di lettura del workflow
	 * <p>
	 * @param id: identificativo del workflow
	 * @return oggetto AttoWfView.
	 * @exception Throwable
	 */
	@Override
	public AttoWfView leggiWorkflow(long id) throws Throwable {
		AttoWf wf = attoWfDAO.leggiWorkflow(id);
		return (AttoWfView) convertiVoInView(wf);
	}
	
	/**
	 * Annulla l'ultimo step effettuato nel workflow attivo.
	 * @param idAtto identificativo dell'atto
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 */
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean discardStep(long idAtto, String userIdUtenteConnesso) throws Throwable {

		AttoWfView workflowCorrente = null;
		AttoWf attoWf = null;
		StepWf stepCorrente = null;
		StepWf stepLast = null;
		Utente utenteConnesso = null;
		Atto atto = null;
		
		//recupero l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
				
		//recupero l'atto correlato
		atto = attoDAO.leggi(idAtto);
	 
		Timestamp dataDiscard = new Timestamp(System.currentTimeMillis());

		//recupero il workflow
		workflowCorrente = leggiWorkflowInCorso(idAtto);
		attoWf = workflowCorrente.getVo();
		
		// recupero lo step corrente
		stepCorrente = stepWfDAO.leggiStepCorrente(workflowCorrente.getVo().getId(), CostantiDAO.REGISTRAZIONE_ATTO);
		
		//effettuo il discard sullo step corrente
		stepCorrente.setDataChiusura(dataDiscard);
		stepCorrente.setUtenteChiusura(utenteConnesso.getMatricolaUtil());
		stepCorrente.setDiscarded(Character.toString(CostantiDAO.TRUE_CHAR));
		stepWfDAO.modifica(stepCorrente);

		//recupero lo step da ripristinare
		stepLast = stepWfDAO.leggiUltimoStepChiuso(workflowCorrente.getVo().getId(), CostantiDAO.REGISTRAZIONE_ATTO);
		if(stepLast != null){
			
			//ripristino lo step
			stepLast.setDataChiusura(null);
			stepLast.setUtenteChiusura(null);
			stepWfDAO.modifica(stepLast);
			
			//aggiorno l'atto
			atto.setStatoAtto(anagraficaStatiTipiDAO.leggiStatoAtto(stepLast.getConfigurazioneStepWf().getStateTo(), CostantiDAO.LINGUA_ITALIANA));
			atto.setDataUltimaModifica(dataDiscard);

			atto.setOperation(AbstractEntity.UPDATE_OPERATION);
			atto.setOperationTimestamp(new Date());
			attoDAO.modifica(atto);
			
			//aggiorno l'assegnatario nel workflow, settando il creatore dello step annullato
			attoWf.setUtenteAssegnatario(stepCorrente.getUtente().getMatricolaUtil());
			attoWfDAO.modifica(attoWf);

		}
		else{
			
			//chiudo il workflow settandolo in Interrotto
		
			attoWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_INTERROTTO, CostantiDAO.LINGUA_ITALIANA));
			attoWf.setDataChiusura(dataDiscard);
			attoWfDAO.modifica(attoWf);
			
			//riporto l'atto in Bozza
			atto.setStatoAtto(anagraficaStatiTipiDAO.leggiStatoAtto(CostantiDAO.ATTO_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA));
			atto.setDataUltimaModifica(dataDiscard);

			atto.setOperation(AbstractEntity.UPDATE_OPERATION);
			atto.setOperationTimestamp(new Date());
			attoDAO.modifica(atto);
		}
		return true;
	}

	/**
	 * Riporta in bozza il workflow corrente.
	 * @param idAtto identificativo dell'atto
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 */
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean riportaInBozzaWorkflow(long idAtto, String userIdUtenteConnesso) throws Throwable {

		AttoWfView workflowCorrente = null;
		AttoWf attoWf = null;
		StepWf stepCorrente = null;
		Utente utenteConnesso = null;
		Atto atto = null;
		boolean terminato = false;
		
		//recupero l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
				
		//recupero l'atto correlato
		atto = attoDAO.leggi(idAtto);
	 
		Timestamp dataDiscard = new Timestamp(System.currentTimeMillis());

		//recupero il workflow
		workflowCorrente = leggiWorkflowInCorso(idAtto);
		if(workflowCorrente != null){
			
			attoWf = workflowCorrente.getVo();
			
			// recupero lo step corrente
			stepCorrente = stepWfDAO.leggiStepCorrente(workflowCorrente.getVo().getId(), CostantiDAO.REGISTRAZIONE_ATTO);
		}
		else{
			
			terminato = true;

			workflowCorrente = leggiUltimoWorkflowTerminato(idAtto);
			if(workflowCorrente != null){
				attoWf = workflowCorrente.getVo();
				// recupero l'ultimo step del workflow recuperato
				stepCorrente = stepWfDAO.leggiUltimoStepChiuso(workflowCorrente.getVo().getId(), CostantiDAO.REGISTRAZIONE_ATTO);
			}
			else //non esiste alcun workflow da riportare in bozza: mi limito a riporre in stato Bozza l'atto
			{
				atto.setStatoAtto(anagraficaStatiTipiDAO.leggiStatoAtto(CostantiDAO.ATTO_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA));
				atto.setDataUltimaModifica(dataDiscard);
				atto.setDataRegistrazione(null);
				atto.setOwner(null);

				atto.setOperation(AbstractEntity.UPDATE_OPERATION);
				atto.setOperationTimestamp(new Date());
				attoDAO.modifica(atto);
				return (atto.getStatoAtto().getId() == anagraficaStatiTipiDAO.leggiStatoAtto(CostantiDAO.ATTO_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA).getId());

			}
		}
		
		
		//effettuo il discard sullo step corrente
		if(!terminato){
			stepCorrente.setDataChiusura(dataDiscard);
			stepCorrente.setUtenteChiusura(utenteConnesso.getMatricolaUtil());
		}
		stepCorrente.setDiscarded(Character.toString(CostantiDAO.TRUE_CHAR));
		stepWfDAO.modifica(stepCorrente);

		//chiudo il workflow settandolo in Interrotto
		
		attoWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_INTERROTTO, CostantiDAO.LINGUA_ITALIANA));
		attoWf.setDataChiusura(dataDiscard);
		attoWfDAO.modifica(attoWf);
		
		//riporto l'atto in Bozza
		atto.setStatoAtto(anagraficaStatiTipiDAO.leggiStatoAtto(CostantiDAO.ATTO_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA));
		atto.setDataUltimaModifica(dataDiscard);
		atto.setDataRegistrazione(null);
		atto.setOwner(null);
		atto.setOperation(AbstractEntity.UPDATE_OPERATION);
		atto.setOperationTimestamp(new Date());
		attoDAO.modifica(atto);
		
		return (atto.getStatoAtto().getId() == anagraficaStatiTipiDAO.leggiStatoAtto(CostantiDAO.ATTO_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA).getId());
	}
	
	
}
