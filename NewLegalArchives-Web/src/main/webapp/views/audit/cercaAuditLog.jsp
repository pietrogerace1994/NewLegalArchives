<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
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
							<div class="card-header">
								<h1>
									<spring:message text="??audit.label.cercaAuditLog??"
										code="audit.label.cercaAuditLog" />
								</h1>
								<p class="visible-lg visible-md visible-xs visible-sm text-left">
									<spring:message text="??fascicolo.label.nonHaiTrovatoCercavi??"
										code="fascicolo.label.nonHaiTrovatoCercavi" />
									<a data-toggle="modal" href="#panelRicercaAuditLog" class=""> <spring:message
											text="??fascicolo.label.affinaRicerca??"
											code="fascicolo.label.affinaRicerca" />
									</a>
								</p>
							</div>
							<div class="card-body">
								<div id="sezioneMessaggiApplicativi"></div>
								<div class="">
									<table id="tabellaRicercaAuditLog"
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
	<div class="modal fade" id="panelRicercaAuditLog" tabindex="-1"
		role="dialog" aria-hidden="true">
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
							<div class="form-group">
								<label class="col-md-4 control-label" for="tipologiaEntita"><spring:message
										text="??audit.label.tipologiaEntita??"
										code="audit.label.tipologiaEntita" /></label>
								<div class="col-md-4">
 									<select id="tipologiaEntita" class="form-control">

										<option value="">
											<c:out value=""></c:out>
										</option>

										<option value="UTENTE">
											<c:out value="ACCESSI"></c:out>
										</option>
										
										<option value="FASCICOLO">
											<c:out value="FASCICOLO"></c:out>
										</option>

										<option value="INCARICO">
											<c:out value="INCARICO"></c:out>
										</option>

										<option value="COLLEGIO ARBITRALE">
											<c:out value="COLLEGIO ARBITRALE"></c:out>
										</option>
										
										<option value="PROFORMA">
											<c:out value="PROFORMA"></c:out>
										</option>

										<option value="ATTO">
											<c:out value="ATTO"></c:out>
										</option> 
									</select>
								</div>
							</div>

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
							<div class="form-group">
								<label class="col-md-4 control-label" for="userId"><spring:message
										text="??audit.label.userId??"
										code="audit.label.userId" /></label>
								<div class="col-md-4">
									<select id="comboUserId" class="form-control">
										<option value=""></option>

									</select>
								</div>
							</div>

							<!-- Button -->
							<div class="form-group">
								<label class="col-md-4 control-label" for="btnApplicaFiltri"></label>
								<div class="col-md-8">
									<button id="btnApplicaFiltri" name="singlebutton" type="button"
										onclick="cercaAuditLog()" data-dismiss="modal"
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
 
    <script src="<%=request.getContextPath()%>/portal/js/controller/audit.js"></script>
	
	<script type="text/javascript"> 
	caricaComboUserId(); 
	</script>
	<script type="text/javascript">
		initTabellaRicercaAuditLog();
	</script>
 
</body>
</html>
