<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>



<c:set var="isp" value="0"></c:set>
<c:if test="${ incaricoDettaglioView.penale }"> 
	<c:set var="isp" value="1"></c:set>
</c:if>

<!-- PROCURA DOCUMENTO-->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="procuraDesc" class="col-sm-2 control-label"><spring:message
					text="??incarico.label.procura??" code="incarico.label.procura" /></label>
			<div class="col-sm-6">
				<c:if test="${ not empty incaricoDettaglioView.procuraDoc }">
					
					<script>

					window.addEventListener("load", function(){  
						showGraffa("procuraGraffa") ;
					}); 
					</script>
					<input readonly class="form-control" value="${ incaricoDettaglioView.procuraDoc.nomeFile }"/>
				</c:if>
				<c:if test="${ empty incaricoDettaglioView.procuraDoc }">
					<input readonly class="form-control" value=""/>
				</c:if>
			</div>
			<div class="col-sm-4"> 
				
				<c:if test="${ not empty incaricoDettaglioView.procuraDoc }">
					<a href="<%=request.getContextPath() %>/download?uuid=${incaricoDettaglioView.procuraDoc.vo.uuid}&isp=${isp}" class="  bg   waves-effect waves-circle waves-float btn-circle-mini"
							style="float: left; position: relative !important;background-color: #d9d9d9;" target="_BLANK">
							<i class="zmdi zmdi-download icon-mini"></i>
					</a>
				</c:if>
			 
			</div> 
		</div>
	</div>
</div>

 