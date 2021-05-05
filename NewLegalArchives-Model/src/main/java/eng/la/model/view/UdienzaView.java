/*
 * @author Benedetto Giordano
 */
package eng.la.model.view;

import eng.la.model.Udienza;

public class UdienzaView extends BaseView {
	private static final long serialVersionUID = 1L;
	private Udienza vo;
	
	public void setVo(Udienza vo){
		this.vo = vo;
	}
	
	public Udienza getVo() {
		return this.vo;
	}
	
	/* CAMPI FORM PRINCIPALE UDIENZA */
	private FascicoloView fascicoloRiferimento;
	private String nomeFascicolo;
	private Long udienzaId;
	private String dataCreazione;
	private String dataUdienza;
	private String descrizione;

	public FascicoloView getFascicoloRiferimento() {
		return fascicoloRiferimento;
	}

	public void setFascicoloRiferimento(FascicoloView fascicoloRiferimento) {
		this.fascicoloRiferimento = fascicoloRiferimento;
	}

	public String getNomeFascicolo() {
		return nomeFascicolo;
	}

	public void setNomeFascicolo(String nomeFascicolo) {
		this.nomeFascicolo = nomeFascicolo;
	}

	public Long getUdienzaId() {
		return udienzaId;
	}

	public void setUdienzaId(Long udienzaId) {
		this.udienzaId = udienzaId;
	}

	public String getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(String dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public String getDataUdienza() {
		return dataUdienza;
	}

	public void setDataUdienza(String dataUdienza) {
		this.dataUdienza = dataUdienza;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
}