package eng.la.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.xml.ws.RequestWrapper;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.castor.core.util.Base64Decoder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
//@@ import com.filenet.api.core.Folder;
import it.snam.ned.libs.dds.dtos.v2.folder.Folder;
import eng.la.business.AnagraficaStatiTipiService;
import eng.la.business.ArchivioProtocolloService;
import eng.la.business.AttoService;
import eng.la.business.AutorizzazioneService;
import eng.la.business.EventoService;
import eng.la.business.FascicoloService;
import eng.la.business.IncaricoService;
import eng.la.business.LivelloAttribuzioniIIService;
import eng.la.business.LivelloAttribuzioniIService;
import eng.la.business.ProcureService;
import eng.la.business.ProfessionistaEsternoService;
import eng.la.business.ProformaService;
import eng.la.business.RepertorioPoteriService;
import eng.la.business.RepertorioStandardService;
import eng.la.business.RicercaService;
import eng.la.business.ScadenzaService;
import eng.la.business.SettoreGiuridicoService;
import eng.la.business.TipoProcureService;
import eng.la.business.TipologiaFascicoloService;
import eng.la.business.UtenteService;
import eng.la.business.mail.EmailNotificationService;
import eng.la.model.Acconti;
import eng.la.model.ArchivioProtocollo;
import eng.la.model.Atto;
import eng.la.model.Autorizzazione;
import eng.la.model.Bonus;
import eng.la.model.CategoriaTessere;
import eng.la.model.Evento;
import eng.la.model.Fascicolo;
import eng.la.model.Incarico;
import eng.la.model.LetteraIncarico;
import eng.la.model.LivelloAttribuzioniI;
import eng.la.model.LivelloAttribuzioniII;
import eng.la.model.PosizioneOrganizzativa;
import eng.la.model.Procure;
import eng.la.model.Proforma;
import eng.la.model.RepertorioPoteri;
import eng.la.model.RepertorioStandard;
import eng.la.model.Scadenza;
import eng.la.model.Societa;
import eng.la.model.SubCategoriaTessere;
import eng.la.model.TipoProcure;
import eng.la.model.Utente;
import eng.la.model.custom.AllegatoMultipartFile;
import eng.la.model.custom.Numeri;
import eng.la.model.rest.AllegatoRest;
import eng.la.model.rest.AttoRest;
import eng.la.model.rest.CodiceDescrizioneBean;
import eng.la.model.rest.CreaFascicoloIncaricoRest;
import eng.la.model.rest.FascicoloRest;
import eng.la.model.rest.FascicoloRestPerAvanzamento;
import eng.la.model.rest.FascicoloRestPerAvanzamentoTot;
import eng.la.model.rest.LetteraIncaricoRest;
import eng.la.model.rest.ProcureConferiteRest;
import eng.la.model.rest.ProcureSpecialiRest;
import eng.la.model.rest.ProcureStandardRest;
import eng.la.model.rest.ProformaBonificaRest;
import eng.la.model.rest.RepertorioPoteriBonificaRest;
import eng.la.model.rest.RicercaAttoRest;
import eng.la.model.rest.RicercaFascicoloRest;
import eng.la.model.rest.RisultatoOperazioneLettera;
import eng.la.model.rest.RisultatoOperazioneProforma;
import eng.la.model.rest.RisultatoOperazioneRest;
import eng.la.model.rest.RisultatoOperazioneSentenze;
import eng.la.model.rest.SentenzeBonificaRest;
import eng.la.model.view.AttoView;
import eng.la.model.view.AutorizzazioneView;
import eng.la.model.view.DocumentoView;
import eng.la.model.view.EventoView;
import eng.la.model.view.FascicoloView;
import eng.la.model.view.IncaricoView;
import eng.la.model.view.ProcureView;
import eng.la.model.view.ProfessionistaEsternoView;
import eng.la.model.view.ProformaView;
import eng.la.model.view.ProtocolloView;
import eng.la.model.view.RepertorioPoteriView;
import eng.la.model.view.RepertorioStandardView;
import eng.la.model.view.ScadenzaView;
import eng.la.model.view.TipoProcureView;
import eng.la.model.view.UtenteView;
import eng.la.persistence.CostantiDAO;
import eng.la.persistence.DocumentaleDAO;
import eng.la.util.CurrentSessionUtil;
import eng.la.util.DateUtil;
import eng.la.util.ListaPaginata;
import eng.la.util.MimeTypeUtil;
import eng.la.util.SpringUtil;
import eng.la.util.costants.Costanti;
import eng.la.util.filenet.model.CostantiFileNet;
import eng.la.util.filenet.model.FileNetClassNames;
import eng.la.util.filenet.model.FileNetUtil;
import eng.la.util.va.csrf.HTMLActionSupport;
import eng.la.model.mail.JSONMail;

@Controller("restController")
public class RestController {

	@Autowired
	private FascicoloService service;

	@Autowired
	private AttoService attoService;

	@Autowired
	private ProformaService proformaService;

	@Autowired
	private EmailNotificationService emailNotificationService;

	@Autowired
	private IncaricoService incaricoService;

	@Autowired
	private TipologiaFascicoloService tipologiaFascicoloService;

	@Autowired
	private SettoreGiuridicoService settoreGiuridicoService;

	@Autowired
	private AnagraficaStatiTipiService anagraficaStatiTipiService;

	@Autowired
	private EventoService eventoService;

	@Autowired
	private ScadenzaService scadenzaService;

	@Autowired
	private ArchivioProtocolloService archivioProtocolloService;

	@Autowired
	private RicercaService ricercaService;

	@Autowired
	private FascicoloService fascicoloService;

	@Autowired
	private AutorizzazioneService autorizzazioneService;

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private ProfessionistaEsternoService professionistaEsternoService;

	@Autowired
	private RepertorioPoteriService repertorioPoteriService;

	@Autowired
	private RepertorioStandardService repertorioStandardService;

	@Autowired
	private TipoProcureService tipoProcureService;

	@Autowired
	private LivelloAttribuzioniIService livelloAttribuzioniIService;

	@Autowired
	private LivelloAttribuzioniIIService livelloAttribuzioniIIService;

	@Autowired
	private ProcureService procureService;

	private static final org.apache.log4j.Logger logger = Logger.getLogger(RestController.class);

	public static File convert(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	@Async
	@RequestMapping(value = "/rest/repertorioStandard", method = RequestMethod.POST, consumes = {
			"multipart/form-data" })
	public @ResponseBody List<RisultatoOperazioneRest> repertorioStandard(
			@RequestParam(value = "jsonFile", required = false) MultipartFile jsonFile,
			@RequestParam(value = "zipFile", required = false) MultipartFile zipFile,
			MultipartHttpServletRequest request, Model model) {

		String methodName = "repertorioStandard() ";
		List<RisultatoOperazioneRest> risultato = new ArrayList<RisultatoOperazioneRest>();
		logger.debug(methodName + " begin: " + System.currentTimeMillis());
		logger.debug(methodName
				+ " /**************************************************INIZIO PROCEDURA REPERTORIO STANDARD******************************************************/");
		ObjectMapper mapper = new ObjectMapper();
		Map<String, ProcureStandardRest> docObject = new HashMap<String, ProcureStandardRest>();
		List<CodiceDescrizioneBean> beansPosizioniOrganizzative = repertorioStandardService
				.leggiListaPosizioneOrganizzativa(Locale.ITALY);
		List<CodiceDescrizioneBean> beansPrimoLivelloAttr = repertorioStandardService
				.leggiListaPrimoLivelloAttribuzioni(Locale.ITALY);
		List<CodiceDescrizioneBean> beansSecondoLivelloAttr = repertorioStandardService
				.leggiListaSecondoLivelloAttribuzioni(Locale.ITALY);
		List<UtenteView> beansUtenti = null;
		try {
			beansUtenti = utenteService.leggiUtenti(true);
		} catch (Throwable e1) {
			e1.printStackTrace();
		}
		List<ProcureStandardRest> poteri = null;
		Map<String, CodiceDescrizioneBean> mappaPosizioniOrganizzative = new HashMap<String, CodiceDescrizioneBean>();
		Map<String, CodiceDescrizioneBean> mappaPrimoLivello = new HashMap<String, CodiceDescrizioneBean>();
		Map<String, CodiceDescrizioneBean> mappaSecondoLivello = new HashMap<String, CodiceDescrizioneBean>();
		Map<String, Utente> utView = new HashMap<String, Utente>();

		if (beansPosizioniOrganizzative != null && beansPosizioniOrganizzative.size() > 0) {
			for (CodiceDescrizioneBean codiceDescrizioneBean : beansPosizioniOrganizzative) {
				mappaPosizioniOrganizzative.put(codiceDescrizioneBean.getDescrizione().trim(), codiceDescrizioneBean);
			}
			logger.debug(methodName + " inizializzo posizioni organizzative caricate: " + mappaPosizioniOrganizzative.toString());
		}

		if (beansPrimoLivelloAttr != null && beansPrimoLivelloAttr.size() > 0) {
			for (CodiceDescrizioneBean codiceDescrizioneBean : beansPrimoLivelloAttr) {
				mappaPrimoLivello.put(codiceDescrizioneBean.getDescrizione().trim(), codiceDescrizioneBean);
			}
			logger.debug(methodName + " inizializzo primo livello attribuzioni caricate: " + mappaPrimoLivello.toString());
		}

		if (beansSecondoLivelloAttr != null && beansSecondoLivelloAttr.size() > 0) {
			for (CodiceDescrizioneBean codiceDescrizioneBean : beansSecondoLivelloAttr) {
				mappaSecondoLivello.put(codiceDescrizioneBean.getDescrizione().trim(), codiceDescrizioneBean);
			}
			logger.debug(methodName + " inizializzo secondo livello attribuzione caricate: " + mappaSecondoLivello.toString());
		}

		if (beansUtenti != null && beansUtenti.size() > 0) {
			for (UtenteView ut : beansUtenti) {
				utView.put(ut.getVo().getNominativoUtil(), ut.getVo());
			}
			logger.debug(methodName + " inizializzo utenti caricati: " + utView.toString());
		}

		try {
			poteri = mapper.readValue(jsonFile.getInputStream(), new TypeReference<List<ProcureStandardRest>>() {
			});
			logger.debug(methodName + " JSON Letto: " + poteri.toString());
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Iterator<ProcureStandardRest> iterator = poteri.iterator(); iterator.hasNext();) {
			ProcureStandardRest repertorioPoteriBonificaRest = (ProcureStandardRest) iterator.next();
			docObject.put(repertorioPoteriBonificaRest.getNome(), repertorioPoteriBonificaRest);
		}

		ZipFile zipFileE = null;
		try {
			zipFileE = new ZipFile(convert(zipFile), Charset.forName("Cp437"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		Enumeration<? extends ZipEntry> entries = zipFileE.entries();

		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();

			if (!entry.isDirectory()) {

				InputStream stream;
				ProcureStandardRest standardRest = null;
				try {
					stream = zipFileE.getInputStream(entry);
					logger.debug(methodName + " /-----------*INIZIO FILE: " + entry.getName() + "*------------/");
					byte[] bytes = IOUtils.toByteArray(stream);

					File fileTmp = File.createTempFile("allegatoGenerico", "___" + entry.getName().split("/")[1]);
					FileUtils.writeByteArrayToFile(fileTmp, bytes);

					RepertorioStandardView repertorioView = new RepertorioStandardView();

					RepertorioStandard repertorio = new RepertorioStandard();
					standardRest = docObject.get(entry.getName().split("/")[1]);
					logger.debug(methodName + " /-----------RECORD RECUPERATO: " + standardRest.toString()
							+ " ------------/");
					repertorio.setDataCreazione(DateUtil.toDate("dd/MM/yyyy HH:mm", standardRest.getDataCreazione()));
					repertorio.setDataModifica(DateUtil.toDate("dd/MM/yyyy HH:mm", standardRest.getDataModifica()));
					repertorio.setLingua("IT");
					repertorio.setNome(standardRest.getNome());
					if (standardRest.getNote() != null) {
						repertorio.setNota(standardRest.getNote());
					} else {
						repertorio.setNota("");
					}

					/**
					 * Blocco per Nome Posizione
					 */
					String nomePosizione = standardRest.getPosizione().trim().toUpperCase();
					if (nomePosizione != null && nomePosizione.contains(";")) {
						nomePosizione = nomePosizione.split(";")[0];
					}
					CodiceDescrizioneBean posizioneOrganizzativa = mappaPosizioniOrganizzative.get(nomePosizione);

					if (posizioneOrganizzativa != null) {
						PosizioneOrganizzativa po = new PosizioneOrganizzativa(posizioneOrganizzativa.getId());
						repertorio.setPosizioneOrganizzativa(po);
						logger.debug(methodName + "/-----------POSIZIONE ORGANIZZATIVA TROVATA: " + po.toString()
								+ " ------------/");
					} else {
						logger.warn(methodName + "/-----------POSIZIONE ORGANIZZATIVA NON TROVATA: " + nomePosizione
								+ " ------------/");
						if (nomePosizione != null && !nomePosizione.equalsIgnoreCase("")) {
							logger.warn(methodName + "/-----------ALERT - POSIZIONE NON TROVATA NEL DB!: "
									+ standardRest.getPosizione() + " ------------/");
						}
					}

					/**
					 * Blocco per primo livello
					 */
					String primoLivello = standardRest.getPrimoLivello().trim().toUpperCase();
					if (primoLivello != null && primoLivello.contains(";")) {
						primoLivello = primoLivello.split(";")[0];
					}
					CodiceDescrizioneBean primoLivelloTmp = mappaPrimoLivello.get(primoLivello);

					if (primoLivelloTmp != null) {
						repertorio.setPrimoLivelloAttribuzioni(
								repertorioStandardService.findPrimoLivelloByPk(primoLivelloTmp.getId()));
						logger.debug(
								methodName + "/-----------PRIMO LIVELLO ATTRIBUZIONI: " + primoLivelloTmp.toString());
					} else {
						logger.warn(methodName + "/-----------PRIMO LIVELLO ATTRIBUZIONI NON TROVATA: " + primoLivello
								+ " ------------/");
						if (primoLivello != null && !primoLivello.equalsIgnoreCase("")) {
							logger.warn(methodName + "/-----------ALERT - PRIMO LIVELLO NON TROVATA NEL DB!: "
									+ standardRest.getPrimoLivello() + " ------------/");
						}
					}

					/**
					 * Blocco per secondo livello
					 */
					String secondoLivello = standardRest.getSecondoLivello().trim().toUpperCase();
					if (secondoLivello != null && secondoLivello.contains(";")) {
						secondoLivello = secondoLivello.split(";")[0];
					}
					CodiceDescrizioneBean secondoLivelloTmp = mappaSecondoLivello.get(secondoLivello);

					if (secondoLivelloTmp != null) {
						repertorio.setSecondoLivelloAttribuzioni(
								repertorioStandardService.findSecondoLivelloByPk(secondoLivelloTmp.getId()));
						logger.debug(methodName + "/-----------SECONDO LIVELLO ATTRIBUZIONI: "
								+ secondoLivelloTmp.toString() + " ------------/");
					} else {
						logger.warn(methodName + "/-----------SECONDO LIVELLO ATTRIBUZIONI NON TROVATA: "
								+ secondoLivello + " ------------/");
						// throw new RuntimeException();
						if (secondoLivello != null && !secondoLivello.equalsIgnoreCase("")) {
							logger.warn(methodName + "/-----------ALERT - SECONDO LIVELLO NON TROVATA NEL DB!: "
									+ standardRest.getSecondoLivello() + " ------------/");
						}
					}

					repertorio.setSocieta(standardRest.getSocieta().trim());
					repertorio.setUtente(utenteService.findUtenteByNominativo(standardRest.getAutoreUltimaModifica()));

					if (repertorio.getUtente() == null) {
						logger.warn(methodName + "/-----------UTENTE NON TROVATO NEL DB!:  "
								+ standardRest.getAutoreUltimaModifica() + " ------------/");
					}

					repertorioView.setVo(repertorio);

					DocumentoView docView = new DocumentoView();

					docView.setFile(fileTmp);
					docView.setNomeFile(entry.getName().split("/")[1]);
					docView.setUuid("55");
					docView.setContentType(MimeTypeUtil.checkMimeType(entry.getName().split("/")[1]));
					docView.setNuovoDocumento(true);

					List<DocumentoView> allegati = new ArrayList<DocumentoView>();
					logger.debug(methodName + " /-----------NOMEFILE: " + standardRest.getNome() + " ------------/");
					logger.debug(methodName + " /-----------MIMETYPE: "
							+ MimeTypeUtil.checkMimeType(entry.getName().split("/")[1]) + " ------------/");

					allegati.add(docView);

					repertorioView.setAllegato(allegati);
					repertorioStandardService.salvaAllegatiGenerici(repertorioView);
					RisultatoOperazioneRest ris = new RisultatoOperazioneRest(
							RisultatoOperazioneLettera.OK, asJson(standardRest.toString()));
					risultato.add(ris);
					logger.debug(methodName + " /-----------*FINE FILE: " + entry.getName() + "*------------/");
				} catch (Throwable e) {
					RisultatoOperazioneRest ris = null;
					try {
						ris = new RisultatoOperazioneRest(RisultatoOperazioneLettera.KO, asJson(e)+" OGGETTO: "+standardRest.toString());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					risultato.add(ris);
					e.printStackTrace();
					logger.error(methodName + " errore", e);
				}
			}
		}

		Collections.sort(risultato, new Comparator<RisultatoOperazioneRest>() {
			@Override
			public int compare(final RisultatoOperazioneRest object1, final RisultatoOperazioneRest object2) {
				return object1.getStato().compareTo(object2.getStato());
			}
		});
		logger.info(methodName+" RISULTATI: "+risultato.toString());
		logger.debug(methodName
				+ " /**************************************************FINE PROCEDURA******************************************************/");
		logger.debug(methodName + " end: " + System.currentTimeMillis());
		return risultato;
	}

	@Async
	@RequestMapping(value = "/rest/procureSpeciali", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public @ResponseBody List<RisultatoOperazioneRest> procureSpeciali(
			@RequestParam(value = "jsonFile", required = false) MultipartFile jsonFile,
			@RequestParam(value = "zipFile", required = false) MultipartFile zipFile,
			MultipartHttpServletRequest request, Model model) {

		String methodName = "procureSpeciali() ";
		List<RisultatoOperazioneRest> risultato = new ArrayList<RisultatoOperazioneRest>();
		Map<String, ProcureSpecialiRest> docObject = new HashMap<>();
		List<ProcureSpecialiRest> docSenzaFile = new LinkedList<>();
		List<ProcureSpecialiRest> procureRests = null;
		ObjectMapper mapper = new ObjectMapper();
		logger.debug(methodName + " begin: " + System.currentTimeMillis());
		logger.debug(methodName
				+ " /**************************************************INIZIO PROCEDURA PROCURE SPECIALI******************************************************/");
		ProfessionistaEsternoView professionistaEsterno = null;
		List<UtenteView> ut = null;
		Map<String, Utente> utenti = new HashMap<>();
		Map<String, Societa> mappaSocieta = new HashMap<>();
		
		try {
			List<Societa> listaSocieta = attoService.getlistSocieta(true);
			for (Societa societa : listaSocieta) {
				mappaSocieta.put(societa.getNome().trim(), societa);
			}
			logger.info(methodName+" inizializzo societa");
		} catch (Throwable e) {
			logger.error(methodName+" impossibile inizializzare le societa",e);
		}
		
		
		try {
			ut = utenteService.leggiUtenti(true);
			logger.info(methodName+" inizializzo la lista utenti");
		} catch (Throwable e2) {
			e2.printStackTrace();
		}
		try {
			professionistaEsterno = professionistaEsternoService.leggi(new Long("-1"), true);
			logger.info(methodName+" carico il notaio fittizio");
		} catch (NumberFormatException e3) {
			e3.printStackTrace();
		} catch (Throwable e3) {
			e3.printStackTrace();
		}
		TipoProcureView tipoProcureView = null;
		try {
			tipoProcureView = tipoProcureService.leggiNotDataCancellazione(new Long("-1"));
			logger.info(methodName+" carico il tipo procura fittizio");
		} catch (NumberFormatException e3) {
			e3.printStackTrace();
		} catch (Throwable e3) {
			e3.printStackTrace();
		}

		if (ut != null && ut.size() > 0) {
			for (UtenteView utenteView : ut) {
				utenti.put(utenteView.getVo().getNominativoUtil(), utenteView.getVo());
			}
		}

		try {
			procureRests = mapper.readValue(jsonFile.getInputStream(), new TypeReference<List<ProcureSpecialiRest>>() {
			});
			logger.debug(methodName + " JSON Letto: " + procureRests.toString());
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Iterator<ProcureSpecialiRest> iterator = procureRests.iterator(); iterator.hasNext();) {
			ProcureSpecialiRest procureRst = (ProcureSpecialiRest) iterator.next();
			if (procureRst.getNomeFile() != null) {
				docObject.put(procureRst.getNomeFile(), procureRst);
			} else {
				docSenzaFile.add(procureRst);
			}
		}

		ZipFile zipFileE = null;
		try {
			zipFileE = new ZipFile(convert(zipFile), Charset.forName("Cp437"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		Enumeration<? extends ZipEntry> entries = zipFileE.entries();

		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();

			if (!entry.isDirectory()) {

				InputStream stream;
				ProcureSpecialiRest procureRest = null;
				try {
					stream = zipFileE.getInputStream(entry);
					logger.debug(methodName + " /-----------*INIZIO FILE: " + entry.getName() + "*------------/");
					byte[] bytes = IOUtils.toByteArray(stream);

					File fileTmp = File.createTempFile("allegatoGenerico", "___" + entry.getName());
					FileUtils.writeByteArrayToFile(fileTmp, bytes);

					procureRest = docObject.get(entry.getName());
					logger.debug(methodName + " /-----------RECORD RECUPERATO: " + procureRest.toString()
							+ " ------------/");

					ProcureView procureView = new ProcureView();

					Procure procureVo = new Procure();
					procureVo.setNomeProcuratore(procureRest.getTipologia());
					procureVo.setNotaio(professionistaEsterno.getVo());
					procureVo.setFascicolo(null);
					procureVo.setUtente(procureRest.getAssegnatario());
					procureVo.setNumeroRepertorio(null);
					procureVo.setLegaleInterno(utenteService
							.findUtenteByNominativo(procureRest.getAutoreUltimaModifica()).getUseridUtil());
					procureVo.setDataRevoca(null);
					procureVo.setTipoProcure(tipoProcureView.getVo());
					procureVo.setPosizioneOrganizzativa(null);
					procureVo.setLivelloAttribuzioniI(null);
					procureVo.setLivelloAttribuzioniII(null);
					procureVo.setSocieta(findSocieta(procureRest.getSocieta(), mappaSocieta));
					procureVo.setDataConferimento(DateUtil.toDate("dd/MM/yyyy", procureRest.getInizio()));

					DocumentoView docView = new DocumentoView();

					docView.setFile(fileTmp);
					docView.setNomeFile(entry.getName());
					docView.setUuid("55");
					docView.setContentType(MimeTypeUtil.checkMimeType(entry.getName()));
					docView.setNuovoDocumento(true);

					List<DocumentoView> allegati = new ArrayList<DocumentoView>();
					logger.debug(
							methodName + " /-----------TIPOLOGIA: " + procureRest.getTipologia() + " ------------/");
					logger.debug(
							methodName + " /-----------NOMEFILE: " + procureRest.getNomeFile() + " ------------/");
					logger.debug(methodName + " /-----------MIMETYPE: "
							+ MimeTypeUtil.checkMimeType(entry.getName()) + " ------------/");

					allegati.add(docView);
					procureView.setListaAllegatiGenerici(allegati);
					procureView.setVo(procureVo);
					logger.info(methodName + " Oggetto da salvare: " + procureView.toString());
					procureService.salvaAllegatiGenerici(procureView);
					logger.debug(methodName + " /-----------*FINE FILE: " + entry.getName() + "*------------/");
					RisultatoOperazioneRest ris = new RisultatoOperazioneRest(RisultatoOperazioneLettera.OK, asJson(procureRest));
					risultato.add(ris);
				} catch (Throwable e) {
					RisultatoOperazioneRest ris = null;
					try {
						ris = new RisultatoOperazioneRest(RisultatoOperazioneLettera.KO, asJson(e)+" OGGETTO: "+procureRest.toString());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					risultato.add(ris);
					e.printStackTrace();
					logger.error(methodName + " errore", e);
				}
			}
		}

		logger.debug(methodName + " /-----------INSERISCI I RECORD SENZA FILE------------/");
		if (docSenzaFile != null && docSenzaFile.size() > 0) {
			logger.debug(methodName + " /-----------RECORD SENZA FILE: " + docSenzaFile.size() + "------------/");
			for (ProcureSpecialiRest rest : docSenzaFile) {
				ProcureView procureView = new ProcureView();
				Procure procureVo = new Procure();
				procureVo.setNomeProcuratore(rest.getTipologia());
				procureVo.setNotaio(professionistaEsterno.getVo());
				procureVo.setFascicolo(null);
				procureVo.setUtente(rest.getAssegnatario());
				procureVo.setNumeroRepertorio(null);
				try {
					procureVo.setLegaleInterno(
							utenteService.findUtenteByNominativo(rest.getAutoreUltimaModifica()).getUseridUtil());
				} catch (Throwable e1) {
					e1.printStackTrace();
					logger.error(methodName + " impossibile settare trovare la matricola by nominativo util"
							+ rest.getAutoreUltimaModifica());
				}
				procureVo.setDataRevoca(null);
				procureVo.setTipoProcure(tipoProcureView.getVo());
				procureVo.setPosizioneOrganizzativa(null);
				procureVo.setLivelloAttribuzioniI(null);
				procureVo.setLivelloAttribuzioniII(null);
				try {
					procureVo.setSocieta(findSocieta(rest.getSocieta(), mappaSocieta));
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				procureVo.setDataConferimento(DateUtil.toDate("dd/MM/yyyy", rest.getInizio()));
				procureView.setVo(procureVo);
				try {
					logger.info(methodName + " Oggetto da salvare: " + procureView.toString());
					procureService.inserisci(procureView);
					RisultatoOperazioneRest ris = new RisultatoOperazioneRest(RisultatoOperazioneLettera.OK, asJson(rest));
					risultato.add(ris);
				} catch (Throwable e) {
					e.printStackTrace();
					logger.error(methodName + " errore", e);
					RisultatoOperazioneRest ris = null;
					try {
						ris = new RisultatoOperazioneRest(RisultatoOperazioneLettera.KO, asJson(e)+" OGGETTO: "+rest.toString());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					risultato.add(ris);
				}
			}
		}

		logger.debug(methodName + " /-----------FINE INSERISCI I RECORD SENZA FILE------------/");
		try {
			zipFileE.close();
		} catch (Exception e) {
			logger.error(methodName + " Errore sulla chiusura del file zip", e);
		}
		
		logger.info(methodName+" RISULTATI: "+risultato.toString());
		logger.debug(methodName
				+ " /**************************************************FINE PROCEDURA******************************************************/");
		logger.debug(methodName + " end: " + System.currentTimeMillis());
		return risultato;
	}

	@Async
	@RequestMapping(value = "/rest/procureConferite", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public @ResponseBody List<RisultatoOperazioneRest> procureConferite(
			@RequestParam(value = "jsonFile", required = false) MultipartFile jsonFile,
			@RequestParam(value = "zipFile", required = false) MultipartFile zipFile,
			MultipartHttpServletRequest request, Model model) {

		String methodName = "procureConferite() ";
		List<RisultatoOperazioneRest> risultato = new ArrayList<RisultatoOperazioneRest>();
		List<ProcureConferiteRest> procureRests = null;
		ObjectMapper mapper = new ObjectMapper();
		logger.debug(methodName + " begin: " + System.currentTimeMillis());
		logger.debug(methodName
				+ " /**************************************************INIZIO PROCEDURA PROCURE CONFERITE******************************************************/"+System.currentTimeMillis());
		ProfessionistaEsternoView professionistaEsterno = null;
		List<UtenteView> ut = null;
		Map<String, Utente> utenti = new HashMap<>();
		List<CodiceDescrizioneBean> beansPosizioniOrganizzative = repertorioStandardService
				.leggiListaPosizioneOrganizzativa(Locale.ITALY);
		List<CodiceDescrizioneBean> beansPrimoLivelloAttr = repertorioStandardService
				.leggiListaPrimoLivelloAttribuzioni(Locale.ITALY);
		List<CodiceDescrizioneBean> beansSecondoLivelloAttr = repertorioStandardService
				.leggiListaSecondoLivelloAttribuzioni(Locale.ITALY);
		Map<String, CodiceDescrizioneBean> mappaPosizioniOrganizzative = new HashMap<String, CodiceDescrizioneBean>();
		Map<String, CodiceDescrizioneBean> mappaPrimoLivello = new HashMap<String, CodiceDescrizioneBean>();
		Map<String, CodiceDescrizioneBean> mappaSecondoLivello = new HashMap<String, CodiceDescrizioneBean>();
		Map<String, Societa> mappaSocieta = new HashMap<>();

		if (beansPosizioniOrganizzative != null && beansPosizioniOrganizzative.size() > 0) {
			for (CodiceDescrizioneBean codiceDescrizioneBean : beansPosizioniOrganizzative) {
				mappaPosizioniOrganizzative.put(codiceDescrizioneBean.getDescrizione().trim(), codiceDescrizioneBean);
			}
			logger.debug(methodName + "inizializzo posizioni organizzative caricate: " + mappaPosizioniOrganizzative.toString());
		}

		if (beansPrimoLivelloAttr != null && beansPrimoLivelloAttr.size() > 0) {
			for (CodiceDescrizioneBean codiceDescrizioneBean : beansPrimoLivelloAttr) {
				mappaPrimoLivello.put(codiceDescrizioneBean.getDescrizione().trim(), codiceDescrizioneBean);
			}
			logger.debug(methodName + "inizializzo primo livello attribuzioni caricate: " + mappaPrimoLivello.toString());
		}

		if (beansSecondoLivelloAttr != null && beansSecondoLivelloAttr.size() > 0) {
			for (CodiceDescrizioneBean codiceDescrizioneBean : beansSecondoLivelloAttr) {
				mappaSecondoLivello.put(codiceDescrizioneBean.getDescrizione().trim(), codiceDescrizioneBean);
			}
			logger.debug(methodName + "inizializzo secondo livello attribuzione caricate: " + mappaSecondoLivello.toString());
		}
		
		try {
			List<Societa> listaSocieta = attoService.getlistSocieta(true);
			for (Societa societa : listaSocieta) {
				mappaSocieta.put(societa.getNome().trim(), societa);
			}
			logger.info(methodName+" inizializzo societa");
		} catch (Throwable e) {
			logger.error(methodName+" impossibile inizializzare le societa",e);
		}

		try {
			ut = utenteService.leggiUtenti(true);
		} catch (Throwable e2) {
			e2.printStackTrace();
		}
		try {
			professionistaEsterno = professionistaEsternoService.leggi(new Long("-1"), true);
		} catch (NumberFormatException e3) {
			e3.printStackTrace();
		} catch (Throwable e3) {
			e3.printStackTrace();
		}
		TipoProcureView tipoProcureView = null;
		try {
			tipoProcureView = tipoProcureService.leggiNotDataCancellazione(new Long("-1"));
		} catch (NumberFormatException e3) {
			e3.printStackTrace();
		} catch (Throwable e3) {
			e3.printStackTrace();
		}
		List<TipoProcureView> procureViews = null;
		try {
			procureViews = tipoProcureService.leggiListNotDataCancellazione(Locale.ITALY);
		} catch (Throwable e4) {
			e4.printStackTrace();
		}

		if (ut != null && ut.size() > 0) {
			for (UtenteView utenteView : ut) {
				utenti.put(utenteView.getVo().getUseridUtil(), utenteView.getVo());
			}
		}

		try {
			procureRests = mapper.readValue(jsonFile.getInputStream(), new TypeReference<List<ProcureConferiteRest>>() {
			});
			logger.debug(methodName + " JSON Letto: " + procureRests.toString());
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		ZipFile zipFileE = null;
		try {
			zipFileE = new ZipFile(convert(zipFile), Charset.forName("Cp437"));
			logger.info(methodName+" FILE ZIP APERTO: "+zipFileE.getName());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(methodName+" Errore nell'apertura del file zip");
		}

		for (Iterator<ProcureConferiteRest> iterator = procureRests.iterator(); iterator.hasNext();) {
			ProcureConferiteRest procureRst = (ProcureConferiteRest) iterator.next();
			try {
				logger.info(methodName+" /-----------*PROCURA DA PROCESSARE: "+procureRst.toString()+"* ------------/");
				ProcureView procureView = new ProcureView();
				Procure procureVo = new Procure();
				procureVo.setNomeProcuratore(procureRst.getRepertorioProcure());
				logger.info(methodName+" /-----------REPERTORIO PROCURE: "+procureVo.getNomeProcuratore()+" ------------/");
				procureVo.setNotaio(professionistaEsterno.getVo());
				logger.info(methodName+" /-----------NOTAIO: "+procureVo.getNotaio().getCognomeNome()+" ------------/");
				procureVo.setFascicolo(null);
				logger.info(methodName+" /-----------FASCICOLO: "+procureVo.getFascicolo()+" ------------/");
				procureVo.setUtente(null);
				logger.info(methodName+" /-----------UTENTE: "+procureVo.getUtente()+" ------------/");
				procureVo.setNumeroRepertorio(null);
				logger.info(methodName+" /-----------NUMERO REPERTORIO: "+procureVo.getNumeroRepertorio()+" ------------/");
				procureVo.setLegaleInterno(procureRst.getLegaleInterno());
				logger.info(methodName+" /-----------LEGALE INTERNO: "+procureVo.getLegaleInterno()+" ------------/");
				procureVo.setTipoProcure(getTipoProcure(procureRst, procureViews, tipoProcureView));
				if (procureVo.getTipoProcure()==null) {
					procureVo.setTipoProcure(tipoProcureView.getVo());
				}
				logger.info(methodName+" /-----------TIPO PROCURE: "+procureVo.getTipoProcure().getDescrizione()+" ------------/");
				procureVo.setPosizioneOrganizzativa(getPosizioneOrganizzativa(procureRst, mappaPosizioniOrganizzative));
				if (procureVo.getPosizioneOrganizzativa()!=null) {
					logger.info(methodName+" /-----------POSIZIONE ORGANIZZATIVA: "+procureVo.getPosizioneOrganizzativa().getDescrizione()+" ------------/");
				} else {
					logger.info(methodName+" /-----------POSIZIONE ORGANIZZATIVA: NON PRESENTE ------------/");
				}
				procureVo.setLivelloAttribuzioniI(getLivelloAttribuzioniI(procureRst, mappaPrimoLivello));
				if (procureVo.getLivelloAttribuzioniI()!=null) {
					logger.info(methodName+" /-----------LIVELLO ATTRIBUZIONI I: "+procureVo.getLivelloAttribuzioniI().getDescrizione()+" ------------/");
				} else {
					logger.info(methodName+" /-----------LIVELLO ATTRIBUZIONI I: NON PRESENTE ------------/");
				}
				procureVo.setLivelloAttribuzioniII(getLivelloAttribuzioniII(procureRst, mappaSecondoLivello));
				if (procureVo.getLivelloAttribuzioniII()!=null) {
					logger.info(methodName+" /-----------LIVELLO ATTRIBUZIONI II: "+procureVo.getLivelloAttribuzioniII().getDescrizione()+" ------------/");
				} else {
					logger.info(methodName+" /-----------LIVELLO ATTRIBUZIONI II: NON PRESENTE ------------/");
				}
				procureVo.setSocieta(findSocieta(procureRst.getSocieta(), mappaSocieta));
				logger.info(methodName+" /-----------SOCIETA: "+procureVo.getSocieta()+" ------------/");
				procureVo.setDataConferimento(DateUtil.toDate("dd/MM/yyyy", procureRst.getInizio()));
				logger.info(methodName+" /-----------DATA CONFERIMENTO: "+procureVo.getDataConferimento()+" ------------/");
				procureVo.setDataRevoca(DateUtil.toDate("dd/MM/yyyy", procureRst.getRevoca()));
				logger.info(methodName+" /-----------DATA REVOCA: "+procureVo.getDataRevoca()+" ------------/");
				List<DocumentoView> allegati = new ArrayList<DocumentoView>();
				allegati = this.cercaFileInZip(zipFileE, procureRst);
				procureView.setListaAllegatiGenerici(allegati);
				procureView.setVo(procureVo);
				procureService.salvaAllegatiGenericiConferite(procureView);
				logger.info(methodName+" /-----------*FINE - PROCURA PROCESSATA: "+procureRst.toString()+"* ------------/");
				RisultatoOperazioneRest ris = new RisultatoOperazioneRest(RisultatoOperazioneLettera.OK, asJson(procureRst.toString()));
				risultato.add(ris);
			} catch (Exception e) {
				logger.error(methodName + " Eccezzione", e);
				RisultatoOperazioneRest ris = null;
				try {
					ris = new RisultatoOperazioneRest(RisultatoOperazioneLettera.KO, asJson(e)+"OGGETTO: "+procureRst.toString());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				risultato.add(ris);
			} catch (Throwable e) {
				e.printStackTrace();
				logger.error(methodName + " Eccezzione", e);
			}
		}
		try {
			zipFileE.close();
		} catch (Exception e) {
			logger.error(methodName + " Errore sulla chiusura del file zip", e);
		}

		logger.info(methodName+" RISULTATI: "+risultato.toString());
		logger.debug(methodName
				+ " /**************************************************FINE PROCEDURA******************************************************/"+System.currentTimeMillis());
		logger.debug(methodName + " end: " + System.currentTimeMillis());
		return risultato;
	}

	private List<DocumentoView> cercaFileInZip(ZipFile zip, ProcureConferiteRest procureConferiteRest) {
		String methodName = "cercaFileInZip() ";
		logger.info(methodName+" BEGIN ");
		List<DocumentoView> allegati = new ArrayList<DocumentoView>();
		if (procureConferiteRest.getNomeFile() != null && procureConferiteRest.getNomeFile().size() > 0) {
			logger.info(methodName+" /-----------FILE DA CERCARE: "+procureConferiteRest.getNomeFile().toString()+" ------------/");
			for (int i = 0; i < procureConferiteRest.getNomeFile().size(); i++) {
				try {
					Enumeration<? extends ZipEntry> entries = zip.entries();
					ZIPFILE: while (entries.hasMoreElements()) {
						ZipEntry entry = entries.nextElement();
						if (!entry.isDirectory()) {
							if (procureConferiteRest.getNomeFile().get(i).getNomeFile()
									.equalsIgnoreCase(entry.getName())) {
								InputStream stream;
								try {
									stream = zip.getInputStream(entry);
									byte[] bytes = IOUtils.toByteArray(stream);
									File fileTmp = File.createTempFile("allegatoGenerico",
											"___" + entry.getName());
									FileUtils.writeByteArrayToFile(fileTmp, bytes);
									DocumentoView docView = new DocumentoView();
									docView.setFile(fileTmp);
									docView.setNomeFile(pulisciNomiDuplicati(entry.getName()));
									docView.setUuid("55");
									docView.setContentType(MimeTypeUtil.checkMimeType(entry.getName()));
									docView.setNuovoDocumento(true);
									allegati.add(docView);
									logger.info(methodName+"/----------- FILE ALLEGATO: "+docView.getNomeFile()+" ------------/");
									break ZIPFILE;
								} catch (Throwable e) {
									logger.error(methodName + " errore", e);
								}
							}
						}
					}
				} catch (Exception e) {
					logger.error(methodName+" Errore per recuperare l'allegato dal file zip: "+procureConferiteRest.toString(), e);
				}
			}
		}
		logger.info(methodName+" END");
		return allegati;
	}
	
	private String pulisciNomiDuplicati(String nomeFile) {
		String methodName="pulisciNomiDuplicati() ";
		try {
			if (nomeFile!=null && nomeFile.contains("DUPLICATO_")) {
				String[] split = nomeFile.split("DUPLICATO_");
				nomeFile = split[1];
			}
		} catch (Exception e) {
			logger.error(methodName+"Errore",e);
		}
		return nomeFile;
	}
	

	private PosizioneOrganizzativa getPosizioneOrganizzativa(ProcureConferiteRest procureRest,
			Map<String, CodiceDescrizioneBean> mappaPosizioniOrganizzative) {
		String methodName = "getPosizioneOrganizzativa()";
		PosizioneOrganizzativa pos = null;
		/**
		 * Blocco per Nome Posizione
		 */
		try {
			String nomePosizione =null;
			if (procureRest.getPosizioneOrganizzativa()!=null) {
			nomePosizione = procureRest.getPosizioneOrganizzativa().trim().toUpperCase();
			if (nomePosizione != null && nomePosizione.contains(";")) {
				nomePosizione = nomePosizione.split(";")[0];
			}
			CodiceDescrizioneBean posizioneOrganizzativa = mappaPosizioniOrganizzative.get(nomePosizione);

			if (posizioneOrganizzativa != null) {
				PosizioneOrganizzativa po = null;
				try {
					po = this.repertorioStandardService.findPosizioneOrganizzByPk(posizioneOrganizzativa.getId());
				} catch (Throwable e) {
					e.printStackTrace();
				}
				pos = po;
				logger.debug(methodName + "/-----------POSIZIONE ORGANIZZATIVA TROVATA: " + po.toString()
						+ " ------------/");
			} else {
				logger.warn(methodName + "/-----------POSIZIONE ORGANIZZATIVA NON TROVATA: " + nomePosizione
						+ " ------------/");
				if (nomePosizione != null && !nomePosizione.equalsIgnoreCase("")) {
					logger.warn(methodName + "/-----------ALERT - POSIZIONE NON TROVATA NEL DB!: "
							+ procureRest.getPosizioneOrganizzativa() + " ------------/");
				}
			}
		}
		} catch (Exception e) {
			logger.info(methodName+" Errore: ",e);
		}
		return pos;
	}

	private TipoProcure getTipoProcure(ProcureConferiteRest conferiteRest, List<TipoProcureView> procureViews,
			TipoProcureView procuraFittizia) {
		String methodName = "getTipoProcure()";
		TipoProcure tipoProcure = null;
		try {
			if (conferiteRest.getTipologia() != null && !"".equalsIgnoreCase(conferiteRest.getTipologia().trim())) {
				for (TipoProcureView tipoProcureView : procureViews) {
					if (tipoProcureView.getVo().getDescrizione().trim().toLowerCase()
							.equalsIgnoreCase(conferiteRest.getTipologia().trim().toLowerCase())) {
						tipoProcure = tipoProcureView.getVo();
						break;
					}
				}
				if (tipoProcure == null)
				logger.info(methodName+" ATTENZIONE TIPO PROCURA NON TROVATA "+conferiteRest.getTipologia());
			} else {
				tipoProcure = procuraFittizia.getVo();
			}
		} catch (Exception e) {
			logger.error(methodName + " Errore nel recuperare il tipo procure",e);
		}
		return tipoProcure;
	}

	private LivelloAttribuzioniI getLivelloAttribuzioniI(ProcureConferiteRest procureRest,
			Map<String, CodiceDescrizioneBean> mappaPrimoLivello) throws Throwable {
		String methodName = "getLivelloAttribuzioniI()";
		LivelloAttribuzioniI livelloAttribuzioniI = null;
		/**
		 * Blocco per primo livello
		 */
		String primoLivello = procureRest.getPrimoLivello().trim().toUpperCase();
		if (primoLivello != null && primoLivello.contains(";")) {
			primoLivello = primoLivello.split(";")[0];
		}
		CodiceDescrizioneBean primoLivelloTmp = mappaPrimoLivello.get(primoLivello);

		if (primoLivelloTmp != null) {
			livelloAttribuzioniI = livelloAttribuzioniIService.findLivelloAttribuzioniIByPk(primoLivelloTmp.getId())
					.getVo();
			logger.debug(methodName + "/-----------PRIMO LIVELLO ATTRIBUZIONI: " + primoLivelloTmp.toString());
		} else {
			logger.warn(methodName + "/-----------PRIMO LIVELLO ATTRIBUZIONI NON TROVATA: " + primoLivello
					+ " ------------/");
			if (primoLivello != null && !primoLivello.equalsIgnoreCase("")) {
				logger.warn(methodName + "/-----------ALERT - PRIMO LIVELLO NON TROVATA NEL DB!: "
						+ procureRest.getPrimoLivello() + " ------------/");
			}
		}
		return livelloAttribuzioniI;
	}

	private LivelloAttribuzioniII getLivelloAttribuzioniII(ProcureConferiteRest procureRest,
			Map<String, CodiceDescrizioneBean> mappaSecondoLivello) throws Throwable {
		String methodName = "getLivelloAttribuzioniII()";
		LivelloAttribuzioniII livelloAttribuzioniII = null;
		/**
		 * Blocco per secondo livello
		 */
		String secondoLivello = procureRest.getSecondoLivello().trim().toUpperCase();
		if (secondoLivello != null && secondoLivello.contains(";")) {
			secondoLivello = secondoLivello.split(";")[0];
		}
		CodiceDescrizioneBean secondoLivelloTmp = mappaSecondoLivello.get(secondoLivello);

		if (secondoLivelloTmp != null) {
			livelloAttribuzioniII = livelloAttribuzioniIIService
					.findLivelloAttribuzioniIIByPk(secondoLivelloTmp.getId()).getVo();
			logger.debug(methodName + "/-----------SECONDO LIVELLO ATTRIBUZIONI: " + secondoLivelloTmp.toString()
					+ " ------------/");
		} else {
			logger.warn(methodName + "/-----------SECONDO LIVELLO ATTRIBUZIONI NON TROVATA: " + secondoLivello
					+ " ------------/");
			// throw new RuntimeException();
			if (secondoLivello != null && !secondoLivello.equalsIgnoreCase("")) {
				logger.warn(methodName + "/-----------ALERT - SECONDO LIVELLO NON TROVATA NEL DB!: "
						+ procureRest.getSecondoLivello() + " ------------/");
			}
		}
		return livelloAttribuzioniII;
	}
	
	
	private Societa findSocieta(String societa, Map<String, Societa> mappaSocieta) throws Exception {
		String methodName="findSocieta() ";
		Societa soc = null;
		try {
			soc = mappaSocieta.get(societa);
			if (soc == null) {
				throw new Exception("Societa non trovata "+societa);
			}
		} catch (Exception e) {
			logger.error(methodName+" Errore: ",e);
			throw e;
		}
		return soc;
	}
	

	@RequestMapping(value = "/rest/repertorioPoteri", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody List<RisultatoOperazioneRest> repertorioPoteri(
			@RequestBody List<RepertorioPoteriBonificaRest> rests, Model model) throws Throwable {
		List<RisultatoOperazioneRest> risultato = new ArrayList<RisultatoOperazioneRest>();

		Integer i = 1;

		List<CodiceDescrizioneBean> categorie = repertorioPoteriService.leggiListaCategorie(new Locale("IT"));
		List<CodiceDescrizioneBean> subCategorie = repertorioPoteriService.leggiListaSubCategorie(new Locale("IT"));

		Map<String, CategoriaTessere> mapCategorie = new HashMap<String, CategoriaTessere>();
		for (CodiceDescrizioneBean cat : categorie)
			mapCategorie.put(cat.getCategoria().getDescrizione(), cat.getCategoria());

		Map<String, SubCategoriaTessere> mapSubCategorie = new HashMap<String, SubCategoriaTessere>();
		for (CodiceDescrizioneBean cat : subCategorie)
			mapSubCategorie.put(cat.getDescrizione(), cat.getSubCategoria());

		for (RepertorioPoteriBonificaRest repertorio : rests) {

			try {

				RepertorioPoteri repertorioPoteri = new RepertorioPoteri();

				repertorioPoteri.setCodice(repertorio.getCodice());

				if (repertorio.getDescrizione().equals("") || repertorio.getCategoria() == null)
					repertorioPoteri.setDescrizione("-");
				else
					repertorioPoteri.setDescrizione(repertorio.getDescrizione());

				repertorioPoteri.setTesto(repertorio.getTesto());

				repertorioPoteri.setLingua("IT");

				repertorioPoteri.setCodGruppoLingua("RPOT_" + i);

				repertorioPoteri.setCategoria(mapCategorie.get(repertorio.getCategoria()));

				if (repertorio.getSubcategoria().equals("") || repertorio.getSubcategoria() == null)
					repertorioPoteri.setSubcategoria(mapSubCategorie.get("-"));
				else
					repertorioPoteri.setSubcategoria(mapSubCategorie.get(repertorio.getSubcategoria() + " "));

				RepertorioPoteriView view = new RepertorioPoteriView();
				view.setVo(repertorioPoteri);

				repertorioPoteriService.inserisci(view);

				RisultatoOperazioneRest ris = new RisultatoOperazioneRest(RisultatoOperazioneProforma.OK, "");

				risultato.add(ris);
			} catch (Exception e) {
				RisultatoOperazioneRest ris = new RisultatoOperazioneRest(RisultatoOperazioneProforma.KO,
						"Errore per codice: " + repertorio.getCodice() + " " + repertorio.getDescrizione() + " " + i
								+ " " + e);
				risultato.add(ris);
			}

			i++;
		}

		Collections.sort(risultato, new Comparator<RisultatoOperazioneRest>() {
			@Override
			public int compare(final RisultatoOperazioneRest object1, final RisultatoOperazioneRest object2) {
				return object1.getStato().compareTo(object2.getStato());
			}
		});

		logger.debug(risultato);

		return risultato;
	}

	@RequestMapping(value = "/rest/atto/bonificaSentenze", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody List<RisultatoOperazioneSentenze> bonificaSentenze(
			@RequestBody List<SentenzeBonificaRest> rests, Model model) throws Throwable {
		List<RisultatoOperazioneSentenze> risultato = new ArrayList<RisultatoOperazioneSentenze>();

		String numeroProtocollo = "";

		for (SentenzeBonificaRest sentenza : rests) {

			try {

				numeroProtocollo = sentenza.getNumeroProtocollo();

				Atto atto = attoService.getAttoPerNumeroProtocollo(numeroProtocollo);

				if (atto != null) {

					if (sentenza.getIdEsitoAtto().equalsIgnoreCase("F"))
						atto.setEsitoAtto(attoService.getEsitoByCode("FAV", "IT"));
					else if (sentenza.getIdEsitoAtto().equalsIgnoreCase("SF"))
						atto.setEsitoAtto(attoService.getEsitoByCode("SFAV", "IT"));
					else if (sentenza.getIdEsitoAtto().equalsIgnoreCase("T"))
						atto.setEsitoAtto(attoService.getEsitoByCode("TRANS", "IT"));

					if (sentenza.getPagamentoDovuto() != null)
						atto.setPagamentoDovuto(sentenza.getPagamentoDovuto());

					if (sentenza.getSpeseCarico() != null)
						atto.setSpeseCarico(sentenza.getSpeseCarico());

					if (sentenza.getSpeseFavore() != null)
						atto.setSpeseFavore(sentenza.getSpeseFavore());
				}

				AttoView view = new AttoView();
				view.setVo(atto);

				attoService.modifica(view);

				RisultatoOperazioneSentenze ris = new RisultatoOperazioneSentenze(numeroProtocollo,
						RisultatoOperazioneProforma.OK);

				risultato.add(ris);
			} catch (Exception e) {
				RisultatoOperazioneSentenze ris = new RisultatoOperazioneSentenze(numeroProtocollo,
						RisultatoOperazioneProforma.KO, asJson(e));
				risultato.add(ris);
			}
		}

		Collections.sort(risultato, new Comparator<RisultatoOperazioneSentenze>() {
			@Override
			public int compare(final RisultatoOperazioneSentenze object1, final RisultatoOperazioneSentenze object2) {
				return object1.getStato().compareTo(object2.getStato());
			}
		});

		return risultato;
	}

	@RequestMapping(value = "/rest/proforma/bonificaProforma", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody List<RisultatoOperazioneProforma> bonificaProforma(
			@RequestBody List<ProformaBonificaRest> rests, Model model) throws Throwable {
		List<RisultatoOperazioneProforma> risultato = new ArrayList<RisultatoOperazioneProforma>();

		String idProforma = "";

		for (ProformaBonificaRest proforma : rests) {

			try {

				idProforma = String.valueOf(proforma.getIdProforma());

				ProformaView proformaView = proformaService.leggi(proforma.getIdProforma());

				Proforma proformaVo = proformaView.getVo();

				// SETTO I VALORI IN BASE ALL'INPUT

				if (proforma.getOnorari() != null)
					proformaVo.setOnorari(proforma.getOnorari());

				if (proforma.getIsCpaDisabled() != null && proforma.getIsCpaDisabled() == 1) {
					proformaVo.setCPADisabled(true);
					proformaVo.setCpa(new BigDecimal(0));
				}

				if (proforma.getSpeseNonImponibili() != null)
					proformaVo.setSpeseNonImponibili(proforma.getSpeseNonImponibili());

				// RICALCOLO I TOTALI

				BigDecimal cpa;

				if (!proformaVo.isCPADisabled()
						&& proformaVo.getTipoProforma().getCodGruppoLingua().equalsIgnoreCase("ITA")) {

					cpa = proformaVo.getDiritti().add(proformaVo.getOnorari()).add(proformaVo.getSpeseImponibili())
							.multiply(new BigDecimal(0.04)).setScale(2, RoundingMode.DOWN);

					proformaVo.setCpa(cpa);

				} else
					cpa = new BigDecimal(0);

				BigDecimal totaleImponibile;

				totaleImponibile = proformaVo.getDiritti().add(proformaVo.getOnorari())
						.add(proformaVo.getSpeseImponibili()).add(cpa).setScale(2, RoundingMode.DOWN);

				proformaVo.setTotaleImponibile(totaleImponibile);

				BigDecimal totale;

				if (proforma.getSpeseNonImponibili() != null)
					totale = totaleImponibile.add(proforma.getSpeseNonImponibili()).setScale(2, RoundingMode.DOWN);
				else
					totale = totaleImponibile.add(proformaVo.getSpeseNonImponibili()).setScale(2, RoundingMode.DOWN);

				proformaVo.setTotaleAutorizzato(totale);

				proformaService.modificaPerWS(proformaView);

				RisultatoOperazioneProforma ris = new RisultatoOperazioneProforma(idProforma,
						RisultatoOperazioneProforma.OK);

				risultato.add(ris);
			} catch (Exception e) {
				RisultatoOperazioneProforma ris = new RisultatoOperazioneProforma(idProforma,
						RisultatoOperazioneProforma.KO, asJson(e));
				risultato.add(ris);
			}
		}

		Collections.sort(risultato, new Comparator<RisultatoOperazioneProforma>() {
			@Override
			public int compare(final RisultatoOperazioneProforma object1, final RisultatoOperazioneProforma object2) {
				return object1.getStato().compareTo(object2.getStato());
			}
		});

		return risultato;
	}

	@RequestMapping(value = "/rest/incarico/insLetteraIncarico", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody List<RisultatoOperazioneLettera> inserisciLetteraIncarico(
			@RequestBody List<LetteraIncaricoRest> rests, Model model) throws Throwable {
		List<RisultatoOperazioneLettera> risultato = new ArrayList<RisultatoOperazioneLettera>();

		String idIncarico = "";

		for (LetteraIncaricoRest lettera : rests) {

			try {

				idIncarico = String.valueOf(lettera.getIdIncarico());

				IncaricoView incaricoView = incaricoService.leggi(lettera.getIdIncarico());

				Incarico incarico = incaricoView.getVo();

				LetteraIncarico letteraIncarico = new LetteraIncarico();

				letteraIncarico.setProtocollo(lettera.getProtocollo());
				letteraIncarico.setCompenso(lettera.getCompenso());
				letteraIncarico.setOggetto("-");
				letteraIncarico.setQualifica("-");
				letteraIncarico.setDataProtocollo(incarico.getDataCreazione());
				letteraIncarico.setInviata(0);
				letteraIncarico.setTipoValuta(
						anagraficaStatiTipiService.leggiTipoValuta(incaricoService.getEuroValuta()).getVo());

				String legaleInterno = "";

				Utente utente = utenteService.leggiUtenteDaUserId(lettera.getLegaleInterno()).getVo();

				Numeri numeri = utenteService.getNumeriDaUserProfile(utente.getUseridUtil());

				legaleInterno = utente.getNominativoUtil() + " (";

				legaleInterno += "tel: " + numeri.getNumTelefono();

				legaleInterno += " - fax: " + numeri.getNumFax();

				legaleInterno += " - email: " + utente.getEmailUtil() + ")";

				letteraIncarico.setLegaleInterno(legaleInterno);

				if (lettera.getAcconto1() != null) {
					Acconti acconti = new Acconti();
					acconti.setAnno(lettera.getAnno1());
					acconti.setImporto(lettera.getAcconto1());
					letteraIncarico.addAcconti(acconti);
				}

				if (lettera.getAcconto2() != null) {
					Acconti acconti = new Acconti();
					acconti.setAnno(lettera.getAnno2());
					acconti.setImporto(lettera.getAcconto2());
					letteraIncarico.addAcconti(acconti);
				}

				if (lettera.getAcconto3() != null) {
					Acconti acconti = new Acconti();
					acconti.setAnno(lettera.getAnno3());
					acconti.setImporto(lettera.getAcconto3());
					letteraIncarico.addAcconti(acconti);
				}

				if (lettera.getAcconto4() != null) {
					Acconti acconti = new Acconti();
					acconti.setAnno(lettera.getAnno4());
					acconti.setImporto(lettera.getAcconto4());
					letteraIncarico.addAcconti(acconti);
				}

				if (lettera.getAcconto5() != null) {
					Acconti acconti = new Acconti();
					acconti.setAnno(lettera.getAnno5());
					acconti.setImporto(lettera.getAcconto5());
					letteraIncarico.addAcconti(acconti);
				}

				if (lettera.getBonus1() != null) {
					Bonus bonus = new Bonus();
					bonus.setImporto(lettera.getBonus1());
					bonus.setDescrizione(lettera.getDescrizione1());
					letteraIncarico.addBonus(bonus);
				}
				if (lettera.getBonus2() != null) {
					Bonus bonus = new Bonus();
					bonus.setImporto(lettera.getBonus2());
					bonus.setDescrizione(lettera.getDescrizione2());
					letteraIncarico.addBonus(bonus);
				}
				if (lettera.getBonus3() != null) {
					Bonus bonus = new Bonus();
					bonus.setImporto(lettera.getBonus3());
					bonus.setDescrizione(lettera.getDescrizione3());
					letteraIncarico.addBonus(bonus);
				}
				if (lettera.getBonus4() != null) {
					Bonus bonus = new Bonus();
					bonus.setImporto(lettera.getBonus4());
					bonus.setDescrizione(lettera.getDescrizione4());
					letteraIncarico.addBonus(bonus);
				}
				if (lettera.getBonus5() != null) {
					Bonus bonus = new Bonus();
					bonus.setImporto(lettera.getBonus5());
					bonus.setDescrizione(lettera.getDescrizione5());
					letteraIncarico.addBonus(bonus);
				}

				incaricoView.getVo().setLetteraIncarico(letteraIncarico);

				incaricoService.modificaPerWS(incaricoView);

				RisultatoOperazioneLettera ris = new RisultatoOperazioneLettera(idIncarico,
						RisultatoOperazioneLettera.OK);

				risultato.add(ris);
			} catch (Exception e) {
				RisultatoOperazioneLettera ris = new RisultatoOperazioneLettera(idIncarico,
						RisultatoOperazioneLettera.KO, asJson(e));
				risultato.add(ris);
			}
		}

		Collections.sort(risultato, new Comparator<RisultatoOperazioneLettera>() {
			@Override
			public int compare(final RisultatoOperazioneLettera object1, final RisultatoOperazioneLettera object2) {
				return object1.getStato().compareTo(object2.getStato());
			}
		});

		return risultato;
	}

	@RequestMapping(value = "/rest/bonificaAcl", produces = "application/json", method = RequestMethod.GET)
	public @ResponseBody RisultatoOperazioneRest bonificaAcl(@RequestParam("id") long id, Model model) {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		try {
			FascicoloView fascicoloView = fascicoloService.leggi(id);
			bonificaFascicoliAcl(fascicoloView);

			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
		} catch (Throwable e) {
			logger.error("ERRORE * Bonifica per id fascicolo: " + id);
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, e.getMessage());
			logger.error(e);
		}
		return risultato;
	}

	@RequestMapping(value = "/rest/bonificaAclAnnoMese", produces = "application/json", method = RequestMethod.GET)
	public @ResponseBody RisultatoOperazioneRest bonificaAclAnnoMese(@RequestParam("anno") Integer anno,
			@RequestParam("mese") Integer mese, Model model) throws Exception {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		ArrayList<Long> okid = new ArrayList<>();
		ArrayList<Long> koid = new ArrayList<>();

		logger.debug("INIZIO * Bonifica ACL per ANNO: " + anno + " MESE: " + mese);

		try {
			List<FascicoloView> listFascicoli = fascicoloService.cerca(anno, mese);
			logger.debug("Trovati " + listFascicoli.size() + " Fascicoli da bonificare!");

			for (FascicoloView fascicoloView : listFascicoli) {
				long id = fascicoloView.getVo().getId();

				try {

					bonificaFascicoliAcl(fascicoloView);
					okid.add(id);

				} catch (Throwable e) {
					koid.add(id);
					logger.error(e);
				}
			}

		} catch (Throwable e) {
			logger.error(e);
		}

		risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, okid.toString() + " Eseguite con successo: "
				+ okid.size() + " - Eseguite senza successo: " + koid.size() + " " + koid);

		return risultato;
	}

	@RequestMapping(value = "/rest/bonificaAclAnno", produces = "application/json", method = RequestMethod.GET)
	public @ResponseBody RisultatoOperazioneRest bonificaAclAnno(@RequestParam("anno") Integer anno, Model model) {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		try {

			logger.debug("INIZIO * Bonifica ACL per ANNO: " + anno);

			ricercaService.bonificaFascicoliAclAnno(anno);

			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK,
					"Lanciata Bonifica per anno " + anno + ". Vedere i log per il risultato!");
		} catch (Throwable e) {
			logger.error("ERRORE * Bonifica per anno: " + anno);
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, e.getMessage());
			logger.error(e);
		}
		return risultato;
	}

	@RequestMapping(value = "/rest/generaNumeroProtocollo", produces = "application/json", method = RequestMethod.GET)
	public synchronized @ResponseBody RisultatoOperazioneRest generaNumeroProtocollo(Locale locale,
			HttpServletRequest request, @RequestParam("mittente") String mittente,
			@RequestParam("destinatario") String destinatario, @RequestParam("unitaAppart") String unitaAppart,
			@RequestParam("oggetto") String oggetto, @RequestParam("tipo") String tipo, Model model) throws Throwable {

		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		String numProtocollo = "";
		UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);

		try {

			logger.debug("Genero Numero Protocollo per  " + mittente + " " + destinatario + " " + unitaAppart + " "
					+ oggetto + " " + tipo);

			if (tipo.equalsIgnoreCase("in")) {

				Utente utente = utenteService.leggiUtenteDaMatricola(destinatario).getVo();

				numProtocollo = archivioProtocolloService.generaNumeroProtocollo(utente, mittente, unitaAppart, oggetto,
						tipo, utenteView.getVo(), locale);
			} else if (tipo.equalsIgnoreCase("out")) {
				Utente utente = utenteService.leggiUtenteDaMatricola(mittente).getVo();

				numProtocollo = archivioProtocolloService.generaNumeroProtocollo(utente, destinatario, unitaAppart,
						oggetto, tipo, utenteView.getVo(), locale);
			} else {
				risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "Tipo non corretto");
				return risultato;
			}

			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, numProtocollo);
		} catch (Exception e) {
			logger.error(e);

			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, e.toString());

			return risultato;
		}
		return risultato;
	}

	@RequestMapping(value = "/rest/invioNotifica", produces = "application/json", method = RequestMethod.GET)
	public @ResponseBody RisultatoOperazioneRest invioNotifica(@RequestParam("idEntita") long idEntita,
			@RequestParam("tipNot") String tipoNotifica, @RequestParam("tipEnt") String tipoEntita,
			@RequestParam("utente") String utente, Model model) throws Throwable {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		try {

			logger.debug("Bonifica " + idEntita + " " + tipoNotifica + " " + tipoEntita + " " + utente);

			emailNotificationService.inviaNotifica(tipoNotifica, tipoEntita, idEntita, "IT", utente);

			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
		} catch (Exception e) {
			logger.error(e);
		}
		return risultato;
	}

	@RequestMapping(value = "/rest/invioNotificaProtocolli", produces = "application/json", method = RequestMethod.GET)
	public @ResponseBody RisultatoOperazioneRest invioNumeroProtocolli(Model model) throws Throwable {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		try {

			emailNotificationService.inviaNotificaProtocollo();

			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
		} catch (Exception e) {
			logger.error(e);
		}
		return risultato;
	}

	@RequestMapping(value = "/rest/bonificaEmailProf", produces = "application/json", method = RequestMethod.GET)
	public @ResponseBody RisultatoOperazioneRest bonificaEmailProf(@RequestParam("id") long id, Model model) {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		try {

			logger.debug("Bonifica per id: " + id);

			emailNotificationService.inviaNotificaProfessionistaEsterno(id, "IT", "");
			;

			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
		} catch (Throwable e) {
			logger.error(e);
		}
		return risultato;
	}

	@RequestMapping(value = "/rest/bonificaEmailMultiProf", produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
	public @ResponseBody RisultatoOperazioneRest bonificaEmailMultiProf(@RequestBody ArrayList<Long> ids, Model model)
			throws Exception {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		ArrayList<Long> okid = new ArrayList<>();

		logger.debug("Bonifica per gli id: " + ids);

		for (Long id : ids) {

			logger.debug("Bonifica per id: " + id);

			try {
				emailNotificationService.inviaNotificaProfessionistaEsterno(id, "IT", "");
				okid.add(id);
			} catch (Throwable e) {
				logger.error(e);
			}

		}
		ids.removeAll(okid);
		risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK,
				okid.toString() + "Totale: " + okid.size() + " su " + ids.size() + " " + ids);
		return risultato;
	}

	@RequestMapping(value = "/rest/bonificaEmail", produces = "application/json", method = RequestMethod.GET)
	public @ResponseBody RisultatoOperazioneRest bonificaEmail(@RequestParam("id") long id, Model model) {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		try {

			logger.debug("Bonifica per id: " + id);

			emailNotificationService.inviaNotificaBonifica(id);

			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
		} catch (Throwable e) {
			logger.error(e);
		}
		return risultato;
	}

	@RequestMapping(value = "/rest/bonificaEmailMulti", produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
	public @ResponseBody RisultatoOperazioneRest bonificaEmailMulti(@RequestBody ArrayList<Long> ids, Model model)
			throws Exception {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		ArrayList<Long> okid = new ArrayList<>();

		logger.debug("Bonifica per gli id: " + ids);

		for (Long id : ids) {

			logger.debug("Bonifica per id: " + id);

			try {
				emailNotificationService.inviaNotificaBonifica(id);
				okid.add(id);
			} catch (Throwable e) {
				logger.error(e);
			}

		}
		ids.removeAll(okid);
		risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK,
				okid.toString() + "Totale: " + okid.size() + " su " + ids.size() + " " + ids);
		return risultato;
	}

	@RequestMapping(value = "/rest/fascicolo/findByUser", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody RicercaFascicoloRest findFascicoliByUser(HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		try {
			String userId = request.getParameter("userId");
			String roles = request.getParameter("roles");
			String parole = request.getParameter("parole") == null ? null
					: URLDecoder.decode(request.getParameter("parole"), "UTF-8");
			String dataDal = request.getParameter("dataDal") == null ? null
					: URLDecoder.decode(request.getParameter("dataDal"), "UTF-8");
			String dataAl = request.getParameter("dataAl") == null ? null
					: URLDecoder.decode(request.getParameter("dataAl"), "UTF-8");

			if (userId == null || roles == null) {
				throw new RuntimeException("userId e roles non possono essere null");
			}

			if (request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO) == null) {
				prepareSessionUser(userId, roles, request);
			}

			List<String> listaParole = new ArrayList<String>();
			if (roles != null && roles.length() > 0) {
				listaParole = Arrays.asList(parole.toString().split(","));
			}

			int numeroPagina = 1;
			String strNumeroPagina = request.getParameter("numeroPagina");

			if (strNumeroPagina != null && !strNumeroPagina.isEmpty())
				numeroPagina = NumberUtils.toInt(request.getParameter("numeroPagina"));

			int numeroElementi = 10;
			String strNnumeroElementi = request.getParameter("numeroElementi");

			if (strNnumeroElementi != null && !strNnumeroElementi.isEmpty())
				numeroElementi = NumberUtils.toInt(request.getParameter("numeroElementi"));

			ListaPaginata<FascicoloView> lista = (ListaPaginata<FascicoloView>) service.cerca(listaParole, dataDal,
					dataAl, numeroElementi, numeroPagina, null, null, Costanti.TIPO_PERMESSO_SCRITTURA);

			RicercaFascicoloRest ricercaModelRest = new RicercaFascicoloRest();
			ricercaModelRest.setTotal(lista.getNumeroTotaleElementi());
			List<FascicoloRest> rows = new ArrayList<FascicoloRest>();
			for (FascicoloView fascicoloView : lista) {
				FascicoloRest fascicoloRest = new FascicoloRest();
				fascicoloRest.setId(fascicoloView.getVo().getId());
				fascicoloRest.setNome(fascicoloView.getVo().getNome());
				fascicoloRest.setPathFilenet(FileNetUtil.getFascicoloCartella(fascicoloView.getVo().getDataCreazione(),
						fascicoloView.getVo().getNome()));
				fascicoloRest.setOggetto(fascicoloView.getVo().getOggettoSintetico());
				fascicoloRest
						.setDataCreazione(DateUtil.getDataDDMMYYYY(fascicoloView.getVo().getDataCreazione().getTime()));
				rows.add(fascicoloRest);
			}
			ricercaModelRest.setRows(rows);
			return ricercaModelRest;
		} catch (Throwable e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);

		}
		return null;
	}

	@RequestMapping(value = "/rest/atto/findByUser", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody RicercaAttoRest findAttiByUser(HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		try {
			String userId = request.getParameter("userId");
			String roles = request.getParameter("roles");
			String nomeAtto = request.getParameter("nomeAtto") == null ? null
					: URLDecoder.decode(request.getParameter("nomeAtto"), "UTF-8");
			;
			String dataDal = request.getParameter("dataDal") == null ? null
					: URLDecoder.decode(request.getParameter("dataDal"), "UTF-8");
			;
			String dataAl = request.getParameter("dataAl") == null ? null
					: URLDecoder.decode(request.getParameter("dataAl"), "UTF-8");
			;

			if (userId == null || roles == null) {
				throw new RuntimeException("userId e roles non possono essere null");
			}

			if (request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO) == null) {
				prepareSessionUser(userId, roles, request);
			}

			int numeroPagina = 1;
			String strNumeroPagina = request.getParameter("numeroPagina");

			if (strNumeroPagina != null && !strNumeroPagina.isEmpty())
				numeroPagina = NumberUtils.toInt(request.getParameter("numeroPagina"));

			int numeroElementi = 10;
			String strNnumeroElementi = request.getParameter("numeroElementi");

			if (strNnumeroElementi != null && !strNnumeroElementi.isEmpty())
				numeroElementi = NumberUtils.toInt(request.getParameter("numeroElementi"));

			List<Atto> lista = (List<Atto>) attoService.getCercaAtti(dataDal, dataAl, nomeAtto, 0, 0, null,
					numeroElementi, numeroPagina, "desc", false, false);

			RicercaAttoRest ricercaModelRest = new RicercaAttoRest();
			ricercaModelRest.setTotal(attoService.countAtti(dataDal, dataAl, nomeAtto, 0, 0, null, false, false));
			List<AttoRest> rows = new ArrayList<AttoRest>();
			for (Atto atto : lista) {
				AttoRest attoRest = new AttoRest();
				attoRest.setId(atto.getId());
				attoRest.setNumeroProtocollo(atto.getNumeroProtocollo());
				attoRest.setPathFilenet(
						FileNetUtil.getAttoCartella(atto.getDataCreazione(), atto.getNumeroProtocollo()));
				rows.add(attoRest);
			}
			ricercaModelRest.setRows(rows);
			return ricercaModelRest;
		} catch (Throwable e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);

		}
		return null;
	}

	@RequestMapping(value = "/rest/fascicolo/allega", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody RisultatoOperazioneRest allegaAlFascicoloByUser(@RequestBody AllegatoRest allegato,
			HttpServletRequest request, HttpServletResponse response) throws Throwable {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");

		try {
			String userId = allegato.getUserId();
			String roles = allegato.getRoles();

			if (userId == null || roles == null) {
				throw new RuntimeException("userId e roles non possono essere null");
			}

			if (allegato.getIdFascicolo() == null) {
				throw new RuntimeException("fascicoloId non pu� essere null");
			}

			if (request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO) == null) {
				prepareSessionUser(userId, roles, request);
			}

			AllegatoMultipartFile multipartFile = new AllegatoMultipartFile();
			multipartFile.setName(allegato.getNomeFile());
			multipartFile.setOriginalFileName(allegato.getNomeFile());
			multipartFile.setContentType(allegato.getMimeType());
			byte[] bytes = Base64Decoder.decode(allegato.getBase64());
			multipartFile.setSize(bytes.length);
			multipartFile.setBytes(bytes);

			multipartFile.setCc(null);
			multipartFile.setDataInvio(allegato.getDataInvio());
			multipartFile.setDataRicezione(allegato.getDataRicezione());
			multipartFile.setFrom(allegato.getFrom());
			multipartFile.setOggetto(allegato.getOggetto());
			multipartFile.setTo(null);

			long categoriaAltro = service.getTipoCategoriaDocumentale("TCAT_5");// categoria
																				// generica
			service.aggiungiDocumento(allegato.getIdFascicolo(), categoriaAltro, multipartFile);
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
		} catch (Throwable e) {
			e.printStackTrace();
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, e.getMessage());

		}

		return risultato;
	}

	@RequestMapping(value = "/rest/atto/allegaMulti", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody RisultatoOperazioneRest allegaAttoByUser(@RequestBody List<AllegatoRest> rests,
			HttpServletRequest request, HttpServletResponse response) throws Throwable {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");

		try {
			List<MultipartFile> allegati = new ArrayList<MultipartFile>();
			Long idAtto = new Long(0);
			for (AllegatoRest rest : rests) {
				String userId = rest.getUserId();
				String roles = rest.getRoles();

				if (userId == null || roles == null) {
					throw new RuntimeException("userId e roles non possono essere null");
				}

				if (rest.getIdFascicolo() == null) {
					throw new RuntimeException("attoId non pu� essere null");
				}

				if (request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO) == null) {
					prepareSessionUser(userId, roles, request);
				}

				idAtto = rest.getIdFascicolo();

				AllegatoMultipartFile multipartFile = new AllegatoMultipartFile();
				multipartFile.setName(rest.getNomeFile());
				multipartFile.setOriginalFileName(rest.getNomeFile());
				multipartFile.setContentType(rest.getMimeType());
				byte[] bytes = Base64Decoder.decode(rest.getBase64());
				multipartFile.setSize(bytes.length);
				multipartFile.setBytes(bytes);

				multipartFile.setCc(null);
				multipartFile.setDataInvio(rest.getDataInvio());
				multipartFile.setDataRicezione(rest.getDataRicezione());
				multipartFile.setFrom(rest.getFrom());
				multipartFile.setOggetto(rest.getOggetto());
				multipartFile.setTo(null);

				allegati.add(multipartFile);
			}
			long categoriaAltro = service.getTipoCategoriaDocumentale("TCAT_5");// categoria
																				// generica
			attoService.aggiungiDocumento(idAtto, categoriaAltro, allegati);

			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
		} catch (Throwable e) {
			e.printStackTrace();
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, e.getMessage());

		}
		return risultato;
	}

	@RequestMapping(value = "/rest/fascicolo/allegaMulti", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody RisultatoOperazioneRest allegaAlFascicoloByUser(@RequestBody List<AllegatoRest> rests,
			HttpServletRequest request, HttpServletResponse response) throws Throwable {
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");

		System.out.println("Chiamato");
		try {
			List<MultipartFile> allegati = new ArrayList<MultipartFile>();
			Long idFascicolo = new Long(0);
			for (AllegatoRest rest : rests) {
				String userId = rest.getUserId();
				String roles = rest.getRoles();

				if (userId == null || roles == null) {
					throw new RuntimeException("userId e roles non possono essere null");
				}

				if (rest.getIdFascicolo() == null) {
					throw new RuntimeException("fascicoloId non pu� essere null");
				}

				if (request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO) == null) {
					prepareSessionUser(userId, roles, request);
				}

				idFascicolo = rest.getIdFascicolo();

				AllegatoMultipartFile multipartFile = new AllegatoMultipartFile();
				multipartFile.setName(rest.getNomeFile());
				multipartFile.setOriginalFileName(rest.getNomeFile());
				multipartFile.setContentType(rest.getMimeType());
				byte[] bytes = Base64Decoder.decode(rest.getBase64());
				multipartFile.setSize(bytes.length);
				multipartFile.setBytes(bytes);

				multipartFile.setCc(null);
				multipartFile.setDataInvio(rest.getDataInvio());
				multipartFile.setDataRicezione(rest.getDataRicezione());
				multipartFile.setFrom(rest.getFrom());
				multipartFile.setOggetto(rest.getOggetto());
				multipartFile.setTo(null);

				allegati.add(multipartFile);
			}
			long categoriaAltro = service.getTipoCategoriaDocumentale("TCAT_5");// categoria
																				// generica
			service.aggiungiDocumento(idFascicolo, categoriaAltro, allegati);

			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
		} catch (Throwable e) {
			e.printStackTrace();
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, e.getMessage());

		}
		return risultato;
	}

	private void prepareSessionUser(String userId, String roles, HttpServletRequest request) throws Throwable {

		UtenteService utenteService = (UtenteService) SpringUtil.getBean("utenteService");
		UtenteView utenteView = utenteService.leggiUtenteDaUserId(userId);

		if (utenteView != null) {

			List<UtenteView> listaCollaboratori = utenteService
					.leggiCollaboratori(utenteView.getVo().getMatricolaUtil());
			List<String> collaboratoriMatricole = new ArrayList<String>();
			if (listaCollaboratori != null) {
				for (UtenteView collaboratore : listaCollaboratori) {
					collaboratoriMatricole.add(collaboratore.getVo().getMatricolaUtil());
				}
			}

			//DARIO ********************************************************************
			//utenteView.setPrimoRiporto(utenteService.leggiSePrimoRiporto(utenteView));
			utenteView = utenteService.settaMatricoleTopManagers(utenteView); 
			//**************************************************************************
			
			utenteView.setResponsabileFoglia(utenteService.leggiSeResponsabileFoglia(utenteView));
			utenteView.setLegaleInterno(utenteService.leggiSeLegaleInterno(utenteView));
			utenteView
					.setResponsabileSenzaCollaboratori(utenteService.leggiSeResponsabileSenzaCollaboratori(utenteView));

			CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
			currentSessionUtil.setUserId(userId);
			currentSessionUtil.setNominativo(utenteView.getVo().getNominativoUtil());
			List<String> rolesCode = new ArrayList<String>();
			if (roles != null && roles.length() > 0) {
				rolesCode = Arrays.asList(roles.toString().split(","));
			}

			currentSessionUtil.setRolesCode(rolesCode);
			if (listaCollaboratori != null)
				currentSessionUtil.setCollMatricole(collaboratoriMatricole);
			else
				currentSessionUtil.setCollMatricole(null);

			if (rolesCode != null) {
				for (String role : rolesCode) {
					if (role.trim().equalsIgnoreCase(CostantiDAO.AMMINISTRATIVO)) {
						utenteView.setAmministrativo(true);
					}
					if (role.trim().equalsIgnoreCase(CostantiDAO.OPERATORE_SEGRETERIA)) {
						utenteView.setOperatoreSegreteria(true);
					}
					if (role.trim().equalsIgnoreCase(CostantiDAO.GRUPPO_AMMINISTRATORE)) {
						utenteView.setAmministratore(true);
					}
					if (role.trim().equalsIgnoreCase(CostantiDAO.GESTORE_VENDOR)) {
						utenteView.setGestoreVendor(true);
					}
				}
			}

			request.getSession().setAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO, utenteView);

		} else {
			throw new Exception("Utente non autorizzato");
		}

	}

	@RequestMapping(value = "/rest/fascicolo/getFascicoliPerAvanzamento", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody FascicoloRestPerAvanzamentoTot findFascicoliPerAvanzamento(HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		// engsecurity VA ??
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();
		htmlActionSupport.checkCSRFToken(request);
		// removeCSRFToken(request);

		try {

			UtenteView utenteView = (UtenteView) request.getSession()
					.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);

			if (utenteView == null) {
				throw new RuntimeException("utenteView non pu� essere null");
			}

			String userId = utenteView.getVo().getUseridUtil();

			List<FascicoloView> lista = (List<FascicoloView>) service.leggiFascicoloOrdData(userId);

			FascicoloRestPerAvanzamentoTot restOutput = new FascicoloRestPerAvanzamentoTot();

			restOutput.setTotal(lista.size());
			List<FascicoloRestPerAvanzamento> rows = new ArrayList<FascicoloRestPerAvanzamento>();

			for (FascicoloView fascicoloView : lista) {
				Boolean isOrange = false;
				Boolean isYellow = false;
				Boolean isBlue = false;
				Boolean isGreen = false;
				Date maxOrange = new Date(0);
				Date maxYellow = new Date(0);
				Date maxBlue = new Date(0);
				Date maxGreen = new Date(0);
				FascicoloRestPerAvanzamento fascicoloRest = new FascicoloRestPerAvanzamento();
				fascicoloRest.setId(fascicoloView.getVo().getId());
				fascicoloRest.setNomeFascicolo(fascicoloView.getVo().getNome());

				Fascicolo fascicolo = fascicoloView.getVo();

				if (fascicolo.getIncaricos().size() == 0) {
					fascicoloRest.setStato("red");
					fascicoloRest.setData(DateUtil.formattaData(fascicolo.getDataCreazione().getTime()));
				} else {
					Set<Incarico> incaricos = fascicolo.getIncaricos();
					for (Iterator<Incarico> iterator = incaricos.iterator(); iterator.hasNext();) {
						Incarico incarico = (Incarico) iterator.next();

						if (incarico.getStatoIncarico().getCodGruppoLingua().equals(CostantiDAO.INCARICO_STATO_BOZZA)) {
							isOrange = true;
							if (incarico.getDataCreazione().after(maxOrange))
								maxOrange = incarico.getDataCreazione();
						} else if (incarico.getStatoIncarico().getCodGruppoLingua()
								.equals(CostantiDAO.INCARICO_STATO_AUTORIZZATO)) {

							isYellow = true;

							if (incarico.getDataModifica().after(maxYellow))
								maxYellow = incarico.getDataModifica();

							List<ProformaView> proforma = proformaService
									.leggiProformaAssociatiAIncarico(incarico.getId());

							if (proforma != null) {
								for (Iterator<ProformaView> iterator2 = proforma.iterator(); iterator2.hasNext();) {
									ProformaView proformaView = (ProformaView) iterator2.next();

									if (proformaView.getVo().getStatoProforma().getCodGruppoLingua()
											.equals(CostantiDAO.PROFORMA_STATO_BOZZA)) {
										isBlue = true;

										if (proformaView.getVo().getDataInserimento().after(maxBlue))
											maxBlue = proformaView.getVo().getDataInserimento();
									} else if (proformaView.getVo().getStatoProforma().getCodGruppoLingua()
											.equals(CostantiDAO.PROFORMA_STATO_AUTORIZZATO)) {
										isGreen = true;

										if (proformaView.getVo().getDataUltimaModifica().after(maxGreen))
											maxGreen = proformaView.getVo().getDataUltimaModifica();
									}

								}
							}

						}
					}
					if (isGreen) {
						fascicoloRest.setStato("green");
						fascicoloRest.setData(DateUtil.formattaData(maxGreen.getTime()));
					} else if (isBlue) {
						fascicoloRest.setStato("blue");
						fascicoloRest.setData(DateUtil.formattaData(maxBlue.getTime()));
					} else if (isYellow) {
						fascicoloRest.setStato("yellow");
						fascicoloRest.setData(DateUtil.formattaData(maxYellow.getTime()));
					} else if (isOrange) {
						fascicoloRest.setStato("orange");
						fascicoloRest.setData(DateUtil.formattaData(maxOrange.getTime()));
					} else {
						fascicoloRest.setStato("red");
						fascicoloRest.setData(DateUtil.formattaData(fascicolo.getDataCreazione().getTime()));
					}
				}

				rows.add(fascicoloRest);
			}
			restOutput.setRows(rows);
			return restOutput;
		} catch (Throwable e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);

		}
		return null;
	}

	@RequestMapping(value = "/procure/creaFascicoloEIncarico", method = RequestMethod.POST)
	public @ResponseBody RisultatoOperazioneRest creaFascicoloEIncarico(@RequestBody CreaFascicoloIncaricoRest fi,
			HttpServletRequest request) {

		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
		UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);

		try {

			Fascicolo fascicolo = new Fascicolo();
			Incarico incarico = new Incarico();

			if (utenteView != null && utenteView.getVo() != null)
				fascicolo.setLegaleInterno(utenteView.getVo().getUseridUtil());

			fascicolo.setTipologiaFascicolo(tipologiaFascicoloService.leggi("TFSC_4", new Locale("IT"), false).getVo());

			fascicolo.setSettoreGiuridico(settoreGiuridicoService.leggi("TSTT_0", new Locale("IT"), false).getVo());
			Date d = new Timestamp(System.currentTimeMillis());
			fascicolo.setDataCreazione(d);
			fascicolo.setDataUltimaModifica(d);
			fascicolo.setStatoFascicolo(anagraficaStatiTipiService
					.leggiStatiFascicolo(CostantiDAO.FASCICOLO_STATO_APERTO, Locale.ITALIAN.getLanguage().toUpperCase())
					.getVo());
			fascicolo.setTitolo("autorizzazione procure");
			fascicolo.setDescrizione("autorizzazione procure");

			FascicoloView fview = new FascicoloView();
			fview.setVo(fascicolo);

			incarico.setDataCreazione(new Date());

			incarico.setStatoIncarico(anagraficaStatiTipiService.leggiStatoIncarico("B", "IT").getVo());

			incarico.setFascicolo(fascicolo);

			ProfessionistaEsternoView pev = professionistaEsternoService.leggi(Long.parseLong(fi.getNotaio()));
			String nomeIncarico = "Incarico - ";

			if (pev != null) {

				if (pev.getVo() != null) {

					incarico.setProfessionistaEsterno(pev.getVo());
					nomeIncarico += pev.getVo().getCognomeNome() + " - "
							+ DateUtil.getDataDDMMYYYYtrattino(new Date().getTime());
				}
			}
			incarico.setNomeIncarico(nomeIncarico);

			incarico.setCollegioArbitrale("F");
			IncaricoView iv = new IncaricoView();
			iv.setVo(incarico);
			incaricoService.inserisciFascicoloEincarico(fview, iv, fi);

		} catch (Exception e) {
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		} catch (Throwable e) {
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		}

		return risultato;

	}

	/**
	 * Carica Eventi e Scadenze in base alla data di inizio e fine e l'utente
	 * owner
	 **/
	@RequestMapping(value = "/rest/agenda/loadEventSources", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String loadEventSources(HttpServletRequest request) {

		String risultato = new String("messaggio.ok");
		// UtenteView utenteView = (UtenteView)
		// request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);

		try {

			String start_req = request.getParameter("start");
			String end_req = request.getParameter("end");
			String userIdUtil = request.getParameter("userIdUtil");

			Date start = DateUtil.toDate("dd/MM/yyyy", start_req);
			Date end = DateUtil.toDate("dd/MM/yyyy", end_req);

			List<EventoView> eventi_lettiDalDB = (List<EventoView>) eventoService.leggi(start, end);
			List<ScadenzaView> scadenze_lettiDalDB = (List<ScadenzaView>) scadenzaService.leggi(start, end);

			UtenteView utenteView = utenteService.leggiUtenteDaUserId(userIdUtil);

			// utente is amministratore
			boolean utenteIsAdmin = utenteView.isAmministratore();

			// filtro eventi e scadenze per autorizzazioni
			List<EventoView> eventi = filtroEventiPerAutorizzazioniLettura(eventi_lettiDalDB, utenteIsAdmin,
					userIdUtil);
			List<ScadenzaView> scadenze = filtroScadenzePerAutorizzazioniLettura(scadenze_lettiDalDB, utenteIsAdmin,
					userIdUtil);

			List<EventSource> eventSources = new ArrayList<EventSource>(0);

			for (int i = 0; i < eventi.size(); i++) {
				EventoView eventoView = eventi.get(i);
				Evento evento = eventoView.getVo();

				if (evento.getFascicolo() != null) {
					EventSource es = new EventSource();
					es.setId(evento.getId());
					es.setIdFascicolo(evento.getFascicolo().getId());
					es.setTitle(evento.getOggetto());
					es.setDescrizione(evento.getDescrizione());
					es.setStart(evento.getDataEvento());
					es.setClassName("bgm-green");
					eventSources.add(es);
				}
			}

			for (int i = 0; i < scadenze.size(); i++) {
				ScadenzaView scadenzaView = scadenze.get(i);
				Scadenza scadenza = scadenzaView.getVo();

				if (scadenza.getFascicolo() != null) {
					EventSource es = new EventSource();
					es.setId(scadenza.getId());
					es.setIdFascicolo(scadenza.getFascicolo().getId());
					es.setTitle(scadenza.getOggetto());
					es.setDescrizione(scadenza.getDescrizione());
					es.setStart(scadenza.getDataScadenza());
					if (scadenza.getTipoScadenza().getCodGruppoLingua().equals("TS_1")) {
						es.setClassName("bgm-red");
					} else if (scadenza.getTipoScadenza().getCodGruppoLingua().equals("TS_2")) {
						es.setClassName("bgm-red");
					}
					eventSources.add(es);
				}
			}

			JSONArray jsonArray = new JSONArray();
			for (EventSource es : eventSources) {
				JSONObject jsonObject = new JSONObject();

				jsonObject.put("id", es.getId());
				jsonObject.put("idFascicolo", es.getIdFascicolo());
				jsonObject.put("title", es.getTitle());
				jsonObject.put("description", es.getDescrizione());
				jsonObject.put("start", es.getStart());
				jsonObject.put("end", es.getEnd());
				jsonObject.put("className", es.getClassName());

				jsonArray.put(jsonObject);
			}

			return jsonArray.toString();

		} catch (Exception e) {
			e.printStackTrace();
			risultato = new String("messaggio.ko");
		} catch (Throwable e) {
			e.printStackTrace();
			risultato = new String("messaggio.ko");
		}
		return risultato;
	}

	private List<EventoView> filtroEventiPerAutorizzazioniLettura(List<EventoView> eventiIn, boolean utenteIsAdmin,
			String userIdUtil) throws Throwable {
		List<EventoView> eventiOut = new ArrayList<EventoView>();
		// eventi - filtro per autorizzazioni di LETTURA
		for (EventoView ev : eventiIn) {

			if (ev != null) {

				if (ev.getVo().getFascicolo() != null) {

					String userIdOwnerFascicolo = ev.getVo().getFascicolo().getLegaleInterno();

					// amministratore
					if (utenteIsAdmin) {
						eventiOut.add(ev);
					} else {
						if (userIdUtil != null && !userIdUtil.isEmpty()) {

							/**
							 * Visualizza gli eventi del legale sottoposto
							 * inserito in input
							 **/
							if (userIdUtil.equals(userIdOwnerFascicolo)) {
								eventiOut.add(ev);
							}
						}
					}
				}
			}
		}
		return eventiOut;
	}

	private List<ScadenzaView> filtroScadenzePerAutorizzazioniLettura(List<ScadenzaView> scadenzeIn,
			boolean utenteIsAdmin, String userIdUtil) throws Throwable {
		List<ScadenzaView> scadenzeOut = new ArrayList<ScadenzaView>();

		// scadenze - filtro per autorizzazioni di LETTURA
		for (ScadenzaView sv : scadenzeIn) {

			if (sv != null) {

				if (sv.getVo().getFascicolo() != null) {

					String userIdOwnerFascicolo = sv.getVo().getFascicolo().getLegaleInterno();

					// amministratore
					if (utenteIsAdmin) {
						scadenzeOut.add(sv);
					} else {
						if (userIdUtil != null && !userIdUtil.isEmpty()) {

							/**
							 * Visualizza gli eventi del legale sottoposto
							 * inserito in input
							 **/
							if (userIdUtil.equals(userIdOwnerFascicolo)) {
								scadenzeOut.add(sv);
							}
						}
					}
				}
			}
		}
		return scadenzeOut;
	}

	private void bonificaFascicoliAcl(FascicoloView fascicoloView) throws Throwable {

		long id = fascicoloView.getVo().getId();
		logger.debug("INIZIO * Bonifica ACL per id fascicolo: " + id);

		boolean isPenale = fascicoloView.getVo().getSettoreGiuridico().getCodGruppoLingua()
				.equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);
		String nomeCartellaFascicolo = FileNetUtil.getFascicoloCartella(fascicoloView.getVo().getDataCreazione(),
				fascicoloView.getVo().getNome());
		Folder cartellaFascicolo = isPenale ? ricercaService.leggiCartellaCrypt(nomeCartellaFascicolo)
				: ricercaService.leggiCartella(nomeCartellaFascicolo);
		List<AutorizzazioneView> autorizzazioni = autorizzazioneService
				.leggiAutorizzazioni2(fascicoloView.getVo().getId(), CostantiDAO.NOME_CLASSE_FASCICOLO);
		for (AutorizzazioneView av : autorizzazioni) {
			Autorizzazione a = av.getVo();

			// logger.debug("TipoAutorizzazione =
			// "+a.getTipoAutorizzazione().getCodGruppoLingua());

			if (a.getTipoAutorizzazione().getCodGruppoLingua().equals(CostantiDAO.TIPO_AUTORIZZAZIONE_UTENTE_LETTURA)) {

				String matricolaUtil = a.getUtente().getMatricolaUtil();
				UtenteView uv = utenteService.leggiUtenteDaMatricola(matricolaUtil);
				logger.debug("@@DDS bonificaFascicoliAcl ");
				//TODO @@DDS
				//TODO @@DDS FileNetUtil.addAclFolder(true, uv.getVo().getUseridUtil(), cartellaFascicolo, false);

			} else if (a.getTipoAutorizzazione().getCodGruppoLingua()
					.equals(CostantiDAO.TIPO_AUTORIZZAZIONE_GRUPPO_LETTURA)) {

				String forRespMatricola = a.getUtenteForResp() == null ? null : a.getUtenteForResp().getMatricolaUtil();
				String gruppoUtente = a.getGruppoUtente() == null ? null : a.getGruppoUtente().getCodice();

				if (forRespMatricola != null) {
					List<UtenteView> listaResponsabili = utenteService.leggiResponsabili(forRespMatricola);
					if (listaResponsabili != null && !listaResponsabili.isEmpty()) {
						for (UtenteView uv : listaResponsabili) {
							//TODO @@DDS
							//TODO @@DDS FileNetUtil.addAclFolder(true, uv.getVo().getUseridUtil(), cartellaFascicolo, false);

						}
					}
				} else if (gruppoUtente != null) {
					//TODO @@DDS
					//TODO @@DDS FileNetUtil.addAclFolder(false, gruppoUtente, cartellaFascicolo, false);
				}

			} else if (a.getTipoAutorizzazione().getCodGruppoLingua()
					.equals(CostantiDAO.TIPO_AUTORIZZAZIONE_UTENTE_SCRITTURA)) {

				String matricolaUtil = a.getUtente().getMatricolaUtil();
				UtenteView uv = utenteService.leggiUtenteDaMatricola(matricolaUtil);
				//TODO @@DDS
				//TODO @@DDS FileNetUtil.addAclFolder(true, uv.getVo().getUseridUtil(), cartellaFascicolo, true);

			} else if (a.getTipoAutorizzazione().getCodGruppoLingua()
					.equals(CostantiDAO.TIPO_AUTORIZZAZIONE_GRUPPO_SCRITTURA)) {

				String forRespMatricola = a.getUtenteForResp() == null ? null : a.getUtenteForResp().getMatricolaUtil();
				String gruppoUtente = a.getGruppoUtente() == null ? null : a.getGruppoUtente().getCodice();

				if (forRespMatricola != null) {
					List<UtenteView> listaResponsabili = utenteService.leggiResponsabili(forRespMatricola);
					if (listaResponsabili != null && !listaResponsabili.isEmpty()) {
						for (UtenteView uv : listaResponsabili) {
							//TODO @@DDS
							//TODO @@DDS FileNetUtil.addAclFolder(true, uv.getVo().getUseridUtil(), cartellaFascicolo, true);

						}
					}
				} else if (gruppoUtente != null) {
					//TODO @@DDS
					//TODO @@DDS FileNetUtil.addAclFolder(false, gruppoUtente, cartellaFascicolo, true);
				}

			}

		}

		// ereditariet� document
		//TODO @@DDS
		//TODO @@DDS FileNetUtil.addAclFile(cartellaFascicolo);

		logger.debug("FINE * Bonifica ACL per id fascicolo: " + id);

	}

	/**
	 * Carica Eventi e Scadenze in base alla data di inizio e fine per tutti gli
	 * utenti
	 **/
	@RequestMapping(value = "/rest/agenda/loadAllEventSources", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String loadAllEventSources(HttpServletRequest request) {

		String risultato = new String("messaggio.ok");

		try {

			String start_req = request.getParameter("start");
			String end_req = request.getParameter("end");

			Date start = DateUtil.toDate("dd/MM/yyyy", start_req);
			Date end = DateUtil.toDate("dd/MM/yyyy", end_req);

			List<EventoView> eventi_lettiDalDB = (List<EventoView>) eventoService.leggi(start, end);
			List<ScadenzaView> scadenze_lettiDalDB = (List<ScadenzaView>) scadenzaService.leggi(start, end);

			List<EventSource> eventSources = new ArrayList<EventSource>(0);

			for (int i = 0; i < eventi_lettiDalDB.size(); i++) {
				EventoView eventoView = eventi_lettiDalDB.get(i);
				Evento evento = eventoView.getVo();

				if (evento.getFascicolo() != null) {

					EventSource es = new EventSource();
					es.setId(evento.getId());
					es.setIdFascicolo(evento.getFascicolo().getId());
					es.setNomeFascicolo(evento.getFascicolo().getNome());
					es.setTitoloFascicolo(evento.getFascicolo().getTitolo());
					es.setTitle(evento.getOggetto());
					es.setDescrizione(evento.getDescrizione());
					es.setStart(evento.getDataEvento());
					es.setClassName("bgm-green");
					es.setOwner(evento.getFascicolo().getLegaleInterno());
					eventSources.add(es);
				}
			}

			for (int i = 0; i < scadenze_lettiDalDB.size(); i++) {
				ScadenzaView scadenzaView = scadenze_lettiDalDB.get(i);
				Scadenza scadenza = scadenzaView.getVo();

				if (scadenza.getFascicolo() != null) {

					EventSource es = new EventSource();
					es.setId(scadenza.getId());
					es.setIdFascicolo(scadenza.getFascicolo().getId());
					es.setNomeFascicolo(scadenza.getFascicolo().getNome());
					es.setTitoloFascicolo(scadenza.getFascicolo().getTitolo());
					es.setTitle(scadenza.getOggetto());
					es.setDescrizione(scadenza.getDescrizione());
					es.setStart(scadenza.getDataScadenza());
					if (scadenza.getTipoScadenza().getCodGruppoLingua().equals("TS_1")) {
						es.setClassName("bgm-red");
					} else if (scadenza.getTipoScadenza().getCodGruppoLingua().equals("TS_2")) {
						es.setClassName("bgm-red");
					}
					es.setOwner(scadenza.getFascicolo().getLegaleInterno());
					eventSources.add(es);
				}
			}

			JSONArray jsonArray = new JSONArray();
			for (EventSource es : eventSources) {
				JSONObject jsonObject = new JSONObject();

				jsonObject.put("id", es.getId());
				jsonObject.put("idFascicolo", es.getIdFascicolo());
				jsonObject.put("nomeFascicolo", es.getNomeFascicolo());
				jsonObject.put("titoloFascicolo", es.getTitoloFascicolo());
				jsonObject.put("title", es.getTitle());
				jsonObject.put("description", es.getDescrizione());
				jsonObject.put("start", es.getStart());
				jsonObject.put("end", es.getEnd());
				jsonObject.put("className", es.getClassName());
				jsonObject.put("owner", es.getOwner());

				jsonArray.put(jsonObject);
			}

			return jsonArray.toString();

		} catch (Exception e) {
			e.printStackTrace();
			risultato = new String("messaggio.ko");
		} catch (Throwable e) {
			e.printStackTrace();
			risultato = new String("messaggio.ko");
		}
		return risultato;
	}

	/** Carica tutti gli utenti con data di cancellazione null **/
	@RequestMapping(value = "/rest/agenda/loadAllUserId", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String loadAllUserId(HttpServletRequest request) {

		String risultato = new String("messaggio.ok");

		try {

			List<UtenteView> utentiNotNull = utenteService.leggiUtenti(false);

			JSONArray jsonArray = new JSONArray();
			for (UtenteView utenteView : utentiNotNull) {

				if (utenteView.getVo() != null) {

					if (utenteView.getVo().getUseridUtil() != null && !utenteView.getVo().getUseridUtil().isEmpty()) {

						JSONObject jsonObject = new JSONObject();
						jsonObject.put("userId", utenteView.getVo().getUseridUtil());
						jsonArray.put(jsonObject);
					}
				}
			}

			return jsonArray.toString();

		} catch (Exception e) {
			e.printStackTrace();
			risultato = new String("messaggio.ko");
		} catch (Throwable e) {
			e.printStackTrace();
			risultato = new String("messaggio.ko");
		}
		return risultato;
	}

	/**
	 * Archivia un documento salvato in un json
	 * @author MASSIMO CARUSO
	 */
	@RequestMapping(value = "/rest/archiviaDocumento", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public RisultatoOperazioneRest archiviaDocumento(
			@RequestBody String jsonFile) throws Throwable {
		logger.info("Inserimento Documento in archivo  --  start");
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
		ObjectMapper mapper = new ObjectMapper();
		ArchivioProtocollo protocollo = null;
		JSONMail mail = null;
		String to_convert = null;
		FileOutputStream fos = null;
		String path_email = null;
		long search_result = -1;
		
		try {

			// acquisizione della email
			mail = mapper.readValue(jsonFile, JSONMail.class);
			to_convert = mail.getBase64().replace(' ', '+');
			
			// generazione del path
			path_email = System.getProperty("java.io.tmpdir")+mail.getNomeFile();
			
			// salvataggio nella cartella temp della email
			fos = new FileOutputStream(path_email);
			fos.write(Base64.getDecoder().decode(to_convert));
			
			logger.info("File temporaneo: "+path_email);
			
			// inserisco un nuovo protocollo
			protocollo = archivioProtocolloService.inserisciNuovoProtocolloInArchivio(mail.getUserId(),mail.getDocType());
						
			// salvo l'email
			search_result = archivioProtocolloService.salvaDocumento(System.getProperty("java.io.tmpdir"), mail,protocollo);
			
			// verifico che non sia stata gi� inserita
			if(search_result == -1){
				logger.info("Email "+mail.getNomeFile()+" gi� presente");
				risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "Il documento � stato archiviato in precedenza");
				// rimuovo il protocollo creato
				archivioProtocolloService.rimuoviProtocollo(protocollo.getId());
			}else{
				// creazione risultato ok
				risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, protocollo.getNumProtocollo());			
			}
			
		} catch (Exception e) {
			logger.info("Errore durante l'esecuzione del metodo archiviaDocumento");
			e.printStackTrace();
			logger.error(e);
			logger.info(e);
			
		}finally{
			fos.close();
		}
		
		logger.info("Inserimento Documento in archivo  --  end");
		
		return risultato;
	}
	
	
	
	/**
	 * Inserisce un nuovo atto da validare.
	 * @author MASSIMO CARUSO
	 */
	@RequestMapping(value = "/rest/inserisciAttoDaValidare", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public RisultatoOperazioneRest inserisciAttoDaValidare(
		@RequestParam("assegnatario") String assegnatario, @RequestParam("idDocumento") String idDocumento, @RequestParam("idProtocollo") long idProtocollo) throws Throwable {
		logger.info("Inserimento Atto da validare --  start");
		logger.info("Assegnatario: "+assegnatario+"\nidDocumento: "+idDocumento+"\nidProtocollo: "+idProtocollo);
		RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
			
		try {

			ProtocolloView protocollo = archivioProtocolloService.leggi(idProtocollo);
			String numero_protocollo = protocollo.getNumeroProtocollo();
			
			//cambio lo stato dell'atto in "DA VALIDARE"
			Atto atto = attoService.inserisciNuovoAttoInArchivio(assegnatario,idDocumento);				
			archivioProtocolloService.cambiaStatoAdAssegnatoProtocollo(idProtocollo,assegnatario);
			
			risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, atto.getNumeroProtocollo());			
				
		} catch (Exception e) {
			logger.info("Errore durante l'esecuzione del metodo inserisciAttoDaValidare");
			e.printStackTrace();
			logger.error(e);
			logger.info(e);
			
		}
		
		logger.info("Inserimento Atto da validare --  end");
		
		return risultato;
	}
	
	
	@SuppressWarnings("unused")
	private class EventSource {

		public EventSource() {
		}

		private Long id;
		private Long idFascicolo;
		private String nomeFascicolo;
		private String titoloFascicolo;
		private String title;
		private String descrizione;
		private Date start;
		private Date end;
		private String className;
		private String owner;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public Date getStart() {
			return start;
		}

		public void setStart(Date start) {
			this.start = start;
		}

		public String getClassName() {
			return className;
		}

		public void setClassName(String className) {
			this.className = className;
		}

		public Date getEnd() {
			return end;
		}

		public void setEnd(Date end) {
			this.end = end;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getDescrizione() {
			return descrizione;
		}

		public void setDescrizione(String descrizione) {
			this.descrizione = descrizione;
		}

		public Long getIdFascicolo() {
			return idFascicolo;
		}

		public void setIdFascicolo(Long idFascicolo) {
			this.idFascicolo = idFascicolo;
		}

		public String getOwner() {
			return owner;
		}

		public void setOwner(String owner) {
			this.owner = owner;
		}

		public String getNomeFascicolo() {
			return nomeFascicolo;
		}

		public void setNomeFascicolo(String nomeFascicolo) {
			this.nomeFascicolo = nomeFascicolo;
		}

		public String getTitoloFascicolo() {
			return titoloFascicolo;
		}

		public void setTitoloFascicolo(String titoloFascicolo) {
			this.titoloFascicolo = titoloFascicolo;
		}
	}

	private String asJson(Object obj) throws Exception {
		StringWriter w = new StringWriter();
		new ObjectMapper().configure(SerializationFeature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS, true).writeValue(w, obj);
		String result = w.toString();
		return result;
	}
}
