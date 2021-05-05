package eng.la.presentation.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import eng.la.model.view.RepertorioStandardView;

public class RepertorioStandardValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return RepertorioStandardView.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		RepertorioStandardView repertorioStandardView = (RepertorioStandardView) target;
		if( repertorioStandardView.getOp() != null 
				&& repertorioStandardView.getOp().equals("salvaRepertorioStandard") ){
			
				if (repertorioStandardView.getNome()==null || repertorioStandardView.getNome().isEmpty())
					errors.rejectValue("nome", "errore.campo.obbligatorio.nome");
				
				if (repertorioStandardView.getNota()==null || repertorioStandardView.getNota().isEmpty())
					errors.rejectValue("nota", "errore.campo.obbligatorio.nota");

				if (repertorioStandardView.getSocietaSelezionata()==null || repertorioStandardView.getSocietaSelezionata().compareTo("")==0 || repertorioStandardView.getSocietaSelezionata().compareTo("0")==0)
					errors.rejectValue("idSocieta", "errore.campo.obbligatorio.societa");
				
//				if (repertorioStandardView.getIdPosizioneOrganizzativa()==null || repertorioStandardView.getIdPosizioneOrganizzativa().compareTo(0L)==0)
//					errors.rejectValue("idPosizioneOrganizzativa", "errore.campo.obbligatorio.idPosizioneOrganizzativa");
//				
//				if (repertorioStandardView.getIdPrimoLivelloAttribuzioni()==null || repertorioStandardView.getIdPrimoLivelloAttribuzioni().compareTo(0L)==0)
//					errors.rejectValue("idPrimoLivelloAttribuzioni", "errore.campo.obbligatorio.idPrimoLivelloAttribuzioni");
//
//				if (repertorioStandardView.getIdSecondoLivelloAttribuzioni()==null || repertorioStandardView.getIdSecondoLivelloAttribuzioni().compareTo(0L)==0)
//					errors.rejectValue("idSecondoLivelloAttribuzioni", "errore.campo.obbligatorio.idSecondoLivelloAttribuzioni");
//
			if( errors.hasErrors() ){
				return;
			}
		}
	}

}
