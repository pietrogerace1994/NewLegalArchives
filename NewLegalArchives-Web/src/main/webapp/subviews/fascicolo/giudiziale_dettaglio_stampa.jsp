<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>

	<!--SETTORE GIURIDICO-->
	<tr>
		<td style="width:30%">			
		<label class="col-sm-12 control-label" for="settoreGiuridico">
			<spring:message text="??fascicolo.label.settoreGiuridico??" code="fascicolo.label.settoreGiuridico" />
		</label>
		</td>
		  <td style="width:70%">		
			<form:select path="settoreGiuridicoCode" disabled="true" cssClass="form-control">
				<form:option value="">
					<spring:message
						text="??fascicolo.label.selezionaSettoreGiuridico??"
						code="fascicolo.label.selezionaSettoreGiuridico" />
				</form:option>

				<c:if test="${ fascicoloDettaglioView.listaSettoreGiuridico != null }">
					<c:forEach items="${fascicoloDettaglioView.listaSettoreGiuridico}"
						var="oggetto">
						<form:option value="${ oggetto.vo.codGruppoLingua }">
							<c:out value="${oggetto.vo.nome}"></c:out>
						</form:option>
					</c:forEach>
				</c:if>
			</form:select>
		</td>
	</tr>
	

	<!-- MATERIA -->
	<tr>
		<td style="width:30%">
				<label for="comboMaterie" class="col-sm-12 control-label"><spring:message
						text="??fascicolo.label.materia??" code="fascicolo.label.materia" /></label>
						
		</td>
		<td style="width:70%">
			<table >
				<tbody id="boxMateria">
					<c:if test="${ fascicoloDettaglioView.listaMaterieAggiunteDesc != null }">
						<c:forEach items="${fascicoloDettaglioView.listaMaterieAggiunteDesc}"
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
					<c:forEach items="${fascicoloDettaglioView.listaNazioni}" var="oggetto">
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
			<form:input path="titolo" disabled="true" cssClass="form-control" id="titolo"/>
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
	
	<!-- SOCIETA PARTE PROCEDIMENTO-->
	<tr>
		<td style="width:30%">
			<label for="societa" class="col-sm-12 control-label"><spring:message
					text="??fascicolo.label.societaParteProcedimento??"
					code="fascicolo.label.societaParteProcedimento" /></label>
		</td>
		<td style="width:70%">
			<table >
				<tbody id="boxSocietaProcedimento">
					<c:if
						test="${ fascicoloDettaglioView.listaSocietaProcAggiunteDesc != null }">
						<c:forEach
							items="${fascicoloDettaglioView.listaSocietaProcAggiunteDesc}"
							var="descrizione">
							<tr>
								<td colspan="2">${descrizione}</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
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
								<td colspan="2">${descrizione}</td>
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
	
	<c:if test="${ fascicoloDettaglioView.settoreGiuridicoCode != null }">
		<c:if test="${fascicoloDettaglioView.settoreGiuridicoCode eq 'TSTT_1'}">
			
			<!-- SOCIETA POSIZIONE-->
			<tr>
				<td style="width:30%">
					<label for="societaAddebito" class="col-sm-12 control-label"><spring:message
						text="??fascicolo.label.posizioneSocietaAddebito??"
						code="fascicolo.label.posizioneSocietaAddebito" /></label>
				</td>
				<td style="width:70%">
					<c:if test="${ fascicoloDettaglioView.listaSocieta != null }">
						<form:select id="posizioneSocietaAddebito" path="posizioneSocietaAddebitoCode" disabled="true" cssClass="form-control">
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
				</td>
			</tr>
			
			<!-- NOME CONTROPARTE-->
			<tr>
				<td style="width:30%">
					<label for="nomeControparte" class="col-sm-12 control-label"><spring:message
						text="??fascicolo.label.nomeControparte??"
						code="fascicolo.label.nomeControparte" /></label>
				</td>
				<td style="width:70%">
					<table  style="margin-top: 20px;">
						<tbody id="boxControparte">
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
				</td>
			</tr>
			
			<!-- TERZO CHIAMATO IN CAUSA-->
			<tr>
				<td style="width:30%">
					<label for="terzoChiamatoInCausa" class="col-sm-12 control-label"><spring:message
						text="??fascicolo.label.terzoChiamatoInCausa??"
						code="fascicolo.label.terzoChiamatoInCausa" /></label>
				</td>
				<td style="width:70%">
					<table  style="margin-top: 20px;">
						<tbody id="boxTerzoChiamato">
							<c:if
								test="${ not empty fascicoloDettaglioView.terzoChiamatoInCausaAggiunti }">
								<c:forEach items="${fascicoloDettaglioView.terzoChiamatoInCausaAggiunti}"
									var="terzoChiamatoCausa" varStatus="indiceArray">
									<tr style="border:1px solid #e0e0e0">
										<td>${terzoChiamatoCausa.nomeTerzoChiamatoCausa}</td>
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
									<td><spring:message code="fascicolo.label.tabella.no.dati"></spring:message>
									</td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</td>
			</tr>
			
			
			<!-- GIUDIZIO--> 
			<tr>
				<td style="width:30%">
					<label for="giudizio" class="col-sm-12 control-label"><spring:message
						text="??fascicolo.label.giudizio??"
						code="fascicolo.label.giudizio" /></label>
				</td>
				<td style="width:70%">
					<table  style="margin-top: 20px;">
						<tbody id="boxGiudizio">
							<tr style="font-weight: bold;">
								<td data-column-id="id"><spring:message
										text="??fascicolo.label.giudizio??"
										code="fascicolo.label.giudizio" />&nbsp;</td>
								<td data-column-id="01"><spring:message
										text="??fascicolo.label.organoGiudicante??"
										code="fascicolo.label.organoGiudicante" />&nbsp;</td>
								<td data-column-id="03"><spring:message
										text="??fascicolo.label.foro??"
										code="fascicolo.label.foro" />&nbsp;</td>
								<td data-column-id="03"><spring:message
										text="??fascicolo.label.numeroRegistroCausa??"
										code="fascicolo.label.numeroRegistroCausa" />&nbsp;</td>
								<td data-column-id="07"><spring:message
										text="??fascicolo.label.note??"
										code="fascicolo.label.note" />&nbsp;</td>
								<td data-column-id="04">&nbsp;</td>	
								<td data-column-id="06">&nbsp;</td>	
							</tr>
							
							<c:if test="${ not empty fascicoloDettaglioView.giudiziAggiunti }">
								<c:forEach items="${fascicoloDettaglioView.giudiziAggiunti}"
									var="giudizioAggiunto" varStatus="indiceArray">
									<tr>
										<td>${giudizioAggiunto.vo.giudizio.descrizione}</td>
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

							<c:if test="${ empty fascicoloDettaglioView.giudiziAggiunti  }">
								<tr>
									<td><spring:message code="fascicolo.label.tabella.no.dati"></spring:message>
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
				</td>
			</tr>
		</c:if>
		
		
		<c:if test="${fascicoloDettaglioView.settoreGiuridicoCode eq 'TSTT_3' }">
			
			<!-- SOCIETA POSIZIONE-->
			<tr>
				<td style="width:30%">
					<label for="societaAddebito" class="col-sm-12 control-label"><spring:message
						text="??fascicolo.label.posizioneSocietaAddebito??"
						code="fascicolo.label.posizioneSocietaAddebito" /></label>
				</td>
				<td style="width:70%">
					<c:if test="${ fascicoloView.listaSocieta != null }">
						<form:select id="posizioneSocietaAddebito" path="posizioneSocietaAddebitoCode" disabled="true" cssClass="form-control">
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
				</td>
			</tr>
			
			<!-- AUTORITA EMANANTE-->
			<tr>
				<td style="width:30%">
					<label for="autoritaEmanante" class="col-sm-12 control-label"><spring:message
						text="??fascicolo.label.autoritaEmanante??"
						code="fascicolo.label.autoritaEmanante" /></label>
				</td>
				<td style="width:70%">
					<form:input path="autoritaEmanante" id="txtAutoritaEmanante" disabled="true" cssClass="form-control"/>
				</td>
			</tr>
			
			<!-- CONTROINTERESSATO-->
			<tr>
				<td style="width:30%">
					<label for="controinteressato" class="col-sm-12 control-label"><spring:message
						text="??fascicolo.label.controinteressato??"
						code="fascicolo.label.controinteressato" /></label>
				</td>
				<td style="width:70%">
					<form:input path="controinteressato" id="txtControinteressato" disabled="true" cssClass="form-control"/>
				</td>
			</tr>
			 
			<!-- RICORSO--> 
			<tr>
				<td style="width:30%">
					<label for="giudizio" class="col-sm-12 control-label"><spring:message
						text="??fascicolo.label.ricorso??"
						code="fascicolo.label.ricorso" /></label>
				</td>
				<td style="width:70%">
					<table  >
						<tbody id="boxRicorso">
							<tr style="font-weight: bold;">
								<td data-column-id="id"><spring:message
										text="??fascicolo.label.ricorso??"
										code="fascicolo.label.ricorso" /> &nbsp;</td>
								<td data-column-id="01"><spring:message
										text="??fascicolo.label.organoGiudicante??"
										code="fascicolo.label.organoGiudicante" /> &nbsp;</td>
								<td data-column-id="03"><spring:message
										text="??fascicolo.label.foro??"
										code="fascicolo.label.foro" /> &nbsp;</td>
								<td data-column-id="03"><spring:message
										text="??fascicolo.label.numeroRegistroCausa??"
										code="fascicolo.label.numeroRegistroCausa" /> &nbsp;</td>
								<td data-column-id="07"><spring:message
										text="??fascicolo.label.note??"
										code="fascicolo.label.note" /> &nbsp;</td>
								<td data-column-id="04"> &nbsp;</td>	
								<td data-column-id="06"> &nbsp;</td>	
							</tr>
							<c:if
								test="${ not empty fascicoloDettaglioView.ricorsiAggiunti }">
								<c:forEach items="${fascicoloDettaglioView.ricorsiAggiunti}"
									var="ricorsoAggiunto" varStatus="indiceArray">
									<tr>
										<td>${ricorsoAggiunto.vo.ricorso.descrizione}</td>
										<td>${ricorsoAggiunto.vo.organoGiudicante.nome}</td>
										<td>${ricorsoAggiunto.foro}</td>
										<td>${ricorsoAggiunto.numeroRegistroCausa}</td>
										<td>${ricorsoAggiunto.note}</td>
										<td> 
										</td>
										<td>	 
										</td>
									</tr>
								</c:forEach>
							</c:if>

							<c:if
								test="${ empty fascicoloDettaglioView.ricorsiAggiunti  }">
								<tr>
									<td><spring:message code="fascicolo.label.tabella.no.dati"></spring:message>
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
				</td>
			</tr>
		</c:if>
		
		<c:if test="${fascicoloDettaglioView.settoreGiuridicoCode eq 'TSTT_2' }">
			
			<!-- AUTORITA GIUDIZIALE-->
			<tr>
				<td style="width:30%">
					<label for="autoritaGiudiziale" class="col-sm-12 control-label"><spring:message
						text="??fascicolo.label.autoritaGiudiziale??"
						code="fascicolo.label.autoritaGiudiziale" /></label>
				</td>
				<td style="width:70%">
					<form:input path="autoritaGiudiziaria" id="txtAutoritaGiudiziale" disabled="true" cssClass="form-control"/>
				</td>
			</tr>
			
			<!-- NOME SOGGETTO INDAGATO-->
			<tr>
				<td style="width:30%">
					<label for="nomeSoggettoIndagato" class="col-sm-12 control-label"><spring:message
						text="??fascicolo.label.nomeSoggettoIndagato??"
						code="fascicolo.label.nomeSoggettoIndagato" /></label>
				</td>
				<td style="width:70%">
					<table >
						<tbody id="boxSoggettoIndagato">
							<c:if
								test="${ not empty fascicoloDettaglioView.soggettoIndagatoAggiunti  }">
								<c:forEach items="${fascicoloDettaglioView.soggettoIndagatoAggiunti}"
									var="soggettoTmp" varStatus="indiceArray">
									<tr>
										<td>(${soggettoTmp.tipoSoggettoIndagato })
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
									<td><spring:message code="fascicolo.label.tabella.no.dati"></spring:message>
									</td>
									<td></td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</td>
			</tr>
			
			<!-- NOME PERSONA OFFESA-->
			<tr>
				<td style="width:30%">
					<label for="nomePersonaOffesa" class="col-sm-12 control-label"><spring:message
						text="??fascicolo.label.nomePersonaOffesa??"
						code="fascicolo.label.nomePersonaOffesa" /></label>
				</td>
				<td style="width:70%">
					<table  >
						<tbody id="boxPersonaOffesa">
							<c:if
								test="${ not empty fascicoloDettaglioView.personaOffesaAggiunte }">
								<c:forEach items="${fascicoloDettaglioView.personaOffesaAggiunte}"
									var="soggettoTmp" varStatus="indiceArray">
									<tr>
										<td>(${soggettoTmp.tipoPersonaOffesa }) 
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
									<td><spring:message code="fascicolo.label.tabella.no.dati"></spring:message>
									</td>
									<td></td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</td>
			</tr>
			
			<!-- NOME PARTE CIVILE-->
			<tr>
				<td style="width:30%">
					<label for="nomeParteCivile" class="col-sm-12 control-label"><spring:message
						text="??fascicolo.label.nomeParteCivile??"
						code="fascicolo.label.nomeParteCivile" /></label>
				</td>
				<td style="width:70%">
					<table  >
						<tbody id="boxParteCivile">
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
									<td><spring:message code="fascicolo.label.tabella.no.dati"></spring:message></td>
									<td></td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</td>
			</tr>
			 
			<!-- NOME RESPONSABILE CIVILE-->
			<tr>
				<td style="width:30%">
					<label for="nomeResponsabileCivile" class="col-sm-12 control-label"><spring:message
						text="??fascicolo.label.nomeResponsabileCivile??"
						code="fascicolo.label.nomeResponsabileCivile" /></label>
				</td>
				<td style="width:70%">
					<table  >
						<tbody id="boxResponsabileCivile">
							<c:if test="${not empty fascicoloDettaglioView.responsabileCivileAggiunte }">
								<c:forEach items="${fascicoloDettaglioView.responsabileCivileAggiunte}"
									var="soggettoTmp" varStatus="indiceArray">
									<tr>
										<td>(${soggettoTmp.tipoResponsabileCivile }) 
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
	
							<c:if test="${ empty fascicoloDettaglioView.parteCivileAggiunte }">
								<tr>
									<td><spring:message code="fascicolo.label.tabella.no.dati"></spring:message>
									</td>
									<td></td>

								</tr>
							</c:if>
						</tbody>
					</table>
				</td>
			</tr>
			
			<!-- GIUDIZIO--> 
			<tr>
				<td style="width:30%">
					<label for="giudizio" class="col-sm-12 control-label"><spring:message
						text="??fascicolo.label.giudizio??"
						code="fascicolo.label.giudizio" /></label>
				</td>
				<td style="width:70%">
					<table  >
						<tbody id="boxGiudizio">
							<tr style="font-weight: bold;">
								<td data-column-id="id"><spring:message
										text="??fascicolo.label.giudizio??"
										code="fascicolo.label.giudizio" />&nbsp;</td>
								<td data-column-id="01"><spring:message
										text="??fascicolo.label.organoGiudicante??"
										code="fascicolo.label.organoGiudicante" />&nbsp;</td>
								<td data-column-id="03"><spring:message
										text="??fascicolo.label.foro??"
										code="fascicolo.label.foro" />&nbsp;</td>
								<td data-column-id="03"><spring:message
										text="??fascicolo.label.numeroRegistroCausa??"
										code="fascicolo.label.numeroRegistroCausa" />&nbsp;</td>
								<td data-column-id="07"><spring:message
										text="??fascicolo.label.note??"
										code="fascicolo.label.note" />&nbsp;</td>
								<td data-column-id="06">&nbsp;</td>
								<td data-column-id="04">&nbsp;</td>  
							</tr>
							<c:if 	test="${ not empty fascicoloDettaglioView.giudiziAggiunti }">
								<c:forEach items="${fascicoloDettaglioView.giudiziAggiunti}"
									var="giudizioAggiunto" varStatus="indiceArray">
									<tr>
										<td>${giudizioAggiunto.vo.giudizio.descrizione}</td>
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
									<td><spring:message code="fascicolo.label.tabella.no.dati"></spring:message>
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
				</td>
			</tr>
		</c:if>
		
		<c:if test="${fascicoloDettaglioView.settoreGiuridicoCode eq 'TSTT_6' }">
			
			<!-- NOME CONTROPARTE-->
			<tr>
				<td style="width:30%">
					<label for="nomeControparte" class="col-sm-12 control-label"><spring:message
						text="??fascicolo.label.nomeControparte??"
						code="fascicolo.label.nomeControparte" /></label>
				</td>
				<td style="width:70%">
					<table  style="margin-top: 20px;">
						<tbody id="boxControparte">
							<c:if test="${ not empty fascicoloDettaglioView.contropartiAggiunte  }">
								<c:forEach items="${fascicoloDettaglioView.contropartiAggiunte}"
									var="controparteTmp" varStatus="indiceArray">
									<tr>
										<td>(${controparteTmp.tipoControparte })
											${controparteTmp.nomeControparte }</td>
										<td></td>
									</tr>
								</c:forEach>
							</c:if>

							<c:if test="${ empty fascicoloDettaglioView.contropartiAggiunte }">
								<tr>
									<td><spring:message code="fascicolo.label.tabella.no.dati"></spring:message>
									</td>
									<td></td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</td>
			</tr>
			
		</c:if>

	</c:if>

	<!-- TIPO CONTENZIOSO-->
	<tr>
		<td style="width:30%">
			<label for="tipoContenzioso" class="col-sm-12 control-label"><spring:message
				text="??fascicolo.label.tipoContenzioso??"
				code="fascicolo.label.tipoContenzioso" /></label>
		</td>
		<td style="width:70%">
			<form:select id="tipoContenziosoCode" path="tipoContenziosoCode" disabled="true" cssClass="form-control">
				<form:option value="">
					<spring:message
						text="??fascicolo.label.selezionaTipoContenzioso??"
						code="fascicolo.label.selezionaTipoContenzioso" />
				</form:option>
				<c:if test="${ fascicoloDettaglioView.listaTipoContenzioso != null }">
					<c:forEach items="${fascicoloDettaglioView.listaTipoContenzioso}"
						var="oggetto">
						<form:option value="${ oggetto.vo.codGruppoLingua }">
							<c:out value="${oggetto.vo.nome}"></c:out>
						</form:option>
					</c:forEach>
				</c:if>
			</form:select>
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
			<spring:message var="lblLegaleCorrenteDesc" text="??fascicolo.label.legaleInternoDal??"
				code="fascicolo.label.legaleInternoDal" 
				arguments="${fascicoloDettaglioView.ownerDal}|" argumentSeparator="|"/>
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
			<form:hidden path="unitaLegale" disabled="true" cssClass="form-control" />
			<form:input path="unitaLegaleDesc" disabled="true" cssClass="form-control" />
		</td>
	</tr>
	
	<!-- VALORE DELLA CAUSA-->
	<tr>
		<td style="width:30%">
			<label for="valoreDellaCausa" class="col-sm-12 control-label"><spring:message
				text="??fascicolo.label.valoreDellaCausa??"
				code="fascicolo.label.valoreDellaCausa" /></label>
		</td>
		<td style="width:70%">
			<form:select id="valoreCausa" path="valoreCausaCode" disabled="true" cssClass="form-control">
				<form:option value="">
					<spring:message text="??fascicolo.label.selezionaValoreCausa??"
						code="fascicolo.label.selezionaValoreCausa" />
				</form:option>
				<c:if test="${ fascicoloDettaglioView.listaValoreCausa != null }">
					<c:forEach items="${fascicoloDettaglioView.listaValoreCausa}"
						var="oggetto">
						<form:option value="${ oggetto.vo.codGruppoLingua }">
							<c:out value="${oggetto.vo.nome}"></c:out>
						</form:option>
					</c:forEach>
				</c:if>
			</form:select>
		</td>
	</tr>
	
	<!-- VALORE -->
	<tr>
		<td style="width:30%">
			<label for="valore" class="col-sm-12 control-label"><spring:message
				text="??fascicolo.label.valore??" code="fascicolo.label.valore" /></label>
		</td>
		<td style="width:70%">
			<form:input path="valore" disabled="true" cssClass="form-control" id="txtValore"/>
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
			<form:input path="siglaCliente" disabled="true" cssClass="form-control"/>
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
			<input type="text" id="progettoNome" disabled="true" class="form-control"  value="${fascicoloDettaglioView.progettoNome }"/>
		</td>
	</tr>
	
	<!-- FASCICOLO PADRE -->
	<tr>
		<td style="width:30%">
			<label for="fascicoloPadreSelezionato"
				class="col-sm-12 control-label"><spring:message
					text="??fascicolo.label.fascicoloPadre??"
					code="fascicolo.label.fascicoloPadre" /></label>
		</td>
		<td style="width:70%">
			<input type="text" id="fascicoloPadreSelezionato" disabled="true" class="form-control" value="${fascicoloDettaglioView.fascicoloPadreNome }" name="fascicoloPadreNome"/>
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
			<table  style="margin-top: 60px;">
				<tbody id="boxFascicoliCorrelati" class="collapse in">
					<c:if test="${ not empty fascicoloDettaglioView.fascicoliCorrelatiAggiunti  }">
						<c:forEach items="${fascicoloDettaglioView.fascicoliCorrelatiAggiunti}"
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
							<td>
								<spring:message code="fascicolo.label.tabella.no.dati"></spring:message>
							</td>
							<td></td>
						</tr>		
					</c:if>
				</tbody>
			</table>
		</td>
	</tr>

	<c:if
		test="${ fascicoloDettaglioView.fascicoloId != null && fascicoloDettaglioView.fascicoloId > 0 }">
		<!-- N ARCHIVIO -->
		<tr>
			<td style="width:30%">
				<label for="numeroArchivio" class="col-sm-12 control-label"><spring:message
					text="??fascicolo.label.numeroArchivio??"
					code="fascicolo.label.numeroArchivio" /></label>
			</td>
			<td style="width:70%">
				<form:input path="numeroArchivio" disabled="true" cssClass="form-control"/>
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

	<!-- RILEVANTE -->
	<tr>
		<td style="width:30%">
			<label for="rilevante" class="col-sm-12 control-label"><spring:message
				text="??fascicolo.label.rilevante??"
				code="fascicolo.label.rilevante" /></label>
		</td>
		<td style="width:70%">
			<form:checkbox path="rilevante" disabled="true" cssClass="checkbox"/>
		</td>
	</tr>