package eng.la.presentation.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import eng.la.model.view.UdienzaView;

public class UdienzaValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UdienzaView.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UdienzaView view = (UdienzaView) target;
		if( view.getOp() != null 
				&& view.getOp().equals("salvaUdienza") ){
			if( view.getUdienzaId() == null ){
				
				if(view.getDataUdienza() == null || view.getDataUdienza().isEmpty()){
					errors.rejectValue("dataUdienza", "errore.campo.obbligatorio.dataUdienza");
				}
			}else{ 
				/** DA COMPILARE **/
			}
		}
	}

}
