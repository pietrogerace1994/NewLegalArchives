package eng.la.model.view;

import eng.la.model.Rubrica;

public class RubricaView extends BaseView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Rubrica vo;
	private Long rubricaId;
	private String nominativo;
	private String email;
	private boolean insertMode;
	private boolean editMode;
	private boolean deleteMode;
	private boolean flagCode;
	private String tabAttiva = "1";

	
	
	public String getTabAttiva() {
		return tabAttiva;
	}

	public void setTabAttiva(String tabAttiva) {
		this.tabAttiva = tabAttiva;
	}

	public boolean isInsertMode() {
		return insertMode;
	}

	public void setInsertMode(boolean insertMode) {
		this.insertMode = insertMode;
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

	public boolean isFlagCode() {
		return flagCode;
	}

	public void setFlagCode(boolean flagCode) {
		this.flagCode = flagCode;
	}

	public Rubrica getVo() {
		return vo;
	}

	public void setVo(Rubrica vo) {
		this.vo = vo;
	}

	public Long getRubricaId() {
		return rubricaId;
	}

	public void setRubricaId(Long rubricaId) {
		this.rubricaId = rubricaId;
	}

	public String getNominativo() {
		return nominativo;
	}

	public void setNominativo(String nominativo) {
		this.nominativo = nominativo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
