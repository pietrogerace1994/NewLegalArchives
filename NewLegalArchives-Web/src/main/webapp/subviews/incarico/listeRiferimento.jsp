<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>



<%-- <!-- engsecurity VA --><form method="post" name="formToken">
<engsecurity:token regenerate="false"/> --%>
			

<c:set var="isp" value="0"></c:set>
<c:if test="${ incaricoView.penale }"> 
	<c:set var="isp" value="1"></c:set>
</c:if>

<!-- LISTE RIFERIMENTO DOCUMENTO-->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="listeRiferimentoDesc" class="col-sm-2 control-label"><spring:message
					text="??incarico.label.listeRiferimento??" code="incarico.label.listeRiferimento" /></label>
			<div class="col-sm-7">
				<c:if test="${ not empty incaricoView.listeRiferimentoDoc }">
					<input readonly class="form-control" value="${ incaricoView.listeRiferimentoDoc.nomeFile }"/>
					<script>

					window.addEventListener("load", function(){  
						showGraffa("listeRiferimentoGraffa") ;
					});    
					</script>
				</c:if>
				<c:if test="${ empty incaricoView.listeRiferimentoDoc }">
					<script>

					window.addEventListener("load", function(){  
						hideGraffa("listeRiferimentoGraffa") ;
					});    
					</script>
					<input readonly class="form-control" value=""/>
				</c:if> 
			</div>
			
			<c:if test="${ empty incaricoView.listeRiferimentoDoc }">	
			<div class="col-sm-1">		
				<button type="button" data-toggle="modal" 
						data-target="#panelAggiungiListeRiferimentoDoc"
						class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
						style="float: left; position: relative !important;">
						<i class="zmdi zmdi-plus icon-mini"></i>
				</button>
			
			</div>
			</c:if>
			<c:if
				test="${ not empty incaricoView.listeRiferimentoDoc && not empty incaricoView.listeRiferimentoDoc.vo &&  
							not empty incaricoView.listeRiferimentoDoc.vo.uuid }">
			<div class="col-sm-1">
			
				<a
					href="<%=request.getContextPath() %>/download?uuid=${incaricoView.listeRiferimentoDoc.vo.uuid}&isp=${isp}"
					class="  bg   waves-effect waves-circle waves-float btn-circle-mini"
					style="float: left; position: relative !important;background-color: #d9d9d9;" target="_BLANK">
					<i class="zmdi zmdi-download icon-mini"></i>
				</a>
		   
		
			</div>
			</c:if>
			<div class="col-sm-1">
				<c:if test="${ not empty incaricoView.listeRiferimentoDoc }">
					<button type="button" onclick="rimuoviListeRiferimento()"
							class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
							style="float: left; position: relative !important;">
							<i class="fa fa-trash icon-mini"></i>
					</button>
				</c:if>
			</div>
		</div>
	</div>
</div>


<div class="modal fade" id="panelAggiungiListeRiferimentoDoc" tabindex="-1"
	role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">
					<spring:message text="??incarico.label.aggiungiListeRiferimento??"
						code="incarico.label.aggiungiListeRiferimento" />
				</h4>
			</div>
			<div class="modal-body">
				<form id="formAggiungiListeRiferimento" method="post"
					enctype="application/x-www-form-urlencoded" class="form-horizontal">
					<engsecurity:token regenerate="false"/>
 					<div class="form-group">
						<div class="col-md-8">
							<input type="file" name="fileListeRiferimento" id="fileListeRiferimento" />
						</div>
					</div>
					<!-- Button -->
					<div class="form-group">
						<div class="col-md-8">
							<button id="btnAggiungiListeRiferimento" name="btnAggiungiListeRiferimento"
								data-dismiss="modal" type="button" onclick="aggiungiListeRiferimento(${incaricoView.incaricoId})"
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

<%-- </form> --%>

<!-- PANEL UPLOAD DOC FINE -->
