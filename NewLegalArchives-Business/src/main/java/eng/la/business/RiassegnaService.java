package eng.la.business;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import eng.la.model.Fascicolo;
import eng.la.model.RUtenteFascicolo;
import eng.la.model.SettoreGiuridico;
import eng.la.model.Societa;
import eng.la.model.TipologiaFascicolo;
import eng.la.model.Utente;
import eng.la.model.rest.RiassegnaRest;

public interface RiassegnaService {
	/** riassegnazione > Legale Interno Owner(combo degli owner dei fascioli)*/
	public List<Utente> getListaLegaleInternoOwnerFascicolo(long idFascicolo) throws Throwable;
	public List<RiassegnaRest> getListaLegaleInternoOwnerFascicoloRest(long idFascicolo) throws Throwable;
	public List<TipologiaFascicolo> getListaTipologiaFascicolo() throws Throwable;
	public List<SettoreGiuridico> getListaSettoreGiuridico() throws Throwable;
	public List<Societa> getListaSocieta() throws Throwable;
	public List<Fascicolo> getListaFascicoliXRiassegna(String matricolaUtil) throws Throwable;
	public List<BigDecimal> getIDFascicoliXRiassegna(String matricolaUtil,String idSocieta,String idTipologiaFascicolo,String idSettoreGiuridico,String nomeFascicolo) throws Throwable;
	public List<BigDecimal> getIDFascicoliXEstendiPermessi(String amministratore, String matricolaUtil,String legaleInterno,String idSocieta,String idTipologiaFascicolo,String idSettoreGiuridico,String nomeFascicolo) throws Throwable; 
	public Fascicolo getFascicoli(long idFascicolo) throws Throwable;
	public List<Utente> getListaUtentiNotAmmistrativiNotAmministratore() throws Throwable;
	public void insertRUtenteFascicolo(RUtenteFascicolo vo) throws Throwable;
	public void updateRUtenteFascicoloDataCancellazione(RUtenteFascicolo vo) throws Throwable;
	public Utente getUtente(String matricolaUtil) throws Throwable;
	public void riassegnaAutorizzazioni(long idFascicolo, String oldOwnerUserId, String newOwnerUserId) throws Throwable;
	public boolean riassegna(long idFascicolo,  String oldOwnerUserId, String newOwnerUserId) throws Throwable;
	public String riassegnazioneMultipla(String idFas, String owner, HttpServletRequest request, Model model,
			Locale locale); 
}
