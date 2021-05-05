package eng.la.util;

import java.util.ArrayList;

public class ListaPaginata<E> extends ArrayList<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long numeroTotaleElementi;
	private int paginaCorrente;
	private int numeroElementiPerPagina;
	private String ordinamento;
	private String ordinamentoDirezione = "ASC";

	public long getNumeroTotaleElementi() {
		return numeroTotaleElementi;
	}

	public void setNumeroTotaleElementi(long numeroTotaleElementi) {
		this.numeroTotaleElementi = numeroTotaleElementi;
	}

	public int getPaginaCorrente() {
		return paginaCorrente;
	}

	public void setPaginaCorrente(int paginaCorrente) {
		this.paginaCorrente = paginaCorrente;
	}

	public int getNumeroElementiPerPagina() {
		return numeroElementiPerPagina;
	}

	public void setNumeroElementiPerPagina(int numeroElementiPerPagina) {
		this.numeroElementiPerPagina = numeroElementiPerPagina;
	}

	public String getOrdinamento() {
		return ordinamento;
	}

	public void setOrdinamento(String ordinamento) {
		this.ordinamento = ordinamento;
	}

	public String getOrdinamentoDirezione() {
		return ordinamentoDirezione;
	}

	public void setOrdinamentoDirezione(String ordinamentoDirezione) {
		this.ordinamentoDirezione = ordinamentoDirezione;
	}

}
