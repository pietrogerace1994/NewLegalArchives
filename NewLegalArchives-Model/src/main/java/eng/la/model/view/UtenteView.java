package eng.la.model.view;

import org.apache.commons.lang.StringUtils;

import eng.la.model.Utente;

public class UtenteView  extends BaseView {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6089768264379819631L;
	
	private Utente vo;
	private String remoteUserId;
	
	private boolean responsabileFoglia;
	private boolean operatoreSegreteria;
	private boolean legaleInterno;
	private boolean responsabileSenzaCollaboratori;
	private boolean amministratore;
	private boolean isAmministrativo;
	private boolean isGestoreVendor;
	//DARIO**********************************************
	//private boolean primoRiporto;
	private String matricolaDelTopResponsabile;
	private String matricolaDelTopHead;
	//***************************************************
	public Utente getVo() {
		return vo;
	}

	public void setVo(Utente vo) {
		this.vo = vo;
	}
	
	public String getRemoteUserId() {
		return remoteUserId;
	}

	public void setRemoteUserId(String remoteUserId) {
		this.remoteUserId = remoteUserId;
	}

	public String getCodiceDescrizioneUtente() {
		if( vo == null ) return "";
		String codiceDescrizione = StringUtils.EMPTY;
		if( vo.getNominativoUtil() != null)
			codiceDescrizione = vo.getUseridUtil() + " - " + vo.getNominativoUtil();
		else
			codiceDescrizione = vo.getUseridUtil();
		return codiceDescrizione;
	}

	public String getCodiceDescrizioneUnitaLegale() {
		if( vo == null ) return "";
		if( vo.getCodiceUnitaUtil() == null) return"";
		String codiceDescrizione = vo.getCodiceUnitaUtil().substring(5, vo.getCodiceUnitaUtil().length()) + " - " + vo.getDescrUnitaUtil();
		return codiceDescrizione;
	}

	//DARIO*************************************************
//	public boolean isPrimoRiporto() {
//		return primoRiporto;
//	}
//
//	public void setPrimoRiporto(boolean primoRiporto) {
//		this.primoRiporto = primoRiporto;
//	}
	//******************************************************
	
	public boolean isResponsabileFoglia() {
		return responsabileFoglia;
	}

	public void setResponsabileFoglia(boolean responsabileFoglia) {
		this.responsabileFoglia = responsabileFoglia;
	}

	public boolean isOperatoreSegreteria() {
		return operatoreSegreteria;
	}

	public void setOperatoreSegreteria(boolean operatoreSegreteria) {
		this.operatoreSegreteria = operatoreSegreteria;
	}

	public boolean isResponsabile() {
		if( vo == null ) return false;
		String responsabileUtil = vo.getResponsabileUtil();
		if(responsabileUtil == null) return false;
		if(responsabileUtil.equalsIgnoreCase("Y"))
			return true;
		else
			return false;
	}

	public boolean isLegaleInterno() {
		return legaleInterno;
	}

	public void setLegaleInterno(boolean legaleInterno) {
		this.legaleInterno = legaleInterno;
	}

	public boolean isResponsabileSenzaCollaboratori() {
		return responsabileSenzaCollaboratori;
	}

	public void setResponsabileSenzaCollaboratori(boolean responsabileSenzaCollaboratori) {
		this.responsabileSenzaCollaboratori = responsabileSenzaCollaboratori;
	}

	public boolean isAmministratore() {
		return amministratore;
	}

	public void setAmministratore(boolean amministratore) {
		this.amministratore = amministratore;
	}

	public boolean isAmministrativo() {
		return isAmministrativo;
	}

	public void setAmministrativo(boolean isAmministrativo) {
		this.isAmministrativo = isAmministrativo;
	}

	public boolean isGestoreVendor() {
		return isGestoreVendor;
	}

	public void setGestoreVendor(boolean isGestoreVendor) {
		this.isGestoreVendor = isGestoreVendor;
	}

	//DARIO ***************************************************
	public boolean isTopResponsabile() {
		if(getMatricolaDelTopResponsabile()==null) return false;
		if( vo == null ) return false;
		return (vo.getMatricolaUtil().equals(getMatricolaDelTopResponsabile()));
	}

	public boolean isTopHead() {
		if(getMatricolaDelTopHead()==null) return false;
		if( vo == null ) return false;
		return (vo.getMatricolaUtil().equals(getMatricolaDelTopHead()));
	}

	public boolean isPrimoRiporto() {
		if(getMatricolaDelTopResponsabile()==null) return false;
		if( vo == null ) return false;
		
		return (isResponsabile() && vo.getMatricolaRespUtil().equals(getMatricolaDelTopResponsabile()));
	}
	
	public String getMatricolaDelTopResponsabile() {
		return matricolaDelTopResponsabile;
	}

	public void setMatricolaDelTopResponsabile(String matricolaDelTopResponsabile) {
		this.matricolaDelTopResponsabile = matricolaDelTopResponsabile;
	}

	public String getMatricolaDelTopHead() {
		return matricolaDelTopHead;
	}

	public void setMatricolaDelTopHead(String matricolaDelTopHead) {
		this.matricolaDelTopHead = matricolaDelTopHead;
	}
	
	//*********************************************************
	
	
	
//	public boolean isAmministratore(){
//		if( vo == null || vo.getRUtenteGruppos() == null ) return false;
//		Collection<RUtenteGruppo> grupppiUtente = vo.getRUtenteGruppos();
//		
//		for( RUtenteGruppo gruppoUtente : grupppiUtente ){
//			if( gruppoUtente.getGruppoUtente().getCodice().equals("LEG_ARC_AMMINISTRATORE")){
//				return true;
//			}
//		}
//		return false;
//	}
	
}
