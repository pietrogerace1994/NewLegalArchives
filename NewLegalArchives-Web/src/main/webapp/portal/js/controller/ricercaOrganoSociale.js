

function openOrganoSociale(id, azione){
	$("#id").val(id)
	$("#azione").val(azione)
	$("#openOrganoSociale").submit();
}	


function caricaAzioniSuOrganoSociale(id) {
	var containerAzioni = document.getElementById("action-organosociale-" + id);
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		containerAzioni.innerHTML = data;
	};
	
	var url = WEBAPP_BASE_URL + "organoSociale/caricaAzioniOrganoSociale.action?idOrganoSociale=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

function caricaAzioniOrganoSociale(data) {
	if (data != null && data.rows != null) {
		var rows = data.rows;
		for (i = 0; i < rows.length; i++) {
			var row = rows[i];
			var id = row.id;
			caricaAzioniSuOrganoSociale(id);
		}
	}
}


function getOrganoSocialeTableColumns(){
	return [ {
		field : 'idSocieta',
		title : 'SOCIETA',
		align : 'left',
		valign : 'top',
		sortable : true

	}, {
		field : 'tipoOrganoSociale',
		title : 'ORGANO',
		align : 'left',
		valign : 'top',
		sortable : false

	}, {
		field : 'nominativo',
		title : 'NOMINATIVO',
		align : 'left',
		valign : 'top',
		sortable : false
	}, {
		field : 'carica',
		title : 'CARICA',
		align : 'left',
		valign : 'top',
		sortable : false

	},{
		field : 'dataNomina',
		title : 'NOMINA',
		align : 'left',
		valign : 'top',
		sortable : true

	},
	{
		field : 'dataCessazione',
		title : 'CESSAZIONE',
		align : 'left',
		valign : 'top',
		sortable : true

	},
	{
		field : 'dataScadenza',
		title : 'SCADENZA',
		align : 'left',
		valign : 'top',
		sortable : true

	},{
		field : 'dataAccettazioneCarica',
		title : 'ACCETTAZIONE',
		align : 'left',
		valign : 'top',
		sortable : true

	},{
		field : 'emolumento',
		title : 'EMOLUMENTO',
		align : 'left',
		valign : 'top',
		sortable : false
	},{
		field : 'azioni',
		title : '',
		align : 'left',
		valign : 'top',
		sortable : false
	} ];
}


function initTabellaRicercaOrganoSociale() {
	var organoSocialeTableColumns = getOrganoSocialeTableColumns();
	var $table = $('#data-table-organosociale').bootstrapTable({
		method : 'GET',
		url : WEBAPP_BASE_URL + 'organoSociale/cerca.action',
		cache : false,
		striped : true,
		pagination : true,
		pageSize : 15,
		search : false,
		onLoadSuccess : caricaAzioniOrganoSociale,
		sidePagination : 'server',
		showRefresh : false,
		clickToSelect : true,
		sortOrder: "desc",
		columns : organoSocialeTableColumns
	});
}


function applyOrganoSocialeSearchFilter() {
	var cognome = encodeURIComponent(document.getElementById("cognome").value || "");
	var nome = encodeURIComponent(document.getElementById("nome").value || "");
	var incarica = ($("#divIncarica input:checked").val() || "");
	var tipoOrganoSociale = encodeURIComponent(document.getElementById("tipoOrganoSociale").value || "0");
	var idSocietaAffari = encodeURIComponent(document.getElementById("idSocietaAffari").value || "0");
	var gruppoSnam = ($("#divGruppoSnam input:checked").val() || "");
	
	
	$('#modalRicercaOrganoSociale').modal("hide");
	
	var organoSocialeTableColumns = getOrganoSocialeTableColumns();
	var filtraRicerca="cognome="+cognome
					+"&nome="+nome
					+"&incarica="+incarica
					+"&tipoOrganoSociale="+tipoOrganoSociale
					+"&idSocietaAffari="+idSocietaAffari
					+"&gruppoSnam="+gruppoSnam;
	
	$('#data-table-organosociale').bootstrapTable('refresh', {
		method : 'GET',
		url : WEBAPP_BASE_URL + 'organoSociale/cerca.action?'+filtraRicerca,
		cache : false,
		striped : true,
		pagination : true,
		pageSize : 100,
		search : false,
		onLoadSuccess : caricaAzioniOrganoSociale,
		sidePagination : 'server',
		showRefresh : false,
		clickToSelect : true,
		sortOrder:'desc',
		columns : organoSocialeTableColumns
	});
}