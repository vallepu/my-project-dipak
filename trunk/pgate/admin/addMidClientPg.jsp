<%@ page language="java" %>
<%@ page import="java.util.*"%>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="app.Config"%>
<% Config.setAllConfig(); %>
<%@page import="admin.servlet.MidClientPgServlet"%><jsp:include page="header.jsp" flush="true" />
<script language="javascript" type="text/javascript" src="<%=Config.get("SITE_PATH")%>/admin/js/jquery.js"></script>
<script language="javascript" type="text/javascript">
	var SITE_PATH = '<%=Config.get("SITE_PATH")%>';

	function newXMLHttpRequest() {

		  var xmlreq = false;

		  if (window.XMLHttpRequest) {

		    // Create XMLHttpRequest object in non-Microsoft browsers
		    xmlreq = new XMLHttpRequest();

		  } else if (window.ActiveXObject) {

		    // Create XMLHttpRequest via MS ActiveX
		    try {
		      // Try to create XMLHttpRequest in later versions
		      // of Internet Explorer

		      xmlreq = new ActiveXObject("Msxml2.XMLHTTP");

		    } catch (e1) {

		      // Failed to create required ActiveXObject

		      try {
		        // Try version supported by older versions
		        // of Internet Explorer

		        xmlreq = new ActiveXObject("Microsoft.XMLHTTP");

		      } catch (e2) {

		        // Unable to create an XMLHttpRequest with ActiveX
		      }
		    }
		  }

		  return xmlreq;
		}

	/*
	 * Returns a function that waits for the specified XMLHttpRequest
	 * to complete, then passes its XML response
	 * to the given handler function.
	 * req - The XMLHttpRequest whose state is changing
	 * responseXmlHandler - Function to pass the XML response to
	 */
	function getReadyStateHandler(req, responseXmlHandler) {

	  // Return an anonymous function that listens to the 
	  // XMLHttpRequest instance
	  return function () {

	    // If the request's status is "complete"
	    if (req.readyState == 4) {
	      
	      // Check that a successful server response was received
	      if (req.status == 200) {

	        // Pass the XML payload of the response to the 
	        // handler function
	        //responseXmlHandler(req.responseXML);
	        //alert(req.responseText);

	    	  document.getElementById("merchantIds").innerHTML=req.responseText;

	      } else {

	        // An HTTP problem has occurred
	        alert("HTTP error: "+req.status);
	      }
	    }
	  }
	}
		
			
	
	function showMerchant(value){
		var pgIdval = value;
		 //alert(pgIdval);
		 // Obtain an XMLHttpRequest instance
		 var req = newXMLHttpRequest();
		
		 // Set the handler function to receive callback notifications
		 // from the request object
		 var handlerFunction = getReadyStateHandler(req, showMerchant);
		 req.onreadystatechange = handlerFunction;
		 
		 // Open an HTTP POST connection to the shopping cart servlet.
		 // Third parameter specifies request is asynchronous.
		 req.open("GET", SITE_PATH+"/admin/GetMids?pgIdval="+pgIdval, true);
		
		 // Specify that the body of the request contains form data
		 req.setRequestHeader("Content-Type", 
		                      "application/x-www-form-urlencoded");
		
		 // Send form encoded data stating that I want to add the 
		 // specified item to the cart.
		 
		 
		 req.send();
	}
</script>
<script language="javascript" type="text/javascript">
	function validate()
	{
		var client_id = $.trim(jQuery('#client_id').val());
		var pg_id = $.trim(jQuery('#pg_id').val());
		var merchant_id = $.trim(jQuery('#merchantIds').val());
		var currency_no = $.trim(jQuery('#currency_no').val());
		var limit_to = $.trim(jQuery('#limit_to').val());
		var valid_from = $.trim(jQuery('#valid_from').val());
		var valid_to = $.trim(jQuery('#valid_to').val());
		var stat_active = jQuery('#stat_active').attr('checked');
		var stat_inactive = jQuery('#stat_inactive').attr('checked');
		var caption = $.trim(jQuery('#caption').val());
		
	    var error = "Error:-\n";
	    var isError = false;
	    
	    if(client_id == ""){
	        isError = true;
	        error += "Select an Application\n";
	    }  
	
	    if(pg_id == ""){
	        isError = true;
	        error += "Select a Payment Gateway \n";
	    }
	
	    if(merchant_id == ""){
	        isError = true;
	        error += "Select a Merchant Code\n";
	    }
	
	    if(currency_no == ""){
	        isError = true;
	        error += "Select a Currency\n";
	    }
	
	    if(limit_to == ""){
	        isError = true;
	        error += "Enter Txn Max Amount \n";
	    }
	
	    if(valid_from == ""){
	        isError = true;
	        error += "Select a Validity From\n";
	    }
	
	    if(valid_to == ""){
	        isError = true;
	        error += "Select a Validity To\n";
	    }
	
	    if(stat_active == false && stat_inactive == false){
	        isError = true;
	        error += "Select a Status\n";
	    }
	
	    if(caption == ""){
	        isError = true;
	        error += "Enter a Caption\n";
	    }
	
	    if(isError){
	        alert(error);
	        return false;
	    }else{
	    	return true;
	    }  
	}
</script>
<script language="javascript" type="text/javascript" src="<%=Config.get("SITE_PATH")%>/admin/js/ajax.js"></script>
<script language="javascript" type="text/javascript" src="<%=Config.get("SITE_PATH")%>/admin/js/validateTxn.js"></script>
<% 	//out.println("McpId :- "+request.getAttribute("mcpId"));
	String mcpId = (String) request.getAttribute("mcpId"); 
	//String mcpId = (String) session.getAttribute("mcpId");
%>
<div id="body">
<table width="100%" cellspacing="2" cellpadding="0" border="0">
	<tbody>
	<tr>
		<td width="1%" valign="top" align="left"></td>
		<td width="19%" valign="top" align="left">

			<!--  left menu  -->	
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tbody><tr>
					<td valign="top" align="left">
					<!-- Left Side Start -->
						<jsp:include page="menu.jsp" flush="true"></jsp:include> 
					<!-- Left Side Ends --> 
					</td>
				</tr>
			</tbody></table>
			<!--  left menu  -->	

		</td>
		<td width="79%" valign="top" align="center">
 	
    <% if(mcpId == "" || mcpId == null){
    	String method = "post";
    	String action = Config.get("SITE_PATH")+"/admin/MidClientPgServlet";
    %>
    <form method="<%=method %>" action="<%=action%>" name="frm_midclientpg" onsubmit="return validsTxnVals(this);">
	    <table cellspacing="0" cellpadding="5" border="0" style="table-layout: fixed;" class="sortable logincss" id="table">
	    <tr style="height: 0px;" class="menuHeading">
			<td align="left" style="width: 20%; height: 2px; line-height: 4px;"></td>
			<td align="left"></td>
		</tr>
				
		<tr style="height: 24px;" class="menuHeading">
			<td align="left" colspan="2">
			Add Mid Client Pg :
			</td>
		</tr>
		<tr class="content_blacknormal_back_white_11">
			<td>
				Applications <!-- client_master -->
			</td>
			<td class="txtl">
				<select name="client_id" id="client_id" style="width: 162px;">
	                <option value="">SELECT</option>
		            <%        
		                HashMap<String,Object> clientobjField = new HashMap<String,Object> ();
		                
		                ArrayList<Object> clientrows = (ArrayList) request.getAttribute("arrClientLists");
		            
		                Iterator clienti = clientrows.iterator();
		                while(clienti.hasNext()){
		                	clientobjField.putAll((HashMap)clienti.next());
		                    
		                	String client_id = clientobjField.get("client_id").toString();
		                    String client_name = clientobjField.get("client_name").toString();
		            %>
		                    <option value="<%=client_id %>"><%=client_name %></option>
		            <%
		               } 
		            %> 
	            </select>
			</td>
		</tr>
		<tr class="content_blacknormal_back_white_11">
			<td>
				Payment Gateway <!-- pg_master -->
			</td>
			<td class="txtl">
				<select name="pg_id" id="pg_id" class="txtl" onchange="showMerchant(this.value);" style="width: 162px;">
	                <option value="">SELECT</option>
		            <%        
		                HashMap<String,Object> pgobjField = new HashMap<String,Object> ();
		                
		                ArrayList<Object> pgrows = (ArrayList) request.getAttribute("arrPgLists");
		            
		                Iterator pgi = pgrows.iterator();
		                while(pgi.hasNext()){
		                	pgobjField.putAll((HashMap)pgi.next());
		                    
		                	String pg_id = pgobjField.get("pg_id").toString();
		                    String pg_name = pgobjField.get("pg_name").toString();
		            %>
		                    <option value="<%=pg_id %>"><%=pg_name %></option>
		            <%
		               } 
		            %>  
	            </select>
			</td>
		</tr>
		<tr class="content_blacknormal_back_white_11">
			<td>
				Merchant Code <!-- merchant_master -->
			</td>
			<td class="txtl">
				<select name="merchant_id" class="txtl" id="merchantIds" onchange="showTxnvals(this.value);" style="width: 162px;">
	                <option value="">SELECT</option>   
	            </select>
			</td>
		</tr>
		<tr class="content_blacknormal_back_white_11">
			<td>
				Currency <!-- client_master -->
			</td>
			<td class="txtl">
				<select name="currency_no" id="currency_no" class="txtl" style="width: 162px;">
	                <option value="">SELECT</option>
		            <%        
		                HashMap<String,Object> currobjField = new HashMap<String,Object> ();
		                
		                ArrayList<Object> currrows = (ArrayList) request.getAttribute("arrCurrencyLists");
		            
		                Iterator curri = currrows.iterator();
		                while(curri.hasNext()){
		                	currobjField.putAll((HashMap)curri.next());
		                    
		                	String currency_name = currobjField.get("currency_name").toString();
		                    String currency_no = currobjField.get("currency_no").toString();
		            %>
		                    <option value="<%=currency_no %>"><%=currency_name + " (" + currency_no + ")" %></option>
		            <%
		               }
		            %>
	            </select>
			</td>
		</tr>
		<tr style="display:none;">
			<td>&nbsp;</td>
			
			<td id="ajaxTxnData">
				
			</td>
		
		</tr>
		<tr class="content_blacknormal_back_white_11">
			<td>
				Transaction
			</td>
			<td class="txtl">
				Min amount &nbsp; <input type="text" name="limit_from" id="limit_from" value="0" readonly="readonly"> &nbsp; 
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				Max amount &nbsp; <input type="text" name="limit_to" id="limit_to" value="">
			</td>
		</tr>
		<tr class="content_blacknormal_back_white_11">
			<td>
				Validity
			</td>
			<td class="txtl">
				From: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input class="textbox w153" name="valid_from" id="valid_from" type="text" readonly="" href="javascript:void(0)" onfocus="" value="">
				&nbsp;&nbsp;
				<a href="javascript:void(0)" style="text-decoration:none;" onclick="if(self.gfPop)gfPop.fPopCalendar(document.frm_midclientpg.valid_from);return false;" hidefocus="">
				<img border="0" align="absMiddle" alt="Calender Pop Up" src="http://static.coxandkings.co.in/cnk_html/images/cal.gif" name="popcal"></a>
				&nbsp; 
				To: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input class="textbox w153" name="valid_to" id="valid_to" type="text" readonly="" href="javascript:void(0)" onfocus="" value="">
				&nbsp;&nbsp;
				<a href="javascript:void(0)" style="text-decoration:none;" onclick="if(self.gfPop)gfPop.fPopCalendar(document.frm_midclientpg.valid_to);return false;" hidefocus="">
				<img border="0" align="absMiddle" alt="Calender Pop Up" src="http://static.coxandkings.co.in/cnk_html/images/cal.gif" name="popcal"></a>
			</td>
		</tr>
	
	    <tr class="content_blacknormal_back_white_11">
	        <td>
	            Status
	        </td>
	        <td class="txtl">
	        	<input type="radio" name="is_active" id="stat_active" value="yes"> Active &nbsp; 
			    <input type="radio" name="is_active" id="stat_inactive" value="no"> InActive
	        </td>
	    </tr>
	    
	    <tr class="content_blacknormal_back_white_11">
			<td>
				Caption
			</td>
			<td class="txtl">
				<input type="text" name="caption" id="caption" value="">
			</td>
		</tr>
	
		<tr class="content_blacknormal_back_white_11">
			<td colspan=2 align="center">
				<input name="btn_sub" value="Submit" type="submit" id="btn_sub" onclick="return validate();">
			</td>
		</tr>
	
	</table>
</form>
    <%  }else{  
    	String method = "post";
    	String action = Config.get("SITE_PATH")+"/admin/MidClientPgServlet?mcpId="+mcpId;
    %>
    <form method="<%=method %>" action="<%=action%>" name="frm_midclientpg" onsubmit="return validsTxnVals(this);">
    <table cellspacing="0" cellpadding="5" border="0" style="table-layout: fixed;" class="sortable logincss" id="table">
    	    <tr style="height: 0px;" class="menuHeading">
			<td align="left" style="width: 20%; height: 2px; line-height: 4px;"></td>
			<td align="left"></td>
		</tr>
				
		<tr style="height: 24px;" class="menuHeading">
			<td align="left" colspan="2">
			Edit Mid Client Pg :
			</td>
		</tr>
		<tr class="content_blacknormal_back_white_11">
			<td>
				Applications <!-- client_master -->
			</td>
			<td class="txtl">
				<select name="client_id" id="client_id" style="width: 162px;">
	                <option value="">SELECT</option>
		            <%        
		                HashMap<String,Object> clientobjField = new HashMap<String,Object> ();
		                
		                ArrayList<Object> clientrows = (ArrayList) request.getAttribute("arrClientLists");
		            
		                Iterator clienti = clientrows.iterator();
		                while(clienti.hasNext()){
		                	clientobjField.putAll((HashMap)clienti.next());
		                    
		                	String client_id = clientobjField.get("client_id").toString();
		                    String client_name = clientobjField.get("client_name").toString();
		            %>
		                    <option value="<%=client_id %>"><%=client_name %></option>
		            <%
		               } 
		            %> 
		            <option value="<%=request.getAttribute("client_id")%>" selected="selected"><%=request.getAttribute("client_name")%></option>  
	            </select>
			</td>
		</tr>
		<tr class="content_blacknormal_back_white_11">
			<td>
				Payment Gateway <!-- pg_master -->
			</td>
			<td class="txtl">
				<select name="pg_id" id="pg_id" class="txtl" onchange="showMerchant(this.value);" style="width: 162px;">
	                <option value="">SELECT</option>
		            <%        
		                HashMap<String,Object> pgobjField = new HashMap<String,Object> ();
		                
		                ArrayList<Object> pgrows = (ArrayList) request.getAttribute("arrPgLists");
		            
		                Iterator pgi = pgrows.iterator();
		                while(pgi.hasNext()){
		                	pgobjField.putAll((HashMap)pgi.next());
		                    
		                	String pg_id = pgobjField.get("pg_id").toString();
		                    String pg_name = pgobjField.get("pg_name").toString();
		            %>
		                    <option value="<%=pg_id %>"><%=pg_name %></option>
		            <%
		               } 
		            %>  
		            <option value="<%=request.getAttribute("pg_id")%>" selected="selected"><%=request.getAttribute("pg_name")%></option> 
	            </select>
			</td>
		</tr>
		<tr class="content_blacknormal_back_white_11">
			<td>
				Merchant Code <!-- merchant_master -->
			</td>
			<td class="txtl">
				<select name="merchant_id" class="txtl" id="merchantIds" onchange="showTxnvals(this.value);" style="width: 162px;">
	                <option value="">SELECT</option>
		             <option value="<%=request.getAttribute("merchant_id")%>" selected="selected"><%=request.getAttribute("merchant_code")%></option>   
	            </select>
			</td>
		</tr>
		<tr class="content_blacknormal_back_white_11">
			<td>
				Currency <!-- client_master -->
			</td>
			<td class="txtl">
				<select name="currency_no" id="currency_no" class="txtl" style="width: 162px;">
	                <option value="">SELECT</option>
		            <%        
		                HashMap<String,Object> currobjField = new HashMap<String,Object> ();
		                
		                ArrayList<Object> currrows = (ArrayList) request.getAttribute("arrCurrencyLists");
		            
		                Iterator curri = currrows.iterator();
		                while(curri.hasNext()){
		                	currobjField.putAll((HashMap)curri.next());
		                    
		                	String currency_name = currobjField.get("currency_name").toString();
		                    String currency_no = currobjField.get("currency_no").toString();
		            %>
		                    <option value="<%=currency_no %>" selected="selected"><%=currency_name + " (" + currency_no + ")" %></option>
		            <%
		               }
		            %>
		            <option value="<%=request.getAttribute("currency_no")%>" selected="selected"><%=request.getAttribute("currency_name") + " (" + request.getAttribute("currency_no") + ")" %></option>       
	            </select>
			</td>
		</tr>
		<tr style="display:none;">
			<td>&nbsp;</td>
			
			<td id="ajaxTxnData">
				
			</td>
		
		</tr>
		<tr class="content_blacknormal_back_white_11">
			<td>
				Transaction
			</td>
			<td class="txtl">
				Min amount &nbsp; <input type="text" name="limit_from" id="limit_from" value="<%=request.getAttribute("limit_from")%>"> 
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				Max amount &nbsp; <input type="text" name="limit_to" id="limit_to" value="<%=request.getAttribute("limit_to")%>">
			</td>
		</tr>
		<tr class="content_blacknormal_back_white_11">
			<td>
				Validity
			</td>
			<td class="txtl">
				From: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input class="textbox w153" name="valid_from" id="valid_from" type="text" readonly="" href="javascript:void(0)" onfocus="" value="<%=request.getAttribute("valid_from")%>">
				&nbsp;&nbsp;<a href="javascript:void(0)" style="text-decoration:none;" onclick="if(self.gfPop)gfPop.fPopCalendar(document.frm_midclientpg.valid_from);return false;" hidefocus="">
				<img border="0" align="absMiddle" alt="Calender Pop Up" src="http://static.coxandkings.co.in/cnk_html/images/cal.gif" name="popcal"></a>
				&nbsp; 
				To: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input class="textbox w153" name="valid_to" id="valid_to" type="text" readonly="" href="javascript:void(0)" onfocus="" value="<%=request.getAttribute("valid_to")%>">
				&nbsp;&nbsp;<a href="javascript:void(0)" style="text-decoration:none;" onclick="if(self.gfPop)gfPop.fPopCalendar(document.frm_midclientpg.valid_to);return false;" hidefocus="">
				<img border="0" align="absMiddle" alt="Calender Pop Up" src="http://static.coxandkings.co.in/cnk_html/images/cal.gif" name="popcal"></a>
			</td>
		</tr>
	
	    <tr class="content_blacknormal_back_white_11">
	        <td>
	            Status
	        </td>
	        <td class="txtl">
	        <%  
	        	String active = request.getAttribute("is_active").toString();
		        if(active.equalsIgnoreCase("yes")){  
		        	String checkedYes = "checked='checked'";
		    %>
		    	<input type="radio" name="is_active" value="yes" id="stat_active" <%=checkedYes  %>> Active &nbsp; 
	        	<input type="radio" name="is_active" value="no" id="stat_inactive"> InActive
		    <%
		        }else if(active.equalsIgnoreCase("no")){  
	        		String checkedNo = "checked='checked'";
	        %>
	        	<input type="radio" name="is_active" id="stat_active" value="yes"> Active &nbsp; 
	            <input type="radio" name="is_active" id="stat_inactive" value="no" <%=checkedNo  %>> InActive
	        <%  }  %>
	        </td>
	    </tr>
	    
	    <tr class="content_blacknormal_back_white_11">
			<td>
				Caption
			</td>
			<td class="txtl">
				<input type="text" name="caption" id="caption" value="<%=request.getAttribute("caption")%>">
			</td>
		</tr>
	
		<tr class="content_blacknormal_back_white_11">
			<td colspan=2 align="center">
				<input name="btn_sub" value="Submit" type="submit" id="btn_sub" onclick="return validate();">
			</td>
		</tr>
	
	</table>
	<%  }  %>


</form>

 </table>
  	<br>
  	<!--<center>
	<a href="#" class="current">1</a> &nbsp;<span class="paginate">Page:&nbsp;</span><select onchange="window.location='/corporatetravel/index.php?page='+this[this.selectedIndex].value+'&amp;ipp=10&amp;link=list-tr';return false" class="paginate"><option selected="" value="1">1</option>
	</select>
	 <span class="paginate">Items per page:&nbsp;</span><select onchange="window.location='/corporatetravel/index.php?page=1&amp;ipp='+this[this.selectedIndex].value+'&amp;link=list-tr';return false" class="paginate"><option value="All">All</option>
	<option value="10" selected="selected">10</option>
	<option value="20">20</option>
	<option value="30">30</option>
	<option value="50">50</option>
	</select>
	</center>-->
	<br>
							
</td>
</tr>
</tbody></table>
<iframe width=132 height=142 name="gToday:contrast:agenda.js" id="gToday:contrast:agenda.js" src="<%=Config.get("SITE_PATH")%>/admin/js/calendar/themes/Contrast/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
</body>
<jsp:include page="footer.jsp" flush="true" />