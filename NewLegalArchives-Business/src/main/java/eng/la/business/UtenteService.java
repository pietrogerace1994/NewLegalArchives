package eng.la.business;

import java.util.List;

import eng.la.model.Utente;
import eng.la.model.custom.Numeri;
import eng.la.model.view.UtenteView;

public interface UtenteService {

	public UtenteView leggiAssegnatarioWfCorrente(long idConfigurazione, long idObject) throws Throwable;
	public UtenteView leggiResponsabileTop() throws Throwable;
	public List<UtenteView> leggiResponsabili(String matricola) throws Throwable;
	public UtenteView leggiUtenteDaMatricola(String matricola) throws Throwable;
	public UtenteView leggiUtenteDaUserId(String userId) throws Throwable;
	public List<UtenteView> leggiCollaboratori(String matricola) throws Throwable;
	public List<UtenteView> leggiCollaboratoriDiretti(String matricola) throws Throwable;
	public List<UtenteView> leggiCollaboratoriResponsabili(String matricola) throws Throwable;
	public List<UtenteView> leggiCollaboratoriLegaliInterni(String matricola) throws Throwable;
	public String leggiUrlClick()throws Throwable;
	/**
	 * Metodo che verifica se l'utente fornito in input è un operatore di segreteria.
	 * <p>
	 * @param utente l'utente
	 * @return esito verifica.
	 * @exception Throwable
	 */

	boolean leggiSeOperatoreSegreteria(UtenteView utente) throws Throwable;
	boolean leggiSePrimoRiporto(UtenteView utente) throws Throwable;
	
	//DARIO****************************************************************************
	public UtenteView settaMatricoleTopManagers(UtenteView utente) throws Throwable;
	public List<UtenteView> leggiAssegnatari(UtenteView utente) throws Throwable;
	public List<UtenteView> leggiAssegnatariAtto(UtenteView utente) throws Throwable;
	//*********************************************************************************
	
	boolean leggiSeResponsabileFoglia(UtenteView utente) throws Throwable;
	boolean leggiSeLegaleInterno(UtenteView utente) throws Throwable;
	boolean leggiSeResponsabileSenzaCollaboratori(UtenteView utente) throws Throwable;
	boolean leggiSeAmministratore(UtenteView utente) throws Throwable;
	public List<UtenteView> leggiGC() throws Throwable;
	
	public UtenteView leggiAssegnatarioWfIncarico(long idIncarico) throws Throwable;
	public UtenteView leggiAssegnatarioWfProforma(long idproforma) throws Throwable;
	public UtenteView leggiAssegnatarioWfBeautyContest(long idBeautyContest) throws Throwable;
	public List<UtenteView> leggiAssegnatarioWfSchedaFondoRischi(long idSchedaFondoRischi) throws Throwable;
	
	
	public UtenteView leggiAssegnatarioWfProfessionistaEsterno(long idProfessionista) throws Throwable;
	public UtenteView leggiAssegnatarioWfFascicolo(long idFascicolo) throws Throwable;
	public UtenteView leggiAssegnatarioWfAtto(long idAtto) throws Throwable;
	
	
	public List<UtenteView> leggiAttoriWorkflow(long idWorkflow, String classeWf) throws Throwable;
	public boolean isAmministrativo(UtenteView utente) throws Throwable;

	
	public List<UtenteView> leggiUtenti() throws Throwable;
	public List<UtenteView> leggiUtenti(boolean tutti) throws Throwable;
	public String leggiNominativoExternalUserId(String attribute);
	public List<UtenteView> leggiGestoriPresidioNormativo() throws Throwable;
	public Utente modificaPresenzaUtente(String userId,String assente) throws Throwable;
	public boolean isAssegnatarioManualeCorrenteStandard(long idWorkflowCorrente,String classeWf)throws Throwable;
	public Numeri getNumeriDaUserProfile(String userId) throws Throwable;
	public List<String> estraiListaIndirizziMail(List<String> userId) throws Throwable;

	public List<UtenteView> leggiUtentiAlesocr() throws Throwable;

	public List<UtenteView> leggiUtentiOperatoriSegreteria() throws Throwable;
	
	public List<UtenteView> leggiUtentiGestoriFascicoli() throws Throwable;
	boolean leggiSeGestorePresidioNormativo(UtenteView utente) throws Throwable;
	public Utente findUtenteByNominativo(String nominativoUtil)  throws Throwable;

}
