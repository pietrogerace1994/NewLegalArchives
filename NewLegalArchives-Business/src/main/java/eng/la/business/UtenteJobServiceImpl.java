package eng.la.business;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import eng.la.business.mail.EmailNotificationService;
import eng.la.business.mail.EmailNotificationServiceImpl;
import eng.la.model.GruppoUtente;
import eng.la.model.RUtenteGruppo;
import eng.la.model.Utente;
import eng.la.persistence.GruppoUtenteDAO;
import eng.la.persistence.UtenteDAO;
import eng.la.util.SpringUtil;
import eng.la.util.costants.Costanti;
import it.snamretegas.portal.AuthorizationManagerLitLocator;

@Service("utenteJobService")
public class UtenteJobServiceImpl implements UtenteJobService {

	private static final Logger logger = Logger.getLogger(EmailNotificationServiceImpl.class);

	@Autowired
	private UtenteDAO utenteDAO;

	@Autowired
	private GruppoUtenteDAO gruppoUtenteDAO;
	
	@Autowired
	private EmailNotificationService emailNotificationService;

	private AuthorizationManagerLitLocator authorizationLocator = null;

	private void configuraLocator() {
		try {
			if (authorizationLocator == null) {
				authorizationLocator = new AuthorizationManagerLitLocator();
				synchronized (UtenteJobServiceImpl.class) {
					ResourceBundle bundle = ResourceBundle.getBundle("config");
					authorizationLocator.setEndpointAddress("AuthorizationManagerSoap",
							bundle.getString("ws.endpointAddress.authorizationManager"));

				}
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void aggiornaUtenteRuolo() throws Throwable {

		configuraLocator();
		List<Utente> utenti = utenteDAO.leggiUtenti(false);
		@SuppressWarnings("unused")
		Utente currentUtente = null;

		ArrayList<String> utentiAggiornati = new ArrayList<String>();
		ArrayList<String> utentiNonAggiornati = new ArrayList<String>();

		InputStream is = null;
		String fileOutputAggiornamento = null;
		try {
			is = UtenteJobServiceImpl.class.getResourceAsStream("/log4j.properties");
			Properties properties = new Properties();
			properties.load(is);

			fileOutputAggiornamento = properties.getProperty("log4j.appender.LOGFILE.File");
			File filePath = new File(fileOutputAggiornamento);
			String dir = filePath.getAbsolutePath().replaceAll(filePath.getName(), "");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
			String replaceString = String.format("_%s", sdf.format(new Date()));
			fileOutputAggiornamento = String.format("%s/AggiornamentoUtente%s.log", dir, replaceString);
		} catch (Exception e) {
			logger.error("aggiornaUtenteRuolo()", e);
			throw new Exception(String.format("Properties non lette %s", e.toString()));
		}

		try {

			if (fileOutputAggiornamento == null)
				throw new Exception(String.format("File di output aggiornamento utente-ruolo non definito"));

			SessionFactory sessionFactory = (SessionFactory) SpringUtil.getBean("sessionFactory");

			String ruoliAttualiElenco = "";
			String ruoliElenco = "";
			String separatore = "";

			for (Utente utente : utenti) {
				boolean utenteModificato = false;
				ruoliAttualiElenco = "";
				ruoliElenco = "";
				separatore = "";

				int numeroRuoli = utente.getRUtenteGruppos().size();
				String userID = utente.getUseridUtil();
				logger.info("Address : " + authorizationLocator.getAuthorizationManagerSoapAddress());
				String[] ruoliSSO = authorizationLocator.getAuthorizationManagerSoap()
						.getUserRoles(Costanti.SSO_APPLICATION_NAME, userID);
				if (ruoliSSO != null && ruoliSSO.length > 0) {

					utenteModificato = (numeroRuoli != ruoliSSO.length);
					currentUtente = utente;

					ArrayList<String> ruoliAttuali = new ArrayList<String>();
					Set<RUtenteGruppo> rUtenteGruppos = utente.getRUtenteGruppos();
					// RECUPERO RUOLI ATTUALI SE HANNO STESSA NUMEROSITA'
					for (RUtenteGruppo rUtenteGruppo : rUtenteGruppos) {
						ruoliAttuali.add(rUtenteGruppo.getGruppoUtente().getCodice());
						ruoliAttualiElenco += String.format("%s%s", separatore,
								rUtenteGruppo.getGruppoUtente().getCodice());
						separatore = ",";
					}

					separatore = "";
					for (String ruoloSSO : ruoliSSO) {
						if (ruoliAttuali.contains(ruoloSSO))
							ruoliAttuali.remove(ruoloSSO);
						else
							utenteModificato = true;

						ruoliElenco += String.format("%s%s", separatore, ruoloSSO);
						separatore = ",";
					}
					utenteModificato = utenteModificato || ruoliAttuali.size() > 0;

					if (utenteModificato) {
						// ELIMINAZIONE RUOLI
						for (RUtenteGruppo rUtenteGruppo : rUtenteGruppos) {
							utenteDAO.eliminaRUtenteGruppo(rUtenteGruppo);
							ruoliAttuali.add(rUtenteGruppo.getGruppoUtente().getCodice());
						}
						sessionFactory.getCurrentSession().flush();

						// INSERIMENTO NUOVI RUOLI
						for (String ruolo : ruoliSSO) {
							GruppoUtente gu = gruppoUtenteDAO.getGruppoUtente(ruolo);
							RUtenteGruppo rug = new RUtenteGruppo();
							rug.setUtente(utente);
							rug.setGruppoUtente(gu);
							utenteDAO.insertRUtenteGruppo(rug);
						}

						utentiAggiornati.add(utente.getUseridUtil() + "\nRuoli Precedenti:" + ruoliElenco
								+ " - Ruoli Attuali:" + ruoliAttualiElenco);
					}
				} else {
					utentiNonAggiornati.add(utente.getUseridUtil() + "\nRuoli Precedenti:" + ruoliElenco);
				}
			}

			File file = new File(fileOutputAggiornamento);
			FileWriter outFile = new FileWriter(file);
			final PrintWriter out = new PrintWriter(outFile);
			out.println("UTENTI AGGIORNATI");
			for (String utente : utentiAggiornati)
				out.println(utente);

			out.println("\nUTENTI NON AGGIORNATI");
			for (String utente : utentiNonAggiornati)
				out.println(utente);

			out.close();

		} catch (Exception ex) {
			throw new Exception(String.format("Errore aggiornamento %s", ex.toString()));
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void inviaMailProtocollo() throws Throwable {
		
		emailNotificationService.inviaNotificaProtocollo();
		
	}
}
