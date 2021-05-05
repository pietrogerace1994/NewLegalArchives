<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld" %>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>

<!-- TIPO CONTROPARTE-->
<div class="list-group-item media">
	<a name="anchorControparte"></a>
	<div class="media-body">
		<div class="form-group">
			<label for="tipoControparteId" class="col-sm-2 control-label"><spring:message
					text="??fascicolo.label.tipoControparte??"
					code="fascicolo.label.tipoControparte" /></label>
			<div class="col-sm-10">
				<form:select id="comboTipoControparte" path="tipoControparteCode"
					cssClass="form-control">
					<form:option value="">
						<spring:message
							text="??fascicolo.label.selezionaTipoControparte??"
							code="fascicolo.label.selezionaTipoControparte" />
					</form:option>
					<c:if test="${ fascicoloView.listaTipoControparte != null }">
						<c:forEach items="${fascicoloView.listaTipoControparte}"
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

<!-- NOME CONTROPARTE-->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="nomeControparte" class="col-sm-2 control-label"><spring:message
					text="??fascicolo.label.nomeControparte??"
					code="fascicolo.label.nomeControparte" /></label>
			<div class="col-sm-10">
				<div class="col-sm-10">
					<form:input path="nomeControparte" cssClass="form-control autocomplete" id="txtControparte"/>
				</div>
			
				<div class="col-sm-2">
					<button onclick="aggiungiControparte()" type="button"
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
										data-target="#boxControparte"
										class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
										style="float: left; position: relative !important;">
										<i class="zmdi zmdi-collection-text icon-mini"></i>
									</button>
								</th>
								<th data-column-id="id"><spring:message
										text="??fascicolo.label.nomeControparte??"
										code="fascicolo.label.nomeControparte" /></th> 
							</tr>
						</thead>
						<tbody id="boxControparte" class="collapse in">
							<c:if
								test="${ not empty fascicoloView.contropartiAggiunte }">
								<c:forEach items="${fascicoloView.contropartiAggiunte}"
									var="controparteTmp" varStatus="indiceArray">
									<tr>
										<td>(${controparteTmp.tipoControparte }) ${controparteTmp.nomeControparte }</td>
										<td>
											<button
												onclick="rimuoviControparte(${indiceArray.index})"
												type="button"
												class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini">
												<i class="fa fa-trash icon-mini"></i>
											</button>
										</td>
									</tr>
								</c:forEach>
							</c:if>
	
							<c:if
								test="${ empty fascicoloView.contropartiAggiunte }">
								<tr>
									<td><spring:message code="fascicolo.label.tabella.no.dati"></spring:message>
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
