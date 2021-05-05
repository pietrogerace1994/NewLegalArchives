<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>


<div id="notaAggiudicazioneFirmataDiv" class="form-group">
	<label for="notaAggiudicazioneFirmataDesc" class="col-sm-3 control-label"><spring:message
	text="??beautyContest.label.notaAggiudicazioneFirmata??" code="beautyContest.label.notaAggiudicazioneFirmata" /></label>
	
	<div class="col-sm-6">
		
		<c:if test="${ not empty beautyContestView.notaAggiudicazioneFirmataDoc}">
		<input readonly class="form-control" value="${ beautyContestView.notaAggiudicazioneFirmataDoc.nomeFile }"/>
	</c:if>
	
	<c:if test="${ empty beautyContestView.notaAggiudicazioneFirmataDoc }">
		<input readonly class="form-control" value=""/>
	</c:if>
		
	</div>
	<c:if test="${ empty beautyContestView.notaAggiudicazioneFirmataDoc }">
	<div class="col-sm-1">		
		<button type="button" data-toggle="modal" 
				data-target="#panelAggiungiNotaAggiudicazioneFirmata"
				class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
				style="float: left; position: relative !important;background-color: #d9d9d9;">
				<i class="zmdi zmdi-plus icon-mini"></i>
		</button>
	</div>
	</c:if>
	
	<c:if
		test="${ not empty beautyContestView.notaAggiudicazioneFirmataDoc && not empty beautyContestView.notaAggiudicazioneFirmataDoc.uuid }">
	  <div class="col-sm-1">
		<a href="<%=request.getContextPath() %>/download?uuid=${beautyContestView.notaAggiudicazioneFirmataDoc.uuid}&isp=1"
			class="  bg   waves-effect waves-circle waves-float btn-circle-mini"
			style="float: left; position: relative !important;" target="_BLANK">
				<i class="zmdi zmdi-download icon-mini"></i>
		</a>
	
	</div>	
	</c:if>		
	<div class="col-sm-1">
		<c:if test="${ not empty beautyContestView.notaAggiudicazioneFirmataDoc }">
			<button type="button" onclick="rimuoviNotaAggiudicazioneFirmata(${beautyContestView.beautyContestId})"
				class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
				style="float: left; position: relative !important;">
				<i class="fa fa-trash icon-mini"></i>
			</button>
		</c:if>
	</div>
</div>

