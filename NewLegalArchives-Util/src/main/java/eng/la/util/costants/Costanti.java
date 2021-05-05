package eng.la.util.costants;

public interface Costanti {

	public static final String UTENTE_CONNESSO_NOME_PARAMETRO = "UTENTE_CONNESSO";

	public static final String TIPOLOGIA_FASCICOLO_GIUDIZIALE_COD = "TFSC_1";
	public static final String TIPOLOGIA_FASCICOLO_STRAGIUDIZIALE_COD = "TFSC_2";
	public static final String TIPOLOGIA_FASCICOLO_NOTARILE_COD = "TFSC_4";

	public static final String TIPOLOGIA_PROFESSIONISTA_LEGALE_ESTERNO_COD = "TPRF_1";
	public static final String TIPOLOGIA_PROFESSIONISTA_ARBITRO_COD = "TPRF_2";
	public static final String TIPOLOGIA_PROFESSIONISTA_NOTAIO_COD = "TPRF_3";

	public static final String SETTORE_GIURIDICO_CIVILE_CODE = "TSTT_1";
	public static final String SETTORE_GIURIDICO_AMMINISTRATIVO_CODE = "TSTT_3";
	public static final String SETTORE_GIURIDICO_PENALE_CODE = "TSTT_2";
	public static final String SETTORE_GIURIDICO_ARBITRALE_CODE = "TSTT_6";
	public static final String SETTORE_GIURIDICO_JOIN_VENTURE_CODE = "TSTT_16";
	public static final String SOCIETA_TIPOLOGIA_ADDEBITO = "A";
	public static final String SOCIETA_TIPOLOGIA_PARTE_PROCEDIMENTO = "P";

	public static final String FASCICOLO_STATO_APERTO = "A";
	public static final String FASCICOLO_STATO_CHIUSO = "C";
	public static final String FASCICOLO_STATO_COMPLETATO = "COMP";
	public static final String FASCICOLO_STATO_ATTESA_AUTORIZZAZIONE_CHIUSURA = "AAUT";
	public static final String FASCICOLO_STATO_ARCHIVIATO = "ARCH";
	
	public static final String NEWSLETTER_STATO_BOZZA = "B";
	public static final String NEWSLETTER_STATO_ATTIVA = "A";
	public static final String NEWSLETTER_STATO_CANCELLATA = "C";
	
	public static final String PROTOCOLLO_STATO_BOZZA = "BOZ";
	public static final String PROTOCOLLO_STATO_DA_ASSEGNARE = "DAS";
	public static final String PROTOCOLLO_STATO_ASSEGNATO = "ASS";
	public static final String PROTOCOLLO_STATO_TRASFERITO = "TRA";
	public static final String PROTOCOLLO_STATO_NON_TRASFERITO = "NTR";

	public static final String INCARICO_STATO_AUTORIZZATO = "A";
	public static final String INCARICO_STATO_BOZZA = "B";
	public static final String INCARICO_STATO_IN_ATTESA_DI_PRE_APPROVAZIONE = "APAP";
	public static final String INCARICO_STATO_IN_ATTESA_DI_APPROVAZIONE = "APAP1";
	public static final String INCARICO_STATO_IN_ATTESA_DI_APPROVAZIONE_IN_SECONDA_FIRMA = "APAP2";
	public static final String INCARICO_STATO_IN_ATTESA_DI_AUTORIZZAZIONE = "AAUT";

	public static final String PROFORMA_STATO_AUTORIZZATO = "A";
	public static final String PROFORMA_STATO_BOZZA = "B";
	public static final String PROFORMA_STATO_IN_ATTESA_DI_PRE_APPROVAZIONE = "APAP";
	public static final String PROFORMA_STATO_IN_ATTESA_DI_APPROVAZIONE = "APAP1";
	public static final String PROFORMA_STATO_IN_ATTESA_DI_APPROVAZIONE_IN_SECONDA_FIRMA = "APAP2";
	public static final String PROFORMA_STATO_IN_ATTESA_DI_AUTORIZZAZIONE = "AAUT";
	
	public static final String SCHEDA_FONDO_RISCHI_STATO_AUTORIZZATO = "A";
	public static final String SCHEDA_FONDO_RISCHI_STATO_BOZZA = "B";
	public static final String SCHEDA_FONDO_RISCHI_STATO_IN_ATTESA_DI_PRE_APPROVAZIONE = "APAP";
	public static final String SCHEDA_FONDO_RISCHI_STATO_IN_ATTESA_DI_APPROVAZIONE = "APAP1";
	public static final String SCHEDA_FONDO_RISCHI_STATO_IN_ATTESA_DI_APPROVAZIONE_IN_SECONDA_FIRMA = "APAP2";
	public static final String SCHEDA_FONDO_RISCHI_STATO_IN_ATTESA_DI_AUTORIZZAZIONE = "AAUT";
	
	public static final String BEAUTYCONTEST_STATO_AUTORIZZATO = "A";
	public static final String BEAUTYCONTEST_STATO_BOZZA = "B";
	public static final String BEAUTYCONTEST_STATO_IN_ATTESA_DI_PRE_APPROVAZIONE = "APAP";
	public static final String BEAUTYCONTEST_STATO_IN_ATTESA_DI_APPROVAZIONE = "APAP1";
	public static final String BEAUTYCONTEST_STATO_IN_ATTESA_DI_APPROVAZIONE_IN_SECONDA_FIRMA = "APAP2";
	public static final String BEAUTYCONTEST_STATO_IN_ATTESA_DI_AUTORIZZAZIONE = "AAUT";

	public static final String VALORE_CAUSA_INDETERMINATO = "VCAU_1";
	public static final String VALORE_CAUSA_UGUALE_A = "VCAU_2";

	public static final String FALSE_CHAR = "F";
	public static final String TRUE_CHAR = "T";

	public static final String WEBSOCKET_EVENTO_NOTIFICHE_INCARICO = "NOTIFICHE_INCARICO";
	public static final String WEBSOCKET_EVENTO_NOTIFICHE_PROFESSIONISTA = "NOTIFICHE_PROFESSIONISTA";
	public static final String WEBSOCKET_EVENTO_NOTIFICHE_PROFORMA = "NOTIFICHE_PROFORMA";
	public static final String WEBSOCKET_EVENTO_NOTIFICHE_ATTO = "NOTIFICHE_ATTO";
	public static final String WEBSOCKET_EVENTO_NOTIFICHE_FASCICOLO = "NOTIFICHE_FASCICOLO";
	public static final String WEBSOCKET_EVENTO_NOTIFICHE = "NOTIFICHE";

	public static final String WEBSOCKET_EVENTO_CALENDARIO = "CALENDARIO";

	public static final String USERID_GUEST = "GUEST";

	public static final String TIPO_PERMESSO_SCRITTURA = "W";
	public static final String TIPO_PERMESSO_LETTURA = "R";
	public static final String TIPO_PERMESSO_GRANT = "Z";
	public static final String TIPO_ENTITA_PROFORMA = "PROFORMA";
	public static final String TIPO_ENTITA_FASCICOLO = "FASCICOLO";
	public static final String TIPO_ENTITA_INCARICO = "INCARICO";
	public static final String TIPO_ENTITA_RUBRICA = "RUBRICA";
	public static final String TIPO_ENTITA_SCHEDA_FONDO_RISCHI = "SCHEDA_FONDO_RISCHI";
	public static final String TIPO_ENTITA_ATTO = "ATTO";
	public static final String TIPO_ENTITA_UDIENZA = "UDIENZA";
	public static final String TIPO_ENTITA_BEAUTYCONTEST = "BEAUTY_CONTEST";

	public static final String FUNZIONALITA_FASCICOLI = "FASCICOLI";
	public static final String FUNZIONALITA_FASCICOLI_CERCA = "FASCICOLI_CERCA";
	public static final String FUNZIONALITA_FASCICOLI_NUOVO = "FASCICOLI_NUOVO";

	public static final String FUNZIONALITA_ATTI = "ATTI";
	public static final String FUNZIONALITA_ATTI_CERCA = "ATTI_CERCA";
	public static final String FUNZIONALITA_ATTI_NUOVO = "ATTI_NUOVO";
	/**
	 * Aggiunta costante per validazione atti 
	 * @author MASSIMO CARUSO
	 */
	public static final String FUNZIONALITA_ATTI_VALIDA = "ATTI_VALIDA";

	public static final String FUNZIONALITA_INCARICHI = "INCARICHI";
	public static final String FUNZIONALITA_SCHEDE_FONDO_RISCHI = "SCHEDE_FONDO_RISCHI";
	public static final String FUNZIONALITA_BEAUTY_CONTEST = "BEAUTY_CONTEST";
	public static final String FUNZIONALITA_PROFORMA = "PROFORMA";

	public static final String FUNZIONALITA_REPORT = "REPORT";
	
	public static final String FUNZIONALITA_ESTRAZIONE_DATI = "ESTRAZIONE_DATI";
	
	public static final String FUNZIONALITA_ARCHIVI = "ARCHIVI";

	public static final String FUNZIONALITA_ARCHIVI_PARTE_CORRELATA = "ARCHIVI_PARTE_CORRELATA";
	
	public static final String FUNZIONALITA_ARCHIVI_PROTOCOLLO = "ARCHIVI_PROTOCOLLO";
	
	public static final String FUNZIONALITA_ARCHIVI_AUTORITA_GIUDIZIARIA = "ARCHIVI_AUTORITA_GIUDIZIARIA";
	
	public static final String FUNZIONALITA_ARCHIVI_DUE_DILIGENCE = "FUNZIONALITA_ARCHIVI_DUE_DILIGENCE";
	
	public static final String FUNZIONALITA_ARCHIVI_PRESIDIO_NORMATIVO = "ARCHIVI_PRESIDIO_NORMATIVO";

	public static final String FUNZIONALITA_ANAGRAFICA = "ANAGRAFICA";

	public static final String FUNZIONALITA_ANAGRAFICA_VISUALIZZA = "ANAGRAFICA_VISUALIZZA";

	public static final String FUNZIONALITA_ANAGRAFICA_NUOVO = "ANAGRAFICA_NUOVO";

	public static final String FUNZIONALITA_ANAGRAFICA_NUOVO_PROFESSIONISTA = "ANAGRAFICA_NUOVO_PROFESSIONISTA";

	public static final String FUNZIONALITA_ANAGRAFICA_GESTIONE = "ANAGRAFICA_GESTIONE";
	
	public static final String FUNZIONALITA_ANAGRAFICA_GESTIONE_NAZIONE = "ANAGRAFICA_GESTIONE_NAZIONE";

	public static final String FUNZIONALITA_AMMINISTRAZIONE = "AMMINISTRAZIONE";
	
	public static final String FUNZIONALITA_RESPONSABILE = "RESPONSABILE";
	
	public static final String FUNZIONALITA_VENDOR_MANAGEMENT = "GESTORE_VENDOR";

	public static final String NOME_APPLICAZIONE = "LEGAL";

	public static final String RICERCA_PARTE_CORRELATA_ALL = "RICERCA_PARTE_CORRELATA_ALL";
	
	public static final String FUNZIONALITA_MAILINGLIST = "FUNZIONALITA_MAILINGLIST";

	public static final String EXTERNAL_USER_ID = "EXTERNAL_USER_ID";

	public static final String SSO_APPLICATION_NAME = "LA";

	public static final int ASSE_PERCENTUALE_AUTOREVOLEZZA = 15;
	public static final int ASSE_PERCENTUALE_CAPACITA = 20; 
	public static final int ASSE_PERCENTUALE_COMPETENZA = 20; 
	public static final int ASSE_PERCENTUALE_COSTI = 10;
	public static final int ASSE_PERCENTUALE_FLESSIBILITA = 10;
	public static final int ASSE_PERCENTUALE_TEMPI = 15;
	public static final int ASSE_PERCENTUALE_REPERIBILITA = 10;
	
	public static final String ASSE_DESCRIZIONE_AUTOREVOLEZZA = "Autorevolezza giuridica";
	public static final String ASSE_DESCRIZIONE_CAPACITA = "Comprensione e condivisione degli obiettivi dell&#39;azienda";
	public static final String ASSE_DESCRIZIONE_COMPETENZA = "Professionalit&agrave;/Affidabilit&agrave;";
	public static final String ASSE_DESCRIZIONE_COSTI = "Costi";
	public static final String ASSE_DESCRIZIONE_FLESSIBILITA = "Flessibilit&agrave;";
	public static final String ASSE_DESCRIZIONE_TEMPI = "Tempestivit&agrave; della comunicazione";
	public static final String ASSE_DESCRIZIONE_REPERIBILITA = "Reperibilit&agrave;";
	
	public static final String ASSE_DESCRIZIONE_SHORT_AUTOREVOLEZZA = "Autorevolezza";
	public static final String ASSE_DESCRIZIONE_SHORT_CAPACITA = "Capacita";
	public static final String ASSE_DESCRIZIONE_SHORT_COMPETENZA = "Competenza";
	public static final String ASSE_DESCRIZIONE_SHORT_COSTI = "Costi";
	public static final String ASSE_DESCRIZIONE_SHORT_FLESSIBILITA = "Flessibilita";
	public static final String ASSE_DESCRIZIONE_SHORT_TEMPI = "Tempi";
	public static final String ASSE_DESCRIZIONE_SHORT_REPERIBILITA = "Reperibilita";
	
	public static final String CODGRUPPOLINGUA_NAZIONE_ITALIA = "NAZI_3";
	public static final String CODGRUPPOLINGUA_SPECIALIZZAZIONE_ALTRO = "TSPC_24";
	public static final String DESCRIZIONE_NAZIONE_ITALIA = "Italia";
	public static final String DESCRIZIONE_SPECIALIZZAZIONE_ALTRO = "Altro";
	
	public static final int AGENDA_NUMERO_GIORNI_FINESTRA = 30;

	public static final String TIPO_ENTITA_PROGETTO = "PROGETTO";

	public static final String TIPO_ENTITA_PROCURE = "PROCURE";
	
	//GIUDIZIO VENDOR MANAGEMENT
	public static final String VENDOR_GIUDIZIO_OTTIMO = "Ottimo";
	public static final String VENDOR_GIUDIZIO_BUONO = "Buono";
	public static final String VENDOR_GIUDIZIO_DISCRETO = "Discreto";
	public static final String VENDOR_GIUDIZIO_SUFFICIENTE = "Sufficiente";
	public static final String VENDOR_GIUDIZIO_MEDIOCRE = "Mediocre";
	public static final String VENDOR_GIUDIZIO_INSUFFICIENTE = "Insufficiente";


	public static final String REPERTORIO_POTERI_R = "REPERTORIO_POTERI_R";
	public static final String REPERTORIO_POTERI_W = "REPERTORIO_POTERI_W";
	public static final String REPERTORIO_STANDARD_R = "REPERTORIO_STANDARD_R";
	public static final String REPERTORIO_STANDARD_W = "REPERTORIO_STANDARD_W";
	public static final String TIPO_PROCURE_R = "TIPO_PROCURE_R";
	public static final String TIPO_PROCURE_W = "TIPO_PROCURE_W";
	
	
	
	public static final String PROCURE_CONFERITE_R = "PROCURE_CONFERITE_R";
	public static final String PROCURE_CONFERITE_W = "PROCURE_CONFERITE_W";
	
	

	public static final String AFFARI_SOCIETARI_R = "AFFARI_SOCIETARI_R";
	public static final String AFFARI_SOCIETARI_W = "AFFARI_SOCIETARI_W";

	public static final String ORGANO_SOCIALE_R = "ORGANO_SOCIALE_R";
	public static final String ORGANO_SOCIALE_W = "ORGANO_SOCIALE_W";

}
