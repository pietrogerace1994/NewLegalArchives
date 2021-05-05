package eng.la.model.view;

import java.util.List;

import eng.la.model.VendorManagement;

public class VendorManagementView extends BaseView{

	private static final long serialVersionUID = 1L;

	private VendorManagement vo;
	private String jsonArrayIncarichiAutorizzati;
	private String jsonArrayIncarichiAutorizzatiById;
	private String nazioneCode;
	private String specializzazioneCode;
	private String legaleInternoNominativoUtil;
	private boolean nessunaVotazione;
	private List<TabellaSemestriView> listTabelleSemestriView;
	private String statoVendorManagement;
	private String statoVendorManagementCode;
	private List<StatoVendorManagementView> listaStatoVendorManagement;
	
	public VendorManagement getVo() {
		return vo;
	}

	public void setVo(VendorManagement vo) {
		this.vo = vo;
	}

	public String getJsonArrayIncarichiAutorizzati() {
		return jsonArrayIncarichiAutorizzati;
	}

	public void setJsonArrayIncarichiAutorizzati(String jsonArrayIncarichiAutorizzati) {
		this.jsonArrayIncarichiAutorizzati = jsonArrayIncarichiAutorizzati;
	}

	public String getJsonArrayIncarichiAutorizzatiById() {
		return jsonArrayIncarichiAutorizzatiById;
	}

	public void setJsonArrayIncarichiAutorizzatiById(String jsonArrayIncarichiAutorizzatiById) {
		this.jsonArrayIncarichiAutorizzatiById = jsonArrayIncarichiAutorizzatiById;
	}

	public String getNazioneCode() {
		return nazioneCode;
	}

	public void setNazioneCode(String nazioneCode) {
		this.nazioneCode = nazioneCode;
	}

	public String getSpecializzazioneCode() {
		return specializzazioneCode;
	}

	public void setSpecializzazioneCode(String specializzazioneCode) {
		this.specializzazioneCode = specializzazioneCode;
	}

	public boolean isNessunaVotazione() {
		return nessunaVotazione;
	}

	public void setNessunaVotazione(boolean nessunaVotazione) {
		this.nessunaVotazione = nessunaVotazione;
	}

	public String getLegaleInternoNominativoUtil() {
		return legaleInternoNominativoUtil;
	}

	public void setLegaleInternoNominativoUtil(String legaleInternoNominativoUtil) {
		this.legaleInternoNominativoUtil = legaleInternoNominativoUtil;
	}

	public List<TabellaSemestriView> getListTabelleSemestriView() {
		return listTabelleSemestriView;
	}

	public void setListTabelleSemestriView(List<TabellaSemestriView> listTabelleSemestriView) {
		this.listTabelleSemestriView = listTabelleSemestriView;
	}

	public String getStatoVendorManagement() {
		return statoVendorManagement;
	}

	public void setStatoVendorManagement(String statoVendorManagement) {
		this.statoVendorManagement = statoVendorManagement;
	}

	public List<StatoVendorManagementView> getListaStatoVendorManagement() {
		return listaStatoVendorManagement;
	}

	public void setListaStatoVendorManagement(List<StatoVendorManagementView> listaStatoVendorManagement) {
		this.listaStatoVendorManagement = listaStatoVendorManagement;
	}

	public String getStatoVendorManagementCode() {
		return statoVendorManagementCode;
	}

	public void setStatoVendorManagementCode(String statoVendorManagementCode) {
		this.statoVendorManagementCode = statoVendorManagementCode;
	}

	
	
}
