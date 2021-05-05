var trimestreScelto = "";

$(document).ready(function () {
	
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		$("#trimestreBadge").html(data);
		trimestreScelto = data;
		initTabellaRicercaSchedaFondoRischi();
		caricaTrimestriDisponibili();
	};
	var url = WEBAPP_BASE_URL + "schedaFondoRischi/calcolaTrimestreCorrente.action";
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
});

function changeTrimestre(trimestre) {
	$("#trimestreBadge").html(trimestre);
	trimestreScelto = trimestre;
	$('#tabellaRicercaSchedaFondoRischi').bootstrapTable('refresh');
}

function caricaAzioniSchedaFondoRischiColore(data) {
	waitingDialog.hide();
	if (data != null && data.rows != null) {
		var rows = data.rows;
		var buttonToEnable = false;
		for (i = 0; i < rows.length; i++) {
			var row = rows[i];
			var id = row.id;
			var stato = row.stato;
			if(row.stato == 'AUTORIZZATO' || row.stato == 'AUTHORIZED'){
				buttonToEnable = true;
			}
			caricaAzioniSuSchedaFondoRischiColore(id, trimestreScelto);
		}
		if(buttonToEnable){
			$("#generaPFR")[0].disabled=false;
			$("#generaPFRModifiche")[0].disabled=false;
		}
		else{
			$("#generaPFR")[0].disabled=true;
			$("#generaPFRModifiche")[0].disabled=true;
		}
	}
}

function caricaAzioniSuSchedaFondoRischiColore(id, trimestreScelto) {
	var containerAzioni = document.getElementById("containerAzioniRigaScheda" + id);
	containerAzioni.innerHTML="<img src='"+WEBAPP_BASE_URL+"vendors/jquery/loading.gif'>";
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {

		containerAzioni.innerHTML = data;

	};
	var url = WEBAPP_BASE_URL
			+ "schedaFondoRischi/caricaAzioniSchedaFrColore.action?idScheda=" + id + "&trimestre=" + trimestreScelto;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

function initTabellaRicercaSchedaFondoRischi() {

	var $table = $('#tabellaRicercaSchedaFondoRischi').bootstrapTable(
			{
				method : 'GET',
				url : WEBAPP_BASE_URL + 'schedaFondoRischi/ricercaPFR.action?trimestre=' + trimestreScelto,
				cache : false,
				striped : true,
				pagination : true,
				pageSize : 10,
				search : false,
				onLoadSuccess : caricaAzioniSchedaFondoRischiColore,
				sidePagination : 'server',
				showRefresh : false,
				clickToSelect : true,
				sortOrder:'desc',
				queryParams : function(params) {
					params.trimestreSelezionato = trimestreScelto;
					params.dal = null;
					params.al = null;
					return params;
				},
				columns : [ {
					field : 'azioni',
					title : '',
					align : 'left',
					valign : 'top',
					sortable : false
				} ,{
					field : 'nomeFascicolo',
					title : 'FASCICOLO',
					align : 'left',
					valign : 'top',
					sortable : false
					
				},{
					field : 'tipologia',
					title : 'TIPOLOGIA',
					align : 'left',
					valign : 'top',
					sortable : true
				}, {
					field : 'stato',
					title : 'STATO SCHEDA',
					align : 'center',
					valign : 'top',
					sortable : true
					
				}, {
					field : 'controparte',
					title : 'CONTROPARTE',
					align : 'center',
					valign : 'top',
					sortable : true
				}, {
					field : 'rischioSoccombenza',
					title : 'RISCHIO SOCCOMBENZA',
					align : 'center',
					valign : 'top',
					sortable : true
				}, {
					field : 'dataCreazione',
					title : 'DATA CREAZIONE',
					align : 'center',
					valign : 'top',
					sortable : false
				}  ]
			});
	$('#btnApplicaFiltri').click(function() {
		$table.bootstrapTable('refresh');
	});
}

function caricaTrimestriDisponibili() {
	
	$("#trimestriDisponibili").empty();
	$("#trimestriDisponibili").html('<small class="lgi-text">Loading...</small>');

	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {

		$("#trimestriDisponibili").empty();
		 var arrJson = JSON.parse(data);
		
		 for (var i = 0; i < arrJson.length; i++) {
			
			var ele = arrJson[i];
			
			var str = "";
			str = "<li><a href=\"#\" onclick=\"changeTrimestre('"+ele.trimestre+"')\">" +ele.trimestre+ "</a></li>";
			
			$(str).appendTo("#trimestriDisponibili");
		 }
	};
	var url = WEBAPP_BASE_URL + "schedaFondoRischi/caricaTrimestriDisponibili.action";
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
	
}

function generaPFR(){
	console.log("genera PFR per il trimestre: " + trimestreScelto);
	waitingDialog.show('Loading...');
	var urlFase1 = WEBAPP_BASE_URL + "schedaFondoRischi/generaPFR.action?trimestre="+trimestreScelto;
	urlFase1=legalSecurity.verifyToken(urlFase1);
	downloadFilePDF(urlFase1);
	
}

function generaPFRModifiche(){
	console.log("genera PFR modifiche per il trimestre: " + trimestreScelto);
	waitingDialog.show('Loading...');
	var urlFase2 = WEBAPP_BASE_URL + "schedaFondoRischi/generaPFRModifiche.action?trimestre="+trimestreScelto;
	urlFase2=legalSecurity.verifyToken(urlFase2);
	downloadFileDOCX(urlFase2);
}

function downloadFilePDF(urlToSend) {
    var req = new XMLHttpRequest();
    req.open("GET", urlToSend, true);
    req.responseType = "document";
    req.onload = function (event) {
        var link=document.createElement('a');
        link.href=urlToSend;
        link.target="_blank";
        link.click();
        waitingDialog.hide();
    };
    req.send();
}

function downloadFileDOCX(urlToSend) {
	
    var req = new XMLHttpRequest();
    req.open("GET", urlToSend, true);
    req.responseType = "document";
    req.onload = function (event) {
        var link=document.createElement('a');
        link.href=urlToSend;
        link.target="_blank";
        link.click();
        waitingDialog.hide();
    };
    req.send();
}