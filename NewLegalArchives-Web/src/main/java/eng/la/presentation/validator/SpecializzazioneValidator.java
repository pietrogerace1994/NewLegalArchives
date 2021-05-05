package eng.la.presentation.validator;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import eng.la.model.view.LinguaView;
import eng.la.model.view.SpecializzazioneView;

public class SpecializzazioneValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return SpecializzazioneView.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		SpecializzazioneView specializzazioneView = (SpecializzazioneView) target;
		if( specializzazioneView.getOp() != null 
				&& specializzazioneView.getOp().equals("salvaSpecializzazione") ){
			List<LinguaView> listaLingua = specializzazioneView.getListaLingua();
			
			if(specializzazioneView.isInsertMode()){
				
				for (int i = 0; i < listaLingua.size(); i++) {
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "specializzazioneIns["+i+"]", "errore.campo.obbligatorio.descrizione.specializzazione");
					if( errors.hasErrors() ){
						return;
					}
				}
				
			} else {
				
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "specializzazioneCode", "errore.campo.obbligatorio.specializzazione");
				if(!errors.hasErrors() && specializzazioneView.isFlagCode()){
					errors.rejectValue("specializzazioneCode", "errore.campo.obbligatorio.specializzazione");
				}
				
				if( errors.hasErrors() ){
					return;
				}
				
				if(!specializzazioneView.isDeleteMode()){
					for (int i = 0; i < listaLingua.size(); i++) {
						ValidationUtils.rejectIfEmptyOrWhitespace(errors, "specializzazioneDesc["+i+"]", "errore.campo.obbligatorio.descrizione.specializzazione");
						if( errors.hasErrors() ){
							return;
						}
					}
				}
			}
		}
	}

}
