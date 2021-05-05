<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>

<form:form name="proformaForm" modelAttribute="proformaView"> 
 <jsp:include page="/subviews/proforma/schedaValutazione.jsp"> 	</jsp:include>		
</form:form>