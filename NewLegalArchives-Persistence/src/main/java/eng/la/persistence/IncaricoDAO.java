package eng.la.persistence;

import java.util.Date;
import java.util.List;

import eng.la.model.Documento;
import eng.la.model.Incarico;
import eng.la.model.LetteraIncarico;
import eng.la.model.ListaRiferimento;
import eng.la.model.NotaPropIncarico;
import eng.la.model.Procura;
import eng.la.model.Proforma;
import eng.la.model.RIncaricoProformaSocieta;
import eng.la.model.RProformaFattura;
import eng.la.model.VerificaAnticorruzione;
import eng.la.model.VerificaPartiCorrelate;
import eng.la.model.custom.Stanziamenti;

public interface IncaricoDAO {
	public List<Incarico> leggi() throws Throwable;

	public List<Incarico> cerca(String nome, long professionistaEsternoId) throws Throwable;

	public List<Incarico> cerca(String nome, String commento, long professionistaEsternoId, String dal, String al, String statoIncaricoCode, String nomeFascicolo, int elementiPerPagina,
			int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable;

	public Incarico leggi(long id) throws Throwable;
	
	public Incarico leggiTutti(long id) throws Throwable;

	public Incarico inserisci(Incarico vo) throws Throwable;

	/**
	 * Aggiorna l'incarico modificando solo i dati aggiornabili.
	 * 
	 * @param vo:
	 *            l'incarico
	 * 
	 */
	public void modifica(Incarico vo) throws Throwable;

	public void cancella(long id) throws Throwable;

	public Long conta(String nome, String commento, long professionistaEsternoId, String dal, String al,String statoIncaricoCode,String nomeFascicolo) throws Throwable;

	public Procura creaProcura(Documento documento) throws Throwable;

	public VerificaAnticorruzione creaVerificaAnticorruzione(Documento documento) throws Throwable;

	public VerificaPartiCorrelate creaVerificaPartiCorrelate(Documento documento) throws Throwable;

	public ListaRiferimento creaListaRiferimento(Documento documento) throws Throwable;

	public String getNextNumeroIncarico() throws Throwable;

	public LetteraIncarico inserisciLetteraIncarico(LetteraIncarico letteraIncarico) throws Throwable;

	public void aggiornaLetteraIncarico(LetteraIncarico letteraIncarico) throws Throwable;

	public NotaPropIncarico inserisciNotaPropostaIncarico(NotaPropIncarico notaPropIncarico) throws Throwable;

	public void aggiornaNotaPropostaIncarico(NotaPropIncarico notaPropIncarico) throws Throwable;

	public void cancellaListaRiferimento(ListaRiferimento listaRiferimento) throws Throwable;

	public void cancellaVerificaPartiCorrelate(VerificaPartiCorrelate verificaPartiCorrelate) throws Throwable;

	public void cancellaVerificaAnticorruzione(VerificaAnticorruzione verificaAnticorruzione) throws Throwable;

	public void cancellaProcura(Procura procura) throws Throwable;

	public Incarico leggiIncaricoAssociatoAProforma(long id) throws Throwable;

	public Incarico inserisciCollegioArbitrale(Incarico vo) throws Throwable;

	public void cancellaCollegioArbitrale(long id) throws Throwable;

	public void modificaCollegioArbitrale(Incarico vo) throws Throwable;

	public Incarico leggiCollegioArbitrale(long id) throws Throwable;

	public List<Incarico> cercaArbitrale(String nome, String nominativoArbitroControparte, String indirizzoArbitroControparte, long professionistaEsternoId, String dal, String al,String statoIncaricoCode,String nomeFascicolo,
			int numElementiPerPagina, int numeroPagina, String ordinamento, String tipoOrdinamento) throws Throwable;

	public Long contaArbitrale(String nome, long professionistaEsternoId, String dal, String al, String statoIncaricoCode,String nomeFascicolo) throws Throwable;

	public List<Incarico> cerca(List<String> parole) throws Throwable;
	
	public List<Incarico> cercaArbitrale(List<String> parole) throws Throwable;

	public Incarico leggiSenzaHibernate(long id)throws Throwable;
	//Reporting
	public List<Proforma> leggiProformaAssociatoIncarico(long id) throws Throwable;
	//Reporting
	public List<RIncaricoProformaSocieta> leggiRIncaricoProformaSocieta(String codGruppoLingua,Object[] params) throws Throwable;
	//Reporting
	public List<Incarico> leggiIncarichiReporting(String[] params) throws Throwable ;
	//Reporting Per Contabilizzazione
	public List<RProformaFattura> getRProformaFattura(long idProforma) throws Throwable;
	public List<Incarico> getIncaricoDaIdFascicolo(long idFascicolo) throws Throwable;

	public List<Incarico> leggiIncarichiAutorizzati(String sortByFieldName, String orderAscOrDesc, String userIdOwner) throws Throwable;
	
	public Long contaIncarichiAutorizzati() throws Throwable;
	public List<Long> estraiListaFascicoli(Date begin, Date end) throws Throwable;
	public Long contaIncarichiAutorizzati(Date begin, Date end) throws Throwable;
	public Long contaIncarichiAutorizzati(Date begin, Date end, Long idProfEst) throws Throwable;
	public List<Incarico> leggiIncarichiAutorizzati(String sortByFieldName, String orderAscOrDesc, List<String> userIdOwner) throws Throwable;
	public List<Incarico> leggiIncarichiAutorizzati(String sortByFieldName, String orderAscOrDesc, String userIdOwner, String fascicoloId) throws Throwable;
	public void cancellaBonusAcconti(LetteraIncarico letteraIncarico) throws Throwable;
	
	public List<Incarico> leggiIncarichiAutorizzati(Long idProfessionistaEsterno) throws Throwable;

	public void deleteBonus(Long letteraIncaricoId) throws Throwable;

	public void deleteAcconti(Long letteraIncaricoId) throws Throwable;

	public List<Incarico> leggiIncarichiAutorizzati(Date begin, Date end, String userIdOwner, boolean isGestoreVendor,
			int elementiPerPagina, int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable;
	public Long contaIncarichiAutorizzati(Date begin, Date end, String userIdOwner, boolean isGestoreVendor) throws Throwable;
	
	public Incarico rinviaVotazione(Long idIncarico) throws Throwable;
	public Long getEuroValuta() throws Throwable;

	public Integer checkFile(long id) throws Throwable;
	
	public Integer checkFileNot(long id) throws Throwable;

	public Integer checkStatusInviata(long id) throws Throwable;
	
	public List<Stanziamenti> leggiIncarichiStanziamenti(int anno) throws Throwable;
	public Incarico leggiConPermessi(long id) throws Throwable;

	public Integer checkStatusInviataNot(long id);

}

