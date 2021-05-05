package eng.la.model.view;

import java.util.Date;
import java.util.List;

import eng.la.model.RepertorioPoteri;
import eng.la.model.rest.CodiceDescrizioneBean;

public class RepertorioPoteriView extends BaseView {

	private static final long serialVersionUID = 1L;
	
	private RepertorioPoteri vo;
	
	private long id;
	private String codice;
	private String descrizione;
	private String testo;
	private Date dataCancellazione;
	private String lingua;
	private String codGruppoLingua;
	private Long idCategoria;
	private Long idSubcategoria;
	

	private List<CodiceDescrizioneBean> listaCategorie;

	private List<CodiceDescrizioneBean> listaSubCategorie;
	
	private Long repertorioPoteriId;

	public RepertorioPoteri getVo() {
		return vo;
	}

	public void setVo(RepertorioPoteri vo) {
		this.vo = vo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public Date getDataCancellazione() {
		return dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public String getLingua() {
		return lingua;
	}

	public void setLingua(String lingua) {
		this.lingua = lingua;
	}

	public String getCodGruppoLingua() {
		return codGruppoLingua;
	}

	public void setCodGruppoLingua(String codGruppoLingua) {
		this.codGruppoLingua = codGruppoLingua;
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public Long getIdSubcategoria() {
		return idSubcategoria;
	}

	public void setIdSubcategoria(Long idSubcategoria) {
		this.idSubcategoria = idSubcategoria;
	}

	public Long getRepertorioPoteriId() {
		return repertorioPoteriId;
	}

	public void setRepertorioPoteriId(Long repertorioPoteriId) {
		this.repertorioPoteriId = repertorioPoteriId;
	}

	public List<CodiceDescrizioneBean> getListaCategorie() {
		return listaCategorie;
	}

	public void setListaCategorie(List<CodiceDescrizioneBean> listaCategorie) {
		this.listaCategorie = listaCategorie;
	}

	public List<CodiceDescrizioneBean> getListaSubCategorie() {
		return listaSubCategorie;
	}

	public void setListaSubCategorie(List<CodiceDescrizioneBean> listaSubCategorie) {
		this.listaSubCategorie = listaSubCategorie;
	}

	
	
	


	
}
