<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>



<!--  RICERCA FASCICOLO-->


						<div class="row">
						<form action="<%=request.getContextPath()%>/reporting/export-fascicolo.action" method="post" >
						<engsecurity:token regenerate="false"/> 
						<fieldset>

						
							<div class="form-group">
							<label class="col-md-12 control-label" for="fascicoloTipologiaFascicolo"><spring:message text="??reporting.fascicolo.tipologiaFascicolo??" code="reporting.fascicolo.tipologiaFascicolo" />  </label>
							<div class="col-md-12">
							<select id="fascicoloTipologiaFascicolo" name="fascicoloTipologiaFascicolo" class="form-control" onchange="checkSettoreGiudizio(this)">
							<option value="tutti">Tutti</option>
							<c:if test="${ ltsTipologiaFascicolo != null }">
							<c:forEach items="${ltsTipologiaFascicolo}" var="oggetto">
							<option value="${ oggetto.codGruppoLingua }"><c:out value="${oggetto.descrizione}"></c:out></option>
							</c:forEach>
							</c:if>
							</select>
							</div>
							</div>
						 
							<div class="form-group">
							<label class="col-md-12 control-label" for="fascicoloSettoreGiuridico"><spring:message text="??reporting.fascicolo.settoreGiuridico??" code="reporting.fascicolo.settoreGiuridico" />  </label>
							<div class="col-md-12">
							<select id="fascicoloSettoreGiuridico" name="fascicoloSettoreGiuridico" class="form-control" disabled="disabled">
							<option value="tutti">Tutti</option>
							<c:if test="${ ltsSettoreGiuridico != null }">
							<c:forEach items="${ltsSettoreGiuridico}" var="oggetto">
							<option value="${ oggetto.codGruppoLingua }"><c:out value="${oggetto.nome}"></c:out></option>
							</c:forEach>
							</c:if>
							</select>
							</div>
							</div>
							<div style="padding-bottom:15px" class="col-md-12"> 
							<div class="form-group" id="box-societa-fascicolo">
							<label class="col-md-11 control-label" for="fascicoloSocieta"><spring:message text="??reporting.fascicolo.societa??" code="reporting.fascicolo.societa" /> </label>
							<div class="col-md-11">
							<select id="fascicoloSocieta" name="fascicoloSocieta" class="form-control">
							<option></option>
							<c:if test="${ ltsSocieta != null }">
							<c:forEach items="${ltsSocieta}" var="oggetto">
							<option value="${ oggetto.id }" <c:if test="${ oggetto.ragioneSociale eq 'Snam S.p.A.' }"> selected</c:if> ><c:out value="${oggetto.ragioneSociale}"></c:out></option>
							</c:forEach>
							</c:if>
							</select>
							</div>
							
							<div class="col-md-1" style="padding-top: 18px;">
							<button type="button" onclick="addSocietaFascicolo()" class="btn palette-Green-300 bg btn-float waves-effect waves-circle waves-float">
									<span class="glyphicon glyphicon-plus"></span>
       						</button>
						  	</div>
							
								</div>	
							</div>		
						<!-- Select Basic -->
						<div class="form-group">
							<label class="col-md-12 control-label" for="fascicoloStatoFascicolo"><spring:message text="??reporting.fascicolo.stato??" code="reporting.fascicolo.stato" /></label>
							<div class="col-md-12">
							<select id="fascicoloStatoFascicolo" name=fascicoloStatoFascicolo class="form-control">
							<option value="tutti">TUTTI</option>
							<c:if test="${ ltsStatoFascicolo != null }">
							<c:forEach items="${ltsStatoFascicolo}" var="oggetto">
								<option value="${ oggetto.codGruppoLingua }">
									<c:out value="${oggetto.descrizione}"></c:out>
								</option>
							</c:forEach>
							</c:if>
							</select>
							</div>
						</div>
						
						
						
						 <!-- End Select Basic -->
						<div class="form-group">
							<label class="col-md-12 control-label" for="fascicolo_data_crazuione_da"><spring:message text="??reporting.fascicolo.dataCreazioneDa??" code="reporting.fascicolo.dataCreazioneDa" /> </label>
							<div class="col-md-12">
								 <input id="fascicolo_data_creazione_da" name="fascicolo_data_creazione_da"
									type="text" placeholder="" class="typeahead form-control input-md date-picker"> 
									<span class="help-block"></span>
							</div>
						</div>
						 
						 <div class="form-group">
							<label class="col-md-12 control-label" for="fascicolo_data_crazuione_a"><spring:message text="??reporting.fascicolo.dataCreazioneA??" code="reporting.fascicolo.dataCreazioneA" /> </label>
							<div class="col-md-12">
								 <input id="fascicolo_data_creazione_a" name="fascicolo_data_creazione_a"
									type="text" placeholder="" class="typeahead form-control input-md date-picker"> 
									<span class="help-block"></span>
							</div>
						</div>
						 <div style="padding-bottom:15px" class="col-md-12"> 
						 <div class="form-group" id="box-destinatario-fascicolo">
							<label class="col-md-11 control-label" for="fascicoloOwner"><spring:message text="??reporting.fascicolo.legaleInternoOwner??" code="reporting.fascicolo.legaleInternoOwner" /> </label>
							<div class="col-md-11">
							<select id="fascicoloOwner" name="fascicoloOwner" class="form-control">
							<option></option>
							<c:if test="${ ltsOwner != null }">
							<c:forEach items="${ltsOwner}" var="oggetto">
								<option value="${ oggetto.useridUtil }">
									<c:out value="${oggetto.nominativoUtil}"></c:out>
								</option>
							</c:forEach>
							</c:if>
							</select>
							</div>
							
							<div class="col-md-1" style="padding-top: 18px;">
							<button type="button" onclick="addDestinatarioFascicolo()" class="btn palette-Green-300 bg btn-float waves-effect waves-circle waves-float">
									<span class="glyphicon glyphicon-plus"></span>
       						</button>
						  	</div>	
						</div>	
						</div>
						
						<div class="form-group">
							<label class="col-md-12 control-label" for="tipoContenzioso"><spring:message text="??reporting.fascicolo.tipoContenzioso??" code="reporting.fascicolo.tipoContenzioso" /> </label>
							<div class="col-md-12">
							<select id="tipoContenzioso" name="tipoContenzioso" class="form-control" disabled="disabled" >
							<option></option>
							<c:if test="${ ltsTipoContenzioso != null }">
							<c:forEach items="${ltsTipoContenzioso}" var="oggetto">
							<option value="${ oggetto.codGruppoLingua }"><c:out value="${oggetto.nome}"></c:out></option>
							</c:forEach>
							</c:if>
							</select>
							</div>
						</div>
						
							</fieldset> 
							<div class="modal-footer">
								<button style="float:left" type="button" class="btn btn-primary waves-effect" onclick="pulisciCampi(this)"><spring:message text="??reporting.fascicolo.btn.clear??" code="reporting.fascicolo.btn.clear" /></button>
								<button type="submit" class="btn btn-primary" ><spring:message text="??reporting.fascicolo.btn.applicaFiltri??" code="reporting.fascicolo.btn.applicaFiltri" /> </button>
								 
							</div>
						 </form>
					 
							</div>
							
							
	<!-- -- -->			
			 
	<script>
	
	//SOCIETA
	
	function addSocietaFascicolo(){
	var esixst=0;
 
	
	var max = findMaxSocFascicolo();
	max = max +1;
	var societaText="",societaId="";
	societaId=$('#fascicoloSocieta').val();
	societaText=$("#fascicoloSocieta option:selected").text();
	societaText=$.trim(societaText);
	societaId=$.trim(societaId);
		if(societaId=="")
		esixst=1;
	
	$("input[name='fascicoloSocieta']").each(function(e){
	if($(this).val()==societaId)
	esixst=1;
	})
	
	$('#fascicoloSocieta').val("");
	if(!esixst)
	$('#box-societa-fascicolo').append('<div class="col-md-12 box-dinamik" style="margin-top:25px;" id="societaFascicolo_'+ max +'">'+
			'<div class="col-md-9">'+
			'<input type="hidden" name="fascicoloSocieta" value="'+ societaId +'" class="form-control">'+
			'<input value="'+ societaText +'" class="form-control" type="text" readonly>'+
		'</div><div class="col-md-1" style="padding-top: 18px;">'+
		'<button type="button" onclick="removeSocietaFascicolo(this.id)" id="'+ max +'" class="btn palette-Green-300 bg btn-float waves-effect waves-circle waves-float">'+
			'<span class="glyphicon glyphicon-minus"></span>'+
		'</button></div></div>' );
		
		
		
}

function removeSocietaFascicolo(idCliccato){
	$('#societaFascicolo_'+ idCliccato).remove();
}
	
function findMaxSocFascicolo(){
	
	var textemails = document.querySelectorAll("input[name^='fascicoloSocieta'");
	if(textemails.length <= 1)
		return 0;
	 else
	return textemails.length;
	 
}	
	
	
	//DESTINATARIO
	
	function addDestinatarioFascicolo(){
	var esixst=0;
 
	
	var max = findMaxDestFascicolo();
	max = max +1;
	var destinatarioText="",destinatarioId="";
	destinatarioId=$('#fascicoloOwner').val();
	destinatarioText=$("#fascicoloOwner option:selected").text();
	destinatarioText=$.trim(destinatarioText);
	destinatarioId=$.trim(destinatarioId);
	if(destinatarioId=="")
	  esixst=1;
	
	$("input[name='fascicoloOwner']").each(function(e){
	if($(this).val()==destinatarioId)
	esixst=1;
	})
	
	$('#fascicoloOwner').val("");
	if(!esixst)
	$('#box-destinatario-fascicolo').append('<div class="col-md-12 box-dinamik" style="margin-top:25px;" id="destinatarioFascicolo_'+ max +'">'+
			'<div class="col-md-9">'+
			'<input type="hidden" name="fascicoloOwner" value="'+ destinatarioId +'" class="form-control">'+
			'<input value="'+ destinatarioText +'" class="form-control" type="text" readonly>'+
		'</div><div class="col-md-1" style="padding-top: 18px;">'+
		'<button type="button" onclick="removeDestinatarioFascicolo(this.id)" id="'+ max +'" class="btn palette-Green-300 bg btn-float waves-effect waves-circle waves-float">'+
			'<span class="glyphicon glyphicon-minus"></span>'+
		'</button></div></div>' );
		
		
		
}

function removeDestinatarioFascicolo(idCliccato){
	$('#destinatarioFascicolo_'+ idCliccato).remove();
}
	
function findMaxDestFascicolo(){
	
	var textemails = document.querySelectorAll("input[name^='fascicoloOwner'");
	if(textemails.length <= 1)
		return 0;
	 else
	return textemails.length;
	 
}	
	
	</script>	
</form>					

<!-- FINE  RICERCA FASCICOLO-->	
