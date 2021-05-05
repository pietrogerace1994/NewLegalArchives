<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>



<div class="table-responsive" style="clear: both;">
 
	<table class="table table-striped table-responsive">
		<tbody>
			<c:if test="${ not empty schedaFondoRischiView.listaAllegatiLegaleEsterno  }">
				<c:forEach items="${schedaFondoRischiView.listaAllegatiLegaleEsterno}"
					var="oggetto">
					<tr>
						<td>${oggetto.nomeFile}</td>
						<td><a
							href="<%=request.getContextPath() %>/download?onlyfn=1&isp=0&uuid=${oggetto.uuid}"
							class="  bg   waves-effect waves-circle waves-float btn-circle-mini"
							style="float: left; position: relative !important; background-color: #d9d9d9;"
							target="_BLANK"> <i class="zmdi zmdi-download icon-mini"></i>
						    </a>
						</td>
						<td>
							<button type="button" onclick="rimuoviAllegatoProfessionista('${oggetto.uuid}')"
							class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
							style="float: left; position: relative !important;">
							<i class="fa fa-trash icon-mini"></i>
							</button>
						</td>
						<script>

						window.onload = function(e) {
							showGraffa("allegatiGenericiGraffa") ;
						}
						</script>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${ empty schedaFondoRischiView.listaAllegatiLegaleEsterno }">
				<tr>	<script>

						window.onload = function(e) {
				          hideGraffa("allegatiGenericiGraffa") ;
						}
					 	</script>
					<td colspan="2"><spring:message
							code="fascicolo.label.tabella.no.dati"
							text="??fascicolo.label.tabella.no.dati??">
						</spring:message></td>
				</tr>
			</c:if>
		</tbody>
	</table>

</div>


<div class="modal fade" id="panelAggiungiAllegatoProfessionista" tabindex="-1"
	role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">
					<spring:message text="??schedaFondoRischi.label.aggiungiAllegatoProfessionista??" code="schedaFondoRischi.label.aggiungiAllegatoProfessionista"/>
				</h4>
			</div>
			<div class="modal-body">
				<form id="formAggiungiAllegatoProfessionista" method="post"
					enctype="application/x-www-form-urlencoded" class="form-horizontal">
					<engsecurity:token regenerate="false"/>
 					<div class="form-group">
						<div class="col-md-8">
							<input type="file" name="fileAllegatoProfessionista" id="fileAllegatoProfessionista"/>
						</div>
					</div>
					<!-- Button -->
					<div class="form-group">
						<div class="col-md-8">
							<button id="btnAggiungiAllegatoProfessionista" name="btnAggiungiAllegatoProfessionista"
								data-dismiss="modal" type="button" onclick="aggiungiAllegatoProfessionista(${schedaFondoRischiView.schedaFondoRischiId})" class="btn btn-primary">
								<spring:message text="??schedaFondoRischi.label.ok??" code="schedaFondoRischi.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal" class="btn btn-warning">
								<spring:message text="??schedaFondoRischi.label.chiudi??" code="schedaFondoRischi.label.chiudi" />
							</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

<!-- PANEL UPLOAD DOC FINE -->

