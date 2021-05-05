package eng.la.model.mail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Categorie implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idCategoria;
	private String categoria;
	private ArrayList<Long> categorieFiglie;
	private String coloreCategoria;
	private String icon;
	private List<Comunicazione> comunicazioni;
	
	
	
	
	
	
	
	public ArrayList<Long> getCategorieFiglie() {
		return categorieFiglie;
	}
	public void setCategorieFiglie(ArrayList<Long> categorieFiglie) {
		this.categorieFiglie = categorieFiglie;
	}
	public Long getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public List<Comunicazione> getComunicazioni() {
		return comunicazioni;
	}
	public void setComunicazioni(List<Comunicazione> comunicazioni) {
		this.comunicazioni = comunicazioni;
	}
	public String getColoreCategoria() {
		return coloreCategoria;
	}
	public void setColoreCategoria(String coloreCategoria) {
		this.coloreCategoria = coloreCategoria;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	

	
}
