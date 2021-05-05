package eng.la.model.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import eng.la.model.Specializzazione;

@SuppressWarnings("all")
public class SpecializzazioneView extends BaseView {

	private static final long serialVersionUID = 1L;
	private Specializzazione vo;

	// DATI PER LA PARTE DI PRESENTATION
	private List<LinguaView> listaLingua;
	private String specializzazioneCode;
	private List<String> specializzazioneDesc; 
	private HashMap specializzazioneHash;
	private boolean insertMode;
	private boolean editMode;
	private boolean deleteMode;
	private boolean flagCode;
	private Long specializzazioneId;
	private List<String> specializzazioneIns;
	
	public SpecializzazioneView() {
		specializzazioneDesc = new ArrayList<String>();
		specializzazioneIns = new ArrayList<String>();
		specializzazioneHash = new HashMap();
	}
	
	public Specializzazione getVo() {
		return vo;
	}

	public void setVo(Specializzazione vo) {
		this.vo = vo;
	}

	public List<LinguaView> getListaLingua() {
		return listaLingua;
	}

	public void setListaLingua(List<LinguaView> listaLingua) {
		this.listaLingua = listaLingua;
	}

	public Long getSpecializzazioneId() {
		return specializzazioneId;
	}

	public void setSpecializzazioneId(Long specializzazioneId) {
		this.specializzazioneId = specializzazioneId;
	}

	public String getSpecializzazioneCode() {
		return specializzazioneCode;
	}

	public void setSpecializzazioneCode(String specializzazioneCode) {
		this.specializzazioneCode = specializzazioneCode;
	}

	public List<String> getSpecializzazioneDesc() {
		return specializzazioneDesc;
	}

	public void setSpecializzazioneDesc(List<String> specializzazioneDesc) {
		this.specializzazioneDesc = specializzazioneDesc;
	}

	public HashMap getSpecializzazioneHash() {
		return specializzazioneHash;
	}

	public void setSpecializzazioneHash(HashMap specializzazioneHash) {
		this.specializzazioneHash = specializzazioneHash;
	}

	public String getSpecDesc(String lang){
		String desc = "";
		
		if(specializzazioneHash.containsKey(lang)){
			desc = (String) specializzazioneHash.get(lang);
		}
		
		return desc;
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

	public List<String> getSpecializzazioneIns() {
		return specializzazioneIns;
	}

	public void setSpecializzazioneIns(List<String> specializzazioneIns) {
		this.specializzazioneIns = specializzazioneIns;
	}

	public boolean isFlagCode() {
		return flagCode;
	}

	public void setFlagCode(boolean flagCode) {
		this.flagCode = flagCode;
	}
	
}
