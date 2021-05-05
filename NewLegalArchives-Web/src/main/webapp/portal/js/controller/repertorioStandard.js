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

	$("#txtNome").val('');
	$("#txtNota").val('');
	$("#idSocieta").val('');
	$("#idPosizioneOrganizzativa").val('');
	$("#idPrimoLivelloAttribuzioni").val('');
	$("#idSecondoLivelloAttribuzioni").val('');

}

function salvaRepertorioStandard(){
	var form = document.getElementById("repertorioStandardForm");
	var op = document.getElementById("op");
	op.value = "salvaRepertorioStandard";
	form.submit(); 
}

function modificaRepertorioStandard(){
	var form = document.getElementById("repertorioStandardForm");
	var op = document.getElementById("op");
	op.value = "modificaRepertorioStandard";
	form.submit(); 
}

function eliminaRepertorioStandard(id) {
	console.log("elimino repertorioStandard con id: " + id);
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		visualizzaMessaggio(data);
		waitingDialog.hide();
		//initTabellaRicercaRepertorioStandard();
		  $('#data-table-repertoriostandard').bootstrapTable('refresh');

	};
	
	var fnCallBackError = function(data){
		visualizzaMessaggio(data);
		waitingDialog.hide();
	};

	var url = WEBAPP_BASE_URL + "repertorioStandard/eliminaRepertorioStandard.action?id=" + id;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null, fnCallBackError);
}

function aggiungiAllegatoGenerico(id){
	var file = $('#fileAllegatoGenerico')[0].files[0];
	var data = new FormData();
	data.append('file', file);
	data.append('idRepertorioStandard', id);
	data.append('CSRFToken',legalSecurity.getToken());
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	var url = WEBAPP_BASE_URL + "repertorioStandard/uploadAllegatoGenerico.action";
	
	var fnCallBackSuccess = function(data){ 
		waitingDialog.hide();
		var container = document.getElementById('boxAllegatiGenerici');
		container.innerHTML=data;
		if( data.indexOf("showGraffa") != -1 ){
			showGraffa("allegatiGenericiGraffa");
		}else if( data.indexOf("hideGraffa") != -1 ){
			hideGraffa("allegatiGenericiGraffa");
		}  
	};
	var fnCallBackError = function(){

		waitingDialog.hide();
	};
	
	ajaxUtil.ajaxUpload(url, data, fnCallBackSuccess, fnCallBackError);
} 

function rimuoviAllegatoGenerico(uuid){
	var ajaxUtil = new AjaxUtil();
	waitingDialog.show('Loading...');
	uuid = uuid.replace('{','');
	uuid = uuid.replace('}','');
	
	var url = WEBAPP_BASE_URL + "repertorioStandard/rimuoviAllegatoGenerico.action?uuid="+uuid;
	url=legalSecurity.verifyToken(url);
	var params =  "";
	var fnCallBackSuccess = function(data){ 
		waitingDialog.hide();
		var container = document.getElementById('boxAllegatiGenerici');
		container.innerHTML=data;
		if( data.indexOf("showGraffa") != -1 ){
			showGraffa("allegatiGenericiGraffa");
		}else if( data.indexOf("hideGraffa") != -1 ){
			hideGraffa("allegatiGenericiGraffa");
		}  
	};
	var fnCallBackError = function(){

		waitingDialog.hide();
	};

	ajaxUtil.ajax(url, params, "post", null, fnCallBackSuccess, null, fnCallBackError);
} 




