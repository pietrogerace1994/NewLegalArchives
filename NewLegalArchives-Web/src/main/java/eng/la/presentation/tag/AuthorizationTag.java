package eng.la.presentation.tag;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import eng.la.business.AutorizzazioneService;
import eng.la.business.UtenteService;
import eng.la.model.view.UtenteView;
import eng.la.persistence.CostantiDAO;
import eng.la.util.CurrentSessionUtil;
import eng.la.util.SpringUtil;
import eng.la.util.costants.Costanti;

public class AuthorizationTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idEntita;
	private String tipoEntita;
	private String nomeFunzionalita;
	private String tipoPermesso;

	public Long getIdEntita() {
		return idEntita;
	}

	public void setIdEntita(Long idEntita) {
		this.idEntita = idEntita;
	}

	public String getTipoEntita() {
		return tipoEntita;
	}

	public void setTipoEntita(String tipoEntita) {
		this.tipoEntita = tipoEntita;
	}

	public void setNomeFunzionalita(String nomeFunzionalita) {
		this.nomeFunzionalita = nomeFunzionalita;
	}

	public String getNomeFunzionalita() {
		return nomeFunzionalita;
	}

	public String getTipoPermesso() {
		return tipoPermesso;
	}

	public void setTipoPermesso(String tipoPermesso) {
		this.tipoPermesso = tipoPermesso;
	}

	@Override
	public int doStartTag() throws JspException {
		boolean isAuthorizad = false;
		HttpSession session = this.pageContext.getSession();
		UtenteView utenteConnesso = (UtenteView) session.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		if (utenteConnesso != null && currentSessionUtil != null ) {
			if( idEntita != null && idEntita > 0 && StringUtils.isNotBlank(tipoEntita)){
				isAuthorizad = verificaPermessi(utenteConnesso, idEntita, tipoEntita, tipoPermesso);
			}else if( StringUtils.isNotBlank(nomeFunzionalita) ){
				isAuthorizad = verificaAutorizzazioneFunzionalita( currentSessionUtil.getRolesCode(), nomeFunzionalita );
			}
		}

		if (isAuthorizad) {
			return EVAL_BODY_INCLUDE;
		} else {
			return SKIP_BODY;
		}

	}

	private boolean verificaAutorizzazioneFunzionalita(List<String> rolesCode, String nomeFunzionalita) {

		if(rolesCode.contains(CostantiDAO.LEG_ARC_GESTORE_ANAGRAFICA_PROCURE) && !nomeFunzionalita.equals(Costanti.FUNZIONALITA_ARCHIVI)){


			if( nomeFunzionalita.equals(CostantiDAO.NASCONDI_A_LEG_ARC_GESTORE_ANAGRAFICA_PROCURE)){
				return false;
			}
			if( nomeFunzionalita.equals(CostantiDAO.LEG_ARC_GESTORE_ANAGRAFICA_PROCURE) ){
				for( String code : rolesCode){
					if(code.equals( CostantiDAO.LEG_ARC_GESTORE_ANAGRAFICA_PROCURE) || 
							code.equals( CostantiDAO.LEG_ARC_GESTORE_ARCHIVIO_PROCURE)){
						return true;
					}
				} 
			}

			if (nomeFunzionalita.equals(Costanti.REPERTORIO_POTERI_R)
					|| nomeFunzionalita.equals(Costanti.REPERTORIO_POTERI_W)
					|| nomeFunzionalita.equals(Costanti.REPERTORIO_STANDARD_R)
					|| nomeFunzionalita.equals(Costanti.REPERTORIO_STANDARD_W)
					|| nomeFunzionalita.equals(Costanti.TIPO_PROCURE_R) || nomeFunzionalita.equals(Costanti.TIPO_PROCURE_W)
					|| nomeFunzionalita.equals(Costanti.PROCURE_CONFERITE_R)) {
				for (String code : rolesCode) {
					if (code.equals(CostantiDAO.LEG_ARC_GESTORE_ANAGRAFICA_PROCURE)) {
						return true;
					}
				}
			}

			if( nomeFunzionalita.equals(CostantiDAO.LEG_ARC_GESTORE_ARCHIVIO_PROCURE) ){
				for( String code : rolesCode){
					if(code.equals( CostantiDAO.LEG_ARC_GESTORE_ANAGRAFICA_PROCURE) || 
							code.equals( CostantiDAO.LEG_ARC_GESTORE_ARCHIVIO_PROCURE )){
						return true;
					}
				} 
			}

			if (nomeFunzionalita.equals(Costanti.PROCURE_CONFERITE_R)
					|| nomeFunzionalita.equals(Costanti.PROCURE_CONFERITE_W)
					|| nomeFunzionalita.equals(Costanti.REPERTORIO_POTERI_R)
					|| nomeFunzionalita.equals(Costanti.REPERTORIO_STANDARD_R)
					|| nomeFunzionalita.equals(Costanti.TIPO_PROCURE_R)) {
				for (String code : rolesCode) {
					if (code.equals(CostantiDAO.LEG_ARC_GESTORE_ARCHIVIO_PROCURE)) {
						return true;
					}
				}
			}

			return false;
		}

		if( nomeFunzionalita.equals(CostantiDAO.NASCONDI_A_LEG_ARC_GESTORE_ANAGRAFICA_PROCURE)){
			return true;
		}



		if( nomeFunzionalita.equals(Costanti.FUNZIONALITA_FASCICOLI) ||
				nomeFunzionalita.equals(Costanti.FUNZIONALITA_FASCICOLI_CERCA )
				){
			return true;
		}
		if( nomeFunzionalita.equals(Costanti.FUNZIONALITA_FASCICOLI_NUOVO) ){
			for( String code : rolesCode){
				if( code.equals( CostantiDAO.GESTORE_FASCICOLI ) || code.equals( CostantiDAO.GRUPPO_RESPONSABILE )){
					return true;
				}
			}

			return false;
		}
		if( nomeFunzionalita.equals(Costanti.FUNZIONALITA_RESPONSABILE) ){
			for( String code : rolesCode){
				if(code.equals( CostantiDAO.GRUPPO_RESPONSABILE )){
					return true;
				}
			}

			return false;
		}
		if( nomeFunzionalita.equals(Costanti.FUNZIONALITA_ATTI) ||
				nomeFunzionalita.equals(Costanti.FUNZIONALITA_ATTI_CERCA )
				){
			return true;
		}

		if( nomeFunzionalita.equals(Costanti.FUNZIONALITA_ATTI_NUOVO) ){
			for( String code : rolesCode){
				if( code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE ) || code.equals( CostantiDAO.OPERATORE_SEGRETERIA )){
					return true;
				}
			}

			return false;
		}
		
		/**
		 * Aggiunta permessi visualizzazione atti da validare
		 * solo per utente segreteria
		 * @author MASSIMO CARUSO
		 */
		if( nomeFunzionalita.equals(Costanti.FUNZIONALITA_ATTI_VALIDA) ){
			for( String code : rolesCode){
				if( code.equals( CostantiDAO.OPERATORE_SEGRETERIA )){
					return true;
				}
			}

			return false;
		}
		

		if( nomeFunzionalita.equals(Costanti.FUNZIONALITA_INCARICHI) ){
			return true;
		}

		if( nomeFunzionalita.equals(Costanti.FUNZIONALITA_SCHEDE_FONDO_RISCHI) ){
			return true;
		}

		if( nomeFunzionalita.equals(Costanti.FUNZIONALITA_PROFORMA) ){
			return true;
		}
		if( nomeFunzionalita.equals(Costanti.FUNZIONALITA_REPORT) ){
			for( String code : rolesCode){
				if( code.equals( CostantiDAO.OPERATORE_SEGRETERIA ) && rolesCode.size()==1){
					return false;
				}
			} 
			return true;
		}
		if( nomeFunzionalita.equals(Costanti.FUNZIONALITA_ESTRAZIONE_DATI) ){
			return true;
		}
		if( nomeFunzionalita.equals(Costanti.FUNZIONALITA_ARCHIVI) ){
			for( String code : rolesCode){
				if( code.equals( CostantiDAO.GESTORE_ARCHIVIO_DUEDILIGENCE) || code.equals( CostantiDAO.GESTORE_ARCHIVIO_RICHIESTE_AG )
						|| code.equals( CostantiDAO.GESTORE_ARCHIVIO_PC ) || code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE) || 
						code.equals( CostantiDAO.LEG_ARC_GESTORE_ANAGRAFICA_PROCURE) || 
						code.equals( CostantiDAO.LEG_ARC_GESTORE_ARCHIVIO_PROCURE ) || 
						code.equals( CostantiDAO.LEG_ARC_GESTORE_AFFSOC) || 
						code.equals( CostantiDAO.LEG_ARC_AFFSOC)){
					return true;
				}
			} 
			return true;
		}

		if( nomeFunzionalita.equals(Costanti.FUNZIONALITA_ARCHIVI_PARTE_CORRELATA) ){
			for( String code : rolesCode){
				if( code.equals( CostantiDAO.GESTORE_ARCHIVIO_PC ) || code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE )){
					return true;
				}
			} 
		}

		if( nomeFunzionalita.equals(Costanti.FUNZIONALITA_ARCHIVI_DUE_DILIGENCE) ){
			for( String code : rolesCode){
				if(code.equals( CostantiDAO.GESTORE_ARCHIVIO_DUEDILIGENCE) || 
						code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE )){
					return true;
				}
			} 
		}

		if( nomeFunzionalita.equals(Costanti.FUNZIONALITA_ARCHIVI_PROTOCOLLO) ){
			HttpSession session = this.pageContext.getSession();
			UtenteService utenteService = (UtenteService) SpringUtil.getBean("utenteService"); 
			UtenteView utenteConnesso = (UtenteView) session.getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);

			for( String code : rolesCode){
				try {
					if(code.equals( CostantiDAO.OPERATORE_SEGRETERIA) 
							|| code.equals( CostantiDAO.GESTORE_FASCICOLI )
							|| code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE )
							|| utenteConnesso.getVo().getMatricolaUtil().equals(utenteService.leggiResponsabileTop().getVo().getMatricolaUtil())){
						return true;
					}
				} catch (Throwable e) {
					e.printStackTrace();
				}
			} 
		}

		if( nomeFunzionalita.equals(CostantiDAO.LEG_ARC_GESTORE_ANAGRAFICA_PROCURE) ){
			for( String code : rolesCode){
				if(code.equals( CostantiDAO.LEG_ARC_GESTORE_ANAGRAFICA_PROCURE) || 
						code.equals( CostantiDAO.LEG_ARC_GESTORE_ARCHIVIO_PROCURE)
						|| code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE )){
					return true;
				}
			} 
		}

		if (nomeFunzionalita.equals(Costanti.REPERTORIO_POTERI_R)
				|| nomeFunzionalita.equals(Costanti.REPERTORIO_POTERI_W)
				|| nomeFunzionalita.equals(Costanti.REPERTORIO_STANDARD_R)
				|| nomeFunzionalita.equals(Costanti.REPERTORIO_STANDARD_W)
				|| nomeFunzionalita.equals(Costanti.TIPO_PROCURE_R) || nomeFunzionalita.equals(Costanti.TIPO_PROCURE_W)
				|| nomeFunzionalita.equals(Costanti.PROCURE_CONFERITE_R)) {
			for (String code : rolesCode) {
				if (code.equals(CostantiDAO.LEG_ARC_GESTORE_ANAGRAFICA_PROCURE)) {
					return true;
				}
			}
		}

		if( nomeFunzionalita.equals(CostantiDAO.LEG_ARC_GESTORE_ARCHIVIO_PROCURE) ){
			for( String code : rolesCode){
				if(code.equals( CostantiDAO.LEG_ARC_GESTORE_ANAGRAFICA_PROCURE) || 
						code.equals( CostantiDAO.LEG_ARC_GESTORE_ARCHIVIO_PROCURE )){
					return true;
				}
			} 
		}

		if (nomeFunzionalita.equals(Costanti.PROCURE_CONFERITE_R)
				|| nomeFunzionalita.equals(Costanti.PROCURE_CONFERITE_W)
				|| nomeFunzionalita.equals(Costanti.REPERTORIO_POTERI_R)
				|| nomeFunzionalita.equals(Costanti.REPERTORIO_STANDARD_R)
				|| nomeFunzionalita.equals(Costanti.TIPO_PROCURE_R)) {
			for (String code : rolesCode) {
				if (code.equals(CostantiDAO.LEG_ARC_GESTORE_ARCHIVIO_PROCURE)) {
					return true;
				}
			}
		}

		if( nomeFunzionalita.equals(CostantiDAO.LEG_ARC_GESTORE_AFFSOC) ){
			for (String code : rolesCode) {
				if (code.equals(CostantiDAO.LEG_ARC_GESTORE_AFFSOC) || code.equals(CostantiDAO.LEG_ARC_AFFSOC)) {
					return true;
				}
			}
		}

		if (nomeFunzionalita.equals(Costanti.AFFARI_SOCIETARI_R)
				|| nomeFunzionalita.equals(Costanti.AFFARI_SOCIETARI_W)
				|| nomeFunzionalita.equals(Costanti.ORGANO_SOCIALE_R)
				|| nomeFunzionalita.equals(Costanti.ORGANO_SOCIALE_W)) {
			for (String code : rolesCode) {
				if (code.equals(CostantiDAO.LEG_ARC_GESTORE_AFFSOC)) {
					return true;
				}
			}
		}

		if (nomeFunzionalita.equals(Costanti.AFFARI_SOCIETARI_R)
				|| nomeFunzionalita.equals(Costanti.ORGANO_SOCIALE_R)) {
			for (String code : rolesCode) {
				if (code.equals(CostantiDAO.LEG_ARC_AFFSOC)) {
					return true;
				}
			}
		}








		if( nomeFunzionalita.equals(Costanti.FUNZIONALITA_ARCHIVI_AUTORITA_GIUDIZIARIA) ){
			for( String code : rolesCode){
				if(code.equals( CostantiDAO.GESTORE_ARCHIVIO_AUTORITA_GIUDIZIARIA) || 
						code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE )){
					return true;
				}
			} 
		}

		if( nomeFunzionalita.equals(Costanti.FUNZIONALITA_ANAGRAFICA) || nomeFunzionalita.equals(Costanti.FUNZIONALITA_ANAGRAFICA_VISUALIZZA)){
			return true;
		}

		if( nomeFunzionalita.equals(Costanti.FUNZIONALITA_ANAGRAFICA_NUOVO_PROFESSIONISTA)){
			return true;
		}

		if( nomeFunzionalita.equals(Costanti.FUNZIONALITA_ANAGRAFICA_NUOVO) || nomeFunzionalita.equals(Costanti.FUNZIONALITA_ANAGRAFICA_GESTIONE)){
			for( String code : rolesCode){
				if( code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE )){
					return true;
				}
			} 
		}

		if(nomeFunzionalita.equals(Costanti.FUNZIONALITA_ANAGRAFICA_GESTIONE_NAZIONE)){
			for( String code : rolesCode){
				if( code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE )){
					return true;
				}
				else if( code.equals( CostantiDAO.GESTORE_ARCHIVIO_PC )){
					return true;
				}
			} 
		}

		if( nomeFunzionalita.equals(Costanti.FUNZIONALITA_AMMINISTRAZIONE) ){
			for( String code : rolesCode){
				if( code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE )){
					return true;
				}
			} 
		}

		if( nomeFunzionalita.equals(Costanti.FUNZIONALITA_MAILINGLIST) ){
			for( String code : rolesCode){
				if( code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE )){
					return true;
				}
			} 
		}

		if(nomeFunzionalita.equals(Costanti.FUNZIONALITA_ARCHIVI_PRESIDIO_NORMATIVO)){
			for( String code : rolesCode){
				if( code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE )){
					return true;
				}
				else if( code.equals( CostantiDAO.GESTORE_ARCHIVIO_PRESIDIO_NORMATIVO )){
					return true;
				}
			} 
		}

		if( nomeFunzionalita.equals(Costanti.FUNZIONALITA_VENDOR_MANAGEMENT) ){
			for( String code : rolesCode){
				if( code.equals( CostantiDAO.GESTORE_VENDOR )){
					return true;
				}
			} 
		}

		if( nomeFunzionalita.equals(Costanti.FUNZIONALITA_BEAUTY_CONTEST) ){
			for( String code : rolesCode){
				if( code.equals( CostantiDAO.GRUPPO_AMMINISTRATORE)){
					return true;
				}
				if( code.equals( CostantiDAO.GESTORE_FASCICOLI)){
					return true;
				}
			} 
		}

		return false;
	}

	private boolean verificaPermessi(UtenteView utenteConnesso, Long idEntita, String tipoEntita, String tipoPermesso) {
		AutorizzazioneService service = (AutorizzazioneService) SpringUtil.getBean("autorizzazioneService"); 
		boolean isAuthorized = service.isAutorizzato(idEntita, tipoEntita, tipoPermesso); 
		return isAuthorized;
	}

}
