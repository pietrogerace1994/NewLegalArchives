package eng.la.model.view;

import java.util.List;

public class IndexView extends BaseView {

	private static final long serialVersionUID = 1L;
	
	
	private List<FascicoloView> listaUltimiFascicoli;
	private Long fascicoliRecentiNumRighe;
	
	public IndexView() {
	}


	public List<FascicoloView> getListaUltimiFascicoli() {
		return listaUltimiFascicoli;
	}


	public void setListaUltimiFascicoli(List<FascicoloView> listaUltimiFascicoli) {
		this.listaUltimiFascicoli = listaUltimiFascicoli;
	}


	public Long getFascicoliRecentiNumRighe() {
		return fascicoliRecentiNumRighe;
	}


	public void setFascicoliRecentiNumRighe(Long fascicoliRecentiNumRighe) {
		this.fascicoliRecentiNumRighe = fascicoliRecentiNumRighe;
	}
	
	
	
}
