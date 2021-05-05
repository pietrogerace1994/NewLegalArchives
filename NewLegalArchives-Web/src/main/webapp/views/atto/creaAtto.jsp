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

</head>

<body data-ma-header="teal">
	<jsp:include page="/parts/header.jsp">
</jsp:include>

<!-- SECION MAIN -->
<section id="main">
	<jsp:include page="/parts/aside.jsp">
</jsp:include>
<!-- SECTION CONTENT -->
<section id="content">
	<div class="container">
		<div class="row">
		<div id="col-1" class="col-lg-12 col-md-12 col-sm-12 col-sx-12">

			<div class="card">
				<div class="card-header ch-dark palette-Green-SNAM bg">
					 
					<h2>
					<spring:message text="??atto.label.nuovoAtto??" code="atto.label.nuovoAtto" />	 
					</h2>
	 	 
	</div>
	<div class="card-body">

<!-- Area Crea Atto-->
	
	<div class="table-responsive">

	<div class="col-md-12">
		<div class="col-md-12">
			<div class="col-md-12" style="margin-top: 20px;">
				<h4><spring:message text="??atto.label.nuovoAttoDati??" code="atto.label.nuovoAttoDati" />	</h4>
	</div>
	<div class="col-md-12">
	<form:form name="attoForm" method="POST" modelAttribute="attoView" action="./save.action"  enctype="multipart/form-data"  class="form-horizontal" onsubmit="return validaForm(this)">
		 	            <c:if test="${ not empty listError }">
										<div class="alert alert-danger box-error">
										<c:forEach items="${listError}" var="value">
								         <c:out value="${value}"></c:out><br>
						   				</c:forEach>
										</div>
						</c:if>
						<engsecurity:token regenerate="false"/>
				<div class="alert alert-danger box-error" style="display:none"></div>		
				<form:errors path="*" cssClass="alert alert-danger" element="div"> </form:errors>
				<input type="hidden" id="operazioneCorrente" name="operazioneCorrente" value="salvaAtto" >
			<fieldset>	 
		<!-- Text input-->
			<div class="form-group">
				<label class="col-md-3 control-label"
					for="numeroProtocollo"><spring:message text="??atto.label.numeroAtto??" code="atto.label.numeroAtto" /></label>
				<div class="col-md-8">
					<input  disabled="disabled" 
						type="text" class="typeahead form-control input-md" value="<c:out value="${numeroProtocollo}"></c:out>" > 
						<input id="numeroProtocollo" name="numeroProtocollo"
						type="hidden" class="typeahead form-control input-md" value="<c:out value="${numeroProtocollo}"></c:out>" > 
				</div>
			</div>
			<!-- Text input-->
			<div class="form-group">
				<label class="col-md-3 control-label"
					for="idSocieta"><spring:message text="??atto.label.societa??" code="atto.label.societa" /></label>
				<div class="col-md-8">
				<select id="idSocieta" name="idSocieta" class="form-control">
						<option></option>
						<c:if test="${ listaSocieta != null }">
						<c:forEach items="${listaSocieta}" var="oggetto">
							<option value="${ oggetto.id }">
								<c:out value="${oggetto.ragioneSociale}"></c:out>
							</option>
						</c:forEach>
						</c:if>
					
					</select>  
				</div>
			</div>
			<!-- Text input-->
				<!-- Text input-->
			<div class="form-group">
				<label class="col-md-3 control-label"
					for="parteNotificante"><spring:message text="??atto.label.parteNotificante??" code="atto.label.parteNotificante" /></label>
				<div class="col-md-8">
					<input id="parteNotificante" name="parteNotificante"
						type="text" class="typeahead form-control input-md" value="" >  
				</div>
			</div>
			<!-- Text input-->
			
			<!-- Text input-->
			<div class="form-group">
				<label class="col-md-3 control-label"
					for="idCategoriaAtto"><spring:message text="??atto.label.categoria??" code="atto.label.categoria" /></label>
				<div class="col-md-8">
				<select id="idCategoriaAtto" name="idCategoriaAtto" class="form-control">
				<option></option>
							<c:if test="${ listaCategorie != null }">
						<c:forEach items="${listaCategorie}" var="oggetto">
							<option value="${ oggetto.id }" id="${oggetto.codGruppoLingua }">
								<c:out value="${oggetto.nome}"></c:out>
							</option>
						</c:forEach>
						</c:if>
					</select>
				</div>
			</div>
			<!-- Text input-->
			<!-- Text input-->
			<!-- ESITO-->
			<div class="form-group" id="formEsito" style="display: none;">
				<label class="col-md-3 control-label"
					for="idEsito"><spring:message text="??atto.label.esito??" code="atto.label.esito" /></label>
				<div class="col-md-8">
				<select id="idEsito" name="idEsito" class="form-control">
				<option value="0" selected="selected"></option>
							<c:if test="${ listaEsitoAtto != null }">
						<c:forEach items="${listaEsitoAtto}" var="oggetto">
							<option value="${ oggetto.id }" id="${oggetto.codGruppoLingua }">
								<c:out value="${oggetto.descrizione}"></c:out>
							</option>
						</c:forEach>
						</c:if>
					</select>
				</div>
			</div>

														<!--PAGAMENTO DOVUTO-->
														<div class="form-group" id="formPagamentoDovuto" style="display: none;">
															<label class="col-md-3 control-label"
																for="pagamentoDovuto"><spring:message
																	text="??atto.label.pagamentodovuto??"
																	code="atto.label.pagamentodovuto" /></label>
															<div class="col-md-8">
																<input type="number" id="pagamentoDovuto" min="0" name="pagamentoDovuto"
																	value=""
																	class="form-control" />
															</div>
														</div>
														<!-- Text input-->
														<!--SPESE DI LITE A CARICO-->
														<div class="form-group" id="speseCarico"
															style="display: none;">
															<label class="col-md-3 control-label"
																for="speseCarico"><spring:message
																	text="??atto.label.speseCarico??"
																	code="atto.label.speseCarico" /></label>
															<div class="col-md-8">
																<input type="number" id="speseCarico" min="0" name="speseCarico"
																	value=""
																	class="form-control" />
															</div>
														</div>
														<!-- Text input-->
														<!--SPESE DI LITE A FAVORE-->
														<div class="form-group" id="speseFavore"
															style="display: none;">
															<label class="col-md-3 control-label"
																for="speseFavore"><spring:message
																	text="??atto.label.speseFavore??"
																	code="atto.label.speseFavore" /></label>
															<div class="col-md-8">
																<input type="number" id="speseFavore" min="0" name="speseFavore"
																	value=""
																	class="form-control" />
															</div>
														</div>
														<!-- Text input-->
														<!-- Text input-->
			<div class="form-group">
				<label class="col-md-3 control-label"
					for="destinatario"><spring:message text="??atto.label.destinatario??" code="atto.label.destinatario" /></label>
				<div class="col-md-8">
					<select id="destinatario" name="destinatario"
						class="form-control">
						<option></option>
						<c:if test="${ listaDestinatario != null }">
						<c:forEach items="${listaDestinatario}" var="oggetto">
							<c:choose>
							<c:when test="${oggetto.assente eq 'T'}">
							<option value="${ oggetto.matricolaUtil }" style="background:#ff0000;color:#ffffff">
								<c:out value="${oggetto.nominativoUtil}"></c:out>
								<c:if test="${ oggetto.assente eq 'T' }">  (ASSENTE)</c:if>
							</option> 
							</c:when>						 
							<c:otherwise>
								<option value="${ oggetto.matricolaUtil }" >
								<c:out value="${oggetto.nominativoUtil}"></c:out>
							</option>
							</c:otherwise>
							</c:choose>
						</c:forEach>
						</c:if>
						
					</select>
				</div>
			</div>
	
			<!-- Text input-->
			<!-- Text input-->
			<div class="form-group">
				<label class="col-md-3 control-label"
					for="tipoAtto"><spring:message text="??atto.label.tipoAtto??" code="atto.label.tipoAtto" /></label>
				<div class="col-md-8">
					<input id="tipoAtto" name="tipoAtto"
						type="text" class="typeahead form-control input-md" value="" >  
				</div>
			</div>
					<!-- Text input-->
			<div class="form-group">
				<label class="col-md-3 control-label"
					for="foroCompetente"><spring:message text="??atto.label.foroCompetente??" code="atto.label.foroCompetente" /></label>
				<div class="col-md-8">
					<input id="foroCompetente" name="foroCompetente"
						type="text" class="typeahead form-control input-md" value="" >  
				</div>
			</div>
			<!-- Text input-->
			<div class="form-group">
				<label class="col-md-3 control-label"
					for="dataNotifica"><spring:message text="??atto.label.dataNotifica??" code="atto.label.dataNotifica" /></label>
				<div class="col-md-8">
					<input id="dataNotifica" name="dataNotifica"
						type="text" class="typeahead form-control input-md date-picker" value="" >  
				</div>
			</div>
			<!-- Text input-->																												<!-- Text input-->
			<div class="form-group">
				<label class="col-md-3 control-label"
					for="note"><spring:message text="??atto.label.note??" code="atto.label.note" /></label>
				<div class="col-md-8">
					<input id="note" name="note"
						type="text" class="typeahead form-control input-md" value="" >  
				</div>
			</div>
			<!-- Text input-->
		<div class="form-group">
				<label class="col-md-3 control-label"
					for="creatoDa"><spring:message text="??atto.label.creatoDa??" code="atto.label.creatoDa" /></label>
				<div class="col-md-8">
					<input id="creatoDa" name="creatoDa" type="hidden"  value="<c:out value="${creatoDa}"></c:out>" >
					<input type="text" class="typeahead form-control input-md" disabled="disabled" value="<c:out value="${creatoDaNome}"></c:out>" >  
				</div>
			</div>
			<!-- Text input--> 
		 
			<div class="form-group">
				<label class="col-md-3 control-label"
					for="dataCreazione"><spring:message text="??atto.label.dataCreazione??" code="atto.label.dataCreazione" /></label>
				<div class="col-md-8">
					<input id="dataCreazione" name="dataCreazione"
						type="text" class="typeahead form-control input-md" disabled="disabled" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${dataCreazione}"/>" >  
				</div>
			</div>
	 
			<!-- Text input-->
			<!--
			<div class="form-group">
				<label class="col-md-3 control-label"
					for="dataUltimaModifica"><spring:message text="??atto.label.dataUltimaModifica??" code="atto.label.dataUltimaModifica" /></label>
				<div class="col-md-8">
					<input id="dataUltimaModifica" name="dataUltimaModifica"
						type="text" class="typeahead form-control input-md date-picker" value="" >  
				</div>
			</div>
			-->
			<!-- Text input-->	
				<div class="form-group">
				<label class="col-md-3 control-label"
					for="stato"><spring:message text="??atto.label.stato??" code="atto.label.stato" /></label>
				<div class="col-md-8">
					<input id="stato" name="stato"
						type="text" class="typeahead form-control input-md" disabled="disabled" value="BOZZA" > 
					<input id="idStatoAtto" name="idStatoAtto"
						type="hidden" class="typeahead form-control input-md" value="1" >  						
				</div>
			</div>
			<!-- Text input-->	
				<div class="form-group">
				<label class="col-md-3 control-label"
					for="dataUdienza"><spring:message text="??atto.label.dataUdienza??" code="atto.label.dataUdienza" /></label>
				<div class="col-md-8">
					<input id="dataUdienza"  name="dataUdienza"
						type="text" class="typeahead form-control input-md date-picker" value="" >  
				</div>
			</div>
			<!-- Text input-->	
				<div class="form-group">
				<label class="col-md-3 control-label"
					for="rilevante"><spring:message text="??atto.label.rilevante??" code="atto.label.rilevante" /></label>
				<div class="col-md-8">
						<select id="rilevante" name="rilevante"
						class="form-control">
						<option></option>
						<option value="T"><spring:message text="??atto.label.si??" code="atto.label.si" /></option>
						<option value="F"><spring:message text="??atto.label.no??" code="atto.label.no" /></option>
					</select> 
				</div>
			</div>
			
			<c:if test="${ uuidPec == null }">
			
				<input id="uuidPecAtto"  name="uuidPecAtto"	type="hidden" value="">
			<div class="form-group">
				<label class="col-md-3 control-label"
					for="rilevante"><spring:message text="??atto.button.addDocument??" code="atto.button.addDocument" /></label>
				<div class="form-group"> 
		<div class="col-md-8">
			<input type="file" name="file" id="file">
		</div>
		</div>
		    </div>
		    
		    </c:if>
	
		    <!-- Gestione Creazione Atto da PEC-->
		    
		    <c:if test="${ uuidPec != null }">
		    
		    	<input id="uuidPecAtto"  name="uuidPecAtto"	type="hidden" value="${ uuidPec }" >
	  			<div class="form-group" id="pecAttachDivAtto" >
					<label class="col-md-3 control-label"><spring:message text="??pecMail.label.pecAllegata??" code="pecMail.label.pecAllegata" /></label>
					<div class="col-md-8">
						<button style="float:left" type="button" data-toggle="dropdown" id="download-pecAttachAtto" uuid="${ uuidPec }"	class="btn btn-success dropdown-toggle">
						<spring:message text="??pecMail.button.scaricaPec??" code="pecMail.button.scaricaPec" /> 
						<i class="fa fa-arrow-circle-down"></i>
						</button>
					</div>
	    </div>
		    
		    	<div class="form-group" style="display: none;">
		    		<label class="col-md-3 control-label"
						for="rilevante"><spring:message text="??atto.button.addDocument??" code="atto.button.addDocument" /></label>
					<div class="form-group"> 
						<div class="col-md-8">
							<input type="file" name="file" id="file">
						</div>
					</div>
		    	</div>
		    
		    </c:if>
		    
		    <!-- Fine Gestione Creazione Atto da PEC-->
		    
			<!-- Text input-->	
			<!-- Text input-->	

			<!-- Text input-->	
		
			<!-- Text input-->	

			<!-- Text input-->																

		</fieldset>
	 
	 
	 
	 		<div class="modal-footer">
			<div class="col-md-12 column">
    <div class="btn-group dropup pull-right ">
<div class="btn-group pull-right space-to-left">
	<button id="save" type="submit"
		class="btn btn-primary dropdown-toggle"
		style="margin-left: 5px">
<spring:message text="??atto.button.save??" code="atto.button.save" /> <i class="fa fa-save"></i>
	</button>
</div>
<!-- pulsante esporta senza opzioni -->
<div class="btn-group pull-right">
	<button type="button" 
		class="btn btn-warning" onclick="javascript:history.back();">
		<spring:message text="??atto.button.annulla??" code="atto.button.annulla" /> <i class="fa"></i>
			</button>
		 
		</div>
	</div>
</div>
	</div>
	</form:form>
			</div>

		</div>
			</div>
			 	</div>

		 

			<!-- Fine Area Crea Atto-->
	</div>
</div><!--/ fine card -->
		</div>
	</div>
</div>
<!--/ fine col-1 -->
	</section>
<form id="downloadPecAttachAtto" action="../download" method="get" style="display:none">
   <engsecurity:token regenerate="false"/>	
	<input type="hidden" name="uuid" id="uuid" value="">
	<input type="hidden" name="onlyfn" id="onlyfn" value="1">
	<input type="hidden" name="isp" id="isp" value="1">
	<input type="submit" value="" style="display:none">
</form>
</section>


<footer>
	<jsp:include page="/parts/footer.jsp"></jsp:include>
</footer>
<jsp:include page="/parts/script-end.jsp"></jsp:include>


<script src="<%=request.getContextPath()%>/portal/js/controller/fascicolo.js"></script>
<script src="<%=request.getContextPath()%>/portal/js/controller/atto.js"></script>



</body>
</html>
