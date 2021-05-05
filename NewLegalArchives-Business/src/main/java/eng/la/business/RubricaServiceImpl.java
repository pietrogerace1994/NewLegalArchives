package eng.la.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eng.la.model.Rubrica;
import eng.la.model.view.RubricaView;
import eng.la.persistence.RubricaDAO;
import eng.la.util.ListaPaginata;

@Service("rubricaService")
public class RubricaServiceImpl extends BaseService<Rubrica,RubricaView> implements RubricaService {

	@Autowired
	private RubricaDAO dao;

	@Override
	public RubricaView leggiRubrica(long id) throws Throwable {
		Rubrica vo = dao.leggiRubrica(id);
		if (vo != null) {
			RubricaView view = new RubricaView();
			view.setVo(vo);
			return view;
		}
		return null;
	}

	@Override
	public List<RubricaView> leggiRubrica() throws Throwable {
		List<Rubrica> lvo = dao.leggiRubrica();
		if (lvo != null) {
			List<RubricaView> lview = new ArrayList<RubricaView>();
			for (Rubrica vo : lvo) {
				RubricaView view = new RubricaView();
				view.setVo(vo);
				lview.add(view);
			}

			return lview;
		}
		return null;
	}

	@Override
	public RubricaView salvaRubrica(RubricaView rubricaView) throws Throwable {
		Rubrica vo = dao.salvaRubrica(rubricaView.getVo());
		return (RubricaView) convertiVoInView(vo, leggiClassVO(), leggiClassView());  
	}

	@Override
	public void cancellaRubrica(long id) throws Throwable {
		dao.cancellaRubrica(id);
	}

	@Override
	public void modificaRubrica(RubricaView rubricaView) throws Throwable {
		dao.modificaRubrica(rubricaView.getVo());
	}
	
	@Override
	public ListaPaginata<RubricaView> cerca(String nominativo, String email, int elementiPerPagina,
			int numeroPagina, String ordinamento, String ordinamentoDirezione) throws Throwable {
		List<Rubrica> lista = dao.cerca(nominativo, email, elementiPerPagina, numeroPagina, ordinamento,ordinamentoDirezione);
		List<RubricaView> listaView = convertiVoInView(lista);
		ListaPaginata<RubricaView> listaRitorno = new ListaPaginata<RubricaView>();
		Long conta = dao.conta(nominativo, email);
		listaRitorno.addAll(listaView);
		listaRitorno.setNumeroElementiPerPagina(elementiPerPagina);
		listaRitorno.setPaginaCorrente(numeroPagina);
		listaRitorno.setNumeroTotaleElementi(conta);
		listaRitorno.setOrdinamento(ordinamento);
		listaRitorno.setOrdinamentoDirezione(ordinamentoDirezione);
		return listaRitorno;
	}

	@Override
	protected Class<Rubrica> leggiClassVO() {
		return Rubrica.class;
	}

	@Override
	protected Class<RubricaView> leggiClassView() {
		return RubricaView.class;
	}
}
