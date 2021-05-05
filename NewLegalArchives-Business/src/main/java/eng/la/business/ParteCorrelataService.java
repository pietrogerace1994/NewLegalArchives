package eng.la.business;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import eng.la.model.ParteCorrelata;
import eng.la.model.view.ParteCorrelataView;
import eng.la.util.ListaPaginata;

public interface ParteCorrelataService {
	// Da spostare nella classe costanti di util.
	public static final String ESTRAI_TUTTE ="T";
	public static final String ESTRAI_ATTIVE ="A";
	public static final String ESTRAI_NON_ATTIVE ="N";
		
	public List<ParteCorrelataView> cerca(ParteCorrelataView parteCorrelata) throws Throwable;
	public List<ParteCorrelataView> cerca(String denominazione, String codFiscale, String partitaIva, Date dataInput) throws Throwable;
	public List<ParteCorrelataView> cerca(String denominazione, String codFiscale, String partitaIva) throws Throwable;
	public HashMap<String, Object> cerca(Boolean isUtenteSS,String denominazione,String codFiscale, String partitaIva, Date data) throws Throwable;
	public HashMap<String, Object> cercaPartiCorrelate(Boolean isUtenteSS,String denominazione,String codFiscale, String partitaIva,Date data) throws Throwable;
	public ParteCorrelataView leggi(long id) throws Throwable;
	public List<ParteCorrelataView> leggi() throws Throwable;
	public List<ParteCorrelataView> leggi(String tipoEstrazione) throws Throwable;
	public List<ParteCorrelataView> leggiAttive() throws Throwable;
	public void cancella(long id) throws Throwable;
	public ParteCorrelataView inserisci(ParteCorrelataView parteCorrelata) throws Throwable;
	public void modifica(ParteCorrelataView parteCorrelata) throws Throwable;
	public byte[] generaReportXLS(String tipoEstrazione) throws Throwable;
	public ListaPaginata<ParteCorrelataView> leggi(int elementiPerPagina, int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable;
	public ListaPaginata<ParteCorrelataView> leggiElenco(int elementiPerPagina, int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable;
	public void generaReportExcel(String tipoEstrazione, HttpServletResponse response) throws Throwable;
	public List<ParteCorrelataView> ricerca(String denominazione, String codFiscale, String partitaIva) throws Throwable;
	public void generaReportRicercaPdf(HttpServletResponse response, ParteCorrelataView parteCorrelataView) throws IOException;
	public void generazioneReportRicercaPDF1(HttpServletResponse response, List<ParteCorrelataView> pcList,Boolean esitoRicerca,String denominazioneInput,String codFiscaleInput, String partitaIvaInput, Date dataInput,String nomeUtenteVisualizzato,Boolean isUtenteSS ) throws Throwable; 
	public void generazioneReportRicercaPDF2(HttpServletResponse response, HttpServletRequest request, ParteCorrelataView pc, List<ParteCorrelataView> pcList,Boolean esitoRicerca,String denominazioneInput,String codFiscaleInput, String partitaIvaInput,Date dataInput,String nomeUtenteVisualizzato,Boolean isUtenteSS ) throws Throwable;
}