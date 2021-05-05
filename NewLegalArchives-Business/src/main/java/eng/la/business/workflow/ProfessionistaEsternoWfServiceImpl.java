package eng.la.business.workflow;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import eng.la.business.BaseService;
import eng.la.model.ClasseWf;
import eng.la.model.ConfigurazioneStepWf;
import eng.la.model.ProfessionistaEsterno;
import eng.la.model.ProfessionistaEsternoWf;
import eng.la.model.StepWf;
import eng.la.model.Utente;
import eng.la.model.view.ProfessionistaEsternoWfView;
import eng.la.persistence.AnagraficaStatiTipiDAO;
import eng.la.persistence.CostantiDAO;
import eng.la.persistence.ProfessionistaEsternoDAO;
import eng.la.persistence.UtenteDAO;
import eng.la.persistence.workflow.ConfigurazioneStepWfDAO;
import eng.la.persistence.workflow.ProfessionistaEsternoWfDAO;
import eng.la.persistence.workflow.StepWfDAO;
import eng.la.util.exceptions.LAException;

/**
 * <h1>Classe di business ProfessionistaEsternoWfServiceImpl </h1>
 * Classe preposta alla gestione delle operazione di scrittura
 * lettura sulla base dati attraverso l'uso delle classi DAO 
 * di pertinenza all'operazione.
 * <p>
 * @author Silvana Di Perna
 * @version 1.0
 * @since 2016-07-14
 */
@Service("professionistaEsternoWfService")
public class ProfessionistaEsternoWfServiceImpl extends BaseService implements ProfessionistaEsternoWfService {

	@Autowired
	private AnagraficaStatiTipiDAO anagraficaStatiTipiDAO;
 
	public AnagraficaStatiTipiDAO getAnagraficaStatiTipiDAO() {
		return anagraficaStatiTipiDAO;
	}
		
	@Autowired
	private ProfessionistaEsternoDAO professionistaEsternoDAO;
 
	public ProfessionistaEsternoDAO getProfessionistaEsternoDAO() {
		return professionistaEsternoDAO;
	}
	
	@Autowired
	private ProfessionistaEsternoWfDAO professionistaEsternoWfDAO;
 
	public ProfessionistaEsternoWfDAO getProfessionistaEsternoWfDAO() {
		return professionistaEsternoWfDAO;
	}
	
	@Autowired
	private UtenteDAO utenteDAO;
 
	public UtenteDAO getUtenteDAO() {
		return utenteDAO;
	}
	
	@Autowired
	private ConfigurazioneStepWfDAO configurazioneStepWfDAO;
 
	public ConfigurazioneStepWfDAO getConfigurazioneStepWfDAO() {
		return configurazioneStepWfDAO;
	}
	
	@Autowired
	private StepWfDAO stepWfDAO;
 
	public StepWfDAO getStepWfDAO() {
		return stepWfDAO;
	}
	
	@Override
	protected Class<ProfessionistaEsternoWf> leggiClassVO() {
		return ProfessionistaEsternoWf.class;
	}

	@Override
	protected Class<ProfessionistaEsternoWfView> leggiClassView() {
		return ProfessionistaEsternoWfView.class;
	}

	/**
	 * Crea un nuovo workflow e lo pone in stato "IN CORSO"
	 *
	 * @param idProfessionusta identificativo del professionistaEsterno correlato
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean avviaWorkflow(long idProfessionista, String userIdUtenteConnesso) throws Throwable {
		
		Timestamp dataCreazione = new Timestamp(System.currentTimeMillis());
		boolean primoRiporto = false;
		boolean respPrimoRiporto = false;
		
		ProfessionistaEsternoWf professionistaEsternoWf = null;
		ClasseWf classeWf = null;
		Utente responsabileTop = null;
		Utente responsabileDiretto = null;
		Utente utenteConnesso = null;
		ConfigurazioneStepWf configurazioneStart = null;
		ConfigurazioneStepWf configurazione = null;
		ProfessionistaEsternoWf professionistaEsternoWfSalvato = null;
		ProfessionistaEsterno professionistaEsterno = null;
		
		//recupero l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
		
		//recupero il professionista esterno correlato
		professionistaEsterno = professionistaEsternoDAO.leggi(idProfessionista);
		
		//se il professionista non è in stato Bozza genero l'eccezione
		if(!professionistaEsterno.getStatoProfessionista().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.PROFESSIONISTA_STATO_BOZZA))
			throw new LAException("Lo stato " + professionistaEsterno.getStatoProfessionista().getDescrizione() + " del professionista non è compatibile con l'operazione richiesta", "errore.proforma.stato");

		
		//creo l'occorrenza ProfessionistaEsternoWf
		professionistaEsternoWf = new ProfessionistaEsternoWf();
		professionistaEsternoWf.setProfessionistaEsterno(professionistaEsterno);
		professionistaEsternoWf.setUtenteCreazione(userIdUtenteConnesso);
		professionistaEsternoWf.setDataCreazione(dataCreazione);
		professionistaEsternoWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_IN_CORSO, CostantiDAO.LINGUA_ITALIANA));
		professionistaEsternoWf.setLang(CostantiDAO.LINGUA_ITALIANA);
		
		classeWf =  anagraficaStatiTipiDAO.leggiClasseWf(CostantiDAO.AUTORIZZAZIONE_INS_PROFESSIONISTA_ESTERNO_IN_ELENCO);	
		
		// recupero la profilazione dell'utente connesso
		responsabileTop = utenteDAO.leggiResponsabileTop();
		if(responsabileTop.getUseridUtil().equalsIgnoreCase(userIdUtenteConnesso))//l'utente connesso è il responsabile top, per cui mi limito ad autorizzare il professionista
		{
			professionistaEsterno.setStatoProfessionista(anagraficaStatiTipiDAO.leggiStatoProfessionista(CostantiDAO.PROFESSIONISTA_STATO_AUTORIZZATO, CostantiDAO.LINGUA_ITALIANA));
			professionistaEsterno.setDataUltimaModifica(dataCreazione);
			professionistaEsterno.setDataAutorizzazione(dataCreazione);
			professionistaEsternoDAO.modifica(professionistaEsterno);
			return professionistaEsterno.getStatoProfessionista().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.PROFESSIONISTA_STATO_AUTORIZZATO);
		}
		
		
		if(utenteConnesso.getMatricolaRespUtil().equalsIgnoreCase(responsabileTop.getMatricolaUtil())){
				primoRiporto = true;
				respPrimoRiporto = true;
		}
		else{
			responsabileDiretto = utenteDAO.leggiUtenteDaMatricola(utenteConnesso.getMatricolaRespUtil());
			if(responsabileDiretto.getMatricolaRespUtil().equalsIgnoreCase(responsabileTop.getMatricolaUtil()))
				respPrimoRiporto = true;
		}
		
		//recupero lo step di partenza
		//vedo se nel flusso è previsto un avanzamento manuale assegnato all'utente corrente
		//in tal caso il workflow parte direttamente da lì
		configurazioneStart = configurazioneStepWfDAO.leggiConfigurazioneManuale(classeWf.getId(), utenteConnesso.getMatricolaUtil(), CostantiDAO.LINGUA_ITALIANA);
		
		//se non c'è alcuna assegnazione manuale, il workflow parte dallo step numero 1
		if(configurazioneStart == null)
			configurazioneStart = configurazioneStepWfDAO.leggiConfigurazioneStepNumeroUno(classeWf.getId(), CostantiDAO.LINGUA_ITALIANA);
//				else
//					//calcolo lo step da mandare in assegnazione
//					configurazione = configurazioneStepWfDAO.leggiConfigurazioneSuccessivaStandard(configurazioneStart, primoRiporto, respPrimoRiporto, CostantiDAO.LINGUA_ITALIANA);
//				if(primoRiporto || respPrimoRiporto)
		configurazione = configurazioneStepWfDAO.leggiConfigurazioneSuccessivaStandard(configurazioneStart, primoRiporto, respPrimoRiporto, CostantiDAO.LINGUA_ITALIANA);

		if(configurazione == null || configurazione.getTipoAssegnazione() == null){ //non c'è alcuno step successivo, per cui mi limito ad aggiornare lo stato del professionista in Autorizzato
			
			
			//aggiorno il professionistaEsterno
			professionistaEsterno.setStatoProfessionista(anagraficaStatiTipiDAO.leggiStatoProfessionista(CostantiDAO.PROFESSIONISTA_STATO_AUTORIZZATO, CostantiDAO.LINGUA_ITALIANA));
			professionistaEsterno.setDataUltimaModifica(dataCreazione);
			professionistaEsterno.setDataAutorizzazione(dataCreazione);
			professionistaEsterno.setStatoEsitoValutazioneProf(anagraficaStatiTipiDAO.leggiStatoEsitoValutazioneProf(CostantiDAO.PROFESSIONISTA_DA_ATTIVARE, CostantiDAO.LINGUA_ITALIANA));
			
			professionistaEsternoDAO.modifica(professionistaEsterno);
			return professionistaEsterno.getStatoProfessionista().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.PROFESSIONISTA_STATO_AUTORIZZATO);

		}
		
		//creo l'istanza di workflow
		professionistaEsternoWfSalvato = professionistaEsternoWfDAO.avviaWorkflow(professionistaEsternoWf);  
		
		//creo lo step
		creaStepWf(professionistaEsternoWfSalvato,configurazione, dataCreazione, utenteConnesso);
		
		//aggiorno il professionistaEsterno
//		if(configurazioneStart == null)
//			professionistaEsterno.setStatoProfessionista(anagraficaStatiTipiDAO.leggiStatoProfessionista(configurazione.getStateFrom(), CostantiDAO.LINGUA_ITALIANA));
//		else
		professionistaEsterno.setStatoProfessionista(anagraficaStatiTipiDAO.leggiStatoProfessionista(configurazione.getStateTo(), CostantiDAO.LINGUA_ITALIANA));
		professionistaEsterno.setDataUltimaModifica(dataCreazione);
		professionistaEsterno.setDataRichAutorizzazione(dataCreazione);
		professionistaEsternoDAO.modifica(professionistaEsterno);
		
		return professionistaEsternoWfSalvato.getStatoWf().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.STATO_WF_IN_CORSO);
	}

	/**
	  * Rifiuta un workflow e lo pone in stato "RIFIUTATO"
	 *
	 * @param idProfessionistaEsternoWf identificativo del workflow
	 * @param userIdUtenteConnessoo userId dell'utente connesso
	 * @param motivoRifiuto motivo del rifiuto
	 * @return true se il rifiuto è andato a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean rifiutaWorkflow(long idProfessionistaEsternoWf, String userIdUtenteConnesso, String motivoRifiuto) throws Throwable {

		Timestamp dataRifiuto = new Timestamp(System.currentTimeMillis());
		ProfessionistaEsternoWf professionistaEsternoWf = null;
		Utente utenteConnesso = null;
		StepWf stepCorrente = null;
		ProfessionistaEsterno professionistaEsternoCorrelato = null;
		
		//recupero il workflow
		professionistaEsternoWf = professionistaEsternoWfDAO.leggiWorkflow(idProfessionistaEsternoWf);
		
		//se il workflow non è in stato In corso genero l'eccezione
		if(!professionistaEsternoWf.getStatoWf().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.STATO_WF_IN_CORSO))
			throw new LAException("Lo stato " + professionistaEsternoWf.getStatoWf().getDescrizione() + " del workflow non è compatibile con l'operazione richiesta", "errore.workflow.stato");

		
		//recupero l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
		
		// recupero lo step corrente
		stepCorrente = stepWfDAO.leggiStepCorrente(idProfessionistaEsternoWf, CostantiDAO.AUTORIZZAZIONE_INS_PROFESSIONISTA_ESTERNO_IN_ELENCO);
		
		//chiudo lo step corrente
		stepCorrente.setDataChiusura(dataRifiuto);
		stepCorrente.setUtenteChiusura(utenteConnesso.getMatricolaUtil());
		stepCorrente.setMotivoRifiuto(motivoRifiuto);
		stepWfDAO.modifica(stepCorrente);
		
		//aggiorno l'occorrenza ProfessionistaEsternoWf
		
		professionistaEsternoWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_RIFIUTATO, CostantiDAO.LINGUA_ITALIANA));
		professionistaEsternoWf.setDataChiusura(dataRifiuto);
		professionistaEsternoWfDAO.modifica(professionistaEsternoWf);
		
		//recupero il professionistaEsterno
		professionistaEsternoCorrelato = professionistaEsternoWf.getProfessionistaEsterno();
		professionistaEsternoCorrelato.setStatoProfessionista(anagraficaStatiTipiDAO.leggiStatoProfessionista(CostantiDAO.PROFESSIONISTA_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA));
		//professionistaEsternoCorrelato.setDataUltimaModifica(dataRifiuto);
		professionistaEsternoDAO.modifica(professionistaEsternoCorrelato);
		
		return professionistaEsternoWf.getStatoWf().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.STATO_WF_RIFIUTATO);
	}
	
	/**
	  * Fa avanzare un workflow 
	 *
	 * @param idProfessionistaEsternoWf identificativo del workflow
	 * @param userIdUtenteConnesso userid dell'utente connesso
	 * @return true se l'avanzamento è andato a buon fine, false in caso contrario
	 * @throws Throwable eccezione
	 */
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean avanzaWorkflow(long idProfessionistaEsternoWf, String userIdUtenteConnesso) throws Throwable {
		
		//aggiorno l'occorrenza ProfessionistaEsternoWf
		Timestamp dataAvanzamento = new Timestamp(System.currentTimeMillis());
		
		boolean primoRiporto = false;
		boolean respPrimoRiporto = false;
		boolean respTop = false;
		
		ProfessionistaEsternoWf professionistaEsternoWf = null;
		ProfessionistaEsterno professionistaEsternoCorrelato = null;
		StepWf stepCorrente = null;
		Utente responsabileTop = null;
		Utente responsabileDiretto = null;
		Utente utenteConnesso = null;
		ConfigurazioneStepWf configurazione = null;
		ConfigurazioneStepWf configurazioneStart = null;
		
		//recupero l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
		
		professionistaEsternoWf = professionistaEsternoWfDAO.leggiWorkflow(idProfessionistaEsternoWf);
		
		//se il workflow non è in stato In corso genero l'eccezione
		if(!professionistaEsternoWf.getStatoWf().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.STATO_WF_IN_CORSO))
			throw new LAException("Lo stato " + professionistaEsternoWf.getStatoWf().getDescrizione() + " del workflow non è compatibile con l'operazione richiesta", "errore.workflow.stato");

		
		//recupero il professionistaEsterno
		professionistaEsternoCorrelato = professionistaEsternoWf.getProfessionistaEsterno();
	
		
		// recupero lo step corrente
		stepCorrente = stepWfDAO.leggiStepCorrente(idProfessionistaEsternoWf, CostantiDAO.AUTORIZZAZIONE_INS_PROFESSIONISTA_ESTERNO_IN_ELENCO);
		
		//chiudo lo step corrente
		stepCorrente.setDataChiusura(dataAvanzamento);
		stepCorrente.setUtenteChiusura(utenteConnesso.getMatricolaUtil());
		stepWfDAO.modifica(stepCorrente);
		
		// recupero la profilazione dell'utente connesso
		responsabileTop = utenteDAO.leggiResponsabileTop();
		
		//verifico se sia il responsabile top
		if(responsabileTop.getUseridUtil().equalsIgnoreCase(userIdUtenteConnesso))
			respTop = true;
		
		if(utenteConnesso.getMatricolaRespUtil().equalsIgnoreCase(responsabileTop.getMatricolaUtil()) || respTop){
				primoRiporto = true;
				respPrimoRiporto = true;
		}
		else{
			responsabileDiretto = utenteDAO.leggiUtenteDaMatricola(utenteConnesso.getMatricolaRespUtil());
			if(responsabileDiretto.getMatricolaRespUtil().equalsIgnoreCase(responsabileTop.getMatricolaUtil()))
				respPrimoRiporto = true;
		}
		
		//recupero lo step di partenza
		//vedo se nel flusso è previsto un avanzamento manuale assegnato all'utente corrente
		//in tal caso il workflow parte direttamente da lì
		configurazioneStart = configurazioneStepWfDAO.leggiConfigurazioneManuale(anagraficaStatiTipiDAO.leggiClasseWf(CostantiDAO.AUTORIZZAZIONE_INS_PROFESSIONISTA_ESTERNO_IN_ELENCO).getId(), utenteConnesso.getMatricolaUtil(), CostantiDAO.LINGUA_ITALIANA);
		
		//se non c'è alcuna assegnazione manuale, il workflow parte dallo step numero 1
		if(configurazioneStart == null)
			configurazioneStart = configurazioneStepWfDAO.leggiConfigurazioneStepNumeroUno(anagraficaStatiTipiDAO.leggiClasseWf(CostantiDAO.AUTORIZZAZIONE_INS_PROFESSIONISTA_ESTERNO_IN_ELENCO).getId(), CostantiDAO.LINGUA_ITALIANA);
		
		//calcolo lo step da mandare in assegnazione
		configurazione = configurazioneStepWfDAO.leggiConfigurazioneSuccessivaStandard(configurazioneStart, primoRiporto, respPrimoRiporto, CostantiDAO.LINGUA_ITALIANA);
		
		if(configurazione == null || configurazione.getTipoAssegnazione() == null){ //Workflow finito
			
			//aggiorno l'occorrenza ProfessionistaEsternoWf
			professionistaEsternoWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_COMPLETATO, CostantiDAO.LINGUA_ITALIANA));
			professionistaEsternoWf.setDataChiusura(dataAvanzamento);
			professionistaEsternoWfDAO.modifica(professionistaEsternoWf);
			
			//aggiorno il professionistaEsterno
			professionistaEsternoCorrelato.setStatoProfessionista(anagraficaStatiTipiDAO.leggiStatoProfessionista(CostantiDAO.PROFESSIONISTA_STATO_AUTORIZZATO, CostantiDAO.LINGUA_ITALIANA));
			//professionistaEsternoCorrelato.setDataUltimaModifica(dataAvanzamento);
			professionistaEsternoCorrelato.setDataAutorizzazione(dataAvanzamento);
			professionistaEsternoCorrelato.setStatoEsitoValutazioneProf(anagraficaStatiTipiDAO.leggiStatoEsitoValutazioneProf(CostantiDAO.PROFESSIONISTA_DA_ATTIVARE, CostantiDAO.LINGUA_ITALIANA));
			
			professionistaEsternoDAO.modifica(professionistaEsternoCorrelato);
		
		}
		else{

			//creo lo step
			creaStepWf(professionistaEsternoWf,configurazione, dataAvanzamento, utenteConnesso);
			
			//aggiorno il professionistaEsterno
			professionistaEsternoCorrelato.setStatoProfessionista(anagraficaStatiTipiDAO.leggiStatoProfessionista(configurazione.getStateTo(), CostantiDAO.LINGUA_ITALIANA));
			professionistaEsternoCorrelato.setDataUltimaModifica(dataAvanzamento);
			professionistaEsternoDAO.modifica(professionistaEsternoCorrelato);
		}
		
		return (stepCorrente.getDataChiusura() != null);
	}
	
	/**
	 * Crea un nuovo steo workflow
	 *
	 * @param professionistaEsternoWf workflow correlato
	 * @param configurazioneStepWf configurazione associata
	 * @throws Throwable eccezione
	 */
	private void creaStepWf(ProfessionistaEsternoWf professionistaEsternoWf, ConfigurazioneStepWf configurazioneStepWf, Timestamp dataCorrente, Utente utenteConnesso) throws Throwable {
		StepWf stepWf = new StepWf();
		stepWf.setDataCreazione(dataCorrente);
		stepWf.setConfigurazioneStepWf(configurazioneStepWf);
		stepWf.setProfessionistaEsternoWf(professionistaEsternoWf);
		stepWf.setUtente(utenteConnesso);
		stepWf.setLang(CostantiDAO.LINGUA_ITALIANA);
		stepWfDAO.creaStepWf(stepWf);
	}
	/**
	 * Metodo di lettura del workflow in corso
	 * <p>
	 * @param idProfessionista: identificativo del professionista
	 * @return oggetto ProfessionistaEsternoWf.
	 * @exception Throwable
	 */
	@Override
	public ProfessionistaEsternoWfView leggiWorkflowInCorso(long idProfessionista) throws Throwable {
		ProfessionistaEsternoWf wf = professionistaEsternoWfDAO.leggiWorkflowInCorso(idProfessionista);
		if(wf != null)
			return (ProfessionistaEsternoWfView) convertiVoInView(wf);
		else
			return null;
	}
	
	public ProfessionistaEsternoWf leggiWorkflowInCorsoNotView(long idProfessionista) throws Throwable {
		ProfessionistaEsternoWf wf = professionistaEsternoWfDAO.leggiWorkflowInCorso(idProfessionista);
		if(wf != null)
			return wf;
		else
			return null;
	}
	
	/**
	 * Metodo di lettura dell'ultimo workflow terminato
	 * <p>
	 * @param idProfessionista: identificativo del professionista
	 * @return oggetto ProfessionistaEsternoWfView.
	 * @exception Throwable
	 */
	@Override
	public ProfessionistaEsternoWfView leggiUltimoWorkflowTerminato(long idProfessionista) throws Throwable {
		ProfessionistaEsternoWf wf = professionistaEsternoWfDAO.leggiUltimoWorkflowTerminato(idProfessionista);
		if(wf != null)
		return (ProfessionistaEsternoWfView) convertiVoInView(wf);
		else
			return null;
	}
	
	/**
	 * Metodo di lettura dell'ultimo workflow rifiutato
	 * <p>
	 * @param idProfessionista: identificativo del professionista
	 * @return oggetto ProfessionistaEsternoWfView.
	 * @exception Throwable
	 */
	@Override
	public ProfessionistaEsternoWfView leggiUltimoWorkflowRifiutato(long idProfessionista) throws Throwable {
		ProfessionistaEsternoWf wf = professionistaEsternoWfDAO.leggiUltimoWorkflowRifiutato(idProfessionista);
		if(wf != null)
		return (ProfessionistaEsternoWfView) convertiVoInView(wf);
		else
			return null;
	}
	
	/**
	 * Metodo di lettura del workflow
	 * <p>
	 * @param id: identificativo del workflow
	 * @return oggetto ProfessionistaEsternoWfView.
	 * @exception Throwable
	 */
	@Override
	public ProfessionistaEsternoWfView leggiWorkflow(long id) throws Throwable {
		ProfessionistaEsternoWf wf = professionistaEsternoWfDAO.leggiWorkflow(id);
		return (ProfessionistaEsternoWfView) convertiVoInView(wf);
	}
	
	/**
	 * Annulla l'ultimo step effettuato nel workflow attivo.
	 * @param idProfessionista identificativo del professionista
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 */
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean discardStep(long idProfessionista, String userIdUtenteConnesso) throws Throwable {

		ProfessionistaEsternoWfView workflowCorrente = null;
		ProfessionistaEsternoWf professionistaWf = null;
		StepWf stepCorrente = null;
		StepWf stepLast = null;
		Utente utenteConnesso = null;
		ProfessionistaEsterno professionista = null;
		
		//recupero l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
				
		//recupero il professionista correlato
		professionista = professionistaEsternoDAO.leggi(idProfessionista);
	 
		Timestamp dataDiscard = new Timestamp(System.currentTimeMillis());

		//recupero il workflow
		workflowCorrente = leggiWorkflowInCorso(idProfessionista);
		professionistaWf = workflowCorrente.getVo();
		
		// recupero lo step corrente
		stepCorrente = stepWfDAO.leggiStepCorrente(workflowCorrente.getVo().getId(), CostantiDAO.AUTORIZZAZIONE_INS_PROFESSIONISTA_ESTERNO_IN_ELENCO);
		
		//effettuo il discard sullo step corrente
		stepCorrente.setDataChiusura(dataDiscard);
		stepCorrente.setUtenteChiusura(utenteConnesso.getMatricolaUtil());
		stepCorrente.setDiscarded(Character.toString(CostantiDAO.TRUE_CHAR));
		stepWfDAO.modifica(stepCorrente);

		//recupero lo step da ripristinare
		stepLast = stepWfDAO.leggiUltimoStepChiuso(workflowCorrente.getVo().getId(), CostantiDAO.AUTORIZZAZIONE_INS_PROFESSIONISTA_ESTERNO_IN_ELENCO);
		if(stepLast != null){
			
			//ripristino lo step
			stepLast.setDataChiusura(null);
			stepLast.setUtenteChiusura(null);
			stepWfDAO.modifica(stepLast);
			
			//aggiorno il professionista
			professionista.setStatoProfessionista(anagraficaStatiTipiDAO.leggiStatoProfessionista(stepLast.getConfigurazioneStepWf().getStateTo(), CostantiDAO.LINGUA_ITALIANA));
			professionista.setDataUltimaModifica(dataDiscard);
			professionistaEsternoDAO.modifica(professionista);
		}
		else{
			
			//chiudo il workflow settandolo in Interrotto
		
			professionistaWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_INTERROTTO, CostantiDAO.LINGUA_ITALIANA));
			professionistaWf.setDataChiusura(dataDiscard);
			professionistaEsternoWfDAO.modifica(professionistaWf);
			
			//riporto il professionista in Bozza
			professionista.setStatoProfessionista(anagraficaStatiTipiDAO.leggiStatoProfessionista(CostantiDAO.PROFESSIONISTA_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA));
			professionista.setDataUltimaModifica(dataDiscard);
			professionistaEsternoDAO.modifica(professionista);
		}
		
		return (stepCorrente.getDataChiusura() != null);
	}

	/**
	 * Riporta in bozza il workflow corrente.
	 * @param idProfessionista identificativo del professionista
	 * @param userIdUtenteConnesso userId dell'utente connesso
	 * @return true se l'operazione è andata a buon fine, false in caso contrario
	 */
	@Transactional( rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@Override
	public boolean riportaInBozzaWorkflow(long idProfessionista, String userIdUtenteConnesso) throws Throwable {

		ProfessionistaEsternoWfView workflowCorrente = null;
		ProfessionistaEsternoWf professionistaWf = null;
		StepWf stepCorrente = null;
		Utente utenteConnesso = null;
		ProfessionistaEsterno professionista = null;
		boolean terminato = false;
		
		//recupero l'utente connesso
		utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
				
		//recupero il professionista correlato
		professionista = professionistaEsternoDAO.leggi(idProfessionista);
	 
		Timestamp dataDiscard = new Timestamp(System.currentTimeMillis());

		//recupero il workflow
		workflowCorrente = leggiWorkflowInCorso(idProfessionista);
		if(workflowCorrente != null){
			
			professionistaWf = workflowCorrente.getVo();
			
			// recupero lo step corrente
			stepCorrente = stepWfDAO.leggiStepCorrente(workflowCorrente.getVo().getId(), CostantiDAO.PROFESSIONISTA_ESTERNO);
		}
		else{
			
			terminato = true;

			workflowCorrente = leggiUltimoWorkflowTerminato(idProfessionista);
			if(workflowCorrente != null){
				professionistaWf = workflowCorrente.getVo();
				// recupero l'ultimo step del workflow recuperato
				stepCorrente = stepWfDAO.leggiUltimoStepChiuso(workflowCorrente.getVo().getId(), CostantiDAO.AUTORIZZAZIONE_INS_PROFESSIONISTA_ESTERNO_IN_ELENCO);
			}
			else //non esiste alcun workflow da riportare in bozza: mi limito a riporre in stato Bozza il professionista
			{
				professionista.setStatoProfessionista(anagraficaStatiTipiDAO.leggiStatoProfessionista(CostantiDAO.PROFESSIONISTA_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA));
				professionista.setDataUltimaModifica(dataDiscard);
				professionista.setDataAutorizzazione(null);
				professionistaEsternoDAO.modifica(professionista);
				return (professionista.getStatoProfessionista().getId() == anagraficaStatiTipiDAO.leggiStatoProfessionista(CostantiDAO.PROFESSIONISTA_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA).getId());

			}
		}
		
		if(!terminato)
			// recupero lo step corrente
			stepCorrente = stepWfDAO.leggiStepCorrente(workflowCorrente.getVo().getId(), CostantiDAO.AUTORIZZAZIONE_INS_PROFESSIONISTA_ESTERNO_IN_ELENCO);
		else
			// recupero l'ultimo step chiuso
			stepCorrente = stepWfDAO.leggiUltimoStepChiuso(workflowCorrente.getVo().getId(), CostantiDAO.AUTORIZZAZIONE_INS_PROFESSIONISTA_ESTERNO_IN_ELENCO);
		
		//effettuo il discard sullo step corrente
		if(terminato){
			stepCorrente.setDataChiusura(dataDiscard);
			stepCorrente.setUtenteChiusura(utenteConnesso.getMatricolaUtil());
		}
		stepCorrente.setDiscarded(Character.toString(CostantiDAO.TRUE_CHAR));
		stepWfDAO.modifica(stepCorrente);


		//chiudo il workflow settandolo in Interrotto
		
		professionistaWf.setStatoWf(anagraficaStatiTipiDAO.leggiStatoWf(CostantiDAO.STATO_WF_INTERROTTO, CostantiDAO.LINGUA_ITALIANA));
		professionistaWf.setDataChiusura(dataDiscard);
		professionistaEsternoWfDAO.modifica(professionistaWf);
		
		//riporto il professionista in Bozza
		professionista.setStatoProfessionista(anagraficaStatiTipiDAO.leggiStatoProfessionista(CostantiDAO.PROFESSIONISTA_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA));
		professionista.setDataUltimaModifica(dataDiscard);
		professionista.setDataAutorizzazione(null);
		professionistaEsternoDAO.modifica(professionista);
		
		return (professionista.getStatoProfessionista().getId() == anagraficaStatiTipiDAO.leggiStatoProfessionista(CostantiDAO.PROFESSIONISTA_STATO_BOZZA, CostantiDAO.LINGUA_ITALIANA).getId());
	}
	
	
}

