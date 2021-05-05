package eng.la.model.view;

import java.util.List;
import java.util.Set;

import eng.la.model.Procure;

public class ProcureView extends BaseView {

	private static final long serialVersionUID = 1L;
	private Procure vo;
	
	// Campi Insert
	private long id;
	private String dataConferimento;
	private String dataRevoca;
	private String dataCreazione;
	private String tipologia;
	private String nomeProcuratore;
	private String numeroRepertorio;
	private Long idSocieta;
	private Long idNotaio;	
	private List<DocumentoView> listaDocumenti;
	private List<DocumentoView> listaAllegatiGenerici;
	
	private Set<String> allegatiDaRimuovereUuid;

	private List<ProfessionistaEsternoView> listaProfessionista;
	private ProfessionistaEsternoView professionistaSelezionato;
	
	private List<TipoProcureView> listaTipologie;
	
	private String utente;
	private String posizioneOrganizzativa;
	private String livelloAttribuzioniI;
	private String livelloAttribuzioniII;
	
	private List<PosizioneOrganizzativaView> listaPosizioneOrganizzativa;
	private List<LivelloAttribuzioniIView> listaLivelloAttribuzioniI;
	private List<LivelloAttribuzioniIIView> listaLivelloAttribuzioniII;


	private Long procureId;




	public Procure getVo() {
		return vo;
	}


	public void setVo(Procure vo) {
		this.vo = vo;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getDataConferimento() {
		return dataConferimento;
	}


	public void setDataConferimento(String dataConferimento) {
		this.dataConferimento = dataConferimento;
	}


	public String getDataRevoca() {
		return dataRevoca;
	}


	public void setDataRevoca(String dataRevoca) {
		this.dataRevoca = dataRevoca;
	}


	public String getDataCreazione() {
		return dataCreazione;
	}


	public void setDataCreazione(String dataCreazione) {
		this.dataCreazione = dataCreazione;
	}


	public String getTipologia() {
		return tipologia;
	}


	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}


	public String getNomeProcuratore() {
		return nomeProcuratore;
	}


	public void setNomeProcuratore(String nomeProcuratore) {
		this.nomeProcuratore = nomeProcuratore;
	}


	public String getNumeroRepertorio() {
		return numeroRepertorio;
	}


	public void setNumeroRepertorio(String numeroRepertorio) {
		this.numeroRepertorio = numeroRepertorio;
	}


	public Long getIdSocieta() {
		return idSocieta;
	}


	public void setIdSocieta(Long idSocieta) {
		this.idSocieta = idSocieta;
	}


	public Long getIdNotaio() {
		return idNotaio;
	}


	public void setIdNotaio(Long idNotaio) {
		this.idNotaio = idNotaio;
	}


	public List<DocumentoView> getListaDocumenti() {
		return listaDocumenti;
	}


	public void setListaDocumenti(List<DocumentoView> listaDocumenti) {
		this.listaDocumenti = listaDocumenti;
	}


	public List<DocumentoView> getListaAllegatiGenerici() {
		return listaAllegatiGenerici;
	}


	public void setListaAllegatiGenerici(List<DocumentoView> listaAllegatiGenerici) {
		this.listaAllegatiGenerici = listaAllegatiGenerici;
	}


	public Set<String> getAllegatiDaRimuovereUuid() {
		return allegatiDaRimuovereUuid;
	}


	public void setAllegatiDaRimuovereUuid(Set<String> allegatiDaRimuovereUuid) {
		this.allegatiDaRimuovereUuid = allegatiDaRimuovereUuid;
	}


	public List<ProfessionistaEsternoView> getListaProfessionista() {
		return listaProfessionista;
	}


	public void setListaProfessionista(List<ProfessionistaEsternoView> listaProfessionista) {
		this.listaProfessionista = listaProfessionista;
	}


	public ProfessionistaEsternoView getProfessionistaSelezionato() {
		return professionistaSelezionato;
	}


	public void setProfessionistaSelezionato(ProfessionistaEsternoView professionistaSelezionato) {
		this.professionistaSelezionato = professionistaSelezionato;
	}


	public Long getProcureId() {
		return procureId;
	}
	
	public void setProcureId(Long procureId) {
		this.procureId = procureId;
	}


	public List<TipoProcureView> getListaTipologie() {
		return listaTipologie;
	}


	public void setListaTipologie(List<TipoProcureView> listaTipologie) {
		this.listaTipologie = listaTipologie;
	}


	public String getUtente() {
		return utente;
	}


	public void setUtente(String utente) {
		this.utente = utente;
	}

	public String getPosizioneOrganizzativa() {
		return posizioneOrganizzativa;
	}


	public void setPosizioneOrganizzativa(String posizioneOrganizzativa) {
		this.posizioneOrganizzativa = posizioneOrganizzativa;
	}


	public String getLivelloAttribuzioniI() {
		return livelloAttribuzioniI;
	}


	public void setLivelloAttribuzioniI(String livelloAttribuzioniI) {
		this.livelloAttribuzioniI = livelloAttribuzioniI;
	}


	public String getLivelloAttribuzioniII() {
		return livelloAttribuzioniII;
	}


	public void setLivelloAttribuzioniII(String livelloAttribuzioniII) {
		this.livelloAttribuzioniII = livelloAttribuzioniII;
	}


	public List<PosizioneOrganizzativaView> getListaPosizioneOrganizzativa() {
		return listaPosizioneOrganizzativa;
	}


	public void setListaPosizioneOrganizzativa(List<PosizioneOrganizzativaView> listaPosizioneOrganizzativa) {
		this.listaPosizioneOrganizzativa = listaPosizioneOrganizzativa;
	}


	public List<LivelloAttribuzioniIView> getListaLivelloAttribuzioniI() {
		return listaLivelloAttribuzioniI;
	}


	public void setListaLivelloAttribuzioniI(List<LivelloAttribuzioniIView> listaLivelloAttribuzioniI) {
		this.listaLivelloAttribuzioniI = listaLivelloAttribuzioniI;
	}


	public List<LivelloAttribuzioniIIView> getListaLivelloAttribuzioniII() {
		return listaLivelloAttribuzioniII;
	}


	public void setListaLivelloAttribuzioniII(List<LivelloAttribuzioniIIView> listaLivelloAttribuzioniII) {
		this.listaLivelloAttribuzioniII = listaLivelloAttribuzioniII;
	}



	
}
