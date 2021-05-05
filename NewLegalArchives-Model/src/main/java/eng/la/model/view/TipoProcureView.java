package eng.la.model.view;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import eng.la.model.TipoProcure;

@SuppressWarnings("all")
public class TipoProcureView extends BaseView {

	private static final long serialVersionUID = 1L;
	private TipoProcure vo;
	
	// Campi Insert
	private long id;
	private String codGruppoLingua;
	private Date dataCancellazione;
	private String descrizione;
	private String lang;
	
	private List<LinguaView> listaLingua;
	private boolean isInsertMode;
	private boolean isDeleteMode;
	private HashMap tipoProcureHash;
	private String tipoProcureCode;
	private boolean editMode;
	private boolean flagCode;
	
	private List<String> tipoProcureIns;
	private List<String> tipoProcureDesc;
	
	private Long tipoProcureId;
	private List<TipoProcureView> listaTipoProcure;
	
	
	
	
	

	public List<TipoProcureView> getListaTipoProcure() {
		return listaTipoProcure;
	}
	public void setListaTipoProcure(List<TipoProcureView> listaTipoProcure) {
		this.listaTipoProcure = listaTipoProcure;
	}
	public Long getTipoProcureId() {
		return tipoProcureId;
	}
	public void setTipoProcureId(Long tipoProcureId) {
		this.tipoProcureId = tipoProcureId;
	}
	public TipoProcure getVo() {
		return vo;
	}
	public void setVo(TipoProcure vo) {
		this.vo = vo;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCodGruppoLingua() {
		return codGruppoLingua;
	}
	public void setCodGruppoLingua(String codGruppoLingua) {
		this.codGruppoLingua = codGruppoLingua;
	}
	public Date getDataCancellazione() {
		return dataCancellazione;
	}
	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public List<LinguaView> getListaLingua() {
		return listaLingua;
	}
	public void setListaLingua(List<LinguaView> listaLingua) {
		this.listaLingua = listaLingua;
	}
	public boolean isInsertMode() {
		return isInsertMode;
	}
	public void setInsertMode(boolean isInsertMode) {
		this.isInsertMode = isInsertMode;
	}
	public boolean isDeleteMode() {
		return isDeleteMode;
	}
	public void setDeleteMode(boolean isDeleteMode) {
		this.isDeleteMode = isDeleteMode;
	}
	public HashMap getTipoProcureHash() {
		return tipoProcureHash;
	}
	public void setTipoProcureHash(HashMap tipoProcureHash) {
		this.tipoProcureHash = tipoProcureHash;
	}
	public String getTipoProcureCode() {
		return tipoProcureCode;
	}
	public void setTipoProcureCode(String tipoProcureCode) {
		this.tipoProcureCode = tipoProcureCode;
	}
	public boolean isEditMode() {
		return editMode;
	}
	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}
	public boolean isFlagCode() {
		return flagCode;
	}
	public void setFlagCode(boolean flagCode) {
		this.flagCode = flagCode;
	}	
	public List<String> getTipoProcureIns() {
		return tipoProcureIns;
	}
	public void setTipoProcureIns(List<String> tipoProcureIns) {
		this.tipoProcureIns = tipoProcureIns;
	}
	public List<String> getTipoProcureDesc() {
		return tipoProcureDesc;
	}
	public void setTipoProcureDesc(List<String> tipoProcureDesc) {
		this.tipoProcureDesc = tipoProcureDesc;
	}
	
}
