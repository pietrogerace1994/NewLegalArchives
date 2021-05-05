package eng.la.model.custom;

import java.io.Serializable;
import java.util.List;

import eng.la.model.Newsletter;

public class NewsletterArticleListCustom implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2139763210453885531L;
	int totale;
	List<NewsletterArticleCustom> lstNewsletterArticleCustom;
	
	public NewsletterArticleListCustom() {
		
	}

	
	public NewsletterArticleListCustom(int totale, List<NewsletterArticleCustom> lstNewsletterArticleCustom) {
		super();
		this.totale = totale;
		this.lstNewsletterArticleCustom = lstNewsletterArticleCustom;
	}


	public int getTotale() {
		return totale;
	}
	public void setTotale(int totale) {
		this.totale = totale;
	}
	public List<NewsletterArticleCustom> getLstNewsletterArticleCustom() {
		return lstNewsletterArticleCustom;
	}
	public void setLstNewsletterArticleCustom(List<NewsletterArticleCustom> lstNewsletterArticleCustom) {
		this.lstNewsletterArticleCustom = lstNewsletterArticleCustom;
	}


	@Override
	public String toString() {
		return "NewsletterArticleListCustom [totale=" + totale + "]";
	}
	
	
	
	
	
}
