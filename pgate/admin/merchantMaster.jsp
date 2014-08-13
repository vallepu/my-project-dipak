<%@ page language="java" %>
<%@ page import="java.util.*"%>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="app.Config"%>
<% Config.setAllConfig(); %>
<%@page import="admin.servlet.MerchantMasterServlet"%><jsp:include page="header.jsp" flush="true" />
<script language="javascript" type="text/javascript" src="<%=Config.get("SITE_PATH")%>/admin/js/jquery.js"></script>
<script type="text/javascript">
function validate(frmObj){
	var stat_active = jQuery('#stat_active').attr('checked');
	var stat_inactive = jQuery('#stat_inactive').attr('checked');
    var error = "Error:-\n";
    var isError = false;
    
    if(frmObj.merchant_code.value == ""){
        isError = true;
        error += "Enter Merchant Code\n";
    }  

    /*if(frmObj.password.value == ""){
        isError = true;
        error += "Enter Password \n";
    }

    if(frmObj.security_id.value == ""){
        isError = true;
        error += "Enter Security Id\n";
    }*/

    if(frmObj.txn_limit.value == ""){
        isError = true;
        error += "Enter Transaction Limit\n";
    }

    if(frmObj.valid_from.value == ""){
        isError = true;
        error += "Select Validity From\n";
    }

    if(frmObj.valid_to.value == ""){
        isError = true;
        error += "Select Validity To\n";
    }

    if(stat_active == false && stat_inactive == false){
        isError = true;
        error += "Select Status\n";
    }

    if(frmObj.pg_id.value == ""){
        isError = true;
        error += "Select Payment Gateway\n";
    }
	
    if(isError){
        alert(error);
        return false;
    }else{
    	return true;
    }  
}
</script>
	<%
		String mmId = (String) request.getAttribute("mmId"); 
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

    <% if(mmId == "" || mmId == null){
    	String method = "post";
    	String action = Config.get("SITE_PATH")+"/admin/MerchantMasterServlet";
    %>
    <form method="<%=method %>" action="<%=action%>" name="frm_merchantmaster" onsubmit="return validate(this);">
	    <table cellspacing="0" cellpadding="5" border="0" style="table-layout: fixed;" class="sortable logincss" id="table">
    	<tr style="height: 0px;" class="menuHeading">
			<td align="left" style="width: 20%; height: 2px; line-height: 4px;"></td>
			<td align="left"></td>
		</tr>
				
		<tr style="height: 24px;" class="menuHeading">
			<td align="left" colspan="2">
			Add Merchant :
			</td>
		</tr>
		<tr class="content_blacknormal_back_white_11">
			<td>
				Merchant Code
			</td>
			<td class="txtl">
				<input type="text" name="merchant_code" id="merchant_code" value="">
			</td>
		</tr>
		
		<tr class="content_blacknormal_back_white_11">
			<td>
				Password
			</td>
			<td class="txtl">
				<input type="text" name="password" id="password" value="">
			</td>
		</tr>
		
		<tr class="content_blacknormal_back_white_11">
			<td>
				Security Id
			</td>
			<td class="txtl">
				<input type="text" name="security_id" id="security_id" value="">
			</td>
		</tr>
		
		<tr class="content_blacknormal_back_white_11">
			<td>
				Transaction Limit
			</td>
			<td class="txtl">
				<input type="text" name="txn_limit" id="txn_limit" value="">
			</td>
		</tr>
		
		<tr class="content_blacknormal_back_white_11">
			<td>
				Validity
			</td>
			<td class="txtl">
				From: &nbsp; 
				<input class="textbox w153" name="valid_from" id="valid_from" type="text" readonly="" href="javascript:void(0)" onfocus="" value="">
				&nbsp;&nbsp;
				<a href="javascript:void(0)" style="text-decoration:none;" onclick="if(self.gfPop)gfPop.fPopCalendar(document.frm_merchantmaster.valid_from);return false;" hidefocus="">
				<img border="0" align="absMiddle" alt="Calender Pop Up" src="http://static.coxandkings.co.in/cnk_html/images/cal.gif" name="popcal"></a>
				&nbsp; 
				To: &nbsp; 
				<input class="textbox w153" name="valid_to" id="valid_to" type="text" readonly="" href="javascript:void(0)" onfocus="" value="">
				&nbsp;&nbsp;
				<a href="javascript:void(0)" style="text-decoration:none;" onclick="if(self.gfPop)gfPop.fPopCalendar(document.frm_merchantmaster.valid_to);return false;" hidefocus="">
				<img border="0" align="absMiddle" alt="Calender Pop Up" src="http://static.coxandkings.co.in/cnk_html/images/cal.gif" name="popcal"></a>
			</td>
		</tr>
		
		<tr class="content_blacknormal_back_white_11">
			<td>
	            Status
	        </td>
	        <td class="txtl">
	        	<input type="radio" name="is_active" value="yes" id="stat_active"> Active &nbsp; 
			    <input type="radio" name="is_active" value="no" id="stat_inactive"> InActive
	        </td>
		</tr>
		
		<tr class="content_blacknormal_back_white_11">
			<td>
				Payment Gateway <!-- pg_master -->
			</td>
			<td class="txtl">
				<select name="pg_id" id="pg_id" class="txtl">
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
			<td colspan=2 align="center">
				<input name="btn_sub" value="Submit" type="submit" id="btn_sub">
			</td>
		</tr>
	
	</table>
</form>
    <%  }else{  
    	String method = "post";
    	String action = Config.get("SITE_PATH")+"/admin/MerchantMasterServlet?mmId="+mmId;
    %>
    <form method="<%=method %>" action="<%=action%>" name="frm_merchantmaster" onsubmit="return validate(this);">
    <table cellspacing="0" cellpadding="5" border="0" style="table-layout: fixed;" class="sortable logincss" id="table">
    	<tr style="height: 0px;" class="menuHeading">
			<td align="left" style="width: 20%; height: 2px; line-height: 4px;"></td>
			<td align="left"></td>
		</tr>
				
		<tr style="height: 24px;" class="menuHeading">
			<td align="left" colspan="2">
			Add Merchant :
			</td>
		</tr>
    	<tr class="content_blacknormal_back_white_11">
			<td>
				Merchant Code
			</td>
			<td class="txtl">
				<input type="text" name="merchant_code" id="merchant_code" value="<%=request.getAttribute("merchant_code")%>">
			</td>
		</tr>
		
		<tr class="content_blacknormal_back_white_11">
			<td>
				Password
			</td>
			<td class="txtl">
				<input type="text" name="password" id="password" value="<%=request.getAttribute("password")%>">
			</td>
		</tr>
		
		<tr class="content_blacknormal_back_white_11">
			<td>
				Security Id
			</td>
			<td class="txtl">
				<input type="text" name="security_id" id="security_id" value="<%=request.getAttribute("security_id")%>">
			</td>
		</tr>
		
		<tr class="content_blacknormal_back_white_11">
			<td>
				Transaction Limit
			</td>
			<td class="txtl">
				<input type="text" name="txn_limit" id="txn_limit" value="<%=request.getAttribute("txn_limit")%>">
			</td>
		</tr>
		
		<tr class="content_blacknormal_back_white_11">
			<td>
				Validity
			</td>
			<td class="txtl">
				From: &nbsp; <input class="textbox w153" name="valid_from" id="valid_from" type="text" readonly="" href="javascript:void(0)" onfocus="" value="<%=request.getAttribute("valid_from")%>">
				&nbsp;&nbsp;<a href="javascript:void(0)" style="text-decoration:none;" onclick="if(self.gfPop)gfPop.fPopCalendar(document.frm_merchantmaster.valid_from);return false;" hidefocus="">
				<img border="0" align="absMiddle" alt="Calender Pop Up" src="http://static.coxandkings.co.in/cnk_html/images/cal.gif" name="popcal"></a>
				&nbsp; 
				To: &nbsp; <input class="textbox w153" name="valid_to" id="valid_to" type="text" readonly="" href="javascript:void(0)" onfocus="" value="<%=request.getAttribute("valid_to")%>">
				&nbsp;&nbsp;<a href="javascript:void(0)" style="text-decoration:none;" onclick="if(self.gfPop)gfPop.fPopCalendar(document.frm_merchantmaster.valid_to);return false;" hidefocus="">
				<img border="0" align="absMiddle" alt="Calender Pop Up" src="http://static.coxandkings.co.in/cnk_html/images/cal.gif" name="popcal"></a>
			</td>
		</tr>
		
		<tr class="content_blacknormal_back_white_11">
			 <td>
	            Status
	        </td>
	        <td class="txtl">
	        <%  
	        	String active = (String) request.getAttribute("is_active");
		        if(active.equalsIgnoreCase("yes")){  
		        	String checkedYes = "checked='checked'";
		    %>
		    	<input type="radio" name="is_active" value="yes" id="stat_active" <%=checkedYes  %>> Active &nbsp; 
	        	<input type="radio" name="is_active" value="no" id="stat_inactive"> InActive
		    <%
		        }else if(active.equalsIgnoreCase("no")){  
	        		String checkedNo = "checked='checked'";
	        %>
	        	<input type="radio" name="is_active" value="yes" id="stat_active"> Active &nbsp; 
	            <input type="radio" name="is_active" value="no" id="stat_inactive" <%=checkedNo  %>> InActive
	        <%  }  %>
	        </td>
		</tr>
		
		<tr class="content_blacknormal_back_white_11">
			<td>
				Payment Gateway <!-- pg_master -->
			</td>
			<td class="txtl">
				<select name="pg_id" id="pg_id" class="txtl">
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
			<td colspan=2 align="center">
				<input name="btn_sub" value="Submit" type="submit" id="btn_sub">
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