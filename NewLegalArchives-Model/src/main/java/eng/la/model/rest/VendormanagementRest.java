package eng.la.model.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.tuple.MutablePair;

@XmlRootElement
public class VendormanagementRest {

	private String studioLegale;
	private String professionistaEsternoNomeCognome;
	private Long professionistaEsternoId;
	private String valutatore;
	private List<CriterioRest> listaCriterio;
	private List<String> listaNazioniStr;
	private List<String> listaSpecializzazione;
	private String valutatoreMatricola;
	private List<NazioneRest> listaNazioniRest;
	private List<SpecializzazioneRest> listaSpecializzazioneRest;
	private List<MutablePair<String,String>> listaVoted;  
	private String coloreVoto;
	private String semestreRiferimento;
	private String reperibilita;
	private String autorevolezza;
	private String comprensione;
	private String professionalita;
	private String costi;
	private String flessibilita;
	private String tempestivita;
	private String valutazioneComplessiva;
	private String nota;
	
	
	
	public String getStudioLegale() {
		return studioLegale;
	}

	public void setStudioLegale(String studioLegale) {
		this.studioLegale = studioLegale;
	}

	public String getProfessionistaEsternoNomeCognome() {
		return professionistaEsternoNomeCognome;
	}

	public void setProfessionistaEsternoNomeCognome(String professionistaEsternoNomeCognome) {
		this.professionistaEsternoNomeCognome = professionistaEsternoNomeCognome;
	}

	public String getValutatore() {
		return valutatore;
	}

	public void setValutatore(String valutatore) {
		this.valutatore = valutatore;
	}

	public List<CriterioRest> getListaCriterio() {
		return listaCriterio;
	}

	public void setListaCriterio(List<CriterioRest> listaCriterio) {
		this.listaCriterio = listaCriterio;
	}

	public List<String> getListaNazioniStr() {
		return listaNazioniStr;
	}

	public void setListaNazioniStr(List<String> listaNazioniStr) {
		this.listaNazioniStr = listaNazioniStr;
	}

	public List<String> getListaSpecializzazione() {
		return listaSpecializzazione;
	}

	public void setListaSpecializzazione(List<String> listaSpecializzazione) {
		this.listaSpecializzazione = listaSpecializzazione;
	}

	public Long getProfessionistaEsternoId() {
		return professionistaEsternoId;
	}

	public void setProfessionistaEsternoId(Long professionistaEsternoId) {
		this.professionistaEsternoId = professionistaEsternoId;
	}

	public String getValutatoreMatricola() {
		return valutatoreMatricola;
	}

	public void setValutatoreMatricola(String valutatoreMatricola) {
		this.valutatoreMatricola = valutatoreMatricola;
	}

	public List<NazioneRest> getListaNazioniRest() {
		return listaNazioniRest;
	}

	public void setListaNazioniRest(List<NazioneRest> listaNazioniRest) {
		this.listaNazioniRest = listaNazioniRest;
	}

	public List<SpecializzazioneRest> getListaSpecializzazioneRest() {
		return listaSpecializzazioneRest;
	}

	public void setListaSpecializzazioneRest(List<SpecializzazioneRest> listaSpecializzazioneRest) {
		this.listaSpecializzazioneRest = listaSpecializzazioneRest;
	}

	public List<MutablePair<String, String>> getListaVoted() {
		return listaVoted;
	}

	public void setListaVoted(List<MutablePair<String, String>> listaVoted) {
		this.listaVoted = listaVoted;
	}

	public String getColoreVoto() {
		return coloreVoto;
	}

	public void setColoreVoto(String coloreVoto) {
		this.coloreVoto = coloreVoto;
	}

	public String getSemestreRiferimento() {
		return semestreRiferimento;
	}

	public void setSemestreRiferimento(String semestreRiferimento) {
		this.semestreRiferimento = semestreRiferimento;
	}

	public String getReperibilita() {
		return reperibilita;
	}

	public void setReperibilita(String reperibilita) {
		this.reperibilita = reperibilita;
	}

	public String getAutorevolezza() {
		return autorevolezza;
	}

	public void setAutorevolezza(String autorevolezza) {
		this.autorevolezza = autorevolezza;
	}

	public String getComprensione() {
		return comprensione;
	}

	public void setComprensione(String comprensione) {
		this.comprensione = comprensione;
	}

	public String getProfessionalita() {
		return professionalita;
	}

	public void setProfessionalita(String professionalita) {
		this.professionalita = professionalita;
	}

	public String getCosti() {
		return costi;
	}

	public void setCosti(String costi) {
		this.costi = costi;
	}

	public String getFlessibilita() {
		return flessibilita;
	}

	public void setFlessibilita(String flessibilita) {
		this.flessibilita = flessibilita;
	}

	public String getTempestivita() {
		return tempestivita;
	}

	public void setTempestivita(String tempestivata) {
		this.tempestivita = tempestivata;
	}

	public String getValutazioneComplessiva() {
		return valutazioneComplessiva;
	}

	public void setValutazioneComplessiva(String valutazioneComplessiva) {
		this.valutazioneComplessiva = valutazioneComplessiva;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}
	
}
