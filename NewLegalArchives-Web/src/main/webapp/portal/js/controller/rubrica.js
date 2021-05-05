
function salvaNominativo() {	
	console.log('salva nominativo');
	 
	var form = document.getElementById("rubricaView");
	var op = document.getElementById("op");
	op.value = "salvaRubrica";
	waitingDialog.show('Loading...');
	
	form.submit();
}
 
 
function modificaRubrica(id){
  location.href=legalSecurity.verifyToken(WEBAPP_BASE_URL+"email/modificaRubrica.action?id="+id);
}
  


function cercaRubrica(){
	document.getElementById('btnApplicaFiltri').click();

}

 
function initTabellaRicercaRubrica() {

	var $table = $('#tabellaRicercaRubrica').bootstrapTable(
			{
				method : 'GET',
				url : WEBAPP_BASE_URL + 'rubrica/ricerca.action',
				cache : false,
				striped : true,
				pagination : true,
				pageSize : 10,
				search : false,
				onLoadSuccess : caricaAzioniRubrica,
				sidePagination : 'server',
				paginationVAlign: 'top',
				showRefresh : false,
				clickToSelect : true,
				queryParams : function(params) {
					var nominativo = encodeURIComponent(document
							.getElementById("txtNominativo").value); 
					var email = encodeURIComponent(document
							.getElementById("txtEmail").value);
					 
					params.nominativo = nominativo;  
					params.email = email; 
					return params;
				},
				columns : [  {
					field : 'azioni',
					title : '',
					align : 'left',
					valign : 'top',
					sortable : false
				},{
					field : 'nominativo',
					title : 'NOMINATIVO',
					align : 'left',
					valign : 'top',
					sortable : true

				}, {
					field : 'email',
					title : 'EMAIL',
					align : 'left',
					valign : 'top',
					sortable : false
				} ]
			});

	$('#btnApplicaFiltri').click(function() {
		$table.bootstrapTable('refresh');
	});
}
 

function eliminaRubrica(id){
	console.log("elimino rubrica con id: " + id);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		cercaRubrica();
	};
	var callBackFnErr = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide(); 
	};
	var url = WEBAPP_BASE_URL + "presidionormativo/eliminaRubrica.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null, callBackFnErr);
}

function caricaAzioniRubrica(data) {
	if (data != null && data.rows != null) {
		var rows = data.rows;
		for (i = 0; i < rows.length; i++) {
			var row = rows[i];
			var id = row.id;
			caricaAzioniSuRubrica(id);
		}

	}
} 

function caricaAzioniSuRubrica(id) {
	var containerAzioni = document.getElementById("containerAzioniRigaRubrica" + id);
	containerAzioni.innerHTML="<img src='"+WEBAPP_BASE_URL+"vendors/jquery/loading.gif'>";
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {

		containerAzioni.innerHTML = data;

	};

	var url = WEBAPP_BASE_URL
			+ "presidionormativo/caricaAzioniRubrica.action?idRubrica=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);

}


 