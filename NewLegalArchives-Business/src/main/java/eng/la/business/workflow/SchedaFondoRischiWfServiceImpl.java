package eng.la.business.workflow;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import eng.la.business.BaseService;
import eng.la.model.ClasseWf;
import eng.la.model.ConfigurazioneStepWf;
import eng.la.model.SchedaFondoRischi;
import eng.la.model.SchedaFondoRischiWf;
import eng.la.model.StepWf;
import eng.la.model.Utente;
import eng.la.model.view.SchedaFondoRischiWfView;
import eng.la.persistence.AnagraficaStatiTipiDAO;
import eng.la.persistence.CostantiDAO;
import eng.la.persistence.SchedaFondoRischiDAO;
import eng.la.persistence.UtenteDAO;
import eng.la.persistence.workflow.ConfigurazioneStepWfDAO;
import eng.la.persistence.workflow.SchedaFondoRischiWfDAO;
import eng.la.persistence.workflow.StepWfDAO;
import eng.la.util.exceptions.LAException;

/**
 * <h1>Classe di business SchedaFondoRischiWfServiceImpl </h1>
 * Classe preposta alla gestione delle operazione di scrittura
 * lettura sulla base dati attraverso l'uso delle classi DAO 
 * di pertinenza all'operazione.
 * <p>
 * @author Benedetto Giordano
 * @version 1.0
 * @since 2017-04-03
 */
@Service("schedaFondoRischiWfService")
public class SchedaFondoRischiWfServiceImpl extends BaseService<SchedaFondoRischiWf,SchedaFondoRischiWfView> implements SchedaFondoRischiWfService {

	@Autowired
	private AnagraficaStatiTipiDAO anagraficaStatiTipiDAO;
 
	public AnagraficaStatiTipiDAO getAnagraficaStatiTipiDAO() {
		return anagraficaStatiTipiDAO;
	}
		
	@Autowired
	private SchedaFondoRischiDAO schedaFondoRischiDAO;
	
	@Autowired
	private SchedaFondoRischiWfDAO schedaFondoRischiWfDAO;
	
	@Autowired
	private UtenteDAO utenteDAO;
	
	@Autowired
	private ConfigurazioneStepWfDAO configurazioneStepWfDAO;
	
	@Autowired
	private StepWfDAO stepWfDAO;
	
	@Override
	protected Class<SchedaFondoRischiWf> leggiClassVO() {
		return SchedaFondoRischiWf.class;
	}

	@Override
	protected Class<SchedaFondoRischiWfView> leggiClassView() {
		return SchedaFondoRischiWfView.class;
	}

	/**
	 * Crea un nuovo workflow e lo pone in stato "IN CORSO"
	 *
	 * @param idSchedaFondoRischi identificativo della scheda correlata
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	//DARIO C *********************************************************************************************************
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean avviaWorkflow(long idSchedaFr, String userIdUtenteConnesso) throws Throwable {
		return avviaWorkflow(idSchedaFr, userIdUtenteConnesso,"");
	}
		
	//*****************************************************************************************************************
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean avviaWorkflow(long idSchedaFr, String userIdUtenteConnesso, String matricolaDestinatario) throws Throwable {
		//DARIO C aggiunto alla funzione il nuovo parametro 'matricolaDestinatario'
		
		Timestamp dataCreazione = new Timestamp(System.currentTimeMillis());
		boolean primoRiporto = false;
		boolean respPrimoRiporto = false;
		boolean amministratore = false;
		
		SchedaFondoRischiWf schedaFondoRischiWf = null;
		ClasseWf classeWf = null;
		Utente responsabileTop = null;
		Utente responsabileDiretto = null;
		Utente utenteConnesso = null;
		ConfigurazioneStepWf configurazioneStart = null;
		ConfigurazioneStepWf configurazione = null;
		SchedaFondoRischiWf schedaFondoRischiWfSalvato = null;
		SchedaFondoRischi schedaFondoRischi = null;
		
		//recupero l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
				
		schedaFondoRischi = schedaFondoRischiDAO.leggi(idSchedaFr);
		
		
		//se la scheda non è in stato Bozza genero l'eccezione
		if(!schedaFondoRischi.getStatoSchedaFondoRischi().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.SCHEDA_FONDO_RISCHI_STATO_BOZZA)){

				throw new LAException("Lo stato " + schedaFondoRischi.getStatoSchedaFondoRischi().getDescrizione() + " della schedaFondoRischi non è compatibile con l'operazione richiesta", "errore.schedaFondoRischi.stato");

		}
		
		//creo l'occorrenza SchedaFondoRischiWf
		schedaFondoRischiWf = new SchedaFondoRischiWf();
		schedaFondoRischiWf.setSchedaFondoRischi(schedaFondoRischi);
		schedaFondoRischiWf.setUtenteCreazione(userIdUtenteConnesso);
		schedaFondoRischiWf.setDataCreazione(dataCreazione);
		schedaFondoRischiWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_IN_CORSO, CostantiDAO.LINGUA_ITALIANA));
		schedaFondoRischiWf.setLang(CostantiDAO.LINGUA_ITALIANA);
		
		//DARIO C ************************************************************************************
		if (matricolaDestinatario.trim().length()!=0){
			schedaFondoRischiWf.setUtenteAssegnatario(matricolaDestinatario);	
		}
		//********************************************************************************************	
		
		classeWf =  anagraficaStatiTipiDAO.leggiClasseWf(CostantiDAO.AUTORIZZAZIONE_SCHEDA_FONDO_RISCHI);	
		
		// recupero la profilazione dell'utente connesso
		responsabileTop = utenteDAO.leggiResponsabileTop();
		if(responsabileTop.getUseridUtil().equalsIgnoreCase(userIdUtenteConnesso))//l'utente connesso è il responsabile top, per cui mi limito ad autorizzare la scheda
		{
			schedaFondoRischi.setStatoSchedaFondoRischi(anagraficaStatiTipiDAO.leggiStatoSchedaFondoRischi(CostantiDAO.SCHEDA_FONDO_RISCHI_STATO_AUTORIZZATO, CostantiDAO.LINGUA_ITALIANA));
			schedaFondoRischi.setDataModifica(dataCreazione);
			schedaFondoRischi.setDataAutorizzazione(dataCreazione);
			schedaFondoRischiDAO.modifica(schedaFondoRischi);
			return schedaFondoRischi.getStatoSchedaFondoRischi().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.SCHEDA_FONDO_RISCHI_STATO_AUTORIZZATO);
		}
		
		//DARIO C **************************************************************************************************
//		if(isPrimoRiporto(utenteConnesso, responsabileTop)){
//			primoRiporto = true;
//			respPrimoRiporto = true;
//		}
//		else{
//			responsabileDiretto = utenteDAO.leggiUtenteDaMatricola(utenteConnesso.getMatricolaRespUtil());
//			if(isPrimoRiporto(responsabileDiretto, responsabileTop))
//				respPrimoRiporto = true;
//		}


		matricolaDestinatario = matricolaDestinatario.trim().length()!=0 ? matricolaDestinatario : utenteConnesso.getMatricolaRespUtil(); 
		if(matricolaDestinatario.equalsIgnoreCase(responsabileTop.getMatricolaUtil())){
			primoRiporto = true;
			respPrimoRiporto = true;
		}else{
			responsabileDiretto = utenteDAO.leggiUtenteDaMatricola(matricolaDestinatario);
			if(responsabileDiretto.getMatricolaRespUtil().equalsIgnoreCase(responsabileTop.getMatricolaUtil()))
				respPrimoRiporto = true;
		}
		//************************************************************************************************************
		
		
		amministratore = utenteDAO.leggiSeAmministratore(utenteConnesso);
		
		//recupero lo step di partenza
		//vedo se nel flusso è previsto un avanzamento manuale assegnato all'utente corrente
		//in tal caso il workflow parte direttamente da lì
		configurazioneStart = configurazioneStepWfDAO.leggiConfigurazioneManuale(classeWf.getId(), utenteConnesso.getMatricolaUtil(), CostantiDAO.LINGUA_ITALIANA);
		
		//se non c'è alcuna assegnazione manuale, il workflow parte dallo step numero 1
		if(configurazioneStart == null){
			
			configurazioneStart = configurazioneStepWfDAO.leggiConfigurazioneStepNumeroUno(classeWf.getId(), CostantiDAO.LINGUA_ITALIANA);
			configurazione = configurazioneStepWfDAO.leggiConfigurazioneSuccessivaStandard(configurazioneStart, primoRiporto, respPrimoRiporto, amministratore, CostantiDAO.LINGUA_ITALIANA);
		}
		if(configurazione == null || configurazione.getTipoAssegnazione() == null){ //non c'è alcuno step successivo, per cui mi limito ad aggiornare lo stato della scheda in Autorizzato
			
			schedaFondoRischi.setStatoSchedaFondoRischi(anagraficaStatiTipiDAO.leggiStatoSchedaFondoRischi(CostantiDAO.SCHEDA_FONDO_RISCHI_STATO_AUTORIZZATO, CostantiDAO.LINGUA_ITALIANA));
			schedaFondoRischi.setDataModifica(dataCreazione);
			schedaFondoRischi.setDataAutorizzazione(dataCreazione);
			schedaFondoRischiDAO.modifica(schedaFondoRischi);
			return schedaFondoRischi.getStatoSchedaFondoRischi().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.SCHEDA_FONDO_RISCHI_STATO_AUTORIZZATO);
		}

		//creo l'istanza di workflow
		schedaFondoRischiWfSalvato = schedaFondoRischiWfDAO.avviaWorkflow(schedaFondoRischiWf);  
		
		//creo lo step
		creaStepWf(schedaFondoRischiWfSalvato,configurazione, dataCreazione, utenteConnesso);
		
		schedaFondoRischi.setStatoSchedaFondoRischi(anagraficaStatiTipiDAO.leggiStatoSchedaFondoRischi(configurazione.getStateTo(), CostantiDAO.LINGUA_ITALIANA));
			
		schedaFondoRischi.setDataModifica(dataCreazione);
		schedaFondoRischi.setDataRichiestaAutorScheda(dataCreazione);
		
		schedaFondoRischiDAO.modifica(schedaFondoRischi);
		
		return schedaFondoRischiWfSalvato.getStatoWf().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.STATO_WF_IN_CORSO);
	}
	

	/** 
	  * Rifiuta un workflow e lo pone in stato "RIFIUTATO"
	 *
	 * @param idSchedaFondoRischiWf identificativo del workflow
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @param motivoRifiuto motivo del rifiuto
	 * @return true se il rifiuto è andato a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean rifiutaWorkflow(long idSchedaFondoRischiWf, String userIdUtenteConnesso, String motivoRifiuto) throws Throwable {

		Timestamp dataRifiuto = new Timestamp(System.currentTimeMillis());
		SchedaFondoRischiWf schedaFondoRischiWf = null;
		StepWf stepCorrente = null;
		SchedaFondoRischi schedaFondoRischiCorrelata = null;
		Utente utenteConnesso = null;
		
		//recupero il workflow
		schedaFondoRischiWf = schedaFondoRischiWfDAO.leggiWorkflow(idSchedaFondoRischiWf);
		
		//se il workflow non è in stato In corso genero l'eccezione
		if(!schedaFondoRischiWf.getStatoWf().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.STATO_WF_IN_CORSO))
			throw new LAException("Lo stato " + schedaFondoRischiWf.getStatoWf().getDescrizione() + " del workflow non è compatibile con l'operazione richiesta", "errore.workflow.stato");

		
		//recupero l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
		
		// recupero lo step corrente
		stepCorrente = stepWfDAO.leggiStepCorrente(idSchedaFondoRischiWf, CostantiDAO.AUTORIZZAZIONE_SCHEDA_FONDO_RISCHI);
		
		//chiudo lo step corrente
		stepCorrente.setDataChiusura(dataRifiuto);
		stepCorrente.setUtenteChiusura(utenteConnesso.getMatricolaUtil());
		stepCorrente.setMotivoRifiuto(motivoRifiuto);
		stepWfDAO.modifica(stepCorrente);
		
		//aggiorno l'occorrenza SchedaFondoRischiWf
		
		schedaFondoRischiWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_RIFIUTATO, CostantiDAO.LINGUA_ITALIANA));
		schedaFondoRischiWf.setDataChiusura(dataRifiuto);
		schedaFondoRischiWfDAO.modifica(schedaFondoRischiWf);
				
		//recupero la scheda
		schedaFondoRischiCorrelata = schedaFondoRischiWf.getSchedaFondoRischi();
		schedaFondoRischiCorrelata.setStatoSchedaFondoRischi(anagraficaStatiTipiDAO.leggiStatoSchedaFondoRischi(CostantiDAO.SCHEDA_FONDO_RISCHI_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA));
		//schedaFondoRischiCorrelata.setDataModifica(dataRifiuto);

		schedaFondoRischiDAO.modifica(schedaFondoRischiCorrelata);
		
		return schedaFondoRischiWf.getStatoWf().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.STATO_WF_RIFIUTATO);
	}
	
	/**
	  * Fa avanzare un workflow 
	 *
	 * @param idSchedaFondoRischiWf identificativo del workflow
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'avanzamento è andato a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	//DARIO C ************************************************************************************************
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean avanzaWorkflow(long idSchedaFondoRischiWf, String userIdUtenteConnesso) throws Throwable {
		return avanzaWorkflow(idSchedaFondoRischiWf, userIdUtenteConnesso,"");
	}
	//********************************************************************************************************	
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean avanzaWorkflow(long idSchedaFondoRischiWf, String userIdUtenteConnesso, String matricolaDestinatario) throws Throwable {
		//DARIO C aggiunto alla funzione il nuovo parametro 'matricolaDestinatario'
		
		//aggiorno l'occorrenza SchedaFondoRischiWf
		Timestamp dataAvanzamento = new Timestamp(System.currentTimeMillis());
		
		boolean primoRiporto = false;
		boolean respPrimoRiporto = false;
		boolean respTop = false;
		boolean amministratore = false;
		
		SchedaFondoRischiWf schedaFondoRischiWf = null;
		SchedaFondoRischi schedaFondoRischiCorrelata = null;
		StepWf stepCorrente = null;
		Utente responsabileTop = null;
		Utente utenteConnesso = null;
		Utente responsabileDiretto = null;
		ConfigurazioneStepWf configurazione = null;
		ConfigurazioneStepWf configurazioneStart = null;
		
		//recupero l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
		
		schedaFondoRischiWf = schedaFondoRischiWfDAO.leggiWorkflow(idSchedaFondoRischiWf);
		
		//se il workflow non è in stato In corso genero l'eccezione
		if(!schedaFondoRischiWf.getStatoWf().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.STATO_WF_IN_CORSO))
			throw new LAException("Lo stato " + schedaFondoRischiWf.getStatoWf().getDescrizione() + " del workflow non è compatibile con l'operazione richiesta", "errore.workflow.stato");

		
		//recupero la scheda
		schedaFondoRischiCorrelata = schedaFondoRischiWf.getSchedaFondoRischi();
	
		
		// recupero lo step corrente
		stepCorrente = stepWfDAO.leggiStepCorrente(idSchedaFondoRischiWf, CostantiDAO.AUTORIZZAZIONE_SCHEDA_FONDO_RISCHI);
		
		//chiudo lo step corrente
		stepCorrente.setDataChiusura(dataAvanzamento);
		stepCorrente.setUtenteChiusura(utenteConnesso.getMatricolaUtil());
		stepWfDAO.modifica(stepCorrente);
		
		// recupero la profilazione dell'utente connesso
		responsabileTop = utenteDAO.leggiResponsabileTop();
		
		//verifico se sia il responsabile top
		if(responsabileTop.getUseridUtil().equalsIgnoreCase(userIdUtenteConnesso))
			respTop = true;
		
		//DARIO C ****************************************************************************************************
//		if(isPrimoRiporto(utenteConnesso, responsabileTop) || respTop){
//				primoRiporto = true;
//				respPrimoRiporto = true;
//		}
//		else{
//			responsabileDiretto = utenteDAO.leggiUtenteDaMatricola(utenteConnesso.getMatricolaRespUtil());
//			if(isPrimoRiporto(responsabileDiretto, responsabileTop))
//				respPrimoRiporto = true;
//		}
		
		String matricola_responsabile = matricolaDestinatario.trim().length()!=0 ? matricolaDestinatario : utenteConnesso.getMatricolaRespUtil(); 
		if(matricola_responsabile.equalsIgnoreCase(responsabileTop.getMatricolaUtil()) || respTop){
			primoRiporto = true;
			respPrimoRiporto = true;
		}else{
			responsabileDiretto = utenteDAO.leggiUtenteDaMatricola(matricola_responsabile);
			if(responsabileDiretto.getMatricolaRespUtil().equalsIgnoreCase(responsabileTop.getMatricolaUtil()))
				respPrimoRiporto = true;
		}
		//************************************************************************************************************		
		amministratore = utenteDAO.leggiSeAmministratore(utenteConnesso);
		
		//recupero lo step di partenza
		//vedo se nel flusso è previsto un avanzamento manuale assegnato all'utente corrente
		//in tal caso il workflow parte direttamente da lì
		configurazioneStart = configurazioneStepWfDAO.leggiConfigurazioneManuale(anagraficaStatiTipiDAO.leggiClasseWf(CostantiDAO.AUTORIZZAZIONE_SCHEDA_FONDO_RISCHI).getId(), utenteConnesso.getMatricolaUtil(), CostantiDAO.LINGUA_ITALIANA);
		
		//se non c'è alcuna assegnazione manuale, il workflow parte dallo step numero 1
		if(configurazioneStart == null)
			configurazioneStart = configurazioneStepWfDAO.leggiConfigurazioneStepNumeroUno(anagraficaStatiTipiDAO.leggiClasseWf(CostantiDAO.AUTORIZZAZIONE_SCHEDA_FONDO_RISCHI).getId(), CostantiDAO.LINGUA_ITALIANA);
		
		//calcolo lo step da mandare in assegnazione
		configurazione = configurazioneStepWfDAO.leggiConfigurazioneSuccessivaStandard(configurazioneStart, primoRiporto, respPrimoRiporto, amministratore, CostantiDAO.LINGUA_ITALIANA);
		
		if(configurazione == null || configurazione.getTipoAssegnazione() == null){ //Workflow finito
			
			//aggiorno l'occorrenza SchedaFondoRischiWf
			schedaFondoRischiWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_COMPLETATO, CostantiDAO.LINGUA_ITALIANA));
			schedaFondoRischiWf.setDataChiusura(dataAvanzamento);
			schedaFondoRischiWfDAO.modifica(schedaFondoRischiWf);
			
			//aggiorno la schedaFondoRischi
			schedaFondoRischiCorrelata.setStatoSchedaFondoRischi(anagraficaStatiTipiDAO.leggiStatoSchedaFondoRischi(CostantiDAO.SCHEDA_FONDO_RISCHI_STATO_AUTORIZZATO, CostantiDAO.LINGUA_ITALIANA));
			schedaFondoRischiCorrelata.setDataModifica(dataAvanzamento);
			schedaFondoRischiCorrelata.setDataAutorizzazione(dataAvanzamento);

			schedaFondoRischiDAO.modifica(schedaFondoRischiCorrelata);
		
		}
		else{

			//DARIO C ************************************************************************************
			if (matricolaDestinatario.trim().length()!=0){
				schedaFondoRischiWf.setUtenteAssegnatario(matricolaDestinatario);
				schedaFondoRischiWfDAO.modifica(schedaFondoRischiWf);
			}
			//********************************************************************************************
			
			//creo lo step
			creaStepWf(schedaFondoRischiWf,configurazione, dataAvanzamento, utenteConnesso);
			
			//aggiorno la schedaFondoRischi
			schedaFondoRischiCorrelata.setStatoSchedaFondoRischi(anagraficaStatiTipiDAO.leggiStatoSchedaFondoRischi(configurazione.getStateTo(), CostantiDAO.LINGUA_ITALIANA));
			schedaFondoRischiCorrelata.setDataModifica(dataAvanzamento);
			
			schedaFondoRischiDAO.modifica(schedaFondoRischiCorrelata);
		}
		
		return (stepCorrente.getDataChiusura() != null);
	}
	
	/**
	 * Crea un nuovo step workflow
	 *
	 * @param schedaFondoRischiWf workflow correlato
	 * @param configurazioneStepWf configurazione associata
	 * @param dataCorrente data corrente
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @throws Throwable eccezione
	 */
	private void creaStepWf(SchedaFondoRischiWf schedaFondoRischiWf, ConfigurazioneStepWf configurazioneStepWf, Timestamp dataCorrente, Utente utenteConnesso) throws Throwable {
		StepWf stepWf = new StepWf();
		stepWf.setDataCreazione(dataCorrente);
		stepWf.setConfigurazioneStepWf(configurazioneStepWf);
		stepWf.setSchedaFondoRischiWf(schedaFondoRischiWf);
		stepWf.setUtente(utenteConnesso);
		stepWf.setLang(CostantiDAO.LINGUA_ITALIANA);
		stepWfDAO.creaStepWf(stepWf);
	}
	
	/**
	 * Metodo di lettura del workflow in corso
	 * <p>
	 * @param idSchedaFondoRischi: identificativo della schedaFondoRischi
	 * @return oggetto SchedaFondoRischiWfView.
	 * @exception Throwable
	 */
	@Override
	public SchedaFondoRischiWfView leggiWorkflowInCorso(long idSchedaFondoRischi) throws Throwable {
		SchedaFondoRischiWf wf = schedaFondoRischiWfDAO.leggiWorkflowInCorso(idSchedaFondoRischi);
		if(wf != null)
			return (SchedaFondoRischiWfView) convertiVoInView(wf);
		else
			return null;
	}
	
	public SchedaFondoRischiWf leggiWorkflowInCorsoNotView(long idSchedaFondoRischi) throws Throwable {
		SchedaFondoRischiWf wf = schedaFondoRischiWfDAO.leggiWorkflowInCorso(idSchedaFondoRischi);
		if(wf != null)
			return wf;
		else
			return null;
	}
	
	/**
	 * Metodo di lettura dell'ultimo workflow terminato
	 * <p>
	 * @param idSchedaFondoRischi: identificativo della schedaFondoRischi
	 * @return oggetto SchedaFondoRischiWfView.
	 * @exception Throwable
	 */
	@Override
	public SchedaFondoRischiWfView leggiUltimoWorkflowTerminato(long idSchedaFondoRischi) throws Throwable {
		SchedaFondoRischiWf wf = schedaFondoRischiWfDAO.leggiUltimoWorkflowTerminato(idSchedaFondoRischi);
		if(wf != null)
		return (SchedaFondoRischiWfView) convertiVoInView(wf);
		else
			return null;
	}
	
	/**
	 * Metodo di lettura dell'ultimo workflow rifiutato
	 * <p>
	 * @param idSchedaFondoRischi: identificativo della schedaFondoRischi
	 * @return oggetto SchedaFondoRischiWfView.
	 * @exception Throwable
	 */
	@Override
	public SchedaFondoRischiWfView leggiUltimoWorkflowRifiutato(long idSchedaFondoRischi) throws Throwable {
		SchedaFondoRischiWf wf = schedaFondoRischiWfDAO.leggiUltimoWorkflowRifiutato(idSchedaFondoRischi);
		if(wf != null)
		return (SchedaFondoRischiWfView) convertiVoInView(wf);
		else
			return null;
	}
	
	/**
	 * Metodo di lettura del workflow
	 * <p>
	 * @param id: identificativo del workflow
	 * @return oggetto SchedaFondoRischiWfView.
	 * @exception Throwable
	 */
	@Override
	public SchedaFondoRischiWfView leggiWorkflow(long id) throws Throwable {
		SchedaFondoRischiWf wf = schedaFondoRischiWfDAO.leggiWorkflow(id);
		return (SchedaFondoRischiWfView) convertiVoInView(wf);
	}
	
	/**
	 * Annulla l'ultimo step effettuato nel workflow attivo.
	 * @param idSchedaFondoRischi identificativo della schedaFondoRischi
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 */
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean discardStep(long idSchedaFondoRischi, String userIdUtenteConnesso) throws Throwable {

		SchedaFondoRischiWfView workflowCorrente = null;
		SchedaFondoRischiWf schedaFondoRischiWf = null;
		StepWf stepCorrente = null;
		StepWf stepLast = null;
		Utente utenteConnesso = null;
		SchedaFondoRischi schedaFondoRischi = null;
		
		//recupero l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
				
		//recupero la scheda correlata
		schedaFondoRischi = schedaFondoRischiDAO.leggi(idSchedaFondoRischi);
	 
		Timestamp dataDiscard = new Timestamp(System.currentTimeMillis());

		//recupero il workflow
		workflowCorrente = leggiWorkflowInCorso(idSchedaFondoRischi);
		schedaFondoRischiWf = workflowCorrente.getVo();
		
		// recupero lo step corrente
		stepCorrente = stepWfDAO.leggiStepCorrente(workflowCorrente.getVo().getId(), CostantiDAO.AUTORIZZAZIONE_SCHEDA_FONDO_RISCHI);
		
		//effettuo il discard sullo step corrente
		stepCorrente.setDataChiusura(dataDiscard);
		stepCorrente.setUtenteChiusura(utenteConnesso.getMatricolaUtil());
		stepCorrente.setDiscarded(Character.toString(CostantiDAO.TRUE_CHAR));
		stepWfDAO.modifica(stepCorrente);

		//recupero lo step da ripristinare
		stepLast = stepWfDAO.leggiUltimoStepChiuso(workflowCorrente.getVo().getId(), CostantiDAO.AUTORIZZAZIONE_SCHEDA_FONDO_RISCHI);
		if(stepLast != null){
			
			//ripristino lo step
			stepLast.setDataChiusura(null);
			stepLast.setUtenteChiusura(null);
			stepWfDAO.modifica(stepLast);
			
			//aggiorno la scheda
			schedaFondoRischi.setStatoSchedaFondoRischi(anagraficaStatiTipiDAO.leggiStatoSchedaFondoRischi(stepLast.getConfigurazioneStepWf().getStateTo(), CostantiDAO.LINGUA_ITALIANA));
			schedaFondoRischi.setDataModifica(dataDiscard);
			schedaFondoRischiDAO.modifica(schedaFondoRischi);
		}
		else{
			
			//chiudo il workflow settandolo in Interrotto
		
			schedaFondoRischiWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_INTERROTTO, CostantiDAO.LINGUA_ITALIANA));
			schedaFondoRischiWf.setDataChiusura(dataDiscard);
			schedaFondoRischiWfDAO.modifica(schedaFondoRischiWf);
			
			//riporto la scheda in Bozza
			schedaFondoRischi.setStatoSchedaFondoRischi(anagraficaStatiTipiDAO.leggiStatoSchedaFondoRischi(CostantiDAO.SCHEDA_FONDO_RISCHI_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA));
			schedaFondoRischi.setDataModifica(dataDiscard);
			schedaFondoRischiDAO.modifica(schedaFondoRischi);
		}
		return (stepCorrente.getDataChiusura() != null);
	}

	/**
	 * Riporta in bozza il workflow corrente.
	 * @param idSchedaFondoRischi identificativo della schedaFondoRischi
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 */
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean riportaInBozzaWorkflow(long idSchedaFondoRischi, String userIdUtenteConnesso) throws Throwable {

		SchedaFondoRischiWfView workflowCorrente = null;
		SchedaFondoRischiWf schedaFondoRischiWf = null;
		StepWf stepCorrente = null;
		Utente utenteConnesso = null;
		SchedaFondoRischi schedaFondoRischi = null;
		boolean terminato = false;
		
		//recupero l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
				
		schedaFondoRischi = schedaFondoRischiDAO.leggi(idSchedaFondoRischi);
		
		Timestamp dataDiscard = new Timestamp(System.currentTimeMillis());

		//recupero il workflow
		workflowCorrente = leggiWorkflowInCorso(idSchedaFondoRischi);
		if(workflowCorrente != null){
			
			schedaFondoRischiWf = workflowCorrente.getVo();
			
			// recupero lo step corrente
			stepCorrente = stepWfDAO.leggiStepCorrente(workflowCorrente.getVo().getId(), CostantiDAO.AUTORIZZAZIONE_SCHEDA_FONDO_RISCHI);
		}
		else{
			
			terminato = true;
			workflowCorrente = leggiUltimoWorkflowTerminato(idSchedaFondoRischi);
			if(workflowCorrente != null){
				schedaFondoRischiWf = workflowCorrente.getVo();
				// recupero l'ultimo step del workflow recuperato
				stepCorrente = stepWfDAO.leggiUltimoStepChiuso(workflowCorrente.getVo().getId(), CostantiDAO.AUTORIZZAZIONE_SCHEDA_FONDO_RISCHI);
			}
			else //non esiste alcun workflow da riportare in bozza: mi limito a riporre in stato Bozza la schedaFondoRischi
			{
				schedaFondoRischi.setStatoSchedaFondoRischi(anagraficaStatiTipiDAO.leggiStatoSchedaFondoRischi(CostantiDAO.SCHEDA_FONDO_RISCHI_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA));
				schedaFondoRischi.setDataModifica(dataDiscard);
				schedaFondoRischiDAO.modifica(schedaFondoRischi);
				return (schedaFondoRischi.getStatoSchedaFondoRischi().getId() == anagraficaStatiTipiDAO.leggiStatoSchedaFondoRischi(CostantiDAO.SCHEDA_FONDO_RISCHI_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA).getId());

			}
				
		}
		
		//effettuo il discard sullo step corrente
		if(!terminato ){
			stepCorrente.setDataChiusura(dataDiscard);
			stepCorrente.setUtenteChiusura(utenteConnesso.getMatricolaUtil());
		}
		stepCorrente.setDiscarded(Character.toString(CostantiDAO.TRUE_CHAR));
		stepWfDAO.modifica(stepCorrente);

		//chiudo il workflow settandolo in Interrotto
		
		schedaFondoRischiWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_INTERROTTO, CostantiDAO.LINGUA_ITALIANA));
		schedaFondoRischiWf.setDataChiusura(dataDiscard);
		schedaFondoRischiWfDAO.modifica(schedaFondoRischiWf);
		
		//riporto la schedaFondoRischi in Bozza
		schedaFondoRischi.setStatoSchedaFondoRischi(anagraficaStatiTipiDAO.leggiStatoSchedaFondoRischi(CostantiDAO.SCHEDA_FONDO_RISCHI_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA));
		schedaFondoRischi.setDataModifica(dataDiscard);
		schedaFondoRischiDAO.modifica(schedaFondoRischi);
		return (schedaFondoRischi.getStatoSchedaFondoRischi().getId() == anagraficaStatiTipiDAO.leggiStatoSchedaFondoRischi(CostantiDAO.SCHEDA_FONDO_RISCHI_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA).getId());
	}
	
	boolean isPrimoRiporto(Utente utenteConnesso, Utente responsabileTop){
		
		boolean result = false;
		String responsabile = "N";
		
		if(utenteConnesso.getResponsabileUtil() != null && !utenteConnesso.getResponsabileUtil().isEmpty()){
			
			if(utenteConnesso.getResponsabileUtil().equalsIgnoreCase("Y"))
				responsabile = "Y";
		}
		
		if(utenteConnesso.getMatricolaRespUtil().equalsIgnoreCase(responsabileTop.getMatricolaUtil()) && responsabile.equals("Y")){
			result = true;
		}
		
		return result;
		
		
		
	}
	
	
}
