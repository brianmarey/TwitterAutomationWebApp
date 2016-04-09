	var time = 0;
	var timeIncrement = 20000;
	var theHost = '';
	var twitterUser = '';
	
	function beginAutoFollow(localhost) {
		theHost = localhost;
		
		$("#followForm").hide();
		$("#buttonDiv").hide();
		$("#statusArea").show();
		$("#spinnerIcon").show();
		$("#statusText").show();
		$("anotherRunButtonDiv").hide();
		
		twitterUser = $("#twitteruser").val();
		
		var runTime = 0;
		var runTimeIncrement = 3600000;
		
		runNumber = 1;
		
		var numberOfRuns = 1; //$('#runs').val();
		
		for (var j=0;j<numberOfRuns;j++) {			
			setTimeout(beginRun,runTime);
			runTime+=runTimeIncrement;
		}
		
		setTimeout(completelyFinished, runTime);
	}
	
	
	function completelyFinished() {
		$("#statusResults").append("Completely finished.<br/>");
		$("#spinnerIcon").hide();
		$("anotherRunButtonDiv").show();
	}
	
	
	function beginRun() {
		time = 0;

		var tags = $("#tagfield").tagit("tags");
		var tagString = "";
		
		for (var i in tags) {
			tagString += encodeURIComponent(tags[i].value);
			tagString += ",";
		}
		
		//var keyword = encodeURIComponent(tags[i].value);
		//alert("looking at " + tagString);
			
		var url = theHost + ":8080/TwitterAutomation/getTweeps?keyword=" + tagString + "&twitterUser=" + twitterUser;
		
		//alert (url);
		
		$.get(url, processTweeps);
	}
	
	function processTweeps(data) {
		//alert(data.length);
		for (var ii=0;ii<data.length;ii++) {
			//alert(data[ii].screenName);
			var id = data[ii].id;
			var screenName = data[ii].screenName;
			var following = data[ii].following;
			
			if (!following) {
				setTimeout(followTweep,time,id,screenName);
				//alert("time is " + time);
				time += timeIncrement;
			}
		}
		
		setTimeout(pauseNotice,time);
		//time += timeIncrement;
	}

	var runNumber = 1;
	
	function pauseNotice() {
		$("#statusResults").append("Finished run #"+ runNumber + ". Pausing...<br/>");
		runNumber++;
	}
	
	
	function followTweep(id,screenName) {		
		var url = theHost + ":8080/TwitterAutomation/followTweep?id=" + id + "&screenName=" + screenName + "&twitterUser=" + twitterUser;
		//alert(url);
		$.get(url,function( data ) {			
			var notice = data.message + "" + screenName + "<br/>";
			//alert(notice);
			$("#statusResults").append( notice);
		});
	}
	