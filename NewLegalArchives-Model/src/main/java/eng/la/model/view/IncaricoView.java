package eng.la.model.view;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import eng.la.model.Acconti;
import eng.la.model.Bonus;
import eng.la.model.Documento;
import eng.la.model.Incarico;
import eng.la.model.ListaRiferimento;
import eng.la.model.NotaPropIncarico;
import eng.la.model.Procura;
import eng.la.model.VerificaAnticorruzione;
import eng.la.model.VerificaPartiCorrelate;

public class IncaricoView extends BaseView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Incarico vo;

	public Incarico getVo() {
		return vo;
	}

	public void setVo(Incarico vo) {
		this.vo = vo;
	}

	/* CAMPI FORM PRINCIPALE INCARICO */
	private FascicoloView fascicoloRiferimento;
	private String commento;
	private String dataAutorizzazione;
	private String dataRichiestaAutorizzazione;
	private List<ProfessionistaEsternoView> listaProfessionista;
	private String statoIncarico;
	private String statoIncaricoCode;	
	private String nomeFascicolo;
	private String nomeIncarico;
	private Long incaricoId;
	private Long professionistaId;
	private ProfessionistaEsternoView professionistaSelezionato;
	private List<TipoValutaView> listaTipoValuta;
	private Long valutaId;
	private String dataRinvioVotazione;
 
	private List<StatoIncaricoView> listaStatoIncarico;
	private List<DocumentoView> listaAllegatiGenerici;
	private Set<String> allegatiDaRimuovereUuid;
	/* CAMPI NOTA PROPOSTA INCARICO */
	private Long notaIncaricoId;
	private String pratica;
	private String valoreIncarico;
	private String oggetto;
	private String descrizione;
	private String proposta;
	private String proponente;
	private String proponenteDesc;
	private Long procuratoreId;
	private List<ProcuratoreView> listaProcuratore;
	private String dataNotaProposta;
	private String responsabili;
	private String approvatore;
	private String autorizzatore;
	private String nomeForo;
	private String propostaDesc;
	private String infoCompensoNotaProp;
	private String  infoCorresponsioneCompenso;
	private String infoHandBook;
	private Integer sceltaInfo;
	private String allegato;
	private String societaParteProcedimento;
	private String compensoBonus;


	/* CAMPI LETTERA INCARICO */
	private Long letteraIncaricoId;
	private List<String> listaSocietaAddebitoAggiunteDesc;
	private String protocollo;
	private String dataProtocollo;
	private String oggettoProtocollo;
	private String descrizioneProtocollo;
	private BigDecimal compenso;
	private Integer mesiCompensoStragiudiziale;
	private Integer bonusCivile1;
	private Integer bonusCivile2;
	private Integer bonusCivile3;
	private Integer esitoBonusCivile2;
	private Integer esitoBonusCivile3;

	private Integer bonusAmministrativo1;
	private Integer bonusAmministrativo2;
	private Integer bonusArbitrale1;
	private Integer bonusArbitrale2;
	private Integer bonusArbitrale3;
	private Integer esitoBonusArbitrale2;
	private Integer esitoBonusArbitrale3;
	private String unitaOrganizzativa;
	private Boolean isQuadro;
	private List<SocietaView> listaSocietaAddebitoAggiunteDescLet;
	private String attivita;
	private String infoCompenso;
	private String snamretegasWebsite;
	private String snamWebsite;
	private String emailSegnalazioni;
	private String pieDiPaginaSnam;
	
	private String attivitaStragiudiziale;
	private String qualifica;
	
	private String utenteConnesso;
	private BigDecimal saldoImporto;
	private String saldoAnno;
	
	/* NUOVI CAMPI LETTERA INCARICO */
	
	private List<BonusView> bonus;
	private List<AccontoView> acconto;
	
	private Set<Bonus> bonusIn;
	private Set<Acconti> accontiIn;
	
	private Integer sizeBonus;
	private Integer sizeAcconti;
	
	private String numQuadro;
	
	private String timeout;
	private String timeoutNot;
	
	private String responsabileTop;
	
	private String idNotaProp;
	
	private boolean disabled;
	
	/* CAMPI NAZIONE E SPECIALIZZAZIONE */
	private String nazioneCode;
	private String specializzazioneCode;
	private NazioneView nazione;
	private SpecializzazioneView specializzazione;
	
	private List<ProcureView> listaProcure;
	
	
	public Set<String> getAllegatiDaRimuovereUuid() {
		return allegatiDaRimuovereUuid;
	}

	public void setAllegatiDaRimuovereUuid(Set<String> allegatiDaRimuovereUuid) {
		this.allegatiDaRimuovereUuid = allegatiDaRimuovereUuid;
	}

	/* CAMPI LISTE RIFERIMENTO */	
	private DocumentoView listeRiferimentoDoc;
	private ListaRiferimento listeRiferimento;
	/* CAMPI VERIFICA ANTICORRUZIONE */	
	private DocumentoView verificaAnticorruzioneDoc;
	private VerificaAnticorruzione verificaAnticorruzione;
	/* CAMPI VERIFICA PARTI CORRELATE */	
	private DocumentoView verificaPartiCorrelateDoc;
	private VerificaPartiCorrelate verificaPartiCorrelate;
	/* CAMPI PROCURA */	
	private DocumentoView procuraDoc;
	private Procura procura; 
	
	/* CAMPI LetteraIncaricoFirmata */	
	private DocumentoView letteraFirmataDoc;
	private Documento letteraFirmata;
	/*CAMPI NOTA PROPOSTA*/
	private DocumentoView notaPropostaDoc;
	private NotaPropIncarico notaProposta;

	/* CAMPI NotaFirmata */	
	private DocumentoView letteraFirmataDocNota;
	private Documento letteraFirmataNota;
	
	
	
	
	public DocumentoView getLetteraFirmataDocNota() {
		return letteraFirmataDocNota;
	}

	public void setLetteraFirmataDocNota(DocumentoView letteraFirmataDocNota) {
		this.letteraFirmataDocNota = letteraFirmataDocNota;
	}

	public Documento getLetteraFirmataNota() {
		return letteraFirmataNota;
	}

	public void setLetteraFirmataNota(Documento letteraFirmataNota) {
		this.letteraFirmataNota = letteraFirmataNota;
	}

	public Documento getLetteraFirmata() {
		return letteraFirmata;
	}

	public void setLetteraFirmata(Documento letteraFirmata) {
		this.letteraFirmata = letteraFirmata;
	}

	public DocumentoView getLetteraFirmataDoc() {
		return letteraFirmataDoc;
	}

	public void setLetteraFirmataDoc(DocumentoView letteraFirmataDoc) {
		this.letteraFirmataDoc = letteraFirmataDoc;
	}

	public String getCompensoBonus() {
		return compensoBonus;
	}

	public void setCompensoBonus(String compensoBonus) {
		this.compensoBonus = compensoBonus;
	}

	public String getResponsabileTop() {
		return responsabileTop;
	}

	public void setResponsabileTop(String responsabileTop) {
		this.responsabileTop = responsabileTop;
	}

	public String getTimeoutNot() {
		return timeoutNot;
	}

	public void setTimeoutNot(String timeoutNot) {
		this.timeoutNot = timeoutNot;
	}

	public String getIdNotaProp() {
		return idNotaProp;
	}

	public void setIdNotaProp(String idNotaProp) {
		this.idNotaProp = idNotaProp;
	}

	public Integer getSceltaInfo() {
		return sceltaInfo;
	}

	public void setSceltaInfo(Integer sceltaInfo) {
		this.sceltaInfo = sceltaInfo;
	}

	public DocumentoView getNotaPropostaDoc() {
		return notaPropostaDoc;
	}

	public void setNotaPropostaDoc(DocumentoView notaPropostaDoc) {
		this.notaPropostaDoc = notaPropostaDoc;
	}

	public NotaPropIncarico getNotaProposta() {
		return notaProposta;
	}

	public void setNotaProposta(NotaPropIncarico notaProposta) {
		this.notaProposta = notaProposta;
	}

	public String getPieDiPaginaSnam() {
		return pieDiPaginaSnam;
	}

	public void setPieDiPaginaSnam(String pieDiPaginaSnam) {
		this.pieDiPaginaSnam = pieDiPaginaSnam;
	}

	public Integer getSizeBonus() {
		return sizeBonus;
	}

	public void setSizeBonus(Integer sizeBonus) {
		this.sizeBonus = sizeBonus;
	}

	public Integer getSizeAcconti() {
		return sizeAcconti;
	}

	public void setSizeAcconti(Integer sizeAcconti) {
		this.sizeAcconti = sizeAcconti;
	}

	public Set<Bonus> getBonusIn() {
		return bonusIn;
	}

	public void setBonusIn(Set<Bonus> bonusIn) {
		this.bonusIn = bonusIn;
	}

	public Set<Acconti> getAccontiIn() {
		return accontiIn;
	}

	public void setAccontiIn(Set<Acconti> accontiIn) {
		this.accontiIn = accontiIn;
	}

	public BigDecimal getSaldoImporto() {
		return saldoImporto;
	}

	public void setSaldoImporto(BigDecimal saldoImporto) {
		this.saldoImporto = saldoImporto;
	}

	public String getSaldoAnno() {
		return saldoAnno;
	}

	public void setSaldoAnno(String saldoAnno) {
		this.saldoAnno = saldoAnno;
	}

	public List<AccontoView> getAcconto() {
		return acconto;
	}

	public void setAcconto(List<AccontoView> acconto) {
		this.acconto = acconto;
	}

	public String getQualifica() {
		return qualifica;
	}

	public void setQualifica(String qualifica) {
		this.qualifica = qualifica;
	}

	public String getAttivitaStragiudiziale() {
		return attivitaStragiudiziale;
	}

	public void setAttivitaStragiudiziale(String attivitaStragiudiziale) {
		this.attivitaStragiudiziale = attivitaStragiudiziale;
	}

	public String getEmailSegnalazioni() {
		return emailSegnalazioni;
	}

	public void setEmailSegnalazioni(String emailSegnalazioni) {
		this.emailSegnalazioni = emailSegnalazioni;
	}

	public String getSnamretegasWebsite() {
		return snamretegasWebsite;
	}

	public void setSnamretegasWebsite(String snamretegasWebsite) {
		this.snamretegasWebsite = snamretegasWebsite;
	}

	public String getSnamWebsite() {
		return snamWebsite;
	}

	public void setSnamWebsite(String snamWebsite) {
		this.snamWebsite = snamWebsite;
	}

	public String getAttivita() {
		return attivita;
	}

	public void setAttivita(String attivita) {
		this.attivita = attivita;
	}

	



	public String getInfoCompenso() {
		return infoCompenso;
	}

	public void setInfoCompenso(String infoCompenso) {
		this.infoCompenso = infoCompenso;
	}

	public List<SocietaView> getListaSocietaAddebitoAggiunteDescLet() {
		return listaSocietaAddebitoAggiunteDescLet;
	}

	public void setListaSocietaAddebitoAggiunteDescLet(List<SocietaView> listaSocietaAddebitoAggiunteDescLet) {
		this.listaSocietaAddebitoAggiunteDescLet = listaSocietaAddebitoAggiunteDescLet;
	}

	public String getUtenteConnesso() {
		return utenteConnesso;
	}

	public void setUtenteConnesso(String utenteConnesso) {
		this.utenteConnesso = utenteConnesso;
	}

	public Boolean getIsQuadro() {
		return isQuadro;
	}

	public void setIsQuadro(Boolean isQuadro) {
		this.isQuadro = isQuadro;
	}

	public String getUnitaOrganizzativa() {
		return unitaOrganizzativa;
	}

	public void setUnitaOrganizzativa(String unitaOrganizzativa) {
		this.unitaOrganizzativa = unitaOrganizzativa;
	}

	public List<BonusView> getBonus() {
		return bonus;
	}

	public void setBonus(List<BonusView> bonus) {
		this.bonus = bonus;
	}

	public Long getValutaId() {
		return valutaId;
	}

	public void setValutaId(Long valutaId) {
		this.valutaId = valutaId;
	}

	public List<TipoValutaView> getListaTipoValuta() {
		return listaTipoValuta;
	}

	public void setListaTipoValuta(List<TipoValutaView> listaTipoValuta) {
		this.listaTipoValuta = listaTipoValuta;
	}

	public List<StatoIncaricoView> getListaStatoIncarico() {
		return listaStatoIncarico;
	}

	public void setListaStatoIncarico(List<StatoIncaricoView> listaStatoIncarico) {
		this.listaStatoIncarico = listaStatoIncarico;
	}

	public String getStatoIncaricoCode() {
		return statoIncaricoCode;
	}

	public void setStatoIncaricoCode(String statoIncaricoCode) {
		this.statoIncaricoCode = statoIncaricoCode;
	}
	
	public List<DocumentoView> getListaAllegatiGenerici() {
		return listaAllegatiGenerici;
	}

	public void setListaAllegatiGenerici(List<DocumentoView> listaAllegatiGenerici) {
		this.listaAllegatiGenerici = listaAllegatiGenerici;
	}

	public String getCommento() {
		return commento;
	}

	public void setCommento(String commento) {
		this.commento = commento;
	}

	public Long getNotaIncaricoId() {
		return notaIncaricoId;
	}

	public void setNotaIncaricoId(Long notaIncaricoId) {
		this.notaIncaricoId = notaIncaricoId;
	}

	public Long getLetteraIncaricoId() {
		return letteraIncaricoId;
	}

	public void setLetteraIncaricoId(Long letteraIncaricoId) {
		this.letteraIncaricoId = letteraIncaricoId;
	}

	public Long getProfessionistaId() {
		return professionistaId;
	}

	public void setProfessionistaId(Long professionistaId) {
		this.professionistaId = professionistaId;
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
	
	public String getDataRinvioVotazione() {
		return dataRinvioVotazione;
	}

	public void setDataRinvioVotazione(String dataRinvioVotazione) {
		this.dataRinvioVotazione = dataRinvioVotazione;
	}

	public List<ProfessionistaEsternoView> getListaProfessionista() {
		return listaProfessionista;
	}

	public void setListaProfessionista(List<ProfessionistaEsternoView> listaProfessionista) {
		this.listaProfessionista = listaProfessionista;
	}

	public String getStatoIncarico() {
		return statoIncarico;
	}

	public void setStatoIncarico(String statoIncarico) {
		this.statoIncarico = statoIncarico;
	}

	public String getNomeFascicolo() {
		return nomeFascicolo;
	}

	public void setNomeFascicolo(String nomeFascicolo) {
		this.nomeFascicolo = nomeFascicolo;
	}

	public String getNomeIncarico() {
		return nomeIncarico;
	}

	public void setNomeIncarico(String nomeIncarico) {
		this.nomeIncarico = nomeIncarico;
	}

	public Long getIncaricoId() {
		return incaricoId;
	}

	public void setIncaricoId(Long incaricoId) {
		this.incaricoId = incaricoId;
	}

	public String getPratica() {
		return pratica;
	}

	public void setPratica(String pratica) {
		this.pratica = pratica;
	}


	public String getValoreIncarico() {
		return valoreIncarico;
	}

	public void setValoreIncarico(String valoreIncarico) {
		this.valoreIncarico = valoreIncarico;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getProposta() {
		return proposta;
	}

	public void setProposta(String proposta) {
		this.proposta = proposta;
	}

	public String getProponente() {
		return proponente;
	}

	public void setProponente(String proponente) {
		this.proponente = proponente;
	}

	public String getProponenteDesc() {
		return proponenteDesc;
	}

	public void setProponenteDesc(String proponenteDesc) {
		this.proponenteDesc = proponenteDesc;
	}

	public Long getProcuratoreId() {
		return procuratoreId;
	}

	public void setProcuratoreId(Long procuratoreId) {
		this.procuratoreId = procuratoreId;
	}

	public List<ProcuratoreView> getListaProcuratore() {
		return listaProcuratore;
	}

	public void setListaProcuratore(List<ProcuratoreView> listaProcuratore) {
		this.listaProcuratore = listaProcuratore;
	}

	public String getDataNotaProposta() {
		return dataNotaProposta;
	}

	public void setDataNotaProposta(String dataNotaProposta) {
		this.dataNotaProposta = dataNotaProposta;
	}

	public ProfessionistaEsternoView getProfessionistaSelezionato() {
		return professionistaSelezionato;
	}

	public void setProfessionistaSelezionato(ProfessionistaEsternoView professionistaSelezionato) {
		this.professionistaSelezionato = professionistaSelezionato;
	}

	public FascicoloView getFascicoloRiferimento() {
		return fascicoloRiferimento;
	}

	public void setFascicoloRiferimento(FascicoloView fascicoloRiferimento) {
		this.fascicoloRiferimento = fascicoloRiferimento;
	}

	public List<String> getListaSocietaAddebitoAggiunteDesc() {
		return listaSocietaAddebitoAggiunteDesc;
	}

	public void setListaSocietaAddebitoAggiunteDesc(List<String> listaSocietaAddebitoAggiunteDesc) {
		this.listaSocietaAddebitoAggiunteDesc = listaSocietaAddebitoAggiunteDesc;
	}

	public String getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}

	public String getDataProtocollo() {
		return dataProtocollo;
	}

	public void setDataProtocollo(String dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}

	public String getOggettoProtocollo() {
		return oggettoProtocollo;
	}

	public void setOggettoProtocollo(String oggettoProtocollo) {
		this.oggettoProtocollo = oggettoProtocollo;
	}

	public String getDescrizioneProtocollo() {
		return descrizioneProtocollo;
	}

	public void setDescrizioneProtocollo(String descrizioneProtocollo) {
		this.descrizioneProtocollo = descrizioneProtocollo;
	}

	public BigDecimal getCompenso() {
		return compenso;
	}

	public void setCompenso(BigDecimal compenso) {
		this.compenso = compenso;
	}

	public Integer getMesiCompensoStragiudiziale() {
		return mesiCompensoStragiudiziale;
	}

	public void setMesiCompensoStragiudiziale(Integer mesiCompensoStragiudiziale) {
		this.mesiCompensoStragiudiziale = mesiCompensoStragiudiziale;
	}

	public Integer getBonusCivile1() {
		return bonusCivile1;
	}

	public void setBonusCivile1(Integer bonusCivile1) {
		this.bonusCivile1 = bonusCivile1;
	}

	public Integer getBonusCivile2() {
		return bonusCivile2;
	}

	public void setBonusCivile2(Integer bonusCivile2) {
		this.bonusCivile2 = bonusCivile2;
	}

	public Integer getBonusCivile3() {
		return bonusCivile3;
	}

	public void setBonusCivile3(Integer bonusCivile3) {
		this.bonusCivile3 = bonusCivile3;
	}

	public Integer getEsitoBonusCivile2() {
		return esitoBonusCivile2;
	}

	public void setEsitoBonusCivile2(Integer esitoBonusCivile2) {
		this.esitoBonusCivile2 = esitoBonusCivile2;
	}

	public Integer getEsitoBonusCivile3() {
		return esitoBonusCivile3;
	}

	public void setEsitoBonusCivile3(Integer esitoBonusCivile3) {
		this.esitoBonusCivile3 = esitoBonusCivile3;
	}

	public Integer getBonusAmministrativo1() {
		return bonusAmministrativo1;
	}

	public void setBonusAmministrativo1(Integer bonusAmministrativo1) {
		this.bonusAmministrativo1 = bonusAmministrativo1;
	}

	public Integer getBonusAmministrativo2() {
		return bonusAmministrativo2;
	}

	public void setBonusAmministrativo2(Integer bonusAmministrativo2) {
		this.bonusAmministrativo2 = bonusAmministrativo2;
	}

	public Integer getBonusArbitrale1() {
		return bonusArbitrale1;
	}

	public void setBonusArbitrale1(Integer bonusArbitrale1) {
		this.bonusArbitrale1 = bonusArbitrale1;
	}

	public Integer getBonusArbitrale2() {
		return bonusArbitrale2;
	}

	public void setBonusArbitrale2(Integer bonusArbitrale2) {
		this.bonusArbitrale2 = bonusArbitrale2;
	}

	public Integer getBonusArbitrale3() {
		return bonusArbitrale3;
	}

	public void setBonusArbitrale3(Integer bonusArbitrale3) {
		this.bonusArbitrale3 = bonusArbitrale3;
	}

	public Integer getEsitoBonusArbitrale2() {
		return esitoBonusArbitrale2;
	}

	public void setEsitoBonusArbitrale2(Integer esitoBonusArbitrale2) {
		this.esitoBonusArbitrale2 = esitoBonusArbitrale2;
	}

	public Integer getEsitoBonusArbitrale3() {
		return esitoBonusArbitrale3;
	}

	public void setEsitoBonusArbitrale3(Integer esitoBonusArbitrale3) {
		this.esitoBonusArbitrale3 = esitoBonusArbitrale3;
	}

	public DocumentoView getListeRiferimentoDoc() {
		return listeRiferimentoDoc;
	}

	public void setListeRiferimentoDoc(DocumentoView listeRiferimentoDoc) {
		this.listeRiferimentoDoc = listeRiferimentoDoc;
	}

	public DocumentoView getVerificaAnticorruzioneDoc() {
		return verificaAnticorruzioneDoc;
	}

	public void setVerificaAnticorruzioneDoc(DocumentoView verificaAnticorruzioneDoc) {
		this.verificaAnticorruzioneDoc = verificaAnticorruzioneDoc;
	}

	public DocumentoView getVerificaPartiCorrelateDoc() {
		return verificaPartiCorrelateDoc;
	}

	public void setVerificaPartiCorrelateDoc(DocumentoView verificaPartiCorrelateDoc) {
		this.verificaPartiCorrelateDoc = verificaPartiCorrelateDoc;
	}

	public DocumentoView getProcuraDoc() {
		return procuraDoc;
	}
 
	public void setProcuraDoc(DocumentoView procuraDoc) {
		this.procuraDoc = procuraDoc;
	}

	public ListaRiferimento getListeRiferimento() {
		return listeRiferimento;
	}

	public void setListeRiferimento(ListaRiferimento listeRiferimento) {
		this.listeRiferimento = listeRiferimento;
	}

	public VerificaAnticorruzione getVerificaAnticorruzione() {
		return verificaAnticorruzione;
	}

	public void setVerificaAnticorruzione(VerificaAnticorruzione verificaAnticorruzione) {
		this.verificaAnticorruzione = verificaAnticorruzione;
	}

	public VerificaPartiCorrelate getVerificaPartiCorrelate() {
		return verificaPartiCorrelate;
	}

	public void setVerificaPartiCorrelate(VerificaPartiCorrelate verificaPartiCorrelate) {
		this.verificaPartiCorrelate = verificaPartiCorrelate;
	}

	public Procura getProcura() {
		return procura;
	}

	public void setProcura(Procura procura) {
		this.procura = procura;
	}

	public String getNumQuadro() {
		return numQuadro;
	}

	public void setNumQuadro(String numQuadro) {
		this.numQuadro = numQuadro;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getNazioneCode() {
		return nazioneCode;
	}

	public void setNazioneCode(String nazioneCode) {
		this.nazioneCode = nazioneCode;
	}

	public String getSpecializzazioneCode() {
		return specializzazioneCode;
	}

	public void setSpecializzazioneCode(String specializzazioneCode) {
		this.specializzazioneCode = specializzazioneCode;
	}

	public NazioneView getNazione() {
		return nazione;
	}

	public void setNazione(NazioneView nazione) {
		this.nazione = nazione;
	}

	public SpecializzazioneView getSpecializzazione() {
		return specializzazione;
	}

	public void setSpecializzazione(SpecializzazioneView specializzazione) {
		this.specializzazione = specializzazione;
	}
	
	public String getResponsabili() {
		return responsabili;
	}

	public void setResponsabili(String responsabili) {
		this.responsabili = responsabili;
	}

	public String getApprovatore() {
		return approvatore;
	}

	public void setApprovatore(String approvatore) {
		this.approvatore = approvatore;
	}

	public String getNomeForo() {
		return nomeForo;
	}

	public void setNomeForo(String nomeForo) {
		this.nomeForo = nomeForo;
	}

	public String getPropostaDesc() {
		return propostaDesc;
	}

	public void setPropostaDesc(String propostaDesc) {
		this.propostaDesc = propostaDesc;
	}

	public String getInfoCompensoNotaProp() {
		return infoCompensoNotaProp;
	}

	public void setInfoCompensoNotaProp(String infoCompensoNotaProp) {
		this.infoCompensoNotaProp = infoCompensoNotaProp;
	}

	public String getInfoCorresponsioneCompenso() {
		return infoCorresponsioneCompenso;
	}

	public void setInfoCorresponsioneCompenso(String infoCorresponsioneCompenso) {
		this.infoCorresponsioneCompenso = infoCorresponsioneCompenso;
	}

	public String getInfoHandBook() {
		return infoHandBook;
	}

	public void setInfoHandBook(String infoHandBook) {
		this.infoHandBook = infoHandBook;
	}

	public String getAllegato() {
		return allegato;
	}

	public void setAllegato(String allegato) {
		this.allegato = allegato;
	}

	public String getAutorizzatore() {
		return autorizzatore;
	}

	public void setAutorizzatore(String autorizzatore) {
		this.autorizzatore = autorizzatore;
	}

	public String getSocietaParteProcedimento() {
		return societaParteProcedimento;
	}

	public void setSocietaParteProcedimento(String societaParteProcedimento) {
		this.societaParteProcedimento = societaParteProcedimento;
	}

	public List<ProcureView> getListaProcure() {
		return listaProcure;
	}

	public void setListaProcure(List<ProcureView> listaProcure) {
		this.listaProcure = listaProcure;
	}

}
