package eng.la.presentation.validator;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import eng.la.model.view.LinguaView;
import eng.la.model.view.MateriaView;

public class MateriaValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return MateriaView.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		MateriaView materiaView = (MateriaView) target;
		
		if( materiaView.getOp() != null && materiaView.getOp().equals("salvaMateria") ) {
			
			if(materiaView.isEditMode()) {
				
				if(materiaView.getTipologiaFascicoloId() == null || materiaView.getTipologiaFascicoloId() == 0) 
					errors.rejectValue("tipologiaFascicoloId", "errore.campo.obbligatorio.materia.tipologiaFascicolo");
				
				if(materiaView.getSettoreGiuridicoId() == null || materiaView.getSettoreGiuridicoId() == 0) 
					errors.rejectValue("settoreGiuridicoId", "errore.campo.obbligatorio.materia.settoreGiuridicoId");	
				
				if(materiaView.getMateriaCodGruppoLingua() == null || materiaView.getMateriaCodGruppoLingua().equals("0") )
					errors.rejectValue("materiaCodGruppoLingua", "errore.campo.obbligatorio.materia.idMateria");
				
				boolean erroreTrovato=false; 
				List<LinguaView> listaLingua = materiaView.getListaLingua();
				for(int i=0; i< listaLingua.size(); i++) {
					LinguaView lingua = materiaView.getListaLingua().get(i);
//					String lang = lingua.getVo().getLang();
					
					String nome = materiaView.getNome().get((int)lingua.getVo().getId());
					if(nome == null || nome.isEmpty() ) {
						erroreTrovato=true;
						break;
					}
				}
				if(erroreTrovato)
					errors.rejectValue("nome", "errore.campo.obbligatorio.materia.nome");
				
				
			}
			else if(materiaView.isInsertMode()) {
				
				if(materiaView.getTipologiaFascicoloIdIns() == null || materiaView.getTipologiaFascicoloIdIns() == 0) 
					errors.rejectValue("tipologiaFascicoloIdIns", "errore.campo.obbligatorio.materia.tipologiaFascicolo");
				
				if(materiaView.getSettoreGiuridicoIdIns() == null || materiaView.getSettoreGiuridicoIdIns() == 0) 
					errors.rejectValue("settoreGiuridicoIdIns", "errore.campo.obbligatorio.materia.settoreGiuridicoId");	
				
				if(materiaView.getMateriaPadreCodGruppoLinguaIns() == null || materiaView.getMateriaPadreCodGruppoLinguaIns().equals("0") )
					errors.rejectValue("materiaPadreCodGruppoLinguaIns", "errore.campo.obbligatorio.materia.materiaPadreCodGruppoLingua");
				
				boolean erroreTrovato=false; 
				List<LinguaView> listaLingua = materiaView.getListaLingua();
				for(int i=0; i< listaLingua.size(); i++) {
					LinguaView lingua = materiaView.getListaLingua().get(i);
//					String lang = lingua.getVo().getLang();
					
					String nome = materiaView.getNomeIns().get((int)lingua.getVo().getId());
					if(nome == null || nome.isEmpty() ) {
						erroreTrovato=true;
						break;
					}
				}
				if(erroreTrovato)
					errors.rejectValue("nomeIns", "errore.campo.obbligatorio.materia.nome");
			}
			else if(materiaView.isDeleteMode()){
				
				if(materiaView.getTipologiaFascicoloId() == null || materiaView.getTipologiaFascicoloId() == 0) 
					errors.rejectValue("tipologiaFascicoloId", "errore.campo.obbligatorio.materia.tipologiaFascicolo");
				
				if(materiaView.getSettoreGiuridicoId() == null || materiaView.getSettoreGiuridicoId() == 0) 
					errors.rejectValue("settoreGiuridicoId", "errore.campo.obbligatorio.materia.settoreGiuridicoId");	
				
				if(materiaView.getMateriaCodGruppoLingua() == null || materiaView.getMateriaCodGruppoLingua().equals("0") )
					errors.rejectValue("materiaCodGruppoLingua", "errore.campo.obbligatorio.materia.idMateria");	
			}
				
		}
	}

}
