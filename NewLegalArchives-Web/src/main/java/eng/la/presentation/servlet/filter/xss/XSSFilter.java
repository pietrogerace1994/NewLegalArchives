package eng.la.presentation.servlet.filter.xss;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class XSSFilter implements Filter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(XSSFilter.class);
	private static final String START = "on";
	private FilterConfig filterConfig = null;

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		final String startXss = filterConfig.getInitParameter("startXss");
		final String startLog = filterConfig.getInitParameter("startLog");

		logger.info("START-XSS: " + startXss + " START-XSS-LOG: " + startLog);

		if (startXss != null && START.equalsIgnoreCase(startXss.trim())) {

			final String bodyParameter = getParameterValueFilter(servletRequest);

			if (startLog != null && START.equalsIgnoreCase(startLog.trim()))
				logger.info("XSS-FILTER MAP-PARAMETERS: " + bodyParameter);

			String reqURL = "";
			if (req.getRequestURL() != null) {
				reqURL = req.getRequestURL().toString().toLowerCase();
				reqURL = decodeString(reqURL).toLowerCase();
			}

			final String requestContextPath = req.getContextPath();

			String qryStrDecodificata = "", qryStrFiltratoBody = "";

			qryStrDecodificata = bodyParameter.toLowerCase(); // decodeString(bodyParameter).toLowerCase();

			qryStrDecodificata += qryStrFiltratoBody.toLowerCase();// decodeString(qryStrFiltratoBody).toLowerCase();

			if (startLog != null && START.equalsIgnoreCase(startLog.trim()))
				logger.info("XSS-FILTER STR-DECODIFICATA: " + qryStrDecodificata);
			
			if (this.boolXSS(reqURL)) {
				if (reqURL.contains("public/news")) {
					response.sendRedirect(requestContextPath + "/views/x-error/page-news-error.jsp");
					return;
				} else {
					response.sendError(401, "Unauthorized");
					return;
				}
			}

			if (this.boolXSS(qryStrDecodificata)) {
				if (reqURL.contains("public/news")) {
					response.sendRedirect(requestContextPath + "/views/x-error/page-news-error.jsp");
					return;
				} else {
					response.sendError(401, "Unauthorized");
					return;
				}
			}

			if (isCheckXss(qryStrDecodificata)) {
				if (reqURL.contains("public/news")) {
					response.sendRedirect(requestContextPath + "/views/x-error/page-news-error.jsp");
					return;
				} else {
					response.sendError(401, "Unauthorized");
					return;
				}
			}
			
			if(isJs(reqURL)){
				String referer = req.getHeader("referer");
				
				if(referer == null){
					
					response.sendError(404, "PageNotFound");
					return;
				}
				else if(!referer.isEmpty()){
					
					if(!referer.contains(requestContextPath)){
						
						response.sendError(404, "PageNotFound");
						return;
					}
				}
			}

			chain.doFilter(req, servletResponse);

		} else {
			
			chain.doFilter(servletRequest, servletResponse);
		}

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;

	}

	@Override
	public void destroy() {}

	/*private boolean isMultipartContent(HttpServletRequest request) {

		if (!"post".equals(request.getMethod().toLowerCase())) {
			return false;
		}
		String contentType = request.getContentType();
		if (contentType == null) {
			return false;
		}
		if (contentType.toLowerCase().startsWith("multipart")) {
			return true;
		}
		return false;
	}*/

	/*private boolean checkFileName(String s,String startLog) {
		boolean isXss = false;
		if (s == null)
			return isXss;
		
		String[] filesName = s.split("filename=");
		boolean isLog=START.equalsIgnoreCase(startLog.trim());
		if (isLog)
		logger.log(Priority.INFO, "XSS FILE-NAME SIZE : " + filesName.length);
		if (filesName.length > 1) {
			String[] nomeFileDaVerificare = new String[filesName.length];
			for (int i = 1; i < filesName.length; i++) {
				String fname = filesName[i].substring(1, filesName[i].indexOf('"', 1));
				if (isLog)
				logger.log(Priority.INFO, "XSS FILE-NAME : " + fname);
				nomeFileDaVerificare[i - 1] = fname;
			}

			for (String sf : nomeFileDaVerificare) {
				if (sf != null){
					int nPunti= sf.split("\\.").length;
					if (nPunti > 2) {
						isXss = true;
					}
				if (isLog){
				logger.log(Priority.INFO, "XSS FILE-NAME PROCESSATO : " + sf);
				logger.log(Priority.INFO, "XSS FILE-NAME PROCESSATO NPUNTI : " + (nPunti-1));		
				}
				}
			}

		}

		return isXss;

	}*/


	private static Pattern[] patts =

			new Pattern[] { Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
					Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'",
							Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
					Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"",
							Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
					Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
					Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
					Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
					Pattern.compile("expression\\((.*?)\\)",
							Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
					Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
					Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
					Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL) };

	private boolean boolXSS(String value) {
		if (value != null) {
			value = value.replaceAll("\0", "");
			for (int i = 0; i < patts.length; i++) {
				Pattern scriptPattern = patts[i];
				if (scriptPattern.matcher(value).find())
					return true;
			}
		}
		return false;
	}
	
	private boolean isJs(String value) {
		boolean flag = false;
		if (value != null) {
			value = value.replaceAll("\0", "");
			
			if(value.contains("portal/js/controller/")){
				
				if(value.endsWith(".js"))
					flag = true;
			}
		}
		return flag;
	}

	public String decodeString(String URL) {
		if(URL!=null){
			URL=URL.replaceAll(" ", "+");
		String urlString = "a=<script>";
		try {
			urlString = URLDecoder.decode(URL, "UTF-8");
		} catch (UnsupportedEncodingException e) {

			if (!URL.contains("script") && !URL.contains("javascript") && !URL.contains("vbscript")
					&& !URL.contains("expression") && !URL.contains("onload"))
				return URL;
		}

		return urlString;
		}
		return "";
	}

	private String getParameterValueFilter(ServletRequest req) {
		try {
			Map<String, String[]> map = req.getParameterMap();

			StringBuilder builder = new StringBuilder();
			for (String paramName : map.keySet()) {
				String[] paramValues = map.get(paramName);
				builder.append("&");
				builder.append(paramName);
				builder.append("=");
				for (String valueOfParam : paramValues) {
					builder.append(valueOfParam);
				}
			}
			return builder.toString();
		} catch (Exception e) {
			logger.debug("getParameterValueFilter " + e);
			return "";
		}

	}

	private boolean isCheckXss(String v) {
		v = v.replaceAll("&#x09;", "");
		v = v.replaceAll("&#x0a;", "");
		v = v.replaceAll("&#x0d;", "");
		v = v.replaceAll("\0", "");
		if (v.contains(".fromCharCode"))
			return true;
		else if (v.contains("eval("))
			return true;
		else if (v.contains("<script>"))
			return true;
		else if (v.contains("javascript:"))
			return true;
		else if (v.contains(
				"&#0000106&#0000097&#0000118&#0000097&#0000115&#0000099&#0000114&#0000105&#0000112&#0000116&#0000058"))
			return true;
		else if (v.contains("&#106;&#97;&#118;&#97;&#115;&#99;&#114;&#105;&#112;&#116;&#58"))
			return true;
		else if (v.contains("&#97;&#108;&#101;&#114;&#116;&#40;&#39"))
			return true;
		else if (v.contains("&#x6a&#x61&#x76&#x61&#x73&#x63&#x72&#x69&#x70&#x74&#x3a"))
			return true;
		else if (v.contains("&#x61&#x6C&#x65&#x72&#x74&#x28"))
			return true;
		else if (v.contains("&#x73&#x63&#x72&#x69&#x70&#x74"))
			return true;
		else if (v.contains("expression("))
			return true;
		else if (v.contains("document.cookie"))
			return true;
		else if (v.contains(".cookie"))
			return true;
		else if (v.contains("&#x61&#x6C&#x65&#x72&#x74&#x28"))
			return true;
		else if (v.contains("&#x73&#x63&#x72&#x69&#x70&#x74"))
			return true;
		else if (v.contains("&#0000115&#0000099&#0000114&#0000105&#0000112&#0000116"))
			return true;
		else if (v.contains("&#115;&#99;&#114;&#105;&#112;&#116"))
			return true;
		else if (v.contains("msgbox("))
			return true;
		else if (v.contains("vbscript:"))
			return true;
		else if (v.contains("livescript:"))
			return true;
		else if (v.contains("document.domain"))
			return true;
		else if (v.contains(".domain"))
			return true;
		else if (v.contains("onload="))
			return true;
		else if (v.contains("function(){"))
			return true;
		else if (v.contains("�script�"))
			return true;
		else
			return false;

	}

}
