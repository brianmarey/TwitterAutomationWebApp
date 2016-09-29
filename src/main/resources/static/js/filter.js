        function filter() {
        	var success = $("#successSelect").val();
        	var status = $("#statusSelect").val();
        	
        	var url = "./seoStrategies";
        	if (success.length > 0 || status.length > 0) {
        		url = url +"?";
        	}
        	
        	if (success.length > 0) {
        		url = url + "success=" + success;
        	}
        	
        	if (status.length > 0 && success.length > 0) {
        		url = url + "&";
        	}
        	
        	if (status.length > 0) {
        		url = url + "status=" + status;
        	}
        	
        	document.location = url;
        }