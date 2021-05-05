if (!String.prototype.startsWith) {
  String.prototype.startsWith = function(searchString, position) {
    position = position || 0;
    return this.indexOf(searchString, position) === position;
  };
}

$("#btnClear").click(function(event) {
	puliscoCampiInsert();
});

function puliscoCampiInsert() {

	$("#txtCodice").val('');
	$("#txtDescrizione").val('');
	$("#txtTesto").val('');
	$("#idCategoria").val('');
	$("#idSubcategoria").val('');

}

function salvaRepertorioPoteri(){
	var form = document.getElementById("repertorioPoteriForm");
	var op = document.getElementById("op");
	op.value = "salvaRepertorioPoteri";
	form.submit(); 
}

function modificaRepertorioPoteri(){
	var form = document.getElementById("repertorioPoteriForm");
	var op = document.getElementById("op");
	op.value = "modificaRepertorioPoteri";
	form.submit(); 
}

function eliminaRepertorioPoteri(id) {
	console.log("elimino repertorioPoteri con id: " + id);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		//initTabellaRicercaRepertorioPoteri();
		  $('#data-table-repertoriopoteri').bootstrapTable('refresh');

	};
	
	var fnCallBackError = function(data){
		visualizzaMessaggio(data);
		waitingDialog.hide();
	};

	var url = WEBAPP_BASE_URL + "repertorioPoteri/eliminaRepertorioPoteri.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null, fnCallBackError);
}




