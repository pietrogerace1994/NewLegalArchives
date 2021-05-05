<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld" %>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>


<!DOCTYPE html lang="${language}">
<!--[if IE 9 ]><html class="ie9"><![endif]-->

<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Legal Archives</title>

	<jsp:include page="/parts/script-init.jsp"></jsp:include>
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
			<!--  ESEMPIO 1 DI TABELLA PAGINATA  -->
			<div class="container">
				<div class="row">
			      <div class="table-responsive">
			        <table class="table table-hover">
			          <thead>
			            <tr>
			              <th>#</th>
			              <th>Table heading</th>
			              <th>Table heading</th>
			              <th>Table heading</th>
			              <th>Table heading</th>
			              <th>Table heading</th>
			              <th>Table heading</th>
			            </tr>
			          </thead>
			          <tbody id="myTable">
			            <tr>
			              <td>1</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			            </tr>
			            <tr>
			              <td>2</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			            </tr>
			            <tr>
			              <td>3</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			            </tr>
			            <tr>
			              <td>4</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			            </tr>
			            <tr class="success">
			              <td>5</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			            </tr>
			            <tr>
			              <td>6</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			            </tr>
			            <tr>
			              <td>7</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			            </tr>
			             <tr>
			              <td>8</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			            </tr>
			            <tr>
			              <td>9</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			            </tr>
			            <tr>
			              <td>10</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			              <td>Table cell</td>
			            </tr>
			          </tbody>
			        </table>   
			      </div>
			      <div class="col-md-12 text-center">
			      <ul class="pagination pagination-lg pager" id="myPager"></ul>
			      </div>
				</div>
			</div>
			
			<!-- ESEMPIO 2 DI TABELLA PAGINATA -->		
			<div class="container">
				<div class="row">
					<div class="table-responsive">
						<table id="data-table-selection" class="table table-striped table-responsive">
	                        <thead>
	                            <tr>
	                                <th data-column-id="id">Denominazione</th>
	                                <th data-column-id="01">Codice Fiscale</th>
	                                <th data-column-id="02">Partita Iva</th>
	                                <th data-column-id="03">Nazione</th>
	                                <th data-column-id="04">Tipo correlazione</th>
	                                <th data-column-id="05">Rapporto</th>
	                                <th data-column-id="06">Familiare</th>
	                               	<th data-column-id="07">Consiglieri sindaci</th>
	                                <th data-column-id="08">Data Inserimento</th>
	                                <th data-column-id="09">Stato</th>
	                            </tr>
	                        </thead>
	                        <tbody>
	                            <tr>
							        <td>Società A</td>
							        <td>-</td>
							        <td>0001234567</td>
							        <td>Italia</td>
							        <td>Parte correlata</td>
							        <td>Rapporto1</td>
							        <td>-</td>
							        <td>-</td>
							        <td>14/07/2016</td>
							        <td>Attiva</td>
	                            </tr>
	                            <tr>
							        <td>Società B</td>
							        <td>-</td>
							        <td>0001234568</td>
							        <td>Italia</td>
							        <td>Dichiaranti e loro stretti familiari</td>
							        <td>-</td>
							        <td>Parente</td>
							        <td>-</td>
							        <td>14/07/2016</td>
	                            </tr>
	                            <tr>
							        <td>Società C</td>
							        <td>-</td>
							        <td>0001234569</td>
							        <td>Italia</td>
							        <td>Soggetti d'interesse</td>
							        <td>-</td>
							        <td>-</td>
							        <td>Consigliere1</td>
							        <td>14/07/2016</td>
							        <td>Non Attiva</td>
	                            </tr>
	                             <tr>
							        <td>Società D</td>
							        <td>-</td>
							        <td>0001234570</td>
							        <td>Italia</td>
							        <td>Soggetti d'interesse</td>
							        <td>-</td>
							        <td>-</td>
							        <td>Consigliere4</td>
							        <td>14/07/2016</td>
							        <td>Attiva</td>
	                            </tr>                                    
	                        </tbody>
	                	</table>
	             	</div>
				</div>
			</div>
			
			<!-- ESEMPIO 3 DI TABELLA PAGINATA -->
			<div class=container>
			<div class="list-group-item media">
				<div class="media-body">
					<div class="form-group">
						<!--  
						<label for="listaPartiCorrelate" class="col-sm-2 control-label">
							<spring:message
								text="??parteCorrelata.label.partiCorrelate??"
								code="parteCorrelata.label.partiCorrelate" />
						</label>
						-->
						<div class="col-sm-20">
							<div class="container" style="clear:both;">
								  <h2>
								  	<spring:message text="??parteCorrelata.label.partiCorrelate??"
													code="parteCorrelata.label.partiCorrelate" />
								  </h2>												  
								  <table class="table table-striped">
								    <thead>
								      <tr>
								        <th>Denominazione</th>
								        <th>Codice Fiscale</th>
								        <th>Partita Iva</th>
								        <th>Nazione</th>
								        <th>Tipo correlazione</th>
								        <th>Rapporto</th>
								        <th>Familiare</th>
								        <th>Consiglieri sindaci</th>
								        <th>Data Inserimento</th>
								      </tr>
								    </thead>
								    <tbody>
								      <tr>
								        <td>Società A</td>
								        <th>-</th>
								        <td>0001234567</td>
								        <td>Italia</td>
								        <th>Parte correlata</th>
								        <th>Rapporto1</th>
								        <th>-</th>
								        <th>-</th>
								        <th>14/07/2016</th>
								      </tr>
								      <tr>
								        <td>Società B</td>
								        <th>-</th>
								        <td>0001234569</td>
								        <td>Italia</td>
								        <th>Dichiaranti e loro stretti familiari</th>
								        <th>-</th>
								        <th>Parente</th>
								        <th>-</th>
								        <th>14/07/2016</th>
								      </tr>
								      <tr>
								        <td>Società C</td>
								        <th>-</th>
								        <td>0001234569</td>
								        <td>Italia</td>
								        <th>Soggetti d'interesse</th>
								        <th>-</th>
								        <th>-</th>
								        <th>Consigliere1</th>
								        <th>14/07/2016</th>
								      </tr>
								    </tbody>
								  </table>
								</div>
							</div>			
						</div>
					</div>
			 	</div>
			</div>
		</section>
	</section>

	<footer>
		<jsp:include page="/parts/footer.jsp">
		</jsp:include>
	</footer>
	<jsp:include page="/parts/script-end.jsp">
	</jsp:include>


	<script src="<%=request.getContextPath()%>/portal/js/controller/parteCorrelata.js"></script>
</body>

</html>