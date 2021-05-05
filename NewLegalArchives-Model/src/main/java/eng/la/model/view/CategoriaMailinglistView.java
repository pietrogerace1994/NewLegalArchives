package eng.la.model.view;

import java.util.List;

import eng.la.model.CategoriaMailinglist;

public class CategoriaMailinglistView extends BaseView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CategoriaMailinglist vo;
	
	private Long categoriaId;
	private String nomeCategoria;
	private List<CategoriaMailinglistView> sottoCategorie;
	private List<CategoriaMailinglistStyleView> stileCategorie;
	private boolean insertMode;
	private boolean editMode;
	private boolean deleteMode;
	private boolean flagCode;
	private String tabAttiva = "1";
	private Long categoriaPadre;
	private Long colorselector;
	private Boolean haFigli;
	private Boolean isSottoCategoria;

	public CategoriaMailinglist getVo() {
		return vo;
	}

	public void setVo(CategoriaMailinglist vo) {
		this.vo = vo;
	}
	
	

	
	public Boolean getIsSottoCategoria() {
		return isSottoCategoria;
	}

	public void setIsSottoCategoria(Boolean isSottoCategoria) {
		this.isSottoCategoria = isSottoCategoria;
	}

	/**
	 * @return the haFigli
	 */
	public Boolean getHaFigli() {
		return haFigli;
	}

	/**
	 * @param haFigli the haFigli to set
	 */
	public void setHaFigli(Boolean haFigli) {
		this.haFigli = haFigli;
	}

	public Long getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}

	public Long getColorselector() {
		return colorselector;
	}

	public void setColorselector(Long colorselector) {
		this.colorselector = colorselector;
	}

	public Long getCategoriaPadre() {
		return categoriaPadre;
	}

	public void setCategoriaPadre(Long categoriaPadre) {
		this.categoriaPadre = categoriaPadre;
	}

	public List<CategoriaMailinglistStyleView> getStileCategorie() {
		return stileCategorie;
	}

	public void setStileCategorie(List<CategoriaMailinglistStyleView> stileCategorie) {
		this.stileCategorie = stileCategorie;
	}

	public String getNomeCategoria() {
		return nomeCategoria;
	}

	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}

	

	public List<CategoriaMailinglistView> getSottoCategorie() {
		return sottoCategorie;
	}

	public void setSottoCategorie(List<CategoriaMailinglistView> sottoCategorie) {
		this.sottoCategorie = sottoCategorie;
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

	public String getTabAttiva() {
		return tabAttiva;
	}

	public void setTabAttiva(String tabAttiva) {
		this.tabAttiva = tabAttiva;
	}
	
	

}
