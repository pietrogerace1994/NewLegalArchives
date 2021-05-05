
var AjaxUtil = function(){
	this.GET = 'GET';
	this.POST = 'POST';
	this.PUT = 'PUT';
	this.CONTENT_TYPE_JSON = "application/json";
	this.CONTENT_TYPE_HTML = "text/html";
	this.CONTENT_TYPE_TEXT = "plain/text";
	this.CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
	this.BASE_URL = WEBAPP_BASE_URL; 
		
	this.ajax = function(url, params, method, contentType, fnCallbackSuccess, dataType, fnCallbackError){
		$.ajax({
		    url : url,
		    contentType: contentType,
			dataType: dataType,
		    type: method,
		    data: params,
		    success : function (data, stato) {
		        
		    	fnCallbackSuccess(data, stato);
		    	
		    },
		    error : function (richiesta,stato,errori) {
		        console.error("Si e' verificato un errore nella chiamata ajax, url: "+url+", method: "+method+", contentType: "+contentType+". Status: "+stato);
		        //alert("Errore nell'invocazione del servizio");
		        if( fnCallbackError != null && fnCallbackError != undefined)
		        	fnCallbackError(richiesta,stato,errori);  
		    }
		});
	};
	
	this.ajaxUpload = function(url, data, fnCallbackSuccess, fnCallbackError){
		$.ajax({
			type : this.POST,
			data : data,
			url : url,
			cache : false,
			contentType : false,
			processData : false,
			success : function(data) { 
				fnCallbackSuccess(data);
				
			},
			error: function(){
				console.error("Si e' verificato un errore nella chiamata ajax, url: "+url);		 
		        if( fnCallbackError != null && fnCallbackError != undefined)
		        	fnCallbackError(richiesta,stato,errori); 
			}
		});
		 
	};
	
	
}
