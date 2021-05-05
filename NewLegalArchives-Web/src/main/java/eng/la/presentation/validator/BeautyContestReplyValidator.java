package eng.la.presentation.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import eng.la.model.view.BeautyContestReplyView;

public class BeautyContestReplyValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return BeautyContestReplyView.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
	}

}
