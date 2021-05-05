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
		
		<style>
		p.text-left a {
		color: #fff;
		}
		.search{
		display: none!important;
		}
		input.select-box {
		color: #000000!important;
		width: 22px;
		height: 22px;
		}
		#box-estendiPermessi-fascicoli{
		display: none;
		}
		
		.table > thead > tr > td.active,
		.table > tbody > tr > td.active,
		.table > tfoot > tr > td.active,
		.table > thead > tr > th.active,
		.table > tbody > tr > th.active,
		.table > tfoot > tr > th.active,
		.table > thead > tr.active > td,
		.table > tbody > tr.active > td,
		.table > tfoot > tr.active > td,
		.table > thead > tr.active > th,
		.table > tbody > tr.active > th,
		.table > tfoot > tr.active > th {
		  background-color: rgb(196, 255, 133);
		  border-bottom: 4px solid #fff!important;
		}
		.pagination > .active > a, .pagination > .active > span, .pagination > .active > a:hover, .pagination > .active > span:hover, .pagination > .active > a:focus, .pagination > .active > span:focus {
		z-index: 3;
		color: #ffffff;
		background-color: #52a256;
		border-color: #ffffff;
		cursor: default;
		}
		
		.cSearch{
		margin-top: -30px!important;
	    position: absolute;
	    display: block!important;
	    width: 160px!important;
	    height: 150px!important;
	    border-radius: 50%!important;
	    border: 5px solid #66bb6a;
	    background: #26a69a;
	    text-align: center!important;
	    margin: 0 auto!important;
	    left: 43%;
	    line-height: 6.5;
	    top: -50px;
	    background-color: #26a69a;
				}
				
		#ownerSelect{
		margin-top: -30px!important;
	    position: absolute;
	    display: block!important;
	    width: 160px!important;
	    height: 150px!important;
	    border-radius: 50%!important;
	    background: transparent;
	    text-align: center!important;
	    margin: 0 auto!important;
		left: 43%;
	    line-height: 17;
	    top: -50px;
	    z-index: 1;
	    font-size: 11px;
		cursor:pointer;
		}
		
		</style>
		<section id="content">
			<div class="container">
				<div class="row">
					<div id="col-1" class="col-lg-12 col-md-12 col-sm-12 col-sx-12">

						<div class="card">
							
							<div class="card-header">
								<h1>
									<spring:message text="??fascicolo.label.estendiPermessiFascicoli??"
										code="fascicolo.label.estendiPermessiFascicoli" />
								</h1>
								<p class="visible-lg visible-md visible-xs visible-sm text-left">
									<spring:message text="??fascicolo.label.nonHaiTrovatoCercavi??"
										code="fascicolo.label.nonHaiTrovatoCercavi" />
									<a data-toggle="modal" href="#modalRicercaEstendiPermessiFascicolo" style="color:#0a6ebd;"> <spring:message
											text="??fascicolo.label.affinaRicerca??"
											code="fascicolo.label.affinaRicerca" />
									</a>
								</p>
							</div>
							
							<c:if test="${ utenteNominativo != null }">
								<input type="hidden" id="utenteNominativo" value="${utenteNominativo}">
								<input type="hidden" id="utenteMatricola" value="${utenteMatricola}">
								
								<c:if test="${ amministratore != null }">
									<input type="hidden" id="amministratore" value="${amministratore}">
								</c:if>
								<c:if test="${ amministratore == null }">
									<input type="hidden" id="amministratore" value="">
								</c:if>
							</c:if>
							
							<div class="card-body" id="box-estendiPermessi-fascicoli">
							
									<c:if test="${ not empty param.successMessage }">
										<div class="alert alert-info">
											<spring:message code="messaggio.operazione.ok"
												text="??messaggio.operazione.ok??"></spring:message>
										</div>
									</c:if>
									<c:if test="${ not empty param.errorMessage }">
										<div class="alert alert-danger">
											<spring:message code="${errorMessage}"
												text="??${errorMessage}??"></spring:message>
										</div>
									</c:if>
							
							<!-- Area Table Result-->
							
								<div class="table-responsive">
							
									<table id='estendiPermessi-fascicolo'  class="table table-responsive" cellspacing="0" width="100%">
									   <thead>
										<tr>
											<th data-column-id="id" data-identifier="true" data-visible="false" data-visible-in-selection="false" data-width="5%">ID</th>
											<th data-column-id="numeroFascicolo" data-sortable="false" data-width="35%"><spring:message text="??assegna.label.numerofascicolo??" code="assegna.label.numerofascicolo" /></th>
											<th data-column-id="stato" data-sortable="false" data-width="30%"><spring:message text="??assegna.label.stato??" code="assegna.label.stato" /></th>
											<th data-column-id="owner" data-sortable="false" data-width="30%"><spring:message text="??assegna.label.owner??" code="assegna.label.owner" /></th>
										</tr>	
										</thead>
									</table>
								</div>
								
								
								<script>
									var selectjs=[];
								</script>
								<div class="col-md-12" style="padding: 20px;background: #99c79b;">
									<div class="col-md-12 column">
										<label class="col-md-12 control-label" for="riassegnalegaleInterno"><spring:message text="??estendiPermessi.label.modificaPerUtente??" code="estendiPermessi.label.modificaPerUtente" />:</label>
										<div class="col-md-5">
											<select id="riassegnalegaleInterno" name="riassegnalegaleInterno" class="form-control">
												<option></option>
												<c:if test="${ utentiRiassegnazione != null }">
													<c:forEach items="${utentiRiassegnazione}" var="oggetto">
														<option value="${ oggetto.useridUtil }"><c:out value="${oggetto.nominativoUtil}"></c:out></option>
														<script>selectjs.push({val:"${ oggetto.useridUtil }",text:"${oggetto.nominativoUtil}"})</script>	
													</c:forEach>
												</c:if>
											</select>
										</div>
										<div class="col-md-1">
											<label class="col-md-2 control-label" for="chkPermessiLettura">
												<spring:message text="??fascicolo.label.permessoLettura??" code="fascicolo.label.permessoLettura" />
											</label>
										</div>
										<div class="col-md-1">
											<input type="checkbox" id="chkPermessiLettura" name="chkPermessiLettura" value="R"/>
										</div>
										<div class="col-md-1">
											<label class="col-md-2 control-label" for="chkPermessiScrittura">
												<spring:message text="??fascicolo.label.permessoScrittura??" code="fascicolo.label.permessoScrittura" />
											</label>
										</div>
										<div class="col-md-1">
											<input type="checkbox" id="chkPermessiScrittura" name="chkPermessiScrittura" value="W"/>
										</div>
										<div class="col-md-3" style="text-align:center;">
											<button id="save-estendi" type="button" class="btn btn-success"
											 style="margin-left: 5px" disabled onclick="assegnaSelezionati()">
												<i class="fa fa-save"></i>
												<spring:message text="??estendiPermessi.label.modificaPermessi??" code="estendiPermessi.label.modificaPermessi" />
											</button>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!--/ fine card -->
					</div>
				</div>
			</div>
			
			<!--/ fine col-1 -->
		</section>
		<jsp:include page="/views/estendiPermessi/modalRicerca.jsp"></jsp:include>
		<jsp:include page="/views/estendiPermessi/modalEstendiPermessi.jsp"></jsp:include> 
	</section>
  

	<footer>
		<jsp:include page="/parts/footer.jsp"></jsp:include>
	</footer>
	<jsp:include page="/parts/script-end.jsp"></jsp:include>

 
	<script src="<%=request.getContextPath()%>/portal/js/controller/estendiPermessiFascicoli.js"></script>
 
</body>
</html>
