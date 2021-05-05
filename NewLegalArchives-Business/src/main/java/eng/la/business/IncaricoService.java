/*
 * @author Luigi Nardiello
 */
package eng.la.business;

import java.util.Date;
import java.util.List;

import eng.la.model.Documento;
import eng.la.model.LetteraIncarico;
import eng.la.model.NotaPropIncarico;
import eng.la.model.rest.CreaFascicoloIncaricoRest;
import eng.la.model.view.CollegioArbitraleView;
import eng.la.model.view.FascicoloView;
import eng.la.model.view.IncaricoView;
import eng.la.model.view.LetteraIncaricoView;
import eng.la.model.view.NotaPropIncaricoView;
import eng.la.util.ListaPaginata;

/**
 * Interface IncaricoService.
 */
public interface IncaricoService {

	/**
	 * Ritorna la lista di IncaricoView.
	 *
	 * @return List<IncaricoView>
	 * @throws Throwable
	 */
	public List<IncaricoView> leggi() throws Throwable;

	/**
	 * Ritorna un IncaricoView con id = @param id.
	 *
	 * @param id
	 * @return IncaricoView
	 * @throws Throwable
	 */
	public IncaricoView leggi(long id) throws Throwable;
	public IncaricoView leggiConPermessi(long id) throws Throwable;
	/**
	 * Ritorna un IncaricoView con id = @param id.
	 *
	 * @param id
	 * @return IncaricoView
	 * @throws Throwable
	 */
	public IncaricoView leggiTutti(long id) throws Throwable;

	/**
	 * Cerca tra tutti gli incarichi quelli che rispondono ai criteri di filtro
	 * impostati e ritorna una ListaPaginata
	 *
	 * @param nome
	 * @param professionistaEsternoId
	 * @param nomeFascicolo 
	 * @param statoIncaricoCode 
	 * @param elementiPerPagina
	 * @param numeroPagina
	 * @param ordinamento
	 * @param ordinamentoDirezione
	 * @return ListaPaginata<IncaricoView>
	 * @throws Throwable
	 */
	public ListaPaginata<IncaricoView> cerca(String nome, String commento, long professionistaEsternoId, String dal, String al, String statoIncaricoCode, String nomeFascicolo, int elementiPerPagina,
			int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable;

	/**
	 * Cerca tra tutti gli incarichi quelli che rispondono ai criteri di filtro
	 * impostati e ritorna una List
	 *
	 * @param nome
	 * @param professionistaEsternoId
	 * @return List<IncaricoView
	 * @throws Throwable
	 */
	public List<IncaricoView> cerca(String nome, long professionistaEsternoId) throws Throwable;

	/**
	 * Cerca tra tutti gli incarichi quelli che rispondono ai criteri di filtro
	 * impostati e ritorna la count degli elementi trovati
	 *
	 * @param nome
	 * @param professionistaEsternoId
	 * @return Long
	 * @throws Throwable
	 */
	public Long conta(String nome, long professionistaEsternoId, String dal, String al, String statoIncaricoCode, String nomeFascicolo  ) throws Throwable;

	/**
	 * Crea un nuovo incarico.
	 *
	 * @param incaricoViewaricoView
	 *            the incarico viewarico view
	 * @return IncaricoService
	 * @throws Throwable
	 *             the throwable
	 */
	public IncaricoView inserisci(IncaricoView incaricoViewaricoView) throws Throwable;

	/**
	 * Modifica un incarico.
	 *
	 * @param incaricoView
	 *            the incarico view
	 * @throws Throwable
	 *             the throwable
	 */
	public void modifica(IncaricoView incaricoView) throws Throwable;

	/**
	 * Cancella l'incarico.
	 *
	 * @param incaricoView
	 *            the incarico view
	 * @throws Throwable
	 *             the throwable
	 */
	public void cancella(IncaricoView incaricoView) throws Throwable;

	/**
	 * Aggiungi procura crea un record sul db ed invia il file sul documentale.
	 *
	 * @param idIncarico
	 * @param contenutoProcura
	 * @throws Throwable
	 *             the throwable
	 */
	 
	 /**
	 * Metodo di lettura dell'incarico associato
	 * <p>
	 * @param id: identificativo del proforma
	 * @return oggetto Incarico.
	 * @exception Throwable
	 */
	public IncaricoView leggiIncaricoAssociatoAProforma(long id) throws Throwable;

	public CollegioArbitraleView inserisciCollegioArbitrale(CollegioArbitraleView view) throws Throwable;

	public CollegioArbitraleView leggiCollegioArbitrale(long id) throws Throwable;

	public void cancellaCollegioArbitrale(CollegioArbitraleView view) throws Throwable;

	public void modificaCollegioArbitrale(CollegioArbitraleView view) throws Throwable;

	public ListaPaginata<CollegioArbitraleView> cercaArbitrale(String nome, String nominativoArbitroControparte, String indirizzoArbitroControparte, long professionistaEsternoId, String dal,
			String al, String statoIncaricoCode, String nomeFascicolo,  int numElementiPerPagina, int numeroPagina, String ordinamento, String tipoOrdinamento) throws Throwable;
	
	public Long contaArbitrale(String nome, long professionistaEsternoId, String dal, String al, String statoIncaricoCode, String nomeFascicolo  ) throws Throwable;

	public List<IncaricoView> cerca(List<String> parole) throws Throwable;

	public List<CollegioArbitraleView> cercaArbitrale(List<String> parole) throws Throwable;

	public List<IncaricoView> leggiIncarichiAutorizzati(String sortByFieldName, String orderAscOrDesc, String userIdOwner) throws Throwable;

	public Long contaIncarichiAutorizzati() throws Throwable;
	
	public Long contaIncarichiAutorizzati(Date begin, Date end) throws Throwable;
	
	public Long contaIncarichiAutorizzati(Date begin, Date end, Long idProfEst) throws Throwable;
	
	public List<IncaricoView> leggiIncarichiAutorizzati(Long idProfessionistaEsterno) throws Throwable;

	public void deleteAccontiBonus(LetteraIncarico letteraIncarico) throws Throwable;
	
	public void deleteBonusAcconti(Long letteraIncaricoId) throws Throwable;
	
	public List<Long> estraiListaFascicoli(Date begin, Date end) throws Throwable;
	
	public List<IncaricoView> leggiIncarichiAutorizzati(String sortByFieldName, String orderAscOrDesc, List<String> userIdOwner) throws Throwable;
	
	public List<LetteraIncaricoView> leggiLettereIncaricoPerId(List<Long> listaIdLettera) throws Throwable;
	
	public List<NotaPropIncaricoView> leggiNotaPropostaIncaricoPerId(List<Long> listaIdNotaProposta) throws Throwable;
	
	public List<IncaricoView> leggiIncarichiAutorizzati(String sortByFieldName, String orderAscOrDesc, String userIdOwner, String fascicoloID) throws Throwable;

	public ListaPaginata<IncaricoView> leggiIncarichiAutorizzati(Date begin, Date end, String userIdOwner, boolean isGestoreVendor,int elementiPerPagina,
			int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable;
	
	public IncaricoView rinviaVotazione(String incaricoId) throws Throwable;
	
	public Long getEuroValuta() throws Throwable;

	public Integer checkFile(long id) throws Throwable;
	
	public Integer checkFileNot(long id) throws Throwable;

	public LetteraIncarico leggiLetteraIncarico(Long id) throws Throwable;
	
	public NotaPropIncarico leggiNotaPropostaIncarico(Long id) throws Throwable;

	public Integer checkStatusInviata(long id) throws Throwable;

	public String creaFascicoloEIncarico(FascicoloView fascicoloView, IncaricoView incaricoView) throws Throwable;

	public IncaricoView inserisciFascicoloEincarico(FascicoloView fascicoloView, IncaricoView incaricoView,
			CreaFascicoloIncaricoRest fi) throws Throwable;

	public Integer checkStatusInviataNot(long id) throws Throwable;

	public IncaricoView aggiornaIncaricoConListaRiferimento(IncaricoView incaricoView, Documento vo) throws Throwable;
	public IncaricoView aggiornaIncaricoConPartiCorrelate(IncaricoView incaricoView, Documento vo) throws Throwable;
	public IncaricoView aggiornaIncaricoConVerificaAnticorruzione(IncaricoView incaricoView, Documento vo) throws Throwable;
	public IncaricoView aggiornaIncaricoConProcura(IncaricoView incaricoView, Documento vo) throws Throwable;

	public void modificaPerWS(IncaricoView incaricoView) throws Throwable;
	
	/**
	 * Genera un numero di protocollo valido da associare alla lettera di incarico
	 * Il numero di protocollo è generato come una strunga del tipo NNNN/AA/UU dove: 
	 * NNNN è l'ultimo numero +1 a 4 cifre generato nell ultimo anno per l'utente corrente
	 * AA è l'anno di creazione della lettera
	 * UU è la sigla dell'utente corrente
	 * @param sigla la sigla rappresentante l'utente corrente
	 * @param nominativo il nominativo dell'utente corrente
	 * @author MASSIMO CARUSO
	 * @return il numero di protocollo generato
	 * @throws Throwable in caso di errore
	 */
	public String generaNumeroProtocolloLetteraIncarico(String sigla, String nominativo) throws Throwable; 
}
