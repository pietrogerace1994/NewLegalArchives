package eng.la.presentation.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import eng.la.model.view.CentroDiCostoView;

public class CentroDiCostoValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return CentroDiCostoView.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CentroDiCostoView centroDiCostoView = (CentroDiCostoView) target;
		if(centroDiCostoView.getOp() != null 
				&& centroDiCostoView.getOp().equals("salvaCentroDiCosto") ){
			
			
			
			
		}
	}

}
