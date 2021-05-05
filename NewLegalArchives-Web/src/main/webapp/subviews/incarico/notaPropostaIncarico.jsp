<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>

<!--NOME INCARICO-->
<!-- 
<div class="list-group lg-alt">

	<div class="list-group-item media">
		<div class="media-body">
			<div class="form-group">
				<label for="fascicolo" class="col-sm-2 control-label"><spring:message
						text="??incarico.label.nome??" code="incarico.label.nome" /></label>
				<div class="col-sm-10">
					<input class="form-control"
						readonly value="${incaricoView.nomeIncarico}"/>
				</div>
			</div>
		</div>
	</div>
</div>


<!--NOME PROFESSIONISTA-->

<div class="list-group lg-alt">
	<div class="list-group-item media">
		<div class="media-body">
			<div class="form-group">
				<label for="" class="col-sm-2 control-label"></label>
				<div class="col-sm-10">
				Nota per il general counsel
					<!--<br/> AVV.
					<c:out value="${incaricoView.responsabileTop}" ></c:out><br>-->
				</div>
				
			</div>
		</div>
	</div>
</div>


<!--COGNOME PROFESSIONISTA-->
<!--
<div class="list-group lg-alt">
	<div class="list-group-item media">
		<div class="media-body">
			<div class="form-group">
				<label for="fascicolo" class="col-sm-2 control-label"><spring:message
						text="??incarico.label.cognomeProfessionista??" code="incarico.label.cognomeProfessionista" /></label>
				<div class="col-sm-10">
					<input class="form-control"
						readonly value="${incaricoView.professionistaSelezionato.vo.cognome}"/>
				</div>
			</div>
		</div>
	</div>
</div>
-->

<!--DENOMINAZIONE STUDIO PROFESSIONISTA-->
	<!--
<div class="list-group lg-alt">
	<div class="list-group-item media">
		<div class="media-body">
			<div class="form-group">
				<label for="fascicolo" class="col-sm-2 control-label"><spring:message
						text="??incarico.label.denominazioneStudioProfessionista??" code="incarico.label.denominazioneStudioProfessionista" /></label>
				<div class="col-sm-10">
					<input class="form-control"
						readonly value="${incaricoView.professionistaSelezionato.vo.studioLegale.denominazione}"/>
				</div>
			</div>
		</div>
	</div>
</div>

 -->
 
<form:hidden path="notaIncaricoId"/> 
<!-- PRATICA-->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="pratica" class="col-sm-2 control-label"><spring:message
					text="??incarico.label.pratica??" code="incarico.label.pratica" /></label>
			<div class="col-sm-10">
				Fascicolo: <c:out value="${incaricoView.nomeFascicolo}"></c:out> 
					<form:input path="pratica" cssClass="form-control" style="width: 115px;display: initial;" />
					<c:out value="${incaricoView.societaParteProcedimento}"></c:out> 
			</div>
		</div>
	</div>
</div>

<!-- VALORE INCARICO -->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="valoreIncarico" class="col-sm-2 control-label"><spring:message
					text="??incarico.label.valoreIncarico??"
					code="incarico.label.valoreIncarico" /></label>
			<div class="col-sm-10">				
				<input id="valoreIncarico" name="valoreIncarico" readonly style='display: initial;' class="form-control" value="${incaricoView.valoreIncarico}" />
				<%--  <form:input path="valoreIncarico" cssClass="form-control" /> --%>

			</div>
		</div>
	</div>
</div>


<!-- OGGETTO-->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="oggetto" class="col-sm-2 control-label"><spring:message
					text="??incarico.label.oggetto??" code="incarico.label.oggetto" /></label>
			<div class="col-sm-10">
				<form:input path="oggetto" cssClass="form-control" style='display: initial;' />
			</div>
		</div>
	</div>
</div>


<!-- DESCRIZIONE-->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="descrizione" class="col-sm-2 control-label"><spring:message
					text="??incarico.label.descrizione??"
					code="incarico.label.descrizione" /></label>
			<div class="col-sm-10">
				<form:textarea path="descrizione" cols="70" rows="8"
					cssClass="form-control" />
			</div>
		</div>
	</div>
</div>


<!-- PROPOSTA-->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="proposta" class="col-sm-2 control-label"><spring:message
					text="??incarico.label.proposta??" code="incarico.label.proposta" />
			</label>	
		
			<div class="col-md-10" >
				<label for="data"  class="col-sm-5 control-label" style="text-align:left;right:15">Si propone di affidare l'incarico a</br>
						<c:if test="${incaricoView.fascicoloRiferimento.vo.tipologiaFascicolo.codGruppoLingua eq 'TFSC_4'}">
					Dott./Dott.ssa	
						</c:if>
						<c:if test="${incaricoView.fascicoloRiferimento.vo.tipologiaFascicolo.codGruppoLingua ne 'TFSC_4'}">
					Avv.	
						</c:if>
					<c:out value="${incaricoView.professionistaSelezionato.vo.nome}"></c:out> <c:out value="${incaricoView.professionistaSelezionato.vo.cognome}"></c:out><br>
					<c:out value="${incaricoView.professionistaSelezionato.vo.studioLegale.denominazione}"></c:out> 
					<c:out value="${incaricoView.professionistaSelezionato.vo.studioLegale.indirizzo}" ></c:out> 
					<c:out value="${incaricoView.professionistaSelezionato.vo.studioLegale.cap}" ></c:out>
					<c:out value="${incaricoView.professionistaSelezionato.vo.studioLegale.citta}" ></c:out><br>
					<form:input path="nomeForo" cssClass="form-control" style="width: 100px; display: initial;" placeholder="Nome foro" readonly="false"  /> 
				</label>
			</div>
		
			<div class="col-md-10" style="left:133px;top:15px">
				<form:textarea path="proposta" cols="70" rows="8" readonly="false" 
					cssClass="form-control" />
			</div>
		</div>
	</div>
</div>

<div class="list-group-item media">
		<div class="media-body">
			<div class="form-group">	
				<label for="data" class="col-sm-2 control-label"></label>
					<div class="col-md-10">
						<label for="data" >
							Il professionista &egrave; presente nell'elenco Avvocati di Snam S.p.A. e nei suoi confronti &egrave; stata ottenuta la prevista verifica di nulla osta in materia anticorruzione. Le verifiche su Parti Correlate e Liste di Riferimento sono risultate negative. Per quanto concerne la valutazione di idoneit&agrave;   della quotazione proposta, si segnala che: 
						</label>
					</div>
			</div>
		</div>
</div>

<!--8bis-->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="" class="col-sm-2 control-label"></label>
			<div class="col-sm-10">
				<form:textarea path="infoCompensoNotaProp" cols="70" rows="8" readonly="false" cssClass="form-control" id="infoCompensoNotaProp" />
			</div>
		</div>
	</div>
</div>

<!-- INFO CORRESPONSIONE COMPENSI 8ter -->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="" class="col-sm-2 control-label"></label>
			<div class="col-sm-10">
				<form:input path="infoCorresponsioneCompenso" cssClass="form-control" readonly="false" />
			</div>
		</div>
	</div>
</div>

<!-- INFO CORRESPONSIONE COMPENSI 8quater -->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="" class="col-sm-2 control-label"></label>
			<div class="col-sm-10">
				<form:radiobutton path="sceltaInfo" value="0"  onclick="removeQuadro1();" id="quadroC2" label="Si precisa che, con riferimento agli allegati all'handbook, non sono state apportate modifiche" disabled="false"/></br>
				<form:radiobutton path="sceltaInfo" value="1" onclick="addQuadro1();" id="quadroC1"  label="Si precisa che, con riferimento agli allegati all'handbook, sono state apportate modifiche che sono:" disabled="false" />
				<div id="quadro1" style="display: none;">
					<form:input path='infoHandBook' cssClass='form-control' style='display: initial;' readonly="false" />
				</div>
			</div>
		</div>
	</div>
</div>

<!-- ALLEGATI-->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
		<label for="proposta" class="col-sm-2 control-label"><spring:message
					text="??incarico.label.allegato??" 
					code="incarico.label.allegato" />
		</label>	
				<div class="col-sm-10">
				<input id="allegato" readonly class="form-control" value="${incaricoView.allegato}" />
				<form:hidden path="allegato" cssClass="form-control" />
			</div>
		</div>
	</div>
</div>


<div class="modal fade" id="panelAggiungiNotaProposta" tabindex="-1"
	role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">
					<spring:message text="??incarico.label.aggiungiNotaProposta??"
						code="incarico.label.aggiungiNotaProposta" />
				</h4>
			</div>
			<div class="modal-body">
				<form id="formAggiungiNotaProposta" method="post"
					enctype="application/x-www-form-urlencoded" class="form-horizontal">
					<engsecurity:token regenerate="false"/>
 					<div class="form-group">
						<div class="col-md-8">
							<input type="file" name="fileNotaProposta" id="fileNotaProposta" />
						</div>
					</div>
					<!-- Button -->
					<div class="form-group">
						<div class="col-md-8">
							<button id="btnAggiungiNotaProposta" name="btnAggiungiNotaProposta"
								data-dismiss="modal" type="button" onclick="aggiungiNotaProposta(${incaricoView.incaricoId})"
								class="btn btn-primary">
								<spring:message text="??incarico.label.ok??"
									code="incarico.label.ok" />
							</button>
							<button name="singlebutton" type="button" data-dismiss="modal"
								class="btn btn-warning">
								<spring:message text="??incarico.label.chiudi??"
									code="incarico.label.chiudi" />
							</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

<!--LUOGO/DATA -->
<div class="list-group-item media">
		<div class="media-body">
			<div class="form-group">		
					<label for="data" class="col-sm-2 control-label"><spring:message
						text="??incarico.label.luogoData??" 
						code="incarico.label.luogoData" />
					</label>
				<div class="col-md-10">
					<label for="data"  class="col-sm-3 control-label">
						San Donato Milanese,
					</label>
					<label for="data"  class="col-sm-0 control-label">
						<c:out value="${incaricoView.dataNotaProposta}"></c:out>
					</label>
				</div>
			</div>
		</div>
</div>


<!-- PROPONENTE-->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="proponente" class="col-sm-2 control-label"><spring:message
					text="??incarico.label.proponente??"
					code="incarico.label.proponente" /></label>
			<div class="col-sm-10">
				<input id="proponenteDesc" readonly class="form-control"
					value="${incaricoView.proponente}" />
				<form:hidden path="proponente" cssClass="form-control" />
			</div>
		</div>
	</div>
</div>

<!-- PROCURATORE 
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="procuratore" class="col-sm-2 control-label"><spring:message
					text="??incarico.label.procuratore??"
					code="incarico.label.procuratore" /></label>
			<div class="col-sm-10">
				<form:select id="procuratoreId" path="procuratoreId"
					cssClass="form-control">
					<form:option value="">
						<spring:message text="??incarico.label.selezionaProcuratore??"
							code="incarico.label.selezionaProcuratore" />
					</form:option>
					<c:if test="${ incaricoView.listaProcuratore != null }">
						<c:forEach items="${incaricoView.listaProcuratore}" var="oggetto">
							<form:option value="${ oggetto.vo.id }">
								<c:out value="${oggetto.vo.nominativo}"></c:out>
							</form:option>
						</c:forEach>
					</c:if>

				</form:select>
			</div>
		</div>
	</div>
</div>

<div class="list-group lg-alt">
	DATA NOTA PROPOSTA
	<div class="list-group-item media">
		<div class="media-body media-body-datetimepiker">
			<div class="form-group"> 
				<label class="col-md-2 control-label" for="dataNotaProposta"><spring:message
						text="??incarico.label.dataNotaProposta??"
						code="incarico.label.dataNotaProposta" /></label>
				<div class="col-md-10">
					<form:input id="txtDataNotaProposta" path="dataNotaProposta"
						cssClass="form-control date-picker" />
				</div> 
			</div>
		</div>
	</div>-->
	
<!--  responsabili
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="responsabili" class="col-sm-2 control-label"><spring:message
					text="??incarico.label.responsabili??"
					code="incarico.label.responsabili" /></label>
			<div class="col-sm-10">
				<input id="responsabili" readonly class="form-control"
					value="${incaricoView.responsabili}" />
				<form:hidden path="responsabili" cssClass="form-control" />
			</div>
		</div>
	</div>
</div>-->

<!-- RESPONSABILI-->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="responsabili" class="col-sm-2 control-label"><spring:message
					text="??incarico.label.responsabili??"
					code="incarico.label.responsabili" />
			</label>
			<div class="col-sm-10">
			<c:if test="${ incaricoView.responsabili != null }">
				<input id="responsabili" readonly class="form-control" value="${incaricoView.responsabili}" /> 
				<form:hidden path="responsabili" cssClass="form-control" />
			</c:if>
			</div>
		</div>
	</div>
</div>

<!-- APPROVATORI-->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="approvatori" class="col-sm-2 control-label"><spring:message
					text="??incarico.label.approvatori??"
					code="incarico.label.approvatori" /></label>
			<div class="col-sm-10">
			<c:if test="${ incaricoView.approvatore != null }">
				<input id="approvatore" readonly class="form-control" value="${incaricoView.approvatore}" />
				<form:hidden path="approvatore" cssClass="form-control" />
			</c:if>
			</div>
		</div>
	</div>
</div>

