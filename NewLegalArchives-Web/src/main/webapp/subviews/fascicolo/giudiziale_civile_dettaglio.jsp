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
				<c:if test="${ fascicoloDettaglioView.listaSocieta != null }">
					<form:select id="posizioneSocietaAddebito"
						path="posizioneSocietaAddebitoCode" cssClass="form-control">
						<form:option value="">
							<spring:message
								text="??fascicolo.label.selezionaPosizioneSocieta??"
								code="fascicolo.label.selezionaPosizioneSocieta" />
						</form:option>

						<c:if test="${ fascicoloDettaglioView.listaPosizioneSocieta != null }">
							<c:forEach items="${fascicoloDettaglioView.listaPosizioneSocieta}"
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


<!-- NOME CONTROPARTE-->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="nomeControparte" class="col-sm-2 control-label"><spring:message
					text="??fascicolo.label.nomeControparte??"
					code="fascicolo.label.nomeControparte" /></label>
			<div class="col-sm-10">

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
							<c:if test="${not empty fascicoloDettaglioView.contropartiAggiunte }">
								<c:forEach items="${fascicoloDettaglioView.contropartiAggiunte}"
									var="controparteTmp" varStatus="indiceArray">
									<tr>
										<td colspan="2">(${controparteTmp.tipoControparte })
											${controparteTmp.nomeControparte }</td>
										 
									</tr>
								</c:forEach>
							</c:if>

							<c:if test="${ empty fascicoloDettaglioView.contropartiAggiunte  }">
								<tr>
									<td colspan="2"><spring:message code="fascicolo.label.tabella.no.dati"></spring:message>
									</td> 
								</tr>
							</c:if>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>


<!-- TERZO CHIAMATO IN CAUSA-->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="terzoChiamatoInCausa" class="col-sm-2 control-label"><spring:message
					text="??fascicolo.label.terzoChiamatoInCausa??"
					code="fascicolo.label.terzoChiamatoInCausa" /></label>
			<div class="col-sm-10">

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
								test="${ not empty fascicoloDettaglioView.terzoChiamatoInCausaAggiunti }">
								<c:forEach items="${fascicoloDettaglioView.terzoChiamatoInCausaAggiunti}"
									var="terzoChiamatoCausa" varStatus="indiceArray">
									<tr style="border:1px solid #e0e0e0">
										<td colspan="2">${terzoChiamatoCausa.nomeTerzoChiamatoCausa}</td>
										<td>${terzoChiamatoCausa.tipoTerzoChiamatoCausa}</td>
										<td>${terzoChiamatoCausa.legaleRiferimento}</td>
										<td> 
										</td>
									</tr>
								</c:forEach>
							</c:if>

							<c:if
								test="${ empty fascicoloDettaglioView.terzoChiamatoInCausaAggiunti  }">
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

<!-- GIUDIZIO--> 
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="giudizio" class="col-sm-2 control-label"><spring:message
					text="??fascicolo.label.giudizio??"
					code="fascicolo.label.giudizio" /></label>
			<div class="col-sm-10">  
				
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
								<th data-column-id="04"></th>	
								<th data-column-id="06"></th>	
							</tr>
						</thead>
						<tbody id="boxGiudizio" class="collapse in">
							<c:if
								test="${ not empty fascicoloDettaglioView.giudiziAggiunti }">
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
