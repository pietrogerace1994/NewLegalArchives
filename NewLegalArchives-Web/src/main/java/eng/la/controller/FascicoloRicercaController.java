package eng.la.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eng.la.business.*;
import eng.la.model.view.*;
import eng.la.persistence.*;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

//import com.filenet.api.collection.DocumentSet;
//import com.filenet.api.collection.PageIterator;
//@@DDS import com.filenet.api.core.Folder;
import it.snam.ned.libs.dds.dtos.v2.folder.Folder;
import it.snam.ned.libs.dds.dtos.v2.Document;
//import com.filenet.apiimpl.core.DocumentImpl;
//import com.filenet.apiimpl.core.EngineObjectImpl;

import eng.la.business.mail.EmailNotificationService;
import eng.la.business.websocket.WebSocketPublisher;
import eng.la.business.workflow.FascicoloWfService;
import eng.la.model.Atto;
import eng.la.model.Controparte;
import eng.la.model.Fascicolo;
import eng.la.model.Incarico;
import eng.la.model.NotificaWeb;
import eng.la.model.Proforma;
import eng.la.model.Progetto;
import eng.la.model.RFascicoloSocieta;
import eng.la.model.Utente;
import eng.la.model.rest.Event;
import eng.la.model.rest.FascicoloRest;
import eng.la.model.rest.RiassegnaRest;
import eng.la.model.rest.RicercaFascicoloRest;
import eng.la.model.rest.RisultatoOperazioneRest;
import eng.la.model.rest.StepWFRest;
import eng.la.model.rest.TreeviewData;
//@@DDS import eng.la.model.view.UtenteView;
//@@DDS import eng.la.persistence.DocumentaleDAO;
import eng.la.util.DateUtil;
import eng.la.util.ListaPaginata;
import eng.la.util.SpringUtil;
import eng.la.util.costants.Costanti;
import eng.la.util.filenet.model.FileNetClassNames;
import eng.la.util.filenet.model.FileNetUtil;
//engsecurity VA
import eng.la.util.va.csrf.HTMLActionSupport;

@Controller("fascicoloRicercaController")
@SessionAttributes("fascicoloRicercaView")
public class FascicoloRicercaController extends BaseController {
    private static final Logger logger = Logger.getLogger(FascicoloRicercaController.class);

    private static final String MODEL_VIEW_NOME = "fascicoloRicercaView";
    private static final String PAGINA_RICERCA_PATH = "fascicolo/cercaFascicoli";
    private static final String PAGINA_RICERCA_MODALE_PATH = "fascicolo/modale/cercaFascicoli";
    private static final String PAGINA_RICERCA_MODALE_DA_INCARICO_PATH = "fascicolo/modale/cercaFascicoliDaIncarico";
    private static final String PAGINA_AZIONI_CERCA_FASCICOLO = "fascicolo/azioniCercaFascicoli";
    private static final String PAGINA_AZIONI_FASCICOLO = "fascicolo/azioniFascicolo";
    private static final String PAGINA_CONTENUTO_PATH = "fascicolo/contenutoFascicolo";
    private static final String PAGINA_CRONOLOGIA_PATH = "fascicolo/cronologiaFascicolo";
    private static final String PAGINA_PERMESSI_FASCICOLO = "fascicolo/permessiFascicolo";
    private static final String PAGINA_PERMESSI_FASCICOLI = "fascicolo/permessiFascicoli";
    private static final String FASCICOLO_ICON = "glyphicon glyphicon-folder-open palette-Red-300 text";
    private static final String INCARICO_ICON = "glyphicon glyphicon-folder-open palette-Blue-300 text";
    private static final String PROFORMA_ICON = "glyphicon glyphicon-folder-open palette-Orange-300 text";
    private static final String DOCUMENTO_ICON = "glyphicon glyphicon-file palette-Grey-500 text";
    private static final String ATTO_ICON = "glyphicon glyphicon-file palette-Green-300 text";
    private static final String ICON_NOTAPROP_INCARICO = "glyphicon glyphicon-file palette-Blue-500 text";
    private static final String ICON_LETTERA_INCARICO = "glyphicon glyphicon-file palette-Blue-600 text";
    private static final String FASCICOLO = "FASCICOLO";
    private static final String INCARICO = "INCARICO";
    private static final String DOCUMENTO = "DOCUMENTO";
    private static final String ARBITRALE = "ARBITRALE";
    private static final String PROFORMA = "PROFORMA";
    private static final String ATTO = "ATTO";
    private static final String PROPOSTA_INCARICO = "PROPOSTA_INCARICO";
    private static final String LETTERA_INCARICO = "LETTERA_INCARICO";
    private static final String SCHEDA_VALUTAZIONE_DOC = "SCHEDA_VALUTAZIONE";
    public static final String PREFIX_SCHEDA_VALUTAZIONE = "SCHEDA_VALUTAZIONE_";
    private static final String ICON_SCHEDA_VALUTAZIONE = "glyphicon glyphicon-file palette-Orange-600 text";
    private static final String SCHEDA_VALUTAZIONE = "glyphicon glyphicon-fileSCHEDA_VALUTAZIONE";

    @Autowired
    private FascicoloService fascicoloService;

    public void setFascicoloService(FascicoloService fascicoloService) {
        this.fascicoloService = fascicoloService;
    }

    @Autowired
    private FascicoloWfService fascicoloWfService;

    public void setFascicoloWfService(FascicoloWfService fascicoloWfService) {
        this.fascicoloWfService = fascicoloWfService;
    }

    @Autowired
    private ProformaService proformaService;

    public void setProformaService(ProformaService proformaService) {
        this.proformaService = proformaService;
    }


    @Autowired
    private UtenteService utenteService;

    public void setFascicoloService(UtenteService utenteService) {
        this.utenteService = utenteService;
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
    private AnagraficaStatiTipiService anagraficaStatiTipiService;

    public void setAnagraficaStatiTipiService(AnagraficaStatiTipiService anagraficaStatiTipiService) {
        this.anagraficaStatiTipiService = anagraficaStatiTipiService;
    }

    @Autowired
    private EmailNotificationService emailNotificationService;

    public void setEmailNotificationService(EmailNotificationService emailNotificationService) {
        this.emailNotificationService = emailNotificationService;
    }

    @Autowired
    private NotificaWebService notificaWebService;

    public void setNotificaWebService(NotificaWebService notificaWebService) {
        this.notificaWebService = notificaWebService;
    }

    @Autowired
    private SocietaService societaService;

    public void setSocietaService(SocietaService societaService) {
        this.societaService = societaService;
    }


    @RequestMapping(value = "/fascicolo/elimina", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    RisultatoOperazioneRest eliminaFascicolo(@RequestParam("id") long id) {
        RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
        try {
            fascicoloService.eliminaFascicolo(id);
            risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return risultato;
    }


    @RequestMapping(value = "/fascicolo/richiediChiusura", produces = "application/json")
    public @ResponseBody
    RisultatoOperazioneRest richiediChiusura(@RequestParam("id") long id, HttpServletRequest request) {
        RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
        try {
            UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
            if (utenteView != null) {
                FascicoloView fascicolo = fascicoloService.leggi(id);
                if (fascicolo.getVo().getStatoFascicolo().getCodGruppoLingua().equals(FASCICOLO_STATO_COMPLETATO)) {
                    boolean ret = fascicoloWfService.avviaWorkflow(id, utenteView.getVo().getUseridUtil());
                    risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
                    if (ret) {
                        //invio la notifica
                        UtenteView assegnatario = utenteService.leggiAssegnatarioWfFascicolo(id);
                        //					DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                        if (assegnatario != null) {

                            StepWFRest stepSuccessivo = new StepWFRest();
                            stepSuccessivo.setId(0);

                            Event event = WebSocketPublisher.getInstance().createEvent(Costanti.WEBSOCKET_EVENTO_NOTIFICHE, stepSuccessivo, assegnatario.getVo().getUseridUtil());
                            WebSocketPublisher.getInstance().publishEvent(event);
                        }
                        //invio la e-mail
                        try {
                            emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, CostantiDAO.FASCICOLO, id, request.getLocale().getLanguage().toUpperCase(), utenteView.getVo().getUseridUtil());
                        } catch (Exception e) {
                            System.out.println("Errore in invio e-mail" + e);
                        }
                    }
                } else {
                    risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "stato.fascicolo.non.valido");
                }
            } else {
                risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "user.unathorized");
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return risultato;
    }

    @RequestMapping(value = "/fascicolo/completaFascicolo", produces = "application/json")
    public @ResponseBody
    RisultatoOperazioneRest completaFascicolo(@RequestParam("id") long id, HttpServletRequest request) {
        RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");


        try {
            UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
            if (utenteView != null) {
                FascicoloView fascicolo = fascicoloService.leggi(id);
                if (fascicolo.getVo().getStatoFascicolo().getCodGruppoLingua().equals(FASCICOLO_STATO_APERTO)) {
                    fascicoloService.completaFascicolo(id);
                    risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
                } else {
                    risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "stato.fascicolo.non.valido");
                }
            } else {
                risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "user.unathorized");
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return risultato;
    }

    @RequestMapping(value = "/fascicolo/riportaInCompletatoFascicolo", produces = "application/json")
    public @ResponseBody
    RisultatoOperazioneRest riportaInCompletatoFascicolo(@RequestParam("id") long id, HttpServletRequest request) {


        RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
        try {
            UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
            if (utenteView != null) {
                FascicoloView fascicolo = fascicoloService.leggi(id);
                if (fascicolo.getVo().getStatoFascicolo().getCodGruppoLingua().equals(FASCICOLO_STATO_ATTESA_AUTORIZZAZIONE_CHIUSURA)) {
                    fascicoloWfService.riportaInCompletatoWorkflow(id, utenteView.getVo().getUseridUtil());
                    risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
                    //invio la e-mail
                    try {
                        emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, CostantiDAO.FASCICOLO, id, request.getLocale().getLanguage().toUpperCase(), utenteView.getVo().getUseridUtil());
                    } catch (Exception e) {
                        System.out.println("Errore in invio e-mail" + e);
                    }
                } else {
                    risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "stato.fascicolo.non.valido");
                }
            } else {
                risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "user.unathorized");
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return risultato;
    }

    @RequestMapping(value = "/fascicolo/archiviaFascicolo", produces = "application/json")
    public @ResponseBody
    RisultatoOperazioneRest archiviaFascicolo(@RequestParam("id") long id, @RequestParam("numeroArchivio") Integer numeroArchivio,
                                              @RequestParam("numeroArchivioContenitore") Integer numeroArchivioContenitore, HttpServletRequest request) {
        RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
        try {
            UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
            if (utenteView != null) {
                FascicoloView fascicolo = fascicoloService.leggi(id);
                if (fascicolo.getVo() != null) {
                    fascicolo.getVo().setNumeroArchivio(NumberUtils.createBigDecimal("" + numeroArchivio));
                    fascicolo.getVo().setNArchivioContenitore(NumberUtils.createBigDecimal("" + numeroArchivioContenitore));
                    fascicoloService.aggiornaFascicolo(fascicolo);
                    risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
                } else {
                    risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "stato.fascicolo.non.valido");
                }
            } else {
                risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "user.unathorized");
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return risultato;
    }


    @RequestMapping(value = "/fascicolo/riapriFascicolo", produces = "application/json")
    public @ResponseBody
    RisultatoOperazioneRest riapriFascicolo(@RequestParam("id") long id, HttpServletRequest request) {
        RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");


        try {
            UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
            if (utenteView != null) {
                FascicoloView fascicolo = fascicoloService.leggi(id);
                if (fascicolo.getVo().getStatoFascicolo().getCodGruppoLingua().equals(FASCICOLO_STATO_CHIUSO) ||
                        fascicolo.getVo().getStatoFascicolo().getCodGruppoLingua().equals(FASCICOLO_STATO_COMPLETATO)) {

                    fascicoloService.riapriFascicolo(id);

                    NotificaWebView notificaWeb = new NotificaWebView();
                    notificaWeb.setIdFascicolo(id);
                    notificaWeb.setNomeFascicolo(fascicolo.getNome());
                    notificaWeb.setDataInserimento(new Date());
                    NotificaWeb notifica = new NotificaWeb();
                    notifica.setMatricolaMitt(utenteView.getVo());
                    Utente matricolaDest = utenteService.leggiUtenteDaMatricola(utenteView.getVo().getMatricolaRespUtil()).getVo();
                    notifica.setMatricolaDest(matricolaDest == null ? utenteView.getVo() : matricolaDest);
                    notifica.setDataNotifica(new Date());
                    notifica.setKeyMessage("fascicolo.riaperto.indata");
                    notifica.setJsonParam(notificaWeb.getJsonParamAperturaRiaperturaFascicolo());
                    notificaWeb.setVo(notifica);
                    notificaWebService.inserisci(notificaWeb);

                    risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
                } else {
                    risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "stato.fascicolo.non.valido");
                }
            } else {
                risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "user.unathorized");
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return risultato;
    }

    @RequestMapping("/fascicolo/contenuto")
    public String contenutoFascicolo(@RequestParam("id") long id, Model model, Locale locale, HttpServletRequest request) {

        FascicoloView fascicoloView = new FascicoloView();
        try {
            FascicoloView fascicoloSalvato = fascicoloService.leggi(id, FetchMode.JOIN);
            if (fascicoloSalvato == null || fascicoloSalvato.getVo() == null) {
                model.addAttribute("errorMessage", "errore.oggetto.non.trovato");
            } else {
                fascicoloView.setFascicoloId(id);
                fascicoloView.setListaCategoriaDocumentale(anagraficaStatiTipiService.leggiTipoCategDocumentale(locale.getLanguage().toUpperCase()));

            }
        } catch (Throwable e) {
            model.addAttribute("errorMessage", "errore.generico");
            e.printStackTrace();
        }

        model.addAttribute(MODEL_VIEW_NOME, fascicoloView);
        return model.containsAttribute("errorMessage") ? "redirect:/errore.action" : PAGINA_CONTENUTO_PATH;
    }

    @RequestMapping("/fascicolo/cronologia")
    public String cronologiaFascicolo(@RequestParam("id") long id, Model model, Locale locale, HttpServletRequest request) {

        FascicoloView fascicoloView = new FascicoloView();
        try {
            FascicoloView fascicoloSalvato = fascicoloService.leggiPerCronologia(id);
            if (fascicoloSalvato == null || fascicoloSalvato.getVo() == null) {
                model.addAttribute("errorMessage", "errore.oggetto.non.trovato");
            } else {
                fascicoloView.setFascicoloId(id);
                fascicoloView.setNome(fascicoloSalvato.getVo().getNome());
                UtenteView owner = utenteService.leggiUtenteDaUserId(fascicoloSalvato.getVo().getLegaleInterno());
                fascicoloView.setLegaleInterno(owner.getVo().getNominativoUtil());
                String dataApertura = DateUtil.formattaData(fascicoloSalvato.getVo().getDataCreazione().getTime());

                fascicoloView.setDataApertura(dataApertura);

                if (fascicoloSalvato.getVo().getDataUltimaModifica() != null) {
                    String dataUltimaModifica = DateUtil.formattaData(fascicoloSalvato.getVo().getDataUltimaModifica().getTime());
                    fascicoloView.setDataUltimaModifica(dataUltimaModifica);
                }

                fascicoloView.setListaIncarichiCron(fascicoloSalvato.getListaIncarichiCron());
                fascicoloView.setListaProformaCron(fascicoloSalvato.getListaProformaCron());
            }
        } catch (Throwable e) {
            model.addAttribute("errorMessage", "errore.generico");
            e.printStackTrace();
        }

        model.addAttribute(MODEL_VIEW_NOME, fascicoloView);
        //model.addAllAttributes(MODEL_)
        return model.containsAttribute("errorMessage") ? "redirect:/errore.action" : PAGINA_CRONOLOGIA_PATH;
    }

    @RequestMapping(value = "/fascicolo/leggiContenuto", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    List<TreeviewData> leggiContenuto(@RequestParam("id") long id, HttpServletRequest request) {
        List<TreeviewData> risultato = new ArrayList<TreeviewData>();
        try {
            UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
            if (utenteView != null) {
                FascicoloView fascicoloSalvato = fascicoloService.leggi(id, FetchMode.JOIN);
                String statoFascicolo = null;
                if (fascicoloSalvato.getVo().getStatoFascicolo() != null) {
                    statoFascicolo = fascicoloSalvato.getVo().getStatoFascicolo().getDescrizione();
                }
                TreeviewData fascicolo = new TreeviewData(fascicoloSalvato.getVo().getId() + "",
                        "Fascicolo: " + fascicoloSalvato.getVo().getNome(), null, FASCICOLO_ICON, null, FASCICOLO, null, statoFascicolo);
                List<TreeviewData> nodesFascicolo = new ArrayList<TreeviewData>();
                Collection<Incarico> incarichi = fascicoloSalvato.getVo().getIncaricos();
                if (incarichi != null) {
                    for (Incarico incarico : incarichi) {

                        String statoIncarico = null;
                        if (incarico.getStatoIncarico() != null) {
                            statoIncarico = incarico.getStatoIncarico().getDescrizione();
                        }

                        if (incarico.getDataCancellazione() == null) {

                            String icona = incarico.getCollegioArbitrale().equals(TRUE_CHAR) ? DOCUMENTO_ICON : INCARICO_ICON;
                            String nome = incarico.getCollegioArbitrale().equals(TRUE_CHAR) ? incarico.getNomeCollegioArbitrale() : incarico.getNomeIncarico();
                            String tipo = incarico.getCollegioArbitrale().equals(TRUE_CHAR) ? ARBITRALE : INCARICO;

                            TreeviewData incaricoNode = new TreeviewData(incarico.getId() + "", nome,
                                    null, icona, null, tipo, null, statoIncarico);
                            if (incarico.getCollegioArbitrale().equals(FALSE_CHAR)) {
                                List<TreeviewData> incaricoNodes = caricaIncaricoNodes(incarico);
                                incaricoNode.setNodes(incaricoNodes);
                            }
                            nodesFascicolo.add(incaricoNode);
                        }
                    }
                }

                /*CARICA PROFORMA, LOGICA ATTUALE VA IN ERRORE LAZY HIBERNATE*/
                if (incarichi != null) {
                    for (Incarico incarico : incarichi) {
                        List<ProformaView> listaProforma = proformaService.leggiProformaAssociatiAIncarico(incarico.getId());
                        if (listaProforma != null) {
                            for (ProformaView proformaView : listaProforma) {

                                String statoProforma = null;
                                if (proformaView.getVo().getStatoProforma() != null) {
                                    statoProforma = proformaView.getVo().getStatoProforma().getDescrizione();
                                }

                                TreeviewData proformaNode = new TreeviewData(proformaView.getVo().getId() + "",
                                        "Proforma: " + proformaView.getVo().getNomeProforma(), null, PROFORMA_ICON, null, PROFORMA, null, statoProforma);
                                List<TreeviewData> proformaNodes = caricaProformaNodes(proformaView.getVo(), fascicoloSalvato.getVo());
                                proformaNode.setNodes(proformaNodes);
                                nodesFascicolo.add(proformaNode);
                            }
                        }
                    }
                }

                boolean isPenale = fascicoloSalvato.getVo().getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);

                String pathCartellaFascicolo = FileNetUtil.getFascicoloCartella(
                        fascicoloSalvato.getVo().getDataCreazione(), fascicoloSalvato.getVo().getNome());
                //@@DDS DocumentaleDAO documentaleDAO = (DocumentaleDAO) SpringUtil.getBean("documentaleDAO");
                //@@DDS DocumentaleCryptDAO documentaleCryptDAO = (DocumentaleCryptDAO) SpringUtil.getBean("documentaleCryptDAO");
                DocumentaleDdsDAO documentaleDdsDAO = (DocumentaleDdsDAO) SpringUtil.getBean("documentaleDdsDAO");
                DocumentaleDdsCryptDAO documentaleDdsCryptDAO = (DocumentaleDdsCryptDAO) SpringUtil.getBean("documentaleDdsCryptDAO");


                //@@DDS Folder cartellaFascicolo = isPenale ?  documentaleCryptDAO.leggiCartella(pathCartellaFascicolo) : documentaleDAO.leggiCartella(pathCartellaFascicolo);

                List<Document> listaDocumenti = isPenale ? documentaleDdsCryptDAO.leggiDocumentiCartella(pathCartellaFascicolo) : documentaleDdsDAO.leggiDocumentiCartella(pathCartellaFascicolo);

				/* @@DDS  INIZIO
				DocumentSet documenti = cartellaFascicolo.get_ContainedDocuments();
				if (documenti != null) {
					PageIterator it = documenti.pageIterator();
					while (it.nextPage()) {
						EngineObjectImpl[] documentiArray = (EngineObjectImpl[]) it.getCurrentPage();
						for (EngineObjectImpl objDocumento : documentiArray) {
							DocumentImpl documento = (DocumentImpl) objDocumento;
							if( !documento.getClassName().equals(FileNetClassNames.FASCICOLO_DOCUMENT) ){ 
								TreeviewData treeNode = new TreeviewData(documento.get_Id().toString(),
										documento.get_Name(), null, DOCUMENTO_ICON, null, DOCUMENTO, null, isPenale);
								nodesFascicolo.add(treeNode);
							}
						}
					}
				}
				FINE COMMENTO*/
                if (listaDocumenti != null) {

                    for (Document documento : listaDocumenti) {

                        if (!documento.getDocumentalClass().equals(FileNetClassNames.FASCICOLO_DOCUMENT)) {
                            TreeviewData treeNode = new TreeviewData(documento.getId().toString(),
                                    documento.getContents().get(0).getContentsName(), null, DOCUMENTO_ICON, null, DOCUMENTO, null, isPenale);
                            nodesFascicolo.add(treeNode);
                        }
                    }
                } else {
                    logger.info(" La lista vuota :  nessun documento");
                }
                //@@DDS fine

                Collection<Atto> atti = fascicoloSalvato.getVo().getAttos();
                if (atti != null) {
                    for (Atto atto : atti) {

                        String statoAtto = null;
                        if (atto.getStatoAtto() != null) {
                            statoAtto = atto.getStatoAtto().getDescrizione();
                        }

                        TreeviewData attoNode = new TreeviewData(atto.getId() + "", atto.getNumeroProtocollo(), null,
                                ATTO_ICON, null, ATTO, null, statoAtto);
                        nodesFascicolo.add(attoNode);
                    }
                }

                fascicolo.setNodes(nodesFascicolo);

                risultato.add(fascicolo);
            } else {

            }

        } catch (Throwable e) {

            e.printStackTrace();
        }
        return risultato;
    }


    private List<TreeviewData> caricaProformaNodes(Proforma proforma, Fascicolo fascicolo) throws Throwable {

        List<TreeviewData> nodesProforma = new ArrayList<TreeviewData>();
        String nomeCartellaProforma = FileNetUtil.getProformaCartella(proforma.getId(), fascicolo.getDataCreazione(),
                fascicolo.getNome(), proforma.getNomeProforma());
        boolean isPenale = fascicolo.getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);

		/*@@DDS
		DocumentaleDAO documentaleDAO = (DocumentaleDAO) SpringUtil.getBean("documentaleDAO");
		DocumentaleCryptDAO documentaleCryptDAO = (DocumentaleCryptDAO) SpringUtil.getBean("documentaleCryptDAO");
		Folder cartellaIncarico = isPenale ? documentaleCryptDAO.leggiCartella(nomeCartellaProforma)
				: documentaleDAO.leggiCartella(nomeCartellaProforma);

		 */

        DocumentaleDdsDAO documentaleDdsDAO = (DocumentaleDdsDAO) SpringUtil.getBean("documentaleDdsDAO");
        DocumentaleDdsCryptDAO documentaleDdsCryptDAO = (DocumentaleDdsCryptDAO) SpringUtil.getBean("documentaleDdsCryptDAO");
        Folder cartellaIncarico = isPenale ? documentaleDdsCryptDAO.leggiCartella(nomeCartellaProforma)
                : documentaleDdsDAO.leggiCartella(nomeCartellaProforma);

		/*@@DDS
		DocumentSet documenti = cartellaIncarico.get_ContainedDocuments();
		
		if (documenti != null) {
			PageIterator it = documenti.pageIterator();
			while (it.nextPage()) {
				EngineObjectImpl[] documentiArray = (EngineObjectImpl[]) it.getCurrentPage();
				for (EngineObjectImpl objDocumento : documentiArray) {
					DocumentImpl documento = (DocumentImpl) objDocumento;
					if( !documento.get_Name().startsWith( PREFIX_SCHEDA_VALUTAZIONE )){
						if( !documento.getClassName().equals(FileNetClassNames.PROFORMA_DOCUMENT) ){  
							TreeviewData treeNode = new TreeviewData(documento.get_Id().toString(),
									documento.get_Name(), null, DOCUMENTO_ICON, null,DOCUMENTO, null, isPenale);
							nodesProforma.add(treeNode);
						}
					}
					else{
						TreeviewData treeNode = new TreeviewData(documento.get_Id().toString(),
								documento.get_Name(), null, ICON_SCHEDA_VALUTAZIONE, null,SCHEDA_VALUTAZIONE, null, null);
						nodesProforma.add(treeNode);
					}
				}
			}

		}

		 @@DDS fine commento */
        List<Document> listaDocumenti = isPenale ? documentaleDdsCryptDAO.leggiDocumentiCartella(nomeCartellaProforma): documentaleDdsDAO.leggiDocumentiCartella(nomeCartellaProforma);
        if (listaDocumenti != null) {

            for (Document documento : listaDocumenti) {

                if (!documento.getDocumentalClass().equals(FileNetClassNames.FASCICOLO_DOCUMENT)) {
                    TreeviewData treeNode = new TreeviewData(documento.getId().toString(),
                            documento.getContents().get(0).getContentsName(), null, DOCUMENTO_ICON, null, DOCUMENTO, null, isPenale);
                    nodesProforma.add(treeNode);
                }
            }
        } else {
            logger.info(" La lista vuota :  nessun documento");
        }

        if (proforma.getSchedaValutazione() != null) {
            TreeviewData schedaValutazioneNode = new TreeviewData(proforma.getId() + "", proforma.getSchedaValutazione().getReturnFileName(), null, ICON_SCHEDA_VALUTAZIONE, null, SCHEDA_VALUTAZIONE_DOC, null, null);
            nodesProforma.add(schedaValutazioneNode);
        }

        return nodesProforma;
    }

    private List<TreeviewData> caricaIncaricoNodes(Incarico incarico) throws Throwable {
        boolean isPenale = incarico.getFascicolo().getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);

		/*@@DDS
		DocumentaleDAO documentaleDAO = (DocumentaleDAO) SpringUtil.getBean("documentaleDAO");
		DocumentaleCryptDAO documentaleCryptDAO = (DocumentaleCryptDAO) SpringUtil.getBean("documentaleCryptDAO");
	*/
        DocumentaleDdsDAO documentaleDdsDAO = (DocumentaleDdsDAO) SpringUtil.getBean("documentaleDdsDAO");
        DocumentaleDdsCryptDAO documentaleDdsCryptDAO = (DocumentaleDdsCryptDAO) SpringUtil.getBean("documentaleDdsCryptDAO");
        List<TreeviewData> nodesIncarico = new ArrayList<TreeviewData>();
        String pathCartellaIncarico = FileNetUtil.getIncaricoCartella(incarico.getId(),
                incarico.getFascicolo().getDataCreazione(), incarico.getFascicolo().getNome(), incarico.getNomeIncarico());

        //@@DDS Folder cartellaIncarico = isPenale? documentaleCryptDAO.leggiCartella(pathCartellaIncarico) : documentaleDAO.leggiCartella(pathCartellaIncarico);
        Folder cartellaIncarico = isPenale ? documentaleDdsCryptDAO.leggiCartella(pathCartellaIncarico) : documentaleDdsDAO.leggiCartella(pathCartellaIncarico);

		/*@@DDS
		DocumentSet documenti = cartellaIncarico.get_ContainedDocuments();

		if (documenti != null) {
			PageIterator it = documenti.pageIterator();
			while (it.nextPage()) {
				EngineObjectImpl[] documentiArray = (EngineObjectImpl[]) it.getCurrentPage();
				for (EngineObjectImpl objDocumento : documentiArray) {
					DocumentImpl documento = (DocumentImpl) objDocumento;
					TreeviewData treeNode = new TreeviewData(documento.get_Id().toString(),
							documento.get_Name(), null, DOCUMENTO_ICON, null,DOCUMENTO, null,isPenale);
					nodesIncarico.add(treeNode);
				}
			}
		}
		*/
        List<Document> listaDocumenti = isPenale ? documentaleDdsCryptDAO.leggiDocumentiCartella(pathCartellaIncarico): documentaleDdsDAO.leggiDocumentiCartella(pathCartellaIncarico);
        if (listaDocumenti != null) {

            for (Document documento : listaDocumenti) {

                if (!documento.getDocumentalClass().equals(FileNetClassNames.FASCICOLO_DOCUMENT)) {
                    TreeviewData treeNode = new TreeviewData(documento.getId().toString(),
                            documento.getContents().get(0).getContentsName(), null, DOCUMENTO_ICON, null, DOCUMENTO, null, isPenale);
                    nodesIncarico.add(treeNode);
                }
            }
        } else {
            logger.info(" La lista vuota :  nessun documento");
        }
        if (incarico.getLetteraIncarico() != null && incarico.getLetteraIncarico().getReturnFileName() != null) {
            TreeviewData letteraIncaricoNode = new TreeviewData(incarico.getLetteraIncarico().getId() + "", incarico.getLetteraIncarico().getReturnFileName(), null, ICON_LETTERA_INCARICO, null, LETTERA_INCARICO, null, null);
            nodesIncarico.add(letteraIncaricoNode);
        }

        if (incarico.getNotaPropIncarico() != null && incarico.getNotaPropIncarico().getReturnFileName() != null) {
            TreeviewData notaProp = new TreeviewData(incarico.getNotaPropIncarico().getId() + "", incarico.getNotaPropIncarico().getReturnFileName(), null, ICON_NOTAPROP_INCARICO, null, PROPOSTA_INCARICO, null, null);
            nodesIncarico.add(notaProp);
        }
        return nodesIncarico;
    }

    @RequestMapping(value = "/fascicolo/ricerca2", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    RicercaFascicoloRest cercaFascicoli2(HttpServletRequest request, Locale locale) {
        try {

            // INIZIO -- PERMESSI ---
            // scrittura
            String tipoPermesso = CostantiDAO.TIPO_AUTORIZZAZIONE_UTENTE_SCRITTURA;
            // owner
            UtenteView utenteView = (UtenteView) request.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
            String curUser_userId = utenteView.getVo().getUseridUtil();
            String owner = curUser_userId;
            // FINE -- PERMESSI ---

            int numElementiPerPagina = request.getParameter("limit") == null ? ELEMENTI_PER_PAGINA : NumberUtils.toInt(request.getParameter("limit"));

            String tipologiaFascicoloCode = request.getParameter("tipologiaFascicoloCode") == null ? "" : request.getParameter("tipologiaFascicoloCode");
            String settoreGiuridicoCode = request.getParameter("settoreGiuridicoCode") == null ? "" : request.getParameter("settoreGiuridicoCode");

            String offset = request.getParameter("offset");
            int numeroPagina = offset == null || offset.equals("0") ? 1
                    : (NumberUtils.toInt(offset) / numElementiPerPagina) + 1;
            String ordinamento = request.getParameter("sort") == null ? "id" : request.getParameter("sort");
            String tipoOrdinamento = request.getParameter("order") == null ? "ASC" : request.getParameter("order");
            if (request.getParameter("sort") == null) {
                tipoOrdinamento = "DESC";
            }
            String nome = request.getParameter("nome") == null ? "" : URLDecoder.decode(request.getParameter("nome"), "UTF-8");

            String oggetto = request.getParameter("oggetto") == null ? "" : URLDecoder.decode(request.getParameter("oggetto"), "UTF-8");
            String controparte = request.getParameter("controparte") == null ? "" : URLDecoder.decode(request.getParameter("controparte"), "UTF-8");
            String legaleEsterno = request.getParameter("legaleEsterno") == null ? ""
                    : URLDecoder.decode(request.getParameter("legaleEsterno"), "UTF-8");
            String stato = request.getParameter("stato") == null ? ""
                    : URLDecoder.decode(request.getParameter("stato"), "UTF-8");
            String dal = request.getParameter("dal") == null ? "" : URLDecoder.decode(request.getParameter("dal"), "UTF-8");
            String al = request.getParameter("al") == null ? "" : URLDecoder.decode(request.getParameter("al"), "UTF-8");
            String fascicoloCorrenteId = request.getParameter("fascicoloCorrenteId") == null ? null : request.getParameter("fascicoloCorrenteId");

            TipologiaFascicoloView tipologiaFascicoloView = null;
            if (tipologiaFascicoloCode != null && !tipologiaFascicoloCode.isEmpty()) {
                tipologiaFascicoloView = tipologiaFascicoloService.leggi(tipologiaFascicoloCode, Locale.ITALIAN, false);
            }

            SettoreGiuridicoView settoreGiuridicoView = null;
            if (settoreGiuridicoCode != null && !settoreGiuridicoCode.isEmpty()) {
                settoreGiuridicoView = settoreGiuridicoService.leggi(settoreGiuridicoCode, Locale.ITALIAN, false);
            }

            long idSettore = 0;
            if (settoreGiuridicoView != null && settoreGiuridicoView.getVo() != null && settoreGiuridicoView.getVo().getId() != 0) {
                idSettore = settoreGiuridicoView.getVo().getId();
            }

            String societaAddebito = request.getParameter("societaAddebito") == null ? null : request.getParameter("societaAddebito");


            long idTipologiaTascicolo = 0;
            if (tipologiaFascicoloView != null) {
                if (tipologiaFascicoloView.getVo() != null) {
                    idTipologiaTascicolo = tipologiaFascicoloView.getVo().getId();
                }
            }

            List<Long> listaSNAM_SRG_GNL_STOGIT = societaService.getListaSNAM_SRG_GNL_STOGIT();

            ListaPaginata<FascicoloView> lista = (ListaPaginata<FascicoloView>) fascicoloService.cerca(nome, oggetto, null, null, null, null,
                    dal, al, idTipologiaTascicolo, idSettore, controparte, legaleEsterno, owner, stato, numElementiPerPagina, numeroPagina,
                    ordinamento, tipoOrdinamento, tipoPermesso, societaAddebito, listaSNAM_SRG_GNL_STOGIT);

            RicercaFascicoloRest ricercaModelRest = new RicercaFascicoloRest();
            ricercaModelRest.setTotal(lista.getNumeroTotaleElementi());
            List<FascicoloRest> rows = new ArrayList<FascicoloRest>();
            for (FascicoloView fascicoloView : lista) {
                FascicoloRest fascicoloRest = new FascicoloRest();
                if (fascicoloView.getVo().getId() != NumberUtils.toLong(fascicoloCorrenteId)) {
                    fascicoloRest.setId(fascicoloView.getVo().getId());
                    fascicoloRest.setNome(fascicoloView.getVo().getNome());

                    // owner
                    String userIdOwnerFascicolo = fascicoloView.getVo().getLegaleInterno();
                    UtenteView utenteView2 = utenteService.leggiUtenteDaUserId(userIdOwnerFascicolo);
                    String nominativoUtil = utenteView2.getVo().getNominativoUtil();
                    fascicoloRest.setOwner(nominativoUtil);

                    fascicoloRest.setOggetto(fascicoloView.getVo().getOggettoSintetico());
                    fascicoloRest.setAnno(DateUtil.getAnno(fascicoloView.getVo().getDataCreazione()) + "");

                    fascicoloRest.setDataCreazione(DateUtil.formattaDataOra(fascicoloView.getVo().getDataCreazione().getTime()));
                    StatoFascicoloView statoFascicoloView = anagraficaStatiTipiService.leggiStatiFascicolo(fascicoloView.getVo().getStatoFascicolo().getCodGruppoLingua(), locale.getLanguage().toUpperCase());
                    fascicoloRest.setStato(statoFascicoloView.getVo().getDescrizione());
                    Collection<Controparte> controparteLista = fascicoloView.getVo().getContropartes();
                    if (controparteLista != null && controparteLista.size() > 0) {
                        String controparteDesc = "";
                        for (Controparte controparteTmp : controparteLista) {
                            controparteDesc += controparteTmp.getNome() + "; ";
                        }

                        fascicoloRest.setControparte(controparteDesc);
                    }
                    Collection<Incarico> incaricoLista = fascicoloView.getVo().getIncaricos();
                    if (incaricoLista != null && incaricoLista.size() > 0) {
                        String professionistaDesc = "";
                        for (Incarico incaricoTmp : incaricoLista) {
                            professionistaDesc += incaricoTmp.getProfessionistaEsterno().getCognome() + " " + incaricoTmp.getProfessionistaEsterno().getNome() + "; ";
                        }

                        fascicoloRest.setLegaleEsterno(professionistaDesc);
                    }

                    fascicoloRest.setAzioni("<p id='containerAzioniRigaFascicolo" + fascicoloView.getVo().getId() + "' ></p>");
                    fascicoloRest.setSocietaAddebito(decodeAllSocietaAddebito(fascicoloView.getVo().getRFascicoloSocietas()));
                    fascicoloRest.setSocietaProcedimento(decodeAllSocietaProcedimento(fascicoloView.getVo().getRFascicoloSocietas()));
                    rows.add(fascicoloRest);
                }
            }
            ricercaModelRest.setRows(rows);
            return ricercaModelRest;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;

    }

    @RequestMapping(value = "/fascicolo/ricerca", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    RicercaFascicoloRest cercaFascicoli(HttpServletRequest request, Locale locale) {
        try {


            int numElementiPerPagina = request.getParameter("limit") == null ? ELEMENTI_PER_PAGINA : NumberUtils.toInt(request.getParameter("limit"));

            String tipologiaFascicoloCode = request.getParameter("tipologiaFascicoloCode");
            String settoreGiuridicoCode = request.getParameter("settoreGiuridicoCode") == null ? "" : request.getParameter("settoreGiuridicoCode");

            String offset = request.getParameter("offset");
            int numeroPagina = offset == null || offset.equals("0") ? 1
                    : (NumberUtils.toInt(offset) / numElementiPerPagina) + 1;
            String ordinamento = request.getParameter("sort") == null ? "id" : request.getParameter("sort");
            String tipoOrdinamento = request.getParameter("order") == null ? "ASC" : request.getParameter("order");
            if (request.getParameter("sort") == null) {
                tipoOrdinamento = "DESC";
            }
            String nome = request.getParameter("nome") == null ? "" : URLDecoder.decode(request.getParameter("nome"), "UTF-8");

            String oggetto = request.getParameter("oggetto") == null ? "" : URLDecoder.decode(request.getParameter("oggetto"), "UTF-8");
            String controparte = request.getParameter("controparte") == null ? "" : URLDecoder.decode(request.getParameter("controparte"), "UTF-8");
            String legaleEsterno = request.getParameter("legaleEsterno") == null ? ""
                    : URLDecoder.decode(request.getParameter("legaleEsterno"), "UTF-8");
            String owner = request.getParameter("owner") == null ? ""
                    : URLDecoder.decode(request.getParameter("owner"), "UTF-8");
            String stato = request.getParameter("stato") == null ? ""
                    : URLDecoder.decode(request.getParameter("stato"), "UTF-8");
            String dal = request.getParameter("dal") == null ? "" : URLDecoder.decode(request.getParameter("dal"), "UTF-8");
            String al = request.getParameter("al") == null ? "" : URLDecoder.decode(request.getParameter("al"), "UTF-8");
            String fascicoloCorrenteId = request.getParameter("fascicoloCorrenteId") == null ? null : request.getParameter("fascicoloCorrenteId");
            String societaAddebito = request.getParameter("societaAddebito") == null ? null : request.getParameter("societaAddebito");

            TipologiaFascicoloView tipologiaFascicoloView = tipologiaFascicoloService.leggi(tipologiaFascicoloCode, Locale.ITALIAN, false);
            SettoreGiuridicoView settoreGiuridicoView = settoreGiuridicoService.leggi(settoreGiuridicoCode, Locale.ITALIAN, false);
            long idSettore = 0;
            if (settoreGiuridicoView != null && settoreGiuridicoView.getVo() != null && settoreGiuridicoView.getVo().getId() != 0) {
                idSettore = settoreGiuridicoView.getVo().getId();
            }

            List<Long> listaSNAM_SRG_GNL_STOGIT = societaService.getListaSNAM_SRG_GNL_STOGIT();

            long idTipologiaFascicolo = 0;
            if (tipologiaFascicoloView.getVo() != null) {
                idTipologiaFascicolo = tipologiaFascicoloView.getVo().getId();
            }

            ListaPaginata<FascicoloView> lista = (ListaPaginata<FascicoloView>) fascicoloService.cerca(nome, oggetto, null, null, null, null,
                    dal, al, idTipologiaFascicolo, idSettore, controparte, legaleEsterno, owner, stato, numElementiPerPagina, numeroPagina,
                    ordinamento, tipoOrdinamento, null, societaAddebito, listaSNAM_SRG_GNL_STOGIT);
            RicercaFascicoloRest ricercaModelRest = new RicercaFascicoloRest();
            ricercaModelRest.setTotal(lista.getNumeroTotaleElementi());
            List<FascicoloRest> rows = new ArrayList<FascicoloRest>();
            for (FascicoloView fascicoloView : lista) {
                FascicoloRest fascicoloRest = new FascicoloRest();
                if (fascicoloView.getVo().getId() != NumberUtils.toLong(fascicoloCorrenteId)) {
                    fascicoloRest.setId(fascicoloView.getVo().getId());
                    fascicoloRest.setNome(fascicoloView.getVo().getNome());
                    fascicoloRest.setOggetto(fascicoloView.getVo().getOggettoSintetico());
                    fascicoloRest.setAnno(DateUtil.getAnno(fascicoloView.getVo().getDataCreazione()) + "");

                    fascicoloRest.setDataCreazione(DateUtil.formattaDataOra(fascicoloView.getVo().getDataCreazione().getTime()));
                    StatoFascicoloView statoFascicoloView = anagraficaStatiTipiService.leggiStatiFascicolo(fascicoloView.getVo().getStatoFascicolo().getCodGruppoLingua(), locale.getLanguage().toUpperCase());
                    fascicoloRest.setStato(statoFascicoloView.getVo().getDescrizione());

                    if (fascicoloView.getVo().getSettoreGiuridico().getCodGruppoLingua().equals("TSTT_3"))
                        fascicoloRest.setControparte(fascicoloView.getVo().getRicorrente());
                    else {

                        Collection<Controparte> controparteLista = fascicoloView.getVo().getContropartes();
                        if (controparteLista != null && controparteLista.size() > 0) {
                            String controparteDesc = "";
                            for (Controparte controparteTmp : controparteLista) {
                                controparteDesc += controparteTmp.getNome() + "; ";
                            }
                            fascicoloRest.setControparte(controparteDesc);
                        }

                    }
                    Collection<Incarico> incaricoLista = fascicoloView.getVo().getIncaricos();
                    if (incaricoLista != null && incaricoLista.size() > 0) {
                        String professionistaDesc = "";
                        for (Incarico incaricoTmp : incaricoLista) {
                            professionistaDesc += incaricoTmp.getProfessionistaEsterno().getCognome() + " " + incaricoTmp.getProfessionistaEsterno().getNome() + "; ";
                        }

                        fascicoloRest.setLegaleEsterno(professionistaDesc);
                    }

                    fascicoloRest.setAzioni("<p id='containerAzioniRigaFascicolo" + fascicoloView.getVo().getId() + "' ></p>");
                    fascicoloRest.setSocietaAddebito(decodeAllSocietaAddebito(fascicoloView.getVo().getRFascicoloSocietas()));
                    fascicoloRest.setSocietaProcedimento(decodeAllSocietaProcedimento(fascicoloView.getVo().getRFascicoloSocietas()));
                    rows.add(fascicoloRest);
                }
            }
            ricercaModelRest.setRows(rows);
            return ricercaModelRest;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;

    }

    @RequestMapping(value = "/fascicolo/uploadDocumento", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    RisultatoOperazioneRest uploadDocumento(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
        try {
            Long fascicoloId = NumberUtils.toLong(request.getParameter("idFascicolo"));
            String categoriaDocumentaleCode = request.getParameter("categoriaDocumentaleCode");
            TipoCategDocumentaleView tipoCategDocumentaleView = anagraficaStatiTipiService.leggiTipoCategoriaDocumentale(categoriaDocumentaleCode, Locale.ITALIAN);
            if (fascicoloId == null || tipoCategDocumentaleView == null || tipoCategDocumentaleView.getVo() == null) {
                throw new RuntimeException("FascicoloId e tipoCategoriaCode non possono essere null");
            }
            fascicoloService.aggiungiDocumento(fascicoloId, tipoCategDocumentaleView.getVo().getId(), file);
            risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return risultato;
    }

    @RequestMapping(value = "/fascicolo/eliminaDocumento", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    RisultatoOperazioneRest eliminaDocumento(HttpServletRequest request, @RequestParam("uuid") String uuid) {
        RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
        try {

            String fascicoloId = request.getParameter("fascicoloId");
            if (uuid == null || fascicoloId == null) {
                throw new RuntimeException("uuid e fascicoloId non possono essere null");
            }

            fascicoloService.eliminaDocumento(NumberUtils.toLong(fascicoloId), uuid);

            risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return risultato;
    }

    @RequestMapping(value = "/fascicolo/cerca", method = RequestMethod.GET)
    public String cercaFascicoli(HttpServletRequest request, Model model, Locale locale) {
        // engsecurity VA
        HTMLActionSupport htmlActionSupport = new HTMLActionSupport();
        htmlActionSupport.checkCSRFToken(request);
        //removeCSRFToken(request);

        try {
            logger.info("@@DDS cerca fascicoli ");
            String tipologiaFascicoloCode = request.getParameter("tipologiaFascicoloCode");

            if (request.getParameter("id") != null) {
                FascicoloView view = fascicoloService.leggi(NumberUtils.toLong(request.getParameter("id")));
                request.setAttribute("nomeFascicolo", view.getVo().getNome());
                tipologiaFascicoloCode = view.getVo().getTipologiaFascicolo().getCodGruppoLingua();
            } else if (request.getParameter("id") == null) {
                request.setAttribute("nomeFascicolo", "");
            }

            TipologiaFascicoloView tipologiaFascicoloView = tipologiaFascicoloService.leggi(tipologiaFascicoloCode, locale, false);
            List<SettoreGiuridicoView> listaSettoreGiuridico = settoreGiuridicoService.leggiPerTipologiaId(tipologiaFascicoloView.getVo().getId(), false);
            FascicoloView view = new FascicoloView();
            view.setListaSettoreGiuridico(listaSettoreGiuridico);
            view.setTipologiaFascicoloCode(tipologiaFascicoloCode);
            List<TipologiaFascicoloView> listaTipologiaFascicolo = new ArrayList<TipologiaFascicoloView>();
            listaTipologiaFascicolo.add(tipologiaFascicoloView);
            view.setListaTipologiaFascicolo(listaTipologiaFascicolo);
            view.setListaCategoriaDocumentale(anagraficaStatiTipiService.leggiTipoCategDocumentale(locale.getLanguage().toUpperCase()));

            SocietaService societaService = (SocietaService) SpringUtil.getBean("societaService");
            List<SocietaView> listaSocieta = societaService.leggi(false);
            view.setListaSocieta(listaSocieta);

            model.addAttribute(MODEL_VIEW_NOME, view);
            return PAGINA_RICERCA_PATH;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;

    }

    @RequestMapping(value = "/fascicolo/cercaModale", method = RequestMethod.GET)
    public String cercaFascicoliModale(HttpServletRequest request, Model model, Locale locale) {

        try {
            String tipologiaFascicoloCode = "";

            if (request.getParameter("tipologiaFascicoloCode") != null) {
                tipologiaFascicoloCode = request.getParameter("tipologiaFascicoloCode");
            }

//			TipologiaFascicoloView tipologiaFascicoloView = tipologiaFascicoloService.leggi(tipologiaFascicoloCode, locale, false);
//			List<SettoreGiuridicoView> listaSettoreGiuridico = settoreGiuridicoService.leggiPerTipologiaId(tipologiaFascicoloView.getVo().getId(), false);
            FascicoloView view = new FascicoloView();
//			view.setListaSettoreGiuridico(listaSettoreGiuridico);
            view.setTipologiaFascicoloCode(tipologiaFascicoloCode);
            List<TipologiaFascicoloView> listaTipologiaFascicolo = tipologiaFascicoloService.leggi(locale, false);
            view.setListaTipologiaFascicolo(listaTipologiaFascicolo);
            view.setListaCategoriaDocumentale(anagraficaStatiTipiService.leggiTipoCategDocumentale(locale.getLanguage().toUpperCase()));
            model.addAttribute(MODEL_VIEW_NOME, view);

            if (request.getParameter("multiple") != null) {

                if ("incarico".equals(request.getParameter("multiple"))) {
                    return PAGINA_RICERCA_MODALE_DA_INCARICO_PATH;
                }
            }
            return PAGINA_RICERCA_MODALE_PATH;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;

    }

    @RequestMapping(value = "/fascicolo/caricaGrigliaPermessiFascicolo", method = RequestMethod.POST)
    public String caricaGrigliaPermessiFascicolo(@RequestParam("id") long idFascicolo, HttpServletRequest req,
                                                 Locale locale) {
        try {

            // LOGICA DI POPOLAZIONE DATI PER CONSENTIRE O MENO LE AZIONI
            // SUL FASCICOLO
            req.setAttribute("idFascicolo", idFascicolo);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return PAGINA_PERMESSI_FASCICOLO;
    }

    @RequestMapping(value = "/fascicolo/caricaGrigliaPermessiFascicoli", method = RequestMethod.POST)
    public String caricaGrigliaPermessiFascicoli(@RequestParam("ids") String idFascicoli, HttpServletRequest req,
                                                 Locale locale) {
        try {

            /*LOGICA DI POPOLAZIONE DATI PER CONSENTIRE O MENO LE AZIONI SUL FASCICOLO*/
            req.setAttribute("idFascicoli", idFascicoli);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return PAGINA_PERMESSI_FASCICOLI;
    }


    @RequestMapping(value = "/fascicolo/caricaPopupSelezioneProgetto", method = RequestMethod.GET)
    public void caricaPopupSelezioneProgetto(@RequestParam("id") long idFascicolo, @RequestParam("nomeProgetto") String nomeProgetto, HttpServletRequest req, HttpServletResponse response,
                                             Locale locale) throws IOException {

        String ritorno = "/NewLegalArchives/progetto/popupricerca.action?idFascicolo=" + idFascicolo + "&nomeProgetto=" + nomeProgetto;
        req.getSession().setAttribute("idFascicoloDaAssociare", idFascicolo);
        response.sendRedirect(ritorno);
    }

    @RequestMapping(value = "/fascicolo/associaFascicoloAProgetto", method = RequestMethod.POST)
    public @ResponseBody
    RisultatoOperazioneRest associaFascicoloAProgetto(@RequestParam("idFascicolo") long idFascicolo, @RequestParam("idProgetto") long idProgetto, HttpServletRequest req, HttpServletResponse response,
                                                      Locale locale) throws IOException {

        RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
        try {

            FascicoloView f = fascicoloService.leggi(idFascicolo);
            Progetto p = new Progetto();
            p.setId(idProgetto);
            f.getVo().setProgetto(p);
            fascicoloService.aggiornaFascicolo(f);

            risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return risultato;
    }

    @RequestMapping(value = "/fascicolo/caricaAzioniFascicolo", method = RequestMethod.POST)
    public String caricaAzioniFascicolo(@RequestParam("idFascicolo") long idFascicolo, HttpServletRequest req,
                                        Locale locale) {
        try {

            /*LOGICA DI POPOLAZIONE DATI PER CONSENTIRE O MENO LE AZIONI SUL FASCICOLO*/
            req.setAttribute("idFascicolo", idFascicolo);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (req.getParameter("onlyContent") != null) {
            return PAGINA_AZIONI_FASCICOLO;
        }
        return PAGINA_AZIONI_CERCA_FASCICOLO;
    }

    @RequestMapping(value = "/fascicolo/estendiPermessiFascicolo", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    RisultatoOperazioneRest estendiPermessiFascicolo(HttpServletRequest request) {
        RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");


        try {
            Long fascicoloId = NumberUtils.toLong(request.getParameter("idFascicolo"));
            String[] permessiScritturaArray = request.getParameterValues("permessoScrittura");
            String[] permessiLetturaArray = request.getParameterValues("permessoLettura");

            fascicoloService.salvaPermessiFascicolo(fascicoloId, permessiScritturaArray, permessiLetturaArray);
            risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return risultato;
    }

    @RequestMapping(value = "/fascicolo/estendiPermessiFascicoli", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    RisultatoOperazioneRest estendiPermessiFascicoli(HttpServletRequest request) {
        RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");
        try {

            String fascicoli = request.getParameter("idsF");

            if (fascicoli != null && !fascicoli.isEmpty()) {

                String[] idFascicoli = fascicoli.split("-");
                String useridUtil = request.getParameter("owner");

                boolean permRead = Boolean.parseBoolean(request.getParameter("permessoLettura"));
                boolean permWrite = Boolean.parseBoolean(request.getParameter("permessoScrittura"));

                if (useridUtil != null && !useridUtil.isEmpty()) {

                    if (idFascicoli != null && idFascicoli.length > 0) {

                        for (int i = 0; i < idFascicoli.length; i++) {

                            Long fascicoloId = Long.parseLong(idFascicoli[i]);

                            List<String> permessiLettura = new ArrayList<String>();
                            List<String> permessiScrittura = new ArrayList<String>();

                            if (permRead) {
                                permessiLettura.add(useridUtil);
                            }

                            if (permWrite) {
                                permessiScrittura.add(useridUtil);
                            }
                            fascicoloService.salvaPermessiFascicolo(fascicoloId, permessiScrittura, permessiLettura, useridUtil);
                        }
                        risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return risultato;
    }


    @RequestMapping(value = "/fascicolo/cambiaOwnerFascicolo", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    RisultatoOperazioneRest cambiaOwnerFascicolo(HttpServletRequest request) {
        RisultatoOperazioneRest risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.KO, "messaggio.ko");

        try {
            Long idFascicolo = NumberUtils.toLong(request.getParameter("id"));
            String newOwner = request.getParameter("newOwner");
            String oldOwner = request.getParameter("oldOwner");
            if (idFascicolo == null || newOwner == null) {
                throw new RuntimeException("FascicoloId e NewOwner non possono essere null");
            }

            RiassegnaService service = (RiassegnaService) SpringUtil.getBean("riassegnaService");
            if (service.riassegna(idFascicolo, oldOwner, newOwner)) {
                risultato = new RisultatoOperazioneRest(RisultatoOperazioneRest.OK, "messaggio.ok");

                Utente utenteOld = utenteService.leggiUtenteDaUserId(oldOwner).getVo();
                Utente utenteNuovo = utenteService.leggiUtenteDaUserId(newOwner).getVo();

                emailNotificationService.inviaNotificaCambioOwner(idFascicolo, utenteOld, utenteNuovo);
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return risultato;
    }

    @RequestMapping(value = "/fascicolo/caricaComboOwnerRiassegnaFascicolo", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    List<RiassegnaRest> caricaComboOwnerRiassegnaFascicolo(HttpServletRequest request) {

        try {
            Long fascicoloId = NumberUtils.toLong(request.getParameter("idFascicolo"));

            RiassegnaService service = (RiassegnaService) SpringUtil.getBean("riassegnaService");
            return service.getListaLegaleInternoOwnerFascicoloRest(fascicoloId);

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void caricaListeOggetti(BaseView view, Locale locale) throws Throwable {

    }

    @Override
    public void caricaListeOggettiPerDettaglio(BaseView view, Locale locale) throws Throwable {

    }

    private String decodeAllSocietaAddebito(Set<RFascicoloSocieta> societas) {
        String decodeSocietas = "";
        if (societas != null && !societas.isEmpty()) {
            for (Iterator<RFascicoloSocieta> iterator = societas.iterator(); iterator.hasNext(); ) {
                RFascicoloSocieta rFascicoloSocieta = (RFascicoloSocieta) iterator.next();
                if (rFascicoloSocieta.getTipologiaSocieta().equals("A") && rFascicoloSocieta.getSocieta() != null && rFascicoloSocieta.getSocieta().getDataCancellazione() == null)
                    decodeSocietas += rFascicoloSocieta.getSocieta().getNome() + ";";
            }
        }
        return decodeSocietas;
    }

    private String decodeAllSocietaProcedimento(Set<RFascicoloSocieta> societas) {
        String decodeSocietas = "";
        if (societas != null && !societas.isEmpty()) {
            for (Iterator<RFascicoloSocieta> iterator = societas.iterator(); iterator.hasNext(); ) {
                RFascicoloSocieta rFascicoloSocieta = (RFascicoloSocieta) iterator.next();
                if (rFascicoloSocieta.getTipologiaSocieta().equals("P") && rFascicoloSocieta.getSocieta() != null && rFascicoloSocieta.getSocieta().getDataCancellazione() == null)
                    decodeSocietas += rFascicoloSocieta.getSocieta().getNome() + ";";
            }
        }
        return decodeSocietas;
    }

}
