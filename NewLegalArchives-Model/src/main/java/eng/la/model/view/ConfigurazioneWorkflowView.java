package eng.la.model.view;

import java.util.List;

public class ConfigurazioneWorkflowView extends BaseView {
	
	private static final long serialVersionUID = 1L;

	private List<UtenteView> listaAutorizzatori;
	private String topResponsabileNomeCognome;
	private String topResponsabileNomeCognomeSenzaVirgola;
	private Boolean autorizzatoreStatoWf;
	private Long sceltaAutorizzatore;
	private String approvatoreNomeCognome;
	private String listaAutorizzatoriJson;
	private String topResponsabileMatricola;
	private Long sceltaApprovatore;
	private List<UtenteView> listaApprovatori;
	private String listaApprovatoriJson;
	private String matricolaApprovatore;
	private Boolean approvatoreStatoWf;
	private String approvatoreNomeCognomeSenzaVirgola;
	private Long idApprovatore;
	private Long idAutorizzatore;
	private String emailApprovatore;
	private String emailAutorizzatore;
	private Long idAutorizzatoreRigaWf1;
	private Long idAutorizzatoreRigaWf2;
	private Long idAutorizzatoreRigaWf3;
	private Long idAutorizzatoreRigaWf4;
	private Long idApprovatoreRigaWf1;
	private Long idApprovatoreRigaWf2;
	private Long idApprovatoreRigaWf3;
	private Long idApprovatoreRigaWf4;
	
	public String getTopResponsabileNomeCognome() {
		return topResponsabileNomeCognome;
	}

	public void setTopResponsabileNomeCognome(String topResponsabileNomeCognome) {
		this.topResponsabileNomeCognome = topResponsabileNomeCognome;
	}

	public Long getSceltaAutorizzatore() {
		return sceltaAutorizzatore;
	}

	public void setSceltaAutorizzatore(Long sceltaAutorizzatore) {
		this.sceltaAutorizzatore = sceltaAutorizzatore;
	}

	public String getTopResponsabileNomeCognomeSenzaVirgola() {
		return topResponsabileNomeCognomeSenzaVirgola;
	}

	public void setTopResponsabileNomeCognomeSenzaVirgola(String topResponsabileNomeCognomeSenzaVirgola) {
		this.topResponsabileNomeCognomeSenzaVirgola = topResponsabileNomeCognomeSenzaVirgola;
	}

	public String getApprovatoreNomeCognome() {
		return approvatoreNomeCognome;
	}

	public void setApprovatoreNomeCognome(String approvatoreNomeCognome) {
		this.approvatoreNomeCognome = approvatoreNomeCognome;
	}

	public String getListaAutorizzatoriJson() {
		return listaAutorizzatoriJson;
	}

	public void setListaAutorizzatoriJson(String listaAutorizzatoriJson) {
		this.listaAutorizzatoriJson = listaAutorizzatoriJson;
	}

	public String getTopResponsabileMatricola() {
		return topResponsabileMatricola;
	}

	public void setTopResponsabileMatricola(String topResponsabileMatricola) {
		this.topResponsabileMatricola = topResponsabileMatricola;
	}

	public Long getSceltaApprovatore() {
		return sceltaApprovatore;
	}

	public void setSceltaApprovatore(Long sceltaApprovatore) {
		this.sceltaApprovatore = sceltaApprovatore;
	}

	public String getListaApprovatoriJson() {
		return listaApprovatoriJson;
	}

	public void setListaApprovatoriJson(String listaApprovatoriJson) {
		this.listaApprovatoriJson = listaApprovatoriJson;
	}

	public String getMatricolaApprovatore() {
		return matricolaApprovatore;
	}

	public void setMatricolaApprovatore(String matricolaApprovatore) {
		this.matricolaApprovatore = matricolaApprovatore;
	}

	public String getApprovatoreNomeCognomeSenzaVirgola() {
		return approvatoreNomeCognomeSenzaVirgola;
	}

	public void setApprovatoreNomeCognomeSenzaVirgola(String approvatoreNomeCognomeSenzaVirgola) {
		this.approvatoreNomeCognomeSenzaVirgola = approvatoreNomeCognomeSenzaVirgola;
	}

	public Long getIdApprovatore() {
		return idApprovatore;
	}

	public void setIdApprovatore(Long idApprovatore) {
		this.idApprovatore = idApprovatore;
	}

	public Long getIdAutorizzatore() {
		return idAutorizzatore;
	}

	public void setIdAutorizzatore(Long idAutorizzatore) {
		this.idAutorizzatore = idAutorizzatore;
	}

	public List<UtenteView> getListaAutorizzatori() {
		return listaAutorizzatori;
	}

	public void setListaAutorizzatori(List<UtenteView> listaAutorizzatori) {
		this.listaAutorizzatori = listaAutorizzatori;
	}

	public List<UtenteView> getListaApprovatori() {
		return listaApprovatori;
	}

	public void setListaApprovatori(List<UtenteView> listaApprovatori) {
		this.listaApprovatori = listaApprovatori;
	}

	public String getEmailApprovatore() {
		return emailApprovatore;
	}

	public void setEmailApprovatore(String emailApprovatore) {
		this.emailApprovatore = emailApprovatore;
	}

	public String getEmailAutorizzatore() {
		return emailAutorizzatore;
	}

	public void setEmailAutorizzatore(String emailAutorizzatore) {
		this.emailAutorizzatore = emailAutorizzatore;
	}

	public Long getIdAutorizzatoreRigaWf1() {
		return idAutorizzatoreRigaWf1;
	}

	public void setIdAutorizzatoreRigaWf1(Long idAutorizzatoreRigaWf1) {
		this.idAutorizzatoreRigaWf1 = idAutorizzatoreRigaWf1;
	}

	public Long getIdAutorizzatoreRigaWf2() {
		return idAutorizzatoreRigaWf2;
	}

	public void setIdAutorizzatoreRigaWf2(Long idAutorizzatoreRigaWf2) {
		this.idAutorizzatoreRigaWf2 = idAutorizzatoreRigaWf2;
	}

	public Long getIdAutorizzatoreRigaWf3() {
		return idAutorizzatoreRigaWf3;
	}

	public void setIdAutorizzatoreRigaWf3(Long idAutorizzatoreRigaWf3) {
		this.idAutorizzatoreRigaWf3 = idAutorizzatoreRigaWf3;
	}

	public Long getIdAutorizzatoreRigaWf4() {
		return idAutorizzatoreRigaWf4;
	}

	public void setIdAutorizzatoreRigaWf4(Long idAutorizzatoreRigaWf4) {
		this.idAutorizzatoreRigaWf4 = idAutorizzatoreRigaWf4;
	}

	public Long getIdApprovatoreRigaWf1() {
		return idApprovatoreRigaWf1;
	}

	public void setIdApprovatoreRigaWf1(Long idApprovatoreRigaWf1) {
		this.idApprovatoreRigaWf1 = idApprovatoreRigaWf1;
	}

	public Long getIdApprovatoreRigaWf2() {
		return idApprovatoreRigaWf2;
	}

	public void setIdApprovatoreRigaWf2(Long idApprovatoreRigaWf2) {
		this.idApprovatoreRigaWf2 = idApprovatoreRigaWf2;
	}

	public Long getIdApprovatoreRigaWf3() {
		return idApprovatoreRigaWf3;
	}

	public void setIdApprovatoreRigaWf3(Long idApprovatoreRigaWf3) {
		this.idApprovatoreRigaWf3 = idApprovatoreRigaWf3;
	}

	public Long getIdApprovatoreRigaWf4() {
		return idApprovatoreRigaWf4;
	}

	public void setIdApprovatoreRigaWf4(Long idApprovatoreRigaWf4) {
		this.idApprovatoreRigaWf4 = idApprovatoreRigaWf4;
	}

	public Boolean getAutorizzatoreStatoWf() {
		return autorizzatoreStatoWf;
	}

	public void setAutorizzatoreStatoWf(Boolean autorizzatoreStatoWf) {
		this.autorizzatoreStatoWf = autorizzatoreStatoWf;
	}

	public Boolean getApprovatoreStatoWf() {
		return approvatoreStatoWf;
	}

	public void setApprovatoreStatoWf(Boolean approvatoreStatoWf) {
		this.approvatoreStatoWf = approvatoreStatoWf;
	}
	
	
}