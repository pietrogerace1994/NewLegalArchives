package eng.la.presentation.validator;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import eng.la.model.view.ProfessionistaEsternoView;
import eng.la.util.MyValidator;

public class ProfessionistaEsternoValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ProfessionistaEsternoView.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ProfessionistaEsternoView professionistaEsternoView = (ProfessionistaEsternoView) target;
		if(professionistaEsternoView.getOp() != null 
				&& professionistaEsternoView.getOp().equals("salvaProfessionistaEsterno") ){
			
			if(professionistaEsternoView.isDeleteMode()){
				
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "professionistaEsternoId", "errore.campo.obbligatorio.professionistaEsterno");
				if( errors.hasErrors() ){
					return;
				}
				
			} else {
				
				if(professionistaEsternoView.isEditMode()){
					
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "professionistaEsternoId", "errore.campo.obbligatorio.professionistaEsterno");
					if( errors.hasErrors() ){
						return;
					}
				}
				else{
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "motivazioneRichiesta", "errore.campo.obbligatorio.professionistaEsterno.motivazioneRichiesta");
					if( errors.hasErrors() ){
						return;
					}
				}
				
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "errore.campo.obbligatorio.professionistaEsterno.nome");
				if( errors.hasErrors() ){
					return;
				}
				
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cognome", "errore.campo.obbligatorio.professionistaEsterno.cognome");
				if( errors.hasErrors() ){
					return;
				}
				
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "codiceFiscale", "errore.campo.obbligatorio.professionistaEsterno.codiceFiscale");
				if( errors.hasErrors() ){
					return;
				}
				
				
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "telefono", "errore.campo.obbligatorio.professionistaEsterno.telefono");
				if( errors.hasErrors() ){
					return;
				}
				
				
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fax", "errore.campo.obbligatorio.professionistaEsterno.fax");
				if( errors.hasErrors() ){
					return;
				}
				
				List<String> email = professionistaEsternoView.getEmail();
				for (int i = 0; i < email.size(); i++) {
					if(email.get(i)!=null){
						
						if(email.get(i).trim().equalsIgnoreCase("")){
							errors.rejectValue("email["+i+"]", "errore.campo.obbligatorio.professionistaEsterno.email");
						}
						
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
				
				if( professionistaEsternoView.getNazioniAggiunte() == null || professionistaEsternoView.getNazioniAggiunte().length == 0 ){
					errors.rejectValue("nazioniAggiunte", "errore.campo.obbligatorio.professionistaEsterno.nazione");
				}
				
				if( errors.hasErrors() ){
					return;
				}
				
				if( professionistaEsternoView.getSpecializzazioniAggiunte() == null || professionistaEsternoView.getSpecializzazioniAggiunte().length == 0 ){
					errors.rejectValue("specializzazioniAggiunte", "errore.campo.obbligatorio.professionistaEsterno.specializzazione");
				}
				
				if( errors.hasErrors() ){
					return;
				}
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tipoProfessionistaCode", "errore.campo.obbligatorio.professionistaEsterno.tipoProfessionista");
				if( errors.hasErrors() ){
					return;
				}
				
				if(!professionistaEsternoView.isEditMode()){
					
					if( professionistaEsternoView.getTipoStudioLegale() == null){
						errors.rejectValue("tipoStudioLegale", "errore.campo.obbligatorio.professionistaEsterno.studioLegale");
					}
					
					if( errors.hasErrors() ){
						return;
					}
					
					if(professionistaEsternoView.getTipoStudioLegale().equalsIgnoreCase("0")){
						//Studio Legale Esistente
						ValidationUtils.rejectIfEmptyOrWhitespace(errors, "studioLegaleId", "errore.campo.obbligatorio.professionistaEsterno.studioLegaleEsistente");
						if( errors.hasErrors() ){
							return;
						}
					} else {
						//Nuovo Studio Legale
						ValidationUtils.rejectIfEmptyOrWhitespace(errors, "studioLegaleDenominazione", "errore.campo.obbligatorio.professionistaEsterno.studioLegaleDenominazione");
						ValidationUtils.rejectIfEmptyOrWhitespace(errors, "studioLegaleIndirizzo", "errore.campo.obbligatorio.professionistaEsterno.studioLegaleIndirizzo");
						ValidationUtils.rejectIfEmptyOrWhitespace(errors, "studioLegaleCap", "errore.campo.obbligatorio.professionistaEsterno.studioLegaleCap");
						ValidationUtils.rejectIfEmptyOrWhitespace(errors, "studioLegaleCitta", "errore.campo.obbligatorio.professionistaEsterno.studioLegaleCitta");
						ValidationUtils.rejectIfEmptyOrWhitespace(errors, "studioLegaleEmail", "errore.campo.obbligatorio.professionistaEsterno.studioLegaleEmail");
						ValidationUtils.rejectIfEmptyOrWhitespace(errors, "studioLegaleTelefono", "errore.campo.obbligatorio.professionistaEsterno.studioLegaleTelefono");
						ValidationUtils.rejectIfEmptyOrWhitespace(errors, "studioLegaleFax", "errore.campo.obbligatorio.professionistaEsterno.studioLegaleFax");
						ValidationUtils.rejectIfEmptyOrWhitespace(errors, "studioLegaleNazioneCode", "errore.campo.obbligatorio.professionistaEsterno.studioLegaleNazioneCode");
						ValidationUtils.rejectIfEmptyOrWhitespace(errors, "studioLegaleCodiceSap", "errore.campo.obbligatorio.professionistaEsterno.studioLegaleCodiceSap");
						ValidationUtils.rejectIfEmptyOrWhitespace(errors, "studioLegalePartitaIva", "errore.campo.obbligatorio.professionistaEsterno.studioLegalePartitaIva");
						
						if( errors.hasErrors() ){
							return;
						}
						
						if(!MyValidator.mailSyntaxCheck(professionistaEsternoView.getStudioLegaleEmail())){
							errors.rejectValue("studioLegaleEmail", "errore.campo.obbligatorio.professionistaEsterno.studioLegaleEmailIncorretta");
						}
						
						if( errors.hasErrors() ){
							return;
						}
					}
				} else {
					
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "studioLegaleId", "errore.campo.obbligatorio.professionistaEsterno.studioLegaleEsistente");
					if( errors.hasErrors() ){
						return;
					}
					
					//modifica Studio Legale
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "studioLegaleDenominazione", "errore.campo.obbligatorio.professionistaEsterno.studioLegaleDenominazione");
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "studioLegaleIndirizzo", "errore.campo.obbligatorio.professionistaEsterno.studioLegaleIndirizzo");
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "studioLegaleCap", "errore.campo.obbligatorio.professionistaEsterno.studioLegaleCap");
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "studioLegaleCitta", "errore.campo.obbligatorio.professionistaEsterno.studioLegaleCitta");
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "studioLegaleEmail", "errore.campo.obbligatorio.professionistaEsterno.studioLegaleEmail");
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "studioLegaleTelefono", "errore.campo.obbligatorio.professionistaEsterno.studioLegaleTelefono");
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "studioLegaleFax", "errore.campo.obbligatorio.professionistaEsterno.studioLegaleFax");
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "studioLegaleNazioneCode", "errore.campo.obbligatorio.professionistaEsterno.studioLegaleNazioneCode");
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "studioLegaleCodiceSap", "errore.campo.obbligatorio.professionistaEsterno.studioLegaleCodiceSap");
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "studioLegalePartitaIva", "errore.campo.obbligatorio.professionistaEsterno.studioLegalePartitaIva");
					
					if( errors.hasErrors() ){
						return;
					}
					
					if(!MyValidator.mailSyntaxCheck(professionistaEsternoView.getStudioLegaleEmail())){
						errors.rejectValue("studioLegaleEmail", "errore.campo.obbligatorio.professionistaEsterno.studioLegaleEmailIncorretta");
					}
					
					if( errors.hasErrors() ){
						return;
					}
					
				}
				
			}
			
		}
	}

}
