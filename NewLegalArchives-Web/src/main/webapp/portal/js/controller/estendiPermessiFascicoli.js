/**
 * 
 */
$(document).ready(function(){
	filtraRicercaAssegnaFascicolo();
});


function filtraRicercaAssegnaFascicolo(){
	
	var ISLOAD=false;
	$('#save-estendi').attr('disabled','true');
	$('#modalRicercaEstendiPermessiFascicolo').modal("hide");
	$("#box-estendiPermessi-fascicoli").css("display","block");	
	$("#estendiPermessi-fascicolo").bootgrid("destroy");
	
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
	var utenteMatricola = encodeURIComponent(document
			.getElementById("utenteMatricola").value || "0");
	var amministratore = encodeURIComponent(document
			.getElementById("amministratore").value || "0");

	var filtraRicerca="societa="+societa+"&settoreGiuridico="+settoreGiuridico
				+"&tipologiaFascicolo="+tipologiaFascicolo+"&nomeFascicolo="+nomeFascicolo
				+"&legaleInterno="+legaleInterno+"&utenteMatricola="+utenteMatricola
				+"&amministratore="+amministratore;

	var grid=$('#estendiPermessi-fascicolo').bootgrid({
		ajax: true,
		ajaxSettings: {
			method: "GET",
			cache: false
		},
		selection: true,
		multiSelect: true,
		rowSelect: true,
		keepSelection: true,
		url: './ricercaEstendiPermessiFascicoli.action?'+filtraRicerca,
		formatters: {
			"link": function(column, row)
			{			
				return "<a onclick=\"visualizzaDettaglio(" + row.id + ")\" href=\"javascript:void(0)\" >"+row.titolo+"</a> "; 
			},
			"numberFormat":function(column, row) {
				if(!isNaN(row.totale)){
					var multiplier = Math.pow(10, 2);
					return (Math.round(row.totale * multiplier) / multiplier).toFixed(2).replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,");
				}
				else{ return row.totale;} 
			}
		}
	}).on("loaded.rs.jquery.bootgrid", function()
	{
		if(!ISLOAD){
			ISLOAD=true;
			$("#allChek").bind("click",function(){
				if($(this).attr('checked')){
					$(this).removeAttr("checked")				
					$(".select-box",$("#estendiPermessi-fascicolo tbody")).each(function(e){
						if($(this).attr('checked')){
							$(this).click(); 
						}
					})
				}
				else{
					$(this).attr('checked',true)
					$(".select-box",$("#estendiPermessi-fascicolo tbody")).each(function(e){
						if(!$(this).attr('checked')){
							$(this).click(); 
						}
					}) 
				 }
			 });
			
			 /* Aggiorno la Select OWNER assegna */
			  aggiornaOwnerSelectAssegna();
			  
		}
		else{ $("#allChek").removeAttr("checked") }
		
		$(".select-box",$("#estendiPermessi-fascicolo tbody")).on("click", function(e)
		{	
			var this_=$(this);
			if($(this_).attr('checked')){
			$(this_).removeAttr("checked");
			$(this_).parent( "td" ).parent( "tr" ).removeClass("active");
			checkAndActiveButton();
			}
			else{
				$(this_).attr('checked',true)
				$(this_).parent( "td" ).parent( "tr" ).addClass("active");
				checkAndActiveButton();
			}
		})
	})
}

function checkAndActiveButton(){
	
	$("#chkPermessiLettura").attr("checked", false);
	$("#chkPermessiScrittura").attr("checked", false);
	
	var arryIdFascicoli=[];
	$("input.select-box",$("#estendiPermessi-fascicolo tbody")).each(function(el){
		if($(this).attr('checked')){
		arryIdFascicoli.push($(this).val())
		}
	})
	
	if($('#riassegnalegaleInterno').find('option:selected').text()==""){
		$('#save-estendi').attr('disabled','true')
	}else{
		if(arryIdFascicoli.length > 0)
			$('#save-estendi').removeAttr('disabled');
		else
			$('#save-estendi').attr('disabled','true')
	}
	return arryIdFascicoli.join("-");
}


function allCheckID(){
	var arryIdFascicoli=[];
	$("input.select-box",$("#estendiPermessi-fascicolo tbody")).each(function(el){
		if($(this).attr('checked')){
		arryIdFascicoli.push($(this).val())
		}
	})
	return arryIdFascicoli.join("-");		
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
	
	var checksLettura = $('#chkPermessiLettura').is(':checked');
	var checksScrittura = $('#chkPermessiScrittura').is(':checked');

	if(ids.split(" ").join("")!=""){
		
		var apos = "\'";
		
		$('#gestionePermessiFascicolo').on('show.bs.modal', function(e) {
			
			$(this).find("#vuoiRimuovere").css("display", "none");
			$(this).find("#vuoiAggiungere").css("display", "none");
			
			if(!checksLettura && !checksScrittura){
				$(this).find("#vuoiRimuovere").css("display", "block");
				$(this).find("#vuoiAggiungere").css("display", "none");
				
			}
			else{
				$(this).find("#vuoiAggiungere").css("display", "block");
				$(this).find("#vuoiRimuovere").css("display", "none");
			}
			
			$(this).find("#btnEstendiPermessi").attr('onclick', "estendiPermessiFascicoli("+apos+ids+apos+","+apos+owner_+apos+","+checksLettura+","+checksScrittura+")");
		});
		
		$('#gestionePermessiFascicolo').modal('show');
		
	}
	else{ 
		jalert.open('<spring:message text="??assegna.msg.selezionalefascicolo??" code="assegna.msg.selezionalefascicolo" />')
	}
}


function estendiPermessiFascicoli(ids, owner, checksLettura, checksScrittura){
	
	waitingDialog.show('Loading...');
	
	var ajaxUtil = new AjaxUtil();
	
	var callBackFn = function(data, stato) {   
		waitingDialog.hide();
		visualizzaMessaggio(data);
		document.location = legalSecurity.verifyToken(WEBAPP_BASE_URL + "/estendiPermessi/index.action");
	};
	
	var callBackFnErr = function(data, stato) {  
		waitingDialog.hide();
		visualizzaMessaggio(data);
	};
	
	var params = "";
	
	params += "idsF=" + ids;
	params += "&owner=" + owner;
	params += "&permessoLettura=" + checksLettura;
	params += "&permessoScrittura=" + checksScrittura;
	params += "&CSRFToken=" +legalSecurity.getToken();
	
	var url = WEBAPP_BASE_URL + "fascicolo/estendiPermessiFascicoli.action";
	ajaxUtil.ajax(url, params, "post", "application/x-www-form-urlencoded", callBackFn, null, callBackFnErr);
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

$('#riassegnalegaleInterno').change(function(){
	
	$("#chkPermessiLettura").attr("checked", false);
	$("#chkPermessiScrittura").attr("checked", false);
	
	var arryIdFascicoli=[];
	$("input.select-box",$("#estendiPermessi-fascicolo tbody")).each(function(el){
		if($(this).attr('checked')){
		arryIdFascicoli.push($(this).val())
		}
	})
	
	if($(this).find('option:selected').text()==""){
		$('#save-estendi').attr('disabled','true')
	}else{
		if(arryIdFascicoli.length > 0)
			$('#save-estendi').removeAttr('disabled');
		else
			$('#save-estendi').attr('disabled','true')
	}
})

