package eng.la.model.view;

import java.util.List;
import java.util.Set;

import eng.la.model.SchedaFondoRischi;

public class SchedaFondoRischiView extends BaseView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SchedaFondoRischi vo;

	public SchedaFondoRischi getVo() {
		return vo;
	}

	public void setVo(SchedaFondoRischi vo) {
		this.vo = vo;
	}
	
	/* CAMPI FORM PRINCIPALE SCHEDA_FONDO_RISCHI */
	private FascicoloView fascicoloRiferimento;
	private String nomeFascicolo;
	private Long schedaFondoRischiId;
	private String dataCreazione;
	private String dataAutorizzazione;
	private String dataRichiestaAutorizzazione;
	private String tipologiaSchedaFondoRischiCode;
	private TipologiaSchedaFrView tipologiaSchedaSelezionata;
	private String controparte;
	private Long professionistaEsternoId;
	private ProfessionistaEsternoView professionistaEsternoSelezionato;
	private String statoSchedaFondoRischi;
	private String statoSchedaFondoRischiCode;
	private Long societaId;
	private List<String> listaSocietaAddebitoAggiunteDesc;
	private Long giudizioId;
	private List<String> listaGiudizioDesc;
	private Long organoGiudicanteId;
	private List<String> listaOrganoGiudicanteDesc;
	private Double valoreDomanda = 0.0;
	private String testoEsplicativo;
	private String rischioSoccombenzaCode;
	private RischioSoccombenzaView rischioSoccombenzaSelezionato;
	private Double coperturaAssicurativa = 0.0;
	private Double manleva = 0.0;
	private Double commessaDiInvestimento = 0.0;
	private Double passivitaStimata = 0.0;
	private String motivazione;
	private List<ProfessionistaEsternoView> listaProfessionistiEsterni;
	private List<TipologiaSchedaFrView> listaTipologiaScheda;
	private List<RischioSoccombenzaView> listaRischioSoccombenza;
	private List<DocumentoView> listaAllegatiLegaleEsterno;
	private Set<String> allegatiDaRimuovereUuid;
	private boolean disabled;
	private String timeout;
	private List<StatoSchedaFondoRischiView> listaStatoSchedaFondoRischi;
	private Integer coperturaAssicurativaFlag;
	private Integer manlevaFlag;
	private Integer commessaDiInvestimentoFlag;
	private List<String> listaControparteDesc;
	private Double valoreDomandaPrecedente = 0.0;
	private String testoEsplicativoPrecedente;
	private String rischioSoccombenzaDesc;
	private String rischioSoccombenzaPrecDesc;
	private String coperturaAssicurativaDesc;
	private String coperturaAssicurativaPrecDesc;
	private String manlevaDesc;
	private String manlevaPrecDesc;
	private String commessaInvestimentoDesc;
	private String commessaInvestimentoPrecDesc;
	private String passivitaStimataDesc;
	private String passivitaStimataPrecDesc;
	private String motivazionePrecedente;
	
	
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
	
	public Long getSchedaFondoRischiId() {
		return schedaFondoRischiId;
	}

	public void setSchedaFondoRischiId(Long schedaFondoRischiId) {
		this.schedaFondoRischiId = schedaFondoRischiId;
	}

	public String getDataAutorizzazione() {
		return dataAutorizzazione;
	}

	public void setDataAutorizzazione(String dataAutorizzazione) {
		this.dataAutorizzazione = dataAutorizzazione;
	}

	public String getDataRichiestaAutorizzazione() {
		return dataRichiestaAutorizzazione;
	}

	public void setDataRichiestaAutorizzazione(String dataRichiestaAutorizzazione) {
		this.dataRichiestaAutorizzazione = dataRichiestaAutorizzazione;
	}

	public String getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(String dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public String getControparte() {
		return controparte;
	}

	public void setControparte(String controparte) {
		this.controparte = controparte;
	}

	public Long getSocietaId() {
		return societaId;
	}

	public void setSocietaId(Long societaId) {
		this.societaId = societaId;
	}
	
	public Long getGiudizioId() {
		return giudizioId;
	}

	public void setGiudizioId(Long giudizioId) {
		this.giudizioId = giudizioId;
	}

	public List<ProfessionistaEsternoView> getListaProfessionistiEsterni() {
		return listaProfessionistiEsterni;
	}
	
	public void setListaProfessionistiEsterni(List<ProfessionistaEsternoView> listaProfessionistiEsterni) {
		this.listaProfessionistiEsterni = listaProfessionistiEsterni;
	}
	
	public String getStatoSchedaFondoRischi() {
		return statoSchedaFondoRischi;
	}

	public void setStatoSchedaFondoRischi(String statoSchedaFondoRischi) {
		this.statoSchedaFondoRischi = statoSchedaFondoRischi;
	}

	public String getStatoSchedaFondoRischiCode() {
		return statoSchedaFondoRischiCode;
	}

	public void setStatoSchedaFondoRischiCode(String statoSchedaFondoRischiCode) {
		this.statoSchedaFondoRischiCode = statoSchedaFondoRischiCode;
	}

	public Long getOrganoGiudicanteId() {
		return organoGiudicanteId;
	}

	public void setOrganoGiudicanteId(Long organoGiudicanteId) {
		this.organoGiudicanteId = organoGiudicanteId;
	}

	public Double getValoreDomanda() {
		return valoreDomanda;
	}

	public void setValoreDomanda(Double valoreDomanda) {
		this.valoreDomanda = valoreDomanda;
	}

	public String getTestoEsplicativo() {
		return testoEsplicativo;
	}

	public void setTestoEsplicativo(String testoEsplicativo) {
		this.testoEsplicativo = testoEsplicativo;
	}

	public Double getCoperturaAssicurativa() {
		return coperturaAssicurativa;
	}

	public void setCoperturaAssicurativa(Double coperturaAssicurativa) {
		this.coperturaAssicurativa = coperturaAssicurativa;
	}

	public Double getManleva() {
		return manleva;
	}

	public void setManleva(Double manleva) {
		this.manleva = manleva;
	}

	public Double getCommessaDiInvestimento() {
		return commessaDiInvestimento;
	}

	public void setCommessaDiInvestimento(Double commessaDiInvestimento) {
		this.commessaDiInvestimento = commessaDiInvestimento;
	}

	public Double getPassivitaStimata() {
		return passivitaStimata;
	}

	public void setPassivitaStimata(Double passivitaStimata) {
		this.passivitaStimata = passivitaStimata;
	}

	public String getMotivazione() {
		return motivazione;
	}

	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}

	public List<DocumentoView> getListaAllegatiLegaleEsterno() {
		return listaAllegatiLegaleEsterno;
	}

	public void setListaAllegatiLegaleEsterno(List<DocumentoView> listaAllegatiLegaleEsterno) {
		this.listaAllegatiLegaleEsterno = listaAllegatiLegaleEsterno;
	}
	
	public List<TipologiaSchedaFrView> getListaTipologiaScheda() {
		return listaTipologiaScheda;
	}

	public void setListaTipologiaScheda(List<TipologiaSchedaFrView> listaTipologiaScheda) {
		this.listaTipologiaScheda = listaTipologiaScheda;
	}

	public List<RischioSoccombenzaView> getListaRischioSoccombenza() {
		return listaRischioSoccombenza;
	}

	public void setListaRischioSoccombenza(List<RischioSoccombenzaView> listaRischioSoccombenza) {
		this.listaRischioSoccombenza = listaRischioSoccombenza;
	}

	public TipologiaSchedaFrView getTipologiaSchedaSelezionata() {
		return tipologiaSchedaSelezionata;
	}

	public void setTipologiaSchedaSelezionata(TipologiaSchedaFrView tipologiaSchedaSelezionata) {
		this.tipologiaSchedaSelezionata = tipologiaSchedaSelezionata;
	}

	public ProfessionistaEsternoView getProfessionistaEsternoSelezionato() {
		return professionistaEsternoSelezionato;
	}

	public void setProfessionistaEsternoSelezionato(ProfessionistaEsternoView professionistaEsternoSelezionato) {
		this.professionistaEsternoSelezionato = professionistaEsternoSelezionato;
	}

	public RischioSoccombenzaView getRischioSoccombenzaSelezionato() {
		return rischioSoccombenzaSelezionato;
	}

	public void setRischioSoccombenzaSelezionato(RischioSoccombenzaView rischioSoccombenzaSelezionato) {
		this.rischioSoccombenzaSelezionato = rischioSoccombenzaSelezionato;
	}

	public String getTipologiaSchedaFondoRischiCode() {
		return tipologiaSchedaFondoRischiCode;
	}

	public void setTipologiaSchedaFondoRischiCode(String tipologiaSchedaFondoRischiCode) {
		this.tipologiaSchedaFondoRischiCode = tipologiaSchedaFondoRischiCode;
	}

	public Long getProfessionistaEsternoId() {
		return professionistaEsternoId;
	}

	public void setProfessionistaEsternoId(Long professionistaEsternoId) {
		this.professionistaEsternoId = professionistaEsternoId;
	}

	public String getRischioSoccombenzaCode() {
		return rischioSoccombenzaCode;
	}

	public void setRischioSoccombenzaCode(String rischioSoccombenzaCode) {
		this.rischioSoccombenzaCode = rischioSoccombenzaCode;
	}

	public List<String> getListaSocietaAddebitoAggiunteDesc() {
		return listaSocietaAddebitoAggiunteDesc;
	}

	public void setListaSocietaAddebitoAggiunteDesc(List<String> listaSocietaAddebitoAggiunteDesc) {
		this.listaSocietaAddebitoAggiunteDesc = listaSocietaAddebitoAggiunteDesc;
	}

	public List<String> getListaGiudizioDesc() {
		return listaGiudizioDesc;
	}

	public void setListaGiudizioDesc(List<String> listaGiudizioDesc) {
		this.listaGiudizioDesc = listaGiudizioDesc;
	}

	public List<String> getListaOrganoGiudicanteDesc() {
		return listaOrganoGiudicanteDesc;
	}

	public void setListaOrganoGiudicanteDesc(List<String> listaOrganoGiudicanteDesc) {
		this.listaOrganoGiudicanteDesc = listaOrganoGiudicanteDesc;
	}

	public Set<String> getAllegatiDaRimuovereUuid() {
		return allegatiDaRimuovereUuid;
	}

	public void setAllegatiDaRimuovereUuid(Set<String> allegatiDaRimuovereUuid) {
		this.allegatiDaRimuovereUuid = allegatiDaRimuovereUuid;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public List<StatoSchedaFondoRischiView> getListaStatoSchedaFondoRischi() {
		return listaStatoSchedaFondoRischi;
	}

	public void setListaStatoSchedaFondoRischi(List<StatoSchedaFondoRischiView> listaStatoSchedaFondoRischi) {
		this.listaStatoSchedaFondoRischi = listaStatoSchedaFondoRischi;
	}

	public Integer getCoperturaAssicurativaFlag() {
		return coperturaAssicurativaFlag;
	}

	public void setCoperturaAssicurativaFlag(Integer coperturaAssicurativaFlag) {
		this.coperturaAssicurativaFlag = coperturaAssicurativaFlag;
	}

	public Integer getManlevaFlag() {
		return manlevaFlag;
	}

	public void setManlevaFlag(Integer manlevaFlag) {
		this.manlevaFlag = manlevaFlag;
	}

	public Integer getCommessaDiInvestimentoFlag() {
		return commessaDiInvestimentoFlag;
	}

	public void setCommessaDiInvestimentoFlag(Integer commessaDiInvestimentoFlag) {
		this.commessaDiInvestimentoFlag = commessaDiInvestimentoFlag;
	}

	public List<String> getListaControparteDesc() {
		return listaControparteDesc;
	}

	public void setListaControparteDesc(List<String> listaControparteDesc) {
		this.listaControparteDesc = listaControparteDesc;
	}

	public Double getValoreDomandaPrecedente() {
		return valoreDomandaPrecedente;
	}

	public void setValoreDomandaPrecedente(Double valoreDomanda) {
		this.valoreDomandaPrecedente = valoreDomanda;
	}

	public String getTestoEsplicativoPrecedente() {
		return testoEsplicativoPrecedente;
	}

	public void setTestoEsplicativoPrecedente(String testoEsplicativoPrecedente) {
		this.testoEsplicativoPrecedente = testoEsplicativoPrecedente;
	}

	public String getRischioSoccombenzaDesc() {
		return rischioSoccombenzaDesc;
	}

	public void setRischioSoccombenzaDesc(String rischioSoccombenzaDesc) {
		this.rischioSoccombenzaDesc = rischioSoccombenzaDesc;
	}

	public String getRischioSoccombenzaPrecDesc() {
		return rischioSoccombenzaPrecDesc;
	}

	public void setRischioSoccombenzaPrecDesc(String rischioSoccombenzaPrecDesc) {
		this.rischioSoccombenzaPrecDesc = rischioSoccombenzaPrecDesc;
	}

	public String getCoperturaAssicurativaDesc() {
		return coperturaAssicurativaDesc;
	}

	public void setCoperturaAssicurativaDesc(String coperturaAssicurativaDesc) {
		this.coperturaAssicurativaDesc = coperturaAssicurativaDesc;
	}

	public String getCoperturaAssicurativaPrecDesc() {
		return coperturaAssicurativaPrecDesc;
	}

	public void setCoperturaAssicurativaPrecDesc(String coperturaAssicurativaPrecDesc) {
		this.coperturaAssicurativaPrecDesc = coperturaAssicurativaPrecDesc;
	}

	public String getManlevaDesc() {
		return manlevaDesc;
	}

	public void setManlevaDesc(String manlevaDesc) {
		this.manlevaDesc = manlevaDesc;
	}

	public String getManlevaPrecDesc() {
		return manlevaPrecDesc;
	}

	public void setManlevaPrecDesc(String manlevaPrecDesc) {
		this.manlevaPrecDesc = manlevaPrecDesc;
	}

	public String getCommessaInvestimentoDesc() {
		return commessaInvestimentoDesc;
	}

	public void setCommessaInvestimentoDesc(String commessaInvestimentoDesc) {
		this.commessaInvestimentoDesc = commessaInvestimentoDesc;
	}

	public String getCommessaInvestimentoPrecDesc() {
		return commessaInvestimentoPrecDesc;
	}

	public void setCommessaInvestimentoPrecDesc(String commessaInvestimentoPrecDesc) {
		this.commessaInvestimentoPrecDesc = commessaInvestimentoPrecDesc;
	}

	public String getPassivitaStimataDesc() {
		return passivitaStimataDesc;
	}

	public void setPassivitaStimataDesc(String passivitaStimataDesc) {
		this.passivitaStimataDesc = passivitaStimataDesc;
	}

	public String getPassivitaStimataPrecDesc() {
		return passivitaStimataPrecDesc;
	}

	public void setPassivitaStimataPrecDesc(String passivitaStimataPrecDesc) {
		this.passivitaStimataPrecDesc = passivitaStimataPrecDesc;
	}

	public String getMotivazionePrecedente() {
		return motivazionePrecedente;
	}

	public void setMotivazionePrecedente(String motivazionePrecedente) {
		this.motivazionePrecedente = motivazionePrecedente;
	}
	
}
