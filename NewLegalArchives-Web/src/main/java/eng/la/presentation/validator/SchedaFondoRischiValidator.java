package eng.la.presentation.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import eng.la.model.view.SchedaFondoRischiView;
import eng.la.util.DateUtil;

public class SchedaFondoRischiValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return SchedaFondoRischiView.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		SchedaFondoRischiView view = (SchedaFondoRischiView) target;
		if( view.getOp() != null 
				&& view.getOp().equals("salvaSchedaFondoRischi") ){
			if( view.getSchedaFondoRischiId() == null ){
				
				if(view.getTipologiaSchedaFondoRischiCode() == null || view.getTipologiaSchedaFondoRischiCode().isEmpty()){
					errors.rejectValue("tipologiaSchedaFondoRischiCode", "errore.campo.obbligatorio.tipologiaSchedaFondoRischi");
				}
				
				if(view.getValoreDomanda() == null){
					errors.rejectValue("valoreDomanda", "errore.campo.obbligatorio.valoreDomanda");
				}
				
				if(view.getRischioSoccombenzaCode() == null || view.getRischioSoccombenzaCode().isEmpty()){
					errors.rejectValue("rischioSoccombenzaCode", "errore.campo.obbligatorio.rischioSoccombenza");
				}

				if(view.getCoperturaAssicurativaFlag() == null){
					errors.rejectValue("coperturaAssicurativaFlag", "errore.campo.obbligatorio.coperturaAssicurativaFlag");
				}
				
				if(view.getManlevaFlag() == null){
					errors.rejectValue("manlevaFlag", "errore.campo.obbligatorio.manlevaFlag");
				}
				
				if(view.getCommessaDiInvestimentoFlag() == null){
					errors.rejectValue("commessaDiInvestimentoFlag", "errore.campo.obbligatorio.commessaDiInvestimentoFlag");
				}
				
				if(view.getPassivitaStimata() == null){
					errors.rejectValue("passivitaStimata", "errore.campo.obbligatorio.passivitaStimata");
				}
			 
				if( DateUtil.isData(view.getDataRichiestaAutorizzazione()) && DateUtil.isData(view.getDataAutorizzazione() ) ){
					if( DateUtil.toDate(view.getDataRichiestaAutorizzazione()).after( DateUtil.toDate(view.getDataAutorizzazione()))){
						errors.rejectValue("dataRichiestaAutorizzazione", "errore.campo.obbligatorio.dataRichiestaAutorizzazioneSuperioreDataAutorizzazione");	
					}
				}
			}else{ 
				/** DA COMPILARE **/
			}
		}
	}

}
