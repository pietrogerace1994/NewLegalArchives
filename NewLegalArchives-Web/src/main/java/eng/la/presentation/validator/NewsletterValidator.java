package eng.la.presentation.validator;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import eng.la.model.view.NewsletterView;
import eng.la.util.MyValidator;

public class NewsletterValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) { 
		return NewsletterView.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		NewsletterView emailView = (NewsletterView) target;
		if( emailView.getOp() != null 
				&& emailView.getOp().equals("salvaNewsletter") ){
			//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numero", "errore.campo.obbligatorio.numero");

//			if(emailView.getCopertina().getSize()==0 && emailView.getNewsletterId()==null)
//				errors.rejectValue("copertina", "errore.campo.obbligatorio.copertina");
			
			List<String> email = emailView.getEmail();
			for (int i = 0; i < email.size(); i++) {
				if(email.get(i)!=null && !email.get(i).isEmpty()){
					
					if( errors.hasErrors() ){
						return;
					}
					
					if(!MyValidator.mailSyntaxCheck(email.get(i))){
						errors.rejectValue("email["+i+"]", "errore.campo.obbligatorio.professionistaEsterno.emailIncorretta");
					}
					
					if( errors.hasErrors() ){
						return;
					}
				}
			}
		}
	}

}
