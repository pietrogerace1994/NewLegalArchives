package eng.la.business;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import eng.la.model.Controparte;
import eng.la.model.Fascicolo;
import eng.la.model.Nazione;
import eng.la.model.ProfessionistaEsterno;
import eng.la.model.Proforma;
import eng.la.model.Societa;
import eng.la.model.custom.ParcellaRow;
import eng.la.model.rest.ParcellaRest;

public interface ParcellaService {
	public List<Proforma> getListaProformaPerSocieta(boolean processato,long idSocieta) throws Throwable;
	public List<Societa> getListaSocietaProformaProcessate(boolean processato) throws Throwable;
	public void exportExcell(List<ParcellaRow> parcellaRows, HttpServletResponse respons,boolean processato) throws IOException;
	public Proforma setProformaLetto(long id,String utenteProcessamento) throws Throwable;
	public List<Nazione> getListaProformaNazione(long idProforma) throws Throwable;
	public List<Controparte> getListaProformaControparte(long idProforma) throws Throwable;
	public List<Fascicolo> getListaProformaFascicolo(long idProforma) throws Throwable;
	public List<ProfessionistaEsterno> getListaProformaProfessionistaEsterno(long idProforma) throws Throwable;
	//Per Ordinamento
	public List<ParcellaRest> getParcellaRest(boolean processato,long idSocieta,String colSort,String sort)throws Throwable;
}
