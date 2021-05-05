/**
 * 
 */
$(document).ready(function(){
 

	$("#save-atto").attr("disabled","disabled")
	$('.isupdate').attr("disabled","disabled")
	$('.isAltri').css("display","none")
	$("#invia-altri").click(function(){
	$('.isAltri').css("display","block")
	$('.isupdate').attr("disabled","disabled")
	$('#operazioneCorrente').val("inviaaltriuffici")
	$("#save-atto").removeAttr("disabled");
	})
	
	$("#btn-modifica").click(function(){
		$('.isupdate').removeAttr("disabled")
		$('.isAltri').css("display","none")
		$('#operazioneCorrente').val("modifica")
		$("#save-atto").removeAttr("disabled");
	})

	
	
	$("#download-atto").on("click",function(){
		var uuid=$(this).attr("uuid");
		return fnDownloadAtto(uuid);
	})
	
	//$("input[name=id]").click();

	 
 
	
});


function openAtto(id,azione){
	var form = document.createElement("form");
	form.action="./visualizza.action";
	form.method="GET";
	var inp=document.createElement("input");
	inp.type="hidden";
	inp.name="id";
	inp.value=id;
	form.appendChild(inp);
	var inz=document.createElement("input");
	inz.type="hidden";
	inz.name="azione";
	inz.value=azione;
	form.appendChild(inz);
	form.submit();
	}


function fnDownloadAtto(id){
	var form = document.createElement("form");
	form.action="./download.action";
	form.method="GET";
	var inp=document.createElement("input");
	inp.type="hidden";
	inp.name="uuid";
	inp.value=id;
	form.appendChild(inp);
	form.submit();
	}
	
var listError=[];
var htmElement=[];

function validaForm(frm){
    listError=[];
	var htmElement=[];

	 if(eliminaSpazi(frm.dataNotifica.value)==""){
		listError.push({obj:frm.dataNotifica,msg:"Campo Data Obbligatorio"})
	}
	if(eliminaSpazi(frm.dataNotifica.value)!="" && !validaData(frm.dataNotifica.value)){
		listError.push({obj:frm.dataNotifica,msg:"Inserisci un formato Data valido (GIORNO/MESE/ANNO)"})
	}
	 if(eliminaSpazi(frm.destinatario.value)==""){
	 listError.push({obj:frm.destinatario,msg:"Campo Obbligatorio"})
	}
	 if(eliminaSpazi(frm.numeroProtocollo.value)=="")
		listError.push({obj:frm.numeroProtocollo,msg:"Campo Obbligatorio"})
	 if	(eliminaSpazi(frm.parteNotificante.value)=="")
		listError.push({obj:frm.parteNotificante,msg:"Campo Obbligatorio"})
	 if	(eliminaSpazi(frm.idSocieta.value)=="")	
		listError.push({obj:frm.idSocieta,msg:"Campo Obbligatorio"})
	if(listError.length>0){
		var msgText="";
		for(i=0;i<listError.length;i++){
		listError[i].obj.style.background="#ffc3bf";
		msgText+=listError[i].msg+"<br>";
		htmElement.push(listError[i].obj)
		}
		$(".box-error").css("display","block").html(msgText);
		$(htmElement).each(function(){
			$(this).bind("focus click",function(){
				$(this).css("background","#fff");
				$(".box-error").css("display","none").html("");
			})
			
		})
		return false;
		}
	 return true;
}


function eliminaSpazi(txt){
	return txt.split(" ").join("");
	}


function numerico(obj){
	$(obj).val(($(obj).val()).replace(/[^0-9]?/gi, ""));
	}
	
	
function validaData(obj){
		(obj).replace(/[^0-9\/]?/gi, "");
		var ary = (obj).split("/");
		if(ary.length==3){
		if((ary[0].length==2 && !isNaN(ary[0])) && (ary[1].length==2 && !isNaN(ary[1])) && (ary[2].length==4 && !isNaN(ary[2]))){ 
		}else{
		return false;
		}
		}else{
		return false;
		}
		return true; 
	}





function initTabellaRicercaAtto() {

	var $table = $('#data-table-atto').bootstrapTable(
			{
				method : 'GET',
				url : WEBAPP_BASE_URL + 'atto/cerca.action',
				cache : false,
				striped : true,
				pagination : true,
				pageSize : 15,
				search : false,
				onLoadSuccess : caricaAzioniAtto,
				sidePagination : 'server',
				showRefresh : false,
				clickToSelect : true,
		
				columns : [ {
					field : 'numeroProtocollo',
					title : 'NUMERO ATTO',
					align : 'left',
					valign : 'top',
					sortable : true

				}, {
					field : 'societa',
					title : 'SOCIETA\'/DIVISIONE INTERESSATA',
					align : 'center',
					valign : 'top',
					sortable : true

				}, {
					field : 'categoriaAtto',
					title : 'CATEGORIA ATTO',
					align : 'center',
					valign : 'top',
					sortable : false
				}, {
					field : 'owner',
					title : 'OWNER',
					align : 'center',
					valign : 'top',
					sortable : false
				}, {
					field : 'unitaLegale',
					title : 'UNITA LEGALE',
					align : 'left',
					valign : 'top',
					sortable : false

				}, {
					field : 'statoAtto',
					title : 'STATO',
					align : 'left',
					valign : 'top',
					class: 'bootstap-table-column-150w',
					sortable : false
				}, {
					field : 'azioni',
					title : '',
					align : 'left',
					valign : 'top',
					sortable : false
				} ]
			});

	$('#btnApplicaFiltri').click(function() {
		//$table.bootstrapTable('refresh');
	});

}

function registraAtto(id){
	waitingDialog.show('Loading...');
	$.post( "./registaAtto.action", { idAtto: id,CSRFToken:legalSecurity.getToken()})
	  .done(function( data ) {
		  $('#data-table-atto').bootstrapTable('refresh');
		  waitingDialog.hide();
	  }).fail(function( jqxhr, textStatus, error ) {
		    var err = textStatus + ", " + error;
		    waitingDialog.hide();
		});
	
}

function riportaInBozza(id){
	waitingDialog.show('Loading...');
	$.post( "./riportaBozza.action", { idAtto: id,CSRFToken:legalSecurity.getToken()})
	  .done(function( data ) {
		  $('#data-table-atto').bootstrapTable('refresh');
		  waitingDialog.hide();
	  }).fail(function( jqxhr, textStatus, error ) {
		    var err = textStatus + ", " + error;
		    waitingDialog.hide();
		});
	
}


function discardWorkLow(id){
	waitingDialog.show('Loading...');
	$.post( "./discardStep.action", { idAtto: id,CSRFToken:legalSecurity.getToken()})
	  .done(function( data ) {
		  $('#data-table-atto').bootstrapTable('refresh');
		  waitingDialog.hide();
	  }).fail(function( jqxhr, textStatus, error ) {
		    var err = textStatus + ", " + error;
		    waitingDialog.hide();
		});
	
}

function assegnaFascicoloAtto(fascicolo){
	var attos=$("#codiceAttoId").val();
	if(attos){
		waitingDialog.show('Loading...');
	$.post( "./assegnaFascicoloAtto.action", { idAtto:attos, idFascicolo:fascicolo,CSRFToken:legalSecurity.getToken() })
	  .done(function( data ) {
		  $("#modal-assegna-fascicolo").modal("hide");
		  waitingDialog.hide();
	  }).fail(function( jqxhr, textStatus, error ) {
		    var err = textStatus + ", " + error;
		    waitingDialog.hide();
		});
}
}	


function openModalListFascicoli(idatto){
	$("#codiceAttoId").val(idatto);
	$("#modal-assegna-fascicolo").modal("show");
	
}





function initTabellaRicercaFascicolo() {
	
	 
	$('#modalRicercaRiassegnaFascicolo').modal("hide");
	
	var filtraRicerca="societa=0&settoreGiuridico=0&tipologiaFascicolo=0&nomeFascicolo=0&legaleInterno=0";
	//NUMERO FASCICOLO, STATO, OWNER 
	$('#riassegna-fascicolo').bootgrid({
		ajax: true,
		ajaxSettings: {
		method: "GET",
		cache: false
		},
		selection: true,
		multiSelect: true,
		rowSelect: true,
		keepSelection: true,
		url: WEBAPP_BASE_URL +'riassegna/ricercaAssegnaFascicoli.action?'+filtraRicerca,
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
 
		});
 

}
