package eng.la.util.va.xss;
//engsecurity VA

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class ValidationFilter implements Filter {

	public ValidationFilter() {
	}

	public void init(javax.servlet.FilterConfig p1) {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new ValidatingHttpRequest( (HttpServletRequest)request ), response);
	}
}