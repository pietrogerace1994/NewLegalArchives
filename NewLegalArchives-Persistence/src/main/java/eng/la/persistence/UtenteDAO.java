package eng.la.persistence;

import java.util.List;

import eng.la.model.GruppoUtente;
import eng.la.model.RUtenteFascicolo;
import eng.la.model.RUtenteGruppo;
import eng.la.model.Utente;

public interface UtenteDAO {

	public Utente leggiUtenteDaMatricola(String matricola) throws Throwable;
	public Utente leggiUtenteDaUserId(String userId) throws Throwable;
	public Utente leggiResponsabileTop() throws Throwable;
	//DARIO *******************************************************************************
	public Utente leggiTopHead() throws Throwable;
	
	public List<Utente> getAssegnatariResponsabileTop() throws Throwable;
	public List<Utente> getAssegnatariLegaliInterni() throws Throwable;
	public List<Utente> getAssegnatariResponsabiliOverHead() throws Throwable;
	public List<Utente> getAssegnatariResponsabiliHead() throws Throwable;
	public List<Utente> getAssegnatariResponsabiliHeadAndUnderHead() throws Throwable;
	public List<Utente> getAssegnatariUnderHead() throws Throwable;
	//*************************************************************************************
	
	//MASSIMO CARUSO***********************************************************************
	/**
	 * Restituisce l'elenco di utenti Head e GC, escludendo l'utente
	 * corrispondente alla matricola passata in input.
	 * @param matricola la matricola dell'utente da non aggiungere nella lista.
	 * @return la lista di utenti.
	 * @author MASSIMO CARUSO
	 */
	public List<Utente> getAssegnatariHead(String matricola);
	
	/**
	 * Restituisce l'elenco di utenti Head, Manager e Team, escludendo l'utente
	 * corrispondente alla matricola passata in input.
	 * @param matricola la matricola dell'utente da non aggiungere nella lista
	 * @return la lista di utenti.
	 * @author MASSIMO CARUSO
	 */
	public List<Utente> getAssegnatariAttoHead(String matricola);
	
	/**
	 * Restituisce l'elenco di utenti Manager e Head, escludendo l'utente
	 * corrispondente alla matricola passata in input.
	 * @param matricola la matricola dell'utente da non aggiungere nella lista
	 * @return la lista di utenti.
	 * @author MASSIMO CARUSO
	 */
	public List<Utente> getAssegnatariManager(String matricola);
	
	/**
	 * Restituisce l'elenco di utenti Manager e Team, escludendo l'utente
	 * corrispondente alla matricola passata in input.
	 * @param matricola la matricola dell'utente da non aggiungere nella lista.
	 * @return la lista di utenti.
	 * @author MASSIMO CARUSO
	 */
	public List<Utente> getAssegnatariAttoManager(String matricola);
	
	/**
	 * Restituisce l'elenco di utenti Head e Manager.
	 * @return la lista di utenti.
	 * @author MASSIMO CARUSO
	 */
	public List<Utente> getAssegnatariTeam();
	
	/**
	 * Restituisce l'elenco di utenti con grado Head.
	 * @return la lista di utenti head.
	 * @author MASSIMO CARUSO
	 */
	public List<Utente> getHeads();
	
	/**
	 * Restituisce l'elenco di utenti Manager.
	 * @return la lista di utenti Manager.
	 * @author MASSIMO CARUSO
	 */
	public List<Utente> getManagers();
	
	/**
	 * Restituisce l'elenco di utenti Team.
	 * @return la lista di utenti Team.
	 * @author MASSIMO CARUSO
	 */
	public List<Utente> getTeams();
	
	/**
	 * Verifica se l'utente associato alla matricola in input è un 
	 * utente Head.
	 * @param matricola la matricola dell'utente.
	 * @return true se l'utente è un membro Head, false altrimenti.
	 * @author MASSIMO CARUSO
	 */
	public boolean isHead(String matricola);
	
	/**
	 * Verifica se l'utente associato alla matricola in input è un 
	 * utente Manager.
	 * @param matricola la matricola dell'utente.
	 * @return true se l'utente è un membro Manager, false altrimenti.
	 * @author MASSIMO CARUSO
	 */
	public boolean isManager(String matricola);
	
	/**
	 * Verifica se l'utente associato alla matricola in input è un 
	 * utente Team.
	 * @param matricola la matricola dell'utente.
	 * @return true se l'utente è un membro Team, false altrimenti.
	 * @author MASSIMO CARUSO
	 */
	public boolean isTeam(String matricola);
	//*************************************************************************************
	
	public List<Utente> leggiResponsabili(String matricola) throws Throwable;
	public List<Utente> leggiCollaboratori(String matricola) throws Throwable;
	public List<Utente> leggiCollaboratoriDiretti(String matricola) throws Throwable;
	public List<Utente> leggiCollaboratoriLegaliInterni(String matricola) throws Throwable;
	public List<Utente> leggiUtentiAmministrativi() throws Throwable;
	public List<Utente> leggiUtentiAmministratori() throws Throwable;
	public boolean leggiSeResponsabileFoglia(String matricola) throws Throwable;
	public boolean leggiSeLegaleInterno(Utente utente) throws Throwable;
	public boolean leggiSeGeneralCounsel(String matricola) throws Throwable;
	public List<Utente> leggiUtentiGestoriPresidioNormativo() throws Throwable;
	public Utente modificaPresenzaUtente(String userId,String assente) throws Throwable;
	public List<Utente> leggiUtentiDaGruppo(GruppoUtente g) throws Throwable;
	
	/**
	 * Recupera l'assegnatario corrente dello step.
	 * @param idStep: identificativo dello step corrente
	 * @return oggetto model utente popolato con i dati inseriti.
	 * @exception Throwable
	 */
	public Utente leggiAssegnatarioCorrenteStandard(long idStep)throws Throwable;
	//DARIO C ********************************************************************
	public Utente leggiAssegnatarioCorrenteStandard(long idStep,String matricolaDestinatario)throws Throwable;
	//****************************************************************************
	
	/**
	 * Recupera il gruppo assegnatario corrente dello step.
	 * @param idStep: identificativo dello step corrente
	 * @return oggetto model gruppoUtente popolato con i dati inseriti.
	 * @exception Throwable
	 */
	public GruppoUtente leggiGruppoAssegnatarioCorrenteStandard(long idStep)throws Throwable;
	
	/**
	 * Recupera l'assegnatario corrente dello step in caso di assegnazione a responsabile
	 * @param idWorkflow: identificativo del workflow corrente
	 * @return oggetto model utente popolato con i dati inseriti.
	 * @exception Throwable
	 */
	public Utente leggiAssegnatarioCorrenteResponsabileAtto(long idWorkflow)throws Throwable;
	
	/**
	 * Recupera l'assegnatario corrente dello step in caso di assegnazione manuale
	 * @param idWorkflow: identificativo del workflow corrente
	 * @return oggetto model utente popolato con i dati inseriti.
	 * @exception Throwable
	 */
	public Utente leggiAssegnatarioCorrenteManualeAtto(long idWorkflow)throws Throwable;
	
	public List<Utente> getUtentiGC()throws Throwable;
	public List<Utente> leggiCollaboratoriResponsabili(String matricola) throws Throwable;
	boolean leggiSeResponsabileSenzaCollaboratori(Utente utente) throws Throwable;
	boolean leggiSeOperatoreSegreteria(Utente utente) throws Throwable;
	public Utente leggiAssegnatarioCorrenteOwnerAtto(long idWorkflow) throws Throwable;
	public List<Utente> leggiAttoriWorkflow(long idWorkflow, String classeWf) throws Throwable;
	public boolean leggiSeAmministratore(Utente utente) throws Throwable;
	public boolean isAmministrativo(Utente utente) throws Throwable;
	public List<Utente> leggiUtenti() throws Throwable;
	
	
	/** riassegnazione > Legale Interno Owner(combo degli owner dei fascioli)*/
	public List<Utente> getListaLegaleInternoOwnerFascicolo(long idFascicolo) throws Throwable;
	public List<Utente> getListaUtentiNotAmmistrativiNotAmministratore() throws Throwable;
	/** riassegnazione >*/  
	public void insertRUtenteFascicolo(RUtenteFascicolo vo) throws Throwable;
	public void updateRUtenteFascicoloDataCancellazione(RUtenteFascicolo vo) throws Throwable;
	public List<Utente> leggiUtenti(boolean tutti) throws Throwable;
	
	public void eliminaRUtenteGruppo(RUtenteGruppo vo);
	public void insertRUtenteGruppo(RUtenteGruppo vo);
	public boolean isAssegnatarioManualeCorrenteStandard(long idStep)throws Throwable;
	public List<String> estraiListaIndirizziMail(List<String> userId)throws Throwable;
	public List<Utente> leggiUtentiAlesocr() throws Throwable;
	public List<Utente> leggiUtentiOperatoriSegreteria() throws Throwable;
	
	public List<Utente> leggiUtentiGestoriFascicoli() throws Throwable;
	boolean isGestorePresidioNormativo(Utente utente) throws Throwable;
	public Utente findUtenteByNominativo(String nominativoUtil)  throws Throwable;
	

	
	
}
