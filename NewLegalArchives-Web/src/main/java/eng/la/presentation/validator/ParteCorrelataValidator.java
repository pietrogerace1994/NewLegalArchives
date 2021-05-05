package eng.la.presentation.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

//import eng.la.controller.BaseController;
//import eng.la.model.view.FascicoloView;
import eng.la.model.view.ParteCorrelataView;



/**
 * Classe preposta alla validazione dei dati inseriti in form.
 * @author ACER
 */
public class ParteCorrelataValidator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
		return ParteCorrelataView.class.equals(clazz);
	}
	
	/**
	 * Controllo di validità dei dati inseriti in form.
	 * <p>
	 * @param target
	 * @param Errors
	 */
	@Override
	public void validate(Object target, Errors errors) {
		
		ParteCorrelataView parteCorrelataView = (ParteCorrelataView) target;
		
		if( parteCorrelataView.getOp() != null && parteCorrelataView.getOp().equals("salvaParteCorrelata") )
		{
			
			
			if(parteCorrelataView.isEditMode()) {
				
				// controllo validità denominazione.
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "denominazioneMod", "errore.campo.obbligatorio.denominazione");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tipoCorrelazioneCodeMod", "errore.campo.obbligatorio.tipoCorrelazione"); 
				
				if( errors.hasErrors() )
					return;
					
			}
			else if(parteCorrelataView.isInsertMode()) {
				
				// controllo validità denominazione.
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "denominazione", "errore.campo.obbligatorio.denominazione");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tipoCorrelazioneCode", "errore.campo.obbligatorio.tipoCorrelazione");
				
				if( errors.hasErrors() )
					return;
				
			}
			else if(parteCorrelataView.isDeleteMode()){
			
				if(parteCorrelataView.getParteCorrelataIdMod() == null || parteCorrelataView.getParteCorrelataIdMod() == 0) 
					errors.rejectValue("parteCorrelataIdMod", "errore.campo.obbligatorio.parteCorrelata.parteCorrelataIdMod");
				
				if( errors.hasErrors() )
					return;
				
			}
			
		}
		else if( parteCorrelataView.getOp() != null && parteCorrelataView.getOp().equals("ricercaParteCorrelata") )
		{
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "denominazione", "errore.campo.obbligatorio.denominazione");
			
			if( errors.hasErrors() )
				return;
		}
		else if( parteCorrelataView.getOp() != null && parteCorrelataView.getOp().equals("storicoParteCorrelata") )
		{
//			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dataFine", "errore.campo.obbligatorio.dataFine");
//			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dataInzio", "errore.campo.obbligatorio.dataInizio");
//			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dataFine", "errore.campo.obbligatorio.dataFine");
			
			String dataInizioStr = parteCorrelataView.getDataInizio();
			Date dataInizio = null;
			if(dataInizioStr!=null && !dataInizioStr.isEmpty())
			{
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
				try {
					dataInizio = simpleDateFormat.parse(dataInizioStr);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			String dataFineStr = parteCorrelataView.getDataFine();
			Date dataFine = null;
			if(dataFineStr!=null && !dataFineStr.isEmpty())
			{
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
				try {
					dataFine = simpleDateFormat.parse(dataFineStr);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			if(dataInizio == null && dataFine != null ) {
				errors.rejectValue("dataInizio", "errore.campo.obbligatorio.dataInizio");
				
				if( errors.hasErrors() )
					return;
			}
			
			if(dataFine != null ) {
				
				if( dataFine.before(dataInizio) ) {
//					errors.rejectValue("dataInizio", "errore.datafine.maggiore.datainizio");
					errors.rejectValue("dataFine", "errore.datafine.maggiore.datainizio");
				}
				
				if( errors.hasErrors() )
					return;
				
			}
			
			
			if( errors.hasErrors() )
				return;
		}	
			
		
	}
	
	
	
}