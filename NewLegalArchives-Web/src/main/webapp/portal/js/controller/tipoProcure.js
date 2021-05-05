function selezionaTipoProcure(codice){
	console.log('tipoProcure selezionata: '+ codice);
	var form = document.getElementById("tipoProcureView"); 
	var op = document.getElementById("op");
	op.value="selezionaTipoProcure";
	form.submit(); 
} 

function salvaTipoProcure(){
	console.log('salva tipoProcure');
	var form = document.getElementById("tipoProcureView"); 
	var op = document.getElementById("op");
	op.value="salvaTipoProcure";
	if(document.getElementById("tipoProcureCode").selectedIndex == -1){
		document.getElementById("flagCode").value = "true";
	}
	form.submit(); 
}

function cancellaTipoProcure(){
	console.log('cancella tipoProcure');
	var form = document.getElementById("tipoProcureView"); 
	var op = document.getElementById("op");
	op.value="salvaTipoProcure";
	var insertMode = document.getElementById("insertMode");
	insertMode.value="false";
	var deleteMode = document.getElementById("deleteMode");
	deleteMode.value="true";
	if(document.getElementById("tipoProcureCode").selectedIndex == -1){
		document.getElementById("flagCode").value = "true";
	}
	form.submit(); 
}

function insertCheck(){
	var insertMode = document.getElementById("insertMode");
	insertMode.value="true";
	document.getElementById("tipoProcureCode").selectedIndex = "-1";
	var textboxes = document.querySelectorAll("input[name^='tipoProcureDesc[']");
	for (var i=0;i<textboxes.length;i++){
		textboxes[i].value="";
	}
}

function editCheck(){
	var insertMode = document.getElementById("insertMode");
	insertMode.value="false";
	var textboxes = document.querySelectorAll("input[name^='tipoProcureIns[']");
	for (var i=0;i<textboxes.length;i++){
		textboxes[i].value="";
	}
}

function caricaDescrizioniTipoProcure(code) {
	console.log('caricaDescrizioniTipoProcure: '+code);
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		var list = data.getElementsByTagName("tipoProcureDesc");
		var textboxes = document.querySelectorAll("input[name^='tipoProcureDesc[']");
		for (i = 0; i < list.length; i++) {
			if (textboxes[i] !== undefined) {
				textboxes[i].value = list[i].firstChild.data;
			}
		}
	};
	var url = WEBAPP_BASE_URL + "tipoProcure/caricaDescrizioniTipoProcure.action?code=" + code;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

function alertResponse(data){
	var xmlstr = data.xml ? data.xml : (new XMLSerializer()).serializeToString(data);
	alert(xmlstr);	
}
