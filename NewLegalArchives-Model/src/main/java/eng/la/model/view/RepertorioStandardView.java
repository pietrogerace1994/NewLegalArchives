package eng.la.model.view;

import java.util.Date;
import java.util.List;

import eng.la.model.RepertorioStandard;
import eng.la.model.rest.CodiceDescrizioneBean;

public class RepertorioStandardView extends BaseView {

	private static final long serialVersionUID = 1L;
	
	private RepertorioStandard vo;
	
	private long id;
	private String nome;
	private String nota;
	private Date dataCancellazione;
	private Date dataCreazione;
	private Date dataModifica;
	private String lingua;
	private String codGruppoLingua;
	private Long idUtente;
	private Long idSocieta;
	private Long idPrimoLivelloAttribuzioni;
	private Long idSecondoLivelloAttribuzioni;
	private Long idPosizioneOrganizzativa;
	private List<String> lstSocieta;
	private String societaSelezionata;

	

	private List<CodiceDescrizioneBean> listaPrimoLivelloAttribuzioni;
	private List<CodiceDescrizioneBean> listaSecondoLivelloAttribuzioni;
	private List<CodiceDescrizioneBean> listaPosizioneOrganizzativa;

	
	private Long repertorioStandardId;
	


	public List<DocumentoView> allegato;
	public String  allegatoDaRimuovereUuid;


	public List<String> getLstSocieta() {
		return lstSocieta;
	}


	public void setLstSocieta(List<String> lstSocieta) {
		this.lstSocieta = lstSocieta;
	}


	public String getSocietaSelezionata() {
		return societaSelezionata;
	}


	public void setSocietaSelezionata(String societaSelezionata) {
		this.societaSelezionata = societaSelezionata;
	}


	public RepertorioStandard getVo() {
		return vo;
	}


	public void setVo(RepertorioStandard vo) {
		this.vo = vo;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
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


	public Date getDataCancellazione() {
		return dataCancellazione;
	}


	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
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


	public String getCodGruppoLingua() {
		return codGruppoLingua;
	}


	public void setCodGruppoLingua(String codGruppoLingua) {
		this.codGruppoLingua = codGruppoLingua;
	}


	public Long getIdUtente() {
		return idUtente;
	}


	public void setIdUtente(Long idUtente) {
		this.idUtente = idUtente;
	}


	public Long getIdSocieta() {
		return idSocieta;
	}


	public void setIdSocieta(Long idSocieta) {
		this.idSocieta = idSocieta;
	}


	public Long getIdPrimoLivelloAttribuzioni() {
		return idPrimoLivelloAttribuzioni;
	}


	public void setIdPrimoLivelloAttribuzioni(Long idPrimoLivelloAttribuzioni) {
		this.idPrimoLivelloAttribuzioni = idPrimoLivelloAttribuzioni;
	}


	public Long getIdSecondoLivelloAttribuzioni() {
		return idSecondoLivelloAttribuzioni;
	}


	public void setIdSecondoLivelloAttribuzioni(Long idSecondoLivelloAttribuzioni) {
		this.idSecondoLivelloAttribuzioni = idSecondoLivelloAttribuzioni;
	}


	public Long getIdPosizioneOrganizzativa() {
		return idPosizioneOrganizzativa;
	}


	public void setIdPosizioneOrganizzativa(Long idPosizioneOrganizzativa) {
		this.idPosizioneOrganizzativa = idPosizioneOrganizzativa;
	}


	public List<CodiceDescrizioneBean> getListaPrimoLivelloAttribuzioni() {
		return listaPrimoLivelloAttribuzioni;
	}


	public void setListaPrimoLivelloAttribuzioni(List<CodiceDescrizioneBean> listaPrimoLivelloAttribuzioni) {
		this.listaPrimoLivelloAttribuzioni = listaPrimoLivelloAttribuzioni;
	}


	public List<CodiceDescrizioneBean> getListaSecondoLivelloAttribuzioni() {
		return listaSecondoLivelloAttribuzioni;
	}


	public void setListaSecondoLivelloAttribuzioni(List<CodiceDescrizioneBean> listaSecondoLivelloAttribuzioni) {
		this.listaSecondoLivelloAttribuzioni = listaSecondoLivelloAttribuzioni;
	}


	public List<CodiceDescrizioneBean> getListaPosizioneOrganizzativa() {
		return listaPosizioneOrganizzativa;
	}


	public void setListaPosizioneOrganizzativa(List<CodiceDescrizioneBean> listaPosizioneOrganizzativa) {
		this.listaPosizioneOrganizzativa = listaPosizioneOrganizzativa;
	}


	public Long getRepertorioStandardId() {
		return repertorioStandardId;
	}


	public void setRepertorioStandardId(Long repertorioStandardId) {
		this.repertorioStandardId = repertorioStandardId;
	}


	public List<DocumentoView> getAllegato() {
		return allegato;
	}


	public void setAllegato(List<DocumentoView> allegato) {
		this.allegato = allegato;
	}


	public String getAllegatoDaRimuovereUuid() {
		return allegatoDaRimuovereUuid;
	}


	public void setAllegatoDaRimuovereUuid(String allegatoDaRimuovereUuid) {
		this.allegatoDaRimuovereUuid = allegatoDaRimuovereUuid;
	}
}
