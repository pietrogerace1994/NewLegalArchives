package eng.la.model.mail;

import java.io.Serializable;
import java.util.List;

public class Newsletter implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String numero;
	private String numeroRomano;
	private String titolo;
	private String anno;
	private List<Categorie> categorie;
	private String urlCopertina;
	private String urlArticolo;
	private byte[] image;
	private String newestTitle;
	private String newestAbstract;
	private List<String> imgPath;
	
	
	
	
	

	/**
	 * @return the imgPath
	 */
	public List<String> getImgPath() {
		return imgPath;
	}
	/**
	 * @param imgPath the imgPath to set
	 */
	public void setImgPath(List<String> imgPath) {
		this.imgPath = imgPath;
	}
	public String getUrlArticolo() {
		return urlArticolo;
	}
	public void setUrlArticolo(String urlArticolo) {
		this.urlArticolo = urlArticolo;
	}
	public String getNewestTitle() {
		return newestTitle;
	}
	public void setNewestTitle(String newestTitle) {
		this.newestTitle = newestTitle;
	}
	public String getNewestAbstract() {
		return newestAbstract;
	}
	public void setNewestAbstract(String newestAbstract) {
		this.newestAbstract = newestAbstract;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getNumeroRomano() {
		return numeroRomano;
	}
	public void setNumeroRomano(String numeroRomano) {
		this.numeroRomano = numeroRomano;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public String getUrlCopertina() {
		return urlCopertina;
	}
	public void setUrlCopertina(String urlCopertina) {
		this.urlCopertina = urlCopertina;
	}
	public String getAnno() {
		return anno;
	}
	public void setAnno(String anno) {
		this.anno = anno;
	}

	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public List<Categorie> getCategorie() {
		return categorie;
	}
	public void setCategorie(List<Categorie> categorie) {
		this.categorie = categorie;
	}
	


	
}
