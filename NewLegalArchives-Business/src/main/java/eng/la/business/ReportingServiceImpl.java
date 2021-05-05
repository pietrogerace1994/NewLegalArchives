package eng.la.business;

import java.awt.Color;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.hibernate.FetchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.GrayColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import eng.la.model.Atto;
import eng.la.model.AttoWf;
import eng.la.model.Controparte;
import eng.la.model.Fascicolo;
import eng.la.model.Fattura;
import eng.la.model.Incarico;
import eng.la.model.OrganoSociale;
import eng.la.model.ProfessionistaEsterno;
import eng.la.model.Proforma;
import eng.la.model.RFascicoloGiudizio;
import eng.la.model.RFascicoloSocieta;
import eng.la.model.RIncaricoProformaSocieta;
import eng.la.model.RProfEstSpec;
import eng.la.model.RProfessionistaNazione;
import eng.la.model.RProformaFattura;
import eng.la.model.SchedaFondoRischi;
import eng.la.model.SettoreGiuridico;
import eng.la.model.Societa;
import eng.la.model.StatoAtto;
import eng.la.model.StatoFascicolo;
import eng.la.model.StatoIncarico;
import eng.la.model.StatoProforma;
import eng.la.model.TipoContenzioso;
import eng.la.model.TipologiaFascicolo;
import eng.la.model.Utente;
import eng.la.model.custom.Stanziamenti;
import eng.la.model.view.FascicoloView;
import eng.la.model.view.SchedaFondoRischiView;
import eng.la.model.view.StoricoSchedaFondoRischiView;
import eng.la.model.view.UtenteView;
import eng.la.model.view.VendorManagementView;
import eng.la.persistence.AnagraficaStatiTipiDAO;
import eng.la.persistence.AttoDAO;
import eng.la.persistence.ControparteDAO;
import eng.la.persistence.CostantiDAO;
import eng.la.persistence.FascicoloDAO;
import eng.la.persistence.IncaricoDAO;
import eng.la.persistence.OrganoSocialeDAO;
import eng.la.persistence.ProfessionistaEsternoDAO;
import eng.la.persistence.SettoreGiuridicoDAO;
import eng.la.persistence.SocietaDAO;
import eng.la.persistence.TipologiaFascicoloDAO;
import eng.la.persistence.UtenteDAO;
import eng.la.persistence.workflow.AttoWfDAO;
import eng.la.util.DateUtil;
import eng.la.util.DateUtil2;
import eng.la.util.SpringUtil;
import eng.la.util.WriteExcell;

@Service("reportingService")
public class ReportingServiceImpl extends BaseService<Fascicolo,FascicoloView> implements ReportingService {


	@Autowired
	private AttoDAO attoDao;

	@Autowired
	private AttoWfDAO attoWfDao;

	@Autowired
	private SocietaDAO societaDAO;

	@Autowired
	private FascicoloDAO fascicoloDAO;
	
	@Autowired
	private OrganoSocialeDAO organoSocialeDAO;

	@Autowired
	private UtenteDAO utenteDAO;	

	@Autowired
	private ProfessionistaEsternoDAO professionistaEsternoDAO;

	@Autowired
	private SettoreGiuridicoDAO settoreGiuridico;

	@Autowired
	private TipologiaFascicoloDAO tipologiaFascicolo;


	@Autowired
	private AnagraficaStatiTipiDAO anagraficaStatiTipiDAO;

	@Autowired
	private IncaricoDAO incararicoDAO;

	@Autowired
	private ControparteDAO controparteDAO;

	@Autowired
	private FascicoloService fascicoloService;

	@Autowired
	private StoricoSchedaFondoRischiService storicoSchedaFondoRischiService;



	@Override
	public List<Societa> getlistSocieta() throws Throwable{
		return (List<Societa>)societaDAO.leggi(false); 
	}

	@Override
	public List<Utente> getGcDestinatario() throws Throwable{

		List<Utente> utentes=(List<Utente>) utenteDAO.getUtentiGC();

		return utentes;

	}

	@Override
	public List<StatoAtto> getListaStatoAttoPerLingua(String lang) throws Throwable {

		return attoDao.getListaStatoAttoPerLingua(lang);
	}

	@Override
	public List<Utente> getListOwner() throws Throwable {

		return utenteDAO.leggiUtenti(false);
	}

	@Override
	public List<ProfessionistaEsterno> getListProfessionistaEsterno() throws Throwable {

		return professionistaEsternoDAO.leggi(false);
	}

	@Override
	public List<SettoreGiuridico> getListSettoreGiuridico(String lang) throws Throwable {

		return settoreGiuridico.leggi(lang);
	}

	@Override
	public List<StatoFascicolo> getListStatoFascicolo(String lang) throws Throwable {

		return anagraficaStatiTipiDAO.leggiStatiFascicolo(lang);
	}

	@Override
	public List<StatoIncarico> getListStatoIncarico(String lang) throws Throwable {

		return   anagraficaStatiTipiDAO.leggiStatiIncarico(lang); 
	}

	@Override
	public List<TipologiaFascicolo> getListTipologiaFascicolo(String lang) throws Throwable {

		return tipologiaFascicolo.leggi(lang, false);
	}

	@Override
	public List<TipoContenzioso> getListTipoContenzioso(String lang) throws Throwable {

		return anagraficaStatiTipiDAO.leggiTipiContenzioso(lang, false);
	}


	@Override
	public List<StatoProforma> getListStatoProforma(String lang) throws Throwable {
		return anagraficaStatiTipiDAO.leggiStatiProforma(lang);
	}



	private void exportAttiXls(List<Atto> atti,String associatoAFascicolo, HttpServletResponse respons,String localLang) throws IOException {
		WriteExcell excell= new WriteExcell();


		String languages =(localLang!=null && !localLang.trim().equals(""))?localLang:"IT";
		String[][] lang=new String[2][21];

		lang[0][0]="NUMERO PROTOCOLLO";
		lang[0][1]="STATO ATTO";
		lang[0][2]="DATA NOTIFICA";
		lang[0][3]="PARTE NOTIFICANTE";
		lang[0][4]="DESTINATARIO";
		lang[0][5]="TIPO ATTO GIUDIZIARIO";
		lang[0][6]="DATA COMPOSIZIONE";
		lang[0][7]="DATA ULTIMA MODIFICA";
		lang[0][8]="SOCIETA'/DIV. INTERESSATA";
		lang[0][9]="UTENTE INV ALTRI UFF";
		lang[0][10]="UL INV ALTRI UFF";
		lang[0][11]="EMAIL INV ALTRI UFF";
		lang[0][12]="NUMERO FASCICOLO";
		lang[0][13]="OGGETTO";
		lang[0][14]="LEGALE INTERNO";
		lang[0][15]="STATO FASCICOLO";
		lang[0][16]="COMPOSTO DA";
		lang[0][17]="MODIFICATO DA";
		lang[0][18]="UTENTE WORK FLOW ATTO";
		lang[0][19]="DATA REGISTRAZIONE ATTO";
		lang[0][20]="DATA AVVIO WORK ATTO";


		lang[1][0] = "PROTOCOL NUMBER";
		lang[1][1] = "ACT STATE";
		lang[1][2] = "NOTIFIED";
		lang[1][3] = "THE NOTIFYING";
		lang[1][4] = "RECIPIENT";
		lang[1][5] = "TYPE JUDICIAL ACT";
		lang[1][6] = "DATA COMPOSITION";
		lang[1][7] = "DEADLINE MODIFICATION";
		lang[1][8] = "COMPANY / DIV. INVOLVED";
		lang[1][9] = "USER INV OTHER UFF";
		lang[1][10] = "UL INV OTHER UFF";
		lang[1][11] = "EMAIL INV OTHER UFF";
		lang[1][12] = "DOSSIER NUMBER";
		lang[1][13] = "OBJECT";
		lang[1][14] = "INTERNAL LEGAL";
		lang[1][15] = "DOSSIER STATUS";
		lang[1][16] = "TO BE MADE";
		lang[1][17] = "CHANGED FROM";
		lang[1][18] = "WORK FLOW ACT";
		lang[1][19] = "DATA REGISTRATION ACT";
		lang[1][20] = "DATA START WORK ACT";


		int language=0;
		if(languages.equalsIgnoreCase("IT"))
			language=0;
		if(languages.equalsIgnoreCase("EN"))
			language=1;


		excell.addHeader(lang[language][0], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][1], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][2], WriteExcell.TYPECELL_DATE,excell.cellStyleFormatt_Acenter("dd/MM/YYYY"));
		excell.addHeader(lang[language][3], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][4], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][5], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][6],  WriteExcell.TYPECELL_DATE,excell.cellStyleFormatt_Acenter("dd/MM/YYYY"));
		excell.addHeader(lang[language][7], WriteExcell.TYPECELL_DATE,excell.cellStyleFormatt_Acenter("dd/MM/YYYY"));
		excell.addHeader(lang[language][8], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][9], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][10], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][11],WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][12],WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][13],WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][14],WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][15],WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][16],WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		//(Annullo il campo), attualmente non ci sono possibilità di risalire all' utente che effettua la modifica
		//excell.addHeader(lang[language][17],WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][18],WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][19], WriteExcell.TYPECELL_DATE,excell.cellStyleFormatt_Acenter("dd/MM/YYYY"));
		excell.addHeader(lang[language][20], WriteExcell.TYPECELL_DATE,excell.cellStyleFormatt_Acenter("dd/MM/YYYY"));



		UtenteService utenteService = (UtenteService) SpringUtil.getBean("utenteService");
		if(atti!=null)
			for(Atto a:atti){
				Vector<Object> row = new Vector<Object>();
				//NUMERO PROTOCOLLO
				row.add(a.getNumeroProtocollo()!=null?a.getNumeroProtocollo():new String(""));
				//STATO ATTO
				row.add(a.getStatoAtto()!=null?(a.getStatoAtto().getDescrizione()!=null?a.getStatoAtto().getDescrizione():""):"");	
				//DATA NOTIFICA
				row.add(a.getDataNotifica()!=null?a.getDataNotifica():null);
				//PARTE NOTIFICANTE
				row.add(a.getParteNotificante()!=null? a.getParteNotificante():"");
				String destinatario=null;
				try {
					if(a.getDestinatario()!=null)
						destinatario=((Utente)utenteDAO.leggiUtenteDaMatricola(a.getDestinatario())).getNominativoUtil();
				} catch (Throwable e) {}
				//DESTINATARIO
				row.add(destinatario!=null?destinatario:"");
				//TIPO ATTO GIUDIZIARIO
				row.add(a.getTipoAtto()!=null?a.getTipoAtto():new String("")); 
				//DATA COMPOSIZIONE
				row.add(a.getDataCreazione()!=null?a.getDataCreazione():null);
				//DATA ULTIMA MODIFICA
				row.add(a.getDataUltimaModifica()!=null?a.getDataUltimaModifica():null);
				//SOCIETA'/DIV. INTERESSATA
				row.add(a.getSocieta()!=null?(a.getSocieta().getRagioneSociale()!=null?a.getSocieta().getRagioneSociale():""):"");

				//colonne popolate solo se lo stato è inviato ad altri uffici
				if(a.getStatoAtto()!=null && a.getStatoAtto().getCodGruppoLingua().equalsIgnoreCase("IAU")){
					//UTENTE INV ALTRI UFF
					row.add(a.getUtenteInvioAltriUffici()!=null?a.getUtenteInvioAltriUffici():new String(""));
					//UL INV ALTRI UFF
					row.add(a.getUnitaLegInvioAltriUffici()!=null?a.getUnitaLegInvioAltriUffici():new String(""));
					//EMAIL INV ALTRI UFF
					row.add(a.getEmailInvioAltriUffici()!=null?a.getEmailInvioAltriUffici():new String(""));
				}else{
					row.add("");
					row.add("");
					row.add("");
				}
				if(associatoAFascicolo!=null && associatoAFascicolo.equalsIgnoreCase("SI")){
					//NUMERO FASCICOLO
					row.add(a.getFascicolo()!=null?(a.getFascicolo().getNome()!=null?a.getFascicolo().getNome():""):"");	
				}else{ row.add(""); }
				//OGGETTO
				row.add(a.getFascicolo()!=null?(a.getFascicolo().getOggettoSintetico()!=null?a.getFascicolo().getOggettoSintetico():""):"");	

				String legale_interno=null;
				try {
					if(a.getFascicolo()!=null && a.getFascicolo().getLegaleInterno()!=null)
						legale_interno=((Utente)utenteDAO.leggiUtenteDaUserId(a.getFascicolo().getLegaleInterno())).getNominativoUtil();
				} catch (Throwable e) { }

				//LEGALE INTERNO
				row.add(legale_interno!=null?legale_interno:"");	
				//STATO FASCICOLO
				row.add(a.getFascicolo()!=null?(a.getFascicolo().getStatoFascicolo()!=null?a.getFascicolo().getStatoFascicolo().getDescrizione():""):"");	
				try{
					String compostoDa=((Utente)utenteDAO.leggiUtenteDaUserId(a.getCreatoDa())).getNominativoUtil();
					//COMPOSTO DA
					row.add(compostoDa!=null?compostoDa:"");
				}catch(Throwable t){
					row.add("");
				} 

				AttoWf attoWf=null;
				try {
					attoWf= attoWfDao.leggiWorkflowInCorso(a.getId());
					Utente utente=null;
					if(attoWf!=null){
						UtenteView utenteView= utenteService.leggiAssegnatarioWfAtto(a.getId());	 
						utente=utenteView.getVo(); //utenteDAO.leggiAssegnatarioCorrenteOwnerAtto(StepId);	 
					}

					// UTENTE WORK FLOW ATTO
					row.add(utente!=null?(utente.getNominativoUtil()!=null?utente.getNominativoUtil():""):"");
				} catch (Throwable e1) { row.add(""); }

				//DATA REGISTRAZIONE ATTO
				row.add(a.getDataRegistrazione()!=null?a.getDataRegistrazione():null);

				//DATA AVVIO WORK ATTO
				row.add(attoWf!=null?(attoWf.getDataCreazione()!=null?attoWf.getDataCreazione():null):null);

				excell.addRowBody(row);

			}
		excell.setNomeFile("Report-Atti.xls");

		excell.createSheet().getCurrentSheet().setDefaultColumnWidth((int) 45);


		excell.write(respons);
	}

	/*private void exportFascicoloXls(List<Fascicolo> fascicoli, HttpServletResponse respons,String localLang) throws Throwable {
		WriteExcell excell= new WriteExcell();

		String languages =(localLang!=null && !localLang.trim().equals(""))?localLang:"IT";

		String[][] lang=new String[2][18];
		lang[0][0]="NUMERO FASCICOLO";
		lang[0][1]="TIPO FASCICOLO";
		lang[0][2]="SETTORE GIURIDICO";
		lang[0][3]="PAESE";
		lang[0][4]="SOCIETA'";
		lang[0][5]="POSIZIONE SOCIETA'";
		lang[0][6]="LEGALE INTERNO OWNER";
		lang[0][7]="UNITA LEGALE";
		lang[0][8]="DATA CREAZIONE";
		lang[0][9]="STATO";
		lang[0][10]="OGGETTO";
		lang[0][11]="TIPO GIUDIZIO";
		lang[0][12]="ORGANO GIUDICANTE";
		lang[0][13]="FORO";
		lang[0][14]="VALORE DELLA CAUSA";
		lang[0][15]="VALORE DELLA PRATICA";
		lang[0][16]="DENOMINAZIONE STUDIO PROFESSIONISTA";
		lang[0][17]="COSTI CONSUNTIVATI";


		lang[1][0]="DOSSIER NUMBER";
		lang[1][1]="DOSSIER TYPE";
		lang[1][2]="LEGAL AREA";
		lang[1][3]="COUNTRY";
		lang[1][4]="COMPANY";
		lang[1][5]="POSITION COMPANY'";
		lang[1][6]="INTERNAL LEGAL OWNER";
		lang[1][7]="LEGAL UNIT";
		lang[1][8]="CREATION DATE";
		lang[1][9]="STATE";
		lang[1][10]="OBJECT";
		lang[1][11]="TYPE RATING";
		lang[1][12]="ORGAN JUDGMENTAL";
		lang[1][13]="HOLE";
		lang[1][14]="VALUE OF THE CASE";
		lang[1][15]="VALUE OF PRACTICE";
		lang[1][16]="PROFESSIONAL STUDIO NAME";
		lang[1][17]="CONSUNTIVATI COSTS";


		int language=0;
		if(languages.equalsIgnoreCase("IT"))
			language=0;
		if(languages.equalsIgnoreCase("EN"))
			language=1;

		excell.addHeader(lang[language][0], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][1], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][2], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][3], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][4], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][5], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][6],  WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][7], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][8], WriteExcell.TYPECELL_DATE,excell.cellStyleFormatt_Acenter("dd/MM/YYYY"));
		excell.addHeader(lang[language][9], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][10], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][11],WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][12],WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][13],WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][14],WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][15],WriteExcell.TYPECELL_NUMBER,excell.cellStyleFormatt_Aleft("#,##"));
		excell.addHeader(lang[language][16],WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][17],WriteExcell.TYPECELL_NUMBER,excell.cellStyleFormatt_Aleft("#,##"));




		if(fascicoli!=null)
			for(Fascicolo fs:fascicoli){
				Vector<Object> row = new Vector<Object>();
				//NUMERO FASCICOLO
				Fascicolo f=fascicoloDAO.leggi(fs.getId(), FetchMode.JOIN);
				row.add(f.getNome()!=null?f.getNome():"");
				//TIPO FASCICOLO
				row.add(f.getTipologiaFascicolo()!=null?(f.getTipologiaFascicolo().getDescrizione()!=null?f.getTipologiaFascicolo().getDescrizione():""):"");	
				//SETTORE GIURIDICO
				row.add(f.getSettoreGiuridico()!=null?(f.getSettoreGiuridico().getNome()!=null?f.getSettoreGiuridico().getNome():""):"");	
				//PAESE
				row.add(f.getNazione()!=null?(f.getNazione().getDescrizione()!=null?f.getNazione().getDescrizione():""):"");	
				//SOCIETA
				RFascicoloSocieta rFascicoloSocieta=null;//f.getRFascicoloSocietas()!=null?f.getRFascicoloSocietas().iterator().next():null;

				// List<RFascicoloSocieta> listRfascioloSocieta=fascicoloDAO.getRFascicoloSocietas(f.getId());
				if(f.getRFascicoloSocietas()!=null && f.getRFascicoloSocietas().iterator()!=null){
					Set<RFascicoloSocieta> rIter= f.getRFascicoloSocietas();
					if(rIter.iterator().hasNext()){
						rFascicoloSocieta=rIter.iterator().next();
					}

				}
				row.add(rFascicoloSocieta!=null?(rFascicoloSocieta.getSocieta()!=null?rFascicoloSocieta.getSocieta().getRagioneSociale():""):"");

				//POSIZIONE SOCIETA  Valorizzato solo in caso di giudiziale civile o amministrativo
				//SOLO IN CASO  GIUDIZIALE  CIVILE PENALE															//giudiziale 
				if(f.getTipologiaFascicolo()!=null && f.getTipologiaFascicolo().getCodGruppoLingua().equalsIgnoreCase("TFSC_1") ||
						//civile - amministrativo				
						f.getSettoreGiuridico()!=null && (f.getSettoreGiuridico().getCodGruppoLingua().equalsIgnoreCase("TSTT_1") 
								|| f.getSettoreGiuridico().getCodGruppoLingua().equalsIgnoreCase("TSTT_3"))){

					row.add(rFascicoloSocieta!=null?(rFascicoloSocieta.getPosizioneSocieta()!=null?rFascicoloSocieta.getPosizioneSocieta().getNome():""):"");


				}else{
					row.add("");
				}	
				Utente legale_interno=null;
				try {
					if(f.getLegaleInterno()!=null && !f.getLegaleInterno().trim().equals(""))
						legale_interno=((Utente)utenteDAO.leggiUtenteDaUserId(f.getLegaleInterno()));
				} catch (Throwable e) { }

				//LEGALE INTERNO OWNER
				row.add(legale_interno!=null?legale_interno.getNominativoUtil():"");	


				//UNITA LEGALE
				row.add(legale_interno!=null?legale_interno.getDescrUnitaAppart():"");	

				//DATA CREAZIONE
				row.add(f.getDataCreazione()!=null?f.getDataCreazione():null);

				//STATO
				row.add(f.getStatoFascicolo()!=null?f.getStatoFascicolo().getDescrizione():"");

				//OGGETTO 
				row.add(f.getOggettoSintetico()!=null?f.getOggettoSintetico():"");

				//SOLO IN CASO  GIUDIZIALE  CIVILE PENALE
				if(f.getTipologiaFascicolo()!=null && f.getTipologiaFascicolo().getCodGruppoLingua().equalsIgnoreCase("TFSC_1") ||
						//civile - amministrativo				
						f.getSettoreGiuridico()!=null && (f.getSettoreGiuridico().getCodGruppoLingua().equalsIgnoreCase("TSTT_1") 
								|| f.getSettoreGiuridico().getCodGruppoLingua().equalsIgnoreCase("TSTT_3"))){

					Collection<RFascicoloGiudizio> rFGiudizios= f.getRFascicoloGiudizios();
					if(rFGiudizios!=null && rFGiudizios.iterator().hasNext()){
						RFascicoloGiudizio rfascGiudizio= rFGiudizios.iterator().next();
						if(rfascGiudizio!=null && rfascGiudizio.getDataCancellazione()==null){

							//TIPO GIUDIZIO
							row.add(rfascGiudizio.getGiudizio()!=null?rfascGiudizio.getGiudizio().getDescrizione():"");	
							//ORGANO GIUDICANTE
							row.add(rfascGiudizio.getOrganoGiudicante()!=null?rfascGiudizio.getOrganoGiudicante().getNome():"");

							//FORO
							row.add(rfascGiudizio.getForo()!=null?rfascGiudizio.getForo():"");

						} else { row.add(""); row.add(""); row.add(""); } 
					} else { row.add(""); row.add(""); row.add(""); }
				}  else { row.add(""); row.add(""); row.add(""); }


				//VALORE DELLA CAUSA
				//SOLO IN CASO  GIUDIZIALE
				if(f.getTipologiaFascicolo()!=null && f.getTipologiaFascicolo().getCodGruppoLingua().equalsIgnoreCase("TFSC_1")){
					row.add(f.getValoreCausa()!=null?(f.getValoreCausa().getNome()!=null?f.getValoreCausa().getNome():""):"");
				}else{ row.add(""); }

				//VALORE DELLA PRATICA
				//SOLO IN CASO  STRAGIUDIZIALI E NOTARILE
				if(f.getTipologiaFascicolo()!=null && (f.getTipologiaFascicolo().getCodGruppoLingua().equalsIgnoreCase("TFSC_2") || f.getTipologiaFascicolo().getCodGruppoLingua().equalsIgnoreCase("TFSC_4"))){
					row.add(f.getValoreCausaPratica()!=null?f.getValoreCausaPratica():"INDETERMINATO");
				}else{ row.add(""); }


				Collection<Incarico> incarichi=f.getIncaricos();

				String  studioLegDenominazione="";
				double costiConsuntivati=0;
				if(incarichi!=null)
					for(Incarico ic:incarichi){
						studioLegDenominazione+=(ic.getProfessionistaEsterno()!=null && ic.getProfessionistaEsterno().getStudioLegale()!=null)? ic.getProfessionistaEsterno().getStudioLegale().getDenominazione():"";	
						studioLegDenominazione+="; ";

						//SOLO IN CASO  DI FASCICOLO IN STATO APERTO
						if(f.getStatoFascicolo()!=null && f.getStatoFascicolo().getCodGruppoLingua().equalsIgnoreCase("A")){
							//SOLO IN CASO  DI INCARICO IN STATO AUTORIZZATO
							if(ic.getStatoIncarico()!=null && ic.getStatoIncarico().getCodGruppoLingua().equalsIgnoreCase("A")){
								List<Proforma> proformas=incararicoDAO.leggiProformaAssociatoIncarico(ic.getId());
								if(proformas!=null)
									for(Proforma p:proformas){
										try{
											//SOLO IN CASO  DI PROFORMA IN STATO AUTORIZZATO
											if(p.getStatoProforma()!=null && p.getStatoProforma().getCodGruppoLingua().equalsIgnoreCase("A")){

												costiConsuntivati+= (p!=null && p.getTotaleAutorizzato()!=null)? p.getTotaleAutorizzato().doubleValue():0;
											}
										}catch(Exception e){ }
									}
							}
						}
					}

				//DENOMINAZIONE STUDIO PROFESSIONISTA
				row.add(studioLegDenominazione);

				//COSTI CONSUNTIVATI
				row.add(costiConsuntivati);

				excell.addRowBody(row);
			}
		excell.setNomeFile("Report-Fascicoli.xls");

		excell.createSheet().getCurrentSheet().setDefaultColumnWidth((int) 38);

		excell.write(respons);
	}*/


	private void exportFascicoloXlsRFS(List<RFascicoloSocieta> fascicoli, HttpServletResponse respons,String localLang) throws Throwable {
		WriteExcell excell= new WriteExcell();

		String languages =(localLang!=null && !localLang.trim().equals(""))?localLang:"IT";

		String[][] lang=new String[2][18];
		lang[0][0]="NUMERO FASCICOLO";
		lang[0][1]="TIPO FASCICOLO";
		lang[0][2]="SETTORE GIURIDICO";
		lang[0][3]="PAESE";
		lang[0][4]="SOCIETA'";
		lang[0][5]="POSIZIONE SOCIETA'";
		lang[0][6]="LEGALE INTERNO OWNER";
		lang[0][7]="UNITA LEGALE";
		lang[0][8]="DATA CREAZIONE";
		lang[0][9]="STATO";
		lang[0][10]="OGGETTO";
		lang[0][11]="TIPO GIUDIZIO";
		lang[0][12]="ORGANO GIUDICANTE";
		lang[0][13]="FORO";
		lang[0][14]="VALORE DELLA CAUSA";
		lang[0][15]="VALORE DELLA PRATICA";
		lang[0][16]="DENOMINAZIONE STUDIO PROFESSIONISTA";
		lang[0][17]="COSTI CONSUNTIVATI";


		lang[1][0]="DOSSIER NUMBER";
		lang[1][1]="DOSSIER TYPE";
		lang[1][2]="LEGAL AREA";
		lang[1][3]="COUNTRY";
		lang[1][4]="COMPANY";
		lang[1][5]="POSITION COMPANY'";
		lang[1][6]="INTERNAL LEGAL OWNER";
		lang[1][7]="LEGAL UNIT";
		lang[1][8]="CREATION DATE";
		lang[1][9]="STATE";
		lang[1][10]="OBJECT";
		lang[1][11]="TYPE RATING";
		lang[1][12]="ORGAN JUDGMENTAL";
		lang[1][13]="HOLE";
		lang[1][14]="VALUE OF THE CASE";
		lang[1][15]="VALUE OF PRACTICE";
		lang[1][16]="PROFESSIONAL STUDIO NAME";
		lang[1][17]="CONSUNTIVATI COSTS";


		int language=0;
		if(languages.equalsIgnoreCase("IT"))
			language=0;
		if(languages.equalsIgnoreCase("EN"))
			language=1;

		excell.addHeader(lang[language][0], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][1], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][2], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][3], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][4], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][5], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][6],  WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][7], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][8], WriteExcell.TYPECELL_DATE,excell.cellStyleFormatt_Acenter("dd/MM/YYYY"));
		excell.addHeader(lang[language][9], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][10], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][11],WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][12],WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][13],WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][14],WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][15],WriteExcell.TYPECELL_NUMBER,excell.cellStyleFormatt_Aleft("#,##"));
		excell.addHeader(lang[language][16],WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][17],WriteExcell.TYPECELL_NUMBER,excell.cellStyleFormatt_Aleft("#,##"));


		if(fascicoli!=null)
			for(RFascicoloSocieta rfs:fascicoli){

				//NUMERO FASCICOLO
				Fascicolo f=fascicoloDAO.leggiConPermessi(rfs.getFascicolo().getId(), FetchMode.JOIN);
				if(f!=null){
					Vector<Object> row = new Vector<Object>();	
					row.add(f.getNome()!=null?f.getNome():"");
					//TIPO FASCICOLO
					row.add(f.getTipologiaFascicolo()!=null?(f.getTipologiaFascicolo().getDescrizione()!=null?f.getTipologiaFascicolo().getDescrizione():""):"");	
					//SETTORE GIURIDICO
					row.add(f.getSettoreGiuridico()!=null?(f.getSettoreGiuridico().getNome()!=null?f.getSettoreGiuridico().getNome():""):"");	
					//PAESE
					row.add(f.getNazione()!=null?(f.getNazione().getDescrizione()!=null?f.getNazione().getDescrizione():""):"");	
					//SOCIETA

					row.add(rfs.getSocieta()!=null?rfs.getSocieta().getRagioneSociale():"");

					//POSIZIONE SOCIETA  Valorizzato solo in caso di giudiziale civile o amministrativo
					//SOLO IN CASO  GIUDIZIALE  CIVILE PENALE															//giudiziale 
					if(f.getTipologiaFascicolo()!=null && f.getTipologiaFascicolo().getCodGruppoLingua().equalsIgnoreCase("TFSC_1") ||
							//civile - amministrativo				
							f.getSettoreGiuridico()!=null && (f.getSettoreGiuridico().getCodGruppoLingua().equalsIgnoreCase("TSTT_1") 
									|| f.getSettoreGiuridico().getCodGruppoLingua().equalsIgnoreCase("TSTT_3"))){

						//VECCHIO
						//row.add(rFascicoloSocieta!=null?(rFascicoloSocieta.getPosizioneSocieta()!=null?rFascicoloSocieta.getPosizioneSocieta().getNome():""):"");

						//NUOVO
						row.add(rfs.getPosizioneSocieta()!=null?rfs.getPosizioneSocieta().getNome():"");

					}else{
						row.add("");
					}	

					Utente legale_interno=null;
					try {
						if(f.getLegaleInterno()!=null && !f.getLegaleInterno().trim().equals(""))
							legale_interno=((Utente)utenteDAO.leggiUtenteDaUserId(f.getLegaleInterno()));
					} catch (Throwable e) { }

					//LEGALE INTERNO OWNER
					row.add(legale_interno!=null?legale_interno.getNominativoUtil():"");	

					//UNITA LEGALE
					row.add(legale_interno!=null?legale_interno.getDescrUnitaAppart():"");	

					//DATA CREAZIONE
					row.add(f.getDataCreazione()!=null?f.getDataCreazione():null);

					//STATO
					row.add(f.getStatoFascicolo()!=null?f.getStatoFascicolo().getDescrizione():"");

					//OGGETTO 
					row.add(f.getOggettoSintetico()!=null?f.getOggettoSintetico():"");

					//SOLO IN CASO  GIUDIZIALE  CIVILE PENALE
					if(f.getTipologiaFascicolo()!=null && f.getTipologiaFascicolo().getCodGruppoLingua().equalsIgnoreCase("TFSC_1") ||
							//civile - amministrativo				
							f.getSettoreGiuridico()!=null && (f.getSettoreGiuridico().getCodGruppoLingua().equalsIgnoreCase("TSTT_1") 
									|| f.getSettoreGiuridico().getCodGruppoLingua().equalsIgnoreCase("TSTT_3"))){

						Collection<RFascicoloGiudizio> rFGiudizios= f.getRFascicoloGiudizios();
						if(rFGiudizios!=null && rFGiudizios.iterator().hasNext()){
							RFascicoloGiudizio rfascGiudizio= rFGiudizios.iterator().next();
							if(rfascGiudizio!=null && rfascGiudizio.getDataCancellazione()==null){

								//TIPO GIUDIZIO
								row.add(rfascGiudizio.getGiudizio()!=null?rfascGiudizio.getGiudizio().getDescrizione():"");	
								//ORGANO GIUDICANTE
								row.add(rfascGiudizio.getOrganoGiudicante()!=null?rfascGiudizio.getOrganoGiudicante().getNome():"");

								//FORO
								row.add(rfascGiudizio.getForo()!=null?rfascGiudizio.getForo():"");

							} else { row.add(""); row.add(""); row.add(""); } 
						} else { row.add(""); row.add(""); row.add(""); }
					}  else { row.add(""); row.add(""); row.add(""); }


					//VALORE DELLA CAUSA
					//SOLO IN CASO  GIUDIZIALE
					if(f.getTipologiaFascicolo()!=null && f.getTipologiaFascicolo().getCodGruppoLingua().equalsIgnoreCase("TFSC_1")){
						row.add(f.getValoreCausa()!=null?(f.getValoreCausa().getNome()!=null?f.getValoreCausa().getNome():""):"");
					}else{ row.add(""); }

					//VALORE DELLA PRATICA
					//SOLO IN CASO  STRAGIUDIZIALI E NOTARILE
					if(f.getTipologiaFascicolo()!=null && (f.getTipologiaFascicolo().getCodGruppoLingua().equalsIgnoreCase("TFSC_2") || f.getTipologiaFascicolo().getCodGruppoLingua().equalsIgnoreCase("TFSC_4"))){
						row.add(f.getValoreCausaPratica()!=null?f.getValoreCausaPratica():"INDETERMINATO");
					}else{ row.add(""); }

					Collection<Incarico> incarichi=f.getIncaricos();

					String  studioLegDenominazione="";
					double costiConsuntivati=0;
					if(incarichi!=null)
						for(Incarico ic:incarichi){
							studioLegDenominazione+=(ic.getProfessionistaEsterno()!=null && ic.getProfessionistaEsterno().getStudioLegale()!=null)? ic.getProfessionistaEsterno().getStudioLegale().getDenominazione():"";	
							studioLegDenominazione+="; ";

							//SOLO IN CASO  DI FASCICOLO IN STATO APERTO
							if(f.getStatoFascicolo()!=null && f.getStatoFascicolo().getCodGruppoLingua().equalsIgnoreCase("A")){
								//SOLO IN CASO  DI INCARICO IN STATO AUTORIZZATO
								if(ic.getStatoIncarico()!=null && ic.getStatoIncarico().getCodGruppoLingua().equalsIgnoreCase("A")){
									List<Proforma> proformas=incararicoDAO.leggiProformaAssociatoIncarico(ic.getId());
									if(proformas!=null)
										for(Proforma p:proformas){
											try{
												//SOLO IN CASO  DI PROFORMA IN STATO AUTORIZZATO
												if(p.getStatoProforma()!=null && p.getStatoProforma().getCodGruppoLingua().equalsIgnoreCase("A")){

													costiConsuntivati+= (p!=null && p.getTotaleAutorizzato()!=null)? p.getTotaleAutorizzato().doubleValue():0;
												}
											}catch(Exception e){ }
										}
								}
							}
						}

					//DENOMINAZIONE STUDIO PROFESSIONISTA
					row.add(studioLegDenominazione);

					//COSTI CONSUNTIVATI
					row.add(costiConsuntivati);


					excell.addRowBody(row);
				}//F!=null	
			}
		excell.setNomeFile("Report-Fascicoli.xls");

		excell.createSheet().getCurrentSheet().setDefaultColumnWidth((int) 38);

		excell.write(respons);
	}
	
	private void exportOrganiSocialiXLS(List<OrganoSociale> organoSociale, HttpServletResponse respons,String localLang) throws Throwable {
		WriteExcell excell= new WriteExcell();
		
		String languages =(localLang!=null && !localLang.trim().equals(""))?localLang:"IT";
		String[][] lang=new String[2][12];
		
		lang[0][0]="NOME";
		lang[0][1]="COGNOME";
		lang[0][2]="CARICA";
		lang[0][3]="DATA NOMINA";
		lang[0][4]="DATA CESSAZIONE";
		lang[0][5]="DATA SCADENZA";
		lang[0][6]="DATA ACCETTAZIONE CARICA";
		lang[0][7]="EMOLUMENTO";
		lang[0][8]="DATA NASCITA";
		lang[0][9]="LUOGO NASCITA";
		lang[0][10]="CODICE FISCALE";
		lang[0][11]="NOTE";
		
		lang[1][0]="NOME";
		lang[1][1]="COGNOME";
		lang[1][2]="CARICA";
		lang[1][3]="DATA NOMINA";
		lang[1][4]="DATA CESSAZIONE";
		lang[1][5]="DATA SCADENZA";
		lang[1][6]="DATA ACCETTAZIONE CARICA";
		lang[1][7]="EMOLUMENTO";
		lang[1][8]="DATA NASCITA";
		lang[1][9]="LUOGO NASCITA";
		lang[1][10]="CODICE FISCALE";
		lang[1][11]="NOTE";
		
		int language=0;
		if(languages.equalsIgnoreCase("IT"))
			language=0;
		if(languages.equalsIgnoreCase("EN"))
			language=1;

		excell.addHeader(lang[language][0], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][1], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][2], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][3], WriteExcell.TYPECELL_DATE,excell.cellStyleFormatt_Acenter("dd/MM/YYYY"));
		excell.addHeader(lang[language][4], WriteExcell.TYPECELL_DATE,excell.cellStyleFormatt_Acenter("dd/MM/YYYY"));
		excell.addHeader(lang[language][5], WriteExcell.TYPECELL_DATE,excell.cellStyleFormatt_Acenter("dd/MM/YYYY"));
		excell.addHeader(lang[language][6], WriteExcell.TYPECELL_DATE,excell.cellStyleFormatt_Acenter("dd/MM/YYYY"));
		excell.addHeader(lang[language][7], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][8], WriteExcell.TYPECELL_DATE,excell.cellStyleFormatt_Acenter("dd/MM/YYYY"));
		excell.addHeader(lang[language][9], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][10],WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][11],WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		
		if(organoSociale != null){
			for(OrganoSociale organoSocialeItem: organoSociale){
				Vector<Object> row = new Vector<Object>();
				
				row.add(organoSocialeItem.getNome());
				row.add(organoSocialeItem.getCognome());
				row.add(organoSocialeItem.getCarica());
				row.add(organoSocialeItem.getDataNomina());
				row.add(organoSocialeItem.getDataCessazione());
				row.add(organoSocialeItem.getDataScadenza());
				row.add(organoSocialeItem.getDataAccettazioneCarica());
				row.add(organoSocialeItem.getEmolumento());
				row.add(organoSocialeItem.getDataNascita());
				row.add(organoSocialeItem.getLuogoNascita());
				row.add(organoSocialeItem.getCodiceFiscale());
				row.add(organoSocialeItem.getNote());
				excell.addRowBody(row);
			}
			excell.setNomeFile("Report-Organi-Sociali.xls");
	
			excell.createSheet().getCurrentSheet().setDefaultColumnWidth((int) 38);
	
			excell.write(respons);
		}
		
	}

	private void exportProformaXls(List<RIncaricoProformaSocieta> rIncaricoProformaSocieta, HttpServletResponse respons,String localLang) throws Throwable {
		WriteExcell excell= new WriteExcell();

		String languages =(localLang!=null && !localLang.trim().equals(""))?localLang:"IT";
		String[][] lang=new String[2][13];

		lang[0][0]="SOCIETA'";
		lang[0][1]="DENOMINAZIONE STUDIO PROFESSIONISTA";
		lang[0][2]="CONTROPARTE";
		lang[0][3]="NUMERO FASCICOLO";
		lang[0][4]="ORGANO GIUDICANTE";
		lang[0][5]="N. REGISTRO DELLA CAUSA";
		lang[0][6]="CENTRO DI COSTO";
		lang[0][7]="VOCE DI CONTO";
		lang[0][8]="DATA RICHIESTA AUTORIZZAZIONE";
		lang[0][9]="DATA DI AUTORIZZAZIONE";
		lang[0][10]="LEGALE INTERNO OWNER";
		lang[0][11]="CONTABILIZZAZIONE";
		lang[0][12]="TOTALE AUTORIZZATO";

		lang[1][0]="COMPANY";
		lang[1][1]="PROFESSIONAL STUDIO NAME";
		lang[1][2]="COUNTERPART";
		lang[1][3]="DOSSIER NUMBER";
		lang[1][4]="ORGAN JUDGMENTAL";
		lang[1][5]="N. REGISTRY OF THE CASE";
		lang[1][6]="COST CENTRE";
		lang[1][7]="VOICE OF ACCOUNT";
		lang[1][8]="DATA REQUEST AUTHORIZATION";
		lang[1][9]="DATE AUTHORIZATION";
		lang[1][10]="INTERNAL LEGAL OWNER";
		lang[1][11]="ACCOUNTING";
		lang[1][12]="AUTHORIZED TOTAL";


		int language=0;
		if(languages.equalsIgnoreCase("IT"))
			language=0;
		if(languages.equalsIgnoreCase("EN"))
			language=1;		

		excell.addHeader(lang[language][0], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][1], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][2], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][3], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][4], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][5], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][6], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][7], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][8],WriteExcell.TYPECELL_DATE,excell.cellStyleFormatt_Acenter("dd/MM/YYYY"));
		excell.addHeader(lang[language][9],WriteExcell.TYPECELL_DATE,excell.cellStyleFormatt_Acenter("dd/MM/YYYY"));
		excell.addHeader(lang[language][10],WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][11],WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][12],WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);

		if(rIncaricoProformaSocieta!=null)
			for(RIncaricoProformaSocieta r:rIncaricoProformaSocieta){
				Fascicolo fascicolo= r.getIncarico().getFascicolo();//fascicoloDAO.leggi(r.getIncarico().getFascicolo().getId(), FetchMode.JOIN);// r.getIncarico().getFascicolo();
				Proforma proforma=r.getProforma();
				Incarico incarico=r.getIncarico();
				Societa societa=r.getSocieta();

				Vector<Object> row = new Vector<Object>();

				//SOCIETA
				row.add(societa!=null?societa.getRagioneSociale():"");

				//DENOMINAZIONE STUDIO PROFESSIONISTA
				if(incarico!=null && incarico.getProfessionistaEsterno()!=null && incarico.getProfessionistaEsterno().getStudioLegale()!=null){
					row.add(incarico.getProfessionistaEsterno().getStudioLegale().getDenominazione());	
				}else{ row.add("");}
				//CONTROPARTE
				//SOLO IN CASO GIUDIZIALE CIVILE 
				if(fascicolo.getTipologiaFascicolo()!=null && fascicolo.getTipologiaFascicolo().getCodGruppoLingua().equalsIgnoreCase("TFSC_1") 
						&& fascicolo.getSettoreGiuridico().getCodGruppoLingua().equalsIgnoreCase("TSTT_1")){
					List<Controparte> listControparte=controparteDAO.getControparteDaIdFascicolo(fascicolo.getId());
					StringBuilder sb=new StringBuilder();
					if(listControparte!=null && listControparte.size()>0){
						for(Controparte c:listControparte){
							sb.append(c.getNome());
							sb.append(", ");
						}
					}

					row.add(sb.toString());
				}else{ row.add(""); }

				//NUMERO FASCICOLO
				row.add(fascicolo!=null?fascicolo.getNome():"");	
				List<RFascicoloGiudizio> listRFascicoloGiudizio=fascicoloDAO.getRFascicoloGiudizioDaIdFascicolo(fascicolo!=null?fascicolo.getId():0);
				if(listRFascicoloGiudizio!=null && listRFascicoloGiudizio.size()>0){
					RFascicoloGiudizio fascicoloGiudizio=listRFascicoloGiudizio.get(0); 	
					//ORGANO GIUDICANTE
					row.add(fascicoloGiudizio!=null?fascicoloGiudizio.getOrganoGiudicante().getNome():"");

					//N. REGISTRO DELLA CAUSA
					row.add(fascicoloGiudizio!=null?fascicoloGiudizio.getNumeroRegistroCausa():"");

				}else{  row.add(""); row.add(""); }
				
				// CENTRO DI COSTO
				row.add(proforma.getCentroDiCosto()!=null?proforma.getCentroDiCosto():"");

				// VOCE DI CONTO 
				row.add(proforma.getVoceDiConto()!=null?proforma.getVoceDiConto():"");

				// DATA RICHIESTA AUTORIZZAZIONE
				row.add(proforma.getDataRichAutorizzazione());

				// DATA DI AUTORIZZAZIONE
				row.add(proforma.getDataAutorizzazione());

				Utente legale_interno=null;
				try {
					if(fascicolo.getLegaleInterno()!=null && !fascicolo.getLegaleInterno().trim().equals(""))
						legale_interno=((Utente)utenteDAO.leggiUtenteDaUserId(fascicolo.getLegaleInterno()));
				} catch (Throwable e) { }

				// LEGALE INTERNO OWNER
				row.add(legale_interno!=null?legale_interno.getNominativoUtil():"");	

				// CONTABILIZZAZIONE
				List<RProformaFattura> fatturas=incararicoDAO.getRProformaFattura(proforma.getId());
				String contabilizzato="NO";
				if(fatturas!=null && fatturas.size()>0){
					Fattura fattura=fatturas.get(0).getFattura();
					if((fattura.getnProtocolloFiscale()!=null && fattura.getnProtocolloFiscale().intValue()>0) && fattura.getDataRegistrazione()!=null)
						contabilizzato="SI";
				}
				row.add(contabilizzato);

				// TOTALE AUTORIZZATO
				row.add(proforma.getTotaleAutorizzato());

				excell.addRowBody(row);
			}
		excell.setNomeFile("Report-Proforma.xls");

		excell.createSheet().getCurrentSheet().setDefaultColumnWidth((int) 38);

		excell.write(respons);
	}


	private void exportIncarichiXls(List<Incarico> incaricos, HttpServletResponse respons,String localLang) throws Throwable {
		WriteExcell excell= new WriteExcell();

		String languages =(localLang!=null && !localLang.trim().equals(""))?localLang:"IT";
		String[][] lang=new String[2][13];

		lang[0][0]="NOME INCARICO";
		lang[0][1]="NUMERO FASCICOLO";
		lang[0][2]="TIPO FASCICOLO";
		lang[0][3]="SETTORE GIURIDICO";
		lang[0][4]="LEGALE INTERNO OWNER";
		lang[0][5]="STATO INCARICO";
		lang[0][6]="DATA CREAZIONE INCARICO";
		lang[0][7]="DATA RICHIESTA AUTORIZZAZIONE INCARICO";
		lang[0][8]="DATA AUTORIZZAZIONE INCARICO";
		lang[0][9]="UTENTE WORK FLOW INCARICO";

		lang[0][10]="SOCIETÀ DI ADDEBITO";
		lang[0][11]="NOME E COGNOME PROFESSIONISTA ESTERNO";
		lang[0][12]="EMAIL PROFESSIONISTA ESTERNO";


		lang[1][0]="NAME ASSIGNMENT";
		lang[1][1]="NUMBER DOSSIER";
		lang[1][2]="TYPE DOSSIER";
		lang[1][3]="LEGAL AREA";
		lang[1][4]="LEGAL INTERNAL OWNER";
		lang[1][5]="STATE ASSIGNMENT";
		lang[1][6]="DATA CREATION ASSIGNMENT";
		lang[1][7]="DATA REQUEST AUTHORIZATION INACRICO";
		lang[1][8]="DATA AUTHORIZATION INACRICO";
		lang[1][9]="WORK FLOW ASSIGNMENT";

		lang[1][10]="DEBIT COMPANY";
		lang[1][11]="FIRST AND LAST NAME EXTERNAL LAWYER";
		lang[1][12]="EMAIL EXTERNAL LAWYER";

		int language=0;
		if(languages.equalsIgnoreCase("IT"))
			language=0;
		if(languages.equalsIgnoreCase("EN"))
			language=1;	

		excell.addHeader(lang[language][0], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][1], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][2], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][3], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][4], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][5], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][6], WriteExcell.TYPECELL_DATE,excell.cellStyleFormatt_Acenter("dd/MM/YYYY"));
		excell.addHeader(lang[language][7], WriteExcell.TYPECELL_DATE,excell.cellStyleFormatt_Acenter("dd/MM/YYYY"));
		excell.addHeader(lang[language][8], WriteExcell.TYPECELL_DATE,excell.cellStyleFormatt_Acenter("dd/MM/YYYY"));
		excell.addHeader(lang[language][9], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);

		excell.addHeader(lang[language][10], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][11], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][12], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);

		UtenteService utenteService = (UtenteService) SpringUtil.getBean("utenteService");

		if(incaricos!=null)
			for(Incarico incarico:incaricos){


				Vector<Object> row = new Vector<Object>();

				//NOME INACRICO
				row.add(incarico.getNomeIncarico());

				//NUMERO FASCICOLO
				if(incarico.getFascicolo()!=null){
					row.add(incarico.getFascicolo().getNome());	
				}else{ row.add("");}

				//TIPO FASCICOLO
				if( incarico.getFascicolo()!=null && incarico.getFascicolo().getTipologiaFascicolo()!=null){
					row.add(incarico.getFascicolo().getTipologiaFascicolo().getDescrizione());	
				}else{ row.add("");}

				//SETTORE GIURIDICO
				if(incarico.getFascicolo()!=null){
					row.add(incarico.getFascicolo().getSettoreGiuridico().getNome());	
				}else{ row.add("");}

				Utente legale_interno=null;
				try {
					if(incarico.getFascicolo()!=null && !incarico.getFascicolo().getLegaleInterno().trim().equals("")){
						legale_interno=((Utente)utenteDAO.leggiUtenteDaUserId(incarico.getFascicolo().getLegaleInterno()));
					}
				} catch (Throwable e) { }

				// LEGALE INTERNO OWNER
				row.add(legale_interno!=null?legale_interno.getNominativoUtil():"");

				//STATO INCARICO
				if(incarico.getStatoIncarico()!=null){
					row.add(incarico.getStatoIncarico().getDescrizione());
				}else{ row.add(""); }

				//DATA CREAZIONE INCARICO
				row.add(incarico.getDataCreazione());

				//DATA RICHIESTA AUTORIZZAZIONE INACRICO
				row.add(incarico.getDataRichiestaAutorIncarico());

				//"DATA AUTORIZZAZIONE INACRICO
				row.add(incarico.getDataAutorizzazione());

				// UTENTE WORK FLOW INCARICO
				UtenteView utenteWfIncarico=utenteService.leggiAssegnatarioWfIncarico(incarico.getId());
				//IncaricoWf incaricoWf=incaricoWfDao.leggiWorkflowInCorso(incarico.getId());
				//row.add( incaricoWf!=null?incaricoWf.getUtenteCreazione():"");	
				row.add( utenteWfIncarico!=null?(utenteWfIncarico.getVo()!=null?utenteWfIncarico.getVo().getNominativoUtil():""):"");	

				Collection<RFascicoloSocieta> listaFascicoloSocieta = fascicoloDAO.getRFascicoloSocietas(incarico.getFascicolo().getId());

				String societaAdd="";

				for (RFascicoloSocieta societa : listaFascicoloSocieta) {
					societaAdd+=societa.getSocieta().getNome()+"; ";
				}
				row.add(societaAdd);

				if(incarico.getProfessionistaEsterno()!=null){

					row.add(incarico.getProfessionistaEsterno().getNome()+" "+incarico.getProfessionistaEsterno().getCognome());

					if(incarico.getProfessionistaEsterno().getEmail()!=null)
						row.add(incarico.getProfessionistaEsterno().getEmail());
				}
				excell.addRowBody(row);
			}
		excell.setNomeFile("Report-Incarichi.xls");

		excell.createSheet().getCurrentSheet().setDefaultColumnWidth((int) 38);

		excell.write(respons);
	}


	private void exportStanziamentiXls(List<Stanziamenti> stanziamentis, HttpServletResponse respons,String localLang,int anno) throws Throwable {
		WriteExcell excell= new WriteExcell();

		String languages =(localLang!=null && !localLang.trim().equals(""))?localLang:"IT";
		String[][] lang=new String[2][10];

		lang[0][0]="ANNO ESERCIZIO FINANZIARIO";
		lang[0][1]="NOME FASCICOLO";
		lang[0][2]="NOME INCARICO";
		lang[0][3]="IMPORTO";
		lang[0][4]="IMPORTO PROFORMA"; /* Totale IVA esclusa */
		lang[0][5]="NOME PROFORMA"; /*Assente se il proforma non è presente a sistema*/
		lang[0][6]="STATO PROFORMA";
		lang[0][7]="FATTURA CONTABILIZZATA";/*Sì/No*/
		lang[0][8]="ID STANZIAMENTO";/*Inizialmente vuota Es. 2017/0000123456. Generato in Z-MAST ed inserito manualmente successivamente*/

		lang[1][0]="FINANCIAL YEAR";
		lang[1][1]="DOSSIER NAME";
		lang[1][2]="NAME ASSIGNMENT";
		lang[1][3]="AMOUNT";
		lang[1][4]="PROFORMA AMOUNT";
		lang[1][5]="NAME PROFORMA";
		lang[1][6]="STATE PROFORMA";
		lang[1][7]="INVOICE ACCOUNTED";
		lang[1][8]="ID APPROPRIATIONS";

		int language=0;
		if(languages.equalsIgnoreCase("IT"))
			language=0;
		if(languages.equalsIgnoreCase("EN"))
			language=1;	

		excell.addHeader(lang[language][0], WriteExcell.TYPECELL_NUMBER,excell.CSTYLE_NUMBER);
		excell.addHeader(lang[language][1], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][2], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][3], WriteExcell.TYPECELL_NUMBER,excell.CSTYLE_NUMBER);
		excell.addHeader(lang[language][4], WriteExcell.TYPECELL_NUMBER,excell.CSTYLE_NUMBER);
		excell.addHeader(lang[language][5], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][6], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][7], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][8], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);

		if(stanziamentis!=null)
			for(Stanziamenti stanziamento:stanziamentis){

				Vector<Object> row = new Vector<Object>();

				//ANNO ESERCIZIO FINANZIARIO  
				row.add(stanziamento.getAnnoEsercizioFinanziario());
				//NOME FASCICOLO 
				row.add(stanziamento.getNomeFascicolo());
				//NOME INCARICO 
				row.add(stanziamento.getNomeIncarico());
				//IMPORTO 
				row.add(stanziamento.getImporto());
				//IMPORTO PROFORMA
				row.add(stanziamento.getImportoProforma());
				//NOME PROFORMA 
				row.add(stanziamento.getNomeProforma());
				//STATO PROFORMA 
				row.add(stanziamento.getStatoProforma());
				//FATTURA CONTABILIZZATA 
				row.add(stanziamento.getFatturaContabilizzata());
				//ID STANZIAMENTO 
				row.add(stanziamento.getIdStanziamento());

				excell.addRowBody(row);
			}

		String fileName="Report-Stanziamenti-"+anno+".xls";
		excell.setNomeFile(fileName);

		excell.createSheet().getCurrentSheet().setDefaultColumnWidth((int) 38);

		excell.write(respons);
	}


	private void exportSchedeFondoRischiPDF(List<SchedaFondoRischiView> listaSchede, String trimestre, HttpServletResponse response,String localLang, int delay) throws Throwable {
		try
		{
			String languages =(localLang!=null && !localLang.trim().equals(""))?localLang:"IT";
			int language=0;
			if(languages.equalsIgnoreCase("IT"))
				language=0;
			if(languages.equalsIgnoreCase("EN"))
				language=1;	

			List<SchedaFondoRischiView> listaSchedeNuove = new ArrayList<SchedaFondoRischiView>();
			List<SchedaFondoRischiView> listaSchedeModificate = new ArrayList<SchedaFondoRischiView>();
			List<SchedaFondoRischiView> listaSchedeNonModificate = new ArrayList<SchedaFondoRischiView>();
			List<SchedaFondoRischiView> listaSchedeEliminate = new ArrayList<SchedaFondoRischiView>();

			if(listaSchede != null && !listaSchede.isEmpty()){

				for(SchedaFondoRischiView s : listaSchede){

					if(s.getVo() != null){
						SchedaFondoRischi scheda = s.getVo();

						/** PRENDE IN CONSIDERAZIONE SOLO LE SCHEDE CON STATO AUTORIZZATO **/
						if(scheda.getStatoSchedaFondoRischi().getCodGruppoLingua().equals(CostantiDAO.SCHEDA_FONDO_RISCHI_STATO_AUTORIZZATO)){

							if(scheda.getDataCancellazione() != null){
								listaSchedeEliminate.add(s);
							}
							else if(scheda.getDataCreazione() != null){

								/** Se è stata creata nel trimestre selezionato sarà di colore BLU**/
								if(DateUtil2.estraiTrimestreDelayedDaData(scheda.getDataCreazione(),delay).equals(trimestre)){

									listaSchedeNuove.add(s);
								}
								/** Se è stata creata prima del semestre selezionato 
								 * (dopo non è possibile dato che la ricerca non li restituisce) **/
								else{
									if(scheda.getDataModifica() != null){

										/** Se la scheda è stata modificata e NON creata nel trimestre selezionato, sarà di colore ROSSO **/
										if(DateUtil2.estraiTrimestreDelayedDaData(scheda.getDataModifica(), delay).equals(trimestre)){

											listaSchedeModificate.add(s);
										}else{
											/** Se la scheda NON è stata modificata e NON creata nel trimestre selezionato, sarà di colore VERDE **/
											listaSchedeNonModificate.add(s);
										}

									}
									/** Se la scheda non è sta MAI modificata e NON creata nel trimestre selezionato, sarà sempre di colre VERDE **/
									else{
										listaSchedeNonModificate.add(s);
									}
								}
							}
						}
					}
				}
			}

			Date now = new Date();

			String mese = "";
			if(language == 0)
				mese = DateUtil.getMeseParlante(now, "IT");
			else
				mese = DateUtil.getMeseParlante(now, "EN");
			String anno = DateUtil.getAnno(now) + "";

			Document document = new Document();
			document.setPageSize(PageSize.A4);
			PdfWriter pdfWriter = PdfWriter.getInstance(document, response.getOutputStream());
			document.open();

			float sizeTitoloDocumento = 12;
			BaseFont bfTitoloDocumento = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
			Font fontDocumentTitle = new Font(bfTitoloDocumento, sizeTitoloDocumento, Font.UNDERLINE, Color.BLACK);

			float sizeTitoloParagrafi = 10;
			BaseFont bfTitoloParagrafi = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
			Font fontTitoloParagrafi = new Font(bfTitoloParagrafi, sizeTitoloParagrafi, Font.UNDERLINE, Color.BLACK);

			float sizeTestoNormale = 8;
			BaseFont bfTestoNormaleSottolineato = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
			Font fontTestoNormaleSottolineato = new Font(bfTestoNormaleSottolineato, sizeTestoNormale, Font.UNDERLINE, Color.BLACK);

			/** TITOLO DEL DOCUMENTO **/
			Paragraph paragraph = new Paragraph();
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.setFont(fontDocumentTitle);
			if(language == 0)
				paragraph.add("Aggiornamento a " + mese + " " + anno );
			else
				paragraph.add("Updated to " + mese + " " + anno );
			document.add(paragraph);
			document.add(Chunk.NEWLINE);

			Paragraph paragraph1_1 = new Paragraph();
			paragraph1_1.setAlignment(Element.ALIGN_CENTER);
			paragraph1_1.setFont(fontDocumentTitle);
			if(language == 0)
				paragraph1_1.add("TIPOLOGIA 'A'");
			else
				paragraph1_1.add("TYPE 'A'");
			document.add(paragraph1_1);
			document.add(Chunk.NEWLINE);

			Paragraph paragraph1_2 = new Paragraph();
			paragraph1_2.setAlignment(Element.ALIGN_CENTER);
			paragraph1_2.setFont(fontDocumentTitle);
			if(language == 0)
				paragraph1_2.add("Stima delle passività relative ai Contenziosi (di società di riferimento) di importo determinato pari o superiore alla soglia di Euro 250.000,00");
			else
				paragraph1_2.add("Estimates of contingent liabilities (of reference companies) of a determined amount equal to or above the threshold of Euro 250,000.00");
			document.add(paragraph1_2);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);

			if(listaSchedeNuove.isEmpty() && listaSchedeModificate.isEmpty() && listaSchedeNonModificate.isEmpty() && listaSchedeEliminate.isEmpty()){

				/** PARAGRAFO CHE AVVERTE CHE NON CI SONO SCHEDE AUTORIZZATE **/
				Paragraph paragraphAlert = new Paragraph();
				paragraphAlert.setAlignment(Element.ALIGN_LEFT);
				paragraphAlert.setFont(fontTitoloParagrafi);
				if(language == 0)
					paragraphAlert.add("Non sono presenti Schede Fondo Rischi autorizzate per generare un PFR per il trimestre  " + trimestre);
				else
					paragraphAlert.add("There are no authorized Risk Fund Card to generate a PFR for the quarter  " + trimestre);
				document.add(paragraphAlert);

			}
			else{
				/** PRIMO PARAGRAFO (SCHEDE MODIFICATE NEL TRIMESTRE CORRENTE) **/
				Map<String, List<SchedaFondoRischiView>> mappaSchedeModificateENonModificateRaggruppate = raggruppaPerPrimoRiporto(listaSchedeModificate);
				aggiungiARaggruppaPerPrimoRiporto(mappaSchedeModificateENonModificateRaggruppate, listaSchedeNonModificate);

				if(mappaSchedeModificateENonModificateRaggruppate != null && !mappaSchedeModificateENonModificateRaggruppate.isEmpty()){
					
					for(Map.Entry<String, List<SchedaFondoRischiView>> entry : mappaSchedeModificateENonModificateRaggruppate.entrySet()){
						
						List<SchedaFondoRischiView> modificate = entry.getValue();
						
						if(modificate != null && !modificate.isEmpty()){

							for(SchedaFondoRischiView scheda : modificate){

								FascicoloView fascicoloView = fascicoloService.leggi(scheda.getVo().getFascicolo().getId(), FetchMode.JOIN);

								String nomeCausa = "";

								if(fascicoloView.getVo().getContropartes() != null && !fascicoloView.getVo().getContropartes().isEmpty()){

									if(fascicoloView.getVo().getContropartes().size() == 1)
										nomeCausa = fascicoloView.getVo().getContropartes().iterator().next().getNome();

									else{
										for(Controparte controparte : fascicoloView.getVo().getContropartes()){

											nomeCausa += controparte.getNome() + ", ";
										}
									}
								}
								else{
									if(scheda.getVo().getFascicolo() != null){

										if(scheda.getVo().getFascicolo().getTitolo() != null){
											nomeCausa = scheda.getVo().getFascicolo().getTitolo();
										}
									}
								}
								if(nomeCausa != null && !nomeCausa.isEmpty()){

									Paragraph nomeCausa1 = new Paragraph();
									nomeCausa1.setAlignment(Element.ALIGN_LEFT);
									nomeCausa1.setFont(fontTestoNormaleSottolineato);
									nomeCausa1.add(nomeCausa);
									document.add(nomeCausa1);
									document.add(Chunk.NEWLINE);
								}
								PdfPTable table = creaPdfTableSchedaFondoRischi(scheda, language, fascicoloView);
								document.add(table);
								document.add(Chunk.NEWLINE);
							}
						}
					}
				}

				/** SECONDO PARAGRAFO (SCHEDE NON MODIFICATE NEL TRIMESTRE CORRENTE) 
				Map<String, List<SchedaFondoRischiView>> mappaSchedeNonModificateRaggruppate = raggruppaPerPrimoRiporto(listaSchedeNonModificate);

				if(mappaSchedeNonModificateRaggruppate != null && !mappaSchedeNonModificateRaggruppate.isEmpty()){

					Set<String> matricolePrimiRiporti = mappaSchedeNonModificateRaggruppate.keySet();

					for(String primoRiporto : matricolePrimiRiporti){

						List<SchedaFondoRischiView> nonModificate = mappaSchedeNonModificateRaggruppate.get(primoRiporto);

						if(nonModificate != null && !nonModificate.isEmpty()){

							for(SchedaFondoRischiView scheda : nonModificate){

								FascicoloView fascicoloView = fascicoloService.leggi(scheda.getVo().getFascicolo().getId(), FetchMode.JOIN);

								String nomeCausa = "";

								if(fascicoloView.getVo().getContropartes() != null && !fascicoloView.getVo().getContropartes().isEmpty()){

									if(fascicoloView.getVo().getContropartes().size() == 1)
										nomeCausa = fascicoloView.getVo().getContropartes().iterator().next().getNome();

									else{
										for(Controparte controparte : fascicoloView.getVo().getContropartes()){

											nomeCausa += controparte.getNome() + ", ";
										}
									}
								}
								else{
									if(scheda.getVo().getFascicolo() != null){

										if(scheda.getVo().getFascicolo().getTitolo() != null){
											nomeCausa = scheda.getVo().getFascicolo().getTitolo();
										}
									}
								}
								if(nomeCausa != null && !nomeCausa.isEmpty()){

									Paragraph nomeCausa1 = new Paragraph();
									nomeCausa1.setAlignment(Element.ALIGN_LEFT);
									nomeCausa1.setFont(fontTestoNormaleSottolineato);
									nomeCausa1.add(nomeCausa);
									document.add(nomeCausa1);
									document.add(Chunk.NEWLINE);
								}
								PdfPTable table = creaPdfTableSchedaFondoRischi(scheda, language, fascicoloView);
								document.add(table);
								document.add(Chunk.NEWLINE);
							}
						}
					}
				}**/

				/** TERZO PARAGRAFO (SCHEDE CREATE NEL TRIMESTRE CORRENTE) **/
				Paragraph paragraph1 = new Paragraph();
				paragraph1.setAlignment(Element.ALIGN_CENTER);
				paragraph1.setFont(fontTitoloParagrafi);

				String trimestrePrecedente = DateUtil2.calcolaTrimestrePrecedente(trimestre);

				if(language == 0)
					paragraph1.add("N.B. Rispetto alla precedente relazione del trimestre "+ trimestrePrecedente +", sono stati inseriti i seguenti contenziosi:");
				else
					paragraph1.add("N.B. Compared with the previous report of quarter "+ trimestrePrecedente +", the following disputes were included:");
				document.add(Chunk.NEWLINE);
				document.add(paragraph1);
				document.add(Chunk.NEWLINE);

				Map<String, List<SchedaFondoRischiView>> mappaSchedeCreateRaggruppate = raggruppaPerPrimoRiporto(listaSchedeNuove);

				if(mappaSchedeCreateRaggruppate != null && !mappaSchedeCreateRaggruppate.isEmpty()){

					Set<String> matricolePrimiRiporti = mappaSchedeCreateRaggruppate.keySet();

					for(String primoRiporto : matricolePrimiRiporti){

						List<SchedaFondoRischiView> nuove = mappaSchedeCreateRaggruppate.get(primoRiporto);

						if(nuove != null && !nuove.isEmpty()){

							for(SchedaFondoRischiView scheda : nuove){

								FascicoloView fascicoloView = fascicoloService.leggi(scheda.getVo().getFascicolo().getId(), FetchMode.JOIN);

								String nomeCausa = "";
								if(fascicoloView.getVo().getContropartes() != null && !fascicoloView.getVo().getContropartes().isEmpty()){

									if(fascicoloView.getVo().getContropartes().size() == 1)
										nomeCausa = fascicoloView.getVo().getContropartes().iterator().next().getNome();

									else{
										for(Controparte controparte : fascicoloView.getVo().getContropartes()){

											nomeCausa += controparte.getNome() + ", ";
										}
									}
								}
								else{
									if(scheda.getVo().getFascicolo() != null){

										if(scheda.getVo().getFascicolo().getTitolo() != null){
											nomeCausa = scheda.getVo().getFascicolo().getTitolo();
										}
									}
								}
								if(nomeCausa != null && !nomeCausa.isEmpty()){

									Paragraph nomeCausa1 = new Paragraph();
									nomeCausa1.setAlignment(Element.ALIGN_LEFT);
									nomeCausa1.setFont(fontTestoNormaleSottolineato);
									nomeCausa1.add(nomeCausa);
									document.add(nomeCausa1);
									document.add(Chunk.NEWLINE);
								}
								PdfPTable table = creaPdfTableSchedaFondoRischi(scheda, language, fascicoloView);
								document.add(table);
								document.add(Chunk.NEWLINE);
							}
						}
					}
				}

				/** QUARTO PARAGRAFO (SCHEDE ELIMINATE NEL TRIMESTRE CORRENTE) **/
				Paragraph paragraph4 = new Paragraph();
				paragraph4.setAlignment(Element.ALIGN_LEFT);
				paragraph4.setFont(fontTitoloParagrafi);
				if(language == 0)
					paragraph4.add("N.B. Rispetto alla precedente relazione del trimestre "+ trimestrePrecedente +", sono stati eliminati i seguenti contenziosi:");
				else
					paragraph4.add("N.B. Compared with the previous report of quarter "+ trimestrePrecedente +", the following disputes were deleted:");
				document.add(Chunk.NEWLINE);
				document.add(paragraph4);
				document.add(Chunk.NEWLINE);

				Map<String, List<SchedaFondoRischiView>> mappaSchedeEliminateRaggruppate = raggruppaPerPrimoRiporto(listaSchedeEliminate);

				if(mappaSchedeEliminateRaggruppate != null && !mappaSchedeEliminateRaggruppate.isEmpty()){

					Set<String> matricolePrimiRiporti = mappaSchedeEliminateRaggruppate.keySet();

					for(String primoRiporto : matricolePrimiRiporti){

						List<SchedaFondoRischiView> eliminate = mappaSchedeEliminateRaggruppate.get(primoRiporto);

						if(eliminate != null && !eliminate.isEmpty()){

							for(SchedaFondoRischiView scheda : eliminate){

								FascicoloView fascicoloView = fascicoloService.leggiTutti(scheda.getVo().getFascicolo().getId(), FetchMode.JOIN);

								String nomeCausa = "";

								if(fascicoloView.getVo().getContropartes() != null && !fascicoloView.getVo().getContropartes().isEmpty()){

									if(fascicoloView.getVo().getContropartes().size() == 1)
										nomeCausa = fascicoloView.getVo().getContropartes().iterator().next().getNome();

									else{
										for(Controparte controparte : fascicoloView.getVo().getContropartes()){

											nomeCausa += controparte.getNome() + ", ";
										}
									}
								}
								else{
									if(scheda.getVo().getFascicolo() != null){

										if(scheda.getVo().getFascicolo().getTitolo() != null){
											nomeCausa = scheda.getVo().getFascicolo().getTitolo();
										}
									}
								}
								if(nomeCausa != null && !nomeCausa.isEmpty()){

									Paragraph nomeCausa1 = new Paragraph();
									nomeCausa1.setAlignment(Element.ALIGN_LEFT);
									nomeCausa1.setFont(fontTestoNormaleSottolineato);
									nomeCausa1.add(nomeCausa);
									document.add(nomeCausa1);
									document.add(Chunk.NEWLINE);
								}
								PdfPTable table = creaPdfTableSchedaFondoRischi(scheda, language, fascicoloView);
								document.add(table);
								document.add(Chunk.NEWLINE);
							}
						}
					}
				}
			}
			document.close(); 
			pdfWriter.close();

			String nomeFiles = "PFR - " + trimestre +".pdf";

			response.setHeader("Content-Disposition", "attachment; filename=" + nomeFiles);
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("application/pdf");
			response.getOutputStream().flush();
			response.getOutputStream().close();
			response.flushBuffer();

		} catch (DocumentException de) {
			throw new IOException(de.getMessage());
		}
	}


	private void exportDifferenzeSchedeFondoRischiPDF(List<SchedaFondoRischiView> listaSchede, String trimestre, HttpServletResponse response,String localLang, int delay) throws Throwable {
		try
		{
			String languages =(localLang!=null && !localLang.trim().equals(""))?localLang:"IT";
			int language=0;
			if(languages.equalsIgnoreCase("IT"))
				language=0;
			if(languages.equalsIgnoreCase("EN"))
				language=1;

			List<SchedaFondoRischiView> listaSchedeNuove = new ArrayList<SchedaFondoRischiView>();
			List<SchedaFondoRischiView> listaSchedeModificate = new ArrayList<SchedaFondoRischiView>();
			List<SchedaFondoRischiView> listaSchedeNonModificate = new ArrayList<SchedaFondoRischiView>();
			List<SchedaFondoRischiView> listaSchedeEliminate = new ArrayList<SchedaFondoRischiView>();

			if(listaSchede != null && !listaSchede.isEmpty()){

				for(SchedaFondoRischiView s : listaSchede){

					if(s.getVo() != null){
						SchedaFondoRischi scheda = s.getVo();

						/** PRENDE IN CONSIDERAZIONE SOLO LE SCHEDE CON STATO AUTORIZZATO **/
						if(scheda.getStatoSchedaFondoRischi().getCodGruppoLingua().equals(CostantiDAO.SCHEDA_FONDO_RISCHI_STATO_AUTORIZZATO)){

							if(scheda.getDataCancellazione() != null){
								listaSchedeEliminate.add(s);
							}
							else if(scheda.getDataCreazione() != null){

								/** Se è stata creata nel trimestre selezionato sarà di colore BLU**/
								if(DateUtil2.estraiTrimestreDelayedDaData(scheda.getDataCreazione(), delay).equals(trimestre)){

									listaSchedeNuove.add(s);
								}
								/** Se è stata creata prima del semestre selezionato 
								 * (dopo non è possibile dato che la ricerca non li restituisce) **/
								else{
									if(scheda.getDataModifica() != null){

										/** Se la scheda è stata modificata e NON creata nel trimestre selezionato, sarà di colore ROSSO **/
										if(DateUtil2.estraiTrimestreDelayedDaData(scheda.getDataModifica(), delay).equals(trimestre)){

											listaSchedeModificate.add(s);
										}else{
											/** Se la scheda NON è stata modificata e NON creata nel trimestre selezionato, sarà di colore VERDE **/
											listaSchedeNonModificate.add(s);
										}

									}
									/** Se la scheda non è sta MAI modificata e NON creata nel trimestre selezionato, sarà sempre di colre VERDE **/
									else{
										listaSchedeNonModificate.add(s);
									}
								}
							}
						}
					}
				}
			}
			XWPFDocument document= new XWPFDocument();

			/** TITOLO DEL DOCUMENTO **/
			XWPFParagraph paragraph = document.createParagraph();
			paragraph.setAlignment(ParagraphAlignment.CENTER);
			XWPFRun paragraphOneRunOne = paragraph.createRun();
			paragraphOneRunOne.setFontFamily("Helvetica");
			paragraphOneRunOne.setFontSize(16);
			paragraphOneRunOne.setBold(true);
			if(language == 0)
				paragraphOneRunOne.setText("PFR per il semestre " + trimestre + " (Variazioni trimestre precedente)");
			else
				paragraphOneRunOne.setText("PFR for the quarter " + trimestre + " (Previous tuarter changes)");
			paragraphOneRunOne.addBreak();

			if(listaSchedeNuove.isEmpty() && listaSchedeModificate.isEmpty() && listaSchedeNonModificate.isEmpty() && listaSchedeEliminate.isEmpty()){

				/** PARAGRAFO CHE AVVERTE CHE NON CI SONO SCHEDE AUTORIZZATE **/
				XWPFParagraph paragraphAlert = document.createParagraph();
				paragraphAlert.setAlignment(ParagraphAlignment.LEFT);
				XWPFRun paragraphAlertRunOne = paragraphAlert.createRun();
				paragraphAlertRunOne.setFontFamily("Helvetica");
				paragraphAlertRunOne.setFontSize(12);
				paragraphAlertRunOne.setBold(false);
				if(language == 0)
					paragraphAlertRunOne.setText("Non sono presenti Schede Fondo Rischi autorizzate per generare un PFR per il trimestre  " + trimestre);
				else
					paragraphAlertRunOne.setText("There are no authorized Risk Fund Card to generate a PFR for the quarter  " + trimestre);
				paragraphAlertRunOne.addBreak();
			}
			else{
				/** PRIMO PARAGRAFO (SCHEDE MODIFICATE NEL TRIMESTRE CORRENTE) **/
				Map<String, List<SchedaFondoRischiView>> mappaSchedeModificateeNonModificateRaggruppate = raggruppaPerPrimoRiporto(listaSchedeModificate);
				aggiungiARaggruppaPerPrimoRiporto(mappaSchedeModificateeNonModificateRaggruppate, listaSchedeNonModificate);

				if(mappaSchedeModificateeNonModificateRaggruppate != null && !mappaSchedeModificateeNonModificateRaggruppate.isEmpty()){

						for(Map.Entry<String, List<SchedaFondoRischiView>> entry : mappaSchedeModificateeNonModificateRaggruppate.entrySet()){
						
						List<SchedaFondoRischiView> modificate = entry.getValue();

						if(modificate != null && !modificate.isEmpty()){

							for(SchedaFondoRischiView scheda : modificate){

								FascicoloView fascicoloView = fascicoloService.leggi(scheda.getVo().getFascicolo().getId(), FetchMode.JOIN);

								String nomeCausa = "";

								if(fascicoloView.getVo().getContropartes() != null && !fascicoloView.getVo().getContropartes().isEmpty()){

									if(fascicoloView.getVo().getContropartes().size() == 1)
										nomeCausa = fascicoloView.getVo().getContropartes().iterator().next().getNome();

									else{
										for(Controparte controparte : fascicoloView.getVo().getContropartes()){

											nomeCausa += controparte.getNome() + ", ";
										}
									}
								}
								else{
									if(scheda.getVo().getFascicolo() != null){

										if(scheda.getVo().getFascicolo().getTitolo() != null){
											nomeCausa = scheda.getVo().getFascicolo().getTitolo();
										}
									}
								}
								if(nomeCausa != null && !nomeCausa.isEmpty()){

									XWPFParagraph paragraphScheda = document.createParagraph();
									paragraphScheda.setAlignment(ParagraphAlignment.LEFT);
									XWPFRun paragraphSchedaDescrizione = paragraphScheda.createRun();
									paragraphSchedaDescrizione.setFontFamily("Helvetica");
									paragraphSchedaDescrizione.setFontSize(10);
									paragraphSchedaDescrizione.setBold(true);
									paragraphSchedaDescrizione.setUnderline(UnderlinePatterns.SINGLE);
									paragraphSchedaDescrizione.setText(nomeCausa);
									paragraphSchedaDescrizione.addBreak();
								}
								/** Recupero l'id della scheda modificata per controllare se esistono versioni precedenti **/
								long idSchedaModificata = scheda.getVo().getId();
								List<StoricoSchedaFondoRischiView> versioniPrecedenti = storicoSchedaFondoRischiService.leggiVersioniPrecedenti(idSchedaModificata);
								StoricoSchedaFondoRischiView storico = null;

								if(versioniPrecedenti != null && !versioniPrecedenti.isEmpty()){

									storico = versioniPrecedenti.get(0);

									for(StoricoSchedaFondoRischiView view : versioniPrecedenti){

										if(view.getVo().getDataModifica().after(storico.getVo().getDataModifica())){

											storico = view;
										}
									}
								}
								
								if(storico != null)
									creaDocTableSchedaFondoRischiModificate(scheda, storico, document, language,fascicoloView);
								else
									creaDocTableSchedaFondoRischiAltre(scheda, document, language, fascicoloView);

								XWPFParagraph paragraphBreak = document.createParagraph();
								paragraphBreak.setAlignment(ParagraphAlignment.CENTER);
								XWPFRun paragraphBreakDescrizione = paragraphBreak.createRun();
								paragraphBreakDescrizione.setFontFamily("Helvetica");
								paragraphBreakDescrizione.setFontSize(10);
								paragraphBreakDescrizione.setBold(true);
								paragraphBreakDescrizione.setText("");
								paragraphBreakDescrizione.addBreak();
							}
						}
					}
				}

				/** SECONDO PARAGRAFO (SCHEDE NON MODIFICATE NEL TRIMESTRE CORRENTE) 
				Map<String, List<SchedaFondoRischiView>> mappaSchedeNonModificateRaggruppate = raggruppaPerPrimoRiporto(listaSchedeNonModificate);

				if(mappaSchedeNonModificateRaggruppate != null && !mappaSchedeNonModificateRaggruppate.isEmpty()){

					Set<String> matricolePrimiRiporti = mappaSchedeNonModificateRaggruppate.keySet();

					for(String primoRiporto : matricolePrimiRiporti){

						List<SchedaFondoRischiView> nonModificate = mappaSchedeNonModificateRaggruppate.get(primoRiporto);

						if(nonModificate != null && !nonModificate.isEmpty()){

							for(SchedaFondoRischiView scheda : nonModificate){

								FascicoloView fascicoloView = fascicoloService.leggi(scheda.getVo().getFascicolo().getId(), FetchMode.JOIN);

								String nomeCausa = "";

								if(fascicoloView.getVo().getContropartes() != null && !fascicoloView.getVo().getContropartes().isEmpty()){

									if(fascicoloView.getVo().getContropartes().size() == 1)
										nomeCausa = fascicoloView.getVo().getContropartes().iterator().next().getNome();

									else{
										for(Controparte controparte : fascicoloView.getVo().getContropartes()){

											nomeCausa += controparte.getNome() + ", ";
										}
									}
								}
								else{
									if(scheda.getVo().getFascicolo() != null){

										if(scheda.getVo().getFascicolo().getTitolo() != null){
											nomeCausa = scheda.getVo().getFascicolo().getTitolo();
										}
									}
								}
								if(nomeCausa != null && !nomeCausa.isEmpty()){

									XWPFParagraph paragraphScheda = document.createParagraph();
									paragraphScheda.setAlignment(ParagraphAlignment.LEFT);
									XWPFRun paragraphSchedaDescrizione = paragraphScheda.createRun();
									paragraphSchedaDescrizione.setFontFamily("Helvetica");
									paragraphSchedaDescrizione.setFontSize(10);
									paragraphSchedaDescrizione.setBold(true);
									paragraphSchedaDescrizione.setUnderline(UnderlinePatterns.SINGLE);
									paragraphSchedaDescrizione.setText(nomeCausa);
									paragraphSchedaDescrizione.addBreak();
								}
								creaDocTableSchedaFondoRischiAltre(scheda, document, language, fascicoloView);

								XWPFParagraph paragraphBreak = document.createParagraph();
								paragraphBreak.setAlignment(ParagraphAlignment.CENTER);
								XWPFRun paragraphBreakDescrizione = paragraphBreak.createRun();
								paragraphBreakDescrizione.setFontFamily("Helvetica");
								paragraphBreakDescrizione.setFontSize(10);
								paragraphBreakDescrizione.setBold(true);
								paragraphBreakDescrizione.setText("");
								paragraphBreakDescrizione.addBreak();
							}
						}
					}
				}**/

				/** TERZO PARAGRAFO (SCHEDE NUOVE NEL TRIMESTRE CORRENTE) **/
				String trimestrePrecedente = DateUtil2.calcolaTrimestrePrecedente(trimestre);

				XWPFParagraph paragraphNBNuove = document.createParagraph();
				paragraphNBNuove.setAlignment(ParagraphAlignment.CENTER);
				XWPFRun paragraphNBNuoveDescrizione = paragraphNBNuove.createRun();
				paragraphNBNuoveDescrizione.setFontFamily("Helvetica");
				paragraphNBNuoveDescrizione.setFontSize(12);
				paragraphNBNuoveDescrizione.setBold(true);
				paragraphNBNuoveDescrizione.setUnderline(UnderlinePatterns.SINGLE);

				if(language == 0)
					paragraphNBNuoveDescrizione.setText("N.B. Rispetto alla precedente relazione del trimestre "+ trimestrePrecedente +", sono stati inseriti i seguenti contenziosi:");
				else
					paragraphNBNuoveDescrizione.setText("N.B. Compared with the previous report of quarter "+ trimestrePrecedente +", the following disputes were included:");
				paragraphNBNuoveDescrizione.addBreak();

				Map<String, List<SchedaFondoRischiView>> mappaSchedeNuoveRaggruppate = raggruppaPerPrimoRiporto(listaSchedeNuove);

				if(mappaSchedeNuoveRaggruppate != null && !mappaSchedeNuoveRaggruppate.isEmpty()){

					Set<String> matricolePrimiRiporti = mappaSchedeNuoveRaggruppate.keySet();

					for(String primoRiporto : matricolePrimiRiporti){

						List<SchedaFondoRischiView> nuove = mappaSchedeNuoveRaggruppate.get(primoRiporto);

						if(nuove != null && !nuove.isEmpty()){

							for(SchedaFondoRischiView scheda : nuove){

								FascicoloView fascicoloView = fascicoloService.leggi(scheda.getVo().getFascicolo().getId(), FetchMode.JOIN);

								String nomeCausa = "";

								if(fascicoloView.getVo().getContropartes() != null && !fascicoloView.getVo().getContropartes().isEmpty()){

									if(fascicoloView.getVo().getContropartes().size() == 1)
										nomeCausa = fascicoloView.getVo().getContropartes().iterator().next().getNome();

									else{
										for(Controparte controparte : fascicoloView.getVo().getContropartes()){

											nomeCausa += controparte.getNome() + ", ";
										}
									}
								}
								else{
									if(scheda.getVo().getFascicolo() != null){

										if(scheda.getVo().getFascicolo().getTitolo() != null){
											nomeCausa = scheda.getVo().getFascicolo().getTitolo();
										}
									}
								}
								if(nomeCausa != null && !nomeCausa.isEmpty()){

									XWPFParagraph paragraphScheda = document.createParagraph();
									paragraphScheda.setAlignment(ParagraphAlignment.LEFT);
									XWPFRun paragraphSchedaDescrizione = paragraphScheda.createRun();
									paragraphSchedaDescrizione.setFontFamily("Helvetica");
									paragraphSchedaDescrizione.setFontSize(10);
									paragraphSchedaDescrizione.setBold(true);
									paragraphSchedaDescrizione.setUnderline(UnderlinePatterns.SINGLE);
									paragraphSchedaDescrizione.setText(nomeCausa);
									paragraphSchedaDescrizione.addBreak();
								}
								creaDocTableSchedaFondoRischiAltre(scheda, document, language, fascicoloView);

								XWPFParagraph paragraphBreak = document.createParagraph();
								paragraphBreak.setAlignment(ParagraphAlignment.CENTER);
								XWPFRun paragraphBreakDescrizione = paragraphBreak.createRun();
								paragraphBreakDescrizione.setFontFamily("Helvetica");
								paragraphBreakDescrizione.setFontSize(10);
								paragraphBreakDescrizione.setBold(true);
								paragraphBreakDescrizione.setText("");
								paragraphBreakDescrizione.addBreak();
							}
						}
					}
				}

				/** QUARTO PARAGRAFO (SCHEDE ELIMINATE NEL TRIMESTRE CORRENTE) **/
				XWPFParagraph paragraphNBEiminate = document.createParagraph();
				paragraphNBEiminate.setAlignment(ParagraphAlignment.CENTER);
				XWPFRun paragraphNBEliminateDescrizione = paragraphNBEiminate.createRun();
				paragraphNBEliminateDescrizione.setFontFamily("Helvetica");
				paragraphNBEliminateDescrizione.setFontSize(12);
				paragraphNBEliminateDescrizione.setBold(true);
				paragraphNBEliminateDescrizione.setUnderline(UnderlinePatterns.SINGLE);

				if(language == 0)
					paragraphNBEliminateDescrizione.setText("N.B. Rispetto alla precedente relazione del trimestre "+ trimestrePrecedente +", sono stati eliminati i seguenti contenziosi:");
				else
					paragraphNBEliminateDescrizione.setText("N.B. Compared with the previous report of quarter "+ trimestrePrecedente +", the following disputes were deleted:");
				paragraphNBEliminateDescrizione.addBreak();

				Map<String, List<SchedaFondoRischiView>> mappaSchedeEliminateRaggruppate = raggruppaPerPrimoRiporto(listaSchedeEliminate);

				if(mappaSchedeEliminateRaggruppate != null && !mappaSchedeEliminateRaggruppate.isEmpty()){

					Set<String> matricolePrimiRiporti = mappaSchedeEliminateRaggruppate.keySet();

					for(String primoRiporto : matricolePrimiRiporti){

						List<SchedaFondoRischiView> eliminate = mappaSchedeEliminateRaggruppate.get(primoRiporto);

						if(eliminate != null && !eliminate.isEmpty()){

							for(SchedaFondoRischiView scheda : eliminate){

								FascicoloView fascicoloView = fascicoloService.leggiTutti(scheda.getVo().getFascicolo().getId(), FetchMode.JOIN);



								String nomeCausa = "";

								if(fascicoloView.getVo().getContropartes() != null && !fascicoloView.getVo().getContropartes().isEmpty()){

									if(fascicoloView.getVo().getContropartes().size() == 1)
										nomeCausa = fascicoloView.getVo().getContropartes().iterator().next().getNome();

									else{
										for(Controparte controparte : fascicoloView.getVo().getContropartes()){

											nomeCausa += controparte.getNome() + ", ";
										}
									}
								}
								else{
									if(scheda.getVo().getFascicolo() != null){

										if(scheda.getVo().getFascicolo().getTitolo() != null){
											nomeCausa = scheda.getVo().getFascicolo().getTitolo();
										}
									}
								}
								if(nomeCausa != null && !nomeCausa.isEmpty()){

									XWPFParagraph paragraphScheda = document.createParagraph();
									paragraphScheda.setAlignment(ParagraphAlignment.LEFT);
									XWPFRun paragraphSchedaDescrizione = paragraphScheda.createRun();
									paragraphSchedaDescrizione.setFontFamily("Helvetica");
									paragraphSchedaDescrizione.setFontSize(10);
									paragraphSchedaDescrizione.setBold(true);
									paragraphSchedaDescrizione.setUnderline(UnderlinePatterns.SINGLE);
									paragraphSchedaDescrizione.setText(nomeCausa);
									paragraphSchedaDescrizione.addBreak();
								}
								creaDocTableSchedaFondoRischiAltre(scheda, document, language, fascicoloView);

								XWPFParagraph paragraphBreak = document.createParagraph();
								paragraphBreak.setAlignment(ParagraphAlignment.CENTER);
								XWPFRun paragraphBreakDescrizione = paragraphBreak.createRun();
								paragraphBreakDescrizione.setFontFamily("Helvetica");
								paragraphBreakDescrizione.setFontSize(10);
								paragraphBreakDescrizione.setBold(true);
								paragraphBreakDescrizione.setText("");
								paragraphBreakDescrizione.addBreak();
							}
						}
					}
				}
			}

			String nomeFile = "";
			if(language == 0)
				nomeFile = "Modifiche PFR - " + trimestre +".docx";
			else
				nomeFile = "Changes PFR - " + trimestre +".docx";

			response.setHeader("Content-Disposition", "attachment; filename=" + nomeFile);
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
			document.write(response.getOutputStream());
			response.getOutputStream().flush();
			response.getOutputStream().close();
			response.flushBuffer();

		} catch (DocumentException de) {
			throw new IOException(de.getMessage());
		}
	}


	protected PdfPTable creaPdfTableSchedaFondoRischi(SchedaFondoRischiView scheda, int language, FascicoloView fascicoloView) throws Throwable {
		PdfPTable table = new PdfPTable(2);

		// Nomi celle
		float sizeColumnTitle = 10;
		BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
		Font fontColumnTitle = new Font(bf, sizeColumnTitle, Font.ITALIC, Color.BLACK);

		// Contenuto celle
		float sizeColumnValue = 9;
		Font fontColumnValue = new Font(bf, sizeColumnValue, Font.NORMAL, Color.BLACK);

		//Info Copertura Assicurativa - Manleva - Commessa di investimento
		float sizeColumnSubValue = 8;
		BaseFont bfSubValue = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
		Font fontColumnSubValue = new Font(bfSubValue, sizeColumnSubValue, Font.ITALIC, Color.BLACK);

		// ORGANO GIUDICANTE
		table.getDefaultCell().setBackgroundColor(GrayColor.DARK_GRAY);
		if(language == 0)
			table.addCell( new PdfPCell( new Phrase( "Organo Giudicante", fontColumnTitle ) ) );
		else
			table.addCell( new PdfPCell( new Phrase( "Judging Body", fontColumnTitle ) ) );
		table.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);

		if (fascicoloView.getVo().getRFascicoloGiudizios() != null) {
			Collection<RFascicoloGiudizio> listaFascicoloGiudizios = fascicoloView.getVo().getRFascicoloGiudizios();					
			String organoGiudicante = "";

			for (RFascicoloGiudizio giudizio : listaFascicoloGiudizios) {

				if (giudizio.getOrganoGiudicante() != null) {
					organoGiudicante += giudizio.getOrganoGiudicante().getNome() + " ";
				}
			}
			table.addCell( new PdfPCell( new Phrase(organoGiudicante,fontColumnValue) ) );
		}
		else{
			table.addCell( new PdfPCell( new Phrase("",fontColumnValue) ) );
		}

		// GIUDIZIO INSTAURATO
		table.getDefaultCell().setBackgroundColor(GrayColor.DARK_GRAY);
		if(language == 0)
			table.addCell( new PdfPCell( new Phrase( "Giudizio Instaurato", fontColumnTitle ) ) );
		else
			table.addCell( new PdfPCell( new Phrase( "Established Judgment", fontColumnTitle ) ) );
		table.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);

		if (fascicoloView.getVo().getRFascicoloGiudizios() != null) {
			Collection<RFascicoloGiudizio> listaFascicoloGiudizios = fascicoloView.getVo().getRFascicoloGiudizios();					
			String giudizi = "";

			for (RFascicoloGiudizio giudizio : listaFascicoloGiudizios) {

				if (giudizio.getGiudizio() != null) {
					giudizi += giudizio.getGiudizio().getDescrizione() + " ";
				}
			}
			table.addCell( new PdfPCell( new Phrase(giudizi,fontColumnValue) ) );
		}
		else{
			table.addCell( new PdfPCell( new Phrase("",fontColumnValue) ) );
		}

		// VALORE DOMANDA
		table.getDefaultCell().setBackgroundColor(GrayColor.DARK_GRAY);
		if(language == 0)
			table.addCell( new PdfPCell( new Phrase( "Valore domanda", fontColumnTitle ) ) );
		else
			table.addCell( new PdfPCell( new Phrase( "Demand Value", fontColumnTitle ) ) );
		table.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
		String valoreDomanda = scheda.getVo().getValoreDomanda() == null ? "" : "" + scheda.getVo().getValoreDomanda().floatValue();
		table.addCell( new PdfPCell( new Phrase(valoreDomanda,fontColumnValue) ) );

		// PROFESSIONISTA ESTERNO
		table.getDefaultCell().setBackgroundColor(GrayColor.DARK_GRAY);
		if(language == 0)
			table.addCell( new PdfPCell( new Phrase( "Legale Esterno", fontColumnTitle ) ) );
		else
			table.addCell( new PdfPCell( new Phrase( "External Legal", fontColumnTitle ) ) );
		table.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);

		if (fascicoloView.getVo().getIncaricos() != null) {
			Collection<Incarico> listaIncaricos = fascicoloView.getVo().getIncaricos();					
			String professionistaEsterno = "";

			for (Incarico incarico : listaIncaricos) {

				if (incarico.getProfessionistaEsterno() != null) {
					professionistaEsterno += incarico.getProfessionistaEsterno().getCognomeNome() + " ";
				}
			}
			table.addCell( new PdfPCell( new Phrase(professionistaEsterno,fontColumnValue) ) );
		}
		else{
			table.addCell( new PdfPCell( new Phrase("",fontColumnValue) ) );
		}

		// FASCICOLO DI RIFERIMENTO
		table.getDefaultCell().setBackgroundColor(GrayColor.DARK_GRAY);
		if(language == 0)
			table.addCell( new PdfPCell( new Phrase( "Numero Fascicolo Legal", fontColumnTitle ) ) );
		else
			table.addCell( new PdfPCell( new Phrase( "Dossier Legal Number", fontColumnTitle ) ) );
		table.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
		String fascicolo = scheda.getVo().getFascicolo() ==null ? "" : scheda.getVo().getFascicolo().getNome();
		table.addCell( new PdfPCell( new Phrase(fascicolo,fontColumnValue) ) );

		// TESTO ESPLICATIVO
		table.getDefaultCell().setBackgroundColor(GrayColor.DARK_GRAY);
		if(language == 0)
			table.addCell( new PdfPCell( new Phrase( "Testo", fontColumnTitle ) ) );
		else
			table.addCell( new PdfPCell( new Phrase( "Text", fontColumnTitle ) ) );
		table.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
		String testoEsplicativo = scheda.getVo().getTestoEsplicativo() == null ? "" : "" + scheda.getVo().getTestoEsplicativo();

		if(scheda.getVo().getCoperturaAssicurativa() != null || scheda.getVo().getManleva() != null || scheda.getVo().getCommessaDiInvestimento() != null){

			Paragraph parSubTestoEsplicativo = new Paragraph();
			parSubTestoEsplicativo.setAlignment(Element.ALIGN_LEFT);
			parSubTestoEsplicativo.setFont(fontColumnSubValue);

			String subInfo = "";

			List<String> info = new ArrayList<String>();

			if(scheda.getVo().getCoperturaAssicurativa() != null){

				if(language == 0)
					info.add("Esiste Copertura Assicurativa ("+ scheda.getVo().getCoperturaAssicurativa().floatValue() +")");
				else
					info.add("Insurance Coverage Exists");
			}

			if(scheda.getVo().getManleva() != null){

				if(language == 0)
					info.add("Esiste Manleva ("+ scheda.getVo().getManleva().floatValue() +")");
				else
					info.add("Manleva Exists");
			}

			if(scheda.getVo().getCommessaDiInvestimento() != null){

				if(language == 0)
					info.add("Esiste Commessa di Investimento ("+ scheda.getVo().getCommessaDiInvestimento().floatValue() +")");
				else
					info.add("Investment Commitment Exists");
			}

			if(info.size() == 1){
				subInfo += info.get(0);
			}else if(info.size() == 2){
				subInfo += info.get(0) + " , " + info.get(1);
			}else if(info.size() == 3){
				subInfo += info.get(0) + " , " + info.get(1) + " , " + info.get(2);
			}

			PdfPTable subtable = new PdfPTable(1);
			subtable.getDefaultCell().setBorderWidth(0f);
			subtable.addCell( new PdfPCell( new Phrase(testoEsplicativo,fontColumnValue) ) );
			subtable.addCell( new PdfPCell( new Phrase(subInfo,fontColumnSubValue) ) );
			table.addCell( new PdfPCell( subtable ));
		}
		else{
			table.addCell( new PdfPCell( new Phrase(testoEsplicativo,fontColumnValue) ) );
		}

		// RISCHIO SOCCOMBENZA
		table.getDefaultCell().setBackgroundColor(GrayColor.DARK_GRAY);
		if(language == 0)
			table.addCell( new PdfPCell( new Phrase( "Rischio Soccombenza", fontColumnTitle ) ) );
		else
			table.addCell( new PdfPCell( new Phrase( "Losing Risk", fontColumnTitle ) ) );
		table.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
		String rischioSoccombenza = scheda.getVo().getRischioSoccombenza().getDescrizione()==null ? "" : scheda.getVo().getRischioSoccombenza().getDescrizione();
		table.addCell( new PdfPCell( new Phrase(rischioSoccombenza,fontColumnValue) ) );

		// PASSIVITA STIMATA
		table.getDefaultCell().setBackgroundColor(GrayColor.DARK_GRAY);
		if(language == 0)
			table.addCell( new PdfPCell( new Phrase( "Passività Stimata", fontColumnTitle ) ) );
		else
			table.addCell( new PdfPCell( new Phrase( "Estimated Liabilities", fontColumnTitle ) ) );
		table.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
		String passivitaStimata = scheda.getVo().getPassivitaStimata() == null ? "" : "" + scheda.getVo().getPassivitaStimata().floatValue();
		table.addCell( new PdfPCell( new Phrase(passivitaStimata,fontColumnValue) ) );

		// MOTIVAZIONE
		table.getDefaultCell().setBackgroundColor(GrayColor.DARK_GRAY);
		if(language == 0)
			table.addCell( new PdfPCell( new Phrase( "Motivazione", fontColumnTitle ) ) );
		else
			table.addCell( new PdfPCell( new Phrase( "Motivation", fontColumnTitle ) ) );
		table.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
		String motivazione = scheda.getVo().getMotivazione() == null ? "" : "" + scheda.getVo().getMotivazione();
		table.addCell( new PdfPCell( new Phrase(motivazione,fontColumnValue) ) );

		return table;
	}

	private void creaDocTableSchedaFondoRischiModificate(SchedaFondoRischiView scheda, StoricoSchedaFondoRischiView storico, XWPFDocument document, int language, FascicoloView fascicoloView) throws Throwable {

		XWPFTable table = document.createTable();

		// PRIMA RIGA
		XWPFTableRow tablePrimaRiga = table.getRow(0);

		XWPFTableCell cellCampo= tablePrimaRiga.getCell(0);
		cellCampo.setVerticalAlignment(XWPFVertAlign.CENTER);
		XWPFParagraph paraCampo = cellCampo.getParagraphs().get(0);
		paraCampo.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun rhCampo = paraCampo.createRun();
		rhCampo.setFontFamily("Helvetica");
		rhCampo.setBold(true);
		rhCampo.setFontSize(10);
		if(language == 0)
			rhCampo.setText("CAMPO");
		else
			rhCampo.setText("FIELD");

		XWPFTableCell cellValoreAttuale = tablePrimaRiga.addNewTableCell();
		cellValoreAttuale.setVerticalAlignment(XWPFVertAlign.CENTER);
		XWPFParagraph paraValoreAttuale = cellValoreAttuale.getParagraphs().get(0);
		paraValoreAttuale.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun rhValoreAttuale = paraValoreAttuale.createRun();
		rhValoreAttuale.setFontFamily("Helvetica");
		rhValoreAttuale.setBold(true);
		rhValoreAttuale.setFontSize(10);
		if(language == 0)
			rhValoreAttuale.setText("VALORE ATTUALE");
		else
			rhValoreAttuale.setText("ACTUAL VALUE");

		XWPFTableCell cellValorePrec = tablePrimaRiga.addNewTableCell();
		cellValorePrec.setVerticalAlignment(XWPFVertAlign.CENTER);
		XWPFParagraph paraValorePrec = cellValorePrec.getParagraphs().get(0);
		paraValorePrec.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun rhValorePrec = paraValorePrec.createRun();
		rhValorePrec.setFontFamily("Helvetica");
		rhValorePrec.setBold(true);
		rhValorePrec.setFontSize(10);
		if(language == 0)
			rhValorePrec.setText("VALORE PRECEDENTE");
		else
			rhValorePrec.setText("PREVIOUS VALUE");

		// ORGANO GIUDICANTE
		XWPFTableRow tableOrganoGiudicante = table.createRow();
		if(language == 0)
			tableOrganoGiudicante.getCell(0).setText("Organo Giudicante");
		else
			tableOrganoGiudicante.getCell(0).setText("Judging Body");

		if (fascicoloView.getVo().getRFascicoloGiudizios() != null) {
			Collection<RFascicoloGiudizio> listaFascicoloGiudizios = fascicoloView.getVo().getRFascicoloGiudizios();					
			String organoGiudicante = "";

			for (RFascicoloGiudizio giudizio : listaFascicoloGiudizios) {

				if (giudizio.getOrganoGiudicante() != null) {
					organoGiudicante += giudizio.getOrganoGiudicante().getNome() + " ";
				}
			}
			tableOrganoGiudicante.getCell(1).setText(organoGiudicante);
		}
		else{
			tableOrganoGiudicante.getCell(1).setText("");
		}
		tableOrganoGiudicante.getCell(2).setText("");

		// GIUDIZIO INSTAURATO
		XWPFTableRow tableGiudizioInstaurato = table.createRow();
		if(language == 0)
			tableGiudizioInstaurato.getCell(0).setText("Giudizio Instaurato");
		else
			tableGiudizioInstaurato.getCell(0).setText("Established Judgment");

		if (fascicoloView.getVo().getRFascicoloGiudizios() != null) {
			Collection<RFascicoloGiudizio> listaFascicoloGiudizios = fascicoloView.getVo().getRFascicoloGiudizios();					
			String giudizi = "";

			for (RFascicoloGiudizio giudizio : listaFascicoloGiudizios) {

				if (giudizio.getGiudizio() != null) {
					giudizi += giudizio.getGiudizio().getDescrizione() + " ";
				}
			}
			tableGiudizioInstaurato.getCell(1).setText(giudizi);
		}
		else{
			tableGiudizioInstaurato.getCell(1).setText("");
		}
		tableGiudizioInstaurato.getCell(2).setText("");

		// VALORE DOMANDA
		XWPFTableRow tableValoreDomanda = table.createRow();
		if(language == 0)
			tableValoreDomanda.getCell(0).setText("Valore domanda");
		else
			tableValoreDomanda.getCell(0).setText("Demand Value");
		String valoreDomanda = scheda.getVo().getValoreDomanda() == null ? "" : "" + scheda.getVo().getValoreDomanda().floatValue();
		String valoreDomandaPrecedente = storico.getVo().getValoreDomanda() == null ? "" : "" + storico.getVo().getValoreDomanda().floatValue();
		tableValoreDomanda.getCell(1).setText(valoreDomanda);

		if(!valoreDomanda.equals(valoreDomandaPrecedente))
			tableValoreDomanda.getCell(2).setText(valoreDomandaPrecedente);
		else
			tableValoreDomanda.getCell(2).setText("");


		// PROFESSIONISTA ESTERNO
		XWPFTableRow tableProfessionistaEsterno = table.createRow();
		if(language == 0)
			tableProfessionistaEsterno.getCell(0).setText("Legale Esterno");
		else
			tableProfessionistaEsterno.getCell(0).setText("External Legal");

		if (fascicoloView.getVo().getIncaricos() != null) {
			Collection<Incarico> listaIncaricos = fascicoloView.getVo().getIncaricos();					
			String professionistaEsterno = "";

			for (Incarico incarico : listaIncaricos) {

				if (incarico.getProfessionistaEsterno() != null) {
					professionistaEsterno += incarico.getProfessionistaEsterno().getCognomeNome() + " ";
				}
			}
			tableProfessionistaEsterno.getCell(1).setText(professionistaEsterno);
		}
		else{
			tableProfessionistaEsterno.getCell(1).setText("");
		}
		tableProfessionistaEsterno.getCell(2).setText("");


		// FASCICOLO DI RIFERIMENTO
		XWPFTableRow tableRigaFascicoloRiferimento = table.createRow();
		if(language == 0)
			tableRigaFascicoloRiferimento.getCell(0).setText("Numero Fascicolo Legal");
		else
			tableRigaFascicoloRiferimento.getCell(0).setText("Legal Dossier Number");
		String fascicolo = scheda.getVo().getFascicolo() ==null ? "" : scheda.getVo().getFascicolo().getNome();
		tableRigaFascicoloRiferimento.getCell(1).setText(fascicolo);
		tableRigaFascicoloRiferimento.getCell(2).setText("");

		//		// DATA CREAZIONE SCHEDA
		//		XWPFTableRow tableRigaDataCreazione = table.createRow();
		//		if(language == 0)
		//			tableRigaDataCreazione.getCell(0).setText("Data Creazione");
		//		else
		//			tableRigaDataCreazione.getCell(0).setText("Creation Date");
		//		Date dataIns = scheda.getVo().getDataCreazione();
		//		String formatDataIns = null;
		//		if(dataIns!=null) {
		//			if(language == 0)
		//				formatDataIns = new SimpleDateFormat("dd/MM/yyyy").format(dataIns);
		//			else
		//				formatDataIns = new SimpleDateFormat("MM/dd/yyyy").format(dataIns);
		//		}
		//		else
		//			formatDataIns = "";
		//		tableRigaDataCreazione.getCell(1).setText(formatDataIns);
		//		tableRigaDataCreazione.getCell(2).setText("");

		//		// STATO SCHEDA
		//		XWPFTableRow tableRigaStatoScheda = table.createRow();
		//		if(language == 0)
		//			tableRigaStatoScheda.getCell(0).setText("Stato Scheda");
		//		else
		//			tableRigaStatoScheda.getCell(0).setText("Card Status");
		//		String statoScheda = scheda.getVo().getStatoSchedaFondoRischi().getDescrizione()==null ? "" : scheda.getVo().getStatoSchedaFondoRischi().getDescrizione();
		//		String statoSchedaPrecedente = storico.getVo().getStatoSchedaFondoRischi().getDescrizione()==null ? "" : storico.getVo().getStatoSchedaFondoRischi().getDescrizione();
		//		tableRigaStatoScheda.getCell(1).setText(statoScheda);
		//
		//		if(!statoScheda.equals(statoSchedaPrecedente))
		//			tableRigaStatoScheda.getCell(2).setText(statoSchedaPrecedente);
		//		else
		//			tableRigaStatoScheda.getCell(2).setText("");
		//
		//		// TIPOLOGIA SCHEDA
		//		XWPFTableRow tableRigaTipologiaScheda = table.createRow();
		//		if(language == 0)
		//			tableRigaTipologiaScheda.getCell(0).setText("Tipologia Scheda");
		//		else
		//			tableRigaTipologiaScheda.getCell(0).setText("Card Type");
		//		String tipologia = scheda.getVo().getTipologiaSchedaFr().getDescrizione()==null ? "" : scheda.getVo().getTipologiaSchedaFr().getDescrizione();
		//		String tipologiaPrecedente = storico.getVo().getTipologiaSchedaFr().getDescrizione()==null ? "" : storico.getVo().getTipologiaSchedaFr().getDescrizione();
		//		tableRigaTipologiaScheda.getCell(1).setText(tipologia);
		//
		//		if(!tipologia.equals(tipologiaPrecedente))
		//			tableRigaTipologiaScheda.getCell(2).setText(tipologiaPrecedente);
		//		else
		//			tableRigaTipologiaScheda.getCell(2).setText("");
		//
		//		// CONTROPARTE
		//		XWPFTableRow tableRigaControparte = table.createRow();
		//		if(language == 0)
		//			tableRigaControparte.getCell(0).setText("Controparte");
		//		else
		//			tableRigaControparte.getCell(0).setText("Counterpart");
		//		String controparte = scheda.getVo().getControparte() == null ? "" : scheda.getVo().getControparte();
		//		String contropartePrecedente = storico.getVo().getControparte() == null ? "" : storico.getVo().getControparte();
		//		tableRigaControparte.getCell(1).setText(controparte);
		//
		//		if(!controparte.equals(contropartePrecedente))
		//			tableRigaControparte.getCell(2).setText(contropartePrecedente);
		//		else
		//			tableRigaControparte.getCell(2).setText("");
		//
		//		// SOCIETA ADDEBITO
		//		XWPFTableRow tableSocietaAddebito = table.createRow();
		//		if(language == 0)
		//			tableSocietaAddebito.getCell(0).setText("Società di addebito");
		//		else
		//			tableSocietaAddebito.getCell(0).setText("Complaining company");
		//
		//		if (fascicoloView.getVo().getRFascicoloSocietas() != null) {
		//			Collection<RFascicoloSocieta> listaFascicoloSocieta = fascicoloView.getVo().getRFascicoloSocietas();					
		//			String societaAddebitoAggiunteDesc = "";
		//
		//			for (RFascicoloSocieta societa : listaFascicoloSocieta) {
		//
		//				if (societa.getTipologiaSocieta().equals(Costanti.SOCIETA_TIPOLOGIA_ADDEBITO)) {
		//					societaAddebitoAggiunteDesc += societa.getSocieta().getNome() + " ";
		//				}
		//			}
		//			tableSocietaAddebito.getCell(1).setText(societaAddebitoAggiunteDesc);
		//		}
		//		else{
		//			tableSocietaAddebito.getCell(1).setText("");
		//		}
		//		tableSocietaAddebito.getCell(2).setText("");
		//
		//		// DATA RICHIESTA AUTORIZZAZIONE
		//		XWPFTableRow tableDataRichiestaAutorizzazione = table.createRow();
		//		if(language == 0)
		//			tableDataRichiestaAutorizzazione.getCell(0).setText("Data Richiesta Autorizzazione");
		//		else
		//			tableDataRichiestaAutorizzazione.getCell(0).setText("Request Authorization Date");
		//		Date dataRischiestaAutorizzazione = scheda.getVo().getDataRichiestaAutorScheda();
		//		String formatDataRischiestaAutorizzazione = null;
		//		if(dataRischiestaAutorizzazione!=null) {
		//			if(language == 0)
		//				formatDataRischiestaAutorizzazione = new SimpleDateFormat("dd/MM/yyyy").format(dataRischiestaAutorizzazione);
		//			else
		//				formatDataRischiestaAutorizzazione = new SimpleDateFormat("MM/dd/yyyy").format(dataRischiestaAutorizzazione);
		//		}
		//		else
		//			formatDataRischiestaAutorizzazione = "";
		//		Date dataRischiestaAutorizzazionePrec = storico.getVo().getDataRichiestaAutorScheda();
		//		String formatDataRischiestaAutorizzazionePrec = null;
		//		if(dataRischiestaAutorizzazionePrec!=null) {
		//			if(language == 0)
		//				formatDataRischiestaAutorizzazionePrec = new SimpleDateFormat("dd/MM/yyyy").format(dataRischiestaAutorizzazionePrec);
		//			else
		//				formatDataRischiestaAutorizzazionePrec = new SimpleDateFormat("MM/dd/yyyy").format(dataRischiestaAutorizzazionePrec);
		//		}
		//		else
		//			formatDataRischiestaAutorizzazionePrec = "";
		//
		//		tableDataRichiestaAutorizzazione.getCell(1).setText(formatDataRischiestaAutorizzazione);
		//
		//		if(!formatDataRischiestaAutorizzazione.equals(formatDataRischiestaAutorizzazionePrec))
		//			tableDataRichiestaAutorizzazione.getCell(2).setText(formatDataRischiestaAutorizzazionePrec);
		//		else
		//			tableDataRichiestaAutorizzazione.getCell(2).setText("");
		//
		//		// DATA AUTORIZZAZIONE
		//		XWPFTableRow tableDataAutorizzazione = table.createRow();
		//		if(language == 0)
		//			tableDataAutorizzazione.getCell(0).setText("Data Autorizzazione");
		//		else
		//			tableDataAutorizzazione.getCell(0).setText("Authorization Date");
		//		Date dataAutorizzazione = scheda.getVo().getDataAutorizzazione();
		//		String formatDataAutorizzazione = null;
		//		if(dataAutorizzazione!=null) {
		//			if(language == 0)
		//				formatDataAutorizzazione = new SimpleDateFormat("dd/MM/yyyy").format(dataAutorizzazione);
		//			else
		//				formatDataAutorizzazione = new SimpleDateFormat("MM/dd/yyyy").format(dataAutorizzazione);
		//		}
		//		else
		//			formatDataAutorizzazione = "";
		//		Date dataAutorizzazionePrec = storico.getVo().getDataAutorizzazione();
		//		String formatDataAutorizzazionePrec = null;
		//		if(dataAutorizzazionePrec!=null) {
		//			if(language == 0)
		//				formatDataAutorizzazionePrec = new SimpleDateFormat("dd/MM/yyyy").format(dataAutorizzazionePrec);
		//			else
		//				formatDataAutorizzazionePrec = new SimpleDateFormat("MM/dd/yyyy").format(dataAutorizzazionePrec);
		//		}
		//		else
		//			formatDataAutorizzazionePrec = "";
		//
		//		tableDataAutorizzazione.getCell(1).setText(formatDataAutorizzazione);
		//
		//		if(!formatDataAutorizzazione.equals(formatDataAutorizzazionePrec))
		//			tableDataAutorizzazione.getCell(2).setText(formatDataAutorizzazionePrec);
		//		else
		//			tableDataAutorizzazione.getCell(2).setText("");

		// TESTO ESPLICATIVO
		XWPFTableRow tableTestoEsplicativo = table.createRow();
		if(language == 0)
			tableTestoEsplicativo.getCell(0).setText("Testo Esplicativo");
		else
			tableTestoEsplicativo.getCell(0).setText("Explanatory Text");
		String testoEsplicativo = scheda.getVo().getTestoEsplicativo() == null ? "" : "" + scheda.getVo().getTestoEsplicativo();
		String testoEsplicativoPrecedente = storico.getVo().getTestoEsplicativo() == null ? "" : "" + storico.getVo().getTestoEsplicativo();
		tableTestoEsplicativo.getCell(1).setText(testoEsplicativo);

		if(!testoEsplicativo.equals(testoEsplicativoPrecedente))
			tableTestoEsplicativo.getCell(2).setText(testoEsplicativoPrecedente);
		else
			tableTestoEsplicativo.getCell(2).setText("");

		// RISCHIO SOCCOMBENZA
		XWPFTableRow tableRischioSoccombenza = table.createRow();
		if(language == 0)
			tableRischioSoccombenza.getCell(0).setText("Rischio Soccombenza");
		else
			tableRischioSoccombenza.getCell(0).setText("Losing Risk");
		String rischioSoccombenza = scheda.getVo().getRischioSoccombenza().getDescrizione()==null ? "" : scheda.getVo().getRischioSoccombenza().getDescrizione();
		String rischioSoccombenzaPrec = storico.getVo().getRischioSoccombenza().getDescrizione()==null ? "" : storico.getVo().getRischioSoccombenza().getDescrizione();
		tableRischioSoccombenza.getCell(1).setText(rischioSoccombenza);

		if(!rischioSoccombenza.equals(rischioSoccombenzaPrec))
			tableRischioSoccombenza.getCell(2).setText(rischioSoccombenzaPrec);
		else
			tableRischioSoccombenza.getCell(2).setText("");

		// COPERTURA ASSICURATIVA
		XWPFTableRow tableCoperturaAssicurativa = table.createRow();
		if(language == 0)
			tableCoperturaAssicurativa.getCell(0).setText("Copertura Assicurativa");
		else
			tableCoperturaAssicurativa.getCell(0).setText("Insurance Coverage");
		String coperturaAssicurativa = scheda.getVo().getCoperturaAssicurativa() == null ? "" : "" + scheda.getVo().getCoperturaAssicurativa().floatValue();
		String coperturaAssicurativaPrec = storico.getVo().getCoperturaAssicurativa() == null ? "" : "" + storico.getVo().getCoperturaAssicurativa().floatValue();
		tableCoperturaAssicurativa.getCell(1).setText(coperturaAssicurativa);

		if(!coperturaAssicurativa.equals(coperturaAssicurativaPrec))
			tableCoperturaAssicurativa.getCell(2).setText(coperturaAssicurativaPrec);
		else
			tableCoperturaAssicurativa.getCell(2).setText("");

		// MANLEVA
		XWPFTableRow tableManleva = table.createRow();
		tableManleva.getCell(0).setText("Manleva");
		String manleva = scheda.getVo().getManleva() == null ? "" : "" + scheda.getVo().getManleva().floatValue();
		String manlevaPrec = storico.getVo().getManleva() == null ? "" : "" + storico.getVo().getManleva().floatValue();
		tableManleva.getCell(1).setText(manleva);

		if(!manleva.equals(manlevaPrec))
			tableManleva.getCell(2).setText(manlevaPrec);
		else
			tableManleva.getCell(2).setText("");

		// COMMESSA DI INVESTIMENTO
		XWPFTableRow tableCommessaDiInvestimento = table.createRow();
		if(language == 0)
			tableCommessaDiInvestimento.getCell(0).setText("Commessa di Investimento");
		else
			tableCommessaDiInvestimento.getCell(0).setText("Investment Commitment");
		String commessaDiInvestimento = scheda.getVo().getCommessaDiInvestimento() == null ? "" : "" + scheda.getVo().getCommessaDiInvestimento().floatValue();
		String commessaDiInvestimentoPrec = storico.getVo().getCommessaDiInvestimento() == null ? "" : "" + storico.getVo().getCommessaDiInvestimento().floatValue();
		tableCommessaDiInvestimento.getCell(1).setText(commessaDiInvestimento);

		if(!commessaDiInvestimento.equals(commessaDiInvestimentoPrec))
			tableCommessaDiInvestimento.getCell(2).setText(commessaDiInvestimentoPrec);
		else
			tableCommessaDiInvestimento.getCell(2).setText("");

		// PASSIVITA STIMATA
		XWPFTableRow tablePassivitaStimata = table.createRow();
		if(language == 0)
			tablePassivitaStimata.getCell(0).setText("Passività Stimata");
		else
			tablePassivitaStimata.getCell(0).setText("Estimated Liabilities");
		String passivitaStimata = scheda.getVo().getPassivitaStimata() == null ? "" : "" + scheda.getVo().getPassivitaStimata().floatValue();
		String passivitaStimataPrec = storico.getVo().getPassivitaStimata() == null ? "" : "" + storico.getVo().getPassivitaStimata().floatValue();
		tablePassivitaStimata.getCell(1).setText(passivitaStimata);

		if(!passivitaStimata.equals(passivitaStimataPrec))
			tablePassivitaStimata.getCell(2).setText(passivitaStimataPrec);
		else
			tablePassivitaStimata.getCell(2).setText("");

		// MOTIVAZIONE
		XWPFTableRow tableMotivazione = table.createRow();
		if(language == 0)
			tableMotivazione.getCell(0).setText("Motivazione");
		else
			tableMotivazione.getCell(0).setText("Motivation");
		String motivazione = scheda.getVo().getMotivazione() == null ? "" : "" + scheda.getVo().getMotivazione();
		String motivazionePrec = storico.getVo().getMotivazione() == null ? "" : "" + storico.getVo().getMotivazione();
		tableMotivazione.getCell(1).setText(motivazione);

		if(!motivazione.equals(motivazionePrec))
			tableMotivazione.getCell(2).setText(motivazionePrec);
		else
			tableMotivazione.getCell(2).setText("");

		//		// ALLEGATI
		//		XWPFTableRow tableAllegati = table.createRow();
		//		if(language == 0)
		//			tableAllegati.getCell(0).setText("Allegati");
		//		else
		//			tableAllegati.getCell(0).setText("Attachments");
		//
		//		caricaDocumentiProfessionistaEsternoFilenet(scheda, scheda.getVo());
		//		String allegati = "";
		//
		//		if (scheda.getListaAllegatiLegaleEsterno() != null && !scheda.getListaAllegatiLegaleEsterno().isEmpty()) {
		//			List<DocumentoView> documenti = scheda.getListaAllegatiLegaleEsterno();					
		//
		//			for (DocumentoView documento : documenti) {
		//
		//				allegati += documento.getNomeFile();
		//			}
		//		}
		//		String allegatiPrec = storico.getVo().getAllegato() == null ? "" : "" + storico.getVo().getAllegato();
		//		tableAllegati.getCell(1).setText(allegati);
		//
		//		if(!allegati.equals(allegatiPrec))
		//			tableAllegati.getCell(2).setText(allegatiPrec);
		//		else
		//			tableAllegati.getCell(2).setText("");
	}


	private void creaDocTableSchedaFondoRischiAltre(SchedaFondoRischiView scheda, XWPFDocument document, int language, FascicoloView fascicoloView) throws Throwable {

		XWPFTable table = document.createTable();

		// PRIMA RIGA
		XWPFTableRow tablePrimaRiga = table.getRow(0);

		XWPFTableCell cellCampo= tablePrimaRiga.getCell(0);
		cellCampo.setVerticalAlignment(XWPFVertAlign.CENTER);
		XWPFParagraph paraCampo = cellCampo.getParagraphs().get(0);
		paraCampo.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun rhCampo = paraCampo.createRun();
		rhCampo.setFontFamily("Helvetica");
		rhCampo.setBold(true);
		rhCampo.setFontSize(10);
		if(language == 0)
			rhCampo.setText("CAMPO");
		else
			rhCampo.setText("FIELD");

		XWPFTableCell cellValoreAttuale = tablePrimaRiga.addNewTableCell();
		cellValoreAttuale.setVerticalAlignment(XWPFVertAlign.CENTER);
		XWPFParagraph paraValoreAttuale = cellValoreAttuale.getParagraphs().get(0);
		paraValoreAttuale.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun rhValoreAttuale = paraValoreAttuale.createRun();
		rhValoreAttuale.setFontFamily("Helvetica");
		rhValoreAttuale.setBold(true);
		rhValoreAttuale.setFontSize(10);
		if(language == 0)
			rhValoreAttuale.setText("VALORE");
		else
			rhValoreAttuale.setText("VALUE");

		// ORGANO GIUDICANTE
		XWPFTableRow tableOrganoGiudicante = table.createRow();
		if(language == 0)
			tableOrganoGiudicante.getCell(0).setText("Organo Giudicante");
		else
			tableOrganoGiudicante.getCell(0).setText("Judging Body");

		if (fascicoloView.getVo().getRFascicoloGiudizios() != null) {
			Collection<RFascicoloGiudizio> listaFascicoloGiudizios = fascicoloView.getVo().getRFascicoloGiudizios();					
			String organoGiudicante = "";

			for (RFascicoloGiudizio giudizio : listaFascicoloGiudizios) {

				if (giudizio.getOrganoGiudicante() != null) {
					organoGiudicante += giudizio.getOrganoGiudicante().getNome() + " ";
				}
			}
			tableOrganoGiudicante.getCell(1).setText(organoGiudicante);
		}
		else{
			tableOrganoGiudicante.getCell(1).setText("");
		}

		// GIUDIZIO INSTAURATO
		XWPFTableRow tableGiudizioInstaurato = table.createRow();
		if(language == 0)
			tableGiudizioInstaurato.getCell(0).setText("Giudizio Instaurato");
		else
			tableGiudizioInstaurato.getCell(0).setText("Established Judgment");

		if (fascicoloView.getVo().getRFascicoloGiudizios() != null) {
			Collection<RFascicoloGiudizio> listaFascicoloGiudizios = fascicoloView.getVo().getRFascicoloGiudizios();					
			String giudizi = "";

			for (RFascicoloGiudizio giudizio : listaFascicoloGiudizios) {

				if (giudizio.getGiudizio() != null) {
					giudizi += giudizio.getGiudizio().getDescrizione() + " ";
				}
			}
			tableGiudizioInstaurato.getCell(1).setText(giudizi);
		}
		else{
			tableGiudizioInstaurato.getCell(1).setText("");
		}

		// VALORE DOMANDA
		XWPFTableRow tableValoreDomanda = table.createRow();
		if(language == 0)
			tableValoreDomanda.getCell(0).setText("Valore domanda");
		else
			tableValoreDomanda.getCell(0).setText("Demand Value");
		String valoreDomanda = scheda.getVo().getValoreDomanda() == null ? "" : "" + scheda.getVo().getValoreDomanda().floatValue();
		tableValoreDomanda.getCell(1).setText(valoreDomanda);

		// PROFESSIONISTA ESTERNO
		XWPFTableRow tableProfessionistaEsterno = table.createRow();
		if(language == 0)
			tableProfessionistaEsterno.getCell(0).setText("Legale Esterno");
		else
			tableProfessionistaEsterno.getCell(0).setText("External Legal");

		if (fascicoloView.getVo().getIncaricos() != null) {
			Collection<Incarico> listaIncaricos = fascicoloView.getVo().getIncaricos();					
			String professionistaEsterno = "";

			for (Incarico incarico : listaIncaricos) {

				if (incarico.getProfessionistaEsterno() != null) {
					professionistaEsterno += incarico.getProfessionistaEsterno().getCognomeNome() + " ";
				}
			}
			tableProfessionistaEsterno.getCell(1).setText(professionistaEsterno);
		}
		else{
			tableProfessionistaEsterno.getCell(1).setText("");
		}

		// FASCICOLO DI RIFERIMENTO
		XWPFTableRow tableRigaFascicoloRiferimento = table.createRow();
		if(language == 0)
			tableRigaFascicoloRiferimento.getCell(0).setText("Numero Fascicolo Legal");
		else
			tableRigaFascicoloRiferimento.getCell(0).setText("Legal Dossier Number");
		String fascicolo = scheda.getVo().getFascicolo() ==null ? "" : scheda.getVo().getFascicolo().getNome();
		tableRigaFascicoloRiferimento.getCell(1).setText(fascicolo);

		//		// DATA CREAZIONE SCHEDA
		//		XWPFTableRow tableRigaDataCreazione = table.createRow();
		//		if(language == 0)
		//			tableRigaDataCreazione.getCell(0).setText("Data Creazione");
		//		else
		//			tableRigaDataCreazione.getCell(0).setText("Creation Date");
		//		Date dataIns = scheda.getVo().getDataCreazione();
		//		String formatDataIns = null;
		//		if(dataIns!=null) {
		//			if(language == 0)
		//				formatDataIns = new SimpleDateFormat("dd/MM/yyyy").format(dataIns);
		//			else
		//				formatDataIns = new SimpleDateFormat("MM/dd/yyyy").format(dataIns);
		//		}
		//		else
		//			formatDataIns = "";
		//		tableRigaDataCreazione.getCell(1).setText(formatDataIns);
		//
		//		// STATO SCHEDA
		//		XWPFTableRow tableRigaStatoScheda = table.createRow();
		//		if(language == 0)
		//			tableRigaStatoScheda.getCell(0).setText("Stato Scheda");
		//		else
		//			tableRigaStatoScheda.getCell(0).setText("Card Status");
		//		String statoScheda = scheda.getVo().getStatoSchedaFondoRischi().getDescrizione()==null ? "" : scheda.getVo().getStatoSchedaFondoRischi().getDescrizione();
		//		tableRigaStatoScheda.getCell(1).setText(statoScheda);
		//
		//		// TIPOLOGIA SCHEDA
		//		XWPFTableRow tableRigaTipologiaScheda = table.createRow();
		//		if(language == 0)
		//			tableRigaTipologiaScheda.getCell(0).setText("Tipologia Scheda");
		//		else
		//			tableRigaTipologiaScheda.getCell(0).setText("Card Type");
		//		String tipologia = scheda.getVo().getTipologiaSchedaFr().getDescrizione()==null ? "" : scheda.getVo().getTipologiaSchedaFr().getDescrizione();
		//		tableRigaTipologiaScheda.getCell(1).setText(tipologia);
		//
		//		// CONTROPARTE
		//		XWPFTableRow tableRigaControparte = table.createRow();
		//		if(language == 0)
		//			tableRigaControparte.getCell(0).setText("Controparte");
		//		else
		//			tableRigaControparte.getCell(0).setText("Counterpart");
		//		String controparte = scheda.getVo().getControparte() == null ? "" : scheda.getVo().getControparte();
		//		tableRigaControparte.getCell(1).setText(controparte);
		//
		//		// SOCIETA ADDEBITO
		//		XWPFTableRow tableSocietaAddebito = table.createRow();
		//		if(language == 0)
		//			tableSocietaAddebito.getCell(0).setText("Società di addebito");
		//		else
		//			tableSocietaAddebito.getCell(0).setText("Complaining company");
		//
		//		if (fascicoloView.getVo().getRFascicoloSocietas() != null) {
		//			Collection<RFascicoloSocieta> listaFascicoloSocieta = fascicoloView.getVo().getRFascicoloSocietas();					
		//			String societaAddebitoAggiunteDesc = "";
		//
		//			for (RFascicoloSocieta societa : listaFascicoloSocieta) {
		//
		//				if (societa.getTipologiaSocieta().equals(Costanti.SOCIETA_TIPOLOGIA_ADDEBITO)) {
		//					societaAddebitoAggiunteDesc += societa.getSocieta().getNome() + " ";
		//				}
		//			}
		//			tableSocietaAddebito.getCell(1).setText(societaAddebitoAggiunteDesc);
		//		}
		//		else{
		//			tableSocietaAddebito.getCell(1).setText("");
		//		}
		//
		//		// DATA RICHIESTA AUTORIZZAZIONE
		//		XWPFTableRow tableDataRichiestaAutorizzazione = table.createRow();
		//		if(language == 0)
		//			tableDataRichiestaAutorizzazione.getCell(0).setText("Data Richiesta Autorizzazione");
		//		else
		//			tableDataRichiestaAutorizzazione.getCell(0).setText("Request Authorization Date");
		//		Date dataRischiestaAutorizzazione = scheda.getVo().getDataRichiestaAutorScheda();
		//		String formatDataRischiestaAutorizzazione = null;
		//		if(dataRischiestaAutorizzazione!=null) {
		//			if(language == 0)
		//				formatDataRischiestaAutorizzazione = new SimpleDateFormat("dd/MM/yyyy").format(dataRischiestaAutorizzazione);
		//			else
		//				formatDataRischiestaAutorizzazione = new SimpleDateFormat("MM/dd/yyyy").format(dataRischiestaAutorizzazione);
		//		}
		//		else
		//			formatDataRischiestaAutorizzazione = "";
		//
		//		tableDataRichiestaAutorizzazione.getCell(1).setText(formatDataRischiestaAutorizzazione);
		//
		//		// DATA AUTORIZZAZIONE
		//		XWPFTableRow tableDataAutorizzazione = table.createRow();
		//		if(language == 0)
		//			tableDataAutorizzazione.getCell(0).setText("Data Autorizzazione");
		//		else
		//			tableDataAutorizzazione.getCell(0).setText("Authorization Date");
		//		Date dataAutorizzazione = scheda.getVo().getDataAutorizzazione();
		//		String formatDataAutorizzazione = null;
		//		if(dataAutorizzazione!=null) {
		//			if(language == 0)
		//				formatDataAutorizzazione = new SimpleDateFormat("dd/MM/yyyy").format(dataAutorizzazione);
		//			else
		//				formatDataAutorizzazione = new SimpleDateFormat("MM/dd/yyyy").format(dataAutorizzazione);
		//		}
		//		else
		//			formatDataAutorizzazione = "";
		//
		//		tableDataAutorizzazione.getCell(1).setText(formatDataAutorizzazione);

		// TESTO ESPLICATIVO
		XWPFTableRow tableTestoEsplicativo = table.createRow();
		if(language == 0)
			tableTestoEsplicativo.getCell(0).setText("Testo Esplicativo");
		else
			tableTestoEsplicativo.getCell(0).setText("Explanatory Text");
		String testoEsplicativo = scheda.getVo().getTestoEsplicativo() == null ? "" : "" + scheda.getVo().getTestoEsplicativo();


		if(scheda.getVo().getCoperturaAssicurativa() != null || scheda.getVo().getManleva() != null || scheda.getVo().getCommessaDiInvestimento() != null){

			String subInfo = "";

			List<String> info = new ArrayList<String>();

			if(scheda.getVo().getCoperturaAssicurativa() != null){

				if(language == 0)
					info.add("Esiste Copertura Assicurativa ("+ scheda.getVo().getCoperturaAssicurativa().floatValue() +")");
				else
					info.add("Insurance Coverage Exists");
			}

			if(scheda.getVo().getManleva() != null){

				if(language == 0)
					info.add("Esiste Manleva ("+ scheda.getVo().getManleva().floatValue() +")");
				else
					info.add("Manleva Exists");
			}

			if(scheda.getVo().getCommessaDiInvestimento() != null){

				if(language == 0)
					info.add("Esiste Commessa di Investimento ("+ scheda.getVo().getCommessaDiInvestimento().floatValue() +")");
				else
					info.add("Investment Commitment Exists");
			}

			if(info.size() == 1){
				subInfo += info.get(0);
			}else if(info.size() == 2){
				subInfo += info.get(0) + " , " + info.get(1);
			}else if(info.size() == 3){
				subInfo += info.get(0) + " , " + info.get(1) + " , " + info.get(2);
			}

			XWPFTableCell cell = tableTestoEsplicativo.getCell(1);

			XWPFParagraph paragraph = cell.getParagraphs().get(0);
			XWPFRun run1 = paragraph.createRun();
			run1.setText(testoEsplicativo);

			XWPFParagraph paragraph2 = cell.addParagraph();
			XWPFRun run2 = paragraph2.createRun();
			run2.setItalic(true);
			run2.setText(subInfo);
		}
		else{

			tableTestoEsplicativo.getCell(1).setText(testoEsplicativo);
		}

		// RISCHIO SOCCOMBENZA
		XWPFTableRow tableRischioSoccombenza = table.createRow();
		if(language == 0)
			tableRischioSoccombenza.getCell(0).setText("Rischio Soccombenza");
		else
			tableRischioSoccombenza.getCell(0).setText("Losing Risk");
		String rischioSoccombenza = scheda.getVo().getRischioSoccombenza().getDescrizione()==null ? "" : scheda.getVo().getRischioSoccombenza().getDescrizione();
		tableRischioSoccombenza.getCell(1).setText(rischioSoccombenza);

		//		// COPERTURA ASSICURATIVA
		//		XWPFTableRow tableCoperturaAssicurativa = table.createRow();
		//		if(language == 0)
		//			tableCoperturaAssicurativa.getCell(0).setText("Copertura Assicurativa");
		//		else
		//			tableCoperturaAssicurativa.getCell(0).setText("Insurance Coverage");
		//		String coperturaAssicurativa = scheda.getVo().getCoperturaAssicurativa() == null ? "" : "" + scheda.getVo().getCoperturaAssicurativa().floatValue();
		//		tableCoperturaAssicurativa.getCell(1).setText(coperturaAssicurativa);
		//
		//		// MANLEVA
		//		XWPFTableRow tableManleva = table.createRow();
		//		tableManleva.getCell(0).setText("Manleva");
		//		String manleva = scheda.getVo().getManleva() == null ? "" : "" + scheda.getVo().getManleva().floatValue();
		//		tableManleva.getCell(1).setText(manleva);
		//
		//		// COMMESSA DI INVESTIMENTO
		//		XWPFTableRow tableCommessaDiInvestimento = table.createRow();
		//		if(language == 0)
		//			tableCommessaDiInvestimento.getCell(0).setText("Commessa di Investimento");
		//		else
		//			tableCommessaDiInvestimento.getCell(0).setText("Investment Commitment");
		//		String commessaDiInvestimento = scheda.getVo().getCommessaDiInvestimento() == null ? "" : "" + scheda.getVo().getCommessaDiInvestimento().floatValue();
		//		tableCommessaDiInvestimento.getCell(1).setText(commessaDiInvestimento);

		// PASSIVITA STIMATA
		XWPFTableRow tablePassivitaStimata = table.createRow();
		if(language == 0)
			tablePassivitaStimata.getCell(0).setText("Passività Stimata");
		else
			tablePassivitaStimata.getCell(0).setText("Estimated Liabilities");
		String passivitaStimata = scheda.getVo().getPassivitaStimata() == null ? "" : "" + scheda.getVo().getPassivitaStimata().floatValue();
		tablePassivitaStimata.getCell(1).setText(passivitaStimata);

		// MOTIVAZIONE
		XWPFTableRow tableMotivazione = table.createRow();
		if(language == 0)
			tableMotivazione.getCell(0).setText("Motivazione");
		else
			tableMotivazione.getCell(0).setText("Motivation");
		String motivazione = scheda.getVo().getMotivazione() == null ? "" : "" + scheda.getVo().getMotivazione();
		tableMotivazione.getCell(1).setText(motivazione);

		//		// ALLEGATI
		//		XWPFTableRow tableAllegati = table.createRow();
		//		if(language == 0)
		//			tableAllegati.getCell(0).setText("Allegati");
		//		else
		//			tableAllegati.getCell(0).setText("Attachments");
		//
		//		caricaDocumentiProfessionistaEsternoFilenet(scheda, scheda.getVo());
		//		String allegati = "";
		//
		//		if (scheda.getListaAllegatiLegaleEsterno() != null && !scheda.getListaAllegatiLegaleEsterno().isEmpty()) {
		//			List<DocumentoView> documenti = scheda.getListaAllegatiLegaleEsterno();					
		//
		//			for (DocumentoView documento : documenti) {
		//
		//				allegati += documento.getNomeFile();
		//			}
		//		}
		//		tableAllegati.getCell(1).setText(allegati);
	}

	/*private void caricaDocumentiProfessionistaEsternoFilenet(SchedaFondoRischiView view, SchedaFondoRischi vo) throws Throwable {		
		String nomeCartellaScheda = FileNetUtil.getSchedaFondoRischiCartella(vo.getId(), vo.getFascicolo().getDataCreazione(), vo.getFascicolo().getNome(), DateUtil.getDatayyyymmddHHmmss(vo.getDataCreazione().getTime()));

		DocumentaleDAO documentaleDAO = (DocumentaleDAO) SpringUtil.getBean("documentaleDAO");
		Folder cartellaSchedaFondoRischi =  documentaleDAO.leggiCartella(nomeCartellaScheda);

		DocumentSet documenti = cartellaSchedaFondoRischi.get_ContainedDocuments();
		List<DocumentoView> listaDocumenti = new ArrayList<DocumentoView>();
		if( documenti != null ){
			PageIterator it = documenti.pageIterator();
			while(it.nextPage()){
				EngineObjectImpl[] documentiArray = (EngineObjectImpl[]) it.getCurrentPage();
				for( EngineObjectImpl objDocumento : documentiArray ){
					DocumentImpl documento = (DocumentImpl)objDocumento;
					DocumentoView docView = new DocumentoView();
					docView.setNomeFile(documento.get_Name() );
					docView.setUuid(documento.get_Id().toString());
					listaDocumenti.add(docView);
				}
			}
			view.setListaAllegatiLegaleEsterno(listaDocumenti);
		}
	}*/

	private void exportUtentiAlesocr(List<UtenteView> utentiAlesocr, HttpServletResponse respons,String localLang) throws Throwable {
		WriteExcell excell= new WriteExcell();

		String languages =(localLang!=null && !localLang.trim().equals(""))?localLang:"IT";
		String[][] lang=new String[2][7];

		lang[0][0]="LEGALE INTERNO";
		lang[0][1]="UNITA' LEGALE";
		lang[0][2]="RESPONSABILE";
		lang[0][3]="UNITA' LEGALE RESPONSABILE";
		lang[0][4]="PRIMO RIPORTO";
		lang[0][5]="UNITA' LEGALE PRIMO RIPORTO";
		lang[0][6]="GENERAL COUNSEL";

		lang[1][0]="INTERNAL LAWYER";
		lang[1][1]="LEGAL UNIT";
		lang[1][2]="CHIEF";
		lang[1][3]="CHIEF LEGAL UNIT";
		lang[1][4]="FIRST RESPONSIBLE MANAGER";
		lang[1][5]="FIRST RESPONSIBLE MANAGER LEGAL UNIT";
		lang[1][6]="GENERAL COUNSEL";


		int language=0;
		if(languages.equalsIgnoreCase("IT"))
			language=0;
		if(languages.equalsIgnoreCase("EN"))
			language=1;		

		excell.addHeader(lang[language][0], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][1], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][2], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][3], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][4], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][5], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][6], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);


		Utente responsabileTop = utenteDAO.leggiResponsabileTop();

		if(utentiAlesocr != null & !utentiAlesocr.isEmpty()){

			for(UtenteView utente : utentiAlesocr){

				if(!utente.getVo().getMatricolaUtil().equals(responsabileTop.getMatricolaUtil())){

					String matricolaResponsabile = utente.getVo().getMatricolaRespUtil();

					if(matricolaResponsabile != null && !matricolaResponsabile.isEmpty()){

						if(!matricolaResponsabile.equals(responsabileTop.getMatricolaUtil())){

							Utente responsabileDiretto = utenteDAO.leggiUtenteDaMatricola(matricolaResponsabile);

							if(responsabileDiretto != null){

								if(!responsabileDiretto.getMatricolaRespUtil().equals(responsabileTop.getMatricolaUtil())){

									Utente primoRiporto = utenteDAO.leggiUtenteDaMatricola(responsabileDiretto.getMatricolaRespUtil());

									if(primoRiporto != null){

										if(primoRiporto.getMatricolaRespUtil().equals(responsabileTop.getMatricolaUtil())){

											Vector<Object> row = new Vector<Object>();

											row.add(utente.getVo().getNominativoUtil() != null ? utente.getVo().getNominativoUtil() : "");
											row.add(utente.getVo().getDescrUnitaUtil() != null ? utente.getVo().getDescrUnitaUtil() : "");

											row.add(responsabileDiretto.getNominativoUtil() != null ? responsabileDiretto.getNominativoUtil() : "");
											row.add(responsabileDiretto.getDescrUnitaUtil() != null ? responsabileDiretto.getDescrUnitaUtil() : "");

											row.add(primoRiporto.getNominativoUtil() != null ? primoRiporto.getNominativoUtil() : "");
											row.add(primoRiporto.getDescrUnitaUtil() != null ? primoRiporto.getDescrUnitaUtil() : "");

											row.add(responsabileTop.getNominativoUtil()!= null ? responsabileTop.getNominativoUtil() : "");

											excell.addRowBody(row);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		excell.setNomeFile("Report-Utenti.xls");
		excell.createSheet().getCurrentSheet().setDefaultColumnWidth((int) 38);
		excell.write(respons);
	}

	private void generateReportVendor(Map<Long, List<VendorManagementView>> voteMap, HttpServletResponse respons,String localLang) throws Throwable {

		WriteExcell excell= new WriteExcell();
		excell.setNomeFile("VENDOR_"+ DateUtil.formattaDataCompatta(new Date().getTime()) +".xls");
		
		/** SHEET VALORI MEDI **/
		
		String languages =(localLang!=null && !localLang.trim().equals(""))?localLang:"IT";
		String[][] lang=new String[2][6];

		lang[0][0]="PROFESSIONISTA ESTERNO";
		lang[0][1]="SPECIALIZZAZIONE";
		lang[0][2]="NAZIONE";
		lang[0][3]="MEDIA TOTALE";
		lang[0][4]="INCARICHI VALUTATI";
		lang[0][5]="VALUTATORI";

		lang[1][0]="EXTERNAL LAWYER";
		lang[1][1]="SPECIALIZATION";
		lang[1][2]="NATION";
		lang[1][3]="TOTAL MEDIA";
		lang[1][4]="EVALUATED ASSIGNMENTS";
		lang[1][5]="EVALUATORS";

		int language=0;
		if(languages.equalsIgnoreCase("IT"))
			language=0;
		if(languages.equalsIgnoreCase("EN"))
			language=1;		

		excell.addHeader(lang[language][0], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][1], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][2], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][3], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][4], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(lang[language][5], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);


		for(Map.Entry<Long, List<VendorManagementView>> entry : voteMap.entrySet()){

			Long idProfEst = entry.getKey();
			List<VendorManagementView> votazioni = entry.getValue();

			ProfessionistaEsterno profEst = professionistaEsternoDAO.leggi(idProfEst, FetchMode.JOIN, true);

			Vector<Object> row = new Vector<Object>();

			/** PROFESSIONISTA ESTERNO (NOME E COGNOME) **/
			row.add(profEst.getCognomeNome() != null ? profEst.getCognomeNome() : "");

			/** SPECIALIZZAZIONE **/
			String specializzazione = "";
			if(profEst.getRProfEstSpecs() != null && !profEst.getRProfEstSpecs().isEmpty()){

				for(RProfEstSpec relSpec : profEst.getRProfEstSpecs()){
					
					specializzazione += relSpec.getSpecializzazione().getDescrizione() + " ";
				}
			}
			if(specializzazione.isEmpty())
				specializzazione = "Altro";
			
			row.add(specializzazione);

			/** NAZIONE **/
			String nazione = "";
			if(profEst.getRProfessionistaNaziones() != null && !profEst.getRProfessionistaNaziones().isEmpty()){

				for(RProfessionistaNazione relNaz : profEst.getRProfessionistaNaziones()){
					
					nazione += relNaz.getNazione().getDescrizione();
				}
			}
			if(nazione.isEmpty())
				nazione = "Italia";
			
			row.add(nazione);

			/** MEDIA TOTALE **/
			BigDecimal mediaTotale = new BigDecimal(0);

			for(VendorManagementView vendorManagementView : votazioni){

				mediaTotale = mediaTotale.add(vendorManagementView.getVo().getValutazione());
			}
			mediaTotale = mediaTotale.divide(new BigDecimal(votazioni.size()), RoundingMode.HALF_UP);

			row.add(mediaTotale.toEngineeringString());

			/** NUMERO INCARICHI **/
			row.add(votazioni.size() + "");

			/** VALUTATORI **/
			String valutatori = "";

			for(VendorManagementView vendorManagementView : votazioni){

				String owner = vendorManagementView.getVo().getIncarico().getFascicolo().getLegaleInterno();

				if(valutatori.isEmpty())
					valutatori += owner + " ";
				else{
					if(!valutatori.contains(owner)){
						valutatori += owner + " ";
					}
				}
			}
			
			if(!valutatori.isEmpty()){
				
				String nominativi = "";
				
				List<String> nominativiValutatori = new ArrayList<String>();
				
				StringTokenizer strt = new StringTokenizer(valutatori, " ");
				
				while(strt.hasMoreTokens()){
					
					String userId = strt.nextToken();
					Utente utente = utenteDAO.leggiUtenteDaUserId(userId);
					
					String nomeCognome = "";
					
					if(utente != null){
						
						if(utente.getNominativoUtil() != null && !utente.getNominativoUtil().isEmpty()){
							
							nomeCognome = utente.getNominativoUtil();
						}
					}
					
					if(!nomeCognome.isEmpty())
						nominativiValutatori.add(nomeCognome);
				}
				
				if(!nominativiValutatori.isEmpty()){
					
					if(nominativiValutatori.size() == 1){
						
						String nominativo = nominativiValutatori.get(0);
						if(nominativo.indexOf(",") > -1)
							nominativo = nominativo.replace(",", "");
						
						nominativi += nominativo;
					}else{
						
						for(String nominativo : nominativiValutatori){
							
							if(nominativo.indexOf(",") > -1)
								nominativo = nominativo.replace(",", "");
							
							nominativi += nominativo + ", ";
						}
					}
				}
				row.add(nominativi);
			}
			else{
				
				row.add(valutatori);
			}
			excell.addRowBody(row);
		}
		excell.createSheet("Valori Medi").getCurrentSheet().setDefaultColumnWidth((int) 38);
		excell.addSheet("Dettaglio Votazioni");
		excell.getCurrentSheet().setDefaultColumnWidth((int) 38);
		
		/** SHEET DETTAGLIO VOTAZIONI **/
		
		String languagesII =(localLang!=null && !localLang.trim().equals(""))?localLang:"IT";
		String[][] langII=new String[2][15];

		langII[0][0]="PROFESSIONISTA ESTERNO";
		langII[0][1]="SPECIALIZZAZIONE";
		langII[0][2]="NAZIONE";
		langII[0][3]="NOTE";
		langII[0][4]="NOME INCARICO";
		langII[0][5]="FASCICOLO";
		langII[0][6]="VALUTATORE";
		langII[0][7]="VALUTAZIONE";
		langII[0][8]="VALUTAZIONE CAPACITA'";
		langII[0][9]="VALUTAZIONE COMPETENZA";
		langII[0][10]="VALUTAZIONE COSTI";
		langII[0][11]="VALUTAZIONE TEMPI";
		langII[0][12]="VALUTAZIONE FLESSIBILITA'";
		langII[0][13]="VALUTAZIONE REPERIBILITA'";
		langII[0][14]="VALUTAZIONE AUTOREVOLEZZA";

		langII[1][0]="EXTERNAL LAWYER";
		langII[1][1]="SPECIALIZATION";
		langII[1][2]="NATION";
		langII[1][3]="NOTES";
		langII[1][4]="ASSIGNMENT NAME";
		langII[1][5]="DOSSIER";
		langII[1][6]="EVALUATOR";
		langII[1][7]="EVALUATION";
		langII[1][8]="CAPACITY EVALUATION";
		langII[1][9]="COMPETENCE EVALUATION";
		langII[1][10]="COSTS EVALUATION";
		langII[1][11]="TIME EVALUATION";
		langII[1][12]="FLEXIBILITY EVALUATION";
		langII[1][13]="AVAILABILITY EVALUATION";
		langII[1][14]="AUTHORITATIVENESS EVALUATION";

		int languageII=0;
		if(languagesII.equalsIgnoreCase("IT"))
			languageII=0;
		if(languagesII.equalsIgnoreCase("EN"))
			languageII=1;		

		excell.addHeader(langII[languageII][0], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(langII[languageII][1], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(langII[languageII][2], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(langII[languageII][3], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(langII[languageII][4], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(langII[languageII][5], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(langII[languageII][6], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(langII[languageII][7], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(langII[languageII][8], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(langII[languageII][9], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(langII[languageII][10], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(langII[languageII][11], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(langII[languageII][12], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(langII[languageII][13], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);
		excell.addHeader(langII[languageII][14], WriteExcell.TYPECELL_STRING,excell.CSTYLE_STRING);

		for(Map.Entry<Long, List<VendorManagementView>> entry : voteMap.entrySet()){

			Long idProfEst = entry.getKey();
			List<VendorManagementView> votazioni = entry.getValue();

			ProfessionistaEsterno profEst = professionistaEsternoDAO.leggi(idProfEst, FetchMode.JOIN, true);

			for(VendorManagementView vendorManagementView : votazioni){
				
				Vector<Object> row = new Vector<Object>();
				
				/** PROFESSIONISTA ESTERNO (NOME E COGNOME) **/
				row.add(profEst.getCognomeNome() != null ? profEst.getCognomeNome() : "");
				
				/** SPECIALIZZAZIONE **/
				row.add(vendorManagementView.getVo().getSpecializzazione().getDescrizione() != null ? vendorManagementView.getVo().getSpecializzazione().getDescrizione() : "");
				
				/** NAZIONE **/
				row.add(vendorManagementView.getVo().getNazione().getDescrizione() != null ? vendorManagementView.getVo().getNazione().getDescrizione() : "");
				
				/** NOTE **/
				row.add(vendorManagementView.getVo().getNote() != null ? vendorManagementView.getVo().getNote() : "");
				
				/**NOME INCARICO**/
				row.add(vendorManagementView.getVo().getIncarico().getNomeIncarico() != null ? vendorManagementView.getVo().getIncarico().getNomeIncarico() : "");
				
				/** FASCICOLO **/
				row.add(vendorManagementView.getVo().getIncarico().getFascicolo().getNome() != null ? vendorManagementView.getVo().getIncarico().getFascicolo().getNome() : "");

				/** VALUTATORE **/
				String valutatore = "";
				if(vendorManagementView.getVo().getIncarico().getFascicolo().getLegaleInterno() != null){
					
					Utente utente = utenteDAO.leggiUtenteDaUserId(vendorManagementView.getVo().getIncarico().getFascicolo().getLegaleInterno());
					
					if(utente != null){
						
						if(utente.getNominativoUtil() != null && !utente.getNominativoUtil().isEmpty()){
							valutatore = utente.getNominativoUtil();
						}
					}
				}
				row.add(valutatore);
				
				/** VALUTAZIONE **/
				row.add(vendorManagementView.getVo().getValutazione() != null ? vendorManagementView.getVo().getValutazione().toEngineeringString() : "");
				
				/** VALUTAZIONE CAPACITA **/
				row.add(vendorManagementView.getVo().getValutazioneCapacita() != null ? vendorManagementView.getVo().getValutazioneCapacita().toEngineeringString() : "");
				
				/** VALUTAZIONE COMPETENZA **/
				row.add(vendorManagementView.getVo().getValutazioneCompetenze() != null ? vendorManagementView.getVo().getValutazioneCompetenze().toEngineeringString() : "");
				
				/** VALUTAZIONE COSTI **/
				row.add(vendorManagementView.getVo().getValutazioneCosti() != null ? vendorManagementView.getVo().getValutazioneCosti().toEngineeringString() : "");
				
				/** VALUTAZIONE TEMPI **/
				row.add(vendorManagementView.getVo().getValutazioneTempi() != null ? vendorManagementView.getVo().getValutazioneTempi().toEngineeringString() : "");
				
				/** VALUTAZIONE FLESSIBILITA **/
				row.add(vendorManagementView.getVo().getValutazioneFlessibilita() != null ? vendorManagementView.getVo().getValutazioneFlessibilita().toEngineeringString() : "");
				
				/** VALUTAZIONE REPERIBILITA **/
				row.add(vendorManagementView.getVo().getValutazioneReperibilita() != null ? vendorManagementView.getVo().getValutazioneReperibilita().toEngineeringString() : "");
				
				/** VALUTAZIONE AUTOREVOLEZZA **/
				row.add(vendorManagementView.getVo().getValutazioneAutorevolezza() != null ? vendorManagementView.getVo().getValutazioneAutorevolezza().toEngineeringString() : "");

				excell.addRowBody(row);
			}
		}
		excell.write(respons);
	}


	@Override
	public void exportExcellAtto(Object[] params, HttpServletResponse respons,String localLang) throws Throwable {

		List<Atto> ltsAtto= attoDao.reportingAtti(params);

		exportAttiXls(ltsAtto,params[6]!=null?params[6].toString():null, respons,localLang);
	}



	@Override
	public void exportExcellFascicoli(Object[] params, HttpServletResponse respons,String localLang) throws Throwable {

		//List<Fascicolo> fascicolos=fascicoloDAO.reportingFascicoli(params);
		List<RFascicoloSocieta> reportingFascicol=fascicoloDAO.reportingFascicoliRFS(params);
		//	exportFascicoloXls(fascicolos, respons,localLang); 
		exportFascicoloXlsRFS(reportingFascicol, respons,localLang); 
	}

	@Override
	public void exportExcellOrganiSociali(HttpServletResponse respons,String localLang) throws Throwable{
		
		List<OrganoSociale> reportingOrganiSociali= organoSocialeDAO.esporta();
		exportOrganiSocialiXLS(reportingOrganiSociali, respons, localLang);
	}
	@Override
	public void exportExcellIncarichi(String[] params, HttpServletResponse respons,String localLang) throws Throwable {

		List<Incarico> incaricos=incararicoDAO.leggiIncarichiReporting(params);

		exportIncarichiXls(incaricos, respons,localLang);
	}

	@Override
	public void exportExcellProforma(Object[] params, HttpServletResponse respons,String localLang) throws Throwable {

		List<RIncaricoProformaSocieta> incaricoProformaSocietas=incararicoDAO.leggiRIncaricoProformaSocieta("A", params);
		exportProformaXls(incaricoProformaSocietas, respons,localLang);
	}

	@Override
	public boolean exportExcellStanziamenti(int annoFinanziario, HttpServletResponse respons, String localLang)
			throws Throwable {

		List<Stanziamenti> stanziamentis =incararicoDAO.leggiIncarichiStanziamenti(annoFinanziario);
		if(stanziamentis!=null && stanziamentis.size()>0){
			exportStanziamentiXls(stanziamentis, respons, localLang,annoFinanziario);
			return true;
		}
		return false;
	}


	@Override
	public String generaPFRFase1(List<SchedaFondoRischiView> lista, String trimestre, HttpServletResponse respons, String localLang, int delay)
			throws Throwable {

		if(lista!=null && lista.size()>0){
			exportSchedeFondoRischiPDF(lista, trimestre, respons, localLang, delay);
		}
		return null;
	}

	@Override
	public String generaPFRFase2(List<SchedaFondoRischiView> lista, String trimestre, HttpServletResponse respons, String localLang, int delay)
			throws Throwable {

		if(lista!=null && lista.size()>0){
			exportDifferenzeSchedeFondoRischiPDF(lista, trimestre, respons, localLang, delay);
		}
		return null;
	}


	protected Map<String, List<SchedaFondoRischiView>> raggruppaPerPrimoRiporto(List<SchedaFondoRischiView> listaSchede){
		Map<String, List<SchedaFondoRischiView>> raggruppamentoPrimoRiporto = new LinkedHashMap<String, List<SchedaFondoRischiView>>();

		try {

			if(listaSchede != null && !listaSchede.isEmpty()){

				for(SchedaFondoRischiView scheda : listaSchede){

					if(scheda.getVo().getFascicolo() != null){

						String userIdLegal = scheda.getVo().getFascicolo().getLegaleInterno();

						if(userIdLegal != null && !userIdLegal.isEmpty()){

							Utente legaleInterno = utenteDAO.leggiUtenteDaUserId(userIdLegal);
							Utente responsabileTop = utenteDAO.leggiResponsabileTop();

							/** Nel caso in cui il legale interno è il responsabile TOP (non dovremme mai essere possibile) **/
							if(legaleInterno.getMatricolaUtil().equals(responsabileTop.getMatricolaUtil())){

								if(raggruppamentoPrimoRiporto.get(legaleInterno.getMatricolaUtil()) != null){

									raggruppamentoPrimoRiporto.get(legaleInterno.getMatricolaUtil()).add(scheda);
								}
								else{
									List<SchedaFondoRischiView> lista = new ArrayList<SchedaFondoRischiView>();
									lista.add(scheda);
									raggruppamentoPrimoRiporto.put(legaleInterno.getMatricolaUtil(), lista);
								}
							}
							/** Nel caso in cui il legale interno è il primo riporto **/
							else if(legaleInterno.getMatricolaRespUtil().equals(responsabileTop.getMatricolaUtil())){

								if(raggruppamentoPrimoRiporto.get(legaleInterno.getMatricolaUtil()) != null){

									raggruppamentoPrimoRiporto.get(legaleInterno.getMatricolaUtil()).add(scheda);
								}
								else{
									List<SchedaFondoRischiView> lista = new ArrayList<SchedaFondoRischiView>();
									lista.add(scheda);
									raggruppamentoPrimoRiporto.put(legaleInterno.getMatricolaUtil(), lista);
								}
							}
							/** Nel caso in cui il legale interno ha come responsabile il primo riporto o un secondo riporto **/
							else{ 

								Utente responsabileLegaleInterno = utenteDAO.leggiUtenteDaMatricola(legaleInterno.getMatricolaRespUtil());

								/** Caso in cui il legale interno è il secondo riporto  **/
								if(responsabileLegaleInterno.getMatricolaRespUtil().equals(responsabileTop.getMatricolaUtil())){

									if(raggruppamentoPrimoRiporto.get(responsabileLegaleInterno.getMatricolaUtil()) != null){

										raggruppamentoPrimoRiporto.get(responsabileLegaleInterno.getMatricolaUtil()).add(scheda);
									}
									else{
										List<SchedaFondoRischiView> lista = new ArrayList<SchedaFondoRischiView>();
										lista.add(scheda);
										raggruppamentoPrimoRiporto.put(responsabileLegaleInterno.getMatricolaUtil(), lista);
									}
								}

								/** Caso in cui è un semplice legale **/
								else{

									Utente responsabileDelResponsabileLegaleInterno = utenteDAO.leggiUtenteDaMatricola(responsabileLegaleInterno.getMatricolaRespUtil());

									if(responsabileDelResponsabileLegaleInterno.getMatricolaRespUtil().equals(responsabileTop.getMatricolaUtil())){

										if(raggruppamentoPrimoRiporto.get(responsabileDelResponsabileLegaleInterno.getMatricolaUtil()) != null){

											raggruppamentoPrimoRiporto.get(responsabileDelResponsabileLegaleInterno.getMatricolaUtil()).add(scheda);
										}
										else{
											List<SchedaFondoRischiView> lista = new ArrayList<SchedaFondoRischiView>();
											lista.add(scheda);
											raggruppamentoPrimoRiporto.put(responsabileDelResponsabileLegaleInterno.getMatricolaUtil(), lista);
										}
									}
								}
							}
						}
					}
				}
			}
		} 
		catch (Throwable e) {
			e.printStackTrace();
		}
		return raggruppamentoPrimoRiporto;
	}
	
	protected void aggiungiARaggruppaPerPrimoRiporto(Map<String, List<SchedaFondoRischiView>> raggruppamentoPrimoRiporto,List<SchedaFondoRischiView> listaSchede){
		try {

			if(listaSchede != null && !listaSchede.isEmpty()){

				for(SchedaFondoRischiView scheda : listaSchede){

					if(scheda.getVo().getFascicolo() != null){

						String userIdLegal = scheda.getVo().getFascicolo().getLegaleInterno();

						if(userIdLegal != null && !userIdLegal.isEmpty()){

							Utente legaleInterno = utenteDAO.leggiUtenteDaUserId(userIdLegal);
							Utente responsabileTop = utenteDAO.leggiResponsabileTop();

							/** Nel caso in cui il legale interno è il responsabile TOP (non dovremme mai essere possibile) **/
							if(legaleInterno.getMatricolaUtil().equals(responsabileTop.getMatricolaUtil())){

								if(raggruppamentoPrimoRiporto.get(legaleInterno.getMatricolaUtil()) != null){

									raggruppamentoPrimoRiporto.get(legaleInterno.getMatricolaUtil()).add(scheda);
								}
								else{
									List<SchedaFondoRischiView> lista = new ArrayList<SchedaFondoRischiView>();
									lista.add(scheda);
									raggruppamentoPrimoRiporto.put(legaleInterno.getMatricolaUtil(), lista);
								}
							}
							/** Nel caso in cui il legale interno è il primo riporto **/
							else if(legaleInterno.getMatricolaRespUtil().equals(responsabileTop.getMatricolaUtil())){

								if(raggruppamentoPrimoRiporto.get(legaleInterno.getMatricolaUtil()) != null){

									raggruppamentoPrimoRiporto.get(legaleInterno.getMatricolaUtil()).add(scheda);
								}
								else{
									List<SchedaFondoRischiView> lista = new ArrayList<SchedaFondoRischiView>();
									lista.add(scheda);
									raggruppamentoPrimoRiporto.put(legaleInterno.getMatricolaUtil(), lista);
								}
							}
							/** Nel caso in cui il legale interno ha come responsabile il primo riporto o un secondo riporto **/
							else{ 

								Utente responsabileLegaleInterno = utenteDAO.leggiUtenteDaMatricola(legaleInterno.getMatricolaRespUtil());

								/** Caso in cui il legale interno è il secondo riporto  **/
								if(responsabileLegaleInterno.getMatricolaRespUtil().equals(responsabileTop.getMatricolaUtil())){

									if(raggruppamentoPrimoRiporto.get(responsabileLegaleInterno.getMatricolaUtil()) != null){

										raggruppamentoPrimoRiporto.get(responsabileLegaleInterno.getMatricolaUtil()).add(scheda);
									}
									else{
										List<SchedaFondoRischiView> lista = new ArrayList<SchedaFondoRischiView>();
										lista.add(scheda);
										raggruppamentoPrimoRiporto.put(responsabileLegaleInterno.getMatricolaUtil(), lista);
									}
								}

								/** Caso in cui è un semplice legale **/
								else{

									Utente responsabileDelResponsabileLegaleInterno = utenteDAO.leggiUtenteDaMatricola(responsabileLegaleInterno.getMatricolaRespUtil());

									if(responsabileDelResponsabileLegaleInterno.getMatricolaRespUtil().equals(responsabileTop.getMatricolaUtil())){

										if(raggruppamentoPrimoRiporto.get(responsabileDelResponsabileLegaleInterno.getMatricolaUtil()) != null){

											raggruppamentoPrimoRiporto.get(responsabileDelResponsabileLegaleInterno.getMatricolaUtil()).add(scheda);
										}
										else{
											List<SchedaFondoRischiView> lista = new ArrayList<SchedaFondoRischiView>();
											lista.add(scheda);
											raggruppamentoPrimoRiporto.put(responsabileDelResponsabileLegaleInterno.getMatricolaUtil(), lista);
										}
									}
								}
							}
						}
					}
				}
			}
		} 
		catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	public String exportReportAlesocr(List<UtenteView> utentiAlesocr, HttpServletResponse respons,String localLang) throws Throwable{

		if(utentiAlesocr!=null && !utentiAlesocr.isEmpty()){
			exportUtentiAlesocr(utentiAlesocr, respons, localLang);
		}
		return null;
	}

	@Override
	public String generaReportVendor(Map<Long, List<VendorManagementView>> voteMap, HttpServletResponse respons,String localLang) throws Throwable{

		if(voteMap!=null && !voteMap.isEmpty()){
			generateReportVendor(voteMap, respons, localLang);
		}
		return null;
	}


	@Override
	protected Class<Fascicolo> leggiClassVO() {
		return null;
	}

	@Override
	protected Class<FascicoloView> leggiClassView() {
		return null;
	}
}