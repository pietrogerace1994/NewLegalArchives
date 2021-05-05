<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>


<!-- SOCIETA POSIZIONE-->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="societaAddebito" class="col-sm-2 control-label"><spring:message
					text="??fascicolo.label.posizioneSocietaAddebito??"
					code="fascicolo.label.posizioneSocietaAddebito" /></label>
			<div class="col-sm-10">
				<c:if test="${ fascicoloView.listaSocieta != null }">
					<form:select id="posizioneSocietaAddebito"
						path="posizioneSocietaAddebitoCode" cssClass="form-control">
						<form:option value="">
							<spring:message
								text="??fascicolo.label.selezionaPosizioneSocieta??"
								code="fascicolo.label.selezionaPosizioneSocieta" />
						</form:option>

						<c:if test="${ fascicoloView.listaPosizioneSocieta != null }">
							<c:forEach items="${fascicoloView.listaPosizioneSocieta}"
								var="oggetto">
								<form:option value="${ oggetto.vo.codGruppoLingua }">
									<c:out value="${oggetto.vo.nome}"></c:out>
								</form:option>
							</c:forEach>
						</c:if>
					</form:select>
				</c:if>
			</div>
		</div>
	</div>
</div>

<!-- AUTORITA EMANANTE-->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="autoritaEmanante" class="col-sm-2 control-label"><spring:message
					text="??fascicolo.label.autoritaEmanante??"
					code="fascicolo.label.autoritaEmanante" /></label>
			<div class="col-sm-10">
				 <form:input path="autoritaEmanante" id="txtAutoritaEmanante" cssClass="form-control"/>
			</div>
		</div>
	</div>
</div>

<!-- CONTROINTERESSATO-->
<div class="list-group-item media" id="divContro">
	<div class="media-body">
		<div class="form-group">
			<label for="controinteressato" class="col-sm-2 control-label"><spring:message
					text="??fascicolo.label.controinteressato??"
					code="fascicolo.label.controinteressato" /></label>
			<div class="col-sm-10">
				 <form:input path="controinteressato" id="txtControinteressato" cssClass="form-control"/>
			</div>
		</div>
	</div>
</div>

<!-- RESISTENTE-->
<div class="list-group-item media" id="divResis">
	<div class="media-body">
		<div class="form-group">
			<label for="resistente" class="col-sm-2 control-label"><spring:message
					text="??fascicolo.label.resistente??"
					code="fascicolo.label.resistente" /></label>
			<div class="col-sm-10">
				 <form:input path="resistente" id="txtResistente" cssClass="form-control"/>
			</div>
		</div>
	</div>
</div>

<!-- RICORRENTE-->
<div class="list-group-item media" id="divRicor">
	<div class="media-body">
		<div class="form-group">
			<label for="ricorrente" class="col-sm-2 control-label"><spring:message
					text="??fascicolo.label.ricorrente??"
					code="fascicolo.label.ricorrente" /></label>
			<div class="col-sm-10">
				 <form:input path="ricorrente" id="txtRicorrente" cssClass="form-control"/>
			</div>
		</div>
	</div>
</div>

 
<!-- RICORSO--> 
<div class="list-group-item media">
	<a name="anchorRicorso"></a>
	<div class="media-body">
		<div class="form-group">
			<label for="ricorso" class="col-sm-2 control-label"><spring:message
					text="??fascicolo.label.ricorso??"
					code="fascicolo.label.ricorso" /></label>
			<div class="col-sm-10"> 
				<c:if test="${ empty fascicoloView.giudiziAggiunti  }">
					<button type="button" data-toggle="modal" onclick="selezionaRicorso('')"
						data-target="#panelFormRicorso"
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
										data-target="#boxRicorso"
										class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
										style="float: left; position: relative !important;">
										<i class="zmdi zmdi-collection-text icon-mini"></i>
									</button>
								</th>
								<th data-column-id="id"><spring:message
										text="??fascicolo.label.ricorso??"
										code="fascicolo.label.ricorso" /></th>
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
								<th data-column-id="04"></th>	
								<th data-column-id="06"></th>	
							</tr>
						</thead>
						<tbody id="boxRicorso" class="collapse in">
							<c:if
								test="${ not empty fascicoloView.ricorsiAggiunti }">
								<c:forEach items="${fascicoloView.ricorsiAggiunti}"
									var="ricorsoAggiunto" varStatus="indiceArray">
									<tr>
										<td colspan="2">${ricorsoAggiunto.vo.ricorso.descrizione}</td>
										<td>${ricorsoAggiunto.vo.organoGiudicante.nome}</td>
										<td>${ricorsoAggiunto.foro}</td>
										<td>${ricorsoAggiunto.numeroRegistroCausa}</td>
										<td>${ricorsoAggiunto.note}</td>
										<td>
											<button data-toggle="modal" data-target="#panelFormRicorso" 
												onclick="editaRicorsoAggiunto(${indiceArray.index})"
												type="button"
												class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini">
												<i class="fa fa-edit icon-mini"></i>
											</button>
										</td>
										<td>	
											<button
												onclick="rimuoviRicorsoAggiunto(${indiceArray.index})"
												type="button"
												class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini">
												<i class="fa fa-trash icon-mini"></i>
											</button>
										</td>
									</tr>
								</c:forEach>
							</c:if>

							<c:if
								test="${ empty fascicoloView.ricorsiAggiunti  }">
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
