function addSottoCategoria(){

	if($('#catPadre').is(':visible')){
		$('#catPadre').hide();
		$('#labelCat').show();
		$('#labelSot').hide();
	}
	else{
		$('#catPadre').show();
		$('#labelCat').hide();
		$('#labelSot').show();
	}
}

function salvaCategoria() {	
	console.log('salva categoria');
	 
	var form = document.getElementById("categoriaView");
	var op = document.getElementById("op");
	op.value = "salvaCategoria";
	waitingDialog.show('Loading...');
	
	form.submit();
}
 
 
function modificaRubrica(id){
  location.href=legalSecurity.verifyToken(WEBAPP_BASE_URL+"categorie/modificaCategoria.action?id="+id);
}
  


function cercaCategoria(){
	$('#tabellaRicercaCategoria').bootstrapTable('refresh');
	$('#tabellaRicercaCategoria').bootstrapTable('selectPage', 1);
}

 
function initTabellaRicercaCategoria() {

	var $table = $('#tabellaRicercaCategoria').bootstrapTable(
			{
				method : 'GET',
				url : WEBAPP_BASE_URL + 'catMailingList/ricerca.action?order=asc&limit=100&offset=0',
				cache : false,
				striped : true,
				pagination : false,
				search : false,
				onLoadSuccess : caricaAzioniCategoria,
				sidePagination : 'server',
				paginationVAlign: 'top',
				showRefresh : false,
				clickToSelect : true,
//				queryParams : function(params) {
//					var nominativo = encodeURIComponent(document
//							.getElementById("txtNominativo").value); 
//					var email = encodeURIComponent(document
//							.getElementById("txtEmail").value);
//					 
//					params.nominativo = nominativo;  
//					params.email = email; 
//					return params;
//				},
				columns : [  {
					field : 'nomeCategoria',
					title : 'CATEGORIA',
					align : 'left',
					valign : 'top',
					sortable : false,
					cellStyle: function(value, row, index){
		            	return {  css: {"color": "#"+row.colore, "font-weight": "bold"} };
	        			}
				},{
					field : 'categoriaFiglia',
					title : 'SOTTOCATEGORIA',
					align : 'left',
					valign : 'top',
					sortable : false
				},{
					field : 'azioni',
					title : '',
					align : 'left',
					valign : 'top',
					sortable : false
				}]
			});

//	$('#btnApplicaFiltri').click(function() {
//		$table.bootstrapTable('refresh');
//	});
}
 

function eliminaCategoria(id){
	console.log("elimino categoria con id: " + id);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		//visualizzaMessaggio(data);
		
		
		
		cercaCategoria();
		$("#categoriaPadre option[value='"+id+"']").remove();
		waitingDialog.hide();
	};
	var callBackFnErr = function(data, stato) { 
		//visualizzaMessaggio(data);
		waitingDialog.hide(); 
	};
	var url = WEBAPP_BASE_URL + "catMailingList/eliminaCategoria.action?id=" + id;
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null, callBackFnErr);
}

function caricaAzioniCategoria(data) {
	if (data != null && data.rows != null) {
		var rows = data.rows;
		for (i = 0; i < rows.length; i++) {
			var row = rows[i];
			var id = row.id;
			caricaAzioniSuCategoria(id);
		}

	}
} 

function caricaAzioniSuCategoria(id) {
	var containerAzioni = document.getElementById("containerAzioniRigaCategoria" + id);
	containerAzioni.innerHTML="<img src='"+WEBAPP_BASE_URL+"vendors/jquery/loading.gif'>";
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {

		containerAzioni.innerHTML = data;

	};

	var url = WEBAPP_BASE_URL
			+ "catMailingList/caricaAzioniCategoria.action?idCat=" + id;
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);

}
 