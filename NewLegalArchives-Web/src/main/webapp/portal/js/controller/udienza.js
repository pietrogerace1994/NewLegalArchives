$(document).ready(function () {
});

function salvaUdienza() {
	console.log('salva udienza');
	var form = document.getElementById("udienzaView");
	var op = document.getElementById("op");
	op.value = "salvaUdienza";
	waitingDialog.show('Loading...');
	form.submit();
}

function modificaUdienza(id){
  location.href=legalSecurity.verifyToken(WEBAPP_BASE_URL+"udienza/modifica.action?id="+id);
}

function caricaAzioniUdienza(data) {
	waitingDialog.hide();
	if (data != null && data.rows != null) {
		var rows = data.rows;
		for (i = 0; i < rows.length; i++) {
			var row = rows[i];
			var id = row.id;
			caricaAzioniSuUdienza(id);
		}
	}
}

function cercaUdienza(){
	document.getElementById('btnApplicaFiltri').click();
}

function caricaAzioniSuUdienza(id) {
	var containerAzioni = document.getElementById("containerAzioniRigaUdienza" + id);
	containerAzioni.innerHTML="<img src='"+WEBAPP_BASE_URL+"vendors/jquery/loading.gif'>";
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {

		containerAzioni.innerHTML = data;

	};
	var url = WEBAPP_BASE_URL
			+ "udienza/caricaAzioniUdienza.action?idUdienza=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}


function initTabellaRicercaUdienza() {

	var $table = $('#tabellaRicercaUdienza').bootstrapTable(
			{
				method : 'GET',
				url : WEBAPP_BASE_URL + 'udienza/ricerca.action',
				cache : false,
				striped : true,
				pagination : true,
				pageSize : 10,
				search : false,
				onLoadSuccess : caricaAzioniUdienza,
				sidePagination : 'server',
				paginationVAlign: 'top',
				showRefresh : false,
				clickToSelect : true,
				queryParams : function(params) { 
					var nomeFascicolo = encodeURIComponent( document.getElementById("txtNomeFascicolo").value) ;

					var dal = encodeURIComponent(document.getElementById("txtDataDal").value);
					
					var al = encodeURIComponent(document.getElementById("txtDataAl").value); 
					
					var legale = document.getElementById("legaleInterno");
					var legaleInterno = "";

					if(legale != null)
						legaleInterno = encodeURIComponent(legale.value);
					
					params.nomeFascicolo = nomeFascicolo;
					params.dal = dal;
					params.al = al;
					params.legaleInterno = legaleInterno;
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
					
				} ,{
					field : 'dataUdienza',
					title : 'DATA UDIENZA',
					align : 'center',
					valign : 'top',
					sortable : false
				} ,{
					field : 'ownerFascicolo',
					title : 'OWNER FASCICOLO',
					align : 'center',
					valign : 'top',
					sortable : false
				} ,{
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

function eliminaUdienza(id){
	console.log("elimino udienza con id: " + id);
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		cercaUdienza();
	};
	var url = WEBAPP_BASE_URL + "udienza/eliminaUdienza.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}