package eng.la.business;

import java.util.Date;
//import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

//@@DDS import com.filenet.api.core.Document;
import it.snam.ned.libs.dds.dtos.v2.Document;
import eng.la.model.ArchivioProtocollo;
import eng.la.model.Atto;
import eng.la.model.CategoriaAtto;
import eng.la.model.Documento;
import eng.la.model.EsitoAtto;
import eng.la.model.Fascicolo;
import eng.la.model.Societa;
import eng.la.model.StatoAtto;
import eng.la.model.Utente;
import eng.la.model.mail.JSONMail;
import eng.la.model.view.AttoView;

public interface AttoService {
	public void cancella(long id) throws Throwable;
	public AttoView inserisci(AttoView atto) throws Throwable;
	public void modifica(AttoView atto) throws Throwable;
	
	public List<AttoView> cerca(AttoView view) throws Throwable;
	public AttoView leggi(long id) throws Throwable;
	public List<AttoView> leggi() throws Throwable;
	// extra
	public void startWf() throws Throwable;
	public void creaFascicolo(AttoView atto) throws Throwable;	
	public List<Atto> getListaAtti() throws Throwable;
	public Atto getAtto(long id) throws Throwable;
	public List<Societa> getlistSocieta(boolean tutte) throws Throwable;
	public List<Societa> getlistSocieta() throws Throwable;
	public List<CategoriaAtto> listaCategoriaAtto(String lingua) throws Throwable;
	public CategoriaAtto getCategoria(long id) throws Throwable;
	public Fascicolo getFascicolo(long id) throws Throwable;
	public Societa getSocieta(long id) throws Throwable;
	public StatoAtto getStatoAtto(long id) throws Throwable;
	public List<Utente> getGcDestinatario() throws Throwable;
	public Documento aggiungiDocumento(Date dataCreazione,Long idAtto, MultipartFile file,String nomeFolder) throws Throwable ;
	public Documento aggiungiDocumentoDaUuid(Date dataCreazione,Long idAtto, String uuid,String nomeFolder) throws Throwable ;
	public void aggiungiDocumento(Long attoId, Long categoriaId, List<MultipartFile> files) throws Throwable;
	public Document leggiDocumentoUUID(String uuid) throws Throwable;
	public Utente leggiUtenteDaUserId(String userId) throws Throwable;
	public Atto getAttoPerNumeroProtocollo()  throws Throwable;
	public Atto getAttoPerNumeroProtocollo(String numeroProtocollo)  throws Throwable;
	
	/**
	 * Aggiunta del flag per la gestione degli atti da validare
	 * @author MASSIMO CARUSO 
	 */
	public List<Atto> getCercaAtti(String dal, String al, String numeroProtocollo, long idCategoriaAtto,
			long idSocieta, String tipoAtto,int elementiPerPagina, int numeroPagina,String order, boolean flagAltriUffici, boolean flagValida) throws Throwable;
	/**
	 * Aggiunta del flag per la gestione degli atti da validare
	 * @author MASSIMO CARUSO 
	 */
	public Long countAtti(String dal, String al, String numeroProtocollo, long idCategoriaAtto,
			long idSocieta, String tipoAtto,  boolean flagAltriUffici, boolean flagValida) throws Throwable;
	public void exportExcell(List<Atto> atti,HttpServletResponse respons) throws Throwable;
	public StatoAtto getStatoAttoPerLingua(String lang, String cod_gruppo_lingua) throws Throwable;
	public CategoriaAtto getCategoriaPreLingua(String lang, String cod_gruppo_lingua) throws Throwable;
	public List<Fascicolo> cercaFascicolo(String nome,int elementiPerPagina,int numeroPagina) throws Throwable;
	public Long countFascicolo(String nome) throws Throwable;
	public List<AttoView> cerca(List<String> parole) throws Throwable;
	public byte[] leggiContenutoDocumentoUUID(String uuid) throws Throwable ;
	public List<EsitoAtto> listaEsitoAtto(String lingua) throws Throwable;
	public EsitoAtto getEsito(long id) throws Throwable;
	public Atto getAttoConPemessi(long id) throws Throwable;
	public EsitoAtto getEsitoByCode(String code, String lingua) throws Throwable;
	
	/**
	 * Crea ed inserisce un nuovo atto in archivio
	 * @author MASSIMO CARUSO
	 * @param assegnatario la user id dell'utente che vuole creare l'atto
	 * @return l'atto generato, null altrimenti
	 * @throws Throwable
	 */
	public Atto inserisciNuovoAttoInArchivio(String assegnatario, String idDocumento) throws Throwable;
	
	/**
	 * Salva un documento (email) in FileNet e restituisce il suo ID.
	 * Durante l'operazione viene aggiornato l'atto passato in input
	 * aggiungendo le informazioni contenute nel documento. 
	 * @author MASSIMO CARUSO
	 * @param root_folder il path in cui si trova il file
	 * @param email la email da salvare
	 * @param atto l'atto a cui assegnare il file
	 * @return l'id del file inserito, -1 in caso di errore o file gi� inserito
	 * @throws Throwable
	 */
	public long salvaDocumento(String root_folder,JSONMail email,Atto atto)  throws Throwable;
	
	/**
	 * Rimuove l'atto richiesto
	 * @author MASSIMO CARUSO
	 * @param id_atto l'id dell'atto da rimuovere
	 * @throws Throwable
	 */
	public void rimuoviAtto(long id_atto) throws Throwable;
	
}
