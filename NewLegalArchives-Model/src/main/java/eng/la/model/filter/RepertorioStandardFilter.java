package eng.la.model.filter;

import java.util.Date;

public class RepertorioStandardFilter {

	private String nome;
	private String nota;
	private Date dataCreazione;
	private Date dataModifica;
	private String lingua;
	private String societa;
	private Long primoLivelloAttribuzioni;
	private Long secondoLivelloAttribuzioni;
	private Long posizioneOrganizzativa;
	private String order;
	
	private int numElementiPerPagina;
	private int numeroPagina;	
	
	
	public RepertorioStandardFilter() {
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getNota() {
		return nota;
	}


	public void setNota(String nota) {
		this.nota = nota;
	}


	public Date getDataCreazione() {
		return dataCreazione;
	}


	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}


	public Date getDataModifica() {
		return dataModifica;
	}


	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}


	public String getLingua() {
		return lingua;
	}


	public void setLingua(String lingua) {
		this.lingua = lingua;
	}





	public String getSocieta() {
		return societa;
	}


	public void setSocieta(String societa) {
		this.societa = societa;
	}


	public Long getPrimoLivelloAttribuzioni() {
		return primoLivelloAttribuzioni;
	}


	public void setPrimoLivelloAttribuzioni(Long primoLivelloAttribuzioni) {
		this.primoLivelloAttribuzioni = primoLivelloAttribuzioni;
	}


	public Long getSecondoLivelloAttribuzioni() {
		return secondoLivelloAttribuzioni;
	}


	public void setSecondoLivelloAttribuzioni(Long secondoLivelloAttribuzioni) {
		this.secondoLivelloAttribuzioni = secondoLivelloAttribuzioni;
	}


	public Long getPosizioneOrganizzativa() {
		return posizioneOrganizzativa;
	}


	public void setPosizioneOrganizzativa(Long posizioneOrganizzativa) {
		this.posizioneOrganizzativa = posizioneOrganizzativa;
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
