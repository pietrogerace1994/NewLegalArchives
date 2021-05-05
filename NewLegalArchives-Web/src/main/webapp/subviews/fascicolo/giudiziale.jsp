<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>

<c:set var="tab1StyleAttiva" scope="page" value="" />
<c:set var="tab2StyleAttiva" scope="page" value="" />
<c:set var="tab3StyleAttiva" scope="page" value="" />

<c:if test="${fascicoloView.tabAttiva eq '1' }">
	<c:set var="tab1StyleAttiva" scope="page" value="active" />
</c:if>
<c:if test="${fascicoloView.tabAttiva eq '2' }">
	<c:set var="tab2StyleAttiva" scope="page" value="active" />
</c:if>
<c:if test="${fascicoloView.tabAttiva eq '3' }">
	<c:set var="tab3StyleAttiva" scope="page" value="active" />
</c:if>


<div role="tabpanel"
	class="tab-pane animated fadeIn in ${tab2StyleAttiva }" id="tab-2">

	<!--SETTORE GIURIDICO-->
	<div class="list-group-item media">
		<div class="media-body">
			<div class="form-group">
				<label for="settoreGiuridico" class="col-sm-2 control-label"><spring:message
						text="??fascicolo.label.settoreGiuridico??"
						code="fascicolo.label.settoreGiuridico" /></label>
				<div class="col-sm-10">

				<c:if test="${ fascicoloView.fascicoloId ne null && fascicoloView.fascicoloId ne 0 }">
					<form:select path="settoreGiuridicoCode" disabled="true"
						onchange="selezionaSettoreGiuridico(this.value)"
						cssClass="form-control">
						<form:option value="">
							<spring:message
								text="??fascicolo.label.selezionaSettoreGiuridico??"
								code="fascicolo.label.selezionaSettoreGiuridico" />
						</form:option>

						<c:if test="${ fascicoloView.listaSettoreGiuridico != null }">
							<c:forEach items="${fascicoloView.listaSettoreGiuridico}"
								var="oggetto">
								<form:option value="${ oggetto.vo.codGruppoLingua }">
									<c:out value="${oggetto.vo.nome}"></c:out>
								</form:option>
							</c:forEach>
						</c:if>
					</form:select>
				</c:if>
				
				<c:if test="${ fascicoloView.fascicoloId eq null || fascicoloView.fascicoloId eq 0 }">
				
					<form:select path="settoreGiuridicoCode"
						onchange="selezionaSettoreGiuridico(this.value)"
						cssClass="form-control">
						<form:option value="">
							<spring:message
								text="??fascicolo.label.selezionaSettoreGiuridico??"
								code="fascicolo.label.selezionaSettoreGiuridico" />
						</form:option>

						<c:if test="${ fascicoloView.listaSettoreGiuridico != null }">
							<c:forEach items="${fascicoloView.listaSettoreGiuridico}"
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

	<!-- MATERIA -->
	<div class="list-group-item media">
		<div class="media-body">
			<div class="form-group">
				<label for="comboMaterie" class="col-sm-2 control-label"><spring:message
						text="??fascicolo.label.materia??" code="fascicolo.label.materia" /></label>
				<div class="col-sm-10">
					 <div id="treeContainerMaterie" style="max-height:250px;overflow:auto;">
					 	
					 </div> 
				</div>
			</div>
		</div>
	</div>

	<!-- NAZIONE-->
	<div class="list-group-item media">
		<div class="media-body">
			<div class="form-group">
				<label for="nazioneCode" class="col-sm-2 control-label"><spring:message
						text="??fascicolo.label.nazione??" code="fascicolo.label.nazione" /></label>
				<div class="col-sm-10">
					<form:select id="nazioneCode" path="nazioneCode"
						cssClass="form-control">
						<form:option value="">
							<spring:message text="??fascicolo.label.selezionaNazione??"
								code="fascicolo.label.selezionaNazione" />
						</form:option>
						<c:if test="${ fascicoloView.listaNazioni != null }">
							<c:forEach items="${fascicoloView.listaNazioni}" var="oggetto">
								<c:if test="${ oggetto.vo.soloParteCorrelata eq 'F' }">
									<form:option value="${ oggetto.vo.codGruppoLingua }">
										<c:out value="${oggetto.vo.descrizione}"></c:out>
									</form:option>
								</c:if>
							</c:forEach>
						</c:if>

					</form:select>
				</div>
			</div>
		</div>
	</div>

	<!-- titolo-->
	<div class="list-group-item media">
		<div class="media-body">
			<div class="form-group">
				<label for="titolo" class="col-sm-2 control-label"><spring:message
						text="??fascicolo.label.titolo??" code="fascicolo.label.titolo" /></label>
				<div class="col-sm-10">
					
					<form:input path="titolo" cssClass="form-control" id="titolo" />
				</div>
			</div>
		</div>
	</div>
	
	<!-- OGGETTO-->
	<div class="list-group-item media">
		<div class="media-body">
			<div class="form-group">
				<label for="oggettoSintetico" class="col-sm-2 control-label"><spring:message
						text="??fascicolo.label.oggettoSintetico??"
						code="fascicolo.label.oggettoSintetico" /></label>
				<div class="col-sm-10">
					<form:textarea path="oggettoSintetico" cols="70" rows="8"
						cssClass="form-control" />
				</div>
			</div>
		</div>
	</div>


	<!-- DESCRIZIONE-->
	<div class="list-group-item media">
		<div class="media-body">
			<div class="form-group">
				<label for="descrizione" class="col-sm-2 control-label"><spring:message
						text="??fascicolo.label.descrizione??"
						code="fascicolo.label.descrizione" /></label>
				<div class="col-sm-10">
					<form:textarea path="descrizione" cols="70" rows="8"
						cssClass="form-control" />
				</div>
			</div>
		</div>
	</div>


	<!-- SOCIETA PARTE PROCEDIMENTO-->
	<div class="list-group-item media">
		<div class="media-body">
			<div class="form-group">
				<label for="societa" class="col-sm-2 control-label"><spring:message
						text="??fascicolo.label.societaParteProcedimento??"
						code="fascicolo.label.societaParteProcedimento" /></label>
				<div class="col-sm-10">
					<div class="table-responsive" style="clear:both;">
						<table class="table table-striped table-responsive" >
							<thead>
								<tr style="border:1px solid #e0e0e0">
									<th data-column-id="01" style="width:50px"> 
										<button  type="button"  data-toggle="collapse" data-target="#boxSocietaProcedimento" 
											class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"  style="float: left;position: relative !important;">
											<i class="zmdi zmdi-collection-text icon-mini"></i>
										</button>
									</th> 
									<th data-column-id="id"><spring:message
											text="??fascicolo.label.societaParteProcedimento??"
											code="fascicolo.label.societaParteProcedimento" /></th>
									
								</tr>
							</thead>
							<tbody id="boxSocietaProcedimento" class="collapse in">
								<c:if test="${ fascicoloView.listaSocieta != null }">
									<c:forEach items="${fascicoloView.listaSocieta}" var="oggetto">
									    <tr>
											<td>
												<form:checkbox value="${ oggetto.vo.id }"
													path="societaProcedimentoAggiunte"></form:checkbox>
											</td>	
											<td>
												${oggetto.vo.nome}
											</td>											
										</tr>	
									</c:forEach>
								</c:if>
							</tbody>
						</table>
					</div>			
				</div>
			</div>
		</div>
	</div>


	<!-- SOCIETA DI ADDEBITO-->
	<div class="list-group-item media">
		<div class="media-body">
			<div class="form-group">
				<label for="societaAddebito" class="col-sm-2 control-label"><spring:message
						text="??fascicolo.label.societaAddebito??"
						code="fascicolo.label.societaAddebito" /></label>
				<div class="col-sm-10">
					<div class="table-responsive" style="clear:both;">
						<table class="table table-striped table-responsive" >
							<thead>
								<tr style="border:1px solid #e0e0e0">
									<th data-column-id="01" style="width:50px">
										<button  type="button" data-toggle="collapse" data-target="#boxSocietaAddebito"
											class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini" style="float: left;position: relative !important;">
											<i class="zmdi zmdi-collection-text icon-mini" ></i>
										</button>
									</th>
									<th data-column-id="id"><spring:message
											text="??fascicolo.label.societaAddebito??"
											code="fascicolo.label.societaAddebito" /></th>
								
								</tr>
							</thead>
							<tbody id="boxSocietaAddebito" class="collapse in">
								<c:if test="${ fascicoloView.listaSocieta != null }">
									<c:forEach items="${fascicoloView.listaSocieta}" var="oggetto">
									    <tr>
											<td>
												<form:checkbox value="${ oggetto.vo.id }"
													path="societaAddebitoAggiunte"></form:checkbox>
											</td>	
											<td>
												${oggetto.vo.nome}
											</td>											
										</tr>	
									</c:forEach>
								</c:if>
							</tbody>
						</table>
					</div>
					 
				</div>
			</div>
		</div>
	</div>
	
	<!-- CENTRO DI COSTO -->
	<div class="list-group-item media">
		<div class="media-body">
			<div class="form-group">
				<label for="centroDiCosto" class="col-sm-2 control-label">
					<spring:message text="??proforma.label.centroDiCosto??" code="proforma.label.centroDiCosto" />
				</label>
				<div class="col-sm-10">
					<form:input path="centroDiCosto" id="centroDiCosto" cssClass="form-control" />
				</div>
			</div>
		</div>
	</div>
	
	<!-- VOCE DI CONTO -->
	<div class="list-group-item media">
		<div class="media-body">
			<div class="form-group">
				<label for="voceDiConto" class="col-sm-2 control-label">
					<spring:message text="??proforma.label.voceDiConto??" code="proforma.label.voceDiConto" />
				</label>
				<div class="col-sm-10">
					<form:input path="voceDiConto" id="voceDiConto" cssClass="form-control" />
				</div>
			</div>
		</div>
	</div>
	
	
	<c:if test="${ fascicoloView.settoreGiuridicoCode != null }">
		<c:if test="${fascicoloView.settoreGiuridicoCode eq 'TSTT_1'}">
			<jsp:include page="/subviews/fascicolo/giudiziale_civile.jsp"></jsp:include>
		</c:if>
		<c:if test="${fascicoloView.settoreGiuridicoCode eq 'TSTT_3' }">
			<jsp:include page="/subviews/fascicolo/giudiziale_amministrativo.jsp"></jsp:include>
		</c:if>
		<c:if test="${fascicoloView.settoreGiuridicoCode eq 'TSTT_2' }">
			<jsp:include page="/subviews/fascicolo/giudiziale_penale.jsp"></jsp:include>
		</c:if>
		<c:if test="${fascicoloView.settoreGiuridicoCode eq 'TSTT_6' }">
			<jsp:include page="/subviews/fascicolo/giudiziale_arbitrale.jsp"></jsp:include>
		</c:if>

	</c:if>
 
</div>
<div role="tabpanel"
	class="tab-pane animated fadeIn in ${tab3StyleAttiva }" id="tab-3">

	<!-- TIPO CONTENZIOSO-->
	<div class="list-group-item media">
		<div class="media-body">
			<div class="form-group">
				<label for="tipoContenzioso" class="col-sm-2 control-label"><spring:message
						text="??fascicolo.label.tipoContenzioso??"
						code="fascicolo.label.tipoContenzioso" /></label>
				<div class="col-sm-10">
					<form:select id="tipoContenziosoCode" path="tipoContenziosoCode"
						cssClass="form-control">
						<form:option value="">
							<spring:message
								text="??fascicolo.label.selezionaTipoContenzioso??"
								code="fascicolo.label.selezionaTipoContenzioso" />
						</form:option>
						<c:if test="${ fascicoloView.listaTipoContenzioso != null }">
							<c:forEach items="${fascicoloView.listaTipoContenzioso}"
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

	<!-- LEGALE INTERNO-->
	<div class="list-group-item media">
		<div class="media-body">
			<div class="form-group">
				<label for="legaleInterno" class="col-sm-2 control-label"><spring:message
						text="??fascicolo.label.legaleInterno??"
						code="fascicolo.label.legaleInterno" /></label>
				<div class="col-sm-10">
					<c:if test="${fascicoloView.fascicoloId != null && fascicoloView.fascicoloId > 0 }">
						<spring:message var="lblLegaleCorrenteDesc" text="??fascicolo.label.legaleInternoDal??"
										code="fascicolo.label.legaleInternoDal" 
										arguments="${fascicoloView.ownerDal}|" argumentSeparator="|"
										/>
					</c:if>			
					<c:if test="${fascicoloView.fascicoloId == null || fascicoloView.fascicoloId == 0 }">
						<c:set var="lblLegaleCorrenteDesc" value="" scope="page"/>				
					</c:if>				
					<input id="legaleInternoDesc" readonly 
						class="form-control" value="${fascicoloView.legaleInternoDesc} ${lblLegaleCorrenteDesc}"/>
					<form:hidden path="legaleInterno" readonly="true"
						cssClass="form-control" />
				</div>
			</div>
		</div>
	</div> 
	
<c:if test="${fascicoloView.fascicoloId != null && fascicoloView.fascicoloId > 0 }">
	<div class="list-group-item media">
		<div class="media-body">
			<div class="form-group">
				<label for="" class="col-sm-2 control-label"><spring:message
						text="??fascicolo.label.vecchiOwnerFascicolo??"
						code="fascicolo.label.vecchiOwnerFascicolo" /></label>
				<div class="col-sm-10"> 
					<div class="table-responsive" style="clear:both;">
						<table class="table table-striped table-responsive" >
							<thead>
								<tr style="border:1px solid #e0e0e0">
									<th data-column-id="01" style="width: 50px">
										<button type="button" data-toggle="collapse"
											data-target="#boxVecchiOwnerFascicolo"
											class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
											style="float: left; position: relative !important;">
											<i class="zmdi zmdi-collection-text icon-mini"></i>
										</button>
									</th>
									<th data-column-id="id"><spring:message
											text="??fascicolo.label.vecchiOwnerFascicolo??"
											code="fascicolo.label.vecchiOwnerFascicolo" /></th> 
									<th></th>		
								</tr>
							</thead>
							<tbody id="boxVecchiOwnerFascicolo" class="collapse in">
								<c:if
									test="${ not empty fascicoloView.utentiFascicolo  }">
									<c:forEach
										items="${fascicoloView.utentiFascicolo}"
										var="utente" varStatus="indiceArray">
										<tr>
											<td colspan="3">${utente.nominativo}
													<spring:message
														text="??fascicolo.label.legaleInternoDalAl??"
														code="fascicolo.label.legaleInternoDalAl"
														arguments="${utente.ownerDal}|${utente.ownerAl}" argumentSeparator="|"/>
											</td> 
										</tr>
									</c:forEach>
								</c:if>

								<c:if
									test="${ empty fascicoloView.utentiFascicolo }">
									<tr>
										<td colspan="2">
											<spring:message code="fascicolo.label.tabella.no.dati"></spring:message>
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
 </c:if>

	<!-- UNITA LEGALE-->
	<div class="list-group-item media">
		<div class="media-body">
			<div class="form-group">
				<label for="unitaLegale" class="col-sm-2 control-label"><spring:message
						text="??fascicolo.label.unitaLegale??"
						code="fascicolo.label.unitaLegale" /></label>
				<div class="col-sm-10">
					<!-- Testo predefinito(in base all'€™UL dell'€™utente loggato all'applicazione) -->
					<form:hidden path="unitaLegale" readonly="true"
						cssClass="form-control" />
					<form:input path="unitaLegaleDesc" readonly="true"
						cssClass="form-control" />
				</div>
			</div>
		</div>
	</div>
	
	<!-- VALORE DELLA CAUSA-->
	<div class="list-group-item media">
		<div class="media-body">
			<div class="form-group">
				<label for="valoreDellaCausa" class="col-sm-2 control-label"><spring:message
						text="??fascicolo.label.valoreDellaCausa??"
						code="fascicolo.label.valoreDellaCausa" /></label>
				<div class="col-sm-10">
					<form:select id="valoreCausa" path="valoreCausaCode"
						cssClass="form-control" onchange="selezionaValoreCausa()">
						<form:option value="">
							<spring:message text="??fascicolo.label.selezionaValoreCausa??"
								code="fascicolo.label.selezionaValoreCausa" />
						</form:option>
						<c:if test="${ fascicoloView.listaValoreCausa != null }">
							<c:forEach items="${fascicoloView.listaValoreCausa}"
								var="oggetto">
								<form:option value="${ oggetto.vo.codGruppoLingua }"									>
									<c:out value="${oggetto.vo.nome}"></c:out>
								</form:option>
							</c:forEach>
						</c:if>

					</form:select>
				</div>
			</div>
		</div>
	</div>


	<!-- VALORE -->
	<div class="list-group-item media">
		<div class="media-body">
			<div class="form-group">
				<label for="valore" class="col-sm-2 control-label"><spring:message
						text="??fascicolo.label.valore??" code="fascicolo.label.valore" /></label>
				<div class="col-sm-10">
					<!-- Valore numerico libero -->
					<form:input path="valore" cssClass="form-control" id="txtValore" />
				</div>
			</div>
		</div>
	</div>

	<!-- SIGLA DEL CLIENTE -->
	<div class="list-group-item media">
		<div class="media-body">
			<div class="form-group">
				<label for="siglaCliente" class="col-sm-2 control-label"><spring:message
						text="??fascicolo.label.siglaCliente??"
						code="fascicolo.label.siglaCliente" /></label>
				<div class="col-sm-10">
					<!-- testo libero -->
					<form:input path="siglaCliente" cssClass="form-control" />
				</div>
			</div>
		</div>
	</div>


	<!-- progetto -->
	<div class="list-group-item media">
		<div class="media-body">
			<div class="form-group">
				<label for="valore" class="col-sm-2 control-label"><spring:message
						text="??fascicolo.label.progetto??"
						code="fascicolo.label.progetto" /></label>
				<div class="col-sm-10">
					<form:input type="text" path="progettoNome" class="form-control" readonly="true"  value="${fascicoloView.progettoNome }"/>
					<form:hidden path="progettoId" />
				</div>
			</div>
		</div>
	</div>


	<!-- FASCICOLO PADRE -->
	<div class="list-group-item media">
		<a name="anchorFascicoloPadre"></a>
		<div class="media-body">
			<div class="form-group">
				<label for="fascicoloPadreSelezionato"
					class="col-sm-2 control-label"><spring:message
						text="??fascicolo.label.fascicoloPadre??"
						code="fascicolo.label.fascicoloPadre" /></label>
				<div class="col-sm-8">
					<input type="text" id="fascicoloPadreSelezionato" readonly 
						class="form-control" value="${fascicoloView.fascicoloPadreNome }" name="fascicoloPadreNome"/>
					<form:hidden path="fascicoloPadreId" />
				</div>
				<div class="col-sm-1">  
					<button type="button" data-toggle="modal" 
								data-target="#panelCercaSelezionaPadreFascicolo"
								class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
								style="float: left; position: relative !important;">
								<i class="zmdi zmdi-plus icon-mini"></i>
					</button>
				</div>
				
				<c:if test="${ not empty fascicoloView.fascicoloPadreId }">
					<div class="col-sm-1">
						<button onclick="rimuoviFascicoloPadre()"
							type="button"
							class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini">
							<i class="fa fa-trash icon-mini"></i>
						</button>
					</div>
				</c:if>
			</div>
		</div>
	</div>



	<!-- FASCICOLO CORRELATO -->
	<div class="list-group-item media">
		<a name="anchorFascicoliCorrelati"></a>
		<div class="media-body">
			<div class="form-group">
				<label for="fascicoliCorrelati" class="col-sm-2 control-label"><spring:message
						text="??fascicolo.label.fascicoliCorrelati??"
						code="fascicolo.label.fascicoliCorrelati" /></label>
				<div class="col-sm-10">
						<button type="button" data-toggle="modal" 
							data-target="#panelCercaSelezionaFascicoli"
							class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
							style="float: left; position: relative !important;">
							<i class="zmdi zmdi-plus icon-mini"></i>
						</button>
					<div class="table-responsive" style="clear:both;">
						<table class="table table-striped table-responsive" style="margin-top: 60px;">
							<thead>
								<tr style="border:1px solid #e0e0e0">
									<th data-column-id="01" style="width: 50px">
										<button type="button" data-toggle="collapse"
											data-target="#boxFascicoliCorrelati"
											class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini"
											style="float: left; position: relative !important;">
											<i class="zmdi zmdi-collection-text icon-mini"></i>
										</button>
									</th>
									<th data-column-id="id"><spring:message
											text="??fascicolo.label.fascicoliCorrelati??"
											code="fascicolo.label.fascicoliCorrelati" /></th> 
									<th></th>		
								</tr>
							</thead>
							<tbody id="boxFascicoliCorrelati" class="collapse in">
								<c:if
									test="${ not empty fascicoloView.fascicoliCorrelatiAggiunti  }">
									<c:forEach
										items="${fascicoloView.fascicoliCorrelatiAggiunti}"
										var="fascicoloCorrelato" varStatus="indiceArray">
										<tr>
											<td colspan="2">${fascicoloCorrelato.vo.nome}</td>
											<td>
												<button onclick="dettaglioFascicolo(${fascicoloCorrelato.vo.id})"
													type="button"
													class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini">
													<i class="fa fa-eye icon-mini"></i>
												</button>
											</td>
											<td>
												<button onclick="rimuoviFascicoloCorrelato(${indiceArray.index})"
													type="button"
													class="btn   bg   waves-effect waves-circle waves-float btn-circle-mini">
													<i class="fa fa-trash icon-mini"></i>
												</button>
											</td>
										</tr>
									</c:forEach>
								</c:if>

								<c:if
									test="${ empty fascicoloView.fascicoliCorrelatiAggiunti }">
									<tr>
										<td colspan="2">
											<spring:message code="fascicolo.label.tabella.no.dati"></spring:message>
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

	<c:if
		test="${ fascicoloView.fascicoloId != null && fascicoloView.fascicoloId > 0 }">
		<!-- N ARCHIVIO -->
		<div class="list-group-item media">
			<div class="media-body">
				<div class="form-group">
					<label for="numeroArchivio" class="col-sm-2 control-label"><spring:message
							text="??fascicolo.label.numeroArchivio??"
							code="fascicolo.label.numeroArchivio" /></label>
					<div class="col-sm-10">
						<form:input path="numeroArchivio" cssClass="form-control" />
					</div>
				</div>
			</div>
		</div>


		<!-- N ARCHIVIO CONTENITORE -->
		<div class="list-group-item media">
			<div class="media-body">
				<div class="form-group">
					<label for="numeroArchivioContenitore"
						class="col-sm-2 control-label"><spring:message
							text="??fascicolo.label.numeroArchivioContenitore??"
							code="fascicolo.label.numeroArchivioContenitore" /></label>
					<div class="col-sm-10">
						<form:input path="numeroArchivioContenitore"
							cssClass="form-control" />
					</div>
				</div>
			</div>
		</div>
	</c:if>

	<!-- RILEVANTE -->
	<div class="list-group-item media">
		<div class="media-body">
			<div class="form-group">
				<label for="rilevante" class="col-sm-2 control-label"><spring:message
						text="??fascicolo.label.rilevante??"
						code="fascicolo.label.rilevante" /></label>
				<div class="col-sm-10">
					<form:checkbox path="rilevante" cssClass="checkbox" />
				</div>
			</div>
		</div>
	</div>
	 
</div>


	
	<div class="modal fade" id="panelFormGiudizio" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">
						<spring:message text="??fascicolo.label.giudizio??"
							code="fascicolo.label.giudizio" />
					</h4>
				</div>
				<div class="modal-body">
					<div  id="containerFormGiudizio">
					
					</div>
				</div>
 			    <div class="modal-footer">
				<!-- Button -->  
					<button id="btnAggiungiGiudizio" name="btnAggiungiGiudizio" data-dismiss="modal"
						data-dismiss="modal" type="button" onclick="aggiungiGiudizio()"
						class="btn btn-primary">
						<spring:message text="??fascicolo.label.ok??"
							code="fascicolo.label.ok" />
					</button>
					<button name="btnChiudiPanelGiudizio" type="button" data-dismiss="modal"
						class="btn btn-warning">
						<spring:message text="??fascicolo.label.chiudi??"
							code="fascicolo.label.chiudi" />
					</button>
					 
				</div>
			</div>
		</div>
	</div>
