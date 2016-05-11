	var time = 0;
	var timeIncrement = 2000;
	var theHost = '';
	var twitterUser = '';
	var unfollowOffset = 0;
	var unfollowMax = 990;
	var context = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	
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
	
	function beginAutoUnfollow() {
		//twitterUser = $("#twitteruser").val();
		//unfollowOffset = $("#offset").val();
		var unfollowCount = $("#unfollowCount").val();
		unfollowCount = unfollowCount.trim();
		alert(unfollowCount);
		if (isNaN(unfollowCount)) {
			$("#numberWarning").show();
			return;
		}
				
		$("#getStartedSection").hide();
		//$("#buttonDiv").hide();
		$("#statusArea").show();
		$("#spinnerIcon").show();
		//$("#statusText").show();
		//$("anotherRunButtonDiv").hide();
		
		beginAutoUnfollowRun(unfollowCount);
	}
	
	function completelyFinished() {
		$("#spinnerIcon").hide();
		$("#statusResults").append("Completely finished.<br/>");
		$("#finishedSection").show();
		$("#progressBarSection").hide();
		$("#actionsButton").hide();
	}

	function beginAutoUnfollowRun(count) {
		time = 0;
			
		var url = context + "/getUnfollowTweeps";
		
		alert (url);
		
		$.get(url, processUnfollowTweeps);
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
			
		var url = theHost + "/TwitterAutomation/getTweeps?keyword=" + tagString + "&twitterUser=" + twitterUser;
		
		//alert (url);
		
		$.get(url, processTweeps);
	}
	
	var maxFollows = 100;
	
	function processTweeps() {
		//alert(currentFollowSet.length);
		actualFollows = 0;
		
		maxFollows = $("#followCount").val();
		if (isNaN(maxFollows)) {
			maxFollows = 10;
		}
		
		if (maxFollows > 100) {
			maxFollows = 100;
		}
		
		if (maxFollows < 0) {
			maxFollows = 10;
		}
		
		if (!currentFollowSet) {
			return;
		}
		
		if (currentFollowSet.length < maxFollows) {
			maxFollows = currentFollowSet.length;
		}

		$("#statusSection").show();
		$("#statusRow").show();
		$("#foundTweepsSection").hide();
		
		var currentCount = 0;
		
		for (var ii=0;ii<currentFollowSet.length;ii++) {
			//alert(data[ii].screenName);
			var id = currentFollowSet[ii].id;
			var screenName = currentFollowSet[ii].screenName;
			var following = currentFollowSet[ii].following;
			
			if (!following) {
				setTimeout(followTweep,time,id,screenName);
				//alert("time is " + time);
				time += timeIncrement;
				currentCount++;		
			}
			
			if (currentCount >= maxFollows) break;
		}
		
		setTimeout(completelyFinished,time);
	}

	
	function processUnfollowTweeps(data) {
		alert(data.length);
		alert(data[0]);
		
		if (data.length < 2) {
			return;
		}
		
		var offset = Math.round(data.length/2);
		alert("offset is " + offset);
		
		//var offset = parseInt(unfollowOffset);
		var unfollowCount = parseInt($("#unfollowCount").val());
		alert("Unfoolow count is " + unfollowCount);
		
		for (var ii=offset;ii<offset + unfollowCount;ii++) {
			alert("ii is " + ii);
			var id = data[ii];
			alert("The id is " + id);
			
			setTimeout(unfollowTweep,time,id);
			//alert("time is " + time);
			time += timeIncrement;
		}
		
		setTimeout(completelyFinishedUnfollow,time);
		//time += timeIncrement;
	}

	var runNumber = 1;
	
	function pauseNotice() {
		$("#statusResults").append("Finished run #"+ runNumber + ". Pausing...<br/>");
		runNumber++;
	}
	
	var actualFollows = 0;
	
	function followTweep(id,screenName) {		
		var url = context + "/followTweep?id=" + id + "&screenName=" + screenName;
		//alert(url);
		$.get(url,function( data ) {			
			var notice = data.message + "" + screenName + "<br/>";
			//alert(notice);
			$("#statusResults").append( notice);
			
			actualFollows++;
			
			var percent = parseFloat(Math.round((actualFollows / maxFollows)*100)).toFixed(0);
			//alert(percent);
			$("#progressBar").attr("aria-valuenow",percent);
			
			var percentStyle = percent + "%";
			$("#progressBar").width(percentStyle);
		});
	}
	
	
	function unfollowTweep(id) {		
		var url = context + "/unfollowTweep?id=" + id;
		alert(url);
		$.get(url,function( data ) {
			alert(data.followResult)
			var notice = data.followResult + "<br/>";
			//alert(notice);
			$("#statusResults").append( notice);
		});
	}
	
	function findByHashtag() {
		$("#hashTagWarning").hide();
		
		var hashtag = $("#hashtag").val();
		//alert("Hashtag is " + hashtag);
		
		hashtag = hashtag.trim();
		
		if (hashtag == '') {
			$("#hashTagWarning").show();
			return;
		}
		
		$("#getTweepsButtonGroup").hide();
		$("#pleaseWaitSection").show();
		$("#messagesSection").hide();
		
		hashtag = encodeURIComponent(hashtag);
			
		var url = context + "/getTweeps?keyword=" + hashtag;
		
		//alert (url);
		
		$.get(url, displayTweeps);
	}
	
	var currentFollowSet = {};
	
	function displayTweeps(data) {
		if (!data || data.length < 3) {
			$("#pleaseWaitSection").hide();
			$("#getStartedSection").hide();
			$("#introSection").hide();
			$("#noResultsSection").show();
			return;
		}
		
		currentFollowSet = data;
		
		$("#getTweepsButtonGroup").show();
		$("#pleaseWaitSection").hide();
		
		$("#getStartedSection").hide();
		$("#foundTweepsSection").show();
		$("#introSection").hide();
		
		$("#tweepsCount").html(data.length);
		$("#followCount").val(data.length);
	}
	
	