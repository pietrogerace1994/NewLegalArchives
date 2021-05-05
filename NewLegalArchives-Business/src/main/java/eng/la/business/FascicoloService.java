/*
 * @author Luigi Nardiello
 */
package eng.la.business;

import java.util.Collection;
import java.util.List;

import org.hibernate.FetchMode;
import org.springframework.web.multipart.MultipartFile;

import eng.la.model.view.FascicoloView;
import eng.la.model.view.ParteCivileView;
import eng.la.model.view.PersonaOffesaView;
import eng.la.model.view.ResponsabileCivileView;
import eng.la.model.view.SoggettoIndagatoView;
import eng.la.util.ListaPaginata;

/**
 * Interface FascicoloService.
 */
public interface FascicoloService {

	/**
	 * Ritorna la Collectiona completa dei fascicoli.
	 *
	 * @return FascicoloService
	 * @throws Throwable
	 */
	public Collection<FascicoloView> leggi() throws Throwable;

	/**
	 * Ritorna il fascicolo con id = @param id.
	 *
	 * @param id
	 * @return FascicoloService
	 * @throws Throwable
	 */
	public FascicoloView leggi(long id) throws Throwable;

	public List<FascicoloView> cerca(List<String> parole) throws Throwable;
	public ListaPaginata<FascicoloView> cerca(List<String> parole, String dal, String al, int elementiPerPagina, int numeroPagina, String ordinamento, String tipoOrdinamento, String tipoPermesso)  throws Throwable;
	public List<FascicoloView> cerca(String nome, String oggetto) throws Throwable;
 
	/**
	 * Cerca fra tutti i fascicoli quelli che rispondono ai criteri di filtro
	 * impostati e li ritorna come Collection
	 *
	 * @param nome
	 * @param oggetto
	 * @param tipoOrdinamento 
	 * @param ordinamento 
	 * @param numeroPagina 
	 * @param elementiPerPagina 
	 * @param legaleEsterno 
	 * @param controparte 
	 * @param tipologiaAtto 
	 * @param tipoAzione 
	 * @param al 
	 * @param dal 
	 * @return Collection<FascicoloView>  
	 * @throws Throwable
	 *             the throwable
	 */
	public Collection<FascicoloView> cerca(String nome, String oggetto, String descrizione, String siglaCliente, String autoritaGiudiziaria, String controinteressato, String dal, String al, long tipologiaFascicoloId,
			long settoreGiuridicoId, String controparte, String legaleEsterno, int elementiPerPagina, int numeroPagina, String ordinamento, String tipoOrdinamento, String tipoPermesso) throws Throwable;
	
	
//	public Collection<FascicoloView> cerca(String nome, String oggetto, String descrizione, String siglaCliente, String autoritaGiudiziaria, String controinteressato, String dal, String al, long tipologiaFascicoloId,
//			long settoreGiuridicoId, String controparte, String legaleEsterno, String owner, String stato,int elementiPerPagina, int numeroPagina, String ordinamento, String tipoOrdinamento, String tipoPermesso) throws Throwable;
	
	public ListaPaginata<FascicoloView> cerca2(String nome, String oggetto, String descrizione, String siglaCliente,
			String autoritaGiudiziaria, String controinteressato, String dal, String al, long tipologiaFascicoloId,
			long settoreGiuridicoId, String controparte, String legaleEsterno, String owner, String stato,
			int elementiPerPagina, int numeroPagina, String ordinamento, String tipoOrdinamento, String tipoPermesso,String matricolaOwner)
					throws Throwable;
	
	/**
	 * Cerca fra tutti i fascicoli quelli che rispondono ai criteri di filtro
	 * impostati e ne ritorna la count
	 *
	 * @param nome
	 * @param oggetto
	 * @return Long
	 * @throws Throwable
	 *             the throwable
	 */
	public Long conta(String nome, String oggetto, String dal, String al, long tipologiaFascicoloId,
			long settoreGiuridicoId, String controparte, String legaleEsterno, String tipoPermesso) throws Throwable;
	public Long conta(String nome, String oggetto, String dal, String al, long tipologiaFascicoloId,
			long settoreGiuridicoId, String controparte, String legaleEsterno, String owner, String stato, String tipoPermesso) throws Throwable;

	/**
	 * Crea un nuovo fascicolo e lo pone in stato "APERTO"
	 *
	 * @param fascicoloView
	 *            the fascicolo view
	 * @return FascicoloView
	 * @throws Throwable
	 *             the throwable
	 */
	public FascicoloView apriFascicolo(FascicoloView fascicoloView) throws Throwable;

	/**
	 * Aggiorna il fascicolo nei metadati aggiornabili.
	 *
	 * @param fascicoloView
	 * @throws Throwable
	 */
	public void aggiornaFascicolo(FascicoloView fascicoloView) throws Throwable;

	/**
	 * Pone in fascicolo nello stato "CHIUSO"
	 *
	 * @param id
	 * @return true, se termina correttamente
	 * @throws Throwable
	 */
	public boolean completaFascicolo(long id) throws Throwable;

	/**
	 * Aggiorna lo stato del fascicolo da "CHIUSO" in "APERTO". Nel caso in cui
	 * il fascicolo non risulti in stato "CHIUSO" il medoto sollever� una
	 * exception
	 *
	 * @param id
	 * @return true, se termina correttamente
	 * @throws Throwable
	 */
	public boolean riapriFascicolo(long id) throws Throwable;

	/**
	 * Elimina il fascicolo identificato dal parametro in input id
	 * 
	 * @param id
	 * @return true, se termina correttamente
	 * @throws Throwable
	 */
	public boolean eliminaFascicolo(long id) throws Throwable;

	public Collection<String> leggiPerAutocompleteForo(String term) throws Throwable;

	public Collection<String> leggiPerAutocompleteAutoritaEmanante(String term) throws Throwable;

	public Collection<String> leggiPerAutocompleteControinteressato(String term) throws Throwable;

	public Collection<String> leggiPerAutocompleteAutoritaGiudiziaria(String term) throws Throwable;

	public Collection<String> leggiPerAutocompleteSoggettoIndagato(String term) throws Throwable;

	public Collection<String> leggiPerAutocompleteResponsabileCivile(String term) throws Throwable;

	public Collection<String> leggiPerAutocompleteParteCivile(String term) throws Throwable;

	public Collection<String> leggiPerAutocompletePersonaOffesa(String term) throws Throwable;

	public FascicoloView leggi(long id, FetchMode fetchMode) throws Throwable;
	
	public FascicoloView leggiTutti(long id, FetchMode fetchMode) throws Throwable;

	public SoggettoIndagatoView leggiSoggettoIndagato(long id) throws Throwable;

	public PersonaOffesaView leggiPersonaOffesa(long id) throws Throwable;

	public ResponsabileCivileView leggiResponsabileCivile(long id) throws Throwable;

	public ParteCivileView leggiParteCivile(long id) throws Throwable;

	public void aggiungiDocumento(Long fascicoloId, Long categoriaId, MultipartFile file)throws Throwable;
 
	public void aggiungiDocumento(Long fascicoloId, Long categoriaId, List<MultipartFile> file)throws Throwable;

	public long getTipoCategoriaDocumentale(String codiceGruppoLingua)throws Throwable;

	public List<FascicoloView> cercaUltimiFascicoli(long numRighe) throws Throwable;

	public void salvaPermessiFascicolo(Long fascicoloId, String[] permessiScrittura, String[] permessiLettura)throws Throwable;

	public void salvaPermessiFascicolo(Long fascicoloId, List<String> permessiScrittura, List<String> permessiLettura, String useridUtil)throws Throwable;

	public void eliminaDocumento(long fascicoloId, String uuid) throws Throwable;

	public List<FascicoloView> leggiFascicoloOrdData(String idUtilMatricola) throws Throwable;

	public FascicoloView leggiPerCronologia(long id) throws Throwable;

	public ListaPaginata<FascicoloView> cerca(String nome, String oggetto, String descrizione, String siglaCliente,
			String autoritaGiudiziaria, String controinteressato, String dal, String al, long tipologiaFascicoloId,
			long settoreGiuridicoId, String controparte, String legaleEsterno, String owner, String stato,
			int elementiPerPagina, int numeroPagina, String ordinamento, String tipoOrdinamento, String tipoPermesso,
			String societaAddebito, List<Long> listaSocieta) throws Throwable;
	

	public List<FascicoloView> cerca(Integer anno, Integer mese) throws Throwable;
	
	//@@DDS public Boolean testFilenet() throws Throwable;
	//TODO, Verificare se serve implementare un metodo per testare DDS
	
	public FascicoloView leggiConPermessi(long id) throws Throwable;
	public FascicoloView leggiConPermessi(long id, FetchMode fetchMode) throws Throwable;
}
