package eng.la.presentation.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import eng.la.model.view.IncaricoView;
import eng.la.util.DateUtil;

public class IncaricoValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return IncaricoView.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		IncaricoView incaricoView = (IncaricoView) target;
		if( incaricoView.getOp() != null 
				&& incaricoView.getOp().equals("salvaIncarico") ){
			if( incaricoView.getIncaricoId() == null ){
			 
				if(incaricoView.getProfessionistaId() == null || incaricoView.getProfessionistaId() == 0 ){
					errors.rejectValue("professionistaId", "errore.campo.obbligatorio.professionista");
				}
				
				if( DateUtil.isData(incaricoView.getDataRichiestaAutorizzazione()) && DateUtil.isData(incaricoView.getDataAutorizzazione() ) ){
					if( DateUtil.toDate(incaricoView.getDataRichiestaAutorizzazione()).after( DateUtil.toDate(incaricoView.getDataAutorizzazione()))){
						errors.rejectValue("dataRichiestaAutorizzazione", "errore.campo.obbligatorio.dataRichiestaAutorizzazioneSuperioreDataAutorizzazione");	
					}
				}
			}else{ 
				/*LETTERA INCARICO*/
				if(  StringUtils.isNotBlank(incaricoView.getProtocollo()) ||  StringUtils.isNotBlank(incaricoView.getDescrizioneProtocollo()) 
						||  StringUtils.isNotBlank(incaricoView.getOggettoProtocollo())){
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "protocollo", "errore.campo.obbligatorio.protocollo");
					//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "descrizioneProtocollo", "errore.campo.obbligatorio.descrizioneProtocollo");
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "oggettoProtocollo", "errore.campo.obbligatorio.oggettoProtocollo");
					if( !DateUtil.isData( incaricoView.getDataProtocollo() )){
						errors.rejectValue("dataProtocollo", "errore.campo.obbligatorio.dataProtocollo");
					}
					
					if( !DateUtil.isYear(incaricoView.getSaldoAnno())){
						errors.rejectValue("saldoAnno", "errore.campo.obbligatorio.saldoanno");
					}
								
				}
				
				/*NOTA PROPOSTA INCARICO*/
				if(  StringUtils.isNotBlank(incaricoView.getDescrizione()) &&  StringUtils.isNotBlank(incaricoView.getPratica()) 
						&& StringUtils.isNotBlank(incaricoView.getOggetto()) 
						&& StringUtils.isNotBlank(incaricoView.getProposta()) && StringUtils.isNotBlank(incaricoView.getDataNotaProposta())){
				
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "descrizione", "errore.campo.obbligatorio.descrizione");
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pratica", "errore.campo.obbligatorio.pratica");
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "oggetto", "errore.campo.obbligatorio.oggetto");
					//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "valoreIncarico", "errore.campo.obbligatorio.valoreIncarico");
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "proponente", "errore.campo.obbligatorio.proponente");
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "proposta", "errore.campo.obbligatorio.proposta");
					if(incaricoView.getDataNotaProposta() == null || incaricoView.getDataNotaProposta().length() == 0 ||
							!DateUtil.isData(incaricoView.getDataNotaProposta())){
						errors.rejectValue("dataNotaProposta", "errore.campo.obbligatorio.dataNotaProposta");
					}
				}
			}
		}
	}

}
