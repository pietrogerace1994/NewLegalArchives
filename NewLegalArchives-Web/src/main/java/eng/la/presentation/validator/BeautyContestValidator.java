package eng.la.presentation.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import eng.la.model.view.BeautyContestView;
import eng.la.util.DateUtil;

public class BeautyContestValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return BeautyContestView.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		BeautyContestView beautyContestView = (BeautyContestView) target;
		if( beautyContestView.getOp() != null && beautyContestView.getOp().equals("salvaBeautyContest") ){
			 
			if(beautyContestView.getTitolo() == null || beautyContestView.getTitolo().isEmpty() ){
				errors.rejectValue("titolo", "errore.campo.obbligatorio.beautyContest.titolo");
			}
			
			if(beautyContestView.getDataChiusura() == null || beautyContestView.getDataChiusura().isEmpty() ){
				errors.rejectValue("dataChiusura", "errore.campo.obbligatorio.beautyContest.dataChiusura");
			}
			
			if( beautyContestView.getMaterie() == null || beautyContestView.getMaterie().length == 0 ){
				errors.rejectValue("listaMaterieAggiunteDesc", "errore.campo.obbligatorio.beautyContest.materie");
			}
			
			if(beautyContestView.getNazioneCode() == null || beautyContestView.getNazioneCode().trim().length() == 0){
				errors.rejectValue("nazioneCode", "errore.campo.obbligatorio.nazione");
			} 
			
			if(beautyContestView.getIncaricoAccordoQuadro() == null){
				errors.rejectValue("incaricoAccordoQuadro", "errore.campo.obbligatorio.beautyContest.incaricoAccordoQuadro");
			}
			
			if( DateUtil.isData(beautyContestView.getDataRichiestaAutorizzazione()) && DateUtil.isData(beautyContestView.getDataAutorizzazione() ) ){
				if( DateUtil.toDate(beautyContestView.getDataRichiestaAutorizzazione()).after( DateUtil.toDate(beautyContestView.getDataAutorizzazione()))){
					errors.rejectValue("dataRichiestaAutorizzazione", "errore.campo.obbligatorio.dataRichiestaAutorizzazioneSuperioreDataAutorizzazione");	
				}
			}
		}
	}

}
