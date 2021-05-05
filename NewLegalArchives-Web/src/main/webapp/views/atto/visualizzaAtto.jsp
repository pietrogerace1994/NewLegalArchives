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
							Crea Nuovo Atto	 
							</h2>
						 	 
					</div>
					<div class="card-body">
					
					<!-- Area Crea Atto-->
			
		<div class="table-responsive">

			<div class="col-md-12">
				<div class="col-md-12">
					<div class="col-md-12">
						<h4>Nuovo Atto</h4>
					</div>
					<div class="col-md-12">
					<form:form name="fascicoloForm" method="post"
									modelAttribute="attoView" action="atto/save.action" class="form-horizontal">
						 <engsecurity:token regenerate="false"/>
							<fieldset>
								<!-- Form Name 
                                                 <legend>Migliora la ricerca</legend>-->
										<!-- Text input-->
								<div class="form-group">
									<label class="col-md-3 control-label"
										for="idSocieta">SOCIETA'/DIVISIONE INTERESSATA</label>
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
										for="parteNotificante">PARTE NOTIFICANTE</label>
									<div class="col-md-8">
										<input id="parteNotificante" name="parteNotificante"
											type="text" class="typeahead form-control input-md" value="MilanoSnam" >  
									</div>
								</div>
								<!-- Text input-->
								
								<!-- Text input-->
								<div class="form-group">
									<label class="col-md-3 control-label"
										for="idCategoriaAtto">CATEGORIA ATTO</label>
									<div class="col-md-8">
									<select id="idCategoriaAtto" name="idCategoriaAtto" class="form-control">
									<option></option>
												<c:if test="${ listaCategorie != null }">
											<c:forEach items="${listaCategorie}" var="oggetto">
												<option value="${ oggetto.id }">
													<c:out value="${oggetto.nome}"></c:out>
												</option>
											</c:forEach>
											</c:if>
										</select>
									</div>
								</div>
								<!-- Text input-->
								<!-- Text input-->
																																									<!-- Text input-->
								<div class="form-group">
									<label class="col-md-3 control-label"
										for="destinatario">DESTINATARIO</label>
									<div class="col-md-8">
										<select id="destinatario" name="destinatario"
											class="form-control">
											<option></option>
											<c:if test="${ listaDestinatario != null }">
											<c:forEach items="${listaDestinatario}" var="oggetto">
												<option value="${ oggetto.matricolaUtil }">
													<c:out value="${oggetto.nominativoUtil}"></c:out>
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
										for="tipoAtto">TIPO ATTO GIUDIZIARIO</label>
									<div class="col-md-8">
										<input id="tipoAtto" name="tipoAtto"
											type="text" class="typeahead form-control input-md" value="Fallimentare" >  
									</div>
								</div>
										<!-- Text input-->
								<div class="form-group">
									<label class="col-md-3 control-label"
										for="foroCompetente">FORO COMPETENTE</label>
									<div class="col-md-8">
										<input id="foroCompetente" name="foroCompetente"
											type="text" class="typeahead form-control input-md" value="Roma" >  
									</div>
								</div>
								<!-- Text input-->
								<div class="form-group">
									<label class="col-md-3 control-label"
										for="dataNotifica">DATA DELLA NOTIFICA</label>
									<div class="col-md-8">
										<input id="dataNotifica" name="dataNotifica"
											type="text" class="typeahead form-control input-md date-picker" value="12/07/2016" >  
									</div>
								</div>
								<!-- Text input-->																												<!-- Text input-->
								<div class="form-group">
									<label class="col-md-3 control-label"
										for="note">NOTE</label>
									<div class="col-md-8">
										<input id="note" name="note"
											type="text" class="typeahead form-control input-md" value="note atto xxxxx" >  
									</div>
								</div>
								<!-- Text input-->
							<div class="form-group">
									<label class="col-md-3 control-label"
										for="creatoDa">CREATO DA</label>
									<div class="col-md-8">
										<input id="creatoDa" name="creatoDa"
											type="text" class="typeahead form-control input-md" value="Marco La Mura" >  
									</div>
								</div>
								<!-- Text input-->
								<div class="form-group">
									<label class="col-md-3 control-label"
										for="dataCreazione">DATA CREAZIONE</label>
									<div class="col-md-8">
										<input id="dataCreazione" name="dataCreazione"
											type="text" class="typeahead form-control input-md date-picker" value="10/07/2016" >  
									</div>
								</div>
								<!-- Text input-->
								<div class="form-group">
									<label class="col-md-3 control-label"
										for="dataUltimaModifica">DATA ULTIMA MODIFICA</label>
									<div class="col-md-8">
										<input id="dataUltimaModifica" name="dataUltimaModifica"
											type="text" class="typeahead form-control input-md date-picker" value="10/08/2016" >  
									</div>
								</div>
								<!-- Text input-->	
									<div class="form-group">
									<label class="col-md-3 control-label"
										for="stato">STATO</label>
									<div class="col-md-8">
										<input id="stato" name="stato"
											type="text" class="typeahead form-control input-md" value="Bozza" >  
									</div>
								</div>
								<!-- Text input-->	
									<div class="form-group">
									<label class="col-md-3 control-label"
										for="dataUdienza">DATA UDIENZA</label>
									<div class="col-md-8">
										<input id="dataUdienza"  name="dataUdienza"
											type="text" class="typeahead form-control input-md date-picker" value="10/02/2017" >  
									</div>
								</div>
								<!-- Text input-->	
									<div class="form-group">
									<label class="col-md-3 control-label"
										for="rilevante">RILEVANZA</label>
									<div class="col-md-8">
											<select id="rilevante" name="rilevante"
											class="form-control">
											<option></option>
											<option value="I">SI</option>
											<option value="NO">NO</option>
										</select> 
									</div>
								</div>
								<!-- Text input-->	
								<!-- Text input-->	
									<div class="form-group">
									<label class="col-md-3 control-label"
										for="utenteInvioAltriUffici">UTENTE INV ALTRI UFF</label>
									<div class="col-md-8">
										<input id="utenteInvioAltriUffici" name="utenteInvioAltriUffici"
											type="text" class="typeahead form-control input-md" value="xxxxxxxxxxxx" >  
									</div>
								</div>
								<!-- Text input-->	
									<div class="form-group">
									<label class="col-md-3 control-label"
										for="unitaLegInvioAltriUffici">UL INV ALTRI UFF</label>
									<div class="col-md-8">
										<input id="unitaLegInvioAltriUffici" name="unitaLegInvioAltriUffici"
											type="text" class="typeahead form-control input-md" value="xxxxxxxxxxxx" >  
									</div>
								</div>
								<!-- Text input-->	
									<div class="form-group">
									<label class="col-md-3 control-label"
										for="emailInvioAltriUffici">EMAIL INV ALTRI UFF</label>
									<div class="col-md-8">
										<input id="emailInvioAltriUffici" name="emailInvioAltriUffici"
											type="text" class="typeahead form-control input-md" value="xxxxxxxxxx" >  
									</div>
								</div>
								<!-- Text input-->																

							</fieldset>
						 
						</form:form>
					</div>
					<div class="modal-footer">
					<div class="col-md-12 column">
	     <div class="btn-group dropup pull-right ">
		<div class="btn-group pull-right space-to-left">
			<button id="save" type="button"
				class="btn btn-primary dropdown-toggle"
				style="margin-left: 5px">
				<i class="fa fa-save"></i> Salva
			</button>
		</div>
		<!-- pulsante esporta senza opzioni -->
		<div class="btn-group pull-right">
			<button type="button" 
				class="btn btn-success">
				Annulla <i class="fa"></i>
			</button>
		 
		</div>
	</div>
</div>
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

</section>


<footer>
	<jsp:include page="/parts/footer.jsp"></jsp:include>
</footer>
<jsp:include page="/parts/script-end.jsp"></jsp:include>


<script src="<%=request.getContextPath()%>/portal/js/controller/fascicolo.js"></script>
<script src="<%=request.getContextPath()%>/portal/js/controller/atto.js"></script>

<c:if test="${ not empty fascicoloView.jsonAlberaturaMaterie }">
<script type="text/javascript">
var jsonDataMaterie = ${fascicoloView.jsonAlberaturaMaterie }

var materieDaSelezionare = new Array();
</script>
</c:if>

<c:if test="${empty fascicoloView.jsonAlberaturaMaterie} ">
<script type="text/javascript">
var jsonDataMaterie = null;
</script>
</c:if>

<c:if test="${ not empty fascicoloView.materie }">
<c:forEach items="${fascicoloView.materie}" var="materiaCodice">
<script type="text/javascript">
materieDaSelezionare.push("${materiaCodice}");
</script>
</c:forEach>
</c:if>

<script type="text/javascript">
function selezionaMaterie() {
	if (materieDaSelezionare) {
		for (i = 0; i < materieDaSelezionare.length; i++) {
			document.getElementById(materieDaSelezionare[i]).checked = true;
		}
	}
}

$(document)
		.ready(
				function() {
					if ($('#treeContainerMaterie')
							&& jsonDataMaterie != null
							&& jsonDataMaterie != undefined) {
						$('#treeContainerMaterie').easytree({
							data : jsonDataMaterie,
							built : selezionaMaterie
						});
					}

					if ($("#txtControparte")) {
						autocompleteNomeControparte("txtControparte");
					}

					if ($("#txtTerzoChiamatoInCausa")) {
						autocompleteTerzoChiamatoInCausa("txtTerzoChiamatoInCausa");
					}

					if ($("#txtForo")) {
						autocompleteForo("txtForo");
					}

					if ($("#txtAutoritaEmanante")) {
						autocompleteAutoritaEmanante("txtAutoritaEmanante");
					}

					if ($("#txtControinteressato")) {
						autocompleteControinteressato("txtControinteressato");
					}

					if ($("#txtAutoritaGiudiziaria")) {
						autocompleteAutoritaGiudiziaria("txtAutoritaGiudiziaria");
					}

					if ($("#txtSoggettoIndagato")) {
						autocompleteSoggettoIndagato("txtSoggettoIndagato");
					}

					if ($("#txtPersonaOffesa")) {
						autocompletePersonaOffesa("txtPersonaOffesa");
					}

					if ($("#txtParteCivile")) {
						autocompleteParteCivile("txtParteCivile");
					}

					if ($("#txtResponsabileCivile")) {
						autocompleteResponsabileCivile("txtResponsabileCivile");
					}
				});
</script>


</body>
</html>
