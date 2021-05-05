package eng.la.presentation.validator;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import eng.la.model.view.LinguaView;
import eng.la.model.view.TipoProcureView;

public class TipoProcureValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return TipoProcureView.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		TipoProcureView tipoProcureView = (TipoProcureView) target;
		if( tipoProcureView.getOp() != null 
				&& tipoProcureView.getOp().equals("salvaTipoProcure") ){
			List<LinguaView> listaLingua = tipoProcureView.getListaLingua();
			
			if(tipoProcureView.isInsertMode()){
				
				for (int i = 0; i < listaLingua.size(); i++) {
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tipoProcureIns["+i+"]", "errore.campo.obbligatorio.descrizione.tipoProcure");
					if( errors.hasErrors() ){
						return;
					}
				}
				
			} else {
				
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tipoProcureCode", "errore.campo.obbligatorio.tipoProcure");
				if(!errors.hasErrors() && tipoProcureView.isFlagCode()){
					errors.rejectValue("tipoProcureCode", "errore.campo.obbligatorio.tipoProcure");
				}
				
				if( errors.hasErrors() ){
					return;
				}
				
				if(!tipoProcureView.isDeleteMode()){
					for (int i = 0; i < listaLingua.size(); i++) {
						ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tipoProcureDesc["+i+"]", "errore.campo.obbligatorio.descrizione.tipoProcure");
						if( errors.hasErrors() ){
							return;
						}
					}
				}
			}
		}
	}

}
