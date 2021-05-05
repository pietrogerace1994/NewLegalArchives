package eng.la.model.rest;

import javax.xml.bind.annotation.XmlRootElement;

import eng.la.model.CategoriaTessere;
import eng.la.model.SubCategoriaTessere;

@XmlRootElement
public class CodiceDescrizioneBean {
	private long id;
	private String descrizione;
	private CategoriaTessere categoria;
	private SubCategoriaTessere subCategoria;
	
	
	
	public SubCategoriaTessere getSubCategoria() {
		return subCategoria;
	}
	public void setSubCategoria(SubCategoriaTessere subCategoria) {
		this.subCategoria = subCategoria;
	}
	public CategoriaTessere getCategoria() {
		return categoria;
	}
	public void setCategoria(CategoriaTessere categoria) {
		this.categoria = categoria;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	@Override
	public String toString() {
		return "CodiceDescrizioneBean [id=" + id + ", descrizione=" + descrizione + "]";
	}
	
	
	
}
