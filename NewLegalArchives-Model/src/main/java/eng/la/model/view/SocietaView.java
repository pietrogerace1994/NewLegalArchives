package eng.la.model.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import eng.la.model.Societa;
import eng.la.model.TipoSocieta;

@SuppressWarnings("all")
public class SocietaView extends BaseView {

	private static final long serialVersionUID = 1L;
	private Societa vo;
	
	private List<TipoSocietaView> listaTipoSocieta;
	
	private boolean insertMode;
	private boolean editMode;
	private boolean deleteMode;
	private boolean flagCode;
	private String countEmailEdit;
	private String countEmailInsert;
	
	// Edit e Modifica
	private Long idSocieta;
	private Long idTipoSocieta;
	private String nazioneCode;
	private String nome;
	private String ragioneSociale;
	private List<String> emailAmministrazione;
	private String indirizzo;
	private String cap;
	private String citta;
	private String sitoWeb;
	private String pieDiPagina;
	
	// Inserimento
	private String nazioneCodeIns;
	private Long idTipoSocietaIns;
	private String nomeIns;
	private String ragioneSocialeIns;
	private List<String> emailAmministrazioneIns;
	private String indirizzoIns;
	private String capIns;
	private String cittaIns;
	
	public SocietaView() {
		listaTipoSocieta = new ArrayList<TipoSocietaView>();
	}

	public void setVo(Societa vo) {
		this.vo = vo;
	}

	public Societa getVo() {
		return vo;
	}
	
	
		
	public String getSitoWeb() {
		return sitoWeb;
	}

	public void setSitoWeb(String sitoWeb) {
		this.sitoWeb = sitoWeb;
	}

	public String getPieDiPagina() {
		return pieDiPagina;
	}

	public void setPieDiPagina(String pieDiPagina) {
		this.pieDiPagina = pieDiPagina;
	}

	public List<TipoSocietaView> getListaTipoSocieta() {
		return this.listaTipoSocieta;
	}

	public void setListaTipoSocieta(List<TipoSocietaView> listaTipoSocieta) {
		this.listaTipoSocieta = listaTipoSocieta;
	}

	public boolean isInsertMode() {
		return insertMode;
	}

	public void setInsertMode(boolean insertMode) {
		this.insertMode = insertMode;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public boolean isDeleteMode() {
		return deleteMode;
	}

	public void setDeleteMode(boolean deleteMode) {
		this.deleteMode = deleteMode;
	}

	public boolean isFlagCode() {
		return flagCode;
	}

	public void setFlagCode(boolean flagCode) {
		this.flagCode = flagCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getIdTipoSocieta() {
		return idTipoSocieta;
	}

	public void setIdTipoSocieta(Long idTipoSocieta) {
		this.idTipoSocieta = idTipoSocieta;
	}

	public String getNazioneCode() {
		return nazioneCode;
	}

	public void setNazioneCode(String nazioneCode) {
		this.nazioneCode = nazioneCode;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public Long getIdSocieta() {
		return idSocieta;
	}

	public void setIdSocieta(Long idSocieta) {
		this.idSocieta = idSocieta;
	}

	public String getNazioneCodeIns() {
		return nazioneCodeIns;
	}

	public void setNazioneCodeIns(String nazioneCodeIns) {
		this.nazioneCodeIns = nazioneCodeIns;
	}

	public Long getIdTipoSocietaIns() {
		return idTipoSocietaIns;
	}

	public void setIdTipoSocietaIns(Long idTipoSocietaIns) {
		this.idTipoSocietaIns = idTipoSocietaIns;
	}

	public String getNomeIns() {
		return nomeIns;
	}

	public void setNomeIns(String nomeIns) {
		this.nomeIns = nomeIns;
	}

	public String getRagioneSocialeIns() {
		return ragioneSocialeIns;
	}

	public void setRagioneSocialeIns(String ragioneSocialeIns) {
		this.ragioneSocialeIns = ragioneSocialeIns;
	}

	public String getIndirizzoIns() {
		return indirizzoIns;
	}

	public void setIndirizzoIns(String indirizzoIns) {
		this.indirizzoIns = indirizzoIns;
	}

	public String getCapIns() {
		return capIns;
	}

	public void setCapIns(String capIns) {
		this.capIns = capIns;
	}

	public String getCittaIns() {
		return cittaIns;
	}

	public void setCittaIns(String cittaIns) {
		this.cittaIns = cittaIns;
	}

	public List<String> getEmailAmministrazione() {
		return emailAmministrazione;
	}

	public void setEmailAmministrazione(List<String> emailAmministrazione) {
		this.emailAmministrazione = emailAmministrazione;
	}

	public List<String> getEmailAmministrazioneIns() {
		return emailAmministrazioneIns;
	}

	public void setEmailAmministrazioneIns(List<String> emailAmministrazioneIns) {
		this.emailAmministrazioneIns = emailAmministrazioneIns;
	}

	public String getCountEmailEdit() {
		return countEmailEdit;
	}

	public void setCountEmailEdit(String countEmailEdit) {
		this.countEmailEdit = countEmailEdit;
	}

	public String getCountEmailInsert() {
		return countEmailInsert;
	}

	public void setCountEmailInsert(String countEmailInsert) {
		this.countEmailInsert = countEmailInsert;
	}

	
}
