package eng.la.model.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProfEstRest {
	
	private long id;
	private List<String> nazioneDesc;
	private List<String> specDesc;
	private String nome;
	private String cognome;
	private String fax;
	private String[] email;
	private String codiceFiscale;
	private String telefono;
	private String studioLegale;
	private String motivazioneRichiesta;
	private String statoEsitoValutazioneProf;
	private String statoProfessionista;
	private String tipoProfessionista;
	private String categoriaContest;
	private List<String> fileUuid;
	private List<DocumentoRest> doc;
	
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
	private Integer giudizio;
	

	public List<DocumentoRest> getDoc() {
		return doc;
	}

	public void setDoc(List<DocumentoRest> doc) {
		this.doc = doc;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public List<String> getNazioneDesc() {
		return nazioneDesc;
	}

	public void setNazioneDesc(List<String> nazioneDesc) {
		this.nazioneDesc = nazioneDesc;
	}

	public List<String> getSpecDesc() {
		return specDesc;
	}

	public void setSpecDesc(List<String> specDesc) {
		this.specDesc = specDesc;
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

	public String getStudioLegale() {
		return studioLegale;
	}

	public void setStudioLegale(String studioLegale) {
		this.studioLegale = studioLegale;
	}

	public String[] getEmail() {
		return email;
	}

	public void setEmail(String[] email) {
		this.email = email;
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

	public List<String> getFileUuid() {
		return fileUuid;
	}

	public void setFileUuid(List<String> fileUuid) {
		this.fileUuid = fileUuid;
	}

	public String getStatoProfessionista() {
		return statoProfessionista;
	}

	public void setStatoProfessionista(String statoProfessionista) {
		this.statoProfessionista = statoProfessionista;
	}

	public String getMotivazioneRichiesta() {
		return motivazioneRichiesta;
	}

	public void setMotivazioneRichiesta(String motivazioneRichiesta) {
		this.motivazioneRichiesta = motivazioneRichiesta;
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

	public Integer getGiudizio() {
		return giudizio;
	}

	public void setGiudizio(Integer giudizioVal) {
		this.giudizio = giudizioVal;
	}

	public String getCategoriaContest() {
		return categoriaContest;
	}

	public void setCategoriaContest(String categoriaContest) {
		this.categoriaContest = categoriaContest;
	}


}
