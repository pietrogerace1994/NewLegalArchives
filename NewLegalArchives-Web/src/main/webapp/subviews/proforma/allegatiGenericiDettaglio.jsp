<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>


<c:set var="isp" value="0"></c:set>
<c:if test="${ proformaDettaglioView.penale }"> 
	<c:set var="isp" value="1"></c:set>
</c:if>

<div class="table-responsive" style="clear: both;">
 	<table class="table table-striped table-responsive">
		<tbody>
			<c:if test="${ not empty proformaDettaglioView.listaAllegatiGenerici  }">
				<c:forEach items="${proformaDettaglioView.listaAllegatiGenerici}"
					var="oggetto">
					<tr>
						<td>${oggetto.nomeFile}</td>
						<td><a
							href="<%=request.getContextPath() %>/download?onlyfn=1&uuid=${oggetto.uuid}&isp=${isp}"
							class="  bg   waves-effect waves-circle waves-float btn-circle-mini"
							style="float: left; position: relative !important; background-color: #d9d9d9;"
							target="_BLANK"> <i class="zmdi zmdi-download icon-mini"></i>
						    </a>
						</td>
					
						<script>
						window.addEventListener("load", function(){  
							showGraffa("allegatiGenericiGraffa") ;
						});   
						</script>
						
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${ empty proformaDettaglioView.listaAllegatiGenerici }">
				<tr>
					<td colspan="2"><spring:message
							code="fascicolo.label.tabella.no.dati"
							text="??fascicolo.label.tabella.no.dati??">
						</spring:message></td>
				</tr>
			</c:if>
		</tbody>
	</table>
 
</div>
