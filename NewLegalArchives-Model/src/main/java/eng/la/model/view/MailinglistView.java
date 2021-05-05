package eng.la.model.view;

import java.util.List;

import eng.la.model.Mailinglist;

public class MailinglistView extends BaseView{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Mailinglist vo;
	private Long mailingListId;
	private String nome;
	private String nomeMod;
	private List<CategoriaMailinglistView> listaCategoriaMailingList;
	private List<CategoriaMailinglistView> sottocategorie;
	private String[] nominativiAggiunti;
	private String[] nominativiAggiuntiMod;
	private boolean insertMode;
	private boolean editMode;
	private boolean deleteMode;
	private boolean viewMode;
	private String categoriaCode;
	private String categoriaCodeMod;
	private String sottoCategoriaCodeMod;
	private List<MailinglistView> listaMailingListView;
	private String tabAttiva = "1";
	
	private String sottoCategoriaCode;
	
	
	
	
	
	public String getSottoCategoriaCodeMod() {
		return sottoCategoriaCodeMod;
	}

	public void setSottoCategoriaCodeMod(String sottoCategoriaCodeMod) {
		this.sottoCategoriaCodeMod = sottoCategoriaCodeMod;
	}

	public List<CategoriaMailinglistView> getSottocategorie() {
		return sottocategorie;
	}

	public void setSottocategorie(List<CategoriaMailinglistView> sottocategorie) {
		this.sottocategorie = sottocategorie;
	}

	public String getSottoCategoriaCode() {
		return sottoCategoriaCode;
	}

	public void setSottoCategoriaCode(String sottoCategoriaCode) {
		this.sottoCategoriaCode = sottoCategoriaCode;
	}

	public String getNomeMod() {
		return nomeMod;
	}

	public void setNomeMod(String nomeMod) {
		this.nomeMod = nomeMod;
	}

	public String[] getNominativiAggiuntiMod() {
		return nominativiAggiuntiMod;
	}

	public void setNominativiAggiuntiMod(String[] nominativiAggiuntiMod) {
		this.nominativiAggiuntiMod = nominativiAggiuntiMod;
	}

	public String getCategoriaCodeMod() {
		return categoriaCodeMod;
	}

	public void setCategoriaCodeMod(String categoriaCodeMod) {
		this.categoriaCodeMod = categoriaCodeMod;
	}

	public String getTabAttiva() {
		return tabAttiva;
	}

	public void setTabAttiva(String tabAttiva) {
		this.tabAttiva = tabAttiva;
	}

	public List<MailinglistView> getListaMailingListView() {
		return listaMailingListView;
	}

	public void setListaMailingListView(List<MailinglistView> listaMailingListView) {
		this.listaMailingListView = listaMailingListView;
	}

	public String getCategoriaCode() {
		return categoriaCode;
	}

	public void setCategoriaCode(String categoriaCode) {
		this.categoriaCode = categoriaCode;
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

	public boolean isViewMode() {
		return viewMode;
	}

	public void setViewMode(boolean viewMode) {
		this.viewMode = viewMode;
	}

	public Long getMailingListId() {
		return mailingListId;
	}

	public void setMailingListId(Long mailingListId) {
		this.mailingListId = mailingListId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<CategoriaMailinglistView> getListaCategoriaMailingList() {
		return listaCategoriaMailingList;
	}

	public void setListaCategoriaMailingList(List<CategoriaMailinglistView> listaCategoriaMailingList) {
		this.listaCategoriaMailingList = listaCategoriaMailingList;
	}

	public String[] getNominativiAggiunti() {
		return nominativiAggiunti;
	}

	public void setNominativiAggiunti(String[] nominativiAggiunti) {
		this.nominativiAggiunti = nominativiAggiunti;
	}

	public Mailinglist getVo() {
		return vo;
	}

	public void setVo(Mailinglist vo) {
		this.vo = vo;
	}
	
}
