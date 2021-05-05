package eng.la.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.AuditLog;
import eng.la.model.view.AuditLogView;
import eng.la.persistence.AuditDAO;
import eng.la.util.ListaPaginata;

@Service("auditService")
public class AuditServiceImpl extends BaseService<AuditLog,AuditLogView> implements AuditService{

	@Autowired
	private AuditDAO auditDao;
	
	@Override
	public ListaPaginata<AuditLogView> cerca(String tipoEntita, String userId, String dal, String al,
			int numElementiPerPagina, int numeroPagina, String ordinamento, String tipoOrdinamento) throws Throwable {
		List<AuditLog> lista = auditDao.cerca(tipoEntita, userId, dal, al, numElementiPerPagina, numeroPagina, ordinamento,
				tipoOrdinamento);
		List<AuditLogView> listaView = convertiVoInView(lista);
		ListaPaginata<AuditLogView> listaRitorno = new ListaPaginata<AuditLogView>();
		Long conta = auditDao.conta(tipoEntita, userId, dal, al);
		listaRitorno.addAll(listaView);
		listaRitorno.setNumeroElementiPerPagina(numElementiPerPagina);
		listaRitorno.setPaginaCorrente(numeroPagina);
		listaRitorno.setNumeroTotaleElementi(conta);
		listaRitorno.setOrdinamento(ordinamento);
		listaRitorno.setOrdinamentoDirezione(tipoOrdinamento);
		return listaRitorno;
	}

	@Override
	protected Class<AuditLog> leggiClassVO() {
		 
		return AuditLog.class;
	}

	@Override
	protected Class<AuditLogView> leggiClassView() {

		return AuditLogView.class;
	}

}
