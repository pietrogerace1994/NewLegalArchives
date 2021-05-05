$(document).ready(function(){
	var num=$('#hdnNroPec').val();
	aggiornaBadgePec(num);
	
	$("#download-pecAttach").on("click",function(){
		var uuid=$(this).attr("uuid");
		return fnDownloadAttach(uuid);
	})
	
});

function fnDownloadAttach(uuid){
	$("#downloadPecAttach #uuid").val(uuid);
	$("#downloadPecAttach").submit();
}

function fnDownloadAttachOp(uuid){
	$("#downloadPecAttachOp #uuid").val(uuid);
	$("#downloadPecAttachOp").submit();
}

function processaPec(id, mittente, destinatario, oggetto, uuid) {
	waitingDialog.show('Loading...');
	
	console.log("id PecMail: "+id);
	var hdnIdPec = document.getElementById("hdnIdPec");
	var btnTrasformaPec = document.getElementById("btnTrasformaPec");
	var btnAnnullaPec = document.getElementById("btnAnnullaPec");
	var btnSpostaProtPec = document.getElementById("btnSpostaProtPec");
	var btnInviaAltriUffPec = document.getElementById("btnInviaAltriUffPec");
	var txtMittente = document.getElementById("txtMittente");
	var txtDestinatario = document.getElementById("txtDestinatario");
	var txtOggetto = document.getElementById("txtOggetto");
	
	if(!isEmpty(uuid)){
		$("#download-pecAttach").attr("uuid", uuid);
		$("#pecAttachDiv").show();
	} else {
		$("#pecAttachDiv").hide();
	}
	
	var divAltriUffView = document.getElementById("divAltriUffView");
	divAltriUffView.style.visibility = "hidden";
	document.getElementById("txtUtenteAltriUff").value = "";
	document.getElementById("txtEmailAltriUff").value = "";
	
	hdnIdPec.value = id;
	txtMittente.value = mittente;
	txtDestinatario.value = destinatario;
	txtOggetto.value = oggetto;
	btnTrasformaPec.style.visibility = "visible";
	btnAnnullaPec.style.visibility = "visible";
	btnSpostaProtPec.style.visibility = "visible";
	btnInviaAltriUffPec.style.visibility = "visible";
	
	waitingDialog.hide(); 
}

function isEmpty(value) {
	  return typeof value == 'string' && !value || typeof value == 'undefined' || value === null;
	}

function chiudiPanelPec() {
	$('#containerPecMail').load(WEBAPP_BASE_URL + '/parts/notifichePecMail.jsp' + '#containerPecMail');
}

function trasformaPec(){
	var id = document.getElementById("hdnIdPec"); 
	console.log("trasformaPec id : "+id.value);
	
	var divAltriUffView = document.getElementById("divAltriUffView");
	divAltriUffView.style.visibility = "hidden";
	
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		waitingDialog.hide();
		$("#hdnNroPec").val($("#hdnNroPec").val() - 1);
		var num=$('#hdnNroPec').val();
		aggiornaBadgePec(num);
		chiudiPanelPec();
	};

	var callBackFnErr = function(data, stato) {  
	};
	
	var url = WEBAPP_BASE_URL + "notificaPec/trasformaPec.action?id=" + id.value;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null, callBackFnErr );
}

function annullaPec(){
	var id = document.getElementById("hdnIdPec"); 
	console.log("annullaPec id : "+id.value);
	
	var divAltriUffView = document.getElementById("divAltriUffView");
	divAltriUffView.style.visibility = "hidden";
	
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		waitingDialog.hide();
		$("#hdnNroPec").val($("#hdnNroPec").val() - 1);
		var num=$('#hdnNroPec').val();
		aggiornaBadgePec(num);
		chiudiPanelPec();
	};

	var callBackFnErr = function(data, stato) {  
	};
	
	var url = WEBAPP_BASE_URL + "notificaPec/annullaPec.action?id=" + id.value;
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null, callBackFnErr );
	
}

function spostProtPec(){
	var id = document.getElementById("hdnIdPec"); 
	console.log("spostProtPec id : "+id.value);
	
	var divAltriUffView = document.getElementById("divAltriUffView");
	divAltriUffView.style.visibility = "hidden";
	
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		waitingDialog.hide();
		$("#hdnNroPec").val($("#hdnNroPec").val() - 1);
		var num=$('#hdnNroPec').val();
		aggiornaBadgePec(num);
		chiudiPanelPec();
	};

	var callBackFnErr = function(data, stato) {  
	};
	
	var url = WEBAPP_BASE_URL + "notificaPec/spostProtPec.action?id=" + id.value;
url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null, callBackFnErr );
	
}

function gestisciInvioAltriUffici() {
	var btnTrasformaPec = document.getElementById("btnTrasformaPec");
	var btnAnnullaPec = document.getElementById("btnAnnullaPec");
	var btnSpostaProtPec = document.getElementById("btnSpostaProtPec");
	var divAltriUffView = document.getElementById("divAltriUffView");
	
	btnTrasformaPec.style.visibility = "hidden";
	btnAnnullaPec.style.visibility = "hidden";
	btnSpostaProtPec.style.visibility = "hidden";
	divAltriUffView.style.visibility = "visible";

}

function inviaAltriUffPec(){
	var divAltriUffView = document.getElementById("divAltriUffView");
	
	if(divAltriUffView.style.visibility == "hidden"){
		gestisciInvioAltriUffici();
	}else{
	
		if(document.getElementById("txtUtenteAltriUff").value.replace(/\s+$|^\s+/g,"") == "" ||
			document.getElementById("txtEmailAltriUff").value.replace(/\s+$|^\s+/g,"") == "")
		//non utilizzo la trim in quanto nelle versioni antecedenti a I.E. 11 genera eccezione
		{
			console.log("Utente e Email non popolate correttamente!");
		}
		else{
	
			var id = document.getElementById("hdnIdPec");
			var utenteAltriUff = document.getElementById("txtUtenteAltriUff").value;
			var emailAltriUff = document.getElementById("txtEmailAltriUff").value;
			console.log("inviaAltriUffPec id : "+id.value+" utente: "+utenteAltriUff+" emailAltriUff: "+emailAltriUff);
			
			if(isEmail(emailAltriUff)) {
				
				waitingDialog.show('Loading...');
				var ajaxUtil = new AjaxUtil();
				var callBackFn = function(data, stato) { 
					waitingDialog.hide();
					$("#hdnNroPec").val($("#hdnNroPec").val() - 1);
					var num=$('#hdnNroPec').val();
					aggiornaBadgePec(num);
					chiudiPanelPec();
					$('#panelFormPec').modal('hide');
					
				};
				
				var callBackFnErr = function(data, stato) {  
				};
				
				var url = WEBAPP_BASE_URL + "notificaPec/inviaAltriUffPec.action?id=" + id.value+"&utenteAltriUff="+utenteAltriUff+"&emailAltriUff="+emailAltriUff;
				ajaxUtil.ajax(url, "", "post", "text/html", callBackFn, null, callBackFnErr );
				
			} else {
				console.log("Email non popolata correttamente!");
			}
		}
	}
}

function isEmail(email) {
	  var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	  return regex.test(email);
}

function aggiornaBadgePec(num){
	if(num>0) {
		$("#badgeNotifichePecMail").html(num);
		$("#badgeNotifichePecMailMd").html(num);
	}
	if(num==0) {
		$("#badgeNotifichePecMail").html("");
		$("#badgeNotifichePecMailMd").html("");
	}
}


function processaPecOp(id, idUtentePec, mittente, destinatario, oggetto, uuid) {
	
	console.log("processaPecOp id: "+id);
	var container = document.getElementById("containerFormPecOp");
	var hdnIdPec = document.getElementById("hdnIdPecOp");
	var txtUuidPec = document.getElementById("txtUuidPec");
	var hdnIdNotificaPecOp = document.getElementById("hdnIdNotificaPecOp");
	var btnTrasformaPecOp = document.getElementById("btnTrasformaPecOp");
	var btnRifiutaPecOp = document.getElementById("btnRifiutaPecOp");

	hdnIdPec.value = idUtentePec;
	hdnIdNotificaPecOp.value = id;
	
	btnTrasformaPecOp.style.visibility = "visible";
	btnRifiutaPecOp.style.visibility = "visible";
	
	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		container.innerHTML=data;
		waitingDialog.hide(); 
		var successOperation = document.getElementById("successOperationOp");
		successOperation.style.visibility = "hidden";
		var faultOperation = document.getElementById("faultOperationOp");
		faultOperation.style.visibility = "hidden";
		var rejectionReason = document.getElementById("rejectionReasonOp");
		rejectionReason.style.visibility = "hidden";
		var divPecOpView = document.getElementById("divPecOpView");
		divPecOpView.style.visibility = "hidden";
		document.getElementById("noteRifiutoPecOp").value = "";
		
		if(!isEmpty(uuid)){
			uuid = uuid.replace("{", "").replace("}", "");
			
			txtUuidPec.value = uuid;
			$("#download-pecAttachOp").attr("uuid", uuid);
			
			$("#download-pecAttachOp").on("click",function(){
				var uuid=$(this).attr("uuid");
				return fnDownloadAttachOp(uuid);
			});
			
			$("#pecAttachDivOp").show();
		} else {
			$("#pecAttachDivOp").hide();
			txtUuidPec.value = "";
		}
		
	};
	var callBackFnErr = function(data, stato) { 
		waitingDialog.hide(); 
	};

	var params = "idUtentePec="+hdnIdPecOp.value+"&"+
	"idNotifica="+hdnIdNotificaPecOp.value+"&"+
	"txtMittenteOp="+mittente+"&"+"txtDestinatarioOp="+destinatario+"&"+"txtOggettoOp="+oggetto;
	var url = WEBAPP_BASE_URL + "notificaPec/processaPecOp.action";
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, params, "post", "application/x-www-form-urlencoded", callBackFn, null, callBackFnErr);

}

function chiudiPanelPecOp() {
	$('#containerAttivitaPendenti').load(WEBAPP_BASE_URL + '/parts/notifichePendenti.jsp' + '#containerAttivitaPendenti');
}

function gestisciRifiutoPecOp() {
	var btnTrasformaPecOp = document.getElementById("btnTrasformaPecOp");
	var divPecOpView = document.getElementById("divPecOpView");
	
	btnTrasformaPecOp.style.visibility = "hidden";
	divPecOpView.style.visibility = "visible";

}

function rifiutaPecOp() {
	var divPecOpView = document.getElementById("divPecOpView");
	var rejectionReasonOp = document.getElementById("rejectionReasonOp");
	if(divPecOpView.style.visibility == "hidden")
		gestisciRifiutoPecOp();
	else{
		if(document.getElementById("noteRifiutoPecOp").value.replace(/\s+$|^\s+/g,"") == "")
		//non utilizzo la trim in quanto nelle versioni antecedenti a I.E. 11 genera eccezione
		{
			rejectionReasonOp.style.visibility = "visible";
		}
		else{
			var container = document.getElementById("containerFormPecOp");
			var btnTrasformaPecOp = document.getElementById("btnTrasformaPecOp");
			var divPecOpView = document.getElementById("divPecOpView");
			var btnRifiutaPecOp = document.getElementById("btnRifiutaPecOp");
			
			var hdnIdPecOp = document.getElementById("hdnIdPecOp");
			var hdnIdNotificaPecOp = document.getElementById("hdnIdNotificaPecOp");
			
			var successOperationOp = document.getElementById("successOperationOp");
			var faultOperationOp = document.getElementById("faultOperationOp");
			btnTrasformaPecOp.style.visibility = "hidden";
			btnRifiutaPecOp.style.visibility = "hidden";
			divPecOpView.style.visibility = "hidden";
			rejectionReasonOp.style.visibility = "hidden";
	
			waitingDialog.show('Loading...');
			var ajaxUtil = new AjaxUtil();
			var callBackFn = function(data, stato) { 
				waitingDialog.hide(); 
				successOperationOp.style.visibility = "visible";
				rejectionReasonOp.visibility = "hidden";
			};
			var callBackFnErr = function(data, stato) { 
				waitingDialog.hide(); 
				faultOperationOp.style.visibility = "visible";
				rejectionReasonOp.visibility = "hidden";
			};

			var params = "idUtentePec="+hdnIdPecOp.value+"&"+
			"idNotifica="+hdnIdNotificaPecOp.value+"&"+
			"noteOp="+document.getElementById("noteRifiutoPecOp").value;
			var url = WEBAPP_BASE_URL + "notificaPec/rifiutaPecOp.action";
			url=legalSecurity.verifyToken(url);
			ajaxUtil.ajax(url, params, "post", "application/x-www-form-urlencoded", callBackFn, null, callBackFnErr);
		}
	}
}

function trasformaPecOp() {
	var container = document.getElementById("containerFormPecOp");
	var btnTrasformaPecOp = document.getElementById("btnTrasformaPecOp");
	var divPecOpView = document.getElementById("divPecOpView");
	var btnRifiutaPecOp = document.getElementById("btnRifiutaPecOp");

	var hdnIdPecOp = document.getElementById("hdnIdPecOp");
	var hdnIdNotificaPecOp = document.getElementById("hdnIdNotificaPecOp");
	var txtUuidPec = document.getElementById("txtUuidPec");
	
	var successOperationOp = document.getElementById("successOperationOp");
	var faultOperationOp = document.getElementById("faultOperationOp");
	var rejectionReasonOp = document.getElementById("rejectionReasonOp");
	btnTrasformaPecOp.style.visibility = "hidden";
	btnRifiutaPecOp.style.visibility = "hidden";
	divPecOpView.style.visibility = "hidden";

	waitingDialog.show('Loading...');
	var ajaxUtil = new AjaxUtil();
	var callBackFn = function(data, stato) { 
		waitingDialog.hide(); 
		successOperationOp.style.visibility = "visible";
		rejectionReasonOp.visibility = "hidden";
		chiudiPanelPecOp();
		redirectCreaAtto(txtUuidPec.value);
	};
	var callBackFnErr = function(data, stato) { 
		waitingDialog.hide(); 
		faultOperationOp.style.visibility = "visible";
		rejectionReasonOp.visibility = "hidden";
	};

	var params = "idUtentePec="+hdnIdPecOp.value+"&"+
	"idNotifica="+hdnIdNotificaPecOp.value;
	var url = WEBAPP_BASE_URL + "notificaPec/trasformaPecOp.action";
	url=legalSecurity.verifyToken(url);
	ajaxUtil.ajax(url, params, "post", "application/x-www-form-urlencoded", callBackFn, null, callBackFnErr);
}

function redirectCreaAtto(uuid){
	$("#redirectAttoPec #uuidPec").val(uuid);
	$("#redirectAttoPec").submit();
}

