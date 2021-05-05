package eng.la.util.va.csrf;
//engsecurity VA

import java.io.IOException;

//import it.enidata.gme.framework.j2ee.event.EventResponse;
import eng.la.util.va.xss.ValidatingMultipartRequest;
import eng.la.util.va.csrf.TokenSecurityTagHandler;
import eng.la.util.va.csrf.HTMLAction;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class HTMLActionSupport implements HTMLAction { 
	
	private static final long serialVersionUID = 453595861182007972L;
	
	protected ServletContext context;
	private static final Logger log = Logger.getLogger(HTMLActionSupport.class);

    public void setServletContext(ServletContext context) {
        this.context = context;
    }

   /* public void doEnd(HttpServletRequest request, EventResponse eventResponse) {
    }*/
    
    
    public void doStart(HttpServletRequest request) {
    }

    /** Controlla l'esistenza del token CSRF nella request */
    public void checkCSRFToken(HttpServletRequest request) {
    	if(request != null && request.getContentType()!= null 
    			&& request.getContentType().indexOf("multipart")>-1){
            int maxUploadSize = 50000000;
            ValidatingMultipartRequest multi = null;
            try {
                multi = new ValidatingMultipartRequest(request, ".", maxUploadSize);
                checkMultiCSRFToken(multi, request);
            } catch (IOException io) {
                log.error("checkCSRFToken - perform(): Error in multipart request: " + io.getMessage());
                io.printStackTrace();
            }
    	} else{
        String requestToken = request.getParameter(TokenSecurityTagHandler.TOKEN_NAME);
        log.debug("requestToken: "+requestToken);
        String sessionToken = (String)request.getSession().getAttribute(TokenSecurityTagHandler.TOKEN_NAME);
        log.debug("sessionToken: "+sessionToken);
        if( null == requestToken || null == sessionToken || !requestToken.equals(sessionToken) ){
            if (log.isDebugEnabled()){
                log.debug("*** " + this.getClass().getName() + " CHECK SECURITY TOKEN TAG: KO ***");
            }
            throw new RuntimeException();
        }
        if (log.isDebugEnabled()){
            log.debug("*** " + this.getClass().getName() + " CHECK SECURITY TOKEN TAG: OK ***");
        }
    }
    }
    
    public boolean checkCSRFTokenBol(HttpServletRequest request) {
    	if(request != null && request.getContentType()!= null 
    			&& request.getContentType().indexOf("multipart")>-1){
            int maxUploadSize = 50000000;
            ValidatingMultipartRequest multi = null;
            try {
                
            	String requestToken = multi.getParameter(TokenSecurityTagHandler.TOKEN_NAME);
                log.debug("requestToken: "+requestToken);
                String sessionToken = (String)request.getSession().getAttribute(TokenSecurityTagHandler.TOKEN_NAME);
                log.debug("sessionToken: "+sessionToken);
                if( null == requestToken || null == sessionToken || !requestToken.equals(sessionToken) ){
                    if (log.isDebugEnabled()){
                        log.debug("*** " + this.getClass().getName() + " CHECK SECURITY TOKEN TAG: KO ***");
                    }
                  return true;
                }
            	
                return false;	
            } catch (Exception io) {
                log.error("checkCSRFToken - perform(): Error in multipart request: " + io.getMessage());
            }
            
    	} else{
        String requestToken = request.getParameter(TokenSecurityTagHandler.TOKEN_NAME);
        log.debug("requestToken: "+requestToken);
        String sessionToken = (String)request.getSession().getAttribute(TokenSecurityTagHandler.TOKEN_NAME);
        log.debug("sessionToken: "+sessionToken);
        if( null == requestToken || null == sessionToken || !requestToken.equals(sessionToken) ){
            if (log.isDebugEnabled()){
                log.debug("*** " + this.getClass().getName() + " CHECK SECURITY TOKEN TAG: KO ***");
            }
            return true;
        }
        if (log.isDebugEnabled()){
            log.debug("*** " + this.getClass().getName() + " CHECK SECURITY TOKEN TAG: OK ***");
        }
        return false;
    }
    	 return false;
    }
    
    /** Controlla l'esistenza del token CSRF nella request */
    public void checkMultiCSRFToken(ValidatingMultipartRequest multi, HttpServletRequest request) {
        String requestToken = multi.getParameter(TokenSecurityTagHandler.TOKEN_NAME);
        log.debug("requestToken: "+requestToken);
        String sessionToken = (String)request.getSession().getAttribute(TokenSecurityTagHandler.TOKEN_NAME);
        log.debug("sessionToken: "+sessionToken);
        if( null == requestToken || null == sessionToken || !requestToken.equals(sessionToken) ){
            if (log.isDebugEnabled()){
                log.debug("*** " + this.getClass().getName() + " CHECK SECURITY TOKEN TAG: KO ***");
            }
            throw new RuntimeException();
        }
        if (log.isDebugEnabled()){
            log.debug("*** " + this.getClass().getName() + " CHECK SECURITY TOKEN TAG: OK ***");
        }
    }
    
    /** Rimuove il token CSRF dalla Sessione Http */
    public void removeCSRFToken(HttpServletRequest request){
        //HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.getSession().removeAttribute(TokenSecurityTagHandler.TOKEN_NAME);
         if (log.isDebugEnabled()){
             log.debug("*** " + this.getClass().getName() + " SECURITY TOKEN TAG REMOVED FROM SESSION  TOKEN: " + (String)request.getSession().getAttribute(TokenSecurityTagHandler.TOKEN_NAME) + "***");
         }   
    }

	@Override
	public void doEnd(HttpServletRequest request, HttpServletResponse eventResponse) {
		// TODO Auto-generated method stub
		
	}
}