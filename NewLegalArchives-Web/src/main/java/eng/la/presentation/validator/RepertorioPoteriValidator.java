package eng.la.presentation.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import eng.la.model.view.RepertorioPoteriView;

public class RepertorioPoteriValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return RepertorioPoteriView.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		RepertorioPoteriView repertorioPoteriView = (RepertorioPoteriView) target;
		if( repertorioPoteriView.getOp() != null 
				&& repertorioPoteriView.getOp().equals("salvaRepertorioPoteri") ){
			
				if (repertorioPoteriView.getCodice()==null || repertorioPoteriView.getCodice().isEmpty())
					errors.rejectValue("codice", "errore.campo.obbligatorio.codice");
				
				if (repertorioPoteriView.getDescrizione()==null || repertorioPoteriView.getDescrizione().isEmpty())
					errors.rejectValue("descrizione", "errore.campo.obbligatorio.descrizione");

				if (repertorioPoteriView.getTesto()==null || repertorioPoteriView.getTesto().isEmpty())
					errors.rejectValue("testo", "errore.campo.obbligatorio.testo");
				else if (repertorioPoteriView.getTesto().length()>4000)
					errors.rejectValue("testo", "errore.campo.troppolungo.testo");
				
				if (repertorioPoteriView.getIdCategoria()==null || repertorioPoteriView.getIdCategoria().compareTo(0L)==0)
					errors.rejectValue("idCategoria", "errore.campo.obbligatorio.idCategoria");
				
				if (repertorioPoteriView.getIdSubcategoria()==null || repertorioPoteriView.getIdSubcategoria().compareTo(0L)==0)
					errors.rejectValue("idSubcategoria", "errore.campo.obbligatorio.idSubcategoria");
			
			if( errors.hasErrors() ){
				return;
			}
		}
	}

}
