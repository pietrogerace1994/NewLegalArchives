<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>


<!-- RICERCA PROFORMA-->

<div class="row">
	<form action="<%=request.getContextPath()%>/reporting/export-proforma.action" method="post" >
		<engsecurity:token regenerate="false"/>
		<fieldset>
			<div style="padding-bottom:10px" class="col-md-12">
				<div class="form-group" id="box-societa-proforma">
					<label class="col-md-11 control-label" for="proformaSocieta"><spring:message text="??reporting.proforma.societa??" code="reporting.proforma.societa" /></label>
					<div class="col-md-11">
						<select id="proformaSocieta" name="proformaSocieta" class="form-control">
						<option></option>
						<c:if test="${ ltsSocieta != null }">
						<c:forEach items="${ltsSocieta}" var="oggetto">
						<option value="${ oggetto.id }" <c:if test="${ oggetto.ragioneSociale eq 'Snam S.p.A.' }"> selected</c:if> ><c:out value="${oggetto.ragioneSociale}"></c:out></option>
						</c:forEach>
						</c:if>
						</select>
					</div>
					
					<div class="col-md-1" style="padding-top: 18px;">
						<button type="button" onclick="addSocietaPro()" class="btn palette-Green-300 bg btn-float waves-effect waves-circle waves-float">
							<span class="glyphicon glyphicon-plus"></span>
      					</button>
				    </div>
				
				</div>	
			</div>	
			<!-- Select Basic -->

			<div class="form-group">
				<label class="col-md-12 control-label" for="data_autorizzazione_da"><spring:message text="??reporting.proforma.dataAutorizzazioneDa??" code="reporting.proforma.dataAutorizzazioneDa" /> </label>
				<div class="col-md-12">
					 <input id="data_autorizzazione_da" name="data_autorizzazione_da"
						type="text" placeholder="" class="typeahead form-control input-md date-picker"> 
						<span class="help-block"></span>
				</div>
			</div>
		 
		 	<div class="form-group">
				<label class="col-md-12 control-label" for="data_autorizzazione_a"><spring:message text="??reporting.proforma.dataAutorizzazioneA??" code="reporting.proforma.dataAutorizzazioneA" /></label>
					<div class="col-md-12">
						 <input id="data_autorizzazione_a" name="data_autorizzazione_a"
							type="text" placeholder="" class="typeahead form-control input-md date-picker"> 
							<span class="help-block"></span>
					</div>
			</div>
		 
			<div class="form-group">
				<label class="col-md-12 control-label" for="proformaSettoreGiuridico"><spring:message text="??reporting.proforma.settoreGiuridico??" code="reporting.proforma.settoreGiuridico" /> </label>
				<div class="col-md-12">
					<select id="proformaSettoreGiuridico" name="proformaSettoreGiuridico" class="form-control">
					<option>Tutti</option>
					<c:if test="${ ltsSettoreGiuridico != null }">
						<c:forEach items="${ltsSettoreGiuridico}" var="oggetto">
						<option value="${ oggetto.codGruppoLingua }"><c:out value="${oggetto.nome}"></c:out></option>
						</c:forEach>
						</c:if>
					</select>
				</div>
			</div>
		
			<div style="padding-bottom:10px" class="col-md-12">	
				<div class="form-group" id="box-destinatario-proforma">
					<label class="col-md-11 control-label" for="proformaOwner"><spring:message text="??reporting.proforma.legaleInternoOwner??" code="reporting.proforma.legaleInternoOwner" /> </label>
					<div class="col-md-11">
						<select id="proformaOwner" name="proformaOwner" class="form-control">
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
						<button type="button" onclick="addDestinatarioPro()" 
						class="btn palette-Green-300 bg btn-float waves-effect waves-circle waves-float">
							<span class="glyphicon glyphicon-plus"></span>
     					</button>
				  	</div>
				</div>
			</div>
		
			<div class="form-group">
				<label class="col-md-12 control-label" for="proformaProfessionistaEsterno"><spring:message text="??reporting.incarico.professionista??" code="reporting.incarico.professionista" /> </label>
				<div class="col-md-12">
					<select id="proformaProfessionistaEsterno" name="proformaProfessionistaEsterno" class="form-control">
					<option>Tutti</option>
					<c:if test="${ ltsProfessionistaEsterno != null }">
						<c:forEach items="${ltsProfessionistaEsterno}" var="oggetto">
						<option value="${ oggetto.id }">
						<c:out value="${oggetto.nome}"></c:out>&nbsp;&nbsp;<c:out value="${oggetto.cognome}"></c:out>
						</option>
						</c:forEach>
					</c:if>
					</select>
				</div>
			</div>

		
		</fieldset> 
		<div class="modal-footer">
			<button style="float:left" type="button" class="btn btn-primary waves-effect" onclick="pulisciCampi(this)"><spring:message text="??reporting.proforma.btn.clear??" code="reporting.proforma.btn.clear" /></button>
			<button type="submit" class="btn btn-primary" ><spring:message text="??reporting.proforma.btn.applicaFiltri??" code="reporting.proforma.btn.applicaFiltri" /></button>
		</div>
	</form>
</div>

<script>
	
	//SOCIETA
	
	function addSocietaPro(){
		var esixst=0;
	 
		
		var max = findMaxSocPro();
		max = max +1;
		var societaText="",societaId="";
		societaId=$('#proformaSocieta').val();
		societaText=$("#proformaSocieta option:selected").text();
		societaText=$.trim(societaText);
		societaId=$.trim(societaId);
			if(societaId=="")
			esixst=1;
		
		$("input[name='proformaSocieta']").each(function(e){
		if($(this).val()==societaId)
		esixst=1;
		})
		
		$('#proformaSocieta').val("");
		if(!esixst)
		$('#box-societa-proforma').append('<div class="col-md-12 box-dinamik" style="margin-top:25px;" id="societaPro_'+ max +'">'+
				'<div class="col-md-9">'+
				'<input type="hidden" name="proformaSocieta" value="'+ societaId +'" class="form-control">'+
				'<input value="'+ societaText +'" class="form-control" type="text" readonly>'+
			'</div><div class="col-md-1" style="padding-top: 18px;">'+
			'<button type="button" onclick="removeSocietaPro(this.id)" id="'+ max +'" class="btn palette-Green-300 bg btn-float waves-effect waves-circle waves-float">'+
				'<span class="glyphicon glyphicon-minus"></span>'+
			'</button></div></div>' );
	}

	function removeSocietaPro(idCliccato){
		$('#societaPro_'+ idCliccato).remove();
	}
	
	function findMaxSocPro(){
		
		var textemails = document.querySelectorAll("input[name^='proformaSocieta'");
		if(textemails.length <= 1)
			return 0;
		 else
		return textemails.length;
		 
	}	
	
	//DESTINATARIO
	
	function addDestinatarioPro(){
		var esixst=0;
	 
		
		var max = findMaxDestPro();
		max = max +1;
		var destinatarioText="",destinatarioId="";
		destinatarioId=$('#proformaOwner').val();
		destinatarioText=$("#proformaOwner option:selected").text();
		destinatarioText=$.trim(destinatarioText);
		destinatarioId=$.trim(destinatarioId);
		if(destinatarioId=="")
		  esixst=1;
		
		$("input[name='proformaOwner']").each(function(e){
		if($(this).val()==destinatarioId)
		esixst=1;
		})
		
		$('#proformaOwner').val("");
		if(!esixst)
		$('#box-destinatario-proforma').append('<div class="col-md-12 box-dinamik" style="margin-top:25px;" id="destinatarioPro_'+ max +'">'+
				'<div class="col-md-9">'+
				'<input type="hidden" name="proformaOwner" value="'+ destinatarioId +'" class="form-control">'+
				'<input value="'+ destinatarioText +'" class="form-control" type="text" readonly>'+
			'</div><div class="col-md-1" style="padding-top: 18px;">'+
			'<button type="button" onclick="removeDestinatarioPro(this.id)" id="'+ max +'" class="btn palette-Green-300 bg btn-float waves-effect waves-circle waves-float">'+
				'<span class="glyphicon glyphicon-minus"></span>'+
			'</button></div></div>' );
	}

	function removeDestinatarioPro(idCliccato){
		$('#destinatarioPro_'+ idCliccato).remove();
	}
	
	function findMaxDestPro(){
		
		var textemails = document.querySelectorAll("input[name^='proformaOwner'");
		if(textemails.length <= 1)
			return 0;
		 else
		return textemails.length;
	}	
</script>

<!-- FINE RICERCA PROFORMA -->	
