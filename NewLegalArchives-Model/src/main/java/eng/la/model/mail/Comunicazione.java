package eng.la.model.mail;

import java.io.Serializable;

public class Comunicazione implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String data;
	private String titolo;
	private String abstractCont;
	private String sottoCategoria;
	private Long sottocategoriaId;
	private Long categoriaId;
	private long id;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getAbstractCont() {
		return abstractCont;
	}

	public void setAbstractCont(String abstractCont) {
		this.abstractCont = abstractCont;
	}

	public String getSottoCategoria() {
		return sottoCategoria;
	}

	public void setSottoCategoria(String sottoCategoria) {
		this.sottoCategoria = sottoCategoria;
	}

	public Long getSottocategoriaId() {
		return sottocategoriaId;
	}

	public void setSottocategoriaId(Long sottocategoriaId) {
		this.sottocategoriaId = sottocategoriaId;
	}

	public Long getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}

	
}
