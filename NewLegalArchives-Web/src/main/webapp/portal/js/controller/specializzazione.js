function selezionaSpecializzazione(codice){
	console.log('specializzazione selezionata: '+ codice);
	var form = document.getElementById("specializzazioneView"); 
	var op = document.getElementById("op");
	op.value="selezionaSpecializzazione";
	form.submit(); 
} 

function salvaSpecializzazione(){
	console.log('salva specializzazione');
	var form = document.getElementById("specializzazioneView"); 
	var op = document.getElementById("op");
	op.value="salvaSpecializzazione";
	if(document.getElementById("specializzazioneCode").selectedIndex == -1){
		document.getElementById("flagCode").value = "true";
	}
	form.submit(); 
}

function cancellaSpecializzazione(){
	console.log('cancella specializzazione');
	var form = document.getElementById("specializzazioneView"); 
	var op = document.getElementById("op");
	op.value="salvaSpecializzazione";
	var insertMode = document.getElementById("insertMode");
	insertMode.value="false";
	var deleteMode = document.getElementById("deleteMode");
	deleteMode.value="true";
	if(document.getElementById("specializzazioneCode").selectedIndex == -1){
		document.getElementById("flagCode").value = "true";
	}
	form.submit(); 
}

function insertCheck(){
	var insertMode = document.getElementById("insertMode");
	insertMode.value="true";
	document.getElementById("specializzazioneCode").selectedIndex = "-1";
	var textboxes = document.querySelectorAll("input[name^='specializzazioneDesc[']");
	for (var i=0;i<textboxes.length;i++){
		textboxes[i].value="";
	}
}

function editCheck(){
	var insertMode = document.getElementById("insertMode");
	insertMode.value="false";
	var textboxes = document.querySelectorAll("input[name^='specializzazioneIns[']");
	for (var i=0;i<textboxes.length;i++){
		textboxes[i].value="";
	}
}

function caricaDescrizioniSpec(code) {
	console.log('caricaDescrizioniSpec: '+code);
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		var list = data.getElementsByTagName("specializzazioneDesc");
		var textboxes = document.querySelectorAll("input[name^='specializzazioneDesc[']");
		for (i = 0; i < list.length; i++) {
			if (textboxes[i] !== undefined) {
				textboxes[i].value = list[i].firstChild.data;
			}
		}
	};
	var url = WEBAPP_BASE_URL + "specializzazione/caricaDescrizioniSpecializzazione.action?code=" + code;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
	
}

function alertResponse(data){
	var xmlstr = data.xml ? data.xml : (new XMLSerializer()).serializeToString(data);
	alert(xmlstr);	
}
