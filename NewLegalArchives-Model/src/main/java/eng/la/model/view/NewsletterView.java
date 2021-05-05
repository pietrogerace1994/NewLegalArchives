package eng.la.model.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import eng.la.model.Newsletter;

public class NewsletterView extends BaseView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Newsletter vo;

	public Newsletter getVo() {
		return vo;
	}

	public void setVo(Newsletter vo) {
		this.vo = vo;
	}

	private Long newsletterId;
	private String numero;
	private String numeroRomano;
	private String titolo;
	private String dataCreazione;
	private List<EmailView> listaArticoli;
	private MultipartFile copertina;
	private String copertinaMod;
	private int statoCode;
	private ArrayList<String> articoliAggiunti;
	private String[] articoliAggiuntiMod;
	private List<String> email;
	private Long comboHigh;
	
	

	public Long getComboHigh() {
		return comboHigh;
	}

	public void setComboHigh(Long comboHigh) {
		this.comboHigh = comboHigh;
	}

	public String getNumeroRomano() {
		return numeroRomano;
	}

	public void setNumeroRomano(String numeroRomano) {
		this.numeroRomano = numeroRomano;
	}

	public String getCopertinaMod() {
		return copertinaMod;
	}

	public void setCopertinaMod(String copertinaMod) {
		this.copertinaMod = copertinaMod;
	}

	public String[] getArticoliAggiuntiMod() {
		return articoliAggiuntiMod;
	}

	public void setArticoliAggiuntiMod(String[] articoliAggiuntiMod) {
		this.articoliAggiuntiMod = articoliAggiuntiMod;
	}

	

	public List<String> getEmail() {
		return email;
	}

	public void setEmail(List<String> email) {
		this.email = email;
	}

	public ArrayList<String> getArticoliAggiunti() {
		return articoliAggiunti;
	}

	public void setArticoliAggiunti(ArrayList<String> articoliAggiunti) {
		this.articoliAggiunti = articoliAggiunti;
	}

	public Long getNewsletterId() {
		return newsletterId;
	}

	public void setNewsletterId(Long newsletterId) {
		this.newsletterId = newsletterId;
	}



	

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(String dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public List<EmailView> getListaArticoli() {
		return listaArticoli;
	}

	public void setListaArticoli(List<EmailView> listaArticoli) {
		this.listaArticoli = listaArticoli;
	}

	public MultipartFile getCopertina() {
		return copertina;
	}

	public void setCopertina(MultipartFile copertina) {
		this.copertina = copertina;
	}

	public int getStatoCode() {
		return statoCode;
	}

	public void setStatoCode(int statoCode) {
		this.statoCode = statoCode;
	}

	@Override
	public String toString() {
		return "NewsletterView [vo=" + vo + ", newsletterId=" + newsletterId + ", numero=" + numero + ", titolo="
				+ titolo + ", dataCreazione=" + dataCreazione + ", listaArticoli=" + listaArticoli + ", copertina="
				+ copertina + ", statoCode=" + statoCode + "]";
	}
	

	
	
	

	
	

}
