<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>

	<!--SETTORE GIURIDICO-->
	<tr>
		<td style="width:30%">
			<label for="settoreGiuridico" class="col-sm-12 control-label"><spring:message
				text="??fascicolo.label.settoreGiuridico??"
				code="fascicolo.label.settoreGiuridico" /></label>
		</td>
		<td style="width:70%">
			<form:select path="settoreGiuridicoCode"  disabled="true"  cssClass="form-control">
				<form:option value="">
					<spring:message
						text="??fascicolo.label.selezionaSettoreGiuridico??"
						code="fascicolo.label.selezionaSettoreGiuridico" />
				</form:option>

				<c:if
					test="${ fascicoloDettaglioView.listaSettoreGiuridico != null }">
					<c:forEach
						items="${fascicoloDettaglioView.listaSettoreGiuridico}"
						var="oggetto">
						<form:option value="${ oggetto.vo.codGruppoLingua }">
							<c:out value="${oggetto.vo.nome}"></c:out>
						</form:option>
					</c:forEach>
				</c:if>
			</form:select>
		</td>
	</tr>

	<!-- NAZIONE-->
	<tr>
		<td style="width:30%">
			<label for="nazioneCode" class="col-sm-12 control-label"><spring:message
				text="??fascicolo.label.nazione??" code="fascicolo.label.nazione" /></label>
		</td>
		<td style="width:70%">
			<form:select id="nazioneCode" path="nazioneCode" disabled="true" cssClass="form-control">
				<form:option value="">
					<spring:message text="??fascicolo.label.selezionaNazione??"
						code="fascicolo.label.selezionaNazione" />
				</form:option>
				<c:if test="${ fascicoloDettaglioView.listaNazioni != null }">
					<c:forEach items="${fascicoloDettaglioView.listaNazioni}"
						var="oggetto">
						<c:if test="${ oggetto.vo.soloParteCorrelata eq 'F' }">
							<form:option value="${ oggetto.vo.codGruppoLingua }">
								<c:out value="${oggetto.vo.descrizione}"></c:out>
							</form:option>
						</c:if>
					</c:forEach>
				</c:if>
			</form:select>
		</td>
	</tr>
	
	<!-- TITOLO -->
	<tr>
		<td style="width:30%">
			<label for="titolo" class="col-sm-12 control-label"><spring:message
					text="??fascicolo.label.titolo??" code="fascicolo.label.titolo" /></label>
		</td>
		<td style="width:70%">
			<form:input path="titolo" cssClass="form-control" id="titolo" disabled/>
		</td>
	</tr>

	<!-- OGGETTO-->
	<tr>
		<td style="width:30%">
			<label for="oggettoSintetico" class="col-sm-12 control-label"><spring:message
				text="??fascicolo.label.oggettoSintetico??"
				code="fascicolo.label.oggettoSintetico" /></label>
		</td>
		<td style="width:70%">
			<form:textarea path="oggettoSintetico" cols="70" rows="8" disabled="true" cssClass="form-control" />
		</td>
	</tr>
	
	<!-- DESCRIZIONE-->
	<tr>
		<td style="width:30%">
			<label for="descrizione" class="col-sm-12 control-label"><spring:message
				text="??fascicolo.label.descrizione??"
				code="fascicolo.label.descrizione" /></label>
		</td>
		<td style="width:70%">
			<form:textarea path="descrizione" cols="70" rows="8" disabled="true" cssClass="form-control" />
		</td>
	</tr>
	
	<!-- SOCIETA DI ADDEBITO-->
	<tr>
		<td style="width:30%">
			<label for="societaAddebito" class="col-sm-12 control-label"><spring:message
				text="??fascicolo.label.societaAddebito??"
				code="fascicolo.label.societaAddebito" /></label>
		</td>
		<td style="width:70%">
			<table >
				<tbody id="boxSocietaAddebito">
					<c:if
						test="${ fascicoloDettaglioView.listaSocietaAddebitoAggiunteDesc != null }">
						<c:forEach
							items="${fascicoloDettaglioView.listaSocietaAddebitoAggiunteDesc}"
							var="descrizione">
							<tr>
								<td>${descrizione}</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
		</td>
	</tr>
	
	<!-- CENTRO DI COSTO -->
	<tr>
		<td style="width:30%">
			<label for="centroDiCosto" class="col-sm-12 control-label"><spring:message
				text="??proforma.label.centroDiCosto??"
				code="proforma.label.centroDiCosto" /></label>
		</td>
		<td style="width:70%">
			<form:input path="centroDiCosto" id="txtCentroDiCosto" disabled="true" cssClass="form-control"/>
		</td>
	</tr>
	
	<!-- VOCE DI CONTO -->
	<tr>
		<td style="width:30%">
			<label for="voceDiConto" class="col-sm-12 control-label"><spring:message
				text="??proforma.label.voceDiConto??"
				code="proforma.label.voceDiConto" /></label>
		</td>
		<td style="width:70%">
			<form:input path="voceDiConto" id="txtvoceDiConto" disabled="true" cssClass="form-control"/>
		</td>
	</tr>
	
	<!-- TIPO PRESTAZIONE NOTARILE-->
	<tr>
		<td style="width:30%">
			<label for="tipoPrestazioneNotarile" class="col-sm-12 control-label"><spring:message
				text="??fascicolo.label.tipoPrestazioneNotarile??"
				code="fascicolo.label.tipoPrestazioneNotarile" /></label>
		</td>
		<td style="width:70%">
			<table >
				<tbody id="boxTipoPrestazioneNotarile" class="collapse in">
					<c:if
						test="${ not empty fascicoloDettaglioView.listaTipoPrestazioneNotarile  }">
						<c:forEach
							items="${fascicoloDettaglioView.listaTipoPrestazioneNotarile}"
							var="oggetto">
							<tr>
								<td><form:checkbox
										value="${ oggetto.vo.codGruppoLingua }" disabled="true" 
										path="tipoPrestazioneNotarileAggiunte"></form:checkbox></td>
								<td>${oggetto.vo.nome}</td>
							</tr>
						</c:forEach>
					</c:if>

					<c:if
						test="${ empty fascicoloDettaglioView.listaTipoPrestazioneNotarile  }">
						<tr>
							<td><spring:message code="fascicolo.label.tabella.no.dati"></spring:message></td>
							<td></td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</td>
	</tr>

	<!-- LEGALE INTERNO-->
	<tr>
		<td style="width:30%">
			<label for="legaleInterno" class="col-sm-12 control-label"><spring:message
				text="??fascicolo.label.legaleInterno??"
				code="fascicolo.label.legaleInterno" /></label>
		</td>
		<td style="width:70%">
			<spring:message var="lblLegaleCorrenteDesc" text="??fascicolo.label.legaleInternoDal??" code="fascicolo.label.legaleInternoDal"
				arguments="${v.ownerDal}|" argumentSeparator="|" />
			<input id="legaleInternoDesc" disabled="true" class="form-control" value="${fascicoloDettaglioView.legaleInternoDesc} ${lblLegaleCorrenteDesc}" />
		</td>
	</tr>
	
	<!-- UNITA LEGALE-->
	<tr>
		<td style="width:30%">
			<label for="unitaLegale" class="col-sm-12 control-label"><spring:message
				text="??fascicolo.label.unitaLegale??"
				code="fascicolo.label.unitaLegale" /></label>
		</td>
		<td style="width:70%">
			<form:input path="unitaLegaleDesc" disabled="true" cssClass="form-control" />
		</td>
	</tr>
	
	<!-- VALORE -->
	<tr>
		<td style="width:30%">
			<label for="valore" class="col-sm-12 control-label"><spring:message
				text="??fascicolo.label.valore??" code="fascicolo.label.valore" /></label>
		</td>
		<td style="width:70%">
			<form:input path="valore" cssClass="form-control" id="txtValore" disabled="true" />
		</td>
	</tr>

	<!-- SIGLA DEL CLIENTE -->
	<tr>
		<td style="width:30%">
			<label for="siglaCliente" class="col-sm-12 control-label"><spring:message
				text="??fascicolo.label.siglaCliente??"
				code="fascicolo.label.siglaCliente" /></label>
		</td>
		<td style="width:70%">
			<form:input path="siglaCliente" cssClass="form-control" disabled="true" />
		</td>
	</tr>

	<!-- PROGETTO -->
	<tr>
		<td style="width:30%">
			<label for="valore" class="col-sm-12 control-label"><spring:message
				text="??fascicolo.label.progetto??"
				code="fascicolo.label.progetto" /></label>
		</td>
		<td style="width:70%">
			<form:input type="text" path="progettoNome" class="form-control"  disabled="true"  value="${fascicoloDettaglioView.progettoNome }"/>
		</td>
	</tr>

	<!-- FASCICOLO PADRE -->
	<tr>
		<td style="width:30%">
			<label for="fascicoloPadreSelezionato" class="col-sm-12 control-label"><spring:message
				text="??fascicolo.label.fascicoloPadre??"
				code="fascicolo.label.fascicoloPadre" /></label>
		</td>
		<td style="width:70%">
			<input type="text" id="fascicoloPadreSelezionato" disabled="true" class="form-control"
				value="${fascicoloDettaglioView.fascicoloPadreNome }" name="fascicoloPadreNome" />
		</td>
	</tr>

	<!-- FASCICOLO CORRELATO -->
	<tr>
		<td style="width:30%">
			<label for="fascicoliCorrelati" class="col-sm-12 control-label"><spring:message
				text="??fascicolo.label.fascicoliCorrelati??"
				code="fascicolo.label.fascicoliCorrelati" /></label>
		</td>
		<td style="width:70%">
			<table  style="margin-top: 20px;">
				<tbody id="boxFascicoliCorrelati" class="collapse in">
					<c:if
						test="${ not empty fascicoloDettaglioView.fascicoliCorrelatiAggiunti  }">
						<c:forEach
							items="${fascicoloDettaglioView.fascicoliCorrelatiAggiunti}"
							var="fascicoloCorrelato" varStatus="indiceArray">
							<tr>
								<td>${fascicoloCorrelato.vo.nome}</td>
								<td></td>
							</tr>
						</c:forEach>
					</c:if>

					<c:if
						test="${ empty fascicoloDettaglioView.fascicoliCorrelatiAggiunti }">
						<tr>
							<td><spring:message
									code="fascicolo.label.tabella.no.dati"></spring:message></td>
							<td></td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</td>
	</tr>

	
	<c:if test="${ fascicoloDettaglioView.fascicoloId != null && fascicoloDettaglioView.fascicoloId > 0 }">
		<!-- N ARCHIVIO -->
		<tr>
			<td style="width:30%">
				<label for="numeroArchivio" class="col-sm-12 control-label"><spring:message
					text="??fascicolo.label.numeroArchivio??"
					code="fascicolo.label.numeroArchivio" /></label>
			</td>
			<td style="width:70%">
				<form:input path="numeroArchivio" cssClass="form-control" disabled="true" />
			</td>
		</tr>

		<!-- N ARCHIVIO CONTENITORE -->
		<tr>
			<td style="width:30%">
				<label for="numeroArchivioContenitore" class="col-sm-12 control-label"><spring:message
					text="??fascicolo.label.numeroArchivioContenitore??"
					code="fascicolo.label.numeroArchivioContenitore" /></label>
			</td>
			<td style="width:70%">
				<form:input path="numeroArchivioContenitore" disabled="true" cssClass="form-control" />
			</td>
		</tr>
	</c:if>
