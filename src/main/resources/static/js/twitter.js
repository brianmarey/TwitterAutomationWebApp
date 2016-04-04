	function beginAutoFollow() {
		$("#followForm").hide();
		$("#buttonDiv").hide();
		$("#statusArea").show();
		$("#spinnerArea").show();
		
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
				setTimeout(followTweep,time,id);
				time += timeIncrement;
			}
		}
	}
	
	function followTweep(id) {		
		var url = "http://localhost:8080/TwitterAutomation/followTweep?id=" + id;
		var notice = "<p>Followed ID:" + id + "</p>";
		$("#statusArea").append( notice);
		//alert ("following " + id);
	}
