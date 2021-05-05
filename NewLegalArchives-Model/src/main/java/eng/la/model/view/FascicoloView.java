package eng.la.model.view;

import java.util.Arrays;
import java.util.List;

import eng.la.model.Fascicolo;
import eng.la.model.custom.IncaricoCron;
import eng.la.model.custom.ProformaCron;

public class FascicoloView extends BaseView {

	/**
	 * 
	 */
	private static final long serialVersionUID = -313919746850986891L;
 
	private Fascicolo vo;

	// DATI PER LA PARTE DI PRESENTATION
	
	private List<StatoFascicoloView> listaStatoFascicolo;
	private List<TipologiaFascicoloView> listaTipologiaFascicolo;
	private List<SettoreGiuridicoView> listaSettoreGiuridico;
	private List<TipoContenziosoView> listaTipoContenzioso;
	private List<ValoreCausaView> listaValoreCausa;
	private List<PosizioneSocietaView> listaPosizioneSocieta;
	private List<TipoEntitaView> listaTipoControparte;
	private List<TipoEntitaView> listaTipoSoggetto;
	private List<GiudizioView> listaGiudizio;
	private List<RicorsoView> listaRicorso;
	private List<MateriaView> listaMateria;
	private List<OrganoGiudicanteView> listaOrganoGiudicante;
	private List<TipoSoggettoIndagatoView> listaTipoSoggettoIndagato;
	private List<TipoPrestNotarileView> listaTipoPrestazioneNotarile;
	private List<TipoCategDocumentaleView> listaCategoriaDocumentale;
	private List<String> listaMaterieAggiunteDesc;
	private List<String> listaSocietaAddebitoAggiunteDesc;
	private List<String> listaSocietaProcAggiunteDesc;
	private List<IncaricoCron> listaIncarichiCron;
	private List<ProformaCron> listaProformaCron;
	private String jsonAlberaturaMaterie; 
	private String statoFascicolo;
	private String ownerDal;
	private String nome="<nuovo>";
	private String descrizione;
	private String oggettoSintetico;
	private String legaleInterno;
	private String unitaLegale;
	private String legaleInternoDesc;
	private String unitaLegaleDesc;
	private String nomeControparte;
	private Integer valore;
	private String siglaCliente;
	private Integer numeroArchivio;
	private Integer numeroArchivioContenitore;
	private String tipologiaFascicoloCode;
	private String settoreGiuridicoCode;
	private String tipoContenziosoCode;
	private String autoritaEmanante;
	private String controinteressato;
	private String resistente;
	private String ricorrente;
	private String autoritaGiudiziaria;
	private String terzoChiamatoInCausa;
	private Boolean rilevante;

	private RUtenteFascicoloView[] utentiFascicolo;
	private TerzoChiamatoCausaView[] terzoChiamatoInCausaAggiunti;
	private ControparteView[] contropartiAggiunte;
	private SoggettoIndagatoView[] soggettoIndagatoAggiunti;
	private PersonaOffesaView[] personaOffesaAggiunte;
	private ParteCivileView[] parteCivileAggiunte;
	private ResponsabileCivileView[] responsabileCivileAggiunte;
	private RFascicoloRicorsoView[] ricorsiAggiunti;
	private RFascicoloGiudizioView[] giudiziAggiunti;

 	private FascicoloView[] fascicoliCorrelatiAggiunti;
	private Integer indexGiudizioAggiunto;
	private Integer indexRicorsoAggiunto;
	private String[] societaProcedimentoAggiunte;
	private String[] societaAddebitoAggiunte;
 	private String[] materie;
 	private String[] fascicoliCorrelati;
 	private String[] tipoPrestazioneNotarileAggiunte;
	
	private Long progettoId;
	private String progettoNome; 
	private String legaleRiferimento; 
	private String tipoTerzoChiamatoCausaCode; 
	private String tipoControparteCode; 
	
	private String tipoJoinVenture;
	private String target;
	private Long partnerId;

	private String statoFascicoloCode; 
	private String valoreCausaCode; 
	private String nomeSoggettoIndagato; 
	private String nomePersonaOffesa; 
	private String tipoPersonaOffesaCode;  
	private String nomeParteCivile; 
	private String tipoParteCivileCode;  
	private String nomeResponsabileCivile; 
	private String tipoResponsabileCivileCode;  
	private String posizioneSocietaAddebitoCode; 
	private String tipoSoggettoIndagatoCode; 
	private Long fascicoloId;
	private Long fascicoloPadreId;
	private String fascicoloPadreNome;
	private String nazioneCode; 
	
	//campi ricorso e giudizio
	private String organoGiudicanteCode; 
	private String ricorsoCode;
	private String giudizioCode;
	private String foro;
	private String numeroRegistroCausa;
	private String note;
	private String dataApertura;
	private String dataUltimaModifica;
	
	private String titolo;
	
	//campi relativi al proforma associato all'incarico relativo al fascicolo
	private String centroDiCosto;
	private String voceDiConto;
	
	
	
	
	public List<ProformaCron> getListaProformaCron() {
		return listaProformaCron;
	}

	public void setListaProformaCron(List<ProformaCron> listaProformaCron) {
		this.listaProformaCron = listaProformaCron;
	}

	public List<IncaricoCron> getListaIncarichiCron() {
		return listaIncarichiCron;
	}

	public void setListaIncarichiCron(List<IncaricoCron> listaIncarichiCron) {
		this.listaIncarichiCron = listaIncarichiCron;
	}

	public String getDataUltimaModifica() {
		return dataUltimaModifica;
	}

	public void setDataUltimaModifica(String dataUltimaModifica) {
		this.dataUltimaModifica = dataUltimaModifica;
	}

	public String getDataApertura() {
		return dataApertura;
	}

	public void setDataApertura(String dataApertura) {
		this.dataApertura = dataApertura;
	}

	private String tabAttiva= "1";
    
	public Fascicolo getVo() {
		return vo;
	}

	public void setVo(Fascicolo vo) {
		this.vo = vo;
	}
 	
	public Integer getIndexGiudizioAggiunto() {
		return indexGiudizioAggiunto;
	}

	public void setIndexGiudizioAggiunto(Integer indexGiudizioAggiunto) {
		this.indexGiudizioAggiunto = indexGiudizioAggiunto;
	}

	public List<StatoFascicoloView> getListaStatoFascicolo() {
		return listaStatoFascicolo;
	}

	public void setListaStatoFascicolo(List<StatoFascicoloView> listaStatoFascicolo) {
		this.listaStatoFascicolo = listaStatoFascicolo;
	}

	public String getStatoFascicoloCode() {
		return statoFascicoloCode;
	}

	public void setStatoFascicoloCode(String statoFascicoloCode) {
		this.statoFascicoloCode = statoFascicoloCode;
	}

	public Integer getIndexRicorsoAggiunto() {
		return indexRicorsoAggiunto;
	}

	public void setIndexRicorsoAggiunto(Integer indexRicorsoAggiunto) {
		this.indexRicorsoAggiunto = indexRicorsoAggiunto;
	}
 
	public RUtenteFascicoloView[] getUtentiFascicolo() {
		return utentiFascicolo;
	}

	public FascicoloView[] getFascicoliCorrelatiAggiunti() {
		return fascicoliCorrelatiAggiunti;
	}

	public void setFascicoliCorrelatiAggiunti(FascicoloView[] fascicoliCorrelatiAggiunti) {
		this.fascicoliCorrelatiAggiunti = fascicoliCorrelatiAggiunti;
	}

	public void setUtentiFascicolo(RUtenteFascicoloView[] utentiFascicolo) {
		this.utentiFascicolo = utentiFascicolo;
	}

	public String getOwnerDal() {
		return ownerDal;
	}

	public void setOwnerDal(String ownerDal) {
		this.ownerDal = ownerDal;
	}

	public String getStatoFascicolo() {
		return statoFascicolo;
	}

	public void setStatoFascicolo(String statoFascicolo) {
		this.statoFascicolo = statoFascicolo;
	}

	public RFascicoloRicorsoView[] getRicorsiAggiunti() {
		return ricorsiAggiunti;
	}

	public void setRicorsiAggiunti(RFascicoloRicorsoView[] ricorsiAggiunti) {
		this.ricorsiAggiunti = ricorsiAggiunti;
	}

	public RFascicoloGiudizioView[] getGiudiziAggiunti() {
		return giudiziAggiunti;
	}

	public void setGiudiziAggiunti(RFascicoloGiudizioView[] giudiziAggiunti) {
		this.giudiziAggiunti = giudiziAggiunti;
	}

	public List<TipoCategDocumentaleView> getListaCategoriaDocumentale() {
		return listaCategoriaDocumentale;
	}

	public void setListaCategoriaDocumentale(List<TipoCategDocumentaleView> listaCategoriaDocumentale) {
		this.listaCategoriaDocumentale = listaCategoriaDocumentale;
	}

	public List<TipoPrestNotarileView> getListaTipoPrestazioneNotarile() {
		return listaTipoPrestazioneNotarile;
	}

	public void setListaTipoPrestazioneNotarile(List<TipoPrestNotarileView> listaTipoPrestazioneNotarile) {
		this.listaTipoPrestazioneNotarile = listaTipoPrestazioneNotarile;
	}

	public String[] getTipoPrestazioneNotarileAggiunte() {
		return tipoPrestazioneNotarileAggiunte;
	}

	public void setTipoPrestazioneNotarileAggiunte(String[] tipoPrestazioneNotarileAggiunte) {
		this.tipoPrestazioneNotarileAggiunte = tipoPrestazioneNotarileAggiunte;
	}

	public List<String> getListaMaterieAggiunteDesc() {
		return listaMaterieAggiunteDesc;
	}

	public void setListaMaterieAggiunteDesc(List<String> listaMaterieAggiunteDesc) {
		this.listaMaterieAggiunteDesc = listaMaterieAggiunteDesc;
	}

	public List<String> getListaSocietaAddebitoAggiunteDesc() {
		return listaSocietaAddebitoAggiunteDesc;
	}

	public void setListaSocietaAddebitoAggiunteDesc(List<String> listaSocietaAddebitoAggiunteDesc) {
		this.listaSocietaAddebitoAggiunteDesc = listaSocietaAddebitoAggiunteDesc;
	}

	public List<String> getListaSocietaProcAggiunteDesc() {
		return listaSocietaProcAggiunteDesc;
	}

	public void setListaSocietaProcAggiunteDesc(List<String> listaSocietaProcAggiunteDesc) {
		this.listaSocietaProcAggiunteDesc = listaSocietaProcAggiunteDesc;
	}

	public List<TipoSoggettoIndagatoView> getListaTipoSoggettoIndagato() {
		return listaTipoSoggettoIndagato;
	}

	public void setListaTipoSoggettoIndagato(List<TipoSoggettoIndagatoView> listaTipoSoggettoIndagato) {
		this.listaTipoSoggettoIndagato = listaTipoSoggettoIndagato;
	}

	public String getFascicoloPadreNome() {
		return fascicoloPadreNome;
	}

	public String getTipoJoinVenture() {
		return tipoJoinVenture;
	}

	public void setTipoJoinVenture(String tipoJoinVenture) {
		this.tipoJoinVenture = tipoJoinVenture;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partenerId) {
		this.partnerId = partenerId;
	}

	public void setFascicoloPadreNome(String fascicoloPadreNome) {
		this.fascicoloPadreNome = fascicoloPadreNome;
	}

	public String getProgettoNome() {
		return progettoNome;
	}

	public void setProgettoNome(String progettoNome) {
		this.progettoNome = progettoNome;
	}

	public PersonaOffesaView[] getPersonaOffesaAggiunte() {
		return personaOffesaAggiunte;
	}

	public void setPersonaOffesaAggiunte(PersonaOffesaView[] personaOffesaAggiunte) {
		this.personaOffesaAggiunte = personaOffesaAggiunte;
	}

	public ParteCivileView[] getParteCivileAggiunte() {
		return parteCivileAggiunte;
	}

	public void setParteCivileAggiunte(ParteCivileView[] parteCivileAggiunte) {
		this.parteCivileAggiunte = parteCivileAggiunte;
	}

	public ResponsabileCivileView[] getResponsabileCivileAggiunte() {
		return responsabileCivileAggiunte;
	}

	public void setResponsabileCivileAggiunte(ResponsabileCivileView[] responsabileCivileAggiunte) {
		this.responsabileCivileAggiunte = responsabileCivileAggiunte;
	}

	public String getNomeParteCivile() {
		return nomeParteCivile;
	}

	public void setNomeParteCivile(String nomeParteCivile) {
		this.nomeParteCivile = nomeParteCivile;
	}

	public String getLegaleRiferimento() {
		return legaleRiferimento;
	}

	public void setLegaleRiferimento(String legaleRiferimento) {
		this.legaleRiferimento = legaleRiferimento;
	}

	public String getTipoTerzoChiamatoCausaCode() {
		return tipoTerzoChiamatoCausaCode;
	}

	public void setTipoTerzoChiamatoCausaCode(String tipoTerzoChiamatoCausaCode) {
		this.tipoTerzoChiamatoCausaCode = tipoTerzoChiamatoCausaCode;
	}

	public String getTipoParteCivileCode() {
		return tipoParteCivileCode;
	}

	public void setTipoParteCivileCode(String tipoParteCivileCode) {
		this.tipoParteCivileCode = tipoParteCivileCode;
	}

	public String getNomeResponsabileCivile() {
		return nomeResponsabileCivile;
	}

	public void setNomeResponsabileCivile(String nomeResponsabileCivile) {
		this.nomeResponsabileCivile = nomeResponsabileCivile;
	}

	public String getTipoResponsabileCivileCode() {
		return tipoResponsabileCivileCode;
	}

	public void setTipoResponsabileCivileCode(String tipoResponsabileCivileCode) {
		this.tipoResponsabileCivileCode = tipoResponsabileCivileCode;
	}

	public String getNomePersonaOffesa() {
		return nomePersonaOffesa;
	}

	public void setNomePersonaOffesa(String nomePersonaOffesa) {
		this.nomePersonaOffesa = nomePersonaOffesa;
	}

	public String getTipoPersonaOffesaCode() {
		return tipoPersonaOffesaCode;
	}

	public void setTipoPersonaOffesaCode(String tipoPersonaOffesaCode) {
		this.tipoPersonaOffesaCode = tipoPersonaOffesaCode;
	}

	public SoggettoIndagatoView[] getSoggettoIndagatoAggiunti() {
		return soggettoIndagatoAggiunti;
	}

	public void setSoggettoIndagatoAggiunti(SoggettoIndagatoView[] soggettoIndagatoAggiunti) {
		this.soggettoIndagatoAggiunti = soggettoIndagatoAggiunti;
	}

	public String getNomeSoggettoIndagato() {
		return nomeSoggettoIndagato;
	}

	public void setNomeSoggettoIndagato(String nomeSoggettoIndagato) {
		this.nomeSoggettoIndagato = nomeSoggettoIndagato;
	}

	public List<TipoEntitaView> getListaTipoSoggetto() {
		return listaTipoSoggetto;
	}

	public void setListaTipoSoggetto(List<TipoEntitaView> listaTipoSoggetto) {
		this.listaTipoSoggetto = listaTipoSoggetto;
	}

	public String getTipoSoggettoIndagatoCode() {
		return tipoSoggettoIndagatoCode;
	}

	public void setTipoSoggettoIndagatoCode(String tipoSoggettoIndagatoCode) {
		this.tipoSoggettoIndagatoCode = tipoSoggettoIndagatoCode;
	}

	public List<RicorsoView> getListaRicorso() {
		return listaRicorso;
	}

	public void setListaRicorso(List<RicorsoView> listaRicorso) {
		this.listaRicorso = listaRicorso;
	}

	public String getJsonAlberaturaMaterie() {
		return jsonAlberaturaMaterie;
	}

	public void setJsonAlberaturaMaterie(String jsonAlberaturaMaterie) {
		this.jsonAlberaturaMaterie = jsonAlberaturaMaterie;
	}

	public List<OrganoGiudicanteView> getListaOrganoGiudicante() {
		return listaOrganoGiudicante;
	}

	public void setListaOrganoGiudicante(List<OrganoGiudicanteView> listaOrganoGiudicante) {
		this.listaOrganoGiudicante = listaOrganoGiudicante;
	}

	public String getTabAttiva() {
		return tabAttiva;
	}

	public void setTabAttiva(String tabAttiva) {
		this.tabAttiva = tabAttiva;
	}

	public String getLegaleInternoDesc() {
		return legaleInternoDesc;
	}

	public void setLegaleInternoDesc(String legaleInternoDesc) {
		this.legaleInternoDesc = legaleInternoDesc;
	}

	public String getUnitaLegaleDesc() {
		return unitaLegaleDesc;
	}

	public void setUnitaLegaleDesc(String unitaLegaleDesc) {
		this.unitaLegaleDesc = unitaLegaleDesc;
	}

	public List<MateriaView> getListaMateria() {
		return listaMateria;
	}

	public void setListaMateria(List<MateriaView> listaMateria) {
		this.listaMateria = listaMateria;
	}

	public TerzoChiamatoCausaView[] getTerzoChiamatoInCausaAggiunti() {
		return terzoChiamatoInCausaAggiunti;
	}

	public void setTerzoChiamatoInCausaAggiunti(TerzoChiamatoCausaView[] terzoChiamatoInCausaAggiunti) {
		this.terzoChiamatoInCausaAggiunti = terzoChiamatoInCausaAggiunti;
	}

	public String getAutoritaEmanante() {
		return autoritaEmanante;
	}

	public void setAutoritaEmanante(String autoritaEmanante) {
		this.autoritaEmanante = autoritaEmanante;
	}

	public String getControinteressato() {
		return controinteressato;
	}

	public void setControinteressato(String controinteressato) {
		this.controinteressato = controinteressato;
	}
	
	       

	public String getResistente() {
		return resistente;
	}

	public void setResistente(String resistente) {
		this.resistente = resistente;
	}

	public String getRicorrente() {
		return ricorrente;
	}

	public void setRicorrente(String ricorrente) {
		this.ricorrente = ricorrente;
	}

	public ControparteView[] getContropartiAggiunte() {
		return contropartiAggiunte;
	}

	public void setContropartiAggiunte(ControparteView[] contropartiAggiunte) {
		this.contropartiAggiunte = contropartiAggiunte;
	}

	public Integer getValore() {
		return valore;
	}

	public void setValore(Integer valore) {
		this.valore = valore;
	}

	public String getTerzoChiamatoInCausa() {
		return terzoChiamatoInCausa;
	}

	public void setTerzoChiamatoInCausa(String terzoChiamatoInCausa) {
		this.terzoChiamatoInCausa = terzoChiamatoInCausa;
	}
 
	public String getNomeControparte() {
		return nomeControparte;
	}

	public void setNomeControparte(String nomeControparte) {
		this.nomeControparte = nomeControparte;
	}
 
	public List<GiudizioView> getListaGiudizio() {
		return listaGiudizio;
	}

	public void setListaGiudizio(List<GiudizioView> listaGiudizio) {
		this.listaGiudizio = listaGiudizio;
	}

	public List<TipoEntitaView> getListaTipoControparte() {
		return listaTipoControparte;
	}

	public void setListaTipoControparte(List<TipoEntitaView> listaTipoControparte) {
		this.listaTipoControparte = listaTipoControparte;
	}

	public String[] getFascicoliCorrelati() {
		return fascicoliCorrelati;
	}

	public void setFascicoliCorrelati(String[] fascicoliCorrelati) {
		this.fascicoliCorrelati = fascicoliCorrelati;
	}

	public Long getFascicoloPadreId() {
		return fascicoloPadreId;
	}

	public void setFascicoloPadreId(Long fascicoloPadreId) {
		this.fascicoloPadreId = fascicoloPadreId;
	}
 
	public String getOggettoSintetico() {
		return oggettoSintetico;
	}

	public void setOggettoSintetico(String oggettoSintetico) {
		this.oggettoSintetico = oggettoSintetico;
	}
 
	public List<PosizioneSocietaView> getListaPosizioneSocieta() {
		return listaPosizioneSocieta;
	}

	public void setListaPosizioneSocieta(List<PosizioneSocietaView> listaPosizioneSocieta) {
		this.listaPosizioneSocieta = listaPosizioneSocieta;
	}
 
	public List<TipologiaFascicoloView> getListaTipologiaFascicolo() {
		return listaTipologiaFascicolo;
	}

	public void setListaTipologiaFascicolo(List<TipologiaFascicoloView> listaTipologiaFascicolo) {
		this.listaTipologiaFascicolo = listaTipologiaFascicolo;
	}

	public List<SettoreGiuridicoView> getListaSettoreGiuridico() {
		return listaSettoreGiuridico;
	}

	public void setListaSettoreGiuridico(List<SettoreGiuridicoView> listaSettoreGiuridico) {
		this.listaSettoreGiuridico = listaSettoreGiuridico;
	}

	public String getTipologiaFascicoloCode() {
		return tipologiaFascicoloCode;
	}

	public void setTipologiaFascicoloCode(String tipologiaFascicoloCode) {
		this.tipologiaFascicoloCode = tipologiaFascicoloCode;
	}

	public String getSettoreGiuridicoCode() {
		return settoreGiuridicoCode;
	}

	public void setSettoreGiuridicoCode(String settoreGiuridicoCode) {
		this.settoreGiuridicoCode = settoreGiuridicoCode;
	}

	public Long getFascicoloId() {
		return fascicoloId;
	}

	public void setFascicoloId(Long fascicoloId) {
		this.fascicoloId = fascicoloId;
	}

	public String getTipoContenziosoCode() {
		return tipoContenziosoCode;
	}

	public void setTipoContenziosoCode(String tipoContenziosoCode) {
		this.tipoContenziosoCode = tipoContenziosoCode;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getLegaleInterno() {
		return legaleInterno;
	}

	public void setLegaleInterno(String legaleInterno) {
		this.legaleInterno = legaleInterno;
	}

	public String getUnitaLegale() {
		return unitaLegale;
	}

	public void setUnitaLegale(String unitaLegale) {
		this.unitaLegale = unitaLegale;
	}
 
	public String getSiglaCliente() {
		return siglaCliente;
	}

	public void setSiglaCliente(String siglaCliente) {
		this.siglaCliente = siglaCliente;
	}

	public Integer getNumeroArchivio() {
		return numeroArchivio;
	}

	public void setNumeroArchivio(Integer numeroArchivio) {
		this.numeroArchivio = numeroArchivio;
	}

	public Integer getNumeroArchivioContenitore() {
		return numeroArchivioContenitore;
	}

	public void setNumeroArchivioContenitore(Integer numeroArchivioContenitore) {
		this.numeroArchivioContenitore = numeroArchivioContenitore;
	}

	public String getForo() {
		return foro;
	}

	public void setForo(String foro) {
		this.foro = foro;
	}

	public String getNumeroRegistroCausa() {
		return numeroRegistroCausa;
	}

	public void setNumeroRegistroCausa(String numeroRegistroCausa) {
		this.numeroRegistroCausa = numeroRegistroCausa;
	}

	public String getAutoritaGiudiziaria() {
		return autoritaGiudiziaria;
	}

	public void setAutoritaGiudiziaria(String autoritaGiudiziaria) {
		this.autoritaGiudiziaria = autoritaGiudiziaria;
	}

	public Boolean getRilevante() {
		return rilevante;
	}

	public void setRilevante(Boolean rilevante) {
		this.rilevante = rilevante;
	}

	public String[] getMaterie() {
		return materie;
	}

	public void setMaterie(String[] materie) {
		this.materie = materie;
	}

	public Long getProgettoId() {
		return progettoId;
	}

	public void setProgettoId(Long progettoId) {
		this.progettoId = progettoId;
	}
  
	public List<TipoContenziosoView> getListaTipoContenzioso() {
		return listaTipoContenzioso;
	}

	public void setListaTipoContenzioso(List<TipoContenziosoView> listaTipoContenzioso) {
		this.listaTipoContenzioso = listaTipoContenzioso;
	}

	public List<ValoreCausaView> getListaValoreCausa() {
		return listaValoreCausa;
	}

	public void setListaValoreCausa(List<ValoreCausaView> listaValoreCausa) {
		this.listaValoreCausa = listaValoreCausa;
	}

	public String[] getSocietaProcedimentoAggiunte() {
		return societaProcedimentoAggiunte;
	}

	public void setSocietaProcedimentoAggiunte(String[] societaProcedimentoAggiunte) {
		this.societaProcedimentoAggiunte = societaProcedimentoAggiunte;
	}

	public String[] getSocietaAddebitoAggiunte() {
		return societaAddebitoAggiunte;
	}

	public void setSocietaAddebitoAggiunte(String[] societaAddebitoAggiunte) {
		this.societaAddebitoAggiunte = societaAddebitoAggiunte;
	}

	public String getTipoControparteCode() {
		return tipoControparteCode;
	}

	public void setTipoControparteCode(String tipoControparteCode) {
		this.tipoControparteCode = tipoControparteCode;
	}

	public String getRicorsoCode() {
		return ricorsoCode;
	}

	public void setRicorsoCode(String ricorsoCode) {
		this.ricorsoCode = ricorsoCode;
	}

	public String getGiudizioCode() {
		return giudizioCode;
	}

	public void setGiudizioCode(String giudizioCode) {
		this.giudizioCode = giudizioCode;
	}

	public String getValoreCausaCode() {
		return valoreCausaCode;
	}

	public void setValoreCausaCode(String valoreCausaCode) {
		this.valoreCausaCode = valoreCausaCode;
	}

	public String getPosizioneSocietaAddebitoCode() {
		return posizioneSocietaAddebitoCode;
	}

	public void setPosizioneSocietaAddebitoCode(String posizioneSocietaAddebitoCode) {
		this.posizioneSocietaAddebitoCode = posizioneSocietaAddebitoCode;
	}

	public String getNazioneCode() {
		return nazioneCode;
	}

	public void setNazioneCode(String nazioneCode) {
		this.nazioneCode = nazioneCode;
	}

	public String getOrganoGiudicanteCode() {
		return organoGiudicanteCode;
	}

	public void setOrganoGiudicanteCode(String organoGiudicanteCode) {
		this.organoGiudicanteCode = organoGiudicanteCode;
	}

	@Override
	public String toString() {
		return "FascicoloView [vo=" + vo + ", listaStatoFascicolo=" + listaStatoFascicolo + ", listaTipologiaFascicolo="
				+ listaTipologiaFascicolo + ", listaSettoreGiuridico=" + listaSettoreGiuridico
				+ ", listaTipoContenzioso=" + listaTipoContenzioso + ", listaValoreCausa=" + listaValoreCausa
				+ ", listaPosizioneSocieta=" + listaPosizioneSocieta + ", listaTipoControparte=" + listaTipoControparte
				+ ", listaTipoSoggetto=" + listaTipoSoggetto + ", listaGiudizio=" + listaGiudizio + ", listaRicorso="
				+ listaRicorso + ", listaMateria=" + listaMateria + ", listaOrganoGiudicante=" + listaOrganoGiudicante
				+ ", listaTipoSoggettoIndagato=" + listaTipoSoggettoIndagato + ", listaTipoPrestazioneNotarile="
				+ listaTipoPrestazioneNotarile + ", listaCategoriaDocumentale=" + listaCategoriaDocumentale
				+ ", listaMaterieAggiunteDesc=" + listaMaterieAggiunteDesc + ", listaSocietaAddebitoAggiunteDesc="
				+ listaSocietaAddebitoAggiunteDesc + ", listaSocietaProcAggiunteDesc=" + listaSocietaProcAggiunteDesc
				+ ", jsonAlberaturaMaterie=" + jsonAlberaturaMaterie + ", statoFascicolo=" + statoFascicolo
				+ ", ownerDal=" + ownerDal + ", nome=" + nome + ", descrizione=" + descrizione + ", oggettoSintetico="
				+ oggettoSintetico + ", legaleInterno=" + legaleInterno + ", unitaLegale=" + unitaLegale
				+ ", legaleInternoDesc=" + legaleInternoDesc + ", unitaLegaleDesc=" + unitaLegaleDesc
				+ ", nomeControparte=" + nomeControparte + ", valore=" + valore + ", siglaCliente=" + siglaCliente
				+ ", numeroArchivio=" + numeroArchivio + ", numeroArchivioContenitore=" + numeroArchivioContenitore
				+ ", tipologiaFascicoloCode=" + tipologiaFascicoloCode + ", settoreGiuridicoCode="
				+ settoreGiuridicoCode + ", tipoContenziosoCode=" + tipoContenziosoCode + ", autoritaEmanante="
				+ autoritaEmanante + ", controinteressato=" + controinteressato + ", autoritaGiudiziaria="
				+ autoritaGiudiziaria + ", terzoChiamatoInCausa=" + terzoChiamatoInCausa + ", rilevante=" + rilevante
				+ ", utentiFascicolo=" + Arrays.toString(utentiFascicolo) + ", terzoChiamatoInCausaAggiunti="
				+ Arrays.toString(terzoChiamatoInCausaAggiunti) + ", contropartiAggiunte="
				+ Arrays.toString(contropartiAggiunte) + ", soggettoIndagatoAggiunti="
				+ Arrays.toString(soggettoIndagatoAggiunti) + ", personaOffesaAggiunte="
				+ Arrays.toString(personaOffesaAggiunte) + ", parteCivileAggiunte="
				+ Arrays.toString(parteCivileAggiunte) + ", responsabileCivileAggiunte="
				+ Arrays.toString(responsabileCivileAggiunte) + ", ricorsiAggiunti=" + Arrays.toString(ricorsiAggiunti)
				+ ", giudiziAggiunti=" + Arrays.toString(giudiziAggiunti) + ", fascicoliCorrelatiAggiunti="
				+ Arrays.toString(fascicoliCorrelatiAggiunti) + ", indexGiudizioAggiunto=" + indexGiudizioAggiunto
				+ ", indexRicorsoAggiunto=" + indexRicorsoAggiunto + ", societaProcedimentoAggiunte="
				+ Arrays.toString(societaProcedimentoAggiunte) + ", societaAddebitoAggiunte="
				+ Arrays.toString(societaAddebitoAggiunte) + ", materie=" + Arrays.toString(materie)
				+ ", fascicoliCorrelati=" + Arrays.toString(fascicoliCorrelati) + ", tipoPrestazioneNotarileAggiunte="
				+ Arrays.toString(tipoPrestazioneNotarileAggiunte) + ", progettoId=" + progettoId + ", progettoNome="
				+ progettoNome + ", legaleRiferimento=" + legaleRiferimento + ", tipoTerzoChiamatoCausaCode="
				+ tipoTerzoChiamatoCausaCode + ", tipoControparteCode=" + tipoControparteCode + ", tipoJoinVenture="
				+ tipoJoinVenture + ", target=" + target + ", partnerId=" + partnerId + ", statoFascicoloCode="
				+ statoFascicoloCode + ", valoreCausaCode=" + valoreCausaCode + ", nomeSoggettoIndagato="
				+ nomeSoggettoIndagato + ", nomePersonaOffesa=" + nomePersonaOffesa + ", tipoPersonaOffesaCode="
				+ tipoPersonaOffesaCode + ", nomeParteCivile=" + nomeParteCivile + ", tipoParteCivileCode="
				+ tipoParteCivileCode + ", nomeResponsabileCivile=" + nomeResponsabileCivile
				+ ", tipoResponsabileCivileCode=" + tipoResponsabileCivileCode + ", posizioneSocietaAddebitoCode="
				+ posizioneSocietaAddebitoCode + ", tipoSoggettoIndagatoCode=" + tipoSoggettoIndagatoCode
				+ ", fascicoloId=" + fascicoloId + ", fascicoloPadreId=" + fascicoloPadreId + ", fascicoloPadreNome="
				+ fascicoloPadreNome + ", nazioneCode=" + nazioneCode + ", organoGiudicanteCode=" + organoGiudicanteCode
				+ ", ricorsoCode=" + ricorsoCode + ", giudizioCode=" + giudizioCode + ", foro=" + foro
				+ ", numeroRegistroCausa=" + numeroRegistroCausa + ", note=" + note + ", tabAttiva=" + tabAttiva + "]";
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getCentroDiCosto() {
		return centroDiCosto;
	}

	public void setCentroDiCosto(String centroDiCosto) {
		this.centroDiCosto = centroDiCosto;
	}

	public String getVoceDiConto() {
		return voceDiConto;
	}

	public void setVoceDiConto(String voceDiConto) {
		this.voceDiConto = voceDiConto;
	}

	
	
	
}
