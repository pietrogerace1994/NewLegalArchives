package eng.la.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.FetchMode;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import eng.la.business.AnagraficaStatiTipiService;
import eng.la.business.FascicoloService;
import eng.la.business.GiudizioService;
import eng.la.business.MateriaService;
import eng.la.business.OrganoGiudicanteService;
import eng.la.business.ProformaService;
import eng.la.business.RicorsoService;
import eng.la.business.SettoreGiuridicoService;
import eng.la.business.TipologiaFascicoloService;
import eng.la.business.UtenteService;
import eng.la.model.Controparte;
import eng.la.model.Fascicolo;
import eng.la.model.Incarico;
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
import eng.la.model.view.BaseView;
import eng.la.model.view.ControparteView;
import eng.la.model.view.FascicoloView;
import eng.la.model.view.GiudizioView;
import eng.la.model.view.MateriaView;
import eng.la.model.view.ParteCivileView;
import eng.la.model.view.PersonaOffesaView;
import eng.la.model.view.ProformaView;
import eng.la.model.view.RFascicoloGiudizioView;
import eng.la.model.view.RFascicoloRicorsoView;
import eng.la.model.view.RUtenteFascicoloView;
import eng.la.model.view.ResponsabileCivileView;
import eng.la.model.view.RicorsoView;
import eng.la.model.view.SettoreGiuridicoView;
import eng.la.model.view.SoggettoIndagatoView;
import eng.la.model.view.TerzoChiamatoCausaView;
import eng.la.model.view.TipoContenziosoView;
import eng.la.model.view.TipoEntitaView;
import eng.la.model.view.TipoPrestNotarileView;
import eng.la.model.view.TipoSoggettoIndagatoView;
import eng.la.model.view.TipologiaFascicoloView;
import eng.la.model.view.UtenteView;
import eng.la.model.view.ValoreCausaView;
import eng.la.persistence.audit.AuditInterceptor;
import eng.la.util.DateUtil;
import eng.la.util.SpringUtil;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("fascicoloDettaglioController")
@SessionAttributes("fascicoloDettaglioView")
public class FascicoloDettaglioController extends BaseController  {

	private static final String MODEL_VIEW_NOME = "fascicoloDettaglioView"; 
	private static final String PAGINA_DETTAGLIO_PATH = "fascicolo/formFascicoloDettaglio"; 
	private static final String PAGINA_VISUALIZZA_STAMPA ="fascicolo/formFascicoloDettaglioStampa";
	
	@Autowired
	private FascicoloService fascicoloService;

	public void setFascicoloService(FascicoloService fascicoloService) {
		this.fascicoloService = fascicoloService;
	}

	@Autowired
	private UtenteService utenteService;

	public void setFascicoloService(UtenteService utenteService) {
		this.utenteService = utenteService;
	}

	@Autowired
	private OrganoGiudicanteService organoGiudicanteService;

	public void setOrganoGiudicanteService(OrganoGiudicanteService organoGiudicanteService) {
		this.organoGiudicanteService = organoGiudicanteService;
	}

	@Autowired
	private RicorsoService ricorsoService;

	public void setRicorsoService(RicorsoService ricorsoService) {
		this.ricorsoService = ricorsoService;
	}

	@Autowired
	private GiudizioService giudizioService;

	public void setGiudizioService(GiudizioService giudizioService) {
		this.giudizioService = giudizioService;
	}

	@Autowired
	private SettoreGiuridicoService settoreGiuridicoService;

	public void setSettoreGiuridicoService(SettoreGiuridicoService settoreGiuridicoService) {
		this.settoreGiuridicoService = settoreGiuridicoService;
	}

	@Autowired
	private TipologiaFascicoloService tipologiaFascicoloService;

	public void setTipologiaFascicoloService(TipologiaFascicoloService tipologiaFascicoloService) {
		this.tipologiaFascicoloService = tipologiaFascicoloService;
	}

	@Autowired
	private MateriaService materiaService;

	public void setMateriaService(MateriaService materiaService) {
		this.materiaService = materiaService;
	}

	@Autowired
	private AnagraficaStatiTipiService anagraficaStatiTipiService;

	public void setAnagraficaStatiTipiService(AnagraficaStatiTipiService anagraficaStatiTipiService) {
		this.anagraficaStatiTipiService = anagraficaStatiTipiService;
	}

        @Autowired
	private ProformaService proformaService;
	
	public void setProformaService(ProformaService proformaService) {
		this.proformaService = proformaService;
	}
   
	@RequestMapping("/fascicolo/dettaglio")
	public String dettaglioFascicolo(@RequestParam("id") long id, Model model, Locale locale
			, HttpServletRequest request
			) {
		FascicoloView fascicoloView = new FascicoloView();
		// engsecurity VA ??id - redirect
				HTMLActionSupport htmlActionSupport = new HTMLActionSupport();  
				htmlActionSupport.checkCSRFToken(request);
		        //removeCSRFToken(request);
		
		try {
			FascicoloView fascicoloSalvato = (FascicoloView) fascicoloService.leggi(id, FetchMode.JOIN);
			if (fascicoloSalvato != null && fascicoloSalvato.getVo() != null) {
				popolaFormDaVoPerDettaglio(fascicoloView, fascicoloSalvato.getVo(), locale);
				
				super.caricaListePerDettaglio(fascicoloView, locale);

				AuditInterceptor auditInterceptor = (AuditInterceptor) SpringUtil.getBean("auditInterceptor"); 
				auditInterceptor.auditRead(fascicoloSalvato.getVo());
			} else {
				model.addAttribute("errorMessage", "errore.oggetto.non.trovato");
			}
		} catch (Throwable e) {
			model.addAttribute("errorMessage", "errore.generico");
			e.printStackTrace();
		}

		model.addAttribute(MODEL_VIEW_NOME, fascicoloView);
		return model.containsAttribute("errorMessage") ? "redirect:/errore.action":PAGINA_DETTAGLIO_PATH;
	}

	private void popolaFormDaVoPerDettaglio(FascicoloView fascicolo, Fascicolo vo, Locale locale) throws Throwable {

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
						tipoPrestNotar.getTipoPrestNotarile().getCodGruppoLingua(), locale, true);
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
						.leggiTipoEntita(controparte.getTipoEntita().getCodGruppoLingua(), locale, true);
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
						.leggiTipoEntita(terzoChiamatoCausa.getTipoEntita().getCodGruppoLingua(), locale, true);
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
				MateriaView materiaView = materiaService.leggi(fascicoloMateria.getMateria().getCodGruppoLingua(), locale, true);
				materieAggiunteDesc.add(materiaView.getVo().getNome());
			}
			materie = new String[materieCode.size()];
			materieCode.toArray(materie);
			fascicolo.setMaterie(materie);
			fascicolo.setListaMaterieAggiunteDesc(materieAggiunteDesc);
		}

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
				TipoEntitaView tipoEntitaView = anagraficaStatiTipiService
						.leggiTipoEntita(parteCivile.getTipoEntita().getCodGruppoLingua(), locale, true);

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
						.leggiTipoEntita(responsabileCivile.getTipoEntita().getCodGruppoLingua(), locale, true);

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
						.leggiTipoEntita(personaOffesa.getTipoEntita().getCodGruppoLingua(), locale, true);

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
						.leggiTipoSoggettoIndagato(soggetto.getTipoSoggettoIndagato().getCodGruppoLingua(), locale, true);

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
		
		/** Visualizzazione info CDC e VDC**/
		if (vo.getIncaricos() != null && vo.getIncaricos().size() > 0) {

			Set<Incarico> incarichiFascicolo = vo.getIncaricos();

			for(Incarico incarico: incarichiFascicolo){

				if(incarico.getDataCancellazione() == null){

					List<ProformaView> proformas = proformaService.leggiProformaAssociatiAIncarico(incarico.getId());

					if(proformas != null && !proformas.isEmpty()){
						
						List<String> centriDiCosto = new ArrayList<String>();
						List<String> vociDiConto = new ArrayList<String>();
						String centroDiCosto = "";
						String voceDiConto = "";

						for(ProformaView proformaView : proformas){
							
							if(proformaView.getVo().getCentroDiCosto() != null && !proformaView.getVo().getCentroDiCosto().isEmpty()){
								
								if(!centriDiCosto.contains(proformaView.getVo().getCentroDiCosto())){
									centriDiCosto.add(proformaView.getVo().getCentroDiCosto());
								}
							}
							
							if(proformaView.getVo().getVoceDiConto() != null && !proformaView.getVo().getVoceDiConto().isEmpty()){
								
								if(!vociDiConto.contains(proformaView.getVo().getVoceDiConto())){
									vociDiConto.add(proformaView.getVo().getVoceDiConto());
								}
							}
							
						}
						
						if(centriDiCosto.size() > 0){
							
							if(centriDiCosto.size() == 1){
								centroDiCosto += centriDiCosto.get(0);
							}
							else{
								for(String s : centriDiCosto){
									centroDiCosto += s +", ";
								}
							}
						}
						
						if(vociDiConto.size() > 0){
							
							if(vociDiConto.size() == 1){
								voceDiConto += vociDiConto.get(0);
							}
							else{
								for(String s : vociDiConto){
									voceDiConto += s +", ";
								}
							}
						}
						fascicolo.setCentroDiCosto(centroDiCosto);
						fascicolo.setVoceDiConto(voceDiConto);
					}
				}
			}
		}
	}
	
	@RequestMapping(value ="/fascicolo/stampaDettaglio", method = RequestMethod.GET)
	public String stampaDettaglioFascicolo(@RequestParam("id") long id, Model model, Locale locale, HttpServletRequest request) throws Throwable {
		
		FascicoloView fascicoloView = new FascicoloView();
		
		try {
			FascicoloView fascicoloSalvato = (FascicoloView) fascicoloService.leggi(id, FetchMode.JOIN);
			if (fascicoloSalvato != null && fascicoloSalvato.getVo() != null) {
				popolaFormDaVoPerDettaglio(fascicoloView, fascicoloSalvato.getVo(), locale);
				
				super.caricaListePerDettaglio(fascicoloView, locale);

				AuditInterceptor auditInterceptor = (AuditInterceptor) SpringUtil.getBean("auditInterceptor"); 
				auditInterceptor.auditRead(fascicoloSalvato.getVo());
			} else {
				model.addAttribute("errorMessage", "errore.oggetto.non.trovato");
			}
		} catch (Throwable e) {
			model.addAttribute("errorMessage", "errore.generico");
			e.printStackTrace();
		}

		model.addAttribute(MODEL_VIEW_NOME, fascicoloView);
		return model.containsAttribute("errorMessage") ? "redirect:/errore.action":PAGINA_VISUALIZZA_STAMPA;
	}
     
	@Override
	public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {
		List<TipologiaFascicoloView> listaTipologiaFascicolo = tipologiaFascicoloService.leggi(locale, true);
		List<TipoContenziosoView> listaTipoContenzioso = anagraficaStatiTipiService
				.leggiTipoContenzioso(locale.getLanguage().toUpperCase(), true);
		List<ValoreCausaView> listaValoreCausa = anagraficaStatiTipiService
				.leggiValoreCausa(locale.getLanguage().toUpperCase(), true);
		FascicoloView fascicoloView = (FascicoloView) view;
		fascicoloView.setListaTipoContenzioso(listaTipoContenzioso);
		fascicoloView.setListaTipologiaFascicolo(listaTipologiaFascicolo);
		fascicoloView.setListaValoreCausa(listaValoreCausa);

		if (fascicoloView.getFascicoloId() != null && fascicoloView.getFascicoloId() > 0) {
			TipologiaFascicoloView tipologiaFascicoloView = tipologiaFascicoloService
					.leggi(fascicoloView.getTipologiaFascicoloCode(), locale, true);
			List<SettoreGiuridicoView> listaSettoreGiuridico = settoreGiuridicoService
					.leggiPerTipologiaId(tipologiaFascicoloView.getVo().getId(), true);
			fascicoloView.setListaSettoreGiuridico(listaSettoreGiuridico);
			SettoreGiuridicoView settoreGiuridicoView = settoreGiuridicoService
					.leggi(fascicoloView.getSettoreGiuridicoCode(), locale, true);

			long idSettore = fascicoloView.getSettoreGiuridicoCode().equalsIgnoreCase(SETTORE_GIURIDICO_ARBITRALE_CODE)
					|| fascicoloView.getSettoreGiuridicoCode().equalsIgnoreCase(SETTORE_GIURIDICO_JOIN_VENTURE_CODE) ? 0
							: settoreGiuridicoView.getVo().getId();
			if (!fascicoloView.getTipologiaFascicoloCode().equals(TIPOLOGIA_FASCICOLO_NOTARILE_COD)) {
				JSONArray jsonArray = materiaService.leggiAlberaturaMateria(idSettore, locale, true);
				if (jsonArray != null) {
					fascicoloView.setJsonAlberaturaMaterie(jsonArray.toString());
				} else {
					fascicoloView.setJsonAlberaturaMaterie(null);
				}
			}

			if (fascicoloView.getRicorsoCode() != null && fascicoloView.getRicorsoCode().trim().length() > 0) {
				RicorsoView ricorso = ricorsoService.leggi(fascicoloView.getRicorsoCode(), locale, true);
				fascicoloView.setListaOrganoGiudicante(organoGiudicanteService.leggiDaRicorso(ricorso.getVo().getId()));
			}

			if (fascicoloView.getGiudizioCode() != null && fascicoloView.getGiudizioCode().trim().length() > 0) {
				GiudizioView giudizio = giudizioService.leggi(fascicoloView.getGiudizioCode(), locale, true);
				fascicoloView.setListaOrganoGiudicante(organoGiudicanteService.leggiDaGiudizio(giudizio.getVo().getId()));
			}

			if (fascicoloView.getTipologiaFascicoloCode().equals(TIPOLOGIA_FASCICOLO_STRAGIUDIZIALE_COD)) {
				fascicoloView.setListaTipoControparte(
						anagraficaStatiTipiService.leggiTipoEntita(locale.getLanguage().toUpperCase(), true));

			}

			if (fascicoloView.getSettoreGiuridicoCode().equalsIgnoreCase(SETTORE_GIURIDICO_CIVILE_CODE)) {
				fascicoloView.setListaTipoControparte(
						anagraficaStatiTipiService.leggiTipoEntita(locale.getLanguage().toUpperCase(), true));
				fascicoloView.setListaGiudizio(
						giudizioService.leggiDaSettoreGiuridicoId(settoreGiuridicoView.getVo().getId(), true));
				fascicoloView.setListaTipoSoggetto(
						anagraficaStatiTipiService.leggiTipoEntita(locale.getLanguage().toUpperCase(), true));

				fascicoloView.setListaPosizioneSocieta(anagraficaStatiTipiService
						.leggiPosizioneSocietaPerSettoreGiuridicoId(settoreGiuridicoView.getVo().getId(), true));

			} else if (fascicoloView.getSettoreGiuridicoCode()
					.equalsIgnoreCase(SETTORE_GIURIDICO_AMMINISTRATIVO_CODE)) {

				fascicoloView.setListaTipoSoggetto(
						anagraficaStatiTipiService.leggiTipoEntita(locale.getLanguage().toUpperCase(), true));
				fascicoloView.setListaRicorso(ricorsoService.leggi(locale, true));
				
				fascicoloView.setListaPosizioneSocieta(anagraficaStatiTipiService
						.leggiPosizioneSocietaPerSettoreGiuridicoId(settoreGiuridicoView.getVo().getId(), true));

			} else if (fascicoloView.getSettoreGiuridicoCode().equalsIgnoreCase(SETTORE_GIURIDICO_PENALE_CODE)) {
				fascicoloView.setListaTipoSoggetto(
						anagraficaStatiTipiService.leggiTipoEntita(locale.getLanguage().toUpperCase(), true));
				fascicoloView.setListaGiudizio(
						giudizioService.leggiDaSettoreGiuridicoId(settoreGiuridicoView.getVo().getId(), true));
				fascicoloView.setListaTipoSoggettoIndagato(
						anagraficaStatiTipiService.leggiTipoSoggettoIndagato(locale.getLanguage().toUpperCase(), true));

			} else if (fascicoloView.getSettoreGiuridicoCode().equalsIgnoreCase(SETTORE_GIURIDICO_ARBITRALE_CODE)) {
				fascicoloView.setListaTipoControparte(
						anagraficaStatiTipiService.leggiTipoEntita(locale.getLanguage().toUpperCase(), true));

			}
			if (fascicoloView.getTipologiaFascicoloCode().equalsIgnoreCase(TIPOLOGIA_FASCICOLO_NOTARILE_COD)) { 
				fascicoloView.setListaTipoPrestazioneNotarile(
						anagraficaStatiTipiService.leggiTipoPrestazioneNotarile(locale.getLanguage().toUpperCase(), true)); 
			}

		}
	}

	@Override
	public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {
	}
	    
}
