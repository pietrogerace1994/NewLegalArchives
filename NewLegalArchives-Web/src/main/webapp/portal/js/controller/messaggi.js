function visualizzaMessaggio(data) {
	$("html").scrollTop();
	var sezioneMessaggi = document.getElementById("sezioneMessaggiApplicativi");
	if( sezioneMessaggi == null || sezioneMessaggi == undefined ){ 
		var div = document.createElement("DIV");
		div.setAttribute('id','sezioneMessaggiApplicativi');
		div.setAttribute('style','position:fixed;top:0px;left:0px;width:100%;z-index:1000');
		document.body.appendChild(div);
		sezioneMessaggi = div;
	}
	
	
	if (data.stato == "KO") {
		var divMessaggio = "<div class='alert alert-danger'>" + paserseMessageCode(data.messaggio)
				+ " <button type=\"button\" onclick=\"rimuoviMessaggio()\" class=\"close\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button></div>";
		sezioneMessaggi.innerHTML = divMessaggio;
	} else if (data.stato == "OK") {
		var divMessaggio = "<div class='alert alert-info'>" + paserseMessageCode(data.messaggio)
				+" <button type=\"button\" onclick=\"rimuoviMessaggio()\" class=\"close\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button></div>";
		sezioneMessaggi.innerHTML = divMessaggio;
	} else if (data.stato == "WARN") {
		var divMessaggio = "<div class='alert alert-warning'>" + paserseMessageCode(data.messaggio)
				+" <button type=\"button\" onclick=\"rimuoviMessaggio()\" class=\"close\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button></div>";
		sezioneMessaggi.innerHTML = divMessaggio;
	}
	
	  
}

function rimuoviMessaggio(){ 
	var sezioneMessaggi = document.getElementById("sezioneMessaggiApplicativi"); 
	sezioneMessaggi.innerHTML="";
} 

function paserseMessageCode(messageCode){ 
	var lang = navigator.language;
	if( messageCode == 'messaggio.ok' ){ 
		if( lang == 'it' || lang == 'IT'){
			return "Operazione completata correttamente";
		}else{
			return "Operation successful";
		}
	}
	else if( messageCode == 'messaggio.ko' ){ 
		if( lang == 'it' || lang == 'IT'){
			return "Operazione non riuscita";
		}else{
			return "The operation failed";
		}
	}
	else if( messageCode == 'messaggio.warn' ){ 
		if( lang == 'it' || lang == 'IT'){
			return "Operazione terminata ma con un warning";
		}else{
			return "Operation terminated but with a warning";
		}
	}else if( messageCode == 'stato.incarico.non.valido'){
		if( lang == 'it' || lang == 'IT'){
			return "Operazione non consentita, stato incarico non valido";
		}else{
			return "Operation is not allowed, invalid state";
		}
	}else if( messageCode == 'stato.fascicolo.non.valido'){
		if( lang == 'it' || lang == 'IT'){
			return "Operazione non consentita, stato fascicolo non valido";
		}else{
			return "Operation is not allowed, invalid state";
		}
	} else if( messageCode == 'user.unauthorized'){
		if( lang == 'it' || lang == 'IT'){
			return "Operazione non consentita, utente non autorizzato";
		}else{
			return "Operation is not allowed, user not authorized";
		}
	} else if( messageCode == 'errore.campo.file.tipologia'){
		if( lang == 'it' || lang == 'IT'){
			return "Il file non &egrave; in formato Excell (.xls o .xslx)";
		}else{
			return "The specified file is not Excel file (.xls o .xslx)";
		}
	} else if( messageCode == 'errore.campi.partecorrelata.obbligatori'){
		if( lang == 'it' || lang == 'IT'){
			return "I campi Denominazione e Tipo Correlazione sono obbligatori";
		}else{
			return "The Denomination and Correlation Type fields are mandatory";
		}
	} else if( messageCode == 'errore.file.vuoto'){
		if( lang == 'it' || lang == 'IT'){
			return "File vuoto o non valido";
		}else{
			return "Empty or not well formed file";
		}
	} else if( messageCode == 'errore.campo.nrdoc.obbligatorio'){
		if( lang == 'it' || lang == 'IT'){
			return "ERRORE: Il campo NR.DOC. è obbligatorio.";
		}else{
			return "ERROR: The NR.DOC. field is mandatory.";
		}
	} else if( messageCode == 'errore.campo.docid.obbligatorio'){
		if( lang == 'it' || lang == 'IT'){
			return "ERRORE: Il campo DOC.ID è obbligatorio.";
		}else{
			return "ERROR: The Doc.ID field is mandatory.";
		}
	} else if( messageCode == 'errore.campo.numfatturalegal.obbligatorio'){
		if( lang == 'it' || lang == 'IT'){
			return "ERRORE: Il campo Fattura LEGAL è obbligatorio.";
		}else{
			return "ERROR: The Fattura LEGAL field is mandatory.";
		}
	} else if( messageCode == 'errore.retrieveproperty'){
		if( lang == 'it' || lang == 'IT'){
			return "ERRORE: Non è stato possibile recuperare l'endpoint per InvoiceManagerService. Contattare l'amministratore.";
		}else{
			return "ERROR: Impossible to retrieve InvoiceManagerService endpoint. Please contact the administrator.";
		}
	} else if( messageCode == 'errore.invoicemanagerservice.response'){
		if( lang == 'it' || lang == 'IT'){
			return "ERRORE: InvoiceManagerService ha risposto con status >= 400. Contattare l'amministratore.";
		}else{
			return "ERROR: InvoiceManagerService replied with a status >= 400. Please contact the administrator.";
		}
	}else if( messageCode == 'errore.invoicemanagerservice.request'){
		if( lang == 'it' || lang == 'IT'){
			return "ERRORE: Errore nella generazione della richiesta verso InvoiceManagerService. Contattare l'amministratore.";
		}else{
			return "ERROR: Error in generating request to InvoiceManagerService. Please contact the administrator.";
		}
	}else if( messageCode == 'errore.invoicemanagercontroller.general'){
		if( lang == 'it' || lang == 'IT'){
			return "ERRORE: Errore generico nella classe java InvoiceManagerController. Contattare l'amministratore.";
		}else{
			return "ERROR: Generic Error in java class InvoiceManagerService. Please contact the administrator.";
		}
	}
	
	
	
}