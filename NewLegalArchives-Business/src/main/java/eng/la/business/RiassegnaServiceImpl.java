package eng.la.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import eng.la.business.mail.EmailNotificationService;
import eng.la.business.websocket.WebSocketPublisher;
import eng.la.business.workflow.FascicoloWfService;
import eng.la.business.workflow.IncaricoWfService;
import eng.la.business.workflow.ProformaWfService;
import eng.la.business.workflow.SchedaFondoRischiWfService;
import eng.la.business.workflow.StepWfService;
import eng.la.model.Autorizzazione;
import eng.la.model.Fascicolo;
import eng.la.model.Incarico;
import eng.la.model.Proforma;
import eng.la.model.RUtenteFascicolo;
import eng.la.model.SchedaFondoRischi;
import eng.la.model.SettoreGiuridico;
import eng.la.model.Societa;
import eng.la.model.TipoAutorizzazione;
import eng.la.model.TipologiaFascicolo;
import eng.la.model.Utente;
import eng.la.model.mail.Mail;
import eng.la.model.rest.Event;
import eng.la.model.rest.RiassegnaRest;
import eng.la.model.rest.StepWFRest;
import eng.la.model.view.FascicoloView;
import eng.la.model.view.FascicoloWfView;
import eng.la.model.view.IncaricoWfView;
import eng.la.model.view.ProformaView;
import eng.la.model.view.ProformaWfView;
import eng.la.model.view.SchedaFondoRischiWfView;
import eng.la.model.view.StepWfView;
import eng.la.model.view.UtenteView;
import eng.la.persistence.AnagraficaStatiTipiDAO;
import eng.la.persistence.AutorizzazioneDAO;
import eng.la.persistence.CostantiDAO;
import eng.la.persistence.FascicoloDAO;
import eng.la.persistence.SettoreGiuridicoDAO;
import eng.la.persistence.SocietaDAO;
import eng.la.persistence.TipologiaFascicoloDAO;
import eng.la.persistence.UtenteDAO;
import eng.la.util.SpringUtil;
import eng.la.util.costants.Costanti;

@Service("riassegnaService")
public class RiassegnaServiceImpl extends BaseService<Fascicolo,FascicoloView> implements RiassegnaService {
	private static final Logger logger = Logger.getLogger(RiassegnaServiceImpl.class);
	@Autowired
	private UtenteDAO utenteDao;
	@Autowired
	private TipologiaFascicoloDAO tipologiaFascicoloDAO;
	@Autowired
	private SettoreGiuridicoDAO settoreGiuridicoDAO;
	@Autowired
	private SocietaDAO societaDAO;
	@Autowired
	private FascicoloDAO fascicoloDAO;
	@Autowired
	private AutorizzazioneDAO autorizzazioneDAO;
	@Autowired
	private AnagraficaStatiTipiDAO anagraficaStatiTipiDAO;

	public AnagraficaStatiTipiDAO getAnagraficaStatiTipiDAO() {
		return anagraficaStatiTipiDAO;
	}

	public void setAutorizzazioneDAO(AutorizzazioneDAO autorizzazioneDAO) {
		this.autorizzazioneDAO = autorizzazioneDAO;
	}

	public void setFascicoloDAO(FascicoloDAO fascicoloDAO) {
		this.fascicoloDAO = fascicoloDAO;
	}

	public void setSocietaDAO(SocietaDAO societaDAO) {
		this.societaDAO = societaDAO;
	}

	public void setSettoreGiuridicoDAO(SettoreGiuridicoDAO settoreGiuridicoDAO) {
		this.settoreGiuridicoDAO = settoreGiuridicoDAO;
	}

	public void setUtenteDao(UtenteDAO utenteDao) {
		this.utenteDao = utenteDao;
	}

	public void setTipologiaFascicoloDAO(TipologiaFascicoloDAO tipologiaFascicoloDAO) {
		this.tipologiaFascicoloDAO = tipologiaFascicoloDAO;
	}

	@Override
	public List<Utente> getListaLegaleInternoOwnerFascicolo(long idFascicolo) throws Throwable {

		return utenteDao.getListaLegaleInternoOwnerFascicolo(idFascicolo);
	}

	@Override
	public List<RiassegnaRest> getListaLegaleInternoOwnerFascicoloRest(long idFascicolo) throws Throwable {

		List<Utente> lista = utenteDao.getListaLegaleInternoOwnerFascicolo(idFascicolo);
		List<RiassegnaRest> listaRest = new ArrayList<RiassegnaRest>();
		if (lista != null) {
			for (Utente u : lista) {
				RiassegnaRest rest = new RiassegnaRest();
				rest.setOwner(u.getNominativoUtil());
				rest.setIdOwner(u.getUseridUtil());
				listaRest.add(rest);
			}
		}
		return listaRest;
	}

	@Override
	public List<TipologiaFascicolo> getListaTipologiaFascicolo() throws Throwable {

		return tipologiaFascicoloDAO.leggi("IT", true);
	}

	@Override
	public List<SettoreGiuridico> getListaSettoreGiuridico() throws Throwable {

		return settoreGiuridicoDAO.leggi("IT");
	}

	@Override
	public List<Societa> getListaSocieta() throws Throwable {

		return societaDAO.leggi(false);
	}

	@Override
	public List<Fascicolo> getListaFascicoliXRiassegna(String matricolaUtil) throws Throwable {

		return fascicoloDAO.getListaFascicoliXRiassegna(matricolaUtil);
	}

	@Override
	public List<BigDecimal> getIDFascicoliXRiassegna(String matricolaUtil, String idSocieta,
			String idTipologiaFascicolo, String idSettoreGiuridico, String nomeFascicolo) throws Throwable {

		return fascicoloDAO.getIDFascicoliXRiassegna(matricolaUtil, idSocieta, idTipologiaFascicolo, idSettoreGiuridico,
				nomeFascicolo);
	}

	@Override
	public List<BigDecimal> getIDFascicoliXEstendiPermessi(String amministratore, String matricolaUtil, String legaleInterno, String idSocieta,
			String idTipologiaFascicolo, String idSettoreGiuridico, String nomeFascicolo) throws Throwable {

		return fascicoloDAO.getIDFascicoliXEstendiPermessi(amministratore, matricolaUtil, legaleInterno, idSocieta, idTipologiaFascicolo, idSettoreGiuridico,
				nomeFascicolo);
	}

	@Override
	public Fascicolo getFascicoli(long idFascicolo) throws Throwable {

		return fascicoloDAO.leggi(idFascicolo, FetchMode.JOIN);
	}

	@Override
	public List<Utente> getListaUtentiNotAmmistrativiNotAmministratore() throws Throwable {
		return utenteDao.getListaUtentiNotAmmistrativiNotAmministratore();
	}

	@Override
	public void insertRUtenteFascicolo(RUtenteFascicolo vo) throws Throwable {
		utenteDao.insertRUtenteFascicolo(vo);

	}

	@Override
	public void updateRUtenteFascicoloDataCancellazione(RUtenteFascicolo vo) throws Throwable {
		utenteDao.updateRUtenteFascicoloDataCancellazione(vo);

	}

	@Override
	public Utente getUtente(String matricolaUtil) throws Throwable {

		return utenteDao.leggiUtenteDaMatricola(matricolaUtil);
	}

	@Override
	protected Class<Fascicolo> leggiClassVO() {
		return null;
	}

	@Override
	protected Class<FascicoloView> leggiClassView() {
		return null;
	}

	/**
	 * Aggiorna le autorizzazioni sul fascicolo.
	 * 
	 * @param idFascicolo
	 *            identificatico del fascicolo
	 * @param oldOwnerUserId
	 *            userId owner uscente
	 * @param newOwnerUserId
	 *            userId owner entrante
	 * @return void
	 */
	@Override
	public void riassegnaAutorizzazioni(long idFascicolo, String oldOwnerUserId, String newOwnerUserId)
			throws Throwable {

		// cancellazione autorizzazioni relative owner uscente
		autorizzazioneDAO.cancellaAutorizzazioniPerCambioOwner(idFascicolo, oldOwnerUserId);

		// inserimento autorizzazioni puntuali owner entrante
		Autorizzazione autorizzazioneUtente = new Autorizzazione();
		autorizzazioneUtente.setIdEntita(idFascicolo);
		TipoAutorizzazione tipoAutorizzazioneUtente = anagraficaStatiTipiDAO.leggiTipoAutorizzazione(
				CostantiDAO.TIPO_AUTORIZZAZIONE_UTENTE_SCRITTURA, Locale.ITALIAN.getLanguage().toUpperCase());
		autorizzazioneUtente.setTipoAutorizzazione(tipoAutorizzazioneUtente);
		autorizzazioneUtente.setNomeClasse(Costanti.TIPO_ENTITA_FASCICOLO);
		Utente utente = utenteDao.leggiUtenteDaUserId(newOwnerUserId);
		autorizzazioneUtente.setUtente(utente);
		autorizzazioneDAO.inserisci(autorizzazioneUtente);

		// inserimento autorizzazioni responsabili owner entrante
		Autorizzazione autorizzazioneResponsabile = new Autorizzazione();
		autorizzazioneResponsabile.setIdEntita(idFascicolo);
		TipoAutorizzazione tipoAutorizzazioneResponsabile = anagraficaStatiTipiDAO.leggiTipoAutorizzazione(
				CostantiDAO.TIPO_AUTORIZZAZIONE_GRUPPO_LETTURA, Locale.ITALIAN.getLanguage().toUpperCase());
		autorizzazioneResponsabile.setTipoAutorizzazione(tipoAutorizzazioneResponsabile);
		autorizzazioneResponsabile.setNomeClasse(Costanti.TIPO_ENTITA_FASCICOLO);
		autorizzazioneResponsabile.setUtenteForResp(utente);
		autorizzazioneDAO.inserisci(autorizzazioneResponsabile);

	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public boolean riassegna(long idFascicolo, String oldOwnerUserId, String newOwnerUserId) throws Throwable {
		try {
			UtenteService utenteService = (UtenteService) SpringUtil.getBean("utenteService");
			FascicoloService fascicoloService = (FascicoloService) SpringUtil.getBean("fascicoloService");
			RiassegnaService riassegnaService = (RiassegnaService) SpringUtil.getBean("riassegnaService");
			FascicoloWfService fascicoloWfService = (FascicoloWfService) SpringUtil.getBean("fascicoloWfService");
			StepWfService stepWfService = (StepWfService) SpringUtil.getBean("stepWfService");
			EmailNotificationService emailNotificationService = (EmailNotificationService) SpringUtil
					.getBean("emailNotificationService");
			IncaricoWfService incaricoWfService = (IncaricoWfService) SpringUtil.getBean("incaricoWfService");
			ProformaWfService proformaWfService = (ProformaWfService) SpringUtil.getBean("proformaWfService");
			ProformaService proformaService = (ProformaService) SpringUtil.getBean("proformaService");
			SchedaFondoRischiWfService schedaFondoRischiWfService = (SchedaFondoRischiWfService) SpringUtil.getBean("schedaFondoRischiWfService");
			SessionFactory sessionFactory = (SessionFactory) SpringUtil.getBean("sessionFactory");
			UtenteView utenteView = utenteService.leggiUtenteDaUserId(oldOwnerUserId);

			// prelevo il nuovo utente
			Utente utenteNuovo = utenteService.leggiUtenteDaUserId(newOwnerUserId).getVo();
			logger.info("@@DDS riassegna service");
			Fascicolo fascicolo = fascicoloService.leggi(idFascicolo).getVo();
			// aggiorno il campo legale interno dell'entit� fascicolo
			FascicoloView fascicoloView = new FascicoloView();

			fascicolo.setLegaleInterno(utenteNuovo.getUseridUtil());
			fascicoloView.setVo(fascicolo);
			fascicoloService.aggiornaFascicolo(fascicoloView);

			if (fascicolo.getRUtenteFascicolos() != null) {

				for (RUtenteFascicolo ruf : fascicolo.getRUtenteFascicolos()) {

					if (ruf.getDataCancellazione() == null) {
						ruf.setDataCancellazione(new Date());
						riassegnaService.updateRUtenteFascicoloDataCancellazione(ruf);
						sessionFactory.getCurrentSession().flush();
					}

				}
			} else {
				fascicolo.setRUtenteFascicolos(new HashSet<RUtenteFascicolo>());
			}
			// Lego il Fascicolo al Nuovo Utente
			RUtenteFascicolo rUtenteFascicolo = new RUtenteFascicolo();
			rUtenteFascicolo.setFascicolo(fascicolo);
			rUtenteFascicolo.setUtente(utenteNuovo);
			riassegnaService.insertRUtenteFascicolo(rUtenteFascicolo);
			fascicolo.getRUtenteFascicolos().add(rUtenteFascicolo);
			FascicoloWfView fascicoloWfView = fascicoloWfService.leggiWorkflowInCorso(idFascicolo);
			if (fascicoloWfView != null) {

				StepWfView stepWfView = stepWfService.leggiStepCorrente(fascicoloWfView.getVo().getId(),
						CostantiDAO.CHIUSURA_FASCICOLO);

				// PRELEVO IL VECCHIO UTENTE PER LA NOTIFICA
				UtenteView utenteViewOld = utenteService.leggiAssegnatarioWfFascicolo(idFascicolo);
				if (stepWfView != null) {
					if (fascicoloWfService.riportaInCompletatoWorkflow(idFascicolo,
							utenteView.getVo().getUseridUtil())) {
						// INVIO LA NOTIFICA AL VECCHIO UTENTE
						if (utenteViewOld != null) {
							StepWFRest step = new StepWFRest();
							step.setId(0);
							Event event = WebSocketPublisher.getInstance().createEvent(
									Costanti.WEBSOCKET_EVENTO_NOTIFICHE, step, utenteViewOld.getVo().getUseridUtil());
							WebSocketPublisher.getInstance().publishEvent(event);
						}

						// riavvio il workFlowp
						if (fascicoloWfService.avviaWorkflow(idFascicolo, utenteNuovo.getUseridUtil())) {
							// NUOVO ASSEGNATARIO
							UtenteView assegnatario = utenteService.leggiAssegnatarioWfFascicolo(idFascicolo);
							// INVIO NOTIFICA AL NUOVO ASSEGNATARIO
							if (assegnatario != null) {
								StepWFRest step = new StepWFRest();
								step.setId(0);
								Event event = WebSocketPublisher.getInstance().createEvent(
										Costanti.WEBSOCKET_EVENTO_NOTIFICHE, step,
										assegnatario.getVo().getUseridUtil());
								WebSocketPublisher.getInstance().publishEvent(event);
							}

							try {
								// INVIO EMAIL AL NUOVO ASSEGNATARIO
								emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, CostantiDAO.FASCICOLO,
										idFascicolo, Locale.ITALIAN.getLanguage().toUpperCase(),
										utenteNuovo.getUseridUtil());
							} catch (Exception e) {
							}

						} // fine riporta in compltetato workflow

					} // Fine riportaInCompletatoWorkflow

				}

			} // fine gestione workflow

			// INCARICO
			for (Incarico incarico : fascicolo.getIncaricos()) {
				if (incarico != null) {
					long idIncarico = incarico.getId();
					IncaricoWfView incaricoWfView = incaricoWfService.leggiWorkflowInCorso(idIncarico);
					if (incaricoWfView != null) {
						StepWfView stepWfView = stepWfService.leggiStepCorrente(incaricoWfView.getVo().getId(),
								CostantiDAO.AUTORIZZAZIONE_INCARICO);
						if (stepWfView != null) {
							String tipoAssegnazione = stepWfView.getVo().getConfigurazioneStepWf()
									.getTipoAssegnazione();
							if (tipoAssegnazione != null && !tipoAssegnazione.equalsIgnoreCase("MAN")) {
								boolean isArbitrale = false;
								// PRELEVO IL VECCHIO UTENTE PER LA NOTIFICA
								UtenteView utenteViewOld = utenteService.leggiAssegnatarioWfIncarico(idIncarico);
								if (incarico.getCollegioArbitrale() == null
										|| incarico.getCollegioArbitrale().equalsIgnoreCase("F")) {
									isArbitrale = false;
								}
								if (incarico.getCollegioArbitrale().equalsIgnoreCase("T")) {
									isArbitrale = true;
								}
								if (incaricoWfService.riportaInBozzaWorkflow(idIncarico,
										utenteView.getVo().getUseridUtil(), isArbitrale)) {
									// INVIO LA NOTIFICA AL VECCHIO UTENTE
									if (utenteViewOld != null) {
										StepWFRest step = new StepWFRest();
										step.setId(0);
										Event event = WebSocketPublisher.getInstance().createEvent(
												Costanti.WEBSOCKET_EVENTO_NOTIFICHE, step,
												utenteViewOld.getVo().getUseridUtil());
										WebSocketPublisher.getInstance().publishEvent(event);
									}

									if (incaricoWfService.avviaWorkflow(idIncarico, utenteNuovo.getUseridUtil(),
											isArbitrale)) {
										// NUOVO ASSEGNATARIO
										UtenteView assegnatario = utenteService.leggiAssegnatarioWfIncarico(idIncarico);
										// INVIO NOTIFICA AL NUOVO ASSEGNATARIO
										if (assegnatario != null) {

											StepWFRest step = new StepWFRest();
											step.setId(0);
											Event event = WebSocketPublisher.getInstance().createEvent(
													Costanti.WEBSOCKET_EVENTO_NOTIFICHE, step,
													assegnatario.getVo().getUseridUtil());
											WebSocketPublisher.getInstance().publishEvent(event);

											try {
												// INVIO EMAIL AL NUOVO
												// ASSEGNATARIO
												emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO,
														isArbitrale ? CostantiDAO.ARBITRATO : CostantiDAO.INCARICO,
																idIncarico, Locale.ITALIAN.getLanguage().toUpperCase(),
																utenteNuovo.getUseridUtil());

											} // fine invio e-mail
											catch (Exception e) {
												System.out.println("Errore in invio e-mail" + e);
											}
										}

									} // fine avvio nuovo workflow

								} // fine riporto in bozza

							}

						}

					} // fine gestione workflow

					// PROFORMA
					List<ProformaView> proformas = proformaService.leggiProformaAssociatiAIncarico(idIncarico);
					if( proformas != null ){
						for (ProformaView proformaView : proformas) {
							if (proformaView != null && proformaView.getVo() != null) {

								Proforma proforma = proformaView.getVo();
								long idProforma = proforma.getId();

								ProformaWfView proformaWfView = proformaWfService.leggiWorkflowInCorso(idProforma);

								if (proformaWfView != null) {

									StepWfView stwfView = stepWfService.leggiStepCorrente(proformaWfView.getVo().getId(),
											CostantiDAO.AUTORIZZAZIONE_PROFORMA);
									String tipoAsseg = stwfView.getVo().getConfigurazioneStepWf().getTipoAssegnazione();

									////////////////////////////////

									if (tipoAsseg != null && !tipoAsseg.equalsIgnoreCase("MAN")) {
										// PRELEVO IL VECCHIO UTENTE PER LA NOTIFICA
										UtenteView utenteViewOld = utenteService.leggiAssegnatarioWfProforma(idProforma);
										if (proformaWfService.riportaInBozzaWorkflow(idIncarico,
												utenteView.getVo().getUseridUtil())) {
											// INVIO LA NOTIFICA AL VECCHIO UTENTE
											if (utenteViewOld != null) {
												StepWFRest step = new StepWFRest();
												step.setId(0);
												Event event = WebSocketPublisher.getInstance().createEvent(
														Costanti.WEBSOCKET_EVENTO_NOTIFICHE, step,
														utenteViewOld.getVo().getUseridUtil());
												WebSocketPublisher.getInstance().publishEvent(event);

											}
											if (proformaWfService.avviaWorkflow(idIncarico, utenteNuovo.getUseridUtil())) {
												// NUOVO ASSEGNATARIO
												UtenteView assegnatario = utenteService
														.leggiAssegnatarioWfProforma(idProforma);
												// INVIO NOTIFICA AL NUOVO
												// ASSEGNATARIO
												if (assegnatario != null) {
													StepWFRest step = new StepWFRest();
													step.setId(0);
													Event event = WebSocketPublisher.getInstance().createEvent(
															Costanti.WEBSOCKET_EVENTO_NOTIFICHE, step,
															assegnatario.getVo().getUseridUtil());
													WebSocketPublisher.getInstance().publishEvent(event);

													try {
														// INVIO EMAIL AL NUOVO
														// ASSEGNATARIO
														emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO,
																CostantiDAO.PROFORMA, idProforma,
																Locale.ITALIAN.getLanguage().toUpperCase(),
																utenteNuovo.getUseridUtil());

													} catch (Exception e) {
														System.out.println("Errore in invio e-mail" + e);
													}
												}
											} // fine avvio workflow

										} // fine riporto in bozza

									}

								} // fine gestione workflow

							}

						}
					}
				} // fine singolo incarico

			} // fine ciclo incarico


			// SCHEDA FONDO RISCHI
			if(fascicolo.getSchedaFondoRischi() != null) {

				SchedaFondoRischi scheda = fascicolo.getSchedaFondoRischi();

				long idScheda = scheda.getId();
				SchedaFondoRischiWfView schedaWfView = schedaFondoRischiWfService.leggiWorkflowInCorso(idScheda);
				if (schedaWfView != null) {
					StepWfView stepWfView = stepWfService.leggiStepCorrente(schedaWfView.getVo().getId(),
							CostantiDAO.AUTORIZZAZIONE_SCHEDA_FONDO_RISCHI);
					if (stepWfView != null) {
						String tipoAssegnazione = stepWfView.getVo().getConfigurazioneStepWf()
								.getTipoAssegnazione();
						if (tipoAssegnazione != null && !tipoAssegnazione.equalsIgnoreCase("MAN")) {
							// PRELEVO IL VECCHIO UTENTE PER LA NOTIFICA
							List<UtenteView> utentiViewOld = utenteService.leggiAssegnatarioWfSchedaFondoRischi(idScheda);
							UtenteView utenteViewOld = utentiViewOld.get(0);

							if (schedaFondoRischiWfService.riportaInBozzaWorkflow(idScheda, utenteView.getVo().getUseridUtil())) {
								// INVIO LA NOTIFICA AL VECCHIO UTENTE
								if (utenteViewOld != null) {
									StepWFRest step = new StepWFRest();
									step.setId(0);
									Event event = WebSocketPublisher.getInstance().createEvent(
											Costanti.WEBSOCKET_EVENTO_NOTIFICHE, step,
											utenteViewOld.getVo().getUseridUtil());
									WebSocketPublisher.getInstance().publishEvent(event);
								}

								if (schedaFondoRischiWfService.avviaWorkflow(idScheda, utenteNuovo.getUseridUtil())) {
									// NUOVO ASSEGNATARIO
									List<UtenteView> assegnatari = utenteService.leggiAssegnatarioWfSchedaFondoRischi(idScheda);
									UtenteView assegnatario = assegnatari.get(0);

									// INVIO NOTIFICA AL NUOVO ASSEGNATARIO
									if (assegnatario != null) {

										StepWFRest step = new StepWFRest();
										step.setId(0);
										Event event = WebSocketPublisher.getInstance().createEvent(
												Costanti.WEBSOCKET_EVENTO_NOTIFICHE, step,
												assegnatario.getVo().getUseridUtil());
										WebSocketPublisher.getInstance().publishEvent(event);

										try {
											// INVIO EMAIL AL NUOVO
											// ASSEGNATARIO
											emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, CostantiDAO.SCHEDA_FONDO_RISCHI,
													idScheda, Locale.ITALIAN.getLanguage().toUpperCase(),
													utenteNuovo.getUseridUtil());

										} // fine invio e-mail
										catch (Exception e) {
											System.out.println("Errore in invio e-mail" + e);
										}
									}
								} // fine avvio nuovo workflow
							} // fine riporto in bozza
						}
					}
				} // fine gestione workflow
			} // fine ciclo scheda fondo rischi

			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}


	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public String riassegnazioneMultipla(String idFas, String owner, HttpServletRequest request, Model model,
			Locale locale) {
		StringBuilder builder = new StringBuilder();
		try {
			UtenteService utenteService = (UtenteService) SpringUtil.getBean("utenteService");
			FascicoloService fascicoloService = (FascicoloService) SpringUtil.getBean("fascicoloService");
			RiassegnaService riassegnaService = (RiassegnaService) SpringUtil.getBean("riassegnaService");
			FascicoloWfService fascicoloWfService = (FascicoloWfService) SpringUtil.getBean("fascicoloWfService");
			StepWfService stepWfService = (StepWfService) SpringUtil.getBean("stepWfService");
			EmailNotificationService emailNotificationService = (EmailNotificationService) SpringUtil
					.getBean("emailNotificationService");
			IncaricoWfService incaricoWfService = (IncaricoWfService) SpringUtil.getBean("incaricoWfService");
			ProformaWfService proformaWfService = (ProformaWfService) SpringUtil.getBean("proformaWfService");
			ProformaService proformaService = (ProformaService) SpringUtil.getBean("proformaService");
			SchedaFondoRischiWfService schedaFondoRischiWfService = (SchedaFondoRischiWfService) SpringUtil.getBean("schedaFondoRischiWfService");
			SessionFactory sessionFactory = (SessionFactory) SpringUtil.getBean("sessionFactory");
			UtenteView utenteView = (UtenteView) request.getSession()
					.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			Utente utenteOld = null;
			List<Fascicolo> fascicolos = new LinkedList<Fascicolo>();
			// Adesso vale per tutti gli UTENTI - FASCICOLO - CAMBIO OWNER MASSIVO
			if (utenteView != null) {

				// prelevo il nuovo utente
				Utente utenteNuovo = riassegnaService.getUtente(owner);
				builder.append("UTENTE::" + utenteNuovo.getNominativoUtil() + "  " + utenteNuovo.getUseridUtil());
				String[] fascicoliArray = idFas.split("-");
				if (fascicoliArray != null && fascicoliArray.length > 0) {

					for (int i = 0; i < fascicoliArray.length; i++) {
						long idFascicolo = new Long(fascicoliArray[i]).longValue();
						builder.append("IDFASCICOLO::" + idFascicolo);
						/**
						 * CR Varianti 3 - Invio mail cambio owner
						 */
						Fascicolo fascicolo = riassegnaService.getFascicoli(idFascicolo);
						fascicolos.add(fascicolo);
						if (fascicolo.getRUtenteFascicolos() != null && fascicolo.getRUtenteFascicolos().size()>0 && utenteOld == null) {
							for (RUtenteFascicolo f : fascicolo.getRUtenteFascicolos()) {
								if (f.getDataCancellazione() == null) {
									utenteOld = f.getUtente();
								}
							}
						}
						//CONTROLLO SE AMMINISTRATORE O OWNER

						if(utenteView.isAmministratore() || fascicolo.getLegaleInterno().equalsIgnoreCase(utenteView.getVo().getUseridUtil())){

							// aggiorno il campo legale interno dell'entit�
							// fascicolo
							FascicoloView fascicoloView = new FascicoloView();

							fascicolo.setLegaleInterno(utenteNuovo.getUseridUtil());
							fascicoloView.setVo(fascicolo);
							fascicoloService.aggiornaFascicolo(fascicoloView);

							if (fascicolo.getRUtenteFascicolos() != null) {
								for (RUtenteFascicolo ruf : fascicolo.getRUtenteFascicolos()) {

									if (ruf.getDataCancellazione() == null) {
										ruf.setDataCancellazione(new Date());
										riassegnaService.updateRUtenteFascicoloDataCancellazione(ruf);

										sessionFactory.getCurrentSession().flush();
										builder.append("UPDATE-RUtenteFascicolo::" + ruf.getId());
									}

								}
							} else {
								fascicolo.setRUtenteFascicolos(new HashSet<RUtenteFascicolo>());
							}
							// Lego il Fascicolo al Nuovo Utente
							RUtenteFascicolo rUtenteFascicolo = new RUtenteFascicolo();
							rUtenteFascicolo.setFascicolo(fascicolo);
							rUtenteFascicolo.setUtente(utenteNuovo);
							riassegnaService.insertRUtenteFascicolo(rUtenteFascicolo);
							fascicolo.getRUtenteFascicolos().add(rUtenteFascicolo);
							builder.append("INSERT-RUtenteFascicolo::" + rUtenteFascicolo.getId());
							FascicoloWfView fascicoloWfView = fascicoloWfService.leggiWorkflowInCorso(idFascicolo);
							if (fascicoloWfView != null) {

								StepWfView stepWfView = stepWfService.leggiStepCorrente(fascicoloWfView.getVo().getId(),
										CostantiDAO.CHIUSURA_FASCICOLO);

								// PRELEVO IL VECCHIO UTENTE PER LA NOTIFICA
								UtenteView utenteViewOld = utenteService.leggiAssegnatarioWfFascicolo(idFascicolo);
								builder.append("utenteViewOld-Fascicolo::" + utenteViewOld.getVo().getMatricolaUtil());
								if (stepWfView != null) {
									if (fascicoloWfService.riportaInCompletatoWorkflow(idFascicolo,
											utenteView.getVo().getUseridUtil())) {
										// INVIO LA NOTIFICA AL VECCHIO UTENTE
										if (utenteViewOld != null) {
											StepWFRest step = new StepWFRest();
											step.setId(0);
											Event event = WebSocketPublisher.getInstance().createEvent(
													Costanti.WEBSOCKET_EVENTO_NOTIFICHE, step,
													utenteViewOld.getVo().getUseridUtil());
											WebSocketPublisher.getInstance().publishEvent(event);
											builder.append("WebSocketPublisher-Fascicolo::"
													+ utenteViewOld.getVo().getMatricolaUtil());
										}

										// riavvio il workFlowp
										if (fascicoloWfService.avviaWorkflow(idFascicolo, utenteNuovo.getUseridUtil())) {
											// NUOVO ASSEGNATARIO
											UtenteView assegnatario = utenteService
													.leggiAssegnatarioWfFascicolo(idFascicolo);
											// INVIO NOTIFICA AL NUOVO ASSEGNATARIO
											if (assegnatario != null) {
												StepWFRest step = new StepWFRest();
												step.setId(0);
												Event event = WebSocketPublisher.getInstance().createEvent(
														Costanti.WEBSOCKET_EVENTO_NOTIFICHE, step,
														assegnatario.getVo().getUseridUtil());
												WebSocketPublisher.getInstance().publishEvent(event);
												builder.append("WebSocketPublisher-NUOVO-Fascicolo::"
														+ assegnatario.getVo().getMatricolaUtil());
											}

											try {
												// INVIO EMAIL AL NUOVO ASSEGNATARIO
												emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO,
														CostantiDAO.FASCICOLO, idFascicolo,
														locale.getLanguage().toUpperCase(), utenteNuovo.getUseridUtil());
												builder.append("emailNotificationService-Fascicolo::"
														+ assegnatario.getVo().getMatricolaUtil());
											} catch (Exception e) {
												builder.append("emailNotificationService-Fascicolo::" + e.getMessage());
											}

										} // fine riporta in compltetato workflow

									} // Fine riportaInCompletatoWorkflow

								}

							} // fine gestione workflow

							// INCARICO
							for (Incarico incarico : fascicolo.getIncaricos()) {
								if (incarico != null) {
									long idIncarico = incarico.getId();
									IncaricoWfView incaricoWfView = incaricoWfService.leggiWorkflowInCorso(idIncarico);
									if (incaricoWfView != null) {
										StepWfView stepWfView = stepWfService.leggiStepCorrente(
												incaricoWfView.getVo().getId(), CostantiDAO.AUTORIZZAZIONE_INCARICO);
										if (stepWfView != null) {
											String tipoAssegnazione = stepWfView.getVo().getConfigurazioneStepWf()
													.getTipoAssegnazione();
											if (tipoAssegnazione != null && !tipoAssegnazione.equalsIgnoreCase("MAN")) {
												boolean isArbitrale = false;
												// PRELEVO IL VECCHIO UTENTE PER LA
												// NOTIFICA
												UtenteView utenteViewOld = utenteService
														.leggiAssegnatarioWfIncarico(idIncarico);
												if (incarico.getCollegioArbitrale() == null
														|| incarico.getCollegioArbitrale().equalsIgnoreCase("F")) {
													isArbitrale = false;
												}
												if (incarico.getCollegioArbitrale().equalsIgnoreCase("T")) {
													isArbitrale = true;
												}
												if (incaricoWfService.riportaInBozzaWorkflow(idIncarico,
														utenteView.getVo().getUseridUtil(), isArbitrale)) {
													// INVIO LA NOTIFICA AL VECCHIO
													// UTENTE
													if (utenteViewOld != null) {
														StepWFRest step = new StepWFRest();
														step.setId(0);
														Event event = WebSocketPublisher.getInstance().createEvent(
																Costanti.WEBSOCKET_EVENTO_NOTIFICHE, step,
																utenteViewOld.getVo().getUseridUtil());
														WebSocketPublisher.getInstance().publishEvent(event);
													}

													if (incaricoWfService.avviaWorkflow(idIncarico,
															utenteNuovo.getUseridUtil(), isArbitrale)) {
														// NUOVO ASSEGNATARIO
														UtenteView assegnatario = utenteService
																.leggiAssegnatarioWfIncarico(idIncarico);
														// INVIO NOTIFICA AL NUOVO
														// ASSEGNATARIO
														if (assegnatario != null) {

															StepWFRest step = new StepWFRest();
															step.setId(0);
															Event event = WebSocketPublisher.getInstance().createEvent(
																	Costanti.WEBSOCKET_EVENTO_NOTIFICHE, step,
																	assegnatario.getVo().getUseridUtil());
															WebSocketPublisher.getInstance().publishEvent(event);

															try {
																// INVIO EMAIL AL
																// NUOVO
																// ASSEGNATARIO
																emailNotificationService.inviaNotifica(
																		CostantiDAO.AVANZAMENTO,
																		isArbitrale ? CostantiDAO.ARBITRATO
																				: CostantiDAO.INCARICO,
																				idIncarico, locale.getLanguage().toUpperCase(),
																				utenteNuovo.getUseridUtil());

															} // fine invio e-mail
															catch (Exception e) {
																System.out.println("Errore in invio e-mail" + e);
															}
														}

													} // fine avvio nuovo workflow

												} // fine riporto in bozza

											}

										}

									} // fine gestione workflow

									// PROFORMA
									List<ProformaView> proformas = proformaService.leggiProformaAssociatiAIncarico(idIncarico);

									if (proformas != null) {

										for (ProformaView proformaView : proformas) {

											if (proformaView != null && proformaView.getVo() != null) {

												Proforma proforma = proformaView.getVo();
												long idProforma = proforma.getId();

												ProformaWfView proformaWfView = proformaWfService.leggiWorkflowInCorso(idProforma);

												if (proformaWfView != null) {

													StepWfView stwfView = stepWfService.leggiStepCorrente(
															proformaWfView.getVo().getId(),
															CostantiDAO.AUTORIZZAZIONE_PROFORMA);
													String tipoAsseg = stwfView.getVo().getConfigurazioneStepWf()
															.getTipoAssegnazione();

													////////////////////////////////

													if (tipoAsseg != null && !tipoAsseg.equalsIgnoreCase("MAN")) {
														
														// PRELEVO IL VECCHIO UTENTE PER LA NOTIFICA
														UtenteView utenteViewOld = utenteService
																.leggiAssegnatarioWfProforma(idProforma);
														if (proformaWfService.riportaInBozzaWorkflow(idIncarico,
																utenteView.getVo().getUseridUtil())) {
															// INVIO LA NOTIFICA AL VECCHIO UTENTE
															if (utenteViewOld != null) {
																StepWFRest step = new StepWFRest();
																step.setId(0);
																Event event = WebSocketPublisher.getInstance().createEvent(
																		Costanti.WEBSOCKET_EVENTO_NOTIFICHE, step,
																		utenteViewOld.getVo().getUseridUtil());
																WebSocketPublisher.getInstance().publishEvent(event);

															}
															if (proformaWfService.avviaWorkflow(idIncarico,
																	utenteNuovo.getUseridUtil())) {
																// NUOVO ASSEGNATARIO
																UtenteView assegnatario = utenteService
																		.leggiAssegnatarioWfProforma(idProforma);
																// INVIO NOTIFICA AL NUOVO ASSEGNATARIO
																if (assegnatario != null) {
																	StepWFRest step = new StepWFRest();
																	step.setId(0);
																	Event event = WebSocketPublisher.getInstance()
																			.createEvent(
																					Costanti.WEBSOCKET_EVENTO_NOTIFICHE,
																					step,
																					assegnatario.getVo().getUseridUtil());
																	WebSocketPublisher.getInstance().publishEvent(event);

																	try {
																		// INVIO EMAIL AL NUOVO ASSEGNATARIO
																		emailNotificationService.inviaNotifica(
																				CostantiDAO.AVANZAMENTO,
																				CostantiDAO.PROFORMA, idProforma,
																				locale.getLanguage().toUpperCase(),
																				utenteNuovo.getUseridUtil());

																	} catch (Exception e) {
																		System.out.println("Errore in invio e-mail" + e);
																	}
																}
															} // fine avvio workflow

														} // fine riporto in bozza
													}
												} // fine gestione workflow
											}
										}
									}
								} // fine singolo incarico

							} // fine ciclo incarico

							// SCHEDA FONDO RISCHI
							if(fascicolo.getSchedaFondoRischi() != null) {
								long idScheda = fascicolo.getSchedaFondoRischi().getId();
								SchedaFondoRischiWfView schedaWfView = schedaFondoRischiWfService.leggiWorkflowInCorso(idScheda);
								if (schedaWfView != null) {
									StepWfView stepWfView = stepWfService.leggiStepCorrente(
											schedaWfView.getVo().getId(), CostantiDAO.AUTORIZZAZIONE_SCHEDA_FONDO_RISCHI);
									if (stepWfView != null) {
										String tipoAssegnazione = stepWfView.getVo().getConfigurazioneStepWf()
												.getTipoAssegnazione();
										if (tipoAssegnazione != null && !tipoAssegnazione.equalsIgnoreCase("MAN")) {

											// PRELEVO IL VECCHIO UTENTE PER LA NOTIFICA
											List<UtenteView> utentiViewOld = utenteService.leggiAssegnatarioWfSchedaFondoRischi(idScheda);
											UtenteView utenteViewOld = utentiViewOld.get(0);

											if (schedaFondoRischiWfService.riportaInBozzaWorkflow(idScheda, utenteView.getVo().getUseridUtil())) {

												// INVIO LA NOTIFICA AL VECCHIO UTENTE
												if (utenteViewOld != null) {
													StepWFRest step = new StepWFRest();
													step.setId(0);
													Event event = WebSocketPublisher.getInstance().createEvent(
															Costanti.WEBSOCKET_EVENTO_NOTIFICHE, step,
															utenteViewOld.getVo().getUseridUtil());
													WebSocketPublisher.getInstance().publishEvent(event);
												}

												if (schedaFondoRischiWfService.avviaWorkflow(idScheda, utenteNuovo.getUseridUtil())) {
													// NUOVO ASSEGNATARIO
													List<UtenteView> assegnatari = utenteService.leggiAssegnatarioWfSchedaFondoRischi(idScheda);
													UtenteView assegnatario = assegnatari.get(0);

													// INVIO NOTIFICA AL NUOVO ASSEGNATARIO
													if (assegnatario != null) {

														StepWFRest step = new StepWFRest();
														step.setId(0);
														Event event = WebSocketPublisher.getInstance().createEvent(Costanti.WEBSOCKET_EVENTO_NOTIFICHE, step,
																assegnatario.getVo().getUseridUtil());
														WebSocketPublisher.getInstance().publishEvent(event);

														try {
															// INVIO EMAIL AL NUOVO ASSEGNATARIO
															emailNotificationService.inviaNotifica(CostantiDAO.AVANZAMENTO, CostantiDAO.SCHEDA_FONDO_RISCHI,
																	idScheda, locale.getLanguage().toUpperCase(),
																	utenteNuovo.getUseridUtil());

														} // fine invio e-mail
														catch (Exception e) {
															System.out.println("Errore in invio e-mail" + e);
														}
													}
												} // fine avvio nuovo workflow
											} // fine riporto in bozza
										}
									}
								} // fine gestione workflow
							} // fine ciclo incarico
							
							
						
						}
						else{
							return null;
						}
					}
				}
				/**
				 * CR Varianti 3 - Invio mail cambio owner
				 */
				Mail mail =new Mail();
				mail.setListaFascioliCambioOwner(fascicolos);
				emailNotificationService.inviaNotificaCambioOwner(mail, utenteOld, utenteNuovo);
				return "OK";
			} // fine controllo amministratore
			else
				return "UTENTE-NON-AUTORIZZATO" + builder.toString();

		} catch (Throwable e) {
			builder.append("Exception::" + e.getMessage());
			e.printStackTrace();
			return builder.toString();

		}
	}
}
