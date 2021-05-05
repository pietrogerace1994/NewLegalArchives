/*
 * @author Luigi Nardiello
 */
package eng.la.persistence;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.FetchMode;

import eng.la.model.DocumentoProtCorrisp;
import eng.la.model.Fascicolo;
import eng.la.model.ParteCivile;
import eng.la.model.PersonaOffesa;
import eng.la.model.RCorrelazioneFascicoli;
import eng.la.model.RFascPrestNotar;
import eng.la.model.RFascicoloGiudizio;
import eng.la.model.RFascicoloMateria;
import eng.la.model.RFascicoloRicorso;
import eng.la.model.RFascicoloSocieta;
import eng.la.model.ResponsabileCivile;
import eng.la.model.SoggettoIndagato; 

/**
 * Interface FascicoloDAO.
 */
public interface FascicoloDAO {

	/**
	 * Ritorna la lista del fascicoli.
	 *
	 * @return FascicoloDAO
	 */
	public List<Fascicolo> leggi() throws Throwable;

	/**
	 * Ritorna il fascicolo con id = @param id.
	 *
	 * @param id
	 * @return FascicoloDAO
	 */
	public Fascicolo leggi(long id) throws Throwable;

	/**
	 * Cerca fra tutti i fascicoli quelli che rispondono ai criteri di filtro
	 * impostati e ritorna una ListaPaginata
	 *
	 * @param nome
	 *            the nome
	 * @param oggetto
	 *            the oggetto
	 * @param legaleEsterno 
	 * @param controparte 
	 * @param tipologiaAtto 
	 * @param tipoAzione 
	 * @param al 
	 * @param dal 
	 * @param elementiPerPagina
	 *            the elementi per pagina
	 * @param numeroPagina
	 *            the numero pagina
	 * @param ordinamento
	 *            the ordinamento
	 * @param ordinamentoDirezione
	 *            the ordinamento direzione
	 * @return FascicoloDAO
	 */
	public List<Fascicolo> cerca(String nome, String oggetto, String descrizione, String siglaCliente, String autoritaGiudiziaria, String controinteressato, String dal, String al, long tipologiaFascicoloId, long settoreGiuridicoId, String controparte, String legaleEsterno, int elementiPerPagina, int numeroPagina,
			String ordinamento, String ordinamentoDirezione, String tipoPermesso) throws Throwable;

	public List<Fascicolo> cerca2(String nome, String oggetto, String descrizione, String siglaCliente,
			String autoritaGiudiziaria, String controinteressato, String dal, String al, long tipologiaFascicoloId,
			long settoreGiuridicoId, String controparte, String legaleEsterno, String owner, String stato,
			int elementiPerPagina, int numeroPagina, String ordinamento, String ordinamentoDirezione,
			String tipoPermesso,String matricolaOwner) throws Throwable;
	
	public List<Fascicolo> cerca(List<String> parole) throws Throwable;
	public List<Fascicolo> cerca(List<String> parole, String dal, String al,int elementiPerPagina, int numeroPagina,
			String ordinamento, String ordinamentoDirezione,
			String tipoPermesso) throws Throwable;	
	public Long conta(List<String> parole, String dal, String al, String tipoPermesso) throws Throwable;	
	/**
	 * fra tutti i fascicoli quelli che rispondono ai criteri di filtro
	 * impostati e ritorna la count.
	 *
	 * @param nome
	 *            the nome
	 * @param oggetto
	 *            the oggetto
	 * @param legaleEsterno 
	 * @param controparte 
	 * @param tipologiaAtto 
	 * @param tipoAzione 
	 * @param al 
	 * @param dal 
	 * @return FascicoloDAO
	 */
	public Long conta(String nome, String oggetto, String descrizione, String siglaCliente, String autoritaGiudiziaria, String controinteressato, String dal, String al,  long tipologiaFascicoloId, long settoreGiuridicoId, String controparte, String legaleEsterno, String tipoPermesso) throws Throwable;

	public Long conta2(String nome, String oggetto, String descrizione, String siglaCliente, String autoritaGiudiziaria,
			String controinteressato, String dal, String al, long tipologiaFascicoloId, long settoreGiuridicoId,
			String controparte, String legaleEsterno, String owner, String stato, String tipoPermesso,String matricolaOwner);
	/**
	 * Cerca fra tutti i fascicoli quelli che rispondono ai criteri di filtro
	 * impostati e ritorna una List.
	 *
	 * @param nome
	 *            the nome
	 * @param oggetto
	 *            the oggetto
	 * @return FascicoloDAO
	 */
	public List<Fascicolo> cerca(String nome, String oggetto) throws Throwable;

	/**
	 * Apre il fascicolo.
	 *
	 * @param vo
	 *            the vo
	 * @return FascicoloDAO
	 */
	public Fascicolo apriFascicolo(Fascicolo vo) throws Throwable;

	/**
	 * Aggiorna il fascicolo modificando solo i dati aggiornabili.
	 *
	 * @param vo
	 *            the vo
	 */
	public void aggiornaFascicolo(Fascicolo vo) throws Throwable;

	/**
	 * Elimina il fascicolo passato in input.
	 *
	 * @param vo
	 *            the vo
	 */
	public void eliminaFascicolo(Fascicolo vo) throws Throwable;

	public void inserisciFascicoloSocieta(RFascicoloSocieta fascicoloSocieta) throws Throwable;

	public void inserisciFascicoloCorrelato(RCorrelazioneFascicoli fascicoloCorrelato) throws Throwable;

	public List<String> leggiPerAutocompleteForo(String term) throws Throwable;

	public List<String> leggiPerAutocompleteAutoritaEmanante(String term) throws Throwable;

	public List<String> leggiPerAutocompleteControinteressato(String term) throws Throwable;

	public List<String> leggiPerAutocompleteAutoritaGiudiziaria(String term) throws Throwable;

	public List<String> leggiPerAutocompleteSoggettoIndagato(String term) throws Throwable;

	public List<String> leggiPerAutocompleteResponsabileCivile(String term) throws Throwable;

	public List<String> leggiPerAutocompleteParteCivile(String term) throws Throwable;

	public List<String> leggiPerAutocompletePersonaOffesa(String term) throws Throwable;

	public String getNextNumeroFascicolo() throws Throwable;

	public void inserisciResponsabileCivile(ResponsabileCivile vo) throws Throwable;

	public void inserisciParteCivile(ParteCivile vo) throws Throwable;

	public void inserisciPersonaOffesa(PersonaOffesa vo) throws Throwable;

	public void inserisciSoggettoIndagato(SoggettoIndagato vo) throws Throwable;

	public void inserisciFascicoloMateria(RFascicoloMateria vo) throws Throwable;

	public Fascicolo leggi(long id, FetchMode fetchMode) throws Throwable;
	
	public Fascicolo leggiTutti(long id, FetchMode fetchMode) throws Throwable;

	public SoggettoIndagato leggiSoggettoIndagato(long id) throws Throwable;

	public PersonaOffesa leggiPersonaOffesa(long id) throws Throwable;

	public ResponsabileCivile leggiResponsabileCivile(long id) throws Throwable;

	public ParteCivile leggiParteCivile(long id) throws Throwable;

	public void cancellaFascicoloResponsabileCivile(long fasicoloId) throws Throwable;

	public void cancellaFascicoloParteCivile(long fasicoloId) throws Throwable;

	public void cancellaFascicoloPersonaOffesa(long fasicoloId) throws Throwable;

	public void cancellaFascicoloSoggettoIndagato(long fasicoloId) throws Throwable;

	public void cancellaFascicoloMaterie(long fasicoloId) throws Throwable;

	public void cancellaCorrelazioneFascicoli(long fasicoloId) throws Throwable;

	public void cancellaFascicoloControparte(long fasicoloId) throws Throwable;

	public void cancellaFascicoloTerzoChiamatoCausa(long fasicoloId) throws Throwable;

	public void cancellaFascicoloSocieta(long fasicoloId) throws Throwable;

	public DocumentoProtCorrisp aggiungiDocumento(Long fascicoloId, Long categoriaId, Long documentoId) throws Throwable;

	public long getTipoCategoriaDocumentale(String codiceGruppoLingua)  throws Throwable;
	
	public void cancellaFascicoloRicorso(long fasicoloId) throws Throwable;

	public void cancellaFascicoloGiudizio(long fasicoloId) throws Throwable;

	public void inserisciFascicoloGiudizio(RFascicoloGiudizio vo) throws Throwable;

	public void inserisciFascicoloRicorso(RFascicoloRicorso vo) throws Throwable;
	
	public List<Fascicolo> cercaAllFascicoli(String nome, int elementiPerPagina, int numeroPagina,String ordinamento, String ordinamentoDirezione)throws Throwable;
	
	public Long contaAllSerch(String nome)  throws Throwable;

	public void cancellaFascicoloPrestazioneNotarile(long id)throws Throwable;

	public void inserisciPrestazioneNotarile(RFascPrestNotar prestNotar)throws Throwable;
	
	public List<Fascicolo> cercaUltimiFascicoli(long numRighe)  throws Throwable;

	public Fascicolo leggiSenzaHibernate(long id)throws Throwable;

	public List<Fascicolo> getListaFascicoliXRiassegna(String matricolaUtil) throws Throwable;
	
	public List<BigDecimal> getIDFascicoliXRiassegna(String matricolaUtil,String idSocieta,String idTipologiaFascicolo,String idSettoreGiuridico,String nomeFascicolo) throws Throwable;

	public List<BigDecimal> getIDFascicoliXEstendiPermessi(String amministratore, String matricolaUtil, String legaleInterno, String idSocieta,String idTipologiaFascicolo,String idSettoreGiuridico,String nomeFascicolo) throws Throwable;
	
	public List<Fascicolo> cerca(String nome, String oggetto, String descrizione, String siglaCliente,
			String autoritaGiudiziaria, String controinteressato, String dal, String al, long tipologiaFascicoloId,
			long settoreGiuridicoId, String controparte, String legaleEsterno, String owner, String stato,
			int elementiPerPagina, int numeroPagina, String ordinamento, String tipoOrdinamento, String tipoPermesso,String societaAddebito,List<Long> listaSNAM_SRG_GNL_STOGIT) throws Throwable;

	public Long conta(String nome, String oggetto, String descrizione, String siglaCliente,
			String autoritaGiudiziaria, String controinteressato, String dal, String al, long tipologiaFascicoloId,
			long settoreGiuridicoId, String controparte, String legaleEsterno, String owner, String stato, String tipoPermesso,String societaAddebito,List<Long> listaSNAM_SRG_GNL_STOGIT) throws Throwable;

	//reporting
	public List<Fascicolo> reportingFascicoli(Object[] params) throws Throwable;

	List<Fascicolo> leggiFascicoloOrdData(String idUtilMatricola);
	
	public List<RFascicoloGiudizio> getRFascicoloGiudizioDaIdFascicolo(long idFascicolo) throws Throwable;

	public Fascicolo leggiPerCronologia(long id);
	
	//reporting
	public List<RFascicoloSocieta> reportingFascicoliRFS(Object[] params) throws Throwable ;
	public Fascicolo leggiConPermessi(long id, FetchMode fetchMode) throws Throwable;

	public List<RFascicoloSocieta> getRFascicoloSocietas(long idFascicolo) throws Throwable;	
	
	public List<Fascicolo> cerca(Integer anno, Integer mese) throws Throwable;
	
	public Fascicolo leggiConPermessi(long id) throws Throwable;
}
