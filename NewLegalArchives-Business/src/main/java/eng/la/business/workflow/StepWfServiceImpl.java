package eng.la.business.workflow;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.castor.core.util.Base64Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import eng.la.business.BaseService;
import eng.la.model.ConfigurazioneStepWf;
import eng.la.model.Incarico;
import eng.la.model.StepWf;
import eng.la.model.rest.AllegatoRest;
import eng.la.model.view.StepWfView;
import eng.la.persistence.CostantiDAO;
import eng.la.persistence.IncaricoDAO;
import eng.la.persistence.workflow.ConfigurazioneStepWfDAO;
import eng.la.persistence.workflow.StepWfDAO;

/**
 * <h1>Classe di business StepWfServiceImpl </h1>
 * Classe preposta alla gestione delle operazione di lettura
 * sulla base dati attraverso l'uso delle classi DAO
 * di pertinenza all'operazione.
 * <p>
 * @author
 * @version 1.0
 * @since 2016-07-01
 */
@Service("stepWfService")
public class StepWfServiceImpl  extends BaseService implements StepWfService{

	@Autowired
	private StepWfDAO stepWfDAO;

	@Autowired
	private ConfigurazioneStepWfDAO configurazioneStepWfDAO;

	public ConfigurazioneStepWfDAO getConfigurazioneStepWfDAO() {
		return configurazioneStepWfDAO;
	}

	@Autowired
	private IncaricoDAO incaricoDAO;

	public IncaricoDAO getIncaricoDAO() {
		return incaricoDAO;
	}

	// metodi extra
	@Override
	protected Class<StepWf> leggiClassVO() {
		return StepWf.class;
	}

	@Override
	protected Class<StepWfView> leggiClassView() {
		return StepWfView.class;
	}

	/**
	 * Recupera gli step in carico all'utente passato in input
	 * @param matricola: matricola dell'utente
	 * @param lang: lingua di visualizzazione
	 */
	@Override
	public List<StepWfView> leggiAttivitaPendenti(String matricola, String lang) throws Throwable {

		List<StepWf> lista = stepWfDAO.leggiAttivitaPendenti(matricola);

		@SuppressWarnings("unchecked")
		List<StepWfView> listaRitorno = (List<StepWfView>)convertiVoInView(lista);

		String noteSpecifiche = StringUtils.EMPTY;

		//setto per ogni occorrenza la descrizione dello step nella lingua passata in input
		for( StepWfView step : listaRitorno ){
			String codiceLingua = step.getVo().getConfigurazioneStepWf().getCodGruppoLingua();
			ConfigurazioneStepWf configurazioneTradotta = configurazioneStepWfDAO.leggiConfigurazioneTradotta(codiceLingua, lang);
			step.setDescLinguaCorrente(configurazioneTradotta.getDescrStateTo());
			switch(step.getVo().getConfigurazioneStepWf().getClasseWf().getCodice()){
				case CostantiDAO.CHIUSURA_FASCICOLO:
					step.setNoteSpecifiche(StringUtils.EMPTY);
					step.setFascicoloConValore(CostantiDAO.FASCICOLO + " " + step.getVo().getFascicoloWf().getFascicolo().getNome());
					break;
				case CostantiDAO.AUTORIZZAZIONE_INCARICO:
					if(step.getVo().getIncaricoWf().getCollegioArbitrale().equalsIgnoreCase(Character.toString(CostantiDAO.FALSE_CHAR))){
						noteSpecifiche = step.getVo().getIncaricoWf().getIncarico().getNomeIncarico();
						step.setNoteSpecifiche(CostantiDAO.INCARICO + " " + noteSpecifiche);
						step.setFascicoloConValore(CostantiDAO.FASCICOLO + " " + step.getVo().getIncaricoWf().getIncarico().getFascicolo().getNome());
					}
					else{
						noteSpecifiche = step.getVo().getIncaricoWf().getIncarico().getNomeCollegioArbitrale();
						step.setNoteSpecifiche(CostantiDAO.ARBITRATO + " " + noteSpecifiche);
						step.setFascicoloConValore(CostantiDAO.FASCICOLO + " " + step.getVo().getIncaricoWf().getIncarico().getIncarico().getFascicolo().getNome());
					}

					break;
				case CostantiDAO.AUTORIZZAZIONE_SCHEDA_FONDO_RISCHI:
					noteSpecifiche = step.getVo().getSchedaFondoRischiWf().getSchedaFondoRischi().getFascicolo().getNome();
					step.setNoteSpecifiche(CostantiDAO.SCHEDA_FONDO_RISCHI + " " + noteSpecifiche);
					step.setFascicoloConValore(CostantiDAO.FASCICOLO + " " + step.getVo().getSchedaFondoRischiWf().getSchedaFondoRischi().getFascicolo().getNome());
					break;
				case CostantiDAO.AUTORIZZAZIONE_BEAUTY_CONTEST:
					noteSpecifiche = step.getVo().getBeautyContestWf().getBeautyContest().getTitolo();
					step.setNoteSpecifiche(CostantiDAO.BEAUTY_CONTEST + " " + noteSpecifiche);
					step.setFascicoloConValore(StringUtils.EMPTY);
					break;
				case CostantiDAO.AUTORIZZAZIONE_PROFORMA:
					Incarico incaricoProforma = incaricoDAO.leggiIncaricoAssociatoAProforma(step.getVo().getProformaWf().getProforma().getId());

					noteSpecifiche = step.getVo().getProformaWf().getProforma().getNomeProforma();
					step.setNoteSpecifiche(CostantiDAO.PROFORMA + " " + noteSpecifiche);
					step.setFascicoloConValore(CostantiDAO.FASCICOLO + " " + incaricoProforma.getFascicolo().getNome());
					break;
				case CostantiDAO.AUTORIZZAZIONE_INS_PROFESSIONISTA_ESTERNO_IN_ELENCO:

					noteSpecifiche = step.getVo().getProfessionistaEsternoWf().getProfessionistaEsterno().getNome() + " " + step.getVo().getProfessionistaEsternoWf().getProfessionistaEsterno().getCognome();
					step.setNoteSpecifiche(CostantiDAO.PROFESSIONISTA_ESTERNO + " " + noteSpecifiche);
					step.setFascicoloConValore(StringUtils.EMPTY);
					break;
				case CostantiDAO.REGISTRAZIONE_ATTO:
					if(step.getVo().getMotivoRifiutoStepPrecedente() != null && !step.getVo().getMotivoRifiutoStepPrecedente().equals(StringUtils.EMPTY))
						step.setRifiutato(true);
					else
						step.setRifiutato(false);
					if(step.getVo().getAttoWf().getAtto().getRilevante().equalsIgnoreCase(Character.toString(CostantiDAO.TRUE_CHAR)))
						noteSpecifiche = "<font color='red'>" + step.getVo().getAttoWf().getAtto().getNumeroProtocollo() + "</font>";
					else
						noteSpecifiche = step.getVo().getAttoWf().getAtto().getNumeroProtocollo();

					step.setNoteSpecifiche(CostantiDAO.ATTO + " " + noteSpecifiche);
					step.setFascicoloConValore(StringUtils.EMPTY);
					break;
			}


		}
		for(StepWfView stepWFView: listaRitorno){
			System.out.println("Attività pendente per matricola: " + matricola + " | " + stepWFView.toString());
		}
		return listaRitorno;
	}

	/**
	 * Metodo di lettura dello step
	 * <p>
	 * @param id: identificativo dello step
	 * @return oggetto StepWfView.
	 * @exception Throwable
	 */
	@Override
	public StepWfView leggiStep(long id) throws Throwable {
		StepWf step = stepWfDAO.leggiStep(id);
		return (StepWfView) convertiVoInView(step);
	}

	/**
	 * Recupera lo step corrente a partire dal workflow e dalla classe.
	 * @param idWorkflow identificatico del workflow
	 * classeWf la tipologia di workflow
	 * @return oggetto StepWfView
	 */
	public StepWfView leggiStepCorrente(long idWorkflow, String classeWf) throws Throwable {
		StepWf step = stepWfDAO.leggiStepCorrente(idWorkflow, classeWf);
		return (StepWfView) convertiVoInView(step);
	}

	public Long checkPendingWf(String matricola) throws Throwable {
		Long conta = stepWfDAO.checkPendingWf(matricola);
		return conta;
	}


	public static void main(String[] args) throws Throwable {
		File f = new File("C:\\Users\\Luigi Nardiello\\Documents\\shared\\jmsmessages.xml");

		ObjectMapper mapper = new ObjectMapper(new JsonFactory());
		AllegatoRest rest = new AllegatoRest();
		FileInputStream is = new FileInputStream(f);

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		IOUtils.copy(is, os);
		byte[] b = os.toByteArray();

		String base64 = new String(Base64Encoder.encode(b));
		rest.setBase64(base64);
		System.out.println(mapper.writeValueAsString(rest));
	}
}