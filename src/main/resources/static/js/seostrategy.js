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
        		
        		if (selectedKeywords) totalKeywords+=selectedKeywords.length;
        		if (addedKeywords) totalKeywords+=addedKeywords.length;
        		
        		if (totalKeywords > 10) {
        			displayError("Please do not add more than 10 keywords.");
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
 				$("#strategyForm").submit()
        	}        	
        	
        	
        	