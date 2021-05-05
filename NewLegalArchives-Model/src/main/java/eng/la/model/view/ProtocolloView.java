package eng.la.model.view;

import java.util.ArrayList;
import java.util.List;

import eng.la.model.ArchivioProtocollo;
import eng.la.model.Utente;

public class ProtocolloView extends BaseView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String mittente;
	
	private String destinatario;

	private String destinatarioOut;
	
	private String oggetto;
	
	private String commento;
	
	private String commentoRi;
	
	private ArrayList<Utente> utentiIN;
	
	private ArrayList<Utente> utentiOUT;
	
	private Long destINid;
	
	private Long mittOUTid;
	
	private String utenteAss;
	
	private String unitaApp;
	
	private String unitaAppOut;
	
	private String oggettoOut;
	
	private String numeroProtocollo;
	
	private String filtro;
	
	private List<StatoProtocolloView> listaStatoProtocollo;
	
	private ArchivioProtocollo vo;
	
	public ArchivioProtocollo getVo() {
		return vo;
	}

	public void setVo(ArchivioProtocollo vo) {
		this.vo = vo;
	}
	
	

	public String getCommentoRi() {
		return commentoRi;
	}

	public void setCommentoRi(String commentoRi) {
		this.commentoRi = commentoRi;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<StatoProtocolloView> getListaStatoProtocollo() {
		return listaStatoProtocollo;
	}

	public void setListaStatoProtocollo(List<StatoProtocolloView> listaStatoProtocollo) {
		this.listaStatoProtocollo = listaStatoProtocollo;
	}

	public String getCommento() {
		return commento;
	}

	public void setCommento(String commento) {
		this.commento = commento;
	}

	public String getUtenteAss() {
		return utenteAss;
	}

	public void setUtenteAss(String utenteAss) {
		this.utenteAss = utenteAss;
	}

	public String getUnitaAppOut() {
		return unitaAppOut;
	}

	public void setUnitaAppOut(String unitaAppOut) {
		this.unitaAppOut = unitaAppOut;
	}

	public String getOggettoOut() {
		return oggettoOut;
	}

	public void setOggettoOut(String oggettoOut) {
		this.oggettoOut = oggettoOut;
	}

	public String getDestinatarioOut() {
		return destinatarioOut;
	}

	public void setDestinatarioOut(String destinatarioOut) {
		this.destinatarioOut = destinatarioOut;
	}

	public Long getMittOUTid() {
		return mittOUTid;
	}

	public void setMittOUTid(Long mittOUTid) {
		this.mittOUTid = mittOUTid;
	}

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public String getUnitaApp() {
		return unitaApp;
	}

	public void setUnitaApp(String unitaApp) {
		this.unitaApp = unitaApp;
	}

	public Long getDestINid() {
		return destINid;
	}

	public void setDestINid(Long destINid) {
		this.destINid = destINid;
	}
	

	public ArrayList<Utente> getUtentiIN() {
		return utentiIN;
	}

	public void setUtentiIN(ArrayList<Utente> utentiIN) {
		this.utentiIN = utentiIN;
	}

	public ArrayList<Utente> getUtentiOUT() {
		return utentiOUT;
	}

	public void setUtentiOUT(ArrayList<Utente> utentiOUT) {
		this.utentiOUT = utentiOUT;
	}

	public String getMittente() {
		return mittente;
	}

	public void setMittente(String mittente) {
		this.mittente = mittente;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	


}
