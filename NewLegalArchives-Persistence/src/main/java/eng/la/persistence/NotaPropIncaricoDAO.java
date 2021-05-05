package eng.la.persistence;

import java.util.List;

import eng.la.model.NotaPropIncarico;

public interface NotaPropIncaricoDAO {
	
	public List<NotaPropIncarico> leggiNotaPropostaIncaricoPerId(List<Long> listaIdNotaProposta) throws Throwable;

	public NotaPropIncarico leggiNotaPropostaIncarico(Long id) throws Throwable;;

}
