<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>


<c:set var="isp" value="0"></c:set>

<%-- <!-- engsecurity VA --><form method="post" name="formToken">
<engsecurity:token regenerate="false"/> --%>

<div class="table-responsive" style="clear: both;">



	<table class="table table-striped table-responsive">
		<tbody>
			<c:if test="${ not empty affariSocietariView.allegato  }">
				<c:forEach items="${affariSocietariView.allegato}"
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
						<td>
							<c:if test="${!(param.disableInVis == 'true')}" >
								<button type="button" onclick="rimuoviAllegatoGenerico('${oggetto.uuid}')"
								class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
								style="float: left; position: relative !important;">
								<i class="fa fa-trash icon-mini"></i>
								</button>
							</c:if>
						</td>
						<script>

						window.onload = function(e) {
							showGraffa("allegatiGenericiGraffa") ;
						}
						</script>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${ empty affariSocietariView.allegato }">
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


<div class="modal fade" id="panelAggiungiAllegatoGenerico" tabindex="-1"
	role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">
					<spring:message text="??incarico.label.aggiungiAllegatoGenerico??"
						code="incarico.label.aggiungiAllegatoGenerico" />
				</h4>
			</div>
			<div class="modal-body">
				<form id="formAggiungiAllegatoGenerico" method="post"
					enctype="application/x-www-form-urlencoded" class="form-horizontal">
					<engsecurity:token regenerate="false"/>
 					<div class="form-group">
						<div class="col-md-8">
							<input type="file" name="fileAllegatoGenerico" id="fileAllegatoGenerico" />
						</div>
					</div>
					<!-- Button -->
					<div class="form-group">
						<div class="col-md-8">
						
								<button id="btnAggiungiAllegatoGenerico" name="btnAggiungiAllegatoGenerico" hidden="${param.disableInVis}"
								data-dismiss="modal" type="button" onclick="aggiungiAllegatoGenerico(${affariSocietariView.affariSocietariId})"
								class="btn btn-primary">
								<spring:message text="??incarico.label.ok??"
									code="incarico.label.ok" />
								</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??incarico.label.chiudi??"
									code="incarico.label.chiudi" />
							</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

<%-- </form>	 --%>

<!-- PANEL UPLOAD DOC FINE -->


