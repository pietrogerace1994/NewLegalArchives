package eng.la.model.custom;

import java.io.Serializable;
import java.util.List;

import eng.la.model.Articolo;
import eng.la.model.CategoriaMailinglist;

public class NewsArticlePageHome implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3791173055196550097L;
	CategoriaMailinglist categoriaPadre;
	List<Articolo> ltsArticolo;
	Articolo articoloHighlights;
	String cssClass;
	int articoloSize;
	
	public NewsArticlePageHome() {
		// TODO Auto-generated constructor stub
	}
	
	
	public NewsArticlePageHome(CategoriaMailinglist categoriaPadre, List<Articolo> ltsArticolo,Articolo articoloHighlights) {
		super();
		this.categoriaPadre = categoriaPadre;
		this.ltsArticolo = ltsArticolo;
		this.cssClass=(categoriaPadre!=null && categoriaPadre.getCss()!=null)? categoriaPadre.getCss():"";
		this.articoloSize=ltsArticolo!=null?ltsArticolo.size():0;
		this.articoloHighlights=articoloHighlights;
	}



	public CategoriaMailinglist getCategoriaPadre() {
		return categoriaPadre;
	}
	public void setCategoriaPadre(CategoriaMailinglist categoriaPadre) {
		this.categoriaPadre = categoriaPadre;
	}
	public List<Articolo> getLtsArticolo() {
		return ltsArticolo;
	}
	public void setLtsArticolo(List<Articolo> ltsArticolo) {
		this.ltsArticolo = ltsArticolo;
	}
	
	public String getCssClass() {
		return cssClass;
		 
	}
	
	public void setArticoloSize(int articoloSize) {
		this.articoloSize = articoloSize;
	}
	
	public int getArticoloSize() {
		return articoloSize;
	}
	public void setArticoloHighlights(Articolo articoloHighlights) {
		this.articoloHighlights = articoloHighlights;
	}
	public Articolo getArticoloHighlights() {
		return articoloHighlights;
	}


	@Override
	public String toString() {
		return "NewsArticlePageHome [categoriaPadre=" + categoriaPadre + ", ltsArticolo=" + ltsArticolo
				+ ", articoloHighlights=" + articoloHighlights + ", cssClass=" + cssClass + ", articoloSize="
				+ articoloSize + "]";
	}
	
	
}
