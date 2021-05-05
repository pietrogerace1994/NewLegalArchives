package eng.la.presentation.validator;


import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import eng.la.model.view.RubricaView;
import eng.la.util.MyValidator;

public class RubricaValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) { 
		return RubricaView.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		RubricaView rubricaView = (RubricaView) target;
		if( rubricaView.getOp() != null 
				&& rubricaView.getOp().equals("salvaRubrica") ){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nominativo", "errore.campo.obbligatorio.rubrica.nominativo");

			String email = rubricaView.getEmail();
				if(email!=null){
					
					if(email.trim().equalsIgnoreCase("")){
						errors.rejectValue("email", "errore.campo.obbligatorio.rubrica.email");
					}
					
					if( errors.hasErrors() ){
						return;
					}
					
					if(!MyValidator.mailSyntaxCheck(email)){
						errors.rejectValue("email", "errore.campo.obbligatorio.rubrica.emailIncorretta");
					}
					
					if( errors.hasErrors() ){
						return;
					}
			}
			
			
		}
	}

}
