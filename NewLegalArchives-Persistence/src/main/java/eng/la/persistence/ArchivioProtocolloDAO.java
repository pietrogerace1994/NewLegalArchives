package eng.la.persistence;



import java.util.List;

import eng.la.model.ArchivioProtocollo;
import eng.la.model.Utente;
import eng.la.model.view.UtenteView;


public interface ArchivioProtocolloDAO {
	
	/**
	 * Rimuove il protocollo richiesto
	 * @author MASSIMO CARUSO
	 * @param id_protocollo l'id del protocollo da rimuovere
	 * @throws Throwable
	 */
	public void removeArchivioProtocollo(long id_protocollo) throws Throwable;

	public ArchivioProtocollo insertArchivioProtocolloFirst(ArchivioProtocollo archivioProtocollo) throws Throwable;

	public ArchivioProtocollo getPrevArchivioProtocollo() throws Throwable;
	
	public ArchivioProtocollo updateArchivioProtocollo(ArchivioProtocollo archivioProtocollo) throws Throwable;

	public List<ArchivioProtocollo> cerca(String numeroProtocollo, String dal, String al, String nomeFascicolo, int numElementiPerPagina, int numeroPagina, String ordinamento, String tipoOrdinamento, String tipo, UtenteView utenteConnesso, String statoProtocolloCode) throws Throwable;

	public Long conta(String numeroProtocollo, String dal, String al, String nomeFascicolo, String tipo, UtenteView utenteConnesso, String statoProtocolloCode) throws Throwable;

	public ArchivioProtocollo leggi(long id) throws Throwable;
	
	public ArchivioProtocollo leggi(String numProtocollo) throws Throwable;

	public List<ArchivioProtocollo> leggiProtocolliDaAssegnare() throws Throwable;

	public List<ArchivioProtocollo> leggiProtocolliAssegnati(Utente utenteConnesso)throws Throwable;
}
