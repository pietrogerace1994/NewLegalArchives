package eng.la.presentation.servlet.filter;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.security.auth.Subject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import eng.la.business.UtenteService;
import eng.la.model.AbstractEntity;
import eng.la.model.view.UtenteView;
import eng.la.persistence.CostantiDAO;
import eng.la.persistence.audit.AuditInterceptor;
import eng.la.util.CurrentSessionUtil;
import eng.la.util.SpringUtil;
import eng.la.util.costants.Costanti;
import weblogic.security.Security;
import weblogic.security.spi.WLSGroup;
import weblogic.security.spi.WLSUser;

public class SSOAuthenticationFilter implements Filter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SSOAuthenticationFilter.class); 

	private static final String REMOTE_USER_PARAM_NAME = "REMOTE_USER";
	private static final String REMOTE_USER_GROUPS_PARAM_NAME = "REMOTE_USER_GROUPS";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		
		
		if (logger.isDebugEnabled()) {
			logger.debug("doFilter(ServletRequest, ServletResponse, FilterChain) - start"); //$NON-NLS-1$
		} 
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		
		if( req.getRequestURL().toString().endsWith("LALogout") ){
			/**
			 * Resetto i cookie della richiesta e li inserisco nella risposta
			 * @author MASSIMO CARUSO
			 */
			if(req.getCookies() != null){
				for(Cookie c : req.getCookies()){
					c.setMaxAge(0);
					resp.addCookie(c);
				}
			}
			
			resp.sendRedirect(req.getContextPath());
			logger.info("Effettuo il logout..."); //$NON-NLS-1$
			AuditInterceptor auditInterceptor = (AuditInterceptor) SpringUtil.getBean("auditInterceptor"); 
			UtenteView utenteView = (UtenteView) req.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			utenteView.getVo().setOperation(AbstractEntity.LOGOUT_OPERATION);
			utenteView.getVo().setOperationTimestamp(new Date());			
			auditInterceptor.auditLoginLogout( utenteView.getVo() );
			
			/**
			 * Rimuovo tutti gli attributi di sessione
			 * @author MASSIMO CARUSO
			 */
			HttpSession sessione = req.getSession();
			Enumeration<String> attribute_names = sessione.getAttributeNames();
			sessione.removeAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO);
			// rimuovo tutti gli attributi della sessione
			while(attribute_names.hasMoreElements()){
				sessione.removeAttribute(attribute_names.nextElement());
			}
			
			return;
		}
		
		if( req.getRequestURL().toString().contains("/websocket") ){
			chain.doFilter(request, response);

			return;
		}
		
		if( req.getRequestURL().toString().contains("/notAut") ){
			chain.doFilter(request, response);

			return;
		} 

		if( req.getRequestURL().toString().contains("/rest/") ){
			chain.doFilter(request, response);

			return;
		} 
		
		//MODIFICA PASQUALE COLETTA *RIDP4KH  --- PER DOWNLOAD DA PRESIDIO NORMATIVO (NEWS-DETTAGLIO.JSP FA PARTIRE IL DOWNLOAD)
		if(req.getRequestURL().toString().contains("/download") && req.getParameter("public") != null ){
			chain.doFilter(request, response);
			
			return;
		}
		
		
		if( req.getRequestURL().toString().contains(req.getContextPath()+"/vendors/")
				||  req.getRequestURL().toString().contains(req.getContextPath()+"/portal/") 
				||  req.getRequestURL().toString().contains(req.getContextPath()+"/portal-news/")
				||  req.getRequestURL().toString().contains(req.getContextPath()+"/ponte/"))  
		{
			chain.doFilter(request, response);
			return; 
		}
		
		
		
		
		if( req.getRequestURL().toString().contains(req.getContextPath()+"/public/") ) 
		{
			StringBuffer remoteUserGroups = new StringBuffer();
			StringBuffer remoteUserId = new StringBuffer(); 
			processaDatiSSO( remoteUserId,  remoteUserGroups);
			if( remoteUserId.length() == 0 ){ 
				remoteUserId.append(req.getHeader(REMOTE_USER_PARAM_NAME) == null ? "" : req.getHeader(REMOTE_USER_PARAM_NAME) );
				req.getSession().setAttribute(Costanti.EXTERNAL_USER_ID, remoteUserId.toString());
			}
			
			if( remoteUserId.length() > 0 ){ 
				req.getSession().setAttribute(Costanti.EXTERNAL_USER_ID, remoteUserId.toString());
			}
			req.getSession().setAttribute(Costanti.RICERCA_PARTE_CORRELATA_ALL, "ON");
			chain.doFilter(request, response);
			return;
		}
		else {
			req.getSession().setAttribute(Costanti.RICERCA_PARTE_CORRELATA_ALL, "OFF");
		}
		
		
		
		if( req.getSession().getAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO) == null ) { 
			logger.info(">cappena>> Avvio autenticazione SSO..."); //$NON-NLS-1$
			StringBuffer remoteUserGroups = new StringBuffer();
			StringBuffer remoteUserId = new StringBuffer();
			
			processaDatiSSO( remoteUserId,  remoteUserGroups);
			 logger.info("Dopo logica SSO - remoteUserId: " + remoteUserId  + ", roles: "+remoteUserGroups.toString()); //$NON-NLS-1$
            if( remoteUserId.length() == 0 && remoteUserGroups.length() == 0 ){ 
            	processaDatiHeader(remoteUserId, remoteUserGroups, req);
            	logger.info("Dopo logica HEADER - remoteUserId: " + remoteUserId + ", roles: "+remoteUserGroups.toString()); //$NON-NLS-1$
            }
            
			try {
				if( remoteUserId != null && remoteUserId.length() > 0
						&& remoteUserGroups != null && remoteUserGroups.length() > 0 ){ 
					
					autenticaUtente( req, resp, remoteUserId, remoteUserGroups);
					return;
				}else{	
					
					//resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Non hai i permessi per accedere al portale");
					resp.sendRedirect(request.getServletContext().getContextPath()+"/notAut.action");
					if (logger.isDebugEnabled()) {
						logger.debug("doFilter(ServletRequest, ServletResponse, FilterChain) - end"); //$NON-NLS-1$
					}
					return;
				}
				
				 
			} catch (Throwable e) {
				
				//resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Non hai i permessi per accedere al portale");
				resp.sendRedirect(request.getServletContext().getContextPath()+"/notAut.action");
				logger.error("doFilter(ServletRequest, ServletResponse, FilterChain)", e); //$NON-NLS-1$

				// TODO Auto-generated catch block
				e.printStackTrace(); 
			}
			
		}

		 
		chain.doFilter(request, response);
		
		if (logger.isDebugEnabled()) {
			logger.debug("doFilter(ServletRequest, ServletResponse, FilterChain) - end"); //$NON-NLS-1$
		}
	}
 

	private void autenticaUtente( HttpServletRequest req, HttpServletResponse resp, StringBuffer remoteUserId, StringBuffer remoteUserGroups) throws Throwable {
		logger.info("Recupero dati utente con userId: " + remoteUserId.toString()); //$NON-NLS-1$
		UtenteService utenteService = (UtenteService) SpringUtil.getBean("utenteService");
		UtenteView utenteView = utenteService.leggiUtenteDaUserId(remoteUserId.toString());
		List <UtenteView> listaCollaboratori = utenteService.leggiCollaboratori(utenteView.getVo().getMatricolaUtil());
		List<String> collaboratoriMatricole = new ArrayList<String>();
		if(listaCollaboratori != null){
			for (UtenteView collaboratore:listaCollaboratori){
				collaboratoriMatricole.add(collaboratore.getVo().getMatricolaUtil());
			}	
		}

		//DARIO ****************************************************************************
		//utenteView.setPrimoRiporto(utenteService.leggiSePrimoRiporto(utenteView));
		
		utenteView = utenteService.settaMatricoleTopManagers(utenteView); 
		
		//***************************************************************************
		
		utenteView.setResponsabileFoglia(utenteService.leggiSeResponsabileFoglia(utenteView));
		utenteView.setLegaleInterno(utenteService.leggiSeLegaleInterno(utenteView));
		utenteView.setResponsabileSenzaCollaboratori(utenteService.leggiSeResponsabileSenzaCollaboratori(utenteView));
		
		CurrentSessionUtil currentSessionUtil = (CurrentSessionUtil) SpringUtil.getBean("currentSessionUtil");
		currentSessionUtil.setClientIp(req.getRemoteAddr());
		currentSessionUtil.setUserId(remoteUserId.toString());
		currentSessionUtil.setNominativo(utenteView.getVo().getNominativoUtil());
		List<String> rolesCode = new ArrayList<String>();
		if( remoteUserGroups != null && remoteUserGroups.length() > 0 ){
			rolesCode = Arrays.asList(remoteUserGroups.toString().split(","));
		} 
		
		currentSessionUtil.setRolesCode(rolesCode);
		if(listaCollaboratori != null)
			currentSessionUtil.setCollMatricole(collaboratoriMatricole);
		else
			currentSessionUtil.setCollMatricole(null);
		
		if(rolesCode!=null){
			for(String role:rolesCode){
				if(role.trim().equalsIgnoreCase(CostantiDAO.AMMINISTRATIVO)){
					utenteView.setAmministrativo(true);
				}
				if(role.trim().equalsIgnoreCase(CostantiDAO.OPERATORE_SEGRETERIA)){
					utenteView.setOperatoreSegreteria(true);
				}
				if(role.trim().equalsIgnoreCase(CostantiDAO.GRUPPO_AMMINISTRATORE)){
					utenteView.setAmministratore(true);
				}
				if(role.trim().equalsIgnoreCase(CostantiDAO.GESTORE_VENDOR)){
					utenteView.setGestoreVendor(true);
				}
			}
		}
		logger.info("Connesso come: " +  currentSessionUtil.getUserId()); //$NON-NLS-1$ 
		System.out.println("Connesso come: " + currentSessionUtil.getUserId());
		req.getSession().setAttribute(Costanti.UTENTE_CONNESSO_NOME_PARAMETRO, utenteView);		
		req.getSession().setAttribute("MSG_BENVENUTO", "TRUE");					
		
		AuditInterceptor auditInterceptor = (AuditInterceptor) SpringUtil.getBean("auditInterceptor");  
		utenteView.getVo().setOperation(AbstractEntity.LOGIN_OPERATION);
		utenteView.getVo().setOperationTimestamp(new Date());			
		auditInterceptor.auditLoginLogout( utenteView.getVo() ); 
		// Francesco Stumpo NUOVO SSO - START
		resp.sendRedirect(req.getContextPath());
		// Francesco Stumpo NUOVO SSO - END
	}

	private void processaDatiHeader(StringBuffer remoteUserId, StringBuffer remoteUserGroups, HttpServletRequest req) {
		remoteUserId.append(req.getHeader(REMOTE_USER_PARAM_NAME) == null ? "" : req.getHeader(REMOTE_USER_PARAM_NAME) );
	    remoteUserGroups.append( req.getHeader(REMOTE_USER_GROUPS_PARAM_NAME) == null ? "" : req.getHeader(REMOTE_USER_GROUPS_PARAM_NAME) );
	    logger.info("processaDatiHeader END - remoteUserId: " + remoteUserId + ", roles: "+remoteUserGroups.toString()); //$NON-NLS-1$
	    
	}

	private void processaDatiSSO(StringBuffer remoteUserId, StringBuffer remoteUserGroups ) {
		logger.info("Recupero current subject"); //$NON-NLS-1$
		Subject subject = Security.getCurrentSubject(); 
		logger.info("Current subject: " + subject); //$NON-NLS-1$
	
        boolean first = true;
        if( subject != null ){
            for(Principal p: subject.getPrincipals()) {
                if(p instanceof WLSGroup) {
                    if(first) {
                    	if( p.getName().trim().startsWith("LEG_ARC_")){
	                        first=false;
	                        remoteUserGroups.append(p.getName().trim());
                    	}
                    } else {
                    	if( p.getName().trim().startsWith("LEG_ARC_")){
	                    	remoteUserGroups.append(",");
	                        remoteUserGroups.append(p.getName().trim());
                    	}
                    }
                } else if (p instanceof WLSUser) {
                	remoteUserId.append( p.getName() );
                }
            }
        }
        
        logger.info("processaDatiSSO END - remoteUserId: " + remoteUserId  + ", roles: "+remoteUserGroups.toString()); //$NON-NLS-1$
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
