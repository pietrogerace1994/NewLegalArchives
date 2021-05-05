package eng.la.persistence;

import java.util.Date;
import java.util.List;

import eng.la.model.Controparte;
import eng.la.model.Fascicolo;
import eng.la.model.Nazione;
import eng.la.model.ProfessionistaEsterno;
import eng.la.model.Proforma;
import eng.la.model.RIncaricoProformaSocieta;
import eng.la.model.RProformaFattura;
import eng.la.model.SchedaValutazione;
import eng.la.model.TipoProforma;
import eng.la.model.rest.ParcellaRest;
import eng.la.model.view.TipoProformaView;

public interface ProformaDAO {
	public List<Proforma> leggi() throws Throwable;
	public Proforma leggiConPermessi(long id) throws Throwable;
	public List<Proforma> cerca(String nome) throws Throwable;
	
	public List<Proforma> cerca(String nome, String statocode, String nomeFascicolo, String nomeIncarico, long societaAddebito, String dal, String al, int elementiPerPagina, int numeroPagina, String ordinamento, String ordinamentoDirezione,String fattura,String contabilizzato) throws Throwable;

	public Proforma leggi(long id) throws Throwable;

	public Proforma inserisci(Proforma vo) throws Throwable;

	public void modifica(Proforma vo) throws Throwable;

	public void cancella(long id) throws Throwable;

	public Long conta(String nome, String statocode, String nomeFascicolo, String nomeIncarico, long societaAddebito, String dal, String al,String fattura,String contabilizzato)
			throws Throwable;

	public void cancellaSchedaValutazione(long schedaId) throws Throwable;

	public void cancellaSocietaProforma(long proformaId) throws Throwable;

	public void salvaSocietaProfoma(RIncaricoProformaSocieta incaricoProformaSocieta) throws Throwable;

	public SchedaValutazione salvaSchedaValutazione(SchedaValutazione schedaValutazione) throws Throwable;

	public List<Proforma> leggiProformaAssociatiAIncarico(long id) throws Throwable;

	public List<Proforma> getListaProformaPerSocieta(boolean processato, long idSocieta) throws Throwable;

	public List<Nazione> getListaProformaNazione(long idProforma) throws Throwable;

	public List<Controparte> getListaProformaControparte(long idProforma) throws Throwable;

	public List<Fascicolo> getListaProformaFascicolo(long idProforma) throws Throwable;

	public List<ProfessionistaEsterno> getListaProformaProfessionistaEsterno(long idProforma) throws Throwable;
	
	public List<Proforma> cerca(List<String> parole) throws Throwable;

	public Proforma leggiSenzaHibernate(long id)throws Throwable;
	//Per Ordinamento
	public List<ParcellaRest> getParcellaRest(boolean processato,long idSocieta,String colSort,String sort) throws Throwable;

	public List<Proforma> leggiProformaAssociatiAIncaricoUnico(long id) throws Throwable;
	
	public void inserisciDataInvioAmministrativo(long idEntita, Date date) throws Throwable;
	public RProformaFattura getRProformaFattura(long idProforma) throws Throwable;
	
	public List<Proforma> leggiProformaAssociatiAIncarico(List<Long> idIncarichi) throws Throwable;
	public SchedaValutazione aggiornaSchedaValutazione(SchedaValutazione schedaValutazione) throws Throwable;
	public Integer checkStatusInviata(long id) throws Throwable;
	public Integer checkFile(long id) throws Throwable;
	
	public List<TipoProformaView> getListaTipoProforma(String lingua)throws Throwable;

	public TipoProforma leggiTipoProforma(Long idTipoProforma) throws Throwable;
}
