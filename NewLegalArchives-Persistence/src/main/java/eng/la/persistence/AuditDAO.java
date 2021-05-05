package eng.la.persistence;

import java.util.List;

import eng.la.model.AuditLog;

public interface AuditDAO {

	public List<AuditLog> cerca(String tipoEntita, String userId, String dal, String al, int numElementiPerPagina,
			int numeroPagina, String ordinamento, String tipoOrdinamento) throws Throwable;

	public Long conta(String tipoEntita, String userId, String dal, String al)throws Throwable;
	
}
