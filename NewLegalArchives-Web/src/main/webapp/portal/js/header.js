
function apriRicercaDropdown() {
	//console.log("header.apriRicercaDropdown()");
	
	var str=$("#headerRicercaTxtBox").val();
	str=str.trim();
	if(str=="") {
		//alert("La parola chiave \u00e8 obbligatoria.");
		$("#headerRicercaTxtBox").val('');
		$('#modalRicerca').modal('show');
		return;
	}
	$("#ricercaDropDown").addClass('open');
}
function chiudiRicercaDropdown() {
	//console.log("header.chiudiRicercaDropdown()");
	$("#ricercaDropDown").removeClass('open');
}

$(document).ready(function () {
	
	$("#btnCercaIn").click(function(event){
		apriRicercaDropdown();
		event.stopPropagation();
	});
	
	$("body").click(function(event){
		chiudiRicercaDropdown();
	});
	
	
//	$('#modalRicerca').modal({
//		  keyboard: false
//	});

	$("#headerRicercaTxtBox").keypress(function(event){
		//console.log('ricerca keypress');
		
		if ( event.which == 13 ) {
		     event.preventDefault();
		     
		     var val=$("#headerRicercaTxtBox").val();
		     val=val.trim();
		     if(val.length>0) 
		     {
		    	 submitRicerca('TUTTO');
		     } 
		     else 
		     {
		    	 $('#modalRicerca').modal('show');
		     }
		  }
		
	});
	
	
});

function submitRicerca(tipo) {
	//console.log("header.submitRicerca()");
	
	$('body').off('blur', '#ricercaDropDown');
	
	chiudiRicercaDropdown();
	
	if(tipo=='ATTI') {
		$("#ricercaOggetto").val('A');
	}
	else if(tipo=='FASCICOLI') {
		$("#ricercaOggetto").val('F');
	}
	else if(tipo=='INCARICHI') {
		$("#ricercaOggetto").val('I');
	}
	else if(tipo=='TUTTO') {
		$("#ricercaOggetto").val('T');
	}
	else if(tipo=='COSTI') {
		$("#ricercaOggetto").val('C');
	}	
	
	var form = document.getElementById("headerRicercaForm"); 
	form.submit(); 
}

