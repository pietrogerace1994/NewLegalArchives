package eng.la.model.filter;

import java.io.Serializable;
import java.util.Date;

public class ProgettoFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1215339120144826720L;
	private long id;
	private Boolean dataCancellazioneIsNull;
	private Date dataChiusuraDal;
	private Date dataChiusuraAl;
	private Date dataCreazioneDal;
	private Date dataCreazioneAl;
	private String descrizione;
	private String oggetto;
	private String nome;
	private String order;
	private int numElementiPerPagina;
	private int numeroPagina;
	
	public ProgettoFilter() {
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public Boolean getDataCancellazioneIsNull() {
		return dataCancellazioneIsNull;
	}


	public void setDataCancellazioneIsNull(Boolean dataCancellazioneIsNull) {
		this.dataCancellazioneIsNull = dataCancellazioneIsNull;
	}


	public Date getDataChiusuraDal() {
		return dataChiusuraDal;
	}


	public void setDataChiusuraDal(Date dataChiusuraDal) {
		this.dataChiusuraDal = dataChiusuraDal;
	}


	public Date getDataChiusuraAl() {
		return dataChiusuraAl;
	}


	public void setDataChiusuraAl(Date dataChiusuraAl) {
		this.dataChiusuraAl = dataChiusuraAl;
	}


	public Date getDataCreazioneDal() {
		return dataCreazioneDal;
	}


	public void setDataCreazioneDal(Date dataCreazioneDal) {
		this.dataCreazioneDal = dataCreazioneDal;
	}


	public Date getDataCreazioneAl() {
		return dataCreazioneAl;
	}


	public void setDataCreazioneAl(Date dataCreazioneAl) {
		this.dataCreazioneAl = dataCreazioneAl;
	}


	public String getDescrizione() {
		return descrizione;
	}


	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


	public String getOggetto() {
		return oggetto;
	}


	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getNumElementiPerPagina() {
		return numElementiPerPagina;
	}

	public void setNumElementiPerPagina(int numElementiPerPagina) {
		this.numElementiPerPagina = numElementiPerPagina;
	}

	public int getNumeroPagina() {
		return numeroPagina;
	}

	public void setNumeroPagina(int numeroPagina) {
		this.numeroPagina = numeroPagina;
	}
	
}