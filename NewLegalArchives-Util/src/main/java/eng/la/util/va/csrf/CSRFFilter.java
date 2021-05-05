package eng.la.util.va.csrf;
//engsecurity VA
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class CSRFFilter implements Filter {

	public CSRFFilter() {
	}

	public void init(javax.servlet.FilterConfig p1) {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) servletRequest;
        
		checkCSRFToken(req);
        removeCSRFToken(req);
		
		filterChain.doFilter(req, servletResponse);
	}
	
    /** Controlla l'esistenza del token CSRF nella request */
    protected void checkCSRFToken(HttpServletRequest request){
    	Logger log = Logger.getLogger("gme.j2ee.web");
        String requestToken = request.getParameter(TokenSecurityTagHandler.TOKEN_NAME);
        String sessionToken = (String)request.getSession().getAttribute(TokenSecurityTagHandler.TOKEN_NAME);
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
    protected void removeCSRFToken(HttpServletRequest request){
    	Logger log = Logger.getLogger("gme.j2ee.web");
        //HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.getSession().removeAttribute(TokenSecurityTagHandler.TOKEN_NAME);
         if (log.isDebugEnabled()){
             log.debug("*** " + this.getClass().getName() + " SECURITY TOKEN TAG REMOVED FROM SESSION  TOKEN: " + (String)request.getSession().getAttribute(TokenSecurityTagHandler.TOKEN_NAME) + "***");
         }
    
    }
	
	
}