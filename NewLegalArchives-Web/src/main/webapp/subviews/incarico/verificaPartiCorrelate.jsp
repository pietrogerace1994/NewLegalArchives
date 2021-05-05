<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>




<c:set var="isp" value="0"></c:set>
<c:if test="${ incaricoView.penale }"> 
	<c:set var="isp" value="1"></c:set>
</c:if>

<!-- PARTI CORRELATE DOCUMENTO-->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="verificaPartiCorrelateDesc" class="col-sm-2 control-label"><spring:message
					text="??incarico.label.verificaPartiCorrelate??" code="incarico.label.verificaPartiCorrelate" /></label>
			<div class="col-sm-7">
				<c:if test="${ not empty incaricoView.verificaPartiCorrelateDoc }">
					<input readonly class="form-control" value="${ incaricoView.verificaPartiCorrelateDoc.nomeFile }"/>
					<script>

					window.addEventListener("load", function(){  
						showGraffa("verificaPartiCorrelateGraffa") ;
					});   
					</script>
				</c:if>
				<c:if test="${ empty incaricoView.verificaPartiCorrelateDoc }">
					<input readonly class="form-control" value=""/>
					<script>

					window.addEventListener("load", function(){  
						hideGraffa("verificaPartiCorrelateGraffa") ;
					});    
					</script>
				</c:if>   
			</div>
	        <c:if test="${ empty incaricoView.verificaPartiCorrelateDoc }">		
				<div class="col-sm-1">	
						<button type="button" data-toggle="modal" 
							data-target="#panelAggiungiVerificaPartiCorrelateDoc"
							class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
							style="float: left; position: relative !important;">
							<i class="zmdi zmdi-plus icon-mini"></i>
						</button>
					
				</div>
			</c:if>
		    <c:if
				test="${ not empty incaricoView.verificaPartiCorrelateDoc && not empty incaricoView.verificaPartiCorrelateDoc.vo &&  
							not empty incaricoView.verificaPartiCorrelateDoc.vo.uuid }">
			<div class="col-sm-1">
			  
				<a
					href="<%=request.getContextPath() %>/download?uuid=${incaricoView.verificaPartiCorrelateDoc.vo.uuid}&isp=${isp}"
					class="  bg   waves-effect waves-circle waves-float btn-circle-mini"
					style="float: left; position: relative !important;" target="_BLANK">
					<i class="zmdi zmdi-download icon-mini"></i>
				</a>
			
			</div>
			</c:if>
			<div class="col-sm-1">
				<c:if test="${ not empty incaricoView.verificaPartiCorrelateDoc }">
					<button type="button" onclick="rimuoviVerificaPartiCorrelate()"
							class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
							style="float: left; position: relative !important;">
							<i class="fa fa-trash icon-mini"></i>
					</button>
				</c:if>
			</div>
		</div>
	</div>
</div>


<div class="modal fade" id="panelAggiungiVerificaPartiCorrelateDoc" tabindex="-1"
	role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">
					<spring:message text="??fascicolo.label.aggiungiVerificaPartiCorrelate??"
						code="fascicolo.label.aggiungiVerificaPartiCorrelate" />
				</h4>
			</div>
			<div class="modal-body">
				<form id="formAggiungiVerificaPartiCorrelate" method="post"
					enctype="application/x-www-form-urlencoded" class="form-horizontal">
					<engsecurity:token regenerate="false"/>
 					<div class="form-group">
						<div class="col-md-8">
							<input type="file" name="fileVerificaPartiCorrelate" id="fileVerificaPartiCorrelate" />
						</div>
					</div>
					<!-- Button -->
					<div class="form-group">
						<div class="col-md-8">
							<button id="btnAggiungiVerificaPartiCorrelate" name="btnAggiungiVerificaPartiCorrelate"
								data-dismiss="modal" type="button" onclick="aggiungiVerificaPartiCorrelate(${incaricoView.incaricoId})"
								class="btn btn-primary">
								<spring:message text="??fascicolo.label.ok??"
									code="fascicolo.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??fascicolo.label.chiudi??"
									code="fascicolo.label.chiudi" />
							</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

<!-- PANEL UPLOAD DOC FINE -->
