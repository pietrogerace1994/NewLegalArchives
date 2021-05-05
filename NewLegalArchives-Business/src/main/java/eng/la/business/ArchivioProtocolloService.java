package eng.la.business;

import java.util.List;
import java.util.Locale;

import eng.la.model.ArchivioProtocollo;
import eng.la.model.Utente;
import eng.la.model.mail.JSONMail;
import eng.la.model.view.DocumentoView;
import eng.la.model.view.ProtocolloView;
import eng.la.model.view.UtenteView;
import eng.la.util.ListaPaginata;

//@@DDS import com.filenet.api.core.Folder;

public interface ArchivioProtocolloService {
	
	/**
	 * Crea ed inserisce un nuovo protocollo in archivio
	 * @author MASSIMO CARUSO
	 * @param user la user id dell'utente che vuole creare il protocollo
	 * @param tipo il tipo di protocollo (IN/OUT)
	 * @return il protocollo generato, null altrimenti
	 * @throws Throwable
	 */
	public ArchivioProtocollo inserisciNuovoProtocolloInArchivio(String user, String tipo) throws Throwable;
	
	/**
	 * Salva un documento (email) in FileNet e restituisce il suo ID.
	 * Durante l'operazione viene aggiornato il protocollo passato in input
	 * aggiungendo le informazioni contenute nel documento. 
	 * @author MASSIMO CARUSO
	 * @param root_folder il path in cui si trova il file
	 * @param email la email da salvare
	 * @param protocollo il protocollo a cui assegnare il file
	 * @return l'id del file inserito, -1 in caso di errore o file gi� inserito
	 * @throws Throwable
	 */
	public long salvaDocumento(String root_folder,JSONMail email,ArchivioProtocollo protocollo)  throws Throwable;
	
	/**
	 * Rimuove il protocollo richiesto
	 * @author MASSIMO CARUSO
	 * @param id_protocollo l'id del protocollo da rimuovere
	 * @throws Throwable
	 */
	public void rimuoviProtocollo(long id_protocollo) throws Throwable;
	
	/**
	 * Cambia lo stato del protocollo in "ASSEGNATO".
	 * @author MASSIMO CARUSO
	 * @param idProtocollo l'id del protocollo da cambiare.
	 */
	public void cambiaStatoAdAssegnatoProtocollo(long idProtocollo, String assegnatario) throws Throwable;
	
	public String generaNumeroProtocollo(Utente utente, String utenteS, String unitaAppart, String oggetto,String tipo, Utente utenteConnesso, Locale locale) throws Throwable;

	public ListaPaginata<ProtocolloView> cerca(String numeroProtocollo, String dal, String al, String nomeFascicolo, int numElementiPerPagina, int numeroPagina, String ordinamento,
			String tipoOrdinamento, String tipo, UtenteView utenteConnesso, String statoProtocolloCode) throws Throwable;
	
	public ProtocolloView leggi(long id) throws Throwable;
	
	public ProtocolloView leggi(String numProtocollo) throws Throwable;
	
	public void salvaDocumentoProtocollo(DocumentoView documentoView, Long idProtocollo, Locale locale) throws Throwable;
	
	public void salvaDocumentoProtocolloDaUuid(String uuid, ArchivioProtocollo protocollo, Locale locale) throws Throwable;
	
	public List<ArchivioProtocollo> leggiProtocolliDaAssegnare() throws Throwable;
	
	public List<ArchivioProtocollo> leggiProtocolliAssegnati(Utente utenteConnesso) throws Throwable;

	public void assegnaProtocollo(long idProtocollo, String assegnatario, String commento, Locale locale) throws Throwable;

	public void spostaSuFascicolo(long idProtocollo, long idFascicolo, Locale locale) throws Throwable;

	public void lasciaSuArchivioProtocollo(long idProtocollo, String language) throws Throwable;

}
