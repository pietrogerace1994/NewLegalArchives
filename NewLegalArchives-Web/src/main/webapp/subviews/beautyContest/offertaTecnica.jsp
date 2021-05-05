<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>

<c:set var="isp" value="1"></c:set>

<!-- Offerta Economica DOCUMENTO-->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="offertaTecnicaDesc" class="col-sm-2 control-label"><spring:message
					text="??beautyContest.label.offertaTecnica??" code="beautyContest.label.offertaTecnica" /></label>
			<div class="col-sm-7">
				<c:if test="${ not empty beautyContestReplyView.offertaTecnicaDoc }">
					<input readonly class="form-control" value="${ beautyContestReplyView.offertaTecnicaDoc.nomeFile }"/>
				</c:if>
				<c:if test="${ empty beautyContestReplyView.offertaTecnicaDoc }">
					<input readonly class="form-control" value=""/>
				</c:if>  
			</div>
			
			<c:if test="${ not empty beautyContestReplyView.offertaTecnicaDoc 
						&& not empty beautyContestReplyView.offertaTecnicaDoc.uuid }">
		
		    <div class="col-sm-1">
				<a href="<%=request.getContextPath() %>/download?uuid=${beautyContestReplyView.offertaTecnicaDoc.uuid}&isp=${isp}"
					class="  bg   waves-effect waves-circle waves-float btn-circle-mini"
					style="float: left; position: relative !important;background-color: #d9d9d9;" target="_BLANK">
					<i class="zmdi zmdi-download icon-mini"></i>
				</a>
			</div>
			</c:if>
		</div>
	</div>
</div>
<!-- PANEL UPLOAD DOC FINE -->