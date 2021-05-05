package eng.la.presentation.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import eng.la.controller.BaseController;
import eng.la.model.view.FascicoloView;
import eng.la.util.costants.Costanti;

public class FascicoloValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return FascicoloView.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FascicoloView fascicoloView = (FascicoloView) target;
		if( fascicoloView.getOp() != null 
				&& fascicoloView.getOp().equals("salvaFascicolo") ){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tipologiaFascicoloCode", "errore.campo.obbligatorio.tipologia");
			if( errors.hasErrors() ){
				return;
			}
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "settoreGiuridicoCode", "errore.campo.obbligatorio.settore"); 
			if( errors.hasErrors() ){
				return;
			}
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "oggettoSintetico", "errore.campo.obbligatorio.oggetto");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "legaleInterno", "errore.campo.obbligatorio.legaleInterno");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "unitaLegale", "errore.campo.obbligatorio.unitaLegale");
		//	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "siglaCliente", "errore.campo.obbligatorio.siglaCliente");

			if(fascicoloView.getOggettoSintetico().length() > 256 ){
				errors.rejectValue("oggettoSintetico", "errore.campo.troppo.lungo.oggetto");
			}
			
			if( fascicoloView.getTipologiaFascicoloCode().equals( BaseController.TIPOLOGIA_FASCICOLO_GIUDIZIALE_COD )){
				if( fascicoloView.getSettoreGiuridicoCode().equals( Costanti.SETTORE_GIURIDICO_CIVILE_CODE) ){
					if( fascicoloView.getPosizioneSocietaAddebitoCode() == null || fascicoloView.getPosizioneSocietaAddebitoCode().trim().length() == 0 ){
						errors.rejectValue("posizioneSocietaAddebitoCode", "errore.campo.obbligatorio.posizioneSocieta");
						
					}
				}
				if( fascicoloView.getSocietaAddebitoAggiunte() == null || fascicoloView.getSocietaAddebitoAggiunte().length == 0 ){
					errors.rejectValue("societaAddebitoAggiunte", "errore.campo.obbligatorio.societaAddebito");
				}
				if( fascicoloView.getSocietaProcedimentoAggiunte() == null || fascicoloView.getSocietaProcedimentoAggiunte().length == 0 ){
					errors.rejectValue("societaProcedimentoAggiunte", "errore.campo.obbligatorio.societaProcedimento");
				}
			}
			
			if(fascicoloView.getNazioneCode() == null || fascicoloView.getNazioneCode().trim().length() == 0){
				errors.rejectValue("nazioneCode", "errore.campo.obbligatorio.nazione");
				
			} 
		}
	}

}
