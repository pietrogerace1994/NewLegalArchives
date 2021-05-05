function selezionaNazione(codice){
	console.log('nazione selezionata: '+ codice);
	var form = document.getElementById("nazioneView"); 
	var op = document.getElementById("op");
	op.value="selezionaNazione";
	form.submit(); 
} 

function salvaNazione(){
	console.log('salva nazione');
	var form = document.getElementById("nazioneView"); 
	var op = document.getElementById("op");
	op.value="salvaNazione";
	if(document.getElementById("nazioneCode").selectedIndex == -1){
		document.getElementById("flagCode").value = "true";
	}
	form.submit(); 
}

function cancellaNazione(){
	console.log('cancella nazione');
	var form = document.getElementById("nazioneView"); 
	var op = document.getElementById("op");
	op.value="salvaNazione";
	var insertMode = document.getElementById("insertMode");
	insertMode.value="false";
	var deleteMode = document.getElementById("deleteMode");
	deleteMode.value="true";
	if(document.getElementById("nazioneCode").selectedIndex == -1){
		document.getElementById("flagCode").value = "true";
	}
	form.submit(); 
}

function insertCheck(){
	var insertMode = document.getElementById("insertMode");
	insertMode.value="true";
	document.getElementById("nazioneCode").selectedIndex = "-1";
	document.getElementsByName("soloParteCorrelata")[0].checked = false;
	var textboxes = document.querySelectorAll("input[name^='nazioneDesc[']");
	for (var i=0;i<textboxes.length;i++){
		textboxes[i].value="";
	}
}

function editCheck(){
	var insertMode = document.getElementById("insertMode");
	insertMode.value="false";
	document.getElementsByName("soloParteCorrelataIns")[0].checked = false;
	var textboxes = document.querySelectorAll("input[name^='nazioneIns[']");
	for (var i=0;i<textboxes.length;i++){
		textboxes[i].value="";
	}
}

function caricaDescrizioniNaz(code) {
	console.log('caricaDescrizioniNaz: '+code);
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) {
		var list = data.getElementsByTagName("nazioneDesc");
		var textboxes = document.querySelectorAll("input[name^='nazioneDesc[']");
		var soloParteCorrelata = document.getElementsByName("soloParteCorrelata");
		for (i = 0; i < list.length; i++) {
			if (textboxes[i] !== undefined) {
				textboxes[i].value = list[i].firstChild.data;
			}
		}
		if (soloParteCorrelata[0] !== undefined) {
			soloParteCorrelata[0].checked = (data.getElementsByTagName("soloParteCorrelata")[0].firstChild.data == 'true');
		}
	};
	var url = WEBAPP_BASE_URL + "nazione/caricaDescrizioniNazione.action?code=" + code;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null);
}

function alertResponse(data){
	var xmlstr = data.xml ? data.xml : (new XMLSerializer()).serializeToString(data);
	alert(xmlstr);	
}
