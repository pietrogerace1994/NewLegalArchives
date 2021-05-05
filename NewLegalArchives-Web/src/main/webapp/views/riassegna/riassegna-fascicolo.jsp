<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/WEB-INF/spring.tld" prefix="spring"%>
<%@ taglib prefix="form" uri="/WEB-INF/spring-form.tld"%>
<%@ taglib uri="/WEB-INF/engsecurity.tld" prefix="engsecurity" %>


<!DOCTYPE html lang="${language}">
<!--[if IE 9 ]><html class="ie9"><![endif]-->

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Legal Archives</title>

<jsp:include page="/parts/script-init.jsp">
</jsp:include>

</head>

<body data-ma-header="teal">
	<jsp:include page="/parts/headerNotSearch.jsp">
	</jsp:include>

	<!-- SECION MAIN -->
	<section id="main">
		<jsp:include page="/parts/aside.jsp">
		</jsp:include>
		<!-- SECTION CONTENT -->
		
		<style>
		p.text-left a {
		color: #fff;
		}
		.search{
		display: none!important;
		}
		input.select-box {
		color: #000000!important;
		width: 22px;
		height: 22px;
		}
		#box-riassegna-fascicoli{
		display: none;
		}
		
		.table > thead > tr > td.active,
		.table > tbody > tr > td.active,
		.table > tfoot > tr > td.active,
		.table > thead > tr > th.active,
		.table > tbody > tr > th.active,
		.table > tfoot > tr > th.active,
		.table > thead > tr.active > td,
		.table > tbody > tr.active > td,
		.table > tfoot > tr.active > td,
		.table > thead > tr.active > th,
		.table > tbody > tr.active > th,
		.table > tfoot > tr.active > th {
		  background-color: rgb(196, 255, 133);
		  border-bottom: 4px solid #fff!important;
		}
		.pagination > .active > a, .pagination > .active > span, .pagination > .active > a:hover, .pagination > .active > span:hover, .pagination > .active > a:focus, .pagination > .active > span:focus {
		z-index: 3;
		color: #ffffff;
		background-color: #52a256;
		border-color: #ffffff;
		cursor: default;
		}
		
		.cSearch{
		margin-top: -30px!important;
	    position: absolute;
	    display: block!important;
	    width: 160px!important;
	    height: 150px!important;
	    border-radius: 50%!important;
	    border: 5px solid #66bb6a;
	    background: #26a69a;
	    text-align: center!important;
	    margin: 0 auto!important;
	    left: 43%;
	    line-height: 6.5;
	    top: -50px;
	    background-color: #26a69a;
				}
				
		#ownerSelect{
		margin-top: -30px!important;
	    position: absolute;
	    display: block!important;
	    width: 160px!important;
	    height: 150px!important;
	    border-radius: 50%!important;
	    background: transparent;
	    text-align: center!important;
	    margin: 0 auto!important;
		left: 43%;
	    line-height: 17;
	    top: -50px;
	    z-index: 1;
	    font-size: 11px;
		cursor:pointer;
		}
		
		</style>
		<section id="content">
			<div class="container">
				<div class="row">
					<div id="col-1" class="col-lg-12 col-md-12 col-sm-12 col-sx-12">

						<div class="card">
							<div class="card-header ch-dark palette-Green-SNAM bg">
								 
									<h2 style="font-size: 28px">
									 <spring:message text="??assegna.label.title??" code="assegna.label.title" />	 
									</h2>
								 	<p class="text-left" style="display:block;position:relative;" style="font-size:18px;">
										<!-- 
										<spring:message text="??atto.label.ricercaCustomAttoStart??" code="atto.label.ricercaCustomAttoStart" /> 
										<a data-toggle="modal" href="#modalRicercaRiassegnaFascicolo" class="" style="font-size:20px;font-weight:bold">
											<spring:message text="??atto.label.ricercaCustomAttoEnd??" code="atto.label.ricercaCustomAttoEnd" />
											</a>
									 -->
									<a data-toggle="modal" href="#modalRicercaRiassegnaFascicolo" class="cSearch" style="font-size:20px;font-weight:bold">
										<spring:message text="??assegna.label.avviaricerca??" code="assegna.label.avviaricerca" />
									</a>
									<span id="ownerSelect"  data-toggle="modal" href="#modalRicercaRiassegnaFascicolo"></span>		
											
									</p>
							</div>
							<div class="card-body" id="box-riassegna-fascicoli">
							
									<c:if test="${ not empty param.successMessage }">
										<div class="alert alert-info">
											<spring:message code="messaggio.operazione.ok"
												text="??messaggio.operazione.ok??"></spring:message>
										</div>
									</c:if>
									<c:if test="${ not empty param.errorMessage }">
										<div class="alert alert-danger">
											<spring:message code="${errorMessage}"
												text="??${errorMessage}??"></spring:message>
										</div>
									</c:if>
							
							<!-- Area Table Result-->
							
							<div class="table-responsive">
 						
						
					<table id='riassegna-fascicolo'  class="table table-responsive" cellspacing="0" width="100%">
					   <thead>
						<tr>
							<th data-column-id="id" data-identifier="true" data-visible="false" data-visible-in-selection="false" data-width="5%">ID</th>
							<th data-column-id="numeroFascicolo" data-sortable="false" data-width="35%"><spring:message text="??assegna.label.numerofascicolo??" code="assegna.label.numerofascicolo" /></th>
							<th data-column-id="stato" data-sortable="false" data-width="30%"><spring:message text="??assegna.label.stato??" code="assegna.label.stato" /></th>
							<th data-column-id="owner" data-sortable="false" data-width="30%"><spring:message text="??assegna.label.owner??" code="assegna.label.owner" /></th>
						</tr>	
						</thead>
					</table>
					
						
						
						
							</div>
							<!-- Table Result
							<!-- Utenti Result-->
							<script>
							var selectjs=[];
							</script>
							<div class="col-md-12" style="padding: 20px;background: #99c79b;">
							<div class="col-md-12 column">
							<label class="col-md-12 control-label" for="riassegnalegaleInterno"><spring:message text="??assegna.label.assegnaselezionatiutente??" code="assegna.label.assegnaselezionatiutente" />:</label>
							<div class="col-md-8">
							<select id="riassegnalegaleInterno" name="riassegnalegaleInterno" class="form-control">
								<option></option>
									<c:if test="${ utentiRiassegnazione != null }">
								<c:forEach items="${utentiRiassegnazione}" var="oggetto">
							<option value="${ oggetto.matricolaUtil }"><c:out value="${oggetto.nominativoUtil}"></c:out></option>
							<script>selectjs.push({val:"${ oggetto.matricolaUtil }",text:"${oggetto.nominativoUtil}"})</script>	
								</c:forEach>
								</c:if>
								</select>
							</div>
							<div class="col-md-4">
							<button id="save-assegna" type="button"
											class="btn btn-success"
											style="margin-left: 5px" disabled onclick="assegnaSelezionati()">
											<i class="fa fa-save"></i>
										<spring:message text="??assegna.btn.assegna??" code="assegna.btn.assegna" />
										</button>
										</div>
										
							</div>
							</div>
							<!-- Utenti Result-->
			
							<!-- Fine Area Table Result-->
							</div>
						</div><!--/ fine card -->
					</div>
				</div>
				
				
			</div>
			
			
			
			
			<!--MODALFASCICOLO-->

			<!--/ fine col-1 -->
		</section>
<jsp:include page="/views/riassegna/modal-ricerca.jsp"></jsp:include> 
	</section>
  

	<footer>
		<jsp:include page="/parts/footer.jsp"></jsp:include>
	</footer>
	<jsp:include page="/parts/script-end.jsp"></jsp:include>

 
	<script src="<%=request.getContextPath()%>/portal/js/controller/riassegnaFascicolo.js"></script>

	<script type="text/javascript">
		initTabellaRicercaFascicolo();
	</script>
	
	<script>
	var ISLOAD=false;
	
	function filtraRicercaAssegnaFascicolo(){
		$('#save-assegna').attr('disabled','true');
		
		if($("#legaleInterno").val()==""){
			$("#box-riassegna-fascicoli").css("display","none");	
			$('#ownerSelect').html("");	
			 
			$('#modalRicercaRiassegnaFascicolo').modal("hide");
			jalert.open('<spring:message text="??assegna.msg.selezionalegaleinterno??" code="assegna.msg.selezionalegaleinterno" />');
			
			return;
		}
		/*
		if($("#utenteNominativo").text()!=""){
			 console.log($('#legaleInterno').val());
		}
		*/
	$('#modalRicercaRiassegnaFascicolo').modal("hide");
	$('#ownerSelect').html($('#legaleInterno').find('option:selected').text());
	$("#box-riassegna-fascicoli").css("display","block");	
		
	$("#riassegna-fascicolo").bootgrid("destroy");
	ISLOAD=false;
	var societa = encodeURIComponent(document
		.getElementById("societa").value || "0");
	var settoreGiuridico = encodeURIComponent(document
			.getElementById("settoreGiuridico").value || "0");
	var tipologiaFascicolo = encodeURIComponent(document
			.getElementById("tipologiaFascicolo").value || "0");
	var nomeFascicolo = encodeURIComponent(document
			.getElementById("nomeFascicolo").value || "0");
	var legaleInterno = encodeURIComponent(document
			.getElementById("legaleInterno").value || "0");

	var filtraRicerca="societa="+societa+"&settoreGiuridico="+settoreGiuridico+"&tipologiaFascicolo="+tipologiaFascicolo+"&nomeFascicolo="+nomeFascicolo+"&legaleInterno="+legaleInterno;
	
	var grid=$('#riassegna-fascicolo').bootgrid({
		ajax: true,
		ajaxSettings: {
		method: "GET",
		cache: false
		},
		selection: true,
		multiSelect: true,
		rowSelect: true,
		keepSelection: true,
		url: './ricercaAssegnaFascicoli.action?'+filtraRicerca,
		formatters: {
						"link": function(column, row)
						{			
							return "<a onclick=\"visualizzaDettaglio(" + row.id + ")\" href=\"javascript:void(0)\" >"+row.titolo+"</a> "; 
						},
						"numberFormat":function(column, row) {
							if(!isNaN(row.totale)){
							var multiplier = Math.pow(10, 2);
							return (Math.round(row.totale * multiplier) / multiplier).toFixed(2).replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,");
							}else{ return row.totale;} 
							}
		}
		}).on("loaded.rs.jquery.bootgrid", function()
		{
			if(!ISLOAD){
				ISLOAD=true;
			$("#allChek").bind("click",function(){
			if($(this).attr('checked')){
				$(this).removeAttr("checked")				
			$(".select-box",$("#riassegna-fascicolo tbody")).each(function(e){
					if($(this).attr('checked')){
					$(this).click(); 
					}
			})
						
			 }else{
			 $(this).attr('checked',true)
			$(".select-box",$("#riassegna-fascicolo tbody")).each(function(e){
					if(!$(this).attr('checked')){
					$(this).click(); 
					}
			}) 
			 
			 }
			 });
			 /* Aggiorno la Select OWNER assegna */
			  aggiornaOwnerSelectAssegna();
			
		   }else{ $("#allChek").removeAttr("checked") }
			
		$(".select-box",$("#riassegna-fascicolo tbody")).on("click", function(e)
		{	
			var this_=$(this);
			if($(this_).attr('checked')){
			$(this_).removeAttr("checked");
			$(this_).parent( "td" ).parent( "tr" ).removeClass("active");
			}else{
			$(this_).attr('checked',true)
			$(this_).parent( "td" ).parent( "tr" ).addClass("active");
			}
		
		})
	})
}
	function allCheckID(){
		var arryIdFascicoli=[];
		$("input.select-box",$("#riassegna-fascicolo tbody")).each(function(el){
			if($(this).attr('checked')){
			arryIdFascicoli.push($(this).val())
			}
			   })
			  // alert(arryIdFascicoli.join("-"))
		return arryIdFascicoli.join("-");		
	}

	function aggiornaOwnerSelectAssegna(){
		$('#riassegnalegaleInterno').html("");
		var legaleInt=$('#legaleInterno').val();
		$("<option></option>",{value:"", text:""}).appendTo('#riassegnalegaleInterno');
		for(i=0;i<selectjs.length;i++){
		(function(val,tex){
		if(val!=legaleInt){
		$("<option></option>",{value:val, text:tex}).appendTo('#riassegnalegaleInterno');
		 }
		})(selectjs[i].val,selectjs[i].text)
		}
		}
	

function getOwnerSelect(){

	if($.trim($('#riassegnalegaleInterno').val())!=""){
	return $.trim($('#riassegnalegaleInterno').val());
	}else{

	return false;

	}

	}	
	
function assegnaSelezionati(){
		 
	var ids=allCheckID();
	var owner_="";
	if(getOwnerSelect()){
	owner_=getOwnerSelect();
	}else{
	jalert.open('<spring:message text="??assegna.msg.selezionalegaleinterno??" code="assegna.msg.selezionalegaleinterno" />')
	 
	return;
	}
	
	if(ids.split(" ").join("")!=""){
	waitingDialog.show('Loading...');
	$.post( "./riassegna.action", { idFas: ids,owner:owner_,CSRFToken:legalSecurity.getToken() })
	  .done(function( data ) {
		if(data.split(" ").join("")=="OK"){
		 waitingDialog.hide();
			jalert.autoClose("Operazione conclusa correttamente!",3000);
		 //$(".collapse").collapse('hide');
			document.location = legalSecurity.verifyToken(WEBAPP_BASE_URL + "/riassegna/index.action");
		
				}
		else if(data.split(" ").join("")=="KO"){
			waitingDialog.hide();
			jalert.open("Oops! Errore generico...")
			 
				}else{ 
				waitingDialog.hide();
				jalert.autoClose(" "+data+" ",4000,{isButtonClose:false});
			 
				 }		
		  
	  }).fail(function( jqxhr, textStatus, error ) {
		    var err = textStatus + ", " + error;
		    waitingDialog.hide();
			jalert.open("Oops! "+err)
			 
		});
}else{ 
 jalert.open('<spring:message text="??assegna.msg.selezionalefascicolo??" code="assegna.msg.selezionalefascicolo" />')
 
}

}
	

function pulisciCampi(){
	$("#societa").val("");
	$("#settoreGiuridico").val("");
	$("#tipologiaFascicolo").val("");
	$("#nomeFascicolo").val("");
	$("#legaleInterno").val("");
 }
	
	
$('#riassegnalegaleInterno').change(function(){
	if($(this).find('option:selected').text()==""){
	$('#save-assegna').attr('disabled','true')
	}else{
	$('#save-assegna').removeAttr('disabled')
	}
})	
	
</script>	
	
 
</body>
</html>
