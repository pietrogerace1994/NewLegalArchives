package eng.la.business.mail;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import eng.la.persistence.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.multipart.MultipartFile;

/*import com.filenet.api.collection.DocumentSet;
import com.filenet.api.collection.PageIterator;*/
//@@DDS import com.filenet.api.core.Document;
//@@DDS import com.filenet.api.core.Folder;
import it.snam.ned.libs.dds.dtos.v2.folder.Folder;
import it.snam.ned.libs.dds.dtos.v2.Document;

/*import com.filenet.apiimpl.core.DocumentImpl;
import com.filenet.apiimpl.core.EngineObjectImpl;*/

import eng.la.model.Atto;
import eng.la.model.AttoWf;
import eng.la.model.BeautyContest;
import eng.la.model.BeautyContestWf;
import eng.la.model.ConfigurazioneStepWf;
import eng.la.model.Fascicolo;
import eng.la.model.FascicoloWf;
import eng.la.model.GruppoUtente;
import eng.la.model.Incarico;
import eng.la.model.IncaricoWf;
import eng.la.model.Nazione;
import eng.la.model.ProfessionistaEsterno;
import eng.la.model.ProfessionistaEsternoWf;
import eng.la.model.Proforma;
import eng.la.model.ProformaWf;
import eng.la.model.RBeautyContestProfessionistaEsterno;
import eng.la.model.SchedaFondoRischi;
import eng.la.model.SchedaFondoRischiWf;
import eng.la.model.Societa;
import eng.la.model.Specializzazione;
import eng.la.model.StatoAtto;
import eng.la.model.StepWf;
import eng.la.model.StudioLegale;
import eng.la.model.TipoProfessionista;
import eng.la.model.Utente;
import eng.la.model.UtentePec;
import eng.la.model.mail.Mail;
import eng.la.model.view.UtentePecView;
import eng.la.persistence.workflow.AttoWfDAO;
import eng.la.persistence.workflow.BeautyContestWfDAO;
import eng.la.persistence.workflow.ConfigurazioneStepWfDAO;
import eng.la.persistence.workflow.FascicoloWfDAO;
import eng.la.persistence.workflow.IncaricoWfDAO;
import eng.la.persistence.workflow.ProfessionistaEsternoWfDAO;
import eng.la.persistence.workflow.ProformaWfDAO;
import eng.la.persistence.workflow.SchedaFondoRischiWfDAO;
import eng.la.persistence.workflow.StepWfDAO;
import eng.la.util.BASE64DecodedMultipartFile;
import eng.la.util.CurrentSessionUtil;
import eng.la.util.SpringUtil;
import eng.la.util.costants.Costanti;
import eng.la.util.filenet.model.FileNetClassNames;
import eng.la.util.filenet.model.FileNetUtil;


@Service("emailNotificationService")
public class EmailNotificationServiceImpl implements EmailNotificationService{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(EmailNotificationServiceImpl.class);

    private static final String SUFF_URL_PROFORMA = "ponte/entryLA.jsp?f=dpr&id=";
    private static final String SUFF_URL_PROFORMA_AMMINISTRATIVI = "ponte/entryLA.jsp?f=pa&pa=";
    private static final String SUFF_URL_PROTOCOLLO = "ponte/entryLA.jsp?f=mprot&filter=";
    private static final String SUFF_URL_ARBITRATO = "ponte/entryLA.jsp?f=dca&id=";
    private static final String SUFF_URL_INCARICO = "ponte/entryLA.jsp?f=di&id=";
    private static final String SUFF_URL_SCHEDA_FONDO_RISCHI = "ponte/entryLA.jsp?f=dsfr&id=";
    private static final String SUFF_URL_FASCICOLO = "ponte/entryLA.jsp?f=df&id=";
    private static final String SUFF_URL_PROFESSIONISTA = "ponte/entryLA.jsp?f=dpe&idProf=";
    private static final String SUFF_URL_ATTO = "ponte/entryLA.jsp?f=va&id=%d&azione=visualizza";
    private static final String SUFF_URL_ATTO_MODIFICA = "ponte/entryLA.jsp?f=ma&id=%d&azione=modifica";
    private static final String SUFF_URL_BEAUTY_CONTEST = "ponte/entryLA.jsp?f=bc&id=";
    private static final String TIPO_ALLEGATO_IT = "Proforma";
    private static final String TIPO_ALLEGATO_EN = "Pro - Forma";
    private static final String PREF_HREF = "<a href='";
    private static final String SUFF_HREF = "'>";
    private static final String FINE_HREF = "</a>";
    private static final String INI_BOLD = "<B>";
    private static final String FINE_BOLD = "</B>";
    private static final String PORTALE_LEGALI_ESTERNI = "Portale dei Legali Esterni";
    private String PARCELLE_LEGALI_PAGABILI = "parcellelegali.pagabili@snam.it";
    private String MITTENTE_STANDARD = "mbx.legalarchives@snam.it";
    private String CC_VENDOR_MANAGEMENT = "xxxvendor.alesoc@snam.it";



    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private VelocityEngine velocityEngine;



    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    @Autowired
    private IncaricoDAO incaricoDAO;

    @Autowired
    private IncaricoWfDAO incaricoWfDAO;

    @Autowired
    private StepWfDAO stepWfDAO;

    @Autowired
    private ConfigurazioneStepWfDAO configurazioneStepWfDAO;

    @Autowired
    private UtenteDAO utenteDAO;

    @Autowired
    private ProformaDAO proformaDAO;

    @Autowired
    private ProformaWfDAO proformaWfDAO;

    @Autowired
    private ProfessionistaEsternoDAO professionistaDAO;

    @Autowired
    private ProfessionistaEsternoWfDAO professionistaWfDAO;

    @Autowired
    private FascicoloDAO fascicoloDAO;

    @Autowired
    private FascicoloWfDAO fascicoloWfDAO;

    @Autowired
    private AttoDAO attoDAO;

    @Autowired
    private AttoWfDAO attoWfDAO;

    @Autowired
    private SocietaDAO societaDAO;

    @Autowired
    private NazioneDAO nazioneDAO;

    @Autowired
    private SpecializzazioneDAO specializzazioneDAO;

    @Autowired
    private AnagraficaStatiTipiDAO anagraficaStatiDAO;

    @Autowired
    private SchedaFondoRischiDAO schedaFondoRischiDAO;

    @Autowired
    private SchedaFondoRischiWfDAO schedaFondoRischiWfDAO;

    @Autowired
    private BeautyContestDAO beautyContestDAO;

    @Autowired
    private BeautyContestWfDAO beautyContestWfDAO;

    @Autowired
    private UtentePecDAO utentePecDAO;

    @Autowired
    private ArchivioProtocolloDAO archivioProtocolloDAO;

    /*
    @Autowired
    private DocumentaleCryptDAO documentaleCryptDAO;
*/
    @Autowired
    private DocumentaleDdsCryptDAO documentaleDdsCryptDAO;

  //DARIO C *****************************************************************************************************************
  	@Async   
  	public void inviaNotifica(String tipoNotifica, String tipoEntita, long idEntita, String lang, String userIdUtenteConnesso) throws Throwable {
  		inviaNotifica(tipoNotifica, tipoEntita, idEntita, lang, userIdUtenteConnesso,"");
  	}
  	//*************************************************************************************************************************
  	
    @Async   
    public void inviaNotifica(String tipoNotifica, String tipoEntita, long idEntita, String lang, String userIdUtenteConnesso,String altro_responsabile ) throws Throwable {
        if (logger.isDebugEnabled()) {
            logger.debug("inviaNotifica(String, String, long, String, String) - start"); //$NON-NLS-1$
        }

        logger.info("Parametri della richiesta:\ntipoNotifica["+tipoNotifica+"]\ntipoEntita["+tipoEntita+"]\nidEntita["+idEntita+"]\nlang["+lang+"]\nuserIdUtenteConnesso["+userIdUtenteConnesso+"]");
        //variabili di mapping con l'entit� mail
        String mailFrom = StringUtils.EMPTY;
        String mailTo = StringUtils.EMPTY;
        String mailCc = StringUtils.EMPTY;
        String mailBcc = StringUtils.EMPTY;
        String mailSubject = StringUtils.EMPTY;
        String nomeTemplate = StringUtils.EMPTY;
        String nomeDocumento = StringUtils.EMPTY;
        String linkAlDocumento = StringUtils.EMPTY;
        String linkAlDocumentoLegaliEsterni = StringUtils.EMPTY;
        String motivazioneRifiuto = StringUtils.EMPTY;
        String dataAutorizzazione = StringUtils.EMPTY;
        String utenteAutorizzazione = StringUtils.EMPTY;
        String utenteRegistrazione = StringUtils.EMPTY;
        String dataApprovazioneInSeconda = StringUtils.EMPTY;
        String utenteApprovazioneInSeconda = StringUtils.EMPTY;
        String utenteRichiedente = StringUtils.EMPTY;
        String utenteRifiuto = StringUtils.EMPTY;
        String dataApprovazione = StringUtils.EMPTY;
        String utenteApprovazione = StringUtils.EMPTY;
        String fascicoloRiferimento = StringUtils.EMPTY;
        String linkFascicoloRiferimento = StringUtils.EMPTY;
        String studioLegale = StringUtils.EMPTY;
        String indirizzo = StringUtils.EMPTY;
        String citta = StringUtils.EMPTY;
        String cap = StringUtils.EMPTY;
        String paese = StringUtils.EMPTY;
        String telefono = StringUtils.EMPTY;
        String fax = StringUtils.EMPTY;
        String email = StringUtils.EMPTY;
        String partitaIva = StringUtils.EMPTY;
        String codiceFiscale = StringUtils.EMPTY;
        String totaleAutorizzato = StringUtils.EMPTY;
        String societaAddebito = StringUtils.EMPTY;
        String annoFinanziario = StringUtils.EMPTY;
        String centroCosto = StringUtils.EMPTY;
        String voceConto = StringUtils.EMPTY;
        String unitaLegale = StringUtils.EMPTY;
        String ultimoProforma = StringUtils.EMPTY;
        String tipoProfessionista = StringUtils.EMPTY;
        String tipoAllegatoIt = StringUtils.EMPTY;
        String motivazioneRichiesta = StringUtils.EMPTY;
        String tipoAllegatoEn = StringUtils.EMPTY;
        String requestingUserID = StringUtils.EMPTY;
        String dataVerifica = StringUtils.EMPTY;
        String utenteVerificatore = StringUtils.EMPTY;

        //variabili di appoggio

        Utente utenteConnesso = null;
        Utente utenteStepInterMedio = null;
        Utente utenteCreazione = null;
        Utente responsabile = null;
        String matricolaResponsabile = StringUtils.EMPTY;
        String codiceLingua = StringUtils.EMPTY;
        String rifiuto = StringUtils.EMPTY;
        String richiesta = StringUtils.EMPTY;
        String matricolaUtenteStepIntermedio = StringUtils.EMPTY;
        boolean proformaAutorizzato = false;
        boolean beautyContestAutorizzato = false;
        StepWf step = null;
        StepWf stepIntermedio = null;
        ConfigurazioneStepWf configurazioneTradotta = null;
        String webUrl = StringUtils.EMPTY;
        String webUrl_Portale_Esterno = StringUtils.EMPTY;
        InputStream is = null;
        String destInvioAltriUffici = StringUtils.EMPTY;
        String destIncaricoAutorizzato = StringUtils.EMPTY;
        String destIncarichi = StringUtils.EMPTY;
        String destAutorizzazioneProforma = StringUtils.EMPTY;

        try {
            is = EmailNotificationServiceImpl.class.getResourceAsStream("/config.properties");
            Properties properties = new Properties();
            properties.load(is);
            webUrl = properties.getProperty("webUrl");
            webUrl_Portale_Esterno= properties.getProperty("webUrl_Portale_Esertno");
            destInvioAltriUffici=properties.getProperty("destInvioAltriUffici");
            destIncaricoAutorizzato=properties.getProperty("destIncaricoAutorizzato");
            destIncarichi=properties.getProperty("destIncarichi");
            destAutorizzazioneProforma=properties.getProperty("destAutorizzazioneProforma");
            PARCELLE_LEGALI_PAGABILI=properties.getProperty("PARCELLE_LEGALI_PAGABILI");
            MITTENTE_STANDARD=properties.getProperty("MITTENTE_STANDARD");
        }
        catch (Exception e) {
            logger.error("inviaNotifica(String, String, long, String, String)", e); //$NON-NLS-1$
            System.out.println("Properties non lette: "+ e);
        } 

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 

        if(lang.equalsIgnoreCase(CostantiDAO.LINGUA_ITALIANA)){
            rifiuto = CostantiDAO.RIFIUTO;
            richiesta = CostantiDAO.RICHIESTA;
        }
        else{
            rifiuto = CostantiDAO.RIFIUTO_EN;
            richiesta = CostantiDAO.RICHIESTA_EN;
        }



        Mail mail = new Mail();

        //recupero l'utente connesso
        utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);


        logger.info("Tipo entita: "+ tipoEntita);
        switch (tipoEntita){
        case CostantiDAO.INCARICO:
        case CostantiDAO.ARBITRATO:

            Incarico incarico = null;
            IncaricoWf incaricoWf = null;

            if(tipoEntita.equals(CostantiDAO.INCARICO)){
                incarico = incaricoDAO.leggi(idEntita);
                if(incarico == null)
                	logger.info("Incarico "+idEntita+" non trovato!");
                nomeDocumento = incarico.getNomeIncarico();
                try{
                	linkAlDocumento = String.format("%s%s%s%d%s%s%s%s%s",PREF_HREF, webUrl,SUFF_URL_INCARICO,idEntita, SUFF_HREF, INI_BOLD, nomeDocumento, FINE_BOLD,FINE_HREF);
                	linkAlDocumentoLegaliEsterni= String.format("%s%s%s%d%s%s%s%s%s",PREF_HREF, webUrl_Portale_Esterno,"entryPLE.jsp?f=di&id=",idEntita, SUFF_HREF, INI_BOLD, PORTALE_LEGALI_ESTERNI, FINE_BOLD,FINE_HREF);
                	//                    linkAlDocumento = String.format("%s%s%d", webUrl,SUFF_URL_INCARICO, idEntita);
            
                }catch(IllegalFormatException e){
                	logger.info("Errore di formato nella creazione del link al documento dell'incarico con id "+idEntita);
                }
            }
            else{
                incarico = incaricoDAO.leggiCollegioArbitrale(idEntita);
                nomeDocumento = incarico.getNomeCollegioArbitrale();
                linkAlDocumento = String.format("%s%s%s%d%s%s%s%s%s",PREF_HREF, webUrl,SUFF_URL_ARBITRATO,idEntita, SUFF_HREF, INI_BOLD, nomeDocumento, FINE_BOLD, FINE_HREF);

            }

            logger.info("Tipo Notifica: "+ tipoNotifica);
            fascicoloRiferimento = incarico.getFascicolo().getNome();
            switch(tipoNotifica){
            case CostantiDAO.RICHIEDIPROFORMA:
                logger.info("fascicoloRiferimento: " +fascicoloRiferimento);

                mailFrom=MITTENTE_STANDARD;

                mailCc=destAutorizzazioneProforma;

                ProfessionistaEsterno professionista = incarico.getProfessionistaEsterno();

                mailTo=professionista.getEmail();


                logger.info("Preparo la mail da inviare");

                mailSubject = String.format("%s - %s - %s",nomeDocumento, anagraficaStatiDAO.leggiStatoIncarico( incarico.getStatoIncarico().getCodGruppoLingua(), lang.toUpperCase()).getDescrizione(), richiesta);

                nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_INCARICO, lang.toLowerCase(), CostantiDAO.RICHIEDIPROFORMA.toLowerCase());


                logger.info("Preparo la mail da inviare, mailSubject: "+ mailSubject + ", nomeTemplate: " + nomeTemplate);

                break;

            case CostantiDAO.AVANZAMENTO:
                if(incarico.getStatoIncarico().getCodGruppoLingua().equals(CostantiDAO.INCARICO_STATO_AUTORIZZATO)){

                    dataAutorizzazione = df.format(incarico.getDataAutorizzazione());
                    utenteAutorizzazione = utenteConnesso.getNominativoUtil();
                    logger.info("leggiUltimoWorkflowTerminato");
                    incaricoWf = incaricoWfDAO.leggiUltimoWorkflowTerminato(idEntita);

                    //ricavo lo step di attesa approvazione in seconda firma
                    logger.info("leggiSpecificoStepWorkflow 1");
                    stepIntermedio = stepWfDAO.leggiSpecificoStepWorkflow(incaricoWf.getId(), CostantiDAO.AUTORIZZAZIONE_INCARICO, CostantiDAO.INCARICO_STATO_ATTESA_SECONDA_APPROVAZIONE);

                    if(stepIntermedio != null){
                        matricolaUtenteStepIntermedio = stepIntermedio.getUtenteChiusura();
                        dataApprovazioneInSeconda = df.format(stepIntermedio.getDataChiusura());
                        if(!matricolaUtenteStepIntermedio.isEmpty()){
                            utenteStepInterMedio = utenteDAO.leggiUtenteDaMatricola(matricolaUtenteStepIntermedio);
                            if(utenteStepInterMedio != null)
                                utenteApprovazioneInSeconda = utenteStepInterMedio.getNominativoUtil();
                        }
                    }


                    //ricavo lo step di attesa approvazione
                    logger.info("leggiSpecificoStepWorkflow 2");
                    stepIntermedio = stepWfDAO.leggiSpecificoStepWorkflow(incaricoWf.getId(), CostantiDAO.AUTORIZZAZIONE_INCARICO, CostantiDAO.INCARICO_STATO_ATTESA_APPROVAZIONE);

                    if(stepIntermedio != null){
                        matricolaUtenteStepIntermedio = stepIntermedio.getUtenteChiusura();
                        dataApprovazione = df.format(stepIntermedio.getDataChiusura());
                        if(!matricolaUtenteStepIntermedio.isEmpty()){
                            utenteStepInterMedio = utenteDAO.leggiUtenteDaMatricola(matricolaUtenteStepIntermedio);
                            if(utenteStepInterMedio != null)
                                utenteApprovazione = utenteStepInterMedio.getNominativoUtil();
                        }
                    }


                    //ricavo il fascicolo di riferimento

                    fascicoloRiferimento = incarico.getFascicolo().getNome();
                    logger.info("fascicoloRiferimento: " +fascicoloRiferimento);

                    //ricavo lo step corrente
                    logger.info("leggiUltimoStepChiuso");
                    step = stepWfDAO.leggiUltimoStepChiuso(incaricoWf.getId(), CostantiDAO.AUTORIZZAZIONE_INCARICO);
                    codiceLingua = step.getConfigurazioneStepWf().getCodGruppoLingua();
                    configurazioneTradotta = configurazioneStepWfDAO.leggiConfigurazioneTradotta(codiceLingua, lang);

                    //invio la e-mail al creatore del workflow
                    Utente utente = utenteDAO.leggiUtenteDaUserId(incaricoWf.getUtenteCreazione());
                    if(utente.getEmailUtil() != null && !utente.getEmailUtil().isEmpty())
                        mailTo = utente.getEmailUtil();

                    /** Durante l'avanzamento di tutti i WF (step intermedi) togliere l'indirizzo mail di chi ha fatto avanzare l'attivit� in Cc **/
                    //e in conoscenza a tutti gli attori coinvolti nel workflow
                    List<Utente> attoriWf = utenteDAO.leggiAttoriWorkflow(incaricoWf.getId(), CostantiDAO.AUTORIZZAZIONE_INCARICO);
                    for( Utente attore : attoriWf ){
                        if(attore.getEmailUtil() != null && !attore.getEmailUtil().isEmpty()){
                            if (!attore.getEmailUtil().equalsIgnoreCase(mailTo))
                                mailCc = mailCc + attore.getEmailUtil() + ",";
                        }
                    }

                    mailCc = mailCc+destIncaricoAutorizzato + ","+destIncarichi+",";

                    String mailProfEst=incarico.getProfessionistaEsterno()!=null?incarico.getProfessionistaEsterno().getEmail():"";
                    mailProfEst = (mailProfEst!=null && !mailProfEst.equals(""))?mailProfEst+",":"";

                    String mailStudioLegale=incarico.getProfessionistaEsterno()!=null?(incarico.getProfessionistaEsterno().getStudioLegale()!=null?incarico.getProfessionistaEsterno().getStudioLegale().getEmail():""):"";
                    mailStudioLegale = (mailStudioLegale!=null && !mailStudioLegale.equals(""))?mailStudioLegale+",":"";

                    mailCc = mailCc+mailProfEst+mailStudioLegale;

                    logger.info("Preparo la mail da inviare");
                    mailSubject = String.format("%s - %s - %s",nomeDocumento, anagraficaStatiDAO.leggiStatoIncarico( incarico.getStatoIncarico().getCodGruppoLingua(), lang.toUpperCase()).getDescrizione(), df.format(step.getDataChiusura()));

                    if(tipoEntita.equals(CostantiDAO.INCARICO))
                        nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_INCARICO, lang.toLowerCase(), incarico.getStatoIncarico().getCodGruppoLingua().toLowerCase());
                    else
                        nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_ARBITRATO, lang.toLowerCase(), incarico.getStatoIncarico().getCodGruppoLingua().toLowerCase());


                    logger.info("Preparo la mail da inviare, mailSubject: "+ mailSubject + ", nomeTemplate: " + nomeTemplate);
                }
                else if(incarico.getStatoIncarico().getCodGruppoLingua().equals(CostantiDAO.INCARICO_STATO_BOZZA)){

                    Fascicolo fascicolo = incarico.getFascicolo();

                    String ownerIncarico = fascicolo.getLegaleInterno();

                    Utente utente = utenteDAO.leggiUtenteDaUserId(ownerIncarico);
                    Utente topResponsabile = utenteDAO.leggiResponsabileTop();

                    String emailOwner = utente.getEmailUtil();

                    mailTo=emailOwner;
                    mailCc=topResponsabile.getEmailUtil();

                    mailSubject = String.format("%s - %s",nomeDocumento, anagraficaStatiDAO.leggiStatoIncarico(incarico.getStatoIncarico().getCodGruppoLingua(), lang.toUpperCase()).getDescrizione());
                    nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_INCARICO, lang.toLowerCase(), CostantiDAO.INCARICO_STATO_BOZZA.toLowerCase());
                }
                else{
                    incaricoWf = incaricoWfDAO.leggiWorkflowInCorso(idEntita);
                    step = stepWfDAO.leggiStepCorrente(incaricoWf.getId(), CostantiDAO.AUTORIZZAZIONE_INCARICO);
                    codiceLingua = step.getConfigurazioneStepWf().getCodGruppoLingua();
                    configurazioneTradotta = configurazioneStepWfDAO.leggiConfigurazioneTradotta(codiceLingua, lang);

                    if(step.getConfigurazioneStepWf().getTipoAssegnazione().equals(CostantiDAO.ASSEGNAZIONE_MANUALE))
                        mailTo = step.getConfigurazioneStepWf().getNotificaMailTo();
                    else{
                    	//DARIO C ****************************************************
						//matricolaResponsabile = utenteConnesso.getMatricolaRespUtil();
						matricolaResponsabile = altro_responsabile.trim().length()!=0?altro_responsabile:utenteConnesso.getMatricolaRespUtil();
						//************************************************************
                        responsabile = utenteDAO.leggiUtenteDaMatricola(matricolaResponsabile);
                        if(responsabile.getEmailUtil() != null && !responsabile.getEmailUtil().isEmpty())
                            mailTo = responsabile.getEmailUtil();
                    }
                    //                    if(!utenteConnesso.getEmailUtil().isEmpty())
                    //                        mailCc = utenteConnesso.getEmailUtil();
                    mailSubject = String.format("%s - %s - %s",nomeDocumento, configurazioneTradotta.getDescrStateTo(), df.format(step.getDataCreazione()));
                }
                if(tipoEntita.equals(CostantiDAO.INCARICO))
                    nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_INCARICO, lang.toLowerCase(), incarico.getStatoIncarico().getCodGruppoLingua().toLowerCase());
                else
                    nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_ARBITRATO, lang.toLowerCase(), incarico.getStatoIncarico().getCodGruppoLingua().toLowerCase());

                break;
            case CostantiDAO.RIFIUTO:
                incaricoWf = incaricoWfDAO.leggiUltimoWorkflowRifiutato(idEntita);
                step = stepWfDAO.leggiUltimoStepChiuso(incaricoWf.getId(), CostantiDAO.AUTORIZZAZIONE_INCARICO);
                motivazioneRifiuto = step.getMotivoRifiuto();
                codiceLingua = step.getConfigurazioneStepWf().getCodGruppoLingua();
                configurazioneTradotta = configurazioneStepWfDAO.leggiConfigurazioneTradotta(codiceLingua, lang);

                //invio la e-mail al creatore del workflow
                utenteCreazione = utenteDAO.leggiUtenteDaUserId(incaricoWf.getUtenteCreazione());
                if(utenteCreazione.getEmailUtil() != null && !utenteCreazione.getEmailUtil().isEmpty())
                    mailTo = utenteCreazione.getEmailUtil();

                //e in conoscenza a tutti gli attori coinvolti nel workflow
                List<Utente> attoriWf = utenteDAO.leggiAttoriWorkflow(incaricoWf.getId(), CostantiDAO.AUTORIZZAZIONE_INCARICO);
                for( Utente attore : attoriWf ){
                    if(attore.getEmailUtil() != null && !attore.getEmailUtil().isEmpty()){
                        if (!attore.getEmailUtil().equalsIgnoreCase(mailTo))
                            mailCc = mailCc + attore.getEmailUtil() + ",";
                    }
                }
                mailSubject = String.format("%s - %s %s - %s",nomeDocumento, rifiuto, configurazioneTradotta.getDescrStateTo(), df.format(step.getDataChiusura()));
                if(tipoEntita.equals(CostantiDAO.INCARICO))
                    nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_INCARICO, lang.toLowerCase(), CostantiDAO.RIFIUTO.toLowerCase());
                else
                    nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_ARBITRATO, lang.toLowerCase(), CostantiDAO.RIFIUTO.toLowerCase());

                break;
            }
            break;

        case CostantiDAO.SCHEDA_FONDO_RISCHI:

            SchedaFondoRischi schedaFondoRischi = null;
            SchedaFondoRischiWf schedaFondoRischiWf = null;

            schedaFondoRischi= schedaFondoRischiDAO.leggi(idEntita);
            nomeDocumento = schedaFondoRischi.getFascicolo().getNome();

            //ricavo il fascicolo di riferimento
            fascicoloRiferimento = schedaFondoRischi.getFascicolo().getNome();
            logger.info("fascicoloRiferimento: " +fascicoloRiferimento);

            linkAlDocumento = String.format("%s%s%s%d%s%s%s%s%s",PREF_HREF, webUrl,SUFF_URL_SCHEDA_FONDO_RISCHI,idEntita, SUFF_HREF, INI_BOLD, nomeDocumento, FINE_BOLD,FINE_HREF);
            linkAlDocumentoLegaliEsterni= String.format("%s%s%s%d%s%s%s%s%s",PREF_HREF, webUrl_Portale_Esterno,"entryPLE.jsp?f=dsfr&id=",idEntita, SUFF_HREF, INI_BOLD, PORTALE_LEGALI_ESTERNI, FINE_BOLD,FINE_HREF);

            logger.info("Tipo Notifica: "+ tipoNotifica);
            switch(tipoNotifica){
            case CostantiDAO.AVANZAMENTO:
                if(schedaFondoRischi.getStatoSchedaFondoRischi().getCodGruppoLingua().equals(CostantiDAO.SCHEDA_FONDO_RISCHI_STATO_AUTORIZZATO)){

                    dataAutorizzazione = df.format(schedaFondoRischi.getDataAutorizzazione());
                    utenteAutorizzazione = utenteConnesso.getNominativoUtil();
                    logger.info("leggiUltimoWorkflowTerminato");
                    schedaFondoRischiWf = schedaFondoRischiWfDAO.leggiUltimoWorkflowTerminato(idEntita);

                    //ricavo lo step di attesa approvazione in seconda firma
                    logger.info("leggiSpecificoStepWorkflow 1");
                    stepIntermedio = stepWfDAO.leggiSpecificoStepWorkflow(schedaFondoRischiWf.getId(), CostantiDAO.AUTORIZZAZIONE_SCHEDA_FONDO_RISCHI, CostantiDAO.SCHEDA_FONDO_RISCHI_STATO_ATTESA_SECONDA_APPROVAZIONE);

                    if(stepIntermedio != null){
                        matricolaUtenteStepIntermedio = stepIntermedio.getUtenteChiusura();
                        dataApprovazioneInSeconda = df.format(stepIntermedio.getDataChiusura());
                        if(!matricolaUtenteStepIntermedio.isEmpty()){
                            utenteStepInterMedio = utenteDAO.leggiUtenteDaMatricola(matricolaUtenteStepIntermedio);
                            if(utenteStepInterMedio != null)
                                utenteApprovazioneInSeconda = utenteStepInterMedio.getNominativoUtil();
                        }
                    }

                    //ricavo lo step di attesa approvazione
                    logger.info("leggiSpecificoStepWorkflow 2");
                    stepIntermedio = stepWfDAO.leggiSpecificoStepWorkflow(schedaFondoRischiWf.getId(), CostantiDAO.AUTORIZZAZIONE_SCHEDA_FONDO_RISCHI, CostantiDAO.SCHEDA_FONDO_RISCHI_STATO_ATTESA_APPROVAZIONE);

                    if(stepIntermedio != null){
                        matricolaUtenteStepIntermedio = stepIntermedio.getUtenteChiusura();
                        dataApprovazione = df.format(stepIntermedio.getDataChiusura());
                        if(!matricolaUtenteStepIntermedio.isEmpty()){
                            utenteStepInterMedio = utenteDAO.leggiUtenteDaMatricola(matricolaUtenteStepIntermedio);
                            if(utenteStepInterMedio != null)
                                utenteApprovazione = utenteStepInterMedio.getNominativoUtil();
                        }
                    }

                    //ricavo lo step di attesa approvazione
                    logger.info("leggiSpecificoStepWorkflow 2");
                    stepIntermedio = stepWfDAO.leggiSpecificoStepWorkflow(schedaFondoRischiWf.getId(), CostantiDAO.AUTORIZZAZIONE_SCHEDA_FONDO_RISCHI, CostantiDAO.SCHEDA_FONDO_RISCHI_STATO_ATTESA_VERIFICA);

                    if(stepIntermedio != null){
                        matricolaUtenteStepIntermedio = stepIntermedio.getUtenteChiusura();
                        dataVerifica = df.format(stepIntermedio.getDataChiusura());
                        if(!matricolaUtenteStepIntermedio.isEmpty()){
                            utenteStepInterMedio = utenteDAO.leggiUtenteDaMatricola(matricolaUtenteStepIntermedio);
                            if(utenteStepInterMedio != null)
                                utenteVerificatore = utenteStepInterMedio.getNominativoUtil();
                        }
                    }

                    //ricavo lo step corrente
                    logger.info("leggiUltimoStepChiuso");
                    step = stepWfDAO.leggiUltimoStepChiuso(schedaFondoRischiWf.getId(), CostantiDAO.AUTORIZZAZIONE_SCHEDA_FONDO_RISCHI);
                    codiceLingua = step.getConfigurazioneStepWf().getCodGruppoLingua();
                    configurazioneTradotta = configurazioneStepWfDAO.leggiConfigurazioneTradotta(codiceLingua, lang);

                    //invio la e-mail al creatore del workflow
                    Utente utente = utenteDAO.leggiUtenteDaUserId(schedaFondoRischiWf.getUtenteCreazione());
                    if(utente.getEmailUtil() != null && !utente.getEmailUtil().isEmpty())
                        mailTo = utente.getEmailUtil();

                    /** Durante l'avanzamento di tutti i WF (step intermedi) togliere l'indirizzo mail di chi ha fatto avanzare l'attivit� in Cc **/
                    //e in conoscenza a tutti gli attori coinvolti nel workflow
                    //List<Utente> attoriWf = utenteDAO.leggiAttoriWorkflow(incaricoWf.getId(), CostantiDAO.AUTORIZZAZIONE_INCARICO);
                    //for( Utente attore : attoriWf ){
                    //if(!attore.getEmailUtil().isEmpty() && (!attore.getEmailUtil().equalsIgnoreCase(mailTo)))
                    //mailCc = mailCc + attore.getEmailUtil() + ",";
                    //}

                    mailCc = mailCc+destIncaricoAutorizzato + ",";

                    mailCc = mailCc+destIncarichi;

                    logger.info("Preparo la mail da inviare");
                    mailSubject = String.format("%s - %s - %s",nomeDocumento, anagraficaStatiDAO.leggiStatoSchedaFondoRischi( schedaFondoRischi.getStatoSchedaFondoRischi().getCodGruppoLingua(), lang.toUpperCase()).getDescrizione(), df.format(step.getDataChiusura()));

                    nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_SCHEDA_FONDO_RISCHI, lang.toLowerCase(), schedaFondoRischi.getStatoSchedaFondoRischi().getCodGruppoLingua().toLowerCase());

                    logger.info("Preparo la mail da inviare, mailSubject: "+ mailSubject + ", nomeTemplate: " + nomeTemplate);
                }
                else if(schedaFondoRischi.getStatoSchedaFondoRischi().getCodGruppoLingua().equals(CostantiDAO.SCHEDA_FONDO_RISCHI_STATO_BOZZA)){

                    Fascicolo fascicolo = schedaFondoRischi.getFascicolo();

                    String ownerIncarico = fascicolo.getLegaleInterno();

                    Utente utente = utenteDAO.leggiUtenteDaUserId(ownerIncarico);
                    Utente topResponsabile = utenteDAO.leggiResponsabileTop();

                    String emailOwner = utente.getEmailUtil();

                    mailTo=emailOwner;
                    mailCc=topResponsabile.getEmailUtil();

                    mailSubject = String.format("%s - %s",nomeDocumento, anagraficaStatiDAO.leggiStatoSchedaFondoRischi(schedaFondoRischi.getStatoSchedaFondoRischi().getCodGruppoLingua(), lang.toUpperCase()).getDescrizione());
                    nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_SCHEDA_FONDO_RISCHI, lang.toLowerCase(), CostantiDAO.SCHEDA_FONDO_RISCHI_STATO_BOZZA.toLowerCase());
                }
                else{
                    schedaFondoRischiWf = schedaFondoRischiWfDAO.leggiWorkflowInCorso(idEntita);
                    step = stepWfDAO.leggiStepCorrente(schedaFondoRischiWf.getId(), CostantiDAO.AUTORIZZAZIONE_SCHEDA_FONDO_RISCHI);
                    codiceLingua = step.getConfigurazioneStepWf().getCodGruppoLingua();
                    configurazioneTradotta = configurazioneStepWfDAO.leggiConfigurazioneTradotta(codiceLingua, lang);

                    if(step.getConfigurazioneStepWf().getTipoAssegnazione().equals(CostantiDAO.ASSEGNAZIONE_MANUALE))
                        mailTo = step.getConfigurazioneStepWf().getNotificaMailTo();
                    else if(step.getConfigurazioneStepWf().getTipoAssegnazione().equals(CostantiDAO.ASSEGNAZIONE_SEGRETERIA)){

                        GruppoUtente gruppo = step.getConfigurazioneStepWf().getGruppoUtente();
                        List<Utente> utenti = utenteDAO.leggiUtentiDaGruppo(gruppo);

                        for(Utente utente : utenti){

                            if(utente.getEmailUtil() != null && !utente.getEmailUtil().isEmpty())
                                mailTo += utente.getEmailUtil() + ",";
                        }
                    }
                    else{
                    	//DARIO C ****************************************************
						//matricolaResponsabile = utenteConnesso.getMatricolaRespUtil();
						matricolaResponsabile = altro_responsabile.trim().length()!=0?altro_responsabile:utenteConnesso.getMatricolaRespUtil();
						//************************************************************
                        responsabile = utenteDAO.leggiUtenteDaMatricola(matricolaResponsabile);
                        if(responsabile.getEmailUtil() != null && !responsabile.getEmailUtil().isEmpty())
                            mailTo = responsabile.getEmailUtil();
                    }
                    //                    if(!utenteConnesso.getEmailUtil().isEmpty())
                    //                        mailCc = utenteConnesso.getEmailUtil();
                    mailSubject = String.format("%s - %s - %s",nomeDocumento, configurazioneTradotta.getDescrStateTo(), df.format(step.getDataCreazione()));
                }
                nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_SCHEDA_FONDO_RISCHI, lang.toLowerCase(), schedaFondoRischi.getStatoSchedaFondoRischi().getCodGruppoLingua().toLowerCase());

                break;
            case CostantiDAO.RIFIUTO:
                schedaFondoRischiWf = schedaFondoRischiWfDAO.leggiUltimoWorkflowRifiutato(idEntita);
                step = stepWfDAO.leggiUltimoStepChiuso(schedaFondoRischiWf.getId(), CostantiDAO.AUTORIZZAZIONE_SCHEDA_FONDO_RISCHI);
                motivazioneRifiuto = step.getMotivoRifiuto();
                codiceLingua = step.getConfigurazioneStepWf().getCodGruppoLingua();
                configurazioneTradotta = configurazioneStepWfDAO.leggiConfigurazioneTradotta(codiceLingua, lang);

                //invio la e-mail al creatore del workflow
                utenteCreazione = utenteDAO.leggiUtenteDaUserId(schedaFondoRischiWf.getUtenteCreazione());
                if(utenteCreazione.getEmailUtil() != null && !utenteCreazione.getEmailUtil().isEmpty())
                    mailTo = utenteCreazione.getEmailUtil();

                mailSubject = String.format("%s - %s %s - %s",nomeDocumento, rifiuto, configurazioneTradotta.getDescrStateTo(), df.format(step.getDataChiusura()));
                nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_SCHEDA_FONDO_RISCHI, lang.toLowerCase(), CostantiDAO.RIFIUTO.toLowerCase());

                break;
            }
            break;

        case CostantiDAO.BEAUTY_CONTEST:

            BeautyContest beautyContest = null;
            BeautyContestWf beautyContestWf = null;

            beautyContest= beautyContestDAO.leggi(idEntita);
            nomeDocumento = beautyContest.getTitolo()!=null?beautyContest.getTitolo():"";
            linkAlDocumento = String.format("%s%s%s%d%s%s%s%s%s",PREF_HREF, webUrl,SUFF_URL_BEAUTY_CONTEST,idEntita, SUFF_HREF, INI_BOLD, nomeDocumento, FINE_BOLD,FINE_HREF);

            logger.info("Tipo Notifica: "+ tipoNotifica);

            switch(tipoNotifica){

            case CostantiDAO.AVANZAMENTO:
                if(beautyContest.getStatoBeautyContest().getCodGruppoLingua().equals(CostantiDAO.BEAUTY_CONTEST_STATO_AUTORIZZATO)){

                    beautyContestAutorizzato = true;
                    dataAutorizzazione = df.format(beautyContest.getDataAutorizzazione());
                    utenteAutorizzazione = utenteConnesso.getNominativoUtil();
                    logger.info("leggiUltimoWorkflowTerminato");
                    beautyContestWf = beautyContestWfDAO.leggiUltimoWorkflowTerminato(idEntita);

                    //ricavo lo step di attesa approvazione in seconda firma
                    logger.info("leggiSpecificoStepWorkflow 1");
                    stepIntermedio = stepWfDAO.leggiSpecificoStepWorkflow(beautyContestWf.getId(), CostantiDAO.AUTORIZZAZIONE_BEAUTY_CONTEST, CostantiDAO.BEAUTY_CONTEST_STATO_ATTESA_SECONDA_APPROVAZIONE);

                    if(stepIntermedio != null){
                        matricolaUtenteStepIntermedio = stepIntermedio.getUtenteChiusura();
                        dataApprovazioneInSeconda = df.format(stepIntermedio.getDataChiusura());
                        if(!matricolaUtenteStepIntermedio.isEmpty()){
                            utenteStepInterMedio = utenteDAO.leggiUtenteDaMatricola(matricolaUtenteStepIntermedio);
                            if(utenteStepInterMedio != null)
                                utenteApprovazioneInSeconda = utenteStepInterMedio.getNominativoUtil();
                        }
                    }

                    //ricavo lo step di attesa approvazione
                    logger.info("leggiSpecificoStepWorkflow 2");
                    stepIntermedio = stepWfDAO.leggiSpecificoStepWorkflow(beautyContestWf.getId(), CostantiDAO.AUTORIZZAZIONE_BEAUTY_CONTEST, CostantiDAO.BEAUTY_CONTEST_STATO_ATTESA_APPROVAZIONE);

                    if(stepIntermedio != null){
                        matricolaUtenteStepIntermedio = stepIntermedio.getUtenteChiusura();
                        dataApprovazione = df.format(stepIntermedio.getDataChiusura());
                        if(!matricolaUtenteStepIntermedio.isEmpty()){
                            utenteStepInterMedio = utenteDAO.leggiUtenteDaMatricola(matricolaUtenteStepIntermedio);
                            if(utenteStepInterMedio != null)
                                utenteApprovazione = utenteStepInterMedio.getNominativoUtil();
                        }
                    }

                    //ricavo lo step corrente
                    logger.info("leggiUltimoStepChiuso");
                    step = stepWfDAO.leggiUltimoStepChiuso(beautyContestWf.getId(), CostantiDAO.AUTORIZZAZIONE_BEAUTY_CONTEST);
                    codiceLingua = step.getConfigurazioneStepWf().getCodGruppoLingua();
                    configurazioneTradotta = configurazioneStepWfDAO.leggiConfigurazioneTradotta(codiceLingua, lang);

                    //invio la e-mail al creatore del workflow
                    Utente utente = utenteDAO.leggiUtenteDaUserId(beautyContestWf.getUtenteCreazione());
                    if(utente.getEmailUtil() != null && !utente.getEmailUtil().isEmpty())
                        mailTo = utente.getEmailUtil();

                    logger.info("Preparo la mail da inviare");

                    mailSubject = String.format("%s - %s - %s",nomeDocumento, anagraficaStatiDAO.leggiStatoBeautyContest( beautyContest.getStatoBeautyContest().getCodGruppoLingua(), lang.toUpperCase()).getDescrizione(), df.format(step.getDataChiusura()));
                    nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_BEAUTY_CONTEST, lang.toLowerCase(), beautyContest.getStatoBeautyContest().getCodGruppoLingua().toLowerCase());

                    logger.info("Preparo la mail da inviare, mailSubject: "+ mailSubject + ", nomeTemplate: " + nomeTemplate);
                }
                else if(beautyContest.getStatoBeautyContest().getCodGruppoLingua().equals(CostantiDAO.BEAUTY_CONTEST_STATO_BOZZA)){

                    String owner = beautyContest.getLegaleInterno();

                    Utente utente = utenteDAO.leggiUtenteDaUserId(owner);
                    Utente topResponsabile = utenteDAO.leggiResponsabileTop();

                    String emailOwner = utente.getEmailUtil();

                    mailTo=emailOwner;
                    mailCc=topResponsabile.getEmailUtil();

                    mailSubject = String.format("%s - %s",nomeDocumento, anagraficaStatiDAO.leggiStatoBeautyContest(beautyContest.getStatoBeautyContest().getCodGruppoLingua(), lang.toUpperCase()).getDescrizione());
                    nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_BEAUTY_CONTEST, lang.toLowerCase(), CostantiDAO.BEAUTY_CONTEST_STATO_BOZZA.toLowerCase());
                }
                else{
                    beautyContestWf = beautyContestWfDAO.leggiWorkflowInCorso(idEntita);
                    step = stepWfDAO.leggiStepCorrente(beautyContestWf.getId(), CostantiDAO.AUTORIZZAZIONE_BEAUTY_CONTEST);
                    codiceLingua = step.getConfigurazioneStepWf().getCodGruppoLingua();
                    configurazioneTradotta = configurazioneStepWfDAO.leggiConfigurazioneTradotta(codiceLingua, lang);

                    if(step.getConfigurazioneStepWf().getTipoAssegnazione().equals(CostantiDAO.ASSEGNAZIONE_MANUALE))
                        mailTo = step.getConfigurazioneStepWf().getNotificaMailTo();
                    else{
                    	//DARIO C ****************************************************
						//matricolaResponsabile = utenteConnesso.getMatricolaRespUtil();
						matricolaResponsabile = altro_responsabile.trim().length()!=0?altro_responsabile:utenteConnesso.getMatricolaRespUtil();
						//************************************************************
                        responsabile = utenteDAO.leggiUtenteDaMatricola(matricolaResponsabile);
                        if(responsabile.getEmailUtil() != null && !responsabile.getEmailUtil().isEmpty())
                            mailTo = responsabile.getEmailUtil();
                    }
                    mailSubject = String.format("%s - %s - %s",nomeDocumento, configurazioneTradotta.getDescrStateTo(), df.format(step.getDataCreazione()));
                }
                nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_BEAUTY_CONTEST, lang.toLowerCase(), beautyContest.getStatoBeautyContest().getCodGruppoLingua().toLowerCase());

                break;
            case CostantiDAO.RIFIUTO:
                beautyContestWf = beautyContestWfDAO.leggiUltimoWorkflowRifiutato(idEntita);
                step = stepWfDAO.leggiUltimoStepChiuso(beautyContestWf.getId(), CostantiDAO.AUTORIZZAZIONE_BEAUTY_CONTEST);
                motivazioneRifiuto = step.getMotivoRifiuto();
                codiceLingua = step.getConfigurazioneStepWf().getCodGruppoLingua();
                configurazioneTradotta = configurazioneStepWfDAO.leggiConfigurazioneTradotta(codiceLingua, lang);

                //invio la e-mail al creatore del workflow
                utenteCreazione = utenteDAO.leggiUtenteDaUserId(beautyContestWf.getUtenteCreazione());
                if(utenteCreazione.getEmailUtil() != null && !utenteCreazione.getEmailUtil().isEmpty())
                    mailTo = utenteCreazione.getEmailUtil();

                mailSubject = String.format("%s - %s %s - %s",nomeDocumento, rifiuto, configurazioneTradotta.getDescrStateTo(), df.format(step.getDataChiusura()));
                nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_BEAUTY_CONTEST, lang.toLowerCase(), CostantiDAO.RIFIUTO.toLowerCase());

                break;
            }
            break;

        case CostantiDAO.PROFORMA:
            Proforma proforma = null;
            ProformaWf proformaWf = null;
            proforma = proformaDAO.leggi(idEntita);

            nomeDocumento = proforma.getNomeProforma();

            //ricavo il fascicolo di riferimento
            fascicoloRiferimento = "";
            Incarico incaricoAssociatoProforma = incaricoDAO.leggiIncaricoAssociatoAProforma(idEntita);

            if(incaricoAssociatoProforma != null){
            	logger.info("Incarico associato al proforma presente");
                if(incaricoAssociatoProforma.getFascicolo() != null){
                	logger.info("fascicolo presente");
                    fascicoloRiferimento = incaricoAssociatoProforma.getFascicolo().getNome();
                }
            }
            logger.info("fascicoloRiferimento: " +fascicoloRiferimento);

            linkAlDocumento = String.format("%s%s%s%d%s%s%s%s%s",PREF_HREF, webUrl,SUFF_URL_PROFORMA,idEntita, SUFF_HREF, INI_BOLD, nomeDocumento, FINE_BOLD, FINE_HREF);
            
            logger.info("linkAlDocumento: "+linkAlDocumento);
            switch(tipoNotifica){
            case CostantiDAO.AVANZAMENTO:

                if(proforma.getStatoProforma().getCodGruppoLingua().equals(CostantiDAO.PROFORMA_STATO_AUTORIZZATO)){

                    proformaAutorizzato = true;
                    dataAutorizzazione = df.format(proforma.getDataAutorizzazione());
                    utenteAutorizzazione = utenteConnesso.getNominativoUtil();
                    unitaLegale = utenteConnesso.getDescrUnitaUtil();

                    //ricavo l'incarico associato al proforma
                    Incarico incaricoAssociato = incaricoDAO.leggiIncaricoAssociatoAProforma(proforma.getId());

                    //ricavo il link al fascicolo di riferimento
                    linkFascicoloRiferimento = String.format("%s%s%s%d%s%s%s%s%s",PREF_HREF, webUrl,SUFF_URL_FASCICOLO,incaricoAssociato.getFascicolo().getId(), SUFF_HREF, INI_BOLD, incaricoAssociato.getFascicolo().getNome(), FINE_BOLD, FINE_HREF);

                    //                                 linkFascicoloRiferimento = String.format("%s%s%d", webUrl,SUFF_URL_FASCICOLO,incaricoAssociato.getFascicolo().getId());

                    //recupero il professionista esterno
                    ProfessionistaEsterno professionistaAssociato = incaricoAssociato.getProfessionistaEsterno();

                    //recupero lo studio legale del professionista
                    StudioLegale studioLegaleAssociato = professionistaAssociato.getStudioLegale();
                    studioLegale = studioLegaleAssociato.getDenominazione();

                    totaleAutorizzato = proforma.getTotaleAutorizzato().toString();

                    societaAddebito = societaDAO.leggiSocietaAddebitoProforma(idEntita).getNome();

                    annoFinanziario = proforma.getAnnoEsercizioFinanziario().toString();

                    centroCosto = (proforma.getCentroDiCosto() == null)? "":proforma.getCentroDiCosto();

                    voceConto = (proforma.getVoceDiConto() == null)? "":proforma.getVoceDiConto();
                    
                    

                    if(proforma.getUltimo().equalsIgnoreCase(Character.toString(CostantiDAO.TRUE_CHAR)))
                        ultimoProforma = Character.toString(CostantiDAO.TRUE_CHAR);


                    proformaWf = proformaWfDAO.leggiUltimoWorkflowTerminato(idEntita);


                    //ricavo lo step di attesa approvazione in seconda firma
                    stepIntermedio = stepWfDAO.leggiSpecificoStepWorkflow(proformaWf.getId(), CostantiDAO.AUTORIZZAZIONE_PROFORMA, CostantiDAO.PROFORMA_STATO_ATTESA_SECONDA_APPROVAZIONE);

                    if(stepIntermedio != null){
                        matricolaUtenteStepIntermedio = stepIntermedio.getUtenteChiusura();
                        dataApprovazioneInSeconda = df.format(stepIntermedio.getDataChiusura());
                        if(!matricolaUtenteStepIntermedio.isEmpty()){
                            utenteStepInterMedio = utenteDAO.leggiUtenteDaMatricola(matricolaUtenteStepIntermedio);
                            if(utenteStepInterMedio != null)
                                utenteApprovazioneInSeconda = utenteStepInterMedio.getNominativoUtil();
                        }
                    }


                    //ricavo lo step di attesa approvazione
                    stepIntermedio = stepWfDAO.leggiSpecificoStepWorkflow(proformaWf.getId(), CostantiDAO.AUTORIZZAZIONE_PROFORMA, CostantiDAO.PROFORMA_STATO_ATTESA_APPROVAZIONE);

                    if(stepIntermedio != null){
                        matricolaUtenteStepIntermedio = stepIntermedio.getUtenteChiusura();
                        dataApprovazione = df.format(stepIntermedio.getDataChiusura());
                        if(!matricolaUtenteStepIntermedio.isEmpty()){
                            utenteStepInterMedio = utenteDAO.leggiUtenteDaMatricola(matricolaUtenteStepIntermedio);
                            if(utenteStepInterMedio != null)
                                utenteApprovazione = utenteStepInterMedio.getNominativoUtil();
                        }
                    }



                    step = stepWfDAO.leggiUltimoStepChiuso(proformaWf.getId(), CostantiDAO.AUTORIZZAZIONE_PROFORMA);
                    codiceLingua = step.getConfigurazioneStepWf().getCodGruppoLingua();
                    configurazioneTradotta = configurazioneStepWfDAO.leggiConfigurazioneTradotta(codiceLingua, lang);

                    //invio la e-mail al creatore del workflow
                    utenteCreazione = utenteDAO.leggiUtenteDaUserId(proformaWf.getUtenteCreazione());
                    if(utenteCreazione.getEmailUtil() != null && !utenteCreazione.getEmailUtil().isEmpty())
                        mailTo = utenteCreazione.getEmailUtil()+ ",";

                    mailTo = mailTo+destAutorizzazioneProforma;

                    /** Durante l'avanzamento di tutti i WF (step intermedi) togliere l'indirizzo mail di chi ha fatto avanzare l'attivit� in Cc **/
                    //e in conoscenza a tutti gli attori coinvolti nel workflow
                    List<Utente> attoriWf = utenteDAO.leggiAttoriWorkflow(proformaWf.getId(), CostantiDAO.AUTORIZZAZIONE_PROFORMA);
                    for( Utente attore : attoriWf ){
                        if(attore.getEmailUtil() != null && !attore.getEmailUtil().isEmpty()){
                            if (!attore.getEmailUtil().equalsIgnoreCase(mailTo))
                                mailCc = mailCc + attore.getEmailUtil() + ",";
                        }
                    }

                    mailSubject = String.format("%s - %s - %s",nomeDocumento, anagraficaStatiDAO.leggiStatoProforma( proforma.getStatoProforma().getCodGruppoLingua(), lang.toUpperCase()).getDescrizione(), df.format(step.getDataChiusura()));
                    nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_PROFORMA, lang.toLowerCase(), proforma.getStatoProforma().getCodGruppoLingua().toLowerCase());

                }
                else if(proforma.getStatoProforma().getCodGruppoLingua().equals(CostantiDAO.PROFORMA_STATO_BOZZA)){

                    String ownerProforma = proforma.getAutorizzatore();


                    Utente utente = utenteDAO.leggiUtenteDaUserId(ownerProforma);
                    Utente topResponsabile = utenteDAO.leggiResponsabileTop();

                    String emailOwner = utente.getEmailUtil();

                    mailTo=emailOwner;
                    mailCc=topResponsabile.getEmailUtil();

                    mailSubject = String.format("%s - %s",nomeDocumento, anagraficaStatiDAO.leggiStatoProforma( proforma.getStatoProforma().getCodGruppoLingua(), lang.toUpperCase()).getDescrizione());
                    nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_PROFORMA, lang.toLowerCase(), CostantiDAO.PROFORMA_STATO_BOZZA.toLowerCase());
                }
                else{
                    proformaWf = proformaWfDAO.leggiWorkflowInCorso(idEntita);
                    step = stepWfDAO.leggiStepCorrente(proformaWf.getId(), CostantiDAO.AUTORIZZAZIONE_PROFORMA);
                    codiceLingua = step.getConfigurazioneStepWf().getCodGruppoLingua();
                    configurazioneTradotta = configurazioneStepWfDAO.leggiConfigurazioneTradotta(codiceLingua, lang);

                    if(step.getConfigurazioneStepWf().getTipoAssegnazione().equals(CostantiDAO.ASSEGNAZIONE_MANUALE))
                        mailTo = step.getConfigurazioneStepWf().getNotificaMailTo();
                    else{
                    	//DARIO C ****************************************************
						//matricolaResponsabile = utenteConnesso.getMatricolaRespUtil();
						matricolaResponsabile = altro_responsabile.trim().length()!=0?altro_responsabile:utenteConnesso.getMatricolaRespUtil();
						//************************************************************
                        responsabile =  utenteDAO.leggiUtenteDaMatricola(matricolaResponsabile);
                        if(responsabile.getEmailUtil() != null && !responsabile.getEmailUtil().isEmpty())
                            mailTo = responsabile.getEmailUtil();
                    }
                    //                    if(utenteConnesso.getEmailUtil() != null && !utenteConnesso.getEmailUtil().isEmpty())
                    //                        mailCc = utenteConnesso.getEmailUtil();
                    mailSubject = String.format("%s - %s - %s",nomeDocumento, configurazioneTradotta.getDescrStateTo(), df.format(step.getDataCreazione()));
                }
                nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_PROFORMA, lang.toLowerCase(), proforma.getStatoProforma().getCodGruppoLingua().toLowerCase());

                break;
            case CostantiDAO.RESPINTO:

                //motivazione
                CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");    
                motivazioneRifiuto=currentSessionUtil.getMotivazione();

                //ricavo l'incarico associato al proforma
                Incarico incaricoAssociato = incaricoDAO.leggiIncaricoAssociatoAProforma(proforma.getId());

                if(incaricoAssociato!=null){

                    //ricavo il link del proforma di riferimento del > PortaleLegaliEsterni
                    linkAlDocumento = String.format("%s%s%s%d%s%s%s%s%s",PREF_HREF, webUrl_Portale_Esterno,SUFF_URL_PROFORMA ,proforma.getId(), SUFF_HREF, INI_BOLD, proforma.getNomeProforma(), FINE_BOLD, FINE_HREF);

                    //recupero  professionista esterno
                    ProfessionistaEsterno professionistaAssociato = incaricoAssociato.getProfessionistaEsterno();
                    //recupero l'indirizzo email del professionista esterno
                    String email_profEsterno=professionistaAssociato.getEmail();

                    //recupero lo studio legale del professionista
                    //StudioLegale studioLegaleAssociato = professionistaAssociato.getStudioLegale();
                    //studioLegale = studioLegaleAssociato.getDenominazione();

                    if(email_profEsterno!=null)
                        mailTo = email_profEsterno;
                    //mailCc = mailCc + utenteConnesso.getEmailUtil() + ",";

                    nomeDocumento=proforma.getNomeProforma();

                    mailSubject = String.format("%s - %s ",nomeDocumento, "Respinto");
                    nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_PROFORMA, lang.toLowerCase(), CostantiDAO.RESPINTO.toLowerCase());

                }



                break;
            case CostantiDAO.RIFIUTO:
                proformaWf = proformaWfDAO.leggiUltimoWorkflowRifiutato(idEntita);
                step = stepWfDAO.leggiUltimoStepChiuso(proformaWf.getId(), CostantiDAO.AUTORIZZAZIONE_PROFORMA);
                motivazioneRifiuto = step.getMotivoRifiuto();
                codiceLingua = step.getConfigurazioneStepWf().getCodGruppoLingua();
                configurazioneTradotta = configurazioneStepWfDAO.leggiConfigurazioneTradotta(codiceLingua, lang);

                //invio la e-mail al creatore del workflow
                utenteCreazione = utenteDAO.leggiUtenteDaUserId(proformaWf.getUtenteCreazione());
                if(utenteCreazione.getEmailUtil() != null && !utenteCreazione.getEmailUtil().isEmpty())
                    mailTo = utenteCreazione.getEmailUtil();

                /** Durante l'avanzamento di tutti i WF (step intermedi) togliere l'indirizzo mail di chi ha fatto avanzare l'attivit� in Cc **/
                //e in conoscenza a tutti gli attori coinvolti nel workflow
                //List<Utente> attoriWf = utenteDAO.leggiAttoriWorkflow(incaricoWf.getId(), CostantiDAO.AUTORIZZAZIONE_INCARICO);
                //for( Utente attore : attoriWf ){
                //if(!attore.getEmailUtil().isEmpty() && (!attore.getEmailUtil().equalsIgnoreCase(mailTo)))
                //mailCc = mailCc + attore.getEmailUtil() + ",";
                //}
                mailSubject = String.format("%s - %s %s - %s",nomeDocumento, rifiuto, configurazioneTradotta.getDescrStateTo(), df.format(step.getDataChiusura()));
                nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_PROFORMA, lang.toLowerCase(), CostantiDAO.RIFIUTO.toLowerCase());
                break;
            }
            break;
        case CostantiDAO.PROFESSIONISTA_ESTERNO:
            ProfessionistaEsterno professionista = null;
            ProfessionistaEsternoWf professionistaWf = null;
            professionista = professionistaDAO.leggi(idEntita);
            if(professionista.getStudioLegale().getDenominazione() != null)
                studioLegale = professionista.getStudioLegale().getDenominazione();
            if(professionista.getStudioLegale().getIndirizzo() != null)
                indirizzo = professionista.getStudioLegale().getIndirizzo();
            if(professionista.getStudioLegale().getCitta() != null)
                citta = professionista.getStudioLegale().getCitta();
            if(professionista.getStudioLegale().getCap() != null)
                cap = professionista.getStudioLegale().getCap();
            if(professionista.getStudioLegale().getTelefono() != null)
                telefono = professionista.getStudioLegale().getTelefono();
            if(professionista.getStudioLegale().getFax() != null)
                fax = professionista.getStudioLegale().getFax();
            if(professionista.getStudioLegale().getEmail() != null)
                email = professionista.getStudioLegale().getEmail();
            if(professionista.getStudioLegale().getPartitaIva() != null)
                partitaIva = professionista.getStudioLegale().getPartitaIva();
            if(professionista.getCodiceFiscale() != null)
                codiceFiscale = professionista.getCodiceFiscale();

            //recupero le specializzaioni del professionista nella lingua corrente
            List<Specializzazione> listaSpecializzazioni = specializzazioneDAO.leggiSpecializzazioniAssociatiAProfessionista(idEntita);
            List<String> specializzazioniList = new ArrayList<String>(0);

            if(listaSpecializzazioni.size() > 0){
                for (Specializzazione specializzazione:listaSpecializzazioni){
                    specializzazioniList.add(specializzazioneDAO.leggi(specializzazione.getCodGruppoLingua(), lang).getDescrizione());
                }    
            }
            mail.setListaSpecializzazioni(specializzazioniList);
            //recupero la Nazione dello studio legale in base alla lingua

            codiceLingua = professionista.getStudioLegale().getNazione().getCodGruppoLingua();
            Nazione nazioneTradotta = nazioneDAO.leggiNazioneTradotta(codiceLingua, lang);
            if(nazioneTradotta != null)
                paese = nazioneTradotta.getDescrizione();


            //ricavo il tipo professionista nella lingua corrente
            TipoProfessionista tipo = anagraficaStatiDAO.leggiTipoProfessionista(professionista.getTipoProfessionista().getCodGruppoLingua(), lang);
            tipoProfessionista = tipo.getDescrizione().toLowerCase();
            nomeDocumento = professionista.getNome() + " " + professionista.getCognome();
            linkAlDocumento = String.format("%s%s%s%d%s%s%s%s%s",PREF_HREF, webUrl,SUFF_URL_PROFESSIONISTA,idEntita, SUFF_HREF, INI_BOLD, nomeDocumento, FINE_BOLD, FINE_HREF);

            switch(tipoNotifica){
            case CostantiDAO.AVANZAMENTO:

                if(professionista.getStatoProfessionista().getCodGruppoLingua().equals(CostantiDAO.PROFESSIONISTA_STATO_BOZZA))
                    return;
                if(professionista.getStatoProfessionista().getCodGruppoLingua().equals(CostantiDAO.PROFESSIONISTA_STATO_AUTORIZZATO)){
                    professionistaWf = professionistaWfDAO.leggiUltimoWorkflowTerminato(idEntita);

                    utenteCreazione = utenteDAO.leggiUtenteDaUserId(professionistaWf.getUtenteCreazione());

                    utenteRichiedente = utenteCreazione.getUseridUtil() + " - " + utenteCreazione.getNominativoUtil();
                    unitaLegale = utenteCreazione.getDescrUnitaUtil();
                    if(professionista.getMotivazioneRichiesta() != null)
                        motivazioneRichiesta = professionista.getMotivazioneRichiesta();

                    step = stepWfDAO.leggiUltimoStepChiuso(professionistaWf.getId(), CostantiDAO.AUTORIZZAZIONE_INS_PROFESSIONISTA_ESTERNO_IN_ELENCO);
                    codiceLingua = step.getConfigurazioneStepWf().getCodGruppoLingua();
                    configurazioneTradotta = configurazioneStepWfDAO.leggiConfigurazioneTradotta(codiceLingua, lang);

                    //invio la e-mail a tutti gli attori con ruolo di amministratore
                    List<Utente> amministratori = utenteDAO.leggiUtentiAmministratori();
                    for( Utente amministratore : amministratori ){
                        if(amministratore.getEmailUtil() != null && !amministratore.getEmailUtil().isEmpty())
                            mailTo = mailTo + amministratore.getEmailUtil() + ",";
                    }
                    //e in cc al creatore del workflow
                    utenteCreazione = utenteDAO.leggiUtenteDaUserId(professionistaWf.getUtenteCreazione());
                    if(utenteCreazione.getEmailUtil() != null && !utenteCreazione.getEmailUtil().isEmpty())
                        mailCc = utenteCreazione.getEmailUtil();


                    mailSubject = String.format("%s - %s - %s",nomeDocumento, anagraficaStatiDAO.leggiStatoProfessionista( professionista.getStatoProfessionista().getCodGruppoLingua(), lang.toUpperCase()).getDescrizione(), df.format(step.getDataChiusura()));
                    nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_PROFESSIONISTA_ESTERNO, lang.toLowerCase(), professionista.getStatoProfessionista().getCodGruppoLingua().toLowerCase());

                }
                else{
                    professionistaWf = professionistaWfDAO.leggiWorkflowInCorso(idEntita);

                    utenteCreazione = utenteDAO.leggiUtenteDaUserId(professionistaWf.getUtenteCreazione());

                    utenteRichiedente = utenteCreazione.getUseridUtil() + " - " + utenteCreazione.getNominativoUtil();
                    unitaLegale = utenteCreazione.getDescrUnitaUtil();
                    if(professionista.getMotivazioneRichiesta() != null)
                        motivazioneRichiesta = professionista.getMotivazioneRichiesta();

                    step = stepWfDAO.leggiStepCorrente(professionistaWf.getId(), CostantiDAO.AUTORIZZAZIONE_INS_PROFESSIONISTA_ESTERNO_IN_ELENCO);
                    codiceLingua = step.getConfigurazioneStepWf().getCodGruppoLingua();
                    configurazioneTradotta = configurazioneStepWfDAO.leggiConfigurazioneTradotta(codiceLingua, lang);

                    if(step.getConfigurazioneStepWf().getTipoAssegnazione().equals(CostantiDAO.ASSEGNAZIONE_MANUALE))
                        mailTo = step.getConfigurazioneStepWf().getNotificaMailTo();
                    else{
                        matricolaResponsabile = utenteConnesso.getMatricolaRespUtil();
                        if(!matricolaResponsabile.isEmpty()){
                            responsabile = utenteDAO.leggiUtenteDaMatricola(matricolaResponsabile);
                            if(responsabile.getEmailUtil() != null && !responsabile.getEmailUtil().isEmpty())
                                mailTo = responsabile.getEmailUtil();
                        }
                    }

                    //mailCc = utenteConnesso.getEmailUtil();
                    mailSubject = String.format("%s - %s - %s",nomeDocumento, configurazioneTradotta.getDescrStateTo(), df.format(step.getDataCreazione()));
                }
                nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_PROFESSIONISTA_ESTERNO, lang.toLowerCase(), professionista.getStatoProfessionista().getCodGruppoLingua().toLowerCase());

                break;
            case CostantiDAO.RIFIUTO:

                professionistaWf = professionistaWfDAO.leggiUltimoWorkflowRifiutato(idEntita);

                utenteCreazione = utenteDAO.leggiUtenteDaUserId(professionistaWf.getUtenteCreazione());

                utenteRichiedente = utenteCreazione.getUseridUtil() + " - " + utenteCreazione.getNominativoUtil();
                unitaLegale = utenteCreazione.getDescrUnitaUtil();
                if(professionista.getMotivazioneRichiesta() != null)
                    motivazioneRichiesta = professionista.getMotivazioneRichiesta();

                step = stepWfDAO.leggiUltimoStepChiuso(professionistaWf.getId(), CostantiDAO.AUTORIZZAZIONE_INS_PROFESSIONISTA_ESTERNO_IN_ELENCO);
                motivazioneRifiuto = step.getMotivoRifiuto();
                codiceLingua = step.getConfigurazioneStepWf().getCodGruppoLingua();
                configurazioneTradotta = configurazioneStepWfDAO.leggiConfigurazioneTradotta(codiceLingua, lang);

                //invio la e-mail al creatore del workflow
                if(utenteCreazione.getEmailUtil() != null && !utenteCreazione.getEmailUtil().isEmpty())
                    mailTo = utenteCreazione.getEmailUtil();

                //e in conoscenza a tutti gli attori coinvolti nel workflow
                List<Utente> attoriWf = utenteDAO.leggiAttoriWorkflow(professionistaWf.getId(), CostantiDAO.AUTORIZZAZIONE_INS_PROFESSIONISTA_ESTERNO_IN_ELENCO);
                for( Utente attore : attoriWf ){
                    if(attore.getEmailUtil() != null && !attore.getEmailUtil().isEmpty()){
                        if(!attore.getEmailUtil().equalsIgnoreCase(mailTo))
                            mailCc = mailCc + attore.getEmailUtil() + ",";
                    }
                }
                mailSubject = String.format("%s - %s %s - %s",nomeDocumento, rifiuto, configurazioneTradotta.getDescrStateTo(), df.format(step.getDataChiusura()));
                nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_PROFESSIONISTA_ESTERNO, lang.toLowerCase(), CostantiDAO.RIFIUTO.toLowerCase());
                break;
            }
            break;
        case CostantiDAO.FASCICOLO:
            Fascicolo fascicolo = fascicoloDAO.leggi(idEntita);
            nomeDocumento = fascicolo.getNome();
            linkAlDocumento = String.format("%s%s%s%d%s%s%s%s%s",PREF_HREF, webUrl,SUFF_URL_FASCICOLO,idEntita, SUFF_HREF, INI_BOLD, nomeDocumento, FINE_BOLD, FINE_HREF);

            logger.info("Tipo Notifica: "+ tipoNotifica);

            FascicoloWf fascicoloWf = null;
            switch(tipoNotifica){
            case CostantiDAO.AVANZAMENTO:
                if(fascicolo.getStatoFascicolo().getCodGruppoLingua().equals(CostantiDAO.FASCICOLO_STATO_APERTO) || 
                        fascicolo.getStatoFascicolo().getCodGruppoLingua().equals(CostantiDAO.FASCICOLO_STATO_ARCHIVIATO))
                    return;
                if(fascicolo.getStatoFascicolo().getCodGruppoLingua().equals(CostantiDAO.FASCICOLO_STATO_CHIUSO)){
                    fascicoloWf = fascicoloWfDAO.leggiUltimoWorkflowTerminato(idEntita);
                    step = stepWfDAO.leggiUltimoStepChiuso(fascicoloWf.getId(), CostantiDAO.CHIUSURA_FASCICOLO);
                    codiceLingua = step.getConfigurazioneStepWf().getCodGruppoLingua();
                    configurazioneTradotta = configurazioneStepWfDAO.leggiConfigurazioneTradotta(codiceLingua, lang);

                    //invio la e-mail al creatore del workflow
                    utenteCreazione = utenteDAO.leggiUtenteDaUserId(fascicoloWf.getUtenteCreazione());
                    if(utenteCreazione.getEmailUtil() != null && !utenteCreazione.getEmailUtil().isEmpty())
                        mailTo = utenteCreazione.getEmailUtil();

                    /** Durante l'avanzamento di tutti i WF (step intermedi) togliere l'indirizzo mail di chi ha fatto avanzare l'attivit� in Cc **/
                    //e in conoscenza a tutti gli attori coinvolti nel workflow
                    //List<Utente> attoriWf = utenteDAO.leggiAttoriWorkflow(incaricoWf.getId(), CostantiDAO.AUTORIZZAZIONE_INCARICO);
                    //for( Utente attore : attoriWf ){
                    //if(!attore.getEmailUtil().isEmpty() && (!attore.getEmailUtil().equalsIgnoreCase(mailTo)))
                    //mailCc = mailCc + attore.getEmailUtil() + ",";
                    //}

                    mailSubject = String.format("%s - %s - %s",nomeDocumento, anagraficaStatiDAO.leggiStatoFascicolo(fascicolo.getStatoFascicolo().getCodGruppoLingua(), lang.toUpperCase()).getDescrizione(), df.format(step.getDataChiusura()));
                    nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_FASCICOLO, lang.toLowerCase(), fascicolo.getStatoFascicolo().getCodGruppoLingua().toLowerCase());



                }
                else if(fascicolo.getStatoFascicolo().getCodGruppoLingua().equals(CostantiDAO.FASCICOLO_STATO_COMPLETATO) ){
                    String ownerFascicolo = fascicolo.getLegaleInterno();


                    Utente utente = utenteDAO.leggiUtenteDaUserId(ownerFascicolo);
                    Utente topResponsabile = utenteDAO.leggiResponsabileTop();

                    String emailOwner = utente.getEmailUtil();

                    mailTo=emailOwner;
                    mailCc=topResponsabile.getEmailUtil();

                    mailSubject = String.format("%s - %s",nomeDocumento, anagraficaStatiDAO.leggiStatoFascicolo( fascicolo.getStatoFascicolo().getCodGruppoLingua(), lang.toUpperCase()).getDescrizione());
                    nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_FASCICOLO, lang.toLowerCase(), CostantiDAO.FASCICOLO_STATO_RIPORTATO_IN_COMPLETATO.toLowerCase());

                }
                else{
                    fascicoloWf = fascicoloWfDAO.leggiWorkflowInCorso(idEntita);
                    step = stepWfDAO.leggiStepCorrente(fascicoloWf.getId(), CostantiDAO.CHIUSURA_FASCICOLO);
                    codiceLingua = step.getConfigurazioneStepWf().getCodGruppoLingua();
                    configurazioneTradotta = configurazioneStepWfDAO.leggiConfigurazioneTradotta(codiceLingua, lang);

                    matricolaResponsabile = utenteConnesso.getMatricolaRespUtil();
                    if(!matricolaResponsabile.isEmpty()){
                        responsabile = utenteDAO.leggiUtenteDaMatricola(matricolaResponsabile);
                        if(responsabile.getEmailUtil() != null && !responsabile.getEmailUtil().isEmpty())
                            mailTo = responsabile.getEmailUtil();
                    }

                    nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_FASCICOLO, lang.toLowerCase(), fascicolo.getStatoFascicolo().getCodGruppoLingua().toLowerCase());
                    //                    mailCc = utenteConnesso.getEmailUtil();
                    mailSubject = String.format("%s - %s - %s",nomeDocumento, configurazioneTradotta.getDescrStateTo(), df.format(step.getDataCreazione()));

                    if(fascicolo.getStatoFascicolo().getCodGruppoLingua().equalsIgnoreCase(CostantiDAO.FASCICOLO_STATO_ATTESA_AUTORIZZAZIONE_CHIUSURA)){
                        sendChiusuraFascicoloLegaliEsterni(idEntita, lang, fascicolo.getStatoFascicolo().getCodGruppoLingua().toLowerCase());
                    }

                }
                break;

            case CostantiDAO.RIFIUTO:

                fascicoloWf = fascicoloWfDAO.leggiUltimoWorkflowRifiutato(idEntita);
                step = stepWfDAO.leggiUltimoStepChiuso(fascicoloWf.getId(), CostantiDAO.CHIUSURA_FASCICOLO);
                motivazioneRifiuto = step.getMotivoRifiuto();
                codiceLingua = step.getConfigurazioneStepWf().getCodGruppoLingua();
                configurazioneTradotta = configurazioneStepWfDAO.leggiConfigurazioneTradotta(codiceLingua, lang);

                //invio la e-mail al creatore del workflow
                utenteCreazione = utenteDAO.leggiUtenteDaUserId(fascicoloWf.getUtenteCreazione());
                if(utenteCreazione.getEmailUtil() != null && !utenteCreazione.getEmailUtil().isEmpty())
                    mailTo = utenteCreazione.getEmailUtil();

                //e in conoscenza a tutti gli attori coinvolti nel workflow
                List<Utente> attoriWf = utenteDAO.leggiAttoriWorkflow(fascicoloWf.getId(), CostantiDAO.CHIUSURA_FASCICOLO);
                for( Utente attore : attoriWf ){
                    if(attore.getEmailUtil() != null && !attore.getEmailUtil().isEmpty()){
                        if (!attore.getEmailUtil().equalsIgnoreCase(mailTo))
                            mailCc = mailCc + attore.getEmailUtil() + ",";
                    }
                }
                mailSubject = String.format("%s - %s %s - %s",nomeDocumento, rifiuto, configurazioneTradotta.getDescrStateTo(), df.format(step.getDataChiusura()));
                nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_FASCICOLO, lang.toLowerCase(), CostantiDAO.RIFIUTO.toLowerCase());
                break;

            }
            break;
        case CostantiDAO.ATTO:
            Atto atto = attoDAO.leggi(idEntita);
            AttoWf attoWf = null;
            nomeDocumento = CostantiDAO.ATTO + " " +  atto.getNumeroProtocollo();
            linkAlDocumento = String.format(SUFF_URL_ATTO, idEntita);
            linkAlDocumento = String.format("%s%s%s%s%s%s%s%s",PREF_HREF, webUrl,linkAlDocumento, SUFF_HREF, INI_BOLD, nomeDocumento, FINE_BOLD, FINE_HREF);

            //                    linkAlDocumento = String.format("%s%s", webUrl,linkAlDocumento);
            logger.info("Tipo Notifica: "+ tipoNotifica);
            logger.info("Stato Atto: "+atto.getStatoAtto().getCodGruppoLingua());

            //ricavo il fascicolo di riferimento
            //NUOVA MODIFICA 07 AGOSTO L.ROCCA
            if(atto.getFascicolo() != null){
                if(atto.getFascicolo().getNome() != null && !atto.getFascicolo().getNome().isEmpty()){
                    fascicoloRiferimento = atto.getFascicolo().getNome();
                    logger.info("fascicoloRiferimento: " +fascicoloRiferimento);
                      }
                }
            switch(tipoNotifica){
            case CostantiDAO.AVANZAMENTO:
                if(atto.getStatoAtto().getCodGruppoLingua().equals(CostantiDAO.ATTO_STATO_INVIATO_ALTRI_UFFICI))
                    return;
                if(atto.getStatoAtto().getCodGruppoLingua().equals(CostantiDAO.ATTO_STATO_REGISTRATO)){
                    utenteRegistrazione = utenteConnesso.getUseridUtil() + " - " + utenteConnesso.getNominativoUtil();

                    attoWf = attoWfDAO.leggiUltimoWorkflowTerminato(idEntita);

                    //ricavo lo step corrente
                    step = stepWfDAO.leggiUltimoStepChiuso(attoWf.getId(), CostantiDAO.REGISTRAZIONE_ATTO);
                    codiceLingua = step.getConfigurazioneStepWf().getCodGruppoLingua();
                    configurazioneTradotta = configurazioneStepWfDAO.leggiConfigurazioneTradotta(codiceLingua, lang);

                    //invio la e-mail al creatore del workflow
                    utenteCreazione = utenteDAO.leggiUtenteDaUserId(attoWf.getUtenteCreazione());
                    if(utenteCreazione.getEmailUtil() != null && !utenteCreazione.getEmailUtil().isEmpty())
                        mailTo = utenteCreazione.getEmailUtil();

                    /** Durante l'avanzamento di tutti i WF (step intermedi) togliere l'indirizzo mail di chi ha fatto avanzare l'attivit� in Cc **/
                    //e in conoscenza a tutti gli attori coinvolti nel workflow
                    //List<Utente> attoriWf = utenteDAO.leggiAttoriWorkflow(incaricoWf.getId(), CostantiDAO.AUTORIZZAZIONE_INCARICO);
                    //for( Utente attore : attoriWf ){
                    //if(!attore.getEmailUtil().isEmpty() && (!attore.getEmailUtil().equalsIgnoreCase(mailTo)))
                    //mailCc = mailCc + attore.getEmailUtil() + ",";
                    //}

                    mailSubject = String.format("%s - %s - %s",nomeDocumento, anagraficaStatiDAO.leggiStatoAtto( atto.getStatoAtto().getCodGruppoLingua(), lang.toUpperCase()).getDescrizione(), df.format(step.getDataChiusura()));
                    nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_ATTO, lang.toLowerCase(), atto.getStatoAtto().getCodGruppoLingua().toLowerCase());
                    requestingUserID = utenteConnesso.getNominativoUtil();
                }
                else if(atto.getStatoAtto().getCodGruppoLingua().equals(CostantiDAO.ATTO_STATO_BOZZA)){

                    attoWf = attoWfDAO.leggiWorkflowDaAtto(idEntita);
                    step = stepWfDAO.leggiUltimoStepChiuso(attoWf.getId(), CostantiDAO.ATTO_STATO_BOZZA);
                    String ownerAtto = atto.getOwner();

                    if(ownerAtto==null)
                        ownerAtto=atto.getDestinatario();

                    Utente utente = utenteDAO.leggiUtenteDaUserId(ownerAtto);
                    //Utente topResponsabile = utenteDAO.leggiResponsabileTop();

                    String emailOwner = utente.getEmailUtil();
                    codiceLingua = step.getConfigurazioneStepWf().getCodGruppoLingua();
                    configurazioneTradotta = configurazioneStepWfDAO.leggiConfigurazioneTradotta(codiceLingua, lang);

                    mailTo=emailOwner;
                    mailCc=destInvioAltriUffici;

                    mailSubject = String.format("%s - %s - %s",nomeDocumento, anagraficaStatiDAO.leggiStatoAtto( atto.getStatoAtto().getCodGruppoLingua(), lang.toUpperCase()).getDescrizione(), df.format(step.getDataChiusura()));
                    nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_ATTO, lang.toLowerCase(), CostantiDAO.ATTO_STATO_BOZZA.toLowerCase());
                }
                else{
                    attoWf = attoWfDAO.leggiWorkflowInCorso(idEntita);
                    step = stepWfDAO.leggiStepCorrente(attoWf.getId(), CostantiDAO.REGISTRAZIONE_ATTO);
                    codiceLingua = step.getConfigurazioneStepWf().getCodGruppoLingua();
                    configurazioneTradotta = configurazioneStepWfDAO.leggiConfigurazioneTradotta(codiceLingua, lang);

                    utenteStepInterMedio = utenteDAO.leggiUtenteDaMatricola(attoWf.getUtenteAssegnatario());
                    if(utenteStepInterMedio.getEmailUtil() != null && !utenteStepInterMedio.getEmailUtil().isEmpty())
                        mailTo = utenteStepInterMedio.getEmailUtil();
                    nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_ATTO, lang.toLowerCase(), atto.getStatoAtto().getCodGruppoLingua().toLowerCase());
                    //                    if(!utenteConnesso.getEmailUtil().isEmpty())
                    //                        mailCc = utenteConnesso.getEmailUtil();
                    mailSubject = String.format("%s - %s - %s" ,nomeDocumento, configurazioneTradotta.getDescrStateTo(), df.format(step.getDataCreazione()));
                }
                break;

            case CostantiDAO.RIFIUTO:

                linkAlDocumento = String.format(SUFF_URL_ATTO_MODIFICA, idEntita);
                linkAlDocumento = String.format("%s%s%s%s%s%s%s%s",PREF_HREF, webUrl,linkAlDocumento, SUFF_HREF, INI_BOLD, nomeDocumento, FINE_BOLD, FINE_HREF);

                utenteRifiuto = utenteConnesso.getUseridUtil() + " - " + utenteConnesso.getNominativoUtil();

                //se General Counsel il rifiuto va notificato all'operatore di segreteria (che ha generato il wf, ormai chiuso)
                if(utenteDAO.leggiSeGeneralCounsel(utenteConnesso.getMatricolaUtil())){
                    attoWf = attoWfDAO.leggiUltimoWorkflowRifiutato(idEntita);
                    step = stepWfDAO.leggiUltimoStepChiuso(attoWf.getId(), CostantiDAO.REGISTRAZIONE_ATTO);
                    codiceLingua = step.getConfigurazioneStepWf().getCodGruppoLingua();
                    configurazioneTradotta = configurazioneStepWfDAO.leggiConfigurazioneTradotta(codiceLingua, lang);

                    utenteCreazione = utenteDAO.leggiUtenteDaUserId(attoWf.getUtenteCreazione());
                    if(utenteCreazione.getEmailUtil() != null && !utenteCreazione.getEmailUtil().isEmpty())
                        mailTo = utenteCreazione.getEmailUtil();

                    //e in conoscenza a tutti gli attori coinvolti nel workflow

                    List<Utente> attoriWf = utenteDAO.leggiAttoriWorkflow(attoWf.getId(), CostantiDAO.REGISTRAZIONE_ATTO);
                    for( Utente attore : attoriWf ){
                        if(attore.getEmailUtil() != null && !attore.getEmailUtil().isEmpty()){
                            if (!attore.getEmailUtil().equalsIgnoreCase(mailTo))
                                mailCc = mailCc + attore.getEmailUtil() + ",";
                        }
                    }


                }

                //altrimenti il rifiuto va al proprio responsabile (e il wf non � terminato)
                else {
                    attoWf = attoWfDAO.leggiWorkflowInCorso(idEntita);
                    step = stepWfDAO.leggiUltimoStepChiuso(attoWf.getId(), CostantiDAO.REGISTRAZIONE_ATTO);
                    codiceLingua = step.getConfigurazioneStepWf().getCodGruppoLingua();
                    configurazioneTradotta = configurazioneStepWfDAO.leggiConfigurazioneTradotta(codiceLingua, lang);

                    responsabile = utenteDAO.leggiUtenteDaMatricola(utenteConnesso.getMatricolaRespUtil());
                    if(responsabile.getEmailUtil() != null && !responsabile.getEmailUtil().isEmpty())
                        mailTo = responsabile.getEmailUtil();

                    //e in conoscenza a tutti gli attori coinvolti nel workflow

                    List<Utente> attoriWf = utenteDAO.leggiAttoriWorkflow(attoWf.getId(), CostantiDAO.REGISTRAZIONE_ATTO);
                    for( Utente attore : attoriWf ){
                        if(attore.getEmailUtil() != null && !attore.getEmailUtil().isEmpty()){
                            if (!attore.getEmailUtil().equalsIgnoreCase(mailTo))
                                mailCc = mailCc + attore.getEmailUtil() + ",";
                        }
                    }

                }
                motivazioneRifiuto = step.getMotivoRifiuto();
                mailSubject = String.format("%s - %s %s - %s",nomeDocumento, rifiuto, configurazioneTradotta.getDescrStateTo(), df.format(step.getDataChiusura()));
                nomeTemplate = String.format("templateMail/%s/%s/%s_%s.vm", CostantiDAO.FOLDER_ATTO, lang.toLowerCase(), configurazioneTradotta.getStateTo().toLowerCase(), CostantiDAO.RIFIUTO.toLowerCase() );
                break;
            }
        }

        //valorizzazione entit�

        logger.info("Preparo la mail da inviare, valorizzazione entity");
        mail.setAnnoFinanziario(annoFinanziario);
        mail.setCap(cap);
        mail.setCentroCosto(centroCosto);
        mail.setCitta(citta);
        mail.setCodiceFiscale(codiceFiscale);
        mail.setDataApprovazione(dataApprovazione);
        mail.setDataApprovazioneInSeconda(dataApprovazioneInSeconda);
        mail.setDataAutorizzazione(dataAutorizzazione);
        mail.setEmail(email);
        mail.setFascicoloRiferimento(fascicoloRiferimento);
        mail.setFax(fax);
        mail.setIndirizzo(indirizzo);
        mail.setLinkAlDocumento(linkAlDocumento);
        mail.setLinkAlDocumentoLegaliEsterni(linkAlDocumentoLegaliEsterni);
        mail.setLinkFascicoloRiferimento(linkFascicoloRiferimento);
        mail.setMailBcc(mailBcc);
        mail.setMailCc(mailCc);
        mail.setMailFrom(mailFrom);
        mail.setMailSubject(mailSubject);
        mail.setMailTo(mailTo);
        mail.setMotivazioneRichiesta(motivazioneRichiesta);
        mail.setMotivazioneRifiuto(motivazioneRifiuto);
        mail.setNomeTemplate(nomeTemplate);
        mail.setNomeDocumento(nomeDocumento);
        mail.setPaese(paese);
        mail.setPartitaIva(partitaIva);
        mail.setSocietaAddebito(societaAddebito);
        mail.setStudioLegale(studioLegale);
        mail.setTelefono(telefono);
        mail.setTipoAllegatoEn(tipoAllegatoEn);
        mail.setTipoAllegatoIt(tipoAllegatoIt);
        mail.setTipoProfessionista(tipoProfessionista);
        mail.setTotaleAutorizzato(totaleAutorizzato);
        mail.setUltimoProforma(ultimoProforma);
        mail.setUnitaLegale(unitaLegale);
        mail.setUtenteApprovazione(utenteApprovazione);
        mail.setUtenteApprovazioneInSeconda(utenteApprovazioneInSeconda);
        mail.setUtenteAutorizzazione(utenteAutorizzazione);
        mail.setUtenteAutorizzatore(utenteAutorizzazione);
        mail.setUtenteRegistrazione(utenteRegistrazione);
        mail.setUtenteRichiedente(utenteRichiedente);
        mail.setUtenteRifiuto(utenteRifiuto);
        mail.setVoceConto(voceConto);
        mail.setRequestingUserID(requestingUserID);
        mail.setDataVerifica(dataVerifica);
        mail.setUtenteVerificatore(utenteVerificatore);
        mail.setMailFrom(MITTENTE_STANDARD);


        logger.info(mail.toString());


        if(!mailTo.isEmpty() || ! mailCc.isEmpty())
            inviaEmail(mail);

        if(proformaAutorizzato){
            inviaNotificaProfessionistaEsterno(idEntita, lang, userIdUtenteConnesso );
            inviaNotificaAmministrativoSocieta(idEntita, lang, userIdUtenteConnesso );
        }

        if(beautyContestAutorizzato){
            inviaNotificaBeautyContestProfessionistaEsterno(idEntita, lang);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("inviaNotifica(String, String, long, String, String) - end"); //$NON-NLS-1$
        }
    }

    private void sendChiusuraFascicoloLegaliEsterni(long idEntita, String lang, String statoFascicolo) {

        String mailFrom = StringUtils.EMPTY;
        String mailTo = StringUtils.EMPTY;
        String mailCc = StringUtils.EMPTY;
        String mailSubject = StringUtils.EMPTY;
        String nomeTemplate = StringUtils.EMPTY;
        //        StepWf step = null;
        //        FascicoloWf fascicoloWf = null;
        //        
        //        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 

        List<Incarico> incarichi=null;
        Fascicolo fascicolo=null;

        try {
            fascicolo=fascicoloDAO.leggi(idEntita);
            incarichi = incaricoDAO.getIncaricoDaIdFascicolo(idEntita);
            //fascicoloWf = fascicoloWfDAO.leggiUltimoWorkflowTerminato(idEntita);
            //step = stepWfDAO.leggiUltimoStepChiuso(fascicoloWf.getId(), CostantiDAO.CHIUSURA_FASCICOLO);
        } catch (Throwable e) {
            logger.debug("Errore: "+e); //$NON-NLS-1$
        }


        if(incarichi!=null){
            for(Incarico incarico:incarichi){

                ProfessionistaEsterno prof = incarico.getProfessionistaEsterno();

                if(prof.getEmail()!=null){
                    mailFrom=MITTENTE_STANDARD;
                    mailTo=prof.getEmail();
                    try {
                        mailSubject = String.format("%s - %s",fascicolo.getNome(), anagraficaStatiDAO.leggiStatoFascicolo(fascicolo.getStatoFascicolo().getCodGruppoLingua(), lang.toUpperCase()).getDescrizione());
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                    nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_FASCICOLO, lang.toLowerCase(), "cprof");


                    Mail mail = new Mail();

                    mail.setMailTo(mailTo);
                    mail.setMailFrom(mailFrom);
                    mail.setMailCc(mailCc);
                    mail.setNomeTemplate(nomeTemplate);
                    mail.setMailSubject(mailSubject);
                    mail.setFascicoloRiferimento(fascicolo.getNome());

                    if(!mailTo.isEmpty())
                        inviaEmail(mail);

                }

            }
        }

    }

    @Override
    public void inviaNotificaAmministrativoSocieta(long idEntita, String lang, String userIdUtenteConnesso) throws Throwable {

        Proforma proforma = null;
        ProformaWf proformaWf = null;
        proforma = proformaDAO.leggi(idEntita);
        boolean invioMail = false;

        if(proforma.getDaProfEsterno() != null){

            if(proforma.getDaProfEsterno().equalsIgnoreCase("F")){
                invioMail = true;
            }
        }else{
            invioMail = true;
        }

        if(invioMail){

            if (logger.isDebugEnabled()) {
                logger.debug("inviaNotificaAmministrativoSocieta("+idEntita+", "+lang+", "+userIdUtenteConnesso+") - start"); //$NON-NLS-1$
            }

            //variabili di mapping all'entit�
            String mailTo = StringUtils.EMPTY;
            String nomeTemplate = StringUtils.EMPTY;
            String mailSubject = StringUtils.EMPTY;
            String nomeDocumento = StringUtils.EMPTY;
            String linkFascicoloRiferimento = StringUtils.EMPTY;
            String fascicoloRiferimento = StringUtils.EMPTY;
            String totaleAutorizzato = StringUtils.EMPTY;
            String societaAddebito = StringUtils.EMPTY;
            String linkAlDocumento = StringUtils.EMPTY;
            String studioLegaleDesc = StringUtils.EMPTY;
            String professionistaEsternoDesc = StringUtils.EMPTY;

            //variabili di appoggio
            String webUrl = StringUtils.EMPTY;
            String destParcelle = StringUtils.EMPTY;

            StepWf step = null;
            DateFormat df = new SimpleDateFormat("dd-MMMM-yyyy HH:mm:ss"); 
            Societa societa = null;

            // creo l'oggetto
            Mail mail = new Mail();

            InputStream is = null;
            try {
                is = EmailNotificationServiceImpl.class.getResourceAsStream("/config.properties");
                Properties properties = new Properties();
                properties.load(is);
                webUrl = properties.getProperty("webUrl");
                destParcelle = properties.getProperty("destParcelle");
                PARCELLE_LEGALI_PAGABILI=properties.getProperty("PARCELLE_LEGALI_PAGABILI");
                MITTENTE_STANDARD=properties.getProperty("MITTENTE_STANDARD");
            }
            catch (Exception e) {
                logger.error("inviaNotificaAmministrativoSocieta("+idEntita+", "+lang+", "+userIdUtenteConnesso+")", e); //$NON-NLS-1$

                System.out.println("Properties non lette: "+ e);
            } 

            nomeDocumento = proforma.getNomeProforma();
            linkAlDocumento = String.format("%s%s%s%d%s%s%s%s%s",PREF_HREF, webUrl,SUFF_URL_PROFORMA_AMMINISTRATIVI,idEntita, SUFF_HREF, INI_BOLD, nomeDocumento, FINE_BOLD, FINE_HREF);

            proformaWf = proformaWfDAO.leggiUltimoWorkflowTerminato(idEntita);
            step = stepWfDAO.leggiUltimoStepChiuso(proformaWf.getId(), CostantiDAO.AUTORIZZAZIONE_PROFORMA);

            //recupero l'incarico associato
            Incarico incaricoAssociato = incaricoDAO.leggiIncaricoAssociatoAProforma(idEntita);

            //--Fix
            ProfessionistaEsterno pEsterno=null;
            StudioLegale studioLegale=null;

            pEsterno= professionistaDAO.leggi(incaricoAssociato.getProfessionistaEsterno().getId()); 

            if(pEsterno!=null){
                professionistaEsternoDesc=pEsterno.getNome() +" "+ pEsterno.getCognome(); 
                studioLegale=pEsterno.getStudioLegale();
                studioLegaleDesc=" - Studio Legale: "+professionistaEsternoDesc;
            } 

            if(studioLegale!=null)
                studioLegaleDesc=" - Studio Legale: "+studioLegale.getDenominazione();

            linkFascicoloRiferimento = String.format("%s%s%s%d%s%s%s%s%s",PREF_HREF, webUrl,SUFF_URL_FASCICOLO,incaricoAssociato.getFascicolo().getId(), SUFF_HREF, INI_BOLD, incaricoAssociato.getFascicolo().getNome(), FINE_BOLD, FINE_HREF);

            //ricavo il fascicolo di riferimento
            fascicoloRiferimento = incaricoAssociato.getFascicolo().getNome();
            logger.info("fascicoloRiferimento: " +fascicoloRiferimento);

            if(proforma.getTotaleAutorizzato() != null)
                totaleAutorizzato = proforma.getTotaleAutorizzato().toString();

            societa = societaDAO.leggiSocietaAddebitoProforma(idEntita);
            if(societa != null)
                societaAddebito = societa.getNome();

            mailTo = societa.getEmailAmministrazione();

            logger.debug("Societ� addebito: "+societaAddebito+ " Email: "+mailTo);

            nomeDocumento=nomeDocumento+studioLegaleDesc;

            mailSubject = String.format("%s - %s - %s",nomeDocumento, anagraficaStatiDAO.leggiStatoProforma( proforma.getStatoProforma().getCodGruppoLingua(), lang.toUpperCase()).getDescrizione(), df.format(step.getDataChiusura()));
            nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_PROFORMA, lang.toLowerCase(), CostantiDAO.MAIL_SOCIETA);


            mail.setMailTo(mailTo);
            mail.setMailCc(destParcelle);
            mail.setNomeTemplate(nomeTemplate);
            mail.setMailSubject(mailSubject);
            mail.setFascicoloRiferimento(fascicoloRiferimento);
            mail.setLinkFascicoloRiferimento(linkFascicoloRiferimento);
            mail.setTotaleAutorizzato(totaleAutorizzato);
            mail.setSocietaAddebito(societaAddebito);
            mail.setTipoAllegatoEn(TIPO_ALLEGATO_EN);
            mail.setTipoAllegatoIt(TIPO_ALLEGATO_IT);
            mail.setNomeDocumento(nomeDocumento);
            mail.setLinkAlDocumento(linkAlDocumento);
            mail.setMailFrom(PARCELLE_LEGALI_PAGABILI);

            if(!mailTo.isEmpty())
                inviaEmail(mail);

            proformaDAO.inserisciDataInvioAmministrativo(idEntita, new Date());

            if (logger.isDebugEnabled()) {
                logger.debug("inviaNotificaAmministrativoSocieta(long, String, String) - end"); //$NON-NLS-1$
            }
        }
    }

    public void inviaNotificaProfessionistaEsterno(long idEntita, String lang, String userIdUtenteConnesso ) throws Throwable {

        Proforma proforma = null;
        proforma = proformaDAO.leggi(idEntita);
        boolean invioMailConLink = false;

        if(proforma.getDaProfEsterno() != null){

            if(proforma.getDaProfEsterno().equalsIgnoreCase("T")){
                invioMailConLink = true;
            }
        }else{
            invioMailConLink = false;
        }



        if (logger.isDebugEnabled()) {
            logger.debug("inviaNotificaProfessionistaEsterno(long, String, String) - start"); //$NON-NLS-1$
        }

        //variabili di mapping all'entit�
        String mailTo = StringUtils.EMPTY;
        String mailCc = StringUtils.EMPTY;
        String nomeTemplate = StringUtils.EMPTY;
        String mailSubject = StringUtils.EMPTY;
        String nomeDocumento = StringUtils.EMPTY;
        String fascicoloRiferimento = StringUtils.EMPTY;
        String societaAddebito = StringUtils.EMPTY;
        String studioLegale = StringUtils.EMPTY;
        String linkAlDocumentoLegaliEsterni = StringUtils.EMPTY;

        //variabili di appoggio
        String webUrl_Portale_Esterno =  StringUtils.EMPTY;
        String totaleAutorizzato = StringUtils.EMPTY;
        Societa societa = null;
        ProfessionistaEsterno professionista = null;
        StudioLegale studio = null;

        InputStream is = null;
        try {
            is = EmailNotificationServiceImpl.class.getResourceAsStream("/config.properties");
            Properties properties = new Properties();
            properties.load(is);
            PARCELLE_LEGALI_PAGABILI=properties.getProperty("PARCELLE_LEGALI_PAGABILI");
            webUrl_Portale_Esterno= properties.getProperty("webUrl_Portale_Esertno");
            MITTENTE_STANDARD=properties.getProperty("MITTENTE_STANDARD");
        }
        catch (Exception e) {
            logger.error("inviaNotificaProfessionistaEsterno(long, String, String)", e); //$NON-NLS-1$

            System.out.println("Properties non lette: "+ e);
        } 
        //        ConfigurazioneStepWf configurazioneTradotta = null;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); // creo l'oggetto

        Mail mail = new Mail();

        if(proforma.getTotaleAutorizzato() != null)
            totaleAutorizzato = proforma.getTotaleAutorizzato().toString();

        societa = societaDAO.leggiSocietaAddebitoProforma(idEntita);
        if(societa != null)
            societaAddebito = societa.getNome();

        nomeDocumento = proforma.getNomeProforma();

        //recupero l'incarico associato
        Incarico incaricoAssociato = incaricoDAO.leggiIncaricoAssociatoAProforma(idEntita);

        fascicoloRiferimento = incaricoAssociato.getFascicolo().getNome();

        linkAlDocumentoLegaliEsterni= String.format("%s%s%s%d%s%s%s%s%s",PREF_HREF, webUrl_Portale_Esterno,"entryPLE.jsp?f=dp&id=",idEntita, SUFF_HREF, INI_BOLD, PORTALE_LEGALI_ESTERNI, FINE_BOLD,FINE_HREF);

        //recupero il professionista esterno
        professionista = incaricoAssociato.getProfessionistaEsterno();

        //recupero lo studio legale del professionista
        studio = professionista.getStudioLegale();

        if(studio != null)
            studioLegale = studio.getDenominazione();

        //invio la e-mail al professionista e allo studio legale
        if(professionista.getEmail() != null && !professionista.getEmail().isEmpty())
            mailTo = professionista.getEmail() + ",";
        if(studio.getEmail() != null && !studio.getEmail().isEmpty())
            mailTo = mailTo + studio.getEmail();


        String mailTemplate = "";

        if(invioMailConLink){
            mailTemplate = CostantiDAO.MAIL_LEGALE;
        }
        else
            mailTemplate = CostantiDAO.MAIL_LEGALE_NO_LINK;

        mailSubject = String.format("%s - %s - %s",nomeDocumento, anagraficaStatiDAO.leggiStatoProforma( proforma.getStatoProforma().getCodGruppoLingua(), lang.toUpperCase()).getDescrizione(), df.format(new Date()));
        nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_PROFORMA, lang.toLowerCase(), mailTemplate);

        mailCc = MITTENTE_STANDARD;

        mail.setMailTo(mailTo);
        mail.setMailCc(mailCc);

        mail.setTotaleAutorizzato(totaleAutorizzato);
        mail.setFascicoloRiferimento(fascicoloRiferimento);
        mail.setLinkAlDocumentoLegaliEsterni(linkAlDocumentoLegaliEsterni);
        mail.setSocietaAddebito(societaAddebito);
        mail.setStudioLegale(studioLegale);
        mail.setNomeTemplate(nomeTemplate);
        mail.setMailSubject(mailSubject);
        mail.setNomeDocumento(nomeDocumento);
        mail.setMailFrom(PARCELLE_LEGALI_PAGABILI);


        //ALLEGATO

        Fascicolo fascicolo = incaricoAssociato.getFascicolo();

        String nomeCartellaProforma = FileNetUtil.getProformaCartella(proforma.getId(), fascicolo.getDataCreazione(),
                fascicolo.getNome(), proforma.getNomeProforma());


        logger.debug("nomeCartella: "+nomeCartellaProforma);

        boolean isPenale = fascicolo.getSettoreGiuridico().getCodGruppoLingua().equals(Costanti.SETTORE_GIURIDICO_PENALE_CODE);

        /*@@DDS
        DocumentaleDAO documentaleDAO = (DocumentaleDAO) SpringUtil.getBean("documentaleDAO");
        DocumentaleCryptDAO documentaleCryptDAO = (DocumentaleCryptDAO) SpringUtil.getBean("documentaleCryptDAO");
        Folder cartellaIncarico = isPenale ? documentaleCryptDAO.leggiCartella(nomeCartellaProforma)
                : documentaleDAO.leggiCartella(nomeCartellaProforma);
           */

        DocumentaleDdsDAO documentaleDdsDAO = (DocumentaleDdsDAO) SpringUtil.getBean("documentaleDdsDAO");
        DocumentaleDdsCryptDAO documentaleDdsCryptDAO = (DocumentaleDdsCryptDAO) SpringUtil.getBean("documentaleDdsCryptDAO");

        /*@@DDS inizio commento
        DocumentSet documenti = cartellaIncarico.get_ContainedDocuments();
        byte[] bytes = null;
        BASE64DecodedMultipartFile allegato = null;

        if (documenti != null) {
            PageIterator it = documenti.pageIterator();
            while (it.nextPage()) {
                EngineObjectImpl[] documentiArray = (EngineObjectImpl[]) it.getCurrentPage();
                for (EngineObjectImpl objDocumento : documentiArray) {
                    DocumentImpl documento = (DocumentImpl) objDocumento;

                    if(documento.getClassName().equals(FileNetClassNames.ALLEGATO_DOCUMENT)){

                        if(!isPenale)
                            bytes = documentaleDAO.leggiContenutoDocumento(documento);
                        else
                            bytes = documentaleCryptDAO.leggiContenutoDocumento(documento);


                        allegato = new BASE64DecodedMultipartFile(bytes, documento.get_Name(), documento.get_Name(), documento.get_MimeType());
                    }
                }
            }



        }
 */
        List<Document> documenti = isPenale ? documentaleDdsCryptDAO.leggiDocumentiCartella(nomeCartellaProforma)
                : documentaleDdsDAO.leggiDocumentiCartella(nomeCartellaProforma);
        byte[] bytes = null;
        BASE64DecodedMultipartFile allegato = null;

        if (documenti != null) {
                for (Document documento:documenti) {


                    if(documento.getDocumentalClass().equals(FileNetClassNames.ALLEGATO_DOCUMENT)){

                        if(!isPenale)
                            bytes = documentaleDdsDAO.leggiContenutoDocumento(documento);
                        else
                            bytes = documentaleDdsCryptDAO.leggiContenutoDocumento(documento);


                        allegato = new BASE64DecodedMultipartFile(bytes, documento.getContents().get(0).getContentsName(), documento.getContents().get(0).getContentsName(), documento.getContents().get(0).getContentsMimeType());
                    }
                }

        }
        if(!mailTo.isEmpty() )
            logger.info("INVIO MAIL: "+mail.toString());

        if(allegato!=null)
            inviaEmailConAllegato(mail, allegato);
        else
            inviaEmail(mail);

        if (logger.isDebugEnabled()) {
            logger.debug("inviaNotificaProfessionistaEsterno(long, String, String) - end"); //$NON-NLS-1$
        }
    }



    public void inviaNotificaAmministrativo(long idEntita, String lang, String userIdUtenteConnesso ) throws Throwable {
        if (logger.isDebugEnabled()) {
            logger.debug("inviaNotificaAmministrativo(long, String, String) - start"); //$NON-NLS-1$
        }

        //variabili di mapping all'entit�
        String mailTo = StringUtils.EMPTY;
        String nomeTemplate = StringUtils.EMPTY;
        String mailSubject = StringUtils.EMPTY;
        String nomeDocumento = StringUtils.EMPTY;
        String fascicoloRiferimento = StringUtils.EMPTY;
        String linkFascicoloRiferimento = StringUtils.EMPTY;
        String totaleAutorizzato = StringUtils.EMPTY;
        String societaAddebito = StringUtils.EMPTY;
        String linkAlDocumento = StringUtils.EMPTY;
        String studioLegaleDesc = StringUtils.EMPTY;
        String professionistaEsternoDesc = StringUtils.EMPTY;

        //variabili di appoggio
        //        String codiceLingua = StringUtils.EMPTY;
        String webUrl = StringUtils.EMPTY;
        String destParcelle = StringUtils.EMPTY;
        //        String destInvioAltriUffici = StringUtils.EMPTY;
        //        String destIncaricoAutorizzato = StringUtils.EMPTY;

        StepWf step = null;
        //        ConfigurazioneStepWf configurazioneTradotta = null;
        DateFormat df = new SimpleDateFormat("dd-MMMM-yyyy HH:mm:ss"); 
        Societa societa = null;

        // creo l'oggetto
        Mail mail = new Mail();

        InputStream is = null;
        try {
            is = EmailNotificationServiceImpl.class.getResourceAsStream("/config.properties");
            Properties properties = new Properties();
            properties.load(is);
            webUrl = properties.getProperty("webUrl");
            destParcelle = properties.getProperty("destParcelle");
            //            destInvioAltriUffici=properties.getProperty("destInvioAltriUffici");
            //            destIncaricoAutorizzato=properties.getProperty("destIncaricoAutorizzato");
            PARCELLE_LEGALI_PAGABILI=properties.getProperty("PARCELLE_LEGALI_PAGABILI");
            MITTENTE_STANDARD=properties.getProperty("MITTENTE_STANDARD");
        }
        catch (Exception e) {
            logger.error("inviaNotificaAmministrativo(long, String, String)", e); //$NON-NLS-1$

            System.out.println("Properties non lette: "+ e);
        } 



        Proforma proforma = null;
        ProformaWf proformaWf = null;
        proforma = proformaDAO.leggi(idEntita);
        nomeDocumento = proforma.getNomeProforma();
        linkAlDocumento = String.format("%s%s%s%d%s%s%s%s%s",PREF_HREF, webUrl,SUFF_URL_PROFORMA_AMMINISTRATIVI,idEntita, SUFF_HREF, INI_BOLD, nomeDocumento, FINE_BOLD, FINE_HREF);

        proformaWf = proformaWfDAO.leggiUltimoWorkflowTerminato(idEntita);
        step = stepWfDAO.leggiUltimoStepChiuso(proformaWf.getId(), CostantiDAO.AUTORIZZAZIONE_PROFORMA);
        //        codiceLingua = step.getConfigurazioneStepWf().getCodGruppoLingua();
        //        configurazioneTradotta = configurazioneStepWfDAO.leggiConfigurazioneTradotta(codiceLingua, lang);

        //recupero l'incarico associato
        Incarico incaricoAssociato = incaricoDAO.leggiIncaricoAssociatoAProforma(idEntita);

        //MS--Fix
        ProfessionistaEsterno pEsterno=null;
        StudioLegale studioLegale=null;

        pEsterno= professionistaDAO.leggi(incaricoAssociato.getProfessionistaEsterno().getId()); 

        if(pEsterno!=null){
            professionistaEsternoDesc=pEsterno.getNome() +" "+ pEsterno.getCognome() +" - "+pEsterno.getCodiceFiscale(); 
            studioLegale=pEsterno.getStudioLegale();
            studioLegaleDesc=" - Studio Legale: "+professionistaEsternoDesc;
        } 

        if(studioLegale!=null)
            studioLegaleDesc=" - Studio Legale: "+studioLegale.getDenominazione();
        //----

        //ricavo il link al fascicolo di riferimento
        //    linkFascicoloRiferimento = String.format("%s%s%d", webUrl,SUFF_URL_FASCICOLO,incaricoAssociato.getFascicolo().getId());

        linkFascicoloRiferimento = String.format("%s%s%s%d%s%s%s%s%s",PREF_HREF, webUrl,SUFF_URL_FASCICOLO,incaricoAssociato.getFascicolo().getId(), SUFF_HREF, INI_BOLD, incaricoAssociato.getFascicolo().getNome(), FINE_BOLD, FINE_HREF);

        //ricavo il fascicolo di riferimento
        fascicoloRiferimento = incaricoAssociato.getFascicolo().getNome();
        logger.info("fascicoloRiferimento: " +fascicoloRiferimento);


        if(proforma.getTotaleAutorizzato() != null)
            totaleAutorizzato = proforma.getTotaleAutorizzato().toString();

        societa = societaDAO.leggiSocietaAddebitoProforma(idEntita);
        if(societa != null)
            societaAddebito = societa.getNome();

        //recupero la lista degli amministrativi
        List<Utente> listaAmministrativi = utenteDAO.leggiUtentiAmministrativi();

        //invio la e-mail a tutti gli amministrativi
        for( Utente utente : listaAmministrativi ){
            if(utente.getEmailUtil() != null && !utente.getEmailUtil().isEmpty())
                mailTo = mailTo + utente.getEmailUtil() + ",";
        }

        nomeDocumento=nomeDocumento+studioLegaleDesc;

        mailSubject = String.format("%s - %s - %s",nomeDocumento, anagraficaStatiDAO.leggiStatoProforma( proforma.getStatoProforma().getCodGruppoLingua(), lang.toUpperCase()).getDescrizione(), df.format(step.getDataChiusura()));
        nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_PROFORMA, lang.toLowerCase(), CostantiDAO.MAIL_SOCIETA);


        mail.setMailTo(mailTo);
        mail.setMailCc(destParcelle);
        mail.setNomeTemplate(nomeTemplate);
        mail.setMailSubject(mailSubject);
        mail.setFascicoloRiferimento(fascicoloRiferimento);
        mail.setLinkFascicoloRiferimento(linkFascicoloRiferimento);
        mail.setTotaleAutorizzato(totaleAutorizzato);
        mail.setSocietaAddebito(societaAddebito);
        mail.setTipoAllegatoEn(TIPO_ALLEGATO_EN);
        mail.setTipoAllegatoIt(TIPO_ALLEGATO_IT);
        mail.setNomeDocumento(nomeDocumento);
        mail.setLinkAlDocumento(linkAlDocumento);
        mail.setMailFrom(PARCELLE_LEGALI_PAGABILI);

        if(!mailTo.isEmpty())
            inviaEmail(mail);

        if (logger.isDebugEnabled()) {
            logger.debug("inviaNotificaAmministrativo(long, String, String) - end"); //$NON-NLS-1$
        }
    }

    public void inviaNotificaBeautyContestProfessionistaEsterno(long idEntita, String lang) throws Throwable {

        BeautyContest beautyContest = null;
        beautyContest = beautyContestDAO.leggi(idEntita);

        if (logger.isDebugEnabled()) {
            logger.debug("inviaNotificaBeautyContestProfessionistaEsterno(long, String) - start"); //$NON-NLS-1$
        }

        //variabili di mapping all'entit�
        String mailTo = StringUtils.EMPTY;
        String mailCc = StringUtils.EMPTY;
        String nomeTemplate = StringUtils.EMPTY;
        String mailSubject = StringUtils.EMPTY;
        String nomeDocumento = StringUtils.EMPTY;
        String linkAlDocumentoLegaliEsterni = StringUtils.EMPTY;

        String webUrl_Portale_Esterno =  StringUtils.EMPTY;
        List<ProfessionistaEsterno> candidati = new ArrayList<ProfessionistaEsterno>();
        StudioLegale studio = null;

        InputStream is = null;
        try {
            is = EmailNotificationServiceImpl.class.getResourceAsStream("/config.properties");
            Properties properties = new Properties();
            properties.load(is);
            webUrl_Portale_Esterno= properties.getProperty("webUrl_Portale_Esertno");
            MITTENTE_STANDARD=properties.getProperty("MITTENTE_STANDARD");
        }
        catch (Exception e) {
            logger.error("inviaNotificaBeautyContestProfessionistaEsterno(long, String)", e); //$NON-NLS-1$

            System.out.println("Properties non lette: "+ e);
        } 

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); // creo l'oggetto

        Mail mail = new Mail();

        nomeDocumento = beautyContest.getTitolo();

        linkAlDocumentoLegaliEsterni= String.format("%s%s%s%d%s%s%s%s%s",PREF_HREF, webUrl_Portale_Esterno, "beautyContestReply/rispondi.action?id=" , idEntita , SUFF_HREF, INI_BOLD, PORTALE_LEGALI_ESTERNI, FINE_BOLD,FINE_HREF);

        //recupero i professionisti esterni candidati
        if (beautyContest.getRBeautyContestProfessionistaEsternos() != null) {

            Collection<RBeautyContestProfessionistaEsterno> listaProfessionisti = beautyContest.getRBeautyContestProfessionistaEsternos();

            if(listaProfessionisti != null && !listaProfessionisti.isEmpty()){

                for (RBeautyContestProfessionistaEsterno professionista : listaProfessionisti) {

                    candidati.add(professionista.getProfessionistaEsterno());
                }
            }
        }

        if(candidati !=null && !candidati.isEmpty()){

            for(ProfessionistaEsterno candidato : candidati){

                //recupero lo studio legale del professionista
                studio = candidato.getStudioLegale();

                //invio la e-mail al professionista e allo studio legale
                if(candidato.getEmail() != null && !candidato.getEmail().isEmpty())
                    mailTo = mailTo + candidato.getEmail() + ",";
                if(studio.getEmail() != null && !studio.getEmail().isEmpty())
                   mailTo = mailTo + studio.getEmail();
            }
        }

        String bcSow = "";

        if(beautyContest.getDescrizione_sow() != null && !beautyContest.getDescrizione_sow().isEmpty()){

            bcSow = beautyContest.getDescrizione_sow();
        }

        String mailTemplate = CostantiDAO.MAIL_LEGALE_BEAUTY_CONTEST;

        mailSubject = String.format("%s - data apertura: %s - data chiusura %s", nomeDocumento, df.format(beautyContest.getDataEmissione()), df.format(beautyContest.getDataChiusura()));
        nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_BEAUTY_CONTEST, lang.toLowerCase(), mailTemplate);
        mailCc = MITTENTE_STANDARD;

        mail.setMailTo(mailTo);
        mail.setMailCc(mailCc);
        mail.setLinkAlDocumentoLegaliEsterni(linkAlDocumentoLegaliEsterni);
        mail.setNomeTemplate(nomeTemplate);
        mail.setMailSubject(mailSubject);
        mail.setNomeDocumento(nomeDocumento);
        mail.setBcSow(bcSow);
        mail.setMailFrom(MITTENTE_STANDARD);

        if(!mailTo.isEmpty() )
            logger.info("INVIO MAIL: "+mail.toString());

        inviaEmail(mail);

        if (logger.isDebugEnabled()) {
            logger.debug("inviaNotificaBeautyContestProfessionistaEsterno(long, String) - end"); //$NON-NLS-1$
        }
    }

    public void inviaNotificaBCVincitoreSelezionato(String lang, long idEntita) throws Throwable {

        BeautyContest beautyContest = null;
        beautyContest = beautyContestDAO.leggi(idEntita);

        if (logger.isDebugEnabled()) {
            logger.debug("inviaNotificaBCVincitoreSelezionato(String, long) - start");
        }

        //variabili di mapping all'entit�
        String mailTo = StringUtils.EMPTY;
        String mailCc = StringUtils.EMPTY;
        String nomeTemplate = StringUtils.EMPTY;
        String mailSubject = StringUtils.EMPTY;
        String nomeDocumento = StringUtils.EMPTY;
        String linkAlDocumentoLegaliEsterni = StringUtils.EMPTY;

        String webUrl_Portale_Esterno =  StringUtils.EMPTY;
        InputStream is = null;
        try {
            is = EmailNotificationServiceImpl.class.getResourceAsStream("/config.properties");
            Properties properties = new Properties();
            properties.load(is);
            webUrl_Portale_Esterno= properties.getProperty("webUrl_Portale_Esertno");
            MITTENTE_STANDARD=properties.getProperty("MITTENTE_STANDARD");
        }
        catch (Exception e) {
            logger.error("inviaNotificaBCVincitoreSelezionato(String, long)", e); //$NON-NLS-1$

            System.out.println("Properties non lette: "+ e);
        } 

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); // creo l'oggetto

        Mail mail = new Mail();

        nomeDocumento = beautyContest.getTitolo();

        linkAlDocumentoLegaliEsterni= String.format("%s%s%s%d%s%s%s%s%s",PREF_HREF, webUrl_Portale_Esterno, "beautyContestReply/rispondi.action?id=" , idEntita , SUFF_HREF, INI_BOLD, PORTALE_LEGALI_ESTERNI, FINE_BOLD,FINE_HREF);

        
        if(beautyContest.getVincitore() != null){
            
            ProfessionistaEsterno vincitore = beautyContest.getVincitore();
    
            //invio la e-mail al professionista vincitore
            if(vincitore.getEmail() != null && !vincitore.getEmail().isEmpty())
                mailTo = vincitore.getEmail();
        }
        
        String bcSow = "";

        if(beautyContest.getDescrizione_sow() != null && !beautyContest.getDescrizione_sow().isEmpty()){

            bcSow = beautyContest.getDescrizione_sow();
        }

        String mailTemplate = CostantiDAO.MAIL_LEGALE_VINCITORE_BEAUTY_CONTEST;

        mailSubject = String.format("%s - data apertura: %s - data chiusura %s", nomeDocumento, df.format(beautyContest.getDataEmissione()), df.format(beautyContest.getDataChiusura()));
        nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_BEAUTY_CONTEST, lang.toLowerCase(), mailTemplate);
        mailCc = MITTENTE_STANDARD;

        mail.setMailTo(mailTo);
        mail.setMailCc(mailCc);
        mail.setLinkAlDocumentoLegaliEsterni(linkAlDocumentoLegaliEsterni);
        mail.setNomeTemplate(nomeTemplate);
        mail.setMailSubject(mailSubject);
        mail.setNomeDocumento(nomeDocumento);
        mail.setBcSow(bcSow);
        mail.setMailFrom(MITTENTE_STANDARD);

        if(!mailTo.isEmpty() )
            logger.info("INVIO MAIL: "+mail.toString());

        inviaEmail(mail);

        if (logger.isDebugEnabled()) {
            logger.debug("inviaNotificaBCVincitoreSelezionato(String, long) - end"); //$NON-NLS-1$
        }
    }

    public void sendNotification(final Mail mail) {
        if (logger.isDebugEnabled()) {
            logger.debug("sendNotification(Mail) - start"); //$NON-NLS-1$
        }

        final MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws MessagingException, UnsupportedEncodingException  {
                if (logger.isDebugEnabled()) {
                    logger.debug("$MimeMessagePreparator.prepare(MimeMessage) - start"); //$NON-NLS-1$
                }

                //MimeMessageHelper message = new MimeMessageHelper( mimeMessage);
                //message.setTo("silvana.diperna@eng.it");
                //message.setBcc("s.diperna@libero.it");
                //message.setFrom(new InternetAddress("s.diperna@libero.it"));
                MimeMessageHelper message = new MimeMessageHelper( mimeMessage);
                message.setTo("legarc2018@gmail.com");
                message.setBcc("legarc2018@gmail.com");
                message.setFrom(new InternetAddress("legarc2018@gmail.com"));
                message.setSubject("New mail");
                message.setSentDate(new Date());
                Map<String, Object> model = new HashMap<String, Object>();
                model.put("newMessage", mail);

                String text = VelocityEngineUtils.mergeTemplateIntoString(
                        velocityEngine, "templateMail/testMailMessage.vm", "UTF-8", model);
                message.setText(text, true);

                if (logger.isDebugEnabled()) {
                    logger.debug("$MimeMessagePreparator.prepare(MimeMessage) - end"); //$NON-NLS-1$
                }
            }
        };
        mailSender.send(preparator);

        if (logger.isDebugEnabled()) {
            logger.debug("sendNotification(Mail) - end"); //$NON-NLS-1$
        }
    }

    public void inviaEmail(final Mail mail) {
        if (logger.isDebugEnabled()) {
            logger.debug("inviaEmail("+mail+") - start"); //$NON-NLS-1$
        }

        final MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws MessagingException, UnsupportedEncodingException  {
                if (logger.isDebugEnabled()) {
                    logger.debug("$MimeMessagePreparator.prepare(MimeMessage) - start"); //$NON-NLS-1$
                }

                MimeMessageHelper message = new MimeMessageHelper( mimeMessage);
                if(!mail.getMailTo().isEmpty()){
                    String[] To = mail.getMailTo().split(",");
                    message.setTo(To[0]);
                    if(To.length > 1)
                        for (int i = 1; i < To.length; i++) 
                            message.addTo(To[i]);

                }
                if(!mail.getMailCc().isEmpty()){
                    String[] Cc = mail.getMailCc().split(",");
                    message.setCc(Cc[0]);
                    if(Cc.length > 1)
                       for (int i = 1; i < Cc.length; i++) 
                            message.addCc(Cc[i]);

                }

                //                if(!mail.getMailBcc().isEmpty()){
                //                    String[] Cc = mail.getMailBcc().split(",");
                //                    message.setBcc(Cc[0]);
                //                    if(Cc.length > 1)
                //                        for (int i = 1; i < Cc.length; i++) 
                //                            message.addBcc(Cc[i]);
                //
                //                }

                message.setFrom(new InternetAddress(mail.getMailFrom()));
                /*String to = mail.getMailTo();
                     String Cc = mail.getMailCc();*/


                /*message.setTo("silvana.diperna@eng.it");
                     message.setCc("s.diperna@libero.it");
                     message.setBcc("FrancescoPaolo.Corsano@eng.it");
                     message.addBcc("silvana.diperna@eng.it");*/

                //message.setFrom(new InternetAddress("s.diperna@libero.it"));

                message.setSubject(mail.getMailSubject());
                message.setSentDate(new Date());
                Map<String, Object> model = new HashMap<String, Object>();
                model.put("newMessage", mail);

                String text = VelocityEngineUtils.mergeTemplateIntoString(
                        velocityEngine, mail.getNomeTemplate(), "UTF-8", model);
                message.setText(text, true);

                if (logger.isDebugEnabled()) {
                    logger.debug("$MimeMessagePreparator.prepare(MimeMessage) - end"); //$NON-NLS-1$
                }
            }
        };
        try{
            mailSender.send(preparator);
        }
        catch(Exception e){
            logger.debug("Eccezione: " + e.getMessage()); //$NON-NLS-1$
        }

        if (logger.isDebugEnabled()) {
            logger.debug("inviaEmail(Mail) - end"); //$NON-NLS-1$
        }
    }

    public void inviaEmailConAllegato(final Mail mail, MultipartFile file) {
        if (logger.isDebugEnabled()) {
            logger.debug("inviaEmailConAllegato(Mail) - start"); //$NON-NLS-1$
        }

        final MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws MessagingException, UnsupportedEncodingException  {
                if (logger.isDebugEnabled()) {
                    logger.debug("$MimeMessagePreparator.prepare(MimeMessage) - start"); //$NON-NLS-1$
                }

                MimeMessageHelper message = new MimeMessageHelper( mimeMessage,true);
                if(!mail.getMailTo().isEmpty()){
                    String[] To = mail.getMailTo().split(",");
                    message.setTo(To[0]);
                    if(To.length > 1)
                        for (int i = 1; i < To.length; i++) 
                            message.addTo(To[i]);

                }
                if(!mail.getMailCc().isEmpty()){
                    String[] Cc = mail.getMailCc().split(",");
                    message.setCc(Cc[0]);
                    if(Cc.length > 1)
                        for (int i = 1; i < Cc.length; i++) 
                            message.addCc(Cc[i]);

                }

                message.setFrom(new InternetAddress(mail.getMailFrom()));
                /*String to = mail.getMailTo();
                     String Cc = mail.getMailCc();*/


                /*message.setTo("silvana.diperna@eng.it");
                     message.setCc("s.diperna@libero.it");
                     message.setBcc("FrancescoPaolo.Corsano@eng.it");
                     message.addBcc("silvana.diperna@eng.it");*/

                //message.setFrom(new InternetAddress("s.diperna@libero.it"));

                message.setSubject(mail.getMailSubject());
                message.setSentDate(new Date());
                Map<String, Object> model = new HashMap<String, Object>();
                model.put("newMessage", mail);

                String attachName = file.getOriginalFilename();
                if (!file.equals("")) {

                    message.addAttachment(attachName, new InputStreamSource() {

                        @Override
                        public InputStream getInputStream() throws IOException {
                            return file.getInputStream();
                        }
                    });
                }

                String text = VelocityEngineUtils.mergeTemplateIntoString(
                        velocityEngine, mail.getNomeTemplate(), "UTF-8", model);
                message.setText(text, true);

                if (logger.isDebugEnabled()) {
                    logger.debug("$MimeMessagePreparator.prepare(MimeMessage) - end"); //$NON-NLS-1$
                }
            }
        };
        try{
            mailSender.send(preparator);
        }
        catch(Exception e){
            e.printStackTrace(); //$NON-NLS-1$
        }

        if (logger.isDebugEnabled()) {
            logger.debug("inviaEmail(Mail) - end"); //$NON-NLS-1$
        }
    }


    @Async
    public void inviaNotificaInvioAltriUffici(long idEntita, String lang, String userIdUtenteConnesso,MultipartFile file ) throws Throwable {
        if (logger.isDebugEnabled()) {
            logger.debug("inviaNotificaInvioAltriUffici(long, String, String) - start"); //$NON-NLS-1$
        }

        //variabili di mapping all'unit�
        String mailTo = StringUtils.EMPTY;
        String mailCc = StringUtils.EMPTY;
        String nomeTemplate = StringUtils.EMPTY;
        String mailSubject = StringUtils.EMPTY;
        String nomeDocumento = StringUtils.EMPTY;
        String fascicoloRiferimento = StringUtils.EMPTY;

        //variabili di appoggio
        Utente utenteConnesso = null;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); // creo l'oggetto

        Mail mail = new Mail();

        InputStream is = null;
        try {
            is = EmailNotificationServiceImpl.class.getResourceAsStream("/config.properties");
            Properties properties = new Properties();
            properties.load(is);
            mailCc = properties.getProperty("destInvioAltriUffici");
            PARCELLE_LEGALI_PAGABILI=properties.getProperty("PARCELLE_LEGALI_PAGABILI");
            MITTENTE_STANDARD=properties.getProperty("MITTENTE_STANDARD");
        }
        catch (Exception e) {
            logger.error("inviaNotificaInvioAltriUffici(long, String, String)", e); //$NON-NLS-1$

            System.out.println("Properties non lette: "+ e);
        } 

        //recupero l'utente connesso
        utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
        if(utenteConnesso.getEmailUtil() != null && !utenteConnesso.getEmailUtil().isEmpty())
            mailCc = mailCc + "," + utenteConnesso.getEmailUtil();

        //recupero l'atto
        Atto atto = null;
        atto = attoDAO.leggi(idEntita);

        //ricavo il fascicolo di riferimento
        fascicoloRiferimento = atto.getFascicolo().getNome();
        logger.info("fascicoloRiferimento: " +fascicoloRiferimento);

        mailTo = atto.getEmailInvioAltriUffici();

        nomeDocumento = CostantiDAO.ATTO + " " +  atto.getNumeroProtocollo();

        StatoAtto stato = anagraficaStatiDAO.leggiStatoAtto(atto.getStatoAtto().getCodGruppoLingua(), lang);


        mailSubject = String.format("%s - %s - %s",nomeDocumento, stato.getDescrizione(), df.format(atto.getDataUltimaModifica()));
        nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_ATTO, lang.toLowerCase(), stato.getCodGruppoLingua().toLowerCase());


        mail.setMailTo(mailTo);
        mail.setMailCc(mailCc);
        mail.setNomeTemplate(nomeTemplate);
        mail.setMailSubject(mailSubject);
        mail.setNomeDocumento(nomeDocumento);
        mail.setMailFrom(MITTENTE_STANDARD);
        mail.setFascicoloRiferimento(fascicoloRiferimento);


        if(!mailTo.isEmpty() || ! mailCc.isEmpty()){
            if(file==null)
                inviaEmail(mail); 
            else
                inviaEmailConAllegato(mail,file);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("inviaNotificaInvioAltriUffici(long, String, String) - end"); //$NON-NLS-1$
        }
    }


    public void inviaEmailConAllegatoDocument(final Mail mail, Document documento) {
        if (logger.isDebugEnabled()) {
            logger.debug("inviaEmailConAllegatoDocument(Mail, Document) - start"); //$NON-NLS-1$
        }

        final MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws MessagingException, UnsupportedEncodingException  {
                if (logger.isDebugEnabled()) {
                    logger.debug("$MimeMessagePreparator.prepare(MimeMessage) - start"); //$NON-NLS-1$
                }

                MimeMessageHelper message = new MimeMessageHelper( mimeMessage,true);
                if(!mail.getMailTo().isEmpty()){
                    String[] To = mail.getMailTo().split(",");
                    message.setTo(To[0]);
                    if(To.length > 1)
                        for (int i = 1; i < To.length; i++) 
                            message.addTo(To[i]);

                }
                if(!mail.getMailCc().isEmpty()){
                    String[] Cc = mail.getMailCc().split(",");
                    message.setCc(Cc[0]);
                    if(Cc.length > 1)
                        for (int i = 1; i < Cc.length; i++) 
                            message.addCc(Cc[i]);

                }

                message.setFrom(new InternetAddress(mail.getMailFrom()));

                message.setSubject(mail.getMailSubject());
                message.setSentDate(new Date());
                Map<String, Object> model = new HashMap<String, Object>();
                model.put("newMessage", mail);

                if (documento!=null) {
                    //@@DDS String attachName = documento.get_Name();
                    String attachName = documento.getContents().get(0).getContentsName();

                    byte[] contenuto = null;
                    try {
                        //@@DDS contenuto = documentaleCryptDAO.leggiContenutoDocumento(documento);
                        contenuto = documentaleDdsCryptDAO.leggiContenutoDocumento(documento);
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }

                    //@@DDS DataSource dataSource = new ByteArrayDataSource(contenuto, documento.get_MimeType());
                    DataSource dataSource = new ByteArrayDataSource(contenuto, documento.getContents().get(0).getContentsMimeType());
                    message.addAttachment(attachName, dataSource);
                }

                String text = VelocityEngineUtils.mergeTemplateIntoString(
                        velocityEngine, mail.getNomeTemplate(), "UTF-8", model);
                message.setText(text, true);

                if (logger.isDebugEnabled()) {
                    logger.debug("$MimeMessagePreparator.prepare(MimeMessage) - end"); //$NON-NLS-1$
                }
            }
        };
        try{
            mailSender.send(preparator);
        }
        catch(Exception e){
            e.printStackTrace(); //$NON-NLS-1$
        }

        if (logger.isDebugEnabled()) {
            logger.debug("inviaEmailConAllegatoDocument(Mail, Document) - end"); //$NON-NLS-1$
        }
    }

    public void inviaNotificaInvioAltriUfficiPec(UtentePecView utentePecView, String lang, String userIdUtenteConnesso, String utenteAltriUff, String emailAltriUff) throws Throwable{
        if (logger.isDebugEnabled()) {
            logger.debug("inviaNotificaInvioAltriUfficiPec(UtentePecView, String, String, String, String) - start"); //$NON-NLS-1$
        }

        //variabili di mapping all'unit�
        String mailTo = StringUtils.EMPTY;
        String mailCc = StringUtils.EMPTY;
        String nomeTemplate = StringUtils.EMPTY;
        String mailSubject = StringUtils.EMPTY;
        String nomeDocumento = StringUtils.EMPTY;

        //variabili di appoggio
        Utente utenteConnesso = null;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); // creo l'oggetto

        Mail mail = new Mail();

        InputStream is = null;
        try {
            is = EmailNotificationServiceImpl.class.getResourceAsStream("/config.properties");
            Properties properties = new Properties();
            properties.load(is);
            //mailCc = properties.getProperty("destInvioAltriUfficiPec");
            MITTENTE_STANDARD=properties.getProperty("MITTENTE_STANDARD");
        }
        catch (Exception e) {
            logger.error("inviaNotificaInvioAltriUfficiPec(UtentePecView, String, String, String, String)", e); //$NON-NLS-1$

            System.out.println("Properties non lette: "+ e);
        } 

        //recupero l'utente connesso
        utenteConnesso = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
        if(utenteConnesso.getEmailUtil() != null && !utenteConnesso.getEmailUtil().isEmpty()){
            //mailCc = mailCc + "," + utenteConnesso.getEmailUtil();
            mailCc = utenteConnesso.getEmailUtil();
        }

        mailTo = emailAltriUff;

        nomeDocumento = "Email Pec";

        mailSubject = String.format("%s - %s",nomeDocumento, df.format(utentePecView.getVo().getData()));
        nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_PEC, lang.toLowerCase(), CostantiDAO.PEC_INVIO_ALTRI_UFFICI);

        mail.setMailTo(mailTo);
        mail.setMailCc(mailCc);
        mail.setNomeTemplate(nomeTemplate);
        mail.setMailSubject(mailSubject);
        mail.setNomeDocumento(nomeDocumento);
        mail.setMailFrom(MITTENTE_STANDARD);

        String uuid = utentePecView.getVo().getUUId().replaceAll("\\{", "").replaceAll("\\}","");
        //@@DDS Document documento = documentaleCryptDAO.leggiDocumentoUUID(uuid);
        Document documento = documentaleDdsCryptDAO.leggiDocumentoUUID(uuid);

        if(!mailTo.isEmpty() || ! mailCc.isEmpty()){
            if(documento==null)
                inviaEmail(mail); 
            else
                inviaEmailConAllegatoDocument(mail,documento);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("inviaNotificaInvioAltriUfficiPec(UtentePecView, String, String, String, String) - end"); //$NON-NLS-1$
        }
    }

    @Override
    public void inviaSollecitoVotazioni(List<String> emailVotanti, String lang, String userIdUtenteConnesso) throws Throwable {
        if (logger.isDebugEnabled()) {
            logger.debug("inviaSollecitoVotazioni("+lang+", "+userIdUtenteConnesso+") - start"); //$NON-NLS-1$
        }

        //variabili di mapping all'entit�
        String mailTo = StringUtils.EMPTY;
        String mailCc = StringUtils.EMPTY;
        String nomeTemplate = StringUtils.EMPTY;
        String mailSubject = StringUtils.EMPTY;

        // creo l'oggetto
        Mail mail = new Mail();

        InputStream is = null;
        try {
            is = EmailNotificationServiceImpl.class.getResourceAsStream("/config.properties");
            Properties properties = new Properties();
            properties.load(is);
            MITTENTE_STANDARD=properties.getProperty("MITTENTE_STANDARD");
            CC_VENDOR_MANAGEMENT=properties.getProperty("CC_VENDOR_MANAGEMENT");
        }
        catch (Exception e) {
            logger.error("inviaSollecitoVotazioni("+lang+", "+userIdUtenteConnesso+")", e); //$NON-NLS-1$

            System.out.println("Properties non lette: "+ e);
        } 

        for(String to : emailVotanti){
            if(!to.isEmpty()){

                if(to.indexOf("@") >-1){

                    mailTo = mailTo + to + ",";
               }
            }
        }


        mailSubject = String.format("Ricorda di Votare");
        nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_SOLLECITO, lang.toLowerCase(), CostantiDAO.MAIL_SOLLECITO);


        mail.setMailTo(mailTo);
        mail.setMailCc(CC_VENDOR_MANAGEMENT);
        mail.setNomeTemplate(nomeTemplate);
        mail.setMailSubject(mailSubject);
        mail.setMailFrom(MITTENTE_STANDARD);

        if(!mailTo.isEmpty() || ! mailCc.isEmpty())
            inviaEmail(mail);

        if (logger.isDebugEnabled()) {
            logger.debug("inviaSollecitoVotazioni("+lang+", "+mailTo+") - end"); //$NON-NLS-1$
        }
    }

    @Override
    public void inviaNotificaBonifica ( long idEntita) throws Throwable{
        if (logger.isDebugEnabled()) {
            logger.debug("inviaNotifica(String, String, long, String, String) - start"); //$NON-NLS-1$
        }

        //variabili di mapping con l'entit� mail
        String mailFrom = StringUtils.EMPTY;
        String mailTo = StringUtils.EMPTY;
        String mailCc = StringUtils.EMPTY;
        String mailBcc = StringUtils.EMPTY;
        String mailSubject = StringUtils.EMPTY;
        String nomeTemplate = StringUtils.EMPTY;
        String nomeDocumento = StringUtils.EMPTY;
        String linkAlDocumento = StringUtils.EMPTY;
        String motivazioneRifiuto = StringUtils.EMPTY;
        String dataAutorizzazione = StringUtils.EMPTY;
        String utenteAutorizzazione = StringUtils.EMPTY;
        String utenteRegistrazione = StringUtils.EMPTY;
        String dataApprovazioneInSeconda = StringUtils.EMPTY;
        String utenteApprovazioneInSeconda = StringUtils.EMPTY;
        String utenteRichiedente = StringUtils.EMPTY;
        String utenteRifiuto = StringUtils.EMPTY;
        String dataApprovazione = StringUtils.EMPTY;
        String utenteApprovazione = StringUtils.EMPTY;
        String fascicoloRiferimento = StringUtils.EMPTY;
        String linkFascicoloRiferimento = StringUtils.EMPTY;
        String studioLegale = StringUtils.EMPTY;
        String indirizzo = StringUtils.EMPTY;
        String citta = StringUtils.EMPTY;
        String cap = StringUtils.EMPTY;
        String paese = StringUtils.EMPTY;
        String telefono = StringUtils.EMPTY;
        String fax = StringUtils.EMPTY;
        String email = StringUtils.EMPTY;
        String partitaIva = StringUtils.EMPTY;
        String codiceFiscale = StringUtils.EMPTY;
        String totaleAutorizzato = StringUtils.EMPTY;
        String societaAddebito = StringUtils.EMPTY;
        String annoFinanziario = StringUtils.EMPTY;
        String centroCosto = StringUtils.EMPTY;
        String voceConto = StringUtils.EMPTY;
        String unitaLegale = StringUtils.EMPTY;
        String ultimoProforma = StringUtils.EMPTY;
        String tipoProfessionista = StringUtils.EMPTY;
        String tipoAllegatoIt = StringUtils.EMPTY;
        String motivazioneRichiesta = StringUtils.EMPTY;
        String tipoAllegatoEn = StringUtils.EMPTY;
        String requestingUserID = StringUtils.EMPTY;

        //variabili di appoggio

        //Utente utenteConnesso = null;
        Utente utenteStepInterMedio = null;
        Utente utenteCreazione = null;
        //        Utente responsabile = null;
        //        String matricolaResponsabile = StringUtils.EMPTY;
        //        String codiceLingua = StringUtils.EMPTY;
        //        String rifiuto = StringUtils.EMPTY;
        String matricolaUtenteStepIntermedio = StringUtils.EMPTY;
        //        boolean proformaAutorizzato = false;
        StepWf step = null;
        StepWf stepIntermedio = null;
        //        ConfigurazioneStepWf configurazioneTradotta = null;
        String webUrl = StringUtils.EMPTY;
        InputStream is = null;
        //        String destInvioAltriUffici = StringUtils.EMPTY;
        //        String destIncaricoAutorizzato = StringUtils.EMPTY;
        //        String destIncarichi = StringUtils.EMPTY;
        String destAutorizzazioneProforma = StringUtils.EMPTY;

        try {
            is = EmailNotificationServiceImpl.class.getResourceAsStream("/config.properties");
            Properties properties = new Properties();
            properties.load(is);
            webUrl = properties.getProperty("webUrl");
            //            destInvioAltriUffici=properties.getProperty("destInvioAltriUffici");
            //            destIncaricoAutorizzato=properties.getProperty("destIncaricoAutorizzato");
            //            destIncarichi=properties.getProperty("destIncarichi");
            destAutorizzazioneProforma=properties.getProperty("destAutorizzazioneProforma");
            PARCELLE_LEGALI_PAGABILI=properties.getProperty("PARCELLE_LEGALI_PAGABILI");
            MITTENTE_STANDARD=properties.getProperty("MITTENTE_STANDARD");
        }
        catch (Exception e) {
            logger.error("inviaNotifica(String, String, long, String, String)", e); //$NON-NLS-1$

            System.out.println("Properties non lette: "+ e);
        } 

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 


        //        rifiuto = CostantiDAO.RIFIUTO;

        String lang="IT";

        Mail mail = new Mail();

        Proforma proforma = null;
        ProformaWf proformaWf = null;
        proforma = proformaDAO.leggi(idEntita);
        nomeDocumento = proforma.getNomeProforma();
        linkAlDocumento = String.format("%s%s%s%d%s%s%s%s%s",PREF_HREF, webUrl,SUFF_URL_PROFORMA,idEntita, SUFF_HREF, INI_BOLD, nomeDocumento, FINE_BOLD, FINE_HREF);

        dataAutorizzazione = df.format(proforma.getDataAutorizzazione());
        //utenteAutorizzazione = utenteConnesso.getNominativoUtil();
        //unitaLegale = utenteConnesso.getDescrUnitaUtil();

        //ricavo l'incarico associato al proforma
        Incarico incaricoAssociato = incaricoDAO.leggiIncaricoAssociatoAProforma(proforma.getId());

        //ricavo il fascicolo di riferimento
        fascicoloRiferimento = incaricoAssociato.getFascicolo().getNome();
        logger.info("fascicoloRiferimento: " +fascicoloRiferimento);

        //ricavo il link al fascicolo di riferimento
        linkFascicoloRiferimento = String.format("%s%s%s%d%s%s%s%s%s",PREF_HREF, webUrl,SUFF_URL_FASCICOLO,incaricoAssociato.getFascicolo().getId(), SUFF_HREF, INI_BOLD, incaricoAssociato.getFascicolo().getNome(), FINE_BOLD, FINE_HREF);

        //                                 linkFascicoloRiferimento = String.format("%s%s%d", webUrl,SUFF_URL_FASCICOLO,incaricoAssociato.getFascicolo().getId());

        //recupero il professionista esterno
        ProfessionistaEsterno professionistaAssociato = incaricoAssociato.getProfessionistaEsterno();

        //recupero lo studio legale del professionista
        StudioLegale studioLegaleAssociato = professionistaAssociato.getStudioLegale();
        studioLegale = studioLegaleAssociato.getDenominazione();

        totaleAutorizzato = proforma.getTotaleAutorizzato().toString();

        societaAddebito = societaDAO.leggiSocietaAddebitoProforma(idEntita).getNome();

        annoFinanziario = proforma.getAnnoEsercizioFinanziario().toString();

        centroCosto = (proforma.getCentroDiCosto() == null)? "":proforma.getCentroDiCosto();

        voceConto = (proforma.getVoceDiConto() == null)? "":proforma.getVoceDiConto();

        if(proforma.getUltimo().equalsIgnoreCase(Character.toString(CostantiDAO.TRUE_CHAR)))
            ultimoProforma = Character.toString(CostantiDAO.TRUE_CHAR);


        proformaWf = proformaWfDAO.leggiUltimoWorkflowTerminato(idEntita);


        //ricavo lo step di attesa approvazione in seconda firma
        stepIntermedio = stepWfDAO.leggiSpecificoStepWorkflow(proformaWf.getId(), CostantiDAO.AUTORIZZAZIONE_PROFORMA, CostantiDAO.PROFORMA_STATO_ATTESA_SECONDA_APPROVAZIONE);

        if(stepIntermedio != null){
            matricolaUtenteStepIntermedio = stepIntermedio.getUtenteChiusura();
            dataApprovazioneInSeconda = df.format(stepIntermedio.getDataChiusura());
            if(!matricolaUtenteStepIntermedio.isEmpty()){
                utenteStepInterMedio = utenteDAO.leggiUtenteDaMatricola(matricolaUtenteStepIntermedio);
                if(utenteStepInterMedio != null)
                    utenteApprovazioneInSeconda = utenteStepInterMedio.getNominativoUtil();
            }
        }


        //ricavo lo step di attesa approvazione
        stepIntermedio = stepWfDAO.leggiSpecificoStepWorkflow(proformaWf.getId(), CostantiDAO.AUTORIZZAZIONE_PROFORMA, CostantiDAO.PROFORMA_STATO_ATTESA_APPROVAZIONE);

        if(stepIntermedio != null){
            matricolaUtenteStepIntermedio = stepIntermedio.getUtenteChiusura();
            dataApprovazione = df.format(stepIntermedio.getDataChiusura());
            if(!matricolaUtenteStepIntermedio.isEmpty()){
                utenteStepInterMedio = utenteDAO.leggiUtenteDaMatricola(matricolaUtenteStepIntermedio);
                if(utenteStepInterMedio != null)
                    utenteApprovazione = utenteStepInterMedio.getNominativoUtil();
            }
        }



        step = stepWfDAO.leggiUltimoStepChiuso(proformaWf.getId(), CostantiDAO.AUTORIZZAZIONE_PROFORMA);
        //        codiceLingua = step.getConfigurazioneStepWf().getCodGruppoLingua();
        //        configurazioneTradotta = configurazioneStepWfDAO.leggiConfigurazioneTradotta(codiceLingua, lang);

        //invio la e-mail al creatore del workflow
        utenteCreazione = utenteDAO.leggiUtenteDaUserId(proformaWf.getUtenteCreazione());
        if(utenteCreazione.getEmailUtil() != null && !utenteCreazione.getEmailUtil().isEmpty())
            mailTo = utenteCreazione.getEmailUtil()+ ",";

        mailTo = mailTo+destAutorizzazioneProforma;

        //e in conoscenza a tutti gli attori coinvolti nel workflow
        List<Utente> attoriWf = utenteDAO.leggiAttoriWorkflow(proformaWf.getId(), CostantiDAO.AUTORIZZAZIONE_PROFORMA);
        for( Utente attore : attoriWf ){
            if(attore.getEmailUtil() != null && !attore.getEmailUtil().isEmpty()){
                if (!attore.getEmailUtil().equalsIgnoreCase(mailTo))
                    mailCc = mailCc + attore.getEmailUtil() + ",";
            }
        }

        mailSubject = String.format("%s - %s - %s",nomeDocumento, anagraficaStatiDAO.leggiStatoProforma( proforma.getStatoProforma().getCodGruppoLingua(), lang.toUpperCase()).getDescrizione(), df.format(step.getDataChiusura()));
        nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_PROFORMA, lang.toLowerCase(), proforma.getStatoProforma().getCodGruppoLingua().toLowerCase());

        //valorizzazione entit�

        logger.info("Preparo la mail da inviare, valorizzazione entity");
        mail.setAnnoFinanziario(annoFinanziario);
        mail.setCap(cap);
        mail.setCentroCosto(centroCosto);
        mail.setCitta(citta);
        mail.setCodiceFiscale(codiceFiscale);
        mail.setDataApprovazione(dataApprovazione);
        mail.setDataApprovazioneInSeconda(dataApprovazioneInSeconda);
        mail.setDataAutorizzazione(dataAutorizzazione);
        mail.setEmail(email);
        mail.setFascicoloRiferimento(fascicoloRiferimento);
        mail.setFax(fax);
        mail.setIndirizzo(indirizzo);
        mail.setLinkAlDocumento(linkAlDocumento);
        mail.setLinkFascicoloRiferimento(linkFascicoloRiferimento);
        mail.setMailBcc(mailBcc);
        mail.setMailCc(mailCc);
        mail.setMailFrom(mailFrom);
        mail.setMailSubject(mailSubject);
        mail.setMailTo(mailTo);
        mail.setMotivazioneRichiesta(motivazioneRichiesta);
        mail.setMotivazioneRifiuto(motivazioneRifiuto);
        mail.setNomeTemplate(nomeTemplate);
        mail.setNomeDocumento(nomeDocumento);
        mail.setPaese(paese);
        mail.setPartitaIva(partitaIva);
        mail.setSocietaAddebito(societaAddebito);
        mail.setStudioLegale(studioLegale);
        mail.setTelefono(telefono);
        mail.setTipoAllegatoEn(tipoAllegatoEn);
        mail.setTipoAllegatoIt(tipoAllegatoIt);
        mail.setTipoProfessionista(tipoProfessionista);
        mail.setTotaleAutorizzato(totaleAutorizzato);
        mail.setUltimoProforma(ultimoProforma);
        mail.setUnitaLegale(unitaLegale);
        mail.setUtenteApprovazione(utenteApprovazione);
        mail.setUtenteApprovazioneInSeconda(utenteApprovazioneInSeconda);
        mail.setUtenteAutorizzazione(utenteAutorizzazione);
        mail.setUtenteAutorizzatore(utenteAutorizzazione);
        mail.setUtenteRegistrazione(utenteRegistrazione);
        mail.setUtenteRichiedente(utenteRichiedente);
        mail.setUtenteRifiuto(utenteRifiuto);
        mail.setVoceConto(voceConto);
        mail.setRequestingUserID(requestingUserID);
        mail.setMailFrom(MITTENTE_STANDARD);


        logger.info(mail.toString());


        if(!mailTo.isEmpty() || ! mailCc.isEmpty())
            inviaEmail(mail);


        if (logger.isDebugEnabled()) {
            logger.debug("inviaNotifica(String, String, long, String, String) - end"); //$NON-NLS-1$
        }
    }


    public void inviaNotificaPecOperatoriSegreteria(long idEntita, String lang, String userIdUtenteConnesso) throws Throwable {
        if (logger.isDebugEnabled()) {
            logger.debug("inviaNotificaPecOperatoriSegreteria(long, String, String) - start"); //$NON-NLS-1$
        }

        //variabili di mapping all'entit�
        String mailTo = StringUtils.EMPTY;
        String mailCc = StringUtils.EMPTY;
        String nomeTemplate = StringUtils.EMPTY;
        String mailSubject = StringUtils.EMPTY;

        InputStream is = null;
        try {
            is = EmailNotificationServiceImpl.class.getResourceAsStream("/config.properties");
            Properties properties = new Properties();
            properties.load(is);
            MITTENTE_STANDARD = properties.getProperty("MITTENTE_STANDARD");
        }
        catch (Exception e) {
            logger.error("inviaNotificaPecOperatoriSegreteria(long, String, String)", e); //$NON-NLS-1$

            System.out.println("Properties non lette: "+ e);
        } 

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); // creo l'oggetto

        Mail mail = new Mail();

        Utente utente = utenteDAO.leggiUtenteDaUserId(userIdUtenteConnesso);
        mail.setUtenteRichiedente(utente.getNominativoUtil());

        UtentePec utentePec = utentePecDAO.leggi(idEntita);
        mail.setMittentePec(utentePec.getPecMittente());
        mail.setDestinatarioPec(utentePec.getPecDestinatario());
        mail.setOggettoPec(utentePec.getPecOggetto());

        List<Utente> opSegreteria = utenteDAO.leggiUtentiOperatoriSegreteria();
        for( Utente op : opSegreteria ){
            if(op.getEmailUtil() != null && !op.getEmailUtil().isEmpty())
                mailTo = mailTo + op.getEmailUtil() + ",";
        }

        mailSubject = String.format("Richiesta trasformazione in Atto - %s", df.format(new Date()));
        nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_OPERATORE_SEGRETERIA, lang.toLowerCase(), CostantiDAO.OPERATORE_SEGRETERIA_NOTIFICA);

        //mailCc = "antonio.amelio@eng.it";

        mail.setMailTo(mailTo);
        mail.setMailCc(mailCc);

        mail.setNomeTemplate(nomeTemplate);
        mail.setMailSubject(mailSubject);
        mail.setMailFrom(MITTENTE_STANDARD);


        if(!mailTo.isEmpty() )
            logger.info("INVIO MAIL: "+mail.toString());
        inviaEmail(mail);

        if (logger.isDebugEnabled()) {
            logger.debug("inviaNotificaPecOperatoriSegreteria(long, String, String) - end"); //$NON-NLS-1$
        }
    }

    @Override
    public void inviaNotificaProtocollo() throws Throwable {
        if (logger.isDebugEnabled()) {
            logger.debug("inviaNotificaProtocollo start"); //$NON-NLS-1$
        }

        //variabili di mapping all'entit�
        String mailTo = StringUtils.EMPTY;
        String nomeTemplate = StringUtils.EMPTY;
        String mailSubject = StringUtils.EMPTY;
        String webUrl = StringUtils.EMPTY;
        String mailCc = StringUtils.EMPTY;

        InputStream is = null;

        try {
            is = EmailNotificationServiceImpl.class.getResourceAsStream("/config.properties");
            Properties properties = new Properties();
            properties.load(is);
            webUrl = properties.getProperty("webUrl");
            MITTENTE_STANDARD=properties.getProperty("MITTENTE_STANDARD");
        }
        catch (Exception e) {
            logger.error("inviaNotificaProtocollo", e);
            System.out.println("Properties non lette: "+ e);
        } 


        Mail mail = new Mail();

        Utente amministratore = utenteDAO.leggiResponsabileTop();

        mailTo=amministratore.getEmailUtil();
        //mailTo="oscar.nastro@eng.it";

        int docuDaAssegnare = archivioProtocolloDAO.leggiProtocolliDaAssegnare().size();

        if(docuDaAssegnare==0){
            logger.debug("inviaNotificaProtocollo - Non ci sono protocolli da assegnare, non invio email");
            return;
        }

        mail.setNumeroProtocolli(String.valueOf(docuDaAssegnare));

        String linkAlDocumento = PREF_HREF+webUrl+SUFF_URL_PROTOCOLLO+"DAS"+SUFF_HREF+INI_BOLD+"archivio Protocollo"+FINE_BOLD+FINE_HREF;
        mail.setLinkAlDocumento(linkAlDocumento);

        mailSubject = "n� "+docuDaAssegnare+" Documenti  in Ingresso Protocollati da assegnare";
        nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_PROTOCOLLO, "it", "a");

        mail.setMailTo(mailTo);
        mail.setMailCc(mailCc);

        mail.setNomeTemplate(nomeTemplate);
        mail.setMailSubject(mailSubject);
        mail.setMailFrom(MITTENTE_STANDARD);


        if(!mailTo.isEmpty() )
            logger.info("INVIO MAIL: "+mail.toString());
        inviaEmail(mail);

        if (logger.isDebugEnabled()) {
            logger.debug("inviaNotificaProtocollo"); //$NON-NLS-1$
        }

    }

    @Override
    public void inviaNotificaCambioOwner(Long idFascicolo, Utente utenteOld, Utente utenteNuovo) throws Throwable {
        if (logger.isDebugEnabled()) {
            logger.debug("inviaNotificaCambioOwner start"); //$NON-NLS-1$
        }

        //variabili di mapping all'entit�
        String mailTo = StringUtils.EMPTY;
        String nomeTemplate = StringUtils.EMPTY;
        String mailSubject = StringUtils.EMPTY;
        String mailCc = StringUtils.EMPTY;

        caricaMittente();

        Mail mail = new Mail();

        Fascicolo fascicolo = fascicoloDAO.leggi(idFascicolo);

        mailTo=utenteNuovo.getEmailUtil();
        mailCc=utenteOld.getEmailUtil();

        mail.setFascicoloRiferimento(fascicolo.getNome());
        mail.setVecchioOwner(utenteOld.getNominativoUtil());
        mail.setNuovoOwner(utenteNuovo.getNominativoUtil());

        mailSubject = "Cambio Owner Fascicolo "+fascicolo.getNome();
        nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_FASCICOLO, "it", "co");

        mail.setNomeTemplate(nomeTemplate);
        mail.setMailSubject(mailSubject);
        mail.setMailFrom(MITTENTE_STANDARD);
        mail.setMailTo(mailTo);
        mail.setMailCc(mailCc);


        if(!mailTo.isEmpty() )
            logger.info("INVIO MAIL: "+mail.toString());
        inviaEmail(mail);

        if (logger.isDebugEnabled()) {
            logger.debug("inviaNotificaCambioOwner fine"); //$NON-NLS-1$
        }        
    }


    private String caricaMittente() {
        String methodName="caricaMittente()";
        if (logger.isDebugEnabled()) {
            logger.debug(methodName+"begin"); //$NON-NLS-1$
        }    
        try {
            InputStream is = null;
            is = EmailNotificationServiceImpl.class.getResourceAsStream("/config.properties");
            Properties properties = new Properties();
            properties.load(is);
            MITTENTE_STANDARD=properties.getProperty("MITTENTE_STANDARD");
            logger.info(methodName+" mittente caricato correttamente "+MITTENTE_STANDARD);
        } catch (Exception e) {
            logger.error(methodName+" errore caricamento mittente dal file di properties",e);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(methodName+"end"); //$NON-NLS-1$
        }
        return MITTENTE_STANDARD;
    }



    @Override
    public void inviaNotificaCambioOwner(Mail mail, Utente utenteOld, Utente utenteNuovo) throws Throwable {
        String methodName = "inviaNotificaCambioOwner() ";
        if (logger.isDebugEnabled()) {
            logger.debug(methodName + "begin"); //$NON-NLS-1$
        }
        try {
            //variabili di mapping all'entit�
            String mailTo = StringUtils.EMPTY;
            String nomeTemplate = StringUtils.EMPTY;
            String mailSubject = StringUtils.EMPTY;
            String mailCc = StringUtils.EMPTY;

            caricaMittente();

            mailTo=utenteNuovo.getEmailUtil();
            mailCc=utenteOld.getEmailUtil();

            mail.setVecchioOwner(utenteOld.getNominativoUtil());
            mail.setNuovoOwner(utenteNuovo.getNominativoUtil());
            String template ="co";
            mailSubject = "Cambio Owner Fascicoli";
            if (mail.getListaFascioliCambioOwner()!= null && mail.getListaFascioliCambioOwner().size()>0) {
                template = "cos";
            }

            nomeTemplate = String.format("templateMail/%s/%s/%s.vm", CostantiDAO.FOLDER_FASCICOLO, "it", template);
            if (logger.isDebugEnabled()) {
                logger.debug(methodName + "template caricato: "+template); //$NON-NLS-1$
            }

            mail.setNomeTemplate(nomeTemplate);
            mail.setMailSubject(mailSubject);
            mail.setMailFrom(MITTENTE_STANDARD);
            mail.setMailTo(mailTo);
            mail.setMailCc(mailCc);

            if(!mailTo.isEmpty() )
                logger.info(methodName+"INVIO MAIL: "+mail.toString());
            inviaEmail(mail);
        } catch (Exception e) {
            logger.error(methodName + " errore invio notifica mail", e);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(methodName + "end"); //$NON-NLS-1$
        }
    }


}


