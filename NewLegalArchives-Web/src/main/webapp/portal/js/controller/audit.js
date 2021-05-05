
function caricaComboUserId(){
	console.log("caricaComboUserId");
	var combo = document.getElementById("comboUserId");
	combo.options.length = 1;
	
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		var combo = document.getElementById("comboUserId");	
		waitingDialog.hide();
		for( i = 0; i < data.length; i++ ){
			var option = document.createElement("OPTION");
			option.setAttribute("value",data[i].idOwner);
			var optionText = document.createTextNode(data[i].owner);
			option.appendChild(optionText);
			combo.appendChild(option);
		}
		
	};

	var callBackFnErr = function(data, stato) {  
		waitingDialog.hide();
	};
	
	var url = WEBAPP_BASE_URL + "audit/caricaComboUserId.action";
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "get", null, callBackFn, null, callBackFnErr );
}



function initTabellaRicercaAuditLog(){ 

		var $table = $('#tabellaRicercaAuditLog').bootstrapTable(
				{
					method : 'GET',
					url : WEBAPP_BASE_URL + 'audit/ricerca.action',
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
						var tipologiaEntita = encodeURIComponent(document
								.getElementById("tipologiaEntita").value); 
						var dal = encodeURIComponent(document
								.getElementById("txtDataDal").value);
						var al = encodeURIComponent(document
								.getElementById("txtDataAl").value);
						var userId = encodeURIComponent(document
								.getElementById("comboUserId").value); 
						params.tipoEntita = tipologiaEntita;
						params.userId = userId; 
						params.dal = dal;
						params.al = al;
						return params;
					},
					columns : [   {
						field : 'dataOra',
						title : 'DATA ORA',
						align : 'left',
						valign : 'top',
						sortable : true

					}/*, {
						field : 'nomeServer',
						title : 'NOME SERVER',
						align : 'left',
						valign : 'top',
						sortable : false

					},{
						field : 'nomeApp',
						title : 'NOME APP',
						align : 'center',
						valign : 'top',
						sortable : false

					}*/, {
						field : 'operazione',
						title : 'OPERAZIONE',
						align : 'left',
						valign : 'top',
						sortable : false
					}, {
						field : 'nomeOggetto',
						title : 'NOME OGGETTO',
						align : 'center',
						valign : 'top',
						sortable : false
					}, {
						field : 'userId',
						title : 'USERID',
						align : 'left',
						valign : 'top',
						sortable : false
					}/*, {
						field : 'opzionale',
						title : 'OPZIONALE',
						align : 'left',
						valign : 'top',
						sortable : false 
					}*/, {
						field : 'valoreOld',
						title : 'VALORE OLD',
						align : 'left',
						valign : 'top',
						//class: 'bootstap-table-column-150w',
						sortable : false
					} , {
						field : 'valoreNew',
						title : 'VALORE NEW',
						align : 'left',
						valign : 'top',
						//class: 'bootstap-table-column-150w',
						sortable : false
					}, {
						field : 'note',
						title : 'NOTE',
						align : 'left',
						valign : 'top',
						//class: 'bootstap-table-column-150w',
						sortable : false
					} ]
				});

		$('#btnApplicaFiltri').click(function() {
			$table.bootstrapTable('refresh');
		});
 
}

function cercaAuditLog(){

	document.getElementById('btnApplicaFiltri').click();
}