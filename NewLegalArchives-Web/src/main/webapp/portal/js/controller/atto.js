/**
 * 
 */
$(document).ready(function(){
if($('#table-assegna-fascicolo')[0]) { 
		$("#table-assegna-fascicolo").bootgrid({
                    css: {
                        icon: 'zmdi icon',
                        iconColumns: 'zmdi-view-module',
                        iconDown: 'zmdi-expand-more',
                        iconRefresh: 'zmdi-refresh',
                        iconUp: 'zmdi-expand-less'
                    },
                    selection: true,
                    multiSelect: true,
                    rowSelect: true,
                    keepSelection: true,
					formatters: {
					"commands": function(column, row)
					{			
						return "<button type=\"button\" onclick=\"assegnaFascicoloAtto(" + row.id + ")\" class=\"btn btn-xs btn-default command-edit\" data-row-id=\"" + row.id + "\"><span class=\"fa fa-eye\"></span>Assegna Fascicolo</button> "; 
					  
					}
                     
				} 
                });
	}

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
	
	$("#download-pecAttachAtto").on("click",function(){
		var uuid=$(this).attr("uuid");
		return fnDownloadAttachAttoPec(uuid);
	})
	
	
	$("#btn-richiedi-registrazione-atto").click(function(){
	var id=$(this).attr("isatto");
     
	if(id && id!=""){
	
	return registraAtto(id);
	 	
	 }else{   $(this).attr("disabled",true); alert("You can not perform the requested operation!") }
	
	})
	 
	
	//$("input[name=id]").click();

	 var $table = $('#table-assegna-fascicolo');
    $(function () {
        $('#modal-assegna-fascicolo').on('shown.bs.modal', function () {
           // $table.bootstrapTable('resetView');
        });
    });
	
});


function openAtto(id,azione){
	$("#id").val(id)
	$("#azione").val(azione)
	$("#openAtto").submit();

	}	


function fnDownloadAtto(uuid){
	$("#downloadAtto #uuid").val(uuid);
	$("#downloadAtto").submit();
	}

function fnDownloadAttachAttoPec(uuid){
	$("#downloadPecAttachAtto #uuid").val(uuid);
	$("#downloadPecAttachAtto").submit();
}

var localMessage={
	IT:{
	validaData:'Inserisci un formato Data valido (GIORNO/MESE/ANNO)',
	societa:'SOCIETA *Campo Obbligatorio',
	parteNotificante:'PARTE NOTIFICANTE *Campo Obbligatorio',
	categoria:'CATEGORIA ATTO *Campo Obbligatorio',
	destinatario:'DESTINATARIO *Campo Obbligatorio',
	tipoAtto:'TIPO ATTO GIUDIZIARIO *Campo Obbligatorio',
	foroCompetente:'FORO COMPETENTE *Campo Obbligatorio',
	dataNotifica:'DATA DELLA NOTIFICA *Campo Obbligatorio',
	rilevante:'RILEVANZA *Campo Obbligatorio',
	idEsito:'ESITO *Campo Obbligatorio',
	pagamentoDovuto:'PAGAMENTO DOVUTO DA SNAM *Campo Obbligatorio',
	speseCarico:'SPESE DI LITE A CARICO DI SNAM *Campo Obbligatorio',
	speseFavore:'SPESE DI LITE IN FAVORE DI SNAM *Campo Obbligatorio'
	},
	EN:{
	validaData:'Please enter a valid date format (DAY / MONTH / YEAR)',
	societa:'COMPANY *Required field',
	parteNotificante:'THE NOTIFYING *Required field',
	categoria:'CATEGORY ACT *Required field',
	destinatario:'RECIPIENT *Required field',
	tipoAtto:'TYPE ACT JUDICIAL *Required field',
	foroCompetente: 'COMPETENT COURT *Required field',
	dataNotifica:'DATE OF NOTIFICATION *Required field',
	rilevante:'RELEVANCE *Required field',
	idEsito:'RESULT *Required field',
	pagamentoDovuto:'PAYMENT DUE FROM SNAM *Required field',
	speseCarico:'AGAINST SNAM LAWSUITE EXPENSES *Required field',
	speseFavore:'FAVOUR SNAM LAWSUITE EXPENSES *Required field'
	}
 }



var a = navigator.browserLanguage;
var b = navigator.userLanguage;
var c = navigator.systemLanguage;
var d= navigator.language;

var langs=a||b||c||d;
langs=(langs!=null && langs!='undefined')?langs.split("-")[0].toUpperCase():"IT";
 

var listError=[];
var htmElement=[];


function validaForm(frm){
    listError=[];
	var htmElement=[];

	 if(eliminaSpazi(frm.dataNotifica.value)==""){
		listError.push({obj:frm.dataNotifica,msg:localMessage[langs].dataNotifica})
	}
	if(eliminaSpazi(frm.dataNotifica.value)!="" && !validaData(frm.dataNotifica.value)){
		listError.push({obj:frm.dataNotifica,msg:localMessage[langs].validaData})
	}
	if(eliminaSpazi(frm.foroCompetente.value)==""){
		listError.push({obj:frm.foroCompetente,msg:localMessage[langs].foroCompetente})
	}
	if(eliminaSpazi(frm.idCategoriaAtto.value)==""){
		listError.push({obj:frm.idCategoriaAtto,msg:localMessage[langs].categoria})
	}
	if(eliminaSpazi(frm.idCategoriaAtto[frm.idCategoriaAtto.selectedIndex].id)=="TPAT_3"){
		if(eliminaSpazi(frm.idEsito.value)=="")
			listError.push({obj:frm.idEsito,msg:localMessage[langs].idEsito});
		if(eliminaSpazi(frm.pagamentoDovuto.value)=="")
			listError.push({obj:frm.pagamentoDovuto,msg:localMessage[langs].pagamentoDovuto});
		if(eliminaSpazi(frm.speseCarico.value)=="")
			listError.push({obj:frm.speseCarico,msg:localMessage[langs].speseCarico});
		if(eliminaSpazi(frm.speseFavore.value)=="")
			listError.push({obj:frm.speseFavore,msg:localMessage[langs].speseFavore});
	}
	if(eliminaSpazi(frm.tipoAtto.value)==""){
		listError.push({obj:frm.tipoAtto,msg:localMessage[langs].tipoAtto})
	}
	if(eliminaSpazi(frm.rilevante.value)==""){
		listError.push({obj:frm.rilevante,msg:localMessage[langs].rilevante})
	}
	 if(eliminaSpazi(frm.destinatario.value)==""){
	 listError.push({obj:frm.destinatario,msg:localMessage[langs].destinatario})
	}
	 if	(eliminaSpazi(frm.parteNotificante.value)=="")
		listError.push({obj:frm.parteNotificante,msg:localMessage[langs].parteNotificante})
	 if	(eliminaSpazi(frm.idSocieta.value)=="")	
		listError.push({obj:frm.idSocieta,msg:localMessage[langs].societa})
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


function caricaAzioniSuAtto(id,statoCodice) {
	var containerAzioni = document.getElementById("action-atto-" + id);
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {

		containerAzioni.innerHTML = data;

	};

	var url = WEBAPP_BASE_URL
			+ "atto/caricaAzioniAtto.action?idAtto=" + id +"&statoCodice="+statoCodice;
	
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);

}

function caricaAzioniAtto(data) {
	if (data != null && data.rows != null) {
		var rows = data.rows;
		for (i = 0; i < rows.length; i++) {
			var row = rows[i];
			var id = row.id;
			var statoCodice=row.statoCodice;
			caricaAzioniSuAtto(id,statoCodice);
		}

	}
}
/**
 * Metodo di supporto per la creazione della tabella di visualizzazione
 * degli atti da validare
 * @author MASSIMO CARUSO
 */
function initTabellaValidaAtto() {

	var $table = $('#data-table-atto').bootstrapTable(
			{
				method : 'GET',
				url : WEBAPP_BASE_URL + 'atto/cerca.action?idSocieta=294&valida=true',
				cache : false,
				striped : true,
				pagination : true,
				pageSize : 15,
				search : false,
				onLoadSuccess : caricaAzioniAtto,
				sidePagination : 'server',
				showRefresh : false,
				clickToSelect : true,
				sortOrder:'desc',
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
					sortable : false

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

				},{
					field : 'tipoAtto',
					title : 'TIPO ATTO',
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
				sortOrder:'desc',
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
					sortable : false

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

				},{
					field : 'tipoAtto',
					title : 'TIPO ATTO',
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

/*motodo vecchio
function registraAtto(id,assegnPre){
	waitingDialog.show('Loading...');
	$.post( "./registaAtto.action", { idAtto: id,assegnPresente:assegnPre ||'0'})
	  .done(function( data ) {
		  $('#data-table-atto').bootstrapTable('refresh');
		  waitingDialog.hide();
	  }).fail(function( jqxhr, textStatus, error ) {
		    var err = textStatus + ", " + error;
		    waitingDialog.hide();
		});
	
}
*/
function registraAtto(id){
$("#modal-registra-atto").remove();
	
	waitingDialog.show('Loading...');
	 
	$.post( "./registaAttoControlloUtenteIsPresente.action", { idAtto: id ,CSRFToken:legalSecurity.getToken()})
	  .done(function( data ) {
		  waitingDialog.hide();
		  if(data && data.length>5){
		  $(data).modal();
		 }
	  }).fail(function( jqxhr, textStatus, error ) {
		    var err = textStatus + ", " + error;
		    waitingDialog.hide();
		});
	
	
}

/**
 * Metodo di supporto per richiamare il servizio rest di registrazione 
 * dell'atto validato
 * @author MASSIMO CARUSO
 * @param id l'id dell'atto validato
 */
function registraAttoDaValidare(id){
	$("#modal-registra-atto").remove();
		
		waitingDialog.show('Loading...');
		 
		$.post( "./registaAtto.action", { idAtto: id,CSRFToken:legalSecurity.getToken()})
		  .done(function( data ) {
			//arrivo dal menu
			if($('#data-table-atto').length){
				  $('#data-table-atto').bootstrapTable('refresh');
		  	}else{
				  location.href=legalSecurity.verifyToken(WEBAPP_BASE_URL + 'atto/valida.action'); /*arrivo da dettaglio*/
			}
			waitingDialog.hide();
		  }).fail(function( jqxhr, textStatus, error ) {
			    var err = textStatus + ", " + error;
			    waitingDialog.hide();
			});	
	}

function registraAttoControlloUtenteIsPresente(id,assegnPre){
	
	if($("#modal-registra-atto").length) 
	 $("#modal-registra-atto").modal('hide');
	
	waitingDialog.show('Loading...');
	$.post( "./registaAtto.action", { idAtto: id,assegnPresente:assegnPre ||'0',CSRFToken:legalSecurity.getToken()})
	  .done(function( data ) {
		  //arrivo dal menu
		  if($('#data-table-atto').length)
		  $('#data-table-atto').bootstrapTable('refresh');
		  else
		 location.href=legalSecurity.verifyToken(WEBAPP_BASE_URL + 'atto/ricerca.action'); /*arrivo da dettaglio*/ 
		  waitingDialog.hide();
	  }).fail(function( jqxhr, textStatus, error ) {
		    var err = textStatus + ", " + error;
		    waitingDialog.hide();
		});
	
}

function registraAttoAssPresent(id){
	
	var matricolaUtil= $("#assegnPresente").val();
	if(matricolaUtil!="")
	return registraAttoControlloUtenteIsPresente(id,matricolaUtil);	
	
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
	$("#modal-assegna-fascicolo").modal("hide");
		waitingDialog.show('Loading...');
	$.post( "./assegnaFascicoloAtto.action", { idAtto:attos, idFascicolo:fascicolo ,CSRFToken:legalSecurity.getToken()})
	  .done(function( data ) {
		  waitingDialog.hide();
	$('#data-table-atto').bootstrapTable('refresh');		  
		 
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





function initFiltraRicercaAtto() {
	
		var idCategoriaAtto = encodeURIComponent(document
							.getElementById("idCategoriaAtto").value || "0");
					var numeroProtocollo = encodeURIComponent(document
							.getElementById("numeroProtocollo").value || "0");
					var idSocieta = encodeURIComponent(document
							.getElementById("idSocieta").value || "0");
					var tipoAtto = encodeURIComponent(document
							.getElementById("tipoAtto").value || "0");
					var dataDal = encodeURIComponent(document
							.getElementById("dataDal").value || "0");
					var dataAl = encodeURIComponent(document
							.getElementById("dataAl").value || "0");
					var flagAltriUffici = encodeURIComponent(document
							.getElementById("altriUffici").value || "false");
							
	$('#modalRicercaAtto').modal("hide");
	
	var filtraRicerca="idCategoriaAtto="+idCategoriaAtto+"&numeroProtocollo="+numeroProtocollo+"&idSocieta="+idSocieta+"&tipoAtto="+tipoAtto+"&dataDal="+dataDal+"&dataAl="+dataAl+"&flagAltriUffici="+flagAltriUffici;
	
	$('#data-table-atto').bootstrapTable('refresh',
			{
				method : 'GET',
				url : WEBAPP_BASE_URL + 'atto/cerca.action?'+filtraRicerca,
				cache : false,
				striped : true,
				pagination : true,
				pageSize : 100,
				search : false,
				onLoadSuccess : caricaAzioniAtto,
				sidePagination : 'server',
				showRefresh : false,
				clickToSelect : true,
				sortOrder:'desc',
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
					sortable : false

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

				},{
					field : 'tipoAtto',
					title : 'TIPO ATTO',
					align : 'left',
					valign : 'top',
					sortable : false

				} ,{
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

}



function downloadListaAtti(){
	
	var idCategoriaAtto = encodeURIComponent(document
			.getElementById("idCategoriaAtto").value || "0");
	var numeroProtocollo = encodeURIComponent(document
			.getElementById("numeroProtocollo").value || "0");
	var idSocieta = encodeURIComponent(document
			.getElementById("idSocieta").value || "0");
	var tipoAtto = encodeURIComponent(document
			.getElementById("tipoAtto").value || "0");
	var dataDal = encodeURIComponent(document
			.getElementById("dataDal").value || "0");
	var dataAl = encodeURIComponent(document
			.getElementById("dataAl").value || "0"); 
	var flagAltriUffici = encodeURIComponent(document
			.getElementById("altriUffici").value || "false");
	var d = new Date();
	location.href=legalSecurity.verifyToken(WEBAPP_BASE_URL + 'atto/ricerca.action?export=1&idCategoriaAtto='+idCategoriaAtto+'&numeroProtocollo='+numeroProtocollo+'&idSocieta='+idSocieta+'&tipoAtto='+tipoAtto+'&dataDal='+dataDal+'&dataAl='+dataAl+"&flagAltriUffici="+flagAltriUffici+'&_d='+d.getTime());
	
}

/** 
 * routine di supporto per il download della lista di atti da validare 
 * @author MASSIMO CARUSO
 */
function downloadListaAttiDaValidare(){
	
	var idCategoriaAtto = encodeURIComponent(document
			.getElementById("idCategoriaAtto").value || "0");
	var numeroProtocollo = encodeURIComponent(document
			.getElementById("numeroProtocollo").value || "0");
	var idSocieta = encodeURIComponent(document
			.getElementById("idSocieta").value || "0");
	var tipoAtto = encodeURIComponent(document
			.getElementById("tipoAtto").value || "0");
	var dataDal = encodeURIComponent(document
			.getElementById("dataDal").value || "0");
	var dataAl = encodeURIComponent(document
			.getElementById("dataAl").value || "0"); 
	var flagAltriUffici = encodeURIComponent(document
			.getElementById("altriUffici").value || "false");
	var d = new Date();
	location.href=legalSecurity.verifyToken(WEBAPP_BASE_URL + 'atto/ricerca.action?export=1&idCategoriaAtto='+idCategoriaAtto+'&numeroProtocollo='+numeroProtocollo+'&idSocieta='+idSocieta+'&tipoAtto='+tipoAtto+'&dataDal='+dataDal+'&dataAl='+dataAl+"&flagAltriUffici="+flagAltriUffici+'&_d='+d.getTime()+"&valida=true");
	
}

$(document).on('change','#idCategoriaAtto',function(){
	
	console.log($(this).children(":selected").attr("id"));
	
	if($(this).children(":selected").attr("id")=='TPAT_3'){
		$('#formEsito').css("display", "block");
		$('#formPagamentoDovuto').css("display", "block");
		$('#speseFavore').css("display", "block");
		$('#speseCarico').css("display", "block");
	}
	else{
		$('#formEsito').css("display", "none");
		$('#formPagamentoDovuto').css("display", "none");
		$('#speseFavore').css("display", "none");
		$('#speseCarico').css("display", "none");
	}
});
