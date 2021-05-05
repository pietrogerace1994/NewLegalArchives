var WebSocketClient = function(callbackOnMessage, userId, hostname ){
	var websocket = null;
	var fnCallbackOnMessage = callbackOnMessage;
	this.connectSocket = function(){
		if( websocket != null ){
			consol.warn("Per lanciare una nuova connessione devi chiudere quella corrente");
			return;
		}
		  
		var urlWebSocket = WEBSOCKET_URL;
		urlWebSocket = urlWebSocket.replace("HOSTNAME", hostname);
		
	    console.log("Inizializzo connessione al websocket");
		websocket = new WebSocket(urlWebSocket+userId );

		websocket.onopen = function() { 
			console.log("Connesso al websocket, in attesa di messaggi");
			websocket.send("connect"); 
			 
		}

		websocket.onmessage = function(evento) {		    
		 
			fnCallbackOnMessage(evento);
			 
		}
		
		websocket.onclose = function(e) {
			console.log('websocket onclose: ' + e); 
			
		}
		
		websocket.onerror = function(e) {
			console.log('websocket error: ' + e);  
			
		}
		
	};
	
	this.disconnectSocket = function(){
		if( websocket != null ){
			websocket.close();
			websocket = null; 
		}
		
	};
};