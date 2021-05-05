<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>
<c:set var="isp" value="0"></c:set>
<c:if test="${ incaricoView.penale }"> 
	<c:set var="isp" value="1"></c:set>
</c:if>

<!-- LETTERA INCARICO FIRMATA-->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="procuraDesc" class="col-sm-2 control-label"><spring:message
					text="??incarico.label.letterafirmata??" code="incarico.label.letterafirmata" /></label>
			<div class="col-sm-7">
				<c:if test="${ not empty incaricoView.letteraFirmataDoc && not empty incaricoView.letteraFirmataDoc.uuid}">
					<script>
					
					window.addEventListener("load", function(){  
						$( "#LetteraFirmataDownload" ).html('<a id="letteraFirmata" href="<%=request.getContextPath() %>/download?uuid=${incaricoView.letteraFirmataDoc.uuid}&isp=${isp}" style="display: block;float: right;position: relative !important;margin-right: 30px;vertical-align: middle;margin-top: 7px;margin-left: 20px;font-size: 25px;" aria-expanded="true"> <i class="fa fa-pencil-square " title="Lettera d\'Incarico Firmata presente"></i></a>');
					}); 
					</script>
				</c:if>
				<c:if test="${ not empty incaricoView.letteraFirmataDoc}">
				<input readonly class="form-control" value="${ incaricoView.letteraFirmataDoc.nomeFile }"/>
				</c:if>
				<c:if test="${ empty incaricoView.letteraFirmataDoc }">
				
					<script>

					window.addEventListener("load", function(){  
						$( "#LetteraFirmataDownload" ).html('');
					}); 
					</script>
					<input readonly class="form-control" value=""/>
				</c:if>
			</div>
			
			<c:if test="${ empty incaricoView.letteraFirmataDoc }">
			<div class="col-sm-1">		
				<button type="button" data-toggle="modal" 
						data-target="#panelAggiungiLetteraIncaricoFirmata"
						class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
						style="float: left; position: relative !important;background-color: #d9d9d9;">
						<i class="zmdi zmdi-plus icon-mini"></i>
				</button>
			</div>
			</c:if>
			
			<c:if
				test="${ not empty incaricoView.letteraFirmataDoc && not empty incaricoView.letteraFirmataDoc.uuid }">
		    <div class="col-sm-1">
				<a
					href="<%=request.getContextPath() %>/download?uuid=${incaricoView.letteraFirmataDoc.uuid}&isp=${isp}"
					class="  bg   waves-effect waves-circle waves-float btn-circle-mini"
					style="float: left; position: relative !important;" target="_BLANK">
					<i class="zmdi zmdi-download icon-mini"></i>
				</a>
			
			</div>	
			</c:if>		
			<div class="col-sm-1">
				<c:if test="${ not empty incaricoView.letteraFirmataDoc }">
					<button type="button" onclick="rimuoviLetteraFirmata()"
							class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
							style="float: left; position: relative !important;">
							<i class="fa fa-trash icon-mini"></i>
					</button>
				</c:if>
			</div>
		</div>
	</div>
</div>


<div class="modal fade" id="panelAggiungiLetteraIncaricoFirmata" tabindex="-1"
	role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">
					<spring:message text="??incarico.label.aggiungiLetteraFirmata??"
						code="incarico.label.aggiungiLetteraFirmata" />
				</h4>
			</div>
			<div class="modal-body">
				<form id="formLetteraIncaricoFirmata" method="post"
					enctype="application/x-www-form-urlencoded" class="form-horizontal">
					<engsecurity:token regenerate="false"/>
 					<div class="form-group">
						<div class="col-md-8">
							<input type="file" name="fileLetteraFirmata" id="fileLetteraFirmata" />
						</div>
					</div>
					<!-- Button -->
					<div class="form-group">
						<div class="col-md-8">
							<button id="btnAggiungiLetteraFirmata" name="btnAggiungiLetteraFirmata"
								data-dismiss="modal" type="button" onclick="aggiungiLetteraFirmata(${incaricoView.incaricoId})"
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
