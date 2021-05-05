package eng.la.presentation.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import eng.la.model.view.OrganoSocialeView;

public class OrganoSocialeValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return OrganoSocialeView.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		OrganoSocialeView organoSocialeView = (OrganoSocialeView) target;
		if( organoSocialeView.getOp() != null 
				&& organoSocialeView.getOp().equals("salvaOrganoSociale") ){
			
				if (organoSocialeView.getCognome()==null || organoSocialeView.getCognome().isEmpty())
					errors.rejectValue("codice", "errore.campo.obbligatorio.cognome");
				
				if (organoSocialeView.getNome()==null || organoSocialeView.getNome().isEmpty())
					errors.rejectValue("nome", "errore.campo.obbligatorio.nome");
				
				if (organoSocialeView.getIdSocietaAffari()==null || organoSocialeView.getIdSocietaAffari().compareTo(0L)==0)
					errors.rejectValue("idSocietaAffari", "errore.campo.obbligatorio.societa");
				
				if (organoSocialeView.getTipoOrganoSociale()==null || organoSocialeView.getTipoOrganoSociale().compareTo(0L)==0)
					errors.rejectValue("tipoOrganoSociale", "errore.campo.obbligatorio.tipoOrganoSociale");

				
			if( errors.hasErrors() ){
				return;
			}
		}
	}

}
