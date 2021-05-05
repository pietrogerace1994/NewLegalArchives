<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>


<c:set var="isp" value="0"></c:set>
<c:if test="${ proformaDettaglioView.penale }"> 
	<c:set var="isp" value="1"></c:set>
</c:if>

<!-- SCHEDA VALUTAZIONE-->
<div class="list-group-item media">
	<div class="media-body">
		<div class="form-group">
			<label for="schedaValutazione.praticaControparte"
				class="col-sm-2 control-label"><spring:message
					text="??proforma.label.praticacontroparte??"
					code="proforma.label.praticacontroparte" /></label>
			<div class="col-sm-7">
				<form:input path="schedaValutazione.praticaControparte"
					cssClass='form-control' disabled="true"/>
			</div>
		</div>
		<div class="form-group">
			<label for="schedaValutazione.autorita"
				class="col-sm-2 control-label"><spring:message
					text="??proforma.label.autorita??" code="proforma.label.autorita" /></label>
			<div class="col-sm-7">
				<form:input path="schedaValutazione.autorita"
					cssClass='form-control' disabled="true" />

			</div>
		</div>
		<div class="form-group">
			<label for="schedaValutazione.valorecontroversia"
				class="col-sm-2 control-label"><spring:message
					text="??proforma.label.valorecontroversia??"
					code="proforma.label.valorecontroversia" /></label>
			<div class="col-sm-7">
				<form:input path="schedaValutazione.valorecontroversia"
					cssClass='form-control'  disabled="true" />

			</div>
		</div>
		<div class="form-group">
			<label for="schedaValutazione.valoreincarico"
				class="col-sm-2 control-label"><spring:message
					text="??proforma.label.valoreincarico??"
					code="proforma.label.valoreincarico" /></label>
			<div class="col-sm-7">
				<form:input path="schedaValutazione.valoreincarico"
					cssClass='form-control' disabled="true" />

			</div>
		</div>
		<div class="form-group">
			<label for="schedaValutazione.parcellan"
				class="col-sm-2 control-label"><spring:message
					text="??proforma.label.parcellan??"
					code="proforma.label.parcellan" /></label>
			<div class="col-sm-7">
				<form:input path="schedaValutazione.parcellan"
					cssClass='form-control' disabled="true" />

			</div>
		</div>
		<div class="form-group">
			<label for="schedaValutazione.dataemissione"
				class="col-sm-2 control-label"><spring:message
					text="??proforma.label.dataemissione??" code="proforma.label.dataemissione" /></label>
			<div class="col-sm-7">
				<form:input path="schedaValutazione.dataemissione"
					cssClass='form-control date-picker' disabled="true" />

			</div>
		</div>
		<div class="form-group">
			<label for="schedaValutazione.avvocatostudio"
				class="col-sm-2 control-label"><spring:message
					text="??proforma.label.avvocatostudio??"
					code="proforma.label.avvocatostudio" /></label>
			<div class="col-sm-7">
				<form:input path="schedaValutazione.avvocatostudio"
					cssClass='form-control' disabled="true" />

			</div>
		</div>
		<div class="form-group">
			<label for="schedaValutazione.verificaparcella"
				class="col-sm-7 control-label"><spring:message
					text="??proforma.label.verificaparcella??"
					code="proforma.label.verificaparcella" /></label>
			<div class="col-sm-3">
				    <label class="radio-inline"><form:radiobutton path="schedaValutazione.optparcella" value="1" disabled="true"/>S&iacute;</label> 
					<label class="radio-inline"><form:radiobutton path="schedaValutazione.optparcella" value="0" disabled="true"/>No</label>
			</div>
		</div>
		<label for="schedaValutazione.verificaparcellacongrua"
				class="col-sm-7 control-label"><spring:message
					text="??proforma.label.verificaparcellacongrua??"
					code="proforma.label.verificaparcellacongrua" /></label>
		<div class="form-group">
			<label for="schedaValutazione.laletteraincarico"
				class="col-sm-7 control-label"><spring:message
					text="??proforma.label.laletteraincarico??"
					code="proforma.label.laletteraincarico" /></label>
			<div class="col-sm-3">
				    <label class="radio-inline"><form:radiobutton path="schedaValutazione.optlettera" value="1" disabled="true"/>S&iacute;</label> 
					<label class="radio-inline"><form:radiobutton path="schedaValutazione.optlettera" value="0"disabled="true"/>No</label>
			</div>
		</div>
		<div class="form-group">
			<label for="schedaValutazione.parametriforensi"
				class="col-sm-7 control-label"><spring:message
					text="??proforma.label.parametriforensi??"
					code="proforma.label.parametriforensi" /></label>
			<div class="col-sm-3">
				    <label class="radio-inline"><form:radiobutton path="schedaValutazione.optparametri" value="1"disabled="true"/>S&iacute;</label> 
					<label class="radio-inline"><form:radiobutton path="schedaValutazione.optparametri" value="0"disabled="true"/>No</label>
			</div>
		</div>
		<div class="form-group">
			<label for="schedaValutazione.legge"
				class="col-sm-7 control-label"><spring:message
					text="??proforma.label.legge??"
					code="proforma.label.legge" /></label>
			<div class="col-sm-3">
				    <label class="radio-inline"><form:radiobutton path="schedaValutazione.optlegge" value="1" disabled="true"/>S&iacute;</label> 
					<label class="radio-inline"><form:radiobutton path="schedaValutazione.optlegge" value="0" disabled="true"/>No</label>
			</div>
		</div>
		<div class="form-group">
			<h3 class="text-center">Analitico Prestazioni (Onorari)</h3>
			</br>
			<table class="table table-bordered table-condensed">
				<thead>
					<tr>
						<th></th>
						<th>Descrizione</th>
						<th>Valore</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>1</td>
						<td>Diritti</td>
						<td>${proformaDettaglioView.diritti }</td>
					</tr>
					<tr>
						<td>2</td>
						<td>Onorari</td>
						<td>${proformaDettaglioView.onorari}</td>
					</tr>
					<tr>
						<td>3</td>
						<td>Spese imponibili</td>
						<td>${proformaDettaglioView.speseImponibili}</td>
					</tr>
					<tr>
						<td>4</td>
						<td>CPA</td>
						<td>${proformaDettaglioView.cpa }</td>
					</tr>
					<tr>
						<td>5</td>
						<td>Totale imponibile</td>
						<td>${proformaDettaglioView.totaleImponibile}</td>
					</tr>
					<tr>
						<td>6</td>
						<td>Spese non imponibili</td>
						<td>${proformaDettaglioView.speseNonImponibili}</td>
					</tr>
					<tr>
						<td>7</td>
						<td>Totale (IVA esclusa)</td>
						<td>${proformaDettaglioView.totale}</td>
					</tr>
				</tbody>
			</table>
		</div>

		<div class="list-group-item media">
			<div class="media-body">
				<div class="form-group">
					<label class="col-sm-2 control-label" for="schedaValutazione.note"><spring:message
							text="??proforma.label.note??" code="proforma.label.note" /></label>
					<div class="col-md-10">
						<form:input path="schedaValutazione.note" class="form-control" disabled="true" />
					</div>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label for="schedaValutazione.data"
				class="col-sm-3 control-label"><spring:message
					text="??proforma.label.datascheda??" code="proforma.label.datascheda" /></label>
			<div class="col-sm-7">
				<form:input path="schedaValutazione.dataScheda"
					cssClass='form-control' disabled="true" />

			</div>
		</div>
		<div class="form-group">
			<label for="schedaValutazione.unitalegaleowner"
				class="col-sm-3 control-label"><spring:message
					text="??proforma.label.unitalegaleowner??" code="proforma.label.unitalegaleowner" /></label>
			<div class="col-sm-7">
				<form:input path="schedaValutazione.unitaLegaleOwner"
					cssClass='form-control' disabled="true" />

			</div>
		</div>
		
		<div id="schedaValutazioneFirmataDiv" class="form-group">
			<label for="schedaValutazioneFirmataDesc" class="col-sm-3 control-label"><spring:message
			text="??proforma.label.schedaValutazioneFirmata??" code="proforma.label.schedaValutazioneFirmata" /></label>
			
			<div class="col-sm-6">
				
				<c:if test="${ not empty proformaDettaglioView.schedaValutazioneFirmataDoc && not empty proformaDettaglioView.schedaValutazioneFirmataDoc.uuid}">
					<script>
					
					window.addEventListener("load", function(){  
						$( "#SchedaValutazioneFirmataDownload" ).html('<a id="schedaValutazioneFirmata" href="<%=request.getContextPath() %>/download?uuid=${proformaDettaglioView.schedaValutazioneFirmataDoc.uuid}&isp=${isp}" style="display: block;float: right;position: relative !important;margin-right: 30px;vertical-align: middle;margin-top: 7px;margin-left: 20px;font-size: 25px;" aria-expanded="true"> <i class="fa fa-pencil-square " title="Scheda di Valutazione Firmata presente"></i></a>');
					}); 
					</script>
					<input readonly class="form-control" value="${ proformaDettaglioView.schedaValutazioneFirmataDoc.nomeFile }"/>
				</c:if>
				
				<c:if test="${empty proformaDettaglioView.schedaValutazioneFirmataDoc }">
					<script>

					window.addEventListener("load", function(){  
						$( "#SchedaValutazioneFirmataDownload" ).html('');
					}); 
					</script>
					<input readonly class="form-control" value=""/>
				</c:if>
				
			</div>
			
			<c:if
				test="${ not empty proformaDettaglioView.schedaValutazioneFirmataDoc && not empty proformaDettaglioView.schedaValutazioneFirmataDoc.uuid }">
		    <div class="col-sm-1">
				<a
					href="<%=request.getContextPath() %>/download?uuid=${proformaDettaglioView.schedaValutazioneFirmataDoc.uuid}&isp=${isp}"
					class="  bg   waves-effect waves-circle waves-float btn-circle-mini"
					style="float: left; position: relative !important;" target="_BLANK">
					<i class="zmdi zmdi-download icon-mini"></i>
				</a>
			
			</div>	
			</c:if>		
		</div>
		
	</div>
</div>
