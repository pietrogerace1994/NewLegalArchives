package eng.la.business;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//import com.ibm.icu.math.BigDecimal;

import eng.la.model.Controparte;
import eng.la.model.Fascicolo;
import eng.la.model.Nazione;
import eng.la.model.ProfessionistaEsterno;
import eng.la.model.Proforma;
import eng.la.model.Societa;
import eng.la.model.custom.ParcellaRow;
import eng.la.model.custom.ProformaRow;
import eng.la.model.rest.ParcellaRest;
import eng.la.model.view.ProformaView;
import eng.la.persistence.ProformaDAO;
import eng.la.persistence.SocietaDAO;
import eng.la.util.WriteExcell;
 
@Service("parcellaService")
public class ParcellaServiceImpl  extends BaseService<Proforma,ProformaView> implements ParcellaService{

	@Autowired
	private SocietaDAO societaDAO;
	
	@Autowired
	private ProformaDAO proformaDAO;
	
	public void setSocietaDAO(SocietaDAO societaDAO) {
		this.societaDAO = societaDAO;
	}
	
	public void setProformaDAO(ProformaDAO proformaDAO) {
		this.proformaDAO = proformaDAO;
	}

	@Override
	public List<Proforma> getListaProformaPerSocieta(boolean processato, long idSocieta) throws Throwable {
		 List<Proforma> proformas=proformaDAO.getListaProformaPerSocieta(processato, idSocieta);
		if(proformas!=null && proformas.size()>0)
			return proformas;
		    return null;
	}
	

	@Override
	public List<Societa> getListaSocietaProformaProcessate(boolean processato) throws Throwable {
		List<Societa> societas=societaDAO.getListaSocietaProformaProcessate(processato);
		if(societas!=null && societas.size()>0)
			return societas;
		return null;
	}

	@Override
	protected Class<Proforma> leggiClassVO() {
		return null;
	}

	@Override
	protected Class<ProformaView> leggiClassView() {
		return null;
	}
	
	public void exportExcell(List<ParcellaRow> parcellaRows, HttpServletResponse respons,boolean processato) throws IOException {
	WriteExcell excell= new WriteExcell();
	excell.addHeader("Società Di Addebito", WriteExcell.TYPECELL_STRING);
	excell.addHeader("Centro Di Costo", WriteExcell.TYPECELL_STRING);
	excell.addHeader("Voce Di Conto", WriteExcell.TYPECELL_STRING);
	excell.addHeader("Paese", WriteExcell.TYPECELL_STRING);
	excell.addHeader("Controparte", WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
	excell.addHeader("Legale Esterno", WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
	excell.addHeader("Totale Autorizzato", WriteExcell.TYPECELL_NUMBER,excell.cellStyleFormatt_Arigth("#,##0.00"));
	excell.addHeader("Valuta", WriteExcell.TYPECELL_STRING);
	excell.addHeader("Data inserimento", WriteExcell.TYPECELL_DATE,excell.cellStyleFormatt_Acenter("dd/MM/YYYY"));
	excell.addHeader("Data Autorizzazione", WriteExcell.TYPECELL_DATE,excell.cellStyleFormatt_Acenter("dd/MM/YYYY"));
	
	for(ParcellaRow a:parcellaRows){
	
		for(ProformaRow prf:a.getProformasRow()){
			Vector<Object> row = new Vector<Object>();
		Proforma p=prf.getProformas();
		if(p!=null) 
		row.add(a.getSocieta()!=null && a.getSocieta().getRagioneSociale()!=null?a.getSocieta().getRagioneSociale():"--");
		row.add(" "+p.getCentroDiCosto()!=null?p.getCentroDiCosto():"--");
		row.add(" "+p.getVoceDiConto()!=null?p.getVoceDiConto():"--");
		row.add(prf.getPaese().getDescrizione());
		row.add(prf.getControparte().getNome());
		row.add(prf.getLegaleEsterno().getNome()+" "+prf.getLegaleEsterno().getCognome());
		row.add(p.getTotaleAutorizzato()!=null?p.getTotaleAutorizzato():new BigDecimal(0));
		row.add(p.getTipoValuta()!=null && p.getTipoValuta().getNome()!=null?p.getTipoValuta().getNome():"--");
		row.add(p.getDataInserimento()!=null?p.getDataInserimento():null);
		row.add(p.getDataAutorizzazione()!=null?p.getDataAutorizzazione():null); 
		excell.addRowBody(row);
		}
		 
	}
	excell.addSheet("RiepilogoAllProforma");
	excell.createSheet().getCurrentSheet().setDefaultColumnWidth((int) 30);
	
	for(ParcellaRow a:parcellaRows){
		
		for(ProformaRow prf:a.getProformasRow()){
			Vector<Object> row = new Vector<Object>();
		Proforma p=prf.getProformas();
		if(p!=null) 
		row.add(a.getSocieta()!=null && a.getSocieta().getRagioneSociale()!=null?a.getSocieta().getRagioneSociale():"--");
		row.add(" "+p.getCentroDiCosto()!=null?p.getCentroDiCosto():"--");
		row.add(" "+p.getVoceDiConto()!=null?p.getVoceDiConto():"--");
		row.add(prf.getPaese().getDescrizione());
		row.add(prf.getControparte().getNome());
		row.add(prf.getLegaleEsterno().getNome()+" "+prf.getLegaleEsterno().getCognome());
		row.add(p.getTotaleAutorizzato()!=null?p.getTotaleAutorizzato():new BigDecimal(0));
		row.add(p.getTipoValuta()!=null && p.getTipoValuta().getNome()!=null?p.getTipoValuta().getNome():"--");
		row.add(p.getDataInserimento()!=null?p.getDataInserimento():null);
		row.add(p.getDataAutorizzazione()!=null?p.getDataAutorizzazione():null); 
		excell.addRowBody(row);
		}
		excell.addSheet(a.getSocieta().getRagioneSociale());
		excell.createSheet().getCurrentSheet().setDefaultColumnWidth((int) 30);
	}
	
	
	if(processato)
	excell.setNomeFile("ProformaProcessati.xls");
	if(!processato)
	excell.setNomeFile("ProformaNonProcessati.xls");	
	
	//excell.createSheet().getCurrentSheet().setDefaultColumnWidth((int) 45);
 	
 	//excell.getCurrentSheet().setDefaultColumnWidth((int) 65);
 	/*
 	excell.getCurrentSheet().setColumnWidth((int)1,(int)4000);
 	excell.getCurrentSheet().setColumnWidth((int)2,(int)14500);
 	excell.getCurrentSheet().setColumnWidth((int)3,(int)14500);
 	excell.getCurrentSheet().setColumnWidth((int)4,(int)4000);
 	excell.getCurrentSheet().setColumnWidth((int)5,(int)4000);
 	excell.getCurrentSheet().setColumnWidth((int)6,(int)4000);
 	excell.getCurrentSheet().setColumnWidth((int)7,(int)4000);
 	*/
 	excell.writeDownloadWorkbook(respons);
	
	
	
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public Proforma setProformaLetto(long id,String utenteProcessamento) throws Throwable{
		
		Proforma proforma= null;
				
		proforma=proformaDAO.leggi(id);
	 if(proforma!=null){
		proforma.setUtenteProcessamento(utenteProcessamento);
		proforma.setProcessato("T");
		proforma.setDataProcessamento(new Date());
		proformaDAO.modifica(proforma);
	 }
		return proforma;
	}

	@Override
	public List<Nazione> getListaProformaNazione(long idProforma) throws Throwable {
		 		return proformaDAO.getListaProformaNazione(idProforma);
	}

	@Override
	public List<Controparte> getListaProformaControparte(long idProforma) throws Throwable {
		return proformaDAO.getListaProformaControparte(idProforma);
	}

	@Override
	public List<Fascicolo> getListaProformaFascicolo(long idProforma) throws Throwable {
		return proformaDAO.getListaProformaFascicolo(idProforma);
	}
	
	@Override
	public List<ProfessionistaEsterno> getListaProformaProfessionistaEsterno(long idProforma) throws Throwable {
		return proformaDAO.getListaProformaProfessionistaEsterno(idProforma);
	}
	
	//Per Ordinamento
	@Override
	public List<ParcellaRest> getParcellaRest(boolean processato,long idSocieta,String colSort,String sort) throws Throwable {
		 List<ParcellaRest> proformaRest=proformaDAO.getParcellaRest(processato, idSocieta, colSort, sort);
		if(proformaRest!=null && proformaRest.size()>0)
			return proformaRest;
		    return null;
	}
	
}
