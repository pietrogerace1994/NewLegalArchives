var agenda_checkedRows = [];
var incarico_checkedRows = [];

$(document).ready(function () {
	loadListaTipoScadenza();
	initCalendar();
	
	$('#agendaPanelCercaSelezionaPadreFascicolo').on('show.bs.modal', function(e) {
	    var div = document.getElementById("containerRicercaModaleFascicoloPadre");
	    div.innerHTML="";
	    var fnCallbackSuccess = function(data){
	    	
	    	div.innerHTML=data;
	    	agenda_initTabellaRicercaFascicoliModale(false);
	    };
	    var ajaxUtil = new AjaxUtil();
	    var url = WEBAPP_BASE_URL + "fascicolo/cercaModale.action?multiple=agenda";
	    url=legalSecurity.verifyToken(url); 
	    ajaxUtil.ajax(url, null, "GET", "text/html", fnCallbackSuccess, null, null)
	});
	
	$("#btnAgendaScegliFascicolo").click(function(event){
		
		if(!$("#btnAgendaScegliFascicolo").hasClass("btn btn-success")){
			
			if($("#agendaFascicoliNome").val() == ""){
				
				$('#agendaPanelCercaSelezionaPadreFascicolo').modal('show');
			}
			else{
				agenda_ricercaFascicoloPerNome($("#agendaFascicoliNome").val());
			}
		}
	});
	
	$('#incaricoPanelCercaSelezionaPadreFascicolo').on('show.bs.modal', function(e) {
	    var div = document.getElementById("containerRicercaModaleIncaricoFascicoloPadre");
	    div.innerHTML="";
	    var fnCallbackSuccess = function(data){
	    	
	    	div.innerHTML=data;
	    	incarico_initTabellaRicercaFascicoliModale(false);
	    };
	    var ajaxUtil = new AjaxUtil();
	    var url = WEBAPP_BASE_URL + "fascicolo/cercaModale.action?multiple=incarico";
	    url=legalSecurity.verifyToken(url);
	    ajaxUtil.ajax(url, null, "GET", "text/html", fnCallbackSuccess, null, null)
	});
	
	$("#btnIncaricoScegliFascicolo").click(function(event){	
		
		if(!$("#btnIncaricoScegliFascicolo").hasClass("btn btn-success")){
			
			if($("#incaricoFascicoliNome").val() == ""){
				
				$('#incaricoPanelCercaSelezionaPadreFascicolo').modal('show');
			}
			else{
				incarico_ricercaFascicoloPerNome($("#incaricoFascicoliNome").val());
			}
		}
	});
	
	$("#agendaFascicoliNome").keyup(function(event) {
		
		if(event.which == 8){
			
			if($("#btnAgendaScegliFascicolo").hasClass("btn btn-success")){

				$('#btnAgendaScegliFascicolo').removeClass();
				$('#btnAgendaScegliFascicolo').addClass("btn btn-primary waves-effect");
				$('#btnAgendaScegliFascicoloIcon').removeClass();
				$('#btnAgendaScegliFascicoloIcon').addClass("fa fa-search");
			}
			else{
				if($('#btnAgendaScegliFascicoloIcon').hasClass("fa fa-spinner fa-pulse")){
					$('#btnAgendaScegliFascicoloIcon').removeClass();
					$('#btnAgendaScegliFascicoloIcon').addClass("fa fa-search");
				}
			}
		}else{
			if(event.which == 13){
				if(!$("#btnAgendaScegliFascicolo").hasClass("btn btn-success")){
					
					if($("#agendaFascicoliNome").val() == ""){
						
						$('#agendaPanelCercaSelezionaPadreFascicolo').modal('show');
					}
					else{
						agenda_ricercaFascicoloPerNome($("#agendaFascicoliNome").val());
					}
				}
			}
		}
	});
	
	$("#incaricoFascicoliNome").keyup(function(event) {
		
		if(event.which == 8){
			
			if($("#btnIncaricoScegliFascicolo").hasClass("btn btn-success")){

				$('#btnIncaricoScegliFascicolo').removeClass();
				$('#btnIncaricoScegliFascicolo').addClass("btn btn-primary waves-effect");
				$('#btnIncaricoScegliFascicoloIcon').removeClass();
				$('#btnIncaricoScegliFascicoloIcon').addClass("fa fa-search");
			}
			else{
				if($('#btnIncaricoScegliFascicoloIcon').hasClass("fa fa-spinner fa-pulse")){
					$('#btnIncaricoScegliFascicoloIcon').removeClass();
					$('#btnIncaricoScegliFascicoloIcon').addClass("fa fa-search");
				}
			}
		}else{
			if(event.which == 13){
				if(!$("#btnIncaricoScegliFascicolo").hasClass("btn btn-success")){
					
					if($("#incaricoFascicoliNome").val() == ""){
						
						$('#incaricoPanelCercaSelezionaPadreFascicolo').modal('show');
					}
					else{
						incarico_ricercaFascicoloPerNome($("#incaricoFascicoliNome").val());
					}
				}
			}
		}
	});
	
	
});	

function agenda_selezionaFascicoloChiudi() {
	agenda_checkedRows=new Array();
	$('#agendaPanelCercaSelezionaPadreFascicolo').modal('hide');
	if($('#btnAgendaScegliFascicoloIcon').hasClass("fa fa-spinner fa-pulse")){
		$('#btnAgendaScegliFascicoloIcon').removeClass();
		$('#btnAgendaScegliFascicoloIcon').addClass("fa fa-search");
	}
}

function agenda_selezionaFascicolo() {
	if( agenda_checkedRows.length>0 ){  
		$("#agendaFascicoliNome").val(agenda_checkedRows[0].nome);
		$("#agendaFascicoliId").val(agenda_checkedRows[0].id);	
		agenda_checkedRows=new Array();
		$('#agendaPanelCercaSelezionaPadreFascicolo').modal('hide');
		$('#btnAgendaScegliFascicolo').removeClass();
		$('#btnAgendaScegliFascicolo').addClass("btn btn-success");
		$('#btnAgendaScegliFascicoloIcon').removeClass();
		$('#btnAgendaScegliFascicoloIcon').addClass("fa fa-check");
	}	
}

function agenda_selezionaFascicolo2(id, nome) {
	$("#agendaFascicoliNome").val(nome);
	$("#agendaFascicoliId").val(id);	
	$('#agendaPanelCercaSelezionaPadreFascicolo').modal('hide');
	$('#btnAgendaScegliFascicolo').removeClass();
	$('#btnAgendaScegliFascicolo').addClass("btn btn-success");
	$('#btnAgendaScegliFascicoloIcon').removeClass();
	$('#btnAgendaScegliFascicoloIcon').addClass("fa fa-check");
}

function agenda_initTabellaRicercaFascicoliModale(isMultiple) {

	var $table = $('#tabellaRicercaFascicoli').bootstrapTable(
			{
				method : 'GET',
				url : WEBAPP_BASE_URL + 'fascicolo/ricerca2.action',
				cache : false,
				striped : true,
				pagination : true,
				pageSize : 10,
				search : false,
				sidePagination : 'server',
				paginationVAlign: 'top',
				showRefresh : false,
				clickToSelect : true,
				queryParams : function(params) {
					if(isMultiple && document.getElementsByName("btSelectAll") ){
						document.getElementsByName("btSelectAll")[0].style.display="none";
					}
					
					var nome = "";
					if(document.getElementById("txtNomeModal").value != ""){
						nome = encodeURIComponent(document.getElementById("txtNomeModal").value);
					}else{
						nome = encodeURIComponent($("#agendaFascicoliNome").val());
					}
					var oggetto = encodeURIComponent(document
							.getElementById("txtOggettoModal").value);
					var legaleEsterno = encodeURIComponent(document
							.getElementById("txtLegaleEsternoModal").value);
					var controparte = encodeURIComponent(document
							.getElementById("txtControparteModal").value);
					var dal = encodeURIComponent(document
							.getElementById("txtDataDalModal").value);
					var al = encodeURIComponent(document
							.getElementById("txtDataAlModal").value);
					var tipologiaFascicoloCode = encodeURIComponent(document
							.getElementById("tipologiaFascicoloCodeModal").value);
					var settoreGiuridicoCode = encodeURIComponent(document
							.getElementById("settoreGiuridicoCodeModal").value);
					params.nome = nome;
					params.oggetto = oggetto;
					params.legaleEsterno = legaleEsterno;
					params.controparte = controparte;
					params.settoreGiuridicoCode = settoreGiuridicoCode;
					params.tipologiaFascicoloCode = tipologiaFascicoloCode;
					params.dal = dal;
					params.al = al;
					return params;
				},
				columns : 
				[ {
					checkbox: isMultiple, 
					radio: !isMultiple,
					title : '',
					align : 'center',
					valign : 'top',
					sortable : true

				}, {
					field : 'nome',
					title : 'NUMERO FASCICOLO',
					align : 'left',
					valign : 'top',
					sortable : true

				},
				{
					field : 'owner',
					title : 'owner',
					align : 'left',
					valign : 'top',
					sortable : true
				},
				{
					field : 'dataCreazione',
					title : 'DATA REG.',
					align : 'left',
					valign : 'top',
					sortable : true
				}, {
					field : 'stato',
					title : 'STATO FASCICOLO',
					align : 'left',
					valign : 'top',
					sortable : true

				}, {
					field : 'legaleEsterno',
					title : 'LEGALE ESTERNO INCAR.',
					align : 'center',
					valign : 'top',
					sortable : true

				}, {
					field : 'controparte',
					title : 'CONTROPARTE',
					align : 'left',
					valign : 'top',
					sortable : false
				}, {
					field : 'anno',
					title : 'ANNO',
					align : 'center',
					valign : 'top',
					sortable : false
				},  {
					field : 'oggetto',
					title : 'OGGETTO',
					align : 'left',
					valign : 'top',
					class: 'bootstap-table-column-150w',
					sortable : false
				} ]
			});

			$('#tabellaRicercaFascicoli').on('check.bs.table', function (e, row) {
			  if( isMultiple ) {
				  agenda_checkedRows.push({id: row.id, nome:row.nome });
			  }else{
				  agenda_checkedRows = [{id: row.id, nome:row.nome}]; 
			  }
			  console.log(agenda_checkedRows);
			  
			  $("#btnAggiungiFascicoloPadreAgenda").css('display','block');
			  $("#btnAggiungiFascicoloPadreAgenda").css('visibility','visible');
			  
			
			});
	$('#btnApplicaFiltri').click(function() {
		$table.bootstrapTable('refresh');
	});
}	
	
	
function incarico_selezionaFascicoloChiudi() {
	incarico_checkedRows=new Array();
	$('#incaricoPanelCercaSelezionaPadreFascicolo').modal('hide');
	if($('#btnIncaricoScegliFascicoloIcon').hasClass("fa fa-spinner fa-pulse")){
		$('#btnIncaricoScegliFascicoloIcon').removeClass();
		$('#btnIncaricoScegliFascicoloIcon').addClass("fa fa-search");
	}
}

function incarico_selezionaFascicolo() {
	$("#incaricoFascicoliNome").val(incarico_checkedRows[0].nome);
	$("#incaricoFascicoliId").val(incarico_checkedRows[0].id);	
	$('#incaricoPanelCercaSelezionaPadreFascicolo').modal('hide');
	$('#btnIncaricoScegliFascicolo').removeClass();
	$('#btnIncaricoScegliFascicolo').addClass("btn btn-success");
	$('#btnIncaricoScegliFascicoloIcon').removeClass();
	$('#btnIncaricoScegliFascicoloIcon').addClass("fa fa-check");
}

function incarico_selezionaFascicolo2(id, nome) {
	$("#incaricoFascicoliNome").val(nome);
	$("#incaricoFascicoliId").val(id);	
	$('#incaricoPanelCercaSelezionaPadreFascicolo').modal('hide');
	$('#btnIncaricoScegliFascicolo').removeClass();
	$('#btnIncaricoScegliFascicolo').addClass("btn btn-success");
	$('#btnIncaricoScegliFascicoloIcon').removeClass();
	$('#btnIncaricoScegliFascicoloIcon').addClass("fa fa-check");
}

function incarico_initTabellaRicercaFascicoliModale(isMultiple) {

	var $table = $('#tabellaRicercaFascicoliDaIncarico').bootstrapTable(
			{
				method : 'GET',
				url : WEBAPP_BASE_URL + 'fascicolo/ricerca2.action',
				cache : false,
				striped : true,
				pagination : true,
				pageSize : 10,
				search : false,
				sidePagination : 'server',
				paginationVAlign: 'top',
				showRefresh : false,
				clickToSelect : true,
				queryParams : function(params) {
					if(isMultiple && document.getElementsByName("btSelectAll") ){
						document.getElementsByName("btSelectAll")[0].style.display="none";
					}

					var nome = "";
					if(document.getElementById("txtNomeModalIncarico").value != ""){
						nome = encodeURIComponent(document.getElementById("txtNomeModalIncarico").value);
					}else{
						nome = encodeURIComponent($("#incaricoFascicoliNome").val());
					}
					var oggetto = encodeURIComponent(document
							.getElementById("txtOggettoModalIncarico").value);
					var legaleEsterno = encodeURIComponent(document
							.getElementById("txtLegaleEsternoModalIncarico").value);
					var controparte = encodeURIComponent(document
							.getElementById("txtControparteModalIncarico").value);
					var dal = encodeURIComponent(document
							.getElementById("txtDataDalModalIncarico").value);
					var al = encodeURIComponent(document
							.getElementById("txtDataAlModalIncarico").value);
					var tipologiaFascicoloCode = encodeURIComponent(document
							.getElementById("tipologiaFascicoloCodeModalIncarico").value);
					var settoreGiuridicoCode = encodeURIComponent(document
							.getElementById("settoreGiuridicoCodeModalIncarico").value);
					params.nome = nome;
					params.oggetto = oggetto;
					params.legaleEsterno = legaleEsterno;
					params.controparte = controparte;
					params.settoreGiuridicoCode = settoreGiuridicoCode;
					params.tipologiaFascicoloCode = tipologiaFascicoloCode;
					params.dal = dal;
					params.al = al;
					return params;
				},
				columns : 
				[ {
					checkbox: isMultiple, 
					radio: !isMultiple,
					title : '',
					align : 'center',
					valign : 'top',
					sortable : true

				}, {
					field : 'nome',
					title : 'NUMERO FASCICOLO',
					align : 'left',
					valign : 'top',
					sortable : true

				},
				{
					field : 'owner',
					title : 'owner',
					align : 'left',
					valign : 'top',
					sortable : true
				},
				{
					field : 'dataCreazione',
					title : 'DATA REG.',
					align : 'left',
					valign : 'top',
					sortable : true
				}, {
					field : 'stato',
					title : 'STATO FASCICOLO',
					align : 'left',
					valign : 'top',
					sortable : true

				}, {
					field : 'legaleEsterno',
					title : 'LEGALE ESTERNO INCAR.',
					align : 'center',
					valign : 'top',
					sortable : true

				}, {
					field : 'controparte',
					title : 'CONTROPARTE',
					align : 'left',
					valign : 'top',
					sortable : false
				}, {
					field : 'anno',
					title : 'ANNO',
					align : 'center',
					valign : 'top',
					sortable : false
				},  {
					field : 'oggetto',
					title : 'OGGETTO',
					align : 'left',
					valign : 'top',
					class: 'bootstap-table-column-150w',
					sortable : false
				} ]
			});

			$('#tabellaRicercaFascicoliDaIncarico').on('check.bs.table', function (e, row) {
			  if( isMultiple ) {
				  incarico_checkedRows.push({id: row.id, nome:row.nome });
			  }else{
				  incarico_checkedRows = [{id: row.id, nome:row.nome}]; 
			  }
			  console.log(agenda_checkedRows);
			  
			  $("#btnSelezionaFascicoloPadreAgenda").css('display','block');
			  $("#btnSelezionaFascicoloPadreAgenda").css('visibility','visible');
			  
			
			});
	$('#btnApplicaFiltriIncarico').click(function() {
		$table.bootstrapTable('refresh');
	});
}

function agenda_ricercaFascicoloPerNome(val){
	
	$('#btnAgendaScegliFascicolo').removeClass();
	$('#btnAgendaScegliFascicolo').addClass("btn btn-primary");
	$('#btnAgendaScegliFascicoloIcon').removeClass();
	$('#btnAgendaScegliFascicoloIcon').addClass("fa fa-spinner fa-pulse");
	
	var fnCallbackSuccess = function(data){
    	
		if (data != null && data.rows != null) {
			var rows = data.rows;
			
			if(rows.length == 1){
				var row = rows[0];
				var id = row.id;
				var nome = row.nome;
				
				agenda_selezionaFascicolo2(id, nome);
			}
			else{
				$('#agendaPanelCercaSelezionaPadreFascicolo').modal('show');
			}
		}
    };
    var ajaxUtil = new AjaxUtil();
    var url = WEBAPP_BASE_URL + "fascicolo/ricerca2.action?nome="+val;
    url=legalSecurity.verifyToken(url);
    ajaxUtil.ajax(url, null, "GET", "application/json", fnCallbackSuccess, null, null)
}

function incarico_ricercaFascicoloPerNome(val){
	
	$('#btnIncaricoScegliFascicolo').removeClass();
	$('#btnIncaricoScegliFascicolo').addClass("btn btn-primary");
	$('#btnIncaricoScegliFascicoloIcon').removeClass();
	$('#btnIncaricoScegliFascicoloIcon').addClass("fa fa-spinner fa-pulse");
	
	var fnCallbackSuccess = function(data){
    	
		if (data != null && data.rows != null) {
			var rows = data.rows;
			
			if(rows.length == 1){
				var row = rows[0];
				var id = row.id;
				var nome = row.nome;
				
				incarico_selezionaFascicolo2(id, nome);
			}
			else{
				$('#incaricoPanelCercaSelezionaPadreFascicolo').modal('show');
			}
		}
    };
    var ajaxUtil = new AjaxUtil();
    var url = WEBAPP_BASE_URL + "fascicolo/ricerca2.action?nome="+val;
    url=legalSecurity.verifyToken(url);
    ajaxUtil.ajax(url, null, "GET", "application/json", fnCallbackSuccess, null, null)
}
