<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>


<c:set var="isp" value="0"></c:set>
<c:if test="${ incaricoView.penale }"> 
	<c:set var="isp" value="1"></c:set>
</c:if>

<!-- VERIFICA ANTICORRUZIONE DOCUMENTO-->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="verificaAnticorruzioneDesc" class="col-sm-2 control-label"><spring:message
					text="??incarico.label.verificaAnticorruzione??" code="incarico.label.verificaAnticorruzione" /></label>
			<div class="col-sm-7">
				<c:if test="${ not empty incaricoView.verificaAnticorruzioneDoc }">
					<input readonly class="form-control" value="${ incaricoView.verificaAnticorruzioneDoc.nomeFile }"/>
					<script>

					window.addEventListener("load", function(){  
						showGraffa("verificaAnticorruzioneGraffa") ;
					});  
					</script>
				</c:if>
				<c:if test="${ empty incaricoView.verificaAnticorruzioneDoc }">
					<input readonly class="form-control" value=""/>
					<script>

					window.addEventListener("load", function(){  
						hideGraffa("verificaAnticorruzioneGraffa") ;
					});  
					</script>
				</c:if>  
			</div>
			<c:if test="${ empty incaricoView.verificaAnticorruzioneDoc }">		
			<div class="col-sm-1">	
				<button type="button" data-toggle="modal" 
						data-target="#panelAggiungiVerificaAnticorruzioneDoc"
						class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
						style="float: left; position: relative !important;">
						<i class="zmdi zmdi-plus icon-mini"></i>
				</button>
				
			</div>
			</c:if>
			<c:if
			test="${ not empty incaricoView.verificaAnticorruzioneDoc && not empty incaricoView.verificaAnticorruzioneDoc.vo &&  
						not empty incaricoView.verificaAnticorruzioneDoc.vo.uuid }">
		
		    <div class="col-sm-1">
		     
			<a
					href="<%=request.getContextPath() %>/download?uuid=${incaricoView.verificaAnticorruzioneDoc.vo.uuid}&isp=${isp}"
					class="  bg   waves-effect waves-circle waves-float btn-circle-mini"
					style="float: left; position: relative !important;background-color: #d9d9d9;" target="_BLANK">
					<i class="zmdi zmdi-download icon-mini"></i>
				</a>
			
			</div>
			</c:if>
			<div class="col-sm-1">
				<c:if test="${ not empty incaricoView.verificaAnticorruzioneDoc }">
					<button type="button" onclick="rimuoviVerificaAnticorruzione()"
							class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
							style="float: left; position: relative !important;">
							<i class="fa fa-trash icon-mini"></i>
					</button>
				</c:if>
			</div>
		</div>
	</div>
</div>


<div class="modal fade" id="panelAggiungiVerificaAnticorruzioneDoc" tabindex="-1"
	role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">
					<spring:message text="??incarico.label.aggiungiVerificaAnticorruzione??"
						code="incarico.label.aggiungiVerificaAnticorruzione" />
				</h4>
			</div>
			<div class="modal-body">
				<form id="formAggiungiVerificaAnticorruzione" method="post"
					enctype="application/x-www-form-urlencoded" class="form-horizontal">
					<engsecurity:token regenerate="false"/>
 					<div class="form-group">
						<div class="col-md-8">
							<input type="file" name="fileVerificaAnticorruzione" id="fileVerificaAnticorruzione" />
						</div>
					</div>
					<!-- Button -->
					<div class="form-group">
						<div class="col-md-8">
							<button id="btnAggiungiVerificaAnticorruzione" name="btnAggiungiVerificaAnticorruzione"
								data-dismiss="modal" type="button" onclick="aggiungiVerificaAnticorruzione(${incaricoView.incaricoId})"
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

<!-- PANEL UPLOAD DOC FINE -->

