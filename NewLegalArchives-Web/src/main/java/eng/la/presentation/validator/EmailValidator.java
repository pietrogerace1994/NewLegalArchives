package eng.la.presentation.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import eng.la.model.view.EmailView;

public class EmailValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) { 
		return EmailView.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		EmailView emailView = (EmailView) target;
		if( emailView.getOp() != null 
				&& emailView.getOp().equals("salvaEmail") ){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "oggetto", "errore.campo.obbligatorio.titolo");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contenutoBreve", "errore.campo.obbligatorio.abstract");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contenuto", "errore.campo.obbligatorio.contenuto");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dataEmail", "errore.campo.obbligatorio.dataarticolo");
			
			if(emailView.getCategoriaCode()== null || emailView.getCategoriaCode().trim().length() == 0){
				errors.rejectValue("categoriaCode", "errore.campo.obbligatorio.categoria");
			}
		}
	}

}
