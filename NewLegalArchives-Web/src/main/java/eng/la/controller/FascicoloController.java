package eng.la.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.FetchMode;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import eng.la.business.AnagraficaStatiTipiService;
import eng.la.business.AttoService;
import eng.la.business.ControparteService;
import eng.la.business.FascicoloService;
import eng.la.business.GiudizioService;
import eng.la.business.MateriaService;
import eng.la.business.NazioneService;
import eng.la.business.NotificaWebService;
import eng.la.business.OrganoGiudicanteService;
import eng.la.business.ProgettoService;
import eng.la.business.RicorsoService;
import eng.la.business.SettoreGiuridicoService;
import eng.la.business.SocietaService;
import eng.la.business.TerzoChiamatoCausaService;
import eng.la.business.TipologiaFascicoloService;
import eng.la.business.UtenteService;
import eng.la.model.Atto;
import eng.la.model.Controparte;
import eng.la.model.Fascicolo;
import eng.la.model.NotificaWeb;
import eng.la.model.ParteCivile;
import eng.la.model.PersonaOffesa;
import eng.la.model.RCorrelazioneFascicoli;
import eng.la.model.RFascPrestNotar;
import eng.la.model.RFascicoloGiudizio;
import eng.la.model.RFascicoloMateria;
import eng.la.model.RFascicoloRicorso;
import eng.la.model.RFascicoloSocieta;
import eng.la.model.RUtenteFascicolo;
import eng.la.model.ResponsabileCivile;
import eng.la.model.SoggettoIndagato;
import eng.la.model.TerzoChiamatoCausa;
import eng.la.model.rest.AutoCompleteModel;
import eng.la.model.rest.SettoreGiuridicoRest;
import eng.la.model.view.AttoView;
import eng.la.model.view.BaseView;
import eng.la.model.view.ControparteView;
import eng.la.model.view.FascicoloView;
import eng.la.model.view.GiudizioView;
import eng.la.model.view.MateriaView;
import eng.la.model.view.NazioneView;
import eng.la.model.view.NotificaWebView;
import eng.la.model.view.OrganoGiudicanteView;
import eng.la.model.view.ParteCivileView;
import eng.la.model.view.PersonaOffesaView;
import eng.la.model.view.PosizioneSocietaView;
import eng.la.model.view.ProgettoView;
import eng.la.model.view.RFascicoloGiudizioView;
import eng.la.model.view.RFascicoloRicorsoView;
import eng.la.model.view.RUtenteFascicoloView;
import eng.la.model.view.ResponsabileCivileView;
import eng.la.model.view.RicorsoView;
import eng.la.model.view.SettoreGiuridicoView;
import eng.la.model.view.SocietaView;
import eng.la.model.view.SoggettoIndagatoView;
import eng.la.model.view.TerzoChiamatoCausaView;
import eng.la.model.view.TipoContenziosoView;
import eng.la.model.view.TipoEntitaView;
import eng.la.model.view.TipoPrestNotarileView;
import eng.la.model.view.TipoSoggettoIndagatoView;
import eng.la.model.view.TipologiaFascicoloView;
import eng.la.model.view.UtenteView;
import eng.la.model.view.ValoreCausaView;
import org.apache.log4j.Logger;
import eng.la.presentation.listener.WebSocketStartupListener;
import eng.la.presentation.tag.DecryptTag;
import eng.la.presentation.validator.FascicoloValidator;
import eng.la.util.DateUtil;
import eng.la.util.costants.Costanti;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("fascicoloController")
@SessionAttributes("fascicoloView")
public class FascicoloController extends BaseController  {
	private static final Logger logger = Logger.getLogger(FascicoloController.class);
	private static final String MODEL_VIEW_NOME = "fascicoloView";
	private static final String PAGINA_FORM_PATH = "fascicolo/formFascicolo"; 
	private static final String PAGINA_FORM_RICORSO = "fascicolo/modale/formRicorso";
	private static final String PAGINA_FORM_GIUDIZIO = "fascicolo/modale/formGiudizio";  
	
	@Autowired
	private FascicoloService fascicoloService;
	 
	@Autowired
	private UtenteService utenteService;

	@Autowired
	private TerzoChiamatoCausaService terzoChiamatoCausaService;

	@Autowired
	private ControparteService controparteService;

	@Autowired
	private OrganoGiudicanteService organoGiudicanteService;

	@Autowired
	private NazioneService nazioneService;

	@Autowired
	private RicorsoService ricorsoService;

	@Autowired
	private GiudizioService giudizioService;

	@Autowired
	private SettoreGiuridicoService settoreGiuridicoService;

	@Autowired
	private TipologiaFascicoloService tipologiaFascicoloService;

	@Autowired
	private MateriaService materiaService;

	@Autowired
	private AnagraficaStatiTipiService anagraficaStatiTipiService;

	@Autowired
	private SocietaService societaService;

	@Autowired
	private ProgettoService progettoService;

	@Autowired
	private AttoService attoService;
	
	@Autowired
	private NotificaWebService notificaWebService;

	
	@RequestMapping("/fascicolo/modifica")
	public String modificaFascicolo(@RequestParam("id") long id, Model model, Locale locale
			, HttpServletRequest request
			) {
		FascicoloView fascicoloView = new FascicoloView();
		// engsecurity VA ??
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		try {
			FascicoloView fascicoloSalvato = (FascicoloView) fascicoloService.leggi(id, FetchMode.JOIN);
			if (fascicoloSalvato != null && fascicoloSalvato.getVo() != null) {
				popolaFormDaVo(fascicoloView, fascicoloSalvato.getVo(), locale);
				super.caricaListe(fascicoloView, locale);
			} else {
				model.addAttribute("errorMessage", "errore.oggetto.non.trovato");
			}
		} catch (Throwable e) {

			model.addAttribute("errorMessage", "errore.generico");
			e.printStackTrace();
		}
		
		model.addAttribute(MODEL_VIEW_NOME, fascicoloView);
		return model.containsAttribute("errorMessage") ? "redirect:/errore.action":PAGINA_FORM_PATH;
	}
	 
  
	private void popolaFormDaVo(FascicoloView fascicolo, Fascicolo vo, Locale locale) throws Throwable {
		logger.info("@@DDS popolaFormDaVo " );
		fascicolo.setFascicoloId(vo.getId());
		fascicolo.setNome(vo.getNome()); 
		fascicolo.setStatoFascicolo(vo.getStatoFascicolo().getDescrizione());
		fascicolo.setValoreCausaCode(vo.getValoreCausa() == null ? null : vo.getValoreCausa().getCodGruppoLingua());
		fascicolo.setTipologiaFascicoloCode(vo.getTipologiaFascicolo().getCodGruppoLingua());
		fascicolo.setSettoreGiuridicoCode(vo.getSettoreGiuridico().getCodGruppoLingua());
		fascicolo.setDescrizione(vo.getDescrizione());
		fascicolo.setOggettoSintetico(vo.getOggettoSintetico());
		fascicolo.setTitolo(vo.getTitolo());
		fascicolo.setCentroDiCosto(vo.getCentroDiCosto());
		fascicolo.setVoceDiConto(vo.getVoceDiConto());
		
		
		Date ultimaDataCancellazioneUtenteFascicolo = vo.getDataCreazione();
		if( vo.getRUtenteFascicolos() != null && vo.getRUtenteFascicolos().size() > 0){
			RUtenteFascicoloView[] utentiFascicolo = null;
			Set<RUtenteFascicolo> listaUtentiFascicolo = vo.getRUtenteFascicolos();
			SortedMap<Date, RUtenteFascicolo> sortedMap = new TreeMap<Date, RUtenteFascicolo>(new Comparator<Object>() {

				@Override
				public int compare(Object o1, Object o2) {
					Date u1 = (Date) o1;
					Date u2 = (Date) o2;
					
					return u1.compareTo(u2);					
				}
			});

			SortedMap<Date, RUtenteFascicoloView> sortedMapInversa = new TreeMap<Date, RUtenteFascicoloView>(new Comparator<Object>() {

				@Override
				public int compare(Object o1, Object o2) {
					Date u1 = (Date) o1;
					Date u2 = (Date) o2;
					
					return u2.compareTo(u1);					
				}
			});
			
			for (RUtenteFascicolo rUtenteFascicolo : listaUtentiFascicolo) {
				if( rUtenteFascicolo.getDataCancellazione() != null ){
					sortedMap.put(new Date(rUtenteFascicolo.getDataCancellazione().getTime()), rUtenteFascicolo);
				}
			}
			List<RUtenteFascicoloView> listaUtentiFascicoloView = new ArrayList<RUtenteFascicoloView>();
			List<RUtenteFascicolo> listaOrdinata = new ArrayList<RUtenteFascicolo>();
			listaOrdinata.addAll(sortedMap.values());
					 
			for (RUtenteFascicolo rUtenteFascicolo : listaOrdinata) {
				if( rUtenteFascicolo.getDataCancellazione() != null ){
					String ownerDal = "";
					String ownerAl = "";
					RUtenteFascicoloView view = new RUtenteFascicoloView();
					view.setVo(rUtenteFascicolo);
					ownerDal = DateUtil.getDataDDMMYYYY( ultimaDataCancellazioneUtenteFascicolo.getTime() );
					ownerAl = DateUtil.getDataDDMMYYYY( rUtenteFascicolo.getDataCancellazione().getTime() );	
					ultimaDataCancellazioneUtenteFascicolo = rUtenteFascicolo.getDataCancellazione();
					view.setOwnerDal(ownerDal);
					view.setOwnerAl(ownerAl);
					sortedMapInversa.put(ultimaDataCancellazioneUtenteFascicolo, view); 
				}
			}
			listaUtentiFascicoloView.addAll(sortedMapInversa.values());
			utentiFascicolo = new RUtenteFascicoloView[listaUtentiFascicoloView.size()];
			listaUtentiFascicoloView.toArray(utentiFascicolo);
			fascicolo.setUtentiFascicolo(utentiFascicolo);
		} 
		
		
		UtenteView utenteOwnerFascicolo = utenteService.leggiUtenteDaUserId(vo.getLegaleInterno());
		String ownerDal = DateUtil.getDataDDMMYYYY( ultimaDataCancellazioneUtenteFascicolo.getTime() );
		if( utenteOwnerFascicolo == null || utenteOwnerFascicolo.getVo() == null ){
			fascicolo.setLegaleInternoDesc(vo.getLegaleInterno());
			fascicolo.setOwnerDal(ownerDal);
			fascicolo.setUnitaLegaleDesc("");
			fascicolo.setLegaleInterno(vo.getLegaleInterno());
			fascicolo.setUnitaLegale("");
		}else{ 
			fascicolo.setOwnerDal(ownerDal);
			fascicolo.setLegaleInternoDesc( utenteOwnerFascicolo.getCodiceDescrizioneUtente());
			fascicolo.setUnitaLegaleDesc(utenteOwnerFascicolo.getCodiceDescrizioneUnitaLegale());
			fascicolo.setLegaleInterno(utenteOwnerFascicolo.getVo().getUseridUtil());
			fascicolo.setUnitaLegale(utenteOwnerFascicolo.getVo().getCodiceUnitaUtil());
		} 
		
		if (vo.getSocieta() != null) {
			fascicolo.setPartnerId(vo.getSocieta().getId());
		}

		fascicolo.setTipoJoinVenture(vo.getJointVenture());
		fascicolo.setTarget(vo.getTarget());

		if (vo.getRFascPrestNotars() != null && vo.getRFascPrestNotars().size() > 0) {
			String[] tipoPrestNotarAggiunte = null;
			Set<RFascPrestNotar> listaFascicoloPrestNotar = vo.getRFascPrestNotars();
			List<String> listaPrestNotarCodes = new ArrayList<String>();
			for (RFascPrestNotar tipoPrestNotar : listaFascicoloPrestNotar) {
				TipoPrestNotarileView tipoView = anagraficaStatiTipiService.leggiTipoPrestazioneNotarile(
						tipoPrestNotar.getTipoPrestNotarile().getCodGruppoLingua(), locale, false);
				listaPrestNotarCodes.add(tipoView.getVo().getCodGruppoLingua());
			}

			tipoPrestNotarAggiunte = new String[listaPrestNotarCodes.size()];
			listaPrestNotarCodes.toArray(tipoPrestNotarAggiunte);
			fascicolo.setTipoPrestazioneNotarileAggiunte(tipoPrestNotarAggiunte);
		}

		if (vo.getContropartes() != null) {
			Collection<Controparte> listaControparte = vo.getContropartes();
			ControparteView[] contropartiArray = null;
			List<ControparteView> controparteViews = new ArrayList<ControparteView>();
			for (Controparte controparte : listaControparte) {
				String nomeControparte = controparte.getNome();
				TipoEntitaView tipoEntitaView = anagraficaStatiTipiService
						.leggiTipoEntita(controparte.getTipoEntita().getCodGruppoLingua(), locale, false);
				String tipoControparteCode = tipoEntitaView.getVo().getCodGruppoLingua();
				ControparteView view = new ControparteView();
				view.setNomeControparte(nomeControparte);
				view.setTipoControparte(tipoEntitaView.getVo().getNome());
				view.setTipoControparteCode(tipoControparteCode);

				controparteViews.add(view);
			}
			contropartiArray = new ControparteView[controparteViews.size()];
			controparteViews.toArray(contropartiArray);
			fascicolo.setContropartiAggiunte(contropartiArray);
		}

		Integer valorePratica = vo.getValoreCausaPratica() == null ? null
				: Integer.parseInt(vo.getValoreCausaPratica().toBigInteger() + "");
		fascicolo.setValore(valorePratica);

		fascicolo.setSiglaCliente(vo.getSiglaCliente());
		if (vo.getNumeroArchivio() != null) {
			fascicolo.setNumeroArchivio(Integer.parseInt(vo.getNumeroArchivio() + ""));
		}
		if (vo.getNArchivioContenitore() != null) {
			fascicolo.setNumeroArchivioContenitore(Integer.parseInt(vo.getNArchivioContenitore() + ""));
		}

		if (vo.getTipoContenzioso() != null) {
			fascicolo.setTipoContenziosoCode(vo.getTipoContenzioso().getCodGruppoLingua());
		}
		
		fascicolo.setAutoritaGiudiziaria(vo.getAutoritaGiudiziaria());
		if (vo.getTerzoChiamatoCausas() != null) {
			Collection<TerzoChiamatoCausa> listaTerzoChiamatoCausa = vo.getTerzoChiamatoCausas();
			TerzoChiamatoCausaView[] terzoChiamatoCausaArray = null;
			List<TerzoChiamatoCausaView> terzoChiamatoCausaViews = new ArrayList<TerzoChiamatoCausaView>();
			for (TerzoChiamatoCausa terzoChiamatoCausa : listaTerzoChiamatoCausa) {
				String nome = terzoChiamatoCausa.getNome();

				TipoEntitaView tipoEntitaView = anagraficaStatiTipiService
						.leggiTipoEntita(terzoChiamatoCausa.getTipoEntita().getCodGruppoLingua(), locale, false);
				String tipoCode = tipoEntitaView.getVo().getCodGruppoLingua();
				TerzoChiamatoCausaView view = new TerzoChiamatoCausaView();
				view.setNomeTerzoChiamatoCausa(nome);
				view.setTipoTerzoChiamatoCausa(tipoEntitaView.getVo().getNome());
				view.setTipoTerzoChiamatoCausaCode(tipoCode);

				terzoChiamatoCausaViews.add(view);
			}
			terzoChiamatoCausaArray = new TerzoChiamatoCausaView[terzoChiamatoCausaViews.size()];
			terzoChiamatoCausaViews.toArray(terzoChiamatoCausaArray);
			fascicolo.setTerzoChiamatoInCausaAggiunti(terzoChiamatoCausaArray);
		}

		if (vo.getRilevante() != null) {
			fascicolo.setRilevante(vo.getRilevante().equals(TRUE_CHAR) ? true : false);
		}

		if (vo.getRFascicoloMaterias() != null) {
			Collection<RFascicoloMateria> listaFascicoloMaterie = vo.getRFascicoloMaterias();
			String[] materie = null;
			List<String> materieCode = new ArrayList<String>();
			List<String> materieAggiunteDesc = new ArrayList<String>();
			for (RFascicoloMateria fascicoloMateria : listaFascicoloMaterie) {
				materieCode.add(fascicoloMateria.getMateria().getCodGruppoLingua());
				MateriaView materiaView = materiaService.leggi(fascicoloMateria.getMateria().getCodGruppoLingua(), locale, false);
				materieAggiunteDesc.add(materiaView.getVo().getNome());
			}
			materie = new String[materieCode.size()];
			materieCode.toArray(materie);
			fascicolo.setMaterie(materie);
			fascicolo.setListaMaterieAggiunteDesc(materieAggiunteDesc);
		}
		logger.info("@@DDS popola form da Vo " );
		if (vo.getRCorrelazioneFascicolis() != null) {
			Collection<RCorrelazioneFascicoli> listaFascicoliCorrelati = vo.getRCorrelazioneFascicolis();
			FascicoloView[] fascicoliCorrelati = null;
			List<FascicoloView> fascicoliCorrelatiList = new ArrayList<FascicoloView>();
			for (RCorrelazioneFascicoli fascicoloCorrelato : listaFascicoliCorrelati) {
				FascicoloView fascicolotmp = fascicoloService.leggi(fascicoloCorrelato.getFascicolo2().getId());
				fascicoliCorrelatiList.add(fascicolotmp);
			}
			fascicoliCorrelati = new FascicoloView[fascicoliCorrelatiList.size()];
			fascicoliCorrelatiList.toArray(fascicoliCorrelati);
			fascicolo.setFascicoliCorrelatiAggiunti(fascicoliCorrelati);
		}

		if (vo.getProgetto() != null) {
			fascicolo.setProgettoId(vo.getProgetto().getId());
			fascicolo.setProgettoNome(vo.getProgetto().getNome());
		}

		fascicolo.setAutoritaEmanante(vo.getAutoritaEmanante()); 
		fascicolo.setAutoritaGiudiziaria(vo.getAutoritaGiudiziaria());
		fascicolo.setControinteressato(vo.getControinteressato());
		fascicolo.setResistente(vo.getResistente());
		fascicolo.setRicorrente(vo.getRicorrente());

		if (vo.getRFascicoloGiudizios() != null) {
			Collection<RFascicoloGiudizio> giudizios = vo.getRFascicoloGiudizios();
			List<RFascicoloGiudizioView> listaFascicoloGiudizi = new ArrayList<RFascicoloGiudizioView>();
			RFascicoloGiudizioView[] array = new RFascicoloGiudizioView[giudizios.size()];
			for( RFascicoloGiudizio rFascicoloGiudizio : giudizios ){
				RFascicoloGiudizioView fascicoloGiudizioView = new RFascicoloGiudizioView();
				fascicoloGiudizioView.setVo(rFascicoloGiudizio);  
				fascicoloGiudizioView.setForo(rFascicoloGiudizio.getForo());
				fascicoloGiudizioView.setNote(rFascicoloGiudizio.getNote());
				fascicoloGiudizioView.setNumeroRegistroCausa(rFascicoloGiudizio.getNumeroRegistroCausa());
				listaFascicoloGiudizi.add(fascicoloGiudizioView);
			}  
			listaFascicoloGiudizi.toArray(array);
			fascicolo.setGiudiziAggiunti(array);
		}
		
		if (vo.getRFascicoloRicorsos() != null) {
			Collection<RFascicoloRicorso> ricorsos = vo.getRFascicoloRicorsos();
			List<RFascicoloRicorsoView> listaFascicoloRicorsi = new ArrayList<RFascicoloRicorsoView>();
			RFascicoloRicorsoView[] array = new RFascicoloRicorsoView[ricorsos.size()];
			for( RFascicoloRicorso rFascicoloRicorso : ricorsos ){
				RFascicoloRicorsoView view = new RFascicoloRicorsoView(); 
				view.setVo(rFascicoloRicorso);  
				view.setForo(rFascicoloRicorso.getForo());
				view.setNote(rFascicoloRicorso.getNote());
				view.setNumeroRegistroCausa(rFascicoloRicorso.getNumeroRegistroCausa());
				listaFascicoloRicorsi.add(view);
			}  
			listaFascicoloRicorsi.toArray(array);
			fascicolo.setRicorsiAggiunti(array);
		}

		if (vo.getNazione() != null) {
			fascicolo.setNazioneCode(vo.getNazione().getCodGruppoLingua());
		}

		if (vo.getRFascicoloSocietas() != null) {
			Collection<RFascicoloSocieta> listaFascicoloSocieta = vo.getRFascicoloSocietas();
			String[] societaIdsArrayProcedimento = null;
			String[] societaIdsArrayAddebito = null;
			List<String> societaIdsListaProcedimento = new ArrayList<String>();
			List<String> societaIdsListaAddebito = new ArrayList<String>();
			List<String> societaAddebitoAggiunteDesc = new ArrayList<String>();
			List<String> societaProcAggiunteDesc = new ArrayList<String>();
			
			for (RFascicoloSocieta societa : listaFascicoloSocieta) {

				if (societa.getTipologiaSocieta().equals(SOCIETA_TIPOLOGIA_PARTE_PROCEDIMENTO)) {
					societaIdsListaProcedimento.add(societa.getSocieta().getId() + "");
					societaProcAggiunteDesc.add(societa.getSocieta().getNome());
				} else {
					societaIdsListaAddebito.add(societa.getSocieta().getId() + "");
					societaAddebitoAggiunteDesc.add(societa.getSocieta().getNome());
					if(societa.getPosizioneSocieta()!=null)
						fascicolo.setPosizioneSocietaAddebitoCode(societa.getPosizioneSocieta().getCodGruppoLingua());
				}

			}
			societaIdsArrayProcedimento = new String[societaIdsListaProcedimento.size()];
			societaIdsArrayAddebito = new String[societaIdsListaAddebito.size()];

			societaIdsListaProcedimento.toArray(societaIdsArrayProcedimento);
			societaIdsListaAddebito.toArray(societaIdsArrayAddebito);
			fascicolo.setSocietaProcedimentoAggiunte(societaIdsArrayProcedimento);
			fascicolo.setSocietaAddebitoAggiunte(societaIdsArrayAddebito);
			fascicolo.setListaSocietaProcAggiunteDesc(societaProcAggiunteDesc);
			fascicolo.setListaSocietaAddebitoAggiunteDesc(societaAddebitoAggiunteDesc);
		}

		if (vo.getFascicolo() != null) {
			fascicolo.setFascicoloPadreId(vo.getFascicolo().getId());
			fascicolo.setFascicoloPadreNome(vo.getFascicolo().getNome());
		}

		if (vo.getParteCiviles() != null && vo.getParteCiviles().size() > 0) {
			ParteCivileView[] parteCivileArray = null;
			Collection<ParteCivile> parteCivileLista = vo.getParteCiviles();
			List<ParteCivileView> parteCivileViewLista = new ArrayList<ParteCivileView>();
			for (ParteCivile parteCivile : parteCivileLista) {
				ParteCivileView view = new ParteCivileView();
				
				view.setNomeParteCivile(parteCivile.getNome());
				view.setEncrypted(true);
				TipoEntitaView tipoEntitaView = anagraficaStatiTipiService
						.leggiTipoEntita(parteCivile.getTipoEntita().getCodGruppoLingua(), locale, false);

				view.setTipoParteCivile(tipoEntitaView.getVo().getNome());
				view.setTipoParteCivileCode(tipoEntitaView.getVo().getCodGruppoLingua());
				view.setVo(parteCivile);
				parteCivileViewLista.add(view);

			}
			parteCivileArray = new ParteCivileView[parteCivileViewLista.size()];
			parteCivileViewLista.toArray(parteCivileArray);
			fascicolo.setParteCivileAggiunte(parteCivileArray);
		}

		if (vo.getResponsabileCiviles() != null && vo.getResponsabileCiviles().size() > 0) {
			ResponsabileCivileView[] responsabileCivileArray = null;
			Collection<ResponsabileCivile> responsabileCivileLista = vo.getResponsabileCiviles();
			List<ResponsabileCivileView> responsabileCivileViewLista = new ArrayList<ResponsabileCivileView>();
			for (ResponsabileCivile responsabileCivile : responsabileCivileLista) {
				ResponsabileCivileView view = new ResponsabileCivileView();
				TipoEntitaView tipoEntitaView = anagraficaStatiTipiService
						.leggiTipoEntita(responsabileCivile.getTipoEntita().getCodGruppoLingua(), locale, false);

				view.setEncrypted(true);
				view.setNomeResponsabileCivile(responsabileCivile.getNome());
				view.setTipoResponsabileCivile(tipoEntitaView.getVo().getNome());
				view.setTipoResponsabileCivileCode(tipoEntitaView.getVo().getCodGruppoLingua());
				view.setVo(responsabileCivile);
				responsabileCivileViewLista.add(view);

			}
			responsabileCivileArray = new ResponsabileCivileView[responsabileCivileViewLista.size()];
			responsabileCivileViewLista.toArray(responsabileCivileArray);
			fascicolo.setResponsabileCivileAggiunte(responsabileCivileArray);
		}

		if (vo.getPersonaOffesas() != null && vo.getPersonaOffesas().size() > 0) {
			PersonaOffesaView[] personaOffesaArray = null;
			Collection<PersonaOffesa> personaOffesaLista = vo.getPersonaOffesas();
			List<PersonaOffesaView> personaOffesaViewLista = new ArrayList<PersonaOffesaView>();
			for (PersonaOffesa personaOffesa : personaOffesaLista) {
				PersonaOffesaView view = new PersonaOffesaView();
				TipoEntitaView tipoEntitaView = anagraficaStatiTipiService
						.leggiTipoEntita(personaOffesa.getTipoEntita().getCodGruppoLingua(), locale, false);

				view.setEncrypted(true);
				view.setNomePersonaOffesa(personaOffesa.getNome());
				view.setTipoPersonaOffesa(tipoEntitaView.getVo().getNome());
				view.setTipoPersonaOffesaCode(tipoEntitaView.getVo().getCodGruppoLingua());
				view.setVo(personaOffesa);
				personaOffesaViewLista.add(view);

			}
			personaOffesaArray = new PersonaOffesaView[personaOffesaViewLista.size()];
			personaOffesaViewLista.toArray(personaOffesaArray);
			fascicolo.setPersonaOffesaAggiunte(personaOffesaArray);
		}

		if (vo.getSoggettoIndagatos() != null && vo.getSoggettoIndagatos().size() > 0) {
			SoggettoIndagatoView[] soggettoArray = null;
			Collection<SoggettoIndagato> soggettoLista = vo.getSoggettoIndagatos();
			List<SoggettoIndagatoView> soggettoViewLista = new ArrayList<SoggettoIndagatoView>();
			for (SoggettoIndagato soggetto : soggettoLista) {
				SoggettoIndagatoView view = new SoggettoIndagatoView();
				TipoSoggettoIndagatoView tipo = anagraficaStatiTipiService
						.leggiTipoSoggettoIndagato(soggetto.getTipoSoggettoIndagato().getCodGruppoLingua(), locale, false);

				view.setEncrypted(true);
				view.setNomeSoggettoIndagato(soggetto.getNome());
				view.setTipoSoggettoIndagato(tipo.getVo().getNome());
				view.setTipoSoggettoIndagatoCode(tipo.getVo().getCodGruppoLingua());
				view.setVo(soggetto);
				soggettoViewLista.add(view);
			}

			soggettoArray = new SoggettoIndagatoView[soggettoViewLista.size()];
			soggettoViewLista.toArray(soggettoArray);
			fascicolo.setSoggettoIndagatoAggiunti(soggettoArray);
		}
	}

	@RequestMapping("/fascicolo/crea")
	public String creaFascicolo(HttpServletRequest request, Model model, Locale locale) {
		FascicoloView fascicoloView = new FascicoloView();
		// engsecurity VA
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		try {
			super.caricaListe(fascicoloView, locale);
		} catch (Throwable e) {
			e.printStackTrace();
		} 
		fascicoloView.setVo(new Fascicolo());
		UtenteView utenteConnesso = (UtenteView) request.getSession()
				.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
		if (utenteConnesso != null) { 
			  
			fascicoloView.setUnitaLegale(utenteConnesso.getVo().getCodiceUnitaUtil());
			fascicoloView.setLegaleInterno(utenteConnesso.getVo().getUseridUtil());
			fascicoloView.setUnitaLegaleDesc(utenteConnesso.getCodiceDescrizioneUnitaLegale());
			fascicoloView.setLegaleInternoDesc(utenteConnesso.getCodiceDescrizioneUtente());
		}
		if(request.getParameter("objatto")!=null && !request.getParameter("objatto").trim().equals("")){
			model.addAttribute("idAtto", request.getParameter("objatto"));
		}
		String idProgettoString = request.getParameter("idProgetto");
		if (idProgettoString != null && !idProgettoString.isEmpty()){
			fascicoloView.setProgettoId(new Long(request.getParameter("idProgetto")));
			fascicoloView.setProgettoNome(request.getParameter("nomeProgetto"));
		}
		
		model.addAttribute(MODEL_VIEW_NOME, fascicoloView);
		return PAGINA_FORM_PATH;
	}
 

	@RequestMapping(value = "/fascicolo/autoCompleteControparte", method = RequestMethod.GET)
	public @ResponseBody List<AutoCompleteModel> autoCompleteControparte(@RequestParam("term") String term,
			Locale locale) {


		List<AutoCompleteModel> autocompleteLista = new ArrayList<AutoCompleteModel>();
		try {
			List<ControparteView> lista = controparteService.leggi(locale, term);

			if (lista != null && lista.size() > 0) {
				for (ControparteView view : lista) {
					AutoCompleteModel model = new AutoCompleteModel();
					model.setId(view.getVo().getId() + "");
					model.setLabel(view.getVo().getNome());
					model.setValue(view.getVo().getNome());
					autocompleteLista.add(model);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return autocompleteLista;
	}

	@RequestMapping(value = "/fascicolo/autocompleteTerzoChiamatoInCausa", method = RequestMethod.GET)
	public @ResponseBody List<AutoCompleteModel> autocompleteTerzoChiamatoInCausa(@RequestParam("term") String term,
			Locale locale) {


		List<AutoCompleteModel> autocompleteLista = new ArrayList<AutoCompleteModel>();
		try {
			List<TerzoChiamatoCausaView> lista = terzoChiamatoCausaService.leggiPerAutocomplete(locale, term);

			if (lista != null && lista.size() > 0) {
				for (TerzoChiamatoCausaView view : lista) {
					AutoCompleteModel model = new AutoCompleteModel();
					model.setId(view.getVo().getId() + "");
					model.setLabel(view.getVo().getNome());
					model.setValue(view.getVo().getNome());
					autocompleteLista.add(model);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return autocompleteLista;
	}

	@RequestMapping(value = "/fascicolo/autocompleteForo", method = RequestMethod.GET)
	public @ResponseBody List<AutoCompleteModel> autocompleteForo(@RequestParam("term") String term, Locale locale) {
		List<AutoCompleteModel> autocompleteLista = new ArrayList<AutoCompleteModel>();


		try {
			Collection<String> lista = fascicoloService.leggiPerAutocompleteForo(term);

			if (lista != null && lista.size() > 0) {
				for (String foro : lista) {
					AutoCompleteModel model = new AutoCompleteModel();
					model.setId("");
					model.setLabel(foro);
					model.setValue(foro);
					autocompleteLista.add(model);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return autocompleteLista;
	}

	@RequestMapping(value = "/fascicolo/autocompleteAutoritaEmanante", method = RequestMethod.GET)
	public @ResponseBody List<AutoCompleteModel> autocompleteAutoritaEmanante(@RequestParam("term") String term,
			Locale locale) {


		List<AutoCompleteModel> autocompleteLista = new ArrayList<AutoCompleteModel>();
		try {
			Collection<String> lista = fascicoloService.leggiPerAutocompleteAutoritaEmanante(term);

			if (lista != null && lista.size() > 0) {
				for (String autorita : lista) {
					AutoCompleteModel model = new AutoCompleteModel();
					model.setId("");
					model.setLabel(autorita);
					model.setValue(autorita);
					autocompleteLista.add(model);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return autocompleteLista;
	}

	@RequestMapping(value = "/fascicolo/autocompleteControinteressato", method = RequestMethod.GET)
	public @ResponseBody List<AutoCompleteModel> autocompleteControinteressato(@RequestParam("term") String term,
			Locale locale) {


		List<AutoCompleteModel> autocompleteLista = new ArrayList<AutoCompleteModel>();
		try {
			Collection<String> lista = fascicoloService.leggiPerAutocompleteControinteressato(term);

			if (lista != null && lista.size() > 0) {
				for (String autorita : lista) {
					AutoCompleteModel model = new AutoCompleteModel();
					model.setId("");
					model.setLabel(autorita);
					model.setValue(autorita);
					autocompleteLista.add(model);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return autocompleteLista;
	}

	@RequestMapping(value = "/fascicolo/autocompleteAutoritaGiudiziaria", method = RequestMethod.GET)
	public @ResponseBody List<AutoCompleteModel> autocompleteAutoritaGiudiziaria(@RequestParam("term") String term,
			Locale locale) {


		List<AutoCompleteModel> autocompleteLista = new ArrayList<AutoCompleteModel>();
		try {
			Collection<String> lista = fascicoloService.leggiPerAutocompleteAutoritaGiudiziaria(term);

			if (lista != null && lista.size() > 0) {
				for (String autorita : lista) {
					AutoCompleteModel model = new AutoCompleteModel();
					model.setId("");
					model.setLabel(autorita);
					model.setValue(autorita);
					autocompleteLista.add(model);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return autocompleteLista;
	}

	@RequestMapping(value = "/fascicolo/autocompleteSoggettoIndagato", method = RequestMethod.GET)
	public @ResponseBody List<AutoCompleteModel> autocompleteSoggettoIndagato(@RequestParam("term") String term,
			Locale locale) {


		List<AutoCompleteModel> autocompleteLista = new ArrayList<AutoCompleteModel>();
		try {
			Collection<String> lista = fascicoloService.leggiPerAutocompleteSoggettoIndagato(term);

			if (lista != null && lista.size() > 0) {
				for (String soggetto : lista) {
					AutoCompleteModel model = new AutoCompleteModel();
					model.setId("");
					model.setLabel(soggetto);
					model.setValue(soggetto);
					autocompleteLista.add(model);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return autocompleteLista;
	}

	@RequestMapping(value = "/fascicolo/autocompletePersonaOffesa", method = RequestMethod.GET)
	public @ResponseBody List<AutoCompleteModel> autocompletePersonaOffesa(@RequestParam("term") String term,
			Locale locale) {


		List<AutoCompleteModel> autocompleteLista = new ArrayList<AutoCompleteModel>();
		try {
			Collection<String> lista = fascicoloService.leggiPerAutocompletePersonaOffesa(term);

			if (lista != null && lista.size() > 0) {
				for (String persona : lista) {
					AutoCompleteModel model = new AutoCompleteModel();
					model.setId("");
					model.setLabel(persona);
					model.setValue(persona);
					autocompleteLista.add(model);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return autocompleteLista;
	}

	@RequestMapping(value = "/fascicolo/autocompleteParteCivile", method = RequestMethod.GET)
	public @ResponseBody List<AutoCompleteModel> autocompleteParteCivile(@RequestParam("term") String term,
			Locale locale) {
		List<AutoCompleteModel> autocompleteLista = new ArrayList<AutoCompleteModel>();
		try {
			Collection<String> lista = fascicoloService.leggiPerAutocompleteParteCivile(term);

			if (lista != null && lista.size() > 0) {
				for (String persona : lista) {
					AutoCompleteModel model = new AutoCompleteModel();
					model.setId("");
					model.setLabel(persona);
					model.setValue(persona);
					autocompleteLista.add(model);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return autocompleteLista;
	}

	@RequestMapping(value = "/fascicolo/autocompleteResponsabileCivile", method = RequestMethod.GET)
	public @ResponseBody List<AutoCompleteModel> autocompleteResponsabileCivile(@RequestParam("term") String term,
			Locale locale) {
		List<AutoCompleteModel> autocompleteLista = new ArrayList<AutoCompleteModel>();
		try {
			Collection<String> lista = fascicoloService.leggiPerAutocompleteResponsabileCivile(term);

			if (lista != null && lista.size() > 0) {
				for (String responsabile : lista) {
					AutoCompleteModel model = new AutoCompleteModel();
					model.setId("");
					model.setLabel(responsabile);
					model.setValue(responsabile);
					autocompleteLista.add(model);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return autocompleteLista;
	}

 
	@RequestMapping(value = "/fascicolo/salva", method = RequestMethod.POST)
	public String salvaFascicolo(Locale locale, Model model,
			@ModelAttribute(MODEL_VIEW_NOME) @Validated FascicoloView fascicoloView, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {
		logger.debug("@@DDS ------------------------------------------- FASCICOLO SALVA");
		// engsecurity VA
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
				String token=request.getParameter("CSRFToken");

		try {
			logger.debug("@@DDS ---------- try");
			if(request.getParameter("idAtto")!=null && !request.getParameter("idAtto").trim().equals("")){
				model.addAttribute("idAtto", request.getParameter("idAtto"));	
			}
			
			if (fascicoloView.getOp() != null && !fascicoloView.getOp().equals("salvaFascicolo")) {
				String ritorno = invocaMetodoDaReflection(fascicoloView, bindingResult, locale, model, request,
						response, this);
				logger.debug("@@DDS ---------- getOp");
				return ritorno == null ? PAGINA_FORM_PATH : ritorno;
			}

			if (bindingResult.hasErrors()) {

				return PAGINA_FORM_PATH;
			}
			logger.debug("@@DDS ---------- preparaPerSalvataggio");
			preparaPerSalvataggio(fascicoloView, bindingResult);

			if (bindingResult.hasErrors()) {

				return PAGINA_FORM_PATH;
			}

			long fascicoloId = 0;
			FascicoloView fascicoloSalvato=null;
			logger.debug("@@DDS ---------- fASCICOLO CONTROLLER");
			if (fascicoloView.getFascicoloId() == null || fascicoloView.getFascicoloId() == 0) {
				fascicoloSalvato = fascicoloService.apriFascicolo(fascicoloView);
				fascicoloId = fascicoloSalvato.getVo().getId();
				
				UtenteView utenteConnesso = (UtenteView) request.getSession()
						.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
				
				NotificaWebView notificaWeb = new NotificaWebView();
				notificaWeb.setIdFascicolo(fascicoloId);
				notificaWeb.setNomeFascicolo(fascicoloSalvato.getVo().getNome());
				notificaWeb.setDataInserimento(new Date());
				NotificaWeb notifica = new NotificaWeb();
				notifica.setMatricolaMitt(utenteConnesso.getVo());
				UtenteView matricolaDest = utenteService.leggiUtenteDaMatricola(utenteConnesso.getVo().getMatricolaRespUtil());
				notifica.setMatricolaDest(matricolaDest.getVo()==null?utenteConnesso.getVo():matricolaDest.getVo());
				notifica.setDataNotifica(new Date());
				notifica.setKeyMessage("fascicolo.aperto.indata");
				notifica.setJsonParam(notificaWeb.getJsonParamAperturaRiaperturaFascicolo());
				notificaWeb.setVo(notifica);
				notificaWebService.inserisci(notificaWeb);

			
			} else {
				fascicoloService.aggiornaFascicolo(fascicoloView);
				fascicoloId = fascicoloView.getVo().getId();
			}
			 
			if(request.getParameter("idAtto")!=null && !request.getParameter("idAtto").trim().equals("")){
			// UPDATE ATTO	ASSOCIO IL FASCICOLO ALL'ATTO
				Long idAtto= new Long(request.getParameter("idAtto").toString());
				Atto atto = attoService.getAtto(idAtto.longValue());	 
				if(atto!=null && fascicoloSalvato!=null){
				AttoView attoView = new AttoView();
				atto.setFascicolo(fascicoloSalvato.getVo());
				attoView.setVo(atto);
				attoService.modifica(attoView);
				}
			}
			
			model.addAttribute("successMessage", "messaggio.operazione.ok");
			
			String returnPath = "redirect:dettaglio.action?id=" + fascicoloId+"&CSRFToken="+token;
			
			if(fascicoloView.getVo().getTipologiaFascicolo().getCodGruppoLingua().equals(Costanti.TIPOLOGIA_FASCICOLO_GIUDIZIALE_COD)){
					
				if(fascicoloView.getVo().getRilevante() != null ){

					if((fascicoloView.getVo().getRilevante().equals(TRUE_CHAR))
							&& (fascicoloView.getVo().getSchedaFondoRischi() == null)){
					
						returnPath = "redirect:/schedaFondoRischi/crea.action?fascicoloId=" + fascicoloId+"&CSRFToken="+token;
					}
				}
			}
			return returnPath;

		} catch (Throwable e) {
			model.addAttribute("errorMessage", "errore.generico");
			return "redirect:/errore.action";
		}

	}
 
	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
		List<TipologiaFascicoloView> listaTipologiaFascicolo = tipologiaFascicoloService.leggi(locale, false);
		List<TipoContenziosoView> listaTipoContenzioso = anagraficaStatiTipiService
				.leggiTipoContenzioso(locale.getLanguage().toUpperCase(), false);
		List<ValoreCausaView> listaValoreCausa = anagraficaStatiTipiService
				.leggiValoreCausa(locale.getLanguage().toUpperCase(), false);
		FascicoloView fascicoloView = (FascicoloView) view;
		fascicoloView.setListaTipoContenzioso(listaTipoContenzioso);
		fascicoloView.setListaTipologiaFascicolo(listaTipologiaFascicolo);
		fascicoloView.setListaValoreCausa(listaValoreCausa);

		if (fascicoloView.getFascicoloId() != null && fascicoloView.getFascicoloId() > 0) {
			TipologiaFascicoloView tipologiaFascicoloView = tipologiaFascicoloService
					.leggi(fascicoloView.getTipologiaFascicoloCode(), locale, false);
			List<SettoreGiuridicoView> listaSettoreGiuridico = settoreGiuridicoService
					.leggiPerTipologiaId(tipologiaFascicoloView.getVo().getId(), false);
			fascicoloView.setListaSettoreGiuridico(listaSettoreGiuridico);
			SettoreGiuridicoView settoreGiuridicoView = settoreGiuridicoService
					.leggi(fascicoloView.getSettoreGiuridicoCode(), locale, false);

			long idSettore = fascicoloView.getSettoreGiuridicoCode().equalsIgnoreCase(SETTORE_GIURIDICO_ARBITRALE_CODE)
					|| fascicoloView.getSettoreGiuridicoCode().equalsIgnoreCase(SETTORE_GIURIDICO_JOIN_VENTURE_CODE) ? 0
							: (settoreGiuridicoView.getVo()!=null? settoreGiuridicoView.getVo().getId():0) ;
			if (!fascicoloView.getTipologiaFascicoloCode().equals(TIPOLOGIA_FASCICOLO_NOTARILE_COD)) {
				JSONArray jsonArray = materiaService.leggiAlberaturaMateria(idSettore, locale, true);
				if (jsonArray != null) {
					fascicoloView.setJsonAlberaturaMaterie(jsonArray.toString());
				} else {
					fascicoloView.setJsonAlberaturaMaterie(null);
				}
			}

			if (fascicoloView.getRicorsoCode() != null && fascicoloView.getRicorsoCode().trim().length() > 0) {
				RicorsoView ricorso = ricorsoService.leggi(fascicoloView.getRicorsoCode(), locale, false);
				fascicoloView.setListaOrganoGiudicante(organoGiudicanteService.leggiDaRicorso(ricorso.getVo().getId()));
			}

			if (fascicoloView.getGiudizioCode() != null && fascicoloView.getGiudizioCode().trim().length() > 0) {
				GiudizioView giudizio = giudizioService.leggi(fascicoloView.getGiudizioCode(), locale, false);
				fascicoloView.setListaOrganoGiudicante(organoGiudicanteService.leggiDaGiudizio(giudizio.getVo().getId()));
			}

			if (fascicoloView.getTipologiaFascicoloCode().equals(TIPOLOGIA_FASCICOLO_STRAGIUDIZIALE_COD)) {
				fascicoloView.setListaTipoControparte(
						anagraficaStatiTipiService.leggiTipoEntita(locale.getLanguage().toUpperCase(), false));

			}

			if (fascicoloView.getSettoreGiuridicoCode().equalsIgnoreCase(SETTORE_GIURIDICO_CIVILE_CODE)) {
				fascicoloView.setListaTipoControparte(
						anagraficaStatiTipiService.leggiTipoEntita(locale.getLanguage().toUpperCase(), false));
				fascicoloView.setListaGiudizio(
						giudizioService.leggiDaSettoreGiuridicoId( (settoreGiuridicoView.getVo()!=null?settoreGiuridicoView.getVo().getId():0), false));
				fascicoloView.setListaTipoSoggetto(
						anagraficaStatiTipiService.leggiTipoEntita(locale.getLanguage().toUpperCase(), false));

				fascicoloView.setListaPosizioneSocieta(anagraficaStatiTipiService
						.leggiPosizioneSocietaPerSettoreGiuridicoId( (settoreGiuridicoView.getVo()!=null?settoreGiuridicoView.getVo().getId():0), false));

			} else if (fascicoloView.getSettoreGiuridicoCode()
					.equalsIgnoreCase(SETTORE_GIURIDICO_AMMINISTRATIVO_CODE)) {

				fascicoloView.setListaTipoSoggetto(
						anagraficaStatiTipiService.leggiTipoEntita(locale.getLanguage().toUpperCase(), false));
				fascicoloView.setListaRicorso(ricorsoService.leggi(locale, false));
				
				fascicoloView.setListaPosizioneSocieta(anagraficaStatiTipiService
						.leggiPosizioneSocietaPerSettoreGiuridicoId( (settoreGiuridicoView.getVo()!=null?settoreGiuridicoView.getVo().getId():0), false));

			} else if (fascicoloView.getSettoreGiuridicoCode().equalsIgnoreCase(SETTORE_GIURIDICO_PENALE_CODE)) {
				fascicoloView.setListaTipoSoggetto(
						anagraficaStatiTipiService.leggiTipoEntita(locale.getLanguage().toUpperCase(), false));
				fascicoloView.setListaGiudizio(
						giudizioService.leggiDaSettoreGiuridicoId( (settoreGiuridicoView.getVo()!=null?settoreGiuridicoView.getVo().getId():0) , false));
				fascicoloView.setListaTipoSoggettoIndagato(
						anagraficaStatiTipiService.leggiTipoSoggettoIndagato(locale.getLanguage().toUpperCase(), false));

			} else if (fascicoloView.getSettoreGiuridicoCode().equalsIgnoreCase(SETTORE_GIURIDICO_ARBITRALE_CODE)) {
				fascicoloView.setListaTipoControparte(
						anagraficaStatiTipiService.leggiTipoEntita(locale.getLanguage().toUpperCase(), false));

			}
			
			if (fascicoloView.getTipologiaFascicoloCode().equalsIgnoreCase(TIPOLOGIA_FASCICOLO_NOTARILE_COD)) {
				 fascicoloView.setListaTipoPrestazioneNotarile(
						anagraficaStatiTipiService.leggiTipoPrestazioneNotarile(locale.getLanguage().toUpperCase(), false)); 
			}

		}

	}

	private void preparaPerSalvataggio(FascicoloView fascicoloView, BindingResult bindingResult) throws Throwable {
		Fascicolo fascicolo = new Fascicolo();

		if (fascicoloView.getFascicoloId() != null) { 
			FascicoloView oldFascicoloView = fascicoloService.leggi(fascicoloView.getFascicoloId());
			fascicolo.setId(fascicoloView.getFascicoloId());
			fascicolo.setNome(fascicoloView.getNome()); 
			fascicolo.setStatoFascicolo(oldFascicoloView.getVo().getStatoFascicolo());
			fascicolo.setDataCreazione(oldFascicoloView.getVo().getDataCreazione());
		}

		Locale linguaItaliana = Locale.ITALIAN;
		SettoreGiuridicoView settoreGiuridicoView = settoreGiuridicoService
				.leggi(fascicoloView.getSettoreGiuridicoCode(), linguaItaliana, false);
		TipologiaFascicoloView tipologiaFascicoloView = tipologiaFascicoloService
				.leggi(fascicoloView.getTipologiaFascicoloCode(), linguaItaliana, false);

		ValoreCausaView valoreCausaView = anagraficaStatiTipiService
				.leggiValoreCausa(fascicoloView.getValoreCausaCode(), linguaItaliana.getLanguage().toUpperCase(), false);
		fascicolo.setValoreCausa(valoreCausaView.getVo());
		fascicolo.setTipologiaFascicolo(tipologiaFascicoloView.getVo());
		fascicolo.setSettoreGiuridico(settoreGiuridicoView.getVo());
		fascicolo.setDescrizione(fascicoloView.getDescrizione()); 
		fascicolo.setOggettoSintetico(fascicoloView.getOggettoSintetico());
		fascicolo.setLegaleInterno(fascicoloView.getLegaleInterno());
		fascicolo.setCentroDiCosto(fascicoloView.getCentroDiCosto());
		fascicolo.setVoceDiConto(fascicoloView.getVoceDiConto());
		
		if (fascicoloView.getPartnerId() != null) {
			SocietaView partner = societaService.leggi(fascicoloView.getPartnerId());
			fascicolo.setSocieta(partner.getVo());
		}

		fascicolo.setJointVenture(fascicoloView.getTipoJoinVenture());
		fascicolo.setTarget(fascicoloView.getTarget());

		if (fascicoloView.getContropartiAggiunte() != null) {
			Set<Controparte> listaControparte = new HashSet<Controparte>();
			ControparteView[] contropartiArray = fascicoloView.getContropartiAggiunte();
			for (ControparteView controparteView : contropartiArray) {

				String nomeControparte = controparteView.getNomeControparte();
				String tipoControparteCode = controparteView.getTipoControparteCode();
				Controparte controparte = new Controparte();
				controparte.setLang(linguaItaliana.getLanguage().toUpperCase());
				controparte.setNome(nomeControparte);
				TipoEntitaView tipoControparte = anagraficaStatiTipiService.leggiTipoEntita(tipoControparteCode,
						linguaItaliana, false);

				controparte.setTipoEntita(tipoControparte.getVo());
				listaControparte.add(controparte);
			}

			fascicolo.setContropartes(listaControparte);
		}

		if (fascicoloView.getValore() != null) {
			fascicolo.setValoreCausaPratica(new BigDecimal(fascicoloView.getValore()));
		}

		fascicolo.setSiglaCliente(fascicoloView.getSiglaCliente());
		if (fascicoloView.getNumeroArchivio() != null) {
			fascicolo.setNumeroArchivio(new BigDecimal(fascicoloView.getNumeroArchivio()));
		}
		if (fascicoloView.getNumeroArchivioContenitore() != null) {
			fascicolo.setNArchivioContenitore(new BigDecimal(fascicoloView.getNumeroArchivioContenitore()));
		}

		if (StringUtils.isNotBlank(fascicoloView.getTipoContenziosoCode())) {
			TipoContenziosoView tipoContenziosoView = anagraficaStatiTipiService.leggiTipoContenzioso(
					fascicoloView.getTipoContenziosoCode(), linguaItaliana.getLanguage().toUpperCase(), false);
			fascicolo.setTipoContenzioso(tipoContenziosoView.getVo());
		}
 
		fascicolo.setAutoritaGiudiziaria(fascicolo.getAutoritaGiudiziaria());
		if (fascicoloView.getTerzoChiamatoInCausaAggiunti() != null) {
			Set<TerzoChiamatoCausa> listaTerzoChiamatoCausa = new HashSet<TerzoChiamatoCausa>();
			TerzoChiamatoCausaView[] terzoChiamatoCausaArray = fascicoloView.getTerzoChiamatoInCausaAggiunti();
			for (TerzoChiamatoCausaView terzoChiamatoCausaView : terzoChiamatoCausaArray) {
				TerzoChiamatoCausa terzoChiamatoCausa = new TerzoChiamatoCausa();
				terzoChiamatoCausa.setNome(terzoChiamatoCausaView.getNomeTerzoChiamatoCausa());
				terzoChiamatoCausa.setLegaleRiferimento(terzoChiamatoCausaView.getLegaleRiferimento());
				terzoChiamatoCausa.setLang(linguaItaliana.getLanguage().toUpperCase());
				terzoChiamatoCausa.setTipoEntita(anagraficaStatiTipiService
						.leggiTipoEntita(terzoChiamatoCausaView.getTipoTerzoChiamatoCausaCode(), linguaItaliana, false)
						.getVo());
				listaTerzoChiamatoCausa.add(terzoChiamatoCausa);
			}

			fascicolo.setTerzoChiamatoCausas(listaTerzoChiamatoCausa);
		}

		if (fascicoloView.getRilevante() != null) {
			fascicolo.setRilevante(fascicoloView.getRilevante() ? TRUE_CHAR : FALSE_CHAR);
		}
		if (fascicoloView.getMaterie() != null && fascicoloView.getMaterie().length > 0) {
			Set<RFascicoloMateria> listaFascicoloMaterie = new HashSet<RFascicoloMateria>();
			String[] materieCodes = fascicoloView.getMaterie();
			for (String codice : materieCodes) {
				RFascicoloMateria fascicoloMateria = new RFascicoloMateria();
				MateriaView materiaView = materiaService.leggi(codice, linguaItaliana, false);
				fascicoloMateria.setMateria(materiaView.getVo());
				listaFascicoloMaterie.add(fascicoloMateria);
			}
			fascicolo.setRFascicoloMaterias(listaFascicoloMaterie);
		}

		if (fascicoloView.getFascicoliCorrelatiAggiunti() != null && fascicoloView.getFascicoliCorrelatiAggiunti().length > 0) {
			Set<RCorrelazioneFascicoli> listaFascicoliCorrelati = new HashSet<RCorrelazioneFascicoli>();
			FascicoloView[] fascicoliCorrelati = fascicoloView.getFascicoliCorrelatiAggiunti();
			for (FascicoloView fascicoloCorrelatoView : fascicoliCorrelati) {
				RCorrelazioneFascicoli fascicoloCorrelato = new RCorrelazioneFascicoli(); 
				fascicoloCorrelato.setFascicolo2(fascicoloCorrelatoView.getVo());
				listaFascicoliCorrelati.add(fascicoloCorrelato);
			}
			fascicolo.setRCorrelazioneFascicolis(listaFascicoliCorrelati);
		}

		if (fascicoloView.getProgettoId() != null && fascicoloView.getProgettoId() > 0) {
			ProgettoView progettoView = progettoService.leggi(fascicoloView.getProgettoId());
			fascicolo.setProgetto(progettoView.getVo());
		}

		if (StringUtils.isNotBlank(fascicoloView.getAutoritaEmanante())) {
			fascicolo.setAutoritaEmanante(fascicoloView.getAutoritaEmanante());
		}

		if (StringUtils.isNotBlank(fascicoloView.getControinteressato())) {
			fascicolo.setControinteressato(fascicoloView.getControinteressato());
		}
		
		if (StringUtils.isNotBlank(fascicoloView.getResistente())) {
			fascicolo.setResistente(fascicoloView.getResistente());
		}
		
		if (StringUtils.isNotBlank(fascicoloView.getRicorrente())) {
			fascicolo.setRicorrente(fascicoloView.getRicorrente());
		}

		if (fascicoloView.getGiudiziAggiunti() != null
				&& fascicoloView.getGiudiziAggiunti().length > 0) {
			RFascicoloGiudizioView[] array = fascicoloView.getGiudiziAggiunti();
			Set<RFascicoloGiudizio> fascicoloGiudizi = new HashSet<RFascicoloGiudizio>();
			for(RFascicoloGiudizioView view : array ){
				OrganoGiudicanteView organoGiudicanteView = organoGiudicanteService
						.leggi(view.getVo().getOrganoGiudicante().getCodGruppoLingua(), linguaItaliana);
				GiudizioView giudizio = giudizioService.leggi(view.getVo().getGiudizio().getCodGruppoLingua(), linguaItaliana, false);
				RFascicoloGiudizio fascicoloGiudizio = new RFascicoloGiudizio();
				fascicoloGiudizio.setOrganoGiudicante(organoGiudicanteView.getVo());
				fascicoloGiudizio.setForo(view.getForo());
				fascicoloGiudizio.setNote(view.getNote());
				fascicoloGiudizio.setNumeroRegistroCausa(view.getNumeroRegistroCausa());
				fascicoloGiudizio.setGiudizio(giudizio.getVo());
				fascicoloGiudizi.add(fascicoloGiudizio);
			}
		
			fascicolo.setRFascicoloGiudizios(fascicoloGiudizi);
			 
		}
		
		if (fascicoloView.getRicorsiAggiunti() != null
				&& fascicoloView.getRicorsiAggiunti().length > 0) {
			RFascicoloRicorsoView[] array = fascicoloView.getRicorsiAggiunti();
			Set<RFascicoloRicorso> fascicoloRicorsi = new HashSet<RFascicoloRicorso>();
			for(RFascicoloRicorsoView view : array ){
				OrganoGiudicanteView organoGiudicanteView = organoGiudicanteService
						.leggi(view.getVo().getOrganoGiudicante().getCodGruppoLingua(), linguaItaliana);
				RicorsoView ricorso = ricorsoService.leggi(view.getVo().getRicorso().getCodGruppoLingua(), linguaItaliana, false);
				RFascicoloRicorso fascicoloRicorso = new RFascicoloRicorso();
				fascicoloRicorso.setOrganoGiudicante(organoGiudicanteView.getVo());
				fascicoloRicorso.setForo(view.getForo());
				fascicoloRicorso.setNote(view.getNote());
				fascicoloRicorso.setNumeroRegistroCausa(view.getNumeroRegistroCausa());
				fascicoloRicorso.setRicorso(ricorso.getVo());
				fascicoloRicorsi.add(fascicoloRicorso);
			}
		
			fascicolo.setRFascicoloRicorsos(fascicoloRicorsi);
			 
		}

		if (fascicoloView.getNazioneCode() != null && fascicoloView.getNazioneCode().trim().length() > 0) {
			NazioneView nazioneView = nazioneService.leggi(fascicoloView.getNazioneCode(), linguaItaliana, false);
			fascicolo.setNazione(nazioneView.getVo());
		}

		if (fascicoloView.getSocietaProcedimentoAggiunte() != null
				&& fascicoloView.getSocietaProcedimentoAggiunte().length > 0) {
			Set<RFascicoloSocieta> listaFascicoloSocieta = new HashSet<RFascicoloSocieta>();
			String[] societaIdsArray = fascicoloView.getSocietaProcedimentoAggiunte();
			for (String societaIds : societaIdsArray) {
				SocietaView societaView = societaService.leggi(NumberUtils.toLong(societaIds));
				RFascicoloSocieta fascicoloSocieta = new RFascicoloSocieta();
				fascicoloSocieta.setSocieta(societaView.getVo());
				fascicoloSocieta.setTipologiaSocieta(SOCIETA_TIPOLOGIA_PARTE_PROCEDIMENTO);
				listaFascicoloSocieta.add(fascicoloSocieta);
			}
			fascicolo.setRFascicoloSocietas(listaFascicoloSocieta);

		}

		if (fascicoloView.getSocietaAddebitoAggiunte() != null
				&& fascicoloView.getSocietaAddebitoAggiunte().length > 0) {
			String[] societaIdsArray = fascicoloView.getSocietaAddebitoAggiunte();
			Set<RFascicoloSocieta> listaFascicoloSocieta = new HashSet<RFascicoloSocieta>();

			for (String societaIds : societaIdsArray) {
				SocietaView societaView = societaService.leggi(NumberUtils.toLong(societaIds));
				RFascicoloSocieta fascicoloSocieta = new RFascicoloSocieta();
				fascicoloSocieta.setSocieta(societaView.getVo());
				fascicoloSocieta.setTipologiaSocieta(SOCIETA_TIPOLOGIA_ADDEBITO);
				if (fascicoloView.getPosizioneSocietaAddebitoCode() != null) {
					PosizioneSocietaView posizioneSocietaView = anagraficaStatiTipiService.leggiPosizioneSocieta(
							fascicoloView.getPosizioneSocietaAddebitoCode(),
							linguaItaliana.getLanguage().toUpperCase(), false);
					fascicoloSocieta.setPosizioneSocieta(posizioneSocietaView.getVo());
				}
				listaFascicoloSocieta.add(fascicoloSocieta);
			}
			if (fascicoloView.getSocietaProcedimentoAggiunte() != null
					&& fascicoloView.getSocietaProcedimentoAggiunte().length > 0) {
				Set<RFascicoloSocieta> listaSocieta = fascicolo.getRFascicoloSocietas();
				if (listaSocieta != null) {
					listaFascicoloSocieta.addAll(listaSocieta);
				}
			}
			fascicolo.setRFascicoloSocietas(listaFascicoloSocieta);
		}

		if (fascicoloView.getTipoPrestazioneNotarileAggiunte() != null
				&& fascicoloView.getTipoPrestazioneNotarileAggiunte().length > 0) {
			String[] tipoPrestazioneNotarileArray = fascicoloView.getTipoPrestazioneNotarileAggiunte();
			Set<RFascPrestNotar> listaFascicoloPrestNotar = new HashSet<RFascPrestNotar>();

			for (String codice : tipoPrestazioneNotarileArray) {
				TipoPrestNotarileView tipoPrestNotarileView = anagraficaStatiTipiService
						.leggiTipoPrestazioneNotarile(codice, linguaItaliana, false);
				RFascPrestNotar fascPrestNotar = new RFascPrestNotar();
				fascPrestNotar.setTipoPrestNotarile(tipoPrestNotarileView.getVo());

				listaFascicoloPrestNotar.add(fascPrestNotar);
			}
			fascicolo.setRFascPrestNotars(listaFascicoloPrestNotar);
		}

		if (fascicoloView.getFascicoloPadreId() != null && fascicoloView.getFascicoloPadreId() > 0) {
			FascicoloView fascicoloPadreView = fascicoloService.leggi(fascicoloView.getFascicoloPadreId());
			fascicolo.setFascicolo(fascicoloPadreView.getVo());
		}

		if (fascicoloView.getParteCivileAggiunte() != null && fascicoloView.getParteCivileAggiunte().length > 0) {
			ParteCivileView[] parteCivileArray = fascicoloView.getParteCivileAggiunte();
			Set<ParteCivile> parteCivileLista = new HashSet<ParteCivile>();
			for (ParteCivileView view : parteCivileArray) {
				ParteCivile vo = new ParteCivile();
				vo.setLang(linguaItaliana.getLanguage().toUpperCase()); 
				if( view.isEncrypted() ){
					DecryptTag decryptTag = new DecryptTag();
					vo.setNome(decryptTag.decrypt(view.getNomeParteCivile()));
				}else{
					vo.setNome(view.getNomeParteCivile());
				}  
				TipoEntitaView tipoEntita = anagraficaStatiTipiService.leggiTipoEntita(view.getTipoParteCivileCode(),
						linguaItaliana, false);
				vo.setTipoEntita(tipoEntita.getVo());
				parteCivileLista.add(vo);

			}
			fascicolo.setParteCiviles(parteCivileLista);
		}

		if (fascicoloView.getResponsabileCivileAggiunte() != null
				&& fascicoloView.getResponsabileCivileAggiunte().length > 0) {
			ResponsabileCivileView[] responsabileCivileArray = fascicoloView.getResponsabileCivileAggiunte();
			Set<ResponsabileCivile> responsabileCivileLista = new HashSet<ResponsabileCivile>();
			for (ResponsabileCivileView view : responsabileCivileArray) {
				ResponsabileCivile vo = new ResponsabileCivile();
				vo.setLang(linguaItaliana.getLanguage().toUpperCase());
				if( view.isEncrypted() ){
					DecryptTag decryptTag = new DecryptTag();
					vo.setNome(decryptTag.decrypt(view.getNomeResponsabileCivile()));
				}else{
					vo.setNome(view.getNomeResponsabileCivile());
				}   
				TipoEntitaView tipoEntita = anagraficaStatiTipiService
						.leggiTipoEntita(view.getTipoResponsabileCivileCode(), linguaItaliana, false);
				vo.setTipoEntita(tipoEntita.getVo());
				responsabileCivileLista.add(vo);

			}
			fascicolo.setResponsabileCiviles(responsabileCivileLista);
		}

		if (fascicoloView.getPersonaOffesaAggiunte() != null && fascicoloView.getPersonaOffesaAggiunte().length > 0) {
			PersonaOffesaView[] personaOffesaArray = fascicoloView.getPersonaOffesaAggiunte();
			Set<PersonaOffesa> personaOffesaLista = new HashSet<PersonaOffesa>();
			for (PersonaOffesaView view : personaOffesaArray) {
				PersonaOffesa vo = new PersonaOffesa();
				vo.setLang(linguaItaliana.getLanguage().toUpperCase());
				if( view.isEncrypted() ){
					DecryptTag decryptTag = new DecryptTag();
					vo.setNome(decryptTag.decrypt(view.getNomePersonaOffesa()));
				}else{
					vo.setNome(view.getNomePersonaOffesa());
				}    
				TipoEntitaView tipoEntita = anagraficaStatiTipiService.leggiTipoEntita(view.getTipoPersonaOffesaCode(),
						linguaItaliana, false);
				vo.setTipoEntita(tipoEntita.getVo());
				personaOffesaLista.add(vo);

			}
			fascicolo.setPersonaOffesas(personaOffesaLista);
		}

		if (fascicoloView.getSoggettoIndagatoAggiunti() != null
				&& fascicoloView.getSoggettoIndagatoAggiunti().length > 0) {
			SoggettoIndagatoView[] soggettoArray = fascicoloView.getSoggettoIndagatoAggiunti();
			Set<SoggettoIndagato> soggettoLista = new HashSet<SoggettoIndagato>();
			for (SoggettoIndagatoView view : soggettoArray) {
				SoggettoIndagato vo = new SoggettoIndagato();
				vo.setLang(linguaItaliana.getLanguage().toUpperCase());
				if( view.isEncrypted() ){
					DecryptTag decryptTag = new DecryptTag();
					vo.setNome(decryptTag.decrypt(view.getNomeSoggettoIndagato()));
				}else{
					vo.setNome(view.getNomeSoggettoIndagato());
				}     
				TipoSoggettoIndagatoView tipo = anagraficaStatiTipiService
						.leggiTipoSoggettoIndagato(view.getTipoSoggettoIndagatoCode(), linguaItaliana, false);
				vo.setTipoSoggettoIndagato(tipo.getVo());
				soggettoLista.add(vo);

			}
			fascicolo.setSoggettoIndagatos(soggettoLista);
		}
		 
		fascicolo.setAutoritaGiudiziaria(fascicoloView.getAutoritaGiudiziaria());
		
		fascicolo.setTitolo(fascicoloView.getTitolo());
		
		fascicoloView.setVo(fascicolo);
	}

	public String rimuoviTerzoChiamatoCausa(FascicoloView fascicoloView, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (fascicoloView.getTerzoChiamatoInCausaAggiunti() != null
					&& fascicoloView.getTerzoChiamatoInCausaAggiunti().length > 0
					&& request.getParameter("terzoChiamatoCausaIndex") != null) {
				TerzoChiamatoCausaView[] terzoChiamatoCausaArray = fascicoloView.getTerzoChiamatoInCausaAggiunti();
				int indiceDaRimuovere = NumberUtils.toInt(request.getParameter("terzoChiamatoCausaIndex"));
				List<TerzoChiamatoCausaView> terzoChiamatoCausaList = new ArrayList<TerzoChiamatoCausaView>();
				int indice = 0;
				for (TerzoChiamatoCausaView terzoChiamatoCausa : terzoChiamatoCausaArray) {
					if (indiceDaRimuovere != indice) {
						terzoChiamatoCausaList.add(terzoChiamatoCausa);
					}
					indice++;
				}

				terzoChiamatoCausaArray = new TerzoChiamatoCausaView[terzoChiamatoCausaList.size()];
				terzoChiamatoCausaList.toArray(terzoChiamatoCausaArray);
				fascicoloView.setTerzoChiamatoInCausaAggiunti(terzoChiamatoCausaArray);
			}
		} catch (Throwable e) {

			e.printStackTrace();
		}

		request.setAttribute("anchorName", "anchorTerzoChiamatoCausa");
		return PAGINA_FORM_PATH;
	}

	public String aggiungiSoggettoIndagato(FascicoloView fascicoloView, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (fascicoloView.getTipoSoggettoIndagatoCode() == null
					|| fascicoloView.getTipoSoggettoIndagatoCode().trim().length() == 0) {
				bindingResult.addError(
						new ObjectError("erroreGenerico", "errore.campo.obbligatorio.tipologiaSoggettoIndagato"));
				return PAGINA_FORM_PATH;
			}

			if (fascicoloView.getNomeSoggettoIndagato() != null
					&& fascicoloView.getNomeSoggettoIndagato().trim().length() > 0) {
				if (fascicoloView.getSoggettoIndagatoAggiunti() != null
						&& fascicoloView.getSoggettoIndagatoAggiunti().length > 0) {
					SoggettoIndagatoView[] soggettiArray = fascicoloView.getSoggettoIndagatoAggiunti();
					List<SoggettoIndagatoView> soggettiList = new ArrayList<SoggettoIndagatoView>();
					soggettiList.addAll(Arrays.asList(soggettiArray));
					SoggettoIndagatoView soggettoView = new SoggettoIndagatoView();
					soggettoView.setNomeSoggettoIndagato(fascicoloView.getNomeSoggettoIndagato());
					TipoSoggettoIndagatoView tipoSoggettoView = anagraficaStatiTipiService.leggiTipoSoggettoIndagato(
							fascicoloView.getTipoSoggettoIndagatoCode(), fascicoloView.getLocale(), false);
					soggettoView.setTipoSoggettoIndagatoCode(tipoSoggettoView.getVo().getCodGruppoLingua());
					soggettoView.setTipoSoggettoIndagato(tipoSoggettoView.getVo().getNome());
					soggettiList.add(soggettoView);
					soggettiArray = new SoggettoIndagatoView[soggettiList.size()];
					soggettiList.toArray(soggettiArray);
					fascicoloView.setSoggettoIndagatoAggiunti(soggettiArray);
				} else {
					SoggettoIndagatoView soggettoView = new SoggettoIndagatoView();
					soggettoView.setNomeSoggettoIndagato(fascicoloView.getNomeSoggettoIndagato());
					TipoSoggettoIndagatoView tipoSoggettoView = anagraficaStatiTipiService.leggiTipoSoggettoIndagato(
							fascicoloView.getTipoSoggettoIndagatoCode(), fascicoloView.getLocale(), false);
					soggettoView.setTipoSoggettoIndagatoCode(tipoSoggettoView.getVo().getCodGruppoLingua());
					soggettoView.setTipoSoggettoIndagato(tipoSoggettoView.getVo().getNome());
					SoggettoIndagatoView[] soggettiArray = new SoggettoIndagatoView[] { soggettoView };
					fascicoloView.setSoggettoIndagatoAggiunti(soggettiArray);
				}
				fascicoloView.setNomeSoggettoIndagato("");
			} else {
				bindingResult.addError(new ObjectError("erroreGenerico", "errore.campo.obbligatorio.soggettoIndagato"));
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}

		request.setAttribute("anchorName", "anchorSoggettoIndagato");
		return PAGINA_FORM_PATH;
	}

	public String rimuoviSoggettoIndagato(FascicoloView fascicoloView, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (fascicoloView.getSoggettoIndagatoAggiunti() != null
					&& fascicoloView.getSoggettoIndagatoAggiunti().length > 0
					&& request.getParameter("soggettoIndagatoIndex") != null) {
				int indiceDaRimuovere = NumberUtils.toInt(request.getParameter("soggettoIndagatoIndex"));
				SoggettoIndagatoView[] soggettiArray = fascicoloView.getSoggettoIndagatoAggiunti();
				List<SoggettoIndagatoView> soggettiList = new ArrayList<SoggettoIndagatoView>();
				int indice = 0;
				for (SoggettoIndagatoView soggetto : soggettiArray) {
					if (indiceDaRimuovere != indice) {
						soggettiList.add(soggetto);
					}
					indice++;
				}

				soggettiArray = new SoggettoIndagatoView[soggettiList.size()];
				soggettiList.toArray(soggettiArray);
				fascicoloView.setSoggettoIndagatoAggiunti(soggettiArray);
			}
		} catch (Throwable e) {

			e.printStackTrace();
		}

		request.setAttribute("anchorName", "anchorSoggettoIndagato");
		return PAGINA_FORM_PATH;
	}

	public String aggiungiPersonaOffesa(FascicoloView fascicoloView, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (fascicoloView.getTipoPersonaOffesaCode() == null
					|| fascicoloView.getTipoPersonaOffesaCode().trim().length() == 0) {
				bindingResult.addError(
						new ObjectError("erroreGenerico", "errore.campo.obbligatorio.tipologiaPersonaOffesa"));
				return PAGINA_FORM_PATH;
			}

			if (fascicoloView.getNomePersonaOffesa() != null
					&& fascicoloView.getNomePersonaOffesa().trim().length() > 0) {
				if (fascicoloView.getPersonaOffesaAggiunte() != null
						&& fascicoloView.getPersonaOffesaAggiunte().length > 0) {
					PersonaOffesaView[] soggettiArray = fascicoloView.getPersonaOffesaAggiunte();
					List<PersonaOffesaView> soggettiList = new ArrayList<PersonaOffesaView>();
					soggettiList.addAll(Arrays.asList(soggettiArray));
					PersonaOffesaView soggettoView = new PersonaOffesaView();
					soggettoView.setNomePersonaOffesa(fascicoloView.getNomePersonaOffesa());
					TipoEntitaView tipoSoggettoView = anagraficaStatiTipiService
							.leggiTipoEntita(fascicoloView.getTipoPersonaOffesaCode(), fascicoloView.getLocale(), false);
					soggettoView.setTipoPersonaOffesaCode(tipoSoggettoView.getVo().getCodGruppoLingua());
					soggettoView.setTipoPersonaOffesa(tipoSoggettoView.getVo().getNome());
					soggettiList.add(soggettoView);
					soggettiArray = new PersonaOffesaView[soggettiList.size()];
					soggettiList.toArray(soggettiArray);
					fascicoloView.setPersonaOffesaAggiunte(soggettiArray);
				} else {
					PersonaOffesaView soggettoView = new PersonaOffesaView();
					soggettoView.setNomePersonaOffesa(fascicoloView.getNomePersonaOffesa());
					TipoEntitaView tipoSoggettoView = anagraficaStatiTipiService
							.leggiTipoEntita(fascicoloView.getTipoPersonaOffesaCode(), fascicoloView.getLocale(), false);
					soggettoView.setTipoPersonaOffesaCode(tipoSoggettoView.getVo().getCodGruppoLingua());
					soggettoView.setTipoPersonaOffesa(tipoSoggettoView.getVo().getNome());
					PersonaOffesaView[] soggettiArray = new PersonaOffesaView[] { soggettoView };
					fascicoloView.setPersonaOffesaAggiunte(soggettiArray);
				}
				fascicoloView.setNomePersonaOffesa("");
			} else {
				bindingResult.addError(new ObjectError("erroreGenerico", "errore.campo.obbligatorio.personaOffesa"));
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}

		request.setAttribute("anchorName", "anchorPersonaOffesa");
		return PAGINA_FORM_PATH;
	}

	public String rimuoviPersonaOffesa(FascicoloView fascicoloView, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (fascicoloView.getPersonaOffesaAggiunte() != null && fascicoloView.getPersonaOffesaAggiunte().length > 0
					&& request.getParameter("personaOffesaIndex") != null) {
				int indiceDaRimuovere = NumberUtils.toInt(request.getParameter("personaOffesaIndex"));
				PersonaOffesaView[] soggettiArray = fascicoloView.getPersonaOffesaAggiunte();
				List<PersonaOffesaView> soggettiList = new ArrayList<PersonaOffesaView>();
				int indice = 0;
				for (PersonaOffesaView soggetto : soggettiArray) {
					if (indiceDaRimuovere != indice) {
						soggettiList.add(soggetto);
					}
					indice++;
				}

				soggettiArray = new PersonaOffesaView[soggettiList.size()];
				soggettiList.toArray(soggettiArray);
				fascicoloView.setPersonaOffesaAggiunte(soggettiArray);
			}
		} catch (Throwable e) {

			e.printStackTrace();
		}

		request.setAttribute("anchorName", "anchorPersonaOffesa");
		return PAGINA_FORM_PATH;
	}

	public String aggiungiParteCivile(FascicoloView fascicoloView, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (fascicoloView.getTipoParteCivileCode() == null
					|| fascicoloView.getTipoParteCivileCode().trim().length() == 0) {
				bindingResult
						.addError(new ObjectError("erroreGenerico", "errore.campo.obbligatorio.tipologiaParteCivile"));
				return PAGINA_FORM_PATH;
			}

			if (fascicoloView.getNomeParteCivile() != null && fascicoloView.getNomeParteCivile().trim().length() > 0) {
				if (fascicoloView.getParteCivileAggiunte() != null
						&& fascicoloView.getParteCivileAggiunte().length > 0) {
					ParteCivileView[] soggettiArray = fascicoloView.getParteCivileAggiunte();
					List<ParteCivileView> soggettiList = new ArrayList<ParteCivileView>();
					soggettiList.addAll(Arrays.asList(soggettiArray));
					ParteCivileView soggettoView = new ParteCivileView();
					soggettoView.setNomeParteCivile(fascicoloView.getNomeParteCivile());
					TipoEntitaView tipoSoggettoView = anagraficaStatiTipiService
							.leggiTipoEntita(fascicoloView.getTipoParteCivileCode(), fascicoloView.getLocale(), false);
					soggettoView.setTipoParteCivileCode(tipoSoggettoView.getVo().getCodGruppoLingua());
					soggettoView.setTipoParteCivile(tipoSoggettoView.getVo().getNome());
					soggettiList.add(soggettoView);
					soggettiArray = new ParteCivileView[soggettiList.size()];
					soggettiList.toArray(soggettiArray);
					fascicoloView.setParteCivileAggiunte(soggettiArray);
				} else {
					ParteCivileView soggettoView = new ParteCivileView();
					soggettoView.setNomeParteCivile(fascicoloView.getNomeParteCivile());
					TipoEntitaView tipoSoggettoView = anagraficaStatiTipiService
							.leggiTipoEntita(fascicoloView.getTipoParteCivileCode(), fascicoloView.getLocale(), false);
					soggettoView.setTipoParteCivileCode(tipoSoggettoView.getVo().getCodGruppoLingua());
					soggettoView.setTipoParteCivile(tipoSoggettoView.getVo().getNome());
					ParteCivileView[] soggettiArray = new ParteCivileView[] { soggettoView };
					fascicoloView.setParteCivileAggiunte(soggettiArray);
				}
				fascicoloView.setNomeParteCivile("");
			} else {
				bindingResult.addError(new ObjectError("erroreGenerico", "errore.campo.obbligatorio.parteCivile"));
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}

		request.setAttribute("anchorName", "anchorParteCivile");
		return PAGINA_FORM_PATH;
	}

	public String rimuoviParteCivile(FascicoloView fascicoloView, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (fascicoloView.getParteCivileAggiunte() != null && fascicoloView.getParteCivileAggiunte().length > 0
					&& request.getParameter("parteCivileIndex") != null) {
				int indiceDaRimuovere = NumberUtils.toInt(request.getParameter("parteCivileIndex"));
				ParteCivileView[] soggettiArray = fascicoloView.getParteCivileAggiunte();
				List<ParteCivileView> soggettiList = new ArrayList<ParteCivileView>();
				int indice = 0;
				for (ParteCivileView soggetto : soggettiArray) {
					if (indiceDaRimuovere != indice) {
						soggettiList.add(soggetto);
					}
					indice++;
				}

				soggettiArray = new ParteCivileView[soggettiList.size()];
				soggettiList.toArray(soggettiArray);
				fascicoloView.setParteCivileAggiunte(soggettiArray);
			}
		} catch (Throwable e) {

			e.printStackTrace();
		}

		request.setAttribute("anchorName", "anchorParteCivile");
		return PAGINA_FORM_PATH;
	}
	

	  public String rimuoviFascicoliCorrelati(FascicoloView fascicoloView, BindingResult bindingResult, Locale locale, Model model, HttpServletRequest request, HttpServletResponse response)
	  {
	    try
	    {
	      if ((fascicoloView.getFascicoliCorrelatiAggiunti() != null) && 
	        (fascicoloView.getFascicoliCorrelatiAggiunti().length > 0) && 
	        (request.getParameter("fascicoliCorrelatiIndex") != null))
	      {
	        int indiceDaRimuovere = NumberUtils.toInt(request.getParameter("fascicoliCorrelatiIndex"));
	        FascicoloView[] array = fascicoloView.getFascicoliCorrelatiAggiunti();
	        List<FascicoloView> list = new ArrayList<FascicoloView>();
	        int indice = 0;
	        for (FascicoloView fascicolo : array)
	        {
	          if (indiceDaRimuovere != indice) {
	            list.add(fascicolo);
	          }
	          indice++;
	        }
	        array = new FascicoloView[list.size()];
	        list.toArray(array);
	        fascicoloView.setFascicoliCorrelatiAggiunti(array);
	      }
	    }
	    catch (Throwable e)
	    {
	      e.printStackTrace();
	    }

		request.setAttribute("anchorName", "anchorFascicoliCorrelati");
	    return PAGINA_FORM_PATH;
	  }

	public String aggiungiFascicoloPadre(FascicoloView fascicoloView, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("@@DDS aggiungiFascicoloPadre " );
		try {
			if (request.getParameter("fascicoloPadreSelezionatoId") != null) {
				FascicoloView fascicoloPadreView = fascicoloService
						.leggi(NumberUtils.toLong(request.getParameter("fascicoloPadreSelezionatoId")));
				fascicoloView.setFascicoloPadreId(fascicoloPadreView.getVo().getId());
				fascicoloView.setFascicoloPadreNome(fascicoloPadreView.getVo().getNome());
			} else {
				bindingResult.addError(new ObjectError("erroreGenerico", "errore.campo.obbligatorio.fascicoloPadre"));
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}

		request.setAttribute("anchorName", "anchorFascicoloPadre");
		return PAGINA_FORM_PATH;
	}

	public String rimuoviFascicoloPadre(FascicoloView fascicoloView, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			if ((fascicoloView.getFascicoloPadreId() != null) && (fascicoloView.getFascicoloPadreId() > 0)) {
				fascicoloView.setFascicoloPadreId(null);
				fascicoloView.setFascicoloPadreNome(null);
			} else {
				bindingResult.addError(new ObjectError("erroreGenerico", "errore.campo.obbligatorio.fascicoloPadre"));
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		request.setAttribute("anchorName", "anchorFascicoloPadre");
		return PAGINA_FORM_PATH;
	}

	public String aggiungiFascicoliCorrelati(FascicoloView fascicoloView, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			if ((fascicoloView.getFascicoliCorrelati() != null) && (fascicoloView.getFascicoliCorrelati().length > 0)) {
				if ((fascicoloView.getFascicoliCorrelatiAggiunti() != null)
						&& (fascicoloView.getFascicoliCorrelatiAggiunti().length > 0)) {
					List<FascicoloView> listaFascicoliCorrelati = new ArrayList<FascicoloView>();
					listaFascicoliCorrelati.addAll(Arrays.asList(fascicoloView.getFascicoliCorrelatiAggiunti()));
					String[] fascicoliSelezionati = fascicoloView.getFascicoliCorrelati();
					for (String fascicoloIds : fascicoliSelezionati) {
						FascicoloView fascicolo = this.fascicoloService.leggi(NumberUtils.toLong(fascicoloIds));

						if (!contains(listaFascicoliCorrelati, fascicolo)) {
							listaFascicoliCorrelati.add(fascicolo);
						}
					}
					FascicoloView[] arrayFascilcoli = new FascicoloView[listaFascicoliCorrelati.size()];
					listaFascicoliCorrelati.toArray((Object[]) arrayFascilcoli);
					fascicoloView.setFascicoliCorrelatiAggiunti((FascicoloView[]) arrayFascilcoli);
					fascicoloView.setFascicoliCorrelati(null);
				} else {
					List<FascicoloView> listaFascicoliCorrelati = new ArrayList<FascicoloView>();

					String[] fascicoliSelezionati = fascicoloView.getFascicoliCorrelati();
					for (String fascicoloIds : fascicoliSelezionati) {
						FascicoloView fascicolo = this.fascicoloService.leggi(NumberUtils.toLong(fascicoloIds));
						listaFascicoliCorrelati.add(fascicolo);
					}
					FascicoloView[] arrayFascilcoli = new FascicoloView[listaFascicoliCorrelati.size()];
					listaFascicoliCorrelati.toArray(arrayFascilcoli);
					fascicoloView.setFascicoliCorrelatiAggiunti(arrayFascilcoli);
					fascicoloView.setFascicoliCorrelati(null);
				}
			} else {
				bindingResult
						.addError(new ObjectError("erroreGenerico", "errore.campo.obbligatorio.fascicoliCorrelati"));
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		request.setAttribute("anchorName", "anchorFascicoliCorrelati");
		return PAGINA_FORM_PATH;
	}

	private boolean contains(List<FascicoloView> listaFascicoliCorrelati, FascicoloView fascicolo) {
		if( listaFascicoliCorrelati != null && listaFascicoliCorrelati.size() > 0 ){
			for( FascicoloView fascicoloView : listaFascicoliCorrelati ){
				if( fascicoloView.getVo().getId() == fascicolo.getVo().getId() ){
					return true;
				}
			} 
		}
		return false;
	}

	public String aggiungiResponsabileCivile(FascicoloView fascicoloView, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (fascicoloView.getTipoResponsabileCivileCode() == null
					|| fascicoloView.getTipoResponsabileCivileCode().trim().length() == 0) {
				bindingResult.addError(
						new ObjectError("erroreGenerico", "errore.campo.obbligatorio.tipologiaResponsabileCivile"));
				return PAGINA_FORM_PATH;
			}

			if (fascicoloView.getNomeResponsabileCivile() != null
					&& fascicoloView.getNomeResponsabileCivile().trim().length() > 0) {
				if (fascicoloView.getResponsabileCivileAggiunte() != null
						&& fascicoloView.getResponsabileCivileAggiunte().length > 0) {
					ResponsabileCivileView[] soggettiArray = fascicoloView.getResponsabileCivileAggiunte();
					List<ResponsabileCivileView> soggettiList = new ArrayList<ResponsabileCivileView>();
					soggettiList.addAll(Arrays.asList(soggettiArray));
					ResponsabileCivileView soggettoView = new ResponsabileCivileView();
					soggettoView.setNomeResponsabileCivile(fascicoloView.getNomeResponsabileCivile());
					TipoEntitaView tipoSoggettoView = anagraficaStatiTipiService
							.leggiTipoEntita(fascicoloView.getTipoResponsabileCivileCode(), fascicoloView.getLocale(), false);
					soggettoView.setTipoResponsabileCivileCode(tipoSoggettoView.getVo().getCodGruppoLingua());
					soggettoView.setTipoResponsabileCivile(tipoSoggettoView.getVo().getNome());
					soggettiList.add(soggettoView);
					soggettiArray = new ResponsabileCivileView[soggettiList.size()];
					soggettiList.toArray(soggettiArray);
					fascicoloView.setResponsabileCivileAggiunte(soggettiArray);
				} else {
					ResponsabileCivileView soggettoView = new ResponsabileCivileView();
					soggettoView.setNomeResponsabileCivile(fascicoloView.getNomeResponsabileCivile());
					TipoEntitaView tipoSoggettoView = anagraficaStatiTipiService
							.leggiTipoEntita(fascicoloView.getTipoResponsabileCivileCode(), fascicoloView.getLocale(), false);
					soggettoView.setTipoResponsabileCivileCode(tipoSoggettoView.getVo().getCodGruppoLingua());
					soggettoView.setTipoResponsabileCivile(tipoSoggettoView.getVo().getNome());
					ResponsabileCivileView[] soggettiArray = new ResponsabileCivileView[] { soggettoView };
					fascicoloView.setResponsabileCivileAggiunte(soggettiArray);
				}
				fascicoloView.setNomeResponsabileCivile("");
			} else {
				bindingResult
						.addError(new ObjectError("erroreGenerico", "errore.campo.obbligatorio.ResponsabileCivile"));
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		request.setAttribute("anchorName", "anchorResponsabileCivile");

		return PAGINA_FORM_PATH;
	}

	public String rimuoviResponsabileCivile(FascicoloView fascicoloView, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (fascicoloView.getResponsabileCivileAggiunte() != null
					&& fascicoloView.getResponsabileCivileAggiunte().length > 0
					&& request.getParameter("responsabileCivileIndex") != null) {
				int indiceDaRimuovere = NumberUtils.toInt(request.getParameter("responsabileCivileIndex"));
				ResponsabileCivileView[] soggettiArray = fascicoloView.getResponsabileCivileAggiunte();
				List<ResponsabileCivileView> soggettiList = new ArrayList<ResponsabileCivileView>();
				int indice = 0;
				for (ResponsabileCivileView soggetto : soggettiArray) {
					if (indiceDaRimuovere != indice) {
						soggettiList.add(soggetto);
					}
					indice++;
				}

				soggettiArray = new ResponsabileCivileView[soggettiList.size()];
				soggettiList.toArray(soggettiArray);
				fascicoloView.setResponsabileCivileAggiunte(soggettiArray);
			}
		} catch (Throwable e) {

			e.printStackTrace();
		}

		request.setAttribute("anchorName", "anchorResponsabileCivile");
		return PAGINA_FORM_PATH;
	}

	public String aggiungiControparte(FascicoloView fascicoloView, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (fascicoloView.getTipoControparteCode() == null
					|| fascicoloView.getTipoControparteCode().trim().length() == 0) {
				bindingResult
						.addError(new ObjectError("erroreGenerico", "errore.campo.obbligatorio.tipologiaControparte"));
				return PAGINA_FORM_PATH;
			}

			if (fascicoloView.getNomeControparte() != null && fascicoloView.getNomeControparte().trim().length() > 0) {
				if (fascicoloView.getContropartiAggiunte() != null
						&& fascicoloView.getContropartiAggiunte().length > 0) {
					ControparteView[] contropartiArray = fascicoloView.getContropartiAggiunte();
					List<ControparteView> contropartiList = new ArrayList<ControparteView>();
					contropartiList.addAll(Arrays.asList(contropartiArray));
					ControparteView controparteView = new ControparteView();
					controparteView.setNomeControparte(fascicoloView.getNomeControparte());
					TipoEntitaView tipoControparteView = anagraficaStatiTipiService
							.leggiTipoEntita(fascicoloView.getTipoControparteCode(), fascicoloView.getLocale(), false);
					controparteView.setTipoControparteCode(tipoControparteView.getVo().getCodGruppoLingua());
					controparteView.setTipoControparte(tipoControparteView.getVo().getNome());
					contropartiList.add(controparteView);
					contropartiArray = new ControparteView[contropartiList.size()];
					contropartiList.toArray(contropartiArray);
					fascicoloView.setContropartiAggiunte(contropartiArray);
				} else {
					ControparteView controparteView = new ControparteView();
					controparteView.setNomeControparte(fascicoloView.getNomeControparte());
					TipoEntitaView tipoControparteView = anagraficaStatiTipiService
							.leggiTipoEntita(fascicoloView.getTipoControparteCode(), fascicoloView.getLocale(), false);
					controparteView.setTipoControparteCode(tipoControparteView.getVo().getCodGruppoLingua());
					controparteView.setTipoControparte(tipoControparteView.getVo().getNome());
					ControparteView[] contropartiArray = new ControparteView[] { controparteView };
					fascicoloView.setContropartiAggiunte(contropartiArray);
				}
				fascicoloView.setNomeControparte("");
			} else {
				bindingResult.addError(new ObjectError("erroreGenerico", "errore.campo.obbligatorio.controparte"));
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}

		request.setAttribute("anchorName", "anchorControparte");
		return PAGINA_FORM_PATH;
	}

	public String rimuoviControparte(FascicoloView fascicoloView, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (fascicoloView.getContropartiAggiunte() != null && fascicoloView.getContropartiAggiunte().length > 0
					&& request.getParameter("controparteIndex") != null) {
				int indiceDaRimuovere = NumberUtils.toInt(request.getParameter("controparteIndex"));
				ControparteView[] controparteArray = fascicoloView.getContropartiAggiunte();
				List<ControparteView> controparteList = new ArrayList<ControparteView>();
				int indice = 0;
				for (ControparteView controparte : controparteArray) {
					if (indiceDaRimuovere != indice) {
						controparteList.add(controparte);
					}
					indice++;
				}

				controparteArray = new ControparteView[controparteList.size()];
				controparteList.toArray(controparteArray);
				fascicoloView.setContropartiAggiunte(controparteArray);
			}
		} catch (Throwable e) {

			e.printStackTrace();
		}

		request.setAttribute("anchorName", "anchorControparte");
		return PAGINA_FORM_PATH;
	}

	public String aggiungiTerzoChiamatoCausa(FascicoloView fascicoloView, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		try {

			if (fascicoloView.getTipoTerzoChiamatoCausaCode() == null
					|| fascicoloView.getTipoTerzoChiamatoCausaCode().trim().length() == 0) {
				bindingResult.addError(
						new ObjectError("erroreGenerico", "errore.campo.obbligatorio.tipologiaTerzoChiamatoCausa"));
				return PAGINA_FORM_PATH;
			}

			if (fascicoloView.getTerzoChiamatoInCausa() != null
					&& fascicoloView.getTerzoChiamatoInCausa().trim().length() > 0) {
				if (fascicoloView.getTerzoChiamatoInCausaAggiunti() != null
						&& fascicoloView.getTerzoChiamatoInCausaAggiunti().length > 0) {
					TerzoChiamatoCausaView[] terzoChiamatoCausaArray = fascicoloView.getTerzoChiamatoInCausaAggiunti();
					List<TerzoChiamatoCausaView> terzoChiamatoCausaList = new ArrayList<TerzoChiamatoCausaView>();
					terzoChiamatoCausaList.addAll(Arrays.asList(terzoChiamatoCausaArray));
					TerzoChiamatoCausaView view = new TerzoChiamatoCausaView();
					view.setLegaleRiferimento(fascicoloView.getLegaleRiferimento());
					view.setNomeTerzoChiamatoCausa(fascicoloView.getTerzoChiamatoInCausa());
					TipoEntitaView tipoSoggettoView = anagraficaStatiTipiService
							.leggiTipoEntita(fascicoloView.getTipoTerzoChiamatoCausaCode(), fascicoloView.getLocale(), false);

					view.setTipoTerzoChiamatoCausa(tipoSoggettoView.getVo().getNome());
					view.setTipoTerzoChiamatoCausaCode(tipoSoggettoView.getVo().getCodGruppoLingua());
					terzoChiamatoCausaList.add(view);
					terzoChiamatoCausaArray = new TerzoChiamatoCausaView[terzoChiamatoCausaList.size()];
					terzoChiamatoCausaList.toArray(terzoChiamatoCausaArray);
					fascicoloView.setTerzoChiamatoInCausaAggiunti(terzoChiamatoCausaArray);
				} else {
					TerzoChiamatoCausaView view = new TerzoChiamatoCausaView();
					view.setLegaleRiferimento(fascicoloView.getLegaleRiferimento());
					view.setNomeTerzoChiamatoCausa(fascicoloView.getTerzoChiamatoInCausa());
					TipoEntitaView tipoSoggettoView = anagraficaStatiTipiService
							.leggiTipoEntita(fascicoloView.getTipoTerzoChiamatoCausaCode(), fascicoloView.getLocale(), false);

					view.setTipoTerzoChiamatoCausa(tipoSoggettoView.getVo().getNome());
					view.setTipoTerzoChiamatoCausaCode(tipoSoggettoView.getVo().getCodGruppoLingua());
					TerzoChiamatoCausaView[] terzoChiamatoCausaArray = new TerzoChiamatoCausaView[] { view };
					fascicoloView.setTerzoChiamatoInCausaAggiunti(terzoChiamatoCausaArray);
				}
				fascicoloView.setTerzoChiamatoInCausa("");
				fascicoloView.setTipoTerzoChiamatoCausaCode("");
			} else {
				bindingResult
						.addError(new ObjectError("erroreGenerico", "errore.campo.obbligatorio.terzoChiamatoCausa"));
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}

		request.setAttribute("anchorName", "anchorTerzoChiamatoCausa");
		return PAGINA_FORM_PATH;
	}


	@RequestMapping(value = "/fascicolo/selezionaTipologiaFascicoloRicerca", method = RequestMethod.GET)
	public @ResponseBody List<SettoreGiuridicoRest> selezionaTipologiaFascicoloRicerca(  Locale locale, HttpServletRequest request, HttpServletResponse response) {


		try {
			TipologiaFascicoloView tipologiaFascicoloView = tipologiaFascicoloService
					.leggi(request.getParameter("tipologiaFascicoloCode"), locale, false);
			 
			List<SettoreGiuridicoView> listaSettore = settoreGiuridicoService.leggiPerTipologiaId(tipologiaFascicoloView.getVo().getId(), false);
			List<SettoreGiuridicoRest> listaRitorno = new ArrayList<SettoreGiuridicoRest>();
			if( listaSettore != null ){
				for( SettoreGiuridicoView settore:listaSettore ){
					SettoreGiuridicoRest rest = new SettoreGiuridicoRest();
					rest.setCodGruppoLingua(settore.getVo().getCodGruppoLingua());
					rest.setNome(settore.getVo().getNome());
					listaRitorno.add(rest);
				}
			}
			return listaRitorno;
		} catch (Throwable e) {

			e.printStackTrace();
		}
		return null;
	}

	
	public String selezionaTipologiaFascicolo(FascicoloView fascicoloView, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			TipologiaFascicoloView tipologiaFascicoloView = tipologiaFascicoloService
					.leggi(fascicoloView.getTipologiaFascicoloCode(), locale, false);
			if (fascicoloView.getTipologiaFascicoloCode().equalsIgnoreCase(TIPOLOGIA_FASCICOLO_GIUDIZIALE_COD)) {
				fascicoloView.setListaSettoreGiuridico(
						settoreGiuridicoService.leggiPerTipologiaId(tipologiaFascicoloView.getVo().getId(), false));
				fascicoloView.setTabAttiva("2");
			} else if (fascicoloView.getTipologiaFascicoloCode()
					.equalsIgnoreCase(TIPOLOGIA_FASCICOLO_STRAGIUDIZIALE_COD)) {
				fascicoloView.setListaTipoControparte(
						anagraficaStatiTipiService.leggiTipoEntita(locale.getLanguage().toUpperCase(), false));
				fascicoloView.setListaSettoreGiuridico(
						settoreGiuridicoService.leggiPerTipologiaId(tipologiaFascicoloView.getVo().getId(), false));
				fascicoloView.setTabAttiva("2");
			} else if (fascicoloView.getTipologiaFascicoloCode().equalsIgnoreCase(TIPOLOGIA_FASCICOLO_NOTARILE_COD)) {
				fascicoloView.setListaSettoreGiuridico(
						settoreGiuridicoService.leggiPerTipologiaId(tipologiaFascicoloView.getVo().getId(), false));
				fascicoloView.setListaTipoPrestazioneNotarile(
						anagraficaStatiTipiService.leggiTipoPrestazioneNotarile(locale.getLanguage().toUpperCase(), false));
				fascicoloView.setTabAttiva("2");
			}

		} catch (Throwable e) {

			e.printStackTrace();
		}
		return PAGINA_FORM_PATH;
	}

	@RequestMapping(value = "/fascicolo/selezionaRicorso", method = RequestMethod.POST)
	public String selezionaRicorso(FascicoloView fascicoloView, BindingResult bindingResult, Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		// engsecurity VA
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		try {
			if( fascicoloView.getIndexRicorsoAggiunto() != null && fascicoloView.getIndexRicorsoAggiunto().intValue() >= 0 ){
				RFascicoloRicorsoView[] array = fascicoloView.getRicorsiAggiunti();
				if( array != null  ){
					int indice = 0;
					int indiceDaModificare = fascicoloView.getIndexRicorsoAggiunto();
					for( RFascicoloRicorsoView view : array ){
						if(indice == indiceDaModificare){
							fascicoloView.setForo(view.getForo());
							fascicoloView.setNote(view.getNote());
							fascicoloView.setNumeroRegistroCausa(view.getNumeroRegistroCausa());
							fascicoloView.setRicorsoCode(view.getVo().getRicorso().getCodGruppoLingua());
							fascicoloView.setOrganoGiudicanteCode(view.getVo().getOrganoGiudicante() == null ? "":view.getVo().getOrganoGiudicante().getCodGruppoLingua());
							fascicoloView.setListaOrganoGiudicante(organoGiudicanteService.leggiDaRicorso(view.getVo().getRicorso().getId()));
							break;
						}
						indice++;
					} 
				}
			}else{
				if (fascicoloView.getRicorsoCode() != null && fascicoloView.getRicorsoCode().trim().length() > 0) {
					RicorsoView ricorso = ricorsoService.leggi(fascicoloView.getRicorsoCode(), locale, false);
					fascicoloView.setListaOrganoGiudicante(organoGiudicanteService.leggiDaRicorso(ricorso.getVo().getId()));
				} else {
					fascicoloView.setListaOrganoGiudicante(null);
				}
			}

		} catch (Throwable e) {

			e.printStackTrace();
		}
		return PAGINA_FORM_RICORSO;
	}

	public String aggiungiGiudizio(FascicoloView fascicoloView, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			
			String foro = fascicoloView.getForo();
			String numeroRegistroCausa =  fascicoloView.getNumeroRegistroCausa();
			String note = fascicoloView.getNote();
			String organoGiudicanteCode = fascicoloView.getOrganoGiudicanteCode();
			String giudizioCode = fascicoloView.getGiudizioCode();
			Integer indexGiudizio = fascicoloView.getIndexGiudizioAggiunto();
			 
			 
			RFascicoloGiudizioView fascicoloGiudizioView = new RFascicoloGiudizioView();
			fascicoloGiudizioView.setForo(foro);
			fascicoloGiudizioView.setNote(note);
			fascicoloGiudizioView.setNumeroRegistroCausa(numeroRegistroCausa);
			RFascicoloGiudizio fascicoloGiudizio = new RFascicoloGiudizio();
			OrganoGiudicanteView organoGiudicante = organoGiudicanteService.leggi(organoGiudicanteCode, locale);
			GiudizioView giudizioView = giudizioService.leggi(giudizioCode, locale, false);
			fascicoloGiudizio.setOrganoGiudicante(organoGiudicante.getVo());
			fascicoloGiudizio.setGiudizio(giudizioView.getVo());
			fascicoloGiudizioView.setVo(fascicoloGiudizio);
			RFascicoloGiudizioView[] array = fascicoloView.getGiudiziAggiunti();
			if( array != null && array.length > 0){
				if( indexGiudizio == null || indexGiudizio.intValue() < 0){
					List<RFascicoloGiudizioView> lista = new ArrayList<RFascicoloGiudizioView>();
					lista.addAll(Arrays.asList(array));
					lista.add(fascicoloGiudizioView);
					array = new RFascicoloGiudizioView[lista.size()];
					lista.toArray(array);
				}else{
					array[indexGiudizio] = fascicoloGiudizioView;
				}
			}else{
				array = new RFascicoloGiudizioView[]{fascicoloGiudizioView};
			}
			
			fascicoloView.setGiudiziAggiunti(array);

		} catch (Throwable e) {

			e.printStackTrace();
		}
		request.setAttribute("anchorName", "anchorGiudizio");
		return PAGINA_FORM_PATH;
	}


	public String aggiungiRicorso(FascicoloView fascicoloView, BindingResult bindingResult, Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
	try {
			
			String foro = fascicoloView.getForo();
			String numeroRegistroCausa =  fascicoloView.getNumeroRegistroCausa();
			String note = fascicoloView.getNote();
			String organoGiudicanteCode = fascicoloView.getOrganoGiudicanteCode();
			String ricorsoCode = fascicoloView.getRicorsoCode();
			Integer indexRicorso = fascicoloView.getIndexRicorsoAggiunto();
			
			RFascicoloRicorsoView fascicoloRicorsoView = new RFascicoloRicorsoView();
			fascicoloRicorsoView.setForo(foro);
			fascicoloRicorsoView.setNote(note);
			fascicoloRicorsoView.setNumeroRegistroCausa(numeroRegistroCausa);
			RFascicoloRicorso fascicoloRicorso = new RFascicoloRicorso();
			OrganoGiudicanteView organoGiudicante = organoGiudicanteService.leggi(organoGiudicanteCode, locale);
			RicorsoView ricorsoView = ricorsoService.leggi(ricorsoCode, locale, false);
			fascicoloRicorso.setOrganoGiudicante(organoGiudicante.getVo());
			fascicoloRicorso.setRicorso(ricorsoView.getVo());
			fascicoloRicorsoView.setVo(fascicoloRicorso);
			RFascicoloRicorsoView[] array = fascicoloView.getRicorsiAggiunti();
			if( array != null && array.length > 0 ){
				if( indexRicorso == null || indexRicorso.intValue() < 0){ 
					List<RFascicoloRicorsoView> lista = new ArrayList<RFascicoloRicorsoView>();
					lista.addAll(Arrays.asList(array));
					lista.add(fascicoloRicorsoView);
					array = new RFascicoloRicorsoView[lista.size()];
					lista.toArray(array);
				}else{
					array[indexRicorso] = fascicoloRicorsoView;
				}
			}else{
				array = new RFascicoloRicorsoView[]{fascicoloRicorsoView};
			}
			
			fascicoloView.setRicorsiAggiunti(array);

		} catch (Throwable e) {

			e.printStackTrace();
		}
	request.setAttribute("anchorName", "anchorRicorso");
		return PAGINA_FORM_PATH;
	}
 
	public String rimuoviRicorsoAggiunto(FascicoloView fascicoloView, BindingResult bindingResult, Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
	 
			Integer index = fascicoloView.getIndexRicorsoAggiunto();
			
			try {
				if (fascicoloView.getRicorsiAggiunti() != null && fascicoloView.getRicorsiAggiunti().length > 0
						&& index != null) { 
					RFascicoloRicorsoView[] array = fascicoloView.getRicorsiAggiunti();
					List<RFascicoloRicorsoView> lista = new ArrayList<RFascicoloRicorsoView>();
					int indice = 0;
					for (RFascicoloRicorsoView view : array) {
						if (index != indice) {
							lista.add(view);
						}
						indice++;
					}

					array = new RFascicoloRicorsoView[lista.size()];
					lista.toArray(array);
					fascicoloView.setRicorsiAggiunti(array);
				}
			} catch (Throwable e) {

				e.printStackTrace();
			}

			request.setAttribute("anchorName", "anchorRicorso");
			return PAGINA_FORM_PATH;
		 
	}
 
	public String rimuoviGiudizioAggiunto(FascicoloView fascicoloView, BindingResult bindingResult, Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		
		// engsecurity VA
		HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
		htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);
	 
			Integer index = fascicoloView.getIndexGiudizioAggiunto();
			
			try {
				if (fascicoloView.getGiudiziAggiunti() != null && fascicoloView.getGiudiziAggiunti().length > 0
						&& index != null) { 
					RFascicoloGiudizioView[] array = fascicoloView.getGiudiziAggiunti();
					List<RFascicoloGiudizioView> lista = new ArrayList<RFascicoloGiudizioView>();
					int indice = 0;
					for (RFascicoloGiudizioView view : array) {
						if (index != indice) {
							lista.add(view);
						}
						indice++;
					}

					array = new RFascicoloGiudizioView[lista.size()];
					lista.toArray(array);
					fascicoloView.setGiudiziAggiunti(array);
				}
			} catch (Throwable e) {

				e.printStackTrace();
			}

			request.setAttribute("anchorName", "anchorGiudizio");
			return PAGINA_FORM_PATH;
		 
	}
 
	@RequestMapping(value = "/fascicolo/selezionaGiudizio", method = RequestMethod.POST)
	public String selezionaGiudizio(FascicoloView fascicoloView, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		// engsecurity VA
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		try {  
			if( fascicoloView.getIndexGiudizioAggiunto() != null && fascicoloView.getIndexGiudizioAggiunto().intValue() >= 0 ){
				RFascicoloGiudizioView[] array = fascicoloView.getGiudiziAggiunti();
				if( array != null ){
					int indice = 0;
					int indiceDaModificare = fascicoloView.getIndexGiudizioAggiunto();
					for( RFascicoloGiudizioView view : array ){
						if(indice == indiceDaModificare){
							fascicoloView.setForo(view.getForo());
							fascicoloView.setNote(view.getNote());
							fascicoloView.setNumeroRegistroCausa(view.getNumeroRegistroCausa());
							fascicoloView.setGiudizioCode(view.getVo().getGiudizio().getCodGruppoLingua());
							fascicoloView.setOrganoGiudicanteCode(view.getVo().getOrganoGiudicante() == null ? "":view.getVo().getOrganoGiudicante().getCodGruppoLingua());
							fascicoloView.setListaOrganoGiudicante(organoGiudicanteService.leggiDaGiudizio(view.getVo().getGiudizio().getId()));
							break;
						}
						indice++;
					} 
				}
			}else{
				if (fascicoloView.getGiudizioCode() != null && fascicoloView.getGiudizioCode().trim().length() > 0) {
					GiudizioView giudizio = giudizioService.leggi(fascicoloView.getGiudizioCode(), locale, false);
					fascicoloView
							.setListaOrganoGiudicante(organoGiudicanteService.leggiDaGiudizio(giudizio.getVo().getId()));
				} else {
					fascicoloView.setListaOrganoGiudicante(null);
				}
			}
		} catch (Throwable e) {

			e.printStackTrace();
		}
		return PAGINA_FORM_GIUDIZIO;
	}

	
	public String selezionaSettoreGiuridico(FascicoloView fascicoloView, BindingResult bindingResult, Locale locale,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			SettoreGiuridicoView settoreGiuridicoView = settoreGiuridicoService
					.leggi(fascicoloView.getSettoreGiuridicoCode(), locale, false);

			long idSettore = fascicoloView.getSettoreGiuridicoCode().equalsIgnoreCase(SETTORE_GIURIDICO_ARBITRALE_CODE)
					|| fascicoloView.getSettoreGiuridicoCode().equalsIgnoreCase(SETTORE_GIURIDICO_JOIN_VENTURE_CODE) ? 0
							: settoreGiuridicoView.getVo().getId();
			if (!fascicoloView.getTipologiaFascicoloCode().equals(TIPOLOGIA_FASCICOLO_NOTARILE_COD)) {
				JSONArray jsonArray = materiaService.leggiAlberaturaMateria(idSettore, locale, false);
				if (jsonArray != null) {
					fascicoloView.setJsonAlberaturaMaterie(jsonArray.toString());
				} else {
					fascicoloView.setJsonAlberaturaMaterie(null);
				}
			}

			if (fascicoloView.getTipologiaFascicoloCode().equals(TIPOLOGIA_FASCICOLO_STRAGIUDIZIALE_COD)) {
				fascicoloView.setListaTipoControparte(
						anagraficaStatiTipiService.leggiTipoEntita(locale.getLanguage().toUpperCase(), false));

			}

			if (fascicoloView.getSettoreGiuridicoCode().equalsIgnoreCase(SETTORE_GIURIDICO_CIVILE_CODE)) {
				fascicoloView.setListaTipoControparte(
						anagraficaStatiTipiService.leggiTipoEntita(locale.getLanguage().toUpperCase(), false));
				fascicoloView.setListaGiudizio(
						giudizioService.leggiDaSettoreGiuridicoId(settoreGiuridicoView.getVo().getId(), false));
				fascicoloView.setListaTipoSoggetto(
						anagraficaStatiTipiService.leggiTipoEntita(locale.getLanguage().toUpperCase(), false));

				fascicoloView.setListaPosizioneSocieta(anagraficaStatiTipiService
						.leggiPosizioneSocietaPerSettoreGiuridicoId(settoreGiuridicoView.getVo().getId(), false));

			} else if (fascicoloView.getSettoreGiuridicoCode()
					.equalsIgnoreCase(SETTORE_GIURIDICO_AMMINISTRATIVO_CODE)) {

				fascicoloView.setListaTipoSoggetto(
						anagraficaStatiTipiService.leggiTipoEntita(locale.getLanguage().toUpperCase(), false));
				fascicoloView.setListaRicorso(ricorsoService.leggi(locale, false));
				fascicoloView.setListaPosizioneSocieta(anagraficaStatiTipiService
						.leggiPosizioneSocietaPerSettoreGiuridicoId(settoreGiuridicoView.getVo().getId(), false));

			} else if (fascicoloView.getSettoreGiuridicoCode().equalsIgnoreCase(SETTORE_GIURIDICO_PENALE_CODE)) {
				fascicoloView.setListaTipoSoggetto(
						anagraficaStatiTipiService.leggiTipoEntita(locale.getLanguage().toUpperCase(), false));
				fascicoloView.setListaGiudizio(
						giudizioService.leggiDaSettoreGiuridicoId(settoreGiuridicoView.getVo().getId(), false));
				fascicoloView.setListaTipoSoggettoIndagato(
						anagraficaStatiTipiService.leggiTipoSoggettoIndagato(locale.getLanguage().toUpperCase(), false));

			} else if (fascicoloView.getSettoreGiuridicoCode().equalsIgnoreCase(SETTORE_GIURIDICO_ARBITRALE_CODE)) {
				fascicoloView.setListaTipoControparte(
						anagraficaStatiTipiService.leggiTipoEntita(locale.getLanguage().toUpperCase(), false));

			}

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return PAGINA_FORM_PATH;

	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new FascicoloValidator());

		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {
		 
	}


}
