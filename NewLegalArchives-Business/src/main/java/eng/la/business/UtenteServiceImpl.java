package eng.la.business;

import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.xml.rpc.ServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.AttoWf;
import eng.la.model.BeautyContestWf;
import eng.la.model.FascicoloWf;
import eng.la.model.GruppoUtente;
import eng.la.model.IncaricoWf;
import eng.la.model.ProfessionistaEsternoWf;
import eng.la.model.ProformaWf;
import eng.la.model.SchedaFondoRischiWf;
import eng.la.model.StepWf;
import eng.la.model.Utente;
import eng.la.model.custom.Numeri;
import eng.la.model.view.UtenteView;
import eng.la.persistence.CostantiDAO;
import eng.la.persistence.UtenteDAO;
import eng.la.persistence.workflow.AttoWfDAO;
import eng.la.persistence.workflow.BeautyContestWfDAO;
import eng.la.persistence.workflow.ConfigurazioneStepWfDAO;
import eng.la.persistence.workflow.FascicoloWfDAO;
import eng.la.persistence.workflow.IncaricoWfDAO;
import eng.la.persistence.workflow.ProfessionistaEsternoWfDAO;
import eng.la.persistence.workflow.ProformaWfDAO;
import eng.la.persistence.workflow.SchedaFondoRischiWfDAO;
import eng.la.persistence.workflow.StepWfDAO;
import eng.la.util.costants.Costanti;
import it.snamretegas.portal.ProfileManagerLitLocator;
import it.snamretegas.portal.UserProfile;

/**
 * <h1>Classe di business UtenteServiceImpl</h1> Classe preposta alla gestione
 * delle operazione di lettura sulla base dati attraverso l'uso delle classi DAO
 * di pertinenza all'operazione.
 * <p>
 * 
 * @author
 * @version 1.0
 * @since 2016-07-01
 */
@Service("utenteService")
public class UtenteServiceImpl extends BaseService<Utente,UtenteView> implements UtenteService {

	@Autowired
	private UtenteDAO utenteDAO;
	@Autowired
	private IncaricoWfDAO incaricoWfDAO;
	@Autowired
	private ProformaWfDAO proformaWfDAO;
	@Autowired
	private ProfessionistaEsternoWfDAO professionistaEsternoWfDAO;
	@Autowired
	private FascicoloWfDAO fascicoloWfDAO;
	@Autowired
	private AttoWfDAO attoWfDAO;
	@Autowired
	private SchedaFondoRischiWfDAO schedaFondoRischiWfDAO;
	@Autowired
	private StepWfDAO stepWfDAO;
	@Autowired
	private BeautyContestWfDAO beautyContestWfDAO;

	private ProfileManagerLitLocator profileLocator = new ProfileManagerLitLocator();

	/**
	 * Metodo di lettura dell'assegnatario corrente del workflow.
	 * <p>
	 * 
	 * @param idConfigurazione:
	 *            identificativo della configurazione dello step corrente
	 * @param idObject:
	 *            identificativo dell'istanza di oggetto corrente
	 * @return oggetto UtenteView.
	 * @exception Throwable
	 */
	@Override
	public UtenteView leggiAssegnatarioWfCorrente(long idConfigurazione, long idObject) throws Throwable {
		Utente assegnatario = stepWfDAO.leggiAssegnatarioCorrente(idConfigurazione, idObject);
		if (assegnatario != null)
			return (UtenteView) convertiVoInView(assegnatario);
		else
			return null;
	}

	/**
	 * Metodo di lettura dell'assegnatario corrente del workflow incarico.
	 * <p>
	 * 
	 * @param idIncarico:
	 *            identificativo dell'incarico
	 * @return oggetto UtenteView.
	 * @exception Throwable
	 */
	
	
		
	@Override
	public UtenteView leggiAssegnatarioWfIncarico(long idIncarico) throws Throwable {
		
		
		StepWf step = null;

		// recupero il workflow
		IncaricoWf workflowCorrente = incaricoWfDAO.leggiWorkflowInCorso(idIncarico);
		if (workflowCorrente == null)
			return null;

		//DARIO C ***********************************************************************
		String matricolaDestinatario = workflowCorrente.getUtenteAssegnatario(); 
		matricolaDestinatario = matricolaDestinatario==null ? "":matricolaDestinatario;
		//*******************************************************************************
		
		// recupero lo step corrente
		step = stepWfDAO.leggiStepCorrente(workflowCorrente.getId(), CostantiDAO.AUTORIZZAZIONE_INCARICO);
		if (step != null) {
			
			//DARIO C ********************************************************************************************
			//Utente assegnatario = utenteDAO.leggiAssegnatarioCorrenteStandard(step.getId());
			Utente assegnatario = utenteDAO.leggiAssegnatarioCorrenteStandard(step.getId(),matricolaDestinatario);
			//****************************************************************************************************
			
			if (assegnatario != null){
				return (UtenteView) convertiVoInView(assegnatario);
			}
			else
				return null;
		} else
			return null;
	}

	/**
	 * Metodo di lettura dell'assegnatario corrente del workflow proforma.
	 * <p>
	 * 
	 * @param idProforma:
	 *            identificativo del proforma
	 * @return oggetto UtenteView.
	 * @exception Throwable
	 */
	
	@Override
	public UtenteView leggiAssegnatarioWfProforma(long idProforma) throws Throwable {
				
		StepWf step = null;

		// recupero il workflow
		ProformaWf workflowCorrente = proformaWfDAO.leggiWorkflowInCorso(idProforma);
		if (workflowCorrente == null)
			return null;

		//DARIO C ***********************************************************************
		String matricolaDestinatario = workflowCorrente.getUtenteAssegnatario(); 
		matricolaDestinatario = matricolaDestinatario==null ? "":matricolaDestinatario;
		//*******************************************************************************
		
		// recupero lo step corrente
		step = stepWfDAO.leggiStepCorrente(workflowCorrente.getId(), CostantiDAO.AUTORIZZAZIONE_PROFORMA);
		if (step != null) {
			
			//DARIO C ********************************************************************************************
			//Utente assegnatario = utenteDAO.leggiAssegnatarioCorrenteStandard(step.getId());
			Utente assegnatario = utenteDAO.leggiAssegnatarioCorrenteStandard(step.getId(),matricolaDestinatario);
			//****************************************************************************************************
			
			
			if (assegnatario != null)
				return (UtenteView) convertiVoInView(assegnatario);
			else
				return null;
		} else
			return null;
	}

	/**
	 * Metodo di lettura dell'assegnatario corrente del workflow professionista.
	 * <p>
	 * 
	 * @param idProfessionista:
	 *            identificativo del professionista
	 * @return oggetto UtenteView.
	 * @exception Throwable
	 */
	@Override
	public UtenteView leggiAssegnatarioWfProfessionistaEsterno(long idProfessionista) throws Throwable {
		StepWf step = null;

		// recupero il workflow
		ProfessionistaEsternoWf workflowCorrente = professionistaEsternoWfDAO.leggiWorkflowInCorso(idProfessionista);
		if (workflowCorrente == null)
			return null;

		// recupero lo step corrente
		step = stepWfDAO.leggiStepCorrente(workflowCorrente.getId(),
				CostantiDAO.AUTORIZZAZIONE_INS_PROFESSIONISTA_ESTERNO_IN_ELENCO);
		if (step != null) {
			Utente assegnatario = utenteDAO.leggiAssegnatarioCorrenteStandard(step.getId());
			if (assegnatario != null)
				return (UtenteView) convertiVoInView(assegnatario);
			else
				return null;
		} else
			return null;
	}

	/**
	 * Metodo di lettura dell'assegnatario corrente del workflow fascicolo.
	 * <p>
	 * 
	 * @param idFascicolo:
	 *            identificativo del fascicolo
	 * @return oggetto UtenteView.
	 * @exception Throwable
	 */
	@Override
	public UtenteView leggiAssegnatarioWfFascicolo(long idFascicolo) throws Throwable {
		StepWf step = null;

		// recupero il workflow
		FascicoloWf workflowCorrente = fascicoloWfDAO.leggiWorkflowInCorso(idFascicolo);
		if (workflowCorrente == null)
			return null;

		// recupero lo step corrente
		step = stepWfDAO.leggiStepCorrente(workflowCorrente.getId(), CostantiDAO.CHIUSURA_FASCICOLO);
		if (step != null) {
			Utente assegnatario = utenteDAO.leggiAssegnatarioCorrenteStandard(step.getId());
			if (assegnatario != null)
				return (UtenteView) convertiVoInView(assegnatario);
			else
				return null;
		} else
			return null;
	}

	/**
	 * Metodo di lettura dell'assegnatario corrente del workflow atto.
	 * <p>
	 * 
	 * @param idFascicolo:
	 *            identificativo dell'atto
	 * @return oggetto UtenteView.
	 * @exception Throwable
	 */
	@Override
	public UtenteView leggiAssegnatarioWfAtto(long idAtto) throws Throwable {
		StepWf step = null;

		// recupero il workflow
		AttoWf workflowCorrente = attoWfDAO.leggiWorkflowInCorso(idAtto);
		if (workflowCorrente == null)
			return null;

		// recupero lo step corrente
		step = stepWfDAO.leggiStepCorrente(workflowCorrente.getId(), CostantiDAO.REGISTRAZIONE_ATTO);
		if (step == null)
			return null;
		else {
			if (step.getConfigurazioneStepWf().getTipoAssegnazione().equalsIgnoreCase(CostantiDAO.ASSEGNAZIONE_OWNER)) {
				Utente assegnatario = utenteDAO.leggiAssegnatarioCorrenteManualeAtto(workflowCorrente.getId());
				if (assegnatario != null)
					return (UtenteView) convertiVoInView(assegnatario);
				else
					return null;
			}

			else if (step.getConfigurazioneStepWf().getNumeroAzioneStep().contains(CostantiDAO.RIFIUTO)) {
				Utente assegnatario = utenteDAO.leggiAssegnatarioCorrenteStandard(step.getId());
				if (assegnatario != null)
					return (UtenteView) convertiVoInView(assegnatario);
				else
					return null;
			} else {
				Utente assegnatario = utenteDAO.leggiAssegnatarioCorrenteManualeAtto(workflowCorrente.getId());
				if (assegnatario != null)
					return (UtenteView) convertiVoInView(assegnatario);
				else
					return null;
			}
		}
	}

	/**
	 * Metodo di lettura dell'assegnatario corrente del workflow schedaFondoRischi.
	 * <p>
	 * 
	 * @param idSchedaFondoRischi:
	 *            identificativo della schedaFondoRischi
	 * @return oggetto UtenteView.
	 * @exception Throwable
	 */
	
		
	@Override
	public List<UtenteView> leggiAssegnatarioWfSchedaFondoRischi(long idSchedaFondoRischi) throws Throwable {
		
		StepWf step = null;
		List<UtenteView> utenteViewList = new ArrayList<UtenteView>(); 
		// recupero il workflow
		SchedaFondoRischiWf workflowCorrente = schedaFondoRischiWfDAO.leggiWorkflowInCorso(idSchedaFondoRischi);
		if (workflowCorrente == null)
			return null;
		
		//DARIO C ***********************************************************************
		String matricolaDestinatario = workflowCorrente.getUtenteAssegnatario(); 
		matricolaDestinatario = matricolaDestinatario==null ? "":matricolaDestinatario;
		//*******************************************************************************
		
		// recupero lo step corrente
		step = stepWfDAO.leggiStepCorrente(workflowCorrente.getId(), CostantiDAO.AUTORIZZAZIONE_SCHEDA_FONDO_RISCHI);
		if (step != null) {
			
			
			//DARIO C ********************************************************************************************
			//Utente assegnatario = utenteDAO.leggiAssegnatarioCorrenteStandard(step.getId());
			Utente assegnatario = utenteDAO.leggiAssegnatarioCorrenteStandard(step.getId(),matricolaDestinatario);
			//****************************************************************************************************
			
			
			if (assegnatario != null){
				UtenteView utenteView = (UtenteView) convertiVoInView(assegnatario);
				utenteViewList.add(utenteView);
				return utenteViewList;
			}
			else {
				GruppoUtente gruppoUtente = utenteDAO.leggiGruppoAssegnatarioCorrenteStandard(step.getId());

				if(gruppoUtente != null){

					List<Utente> gruppoUtenti = utenteDAO.leggiUtentiDaGruppo(gruppoUtente);
					for(Utente user : gruppoUtenti){
						UtenteView utenteView = (UtenteView) convertiVoInView(user);
						utenteViewList.add(utenteView);
					}
					return utenteViewList;
				}
			}
			return null;
		} else
			return null;
	}

	/**
	 * Metodo di lettura dell'assegnatario corrente del workflow BeautyContest.
	 * <p>
	 * 
	 * @param idBeautyContest: identificativo del bc
	 * @return oggetto UtenteView.
	 * @exception Throwable
	 */
	
	@Override
	public UtenteView leggiAssegnatarioWfBeautyContest(long idBeautyContest) throws Throwable {
		
		
		
		StepWf step = null;

		// recupero il workflow
		BeautyContestWf workflowCorrente = beautyContestWfDAO.leggiWorkflowInCorso(idBeautyContest);
		if (workflowCorrente == null)
			return null;

		//DARIO C ***********************************************************************
		String matricolaDestinatario = workflowCorrente.getUtenteAssegnatario(); 
		matricolaDestinatario = matricolaDestinatario==null ? "":matricolaDestinatario;
		//*******************************************************************************
		
		// recupero lo step corrente
		step = stepWfDAO.leggiStepCorrente(workflowCorrente.getId(), CostantiDAO.AUTORIZZAZIONE_BEAUTY_CONTEST);
		if (step != null) {
			
			//DARIO C ********************************************************************************************
			//Utente assegnatario = utenteDAO.leggiAssegnatarioCorrenteStandard(step.getId());
			Utente assegnatario = utenteDAO.leggiAssegnatarioCorrenteStandard(step.getId(),matricolaDestinatario);
			//****************************************************************************************************
			
		
			if (assegnatario != null){
				return (UtenteView) convertiVoInView(assegnatario);
			}
			else
				return null;
		} else
			return null;
	}

	@Override
	protected Class<Utente> leggiClassVO() {
		return Utente.class;
	}

	@Override
	protected Class<UtenteView> leggiClassView() {
		return UtenteView.class;
	}

	/**
	 * Metodo di set della istanza DAO passata come argomento, al corrispondente
	 * membro di classe.
	 * <p>
	 * 
	 * @param dao
	 *            oggetto della classe ConfigurazioneStepWfDAO
	 * @see ConfigurazioneStepWfDAO
	 */
	public void setUtenteDAO(UtenteDAO utenteDAO) {
		this.utenteDAO = utenteDAO;
	}

	/**
	 * Metodo di lettura del responsabile top della gerarchia.
	 * <p>
	 * 
	 * @return oggetto UtenteView.
	 * @exception Throwable
	 */
	@Override
	public UtenteView leggiResponsabileTop() throws Throwable {
		Utente topResponsabile = utenteDAO.leggiResponsabileTop();

		return (UtenteView) convertiVoInView(topResponsabile);

	}

	/**
	 * Metodo di lettura della catena di responsabili dell'utente in input.
	 * <p>
	 * 
	 * @return lista oggetti UserView.
	 * @exception Throwable
	 */
	@Override
	public List<UtenteView> leggiResponsabili(String matricola) throws Throwable {
		List<Utente> lista = utenteDAO.leggiResponsabili(matricola);
		List<UtenteView> listaRitorno = (List<UtenteView>) convertiVoInView(lista);
		return listaRitorno;
	}

	/**
	 * Metodo che verifica se l'utente fornito in input è un responsabile di
	 * primo riporto.
	 * <p>
	 * 
	 * @param utente
	 *            l'utente
	 * @return esito verifica.
	 * @exception Throwable
	 */
	@Override
	public boolean leggiSePrimoRiporto(UtenteView utente) throws Throwable {
		boolean result = false;
		UtenteView responsabileTop = leggiResponsabileTop();
		Utente voUtente = utente.getVo();
		Utente voResponsabileTop = responsabileTop.getVo();
		if(voResponsabileTop != null){
			
			if(Objects.equals(voUtente.getMatricolaRespUtil(), voResponsabileTop.getMatricolaUtil())){
				
				result = true;
			}
		}
		return result;
	}

	
	//DARIO*********************************************************************************************
	@Override
	public UtenteView settaMatricoleTopManagers(UtenteView utente)
			throws Throwable {
		// TODO Auto-generated method stub
		
		//--------------------------------------------------
		
		Utente voResponsabileTop = utenteDAO.leggiResponsabileTop();
		Utente voTopHead = utenteDAO.leggiTopHead();
		
		utente.setMatricolaDelTopResponsabile(voResponsabileTop.getMatricolaUtil());
		utente.setMatricolaDelTopHead(voTopHead.getMatricolaUtil());
		

		return utente;
		
		
	}
	
	@Override
	public List<UtenteView> leggiAssegnatari(UtenteView utente) throws Throwable {
		
		if (utente.isTopResponsabile()){
			return null;	
		}
		
		List<Utente> lista = null;
		
		//MASSIMO CARUSO*************************************************************************
		String matricola = utente.getVo().getMatricolaUtil();

		if(utenteDAO.isHead(matricola)){
			lista = utenteDAO.getAssegnatariHead(matricola);
		}else if(utenteDAO.isManager(matricola)){
			lista = utenteDAO.getAssegnatariManager(matricola);
		}else if(utenteDAO.isTeam(matricola)){
			lista = utenteDAO.getAssegnatariTeam();
		}else{
			//utente GC
			return null;
		}
		//***************************************************************************************
		
//		if (utente.isLegaleInterno()){
//			lista = utenteDAO.getAssegnatariResponsabiliHeadAndUnderHead();
//		}else{
//			
//			if (!utente.isResponsabile()){
//				return null;
//			}
//			if (utente.isTopHead()){
//				lista = utenteDAO.getAssegnatariResponsabileTop();
//			}
//			if (utente.isPrimoRiporto()){
//				lista = utenteDAO.getAssegnatariResponsabiliOverHead();
//			}else{
//				lista = utenteDAO.getAssegnatariResponsabiliHead();
//			}	
//		}
		
		List<UtenteView> listaRitorno = (List<UtenteView>)convertiVoInView(lista);
		
		if (listaRitorno==null) return listaRitorno;
		for (UtenteView utenteView:listaRitorno){
			utenteView.setMatricolaDelTopResponsabile(utente.getMatricolaDelTopResponsabile());
			utenteView.setMatricolaDelTopHead(utente.getMatricolaDelTopHead());
		}
		
		return listaRitorno;
		
	}
	@Override
	public List<UtenteView> leggiAssegnatariAtto(UtenteView utente) throws Throwable {
		
		
		
		List<Utente> lista = null;
		
		
		//MASSIMO CARUSO*************************************************************************
		String matricola = utente.getVo().getMatricolaUtil();

		if(utenteDAO.isHead(matricola)){
			lista = utenteDAO.getAssegnatariAttoHead(matricola);
		}else if(utenteDAO.isManager(matricola)){
			lista = utenteDAO.getAssegnatariAttoManager(matricola);
		}else{
			//Utente team
			return null;
		}
		//***************************************************************************************

		
//		if (!utente.isResponsabile() || utente.isTopResponsabile()){
//			return null;
//			
//		}else{
//			if (utente.isPrimoRiporto()){
//				lista = utenteDAO.getAssegnatariUnderHead();
//			}else{
//				
//				lista = utenteDAO.getAssegnatariLegaliInterni();
//			}	
//		}
		
		List<UtenteView> listaRitorno = (List<UtenteView>)convertiVoInView(lista);
		
		if (listaRitorno==null) return listaRitorno;
		for (UtenteView utenteView:listaRitorno){
			utenteView.setMatricolaDelTopResponsabile(utente.getMatricolaDelTopResponsabile());
			utenteView.setMatricolaDelTopHead(utente.getMatricolaDelTopHead());
		}
		
		return listaRitorno;
		
	}
	
	//******************************************************************************************
	
	

	

	/**
	 * Metodo che verifica se l'utente fornito in input è un responsabile foglia
	 * <p>
	 * 
	 * @param utente
	 *            l'utente
	 * @return esito verifica.
	 * @exception Throwable
	 */
	@Override
	public boolean leggiSeResponsabileFoglia(UtenteView utente) throws Throwable {
		if (!utente.isResponsabile())
			return false;
		else {
			List<UtenteView> responsabili = leggiCollaboratoriResponsabili(utente.getVo().getMatricolaUtil());
			if (responsabili.size() > 0)
				return false;
			else
				return true;
		}
	}

	/**
	 * Metodo di lettura dell'utente dalla matricola.
	 * <p>
	 * 
	 * @param matricola:
	 *            matricola dell'utente
	 * @return oggetto UtenteView.
	 * @exception Throwable
	 */
	@Override
	public UtenteView leggiUtenteDaMatricola(String matricola) throws Throwable {
		Utente utente = utenteDAO.leggiUtenteDaMatricola(matricola);

		return (UtenteView) convertiVoInView(utente);
	}

	/**
	 * Metodo di lettura dell'utente dallo userId.
	 * <p>
	 * 
	 * @param matricola:
	 *            userId dell'utente
	 * @return oggetto UtenteView.
	 * @exception Throwable
	 */
	@Override
	public UtenteView leggiUtenteDaUserId(String userId) throws Throwable {
		Utente utente = utenteDAO.leggiUtenteDaUserId(userId);

		return (UtenteView) convertiVoInView(utente);
	}

	/**
	 * Metodo di lettura degli utenti di cui l'utente in input risulti
	 * responsabile di qualsiasi livello.
	 * <p>
	 * 
	 * @param matricola:
	 *            matricola dell'utente
	 * @return lista oggetti UtenteView.
	 * @exception Throwable
	 */
	@Override
	public List<UtenteView> leggiCollaboratori(String matricola) throws Throwable {
		List<Utente> lista = utenteDAO.leggiCollaboratori(matricola);
		List<UtenteView> listaRitorno = (List<UtenteView>) convertiVoInView(lista);
		return listaRitorno;
	}

	/**
	 * Metodo di lettura degli utenti di cui l'utente in input risulti
	 * responsabile diretto.
	 * <p>
	 * 
	 * @param matricola:
	 *            matricola dell'utente
	 * @return lista oggetti UtenteView.
	 * @exception Throwable
	 */
	@Override
	public List<UtenteView> leggiCollaboratoriDiretti(String matricola) throws Throwable {
		List<Utente> lista = utenteDAO.leggiCollaboratoriDiretti(matricola);
		List<UtenteView> listaRitorno = (List<UtenteView>) convertiVoInView(lista);
		return listaRitorno;
	}

	/**
	 * Metodo di lettura degli utenti di cui l'utente in input risulti
	 * responsabile diretto.
	 * <p>
	 * 
	 * @param matricola:
	 *            matricola dell'utente
	 * @return lista oggetti UtenteView.
	 * @exception Throwable
	 */
	@Override
	public List<UtenteView> leggiCollaboratoriResponsabili(String matricola) throws Throwable {
		List<Utente> lista = utenteDAO.leggiCollaboratoriResponsabili(matricola);
		List<UtenteView> listaRitorno = (List<UtenteView>) convertiVoInView(lista);
		return listaRitorno;
	}

	/**
	 * Metodo di lettura dei legali interni di cui l'utente in input risulti
	 * responsabile diretto.
	 * <p>
	 * 
	 * @param matricola:
	 *            matricola dell'utente
	 * @return lista oggetti UtenteView.
	 * @exception Throwable
	 */
	@Override
	public List<UtenteView> leggiCollaboratoriLegaliInterni(String matricola) throws Throwable {
		List<Utente> lista = utenteDAO.leggiCollaboratoriLegaliInterni(matricola);
		List<UtenteView> listaRitorno = (List<UtenteView>) convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	public List<UtenteView> leggiGestoriPresidioNormativo() throws Throwable {
		List<Utente> lista = utenteDAO.leggiUtentiGestoriPresidioNormativo();
		List<UtenteView> listaRitorno = (List<UtenteView>) convertiVoInView(lista);
		return listaRitorno;
	}

	/**
	 * Metodo di lettura degli utenti responsabili di primo riporto (GC).
	 * <p>
	 * 
	 * @param matricola:
	 *            matricola dell'utente
	 * @return lista oggetti UtenteView.
	 * @exception Throwable
	 */
	@Override
	public List<UtenteView> leggiGC() throws Throwable {
		List<Utente> lista = utenteDAO.getUtentiGC();
		List<UtenteView> listaRitorno = (List<UtenteView>) convertiVoInView(lista);
		return listaRitorno;
	}

	/**
	 * Metodo che verifica se l'utente fornito in input è un operatore di
	 * segreteria.
	 * <p>
	 * 
	 * @param utente
	 *            l'utente
	 * @return esito verifica.
	 * @exception Throwable
	 */
	@Override
	public boolean leggiSeOperatoreSegreteria(UtenteView utente) throws Throwable {
		return utenteDAO.leggiSeOperatoreSegreteria(utente.getVo());
	}

	/**
	 * Metodo che verifica se l'utente fornito in input è un legale interno
	 * <p>
	 * 
	 * @param utente
	 *            l'utente
	 * @return esito verifica.
	 * @exception Throwable
	 */
	@Override
	public boolean leggiSeLegaleInterno(UtenteView utente) throws Throwable {
		return utenteDAO.leggiSeLegaleInterno(utente.getVo());
	}

	/**
	 * Metodo che verifica se l'utente fornito in input è un responsabile privo
	 * di collaboratori
	 * <p>
	 * 
	 * @param utente
	 *            l'utente
	 * @return esito verifica.
	 * @exception Throwable
	 */
	@Override
	public boolean leggiSeResponsabileSenzaCollaboratori(UtenteView utente) throws Throwable {
		return utenteDAO.leggiSeResponsabileSenzaCollaboratori(utente.getVo());
	}

	/**
	 * Metodo che verifica se l'utente fornito in input è un amministratore
	 * <p>
	 * 
	 * @param utente
	 *            l'utente
	 * @return esito verifica.
	 * @exception Throwable
	 */
	@Override
	public boolean leggiSeAmministratore(UtenteView utente) throws Throwable {
		return utenteDAO.leggiSeAmministratore(utente.getVo());
	}

	@Override
	public boolean leggiSeGestorePresidioNormativo(UtenteView utente) throws Throwable {
		return utenteDAO.isGestorePresidioNormativo(utente.getVo());
	}

	@Override
	public boolean isAmministrativo(UtenteView utente) throws Throwable {
		return utenteDAO.isAmministrativo(utente.getVo());
	}

	/**
	 * Metodo di lettura degli utenti che hanno preso parte attiva ad un
	 * workflow.
	 * <p>
	 * 
	 * @param idWorkflow
	 *            identificatico del workflow
	 * @param classeWf
	 *            la tipologia di workflow
	 * @return lista oggetti UtenteView.
	 * @exception Throwable
	 */
	@Override
	public List<UtenteView> leggiAttoriWorkflow(long idWorkflow, String classeWf) throws Throwable {
		List<Utente> lista = utenteDAO.leggiAttoriWorkflow(idWorkflow, classeWf);
		List<UtenteView> listaRitorno = (List<UtenteView>)convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	public List<UtenteView> leggiUtenti() throws Throwable {
		List<Utente> lista = utenteDAO.leggiUtenti();
		List<Utente> listaResult = null;
		
		if(lista != null && !lista.isEmpty()){
			
			for(Utente utente : lista){
				
				if(utente.getRUtenteGruppos() != null && !utente.getRUtenteGruppos().isEmpty()){
					
					if(listaResult == null)
						listaResult = new ArrayList<Utente>();
					
					listaResult.add(utente);
				}
			}
		}
		List<UtenteView> listaRitorno = (List<UtenteView>) convertiVoInView(listaResult);
		return listaRitorno;
	}

	@Override
	public List<UtenteView> leggiUtenti(boolean tutti) throws Throwable {
		List<Utente> lista = utenteDAO.leggiUtenti(tutti);
		List<Utente> listaResult = null;
		
		if(lista != null && !lista.isEmpty()){
			
			for(Utente utente : lista){
				
				if(utente.getRUtenteGruppos() != null && !utente.getRUtenteGruppos().isEmpty()){
					
					if(listaResult == null)
						listaResult = new ArrayList<Utente>();
					
					listaResult.add(utente);
				}
			}
		}
		
		List<UtenteView> listaRitorno = (List<UtenteView>) convertiVoInView(listaResult);
		return listaRitorno;
	}

	@Override
	public String leggiNominativoExternalUserId(String userId) {
		try {
			configuraLocator();
			UserProfile userProfile = profileLocator.getProfileManagerSoap().getUserProfile(userId);
			return userProfile.getLastName() + ", " + userProfile.getFirstName();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return Costanti.USERID_GUEST;
	}

	private void configuraLocator() {
		try {
			if (profileLocator == null) {
				synchronized (UtenteServiceImpl.class) { 
					ResourceBundle bundle = ResourceBundle.getBundle("config");
					profileLocator.setEndpointAddress("ProfileManagerSoap",
							bundle.getString("ws.endpointAddress.profileManager"));

				}
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Utente modificaPresenzaUtente(String userId, String assente) throws Throwable {

		return utenteDAO.modificaPresenzaUtente(userId, assente);
	}


	@Override
	public boolean isAssegnatarioManualeCorrenteStandard(long idWorkflowCorrente, String classeWf)throws Throwable {

		StepWf step = null;

		step = stepWfDAO.leggiStepCorrente(idWorkflowCorrente, classeWf);
		if (step != null)
			return utenteDAO.isAssegnatarioManualeCorrenteStandard(step.getId());
		else
			return true;// blocco il ciclo		
	}

	public List<String> estraiListaIndirizziMail(List<String> userId) throws Throwable {
		List<String> indirizziMail = utenteDAO.estraiListaIndirizziMail(userId);
		return indirizziMail;
	}


	public static void main(String[] args) {
		UtenteServiceImpl serviceImpl = new UtenteServiceImpl();
		try {
			System.out.println(serviceImpl.getNumeriDaUserProfile("RI04738").toString());
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	@Override
	public Numeri getNumeriDaUserProfile(String userId) throws Throwable {
		Numeri numeri = new Numeri();
		try {
			configuraLocator();
			UserProfile userProfile = profileLocator.getProfileManagerSoap().getUserProfile(userId);
			numeri.setNumTelefono(userProfile.getTelephoneNumber());
			numeri.setNumFax(userProfile.getFax());

			System.out.println(userProfile.getFirstName());

			return numeri;
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return numeri;
	}

	@Override
	public List<UtenteView> leggiUtentiAlesocr() throws Throwable {
		List<Utente> lista = utenteDAO.leggiUtentiAlesocr();
		List<UtenteView> listaRitorno = (List<UtenteView>) convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	public List<UtenteView> leggiUtentiOperatoriSegreteria() throws Throwable {
		List<Utente> lista = utenteDAO.leggiUtentiOperatoriSegreteria();
		List<UtenteView> listaRitorno = (List<UtenteView>) convertiVoInView(lista);
		return listaRitorno;
	}	

	@Override
	public List<UtenteView> leggiUtentiGestoriFascicoli() throws Throwable {
		List<Utente> lista = utenteDAO.leggiUtentiGestoriFascicoli();
		List<UtenteView> listaRitorno = (List<UtenteView>) convertiVoInView(lista);
		return listaRitorno;
	}

	@Override
	public String leggiUrlClick() throws Throwable {
		InputStream is = null;
		String urlClick = "";
		try {
			is = UtenteServiceImpl.class.getResourceAsStream("/config.properties");
			Properties properties = new Properties();
			properties.load(is);
			urlClick = properties.getProperty("urlClick");
		}
		catch (Exception e) {
		} 
		return urlClick;
	}

	@Override
	public Utente findUtenteByNominativo(String nominativoUtil) throws Throwable {
		return this.utenteDAO.findUtenteByNominativo(nominativoUtil);
	}


}
