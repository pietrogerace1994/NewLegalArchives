<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>


<!DOCTYPE html lang="${language}">
<!--[if IE 9 ]><html class="ie9"><![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Legal Archives</title>

<jsp:include page="/parts/script-init.jsp">
</jsp:include>

<style>
.form-control[disabled], .form-control[readonly], fieldset[disabled] .form-control {
    background-color: #ffffff!important;
    opacity: 1;}
.form-control:not(.fc-alt) {
    border-left: 0;
    border-right: 0;
    border: 0px !important;
    appearance: none;
    padding: 0;
    paddingleft: 10px;
}
body{
background-color:#ffffff;
}
.card-header{color:#000000!important;}
.card-header.ch-dark h2, .card-header.ch-dark small{color:#000000!important;}

[data-ma-header="teal"]:before, [data-ma-header="teal"] #header {
    background-color: #ffffff!important;
	}

#attoStampa td{ padding-top:5px;padding-bottom:5px;}
#attoStampa label,input{ text-align:left;}	
	
</style>

</head>

<body data-ma-header="teal">
	 

<!-- SECION MAIN -->
<section id="main">
<!-- SECTION CONTENT -->
<section id="content">
	<div class="container">
		<div class="row">
			<div id="col-1" class="col-lg-12 col-md-12 col-sm-12 col-sx-12">

				<div class="card">
					<div class="card-header ch-dark palette-Green-SNAM">
						 
							<h2>
							<spring:message text="??atto.label.visualizza??" code="atto.label.visualizza" />
							</h2>
			         <div id="box-stampa" style="width:100%;padding-bottom:20px;position:relative;"> 
			         <a href="javascript:void(0)" onclick="javascript:window.print()" style="position:absolute;top:10px;right: 20px;z-index: 1000;color: #000000;font-weight: bold;">S T A M P A</a>
			         </div>	
					</div>
					<div class="card-body">
					
					<!-- Area Crea Atto-->
			
		<div class="table-responsive">

			<div class="col-md-12">
				<div class="col-md-12">
					<div class="col-md-12" style="margin-bottom: 20px;margin-top: 20px;border-bottom: 1px">
						<h4><spring:message text="??atto.label.visualizzaDati??" code="atto.label.visualizzaDati" /></h4> 
						Protocollo Num. <c:out value="${atto.numeroProtocollo}"></c:out>
					</div>
					<div class="col-md-12">
					
						        
						          <c:if test="${ not empty listError }">
										<div class="alert alert-danger box-error">
										<c:forEach items="${listError}" var="value">
								         <c:out value="${value}"></c:out><br>
						   				</c:forEach>
										</div>
								</c:if>
						 	<div class="alert alert-danger box-error" style="display:none"></div>
							<fieldset>
							<input type="hidden" name="numeroProtocollo" id="numeroProtocollo" value='<c:out value="${atto.numeroProtocollo}"></c:out>' >
							<input type="hidden" id="idAtto" name="idAtto" value="<c:out value="${atto.id}"></c:out>">
							<input type="hidden" id="operazioneCorrente" name="operazioneCorrente" value="modifica" > 
										<!-- Text input-->
							<table id="attoStampa" style="width: 100%" border="1px"> <!--INIT TABLE -->
								 <tr><td style="width:30%">			
			
									<label class="col-md-12 control-label"
										for="idSocieta"><spring:message text="??atto.label.societa??" code="atto.label.societa" /></label>
								   </td><td style="width:70%">		
										<div class="col-md-12">
											<c:if test="${ listaSocieta != null }">
											<c:forEach items="${listaSocieta}" var="oggetto">
												<c:if test="${ oggetto.id eq atto.societa.id }">
													<c:out value="${oggetto.ragioneSociale}"></c:out>
												 </c:if>
											</c:forEach>
											</c:if>
											</div>
									</td></tr>
							  <tr><td style="width:30%">	
								<!-- Text input-->
									<!-- Text input-->
							 
									<label class="col-md-12 control-label"
										for="parteNotificante"><spring:message text="??atto.label.parteNotificante??" code="atto.label.parteNotificante" /></label>
									</td><td style="width:70%">	
									<div class="col-md-12">
										<c:out value="${atto.parteNotificante}"></c:out> 
									</div>
								 </td></tr>
								<!-- Text input-->
								
								<!-- Text input-->
								  <tr><td style="width:30%">	
								 
									<label class="col-md-12 control-label"
										for="idCategoriaAtto"><spring:message text="??atto.label.categoria??" code="atto.label.categoria" /></label>
									
									</td><td style="width:70%">	
									 <div class="col-md-12">
											<c:if test="${ listaCategorie != null }">
											<c:forEach items="${listaCategorie}" var="oggetto">
												  <c:if test="${ oggetto.id eq atto.categoriaAtto.id }"> 
													<c:out value="${oggetto.nome}"></c:out>
												 </c:if>   
											</c:forEach>
											</c:if>
										 </div>
									  </td></tr>
								 
								<!-- Text input-->
								<!-- Text input-->
								 <tr><td style="width:30%">																																		<!-- Text input-->
								 
									<label class="col-md-12 control-label"
										for="destinatario"><spring:message text="??atto.label.destinatario??" code="atto.label.destinatario" /></label>
									</td><td style="width:70%">	
									  <div class="col-md-12">
											<c:if test="${ listaDestinatario != null }">
											<c:forEach items="${listaDestinatario}" var="oggetto">
											  <c:if test="${ oggetto.matricolaUtil eq atto.destinatario }">  
													<c:out value="${oggetto.nominativoUtil}"></c:out>
											</c:if>  
											</c:forEach>
											</c:if>
										</div>	
										</td></tr>
								 
								 
						
								<!-- Text input-->
						 <tr><td style="width:30%">	
								 
									<label class="col-md-12 control-label"
										for="tipoAtto"><spring:message text="??atto.label.tipoAtto??" code="atto.label.tipoAtto" /></label>
									</td><td style="width:70%">	
									<div class="col-md-12">
									<c:out value="${atto.tipoAtto}"></c:out>
									</div>
							 </td></tr>
										<!-- Text input-->
							 <tr><td style="width:30%">	
									<label class="col-md-12 control-label"
										for="foroCompetente"><spring:message text="??atto.label.foroCompetente??" code="atto.label.foroCompetente" /></label>
									</td><td style="width:70%">	
									<div class="col-md-12">
									<c:out value="${atto.foroCompetente}"></c:out>
									</div>
								  </td></tr>
								<!-- Text input-->
								 <tr><td style="width:30%">	
								 
									<label class="col-md-12 control-label"
										for="dataNotifica"><spring:message text="??atto.label.dataNotifica??" code="atto.label.dataNotifica" /></label>
									</td><td style="width:70%">	
									<div class="col-md-12">
										<fmt:formatDate pattern="dd/MM/yyyy" value="${atto.dataNotifica}"/> 
									</div>
								   </td></tr>
								<!-- Text input-->		<!-- Text input-->
								 <tr><td style="width:30%">	
								 
									<label class="col-md-12 control-label"
										for="note"><spring:message text="??atto.label.note??" code="atto.label.note" /></label>
									</td><td style="width:70%">	
									<div class="col-md-12">
										<c:out value="${atto.note}"></c:out>
									</div>
							   </td></tr>
								<!-- Text input-->
							 <tr><td style="width:30%">	
									<label class="col-md-12 control-label"
										for="creatoDa"><spring:message text="??atto.label.creatoDa??" code="atto.label.creatoDa" /></label>
									</td><td style="width:70%">	
									<div class="col-md-12">
										 
											<c:out value="${creatoDaNome}"></c:out>
									</div>
								  </td></tr>
								<!-- Text input-->
								 <tr><td style="width:30%">	
									<label class="col-md-12 control-label"
										for="dataCreazione"><spring:message text="??atto.label.dataCreazione??" code="atto.label.dataCreazione" /></label>
							</td><td style="width:70%">						
								<div class="col-md-12">
										<fmt:formatDate pattern="dd/MM/yyyy" value="${atto.dataCreazione}"/>
										   
									</div>
								   </td></tr>
								<!-- Text input-->
								
								 <tr><td style="width:30%">	
									<label class="col-md-12 control-label"
										for="dataUltimaModifica"><spring:message text="??atto.label.dataUltimaModifica??" code="atto.label.dataUltimaModifica" /></label>
									</td><td style="width:70%">	
									<div class="col-md-12">
									<fmt:formatDate pattern="dd/MM/yyyy" value="${atto.dataUltimaModifica}"/>
									
									</div>
							  </td></tr>
								<!-- Text input-->	
								 <tr><td style="width:30%">
									<label class="col-md-12 control-label"
										for="stato"><spring:message text="??atto.label.stato??" code="atto.label.stato" /></label>
									</td><td style="width:70%">	
									<div class="col-md-12">
										 
										 <c:out value="${atto.statoAtto.descrizione}"></c:out>  
									</div>
									 </td></tr>
								 
								<!-- Text input-->	
									 <tr><td style="width:30%">
									<label class="col-md-12 control-label"
										for="dataUdienza"><spring:message text="??atto.label.dataUdienza??" code="atto.label.dataUdienza" /></label>
									</td><td style="width:70%">	
									<div class="col-md-12">
										 <fmt:formatDate pattern="dd/MM/yyyy" value="${atto.dataUdienza}"/> 
									</div>
								  </td></tr>
								<!-- Text input-->	
									 <tr><td style="width:30%">
									<label class="col-md-12 control-label"
										for="rilevante"><spring:message text="??atto.label.rilevante??" code="atto.label.rilevante" /></label>
									</td><td style="width:70%">	
									<div class="col-md-12">
									<c:if test="${atto.rilevante eq 'T' }">SI</c:if> 
									<c:if test="${atto.rilevante eq 'F' }">NO</c:if>
				
									</div>
									</td></tr>
						 
								<!-- Text input-->	
									 
									<tr class="isAltri"><td style="width:30%" class="isAltri">
									<label class="col-md-12 control-label"
										for="utenteInvioAltriUffici"><spring:message text="??atto.label.utenteInvioAltriUffici??" code="atto.label.utenteInvioAltriUffici" /></label>
									</td><td style="width:70%" class="isAltri">
									<div class="col-md-12">
										<input id="utenteInvioAltriUffici" name="utenteInvioAltriUffici"
											type="text" class="typeahead form-control input-md isAltri" value="<c:out value="${atto.utenteInvioAltriUffici}"></c:out>" >  
									</div>
							 	</td></tr> 
								<!-- Text input-->	
								<tr class="isAltri"><td style="width:30%" class="isAltri">
									<label class="col-md-12 control-label"
										for="emailInvioAltriUffici"><spring:message text="??atto.label.emailInvioAltriUffici??" code="atto.label.emailInvioAltriUffici" /></label>
									</td><td style="width:70%" class="isAltri">
									<div class="col-md-8">
										<input id="emailInvioAltriUffici" name="emailInvioAltriUffici"
											type="text" class="typeahead form-control input-md isAltri" value="<c:out value="${atto.emailInvioAltriUffici}"></c:out>" >  
									</div>
								</td></tr> 
								<!-- Text input-->	
									<tr class="isAltri"><td style="width:30%" class="isAltri">
									<label class="col-md-3 control-label"
										for="unitaLegInvioAltriUffici"><spring:message text="??atto.label.unitaLegInvioAltriUffici??" code="atto.label.unitaLegInvioAltriUffici" /></label>
									</td><td style="width:70%" class="isAltri">
									<div class="col-md-12">
										<input id="unitaLegInvioAltriUffici" name="unitaLegInvioAltriUffici"
											type="text" class="typeahead form-control input-md isAltri" value="<c:out value="${atto.unitaLegInvioAltriUffici}"></c:out>" >  
									</div>
								</td></tr> 
								<!-- Text input-->	
						
								<c:if test="${ atto.documento != null && not empty atto.documento.uuid}">		
								 <tr><td style="width:30%">

									<label class="col-md-12 control-label">
										  </label>
									</td><td style="width:70%">			  
										  <div class="col-md-12">
										<button style="float:left" type="button" disabled="disabled" data-toggle="dropdown" id="download-atto__" uuid="<c:out value="${atto.documento.uuid}"></c:out>"
											class="btn btn-success dropdown-toggle">
								<spring:message text="??atto.button.scaricaAllegato??" code="atto.button.scaricaAllegato" /> <i class="fa fa-arrow-circle-down"></i>
										</button>
										
										 <div style="float:left;margin-left:10px;">
										<c:out value="${atto.documento.nomeFile}"></c:out>
										 </div>
										 </div>
								</td></tr> 
								</c:if>
								</table> 
							</fieldset>

								<div class="modal-footer">
								<div class="col-md-12 column">
				     <div class="btn-group dropup pull-right ">
					<div class="btn-group pull-right space-to-left">
		 
					</div>
					<!-- pulsante esporta senza opzioni -->
					<div class="btn-group pull-right">
				 
				 
			 
					</div>
						<div class="btn-group pull-right">
				 
					 
					</div>
				</div>
			</div>
				</div>
				
		 					
						 
					</div>

	
		</div>
			</div>
			 	</div>

		 

			 
			</div>
		</div><!--/ fine card -->
		</div>
	</div>
</div>
<!--/ fine col-1 -->
	</section>
<form id="downloadAtto" action="./download.action" method="get" style="display:none">
<engsecurity:token regenerate="false"/>	
<input type="hidden" name="uuid" id="uuid" value="">
<input type="submit" value="" style="display:none">
</form>
</section>


 
<jsp:include page="/parts/script-end.jsp"></jsp:include>


<script src="<%=request.getContextPath()%>/portal/js/controller/fascicolo.js"></script>
<script src="<%=request.getContextPath()%>/portal/js/controller/atto.js"></script>




</body>
</html>
