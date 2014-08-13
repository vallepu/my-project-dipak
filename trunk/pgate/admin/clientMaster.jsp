<%@ page language="java" %>
<%@ page import="java.util.*"%>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="app.Config"%>
<% Config.setAllConfig(); %>
<%@page import="admin.servlet.ClientMasterServlet"%><jsp:include page="header.jsp" flush="true" />
<script language="javascript" type="text/javascript" src="<%=Config.get("SITE_PATH")%>/admin/js/jquery.js"></script>
<script type="text/javascript">
function validate(frmObj){
	var stat_active = jQuery('#stat_active').attr('checked');
	var stat_inactive = jQuery('#stat_inactive').attr('checked');
    var error = "Error:-\n";
    var isError = false;
    
    if(frmObj.client_name.value == ""){
        isError = true;
        error += "Enter Client Name\n";
    }  

    if(frmObj.client_url.value == ""){
        isError = true;
        error += "Enter Client URL \n";
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

    if(frmObj.success_url.value == ""){
        isError = true;
        error += "Enter Success URL\n";
    }
	
    if(frmObj.failure_url.value == ""){
        isError = true;
        error += "Enter Failure URL\n";
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
		String clId = (String) request.getAttribute("clId"); 
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
    <% if(clId == "" || clId == null){
    	String method = "post";
    	String action = Config.get("SITE_PATH")+"/admin/ClientMasterServlet";
    %>
    <form method="<%=method %>" action="<%=action%>" name="frm_clientmaster" onsubmit="return validate(this);">
	   <table cellspacing="0" cellpadding="5" border="0" style="table-layout: fixed;" class="sortable logincss" id="table">
	   <tr style="height: 0px;" class="menuHeading">
			<td align="left" style="width: 20%; height: 2px; line-height: 4px;"></td>
			<td align="left"></td>
		</tr>
				
		<tr style="height: 24px;" class="menuHeading">
			<td align="left" colspan="2">
			Add Client :
			</td>
		</tr>
		<tr class="content_blacknormal_back_white_11">
			<td>
				Client Name
			</td>
			<td class="txtl">
				<input type="text" name="client_name" id="client_name" value="">
			</td>
		</tr>
		
		<tr class="content_blacknormal_back_white_11">
			<td>
				Client URL
			</td>
			<td class="txtl">
				<input type="text" name="client_url" id="client_url" value="">
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
				<a href="javascript:void(0)" style="text-decoration:none;" onclick="if(self.gfPop)gfPop.fPopCalendar(document.frm_clientmaster.valid_from);return false;" hidefocus="">
				<img border="0" align="absMiddle" alt="Calender Pop Up" src="http://static.coxandkings.co.in/cnk_html/images/cal.gif" name="popcal"></a>
				&nbsp; 
				To: &nbsp; 
				<input class="textbox w153" name="valid_to" id="valid_to" type="text" readonly="" href="javascript:void(0)" onfocus="" value="">
				&nbsp;&nbsp;
				<a href="javascript:void(0)" style="text-decoration:none;" onclick="if(self.gfPop)gfPop.fPopCalendar(document.frm_clientmaster.valid_to);return false;" hidefocus="">
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
				Success URL
			</td>
			<td class="txtl">
				<input style="width:315px;" type="text" name="success_url" id="success_url" value="">
			</td>
		</tr>
		
		<tr class="content_blacknormal_back_white_11">
			<td>
				Failure URL
			</td>
			<td class="txtl">
				<input style="width:315px;" type="text" name="failure_url" id="failure_url" value="">
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
    	String action = Config.get("SITE_PATH")+"/admin/ClientMasterServlet?clId="+clId;
    %>
    <form method="<%=method %>" action="<%=action%>" name="frm_clientmaster" onsubmit="return validate(this);">
    <table cellspacing="0" cellpadding="5" border="0" style="table-layout: fixed;" class="sortable logincss" id="table">
	   <tr style="height: 0px;" class="menuHeading">
			<td align="left" style="width: 20%; height: 2px; line-height: 4px;"></td>
			<td align="left"></td>
		</tr>
				
		<tr style="height: 24px;" class="menuHeading">
			<td align="left" colspan="2">
			Edit Client :
			</td>
		</tr>
		<tr class="content_blacknormal_back_white_11">
			<td>
				Client Name
			</td>
			<td class="txtl">
				<input type="text" name="client_name" id="client_name" value="<%=request.getAttribute("client_name")%>">
			</td>
		</tr>
		
		<tr class="content_blacknormal_back_white_11">
			<td>
				Client URL
			</td>
			<td class="txtl">
				<input type="text" name="client_url" id="client_url" value="<%=request.getAttribute("client_url")%>">
			</td>
		</tr>
		
		<tr class="content_blacknormal_back_white_11">
			<td>
				Validity
			</td>
			<td class="txtl">
				From: &nbsp; <input class="textbox w153" name="valid_from" id="valid_from" type="text" readonly="" href="javascript:void(0)" onfocus="" value="<%=request.getAttribute("valid_from")%>">
				&nbsp;&nbsp;<a href="javascript:void(0)" style="text-decoration:none;" onclick="if(self.gfPop)gfPop.fPopCalendar(document.frm_clientmaster.valid_from);return false;" hidefocus="">
				<img border="0" align="absMiddle" alt="Calender Pop Up" src="http://static.coxandkings.co.in/cnk_html/images/cal.gif" name="popcal"></a>
				&nbsp; 
				To: &nbsp; <input class="textbox w153" name="valid_to" id="valid_to" type="text" readonly="" href="javascript:void(0)" onfocus="" value="<%=request.getAttribute("valid_to")%>">
				&nbsp;&nbsp;<a href="javascript:void(0)" style="text-decoration:none;" onclick="if(self.gfPop)gfPop.fPopCalendar(document.frm_clientmaster.valid_to);return false;" hidefocus="">
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
		    	<input type="radio" name="is_active" value="yes" <%=checkedYes  %> id="stat_active"> Active &nbsp; 
	        	<input type="radio" name="is_active" value="no" id="stat_active"> InActive
		    <%
		        }else if(active.equalsIgnoreCase("no")){  
	        		String checkedNo = "checked='checked'";
	        %>
	        	<input type="radio" name="is_active" value="yes" id="stat_active"> Active &nbsp; 
	            <input type="radio" name="is_active" value="no" <%=checkedNo  %> id="stat_active"> InActive
	        <%  }  %>
	        </td>
		</tr>
		
		<tr class="content_blacknormal_back_white_11">
			<td>
				Success URL
			</td>
			<td class="txtl">
				<input style="width:315px;" type="text" name="success_url" id="success_url" value="<%=request.getAttribute("success_url")%>">
			</td>
		</tr>
		
		<tr class="content_blacknormal_back_white_11">
			<td>
				Failure URL
			</td>
			<td class="txtl">
				<input style="width:315px;" type="text" name="failure_url" id="failure_url" value="<%=request.getAttribute("failure_url")%>">
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