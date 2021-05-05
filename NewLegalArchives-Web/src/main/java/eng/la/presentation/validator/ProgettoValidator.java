package eng.la.presentation.validator;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import eng.la.model.view.ProgettoView;

public class ProgettoValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ProgettoView.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ProgettoView progettoView = (ProgettoView) target;
		if( progettoView.getOp() != null 
				&& progettoView.getOp().equals("salvaProgetto") ){
			
				if (progettoView.getDataCreazione()==null || progettoView.getDataCreazione().isEmpty())
					errors.rejectValue("dataCreazione", "errore.campo.obbligatorio.dataCreazione");
				
				if (progettoView.getDataCreazione()!=null && progettoView.getDataChiusura()!=null){
					
					if (!progettoView.getDataCreazione().isEmpty() && !progettoView.getDataChiusura().isEmpty()){
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
						Date dataCreazione;
						try {
							dataCreazione = simpleDateFormat.parse(progettoView.getDataCreazione());
							Date dataChiusura = simpleDateFormat.parse(progettoView.getDataChiusura());

							if (dataChiusura.before(dataCreazione)) 
								errors.rejectValue("dataChiusura", "errore.datafine.minore.dataChiusura");
						
						} catch (ParseException e) {
							errors.rejectValue("dataChiusura", "errore.datafine.minore.dataChiusura");
						}
						
					}
				}

				if (progettoView.getOggetto()==null || progettoView.getOggetto().isEmpty())
					errors.rejectValue("oggetto", "errore.campo.obbligatorio.oggetto");
				
				if (progettoView.getNome()==null || progettoView.getNome().isEmpty())
					errors.rejectValue("nome", "errore.campo.obbligatorio.nome");
				
				if (progettoView.getDescrizione()==null || progettoView.getDescrizione().isEmpty())
					errors.rejectValue("descrizione", "errore.campo.obbligatorio.descrizione");

			
			if( errors.hasErrors() ){
				return;
			}
		}
	}

}
