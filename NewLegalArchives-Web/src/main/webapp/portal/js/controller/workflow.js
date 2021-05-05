//DARIO******************************************************************************************************
//function processaWorkflow(idStep, idWorkflow, codiceClasse, idObject, descrizioneStep) {
function processaWorkflow(idStep, idWorkflow, codiceClasse, idObject, descrizioneStep, flagAssegnatari, callBackFunction) {
//***********************************************************************************************************	
		
	var container = document.getElementById("containerFormWorkflow");	
	var btnAvanzaWorkflow = document.getElementById("btnAvanzaWorkflow");
	var btnRifiutaWorkflow = document.getElementById("btnRifiutaWorkflow");
	var hdnIdWorkflow = document.getElementById("hdnIdWorkflow");
	var hdnClasseWorkflow = document.getElementById("hdnClasseWorkflow");
	var hdnIdObject = document.getElementById("hdnIdObject");
	var hdnIdStep = document.getElementById("hdnIdStep");
	var nomeStep = document.getElementById("nomeStep");
		
	hdnIdWorkflow.value = idWorkflow;
	hdnClasseWorkflow.value = codiceClasse;
	hdnIdObject.value = idObject;
	hdnIdStep = idStep;
	nomeStep.innerHTML = descrizioneStep;
	
	
	btnAvanzaWorkflow.style.visibility = "visible";
	btnRifiutaWorkflow.style.visibility = "visible";
	
		
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		
		container.innerHTML=data;
		waitingDialog.hide(); 
//DARIO ********************************************************************		
//		var successOperation = document.getElementById("successOperation");
//		successOperation.style.visibility = "hidden";
//		var faultOperation = document.getElementById("faultOperation");
//		faultOperation.style.visibility = "hidden";
//		var rejectionReason = document.getElementById("rejectionReason");
//		rejectionReason.style.visibility = "hidden";
//			
//		var divStepView = document.getElementById("divStepView");
//		divStepView.style.visibility = "hidden";
//		document.getElementById("noteRifiutoWs").value = "";

		var successOperation = document.getElementById("successOperation");
		successOperation.style.display="none";
		var faultOperation = document.getElementById("faultOperation");
		faultOperation.style.display="none";
		var rejectionReason = document.getElementById("rejectionReason");
		rejectionReason.style.display="none";
			
		var divStepView = document.getElementById("divStepView");
		divStepView.style.display="none";
		document.getElementById("noteRifiutoWs").value = "";
		
		
		callBackFunction();
		
		
//*********************************************************************************		
		
	};
	var callBackFnErr = function(data, stato) { 
		
		waitingDialog.hide(); 
	};

	//DARIO*****************************************************************
//	var params = "idObject="+idObject+"&codiceClasse="+codiceClasse+"&"+
//	"idWorkflow="+idWorkflow+"&idStep="+idStep;
	
	var params = "idObject="+idObject+"&codiceClasse="+codiceClasse+"&"+
	"idWorkflow="+idWorkflow+"&idStep="+idStep +"&flagAssegnatari="+flagAssegnatari;
	//**********************************************************************
	
	
	var url = WEBAPP_BASE_URL + "stepWf/processaWorkflow.action";
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, params, "post", "application/x-www-form-urlencoded", callBackFn, null, callBackFnErr);
	
	
}


//DARIO***********************************************************************************************************************
//function processaWorkflowAtto(idStep, idWorkflow, idObject, descrizioneStep, idFascicoloAtto, motivoRifiutoStepPrecedente) {
function processaWorkflowAtto(idStep, idWorkflow, idObject, descrizioneStep, idFascicoloAtto, motivoRifiutoStepPrecedente,flagAssegnatari, callBackFunction) {	
//*****************************************************************************************************************************	
	var container = document.getElementById("containerFormWorkflowAtto");	
	var btnRichiediRegistrazione = document.getElementById("btnRichiediRegistrazione");
	var btnRifiutaWorkflowAtto = document.getElementById("btnRifiutaWorkflowAtto");
	var hdnIdWorkflowAtto = document.getElementById("hdnIdWorkflowAtto");
	var hdnIdObjectAtto = document.getElementById("hdnIdObjectAtto");
	var hdnIdStepAtto = document.getElementById("hdnIdStepAtto");
	var nomeStepAtto = document.getElementById("nomeStepAtto");
	var btnAvanzaWorkflowAtto = document.getElementById("btnAvanzaWorkflowAtto");
	var btnRifiutaWorkflowAtto = document.getElementById("btnRifiutaWorkflowAtto");
	var btnRegistraAtto = document.getElementById("btnRegistraAtto");
	var destinatarioAttoGC = document.getElementById("destinatarioAttoGC");
	var destinatarioAttoLegaleInterno = document.getElementById("destinatarioAttoLegaleInterno");
	var destinatarioAttoResponsabile = document.getElementById("destinatarioAttoResponsabile");
	
	if(btnAvanzaWorkflowAtto)
		btnAvanzaWorkflowAtto.style.visibility = "visible";
	if(btnRifiutaWorkflowAtto)
		btnRifiutaWorkflowAtto.style.visibility = "visible";
	if(btnRegistraAtto)
		btnRegistraAtto.style.visibility = "visible";
	if(destinatarioAttoGC)
		destinatarioAttoGC.style.visibility = "visible";
	if(destinatarioAttoLegaleInterno)
		destinatarioAttoLegaleInterno.style.visibility = "visible";
	if(destinatarioAttoResponsabile)
		destinatarioAttoResponsabile.style.visibility = "visible";
	
	
	
	hdnIdWorkflowAtto.value = idWorkflow;
	hdnIdObjectAtto.value = idObject;
	hdnIdStepAtto = idStep;
	nomeStepAtto.innerHTML = descrizioneStep;
//	btnAvanzaWorkflow.style.visibility = "visible";
//	btnRifiutaWorkflow.style.visibility = "visible";
	

	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		container.innerHTML=data;
		waitingDialog.hide(); 
		//DARIO********************************************************************
//		var successOperation = document.getElementById("successOperationAtto");
//		successOperation.style.visibility = "hidden";
//		var faultOperation = document.getElementById("faultOperationAtto");
//		faultOperation.style.visibility = "hidden";
//		var rejectionReason = document.getElementById("rejectionReasonAtto");
//		rejectionReason.style.visibility = "hidden";
//		if(motivoRifiutoStepPrecedente.replace(/\s+$|^\s+/g,"") == ""){
//			var divStepView = document.getElementById("divStepViewAtto");
//			divStepView.style.visibility = "hidden";
//		}
//		else{
//			var divStepView = document.getElementById("divStepViewAtto");
//			divStepView.style.visibility = "visible";
//			var noteRifiutoWsAtto = document.getElementById("noteRifiutoWsAtto");
//			noteRifiutoWsAtto.readOnly = "true";
//
//		}
		var successOperation = document.getElementById("successOperationAtto");
		successOperation.style.display="none";
		var faultOperation = document.getElementById("faultOperationAtto");
		faultOperation.style.display="none";
		var rejectionReason = document.getElementById("rejectionReasonAtto");
		rejectionReason.style.display="none";
		if(motivoRifiutoStepPrecedente.replace(/\s+$|^\s+/g,"") == ""){
			var divStepView = document.getElementById("divStepViewAtto");
			divStepView.style.display="none";
		}
		else{
			var divStepView = document.getElementById("divStepViewAtto");
			divStepView.style.display=""
			var noteRifiutoWsAtto = document.getElementById("noteRifiutoWsAtto");
			noteRifiutoWsAtto.readOnly = "true";

		}
		
		callBackFunction();
		
		//*****************************************************************************
		
	};
	var callBackFnErr = function(data, stato) { 
		waitingDialog.hide(); 
	};

	//DARIO****************************************************************************
//	var params = "idObject="+idObject+
//	"&idWorkflow="+idWorkflow+"&idStep="+idStep+"&CSRFToken="+legalSecurity.getToken();
	var params = "idObject="+idObject+
	"&idWorkflow="+idWorkflow+"&idStep="+idStep +"&flagAssegnatari="+flagAssegnatari+"&CSRFToken="+legalSecurity.getToken();
	//*********************************************************************************
	
	var url = WEBAPP_BASE_URL + "stepWf/processaWorkflowAtto.action";
	
	ajaxUtil.ajax(url, params, "post", "application/x-www-form-urlencoded", callBackFn, null, callBackFnErr);
}

//DARIO *********************************************************************
//function avanzaWorkflow() {
function avanzaWorkflow(matricola_dest) {
//***************************************************************************
	//DARIO ******************************************************************
	console.log("avanzaWorkflow("+ matricola_dest+")");
	//************************************************************************	
	var container = document.getElementById("containerFormWorkflow");
	var btnAvanzaWorkflow = document.getElementById("btnAvanzaWorkflow");
	var divStepView = document.getElementById("divStepView");
	var btnRifiutaWorkflow = document.getElementById("btnRifiutaWorkflow");
	var hdnDoCommit = document.getElementById("hdnDoCommit");
	var hdnIdWorkflow = document.getElementById("hdnIdWorkflow");
	var hdnClasseWorkflow = document.getElementById("hdnClasseWorkflow");
	var successOperation = document.getElementById("successOperation");
	var faultOperation = document.getElementById("faultOperation");
	var rejectionReason = document.getElementById("rejectionReason");
	btnAvanzaWorkflow.style.visibility = "hidden";
	btnRifiutaWorkflow.style.visibility = "hidden";
	divStepView.style.visibility = "hidden";

	hdnDoCommit.value= '1';
	
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		waitingDialog.hide(); 
		successOperation.style.visibility = "visible";
		rejectionReason.visibility = "hidden";
	};
	var callBackFnErr = function(data, stato) { 
		waitingDialog.hide(); 
		faultOperation.style.visibility = "visible";
		rejectionReason.visibility = "hidden";
	};

	//DARIO******************************************************************
	//var params = "codiceClasse="+hdnClasseWorkflow.value+"&"+
	//"idWorkflow="+hdnIdWorkflow.value+"&CSRFToken="+legalSecurity.getToken();
	var params = "codiceClasse="+hdnClasseWorkflow.value+"&"+
	"idWorkflow="+hdnIdWorkflow.value+"&matricola_dest="+matricola_dest+"&CSRFToken="+legalSecurity.getToken();
	//***********************************************************************
	
	
	var url = WEBAPP_BASE_URL + "stepWf/avanzaWorkflow.action";
	ajaxUtil.ajax(url, params, "post", "application/x-www-form-urlencoded", callBackFn, null, callBackFnErr);
}

function registraWfAtto() {
	var container = document.getElementById("containerFormWorkflowAtto");
	var btnRichiediRegistrazione = document.getElementById("btnRichiediRegistrazione");
	var btnRegistraAtto = document.getElementById("btnRegistraAtto");
	var divStepViewAtto = document.getElementById("divStepViewAtto");
	var btnRifiutaWorkflowAtto = document.getElementById("btnRifiutaWorkflowAtto");
	var hdnDoCommitAtto = document.getElementById("hdnDoCommitAtto");
	var hdnIdWorkflowAtto = document.getElementById("hdnIdWorkflowAtto");
	var successOperationAtto = document.getElementById("successOperationAtto");
	var faultOperationAtto = document.getElementById("faultOperationAtto");
	var rejectionReasonAtto = document.getElementById("rejectionReasonAtto");
	var destinatarioAttoResponsabile = document.getElementById("destinatarioAttoResponsabile");
	var hdnDoCommitAttoRegistrato = document.getElementById("hdnDoCommitAttoRegistrato");
	var btnAvanzaWorkflowAtto = document.getElementById("btnAvanzaWorkflowAtto");

	if(btnRichiediRegistrazione != null)
		btnRichiediRegistrazione.style.visibility = "hidden";
	if(btnAvanzaWorkflowAtto != null)
		btnAvanzaWorkflowAtto.style.visibility = "hidden";
	if(btnRifiutaWorkflowAtto != null)
		btnRifiutaWorkflowAtto.style.visibility = "hidden";
	if(btnRegistraAtto != null)
		btnRegistraAtto.style.visibility = "hidden";
	if(divStepViewAtto != null)
		divStepViewAtto.style.visibility = "hidden";
	if(destinatarioAttoResponsabile != null)
		destinatarioAttoResponsabile.style.visibility = "hidden";

	hdnDoCommitAtto.value= '1';
	hdnDoCommitAttoRegistrato.value = '1';
	
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		waitingDialog.hide(); 
		successOperationAtto.style.visibility = "visible";
		rejectionReasonAtto.visibility = "hidden";
		
	};
	var callBackFnErr = function(data, stato) { 
		waitingDialog.hide(); 
		faultOperationAtto.style.visibility = "visible";
		rejectionReasonAtto.visibility = "hidden";
	};

	var params = "idWorkflow="+hdnIdWorkflowAtto.value+"&CSRFToken="+legalSecurity.getToken();
	var url = WEBAPP_BASE_URL + "stepWf/registraAtto.action";
	ajaxUtil.ajax(url, params, "post", "application/x-www-form-urlencoded", callBackFn, null, callBackFnErr);
	
	
}

function inviaAltriUffici() {
	var container = document.getElementById("containerFormWorkflowAtto");
	var btnRichiediRegistrazione = document.getElementById("btnRichiediRegistrazione");
	var btnRifiutaWorkflowAtto = document.getElementById("btnRifiutaWorkflowAtto");
	var btnInviaAltriUffici =  document.getElementById("btnInviaAltriUffici");
	var divStepViewAtto = document.getElementById("divStepViewAtto");
	var hdnDoCommitAtto = document.getElementById("hdnDoCommitAtto");
	var hdnIdWorkflowAtto = document.getElementById("hdnIdWorkflowAtto");
	var successOperationAtto = document.getElementById("successOperationAtto");
	var faultOperationAtto = document.getElementById("faultOperationAtto");
	var rejectionReasonAtto = document.getElementById("rejectionReasonAtto");
	var destinatarioAttoGC = document.getElementById("destinatarioAttoGC");
	if(btnRichiediRegistrazione != null)
		btnRichiediRegistrazione.style.visibility = "hidden";
	if(btnRifiutaWorkflowAtto != null)
		btnRifiutaWorkflowAtto.style.visibility = "hidden";
	if(btnInviaAltriUffici != null)
		btnInviaAltriUffici.style.visibility = "hidden";
	if(divStepViewAtto != null)
		divStepViewAtto.style.visibility = "hidden";
	if(destinatarioAttoGC != null)
		destinatarioAttoGC.style.visibility = "hidden";


	hdnDoCommitAtto.value= '1';
	hdnDoCommitAttoRegistrato.value = '1';
	
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		waitingDialog.hide(); 
		successOperationAtto.style.visibility = "visible";
		rejectionReasonAtto.visibility = "hidden";
		
	};
	var callBackFnErr = function(data, stato) { 
		waitingDialog.hide(); 
		faultOperationAtto.style.visibility = "visible";
		rejectionReasonAtto.visibility = "hidden";
	};

	var params = "idWorkflow="+hdnIdWorkflowAtto.value+"&CSRFToken="+legalSecurity.getToken();
	var url = WEBAPP_BASE_URL + "stepWf/inviaAltriUffici.action";
	ajaxUtil.ajax(url, params, "post", "application/x-www-form-urlencoded", callBackFn, null, callBackFnErr);
	
	
}

//DARIO *********************************************************************
//function assegnaIncarico() {
function assegnaIncarico(matricola_dest) {	
//***************************************************************************	
	//DARIO ******************************************************************
	console.log("assegnaIncarico("+ matricola_dest+")");
	//************************************************************************	
	
	var container = document.getElementById("containerFormWorkflowAtto");
	var btnRichiediRegistrazione = document.getElementById("btnRichiediRegistrazione");
	var btnAvanzaWorkflowAtto = document.getElementById("btnAvanzaWorkflowAtto");
	var btnRegistraAtto = document.getElementById("btnRegistraAtto");
	var divStepViewAtto = document.getElementById("divStepViewAtto");
	var btnRifiutaWorkflowAtto = document.getElementById("btnRifiutaWorkflowAtto");
	var hdnDoCommitAtto = document.getElementById("hdnDoCommitAtto");
	var hdnIdWorkflowAtto = document.getElementById("hdnIdWorkflowAtto");
	var successOperationAtto = document.getElementById("successOperationAtto");
	var faultOperationAtto = document.getElementById("faultOperationAtto");
	var rejectionReasonAtto = document.getElementById("rejectionReasonAtto");
	var destinatarioAttoResponsabile = document.getElementById("destinatarioAttoResponsabile");
	var destinatarioResponsabile = document.getElementById("destinatarioResponsabile");
	var hdnDoCommitAttoRegistrato = document.getElementById("hdnDoCommitAttoRegistrato");
	if(btnRichiediRegistrazione != null)
		btnRichiediRegistrazione.style.visibility = "hidden";
	if(btnRifiutaWorkflowAtto != null)
		btnRifiutaWorkflowAtto.style.visibility = "hidden";
	if(btnRegistraAtto != null)
		btnRegistraAtto.style.visibility = "hidden";
	if(btnAvanzaWorkflowAtto != null)
		btnAvanzaWorkflowAtto.style.visibility = "hidden";
	if(divStepViewAtto != null)
		divStepViewAtto.style.visibility = "hidden";
	if(destinatarioAttoResponsabile != null)
		destinatarioAttoResponsabile.style.visibility = "hidden";

	hdnDoCommitAtto.value= '1';
	
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		waitingDialog.hide(); 
		successOperationAtto.style.visibility = "visible";
		rejectionReasonAtto.visibility = "hidden";
		
	};
	var callBackFnErr = function(data, stato) { 
		waitingDialog.hide(); 
		faultOperationAtto.style.visibility = "visible";
		rejectionReasonAtto.visibility = "hidden";
	};

	//DARIO **************************************************************************************************************************************************
	//var url = WEBAPP_BASE_URL + "stepWf/avanzaWorkflowAtto.action?assegnatario=" + destinatarioResponsabile.options[destinatarioResponsabile.selectedIndex].value
	//+"&idWorkflow="+hdnIdWorkflowAtto.value;
	
	var assegnatario = matricola_dest.trim().length!=0?matricola_dest:destinatarioResponsabile.options[destinatarioResponsabile.selectedIndex].value;
	var url = WEBAPP_BASE_URL + "stepWf/avanzaWorkflowAtto.action?assegnatario=" + assegnatario
	+"&idWorkflow="+hdnIdWorkflowAtto.value;
	//********************************************************************************************************************************************************
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null, callBackFnErr);
}

function avanzaWorkflowAttoSegretaria() {
	var container = document.getElementById("containerFormWorkflowAtto");
	var btnRichiediRegistrazione = document.getElementById("btnRichiediRegistrazione");
	var divStepViewAtto = document.getElementById("divStepViewAtto");
	var btnRifiutaWorkflowAtto = document.getElementById("btnRifiutaWorkflowAtto");
	var hdnDoCommitAtto = document.getElementById("hdnDoCommitAtto");
	var hdnIdWorkflowAtto = document.getElementById("hdnIdWorkflowAtto");
	var successOperationAtto = document.getElementById("successOperationAtto");
	var faultOperationAtto = document.getElementById("faultOperationAtto");
	var rejectionReasonAtto = document.getElementById("rejectionReasonAtto");
	var destinatarioAttoGC = document.getElementById("destinatarioAttoGC");
	var destinatarioGC = document.getElementById("destinatarioGC");
	if(btnRichiediRegistrazione != null)
		btnRichiediRegistrazione.style.visibility = "hidden";
	if(btnRifiutaWorkflowAtto != null)
		btnRifiutaWorkflowAtto.style.visibility = "hidden";
	if(divStepViewAtto != null)
		divStepViewAtto.style.visibility = "hidden";
	if(destinatarioAttoGC != null)
		destinatarioAttoGC.style.visibility = "hidden";

	hdnDoCommitAtto.value= '1';
	
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		waitingDialog.hide(); 
		successOperationAtto.style.visibility = "visible";
		rejectionReasonAtto.visibility = "hidden";
		
	};
	var callBackFnErr = function(data, stato) { 
		waitingDialog.hide(); 
		faultOperationAtto.style.visibility = "visible";
		rejectionReasonAtto.visibility = "hidden";
	};

	var url = WEBAPP_BASE_URL + "stepWf/avanzaWorkflowAtto.action?assegnatario=" + destinatarioGC.options[destinatarioGC.selectedIndex].value
	+"&idWorkflow="+hdnIdWorkflowAtto.value;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null, callBackFnErr);
}
//DARIO *********************************************************************
//function assegnaIncaricoLegaleInterno() {
function assegnaIncaricoLegaleInterno(matricola_dest) {
//***************************************************************************	
	//DARIO *******************************************************************
	console.log("assegnaIncaricoLegaleInterno("+ matricola_dest+")");
	//************************************************************************		
	var container = document.getElementById("containerFormWorkflowAtto");
	var btnRichiediRegistrazione = document.getElementById("btnRichiediRegistrazione");
	var btnAvanzaWorkflowAtto = document.getElementById("btnAvanzaWorkflowAtto");
	var btnRegistraAtto = document.getElementById("btnRegistraAtto");
	var divStepViewAtto = document.getElementById("divStepViewAtto");
	var btnRifiutaWorkflowAtto = document.getElementById("btnRifiutaWorkflowAtto");
	var hdnDoCommitAtto = document.getElementById("hdnDoCommitAtto");
	var hdnIdWorkflowAtto = document.getElementById("hdnIdWorkflowAtto");
	var successOperationAtto = document.getElementById("successOperationAtto");
	var faultOperationAtto = document.getElementById("faultOperationAtto");
	var rejectionReasonAtto = document.getElementById("rejectionReasonAtto");
	var destinatarioAttoLegaleInterno = document.getElementById("destinatarioAttoLegaleInterno");
	var destinatarioLegaleInterno = document.getElementById("destinatarioLegaleInterno");
	var hdnDoCommitAttoRegistrato = document.getElementById("hdnDoCommitAttoRegistrato");
	if(btnRichiediRegistrazione != null)
		btnRichiediRegistrazione.style.visibility = "hidden";
	if(btnRifiutaWorkflowAtto != null)
		btnRifiutaWorkflowAtto.style.visibility = "hidden";
	if(btnRegistraAtto != null)
		btnRegistraAtto.style.visibility = "hidden";
	if(btnAvanzaWorkflowAtto != null)
		btnAvanzaWorkflowAtto.style.visibility = "hidden";
	if(divStepViewAtto != null)
		divStepViewAtto.style.visibility = "hidden";
	if(destinatarioAttoLegaleInterno != null)
		destinatarioAttoLegaleInterno.style.visibility = "hidden";

	hdnDoCommitAtto.value= '1';
	
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		waitingDialog.hide(); 
		successOperationAtto.style.visibility = "visible";
		rejectionReasonAtto.visibility = "hidden";
		
	};
	var callBackFnErr = function(data, stato) { 
		waitingDialog.hide(); 
		faultOperationAtto.style.visibility = "visible";
		rejectionReasonAtto.visibility = "hidden";
	};

	//DARIO***********************************************************************************************************************************************************
	//var url = WEBAPP_BASE_URL + "stepWf/avanzaWorkflowAtto.action?assegnatario=" + destinatarioLegaleInterno.options[destinatarioLegaleInterno.selectedIndex].value
	//+"&idWorkflow="+hdnIdWorkflowAtto.value;
	
	var assegnatario = matricola_dest.trim().length!=0?matricola_dest:destinatarioLegaleInterno.options[destinatarioLegaleInterno.selectedIndex].value;
	var url = WEBAPP_BASE_URL + "stepWf/avanzaWorkflowAtto.action?assegnatario=" + assegnatario
	+"&idWorkflow="+hdnIdWorkflowAtto.value;
	//****************************************************************************************************************************************************************
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null, callBackFnErr);
}


function chiudiPanelWorkflow() {
	var hdnDoCommit = document.getElementById("hdnDoCommit");
	if(hdnDoCommit.value == '1'){
		hdnDoCommit.value= '0';
		$('#containerAttivitaPendenti').load(WEBAPP_BASE_URL + '/parts/notifichePendenti.jsp' + '#containerAttivitaPendenti');
	}
		
}
 
function chiudiPanelWorkflowAtto() {
	var hdnDoCommitAtto = document.getElementById("hdnDoCommitAtto");
	var _IdAtto = "";
	if(document.getElementById("hdnIdObjectAtto")){ _IdAtto= document.getElementById("hdnIdObjectAtto").value;}
	if(hdnDoCommitAttoRegistrato.value == '1'){
		hdnDoCommitAtto.value= '0';
		hdnDoCommitAttoRegistrato = '0';
		$('#containerAttivitaPendenti').load(WEBAPP_BASE_URL + '/parts/notifichePendenti.jsp' + '#containerAttivitaPendenti');
		document.location = WEBAPP_BASE_URL + "fascicolo/crea.action?objatto="+_IdAtto+"&CSRFToken="+legalSecurity.getToken()
	}
	else if(hdnDoCommitAtto.value == '1'){
		hdnDoCommitAtto.value= '0';
		hdnDoCommitAttoRegistrato = '0';
		$('#containerAttivitaPendenti').load(WEBAPP_BASE_URL + '/parts/notifichePendenti.jsp' + '#containerAttivitaPendenti');
	}
		
}

function gestisciRifiutoWorkflow() {
	var btnAvanzaWorkflow = document.getElementById("btnAvanzaWorkflow");
	var divStepView = document.getElementById("divStepView");
	var btnRifiutaWorkflow = document.getElementById("btnRifiutaWorkflow");
	
	var divStepView = document.getElementById("divStepView");
	
	btnAvanzaWorkflow.style.visibility = "hidden";
	//DARIO *************************************************
	//divStepView.style.visibility = "visible";
	divStepView.style.display = "";
	//*******************************************************

}

function gestisciRifiutoWorkflowAtto() {
	var btnAvanzaWorkflowAtto = document.getElementById("btnAvanzaWorkflowAtto");
	var divStepViewAtto = document.getElementById("divStepViewAtto");
	var noteRifiutoWsAtto = document.getElementById("noteRifiutoWsAtto");
	var btnRifiutaWorkflowAtto = document.getElementById("btnRifiutaWorkflowAtto");
	var btnRegistraAtto = document.getElementById("btnRegistraAtto");
	
	if(btnAvanzaWorkflowAtto)
		btnAvanzaWorkflowAtto.style.visibility = "hidden";
	if(btnRegistraAtto)
		btnRegistraAtto.style.visibility = "hidden";
	//DARIO *************************************************
	//divStepViewAtto.style.visibility = "visible";
	divStepViewAtto.style.display = "";
	//*******************************************************
		noteRifiutoWsAtto.readOnly = false;

}

function rifiutaWorkflow() {
	
	var divStepView = document.getElementById("divStepView");
	var rejectionReason = document.getElementById("rejectionReason");
	//DARIO**************************************************
	//if(divStepView.style.visibility == "hidden"){
	if(divStepView.style.display == "none"){
	//*******************************************************	
		
		gestisciRifiutoWorkflow();
	}else{
		
		if(document.getElementById("noteRifiutoWs").value.replace(/\s+$|^\s+/g,"") == "")
		//non utilizzo la trim in quanto nelle versioni antecedenti a I.E. 11 genera eccezione
		{
			//DARIO**************************************************
			//rejectionReason.style.visibility = "visible";
			rejectionReason.style.display = "";
			//*******************************************************	
		}
		else{
			var container = document.getElementById("containerFormWorkflow");
			var btnAvanzaWorkflow = document.getElementById("btnAvanzaWorkflow");
			var divStepView = document.getElementById("divStepView");
			var btnRifiutaWorkflow = document.getElementById("btnRifiutaWorkflow");
			var hdnDoCommit = document.getElementById("hdnDoCommit");
			var hdnIdWorkflow = document.getElementById("hdnIdWorkflow");
			var hdnClasseWorkflow = document.getElementById("hdnClasseWorkflow");
			var successOperation = document.getElementById("successOperation");
			var faultOperation = document.getElementById("faultOperation");
			btnAvanzaWorkflow.style.visibility = "hidden";
			btnRifiutaWorkflow.style.visibility = "hidden";
			
			//DARIO**************************************************
			//divStepView.style.visibility = "hidden";
			//rejectionReason.style.visibility = "hidden";
			divStepView.style.display = "none";
			rejectionReason.style.display = "none";
			//*******************************************************	
			hdnDoCommit.value= '1';
			
		
			waitingDialog.show('Loading...');
			var ajaxUtil = new AjaxUtil();
			var callBackFn = function(data, stato) { 
				waitingDialog.hide(); 
				//DARIO**************************************************
				//successOperation.style.visibility = "visible";
				//rejectionReason.visibility = "hidden";
				successOperation.style.display = "";
				rejectionReason.style.display = "none";
				//*******************************************************	
			};
			var callBackFnErr = function(data, stato) { 
				waitingDialog.hide(); 
				//DARIO**************************************************
				//faultOperation.style.visibility = "visible";
				//rejectionReason.visibility = "hidden";
				faultOperation.style.display = "";
				rejectionReason.style.display = "none";
				//*******************************************************	
			};

						
			var params = "codiceClasse="+hdnClasseWorkflow.value+"&"+
			"idWorkflow="+hdnIdWorkflow.value+"&"+
			"note="+document.getElementById("noteRifiutoWs").value+"&CSRFToken="+legalSecurity.getToken()
			var url = WEBAPP_BASE_URL + "stepWf/rifiutaWorkflow.action";
			ajaxUtil.ajax(url, params, "post", "application/x-www-form-urlencoded", callBackFn, null, callBackFnErr);
		}
	}
}


function rifiutaWorkflowAtto() {
	var divStepViewAtto = document.getElementById("divStepViewAtto");
	var rejectionReasonAtto = document.getElementById("rejectionReasonAtto");
	var noteRifiutoWsAtto = document.getElementById("noteRifiutoWsAtto");
	var isReadOnly = noteRifiutoWsAtto.readOnly;
	
	//DARIO**************************************************
	//if(divStepViewAtto.style.visibility == "hidden" || isReadOnly == true)
	if(divStepViewAtto.style.display == "none" || isReadOnly == true)
	//*******************************************************		
		gestisciRifiutoWorkflowAtto();
	else{
		if(document.getElementById("noteRifiutoWsAtto").value.replace(/\s+$|^\s+/g,"") == "")
		//non utilizzo la trim in quanto nelle versioni antecedenti a I.E. 11 genera eccezione
		{
			//DARIO**************************************************
			//rejectionReasonAtto.style.visibility = "visible";
			rejectionReasonAtto.style.display = "";
			//*******************************************************	
		}
		else{
			var containerAtto = document.getElementById("containerFormWorkflowAtto");
			var btnAvanzaWorkflowAtto = document.getElementById("btnAvanzaWorkflowAtto");
			var btnRegistraAtto = document.getElementById("btnRegistraAtto");
			var divStepViewAtto = document.getElementById("divStepViewAtto");
			var btnRifiutaWorkflowAtto = document.getElementById("btnRifiutaWorkflowAtto");
			var hdnDoCommitAtto = document.getElementById("hdnDoCommitAtto");
			var hdnIdWorkflowAtto = document.getElementById("hdnIdWorkflowAtto");
			var successOperationAtto = document.getElementById("successOperationAtto");
			var faultOperationAtto = document.getElementById("faultOperationAtto");
			var destinatarioAttoGC = document.getElementById("destinatarioAttoGC");
			var destinatarioAttoLegaleInterno = document.getElementById("destinatarioAttoLegaleInterno");
			var destinatarioAttoResponsabile = document.getElementById("destinatarioAttoResponsabile");
			if(btnAvanzaWorkflowAtto)
				btnAvanzaWorkflowAtto.style.visibility = "hidden";
			if(btnRifiutaWorkflowAtto)
				btnRifiutaWorkflowAtto.style.visibility = "hidden";
			if(btnRegistraAtto)
				btnRegistraAtto.style.visibility = "hidden";
			if(destinatarioAttoGC)
				destinatarioAttoGC.style.visibility = "hidden";
			if(destinatarioAttoLegaleInterno)
				destinatarioAttoLegaleInterno.style.visibility = "hidden";
			if(destinatarioAttoResponsabile)
				destinatarioAttoResponsabile.style.visibility = "hidden";
			//DARIO**************************************************
			//divStepViewAtto.style.visibility = "hidden";
			//rejectionReasonAtto.style.visibility = "hidden";
			divStepViewAtto.style.display = "none";
			rejectionReasonAtto.style.display = "none";
			//*******************************************************	
			
			hdnDoCommitAtto.value= '1';
			
		
			waitingDialog.show('Loading...');
			var ajaxUtil = new AjaxUtil();
			var callBackFn = function(data, stato) { 
				waitingDialog.hide(); 
				//DARIO**************************************************
				//successOperationAtto.style.visibility = "visible";
				//rejectionReasonAtto.visibility = "hidden";
				successOperationAtto.style.display = "";
				rejectionReasonAtto.style.display = "none";
				//*******************************************************	
			};
			var callBackFnErr = function(data, stato) { 
				waitingDialog.hide(); 
				//DARIO**************************************************
				//faultOperationAtto.style.visibility = "visible";
				//rejectionReasonAtto.visibility = "hidden";
				faultOperationAtto.style.display = "";
				rejectionReasonAtto.style.display = "none";
				//*******************************************************	
			};

			var params = "idWorkflow="+hdnIdWorkflow.value+"&"+
			"note="+document.getElementById("noteRifiutoWsAtto").value+"&CSRFToken="+legalSecurity.getToken()
			var url = WEBAPP_BASE_URL + "stepWf/rifiutaWorkflowAtto.action";
			ajaxUtil.ajax(url, params, "post", "application/x-www-form-urlencoded", callBackFn, null, callBackFnErr);
		}
	}
}

/*Metodo creato per il controllo degli utenti assenti (metodo non ancora legato a nessun evento)*/
function controlIsAssente(select_,div){
	
	if(select_){
		$("#info-assegnPresente").remove();
		waitingDialog.show('Loading...');
		 
		$.post( WEBAPP_BASE_URL +"stepWf/controlIsAssente.action", { assegnatario: select_.options[select_.selectedIndex].value,CSRFToken:legalSecurity.getToken()})
		  .done(function( data ) {
			 if(data && data.length>5){
			  $("#"+div).append(data);
			 }
			 waitingDialog.hide();
		  }).fail(function( jqxhr, textStatus, error ) {
			    var err = textStatus + ", " + error;
			    waitingDialog.hide();
			});	
		
	}
 }
