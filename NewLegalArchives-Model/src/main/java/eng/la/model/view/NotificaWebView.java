package eng.la.model.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import eng.la.model.NotificaWeb;

public class NotificaWebView extends BaseView {

	private static final long serialVersionUID = 1053147160544212176L;
	
	private String emailInvioAltriUffici;
	private String unitaLegInvioAltriUffici;
	private String utenteInvioAltriUffici;
	private String numeroProtocollo;
	private String nomeFascicolo;
	private long idFascicolo;
	private Date dataInserimento;
	private String nomeProforma;
	private String nomeOwnerFascicolo;
	private String nomeLegaleEsterno;
	private String denominazioneStudioLegaleEsterno;
	private String numeroFattura;
	private String incarico;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
	
	
	
	private NotificaWeb vo;
	
	public NotificaWeb getVo() {
		return vo;
	}
	
	public void setVo(NotificaWeb vo) {
		this.vo = vo;
	}

	public String getEmailInvioAltriUffici() {
		return emailInvioAltriUffici;
	}

	public void setEmailInvioAltriUffici(String emailInvioAltriUffici) {
		this.emailInvioAltriUffici = emailInvioAltriUffici;
	}

	public String getUnitaLegInvioAltriUffici() {
		return unitaLegInvioAltriUffici;
	}

	public void setUnitaLegInvioAltriUffici(String unitaLegInvioAltriUffici) {
		this.unitaLegInvioAltriUffici = unitaLegInvioAltriUffici;
	}

	public String getUtenteInvioAltriUffici() {
		return utenteInvioAltriUffici;
	}

	public void setUtenteInvioAltriUffici(String utenteInvioAltriUffici) {
		this.utenteInvioAltriUffici = utenteInvioAltriUffici;
	}

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}
	
	public String getNomeFascicolo() {
		return nomeFascicolo;
	}

	public void setNomeFascicolo(String nomeFascicolo) {
		this.nomeFascicolo = nomeFascicolo;
	}



	public long getIdFascicolo() {
		return idFascicolo;
	}

	public void setIdFascicolo(long idFascicolo) {
		this.idFascicolo = idFascicolo;
	}

	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date data) {
		this.dataInserimento = data;
	}

	public String getNomeProforma() {
		return nomeProforma;
	}

	public void setNomeProforma(String nomeProforma) {
		this.nomeProforma = nomeProforma;
	}



	public String getNomeOwnerFascicolo() {
		return nomeOwnerFascicolo;
	}

	public void setNomeOwnerFascicolo(String nomeOwnerFascicolo) {
		this.nomeOwnerFascicolo = nomeOwnerFascicolo;
	}
	
	public String getNomeLegaleEsterno() {
		return nomeLegaleEsterno;
	}

	public void setNomeLegaleEsterno(String nomeLegaleEsterno) {
		this.nomeLegaleEsterno = nomeLegaleEsterno;
	}

	public String getDenominazioneStudioLegaleEsterno() {
		return denominazioneStudioLegaleEsterno;
	}

	public void setDenominazioneStudioLegaleEsterno(String denominazioneStudioLegaleEsterno) {
		this.denominazioneStudioLegaleEsterno = denominazioneStudioLegaleEsterno;
	}
	
	public String getNumeroFattura() {
		return numeroFattura;
	}

	public void setNumeroFattura(String numeroFattura) {
		this.numeroFattura = numeroFattura;
	}

	public String getIncarico() {
		return incarico;
	}

	public void setIncarico(String incarico) {
		this.incarico = incarico;
	}

	public String getJsonParamAltriUffici(){
		
		return getNumeroProtocollo()+"$"+getUtenteInvioAltriUffici()+"$"+getUnitaLegInvioAltriUffici()+"$"+getEmailInvioAltriUffici();
	}
	
	public String getJsonParamAperturaRiaperturaFascicolo(){
		
		return /*getIdFascicolo()+"$"+*/getNomeFascicolo()+"$"+df.format(getDataInserimento());
	}
	
	public String getJsonParamAccettaRespingiProforma() {
		return getNomeProforma()+/*"$"+getIdFascicolo()*/"$"+getNomeFascicolo()+"$"+getNomeOwnerFascicolo();
	}

	public String getJsonChiusuraFascicolo() {
		return /*getIdFascicolo()+"$"+*/getNomeFascicolo()+"$"+df.format(getDataInserimento());
	}

	public String getJsonInserimentoPEsterno() {
		return getNomeLegaleEsterno()+"$"+getDenominazioneStudioLegaleEsterno()+"$"+df.format(getDataInserimento());
	}

	public String getJsonInvioFattura() {

		return getNomeLegaleEsterno()+"$"+getDenominazioneStudioLegaleEsterno()+"$"+getNumeroFattura()+"$"+df.format(getDataInserimento())+"$"+getIncarico()+"$"+getNomeFascicolo();
	}
	
}
