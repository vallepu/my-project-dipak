	function validsTxnVals(strbtn){
		
		var strmesg = "";
		var errFlag = false;
		
		var textboxele = strbtn.limit_to;
		var txtamt = textboxele.value;
		
		var comptextele = strbtn.txnLimit;
		var compamt = comptextele.value;
		
		txtamt = Math.round(txtamt);
		compamt = Math.round(compamt);
		if(txtamt > compamt){
			strmesg += "Please enter Max amount upto "+compamt+"\n";
			errFlag = true;
		}
		
		var txtminele = strbtn.valid_from;
		var txtmin = txtminele.value;
		
		var comptxtminele = strbtn.validFrom;
		var compmin = comptxtminele.value;
		
		if(txtmin > compmin){
			strmesg += "Please enter minimum validity upto "+compmin+"\n";
			errFlag = true;
		}
		
		
		var txtmaxele = strbtn.valid_to;
		var txtmax = txtmaxele.value;
		
		var comptxtmaxele = strbtn.validTo;
		var compmax = comptxtmaxele.value;
		
		if(txtmax > compmax){
			strmesg += "Please enter maximum validity upto "+compmax+"\n";
			errFlag = true;
		}
		
		if(errFlag == true)
		{
			alert(strmesg);
			return false;
		}
	}