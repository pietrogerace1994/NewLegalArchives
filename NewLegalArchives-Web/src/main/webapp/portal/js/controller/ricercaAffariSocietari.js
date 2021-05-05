

function openAffariSocietari(id, azione){
	$("#id").val(id)
	$("#azione").val(azione)
	$("#openAffariSocietari").submit();
}	


function caricaAzioniSuAffariSocietari(id,cancellato) {
	var containerAzioni = document.getElementById("action-affarisocietari-" + id);
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		containerAzioni.innerHTML = data;
	};
	
	var url = WEBAPP_BASE_URL + "affariSocietari/caricaAzioniAffariSocietari.action?idAffariSocietari=" + id+"&cancellato="+cancellato;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

function caricaAzioniAffariSocietari(data) {
	if (data != null && data.rows != null) {
		var rows = data.rows;
		for (i = 0; i < rows.length; i++) {
			var row = rows[i];
			var id = row.id;
			caricaAzioniSuAffariSocietari(id,row.cancellato);
		}
	}
}


function getAffariSocietariTableColumns(){
	return [ {
		field : 'denominazione',
		title : 'DENIMINAZIONE',
		align : 'left',
		valign : 'top',
		sortable : true

	}, {
		field : 'sedeLegale',
		title : 'SEDE LEGALE',
		align : 'left',
		valign : 'top',
		sortable : false

	}, {
		field : 'codiceFiscale',
		title : 'CODICE FISCALE',
		align : 'left',
		valign : 'top',
		sortable : false
	}, {
		field : 'partitaIva',
		title : 'PARTITA IVA',
		align : 'left',
		valign : 'top',
		sortable : false
	}, {
		field : 'denominazioneControllante',
		title : 'SNAM E CONTROLLATE',
		align : 'left',
		valign : 'top',
		sortable : false

	},  {
		field : 'percentualeControllante',
		title : '% SNAM E CONTROLLATE',
		align : 'left',
		valign : 'top',
		sortable : false

	}, {
		field : 'percentualeTerzi',
		title : '% terzi',
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


function initTabellaRicercaAffariSocietari() {
	var affariSocietariTableColumns = getAffariSocietariTableColumns();
	var $table = $('#data-table-affarisocietari').bootstrapTable({
		method : 'GET',
		url : WEBAPP_BASE_URL + 'affariSocietari/cerca.action',
		cache : false,
		striped : true,
		pagination : true,
		pageSize : 15,
		search : false,
		onLoadSuccess : caricaAzioniAffariSocietari,
		sidePagination : 'server',
		showRefresh : false,
		clickToSelect : true,
		sortOrder:'desc',
		columns : affariSocietariTableColumns
	});
}

function applyAffariSocietariSearchFilter() {
	var denominazione = encodeURIComponent(document.getElementById("denominazione").value || "");
	var idNazione = encodeURIComponent(document.getElementById("idNazione").value || "0");
	var quotazione = ($("#divQuotazione input:checked").val() || "");
	var modelloDiGovernance = ($("#divModelloDiGovernance input:checked").val() || "");
	var storico = ($("#divStorico input:checked").val() || "");
	var gruppoSnam = ($("#divGruppoSnam input:checked").val() || "");
	
	$('#modalRicercaAffariSocietari').modal("hide");
	
	var affariSocietariTableColumns = getAffariSocietariTableColumns();
	var filtraRicerca="denominazione="+denominazione
					+"&idNazione="+idNazione
					+"&quotazione="+quotazione
					+"&modelloDiGovernance="+modelloDiGovernance
					+"&storico="+storico
					+"&gruppoSnam="+gruppoSnam;
	
	$('#data-table-affarisocietari').bootstrapTable('refresh', {
		method : 'GET',
		url : WEBAPP_BASE_URL + 'affariSocietari/cerca.action?'+filtraRicerca,
		cache : false,
		striped : true,
		pagination : true,
		pageSize : 100,
		search : false,
		onLoadSuccess : caricaAzioniAffariSocietari,
		sidePagination : 'server',
		showRefresh : false,
		clickToSelect : true,
		sortOrder:'desc',
		columns : affariSocietariTableColumns
	});
}


