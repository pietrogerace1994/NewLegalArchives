function openProcure(id, azione){
	$("#id").val(id)
	$("#azione").val(azione)
	$("#openProcure").submit();
}	


function caricaAzioniSuProcure(id,nome) {
	var containerAzioni = document.getElementById("action-procure-" + id);
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		containerAzioni.innerHTML = data;
	};
	
	var url = WEBAPP_BASE_URL + "procure/caricaAzioniProcure.action?idProcure=" + id+"&nomeProcure="+nome;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

function caricaAzioniProcure(data) {
	if (data != null && data.rows != null) {
		var rows = data.rows;
		for (i = 0; i < rows.length; i++) {
			var row = rows[i];
			var id = row.id;
			var nomeProcuratore = row.nomeProcuratore;
			caricaAzioniSuProcure(id,nomeProcuratore);
		}
	}
}

function getProcureTableColumns(){
	return [ {
		field : 'nomeProcuratore',
		title : 'PROCURATORE',
		align : 'left',
		valign : 'top',
		sortable : true

	}, {
		field : 'numeroRepertorio',
		title : 'REPERTORIO',
		align : 'center',
		valign : 'top',
		sortable : false

	}, {
		field : 'tipologia',
		title : 'TIPOLOGIA',
		align : 'center',
		valign : 'top',
		sortable : false
	}, {
		field : 'dataConferimento',
		title : 'CONFERIMENTO',
		align : 'center',
		valign : 'top',
		sortable : false
	}, {
		field : 'dataRevoca',
		title : 'REVOCA',
		align : 'left',
		valign : 'top',
		sortable : false

	},{
		field : 'idSocieta',
		title : 'SOCIETA',
		align : 'left',
		valign : 'top',
		sortable : false

	},{
		field : 'idNotaio',
		title : 'NOTAIO',
		align : 'left',
		valign : 'top',
		sortable : false

	},{
		field : 'idFascicolo',
		title : 'FASCICOLO',
		align : 'left',
		valign : 'top',
		sortable : false

	}, {
		field : 'check',
		title : '',
		align : 'left',
		valign : 'top',
		sortable : false
	}, {
		field : 'azioni',
		title : '',
		align : 'left',
		valign : 'top',
		sortable : false
	} ];
}


function initTabellaRicercaProcure() {
	var procureTableColumns = getProcureTableColumns();
	var $table = $('#data-table-procure').bootstrapTable({
		method : 'GET',
		url : WEBAPP_BASE_URL + 'procure/cerca.action',
		cache : false,
		striped : true,
		pagination : true,
		pageSize : 15,
		search : false,
		onLoadSuccess : caricaAzioniProcure,
		sidePagination : 'server',
		showRefresh : false,
		clickToSelect : true,
		sortOrder:'desc',
		columns : procureTableColumns
	});
}


function applyProcureSearchFilter() {
	var dataConferimentoDal = encodeURIComponent(document.getElementById("dataConferimentoDal").value || "0");
	var dataConferimentoAl = encodeURIComponent(document.getElementById("dataConferimentoAl").value || "0");
	var dataRevocaDal = encodeURIComponent(document.getElementById("dataRevocaDal").value || "0");
	var dataRevocaAl = encodeURIComponent(document.getElementById("dataRevocaAl").value || "0");
	var numeroRepertorio = encodeURIComponent(document.getElementById("numeroRepertorio").value || "0");
	var nomeProcuratore = encodeURIComponent(document.getElementById("nomeProcuratore").value || "0");
	var tipologia = encodeURIComponent(document.getElementById("tipologia").value || "0");
	var idSocieta = encodeURIComponent(document.getElementById("idSocieta").value || "0");
	var idNotaio = encodeURIComponent(document.getElementById("idNotaio").value || "0");
	var utente = encodeURIComponent(document.getElementById("utente").value || "0");
	var idPosizioneOrganizzativa = encodeURIComponent(document.getElementById("idPosizioneOrganizzativa").value || "0");
	var idLivelloAttribuzioniI = encodeURIComponent(document.getElementById("idLivelloAttribuzioniI").value || "0");
	var idLivelloAttribuzioniII = encodeURIComponent(document.getElementById("idLivelloAttribuzioniII").value || "0");
	
	$('#modalRicercaProcure').modal("hide");
	
	var procureTableColumns = getProcureTableColumns();
	var filtraRicerca="dataConferimentoDal="+dataConferimentoDal+"&dataConferimentoAl="+dataConferimentoAl+"&dataRevocaDal="+dataRevocaDal+
					  "&dataRevocaDal="+dataRevocaAl+"&numeroRepertorio="+numeroRepertorio+"&nomeProcuratore="+nomeProcuratore+
					  "&tipologia="+tipologia+"&idSocieta="+idSocieta+"&idNotaio="+idNotaio+"&utente="+utente+"&idPosizioneOrganizzativa="+idPosizioneOrganizzativa
					  "&idLivelloAttribuzioniI="+idLivelloAttribuzioniI+"&idLivelloAttribuzioniII="+idLivelloAttribuzioniII;
	
	$('#data-table-procure').bootstrapTable('refresh', {
		method : 'GET',
		url : WEBAPP_BASE_URL + 'procure/cerca.action?'+filtraRicerca,
		cache : false,
		striped : true,
		pagination : true,
		pageSize : 100,
		search : false,
		onLoadSuccess : caricaAzioniProcure,
		sidePagination : 'server',
		showRefresh : false,
		clickToSelect : true,
		sortOrder:'desc',
		columns : procureTableColumns
	});
}

var listaProcure=[];

function addListaProcure(procura){
	
	var selAll = $("#data-table-procure  input[type='checkbox']:checked");
	var sel = [];
	
	for(i=0;i<selAll.length;i++){
		if(!selAll[i].disabled){
			sel.push(selAll[i]);
		}
	}

	if (sel.length === 1){
		listaProcure.push(procura);
		$("#creaFascicoloEIncarico")[0].disabled=false;
	} else 	if (sel.length === 0){
		listaProcure=[];
		$("#creaFascicoloEIncarico")[0].disabled=true;
	}
	else{
		$("#creaFascicoloEIncarico")[0].disabled=false;
		if (procura.checked===true){
		
				var sameNotaio=true;
				for (i = 1; i < sel.length; i++) {
					if (sel[i].getAttribute("data-notaio") != sel[i-1].getAttribute("data-notaio")){
						sameNotaio = false;
					}
				}
		
			if (sameNotaio){
				
				listaProcure.push(procura);
			}else{
				procura.checked=false;
			}
		}else{
			listaProcure.pop(procura);
		}
	}
}

function creaFascicoloEIncarico() {
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	var callBackFn = function(data, stato) {
		visualizzaMessaggio(data);
		waitingDialog.hide();
		applyProcureSearchFilter();
	};
	
	var fnCallBackError = function(data){
		visualizzaMessaggio(data);
		waitingDialog.hide();
	};
	
	var params={notaio:"",societa:[],procure:[]};
	for (i = 0; i < listaProcure.length; i++) {
		var row = listaProcure[i];
		params.notaio=row.getAttribute("data-notaio");
		params.procure.push(row.getAttribute("data-procure"));
		
		if (params.societa.indexOf(row.getAttribute("data-societa"))===-1)
			params.societa.push(row.getAttribute("data-societa"));
	}
	var url = WEBAPP_BASE_URL + "procure/creaFascicoloEIncarico.action";
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, JSON.stringify(params), "post", "application/json", callBackFn,"json", fnCallBackError);
}


function visualizzaModalAssociazioni(id, fascicoli){
	
	$("#procuraId").val(id);
	
	fascicoli = fascicoli.replace(/\*/g, '\"');
	
	var listaFascicoli = JSON.parse(fascicoli);
	
	var str = '';
	
	for(var i=0; i<listaFascicoli.length; i++) {
		var obj=listaFascicoli[i];
		var id=obj["idFascicolo"];
		var nome=obj["nomeFascicolo"];
		str += '<option value="'+id+'">'+nome+'</option>';
	}
	$("#fascicoliDaAssociare").html('');
	$("#fascicoliDaAssociare").html(str);
	
	console.log("apriModalAssociaFascicolo");
	$('#modalAssociaFascicolo').modal('show');
}

function chiudiModale() {
	console.log("chiudiModalAssociaFascicolo");
	$('#modalAssociaFascicolo').modal('hide');
}

function associaProcuraFascicolo(){	
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	var callBackFn = function(data, stato) {
		chiudiModale();
		visualizzaMessaggio(data);
		waitingDialog.hide();
		applyProcureSearchFilter();
	};
	
	var fnCallBackError = function(data){
		visualizzaMessaggio(data);
		waitingDialog.hide();
	};
	
	var params = "";
	
	//ProcuraId
	var procuraId = $("#procuraId").val();
	
	//Fascicolo da associare
	var fascicoloDaAssociare = document.getElementById("fascicoliDaAssociare");
	var fascicoliDaAssociareSelIndex = fascicoloDaAssociare.selectedIndex;
	var fascicoliDaAssociareVal;
	if(fascicoliDaAssociareSelIndex>=0) {
		fascicoliDaAssociareVal = fascicoloDaAssociare[fascicoliDaAssociareSelIndex].value;
	}
	
	var url = WEBAPP_BASE_URL + "procure/associaProcureAFascicolo.action?idProcure=" + procuraId + "&idFascicolo=" + fascicoliDaAssociareVal;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, params, "post", "application/x-www-form-urlencoded", callBackFn, null, fnCallBackError);
}