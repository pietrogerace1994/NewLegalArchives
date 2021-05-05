package eng.la.presentation.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import eng.la.model.view.ProcureView;

public class ProcureValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return ProcureView.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ProcureView procureView = (ProcureView) target;
		if( procureView.getOp() != null 
				&& procureView.getOp().equals("salvaProcure") ){
			
				if (procureView.getDataConferimento()==null || procureView.getDataConferimento().isEmpty())
					errors.rejectValue("dataConferimento", "errore.campo.obbligatorio.dataConferimento");
				
				if (procureView.getDataConferimento()!=null && procureView.getDataRevoca()!=null){
					
					if (!procureView.getDataConferimento().isEmpty() && !procureView.getDataRevoca().isEmpty()){
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
						Date dataConferimento;
						try {
							dataConferimento = simpleDateFormat.parse(procureView.getDataConferimento());
							Date dataRevoca = simpleDateFormat.parse(procureView.getDataRevoca());

							if (dataRevoca.before(dataConferimento)) 
								errors.rejectValue("dataRevoca", "errore.datafine.minore.dataRevoca");
						
						} catch (ParseException e) {
							errors.rejectValue("dataRevoca", "errore.datafine.minore.dataRevoca");
						}
						
					}
				}

				if (procureView.getIdNotaio()==null || procureView.getIdNotaio().compareTo(0L)==0)
					errors.rejectValue("idNotaio", "errore.campo.obbligatorio.idNotaio");
				
				if (procureView.getIdSocieta()==null || procureView.getIdSocieta().compareTo(0L)==0)
					errors.rejectValue("idSocieta", "errore.campo.obbligatorio.idSocieta");
				
				if (procureView.getNumeroRepertorio()==null || procureView.getNumeroRepertorio().isEmpty())
					errors.rejectValue("numeroRepertorio", "errore.campo.obbligatorio.numeroRepertorio");
			
			if( errors.hasErrors() ){
				return;
			}
		}
	}

}
