package eng.la.presentation.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import eng.la.model.view.SocietaView;

public class SocietaValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return SocietaView.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		// CAMPI FACOLTATIVI:
		// indirizzo
		// cap
		// citta
		// nazione
		
		// CAMPI OBBLIGATORI:
		// nome
		// ragione sociale
		// tipo societa
		// email amministrazione
		// partita iva / codice fiscale
		
		SocietaView societaView = (SocietaView) target;
		
		if( societaView.getOp() != null && societaView.getOp().equals("salvaSocieta") ) {
			
			if(societaView.isEditMode()) {
				
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "errore.campo.obbligatorio.societa.nome");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ragioneSociale", "errore.campo.obbligatorio.societa.ragioneSociale");
				
				if(societaView.getIdTipoSocieta() == null || societaView.getIdTipoSocieta() == 0) {
					errors.rejectValue("idTipoSocieta", "errore.campo.obbligatorio.societa.idTipoSocieta");
				} 
				
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailAmministrazione", "errore.campo.obbligatorio.societa.emailAmministrazione");
			}
			else if(societaView.isInsertMode()) {
				
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nomeIns", "errore.campo.obbligatorio.societa.nome");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ragioneSocialeIns", "errore.campo.obbligatorio.societa.ragioneSociale");
				
				if(societaView.getIdTipoSocietaIns() == null || societaView.getIdTipoSocietaIns() == 0) {
					errors.rejectValue("idTipoSocietaIns", "errore.campo.obbligatorio.societa.idTipoSocieta");
				} 
				
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailAmministrazioneIns", "errore.campo.obbligatorio.societa.emailAmministrazione");
				
			}
			else if(societaView.isDeleteMode()){
				if(societaView.getIdSocieta() == null || societaView.getIdSocieta() == 0)
					errors.rejectValue("idSocieta", "errore.campo.obbligatorio.societa.idSocieta");	
			}
			
				
		}
	}

}
