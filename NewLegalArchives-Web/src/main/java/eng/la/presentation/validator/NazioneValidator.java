package eng.la.presentation.validator;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import eng.la.model.view.LinguaView;
import eng.la.model.view.NazioneView;

public class NazioneValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return NazioneView.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		NazioneView nazioneView = (NazioneView) target;
		if( nazioneView.getOp() != null 
				&& nazioneView.getOp().equals("salvaNazione") ){
			List<LinguaView> listaLingua = nazioneView.getListaLingua();
			
			if(nazioneView.isInsertMode()){
				
				for (int i = 0; i < listaLingua.size(); i++) {
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nazioneIns["+i+"]", "errore.campo.obbligatorio.descrizione.nazione");
					if( errors.hasErrors() ){
						return;
					}
				}
				
			} else {
				
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nazioneCode", "errore.campo.obbligatorio.nazione");
				if(!errors.hasErrors() && nazioneView.isFlagCode()){
					errors.rejectValue("nazioneCode", "errore.campo.obbligatorio.nazione");
				}
				
				if( errors.hasErrors() ){
					return;
				}
				
				if(!nazioneView.isDeleteMode()){
					for (int i = 0; i < listaLingua.size(); i++) {
						ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nazioneDesc["+i+"]", "errore.campo.obbligatorio.descrizione.nazione");
						if( errors.hasErrors() ){
							return;
						}
					}
				}
			}
		}
	}

}
