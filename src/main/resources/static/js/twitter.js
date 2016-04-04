	function beginAutoFollow() {
		$("#followForm").hide();
		$("#buttonDiv").hide();
		$("#statusArea").show();
		$("#spinnerIcon").show();
		$("#statusText").show();
		
		var tags = $("#tagfield").tagit("tags");
		for (var i in tags) {
			var keyword = encodeURIComponent(tags[i].value);
			//alert("looking at " + keyword);
			var url = "http://localhost:8080/TwitterAutomation/getTweeps?keyword=" + keyword;
			//alert("url is " + url);
			
			$.get( url, processTweeps);
			break;
		}		
	}
	
	function processTweeps(data) {
		//alert(data.length);
		var time = 0;
		var timeIncrement = 2000;
		for (var ii=0;ii<data.length;ii++) {
			//alert(data[ii].screenName);
			var id = data[ii].id;
			var screenName = data[ii].screenName;
			var following = data[ii].following;
			
			if (!following) {
				setTimeout(followTweep,time,id,screenName);
				time += timeIncrement;
				break;
			}
		}
	}
	
	function followTweep(id,screenName) {		
		var url = "http://localhost:8080/TwitterAutomation/followTweep?id=" + id;
		$.get(url,function( data ) {			
			var notice = data.message + "" + screenName + "<br/>";
			alert(notice);
			$("#statusResults").append( notice);
		});
	}
	