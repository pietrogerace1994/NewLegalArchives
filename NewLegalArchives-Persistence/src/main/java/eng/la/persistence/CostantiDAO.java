package eng.la.persistence;

public interface CostantiDAO {

	public static final char TRUE_CHAR = 'T';

	public static final char FALSE_CHAR = 'F';
	
	public static final char YES_CHAR = 'Y';

	public static final char NO_CHAR = 'N';


	public static final String FASCICOLO_STATO_APERTO = "A"; 
	public static final String FASCICOLO_STATO_CHIUSO = "C";
	public static final String FASCICOLO_STATO_COMPLETATO = "COMP";
	public static final String FASCICOLO_STATO_RIPORTATO_IN_COMPLETATO = "RIC";
	public static final String FASCICOLO_STATO_ATTESA_AUTORIZZAZIONE_CHIUSURA = "AAUT";
	public static final String FASCICOLO_STATO_ARCHIVIATO = "ARCH";

	public static final String INCARICO_STATO_BOZZA = "B";
	public static final String INCARICO_STATO_ATTESA_PRE_APPROVAZIONE = "APAP";
	public static final String INCARICO_STATO_ATTESA_APPROVAZIONE = "AAP1";
	public static final String INCARICO_STATO_ATTESA_SECONDA_APPROVAZIONE = "AAP2";
	public static final String INCARICO_STATO_ATTESA_AUTORIZZAZIONE = "AAUT";
	public static final String INCARICO_STATO_AUTORIZZATO = "A";
	
	public static final String PROFORMA_STATO_BOZZA = "B";
	public static final String PROFORMA_STATO_ATTESA_PRE_APPROVAZIONE = "APAP";
	public static final String PROFORMA_STATO_ATTESA_APPROVAZIONE = "AAP1";
	public static final String PROFORMA_STATO_ATTESA_SECONDA_APPROVAZIONE = "AAP2";
	public static final String PROFORMA_STATO_ATTESA_AUTORIZZAZIONE = "AAUT";
	public static final String PROFORMA_STATO_AUTORIZZATO = "A";
	public static final String PROFORMA_RESPINTO = "RE";
	
	public static final String NEWSLETTER_STATO_BOZZA = "B";
	public static final String NEWSLETTER_STATO_ATTIVA = "A";
	public static final String NEWSLETTER_STATO_CANCELLATA = "C";
	public static final String NEWSLETTER_STATO_INVIATA = "I";

	
	public static final String PROFESSIONISTA_STATO_BOZZA = "B";
	public static final String PROFESSIONISTA_STATO_ATTESA_PRE_APPROVAZIONE = "APAP";
	public static final String PROFESSIONISTA_STATO_ATTESA_APPROVAZIONE = "AAP1";
	public static final String PROFESSIONISTA_STATO_ATTESA_SECONDA_APPROVAZIONE = "AAP2";
	public static final String PROFESSIONISTA_STATO_ATTESA_AUTORIZZAZIONE = "AAUT";
	public static final String PROFESSIONISTA_STATO_AUTORIZZATO = "A";
	
	public static final String ATTO_STATO_BOZZA = "B";
	public static final String ATTO_STATO_ATTESA_REGISTRAZIONE = "ADR";
	public static final String ATTO_STATO_ATTESA_PRESA_IN_CARICO = "APIC";
	public static final String ATTO_STATO_ATTESA_PRESA_IN_CARICO_LEG = "APICL";
	public static final String ATTO_STATO_REGISTRATO = "R";
	public static final String ATTO_STATO_INVIATO_ALTRI_UFFICI = "IAU";
	/**
	 * Stato utilizzato per indicare un atto da Validare
	 * @author MASSIMO CARUSO
	 */
	public static final String ATTO_STATO_DA_VALIDARE = "VAL";
	/**
	 * Stato utilizzato per indicare un atto Riservato
	 * @author MASSIMO CARUSO
	 */
	public static final String ATTO_STATO_RISERVATO = "RIS";
	/**
	 * Stato utilizzato per indicare un atto assegnato
	 * @author MASSIMO CARUSO
	 */
	public static final String PROTOCOLLO_ATTO_STATO_ASSEGNATO = "AASS";
	
	//costanti per gestione workflow
	public static final String AUTORIZZAZIONE_INCARICO = "AUT_INCARICO";
	public static final String AUTORIZZAZIONE_BEAUTY_CONTEST = "AUT_BEAUTY_CONTEST";
	public static final String AUTORIZZAZIONE_COLLEGIO_ARBITRALE = "AUT_ARBITRATO";
	public static final String AUTORIZZAZIONE_PROFORMA = "AUT_PROFORMA";
	public static final String RESPINTO = "RESPINTO";
	public static final String AUTORIZZAZIONE_INS_PROFESSIONISTA_ESTERNO_IN_ELENCO = "AUT_PROF_EST";
	public static final String REGISTRAZIONE_ATTO = "REGISTR_ATTO";
	public static final String CHIUSURA_FASCICOLO = "CHIUSURA_FASC";
	public static final String IN_ATTESA_DI_AUTORIZZAZIONE = "IN ATTESA DI AUTORIZZAZIONE";
	public static final String IN_ATTESA_DI_APPROVAZIONE_IN_SECONDA_FIRMA = "APAP2";
	
	public static final String SCHEDA_FONDO_RISCHI_STATO_BOZZA = "B";
	public static final String SCHEDA_FONDO_RISCHI_STATO_ATTESA_PRE_APPROVAZIONE = "APAP";
	public static final String SCHEDA_FONDO_RISCHI_STATO_ATTESA_APPROVAZIONE = "AAP1";
	public static final String SCHEDA_FONDO_RISCHI_STATO_ATTESA_SECONDA_APPROVAZIONE = "AAP2";
	public static final String SCHEDA_FONDO_RISCHI_STATO_ATTESA_AUTORIZZAZIONE = "AAUT";
	public static final String SCHEDA_FONDO_RISCHI_STATO_AUTORIZZATO = "A";
	public static final String SCHEDA_FONDO_RISCHI_STATO_ATTESA_VERIFICA = "AV";
	public static final String AUTORIZZAZIONE_SCHEDA_FONDO_RISCHI = "AUT_SCHEDA_FR";
	
	public static final String VENDOR_MANAGEMENT_STATO_BOZZA = "B";
	public static final String VENDOR_MANAGEMENT_STATO_CONFERMATO = "C";
	
	public static final String BEAUTY_CONTEST_STATO_BOZZA = "B";
	public static final String BEAUTY_CONTEST_STATO_ATTESA_PRE_APPROVAZIONE = "APAP";
	public static final String BEAUTY_CONTEST_STATO_ATTESA_APPROVAZIONE = "AAP1";
	public static final String BEAUTY_CONTEST_STATO_ATTESA_SECONDA_APPROVAZIONE = "AAP2";
	public static final String BEAUTY_CONTEST_STATO_ATTESA_AUTORIZZAZIONE = "AAUT";
	public static final String BEAUTY_CONTEST_STATO_AUTORIZZATO = "A";
	public static final String BEAUTY_CONTEST_STATO_CHIUSO = "C";
	public static final String BEAUTY_CONTEST_REPLY_INVIATA = "I";
	
	public static final String LINGUA_ITALIANA = "IT";
	public static final String LINGUA_INGLESE = "EN";
	
	public static final String STATO_WF_IN_CORSO = "TPWS_1";
	public static final String STATO_WF_COMPLETATO = "TPWS_2";
	public static final String STATO_WF_RIFIUTATO = "TPWS_3";
	public static final String STATO_WF_INTERROTTO = "TPWS_4";
	
	public static final String SUFF_AVANZAMENTO_WF = "AVA";
	public static final String SUFF_RIFIUTO_WF = "RIF";
	
	public static final String PRIMO_NUMERO_AZIONE = "1";
	public static final String PRIMO_NUMERO_AZIONE_RESP_PRIMO_LIVELLO = "2";
	public static final String PRIMO_NUMERO_AZIONE_PRIMO_LIVELLO = "3";
	
	public static final String ASSEGNAZIONE_MANUALE = "MAN";
	public static final String ASSEGNAZIONE_RESPONSABILE = "RESP";
	public static final String ASSEGNAZIONE_RICORSIVA = "REC";
	public static final String ASSEGNAZIONE_PRIMO_RIPORTO = "PRIP";
	public static final String ASSEGNAZIONE_OWNER = "OWN";
	public static final String ASSEGNAZIONE_SEGRETERIA = "SEGR";
	
	//chiavi tabella Configurazione
	public static final String KEY_TOP_RESPONSABILE = "TOP_RESPONSABILE";
	
	//DARIO **************************************************************
	public static final String KEY_TOP_HEAD = "TOP_HEAD";
	//********************************************************************
	
	public static final String KEY_SNAMRETEGAS_WEBSITE = "SNAMRETEGAS_WEBSITE";
	public static final String KEY_SNAM_WEBSITE = "SNAM_WEBSITE";
	public static final String KEY_EMAIL_SEGNALAZIONI = "EMAIL_SEGNALAZIONI";
	public static final String KEY_PIE_DI_PAGINA= "SNAM_PIE_DI_PAGINA";
	
	//entità
	public static final String FASCICOLO = "Fascicolo";
	public static final String INCARICO = "Incarico";
	public static final String ARBITRATO = "Collegio Arbitrale";
	public static final String PROFORMA = "Proforma";
	public static final String ATTO = "Atto";
	public static final String PROFESSIONISTA_ESTERNO = "Professionista Esterno";
	public static final String PROGETTO = "Progetto";
	public static final String SCHEDA_FONDO_RISCHI = "Scheda Fondo Rischi";
	public static final String BEAUTY_CONTEST = "Beauty Contest";
	
	//gruppi utente
	public static final String GESTORE_FASCICOLI = "LEG_ARC_GESTOREFASCICOLI";
	public static final String GRUPPO_RESPONSABILE = "LEG_ARC_RESPONSABILE";
	public static final String GRUPPO_AMMINISTRATORE = "LEG_ARC_AMMINISTRATORE";
	public static final String OPERATORE_SEGRETERIA = "LEG_ARC_OPERATORESEGRETERIA";
	public static final String LEGALE_ESTERNO = "LEG_ARC_LEGALEESTERNO";
	public static final String AMMINISTRATIVO = "LEG_ARC_AMMINISTRATIVO";
	public static final String GENERALCOUNSEL = "LEG_ARC_GENERALCOUNSEL";
	public static final String SCRITTORE_UNIVERSALE = "LEG_ARC_SCRITTOREUNIVERSALE";
	public static final String REPORTING_AVANZATO = "LEG_ARC_REPORTINGAVANZATO";
	public static final String AUDIT = "LEG_ARC_AUDIT";
	public static final String GESTORE_ARCHIVIO_DUEDILIGENCE = "LEG_ARC_GESTOREARCHIVIODUEDILIGENCE";
	public static final String GESTORE_ARCHIVIO_RICHIESTE_AG = "LEG_ARC_GESTOREARCHIVIORICHIESTEAG";
	public static final String GESTORE_ARCHIVIO_PC = "LEG_ARC_GESTOREARCHIVIOPC";
	public static final String GRUPPO_AMMINISTRATIVO = "LEG_ARC_AMMINISTRATIVO";
	public static final String GESTORE_ARCHIVIO_AUTORITA_GIUDIZIARIA = "LEG_ARC_GESTOREARCHIVIORICHIESTEAG";
	public static final String GESTORE_ARCHIVIO_PRESIDIO_NORMATIVO="LEG_ARC_GESTOREARCHIVIOPN";
	public static final String GESTORE_VENDOR="LEG_ARC_GESTORE_VENDOR";
	

	//Tipi notifiche
	public static final String AVANZAMENTO = "AVANZAMENTO";
	public static final String RIFIUTO = "RIFIUTO";
	public static final String RIFIUTO_EN= "DENIAL";
	
	//folder mail
	public static final String FOLDER_FASCICOLO = "fascicolo";
	public static final String FOLDER_INCARICO = "incarico";
	public static final String FOLDER_ARBITRATO = "collegioArbitrale";
	public static final String FOLDER_PROFORMA = "proforma";
	public static final String FOLDER_ATTO = "atto";
	public static final String FOLDER_PROFESSIONISTA_ESTERNO = "professionista";
	public static final String FOLDER_SOLLECITO = "sollecito";
	public static final String FOLDER_OPERATORE_SEGRETERIA = "opSegreteria";
	public static final String FOLDER_PROTOCOLLO = "protocollo";
	public static final String FOLDER_SCHEDA_FONDO_RISCHI = "schedaFondoRischi";
	public static final String FOLDER_PEC = "pec";
	public static final String FOLDER_BEAUTY_CONTEST = "beautyContest";
	
	public static final String OPERATORE_SEGRETERIA_NOTIFICA = "notifica";
	
	public static final String PEC_INVIO_ALTRI_UFFICI = "iau";
	
	//template mail
	public static final String MAIL_LEGALE = "legale";
	public static final String MAIL_SOCIETA = "societa";
	public static final String MAIL_SOLLECITO = "sollecito";
	public static final String MAIL_LEGALE_NO_LINK = "legaleNoLink";
	public static final String MAIL_LEGALE_BEAUTY_CONTEST = "legaleBc";
	public static final String MAIL_LEGALE_VINCITORE_BEAUTY_CONTEST = "legaleVincitoreBc";
	
	
	//COSTANTI X AUTORIZZAZIONI
	public static final String NOME_CLASSE_FASCICOLO = "FASCICOLO";
	public static final String NOME_CLASSE_ATTO = "ATTO"; 
	public static final String NOME_CLASSE_INCARICO = "INCARICO";
	public static final String NOME_CLASSE_PROFORMA = "PROFORMA";
	public static final String NOME_CLASSE_PROGETTO = "PROGETTO";
	public static final String NOME_CLASSE_PROTOCOLLO = "PROTOCOLLO";
	public static final String NOME_CLASSE_PROCURE = "PROCURE";
	public static final String NOME_CLASSE_SCHEDA_FONDO_RISCHI = "SCHEDA_FONDO_RISCHI";
	public static final String NOME_CLASSE_UDIENZA = "UDIENZA";
	public static final String NOME_CLASSE_BEAUTY_CONTEST = "BEAUTY_CONTEST";
	
	public static final String TIPO_AUTORIZZAZIONE_UTENTE_SCRITTURA = "UW";
	public static final String TIPO_AUTORIZZAZIONE_UTENTE_LETTURA = "UR";
	public static final String TIPO_AUTORIZZAZIONE_GRUPPO_SCRITTURA = "GW";
	public static final String TIPO_AUTORIZZAZIONE_GRUPPO_LETTURA = "GR";
	
	
	//COSTANTI X ESITO VALUTAZION PROFESSIONISTA
	public static final String PROFESSIONISTA_DA_ATTIVARE = "TEVP_0";
	public static final String PROFESSIONISTA_ATTIVO = "TEVP_1"; 
	public static final String PROFESSIONISTA_SOSPESO = "TEVP_2";
	public static final String PROFESSIONISTA_REVOCATO = "TEVP_3";


	
	public static final String RICHIEDIPROFORMA = "RICHIEDI_PROFORMA";

	public static final String RICHIESTA = "Richiesta invio Proforma";
	
	public static final String RICHIESTA_EN = "Request Proforma";

	public static final String KEY_POLLING_FILE_LETTERA_INCARICO = "POLLING_FILE_LETTERA_INCARICO";
	public static final String KEY_POLLING_FILE_NOTA_PROPOSTA_INCARICO = "POLLING_FILE_NOTA_PROPOSTA_INCARICO";
	public static final String KEY_POLLING_FILE_SCHEDA_VALUTAZIONE = "POLLING_FILE_SCHEDA_VALUTAZIONE";
	
	//GIUDIZIO VENDOR MANAGEMENT
	public static final String VENDOR_GIUDIZIO_OTTIMO = "6";
	public static final String VENDOR_GIUDIZIO_BUONO = "5";
	public static final String VENDOR_GIUDIZIO_DISCRETO = "4";
	public static final String VENDOR_GIUDIZIO_SUFFICIENTE = "3";
	public static final String VENDOR_GIUDIZIO_MEDIOCRE = "2";
	public static final String VENDOR_GIUDIZIO_INSUFFICIENTE = "1";

	
	public static final String LEG_ARC_GESTORE_ANAGRAFICA_PROCURE = "LEG_ARC_GESTOREANAGRAFICAPROCURE";
	public static final String LEG_ARC_GESTORE_ARCHIVIO_PROCURE = "LEG_ARC_GESTOREARCHIVIOPROCURE";
	public static final String LEG_ARC_GESTORE_AFFSOC = "LEG_ARC_GESTORE_AFFSOC";
	public static final String LEG_ARC_AFFSOC = "LEG_ARC_AFFSOC";
	
	public static final String NASCONDI_A_LEG_ARC_GESTORE_ANAGRAFICA_PROCURE = "NASCONDI_A_LEG_ARC_GESTORE_ANAGRAFICA_PROCURE";

	public static final int EMAIL_PEC_NON_GESTITA = 0;
	public static final int EMAIL_PEC_TRASFORMATA_IN_ATTO = 1;
	public static final int EMAIL_PEC_ANNULLATA = 2; 
	public static final int EMAIL_PEC_SPOSTATA_IN_ARCH_PROT = 3;
	public static final int EMAIL_PEC_INVIATA_AD_ALTRI_UFFICI = 4;
	
}
