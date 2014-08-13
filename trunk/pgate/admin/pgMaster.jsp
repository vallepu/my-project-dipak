<%@ page language="java" %>
<%@ page import="java.util.*"%>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="app.Config"%>
<% Config.setAllConfig(); %>
<%@page import="admin.servlet.PgMasterServlet"%><jsp:include page="header.jsp" flush="true" />
<script language="javascript" type="text/javascript" src="<%=Config.get("SITE_PATH")%>/admin/js/jquery.js"></script>
<script type="text/javascript">
	function validate(frmObj){
		var stat_active = jQuery('#stat_active').attr('checked');
		var stat_inactive = jQuery('#stat_inactive').attr('checked');
		
	    var error = "Error:-\n";
	    var isError = false;
	    
	    if(frmObj.pg_name.value == ""){
	        isError = true;
	        error += "Enter PG Name\n";
	    }  

	    if(stat_active == false && stat_inactive == false){
	        isError = true;
	        error += "Select a Status\n";
	    }
	
	    if(isError){
	        alert(error);
	        return false;
	    }else{
	    	return true;
	    }  
	}
</script>
<body>
	<%
		String pgmId = (String) request.getAttribute("pgmId"); 
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
    <% if(pgmId == "" || pgmId == null){
    	String method = "post";
    	String action = Config.get("SITE_PATH")+"/admin/PgMasterServlet";
    %>
    <form method="<%=method %>" action="<%=action%>" name="frm_pgmaster" onsubmit="return validate(this);">
	  <table cellspacing="0" cellpadding="5" border="0" style="table-layout: fixed;" class="sortable logincss" id="table">
	  <tr style="height: 0px;" class="menuHeading">
			<td align="left" style="width: 20%; height: 2px; line-height: 4px;"></td>
			<td align="left"></td>
		</tr>
				
		<tr style="height: 24px;" class="menuHeading">
			<td align="left" colspan="2">
			Add Payment Gateway :
			</td>
		</tr>
		<tr class="content_blacknormal_back_white_11">
			<td>
				PG Name
			</td>
			<td class="txtl">
				<input type="text" name="pg_name" id="pg_name" value="">
			</td>
		</tr>
		
	
	    <tr class="content_blacknormal_back_white_11">
	        <td>
	            Active
	        </td>
	        <td class="txtl">
	        	<input type="radio" name="is_active" id="stat_active" value="yes"> Yes &nbsp; 
			    <input type="radio" name="is_active" id="stat_inactive" value="no"> No
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
    	String action = Config.get("SITE_PATH")+"/admin/PgMasterServlet?pgmId="+pgmId;
    %>
    <form method="<%=method %>" action="<%=action%>" name="frm_pgmaster" onsubmit="return validate(this);">
    <table cellspacing="0" cellpadding="5" border="0" style="table-layout: fixed;" class="sortable logincss" id="table">
	  <tr style="height: 0px;" class="menuHeading">
			<td align="left" style="width: 20%; height: 2px; line-height: 4px;"></td>
			<td align="left"></td>
		</tr>
				
		<tr style="height: 24px;" class="menuHeading">
			<td align="left" colspan="2">
			Edit Payment Gateway :
			</td>
		</tr>
		<tr class="content_blacknormal_back_white_11">
			<td>
				PG Name
			</td>
			<td class="txtl">
				<input type="text" name="pg_name" id="pg_name" value="<%=request.getAttribute("pg_name")%>">
			</td>
		</tr>
		
			
	    <tr class="content_blacknormal_back_white_11">
	        <td>
	            Active
	        </td>
	        <td class="txtl">
	        <%  
	        	String active = request.getAttribute("is_active").toString();
		        if(active.equalsIgnoreCase("yes")){  
		        	String checkedYes = "checked='checked'";
		    %>
		    	<input type="radio" name="is_active" id="stat_active" value="yes" <%=checkedYes  %>> Yes &nbsp; 
	        	<input type="radio" name="is_active" id="stat_inactive" value="no"> No
		    <%
		        }else if(active.equalsIgnoreCase("no")){  
	        		String checkedNo = "checked='checked'";
	        %>
	        	<input type="radio" name="is_active" id="stat_active" value="yes"> Yes &nbsp; 
	            <input type="radio" name="is_active" id="stat_inactive" value="no" <%=checkedNo  %>> No
	        <%  }  %>
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