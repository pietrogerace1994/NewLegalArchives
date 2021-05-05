package eng.la.model.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import eng.la.model.Nazione;

@SuppressWarnings("all")
public class NazioneView extends BaseView {

	private static final long serialVersionUID = 1L;
	private Nazione vo;

	// DATI PER LA PARTE DI PRESENTATION
	private List<LinguaView> listaLingua;
	private String nazioneCode;
	private List<String> nazioneDesc; 
	private HashMap nazioneHash;
	private boolean soloParteCorrelata;
	private boolean insertMode;
	private boolean editMode;
	private boolean deleteMode;
	private boolean flagCode;
	private Long nazioneId;
	private boolean soloParteCorrelataIns;
	private List<String> nazioneIns;
	
	public NazioneView() {
		nazioneDesc = new ArrayList<String>();
		nazioneIns = new ArrayList<String>();
		nazioneHash = new HashMap();
		soloParteCorrelata = false;
		soloParteCorrelataIns = false;
	}
	
	public Nazione getVo() {
		return vo;
	}

	public void setVo(Nazione vo) {
		this.vo = vo;
	}

	public List<LinguaView> getListaLingua() {
		return listaLingua;
	}

	public void setListaLingua(List<LinguaView> listaLingua) {
		this.listaLingua = listaLingua;
	}

	public Long getNazioneId() {
		return nazioneId;
	}

	public void setNazioneId(Long nazioneId) {
		this.nazioneId = nazioneId;
	}

	public String getNazioneCode() {
		return nazioneCode;
	}

	public void setNazioneCode(String nazioneCode) {
		this.nazioneCode = nazioneCode;
	}

	public List<String> getNazioneDesc() {
		return nazioneDesc;
	}

	public void setNazioneDesc(List<String> nazioneDesc) {
		this.nazioneDesc = nazioneDesc;
	}

	public HashMap getNazioneHash() {
		return nazioneHash;
	}

	public void setNazioneHash(HashMap nazioneHash) {
		this.nazioneHash = nazioneHash;
	}

	public String getNazDesc(String lang){
		String desc = "";
		
		if(nazioneHash.containsKey(lang)){
			desc = (String) nazioneHash.get(lang);
		}
		
		return desc;
	}

	public boolean isSoloParteCorrelata() {
		return soloParteCorrelata;
	}

	public void setSoloParteCorrelata(boolean soloParteCorrelata) {
		this.soloParteCorrelata = soloParteCorrelata;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public boolean isDeleteMode() {
		return deleteMode;
	}

	public void setDeleteMode(boolean deleteMode) {
		this.deleteMode = deleteMode;
	}

	public boolean isInsertMode() {
		return insertMode;
	}

	public void setInsertMode(boolean insertMode) {
		this.insertMode = insertMode;
	}

	public boolean isSoloParteCorrelataIns() {
		return soloParteCorrelataIns;
	}

	public void setSoloParteCorrelataIns(boolean soloParteCorrelataIns) {
		this.soloParteCorrelataIns = soloParteCorrelataIns;
	}

	public List<String> getNazioneIns() {
		return nazioneIns;
	}

	public void setNazioneIns(List<String> nazioneIns) {
		this.nazioneIns = nazioneIns;
	}

	public boolean isFlagCode() {
		return flagCode;
	}

	public void setFlagCode(boolean flagCode) {
		this.flagCode = flagCode;
	}
	
}
