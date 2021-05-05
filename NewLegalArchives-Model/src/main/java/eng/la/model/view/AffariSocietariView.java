package eng.la.model.view;

import java.util.List;

import eng.la.model.AffariSocietari;
import eng.la.model.RSocietaAffari;
import eng.la.model.rest.CodiceDescrizioneBean;

public class AffariSocietariView extends BaseView {

	private static final long serialVersionUID = 1L;
	
	private AffariSocietari vo;
	
	private long id;
	private Long codiceSocieta;
	private String denominazione;
	private Long capitaleSottoscritto;
	private Long capitaleSociale;
	private String dataCostituzione;
	private String dataUscita;
	private String denominazioneBreve;
	private String siglaFormaGiuridica;
	private String formaGiuridica;
	private Long idNazione;
	private String siglaStatoProvincia;
	private String siglaProvincia;
	private String italiaEstero;
	private String ueExstraue;
	private String sedeLegale;
	private String indirizzo;
	private Long cap;
	private String codiceFiscale;
	private String partitaIva;
	private String dataRea;
	private Long numeroRea;
	private String comuneRea;
	private String siglaProvinciaRea;
	private String provinciaRea;
	private String quotazione;
	private String inLiquidazione;
	private String modelloDiGovernance;
	private Long nComponentiCDA;
	private String nComponentiCollegioSindacale;
	private String nComponentiOdv;
	private String codiceNazione;
	private String societaDiRevisione;
	private String dataIncarico;
	private Long idControllante;
	private Long percentualeControllante;
	private Long percentualeTerzi;
	private String[] denominazioniSocio;
	private String[] percentualeSocio;
	private List<RSocietaAffari> rSocietaAffaris;
	
	private String rSocietaAffariIdSocieta;
	private String rSocietaAffariPercentuale;
	private String rSocietaAffariDescrizione;
	private String idDaRimuovere;
	private String fromCreation;
	private RSocietaAffariView[] sociAggiunti;

	private List<CodiceDescrizioneBean> listaSocietaControllanti;

	
	private Long affariSocietariId;
	


	public List<DocumentoView> allegato;
	public String  allegatoDaRimuovereUuid;
	public AffariSocietari getVo() {
		return vo;
	}
	public void setVo(AffariSocietari vo) {
		this.vo = vo;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Long getCodiceSocieta() {
		return codiceSocieta;
	}
	public void setCodiceSocieta(Long codiceSocieta) {
		this.codiceSocieta = codiceSocieta;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public Long getCapitaleSottoscritto() {
		return capitaleSottoscritto;
	}
	public void setCapitaleSottoscritto(Long capitaleSottoscritto) {
		this.capitaleSottoscritto = capitaleSottoscritto;
	}
	public Long getCapitaleSociale() {
		return capitaleSociale;
	}
	public void setCapitaleSociale(Long capitaleSociale) {
		this.capitaleSociale = capitaleSociale;
	}
	public String getDataCostituzione() {
		return dataCostituzione;
	}
	public void setDataCostituzione(String dataCostituzione) {
		this.dataCostituzione = dataCostituzione;
	}
	public String getDataUscita() {
		return dataUscita;
	}
	public void setDataUscita(String dataUscita) {
		this.dataUscita = dataUscita;
	}
	public String getDenominazioneBreve() {
		return denominazioneBreve;
	}
	public void setDenominazioneBreve(String denominazioneBreve) {
		this.denominazioneBreve = denominazioneBreve;
	}
	public String getSiglaFormaGiuridica() {
		return siglaFormaGiuridica;
	}
	public void setSiglaFormaGiuridica(String siglaFormaGiuridica) {
		this.siglaFormaGiuridica = siglaFormaGiuridica;
	}
	public String getFormaGiuridica() {
		return formaGiuridica;
	}
	public void setFormaGiuridica(String formaGiuridica) {
		this.formaGiuridica = formaGiuridica;
	}
	public Long getIdNazione() {
		return idNazione;
	}
	public void setIdNazione(Long idNazione) {
		this.idNazione = idNazione;
	}
	public String getSiglaStatoProvincia() {
		return siglaStatoProvincia;
	}
	public void setSiglaStatoProvincia(String siglaStatoProvincia) {
		this.siglaStatoProvincia = siglaStatoProvincia;
	}
	public String getSiglaProvincia() {
		return siglaProvincia;
	}
	public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = siglaProvincia;
	}
	public String getItaliaEstero() {
		return italiaEstero;
	}
	public void setItaliaEstero(String italiaEstero) {
		this.italiaEstero = italiaEstero;
	}
	public String getUeExstraue() {
		return ueExstraue;
	}
	public void setUeExstraue(String ueExstraue) {
		this.ueExstraue = ueExstraue;
	}
	public String getSedeLegale() {
		return sedeLegale;
	}
	public void setSedeLegale(String sedeLegale) {
		this.sedeLegale = sedeLegale;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public Long getCap() {
		return cap;
	}
	public void setCap(Long cap) {
		this.cap = cap;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getPartitaIva() {
		return partitaIva;
	}
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	public String getDataRea() {
		return dataRea;
	}
	public void setDataRea(String dataRea) {
		this.dataRea = dataRea;
	}
	public Long getNumeroRea() {
		return numeroRea;
	}
	public void setNumeroRea(Long numeroRea) {
		this.numeroRea = numeroRea;
	}
	public String getComuneRea() {
		return comuneRea;
	}
	public void setComuneRea(String comuneRea) {
		this.comuneRea = comuneRea;
	}
	public String getSiglaProvinciaRea() {
		return siglaProvinciaRea;
	}
	public void setSiglaProvinciaRea(String siglaProvinciaRea) {
		this.siglaProvinciaRea = siglaProvinciaRea;
	}
	public String getProvinciaRea() {
		return provinciaRea;
	}
	public void setProvinciaRea(String provinciaRea) {
		this.provinciaRea = provinciaRea;
	}
	public String getQuotazione() {
		return quotazione;
	}
	public void setQuotazione(String quotazione) {
		this.quotazione = quotazione;
	}
	public String getInLiquidazione() {
		return inLiquidazione;
	}
	public void setInLiquidazione(String inLiquidazione) {
		this.inLiquidazione = inLiquidazione;
	}
	public String getModelloDiGovernance() {
		return modelloDiGovernance;
	}
	public void setModelloDiGovernance(String modelloDiGovernance) {
		this.modelloDiGovernance = modelloDiGovernance;
	}
	public Long getnComponentiCDA() {
		return nComponentiCDA;
	}
	public void setnComponentiCDA(Long nComponentiCDA) {
		this.nComponentiCDA = nComponentiCDA;
	}
	public String getnComponentiCollegioSindacale() {
		return nComponentiCollegioSindacale;
	}
	public void setnComponentiCollegioSindacale(String nComponentiCollegioSindacale) {
		this.nComponentiCollegioSindacale = nComponentiCollegioSindacale;
	}
	public String getnComponentiOdv() {
		return nComponentiOdv;
	}
	public void setnComponentiOdv(String nComponentiOdv) {
		this.nComponentiOdv = nComponentiOdv;
	}
	public String getCodiceNazione() {
		return codiceNazione;
	}
	public void setCodiceNazione(String codiceNazione) {
		this.codiceNazione = codiceNazione;
	}
	public String getSocietaDiRevisione() {
		return societaDiRevisione;
	}
	public void setSocietaDiRevisione(String societaDiRevisione) {
		this.societaDiRevisione = societaDiRevisione;
	}
	public String getDataIncarico() {
		return dataIncarico;
	}
	public void setDataIncarico(String dataIncarico) {
		this.dataIncarico = dataIncarico;
	}
	public Long getIdControllante() {
		return idControllante;
	}
	public void setIdControllante(Long idControllante) {
		this.idControllante = idControllante;
	}
	public Long getPercentualeControllante() {
		return percentualeControllante;
	}
	public void setPercentualeControllante(Long percentualeControllante) {
		this.percentualeControllante = percentualeControllante;
	}
	public Long getPercentualeTerzi() {
		return percentualeTerzi;
	}
	public void setPercentualeTerzi(Long percentualeTerzi) {
		this.percentualeTerzi = percentualeTerzi;
	}

	public List<CodiceDescrizioneBean> getListaSocietaControllanti() {
		return listaSocietaControllanti;
	}
	public void setListaSocietaControllanti(List<CodiceDescrizioneBean> listaSocietaControllanti) {
		this.listaSocietaControllanti = listaSocietaControllanti;
	}
	public Long getAffariSocietariId() {
		return affariSocietariId;
	}
	public void setAffariSocietariId(Long affariSocietariId) {
		this.affariSocietariId = affariSocietariId;
	}
	public List<DocumentoView> getAllegato() {
		return allegato;
	}
	public void setAllegato(List<DocumentoView> allegato) {
		this.allegato = allegato;
	}
	public String getAllegatoDaRimuovereUuid() {
		return allegatoDaRimuovereUuid;
	}
	public void setAllegatoDaRimuovereUuid(String allegatoDaRimuovereUuid) {
		this.allegatoDaRimuovereUuid = allegatoDaRimuovereUuid;
	}
	public String[] getDenominazioniSocio() {
		return denominazioniSocio;
	}
	public void setDenominazioniSocio(String[] denominazioniSocio) {
		this.denominazioniSocio = denominazioniSocio;
	}
	public String[] getPercentualeSocio() {
		return percentualeSocio;
	}
	public void setPercentualeSocio(String[] percentualeSocio) {
		this.percentualeSocio = percentualeSocio;
	}
	public List<RSocietaAffari> getrSocietaAffaris() {
		return rSocietaAffaris;
	}
	public void setrSocietaAffaris(List<RSocietaAffari> rSocietaAffaris) {
		this.rSocietaAffaris = rSocietaAffaris;
	}
	public String getrSocietaAffariIdSocieta() {
		return rSocietaAffariIdSocieta;
	}
	public void setrSocietaAffariIdSocieta(String rSocietaAffariIdSocieta) {
		this.rSocietaAffariIdSocieta = rSocietaAffariIdSocieta;
	}
	public String getrSocietaAffariPercentuale() {
		return rSocietaAffariPercentuale;
	}
	public void setrSocietaAffariPercentuale(String rSocietaAffariPercentuale) {
		this.rSocietaAffariPercentuale = rSocietaAffariPercentuale;
	}
	public RSocietaAffariView[] getSociAggiunti() {
		return sociAggiunti;
	}
	public void setSociAggiunti(RSocietaAffariView[] sociAggiunti) {
		this.sociAggiunti = sociAggiunti;
	}
	public String getIdDaRimuovere() {
		return idDaRimuovere;
	}
	public void setIdDaRimuovere(String idDaRimuovere) {
		this.idDaRimuovere = idDaRimuovere;
	}
	public String getrSocietaAffariDescrizione() {
		return rSocietaAffariDescrizione;
	}
	public void setrSocietaAffariDescrizione(String rSocietaAffariDescrizione) {
		this.rSocietaAffariDescrizione = rSocietaAffariDescrizione;
	}
	public String getFromCreation() {
		return fromCreation;
	}
	public void setFromCreation(String fromCreation) {
		this.fromCreation = fromCreation;
	}
	
}
