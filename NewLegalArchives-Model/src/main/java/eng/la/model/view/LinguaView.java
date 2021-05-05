package eng.la.model.view;

import java.util.List;

import eng.la.model.Lingua;

public class LinguaView extends BaseView {

	private static final long serialVersionUID = 1L;
	private Lingua vo;
	private List<LinguaView> listaLingua;

	public Lingua getVo() {
		return vo;
	}

	public void setVo(Lingua vo) {
		this.vo = vo;
	}

	public List<LinguaView> getListaLingua() {
		return listaLingua;
	}

	public void setListaLingua(List<LinguaView> listaLingua) {
		this.listaLingua = listaLingua;
	}

}