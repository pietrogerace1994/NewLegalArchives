function apriAutorizzatoreAvviso() {
	console.log("apriAutorizzazioneAvviso()");
	$('#modalAutorizzatoreAvviso').modal('show');
}
function chiudiAutorizzatoreAvviso() {
	console.log("chiudiAutorizzazioneAvviso()");
	$('#modalAutorizzatoreAvviso').modal('hide');
}
function apriAutorizzatoreScelta() {
	console.log("apriAutorizzazioneScelta()");

	//popola select
	var str = '';
	str += '<option value="-1"></option>';
	
	for(var i=0; i<listaAutorizzatoriJson.length; i++) {
		var obj=listaAutorizzatoriJson[i];
		
		var val=obj["id"];
		var lbl=obj["nominativoutil"];
		var matr=obj["matricolautil"];
		
		if(matricolaApprovatore!=matr && topResponsabileMatricola!=matr)
			str += '<option value="'+matr+'">'+lbl+'</option>';
	}
	
	$("#sceltaAutorizzatore").html('');
	$("#sceltaAutorizzatore").html(str);
	
	if($("#errorMsgNessunaSelezioneAutorizzatore").hasClass("hidden")==false)
		$("#errorMsgNessunaSelezioneAutorizzatore").addClass("hidden");
	
	$('#modalAutorizzatoreScelta').modal('show');
}
function chiudiAutorizzatoreScelta() {
	console.log("chiudiAutorizzatoreScelta()");
	$('#modalAutorizzatoreScelta').modal('hide');
}
function apriApprovatoreAvviso() {
	console.log("apriApprovatoreAvviso()");
	$('#modalApprovatoreAvviso').modal('show');
}
function apriApprovatoreScelta() {
	console.log("apriApprovatoreScelta()");
	
	//popola select
	var str = '';
	str += '<option value="-1"></option>';
	
	for(var i=0; i<listaApprovatoriJson.length; i++) {
		var obj=listaApprovatoriJson[i];
		
		var val=obj["id"];
		var lbl=obj["nominativoutil"];
		var matr=obj["matricolautil"];
		
		if(matricolaApprovatore!=matr && topResponsabileMatricola!=matr)
			str += '<option value="'+matr+'">'+lbl+'</option>';
		
		
	}
	
	$("#sceltaApprovatore").html('');
	$("#sceltaApprovatore").html(str);
	
	if($("#errorMsgNessunaSelezioneApprovatore").hasClass("hidden")==false)
		$("#errorMsgNessunaSelezioneApprovatore").addClass("hidden");
	
	$('#modalApprovatoreScelta').modal('show');
}
function chiudiApprovatoreScelta() {
	console.log("chiudiApprovatoreScelta()");
	$('#modalApprovatoreScelta').modal('hide');
}

$(document).ready(function () {
	
	$( "#topResponsabileNomeCognome" ).mouseover(function() {
		if($("#topResponsabileNomeCognomeIcon").hasClass("hidden")==true)
			$("#topResponsabileNomeCognomeIcon").removeClass("hidden");
	});
	$( "#topResponsabileNomeCognome" ).mouseout(function() {
		if($("#topResponsabileNomeCognomeIcon").hasClass("hidden")==false)
			$("#topResponsabileNomeCognomeIcon").addClass("hidden");
	});
	
	$( "#approvatoreNomeCognome" ).mouseover(function() {
		if($("#approvatoreNomeCognomeIcon").hasClass("hidden")==true)
			$("#approvatoreNomeCognomeIcon").removeClass("hidden");
	});
	$( "#approvatoreNomeCognome" ).mouseout(function() {
		if($("#approvatoreNomeCognomeIcon").hasClass("hidden")==false)
			$("#approvatoreNomeCognomeIcon").addClass("hidden");
	});
	
	$("#topResponsabileNomeCognome").click(function(event){	
		event.stopPropagation();
		
		if(autorizzatoreIsInattesa==true) {
			apriAutorizzatoreAvviso();
		}
		else {
			apriAutorizzatoreScelta();
		}
		
	});

	$("#approvatoreNomeCognome").click(function(event){	
		event.stopPropagation();
		
		if(approvatoreIsInattesa==true) {
			apriApprovatoreAvviso();
		}
		else {
			apriApprovatoreScelta();
		}
		
	});
	
	
	$("#btnSalva").click(function(event){	
		event.stopPropagation();
		
		var form = document.getElementById("configurazioneWorkflowForm"); 
		form.submit(); 
		
	});


	$("#btnApplicaAutorizzatore").click(function(event){	
		event.stopPropagation();
		
		var sceltaAutorizzatoreEle = document.getElementById("sceltaAutorizzatore");
		
		var selIndex = sceltaAutorizzatoreEle.selectedIndex;
		if(selIndex<0 || val=="-1") {
			$("#errorMsgNessunaSelezioneAutorizzatore").removeClass("hidden");
			return;
		}
		var lbl;
		var val;
		if(selIndex>=0) {
			val = sceltaAutorizzatoreEle[selIndex].value;
			lbl = sceltaAutorizzatoreEle[selIndex].label;
		}
		
		$("#topResponsabileNomeCognome").val(lbl);
		topResponsabileMatricola=val;
		
		//cerco matricola della selezione
		var trovato=false;
		var idxTrovato;
		for(var i=0; i<listaAutorizzatoriJson.length; i++) {
			var obj=listaAutorizzatoriJson[i];
			var matr=obj["matricolautil"];
			if(val==matr) {
				trovato=true;
				idxTrovato=i;
				break;
			}
		}
		if(trovato) {
			var x=listaAutorizzatoriJson[idxTrovato];
			
			var matr=x["matricolautil"];
			topResponsabileMatricola=matr;
			$("#topResponsabileMatricola").val(topResponsabileMatricola);
			
			var email=x["emailutil"];
			$("#emailAutorizzatore").val(email);
		}
		
		chiudiAutorizzatoreScelta();
		
		if($("#btnSalva").hasClass("hidden")==true)
			$("#btnSalva").removeClass("hidden");
		
	});
	
	$("#btnApplicaApprovatore").click(function(event){	
		event.stopPropagation();
		
		var sceltaApprovatoreEle = document.getElementById("sceltaApprovatore");
		var val = sceltaApprovatoreEle.value;
		var selIndex = sceltaApprovatoreEle.selectedIndex;
		if(selIndex<0 || val=="-1") {
			$("#errorMsgNessunaSelezioneApprovatore").removeClass("hidden");
			return;
		}
		var lbl;
		var val;
		if(selIndex>=0) {
			val = sceltaApprovatoreEle[selIndex].value;
			lbl = sceltaApprovatoreEle[selIndex].label;
		}
			
		
		$("#approvatoreNomeCognome").val(lbl);
		matricolaApprovatore=val;
		
		//cerco matricola della selezione
		var trovato=false;
		var idxTrovato;
		for(var i=0; i<listaApprovatoriJson.length; i++) {
			var obj=listaApprovatoriJson[i];
			var matr=obj["matricolautil"];
			if(val==matr) {
				trovato=true;
				idxTrovato=i;
				break;
			}
		}
		if(trovato) {
			var x=listaApprovatoriJson[idxTrovato];
			
			var matr=x["matricolautil"];
			matricolaApprovatore=matr;
			$("#matricolaApprovatore").val(matricolaApprovatore);
			
			var email=x["emailutil"];
			$("#emailApprovatore").val(email);
			
		}
		
		chiudiApprovatoreScelta();
		
		if($("#btnSalva").hasClass("hidden")==true)
			$("#btnSalva").removeClass("hidden");
	
	});
	
	
});