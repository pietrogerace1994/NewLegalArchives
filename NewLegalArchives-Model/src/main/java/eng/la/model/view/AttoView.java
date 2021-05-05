package eng.la.model.view;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import eng.la.model.Atto;


public class AttoView extends BaseView {
	private static final long serialVersionUID = 1L;
	private Atto vo;
	private List<Atto> listAtti;
	
	private long idAtto;
	private long idCategoriaAtto;
	private long idStatoAtto;
	private long idFascicolo;
	private long idSocieta;
	private String creatoDa;
	private String categoriaNome;
	private String societaRagioneSociale;
	private String statoNome;
	private String fascicoloNome;
	private String dataCancellazione;
	private String dataCreazione;
	private String dataNotifica;
	private String dataUdienza;
	private String dataUltimaModifica;
	private String destinatario;
	private String emailInvioAltriUffici;
	private String foroCompetente;
	private String lang;
	private String note;
	private String numeroProtocollo;
	private String parteNotificante;
	private String rilevante;
	private String tipoAtto;
	private String unitaLegInvioAltriUffici;
	private String utenteInvioAltriUffici;
	private MultipartFile file;
	private String operazioneCorrente;
	private String dataDal;
	private String dataAl;
	private BigDecimal pagamentoDovuto;
	private BigDecimal speseFavore;
	private BigDecimal speseCarico;
	private long idEsito;
	private String uuidPecAtto;
	
	public void setVo(Atto vo){
		this.vo = vo;
	}
	
	public Atto getVo() {
		return this.vo;
	}
	
	
	
	public long getIdEsito() {
		return idEsito;
	}

	public void setIdEsito(long idEsito) {
		this.idEsito = idEsito;
	}

	public BigDecimal getSpeseFavore() {
		return speseFavore;
	}

	public void setSpeseFavore(BigDecimal speseFavore) {
		this.speseFavore = speseFavore;
	}

	public BigDecimal getSpeseCarico() {
		return speseCarico;
	}

	public void setSpeseCarico(BigDecimal speseCarico) {
		this.speseCarico = speseCarico;
	}

	public BigDecimal getPagamentoDovuto() {
		return pagamentoDovuto;
	}

	public void setPagamentoDovuto(BigDecimal pagamentoDovuto) {
		this.pagamentoDovuto = pagamentoDovuto;
	}

	public void setListAtti(List<Atto> listAtti) {
		this.listAtti = listAtti;
	}
	
	public List<Atto> getListAtti() {
		return listAtti;
	}

	
	public long getIdAtto() {
		return idAtto;
	}

	public void setIdAtto(long idAtto) {
		this.idAtto = idAtto;
	}

	public long getIdCategoriaAtto() {
		return idCategoriaAtto;
	}

	public void setIdCategoriaAtto(long idCategoriaAtto) {
		this.idCategoriaAtto = idCategoriaAtto;
	}

	public long getIdStatoAtto() {
		return idStatoAtto;
	}

	public void setIdStatoAtto(long idStatoAtto) {
		this.idStatoAtto = idStatoAtto;
	}

	public long getIdFascicolo() {
		return idFascicolo;
	}

	public void setIdFascicolo(long idFascicolo) {
		this.idFascicolo = idFascicolo;
	}

	public long getIdSocieta() {
		return idSocieta;
	}

	public void setIdSocieta(long idSocieta) {
		this.idSocieta = idSocieta;
	}

	public String getCreatoDa() {
		return creatoDa;
	}

	public void setCreatoDa(String creatoDa) {
		this.creatoDa = creatoDa;
	}

	public String getCategoriaNome() {
		return categoriaNome;
	}

	public void setCategoriaNome(String categoriaNome) {
		this.categoriaNome = categoriaNome;
	}

	public String getSocietaRagioneSociale() {
		return societaRagioneSociale;
	}

	public void setSocietaRagioneSociale(String societaRagioneSociale) {
		this.societaRagioneSociale = societaRagioneSociale;
	}

	public String getStatoNome() {
		return statoNome;
	}

	public void setStatoNome(String statoNome) {
		this.statoNome = statoNome;
	}

	public String getFascicoloNome() {
		return fascicoloNome;
	}

	public void setFascicoloNome(String fascicoloNome) {
		this.fascicoloNome = fascicoloNome;
	}

	public String getDataCancellazione() {
		return dataCancellazione;
	}

	public void setDataCancellazione(String dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public String getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(String dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public String getDataNotifica() {
		return dataNotifica;
	}

	public void setDataNotifica(String dataNotifica) {
		this.dataNotifica = dataNotifica;
	}

	public String getDataUdienza() {
		return dataUdienza;
	}

	public void setDataUdienza(String dataUdienza) {
		this.dataUdienza = dataUdienza;
	}

	public String getDataUltimaModifica() {
		return dataUltimaModifica;
	}

	public void setDataUltimaModifica(String dataUltimaModifica) {
		this.dataUltimaModifica = dataUltimaModifica;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getEmailInvioAltriUffici() {
		return emailInvioAltriUffici;
	}

	public void setEmailInvioAltriUffici(String emailInvioAltriUffici) {
		this.emailInvioAltriUffici = emailInvioAltriUffici;
	}

	public String getForoCompetente() {
		return foroCompetente;
	}

	public void setForoCompetente(String foroCompetente) {
		this.foroCompetente = foroCompetente;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public String getParteNotificante() {
		return parteNotificante;
	}

	public void setParteNotificante(String parteNotificante) {
		this.parteNotificante = parteNotificante;
	}

	public String getRilevante() {
		return rilevante;
	}

	public void setRilevante(String rilevante) {
		this.rilevante = rilevante;
	}

	public String getTipoAtto() {
		return tipoAtto;
	}

	public void setTipoAtto(String tipoAtto) {
		this.tipoAtto = tipoAtto;
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
	
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public void setOperazioneCorrente(String operazioneCorrente) {
		this.operazioneCorrente = operazioneCorrente;
	}
	
	public String getOperazioneCorrente() {
		return operazioneCorrente;
	}
	
	
	public String getDataDal() {
		return dataDal;
	}

	public void setDataDal(String dataDal) {
		this.dataDal = dataDal;
	}

	public String getDataAl() {
		return dataAl;
	}

	public void setDataAl(String dataAl) {
		this.dataAl = dataAl;
	}

	public String getUuidPecAtto() {
		return uuidPecAtto;
	}

	public void setUuidPecAtto(String uuidPecAtto) {
		this.uuidPecAtto = uuidPecAtto;
	}


	//

	
	// qui aggiungere i campi aggiuntivi che non afferiscono all'entità
}