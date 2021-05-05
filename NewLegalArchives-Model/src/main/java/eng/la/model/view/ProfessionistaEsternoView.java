package eng.la.model.view;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import eng.la.model.ProfessionistaEsterno;

public class ProfessionistaEsternoView extends BaseView {

	private static final long serialVersionUID = 1L;

	private ProfessionistaEsterno vo;

	private Long professionistaEsternoId;
	private String[] nazioniAggiunte;
	private String[] specializzazioniAggiunte;
	private String tipoProfessionistaCode;
	private List<TipoProfessionistaView> listaTipoProfessionista;
	private String statoEsitoValutazioneProfCode;
	private String statoProfessionistaCode;
	private List<StatoEsitoValutazioneProfView> listaStatoEsitoValutazioneProf;
	private List<StatoProfessionistaView> listaStatoProfessionista;
	private String tipoStudioLegale;
	private String studioLegaleId;
	private List<StudioLegaleView> listaStudioLegale;
	private String nome;
	private String cognome;
	private String motivazioneRichiesta;
	private String fax;
	private List<String> email;
	private String codiceFiscale;
	private String telefono;
	private String countEmail;
	private MultipartFile fileProfEst;
	private String[] documentiDaEliminare;
	private String jsonArrayProfessionistaEsterno;

	private List<ProfessionistaEsternoView> listaProfEst;
	private boolean insertMode;
	private boolean editMode;
	private boolean deleteMode;
	private boolean viewMode;

	private String studioLegale;
	private String statoEsitoValutazioneProf;
	private String statoProfessionista;
	private String tipoProfessionista;
	private String nazioneDesc;
	private String specDesc;
	private String emailField;
	private String categoriaContest;

	// campi per lo studio legale
	private String studioLegaleDenominazione;
	private String studioLegaleIndirizzo;
	private String studioLegaleCap;
	private String studioLegaleCitta;
	private String studioLegaleEmail;
	private String studioLegaleTelefono;
	private String studioLegaleFax;
	private String studioLegaleNazioneCode;
	private String studioLegaleCodiceSap;
	private String studioLegalePartitaIva;

	private String studioLegaleNazioneDescrizione;
	
	private Integer giudizio;
	private List<GiudizioProfEstView> listaGiudizio;
	private String vendorManagement;
	
	private String categoriaContestCode;
	private List<CategoriaContestView> listaCategoriaContest;

	public String getCognomeNome() {
		if (vo == null)
			return null;
		return vo.getCognome() + " " + vo.getNome();
	}

	public ProfessionistaEsterno getVo() {
		return vo;
	}

	public void setVo(ProfessionistaEsterno vo) {
		this.vo = vo;
	}

	public String getTipoProfessionistaCode() {
		return tipoProfessionistaCode;
	}

	public void setTipoProfessionistaCode(String tipoProfessionistaCode) {
		this.tipoProfessionistaCode = tipoProfessionistaCode;
	}

	public List<TipoProfessionistaView> getListaTipoProfessionista() {
		return listaTipoProfessionista;
	}

	public void setListaTipoProfessionista(
			List<TipoProfessionistaView> listaTipoProfessionista) {
		this.listaTipoProfessionista = listaTipoProfessionista;
	}

	public List<StatoEsitoValutazioneProfView> getListaStatoEsitoValutazioneProf() {
		return listaStatoEsitoValutazioneProf;
	}

	public void setListaStatoEsitoValutazioneProf(
			List<StatoEsitoValutazioneProfView> listaStatoEsitoValutazioneProf) {
		this.listaStatoEsitoValutazioneProf = listaStatoEsitoValutazioneProf;
	}

	public String getStatoEsitoValutazioneProfCode() {
		return statoEsitoValutazioneProfCode;
	}

	public void setStatoEsitoValutazioneProfCode(
			String statoEsitoValutazioneProfCode) {
		this.statoEsitoValutazioneProfCode = statoEsitoValutazioneProfCode;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Long getProfessionistaEsternoId() {
		return professionistaEsternoId;
	}

	public void setProfessionistaEsternoId(Long professionistaEsternoId) {
		this.professionistaEsternoId = professionistaEsternoId;
	}

	public String getStudioLegaleId() {
		return studioLegaleId;
	}

	public void setStudioLegaleId(String studioLegaleId) {
		this.studioLegaleId = studioLegaleId;
	}

	public List<StudioLegaleView> getListaStudioLegale() {
		return listaStudioLegale;
	}

	public void setListaStudioLegale(List<StudioLegaleView> listaStudioLegale) {
		this.listaStudioLegale = listaStudioLegale;
	}

	public String getTipoStudioLegale() {
		return tipoStudioLegale;
	}

	public void setTipoStudioLegale(String tipoStudioLegale) {
		this.tipoStudioLegale = tipoStudioLegale;
	}

	public String[] getNazioniAggiunte() {
		return nazioniAggiunte;
	}

	public void setNazioniAggiunte(String[] nazioniAggiunte) {
		this.nazioniAggiunte = nazioniAggiunte;
	}

	public String[] getSpecializzazioniAggiunte() {
		return specializzazioniAggiunte;
	}

	public void setSpecializzazioniAggiunte(String[] specializzazioniAggiunte) {
		this.specializzazioniAggiunte = specializzazioniAggiunte;
	}

	public String getStudioLegaleDenominazione() {
		return studioLegaleDenominazione;
	}

	public void setStudioLegaleDenominazione(String studioLegaleDenominazione) {
		this.studioLegaleDenominazione = studioLegaleDenominazione;
	}

	public String getStudioLegaleIndirizzo() {
		return studioLegaleIndirizzo;
	}

	public void setStudioLegaleIndirizzo(String studioLegaleIndirizzo) {
		this.studioLegaleIndirizzo = studioLegaleIndirizzo;
	}

	public String getStudioLegaleCap() {
		return studioLegaleCap;
	}

	public void setStudioLegaleCap(String studioLegaleCap) {
		this.studioLegaleCap = studioLegaleCap;
	}

	public String getStudioLegaleCitta() {
		return studioLegaleCitta;
	}

	public void setStudioLegaleCitta(String studioLegaleCitta) {
		this.studioLegaleCitta = studioLegaleCitta;
	}

	public String getStudioLegaleEmail() {
		return studioLegaleEmail;
	}

	public void setStudioLegaleEmail(String studioLegaleEmail) {
		this.studioLegaleEmail = studioLegaleEmail;
	}

	public String getStudioLegaleTelefono() {
		return studioLegaleTelefono;
	}

	public void setStudioLegaleTelefono(String studioLegaleTelefono) {
		this.studioLegaleTelefono = studioLegaleTelefono;
	}

	public String getStudioLegaleFax() {
		return studioLegaleFax;
	}

	public void setStudioLegaleFax(String studioLegaleFax) {
		this.studioLegaleFax = studioLegaleFax;
	}

	public String getStudioLegaleNazioneCode() {
		return studioLegaleNazioneCode;
	}

	public void setStudioLegaleNazioneCode(String studioLegaleNazioneCode) {
		this.studioLegaleNazioneCode = studioLegaleNazioneCode;
	}

	public String getStudioLegaleCodiceSap() {
		return studioLegaleCodiceSap;
	}

	public void setStudioLegaleCodiceSap(String studioLegaleCodiceSap) {
		this.studioLegaleCodiceSap = studioLegaleCodiceSap;
	}

	public String getStudioLegalePartitaIva() {
		return studioLegalePartitaIva;
	}

	public void setStudioLegalePartitaIva(String studioLegalePartitaIva) {
		this.studioLegalePartitaIva = studioLegalePartitaIva;
	}

	public List<String> getEmail() {
		return email;
	}

	public void setEmail(List<String> email) {
		this.email = email;
	}

	public String getCountEmail() {
		return countEmail;
	}

	public void setCountEmail(String countEmail) {
		this.countEmail = countEmail;
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

	public List<ProfessionistaEsternoView> getListaProfEst() {
		return listaProfEst;
	}

	public void setListaProfEst(List<ProfessionistaEsternoView> listaProfEst) {
		this.listaProfEst = listaProfEst;
	}

	public String getStudioLegale() {
		return studioLegale;
	}

	public void setStudioLegale(String studioLegale) {
		this.studioLegale = studioLegale;
	}

	public String getStatoEsitoValutazioneProf() {
		return statoEsitoValutazioneProf;
	}

	public void setStatoEsitoValutazioneProf(String statoEsitoValutazioneProf) {
		this.statoEsitoValutazioneProf = statoEsitoValutazioneProf;
	}

	public String getTipoProfessionista() {
		return tipoProfessionista;
	}

	public void setTipoProfessionista(String tipoProfessionista) {
		this.tipoProfessionista = tipoProfessionista;
	}

	public String getNazioneDesc() {
		return nazioneDesc;
	}

	public void setNazioneDesc(String nazioneDesc) {
		this.nazioneDesc = nazioneDesc;
	}

	public String getSpecDesc() {
		return specDesc;
	}

	public void setSpecDesc(String specDesc) {
		this.specDesc = specDesc;
	}

	public String getEmailField() {
		return emailField;
	}

	public void setEmailField(String emailField) {
		this.emailField = emailField;
	}

	public MultipartFile getFileProfEst() {
		return fileProfEst;
	}

	public void setFileProfEst(MultipartFile fileProfEst) {
		this.fileProfEst = fileProfEst;
	}

	public boolean isViewMode() {
		return viewMode;
	}

	public void setViewMode(boolean viewMode) {
		this.viewMode = viewMode;
	}

	public String[] getDocumentiDaEliminare() {
		return documentiDaEliminare;
	}

	public void setDocumentiDaEliminare(String[] documentiDaEliminare) {
		this.documentiDaEliminare = documentiDaEliminare;
	}

	public String getMotivazioneRichiesta() {
		return motivazioneRichiesta;
	}

	public void setMotivazioneRichiesta(String motivazioneRichiesta) {
		this.motivazioneRichiesta = motivazioneRichiesta;
	}

	public String getStatoProfessionistaCode() {
		return statoProfessionistaCode;
	}

	public void setStatoProfessionistaCode(String statoProfessionistaCode) {
		this.statoProfessionistaCode = statoProfessionistaCode;
	}

	public List<StatoProfessionistaView> getListaStatoProfessionista() {
		return listaStatoProfessionista;
	}

	public void setListaStatoProfessionista(List<StatoProfessionistaView> listaStatoProfessionista) {
		this.listaStatoProfessionista = listaStatoProfessionista;
	}

	public String getStatoProfessionista() {
		return statoProfessionista;
	}

	public void setStatoProfessionista(String statoProfessionista) {
		this.statoProfessionista = statoProfessionista;
	}

	public String getJsonArrayProfessionistaEsterno() {
		return jsonArrayProfessionistaEsterno;
	}

	public void setJsonArrayProfessionistaEsterno(String jsonArrayProfessionistaEsterno) {
		this.jsonArrayProfessionistaEsterno = jsonArrayProfessionistaEsterno;
	}

	public String getStudioLegaleNazioneDescrizione() {
		return studioLegaleNazioneDescrizione;
	}

	public void setStudioLegaleNazioneDescrizione(String studioLegaleNazioneDescrizione) {
		this.studioLegaleNazioneDescrizione = studioLegaleNazioneDescrizione;
	}

	public Integer getGiudizio() {
		return giudizio;
	}

	public void setGiudizio(Integer giudizio) {
		this.giudizio = giudizio;
	}

	public List<GiudizioProfEstView> getListaGiudizio() {
		return listaGiudizio;
	}

	public void setListaGiudizio(List<GiudizioProfEstView> listaGiudizio) {
		this.listaGiudizio = listaGiudizio;
	}

	public String getVendorManagement() {
		return vendorManagement;
	}

	public void setVendorManagement(String vendorManagement) {
		this.vendorManagement = vendorManagement;
	}

	public String getCategoriaContestCode() {
		return categoriaContestCode;
	}

	public void setCategoriaContestCode(String categoriaContestCode) {
		this.categoriaContestCode = categoriaContestCode;
	}

	public List<CategoriaContestView> getListaCategoriaContest() {
		return listaCategoriaContest;
	}

	public void setListaCategoriaContest(List<CategoriaContestView> listaCategoriaContest) {
		this.listaCategoriaContest = listaCategoriaContest;
	}

	public String getCategoriaContest() {
		return categoriaContest;
	}

	public void setCategoriaContest(String categoriaContest) {
		this.categoriaContest = categoriaContest;
	}
	
}
