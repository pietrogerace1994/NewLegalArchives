package eng.la.model.filter;

import java.util.Date;

public class ProcureFilter {

	private long id;
	private Date dataConferimento;
	private Date dataRevoca;
	private Date dataCreazione;
	private Long tipologia;
	private String nomeProcuratore;
	private String numeroRepertorio;
	private Long idSocieta;
	private Long idNotaio;
	private String order;
	private Date dataConferimentoDal;
	private Date dataConferimentoAl;
	private Date dataRevocaDal;
	private Date dataRevocaAl;
	private String utente;
	private Long posizioneOrganizzativa;
	private Long livelloAttribuzioniI;
	private Long livelloAttribuzioniII;
	
	
	
	private int numElementiPerPagina;
	private int numeroPagina;	
	
	
	public ProcureFilter() {
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public Date getDataConferimento() {
		return dataConferimento;
	}


	public void setDataConferimento(Date dataConferimento) {
		this.dataConferimento = dataConferimento;
	}


	public Date getDataRevoca() {
		return dataRevoca;
	}


	public void setDataRevoca(Date dataRevoca) {
		this.dataRevoca = dataRevoca;
	}


	public Date getDataCreazione() {
		return dataCreazione;
	}


	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}


	public Long getTipologia() {
		return tipologia;
	}


	public void setTipologia(Long tipologia) {
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


	public String getOrder() {
		return order;
	}
	
	public void setOrder(String order) {
		this.order = order;
	}

	public Date getDataConferimentoDal() {
		return dataConferimentoDal;
	}


	public Date getDataConferimentoAl() {
		return dataConferimentoAl;
	}
	
	public void setDataConferimentoDal(Date dataConferimentoDal) {
		this.dataConferimentoDal = dataConferimentoDal;
	}

	public void setDataConferimentoAl(Date dataConferimentoAl) {
		this.dataConferimentoAl = dataConferimentoAl;
	}


	public int getNumElementiPerPagina() {
		return numElementiPerPagina;
	}


	public int getNumeroPagina() {
		return numeroPagina;
	}
	
	public void setNumElementiPerPagina(int numElementiPerPagina) {
		this.numElementiPerPagina = numElementiPerPagina;
	}

	public void setNumeroPagina(int numeroPagina) {
		this.numeroPagina = numeroPagina;
	}


	public Date getDataRevocaDal() {
		return dataRevocaDal;
	}


	public void setDataRevocaDal(Date dataRevocaDal) {
		this.dataRevocaDal = dataRevocaDal;
	}


	public Date getDataRevocaAl() {
		return dataRevocaAl;
	}


	public void setDataRevocaAl(Date dataRevocaAl) {
		this.dataRevocaAl = dataRevocaAl;
	}


	public String getUtente() {
		return utente;
	}


	public void setUtente(String utente) {
		this.utente = utente;
	}


	public Long getPosizioneOrganizzativa() {
		return posizioneOrganizzativa;
	}


	public void setPosizioneOrganizzativa(Long posizioneOrganizzativa) {
		this.posizioneOrganizzativa = posizioneOrganizzativa;
	}


	public Long getLivelloAttribuzioniI() {
		return livelloAttribuzioniI;
	}


	public void setLivelloAttribuzioniI(Long livelloAttribuzioniI) {
		this.livelloAttribuzioniI = livelloAttribuzioniI;
	}


	public Long getLivelloAttribuzioniII() {
		return livelloAttribuzioniII;
	}


	public void setLivelloAttribuzioniII(Long livelloAttribuzioniII) {
		this.livelloAttribuzioniII = livelloAttribuzioniII;
	}


	public void setNumeroRepertorio(String numeroRepertorio) {
		this.numeroRepertorio = numeroRepertorio;
	}
}
