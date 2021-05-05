<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>


<c:set var="isp" value="0"></c:set>
<c:if test="${ incaricoView.penale }"> 
	<c:set var="isp" value="1"></c:set>
</c:if>

<!-- PROCURA DOCUMENTO-->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="procuraDesc" class="col-sm-2 control-label"><spring:message
					text="??incarico.label.procura??" code="incarico.label.procura" /></label>
			<div class="col-sm-7">
				<c:if test="${ not empty incaricoView.procuraDoc }">
					<script>
					
					window.addEventListener("load", function(){  
						showGraffa("procuraGraffa") ;
					}); 
					</script>
					<input readonly class="form-control" value="${ incaricoView.procuraDoc.nomeFile }"/>
				</c:if>
				<c:if test="${ empty incaricoView.procuraDoc }">
				
					<script>

					window.addEventListener("load", function(){  
						hideGraffa("procuraGraffa") ;
					}); 
					</script>
					<input readonly class="form-control" value=""/>
				</c:if>
			</div>
			
			<c:if test="${ empty incaricoView.procuraDoc }">
			<div class="col-sm-1">		
				<button type="button" data-toggle="modal" 
						data-target="#panelAggiungiProcuraDoc"
						class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
						style="float: left; position: relative !important;background-color: #d9d9d9;">
						<i class="zmdi zmdi-plus icon-mini"></i>
				</button>
			</div>
			</c:if>
			
			<c:if
				test="${ not empty incaricoView.procuraDoc && not empty incaricoView.procuraDoc.vo &&  
							not empty incaricoView.procuraDoc.vo.uuid }">
		    <div class="col-sm-1">
		   	    
				<a
					href="<%=request.getContextPath() %>/download?uuid=${incaricoView.procuraDoc.vo.uuid}&isp=${isp}"
					class="  bg   waves-effect waves-circle waves-float btn-circle-mini"
					style="float: left; position: relative !important;" target="_BLANK">
					<i class="zmdi zmdi-download icon-mini"></i>
				</a>
			
			</div>	
			</c:if>		
			<div class="col-sm-1">
				<c:if test="${ not empty incaricoView.procuraDoc }">
					<button type="button" onclick="rimuoviProcura()"
							class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
							style="float: left; position: relative !important;">
							<i class="fa fa-trash icon-mini"></i>
					</button>
				</c:if>
			</div>
		</div>
	</div>
</div>


<div class="modal fade" id="panelAggiungiProcuraDoc" tabindex="-1"
	role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">
					<spring:message text="??incarico.label.aggiungiProcura??"
						code="incarico.label.aggiungiProcura" />
				</h4>
			</div>
			<div class="modal-body">
				<form id="formAggiungiProcura" method="post"
					enctype="application/x-www-form-urlencoded" class="form-horizontal">
					<engsecurity:token regenerate="false"/>
 					<div class="form-group">
						<div class="col-md-8">
							<input type="file" name="fileProcura" id="fileProcura" />
						</div>
					</div>
					<!-- Button -->
					<div class="form-group">
						<div class="col-md-8">
							<button id="btnAggiungiProcura" name="btnAggiungiProcura"
								data-dismiss="modal" type="button" onclick="aggiungiProcura(${incaricoView.incaricoId})"
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
