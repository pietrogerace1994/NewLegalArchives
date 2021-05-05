<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>

<c:set var="isp" value="0"></c:set>
<c:if test="${ incaricoView.penale }"> 
	<c:set var="isp" value="1"></c:set>
</c:if>

<!-- NOTA PROPOSTA FIRMATA-->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="procuraDesc" class="col-sm-2 control-label"><spring:message
					text="??incarico.label.notapropostafirmata??" code="incarico.label.notapropostafirmata" /></label>
			<div class="col-sm-7">
				<c:if test="${ not empty incaricoView.letteraFirmataDocNota && not empty incaricoView.letteraFirmataDocNota.uuid}">
					<script>
					
					window.addEventListener("load", function(){  
						$( "#NotaPropostaFirmataDownload" ).html('<a id="notaFirmata" href="<%=request.getContextPath() %>/download?onlyfn=1&uuid=${incaricoView.letteraFirmataDocNota.uuid}&isp=${isp}" style="display: block;float: right;position: relative !important;margin-right: 30px;vertical-align: middle;margin-top: 7px;margin-left: 20px;font-size: 25px;" aria-expanded="true"> <i class="fa fa-pencil-square " title="Nota Proposta d\'Incarico Firmata presente"></i></a>');
					}); 
					</script>
				</c:if>
				
				<c:if test="${ not empty incaricoView.letteraFirmataDocNota }">
					<input readonly class="form-control" value="${ incaricoView.letteraFirmataDocNota.nomeFile }"/>		
				</c:if>
				
				<c:if test="${ empty incaricoView.letteraFirmataDocNota }">
				
					<script>

					window.addEventListener("load", function(){  
						$( "#NotaPropostaFirmataDownload" ).html('');
					}); 
					</script>
					<input readonly class="form-control" value=""/>
				</c:if>
			</div>
			
			<c:if test="${ empty incaricoView.letteraFirmataDocNota }">
			<div class="col-sm-1">		
				<button type="button" data-toggle="modal" 
						data-target="#panelAggiungiNotaPropostaFirmata"
						class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
						style="float: left; position: relative !important;background-color: #d9d9d9;">
						<i class="zmdi zmdi-plus icon-mini"></i>
				</button>
			</div>
			</c:if>
			
			<c:if
				test="${ not empty incaricoView.letteraFirmataDocNota && not empty incaricoView.letteraFirmataDocNota.uuid }">
		    <div class="col-sm-1">
				<a
					href="<%=request.getContextPath() %>/download?uuid=${incaricoView.letteraFirmataDocNota.uuid}&isp=${isp}"
					class="  bg   waves-effect waves-circle waves-float btn-circle-mini"
					style="float: left; position: relative !important;" target="_BLANK">
					<i class="zmdi zmdi-download icon-mini"></i>
				</a>
			
			</div>	
			</c:if>		
			<div class="col-sm-1">
				<c:if test="${ not empty incaricoView.letteraFirmataDocNota }">
					<button type="button" onclick="rimuoviNotaPropostaFirmata()"
							class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
							style="float: left; position: relative !important;">
							<i class="fa fa-trash icon-mini"></i>
					</button>
				</c:if>
			</div>
		</div>
	</div>
</div>


<div class="modal fade" id="panelAggiungiNotaPropostaFirmata" tabindex="-1"
	role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">
					<spring:message text="??incarico.label.notapropostafirmata??"
						code="incarico.label.notapropostafirmata" />
				</h4>
			</div>
			<div class="modal-body">
				<form id="formNotaPropostaFirmata" method="post"
					enctype="application/x-www-form-urlencoded" class="form-horizontal">
					<engsecurity:token regenerate="false"/>
 					<div class="form-group">
						<div class="col-md-8">
							<input type="file" name="fileNotaPropostaFirmata" id="fileNotaPropostaFirmata" />
						</div>
					</div>
					<!-- Button -->
					<div class="form-group">
						<div class="col-md-8">
							<button id="btnAggiungiNotaPropostaFirmata" name="btnAggiungiNotaPropostaFirmata"
								data-dismiss="modal" type="button" onclick="aggiungiNotaPropostaFirmata(${incaricoView.incaricoId})"
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
