<%@page import="java.util.List"%>
<%@page import="eng.la.util.SpringUtil"%>
<%@page import="eng.la.business.NewsletterService"%>
<%@page import="eng.la.model.view.StatoNewsletterView"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>
<%@ page contentType="text/html; charset=UTF-8" %>

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
							<div class="card-header">
								<h1>
									<spring:message text="??newsletter.label.cercaNewsletter??"
										code="newsletter.label.cercaNewsletter" />
								</h1>
								<p class="visible-lg visible-md visible-xs visible-sm text-left">
									<spring:message text="??fascicolo.label.nonHaiTrovatoCercavi??"
										code="fascicolo.label.nonHaiTrovatoCercavi" />
									<a data-toggle="modal" href="#panelRicerca" class=""> <spring:message
											text="??fascicolo.label.affinaRicerca??"
											code="fascicolo.label.affinaRicerca" />
									</a>
								</p>
							</div>
							<div class="card-body">
								<div id="sezioneMessaggiApplicativi">
									<form:errors path="*" cssClass="alert alert-danger"
										htmlEscape="false" element="div"></form:errors>
									<c:if test="${ not empty param.successMessage }">
										<div class="alert alert-info">
											<spring:message code="messaggio.operazione.ok"
												text="??messaggio.operazione.ok??"></spring:message>
											<button type="button" onclick="$(this).parent().hide();"
												class="close" aria-label="Close">
												<span aria-hidden="true">&times;</span>
											</button>
										</div>
									</c:if>
									<c:if test="${ not empty param.errorMessage }">
										<div class="alert alert-danger">
											<spring:message code="${errorMessage}"
												text="??${errorMessage}??"></spring:message>
										</div>
									</c:if>
								</div>
								<div class="table-responsive">
									<table id="tabellaRicercaNewsletter"
										class="table table-striped table-responsive">
									
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</section>


	<!-- PANEL RICERCA MODALE -->
	<div class="modal fade" id="panelRicerca" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.miglioraRicerca??"
							code="fascicolo.label.miglioraRicerca" />
					</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal">
						<fieldset>
							 
							<!-- DAL...AL -->
							<div class="form-group">
								<label class="col-md-4 control-label" for="selectbasic"><spring:message
										text="??fascicolo.label.dataDal??"
										code="fascicolo.label.dataDal" /></label>
								<div class="col-md-4">
									<input id="txtDataDal" type='text'
										class="form-control date-picker" placeholder="">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label" for="selectbasic"><spring:message
										text="??fascicolo.label.dataAl??"
										code="fascicolo.label.dataAl" /></label>
								<div class="col-md-4">
									<input id="txtDataAl" type='text'
										class="form-control date-picker" placeholder="">
								</div>
							</div>
							<!-- Dal...AL -->
							<!-- Text input-->
							<div class="form-group">
								<label class="col-md-4 control-label" for="legale_esterno">
									<spring:message text="??newsletter.label.titolo??"
										code="newsletter.label.titolo" />
								</label>
								<div class="col-md-4"> 
									<input id="txtTitolo" name="titolo" type="text" 
										class="typeahead form-control input-md">
								</div>
							</div>

							<div class="form-group">
								<label class="col-md-4 control-label" for="stato"> <spring:message
										text="??newsletter.label.stato??" code="newsletter.label.stato" />
								</label>
								<div class="col-md-4">
									<select class="form-control" id="comboStato">
										<option value=""></option>
										<%
										NewsletterService service = (NewsletterService) SpringUtil
													.getBean("newsletterService");
											try {
												List<StatoNewsletterView> categorie = service
														.listaStatoNewsletter(request.getLocale().getLanguage().toUpperCase());
												if (categorie != null) {
													for (StatoNewsletterView categoria: categorie) {
										%>
										<option value="<%=categoria.getVo().getCodGruppoLingua()%>">
											<%=categoria.getVo().getDescrizione()%>
										</option>
										<%
											}
												}
											} catch (Throwable e) {
												e.printStackTrace();
											}
										%>
									</select>
								</div>
							</div>

							<!-- Button -->
							<div class="form-group">
								<label class="col-md-4 control-label" for="btnApplicaFiltri"></label>
								<div class="col-md-8">
									<button id="btnApplicaFiltri" name="singlebutton" type="button"
										onclick="cercaNewsletter()" data-dismiss="modal"
										class="btn btn-primary">
										<spring:message text="??fascicolo.label.eseguiRicerca??"
											code="fascicolo.label.eseguiRicerca" />
									</button>
									<button name="singlebutton" type="button" data-dismiss="modal"
										class="btn btn-warning">
										<spring:message text="??fascicolo.label.chiudi??"
											code="fascicolo.label.chiudi" />
									</button>
								</div>
							</div>

						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- FINE PANEL RICERCA MODALE --> 

	<footer>
		<jsp:include page="/parts/footer.jsp">
		</jsp:include>
	</footer>
	<jsp:include page="/parts/script-end.jsp">
	</jsp:include> 
 	 
 	<script type="text/javascript" src="<%=request.getContextPath()%>/portal/js/controller/newsletter.js"></script>
 	
 	<jsp:include page="/subviews/common/panelAzioniCommon.jsp"></jsp:include>
 	
	<script type="text/javascript">
		initTabellaRicercaNewsletter(); 
	</script>

	  
</body>

</html>

