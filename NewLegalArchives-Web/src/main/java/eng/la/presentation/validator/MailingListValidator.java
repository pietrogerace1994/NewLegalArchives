package eng.la.presentation.validator;



import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import eng.la.model.view.MailinglistView;

public class MailingListValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return MailinglistView.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		MailinglistView mailingListView = (MailinglistView) target;
		if(mailingListView.getOp() != null 
				&& mailingListView.getOp().equals("salvaMailingList") ){

			if(mailingListView.isDeleteMode()){

				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mailingListId", "errore.campo.obbligatorio.mailingList");
				if( errors.hasErrors() ){
					return;
				}

			} 
			else if(mailingListView.isEditMode()){

				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mailingListId", "errore.campo.obbligatorio.mailingList");
				if( errors.hasErrors() ){
					return;
				}

				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nomeMod", "errore.campo.obbligatorio.mailingList.nome");
				if( errors.hasErrors() ){
					return;
				}


				if( mailingListView.getNominativiAggiuntiMod() == null || mailingListView.getNominativiAggiuntiMod().length == 0 ){
					errors.rejectValue("nominativiAggiuntiMod", "errore.campo.obbligatorio.mailingList.nominativiAggiunti");
				}
				if( errors.hasErrors() ){
					return;
				}


				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categoriaCodeMod", "errore.campo.obbligatorio.mailingList.categoria");
				if( errors.hasErrors() ){
					return;
				}
			}
			else if(mailingListView.isInsertMode()){

				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mailingListId", "errore.campo.obbligatorio.mailingList");
				if( errors.hasErrors() ){
					return;
				}

				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "errore.campo.obbligatorio.mailingList.nome");
				if( errors.hasErrors() ){
					return;
				}


				if( mailingListView.getNominativiAggiunti() == null || mailingListView.getNominativiAggiunti().length == 0 ){
					errors.rejectValue("nominativiAggiunti", "errore.campo.obbligatorio.mailingList.nominativiAggiunti");
				}
				if( errors.hasErrors() ){
					return;
				}


				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categoriaCode", "errore.campo.obbligatorio.mailingList.categoria");
				if( errors.hasErrors() ){
					return;
				}
			}	


		}

	}
}


