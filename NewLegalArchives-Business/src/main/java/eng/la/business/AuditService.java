package eng.la.business;

import eng.la.model.view.AuditLogView;
import eng.la.util.ListaPaginata;

public interface AuditService {

	ListaPaginata<AuditLogView> cerca(String tipoEntita, String userId, String dal, String al, int numElementiPerPagina,
			int numeroPagina, String ordinamento, String tipoOrdinamento) throws Throwable;

}
