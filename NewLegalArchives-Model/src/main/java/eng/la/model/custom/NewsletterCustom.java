package eng.la.model.custom;

import java.io.Serializable;
import java.util.List;

import eng.la.model.Newsletter;

public class NewsletterCustom implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7755374870009237887L;
	int totale;
	List<Newsletter> lstNewsletter;
	
	public NewsletterCustom() {
		 
	}

	public NewsletterCustom(int totale, List<Newsletter> lstNewsletter) {
		super();
		this.totale = totale;
		this.lstNewsletter = lstNewsletter;
	}


	public void setTotale(int totale) {
		this.totale = totale;
	}
	
	public int getTotale() {
		return totale;
	}
	
	public void setLstNewsletter(List<Newsletter> lstNewsletter) {
		this.lstNewsletter = lstNewsletter;
	}
	
	public List<Newsletter> getLstNewsletter() {
		return lstNewsletter;
	}

	@Override
	public String toString() {
		return "NewsletterCustom [totale=" + totale + "]";
	}
	
	
	
}
