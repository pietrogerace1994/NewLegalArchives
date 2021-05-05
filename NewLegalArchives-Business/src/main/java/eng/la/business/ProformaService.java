/*
 * @author Luigi Nardiello
 */
package eng.la.business;

import java.util.List;

import eng.la.model.Proforma;
import eng.la.model.RProformaFattura;
import eng.la.model.TipoProforma;
import eng.la.model.view.IncaricoView;
import eng.la.model.view.ProformaView;
import eng.la.model.view.TipoProformaView;
import eng.la.util.ListaPaginata;

public interface ProformaService {

	/**
	 * Ritorna la lista di ProformaView.
	 *
	 * @return List<ProformaView>
	 * @throws Throwable 
	 */
	public List<ProformaView> leggi() throws Throwable;

	/**
	 * Ritorna un ProformaView con id = @param id.
	 *
	 * @param id  
	 * @return ProformaView
	 * @throws Throwable 
	 */
	public ProformaView leggi(long id) throws Throwable;
	public ProformaView leggiConPermessi(long id) throws Throwable;
	/**
	 * Cerca tra tutte le proforme quelle che rispondono ai criteri di filtro impostati e ritorna una ListaPaginata
	 *
	 * @param nome  
	 * @param professionistaEsternoId 
	 * @param elementiPerPagina 
	 * @param numeroPagina 
	 * @param ordinamento 
	 * @param ordinamentoDirezione 
	 * @return ListaPaginata<ProformaView> 
	 * @throws Throwable 
	 */
	public ListaPaginata<ProformaView> cerca(String nome, String statocode, String nomeFascicolo, String nomeIncarico, long societaAddebito, String dal, String al, int elementiPerPagina,
			int numeroPagina, String ordinamento, String ordinamentoDirezione,String fattura,String contabilizzato) throws Throwable;
	
	/**
	 * Cerca tra tutte le proforme quelle che rispondono ai criteri di filtro impostati e ritorna una List
	 *
	 * @param nome
	 * @return List<ProformaView>
	 * @throws Throwable  
	 */ 
	public List<ProformaView> cerca(String nome) throws Throwable;
	
	/**
	 * Cerca tra tutte proforme quelle che rispondono ai criteri di filtro impostati e ritorna la count degli elementi trovati
	 *
	 * @param nome  
	 * @param professionistaEsternoId 
	 * @return Long
	 * @throws Throwable  
	 */
	public Long conta(String nome,  String statocode, String nomeFascicolo, String nomeIncarico, String dal, String al,String fattura,String contabilizzato) throws Throwable;

	/**
	 * Crea una proforma.
	 *
	 * @param ProformaView  
	 * @return ProformaView
	 * @throws Throwable the throwable
	 */
	public ProformaView inserisci(ProformaView proformaView) throws Throwable;

	/**
	 * Modifica una proforma..
	 *
	 * @param ProformaView 
	 * @throws Throwable  
	 */
	public void modifica(ProformaView proformaView) throws Throwable;

	/**
	 * Cancella una proforma.
	 *
	 * @param ProformaView 
	 * @throws Throwable 
	 */
	public void cancella(ProformaView proformaView) throws Throwable;
	
	public List <ProformaView> leggiProformaAssociatiAIncarico(long id) throws Throwable;
 
	public List<ProformaView> cerca(List<String> parole) throws Throwable;
	
	public RProformaFattura getRProformaFattura(long idProforma) throws Throwable;
	
	public List <ProformaView> leggiProformaAssociatiAIncarico(List<Long> idIncarichi) throws Throwable;

	public Integer checkStatusInviata(long id) throws Throwable;

	public Integer checkFile(long id) throws Throwable;
	

	public List<TipoProformaView> getListaTipoProforma(String lingua) throws Throwable;

	public TipoProforma leggiTipoProforma(Long idTipoProforma) throws Throwable;

	public void modificaPerWS(ProformaView proformaView) throws Throwable;
	
}
