
function ajaxSetup_SyncOrAsync(asyncOrSync) {
	if(asyncOrSync=="sync") {
		$.ajaxSetup({
	    	async: false
		});
	}
	else if(asyncOrSync=="async") {
		$.ajaxSetup({
	    	async: true
		});
	}
}