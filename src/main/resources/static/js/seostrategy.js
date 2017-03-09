        	function displayError(errorMessage) {
                $.bootstrapGrowl(errorMessage, {
                    ele: 'body', // which element to append to
                    type:'danger', // (null, 'info', 'danger', 'success', 'warning')
                    offset: {
                        from: 'top',
                        amount: 100
                    }, // 'top', or 'bottom'
                    align: 'center', // ('left', 'right', or 'center')
                    width: 'auto', // (integer, or 'auto')
                    delay: 20000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
                    allow_dismiss: true, // If true then will display a cross to close the popup.
                    stackup_spacing: 10 // spacing between consecutively stacked growls.
            	});	
        	}
        	
        	//test
        	
        	function displayMessage(errorMessage) {
                $.bootstrapGrowl(errorMessage, {
                    ele: 'body', // which element to append to
                    type:'info', // (null, 'info', 'danger', 'success', 'warning')
                    offset: {
                        from: 'top',
                        amount: 100
                    }, // 'top', or 'bottom'
                    align: 'center', // ('left', 'right', or 'center')
                    width: 'auto', // (integer, or 'auto')
                    delay: 40000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
                    allow_dismiss: true, // If true then will display a cross to close the popup.
                    stackup_spacing: 10 // spacing between consecutively stacked growls.
            	});	
        	}

			function submitForm() {
        		var selectedKeywords = $('#selectedKeywords').val();
        		var addedKeywords = $('#addedKeywords').val();
        		
        		if (selectedKeywords == null || selectedKeywords.length == 0) {
        			if (addedKeywords == null || addedKeywords.trim().length == 0) {
        				displayError('Please enter at least one keyword to track.');
        				return;
        			}
        		}        		
        		
        		var totalKeywords = 0;
        		
        		var parts = addedKeywords.split(",");

        		if (selectedKeywords) totalKeywords+=selectedKeywords.length;
        		if (addedKeywords) totalKeywords+=parts.length;
        		
        		if (totalKeywords > 5) {
        			displayError("Please do not add more than 5 keywords.");
        			return;
        		}
        		
        		var selectedVal = "";
        		if (selectedKeywords) {
        			$('#selectedKeywords :selected').each(function(i, sel){ 
        			    selectedVal+=$(sel).val()+",";
        			});
        		}

        		$('#selectedKeywordsVal').val(selectedVal);
        		
        		$('#submitButton').prop('disabled', true);
        		$('#loadingDiv').show();
        		
        		displayMessage("This will take at least 2 minutes. It takes a while to grab the original rank for keywords. So relax. Have a beer.");
        		
 				$("#strategyForm").submit()
        	}    	
        	
        	
        	