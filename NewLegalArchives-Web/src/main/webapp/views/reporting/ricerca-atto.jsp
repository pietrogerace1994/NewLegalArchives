<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>



<!-- RICERCA ATTO-->
		
										
						<div class="row">
						<form action="<%=request.getContextPath()%>/reporting/export-atto.action" method="post" >
						<engsecurity:token regenerate="false"/>
						<fieldset>

						<div style="padding-bottom:10px" class="col-md-12">
							<div class="form-group" id="box-societa">
							<label class="col-md-12 control-label" for=attoSocieta><spring:message text="??reporting.atto.societa??" code="reporting.atto.societa" /> </label>
							<div class="col-md-11">
							<select id="attoSocieta" name="societa" class="form-control">
							<option value=""></option>
							<c:if test="${ ltsSocieta != null }">
							<c:forEach items="${ltsSocieta}" var="oggetto">
							<option value="${ oggetto.id }" <c:if test="${ oggetto.ragioneSociale eq 'Snam S.p.A.' }"> selected</c:if> ><c:out value="${oggetto.ragioneSociale}"></c:out></option>
							</c:forEach>
							</c:if>
							</select>
							</div>
							<div class="col-md-1" style="padding-top: 18px;">
							<button type="button" onclick="addSocieta()" class="btn palette-Green-300 bg btn-float waves-effect waves-circle waves-float">
																<span class="glyphicon glyphicon-plus"></span>
       							 							</button>
						  </div>
						  
						</div>
						</div>
						
						<div style="padding-bottom:10px" class="col-md-12">
							<div class="form-group" id="box-destinatario">
							<label class="col-md-12 control-label" for="attoDestinatario"><spring:message text="??reporting.atto.destinatario??" code="reporting.atto.destinatario" /> </label>
							<div class="col-md-11">
						<select id="attoDestinatario" name="destinatario"
						class="form-control">
						<option></option>
						<c:if test="${ ltsDestinatario != null }">
						<c:forEach items="${ltsDestinatario}" var="oggetto">
							<option value="${ oggetto.matricolaUtil }">
								<c:out value="${oggetto.nominativoUtil}"></c:out>
							</option>
						</c:forEach>
						</c:if>
					 	</select>
							</div>
							<div class="col-md-1" style="padding-top: 18px;">
							<button type="button" onclick="addDestinatario()" class="btn palette-Green-300 bg btn-float waves-effect waves-circle waves-float">
																<span class="glyphicon glyphicon-plus"></span>
       							 							</button>
						  </div>
						</div>
						</div>
						<div class="form-group">
							<label class="col-md-12 control-label" for="tipoAttoGiudiziario"><spring:message text="??reporting.atto.tipoAtto??" code="reporting.atto.tipoAtto" /> </label>
							<div class="col-md-12">
								 <input id="tipoAttoGiudiziario" name="tipoAttoGiudiziario"
									type="text" placeholder="" class="typeahead form-control input-md"> 
									<span class="help-block"></span>
							</div>
						</div>
						
						
						<!-- Select Basic -->
						<div class="form-group">
							<label class="col-md-12 control-label" for="attoStatoAtto"><spring:message text="??reporting.atto.stato??" code="reporting.atto.stato" /> </label>
							<div class="col-md-12">
							<select id="attoStatoAtto" name="attoStatoAtto" class="form-control">
							<option>TUTTI</option>
							<c:if test="${ ltsStatoAtto != null }">
							<c:forEach items="${ltsStatoAtto}" var="oggetto">
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
							<label class="col-md-12 control-label" for="data_notifica_da"><spring:message text="??reporting.atto.dataNotificaDa??" code="reporting.atto.dataNotificaDa" /> </label>
							<div class="col-md-12">
								 <input id="data_notifica_da" name="data_notifica_da"
									type="text" placeholder="" class="typeahead form-control input-md date-picker"> 
									<span class="help-block"></span>
							</div>
						</div>
						 
						 <div class="form-group">
							<label class="col-md-12 control-label" for="data_notifica_a"><spring:message text="??reporting.atto.dataNotificaA??" code="reporting.atto.dataNotificaA" /> </label>
							<div class="col-md-12">
								 <input id="data_notifica_a" name="data_notifica_a"
									type="text" placeholder="inserisci il Nome Fascicolo" class="typeahead form-control input-md date-picker"> 
									<span class="help-block"></span>
							</div>
						</div>
						 
						 <div class="form-group">
							<label class="col-md-12 control-label" for="associatoAfascicolo"><spring:message text="??reporting.atto.associatoAFascicolo??" code="reporting.atto.associatoAFascicolo" /> </label>
							<div class="col-md-12">
							<select id="associatoAfascicolo" name="associatoAfascicolo" class="form-control">
							<option></option>
							<option value="SI">SI </option>
							<option value="NO">NO </option>
							</select>
							</div>
						</div>
							</fieldset> 
							<div class="modal-footer">
								<button style="float:left" type="button" class="btn btn-primary waves-effect" onclick="pulisciCampi(this)"><spring:message text="??reporting.atto.btn.clear??" code="reporting.atto.btn.clear" /></button>
								<button type="submit" class="btn btn-primary" ><spring:message text="??reporting.atto.btn.applicaFiltri??" code="reporting.atto.btn.applicaFiltri" /> </button>
								 
							</div>
								</form>
					 
							</div>
					 

	<script>
	
	//SOCIETA
	
	function addSocieta(){
	var esixst=0;
 
	
	var max = findMaxSoc();
	max = max +1;
	var societaText="",societaId="";
	societaId=$('#attoSocieta').val();
	societaText=$("#attoSocieta option:selected").text();
	societaText=$.trim(societaText);
	societaId=$.trim(societaId);
		if(societaId=="")
		esixst=1;
	
	$("input[name='societa']").each(function(e){
	if($(this).val()==societaId)
	esixst=1;
	})
	
	$('#attoSocieta').val("");
	if(!esixst)
	$('#box-societa').append('<div class="col-md-12 box-dinamik" style="margin-top:25px;" id="societa_'+ max +'">'+
			'<div class="col-md-9">'+
			'<input type="hidden" name="societa" value="'+ societaId +'" class="form-control">'+
			'<input value="'+ societaText +'" class="form-control" type="text" readonly>'+
		'</div><div class="col-md-1" style="padding-top: 18px;">'+
		'<button type="button" onclick="removeSocieta(this.id)" id="'+ max +'" class="btn palette-Green-300 bg btn-float waves-effect waves-circle waves-float">'+
			'<span class="glyphicon glyphicon-minus"></span>'+
		'</button></div></div>' );
		
		
		
}

function removeSocieta(idCliccato){
	$('#societa_'+ idCliccato).remove();
}
	
function findMaxSoc(){
	
	var textemails = document.querySelectorAll("input[name^='societa'");
	if(textemails.length <= 1)
		return 0;
	 else
	return textemails.length;
	 
}	
	
	
	//DESTINATARIO
	
	function addDestinatario(){
	var esixst=0;
 
	
	var max = findMaxDest();
	max = max +1;
	var destinatarioText="",destinatarioId="";
	destinatarioId=$('#attoDestinatario').val();
	destinatarioText=$("#attoDestinatario option:selected").text();
	destinatarioText=$.trim(destinatarioText);
	destinatarioId=$.trim(destinatarioId);
	if(destinatarioId=="")
	  esixst=1;
	
	$("input[name='destinatario']").each(function(e){
	if($(this).val()==destinatarioId)
	esixst=1;
	})
	
	$('#attoDestinatario').val("");
	if(!esixst)
	$('#box-destinatario').append('<div class="col-md-12 box-dinamik" style="margin-top:25px;" id="destinatario_'+ max +'">'+
			'<div class="col-md-9">'+
			'<input type="hidden" name="destinatario" value="'+ destinatarioId +'" class="form-control">'+
			'<input value="'+ destinatarioText +'" class="form-control" type="text" readonly>'+
		'</div><div class="col-md-1" style="padding-top: 18px;">'+
		'<button type="button" onclick="removeDestinatario(this.id)" id="'+ max +'" class="btn palette-Green-300 bg btn-float waves-effect waves-circle waves-float">'+
			'<span class="glyphicon glyphicon-minus"></span>'+
		'</button></div></div>' );
		
		
		
}

function removeDestinatario(idCliccato){
	$('#destinatario_'+ idCliccato).remove();
}
	
function findMaxDest(){
	
	var textemails = document.querySelectorAll("input[name^='destinatario'");
	if(textemails.length <= 1)
		return 0;
	 else
	return textemails.length;
	 
}	
	
	</script>

<!-- FINE  RICERCA ATTO-->	
