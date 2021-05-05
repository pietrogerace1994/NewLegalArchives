package eng.la.model.view;

import java.util.Date;
import java.util.List;

import eng.la.model.OrganoSociale;
import eng.la.model.rest.CodiceDescrizioneBean;

public class OrganoSocialeView extends BaseView {

	private static final long serialVersionUID = 1L;
	
	private OrganoSociale vo;
	

	private long id;                      
	private Long idSocietaAffari;			    
	private Long tipoOrganoSociale;
	private String cognome;   		
	private String nome;			   		
	private String carica;			   		
	private String dataNomina;				
	private String dataCessazione;			
	private String dataScadenza;			
	private String dataAccettazioneCarica;
	private Long emolumento;
	private String dataNascita;			
	private String luogoNascita;			
	private String codiceFiscale;			
	private String note;					
	private Date dataCancellazione;		

	

	private List<CodiceDescrizioneBean> listaSocietaAffari;

	private List<CodiceDescrizioneBean> listaOrganoSociale;
	
	private Long organoSocialeId;

	public OrganoSociale getVo() {
		return vo;
	}

	public void setVo(OrganoSociale vo) {
		this.vo = vo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getIdSocietaAffari() {
		return idSocietaAffari;
	}

	public void setIdSocietaAffari(Long idSocieta) {
		this.idSocietaAffari = idSocieta;
	}

	public Long getTipoOrganoSociale() {
		return tipoOrganoSociale;
	}

	public void setTipoOrganoSociale(Long tipoOrganoSociale) {
		this.tipoOrganoSociale = tipoOrganoSociale;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCarica() {
		return carica;
	}

	public void setCarica(String carica) {
		this.carica = carica;
	}

	public String getDataNomina() {
		return dataNomina;
	}

	public void setDataNomina(String dataNomina) {
		this.dataNomina = dataNomina;
	}

	public String getDataCessazione() {
		return dataCessazione;
	}

	public void setDataCessazione(String dataCessazione) {
		this.dataCessazione = dataCessazione;
	}

	public String getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(String dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public String getDataAccettazioneCarica() {
		return dataAccettazioneCarica;
	}

	public void setDataAccettazioneCarica(String dataAccettazioneCarica) {
		this.dataAccettazioneCarica = dataAccettazioneCarica;
	}

	public Long getEmolumento() {
		return emolumento;
	}

	public void setEmolumento(Long emolumento) {
		this.emolumento = emolumento;
	}

	public String getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getLuogoNascita() {
		return luogoNascita;
	}

	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getDataCancellazione() {
		return dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public List<CodiceDescrizioneBean> getListaSocietaAffari() {
		return listaSocietaAffari;
	}

	public void setListaSocietaAffari(List<CodiceDescrizioneBean> listaSocieta) {
		this.listaSocietaAffari = listaSocieta;
	}

	public List<CodiceDescrizioneBean> getListaOrganoSociale() {
		return listaOrganoSociale;
	}

	public void setListaOrganoSociale(List<CodiceDescrizioneBean> listaOrganoSociale) {
		this.listaOrganoSociale = listaOrganoSociale;
	}

	public Long getOrganoSocialeId() {
		return organoSocialeId;
	}

	public void setOrganoSocialeId(Long organoSocialeId) {
		this.organoSocialeId = organoSocialeId;
	}

	@Override
	public String toString() {
		return "OrganoSocialeView [vo=" + vo + ", id=" + id
				+ ", idSocietaAffari=" + idSocietaAffari
				+ ", tipoOrganoSociale=" + tipoOrganoSociale + ", cognome="
				+ cognome + ", nome=" + nome + ", carica=" + carica
				+ ", dataNomina=" + dataNomina + ", dataCessazione="
				+ dataCessazione + ", dataScadenza=" + dataScadenza
				+ ", dataAccettazioneCarica=" + dataAccettazioneCarica
				+ ", emolumento=" + emolumento + ", dataNascita=" + dataNascita
				+ ", luogoNascita=" + luogoNascita + ", codiceFiscale="
				+ codiceFiscale + ", note=" + note + ", dataCancellazione="
				+ dataCancellazione + ", listaSocietaAffari="
				+ listaSocietaAffari + ", listaOrganoSociale="
				+ listaOrganoSociale + ", organoSocialeId=" + organoSocialeId
				+ "]";
	}

	
	
}
