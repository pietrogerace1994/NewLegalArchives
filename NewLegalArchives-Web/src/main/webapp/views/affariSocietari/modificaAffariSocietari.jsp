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

<jsp:include page="/parts/script-init.jsp"></jsp:include>

<style>
.disabled {
	pointer-events: none;
	cursor: not-allowed;
}

.paddMultifile {
	padding-top: 5px;
}
</style>
</head>

<body data-ma-header="teal">
	<% String disableInVis = request.getAttribute("disableInVis")==null?"false":"true";  %>

	<jsp:include page="/parts/header.jsp"></jsp:include>

	<!-- SECION MAIN -->
	<section id="main">
		<jsp:include page="/parts/aside.jsp"></jsp:include>

		<!-- SECTION CONTENT -->
		<section id="content">
			<div class="container">
				<div class="row">
					<div id="col-1" class="col-lg-12 col-md-12 col-sm-12 col-sx-12">
						<div class="card">
							<div class="card-header ch-dark palette-Green-SNAM bg">
								<% if ("true".equals(disableInVis)){ %>							
									<h2>
										<spring:message text="??affariSocietari.label.visualizza??"
											code="affariSocietari.label.visualizza" />
									</h2>
								<% }else{ %>
									<h2>
										<spring:message text="??affariSocietari.label.modifica??"
											code="affariSocietari.label.modifica" />
									</h2>
								<% } %>							
							</div>

							<!-- FORM PARTE CORRELATA -->
							<div class="card-body">
							<form:form id="affariSocietariForm" name="affariSocietariForm"
									method="post" modelAttribute="affariSocietariView"
									action="salva.action" class="form-horizontal la-form"
									enctype="multipart/form-data">
									<engsecurity:token regenerate="false"/>

									<form:errors path="*" cssClass="alert alert-danger"
										htmlEscape="false" element="div"></form:errors>
									<form:hidden path="op" id="op" value="salvaAffariSocietari" />


									<c:if test="${ not empty param.successMessage }">
										<div class="alert alert-info">
											<spring:message code="messaggio.operazione.ok"
												text="??messaggio.operazione.ok??"></spring:message>
										</div>
									</c:if>

									<c:if test="${ not empty param.errorMessage }">
										<div class="alert alert-danger">
											<spring:message code="${param.errorMessage}"
												text="??${param.errorMessage}??"></spring:message>
										</div>
									</c:if>


									<div id="creaDiv">

									<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="denominazione" class="col-sm-2 control-label"><spring:message
															text="??affariSocietari.label.denominazione??"
															code="affariSocietari.label.denominazione" /></label>
													<div class="col-sm-10">
														<form:input path="denominazione" cssClass="form-control" maxlength="200" disabled="<%=disableInVis %>"
															id="txtDenominazione"
															value="${affariSocietariView.getVo().denominazione}" />
													</div>
												</div>
											</div>
										</div>

										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="codiceSocieta" class="col-sm-2 control-label"><spring:message
															text="??affariSocietari.label.codiceSocieta??"
															code="affariSocietari.label.codiceSocieta" /></label>
													<div class="col-sm-10">
														<form:input path="codiceSocieta" cssClass="form-control" maxlength="1000" disabled="true"
															id="txtCodiceSocieta"
															value="${affariSocietariView.getVo().codiceSocieta}" />
													</div>
												</div>
											</div>
										</div>
										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="capitaleSottoscritto" class="col-sm-2 control-label"><spring:message
															text="??affariSocietari.label.capitaleSottoscritto??"
															code="affariSocietari.label.capitaleSottoscritto" /></label>
													<div class="col-sm-10">
														<form:input path="capitaleSottoscritto" cssClass="form-control" maxlength="1000" disabled="<%=disableInVis %>"
															id="txtCapitaleSottoscritto"
															value="${affariSocietariView.getVo().capitaleSottoscritto}" />
													</div>
												</div>
											</div>
										</div>
										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="capitaleSociale" class="col-sm-2 control-label"><spring:message
															text="??affariSocietari.label.capitaleSociale??"
															code="affariSocietari.label.capitaleSociale" /></label>
													<div class="col-sm-10">
														<form:input path="capitaleSociale" cssClass="form-control" maxlength="1000" disabled="<%=disableInVis %>"
															id="txtCapitaleSociale"
															value="${affariSocietariView.getVo().capitaleSociale}" />
													</div>
												</div>
											</div>
										</div>
										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="dataCostituzione" class="col-sm-2 control-label"><spring:message
															text="??affariSocietari.label.dataCostituzione??"
															code="affariSocietari.label.dataCostituzione" /></label>
													<div class="col-sm-10">
														<form:input path="dataCostituzione" cssClass="form-control date-picker" maxlength="1000" disabled="<%=disableInVis %>"
															id="txtDataCostituzione"
															value="${affariSocietariView.getVo().dataCostituzione}" />
													</div>
												</div>
											</div>
										</div>
										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="dataUscita" class="col-sm-2 control-label"><spring:message
															text="??affariSocietari.label.dataUscita??"
															code="affariSocietari.label.dataUscita" /></label>
													<div class="col-sm-10">
														<form:input path="dataUscita" cssClass="form-control date-picker" maxlength="1000" disabled="<%=disableInVis %>"
															id="txtDataUscita"
															value="${affariSocietariView.getVo().dataUscita}" />
													</div>
												</div>
											</div>
										</div>
										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="denominazioneBreve" class="col-sm-2 control-label"><spring:message
															text="??affariSocietari.label.denominazioneBreve??"
															code="affariSocietari.label.denominazioneBreve" /></label>
													<div class="col-sm-10">
														<form:input path="denominazioneBreve" cssClass="form-control" maxlength="1000" disabled="<%=disableInVis %>"
															id="txtDenominazioneBreve"
															value="${affariSocietariView.getVo().denominazioneBreve}" />
													</div>
												</div>
											</div>
										</div>
										
																				<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="siglaFormaGiuridica" class="col-sm-2 control-label"><spring:message
															text="??affariSocietari.label.siglaFormaGiuridica??"
															code="affariSocietari.label.siglaFormaGiuridica" /></label>
													<div class="col-sm-10">
														<form:input path="siglaFormaGiuridica" cssClass="form-control" maxlength="1000" disabled="<%=disableInVis %>"
															id="txtSiglaFormaGiuridica"
															value="${affariSocietariView.getVo().siglaFormaGiuridica}" />
													</div>
												</div>
											</div>
										</div>
										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="formaGiuridica" class="col-sm-2 control-label"><spring:message
															text="??affariSocietari.label.formaGiuridica??"
															code="affariSocietari.label.formaGiuridica" /></label>
													<div class="col-sm-10">
														<form:input path="formaGiuridica" cssClass="form-control" maxlength="1000" disabled="<%=disableInVis %>"
															id="txtFormaGiuridica"
															value="${affariSocietariView.getVo().formaGiuridica}" />
													</div>
												</div>
											</div>
										</div>
										
										<div class="list-group lg-alt">
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
														<label for="idNazione" class="col-sm-2 control-label"><spring:message
																text="??affariSocietari.label.nazione??"
																code="affariSocietari.label.nazione" /></label>
														<div class="col-sm-10">
															<form:select path="idNazione" disabled="<%=disableInVis %>"
																cssClass="form-control">
																<form:option value="">
																	<spring:message
																		text="??affariSocietari.label.selezionaNazione??"
																		code="affariSocietari.label.selezionaNazione" />
																</form:option>
																<c:if
																	test="${ affariSocietariView.listaNazioni != null }">
																	<c:forEach
																		items="${affariSocietariView.listaNazioni}"
																		var="oggetto">

																		<form:option value="${ oggetto.getVo().id }">
																			<c:out value="${oggetto.getVo().descrizione}"></c:out>
																		</form:option>

																	</c:forEach>
																</c:if>
															</form:select>
														</div>
													</div>
												</div>
											</div>
										</div>

										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="siglaStatoProvincia" class="col-sm-2 control-label"><spring:message
															text="??affariSocietari.label.siglaStatoProvincia??"
															code="affariSocietari.label.siglaStatoProvincia" /></label>
													<div class="col-sm-10">
														<form:input path="siglaStatoProvincia" cssClass="form-control" maxlength="1000" disabled="<%=disableInVis %>"
															id="txtSiglaStatoProvincia"
															value="${affariSocietariView.getVo().siglaStatoProvincia}" />
													</div>
												</div>
											</div>
										</div>
										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="siglaProvincia" class="col-sm-2 control-label"><spring:message
															text="??affariSocietari.label.siglaProvincia??"
															code="affariSocietari.label.siglaProvincia" /></label>
													<div class="col-sm-10">
														<form:input path="siglaProvincia" cssClass="form-control" maxlength="1000" disabled="<%=disableInVis %>"
															id="txtSiglaProvincia"
															value="${affariSocietariView.getVo().siglaProvincia}" />
													</div>
												</div>
											</div>
										</div>
										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">

													<label for="italiaEstero" class="col-sm-2 control-label">
														<spring:message
															text="??affariSocietari.label.italiaEstero??"
															code="affariSocietari.label.italiaEstero" />
													</label>
													<div class="col-sm-10">
														<label class="col-sm-1 control-label">
															<spring:message
																text="??affariSocietari.label.italia??"
																code="affariSocietari.label.italia" />
														</label>
														<div class="col-sm-1">
															<form:radiobutton path="italiaEstero" value="I" disabled="<%=disableInVis %>" />
														</div>
														
														<label class="col-sm-1 control-label">
															<spring:message
																text="??affariSocietari.label.estero??"
																code="affariSocietari.label.estero" />
														</label>
														<div class="col-sm-1">
															<form:radiobutton path="italiaEstero" value="E" disabled="<%=disableInVis %>" />
														</div>
													
													</div>
												</div>
											</div>
										</div>

										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">

													<label for="ueExstraue" class="col-sm-2 control-label">
														<spring:message
															text="??affariSocietari.label.ueExstraue??"
															code="affariSocietari.label.ueExstraue" />
													</label>
													<div class="col-sm-10">
														<label class="col-sm-1 control-label">
															<spring:message
																text="??affariSocietari.label.ue??"
																code="affariSocietari.label.ue" />
														</label>
														<div class="col-sm-1">
															<form:radiobutton path="ueExstraue" value="UE"  disabled="<%=disableInVis %>"/>
														</div>
														
														<label class="col-sm-1 control-label">
															<spring:message
																text="??affariSocietari.label.exstraue??"
																code="affariSocietari.label.exstraue" />
														</label>
														<div class="col-sm-1">
															<form:radiobutton path="ueExstraue" value="EUE" disabled="<%=disableInVis %>" />
														</div>
													
													</div>
												</div>
											</div>
										</div>
										
										
										
										
										
										
	
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="sedeLegale" class="col-sm-2 control-label"><spring:message
															text="??affariSocietari.label.sedeLegale??"
															code="affariSocietari.label.sedeLegale" /></label>
													<div class="col-sm-10">
														<form:input path="sedeLegale" cssClass="form-control" maxlength="1000" disabled="<%=disableInVis %>"
															id="txtSedeLegale"
															value="${affariSocietariView.getVo().sedeLegale}" />
													</div>
												</div>
											</div>
										</div>											
										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="indirizzo" class="col-sm-2 control-label"><spring:message
															text="??affariSocietari.label.indirizzo??"
															code="affariSocietari.label.indirizzo" /></label>
													<div class="col-sm-10">
														<form:input path="indirizzo" cssClass="form-control" maxlength="1000" disabled="<%=disableInVis %>"
															id="txtIndirizzo"
															value="${affariSocietariView.getVo().indirizzo}" />
													</div>
												</div>
											</div>
										</div>
										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="cap" class="col-sm-2 control-label"><spring:message
															text="??affariSocietari.label.cap??"
															code="affariSocietari.label.cap" /></label>
													<div class="col-sm-10">
														<form:input path="cap" cssClass="form-control" maxlength="1000" disabled="<%=disableInVis %>"
															id="txtCap"
															value="${affariSocietariView.getVo().cap}" />
													</div>
												</div>
											</div>
										</div>														
										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="codiceFiscale" class="col-sm-2 control-label"><spring:message
															text="??affariSocietari.label.codiceFiscale??"
															code="affariSocietari.label.codiceFiscale" /></label>
													<div class="col-sm-10">
														<form:input path="codiceFiscale" cssClass="form-control" maxlength="1000" disabled="<%=disableInVis %>"
															id="txtCodiceFiscale"
															value="${affariSocietariView.getVo().codiceFiscale}" />
													</div>
												</div>
											</div>
										</div>
										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="partitaIva" class="col-sm-2 control-label"><spring:message
															text="??affariSocietari.label.partitaIva??"
															code="affariSocietari.label.partitaIva" /></label>
													<div class="col-sm-10">
														<form:input path="partitaIva" cssClass="form-control" maxlength="1000" disabled="<%=disableInVis %>"
															id="txtPartitaIva"
															value="${affariSocietariView.getVo().partitaIva}" />
													</div>
												</div>
											</div>
										</div>		

										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="dataRea" class="col-sm-2 control-label"><spring:message
															text="??affariSocietari.label.dataRea??"
															code="affariSocietari.label.dataRea" /></label>
													<div class="col-sm-10">
														<form:input path="dataRea" cssClass="form-control date-picker" maxlength="1000" disabled="<%=disableInVis %>"
															id="txtDataRea"
															value="${affariSocietariView.getVo().dataRea}" />
													</div>
												</div>
											</div>
										</div>
										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="numeroRea" class="col-sm-2 control-label"><spring:message
															text="??affariSocietari.label.numeroRea??"
															code="affariSocietari.label.numeroRea" /></label>
													<div class="col-sm-10">
														<form:input path="numeroRea" cssClass="form-control" maxlength="1000" disabled="<%=disableInVis %>"
															id="txtNumeroRea"
															value="${affariSocietariView.getVo().numeroRea}" />
													</div>
												</div>
											</div>
										</div>
										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="comuneRea" class="col-sm-2 control-label"><spring:message
															text="??affariSocietari.label.comuneRea??"
															code="affariSocietari.label.comuneRea" /></label>
													<div class="col-sm-10">
														<form:input path="comuneRea" cssClass="form-control" maxlength="1000" disabled="<%=disableInVis %>"
															id="txtComuneRea"
															value="${affariSocietariView.getVo().comuneRea}" />
													</div>
												</div>
											</div>
										</div>
										
																				
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="siglaProvinciaRea" class="col-sm-2 control-label"><spring:message
															text="??affariSocietari.label.siglaProvinciaRea??"
															code="affariSocietari.label.siglaProvinciaRea" /></label>
													<div class="col-sm-10">
														<form:input path="siglaProvinciaRea" cssClass="form-control" maxlength="1000" disabled="<%=disableInVis %>"
															id="txtSiglaProvinciaRea"
															value="${affariSocietariView.getVo().siglaProvinciaRea}" />
													</div>
												</div>
											</div>
										</div>
										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="provinciaRea" class="col-sm-2 control-label"><spring:message
															text="??affariSocietari.label.provinciaRea??"
															code="affariSocietari.label.provinciaRea" /></label>
													<div class="col-sm-10">
														<form:input path="provinciaRea" cssClass="form-control" maxlength="1000" disabled="<%=disableInVis %>"
															id="txtProvinciaRea"
															value="${affariSocietariView.getVo().provinciaRea}" />
													</div>
												</div>
											</div>
										</div>

										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">

													<label for="quotazione" class="col-sm-2 control-label">
														<spring:message
															text="??affariSocietari.label.quotazione??"
															code="affariSocietari.label.quotazione" />
													</label>
													<div class="col-sm-10">
														<label class="col-sm-1 control-label">
															<spring:message
																text="??affariSocietari.label.si??"
																code="affariSocietari.label.si" />
														</label>
														<div class="col-sm-1">
															<form:radiobutton path="quotazione" value="SI" disabled="<%=disableInVis %>" />
														</div>
														
														<label class="col-sm-1 control-label">
															<spring:message
																text="??affariSocietari.label.no??"
																code="affariSocietari.label.no" />
														</label>
														<div class="col-sm-1">
															<form:radiobutton path="quotazione" value="NO" disabled="<%=disableInVis %>" />
														</div>
													
													</div>
												</div>
											</div>
										</div>
										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">

													<label for="inLiquidazione" class="col-sm-2 control-label">
														<spring:message
															text="??affariSocietari.label.inLiquidazione??"
															code="affariSocietari.label.inLiquidazione" />
													</label>
													<div class="col-sm-10">
														<label class="col-sm-1 control-label">
															<spring:message
																text="??affariSocietari.label.si??"
																code="affariSocietari.label.si" />
														</label>
														<div class="col-sm-1">
															<form:radiobutton path="inLiquidazione" value="SI" disabled="<%=disableInVis %>" />
														</div>
														
														<label class="col-sm-1 control-label">
															<spring:message
																text="??affariSocietari.label.no??"
																code="affariSocietari.label.no" />
														</label>
														<div class="col-sm-1">
															<form:radiobutton path="inLiquidazione" value="NO" disabled="<%=disableInVis %>" />
														</div>
													
													</div>
												</div>
											</div>
										</div>
										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">

													<label for="modelloDiGovernance" class="col-sm-2 control-label">
														<spring:message
															text="??affariSocietari.label.modelloDiGovernance??"
															code="affariSocietari.label.modelloDiGovernance" />
													</label>
													<div class="col-sm-10">
														<label class="col-sm-1 control-label">
															<spring:message
																text="??affariSocietari.label.monistico??"
																code="affariSocietari.label.monistico" />
														</label>
														<div class="col-sm-1">
															<form:radiobutton path="modelloDiGovernance" value="M" disabled="<%=disableInVis %>" />
														</div>
														
														<label class="col-sm-1 control-label">
															<spring:message
																text="??affariSocietari.label.dualistico??"
																code="affariSocietari.label.dualistico" />
														</label>
														<div class="col-sm-1">
															<form:radiobutton path="modelloDiGovernance" value="D" disabled="<%=disableInVis %>" />
														</div>
														
														<label class="col-sm-1 control-label">
															<spring:message
																text="??affariSocietari.label.tradizionale??"
																code="affariSocietari.label.tradizionale" />
														</label>
														<div class="col-sm-1">
															<form:radiobutton path="modelloDiGovernance" value="T" disabled="<%=disableInVis %>" />
														</div>
													
													</div>
												</div>
											</div>
										</div>
										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="nComponentiCDA" class="col-sm-2 control-label"><spring:message
															text="??affariSocietari.label.nComponentiCDA??"
															code="affariSocietari.label.nComponentiCDA" /></label>
													<div class="col-sm-10">
														<form:input path="nComponentiCDA" cssClass="form-control" maxlength="1000" disabled="<%=disableInVis %>"
															id="txtNComponentiCDA"
															value="${affariSocietariView.getVo().nComponentiCDA}" />
													</div>
												</div>
											</div>
										</div>		
										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="nComponentiCollegioSindacale" class="col-sm-2 control-label"><spring:message
															text="??affariSocietari.label.nComponentiCollegioSindacale??"
															code="affariSocietari.label.nComponentiCollegioSindacale" /></label>
													<div class="col-sm-10">
														<form:input path="nComponentiCollegioSindacale" cssClass="form-control" maxlength="1000" disabled="<%=disableInVis %>"
															id="txtNComponentiCollegioSindacale"
															value="${affariSocietariView.getVo().nComponentiCollegioSindacale}" />
													</div>
												</div>
											</div>
										</div>		
										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="nComponentiOdv" class="col-sm-2 control-label"><spring:message
															text="??affariSocietari.label.nComponentiOdv??"
															code="affariSocietari.label.nComponentiOdv" /></label>
													<div class="col-sm-10">
														<form:input path="nComponentiOdv" cssClass="form-control" maxlength="1000" disabled="<%=disableInVis %>"
															id="txtNComponentiOdv"
															value="${affariSocietariView.getVo().nComponentiOdv}" />
													</div>
												</div>
											</div>
										</div>
										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="codiceNazione" class="col-sm-2 control-label"><spring:message
															text="??affariSocietari.label.codiceNazione??"
															code="affariSocietari.label.codiceNazione" /></label>
													<div class="col-sm-10">
														<form:input path="codiceNazione" cssClass="form-control" maxlength="1000" disabled="<%=disableInVis %>"
															id="txtCodiceNazione"
															value="${affariSocietariView.getVo().codiceNazione}" />
													</div>
												</div>
											</div>
										</div>
										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="societaDiRevisione" class="col-sm-2 control-label"><spring:message
															text="??affariSocietari.label.societaDiRevisione??"
															code="affariSocietari.label.societaDiRevisione" /></label>
													<div class="col-sm-10">
														<form:input path="societaDiRevisione" cssClass="form-control" maxlength="1000" disabled="<%=disableInVis %>"
															id="txtSocietaDiRevisione"
															value="${affariSocietariView.getVo().societaDiRevisione}" />
													</div>
												</div>
											</div>
										</div>
										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="dataIncarico" class="col-sm-2 control-label"><spring:message
															text="??affariSocietari.label.dataIncarico??"
															code="affariSocietari.label.dataIncarico" /></label>
													<div class="col-sm-10">
														<form:input path="dataIncarico" cssClass="form-control date-picker" maxlength="1000" disabled="<%=disableInVis %>"
															id="txtDataIncarico"
															value="${affariSocietariView.getVo().dataIncarico}" />
													</div>
												</div>
											</div>
										</div>
										
										
										<% if ("true".equals(disableInVis)){ %>
											
										<div class="list-group lg-alt">
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group" id="box-controllante">
														<label for="idControllante" class="col-sm-2 control-label"><spring:message
																text="??affariSocietari.label.idControllante??"
																code="affariSocietari.label.idControllante" /></label>
																
																
														<div class="col-md-4">
															<select id="idControllante" name="idControllante" class="form-control" disabled>
																<option value="">
																	<spring:message text="??affariSocietari.label.idControllante??" code="affariSocietari.label.idControllante"/>
																</option>
																<c:if test="${ affariSocietariView.listaSocietaControllanti != null }">
																	<c:forEach items="${affariSocietariView.listaSocietaControllanti}" var="oggetto2">
																		<option value="${ oggetto2.id }"><c:out value="${oggetto2.descrizione}"></c:out></option>
																	</c:forEach>
																</c:if>
															</select>
														</div>
														
														<label for="percentualeControllante" class="col-sm-1 control-label"><spring:message
																text="??affariSocietari.label.percentualeControllante??"
																code="affariSocietari.label.percentualeControllante" /></label>
														<div class="col-sm-4">
															<input class="form-control" maxlength="1000"
																id="txtPercentualeControllante"
																value="${affariSocietariView.getVo().percentualeControllante}"  disabled/>
														</div>
														
														<div class="col-md-1">
															<button type="button" class="btn bg btn-float btn-circle-mini">
																<span class="glyphicon glyphicon-plus"></span>
									      					</button>
													</div>
													    
													    
													    <c:if test="${ affariSocietariView.sociAggiunti != null }">
															<c:forEach items="${affariSocietariView.sociAggiunti}" var="rAffariSocietari">
															
																<div class="col-md-12 box-dinamik" style="margin-top:25px;">
																	<div class="col-md-2"></div>
																	<div class="col-md-7">
																		<input name="idControllante" type="hidden" value="${rAffariSocietari.idSocietaSocio}" class="form-control"/>
																		<input value="${rAffariSocietari.descrizioneSocietaSocio}" class="form-control" type="text" disabled/>
																	</div> 
																	<div class="col-md-2">
																		<input value="${rAffariSocietari.percentualeSocio}" class="form-control" type="text" disabled>
												</div>
																	<div class="col-md-1" style="left:10px;">
																		<button type="button" class="btn bg btn-circle-mini">
																		<span class="fa fa-trash-o" aria-hidden="true"></span>
																		</button>
											</div>
										</div>
										
															</c:forEach>
														</c:if>
													    		
													</div>
												</div>
											</div>
										</div>
										<% }else{ %>
										<div class="list-group lg-alt">
											<div class="list-group-item media">
												<a name="anchorSoci"></a>
												<div class="media-body">
													<div class="form-group" id="box-controllante">
														<label for="idControllante" class="col-sm-2 control-label"><spring:message
																text="??affariSocietari.label.idControllante??"
																code="affariSocietari.label.idControllante" /></label>
																
																
														<div class="col-md-4">
															<select id="idControllante" name="idControllante" class="form-control">
																<option value="">
																	<spring:message text="??affariSocietari.label.idControllante??" code="affariSocietari.label.idControllante"/>
																</option>
																<c:if test="${ affariSocietariView.listaSocietaControllanti != null }">
																	<c:forEach items="${affariSocietariView.listaSocietaControllanti}" var="oggetto2">
																		<option value="${ oggetto2.id }"><c:out value="${oggetto2.descrizione}"></c:out></option>
																	</c:forEach>
																</c:if>
															</select>
														</div>
														
														<label for="percentualeControllante" class="col-sm-1 control-label"><spring:message
															text="??affariSocietari.label.percentualeControllante??"
															code="affariSocietari.label.percentualeControllante" /></label>
														<div class="col-sm-4">
															<input class="form-control" maxlength="1000"
															id="txtPercentualeControllante"
															value="${affariSocietariView.getVo().percentualeControllante}" />
													</div>
														
														<div class="col-md-1">
															<button type="button" onclick="addSocietaPro(false)" class="btn palette-Green-300 bg btn-float waves-effect waves-circle waves-float btn-circle-mini">
																<span class="glyphicon glyphicon-plus"></span>
									      					</button>
													    </div>
													    
													    
													    <c:if test="${ affariSocietariView.sociAggiunti != null }">
															<c:forEach items="${affariSocietariView.sociAggiunti}" var="rAffariSocietari">
															
																<div class="col-md-12 box-dinamik" style="margin-top:25px;">
																	<div class="col-md-2"></div>
																	<div class="col-md-7">
																		<input name="idControllante" type="hidden" value="${rAffariSocietari.idSocietaSocio}" class="form-control"/>
																		<input value="${rAffariSocietari.descrizioneSocietaSocio}" class="form-control" type="text" readonly/>
																	</div> 
																	<div class="col-md-2">
																		<input value="${rAffariSocietari.percentualeSocio}" class="form-control" type="text" readonly="true">
																	</div>
																	<div class="col-md-1" style="left:10px;">
																		<button type="button" onclick="removeSocietaPro(${rAffariSocietari.idSocietaSocio}, false)" 
																		class="btn palette-Green-300 bg btn-float waves-effect waves-circle waves-float btn-circle-mini">
																		<span class="fa fa-trash-o" aria-hidden="true"></span>
																		</button>
																	</div>
												</div>
																
															</c:forEach>
														</c:if>
													    		
													</div>
												</div>
											</div>
										</div>
										<% } %>
										
										<div class="list-group lg-alt">
											<div class="list-group-item">
												<div class="form-group">
													<label for="percentualeTerzi" class="col-sm-2 control-label"><spring:message
															text="??affariSocietari.label.percentualeTerzi??"
															code="affariSocietari.label.percentualeTerzi" /></label>
													<div class="col-sm-10">
														<form:input path="percentualeTerzi" cssClass="form-control" maxlength="1000" disabled="<%=disableInVis %>"
															id="txtPercentualeTerzi"
															value="${affariSocietariView.getVo().percentualeTerzi}" />
													</div>
												</div>
											</div>
										</div>																			
										

										<div class="list-group lg-alt">
											<!--Allegato-->
											<div class="list-group-item media">
												<div class="media-body">
													<div class="form-group">
													 <label for="tipologia" class="col-sm-2 control-label"></label>
													 <div class="col-sm-10">

														<!-- ALLEGATI GENERICI -->
														<div class="panel panel-default">
															<div class="panel-heading" role="tab"
																id="headingAllegatiGenerici">
																<h4 class="panel-title">
																	<button type="button" data-toggle="collapse"
																		data-target="#boxAllegatiGenerici"
																		class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
																		style="float: left; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 5px; margin-left: 20px;"
																		aria-expanded="true">
																		<i class="zmdi zmdi-collection-text icon-mini"></i>
																	</button>

																	<button type="button" data-toggle="modal"
																		onclick="openModalAllegatoGenerico()"
																		class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
																		style="float: right; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 5px; margin-left: 20px;"
																		aria-expanded="true">
																		<i class="zmdi zmdi-plus icon-mini"></i>
																	</button>

																	<a id="allegatiGenericiGraffa"
																		style="display: none; float: right; position: relative !important; margin-right: 30px; vertical-align: middle; margin-top: 0px; margin-left: 20px;"
																		aria-expanded="true"> <i class="fa fa-paperclip "></i>
																	</a> <a data-toggle="collapse" data-parent="#accordion"
																		href="#boxAllegatiGenerici"> <spring:message
																			text="??affariSocietari.label.template??"
																			code="affariSocietari.label.template" />
																	</a>

																</h4>
															</div>
															<div id="boxAllegatiGenerici"
																class="panel-collapse collapse" role="tabpanel"
																aria-labelledby="headingAllegatiGenerici">
																<jsp:include
																	page="/subviews/affariSocietari/allegatiGenerici.jsp"></jsp:include>

															</div>
														</div>
													</div>
													</div>
												</div>
											</div>
										</div>
										
										<% if ("false".equals(disableInVis)){ %>
										<div class="row">
										<div class="col-sm-10"></div>
										<div class="col-sm-1">
										<button form="affariSocietariForm" onclick="salvaAffariSocietari()"
										class="btn palette-Green-SNAM bg btn-float waves-effect waves-circle waves-float btnSalva">
										<i class="zmdi zmdi-save"></i>
										</button>
										</div>
										<div class="col-sm-1"></div>
										</div>
										<% } %>
										
									</div>
									
									

									
									
							</div>
							<!-- creaDiv -->



							</form:form>

						</div>
					</div>
				</div>
			</div>
			</div>

		</section>

	</section>

	<footer>
		<jsp:include page="/parts/footer.jsp"></jsp:include>
	</footer>

	<jsp:include page="/parts/script-end.jsp"></jsp:include>
	<!-- si carica il js -->

	<script
		src="<%=request.getContextPath()%>/portal/js/controller/affariSocietari.js"></script>

	<script type="text/javascript">
		if (document.getElementById("descrizioneProtocollo")) {
			CKEDITOR.replace('descrizioneProtocollo');
		}

		/*
		// funzione per mostrare il modal racchiuso nel boxAllegati
		// Apre il boxAllegati e visualizza il modal per caricare un nuovo allegato
		 */
		function openModalAllegatoGenerico() {
			$("#boxAllegatiGenerici").collapse('show');
			$("#panelAggiungiAllegatoGenerico").modal('show');
		}
		
		<%if( request.getAttribute("anchorName") != null ) {%>
			goToAncora('<%=request.getAttribute("anchorName")%>');
		<%}%>
	</script>

</body>

</html>
