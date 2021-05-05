package eng.la.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.Procuratore;
import eng.la.model.view.ProcuratoreView;
import eng.la.persistence.ProcuratoreDAO;

@Service("procuratoreService")
public class ProcuratoreServiceImpl extends BaseService<Procuratore,ProcuratoreView> implements ProcuratoreService {
	@Autowired
	private ProcuratoreDAO procuratoreDao;

	public ProcuratoreDAO getProcuratoreDao() {
		return procuratoreDao;
	}

	public void setProcuratoreDao(ProcuratoreDAO procuratoreDao) {
		this.procuratoreDao = procuratoreDao;
	}

	@Override
	public List<ProcuratoreView> leggi(boolean tutti) throws Throwable {

		List<Procuratore> lista = procuratoreDao.leggi(tutti);
		List<ProcuratoreView> listaRitorno = convertiVoInView(lista);

		return listaRitorno;
	}

	@Override
	public ProcuratoreView leggi(long id) throws Throwable {
		Procuratore procuratore = procuratoreDao.leggi(id);
		return (ProcuratoreView) convertiVoInView(procuratore);
	}

	@Override
	protected Class<Procuratore> leggiClassVO() { 
		return Procuratore.class;
	}

	@Override
	protected Class<ProcuratoreView> leggiClassView() { 
		return ProcuratoreView.class;
	}

}
