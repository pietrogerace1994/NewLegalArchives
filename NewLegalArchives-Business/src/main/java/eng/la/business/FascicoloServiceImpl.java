package eng.la.business;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import eng.la.persistence.*;
import it.snam.ned.libs.dds.dtos.v2.folder.Folder;
import it.snam.ned.libs.dds.interfaces.v2.DdsFolderDao;
//import it.snam.ned.libs.dds.v2.DdsDocumentDaoImpl;
import it.snam.ned.libs.dds.dtos.v2.Document;
import it.snam.ned.libs.dds.v2.DdsDocumentDaoImpl;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

//@@DDS import com.filenet.api.core.Folder;
import it.snam.ned.libs.dds.interfaces.v2.DdsDocumentDao;

import eng.la.model.AbstractEntity;
import eng.la.model.Autorizzazione;
import eng.la.model.Controparte;
import eng.la.model.Documento;
import eng.la.model.DocumentoProtCorrisp;
import eng.la.model.Fascicolo;
import eng.la.model.Incarico;
import eng.la.model.IncaricoWf;
import eng.la.model.ParteCivile;
import eng.la.model.PersonaOffesa;
import eng.la.model.Proforma;
import eng.la.model.RCorrelazioneFascicoli;
import eng.la.model.RFascPrestNotar;
import eng.la.model.RFascicoloGiudizio;
import eng.la.model.RFascicoloMateria;
import eng.la.model.RFascicoloRicorso;
import eng.la.model.RFascicoloSocieta;
import eng.la.model.ResponsabileCivile;
import eng.la.model.SchedaFondoRischi;
import eng.la.model.SoggettoIndagato;
import eng.la.model.TerzoChiamatoCausa;
import eng.la.model.TipoAutorizzazione;
import eng.la.model.Utente;
import eng.la.model.custom.AllegatoMultipartFile;
import eng.la.model.custom.IncaricoCron;
import eng.la.model.custom.ProformaCron;
import eng.la.model.view.FascicoloView;
import eng.la.model.view.ParteCivileView;
import eng.la.model.view.PersonaOffesaView;
import eng.la.model.view.ResponsabileCivileView;
import eng.la.model.view.SoggettoIndagatoView;
import eng.la.persistence.workflow.IncaricoWfDAO;
import eng.la.persistence.workflow.ProformaWfDAO;
import eng.la.persistence.workflow.StepWfDAO;
import eng.la.util.DateUtil;
import eng.la.util.ListaPaginata;
import eng.la.util.SpringUtil;
import eng.la.util.costants.Costanti;
import eng.la.util.filenet.model.CostantiFileNet;
import eng.la.util.filenet.model.FileNetClassNames;
import eng.la.util.filenet.model.FileNetUtil;

@Service("fascicoloService")
public class FascicoloServiceImpl extends BaseService<Fascicolo, FascicoloView> implements FascicoloService {
    private static final Logger logger = Logger.getLogger(FascicoloServiceImpl.class);
    @Autowired
    private FascicoloDAO fascicoloDAO;

    public void setDao(FascicoloDAO dao) {
        this.fascicoloDAO = dao;
    }

    public FascicoloDAO getDao() {
        return fascicoloDAO;
    }

    /*@@DDS
    @Autowired
    private DocumentaleDAO documentaleDAO;

    public void setDocumentaleDao(DocumentaleDAO dao) {
        this.documentaleDAO = dao;
    }
     */

    //@@DDS
    // public DocumentaleDAO getDocumentaleDao() {
    //    return documentaleDAO;
    //}

    //@@DDS@Autowired
    //@@DDSprivate DocumentaleCryptDAO documentaleCryptDAO;

    //@@DDSpublic void setDocumentaleCryptDAO(DocumentaleCryptDAO dao) {
    //@@DDS    this.documentaleCryptDAO = dao;
    //@@DDS}

    //@@DDSpublic DocumentaleCryptDAO getDocumentaleCryptDAO() {
//@@DDS    return documentaleCryptDAO;
    //@@DDS}

    @Autowired
    private DocumentaleDdsDAO documentaleDdsDAO;
    public void setDocumentaleDdsDao(DocumentaleDdsDAO dao) {
        this.documentaleDdsDAO = dao;
    }

    public DocumentaleDdsDAO getDocumentaleDdsDao() {
        return documentaleDdsDAO;
    }

    @Autowired
    private DocumentaleDdsCryptDAO documentaleDdsCryptDAO;

    public void setDocumentaleDdsCryptDAO(DocumentaleDdsCryptDAO dao) {
        this.documentaleDdsCryptDAO = dao;
    }

    public DocumentaleDdsCryptDAO getDocumentaleDdsCryptDAO() {
        return documentaleDdsCryptDAO;
    }

    @Autowired
    private DocumentoDAO documentoDAO;

    public void setDocumentoDao(DocumentoDAO dao) {
        this.documentoDAO = dao;
    }

    public DocumentoDAO getDocumentoDao() {
        return documentoDAO;
    }

    @Autowired
    private ControparteDAO controparteDAO;

    public ControparteDAO getControparteDAO() {
        return controparteDAO;
    }

    public void setControparteDAO(ControparteDAO controparteDAO) {
        this.controparteDAO = controparteDAO;
    }

    @Autowired
    private AnagraficaStatiTipiDAO anagraficaStatiTipiDAO;

    public AnagraficaStatiTipiDAO getAnagraficaStatiTipiDAO() {
        return anagraficaStatiTipiDAO;
    }

    public void setAnagraficaStatiTipiDAO(AnagraficaStatiTipiDAO anagraficaStatiTipiDAO) {
        this.anagraficaStatiTipiDAO = anagraficaStatiTipiDAO;
    }

    @Autowired
    private TerzoChiamatoInCausaDAO terzoChiamatoInCausaDAO;

    public TerzoChiamatoInCausaDAO getTerzoChiamatoInCausaDAO() {
        return terzoChiamatoInCausaDAO;
    }

    public void setTerzoChiamatoInCausaDAO(TerzoChiamatoInCausaDAO terzoChiamatoInCausaDAO) {
        this.terzoChiamatoInCausaDAO = terzoChiamatoInCausaDAO;
    }

    @Autowired
    private TipologiaFascicoloDAO tipologiaFascicoloDAO;

    public TipologiaFascicoloDAO getTipologiaFascicoloDAO() {
        return tipologiaFascicoloDAO;
    }

    public void setTipologiaFascicoloDAO(TipologiaFascicoloDAO tipologiaFascicoloDAO) {
        this.tipologiaFascicoloDAO = tipologiaFascicoloDAO;
    }

    @Autowired
    private SettoreGiuridicoDAO settoreGiuridicoDAO;

    public SettoreGiuridicoDAO getSettoreGiuridicoDAO() {
        return settoreGiuridicoDAO;
    }

    public void setSettoreGiuridicoDAO(SettoreGiuridicoDAO settoreGiuridicoDAO) {
        this.settoreGiuridicoDAO = settoreGiuridicoDAO;
    }

    @Autowired
    private AutorizzazioneDAO autorizzazioneDAO;

    public AutorizzazioneDAO getAutorizzazioneDAO() {
        return autorizzazioneDAO;
    }

    public void setAutorizzazioneDAO(AutorizzazioneDAO autorizzazioneDAO) {
        this.autorizzazioneDAO = autorizzazioneDAO;
    }

    @Autowired
    private ProformaDAO proformaDAO;

    public ProformaDAO getProformaDAO() {
        return proformaDAO;
    }

    public void setProformaDAO(ProformaDAO proformaDAO) {
        this.proformaDAO = proformaDAO;
    }

    @Autowired
    private IncaricoDAO incaricoDAO;

    public IncaricoDAO getIncaricoDAO() {
        return incaricoDAO;
    }

    public void setIncaricoDAO(IncaricoDAO incaricoDAO) {
        this.incaricoDAO = incaricoDAO;
    }

    @Autowired
    private IncaricoWfDAO incaricoWfDAO;

    public IncaricoWfDAO getIncaricoWfDAO() {
        return incaricoWfDAO;
    }

    public void setIncaricoWfDAO(IncaricoWfDAO incaricoWfDAO) {
        this.incaricoWfDAO = incaricoWfDAO;
    }

    @Autowired
    private ProformaWfDAO proformaWfDAO;

    public ProformaWfDAO getProformaWfDAO() {
        return proformaWfDAO;
    }

    public void setProformaWfDAO(ProformaWfDAO proformaWfDAO) {
        this.proformaWfDAO = proformaWfDAO;
    }

    @Autowired
    private UtenteDAO utenteDAO;

    public UtenteDAO getUtenteDAO() {
        return utenteDAO;
    }

    public void setUtenteDAO(UtenteDAO utenteDAO) {
        this.utenteDAO = utenteDAO;
    }

    @Autowired
    private StepWfDAO stepWfDao;

    public StepWfDAO getStepWfDao() {
        return stepWfDao;
    }

    public void setStepWfDao(StepWfDAO stepWfDao) {
        this.stepWfDao = stepWfDao;
    }

    @Override
    public List<FascicoloView> leggi() throws Throwable {
        List<Fascicolo> lista = fascicoloDAO.leggi();
        List<FascicoloView> listaRitorno = convertiVoInView(lista);
        logger.info("@@DDS leggi ");
        return listaRitorno;
    }

    @Override
    public FascicoloView leggi(long id) throws Throwable {
        Fascicolo fascicolo = fascicoloDAO.leggi(id);
        logger.info("@@DDS leggi CON ID" + id);
        return convertiVoInView(fascicolo);
    }

    public FascicoloView leggiConPermessi(long id) throws Throwable {
        Fascicolo fascicolo = fascicoloDAO.leggiConPermessi(id);
        logger.info("@@DDS leggi CON PERMESSI");
        return convertiVoInView(fascicolo);

    }

    public FascicoloView leggiConPermessi(long id, FetchMode fetchMode) throws Throwable {
        Fascicolo fascicolo = fascicoloDAO.leggiConPermessi(id, fetchMode);
        logger.info("@@DDS leggi CON PERMESSI DUE");
        return convertiVoInView(fascicolo);

    }

    @Override
    public FascicoloView leggiPerCronologia(long id) throws Throwable {
        Fascicolo fascicolo = fascicoloDAO.leggiPerCronologia(id);
        logger.info("@@DDS leggi CON CRONOLOGIA");
        FascicoloView fascicoloView = convertiVoInView(fascicolo);


        ArrayList<IncaricoCron> incarichiCron = new ArrayList<>();
        ArrayList<ProformaCron> proformaCron = new ArrayList<>();
        Collection<Incarico> incaricos = fascicolo.getIncaricos();


        for (Incarico incarico : incaricos) {
            if (incarico.getStatoIncarico().getCodGruppoLingua().equals(CostantiDAO.INCARICO_STATO_AUTORIZZATO)) {
                IncaricoCron incaricoCron = new IncaricoCron();

                incaricoCron.setDataAutorizzazione(DateUtil.formattaData(incarico.getDataAutorizzazione().getTime()));
                incaricoCron.setUtenteAutorizzato(incarico.getProfessionistaEsterno().getCognomeNome());
                incaricoCron.setIdProfessionistaEsterno(incarico.getProfessionistaEsterno().getId());

                IncaricoWf incaricoWF = incaricoWfDAO.leggiUltimoWorkflowTerminato(incarico.getId());


                if (incaricoWF != null) {

                    long idIncaricoWf = incaricoWF.getId();

                    String matricolaUtente = stepWfDao.leggiUltimoStepChiuso(idIncaricoWf, CostantiDAO.AUTORIZZAZIONE_INCARICO).getUtenteChiusura();

                    incaricoCron.setUtenteAutorizzante(utenteDAO.leggiUtenteDaMatricola(matricolaUtente).getNominativoUtil());

                }

                incarichiCron.add(incaricoCron);

                List<Proforma> proformaList = proformaDAO.leggiProformaAssociatiAIncaricoUnico(incarico.getId());

                if (proformaList != null) {
                    for (Iterator<Proforma> iterator = proformaList.iterator(); iterator.hasNext(); ) {
                        Proforma proforma = (Proforma) iterator.next();
                        ProformaCron pCron = new ProformaCron();

                        pCron.setIdProforma(proforma.getId());

                        if (proforma.getStatoProforma().getCodGruppoLingua().equals(CostantiDAO.PROFESSIONISTA_STATO_AUTORIZZATO)) {
                            pCron.setAutorizzato(true);
                            pCron.setDataAutorizzazione(DateUtil.formattaData(proforma.getDataAutorizzazione().getTime()));
                            pCron.setUtenteAutorizzato(utenteDAO.leggiUtenteDaUserId(proforma.getAutorizzatore()).getNominativoUtil());
                            proformaCron.add(pCron);
                        } else {
                            pCron.setAutorizzato(false);
                            pCron.setStato(proforma.getStatoProforma().getCodGruppoLingua());
                            proformaCron.add(pCron);
                        }

                    }
                }

            }
        }

        Set<IncaricoCron> hsI = new HashSet<>();
        hsI.addAll(incarichiCron);
        incarichiCron.clear();
        incarichiCron.addAll(hsI);

        Set<ProformaCron> hsP = new HashSet<>();
        hsP.addAll(proformaCron);
        proformaCron.clear();
        proformaCron.addAll(hsP);

        fascicoloView.setListaIncarichiCron(incarichiCron);
        fascicoloView.setListaProformaCron(proformaCron);


        return fascicoloView;
    }

    @Override
    public ListaPaginata<FascicoloView> cerca(String nome, String oggetto, String descrizione, String siglaCliente,
                                              String autoritaGiudiziaria, String controinteressato, String dal, String al, long tipologiaFascicoloId,
                                              long settoreGiuridicoId, String controparte, String legaleEsterno, int elementiPerPagina, int numeroPagina,
                                              String ordinamento, String tipoOrdinamento, String tipoPermesso) throws Throwable {

        return cerca(nome, oggetto, descrizione, siglaCliente, autoritaGiudiziaria, controinteressato, dal, al,
                tipologiaFascicoloId, settoreGiuridicoId, controparte, legaleEsterno, null, null, elementiPerPagina,
                numeroPagina, ordinamento, tipoOrdinamento, tipoPermesso, null, null);
    }

    @Override
    public ListaPaginata<FascicoloView> cerca(String nome, String oggetto, String descrizione, String siglaCliente,
                                              String autoritaGiudiziaria, String controinteressato, String dal, String al, long tipologiaFascicoloId,
                                              long settoreGiuridicoId, String controparte, String legaleEsterno, String owner, String stato,
                                              int elementiPerPagina, int numeroPagina, String ordinamento, String tipoOrdinamento, String tipoPermesso, String societaAddebito, List<Long> listaSocieta)
            throws Throwable {
        List<Fascicolo> lista = fascicoloDAO.cerca(nome, oggetto, descrizione, siglaCliente, autoritaGiudiziaria,
                controinteressato, dal, al, tipologiaFascicoloId, settoreGiuridicoId, controparte, legaleEsterno, owner,
                stato, elementiPerPagina, numeroPagina, ordinamento, tipoOrdinamento, tipoPermesso, societaAddebito, listaSocieta);
        List<FascicoloView> listaView = convertiVoInView(lista);
        ListaPaginata<FascicoloView> listaRitorno = new ListaPaginata<FascicoloView>();
        Long conta = fascicoloDAO.conta(nome, oggetto, descrizione, siglaCliente, autoritaGiudiziaria,
                controinteressato, dal, al, tipologiaFascicoloId, settoreGiuridicoId, controparte, legaleEsterno, owner,
                stato, tipoPermesso, societaAddebito, listaSocieta);
        listaRitorno.addAll(listaView);
        listaRitorno.setNumeroElementiPerPagina(elementiPerPagina);
        listaRitorno.setPaginaCorrente(numeroPagina);
        listaRitorno.setNumeroTotaleElementi(conta);
        listaRitorno.setOrdinamento(ordinamento);
        listaRitorno.setOrdinamentoDirezione(tipoOrdinamento);
        return listaRitorno;
    }


    @Override
    public ListaPaginata<FascicoloView> cerca2(String nome, String oggetto, String descrizione, String siglaCliente,
                                               String autoritaGiudiziaria, String controinteressato, String dal, String al, long tipologiaFascicoloId,
                                               long settoreGiuridicoId, String controparte, String legaleEsterno, String owner, String stato,
                                               int elementiPerPagina, int numeroPagina, String ordinamento, String tipoOrdinamento, String tipoPermesso, String matricolaOwner)
            throws Throwable {
        List<Fascicolo> lista = fascicoloDAO.cerca2(nome, oggetto, descrizione, siglaCliente, autoritaGiudiziaria,
                controinteressato, dal, al, tipologiaFascicoloId, settoreGiuridicoId, controparte, legaleEsterno, owner,
                stato, elementiPerPagina, numeroPagina, ordinamento, tipoOrdinamento, tipoPermesso, matricolaOwner);
        List<FascicoloView> listaView = convertiVoInView(lista);
        ListaPaginata<FascicoloView> listaRitorno = new ListaPaginata<FascicoloView>();
        Long conta = fascicoloDAO.conta2(nome, oggetto, descrizione, siglaCliente, autoritaGiudiziaria,
                controinteressato, dal, al, tipologiaFascicoloId, settoreGiuridicoId, controparte, legaleEsterno, owner,
                stato, tipoPermesso, matricolaOwner);
        listaRitorno.addAll(listaView);
        listaRitorno.setNumeroElementiPerPagina(elementiPerPagina);
        listaRitorno.setPaginaCorrente(numeroPagina);
        listaRitorno.setNumeroTotaleElementi(conta);
        listaRitorno.setOrdinamento(ordinamento);
        listaRitorno.setOrdinamentoDirezione(tipoOrdinamento);
        return listaRitorno;
    }

    @Override
    public List<FascicoloView> cerca(String nome, String oggetto) throws Throwable {
        List<Fascicolo> lista = fascicoloDAO.cerca(nome, oggetto);
        List<FascicoloView> listaRitorno = convertiVoInView(lista);
        return listaRitorno;
    }

    @Override
    public List<FascicoloView> cerca(List<String> parole) throws Throwable {
        List<Fascicolo> lista = fascicoloDAO.cerca(parole);
        List<FascicoloView> listaRitorno = convertiVoInView(lista);
        return listaRitorno;
    }

    @Override
    public ListaPaginata<FascicoloView> cerca(List<String> parole, String dal, String al, int elementiPerPagina,
                                              int numeroPagina, String ordinamento, String tipoOrdinamento, String tipoPermesso) throws Throwable {

        List<Fascicolo> lista = fascicoloDAO.cerca(parole, dal, al, elementiPerPagina, numeroPagina, ordinamento, tipoOrdinamento, tipoPermesso);
        List<FascicoloView> listaView = convertiVoInView(lista);
        ListaPaginata<FascicoloView> listaRitorno = new ListaPaginata<FascicoloView>();
        Long conta = fascicoloDAO.conta(parole, dal, al, tipoPermesso);
        listaRitorno.addAll(listaView);
        listaRitorno.setNumeroElementiPerPagina(elementiPerPagina);
        listaRitorno.setPaginaCorrente(numeroPagina);
        listaRitorno.setNumeroTotaleElementi(conta);
        listaRitorno.setOrdinamento(ordinamento);
        listaRitorno.setOrdinamentoDirezione(tipoOrdinamento);
        return listaRitorno;
    }

    @Override
    public Long conta(String nome, String oggetto, String dal, String al, long tipologiaFascicoloId,
                      long settoreGiuridicoId, String controparte, String legaleEsterno, String owner, String stato,
                      String tipoPermesso) throws Throwable {
        return fascicoloDAO.conta(nome, oggetto, null, null, null, null, dal, al, tipologiaFascicoloId,
                settoreGiuridicoId, controparte, legaleEsterno, owner, stato, tipoPermesso, null, null);
    }

    @Override
    public Long conta(String nome, String oggetto, String dal, String al, long tipologiaFascicoloId,
                      long settoreGiuridicoId, String controparte, String legaleEsterno, String tipoPermesso) throws Throwable {
        return conta(nome, oggetto, dal, al, tipologiaFascicoloId, settoreGiuridicoId, controparte, legaleEsterno, null,
                null, tipoPermesso);

    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public FascicoloView apriFascicolo(FascicoloView fascicoloView) throws Throwable {
        fascicoloView.getVo().setOperation(AbstractEntity.INSERT_OPERATION);
        fascicoloView.getVo().setOperationTimestamp(new Date());

        fascicoloView.getVo().setStatoFascicolo(anagraficaStatiTipiDAO
                .leggiStatoFascicolo(CostantiDAO.FASCICOLO_STATO_APERTO, Locale.ITALIAN.getLanguage().toUpperCase()));
        Date dataCreazione = new Timestamp(System.currentTimeMillis());
        fascicoloView.getVo().setDataCreazione(dataCreazione);
        fascicoloView.getVo().setDataUltimaModifica(dataCreazione);
        fascicoloView.getVo().setNome(getNextNumeroFascicolo());
        Fascicolo fascicolo = fascicoloDAO.apriFascicolo(fascicoloView.getVo());

        salvaFascicoloSocieta(fascicoloView, fascicolo);
        salvaFascicoloMateria(fascicoloView, fascicolo);
        salvaTerzoChiamatoCausa(fascicoloView, fascicolo);
        salvaSoggettoIndagato(fascicoloView, fascicolo);
        salvaPersonaOffesa(fascicoloView, fascicolo);
        salvaParteCivile(fascicoloView, fascicolo);
        salvaResponsabileCivile(fascicoloView, fascicolo);
        salvaControparte(fascicoloView, fascicolo);
        salvaCorrelazioneFascicoli(fascicoloView, fascicolo);
        salvaGiudizioFascicoli(fascicoloView, fascicolo);
        salvaRicorsoFascicoli(fascicoloView, fascicolo);
        salvaPrestazioneNotarile(fascicoloView, fascicolo);

        SessionFactory sessionFactory = (SessionFactory) SpringUtil.getBean("sessionFactory");
        sessionFactory.getCurrentSession().flush();

        String cartellaPadreFascicolo = FileNetUtil.getFascicoloCartellaPadre(fascicolo.getDataCreazione());

        String nomeCartella = fascicolo.getNome().trim().toUpperCase();
        String uuid = UUID.randomUUID().toString();
        String nomeClasseCartella = FileNetClassNames.FASCICOLO_FOLDER;
        Map<String, Object> proprietaCartella = new HashMap<String, Object>();
        int idFascicolo = Integer.parseInt(fascicolo.getId() + "");
        proprietaCartella.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, idFascicolo);

        boolean isPenale = fascicolo.getSettoreGiuridico().getCodGruppoLingua()
                .equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);

        /*@@DDS inizio
        logger.debug("@@DDS ---------- INIZIO cartellaPadreFascicolo " + cartellaPadreFascicolo);
        logger.debug("@@DDS ---------- idFascicolo " + idFascicolo);
        com.filenet.api.core.Folder cartellaPadre = isPenale ? documentaleCryptDAO.leggiCartella(cartellaPadreFascicolo)
                : documentaleDAO.leggiCartella(cartellaPadreFascicolo);

        if (cartellaPadre == null) {
            if (isPenale) {
                documentaleCryptDAO.verificaCreaPercorsoCartella(cartellaPadreFascicolo);
                cartellaPadre = documentaleCryptDAO.leggiCartella(cartellaPadreFascicolo);
            } else {
                documentaleDAO.verificaCreaPercorsoCartella(cartellaPadreFascicolo);
                cartellaPadre = documentaleDAO.leggiCartella(cartellaPadreFascicolo);
            }
        }
        logger.debug("@@DDS ---------- siamo prima del creaCartella ");
        if (isPenale) {
            documentaleCryptDAO.creaCartella(uuid, nomeCartella, nomeClasseCartella, proprietaCartella, cartellaPadre);
        } else {
            documentaleDAO.creaCartella(uuid, nomeCartella, nomeClasseCartella, proprietaCartella, cartellaPadre);
        }

        fino a qui devo commentare */
        logger.debug("@@DDS ---------- inizio parte DDS ");
        try {
            Folder folderPadre = isPenale ? documentaleDdsCryptDAO.leggiCartella(cartellaPadreFascicolo) : documentaleDdsDAO.leggiCartella(cartellaPadreFascicolo);
            if (folderPadre == null) {
                if (isPenale) {
                    documentaleDdsCryptDAO.verificaCreaPercorsoCartella(cartellaPadreFascicolo);
                    folderPadre = documentaleDdsCryptDAO.leggiCartella(cartellaPadreFascicolo);
                    //documentaleCryptDAO.verificaCreaPercorsoCartella(cartellaPadreFascicolo);
                    //folderPadre = documentaleCryptDAO.leggiCartella(cartellaPadreFascicolo);
                } else {
                    documentaleDdsDAO.verificaCreaPercorsoCartella(cartellaPadreFascicolo);
                    folderPadre = documentaleDdsDAO.leggiCartella(cartellaPadreFascicolo);
                    //documentaleDAO.verificaCreaPercorsoCartella(cartellaPadreFascicolo);
                    //folderPadre = documentaleDAO.leggiCartella(cartellaPadreFascicolo);
                }
            }
            String folderId = null;
            nomeCartella = cartellaPadreFascicolo + nomeCartella;
            logger.debug("@@DDS ---------- Creo cartella DDS: "+ nomeCartella);
            Folder folder;
            if (isPenale) {
                folder = documentaleDdsCryptDAO.creaCartella(uuid, nomeCartella, nomeClasseCartella, proprietaCartella, folderPadre);
            } else {
                folder = documentaleDdsDAO.creaCartella(uuid, nomeCartella, nomeClasseCartella, proprietaCartella, folderPadre);
            }
            logger.info("Cartella creata : " + folder);
        }catch (Exception e){
            logger.debug("@@DDS ---------- Eccezione DDS: "+ e);
        }
        logger.debug("@@DDS ---------- fine");
        FascicoloView view = new FascicoloView();
        view.setVo(fascicolo);
        return view;
    }

    private void salvaRicorsoFascicoli(FascicoloView view, Fascicolo fascicolo) throws Throwable {
        if (view.getVo().getRFascicoloRicorsos() != null && view.getVo().getRFascicoloRicorsos().size() > 0) {
            fascicoloDAO.cancellaFascicoloRicorso(fascicolo.getId());
            Collection<RFascicoloRicorso> lista = view.getVo().getRFascicoloRicorsos();
            for (RFascicoloRicorso vo : lista) {
                vo.setFascicolo(fascicolo);
                fascicoloDAO.inserisciFascicoloRicorso(vo);
            }
        }
    }

    private void salvaGiudizioFascicoli(FascicoloView view, Fascicolo fascicolo) throws Throwable {
        if (view.getVo().getRFascicoloGiudizios() != null && view.getVo().getRFascicoloGiudizios().size() > 0) {
            fascicoloDAO.cancellaFascicoloGiudizio(fascicolo.getId());
            Collection<RFascicoloGiudizio> lista = view.getVo().getRFascicoloGiudizios();
            for (RFascicoloGiudizio vo : lista) {
                vo.setFascicolo(fascicolo);
                fascicoloDAO.inserisciFascicoloGiudizio(vo);
            }
        }
    }

    private void salvaResponsabileCivile(FascicoloView view, Fascicolo fascicolo) throws Throwable {
        if (view.getVo().getResponsabileCiviles() != null && view.getVo().getResponsabileCiviles().size() > 0) {
            fascicoloDAO.cancellaFascicoloResponsabileCivile(fascicolo.getId());
            Collection<ResponsabileCivile> lista = view.getVo().getResponsabileCiviles();
            for (ResponsabileCivile vo : lista) {
                vo.setFascicolo(fascicolo);
                fascicoloDAO.inserisciResponsabileCivile(vo);
            }
        }
    }

    private void salvaParteCivile(FascicoloView view, Fascicolo fascicolo) throws Throwable {
        if (view.getVo().getParteCiviles() != null && view.getVo().getParteCiviles().size() > 0) {
            fascicoloDAO.cancellaFascicoloParteCivile(fascicolo.getId());
            Collection<ParteCivile> lista = view.getVo().getParteCiviles();
            for (ParteCivile vo : lista) {
                vo.setFascicolo(fascicolo);
                fascicoloDAO.inserisciParteCivile(vo);
            }
        }
    }

    private void salvaPersonaOffesa(FascicoloView view, Fascicolo fascicolo) throws Throwable {
        if (view.getVo().getPersonaOffesas() != null && view.getVo().getPersonaOffesas().size() > 0) {
            fascicoloDAO.cancellaFascicoloPersonaOffesa(fascicolo.getId());
            Collection<PersonaOffesa> lista = view.getVo().getPersonaOffesas();
            for (PersonaOffesa vo : lista) {
                vo.setFascicolo(fascicolo);
                fascicoloDAO.inserisciPersonaOffesa(vo);
            }
        }
    }

    private void salvaSoggettoIndagato(FascicoloView view, Fascicolo fascicolo) throws Throwable {
        if (view.getVo().getSoggettoIndagatos() != null && view.getVo().getSoggettoIndagatos().size() > 0) {
            fascicoloDAO.cancellaFascicoloSoggettoIndagato(fascicolo.getId());
            Collection<SoggettoIndagato> lista = view.getVo().getSoggettoIndagatos();
            for (SoggettoIndagato vo : lista) {
                vo.setFascicolo(fascicolo);
                fascicoloDAO.inserisciSoggettoIndagato(vo);
            }
        }
    }

    private void salvaFascicoloMateria(FascicoloView view, Fascicolo fascicolo) throws Throwable {
        if (view.getVo().getRFascicoloMaterias() != null && view.getVo().getRFascicoloMaterias().size() > 0) {
            fascicoloDAO.cancellaFascicoloMaterie(fascicolo.getId());
            Collection<RFascicoloMateria> lista = view.getVo().getRFascicoloMaterias();
            for (RFascicoloMateria vo : lista) {
                vo.setFascicolo(fascicolo);
                fascicoloDAO.inserisciFascicoloMateria(vo);
            }
        }
    }

    private String getNextNumeroFascicolo() throws Throwable {
        return fascicoloDAO.getNextNumeroFascicolo();
    }

    private void salvaCorrelazioneFascicoli(FascicoloView view, Fascicolo fascicolo) throws Throwable {
        if (view.getVo().getRCorrelazioneFascicolis() != null && view.getVo().getRCorrelazioneFascicolis().size() > 0) {
            fascicoloDAO.cancellaCorrelazioneFascicoli(fascicolo.getId());
            Collection<RCorrelazioneFascicoli> fascicoliCorrelatiLista = view.getVo().getRCorrelazioneFascicolis();
            for (RCorrelazioneFascicoli fascicoloCorrelato : fascicoliCorrelatiLista) {
                fascicoloCorrelato.setFascicolo1(fascicolo);
                fascicoloDAO.inserisciFascicoloCorrelato(fascicoloCorrelato);
                RCorrelazioneFascicoli fascicoloCorrelatoInverso = new RCorrelazioneFascicoli();
                fascicoloCorrelatoInverso.setFascicolo2(fascicolo);
                fascicoloCorrelatoInverso.setFascicolo1(fascicoloCorrelato.getFascicolo2());
                fascicoloDAO.inserisciFascicoloCorrelato(fascicoloCorrelatoInverso);
            }
        }
    }

    private void salvaControparte(FascicoloView view, Fascicolo fascicolo) throws Throwable {
        if (view.getVo().getContropartes() != null && view.getVo().getContropartes().size() > 0) {
            fascicoloDAO.cancellaFascicoloControparte(fascicolo.getId());
            Collection<Controparte> controparteLista = view.getVo().getContropartes();
            for (Controparte controparte : controparteLista) {
                controparte.setFascicolo(fascicolo);
                controparteDAO.inserisciControparte(controparte);
            }
        }
    }

    private void salvaTerzoChiamatoCausa(FascicoloView view, Fascicolo fascicolo) throws Throwable {
        if (view.getVo().getTerzoChiamatoCausas() != null && view.getVo().getTerzoChiamatoCausas().size() > 0) {
            fascicoloDAO.cancellaFascicoloTerzoChiamatoCausa(fascicolo.getId());
            Collection<TerzoChiamatoCausa> terzoChiamatoCausaLista = view.getVo().getTerzoChiamatoCausas();
            for (TerzoChiamatoCausa terzoChiamatoCausa : terzoChiamatoCausaLista) {
                terzoChiamatoCausa.setFascicolo(fascicolo);
                terzoChiamatoInCausaDAO.inserisciTerzoChiamatoInCausa(terzoChiamatoCausa);
            }
        }
    }

    private void salvaFascicoloSocieta(FascicoloView view, Fascicolo fascicolo) throws Throwable {
        if (view.getVo().getRFascicoloSocietas() != null && view.getVo().getRFascicoloSocietas().size() > 0) {
            fascicoloDAO.cancellaFascicoloSocieta(fascicolo.getId());
            Collection<RFascicoloSocieta> fascicoloSocietaLista = view.getVo().getRFascicoloSocietas();
            for (RFascicoloSocieta fascicoloSocieta : fascicoloSocietaLista) {
                fascicoloSocieta.setFascicolo(fascicolo);
                fascicoloDAO.inserisciFascicoloSocieta(fascicoloSocieta);
            }
        }
    }

    private void salvaPrestazioneNotarile(FascicoloView view, Fascicolo fascicolo) throws Throwable {
        if (view.getVo().getRFascPrestNotars() != null && view.getVo().getRFascPrestNotars().size() > 0) {
            fascicoloDAO.cancellaFascicoloPrestazioneNotarile(fascicolo.getId());
            Collection<RFascPrestNotar> fascicoloPrestazioneNotarile = view.getVo().getRFascPrestNotars();
            for (RFascPrestNotar prestNotar : fascicoloPrestazioneNotarile) {
                prestNotar.setFascicolo(fascicolo);
                fascicoloDAO.inserisciPrestazioneNotarile(prestNotar);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void aggiornaFascicolo(FascicoloView fascicoloView) throws Throwable {

        fascicoloView.getVo().setOperation(AbstractEntity.UPDATE_OPERATION);
        fascicoloView.getVo().setOperationTimestamp(new Date());

        fascicoloView.getVo().setDataUltimaModifica(new Timestamp(System.currentTimeMillis()));
        fascicoloDAO.aggiornaFascicolo(fascicoloView.getVo());
        Fascicolo fascicolo = fascicoloDAO.leggi(fascicoloView.getVo().getId());
        salvaFascicoloSocieta(fascicoloView, fascicolo);
        salvaFascicoloMateria(fascicoloView, fascicolo);
        salvaTerzoChiamatoCausa(fascicoloView, fascicolo);
        salvaSoggettoIndagato(fascicoloView, fascicolo);
        salvaPersonaOffesa(fascicoloView, fascicolo);
        salvaParteCivile(fascicoloView, fascicolo);
        salvaResponsabileCivile(fascicoloView, fascicolo);
        salvaControparte(fascicoloView, fascicolo);
        salvaCorrelazioneFascicoli(fascicoloView, fascicolo);
        salvaPrestazioneNotarile(fascicoloView, fascicolo);
        salvaGiudizioFascicoli(fascicoloView, fascicolo);
        salvaRicorsoFascicoli(fascicoloView, fascicolo);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public boolean completaFascicolo(long id) throws Throwable {
        logger.info("@@DDS completaFascicolo " );
        FascicoloView fascicolo = leggi(id);
        if (fascicolo == null) {
            throw new RuntimeException(
                    "Fascicolo con id=" + id + " non trovato. Impossibile completare l'operazione richiesta");
        }

        if (fascicolo != null && fascicolo.getVo().getStatoFascicolo() != null
                && (!fascicolo.getVo().getStatoFascicolo().getCodGruppoLingua()
                .equals(CostantiDAO.FASCICOLO_STATO_APERTO)
                && !fascicolo.getVo().getStatoFascicolo().getCodGruppoLingua()
                .equals(CostantiDAO.FASCICOLO_STATO_CHIUSO))) {
            throw new RuntimeException("Il Fascicolo con id=" + id
                    + " non puo' essere chiuso perche' non e' in stato APERTO e non � in stato COMPLETATO. Impossibile completare l'operazione richiesta");

        }

        fascicolo.getVo().setStatoFascicolo(anagraficaStatiTipiDAO.leggiStatoFascicolo(
                CostantiDAO.FASCICOLO_STATO_COMPLETATO, Locale.ITALIAN.getLanguage().toUpperCase()));

        aggiornaFascicolo(fascicolo);
        Fascicolo fascicoloSalvato = fascicoloDAO.leggi(fascicolo.getVo().getId());
        return fascicoloSalvato.getStatoFascicolo().getCodGruppoLingua()
                .equalsIgnoreCase(CostantiDAO.FASCICOLO_STATO_COMPLETATO);

    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public boolean riapriFascicolo(long id) throws Throwable {
        logger.info("@@DDS riapriFascicolo " );
        FascicoloView fascicolo = leggi(id);
        if (fascicolo == null) {
            throw new RuntimeException(
                    "Fascicolo con id=" + id + " non trovato. Impossibile completare l'operazione richiesta");
        }

        if (fascicolo != null && fascicolo.getVo().getStatoFascicolo() != null
                && (!fascicolo.getVo().getStatoFascicolo().getCodGruppoLingua()
                .equals(CostantiDAO.FASCICOLO_STATO_CHIUSO)
                && !fascicolo.getVo().getStatoFascicolo().getCodGruppoLingua()
                .equals(CostantiDAO.FASCICOLO_STATO_COMPLETATO))) {
            throw new RuntimeException("Il Fascicolo con id=" + id
                    + " non puo' essere riaperto perche' non e' in stato CHIUSO e non � in stato COMPLETATO. Impossibile completare l'operazione richiesta");

        }

        fascicolo.getVo().setStatoFascicolo(anagraficaStatiTipiDAO
                .leggiStatoFascicolo(CostantiDAO.FASCICOLO_STATO_APERTO, Locale.ITALIAN.getLanguage().toUpperCase()));

        aggiornaFascicolo(fascicolo);
        Fascicolo fascicoloSalvato = fascicoloDAO.leggi(fascicolo.getVo().getId());
        return fascicoloSalvato.getStatoFascicolo().getCodGruppoLingua()
                .equalsIgnoreCase(CostantiDAO.FASCICOLO_STATO_APERTO);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public boolean eliminaFascicolo(long id) throws Throwable {
        FascicoloView fascicolo = leggiConPermessi(id);
        if (fascicolo == null) {
            throw new RuntimeException(
                    "Fascicolo con id=" + id + " non trovato. Impossibile completare l'operazione richiesta");
        }
        fascicolo.getVo().setOperation(AbstractEntity.DELETE_OPERATION);
        fascicolo.getVo().setOperationTimestamp(new Date());
        fascicoloDAO.eliminaFascicolo(fascicolo.getVo());
        return false;
    }

    @Override
    public List<String> leggiPerAutocompleteForo(String term) throws Throwable {
        List<String> lista = fascicoloDAO.leggiPerAutocompleteForo(term);
        return lista;
    }

    @Override
    public List<String> leggiPerAutocompleteAutoritaEmanante(String term) throws Throwable {
        List<String> lista = fascicoloDAO.leggiPerAutocompleteAutoritaEmanante(term);
        return lista;
    }

    @Override
    public List<String> leggiPerAutocompleteControinteressato(String term) throws Throwable {
        List<String> lista = fascicoloDAO.leggiPerAutocompleteControinteressato(term);
        return lista;
    }

    @Override
    public List<String> leggiPerAutocompleteAutoritaGiudiziaria(String term) throws Throwable {
        List<String> lista = fascicoloDAO.leggiPerAutocompleteAutoritaGiudiziaria(term);
        return lista;
    }

    @Override
    public List<String> leggiPerAutocompleteSoggettoIndagato(String term) throws Throwable {
        List<String> lista = fascicoloDAO.leggiPerAutocompleteSoggettoIndagato(term);
        return lista;
    }

    @Override
    public List<String> leggiPerAutocompleteResponsabileCivile(String term) throws Throwable {
        List<String> lista = fascicoloDAO.leggiPerAutocompleteResponsabileCivile(term);
        return lista;
    }

    @Override
    public List<String> leggiPerAutocompleteParteCivile(String term) throws Throwable {
        List<String> lista = fascicoloDAO.leggiPerAutocompleteParteCivile(term);
        return lista;
    }

    @Override
    public FascicoloView leggi(long id, FetchMode fetchMode) throws Throwable {
        Fascicolo vo = fascicoloDAO.leggi(id, fetchMode);
        return convertiVoInView(vo);
    }

    @Override
    public FascicoloView leggiTutti(long id, FetchMode fetchMode) throws Throwable {
        Fascicolo vo = fascicoloDAO.leggiTutti(id, fetchMode);
        return convertiVoInView(vo);
    }

    @Override
    public SoggettoIndagatoView leggiSoggettoIndagato(long id) throws Throwable {
        SoggettoIndagato vo = fascicoloDAO.leggiSoggettoIndagato(id);
        SoggettoIndagatoView view = new SoggettoIndagatoView();
        view.setVo(vo);
        return view;
    }

    @Override
    public PersonaOffesaView leggiPersonaOffesa(long id) throws Throwable {
        PersonaOffesa vo = fascicoloDAO.leggiPersonaOffesa(id);
        PersonaOffesaView view = new PersonaOffesaView();
        view.setVo(vo);
        return view;
    }

    @Override
    public ResponsabileCivileView leggiResponsabileCivile(long id) throws Throwable {
        ResponsabileCivile vo = fascicoloDAO.leggiResponsabileCivile(id);
        ResponsabileCivileView view = new ResponsabileCivileView();
        view.setVo(vo);
        return view;
    }

    @Override
    public ParteCivileView leggiParteCivile(long id) throws Throwable {
        ParteCivile vo = fascicoloDAO.leggiParteCivile(id);
        ParteCivileView view = new ParteCivileView();
        view.setVo(vo);
        return view;
    }

    @Override
    public List<String> leggiPerAutocompletePersonaOffesa(String term) throws Throwable {
        List<String> lista = fascicoloDAO.leggiPerAutocompletePersonaOffesa(term);
        return lista;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void aggiungiDocumento(Long fascicoloId, Long categoriaId, List<MultipartFile> files) throws Throwable {
        FascicoloView fascicoloView = leggi(fascicoloId);
        if (fascicoloView == null || fascicoloView.getVo() == null) {
            throw new RuntimeException("Fascicolo con id:" + fascicoloId + " non trovato");
        }

        Hashtable<String, Boolean> documentiInseritiFileNet = new Hashtable<String, Boolean>();
        logger.info("@@DDS aggiungiDocumento ");
        try {
            for (MultipartFile file : files) {
                String uuid = UUID.randomUUID().toString();
                String cartellaFascicolo = FileNetUtil.getFascicoloCartella(fascicoloView.getVo().getDataCreazione(),
                        fascicoloView.getVo().getNome());
                String nomeClasseDocumentale = "";

                Map<String, Object> proprietaDocumento = new HashMap<String, Object>();
                if (file instanceof AllegatoMultipartFile) {
                    nomeClasseDocumentale = FileNetClassNames.EMAIL_DOCUMENT;

                    Documento documento = documentoDAO.creaDocumentoDB(uuid, nomeClasseDocumentale,
                            file.getOriginalFilename(), file.getContentType());
                    fascicoloDAO.aggiungiDocumento(fascicoloId, categoriaId, documento.getId());

                    AllegatoMultipartFile allegatoMultipartFile = (AllegatoMultipartFile) file;
                    List<String> ccList = new ArrayList<String>();
                    List<String> toList = new ArrayList<String>();

                    if (allegatoMultipartFile.getTo() != null) {
                        for (String to : allegatoMultipartFile.getTo()) {
                            toList.add(to);
                        }
                        proprietaDocumento.put("To", toList);
                    }

                    if (allegatoMultipartFile.getCc() != null) {
                        for (String cc : allegatoMultipartFile.getCc()) {
                            ccList.add(cc);
                        }
                        proprietaDocumento.put("CarbonCopy", ccList);
                    }
                    logger.info("Allegato multipart file : " + allegatoMultipartFile );
                    proprietaDocumento.put("From", allegatoMultipartFile.getFrom());
                    proprietaDocumento.put("EmailSubject", allegatoMultipartFile.getOggetto());
                    proprietaDocumento.put("SentOn", DateUtil.toDateForDDS(allegatoMultipartFile.getDataInvio()));
                    logger.info("SentOn " + allegatoMultipartFile.getDataInvio());
                    proprietaDocumento.put("ReceivedOn", DateUtil.toDateForDDS(allegatoMultipartFile.getDataRicezione()));
                    logger.info("ReceivedOn " + allegatoMultipartFile.getDataRicezione());
                } else {
                    nomeClasseDocumentale = FileNetClassNames.PROTOCOLLO_DOCUMENT;
                    Documento documento = documentoDAO.creaDocumentoDB(uuid, nomeClasseDocumentale,
                            file.getOriginalFilename(), file.getContentType());
                    DocumentoProtCorrisp doc = fascicoloDAO.aggiungiDocumento(fascicoloId, categoriaId, documento.getId());

                    proprietaDocumento.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, NumberUtils.toInt(doc.getId() + ""));

                }

                boolean isPenale = fascicoloView.getVo().getSettoreGiuridico().getCodGruppoLingua()
                        .equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);

                if (isPenale) {
                    //@@DDS com.filenet.api.core.Folder cartellaPadre = documentaleCryptDAO.leggiCartella(cartellaFascicolo);
                    Folder folderPadre = documentaleDdsCryptDAO.leggiCartella(cartellaFascicolo);
                    byte[] contenuto = file.getBytes();
                    //@@DDS documentaleCryptDAO.creaDocumento(uuid, file.getOriginalFilename(), nomeClasseDocumentale,
                    //@@DDS         file.getContentType(), proprietaDocumento, cartellaPadre, contenuto);
                    documentaleDdsCryptDAO.creaDocumento(uuid, file.getOriginalFilename(), nomeClasseDocumentale,
                            file.getContentType(), proprietaDocumento, cartellaFascicolo, contenuto);
                } else {
                    //@@DDS com.filenet.api.core.Folder cartellaPadre = documentaleDAO.leggiCartella(cartellaFascicolo);
                    Folder folderPadre = documentaleDdsDAO.leggiCartella(cartellaFascicolo);
                    logger.info("@@DDS stampo folderPadre " + folderPadre);
                    byte[] contenuto = file.getBytes();
                    //@@DDS documentaleDAO.creaDocumento(uuid, file.getOriginalFilename(), nomeClasseDocumentale,
                    //@@DDS         file.getContentType(), proprietaDocumento, cartellaPadre, contenuto);
                    documentaleDdsDAO.creaDocumento(uuid, file.getOriginalFilename(), nomeClasseDocumentale,
                            file.getContentType(), proprietaDocumento, cartellaFascicolo, contenuto);
                }

                documentiInseritiFileNet.put(uuid, isPenale);
            }
        } catch (Exception e) {
            for (String uuid : documentiInseritiFileNet.keySet()) {
                boolean isPenale = documentiInseritiFileNet.get(uuid);

                if (isPenale)
                    //@@DDS documentaleCryptDAO.eliminaDocumento(uuid);
                    documentaleDdsCryptDAO.eliminaDocumento(uuid);
                else
                //@@DDS documentaleDAO.eliminaDocumento(uuid);
                    documentaleDdsDAO.eliminaDocumento(uuid);
            }
            throw e;
        }
    }


    @Override
    public void aggiungiDocumento(Long fascicoloId, Long categoriaId, MultipartFile file) throws Throwable {
        FascicoloView fascicoloView = leggi(fascicoloId);
        if (fascicoloView == null || fascicoloView.getVo() == null) {
            throw new RuntimeException("Fascicolo con id:" + fascicoloId + " non trovato");
        }
        logger.info("@@DDS aggiungiDocumento DUE");
        String uuid = UUID.randomUUID().toString();
        String cartellaFascicolo = FileNetUtil.getFascicoloCartella(fascicoloView.getVo().getDataCreazione(),
                fascicoloView.getVo().getNome());

        Map<String, Object> proprietaDocumento = new HashMap<String, Object>();
        if (file instanceof AllegatoMultipartFile) {
            Documento documento = documentoDAO.creaDocumentoDB(uuid, FileNetClassNames.EMAIL_DOCUMENT,
                    file.getOriginalFilename(), file.getContentType());

            fascicoloDAO.aggiungiDocumento(fascicoloId, categoriaId, documento.getId());

            AllegatoMultipartFile allegatoMultipartFile = (AllegatoMultipartFile) file;
            List<String> ccList = new ArrayList<String>();
            List<String> toList = new ArrayList<String>();

            if (allegatoMultipartFile.getTo() != null) {
                for (String to : allegatoMultipartFile.getTo()) {
                    toList.add(to);
                }
            }

            if (allegatoMultipartFile.getCc() != null) {
                for (String cc : allegatoMultipartFile.getCc()) {
                    ccList.add(cc);
                }
            }
        } else {
            Documento documento = documentoDAO.creaDocumentoDB(uuid, FileNetClassNames.PROTOCOLLO_DOCUMENT,
                    file.getOriginalFilename(), file.getContentType());
            DocumentoProtCorrisp doc = fascicoloDAO.aggiungiDocumento(fascicoloId, categoriaId, documento.getId());

            proprietaDocumento.put(CostantiFileNet.PROPERTY_NAME_LEGAL_ARCHIVE_ID, NumberUtils.toInt(doc.getId() + ""));

        }

        boolean isPenale = fascicoloView.getVo().getSettoreGiuridico().getCodGruppoLingua()
                .equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);

        if (isPenale) {
            //@@DDS com.filenet.api.core.Folder cartellaPadre = documentaleCryptDAO.leggiCartella(cartellaFascicolo);
            Folder folderPadre = documentaleDdsCryptDAO.leggiCartella((cartellaFascicolo));
            byte[] contenuto = file.getBytes();
            //@@DDS documentaleCryptDAO.creaDocumento(uuid, file.getOriginalFilename(), FileNetClassNames.PROTOCOLLO_DOCUMENT,
            //@@DDS         file.getContentType(), proprietaDocumento, cartellaPadre, contenuto);
            documentaleDdsCryptDAO.creaDocumento(uuid, file.getOriginalFilename(), FileNetClassNames.PROTOCOLLO_DOCUMENT,
                    file.getContentType(), proprietaDocumento, cartellaFascicolo, contenuto);//@@DDS NUOVA
        } else {
            //@@DDS com.filenet.api.core.Folder cartellaPadre = documentaleDAO.leggiCartella(cartellaFascicolo);
            logger.debug("folder cartellaFascicolo " + cartellaFascicolo);

            byte[] contenuto = file.getBytes();
            //@@DDS documentaleDAO.creaDocumento(uuid, file.getOriginalFilename(), FileNetClassNames.PROTOCOLLO_DOCUMENT,
            //@@DDS         file.getContentType(), proprietaDocumento, cartellaPadre, contenuto);
            documentaleDdsDAO.creaDocumento(uuid, file.getOriginalFilename(), FileNetClassNames.PROTOCOLLO_DOCUMENT,
                    file.getContentType(), proprietaDocumento, cartellaFascicolo, contenuto);//@@DDS NUOVA
        }

    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void salvaPermessiFascicolo(Long fascicoloId, String[] permessiScrittura, String[] permessiLettura)
            throws Throwable {
        Fascicolo fascicolo = fascicoloDAO.leggi(fascicoloId, FetchMode.JOIN);
        autorizzazioneDAO.cancellaAutorizzazioni(fascicoloId, Costanti.TIPO_ENTITA_FASCICOLO,
                fascicolo.getLegaleInterno());
        Collection<Incarico> incaricos = fascicolo.getIncaricos();
        if (incaricos != null) {
            for (Incarico incarico : incaricos) {
                autorizzazioneDAO.cancellaAutorizzazioni(incarico.getId(), Costanti.TIPO_ENTITA_INCARICO,
                        fascicolo.getLegaleInterno());
                List<Proforma> proformas = proformaDAO.leggiProformaAssociatiAIncarico(incarico.getId());
                if (proformas != null) {
                    for (Proforma proforma : proformas) {
                        autorizzazioneDAO.cancellaAutorizzazioni(proforma.getId(), Costanti.TIPO_ENTITA_PROFORMA,
                                fascicolo.getLegaleInterno());
                    }
                }
            }
        }

        if (fascicolo.getSchedaFondoRischi() != null) {

            SchedaFondoRischi schedaFondoRischi = fascicolo.getSchedaFondoRischi();
            autorizzazioneDAO.cancellaAutorizzazioni(schedaFondoRischi.getId(), Costanti.TIPO_ENTITA_SCHEDA_FONDO_RISCHI, fascicolo.getLegaleInterno());

        }

        if (permessiScrittura != null) {
            for (String permessoUserId : permessiScrittura) {
                Autorizzazione autorizzazione = new Autorizzazione();
                autorizzazione.setIdEntita(fascicoloId);
                TipoAutorizzazione tipoAutorizzazione = anagraficaStatiTipiDAO.leggiTipoAutorizzazione(
                        CostantiDAO.TIPO_AUTORIZZAZIONE_UTENTE_SCRITTURA, Locale.ITALIAN.getLanguage().toUpperCase());
                autorizzazione.setTipoAutorizzazione(tipoAutorizzazione);
                autorizzazione.setNomeClasse(Costanti.TIPO_ENTITA_FASCICOLO);
                Utente utente = utenteDAO.leggiUtenteDaUserId(permessoUserId);
                autorizzazione.setUtente(utente);
                autorizzazioneDAO.inserisci(autorizzazione);
                if (incaricos != null) {
                    for (Incarico incarico : incaricos) {
                        Autorizzazione autorizzazioneIncarico = new Autorizzazione();
                        autorizzazioneIncarico.setIdEntita(incarico.getId());
                        autorizzazioneIncarico.setTipoAutorizzazione(tipoAutorizzazione);
                        autorizzazioneIncarico.setNomeClasse(Costanti.TIPO_ENTITA_INCARICO);
                        autorizzazioneIncarico.setUtente(utente);
                        autorizzazioneDAO.inserisci(autorizzazioneIncarico);
                        List<Proforma> proformas = proformaDAO.leggiProformaAssociatiAIncarico(incarico.getId());
                        if (proformas != null) {
                            for (Proforma proforma : proformas) {
                                Autorizzazione autorizzazioneProforma = new Autorizzazione();
                                autorizzazioneProforma.setIdEntita(proforma.getId());
                                autorizzazioneProforma.setTipoAutorizzazione(tipoAutorizzazione);
                                autorizzazioneProforma.setNomeClasse(Costanti.TIPO_ENTITA_PROFORMA);
                                autorizzazioneProforma.setUtente(utente);
                                autorizzazioneDAO.inserisci(autorizzazioneProforma);
                            }
                        }
                    }
                }
                if (fascicolo.getSchedaFondoRischi() != null) {
                    Autorizzazione autorizzazioneIncarico = new Autorizzazione();
                    autorizzazioneIncarico.setIdEntita(fascicolo.getSchedaFondoRischi().getId());
                    autorizzazioneIncarico.setTipoAutorizzazione(tipoAutorizzazione);
                    autorizzazioneIncarico.setNomeClasse(Costanti.TIPO_ENTITA_SCHEDA_FONDO_RISCHI);
                    autorizzazioneIncarico.setUtente(utente);
                    autorizzazioneDAO.inserisci(autorizzazioneIncarico);
                }
            }
        }

        if (permessiLettura != null) {
            for (String permessoUserId : permessiLettura) {
                Autorizzazione autorizzazione = new Autorizzazione();
                autorizzazione.setIdEntita(fascicoloId);
                TipoAutorizzazione tipoAutorizzazione = anagraficaStatiTipiDAO.leggiTipoAutorizzazione(
                        CostantiDAO.TIPO_AUTORIZZAZIONE_UTENTE_LETTURA, Locale.ITALIAN.getLanguage().toUpperCase());
                autorizzazione.setTipoAutorizzazione(tipoAutorizzazione);
                autorizzazione.setNomeClasse(Costanti.TIPO_ENTITA_FASCICOLO);
                Utente utente = utenteDAO.leggiUtenteDaUserId(permessoUserId);
                autorizzazione.setUtente(utente);
                autorizzazioneDAO.inserisci(autorizzazione);
                if (incaricos != null) {
                    for (Incarico incarico : incaricos) {
                        Autorizzazione autorizzazioneIncarico = new Autorizzazione();
                        autorizzazioneIncarico.setIdEntita(incarico.getId());
                        autorizzazioneIncarico.setTipoAutorizzazione(tipoAutorizzazione);
                        autorizzazioneIncarico.setNomeClasse(Costanti.TIPO_ENTITA_INCARICO);
                        autorizzazioneIncarico.setUtente(utente);
                        autorizzazioneDAO.inserisci(autorizzazioneIncarico);
                        List<Proforma> proformas = proformaDAO.leggiProformaAssociatiAIncarico(incarico.getId());
                        if (proformas != null) {
                            for (Proforma proforma : proformas) {
                                Autorizzazione autorizzazioneProforma = new Autorizzazione();
                                autorizzazioneProforma.setIdEntita(proforma.getId());
                                autorizzazioneProforma.setTipoAutorizzazione(tipoAutorizzazione);
                                autorizzazioneProforma.setNomeClasse(Costanti.TIPO_ENTITA_PROFORMA);
                                autorizzazioneProforma.setUtente(utente);
                                autorizzazioneDAO.inserisci(autorizzazioneProforma);
                            }
                        }
                    }
                }
                if (fascicolo.getSchedaFondoRischi() != null) {
                    Autorizzazione autorizzazioneIncarico = new Autorizzazione();
                    autorizzazioneIncarico.setIdEntita(fascicolo.getSchedaFondoRischi().getId());
                    autorizzazioneIncarico.setTipoAutorizzazione(tipoAutorizzazione);
                    autorizzazioneIncarico.setNomeClasse(Costanti.TIPO_ENTITA_INCARICO);
                    autorizzazioneIncarico.setUtente(utente);
                    autorizzazioneDAO.inserisci(autorizzazioneIncarico);
                }
            }
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void salvaPermessiFascicolo(Long fascicoloId, List<String> permessiScrittura, List<String> permessiLettura, String useridUtil)
            throws Throwable {

        Fascicolo fascicolo = fascicoloDAO.leggi(fascicoloId, FetchMode.JOIN);

        autorizzazioneDAO.cancellaAutorizzazione(fascicoloId, Costanti.TIPO_ENTITA_FASCICOLO, useridUtil);

        Collection<Incarico> incaricos = fascicolo.getIncaricos();
        if (incaricos != null) {
            for (Incarico incarico : incaricos) {
                autorizzazioneDAO.cancellaAutorizzazione(incarico.getId(), Costanti.TIPO_ENTITA_INCARICO,
                        useridUtil);
                List<Proforma> proformas = proformaDAO.leggiProformaAssociatiAIncarico(incarico.getId());
                if (proformas != null) {
                    for (Proforma proforma : proformas) {
                        autorizzazioneDAO.cancellaAutorizzazione(proforma.getId(), Costanti.TIPO_ENTITA_PROFORMA,
                                useridUtil);
                    }
                }
            }
        }

        if (fascicolo.getSchedaFondoRischi() != null) {

            SchedaFondoRischi schedaFondoRischi = fascicolo.getSchedaFondoRischi();
            autorizzazioneDAO.cancellaAutorizzazioni(schedaFondoRischi.getId(), Costanti.TIPO_ENTITA_SCHEDA_FONDO_RISCHI, fascicolo.getLegaleInterno());

        }

        if (permessiScrittura != null) {
            for (String permessoUserId : permessiScrittura) {
                Autorizzazione autorizzazione = new Autorizzazione();
                autorizzazione.setIdEntita(fascicoloId);
                TipoAutorizzazione tipoAutorizzazione = anagraficaStatiTipiDAO.leggiTipoAutorizzazione(
                        CostantiDAO.TIPO_AUTORIZZAZIONE_UTENTE_SCRITTURA, Locale.ITALIAN.getLanguage().toUpperCase());
                autorizzazione.setTipoAutorizzazione(tipoAutorizzazione);
                autorizzazione.setNomeClasse(Costanti.TIPO_ENTITA_FASCICOLO);
                Utente utente = utenteDAO.leggiUtenteDaUserId(permessoUserId);
                autorizzazione.setUtente(utente);
                autorizzazioneDAO.inserisci(autorizzazione);
                if (incaricos != null) {
                    for (Incarico incarico : incaricos) {
                        Autorizzazione autorizzazioneIncarico = new Autorizzazione();
                        autorizzazioneIncarico.setIdEntita(incarico.getId());
                        autorizzazioneIncarico.setTipoAutorizzazione(tipoAutorizzazione);
                        autorizzazioneIncarico.setNomeClasse(Costanti.TIPO_ENTITA_INCARICO);
                        autorizzazioneIncarico.setUtente(utente);
                        autorizzazioneDAO.inserisci(autorizzazioneIncarico);
                        List<Proforma> proformas = proformaDAO.leggiProformaAssociatiAIncarico(incarico.getId());
                        if (proformas != null) {
                            for (Proforma proforma : proformas) {
                                Autorizzazione autorizzazioneProforma = new Autorizzazione();
                                autorizzazioneProforma.setIdEntita(proforma.getId());
                                autorizzazioneProforma.setTipoAutorizzazione(tipoAutorizzazione);
                                autorizzazioneProforma.setNomeClasse(Costanti.TIPO_ENTITA_PROFORMA);
                                autorizzazioneProforma.setUtente(utente);
                                autorizzazioneDAO.inserisci(autorizzazioneProforma);
                            }
                        }
                    }
                }
                if (fascicolo.getSchedaFondoRischi() != null) {
                    Autorizzazione autorizzazioneIncarico = new Autorizzazione();
                    autorizzazioneIncarico.setIdEntita(fascicolo.getSchedaFondoRischi().getId());
                    autorizzazioneIncarico.setTipoAutorizzazione(tipoAutorizzazione);
                    autorizzazioneIncarico.setNomeClasse(Costanti.TIPO_ENTITA_SCHEDA_FONDO_RISCHI);
                    autorizzazioneIncarico.setUtente(utente);
                    autorizzazioneDAO.inserisci(autorizzazioneIncarico);
                }
            }
        }

        if (permessiLettura != null) {
            for (String permessoUserId : permessiLettura) {
                Autorizzazione autorizzazione = new Autorizzazione();
                autorizzazione.setIdEntita(fascicoloId);
                TipoAutorizzazione tipoAutorizzazione = anagraficaStatiTipiDAO.leggiTipoAutorizzazione(
                        CostantiDAO.TIPO_AUTORIZZAZIONE_UTENTE_LETTURA, Locale.ITALIAN.getLanguage().toUpperCase());
                autorizzazione.setTipoAutorizzazione(tipoAutorizzazione);
                autorizzazione.setNomeClasse(Costanti.TIPO_ENTITA_FASCICOLO);
                Utente utente = utenteDAO.leggiUtenteDaUserId(permessoUserId);
                autorizzazione.setUtente(utente);
                autorizzazioneDAO.inserisci(autorizzazione);
                if (incaricos != null) {
                    for (Incarico incarico : incaricos) {
                        Autorizzazione autorizzazioneIncarico = new Autorizzazione();
                        autorizzazioneIncarico.setIdEntita(incarico.getId());
                        autorizzazioneIncarico.setTipoAutorizzazione(tipoAutorizzazione);
                        autorizzazioneIncarico.setNomeClasse(Costanti.TIPO_ENTITA_INCARICO);
                        autorizzazioneIncarico.setUtente(utente);
                        autorizzazioneDAO.inserisci(autorizzazioneIncarico);
                        List<Proforma> proformas = proformaDAO.leggiProformaAssociatiAIncarico(incarico.getId());
                        if (proformas != null) {
                            for (Proforma proforma : proformas) {
                                Autorizzazione autorizzazioneProforma = new Autorizzazione();
                                autorizzazioneProforma.setIdEntita(proforma.getId());
                                autorizzazioneProforma.setTipoAutorizzazione(tipoAutorizzazione);
                                autorizzazioneProforma.setNomeClasse(Costanti.TIPO_ENTITA_PROFORMA);
                                autorizzazioneProforma.setUtente(utente);
                                autorizzazioneDAO.inserisci(autorizzazioneProforma);
                            }
                        }
                    }
                }
                if (fascicolo.getSchedaFondoRischi() != null) {
                    Autorizzazione autorizzazioneIncarico = new Autorizzazione();
                    autorizzazioneIncarico.setIdEntita(fascicolo.getSchedaFondoRischi().getId());
                    autorizzazioneIncarico.setTipoAutorizzazione(tipoAutorizzazione);
                    autorizzazioneIncarico.setNomeClasse(Costanti.TIPO_ENTITA_SCHEDA_FONDO_RISCHI);
                    autorizzazioneIncarico.setUtente(utente);
                    autorizzazioneDAO.inserisci(autorizzazioneIncarico);
                }
            }
        }
    }

    @Override
    public List<FascicoloView> cercaUltimiFascicoli(long numRighe) throws Throwable {
        List<Fascicolo> listaVo = fascicoloDAO.cercaUltimiFascicoli(numRighe);
        List<FascicoloView> listaRitorno = convertiVoInView(listaVo);
        return listaRitorno;
    }

    @Override
    public List<FascicoloView> leggiFascicoloOrdData(String idUtilMatricola) throws Throwable {

        List<Fascicolo> listaVo = fascicoloDAO.leggiFascicoloOrdData(idUtilMatricola);

        List<FascicoloView> listaRitorno = convertiVoInView(listaVo);

        return listaRitorno;
    }

    @Override
    public void eliminaDocumento(long fascicoloId, String uuid) throws Throwable {
        FascicoloView fascicoloView = leggi(fascicoloId);
        boolean isPenale = fascicoloView.getVo().getSettoreGiuridico().getCodGruppoLingua()
                .equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);

        if (isPenale) {
            //@@DDS documentaleCryptDAO.eliminaDocumento(uuid);
            documentaleDdsCryptDAO.eliminaDocumento(uuid);
        } else {
            //@@DDSdocumentaleDAO.eliminaDocumento(uuid);
            documentaleDdsDAO.eliminaDocumento(uuid);
        }
    }

    @Override
    public List<FascicoloView> cerca(Integer anno, Integer mese) throws Throwable {
        List<Fascicolo> lista = fascicoloDAO.cerca(anno, mese);
        List<FascicoloView> listaRitorno = convertiVoInView(lista);
        return listaRitorno;
    }

    @Override
    public long getTipoCategoriaDocumentale(String codiceGruppoLingua) throws Throwable {
        return fascicoloDAO.getTipoCategoriaDocumentale(codiceGruppoLingua);
    }

    /*@@DDS TODO CAPIRE SE VA SOSTITUITA CON UN TEST SU DDS
    @Override
    public Boolean testFilenet() throws Throwable {
        return documentaleDAO.testFilenet();
    }
     */

    @Override
    protected Class<Fascicolo> leggiClassVO() {
        return Fascicolo.class;
    }

    @Override
    protected Class<FascicoloView> leggiClassView() {
        return FascicoloView.class;
    }
}
