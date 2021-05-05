package eng.la.presentation.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import eng.la.model.view.ProformaView;

public class ProformaValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ProformaView.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ProformaView view = (ProformaView) target;
		
		if (view.getOp() != null && view.getOp().equals("salvaProforma")) {
			try {
				if (view.getSocietaAddebitoScelta() == null || view.getSocietaAddebitoScelta().trim().equals("")) {
					errors.rejectValue("societaAddebitoScelta", "errore.campo.obbligatorio.societaProforma");
					return;
				}

				if (view.getValutaId() == null) {
					errors.rejectValue("valutaId", "errore.campo.obbligatorio.tipoValuta");
				}

				if (StringUtils.isBlank(view.getAnnoEsercizioFinanziario())) {
					errors.rejectValue("annoEsercizioFinanziario",
							"errore.campo.obbligatorio.annoEsercizioFinanziario");
				}

				if (StringUtils.isBlank(view.getDataInserimento())) {
					errors.rejectValue("dataInserimento", "errore.campo.obbligatorio.dataInserimento");
				}
				if (StringUtils.isBlank(view.getVoceDiConto())) {
					errors.rejectValue("voceDiConto", "errore.campo.obbligatorio.voceDiConto");
				}
				if (StringUtils.isBlank(view.getCentroDiCosto())) {
					errors.rejectValue("centroDiCosto", "errore.campo.obbligatorio.centroDiCosto");
				}
				
				
				if (view.getProformaId() == null || view.getProformaId() == 0) { // NUOVO

				} else {// MODIFICA
					
				}
				
				
				
			} catch (Throwable e) {
				e.printStackTrace();
			}
			finally{
			}
		}
	}

	
}
