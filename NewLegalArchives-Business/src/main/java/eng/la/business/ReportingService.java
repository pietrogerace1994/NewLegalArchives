package eng.la.business;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import eng.la.model.ProfessionistaEsterno;
import eng.la.model.SettoreGiuridico;
import eng.la.model.Societa;
import eng.la.model.StatoAtto;
import eng.la.model.StatoFascicolo;
import eng.la.model.StatoIncarico;
import eng.la.model.StatoProforma;
import eng.la.model.TipoContenzioso;
import eng.la.model.TipologiaFascicolo;
import eng.la.model.Utente;
import eng.la.model.view.SchedaFondoRischiView;
import eng.la.model.view.UtenteView;
import eng.la.model.view.VendorManagementView;

public interface ReportingService {
	public List<Utente> getGcDestinatario() throws Throwable;
	public List<Societa> getlistSocieta() throws Throwable;
	public List<StatoAtto> getListaStatoAttoPerLingua(String lang) throws Throwable;
 
	public List<TipologiaFascicolo> getListTipologiaFascicolo(String lang) throws Throwable;
	 
	public List<SettoreGiuridico> getListSettoreGiuridico(String lang) throws Throwable;
  
	public List<StatoFascicolo> getListStatoFascicolo(String lang) throws Throwable;

	public List<Utente> getListOwner() throws Throwable;

	 //Per Incarichi
	 //anagrafica degli avvocati in elenco
	public List<ProfessionistaEsterno> getListProfessionistaEsterno() throws Throwable;
	 
	public List<StatoIncarico> getListStatoIncarico(String lang) throws Throwable;
	public List<TipoContenzioso> getListTipoContenzioso(String lang) throws Throwable;
	public List<StatoProforma> getListStatoProforma(String lang) throws Throwable;
	
	public void exportExcellAtto(Object[] params,HttpServletResponse respons,String localLang) throws Throwable;
	public void exportExcellFascicoli(Object[] params,HttpServletResponse respons,String localLang) throws Throwable;
	public void exportExcellIncarichi(String[] params,HttpServletResponse respons,String localLang) throws Throwable;
	public void exportExcellProforma(Object[] params,HttpServletResponse respons,String localLang) throws Throwable;
	public void exportExcellOrganiSociali(HttpServletResponse respons,String localLang) throws Throwable;
	public boolean exportExcellStanziamenti(int annoFinanziario,HttpServletResponse respons,String localLang) throws Throwable;
	public String generaPFRFase1(List<SchedaFondoRischiView> lista, String trimestre, HttpServletResponse respons,String localLang, int delay) throws Throwable;
	public String generaPFRFase2(List<SchedaFondoRischiView> lista, String trimestre, HttpServletResponse respons,String localLang, int delay) throws Throwable;
	public String exportReportAlesocr(List<UtenteView> utentiAlesocr, HttpServletResponse respons,String localLang) throws Throwable;
	public String generaReportVendor(Map<Long, List<VendorManagementView>> voteMap, HttpServletResponse respons,String localLang) throws Throwable;
	
}
