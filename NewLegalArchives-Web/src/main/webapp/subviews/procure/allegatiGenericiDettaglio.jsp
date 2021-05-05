<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>



<c:set var="isp" value="0"></c:set>
<div class="table-responsive" style="clear: both;">
 <%--  <!-- engsecurity VA --><form method="post" name="formToken"><engsecurity:token regenerate="false"/>
 --%>	
	<table class="table table-striped table-responsive">
		<tbody>
			<c:if test="${ not empty procureDettaglioView.listaAllegatiGenerici  }">
				<c:forEach items="${procureDettaglioView.listaAllegatiGenerici}"
					var="oggetto">
					<tr>
					  
						<td>${oggetto.nomeFile}</td>
						<td><a
							href="<%=request.getContextPath() %>/download?onlyfn=1&isp=${isp}&uuid=${oggetto.uuid}"
							class="  bg   waves-effect waves-circle waves-float btn-circle-mini"
							style="float: left; position: relative !important; background-color: #d9d9d9;"
							target="_BLANK"> <i class="zmdi zmdi-download icon-mini"></i>
						    </a>
						</td>
					
						<script>
						window.onload = function(e) {
							showGraffa("allegatiGenericiGraffa");
						}
						</script>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${ empty procureDettaglioView.listaAllegatiGenerici }">
				<tr>
					<td colspan="2"><spring:message
							code="fascicolo.label.tabella.no.dati"
							text="??fascicolo.label.tabella.no.dati??">
						</spring:message></td>
				</tr>
			</c:if>
		</tbody>
	</table>
	<%-- </form> --%>
</div>
