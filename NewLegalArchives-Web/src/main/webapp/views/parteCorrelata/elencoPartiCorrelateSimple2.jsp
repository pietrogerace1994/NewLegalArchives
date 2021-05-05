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
			<div class="container">
				<div class="row">
					<div id="col-1" class="col-lg-12 col-md-12 col-sm-12 col-sx-12">
						<div class="card">
							<div class="card-header ch-dark palette-Green-SNAM bg">
								<h2>Elenco Parti Correlate</h2>
								<!-- 
								<c:if
									test="${ parteCorrelataView.vo == null || parteCorrelataView.vo.id == 0 }">
									<h2> 
										<spring:message text="??parteCorrelata.label.creaParteCorrelata??"
											code="parteCorrelata.label.creaParteCorrelata" />
									</h2>
								</c:if>
								<c:if
									test="${ parteCorrelataView.vo != null && parteCorrelataView.vo.id > 0 }">
									<h2>
										<spring:message text="??parteCorrelata.label.modificaParteCorrelata??"
											code="parteCorrelata.label.modificaParteCorrelata" />
									</h2>
								</c:if>
								-->
							</div>
							
							<!--  
								data-search="false" data-pagination="true" data-side-pagination="server"
								data-url="/examples/bootstrap_table/data"
								/parteCorrelata/
							-->
							
							<!-- PARTE CORRELATA -->							
							<div class="card-body">
								<div class="table-responsive">
									<table id="tabellaRicercaPartiCorrelate" data-url="initTabellaRicercaPartiCorrelate"
										class="table table-striped table-responsive">
										<thead>
				                            <tr>
				                                <th data-column-id="denominazione">Denominazione</th>
				                                <th data-column-id="codFisc">Codice Fiscale</th>
				                                <th data-column-id="PartIva">Partita Iva</th>
				                                <th data-column-id="nazione">Nazione</th>
				                                <th data-column-id="tipoCorrelazione">Tipo correlazione</th>
				                                <th data-column-id="rapporto">Rapporto</th>
				                                <th data-column-id="familiare">Familiare</th>
				                               	<th data-column-id="consiglieriSindaci">Consiglieri sindaci</th>
				                                <th data-column-id="datInserimento">Data Inserimento</th>
				                                <th data-column-id="stato">Stato</th>
				                            </tr>
										</thead>
									</table>
								</div>
							</div>
							
							<!--   
							<div class="card-body">
								<div class="table-responsive">
									<table id="data-table-selection" data-search="false" data-pagination="true"
										   data-side-pagination="server" data-url="initTabellaRicercaPartiCorrelate" 
										   class="table table-striped table-responsive" >
				                        <thead>
				                            <tr>
				                                <th data-column-id="denominazione">Denominazione</th>
				                                <th data-column-id="codFiscPartIva">Codice Fiscale / Partita Iva</th>
				                                <th data-column-id="nazione">Nazione</th>
				                                <th data-column-id="tipoCorrelazione">Tipo correlazione</th>
				                                <th data-column-id="rapporto">Rapporto</th>
				                                <th data-column-id="familiare">Familiare</th>
				                               	<th data-column-id="consiglieriSindaci">Consiglieri sindaci</th>
				                                <th data-column-id="datInserimento">Data Inserimento</th>
				                                <th data-column-id="stato">Stato</th>
				                            </tr>
				                        </thead>
				                	</table>
				              -->
				                	<!--  
				                	<ul class="pager">
				                		<li class="previous"><a href="#">&larr; Previous</a></li>
    									<li class="next"><a href="#">Next &rarr;</a></li>
				                	</ul>
				                	-->
				            <!--  
				             	</div>
							</div>
							-->
							
						 </div>
					</div>
				</div>
			</div>
		</section>
			
		
		<!--  ESEMPIO DI TABELLA PAGINATA  -->
		<!--
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
		      <ul class="pagination pagination-lg pager" id="myPager">
		      	<li class="previous">
		      		<a href="#">&larr; Previous</a>
		      	</li>
    			<li class="next">
    				<a href="#">Next &rarr;</a>
    			</li>
		      </ul>
		      </div>
			</div>
		</div>
		-->
				
		<div class="container">
			<div class="row">
				<div class="table-responsive">
					<table id="data-table-selection" data-search="true" class="table table-striped table-responsive">
                               <thead>
                                   <tr>
	                               	<th data-column-id="denominazione">Denominazione</th>
		                            <th data-column-id="codFisc">Codice Fiscale</th>
		                            <th data-column-id="PartIva">Partita Iva</th>
		                            <th data-column-id="nazione">Nazione</th>
		                            <th data-column-id="tipoCorrelazione">Tipo correlazione</th>
	                                <th data-column-id="rapporto">Rapporto</th>
	                                <th data-column-id="familiare">Familiare</th>
	                               	<th data-column-id="consiglieriSindaci">Consiglieri sindaci</th>
	                                <th data-column-id="datInserimento">Data Inserimento</th>
	                                <th data-column-id="stato">Stato</th>                                   
                                   </tr>
                               </thead>
                               <tbody>
                               	<c:forEach items="${listaPartiCorrelate}" var="oggetto">
                                   <tr>
                                       <td><c:out value="${oggetto.vo.denominazione}"/></td>
                                       <td><c:out value="${oggetto.vo.codFiscale}"/></td>
                                       <td><c:out value="${oggetto.vo.partitaIva}"/></td>
                                       <td><c:out value="${oggetto.vo.nazione.descrizione}"/></td>
                                       <td><c:out value="${oggetto.vo.tipoCorrelazione.descrizione}"/></td>
                                       <td><c:out value="${oggetto.vo.rapporto}"/></td>
                                       <td><c:out value="${oggetto.vo.familiare}"/></td>
                                       <td><c:out value="${oggetto.vo.consiglieriSindaci}"/></td>
                                       <td><c:out value="${oggetto.vo.dataInserimento}"/></td>
                                       <td><c:out value="-"/></td>
                                   </tr>
                               	</c:forEach>                                    
                               </tbody>
                           </table>
                       </div>
                       <!--  
                    	<ul class="pager">
			            	<li class="previous"><a href="#">&larr; Previous</a></li>
   							<li class="next"><a href="#">Next &rarr;</a></li>
			            </ul>
			            -->
					</div>
				</div>
		
		<!-- ELENCO DELLE PARTI CORRELATE -->
		<section id="elenco">
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
								<!--  
								<ul class="pager">
				            		<li class="previous"><a href="#">&larr; Previous</a></li>
    								<li class="next"><a href="#">Next &rarr;</a></li>
				            	</ul>
				            	-->
							</div>			
						</div>
					</div>
				<!-- FINE ELENCO PARTI CORRELATE -->
			 </div>
			</div>							
		<!--/ fine col-1 -->
		</section>
	</section>

	<footer>
		<jsp:include page="/parts/footer.jsp">
		</jsp:include>
	</footer>
	<jsp:include page="/parts/script-end.jsp">
	</jsp:include>

	<script src="<%=request.getContextPath()%>/portal/js/controller/parteCorrelata.js"></script>
	<script type="text/javascript">
		initTabellaRicercaPartiCorrelate();
	</script>
</body>

</html>