package eng.la.presentation.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import eng.la.model.view.AffariSocietariView;

public class AffariSocietariValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return AffariSocietariView.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AffariSocietariView repertorioStandardView = (AffariSocietariView) target;
		if (repertorioStandardView.getOp() != null && repertorioStandardView.getOp().equals("salvaAffariSocietari")) {

			if (repertorioStandardView.getDenominazione() == null
					|| repertorioStandardView.getDenominazione().isEmpty())
				errors.rejectValue("denominazione", "errore.campo.obbligatorio.denominazione");
			if (repertorioStandardView.getCodiceSocieta() == null
					|| repertorioStandardView.getCodiceSocieta().compareTo(0L) == 0)
				errors.rejectValue("codiceSocieta", "errore.campo.obbligatorio.codiceSocieta");
			if (repertorioStandardView.getCapitaleSottoscritto() == null
					|| repertorioStandardView.getCapitaleSottoscritto().compareTo(0L) == 0)
				errors.rejectValue("capitaleSottoscritto", "errore.campo.obbligatorio.capitaleSottoscritto");
			if (repertorioStandardView.getCapitaleSociale() == null
					|| repertorioStandardView.getCapitaleSociale().compareTo(0L) == 0)
				errors.rejectValue("capitaleSociale", "errore.campo.obbligatorio.capitaleSociale");
			if (repertorioStandardView.getIdNazione() == null
					|| repertorioStandardView.getIdNazione().compareTo(0L) == 0)
				errors.rejectValue("idNazione", "errore.campo.obbligatorio.idNazione");

			if (errors.hasErrors()) {
				return;
			}
		}
	}

}
