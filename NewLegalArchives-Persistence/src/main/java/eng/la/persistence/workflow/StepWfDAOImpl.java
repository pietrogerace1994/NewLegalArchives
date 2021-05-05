package eng.la.persistence.workflow;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;


import eng.la.model.ConfigurazioneStepWf;
import eng.la.model.GruppoUtente;
import eng.la.model.RUtenteGruppo;
import eng.la.model.StepWf;
import eng.la.model.Utente;
import eng.la.persistence.CostantiDAO;

/**
 * <h1>Classe DAO d'implemtazione delle operazioni su base dati, 
 * per entità StepWf</h1>
 * La classe DAO espone le operazioni di lettura/scrittura sulla base dati per
 * l'entità StepWf.
 * 
 * @author ACER
 */
@SuppressWarnings("unchecked")
@Component("stepWfDAO")
public class StepWfDAOImpl extends HibernateDaoSupport implements StepWfDAO{

	
	private static final Logger logger = Logger.getLogger(StepWfDAOImpl.class);
	
	@Autowired
	public StepWfDAOImpl(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}

	/**
	 * Recupera l'assegnatario corrente dello step.
	 * @param idConfigurazione: identificativo della configurazione dello step corrente
	 * @param idObject: identificativo dell'istanza di oggetto corrente
	 * @return oggetto model utente popolato con i dati inseriti.
	 * @exception Throwable
	 */
	@Override
	public Utente leggiAssegnatarioCorrente(long idConfigurazione, long idObject) throws Throwable {
		DetachedCriteria criteria = DetachedCriteria.forClass( ConfigurazioneStepWf.class ).add( Restrictions.eq("id", idConfigurazione) );
		
		ConfigurazioneStepWf configurazione = (ConfigurazioneStepWf) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );	
		//se l'assegnazione è manuale
		if(configurazione.getTipoAssegnazione().equalsIgnoreCase(CostantiDAO.ASSEGNAZIONE_MANUALE))
			return configurazione.getUtente();
		else if(configurazione.getTipoAssegnazione().equalsIgnoreCase(CostantiDAO.ASSEGNAZIONE_RESPONSABILE) || 
				configurazione.getTipoAssegnazione().equalsIgnoreCase(CostantiDAO.ASSEGNAZIONE_PRIMO_RIPORTO)){
			// ricavo il workflow corrente considerando:
			// data di chiusura not null dello step
			// l'id della configurazione 
			
			DetachedCriteria criteriaStepWf = DetachedCriteria.forClass( StepWf.class )
					.addOrder(Order.desc("dataCreazione"));
			criteriaStepWf.add(Restrictions.isNotNull("dataChiusura"));
			criteriaStepWf.createAlias("configurazioneStepWf", "configurazioneStepWf");
			criteriaStepWf.add(Restrictions.eq("configurazioneStepWf.id", idConfigurazione));
			
			// seleziono il workflow in stato in corso nella rispettiva tabella fascicolo_wf, incarico_wf, ecc
			// ossia quello con id_fascicolo (o id_incarico, ecc. ecc) pari a idobject
			
			switch ((configurazione.getClasseWf().getCodice())){
			case CostantiDAO.AUTORIZZAZIONE_COLLEGIO_ARBITRALE:
			case CostantiDAO.AUTORIZZAZIONE_INCARICO: 
				criteriaStepWf.createAlias("incaricoWf", "incaricoWf");
				criteriaStepWf.add(Restrictions.isNull("incaricoWf.dataChiusura"));
				criteriaStepWf.createAlias("incaricoWf.incarico", "incarico");
				criteriaStepWf.add(Restrictions.eq("incarico.id", idObject));
				criteriaStepWf.createAlias("incaricoWf.statoWf", "statoWf");
				break;
			case CostantiDAO.AUTORIZZAZIONE_INS_PROFESSIONISTA_ESTERNO_IN_ELENCO:
				criteriaStepWf.createAlias("professionistaEsternoWf", "professionistaEsternoWf");
				criteriaStepWf.add(Restrictions.isNull("professionistaEsternoWf.dataChiusura"));
				criteriaStepWf.createAlias("professionistaEsternoWf.professionistaEsterno", "professionistaEsterno");
				criteriaStepWf.add(Restrictions.eq("professionistaEsterno.id", idObject));
				criteriaStepWf.createAlias("professionistaEsternoWf.statoWf", "statoWf");
				break;
			case CostantiDAO.AUTORIZZAZIONE_PROFORMA:
				criteriaStepWf.createAlias("proformaWf", "proformaWf");
				criteriaStepWf.add(Restrictions.isNull("proformaWf.dataChiusura"));
				criteriaStepWf.createAlias("proformaWf.proforma", "proforma");
				criteriaStepWf.add(Restrictions.eq("proforma.id", idObject));
				criteriaStepWf.createAlias("proformaWf.statoWf", "statoWf");
				break;
			case CostantiDAO.CHIUSURA_FASCICOLO:
				criteriaStepWf.createAlias("fascicoloWf", "fascicoloWf");
				criteriaStepWf.add(Restrictions.isNull("fascicoloWf.dataChiusura"));
				criteriaStepWf.createAlias("fascicoloWf.fascicolo", "fascicolo");
				criteriaStepWf.add(Restrictions.eq("fascicolo.id", idObject));
				criteriaStepWf.createAlias("fascicoloWf.statoWf", "statoWf");
				break;
			default:
				break;
			}
			
			// imposto la join con id_stato_wf pari all'id della tabella stato_wf con codice lingua pari a TPWS_1 (ossia in corso) e lingua (lang) pari a IT (ossia italiana)
			criteriaStepWf.add(Restrictions.eq("statoWf.codGruppoLingua", CostantiDAO.STATO_WF_IN_CORSO));
			criteriaStepWf.add(Restrictions.eq("statoWf.lang", CostantiDAO.LINGUA_ITALIANA));
			
			List<StepWf> lista = getHibernateTemplate().findByCriteria(criteriaStepWf);	
			
			if(lista.size() == 0)
				return null;
			else{
				StepWf stepCorrente =  lista.get(0);
				DetachedCriteria criteriaUtenteCorrente = DetachedCriteria.forClass( Utente.class ).add( Restrictions.eq("matricolaUtil", stepCorrente.getUtente().getMatricolaUtil()) );
				// ricavo l'utente con matricola pari alla matricola dell'utente creatore dello step
				Utente utenteCorrente = (Utente) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteriaUtenteCorrente) );		
				
				if(utenteCorrente != null){
					// dell'utente selezionato ricavo il diretto responsabile
					DetachedCriteria criteriaAssegnatario = DetachedCriteria.forClass( Utente.class ).add( Restrictions.eq("matricolaUtil", utenteCorrente.getMatricolaRespUtil()) );
					Utente assegnatario =  (Utente) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteriaAssegnatario) );		
					return assegnatario;

				}
				else
					return null;
			}
		}
		return null;
	}
	
	/**
	 * Crea lo step.
	 * @param vo: oggetto Step
	 * @return oggetto model StepWf creato
	 */
	@Override
	public StepWf creaStepWf(StepWf vo) {
		getHibernateTemplate().save(vo);
		return vo;
	}
	/**
	 * Recupera lo step corrente a partire dal workflow e dalla classe.
	 * @param idWorkflow identificatico del workflow
	 * classeWf la tipologia di workflow
	 * @return oggetto model StepWf
	 */
	@Override
	public StepWf leggiStepCorrente(long idWorkflow, String classeWf)throws Throwable{
		DetachedCriteria criteriaStepWf = DetachedCriteria.forClass( StepWf.class )
				.addOrder(Order.desc("dataCreazione"));
		criteriaStepWf.add(Restrictions.isNull("dataChiusura"));
		
		// seleziono il workflow in stato in corso nella rispettiva tabella fascicolo_wf, incarico_wf, ecc
		// a seconda della classe passata in input
		
		switch (classeWf){
		case CostantiDAO.AUTORIZZAZIONE_COLLEGIO_ARBITRALE:
		case CostantiDAO.AUTORIZZAZIONE_INCARICO: 
			criteriaStepWf.createAlias("incaricoWf", "incaricoWf");
//			criteriaStepWf.add(Restrictions.isNull("incaricoWf.dataChiusura"));
			criteriaStepWf.add(Restrictions.eq("incaricoWf.id", idWorkflow));
			break;
		case CostantiDAO.AUTORIZZAZIONE_INS_PROFESSIONISTA_ESTERNO_IN_ELENCO:
			criteriaStepWf.createAlias("professionistaEsternoWf", "professionistaEsternoWf");
//			criteriaStepWf.add(Restrictions.isNull("professionistaEsternoWf.dataChiusura"));
			criteriaStepWf.add(Restrictions.eq("professionistaEsternoWf.id", idWorkflow));
			break;
		case CostantiDAO.AUTORIZZAZIONE_PROFORMA:
			criteriaStepWf.createAlias("proformaWf", "proformaWf");
//			criteriaStepWf.add(Restrictions.isNull("proformaWf.dataChiusura"));
			criteriaStepWf.add(Restrictions.eq("proformaWf.id", idWorkflow));
			break;
		case CostantiDAO.CHIUSURA_FASCICOLO:
			criteriaStepWf.createAlias("fascicoloWf", "fascicoloWf");
			criteriaStepWf.add(Restrictions.isNull("fascicoloWf.dataChiusura"));
			criteriaStepWf.add(Restrictions.eq("fascicoloWf.id", idWorkflow));
			break;
		case CostantiDAO.REGISTRAZIONE_ATTO:
			criteriaStepWf.createAlias("attoWf", "attoWf");
//			criteriaStepWf.add(Restrictions.isNull("attoWf.dataChiusura"));
			criteriaStepWf.add(Restrictions.eq("attoWf.id", idWorkflow));
			break;
		case CostantiDAO.AUTORIZZAZIONE_SCHEDA_FONDO_RISCHI: 
			criteriaStepWf.createAlias("schedaFondoRischiWf", "schedaFondoRischiWf");
//			criteriaStepWf.add(Restrictions.isNull("incaricoWf.dataChiusura"));
			criteriaStepWf.add(Restrictions.eq("schedaFondoRischiWf.id", idWorkflow));
			break;
		case CostantiDAO.AUTORIZZAZIONE_BEAUTY_CONTEST: 
			criteriaStepWf.createAlias("beautyContestWf", "beautyContestWf");
//			criteriaStepWf.add(Restrictions.isNull("incaricoWf.dataChiusura"));
			criteriaStepWf.add(Restrictions.eq("beautyContestWf.id", idWorkflow));
			break;
		default:
			break;
		}
		List<StepWf> lista = getHibernateTemplate().findByCriteria(criteriaStepWf);	
		
		return lista.get(0);
					
	}
	
	/**
	 * Recupera lo step corrente a partire dal workflow e dalla classe.
	 * @param idWorkflow identificatico del workflow
	 * classeWf la tipologia di workflow
	 * @return oggetto model StepWf
	 */
	@Override
	public StepWf leggiUltimoStepWorkflow(long idWorkflow, String classeWf)throws Throwable{
		DetachedCriteria criteriaStepWf = DetachedCriteria.forClass( StepWf.class )
				.addOrder(Order.desc("dataCreazione"));
		criteriaStepWf.add(Restrictions.isNotNull("dataChiusura"));
		
		// seleziono il workflow in stato in corso nella rispettiva tabella fascicolo_wf, incarico_wf, ecc
		// a seconda della classe passata in input
		
		switch (classeWf){
		case CostantiDAO.AUTORIZZAZIONE_COLLEGIO_ARBITRALE:
		case CostantiDAO.AUTORIZZAZIONE_INCARICO: 
			criteriaStepWf.createAlias("incaricoWf", "incaricoWf");
			criteriaStepWf.add(Restrictions.isNull("incaricoWf.dataChiusura"));
			criteriaStepWf.add(Restrictions.eq("incaricoWf.id", idWorkflow));
			break;
		case CostantiDAO.AUTORIZZAZIONE_INS_PROFESSIONISTA_ESTERNO_IN_ELENCO:
			criteriaStepWf.createAlias("professionistaEsternoWf", "professionistaEsternoWf");
			criteriaStepWf.add(Restrictions.isNull("professionistaEsternoWf.dataChiusura"));
			criteriaStepWf.add(Restrictions.eq("professionistaEsternoWf.id", idWorkflow));
			break;
		case CostantiDAO.AUTORIZZAZIONE_PROFORMA:
			criteriaStepWf.createAlias("proformaWf", "proformaWf");
			criteriaStepWf.add(Restrictions.isNull("proformaWf.dataChiusura"));
			criteriaStepWf.add(Restrictions.eq("proformaWf.id", idWorkflow));
			break;
		case CostantiDAO.CHIUSURA_FASCICOLO:
			criteriaStepWf.createAlias("fascicoloWf", "fascicoloWf");
			criteriaStepWf.add(Restrictions.isNull("fascicoloWf.dataChiusura"));
			criteriaStepWf.add(Restrictions.eq("fascicoloWf.id", idWorkflow));
			break;
		case CostantiDAO.REGISTRAZIONE_ATTO:
			criteriaStepWf.createAlias("attoWf", "attoWf");
			criteriaStepWf.add(Restrictions.isNull("attoWf.dataChiusura"));
			criteriaStepWf.add(Restrictions.eq("attoWf.id", idWorkflow));
			break;
		default:
			break;
		}
		List<StepWf> lista = getHibernateTemplate().findByCriteria(criteriaStepWf);	
		
		return lista.get(0);
					
	}
	
	/**
	 * Aggiorna lo step modificando solo i dati aggiornabili.
	 * @param vo: lo step
	 *            
	 */
	public void modifica(StepWf vo) throws Throwable{
		getHibernateTemplate().update(vo); 
	}

	/**
	 * Recupera gli step in carico all'utente passato in input
	 * @param matricola: matricola dell'utente
	 *            
	 */
	@Override
	public List<StepWf> leggiAttivitaPendenti(String matricola) throws Throwable {

				
		//ricavo lo userId dell'utente (mi serve per recuperare le attività pendenti relative all'atto
		DetachedCriteria criteriaUtente = DetachedCriteria.forClass( Utente.class ).add( Restrictions.eq("matricolaUtil", matricola) );
		Utente utenteCorrente = (Utente) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteriaUtente) );
		
		//ricavo i gruppi a cui appartiene l'utente
		List<GruppoUtente> gruppoList = new ArrayList<GruppoUtente>(0);
		DetachedCriteria criteriaGruppo = DetachedCriteria.forClass(RUtenteGruppo.class);
		criteriaGruppo.createAlias("utente", "utente");
		criteriaGruppo.add(Restrictions.eq("utente.matricolaUtil", matricola));
		criteriaGruppo.add(Restrictions.isNull("dataCancellazione"));
		List<RUtenteGruppo> listaRelazione =(List<RUtenteGruppo>) getHibernateTemplate().findByCriteria(criteriaGruppo);	
		
		 if(listaRelazione.size() > 0){
			 for (RUtenteGruppo relazione:listaRelazione){
				 gruppoList.add(relazione.getGruppoUtente());
				}	
		 }
		
		//inizialmente recupero quelli differenti dalla registrazione atto
		DetachedCriteria criteria = DetachedCriteria.forClass( StepWf.class );
		
		//DARIO C**************************************
		//criteria.createAlias("utente", "utente");
		//*********************************************
		
		criteria.createAlias("configurazioneStepWf", "configurazioneStepWf");
		
		
		//filtro su data chiusura null
		Criterion data = Restrictions.isNull("dataChiusura");
		
		//filtro su data cancellazione null
		Criterion dataCancellazione = Restrictions.isNull("dataCancellazione");
		
		//filtro sui workflow differenti dalla registrazione atto (che verranno trattati successivamente)
		Criterion atto = Restrictions.isNull("attoWf");

		
		//condizione che l'utente sia esecutore manual
		//DARIO C***************************************************************************************************
//		Criterion assegnazioneManuale = Restrictions.conjunction()
//				.add(Restrictions.eq("configurazioneStepWf.tipoAssegnazione", CostantiDAO.ASSEGNAZIONE_MANUALE))
//				.add(Restrictions.eq("configurazioneStepWf.utente.matricolaUtil", matricola));
		
		Criterion assegnazioneManuale = Restrictions.eq("configurazioneStepWf.tipoAssegnazione", CostantiDAO.ASSEGNAZIONE_MANUALE);
		//***********************************************************************************************************
		
		//condizione che l'utente sia il creatore dello step in assegnazione a responsabili o responsabili di primo riporto
		Criterion assegnazioneResponsabile = Restrictions.disjunction()
						.add(Restrictions.eq("configurazioneStepWf.tipoAssegnazione", CostantiDAO.ASSEGNAZIONE_RESPONSABILE))
						.add(Restrictions.eq("configurazioneStepWf.tipoAssegnazione", CostantiDAO.ASSEGNAZIONE_PRIMO_RIPORTO));
		
		Criterion assegnazioneVerificatori = Restrictions.conjunction()
				.add(Restrictions.eq("configurazioneStepWf.tipoAssegnazione", CostantiDAO.ASSEGNAZIONE_SEGRETERIA))
				.add(Restrictions.isNotNull("configurazioneStepWf.gruppoUtente"));
		
		
		//DARIO C ********************************************************************************************************************
//		Criterion creazione = Restrictions.conjunction()
//				.add(Restrictions.eq("utente.matricolaRespUtil", matricola));
//		
//		//assemblo il tutto
//		criteria.add(Restrictions.conjunction()
//				.add(data)
//				.add(dataCancellazione)
//				.add(atto)
//				.add(Restrictions.conjunction()
//						.add(Restrictions.disjunction()
//								.add(assegnazioneManuale)
//								.add(Restrictions.conjunction()
//										.add(assegnazioneResponsabile)
//										.add(creazione)
//								)
//						)
//				)
//		);
		
		criteria.add(Restrictions.conjunction()
				.add(data)
				.add(dataCancellazione)
				.add(atto)
				.add(Restrictions.disjunction()
					.add(assegnazioneManuale)
					.add(assegnazioneResponsabile)));
		
		//****************************************************************************************************************************
		
		
		List<StepWf> lista = getHibernateTemplate().findByCriteria(criteria);
		
		//DARIO C *****************************************************************************************
		lista = getListaFiltrataPerMatricola(lista, matricola);
		//*************************************************************************************************
		
		
		DetachedCriteria criteriaVerificatori = DetachedCriteria.forClass( StepWf.class );
		criteriaVerificatori.createAlias("configurazioneStepWf", "configurazioneStepWf");
		criteriaVerificatori.add(Restrictions.conjunction()
				.add(data)
				.add(dataCancellazione)
				.add(atto)
				.add(assegnazioneVerificatori)
		);
		
		List<StepWf> listaPerVerificatori = getHibernateTemplate().findByCriteria(criteriaVerificatori);
		List<StepWf> listaPerVerificatoriRitorno = new ArrayList<StepWf> ();
		
		for(StepWf stepWf : listaPerVerificatori){

			if(stepWf.getConfigurazioneStepWf() != null){
				
				if(stepWf.getConfigurazioneStepWf().getTipoAssegnazione() != null && !stepWf.getConfigurazioneStepWf().getTipoAssegnazione().isEmpty()){
					
					if(stepWf.getConfigurazioneStepWf().getTipoAssegnazione().equals(CostantiDAO.ASSEGNAZIONE_SEGRETERIA)){
						
						if(stepWf.getConfigurazioneStepWf().getGruppoUtente() != null){
							
							boolean stessoGruppo = false;
							long idGruppo = stepWf.getConfigurazioneStepWf().getGruppoUtente().getId();
							
							if(gruppoList != null){
								
								for(GruppoUtente gruppo : gruppoList){
									
									if(gruppo.getId() == idGruppo){
										stessoGruppo = true;
									}
								}
								
								if(stessoGruppo){
									listaPerVerificatoriRitorno.add(stepWf);
								}
							}
						}
					}
				}
			}
		}
		
		if(!listaPerVerificatoriRitorno.isEmpty()){
			lista.addAll(listaPerVerificatoriRitorno);
		}
		
		DetachedCriteria criteriaAtto = DetachedCriteria.forClass( StepWf.class );
		
		criteriaAtto.createAlias("attoWf", "attoWf");
		criteriaAtto.createAlias("configurazioneStepWf", "configurazioneStepWf");
		criteriaAtto.createAlias("utente", "utente");
		
		//filtro su data chiusura null
		Criterion dataAtto = Restrictions.isNull("dataChiusura");
		
		//filtro su data cancellazione null
		Criterion dataCancellazioneAtto = Restrictions.isNull("dataCancellazione");
		
		//filtro sui workflow relativi alla registrazione atto 
		Criterion attoAtto = Restrictions.isNotNull("attoWf");
		
		//filtro sull'utente assegnatario per gli step non assegnati ai responsabili o all'owner
		//Criterion assegnatarioAtto = Restrictions.eq("attoWf.utenteAssegnatario", matricola);
		
		Criterion tipoAssegnazioneSelezionata = Restrictions.conjunction()
				.add( Restrictions.ilike("configurazioneStepWf.numeroAzioneStep", '%' + CostantiDAO.SUFF_AVANZAMENTO_WF, MatchMode.ANYWHERE) ) 
				.add(Restrictions.eq("attoWf.utenteAssegnatario", matricola));

		//filtro sull'utente assegnatario per gli step assegnati ai responsabili
		Criterion tipoAssegnazioneResponsabile = Restrictions.conjunction()
				.add(Restrictions.disjunction()
						.add(Restrictions.eq("configurazioneStepWf.tipoAssegnazione", CostantiDAO.ASSEGNAZIONE_RESPONSABILE))
						.add(Restrictions.eq("configurazioneStepWf.tipoAssegnazione", CostantiDAO.ASSEGNAZIONE_PRIMO_RIPORTO)))								
				.add(Restrictions.eq("utente.matricolaRespUtil", matricola));
		
		//filtro sull'utente assegnatario per gli step assegnati all'owner
		Criterion tipoAssegnazioneOwner = Restrictions.conjunction()
				.add(Restrictions.eq("configurazioneStepWf.tipoAssegnazione", CostantiDAO.ASSEGNAZIONE_OWNER))
				.add(Restrictions.eq("attoWf.utenteCreazione", utenteCorrente.getUseridUtil()));
		
		//assemblo il tutto
		criteriaAtto.add(Restrictions.conjunction()
				.add(dataAtto)
				.add(dataCancellazioneAtto)
				.add(attoAtto)
				.add(Restrictions.disjunction()
						.add(tipoAssegnazioneSelezionata)
						.add(tipoAssegnazioneResponsabile)
						.add(tipoAssegnazioneOwner)
				)
		);
		
		List<StepWf> listaAtto = getHibernateTemplate().findByCriteria(criteriaAtto);
		
		
		
		
		//faccio l'unione delle due liste
		lista.addAll(listaAtto);
		
		//ordino la lista in senso decrescente in base alla data di creazione
		lista.sort(new Comparator<StepWf>(){
		    public int compare(StepWf s1, StepWf s2) {
		        return -( s1.getDataCreazione().compareTo(s2.getDataCreazione()));
		    }
		});
		
		for(StepWf stepWf: lista){
			logger.info("Attività pendente per matricola: " + matricola + " | " + stepWf.toString());
		}
		
		return lista;
	}
	
	//DARIO C *************************************************************************************************
	private List<StepWf> getListaFiltrataPerMatricola(List<StepWf> lista, String matricola) throws Throwable{
		
		List<StepWf> listaFiltrata = new ArrayList<StepWf> ();
		for(StepWf stepWf : lista){
			if(stepWf.getConfigurazioneStepWf().getTipoAssegnazione().equals(CostantiDAO.ASSEGNAZIONE_MANUALE)){
				if (matricola.equals(stepWf.getConfigurazioneStepWf().getUtente().getMatricolaUtil())){
					listaFiltrata.add(stepWf);
				}
				continue;
			}
			if(stepWf.getIncaricoWf() != null){
				if (stepWf.getIncaricoWf().getUtenteAssegnatario()==null){
					if (matricola.equals(stepWf.getUtente().getMatricolaRespUtil())){
						listaFiltrata.add(stepWf);
					}					
				}else{
					if(matricola.equals(stepWf.getIncaricoWf().getUtenteAssegnatario())){
						listaFiltrata.add(stepWf);
					}
				}
				continue;
			}
			if(stepWf.getProformaWf() != null){
				if (stepWf.getProformaWf().getUtenteAssegnatario()==null){
					if (matricola.equals(stepWf.getUtente().getMatricolaRespUtil())){
						listaFiltrata.add(stepWf);
					}					
				}else{
					if(matricola.equals(stepWf.getProformaWf().getUtenteAssegnatario())){
						listaFiltrata.add(stepWf);
					}
				}
				continue;
			}
			if(stepWf.getSchedaFondoRischiWf() != null){
				if (stepWf.getSchedaFondoRischiWf().getUtenteAssegnatario()==null){
					if (matricola.equals(stepWf.getUtente().getMatricolaRespUtil())){
						listaFiltrata.add(stepWf);
					}					
				}else{
					if(matricola.equals(stepWf.getSchedaFondoRischiWf().getUtenteAssegnatario())){
						listaFiltrata.add(stepWf);
					}
				}
				continue;
			}
			if(stepWf.getBeautyContestWf() != null){
				if (stepWf.getBeautyContestWf().getUtenteAssegnatario()==null){
					if (matricola.equals(stepWf.getUtente().getMatricolaRespUtil())){
						listaFiltrata.add(stepWf);
					}					
				}else{
					if(matricola.equals(stepWf.getBeautyContestWf().getUtenteAssegnatario())){
						listaFiltrata.add(stepWf);
					}
				}
				continue;
			}
			if (matricola.equals(stepWf.getUtente().getMatricolaRespUtil())){
				listaFiltrata.add(stepWf);
			}
		}
		return listaFiltrata;
	}
	//**********************************************************************************************************************
	
	/**
	 * Recupera l'istanza di step.
	 * @param id identificativo dello step
	 * @return oggetto StepWf
	 */
	public StepWf leggiStep(long id) throws Throwable{
		DetachedCriteria criteria = DetachedCriteria.forClass( StepWf.class ).add( Restrictions.eq("id", id) );
		StepWf stepWf = (StepWf) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria) );		
				
		return stepWf;

	}
	
	/**
	 * Recupera l'ultimo Step chiuso a partire dal workflow e dalla classe.
	 * @param idWorkflow identificatico del workflow
	 * @param classeWf la tipologia di workflow
	 * @return oggetto model StepWf
	 */
	public StepWf leggiUltimoStepChiuso(long idWorkflow, String classeWf)throws Throwable {
		DetachedCriteria criteriaStepWf = DetachedCriteria.forClass( StepWf.class )
				.addOrder(Order.desc("dataChiusura"));
		criteriaStepWf.add(Restrictions.isNotNull("dataChiusura"));
		criteriaStepWf.add(Restrictions.disjunction()
				.add(Restrictions.isNull("discarded"))
				.add(Restrictions.eq("discarded", Character.toString(CostantiDAO.FALSE_CHAR)))
		);
		
		// seleziono il workflow in stato in corso nella rispettiva tabella fascicolo_wf, incarico_wf, ecc
		// a seconda della classe passata in input
		
		switch (classeWf){
		case CostantiDAO.AUTORIZZAZIONE_COLLEGIO_ARBITRALE:
		case CostantiDAO.AUTORIZZAZIONE_INCARICO: 
			criteriaStepWf.createAlias("incaricoWf", "incaricoWf");
			criteriaStepWf.add(Restrictions.eq("incaricoWf.id", idWorkflow));
			break;
		case CostantiDAO.AUTORIZZAZIONE_INS_PROFESSIONISTA_ESTERNO_IN_ELENCO:
			criteriaStepWf.createAlias("professionistaEsternoWf", "professionistaEsternoWf");
				criteriaStepWf.add(Restrictions.eq("professionistaEsternoWf.id", idWorkflow));
			break;
		case CostantiDAO.AUTORIZZAZIONE_PROFORMA:
			criteriaStepWf.createAlias("proformaWf", "proformaWf");
			criteriaStepWf.add(Restrictions.eq("proformaWf.id", idWorkflow));
			break;
		case CostantiDAO.CHIUSURA_FASCICOLO:
			criteriaStepWf.createAlias("fascicoloWf", "fascicoloWf");
			criteriaStepWf.add(Restrictions.eq("fascicoloWf.id", idWorkflow));
			break;
		case CostantiDAO.REGISTRAZIONE_ATTO:
			criteriaStepWf.createAlias("attoWf", "attoWf");
			criteriaStepWf.add(Restrictions.eq("attoWf.id", idWorkflow));
			break;
		default:
			break;
		}
		List<StepWf> lista = getHibernateTemplate().findByCriteria(criteriaStepWf);	
		if(lista.size() > 0)
			return lista.get(0);
		else
			return null;
					
	}

	/**
	 * Recupera lo step chiuso di un workflow standard a partire dal workflow, dalla classe e dallo stato finale.
	 * @param idWorkflow identificatico del workflow
	 * @param classeWf la tipologia di workflow
	 * @param stepTo stato finale dello step
	 * @return oggetto model StepWf
	 */
	@Override
	public StepWf leggiSpecificoStepWorkflow(long idWorkflow, String classeWf, String stateTo) throws Throwable {
		DetachedCriteria criteriaStepWf = DetachedCriteria.forClass( StepWf.class )
				.addOrder(Order.desc("dataChiusura"));
		criteriaStepWf.add(Restrictions.isNotNull("dataChiusura"));
		criteriaStepWf.add(Restrictions.disjunction()
				.add(Restrictions.isNull("discarded"))
				.add(Restrictions.eq("discarded", Character.toString(CostantiDAO.FALSE_CHAR)))
		);
		criteriaStepWf.createAlias("configurazioneStepWf", "configurazioneStepWf");
		criteriaStepWf.add(Restrictions.eq("configurazioneStepWf.stateTo", stateTo));
		
		// seleziono il workflow in stato in corso nella rispettiva tabella fascicolo_wf, incarico_wf, ecc
		// a seconda della classe passata in input
		
		switch (classeWf){
		case CostantiDAO.AUTORIZZAZIONE_COLLEGIO_ARBITRALE:
		case CostantiDAO.AUTORIZZAZIONE_INCARICO: 
			criteriaStepWf.createAlias("incaricoWf", "incaricoWf");
			criteriaStepWf.add(Restrictions.eq("incaricoWf.id", idWorkflow));
			break;
		case CostantiDAO.AUTORIZZAZIONE_INS_PROFESSIONISTA_ESTERNO_IN_ELENCO:
			criteriaStepWf.createAlias("professionistaEsternoWf", "professionistaEsternoWf");
				criteriaStepWf.add(Restrictions.eq("professionistaEsternoWf.id", idWorkflow));
			break;
		case CostantiDAO.AUTORIZZAZIONE_PROFORMA:
			criteriaStepWf.createAlias("proformaWf", "proformaWf");
			criteriaStepWf.add(Restrictions.eq("proformaWf.id", idWorkflow));
			break;
		case CostantiDAO.CHIUSURA_FASCICOLO:
			criteriaStepWf.createAlias("fascicoloWf", "fascicoloWf");
			criteriaStepWf.add(Restrictions.eq("fascicoloWf.id", idWorkflow));
			break;
		case CostantiDAO.REGISTRAZIONE_ATTO:
			criteriaStepWf.createAlias("attoWf", "attoWf");
			criteriaStepWf.add(Restrictions.eq("attoWf.id", idWorkflow));
			break;
		default:
			break;
		}
		List<StepWf> lista = getHibernateTemplate().findByCriteria(criteriaStepWf);	
		if(lista.size() > 0)
			return lista.get(0);
		else
			return null;
	}
	
	@Override
	public Long checkPendingWf(String matricola) throws Throwable {	
		
		DetachedCriteria criteria = DetachedCriteria.forClass( StepWf.class );
		criteria.createAlias("configurazioneStepWf", "configurazioneStepWf",DetachedCriteria.LEFT_JOIN);
		criteria.add(Restrictions.eq("configurazioneStepWf.utente.matricolaUtil", matricola));
		criteria.add(Restrictions.isNull("dataChiusura")) ;
		criteria.setProjection(Projections.rowCount());
		
		Long conta = (Long) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
		return conta;
	}
}

