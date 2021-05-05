<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%> 
<%@ taglib uri="http://leg-arc/tags" prefix="legarc" %>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>
<!--  engsecurity VA  --><form method="post" name="formToken"><engsecurity:token regenerate="false"/>

<!-- AUTORITA GIUDIZIALE-->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="autoritaGiudiziale" class="col-sm-2 control-label"><spring:message
					text="??fascicolo.label.autoritaGiudiziale??"
					code="fascicolo.label.autoritaGiudiziale" /></label>
			<div class="col-sm-10">
				 <form:input path="autoritaGiudiziaria" id="txtAutoritaGiudiziale" cssClass="form-control"/>
			</div>
		</div>
	</div>
</div>

<!-- TIPO TERZO CHIAMATO CAUSA
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="tipoTerzoChiamatoCausaCode" class="col-sm-2 control-label"><spring:message
					text="??fascicolo.label.tipoTerzoChiamatoCausa??"
					code="fascicolo.label.tipoTerzoChiamatoCausa" /></label>
			<div class="col-sm-10">
				<form:select id="comboTipoTerzoChiamatoCausa" path="tipoTerzoChiamatoCausaCode"
					cssClass="form-control">
					<form:option value="">
						<spring:message
							text="??fascicolo.label.selezionaTipoTerzoChiamatoCausa??"
							code="fascicolo.label.selezionaTipoTerzoChiamatoCausa" />
					</form:option>
					<c:if test="${ fascicoloView.listaTipoSoggetto != null }">
						<c:forEach items="${fascicoloView.listaTipoSoggetto}"
							var="oggetto">
							<form:option value="${ oggetto.vo.codGruppoLingua }">
								<c:out value="${oggetto.vo.nome}"></c:out>
							</form:option>
						</c:forEach>
					</c:if>

				</form:select>
			</div>
		</div>
	</div>
</div>
-->
<!-- TERZO CHIAMATO IN CAUSA
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="terzoChiamatoInCausa" class="col-sm-2 control-label"><spring:message
					text="??fascicolo.label.terzoChiamatoInCausa??"
					code="fascicolo.label.terzoChiamatoInCausa" /></label>
			<div class="col-sm-10">
				<div class="col-sm-5">
					<form:input path="terzoChiamatoInCausa"
						id="txtTerzoChiamatoInCausa" cssClass="form-control" /> 
					   
				</div>
				<div class="col-sm-5"> 
				  <spring:message var="legaleRiferimentoLabel"
					text="??fascicolo.label.legaleRiferimento??"
					code="fascicolo.label.legaleRiferimento" />
					<form:input path="legaleRiferimento"  
						 cssClass="form-control" placeholder="${legaleRiferimentoLabel }"/>
				</div> 
				<div class="col-sm-2">
					<button onclick="aggiungiTerzoChiamatoCausa()" type="button"
						class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini">
						<i class="zmdi zmdi-plus icon-mini"></i>
					</button>
				</div>
				<div class="table-responsive" style="clear: both;">
					<table class="table table-striped table-responsive"
						style="margin-top: 20px;">
						<thead>
							<tr style="border:1px solid #e0e0e0">
								<th data-column-id="05" style="width: 50px">
									<button type="button" data-toggle="collapse"
										data-target="#boxTerzoChiamato"
										class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
										style="float: left; position: relative !important;">
										<i class="zmdi zmdi-collection-text icon-mini"></i>
									</button>
								</th>
								<th data-column-id="id"><spring:message
										text="??fascicolo.label.terzoChiamatoInCausa??"
										code="fascicolo.label.terzoChiamatoInCausa" /></th>
								<th data-column-id="01"><spring:message
										text="??fascicolo.label.tipoTerzoChiamatoCausa??"
										code="fascicolo.label.tipoTerzoChiamatoCausa" /></th>
								<th data-column-id="03"><spring:message
										text="??fascicolo.label.legaleRiferimento??"
										code="fascicolo.label.legaleRiferimento" /></th>
								<th data-column-id="04"></th>	
							</tr>
						</thead>
						<tbody id="boxTerzoChiamato" class="collapse in">
							<c:if
								test="${ not empty fascicoloView.terzoChiamatoInCausaAggiunti }">
								<c:forEach items="${fascicoloView.terzoChiamatoInCausaAggiunti}"
									var="terzoChiamatoCausa" varStatus="indiceArray">
									<tr>
										<td colspan="2">${terzoChiamatoCausa.nomeTerzoChiamatoCausa}</td>
										<td>${terzoChiamatoCausa.tipoTerzoChiamatoCausa}</td>
										<td>${terzoChiamatoCausa.legaleRiferimento}</td>
										<td>
											<button
												onclick="rimuoviTerzoChiamatoCausa(${indiceArray.index})"
												type="button"
												class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini">
												<i class="fa fa-trash icon-mini"></i>
											</button>
										</td>
									</tr>
								</c:forEach>
							</c:if>

							<c:if
								test="${ empty fascicoloView.terzoChiamatoInCausaAggiunti }">
								<tr>
									<td colspan="2"><spring:message code="fascicolo.label.tabella.no.dati"></spring:message>
									</td>
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
 
-->
<!-- TIPO SOGGETTO INDAGATO-->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="tipoControparteId" class="col-sm-2 control-label"><spring:message
					text="??fascicolo.label.tipoSoggettoIndagato??"
					code="fascicolo.label.tipoSoggettoIndagato" /></label>
			<div class="col-sm-10">
				<form:select id="comboTipoSoggettoIndagato" path="tipoSoggettoIndagatoCode"
					cssClass="form-control">
					<form:option value="">
						<spring:message
							text="??fascicolo.label.selezionaTipoSoggettoIndagato??"
							code="fascicolo.label.selezionaTipoSoggettoIndagato" />
					</form:option>
					<c:if test="${ fascicoloView.listaTipoSoggettoIndagato != null }">
						<c:forEach items="${fascicoloView.listaTipoSoggettoIndagato}"
							var="oggetto">
							<form:option value="${ oggetto.vo.codGruppoLingua }">
								<c:out value="${oggetto.vo.nome}"></c:out>
							</form:option>
						</c:forEach>
					</c:if>

				</form:select>
			</div>
		</div>
	</div>
</div>

<!-- NOME SOGGETTO INDAGATO-->
<div class="list-group-item media">
	<a name="anchorSoggettoIndagato"></a>
	<div class="media-body">
		<div class="form-group">
			<label for="nomeSoggettoIndagato" class="col-sm-2 control-label"><spring:message
					text="??fascicolo.label.nomeSoggettoIndagato??"
					code="fascicolo.label.nomeSoggettoIndagato" /></label>
			<div class="col-sm-10">
				<div class="col-sm-10">
					<form:input path="nomeSoggettoIndagato" cssClass="form-control autocomplete" id="txtSoggettoIndagato"/>
				</div>
			
				<div class="col-sm-2">
					<button onclick="aggiungiSoggettoIndagato()" type="button"
						class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini">
						<i class="zmdi zmdi-plus icon-mini"></i>
					</button>
				</div>
					<div class="table-responsive" style="clear: both;">
					<table class="table table-striped table-responsive"
						style="margin-top: 20px;">
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
								test="${ not empty fascicoloView.soggettoIndagatoAggiunti }">
								<c:forEach items="${fascicoloView.soggettoIndagatoAggiunti}"
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
											<button
												onclick="rimuoviSoggettoIndagato(${indiceArray.index})"
												type="button"
												class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini">
												<i class="fa fa-trash icon-mini"></i>
											</button>
										</td>
									</tr>
								</c:forEach>
							</c:if>
	
							<c:if
								test="${empty fascicoloView.soggettoIndagatoAggiunti }">
								<tr>
									<td><spring:message code="fascicolo.label.tabella.no.dati"></spring:message>
									</td>
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



<!-- TIPO PERSONA OFFESA-->
<div class="list-group-item media">
	<a name="anchorPersonaOffesa"></a>
	<div class="media-body">
		<div class="form-group">
			<label for="tipoControparteId" class="col-sm-2 control-label"><spring:message
					text="??fascicolo.label.tipoPersonaOffesa??"
					code="fascicolo.label.tipoPersonaOffesa" /></label>
			<div class="col-sm-10">
				<form:select id="comboTipoPersonaOffesa" path="tipoPersonaOffesaCode"
					cssClass="form-control">
					<form:option value="">
						<spring:message
							text="??fascicolo.label.selezionaTipoPersonaOffesa??"
							code="fascicolo.label.selezionaTipoPersonaOffesa" />
					</form:option>
					<c:if test="${ fascicoloView.listaTipoSoggetto != null }">
						<c:forEach items="${fascicoloView.listaTipoSoggetto}"
							var="oggetto">
							<form:option value="${ oggetto.vo.codGruppoLingua }">
								<c:out value="${oggetto.vo.nome}"></c:out>
							</form:option>
						</c:forEach>
					</c:if>

				</form:select>
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
				<div class="col-sm-10">
					<form:input path="nomePersonaOffesa" cssClass="form-control autocomplete" id="txtPersonaOffesa"/>
				</div>
			
				<div class="col-sm-2">
					<button onclick="aggiungiPersonaOffesa()" type="button"
						class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini">
						<i class="zmdi zmdi-plus icon-mini"></i>
					</button>
				</div>
					<div class="table-responsive" style="clear: both;">
					<table class="table table-striped table-responsive"
						style="margin-top: 20px;">
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
								test="${ not empty fascicoloView.personaOffesaAggiunte  }">
								<c:forEach items="${fascicoloView.personaOffesaAggiunte}"
									var="soggettoTmp" varStatus="indiceArray">
									<tr>
										<td colspan="2">(${soggettoTmp.tipoPersonaOffesa })  
											<c:if test="${ not empty soggettoTmp.vo}"> 
												<legarc:decrypt idOggetto="${soggettoTmp.vo.id}" dato="${soggettoTmp.nomePersonaOffesa }"></legarc:decrypt>
											</c:if>
											<c:if test="${ empty soggettoTmp.vo}"> 
												${soggettoTmp.nomePersonaOffesa }
											</c:if> 
										<td>
											<button
												onclick="rimuoviPersonaOffesa(${indiceArray.index})"
												type="button"
												class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini">
												<i class="fa fa-trash icon-mini"></i>
											</button>
										</td>
									</tr>
								</c:forEach>
							</c:if>
	
							<c:if
								test="${ empty fascicoloView.personaOffesaAggiunte  }">
								<tr>
									<td><spring:message code="fascicolo.label.tabella.no.dati"></spring:message>
									</td>
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

<!-- TIPO PARTE CIVILE-->
<div class="list-group-item media">
	<a name="anchorParteCivile"></a>
	<div class="media-body">
		<div class="form-group">
			<label for="tipoParteCivile" class="col-sm-2 control-label"><spring:message
					text="??fascicolo.label.tipoParteCivile??"
					code="fascicolo.label.tipoParteCivile" /></label>
			<div class="col-sm-10">
				<form:select id="comboTipoParteCivile" path="tipoParteCivileCode"
					cssClass="form-control">
					<form:option value="">
						<spring:message
							text="??fascicolo.label.selezionaTipoParteCivile??"
							code="fascicolo.label.selezionaTipoParteCivile" />
					</form:option>
					<c:if test="${ fascicoloView.listaTipoSoggetto != null }">
						<c:forEach items="${fascicoloView.listaTipoSoggetto}"
							var="oggetto">
							<form:option value="${ oggetto.vo.codGruppoLingua }">
								<c:out value="${oggetto.vo.nome}"></c:out>
							</form:option>
						</c:forEach>
					</c:if>

				</form:select>
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
				<div class="col-sm-10">
					<form:input path="nomeParteCivile" cssClass="form-control autocomplete" id="txtParteCivile"/>
				</div>
			
				<div class="col-sm-2">
					<button onclick="aggiungiParteCivile()" type="button"
						class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini">
						<i class="zmdi zmdi-plus icon-mini"></i>
					</button>
				</div>
					<div class="table-responsive" style="clear: both;">
					<table class="table table-striped table-responsive"
						style="margin-top: 20px;">
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
								test="${ not empty fascicoloView.parteCivileAggiunte }">
								<c:forEach items="${fascicoloView.parteCivileAggiunte}"
									var="soggettoTmp" varStatus="indiceArray">
									<tr>
										<td colspan="2">(${soggettoTmp.tipoParteCivile }) 
											<c:if test="${ not empty soggettoTmp.vo}"> 
												<legarc:decrypt idOggetto="${soggettoTmp.vo.id}" dato="${soggettoTmp.nomeParteCivile }"></legarc:decrypt>
											</c:if>
											<c:if test="${ empty soggettoTmp.vo}"> 
												${soggettoTmp.nomeParteCivile }
											</c:if>  
										<td>
											<button
												onclick="rimuoviParteCivile(${indiceArray.index})"
												type="button"
												class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini">
												<i class="fa fa-trash icon-mini"></i>
											</button>
										</td>
									</tr>
								</c:forEach>
							</c:if>
	
							<c:if
								test="${ empty fascicoloView.parteCivileAggiunte  }">
								<tr>
									<td><spring:message code="fascicolo.label.tabella.no.dati"></spring:message>
									</td>
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


<!-- TIPO RESPONSABILE CIVILE-->
<div class="list-group-item media">
	<a name="anchorResponsabileCivile"></a>
	<div class="media-body">
		<div class="form-group">
			<label for="tipoResponsabileCivile" class="col-sm-2 control-label"><spring:message
					text="??fascicolo.label.tipoResponsabileCivile??"
					code="fascicolo.label.tipoResponsabileCivile" /></label>
			<div class="col-sm-10">
				<form:select id="comboTipoResponsabileCivile" path="tipoResponsabileCivileCode"
					cssClass="form-control">
					<form:option value="">
						<spring:message
							text="??fascicolo.label.selezionaTipoResponsabileCivile??"
							code="fascicolo.label.selezionaTipoResponsabileCivile" />
					</form:option>
					<c:if test="${ fascicoloView.listaTipoSoggetto != null }">
						<c:forEach items="${fascicoloView.listaTipoSoggetto}"
							var="oggetto">
							<form:option value="${ oggetto.vo.codGruppoLingua }">
								<c:out value="${oggetto.vo.nome}"></c:out>
							</form:option>
						</c:forEach>
					</c:if>

				</form:select>
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
				<div class="col-sm-10">
					<form:input path="nomeResponsabileCivile" cssClass="form-control autocomplete" id="txtResponsabileCivile"/>
				</div>
			
				<div class="col-sm-2">
					<button onclick="aggiungiResponsabileCivile()" type="button"
						class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini">
						<i class="zmdi zmdi-plus icon-mini"></i>
					</button>
				</div>
					<div class="table-responsive" style="clear: both;">
					<table class="table table-striped table-responsive"
						style="margin-top: 20px;">
						<thead>
							<tr style="border: 1px solid #e0e0e0">
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
								test="${ fascicoloView.responsabileCivileAggiunte != null }">
								<c:forEach items="${fascicoloView.responsabileCivileAggiunte}"
									var="soggettoTmp" varStatus="indiceArray">
									<tr>
										<td colspan="2">(${soggettoTmp.tipoResponsabileCivile })
											<c:if test="${ not empty soggettoTmp.vo}"> 
												<legarc:decrypt idOggetto="${soggettoTmp.vo.id}" dato="${soggettoTmp.nomeResponsabileCivile }"></legarc:decrypt>
											</c:if>
											<c:if test="${ empty soggettoTmp.vo}"> 
												${soggettoTmp.nomeResponsabileCivile }
											</c:if>   
										<td>
											<button
												onclick="rimuoviResponsabileCivile(${indiceArray.index})"
												type="button"
												class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini">
												<i class="fa fa-trash icon-mini"></i>
											</button>
										</td>
									</tr>
								</c:forEach>
							</c:if>
	
							<c:if
								test="${ fascicoloView.parteCivileAggiunte == null }">
								<tr>
									<td><spring:message code="fascicolo.label.tabella.no.dati"></spring:message>
									</td>
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
 

<!-- GIUDIZIO--> 
<div class="list-group-item media">
	<a name="anchorGiudizio"></a>
	<div class="media-body">
		<div class="form-group">
			<label for="giudizio" class="col-sm-2 control-label"><spring:message
					text="??fascicolo.label.giudizio??"
					code="fascicolo.label.giudizio" /></label>
			<div class="col-sm-10"> 
				<c:if test="${ empty fascicoloView.giudiziAggiunti  }">
					<button type="button" data-toggle="modal" onclick="selezionaGiudizio('')"
						data-target="#panelFormGiudizio"
						class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
						style="float: left; position: relative !important;">
						<i class="zmdi zmdi-plus icon-mini"></i>
					</button>
				</c:if>
				
				<div class="table-responsive" style="clear: both;">
					<table class="table table-striped table-responsive"
						style="margin-top: 20px;">
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
							<c:if 	test="${ not empty fascicoloView.giudiziAggiunti }">
								<c:forEach items="${fascicoloView.giudiziAggiunti}"
									var="giudizioAggiunto" varStatus="indiceArray">
									<tr>
										<td colspan="2">${giudizioAggiunto.vo.giudizio.descrizione}</td>
										<td>${giudizioAggiunto.vo.organoGiudicante.nome}</td>
										<td>${giudizioAggiunto.foro}</td>
										<td>${giudizioAggiunto.numeroRegistroCausa}</td>
										<td>${giudizioAggiunto.note}</td>
										<td>
											<button data-toggle="modal" data-target="#panelFormGiudizio" 
												onclick="editaGiudizioAggiunto(${indiceArray.index})"
												type="button"
												class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini">
												<i class="fa fa-edit icon-mini"></i>
											</button>
										</td>
										<td>	
											<button
												onclick="rimuoviGiudizioAggiunto(${indiceArray.index})"
												type="button"
												class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini">
												<i class="fa fa-trash icon-mini"></i>
											</button>
										</td>
									</tr>
								</c:forEach>
							</c:if>

							<c:if
								test="${ empty fascicoloView.giudiziAggiunti  }">
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
 </form>