package eng.la.model.view;

import java.util.List;

import eng.la.model.Materia;

public class MateriaView extends BaseView{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Materia vo; 
	
	private boolean insertMode;
	private boolean editMode;
	private boolean deleteMode;
	private String localeStr;
	private String jsonAlberaturaMaterie; 
	
	private List<MateriaView> listaMateria;
	private List<LinguaView> listaLingua;
	private List<SettoreGiuridicoView> listaSettoreGiuridico;
	private List<TipologiaFascicoloView> listaTipologiaFascicolo;
	
	private String codMateria;
	private List<String> nome;
	private String sottoMateriaNome;
	private Long idSottoMateria;
	private String lingua;
	private Long   settoreGiuridicoId;
	private String settoreGiuridicoNome;
	private String settoreGiuridicoCodGruppoLingua;
	private Long tipologiaFascicoloId;
	private String tipologiaFascicoloNome;
	private String materiaCodGruppoLingua;
	private List<Long> materiaNomeId;
	private List<Long> materiaIdPadre;
	
	private List<String> nomeIns;
	private Long idSottoMateriaIns;
	private String sottoMateriaNomeIns;
	private String linguaIns;
	private String materiaPadreCodGruppoLinguaIns;
	private Long tipologiaFascicoloIdIns;
	private Long settoreGiuridicoIdIns;
	

	public Materia getVo() {
		return vo;
	}

	public void setVo(Materia vo) {
		this.vo = vo;
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

		public List<MateriaView> getListaMateria() {
		return listaMateria;
	}

	public void setListaMateria(List<MateriaView> listaMateria) {
		this.listaMateria = listaMateria;
	}

	public String getSottoMateriaNome() {
		return sottoMateriaNome;
	}

	public void setSottoMateriaNome(String sottoMateriaNome) {
		this.sottoMateriaNome = sottoMateriaNome;
	}

	public String getSottoMateriaNomeIns() {
		return sottoMateriaNomeIns;
	}

	public void setSottoMateriaNomeIns(String sottoMateriaNomeIns) {
		this.sottoMateriaNomeIns = sottoMateriaNomeIns;
	}

	public List<LinguaView> getListaLingua() {
		return listaLingua;
	}

	public void setListaLingua(List<LinguaView> listaLingua) {
		this.listaLingua = listaLingua;
	}

	public String getLingua() {
		return lingua;
	}

	public void setLingua(String lingua) {
		this.lingua = lingua;
	}

	public String getLinguaIns() {
		return linguaIns;
	}

	public void setLinguaIns(String linguaIns) {
		this.linguaIns = linguaIns;
	}

	public Long getIdSottoMateriaIns() {
		return idSottoMateriaIns;
	}

	public void setIdSottoMateriaIns(Long idSottoMateriaIns) {
		this.idSottoMateriaIns = idSottoMateriaIns;
	}

	public Long getIdSottoMateria() {
		return idSottoMateria;
	}

	public void setIdSottoMateria(Long idSottoMateria) {
		this.idSottoMateria = idSottoMateria;
	}

	public String getLocaleStr() {
		return localeStr;
	}

	public void setLocaleStr(String localeStr) {
		this.localeStr = localeStr;
	}

	public String getCodMateria() {
		return codMateria;
	}

	public void setCodMateria(String codMateria) {
		this.codMateria = codMateria;
	}

	public String getJsonAlberaturaMaterie() {
		return jsonAlberaturaMaterie;
	}

	public void setJsonAlberaturaMaterie(String jsonAlberaturaMaterie) {
		this.jsonAlberaturaMaterie = jsonAlberaturaMaterie;
	}

	public List<SettoreGiuridicoView> getListaSettoreGiuridico() {
		return listaSettoreGiuridico;
	}

	public void setListaSettoreGiuridico(List<SettoreGiuridicoView> listaSettoreGiuridico) {
		this.listaSettoreGiuridico = listaSettoreGiuridico;
	}

	public Long getSettoreGiuridicoId() {
		return settoreGiuridicoId;
	}

	public void setSettoreGiuridicoId(Long settoreGiuridicoId) {
		this.settoreGiuridicoId = settoreGiuridicoId;
	}

	public String getSettoreGiuridicoNome() {
		return settoreGiuridicoNome;
	}

	public void setSettoreGiuridicoNome(String settoreGiuridicoNome) {
		this.settoreGiuridicoNome = settoreGiuridicoNome;
	}

	public List<TipologiaFascicoloView> getListaTipologiaFascicolo() {
		return listaTipologiaFascicolo;
	}

	public void setListaTipologiaFascicolo(List<TipologiaFascicoloView> listaTipologiaFascicolo) {
		this.listaTipologiaFascicolo = listaTipologiaFascicolo;
	}

	public Long getTipologiaFascicoloId() {
		return tipologiaFascicoloId;
	}

	public void setTipologiaFascicoloId(Long tipologiaFascicoloId) {
		this.tipologiaFascicoloId = tipologiaFascicoloId;
	}

	public String getTipologiaFascicoloNome() {
		return tipologiaFascicoloNome;
	}

	public void setTipologiaFascicoloNome(String tipologiaFascicoloNome) {
		this.tipologiaFascicoloNome = tipologiaFascicoloNome;
	}

	public String getMateriaPadreCodGruppoLinguaIns() {
		return materiaPadreCodGruppoLinguaIns;
	}

	public void setMateriaPadreCodGruppoLinguaIns(String materiaPadreCodGruppoLinguaIns) {
		this.materiaPadreCodGruppoLinguaIns = materiaPadreCodGruppoLinguaIns;
	}

	public List<String> getNomeIns() {
		return nomeIns;
	}

	public void setNomeIns(List<String> nomeIns) {
		this.nomeIns = nomeIns;
	}

	public String getSettoreGiuridicoCodGruppoLingua() {
		return settoreGiuridicoCodGruppoLingua;
	}

	public void setSettoreGiuridicoCodGruppoLingua(String settoreGiuridicoCodGruppoLingua) {
		this.settoreGiuridicoCodGruppoLingua = settoreGiuridicoCodGruppoLingua;
	}

	public List<String> getNome() {
		return nome;
	}

	public void setNome(List<String> nome) {
		this.nome = nome;
	}

	public String getMateriaCodGruppoLingua() {
		return materiaCodGruppoLingua;
	}

	public void setMateriaCodGruppoLingua(String materiaCodGruppoLingua) {
		this.materiaCodGruppoLingua = materiaCodGruppoLingua;
	}

	public Long getTipologiaFascicoloIdIns() {
		return tipologiaFascicoloIdIns;
	}

	public void setTipologiaFascicoloIdIns(Long tipologiaFascicoloIdIns) {
		this.tipologiaFascicoloIdIns = tipologiaFascicoloIdIns;
	}

	public Long getSettoreGiuridicoIdIns() {
		return settoreGiuridicoIdIns;
	}

	public void setSettoreGiuridicoIdIns(Long settoreGiuridicoIdIns) {
		this.settoreGiuridicoIdIns = settoreGiuridicoIdIns;
	}

	public List<Long> getMateriaNomeId() {
		return materiaNomeId;
	}

	public void setMateriaNomeId(List<Long> materiaNomeId) {
		this.materiaNomeId = materiaNomeId;
	}

	public List<Long> getMateriaIdPadre() {
		return materiaIdPadre;
	}

	public void setMateriaIdPadre(List<Long> materiaIdPadre) {
		this.materiaIdPadre = materiaIdPadre;
	}

	
}
