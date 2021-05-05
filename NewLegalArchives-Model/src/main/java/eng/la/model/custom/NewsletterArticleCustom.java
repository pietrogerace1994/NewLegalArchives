package eng.la.model.custom;

import java.io.Serializable;
import java.util.List;

import eng.la.model.Articolo;
import eng.la.model.Newsletter;

public class NewsletterArticleCustom implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4654455603803204757L;
	Newsletter newsletter;
	List<Articolo> articolos;
	
	public NewsletterArticleCustom() {
		
	}
	
	public NewsletterArticleCustom(Newsletter newsletter, List<Articolo> articolos) {
		super();
		this.newsletter = newsletter;
		this.articolos = articolos;
	}

	public Newsletter getNewsletter() {
		return newsletter;
	}
	
	public void setNewsletter(Newsletter newsletter) {
		this.newsletter = newsletter;
	}
	
	public List<Articolo> getArticolos() {
		return articolos;
	}
	
	public void setArticolos(List<Articolo> articolos) {
		this.articolos = articolos;
	}

	@Override
	public String toString() {
		return "NewsletterArticleCustom [newsletter=" + newsletter + ", articolos=" + articolos + "]";
	}
	
	
	
}
