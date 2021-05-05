<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="http://leg-arc/tags" prefix="legarc" %>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>

<!-- AUTORITA GIUDIZIALE-->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="autoritaGiudiziale" class="col-sm-2 control-label"><spring:message
					text="??fascicolo.label.autoritaGiudiziale??"
					code="fascicolo.label.autoritaGiudiziale" /></label>
			<div class="col-sm-10">
				 <form:input path="autoritaGiudiziaria" id="txtAutoritaGiudiziale" readonly="true" cssClass="form-control"/>
 
			</div>
		</div>
	</div>
</div>
 
 
<!-- NOME SOGGETTO INDAGATO-->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="nomeSoggettoIndagato" class="col-sm-2 control-label"><spring:message
					text="??fascicolo.label.nomeSoggettoIndagato??"
					code="fascicolo.label.nomeSoggettoIndagato" /></label>
			<div class="col-sm-10">
				
				<div class="table-responsive" style="clear: both;">
					<table class="table table-striped table-responsive">
						<thead>
							<tr style="border:1px solid #e0e0e0">
								<th data-column-id="01" style="width: 50px">
									<button type="button" data-toggle="collapse"
										data-target="#boxSoggettoIndagato"
										class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
										style="float: left; position: relative !important;">
										<i class="zmdi zmdi-collection-text icon-mini"></i>
									</button>
								</th>
								<th data-column-id="id"><spring:message
										text="??fascicolo.label.nomeSoggettoIndagato??"
										code="fascicolo.label.nomeSoggettoIndagato" /></th> 
								<th></th>
							</tr>
						</thead>
						<tbody id="boxSoggettoIndagato" class="collapse in">
							<c:if
								test="${ not empty fascicoloDettaglioView.soggettoIndagatoAggiunti  }">
								<c:forEach items="${fascicoloDettaglioView.soggettoIndagatoAggiunti}"
									var="soggettoTmp" varStatus="indiceArray">
									<tr>
										<td colspan="2">(${soggettoTmp.tipoSoggettoIndagato })
											<c:if test="${ not empty soggettoTmp.vo}"> 
												<legarc:decrypt idOggetto="${soggettoTmp.vo.id}" dato="${soggettoTmp.nomeSoggettoIndagato }"></legarc:decrypt>
											</c:if>
											<c:if test="${ empty soggettoTmp.vo}"> 
												${soggettoTmp.nomeSoggettoIndagato }
											</c:if>  
										</td>
										<td>
										</td>
									</tr>
								</c:forEach>
							</c:if>
	
							<c:if
								test="${ empty fascicoloDettaglioView.soggettoIndagatoAggiunti }">
								<tr>
									<td colspan="2"><spring:message code="fascicolo.label.tabella.no.dati"></spring:message>
									</td>
									<td></td>

								</tr>
							</c:if>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>  
 
<!-- NOME PERSONA OFFESA-->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="nomePersonaOffesa" class="col-sm-2 control-label"><spring:message
					text="??fascicolo.label.nomePersonaOffesa??"
					code="fascicolo.label.nomePersonaOffesa" /></label>
			<div class="col-sm-10">
				
				<div class="table-responsive" style="clear: both;">
					<table class="table table-striped table-responsive" >
						<thead>
							<tr style="border:1px solid #e0e0e0">
								<th data-column-id="01" style="width: 50px">
									<button type="button" data-toggle="collapse"
										data-target="#boxPersonaOffesa"
										class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
										style="float: left; position: relative !important;">
										<i class="zmdi zmdi-collection-text icon-mini"></i>
									</button>
								</th>
								<th data-column-id="id"><spring:message
										text="??fascicolo.label.nomePersonaOffesa??"
										code="fascicolo.label.nomePersonaOffesa" /></th> 
								<th></th>
							</tr>
						</thead>
						<tbody id="boxPersonaOffesa" class="collapse in">
							<c:if
								test="${ not empty fascicoloDettaglioView.personaOffesaAggiunte }">
								<c:forEach items="${fascicoloDettaglioView.personaOffesaAggiunte}"
									var="soggettoTmp" varStatus="indiceArray">
									<tr>
										<td colspan="2">(${soggettoTmp.tipoPersonaOffesa }) 
											<c:if test="${ not empty soggettoTmp.vo}"> 
												<legarc:decrypt idOggetto="${soggettoTmp.vo.id}" dato="${soggettoTmp.nomePersonaOffesa }"></legarc:decrypt>
											</c:if>
											<c:if test="${ empty soggettoTmp.vo}"> 
												${soggettoTmp.nomePersonaOffesa }
											</c:if> 				 
										</td>
										<td>
										</td>
									</tr>
								</c:forEach>
							</c:if>
	
							<c:if
								test="${ empty fascicoloDettaglioView.personaOffesaAggiunte }">
								<tr>
									<td colspan="2"><spring:message code="fascicolo.label.tabella.no.dati"></spring:message>
									</td>
									<td></td>

								</tr>
							</c:if>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>    
<!-- NOME PARTE CIVILE-->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="nomeParteCivile" class="col-sm-2 control-label"><spring:message
					text="??fascicolo.label.nomeParteCivile??"
					code="fascicolo.label.nomeParteCivile" /></label>
			<div class="col-sm-10">
				
				<div class="table-responsive" style="clear: both;">
					<table class="table table-striped table-responsive" >
						<thead>
							<tr style="border:1px solid #e0e0e0">
								<th data-column-id="01" style="width: 50px">
									<button type="button" data-toggle="collapse"
										data-target="#boxParteCivile"
										class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
										style="float: left; position: relative !important;">
										<i class="zmdi zmdi-collection-text icon-mini"></i>
									</button>
								</th>
								<th data-column-id="id"><spring:message
										text="??fascicolo.label.nomeParteCivile??"
										code="fascicolo.label.nomeParteCivile" /></th> 
								<th></th>

							</tr>
						</thead>
						<tbody id="boxParteCivile" class="collapse in">
							<c:if
								test="${not empty fascicoloDettaglioView.parteCivileAggiunte  }">
								<c:forEach items="${fascicoloDettaglioView.parteCivileAggiunte}"
									var="soggettoTmp" varStatus="indiceArray">
									<tr>
										<td colspan="2">(${soggettoTmp.tipoParteCivile }) 										
											<c:if test="${ not empty soggettoTmp.vo}"> 
												<legarc:decrypt idOggetto="${soggettoTmp.vo.id}" dato="${soggettoTmp.nomeParteCivile }"></legarc:decrypt>
											</c:if>
											<c:if test="${ empty soggettoTmp.vo}"> 
												${soggettoTmp.nomeParteCivile }
											</c:if>  
										</td>
										<td>
										</td>
									</tr>
								</c:forEach>
							</c:if>
	
							<c:if
								test="${ empty fascicoloDettaglioView.parteCivileAggiunte  }">
								<tr>
									<td colspan="2"><spring:message code="fascicolo.label.tabella.no.dati"></spring:message>
									</td>
									<td></td>

								</tr>
							</c:if>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
 
 
<!-- NOME RESPONSABILE CIVILE-->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="nomeResponsabileCivile" class="col-sm-2 control-label"><spring:message
					text="??fascicolo.label.nomeResponsabileCivile??"
					code="fascicolo.label.nomeResponsabileCivile" /></label>
			<div class="col-sm-10">
				
				<div class="table-responsive" style="clear: both;">
					<table class="table table-striped table-responsive" >
						<thead>
							<tr style="border:1px solid #e0e0e0">
								<th data-column-id="01" style="width: 50px">
									<button type="button" data-toggle="collapse"
										data-target="#boxResponsabileCivile"
										class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
										style="float: left; position: relative !important;">
										<i class="zmdi zmdi-collection-text icon-mini"></i>
									</button>
								</th>
								<th data-column-id="id"><spring:message
										text="??fascicolo.label.nomeResponsabileCivile??"
										code="fascicolo.label.nomeResponsabileCivile" /></th> 
								<th></th>

							</tr>
						</thead>
						<tbody id="boxResponsabileCivile" class="collapse in">
							<c:if
								test="${not empty fascicoloDettaglioView.responsabileCivileAggiunte }">
								<c:forEach items="${fascicoloDettaglioView.responsabileCivileAggiunte}"
									var="soggettoTmp" varStatus="indiceArray">
									<tr>
										<td colspan="2">(${soggettoTmp.tipoResponsabileCivile }) 
											<c:if test="${ not empty soggettoTmp.vo}"> 
												<legarc:decrypt idOggetto="${soggettoTmp.vo.id}" dato="${soggettoTmp.nomeResponsabileCivile }"></legarc:decrypt>
											</c:if>
											<c:if test="${ empty soggettoTmp.vo}"> 
												${soggettoTmp.nomeResponsabileCivile }
											</c:if>   
										</td>
										<td>
										</td>
									</tr>
								</c:forEach>
							</c:if>
	
							<c:if
								test="${ empty fascicoloDettaglioView.parteCivileAggiunte }">
								<tr>
									<td colspan="2"><spring:message code="fascicolo.label.tabella.no.dati"></spring:message>
									</td>
									<td></td>

								</tr>
							</c:if>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
 


<!-- GIUDIZIO--> 
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="giudizio" class="col-sm-2 control-label"><spring:message
					text="??fascicolo.label.giudizio??"
					code="fascicolo.label.giudizio" /></label>
			<div class="col-sm-10"> 
			
				<div class="table-responsive" style="clear: both;">
					<table class="table table-striped table-responsive" >
						<thead>
							<tr style="border:1px solid #e0e0e0">
								<th data-column-id="05" style="width: 50px">
									<button type="button" data-toggle="collapse"
										data-target="#boxGiudizio"
										class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
										style="float: left; position: relative !important;">
										<i class="zmdi zmdi-collection-text icon-mini"></i>
									</button>
								</th>
								<th data-column-id="id"><spring:message
										text="??fascicolo.label.giudizio??"
										code="fascicolo.label.giudizio" /></th>
								<th data-column-id="01"><spring:message
										text="??fascicolo.label.organoGiudicante??"
										code="fascicolo.label.organoGiudicante" /></th>
								<th data-column-id="03"><spring:message
										text="??fascicolo.label.foro??"
										code="fascicolo.label.foro" /></th>
								<th data-column-id="03"><spring:message
										text="??fascicolo.label.numeroRegistroCausa??"
										code="fascicolo.label.numeroRegistroCausa" /></th>
								<th data-column-id="07"><spring:message
										text="??fascicolo.label.note??"
										code="fascicolo.label.note" /></th>
								<th data-column-id="06"></th>
								<th data-column-id="04"></th>  
							</tr>
						</thead>
						<tbody id="boxGiudizio" class="collapse in">
							<c:if 	test="${ not empty fascicoloDettaglioView.giudiziAggiunti }">
								<c:forEach items="${fascicoloDettaglioView.giudiziAggiunti}"
									var="giudizioAggiunto" varStatus="indiceArray">
									<tr>
										<td colspan="2">${giudizioAggiunto.vo.giudizio.descrizione}</td>
										<td>${giudizioAggiunto.vo.organoGiudicante.nome}</td>
										<td>${giudizioAggiunto.foro}</td>
										<td>${giudizioAggiunto.numeroRegistroCausa}</td>
										<td>${giudizioAggiunto.note}</td>
										<td>
										</td>
										<td>	
										</td>
									</tr>
								</c:forEach>
							</c:if>

							<c:if
								test="${ empty fascicoloDettaglioView.giudiziAggiunti  }">
								<tr>
									<td colspan="2"><spring:message code="fascicolo.label.tabella.no.dati"></spring:message>
									</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div> 
 </div>
