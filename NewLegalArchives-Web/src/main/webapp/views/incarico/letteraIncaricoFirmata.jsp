<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>

<form:form name="incaricoForm" modelAttribute="incaricoView">
 <engsecurity:token regenerate="false"/>
 	<jsp:include page="/subviews/incarico/letteraIncaricoFirmata.jsp"> 	</jsp:include>		
</form:form>