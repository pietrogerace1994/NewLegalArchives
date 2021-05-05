package eng.la.model.view;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import eng.la.model.BeautyContest;
import eng.la.model.BeautyContestReply;
import eng.la.model.ProfessionistaEsterno;

public class BeautyContestReplyView extends BaseView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BeautyContestReply vo;

	public BeautyContestReply getVo() {
		return vo;
	}

	public void setVo(BeautyContestReply vo) {
		this.vo = vo;
	}
	
	/* CAMPI FORM PRINCIPALE SCHEDA_FONDO_RISCHI */
	private Long beautyContestReplyId;
	private String titolo;
	private String dataInvio;
	private String dataChiusura;
	private String statoBeautyContestReply;
	private String statoBeautyContestReplyCode;
	private String descrizioneSow;
	private String descrizione;
	private List<DocumentoView> listaAllegati;
	private Set<String> allegatiDaRimuovereUuid;
	private String[] materie;
	private List<MateriaView> listaMaterie;
	private List<String> listaMaterieAggiunteDesc;
	private String jsonAlberaturaMaterie;
	private BeautyContest beauty;
	private String descrizioneOffertaTecnica;
	private BigDecimal offertaEconomica;
	private DocumentoView offertaTecnicaDoc;
	private DocumentoView offertaEconomicaDoc;
	private Long beautyContestId;
	private ProfessionistaEsterno professionista;
	
	
	
	
	
	

	public ProfessionistaEsterno getProfessionista() {
		return professionista;
	}

	public void setProfessionista(ProfessionistaEsterno professionista) {
		this.professionista = professionista;
	}

	public Long getBeautyContestId() {
		return beautyContestId;
	}

	public void setBeautyContestId(Long beautyContestId) {
		this.beautyContestId = beautyContestId;
	}

	public DocumentoView getOffertaTecnicaDoc() {
		return offertaTecnicaDoc;
	}

	public void setOffertaTecnicaDoc(DocumentoView offertaTecnicaDoc) {
		this.offertaTecnicaDoc = offertaTecnicaDoc;
	}

	public DocumentoView getOffertaEconomicaDoc() {
		return offertaEconomicaDoc;
	}

	public void setOffertaEconomicaDoc(DocumentoView offertaEconomicaDoc) {
		this.offertaEconomicaDoc = offertaEconomicaDoc;
	}

	public String getDescrizioneOffertaTecnica() {
		return descrizioneOffertaTecnica;
	}

	public void setDescrizioneOffertaTecnica(String descrizioneOffertaTecnica) {
		this.descrizioneOffertaTecnica = descrizioneOffertaTecnica;
	}

	public BigDecimal getOffertaEconomica() {
		return offertaEconomica;
	}

	public void setOffertaEconomica(BigDecimal offertaEconomica) {
		this.offertaEconomica = offertaEconomica;
	}

	public BeautyContest getBeauty() {
		return beauty;
	}

	public void setBeauty(BeautyContest beauty) {
		this.beauty = beauty;
	}

	public Long getBeautyContestReplyId() {
		return beautyContestReplyId;
	}

	public void setBeautyContestReplyId(Long beautyContestReplyId) {
		this.beautyContestReplyId = beautyContestReplyId;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getDataInvio() {
		return dataInvio;
	}

	public void setDataInvio(String dataInvio) {
		this.dataInvio = dataInvio;
	}

	public String getDataChiusura() {
		return dataChiusura;
	}

	public void setDataChiusura(String dataChiusura) {
		this.dataChiusura = dataChiusura;
	}

	public String getStatoBeautyContestReply() {
		return statoBeautyContestReply;
	}

	public void setStatoBeautyContestReply(String statoBeautyContestReply) {
		this.statoBeautyContestReply = statoBeautyContestReply;
	}

	public String getStatoBeautyContestReplyCode() {
		return statoBeautyContestReplyCode;
	}

	public void setStatoBeautyContestReplyCode(String statoBeautyContestReplyCode) {
		this.statoBeautyContestReplyCode = statoBeautyContestReplyCode;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public List<DocumentoView> getListaAllegati() {
		return listaAllegati;
	}

	public void setListaAllegati(List<DocumentoView> listaAllegati) {
		this.listaAllegati = listaAllegati;
	}

	public Set<String> getAllegatiDaRimuovereUuid() {
		return allegatiDaRimuovereUuid;
	}

	public void setAllegatiDaRimuovereUuid(Set<String> allegatiDaRimuovereUuid) {
		this.allegatiDaRimuovereUuid = allegatiDaRimuovereUuid;
	}

	public String[] getMaterie() {
		return materie;
	}

	public void setMaterie(String[] materie) {
		this.materie = materie;
	}

	public List<MateriaView> getListaMaterie() {
		return listaMaterie;
	}

	public void setListaMaterie(List<MateriaView> listaMaterie) {
		this.listaMaterie = listaMaterie;
	}

	public List<String> getListaMaterieAggiunteDesc() {
		return listaMaterieAggiunteDesc;
	}

	public void setListaMaterieAggiunteDesc(List<String> listaMaterieAggiunteDesc) {
		this.listaMaterieAggiunteDesc = listaMaterieAggiunteDesc;
	}

	public String getJsonAlberaturaMaterie() {
		return jsonAlberaturaMaterie;
	}

	public void setJsonAlberaturaMaterie(String jsonAlberaturaMaterie) {
		this.jsonAlberaturaMaterie = jsonAlberaturaMaterie;
	}

	public String getDescrizioneSow() {
		return descrizioneSow;
	}

	public void setDescrizioneSow(String descrizioneSow) {
		this.descrizioneSow = descrizioneSow;
	} 
	
	
	
	
	
	
	

}
