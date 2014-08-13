<%@ page language="java" %>
<%@ page import="java.util.*"%>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="app.Config"%>
<% Config.setAllConfig(); %>
<%@page import="admin.servlet.CurrencyMasterServlet"%><jsp:include page="header.jsp" flush="true" />
<script type="text/javascript">
function validate(frmObj){
    var error = "Error:-\n";
    var isError = false;
    
    if(frmObj.currency_name.value == ""){
        isError = true;
        error += "Enter Currency Name\n";
    }  

    if(frmObj.currency_no.value == ""){
        isError = true;
        error += "Enter Currency No \n";
    }

    if(frmObj.currency_code.value == ""){
        isError = true;
        error += "Enter Currency Code\n";
    }

    if(frmObj.currency_location.value == ""){
        isError = true;
        error += "Entert Currency Location\n";
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
		String cmId = (String) request.getAttribute("cmId"); 
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
    <% if(cmId == "" || cmId == null){
    	String method = "post";
    	String action = Config.get("SITE_PATH")+"/admin/CurrencyMasterServlet";
    %>
    <form method="<%=method %>" action="<%=action%>" name="frm_currencymaster" onsubmit="return validate(this);">
    <table cellspacing="0" cellpadding="5" border="0" style="table-layout: fixed;" class="sortable logincss" id="table">
    	<tr style="height: 0px;" class="menuHeading">
			<td align="left" style="width: 20%; height: 2px; line-height: 4px;"></td>
			<td align="left"></td>
		</tr>
				
		<tr style="height: 24px;" class="menuHeading">
			<td align="left" colspan="2">
			Add Currency :
			</td>
		</tr>
		<tr class="content_blacknormal_back_white_11">
			<td>
				Currency Name
			</td>
			<td class="txtl">
				<input type="text" name="currency_name" id="currency_name" value="">
			</td>
		</tr>
		
		<tr class="content_blacknormal_back_white_11">
			<td>
				Currency No
			</td>
			<td class="txtl">
				<input type="text" name="currency_no" id="currency_no" value="">
			</td>
		</tr>
		
		<tr class="content_blacknormal_back_white_11">
			<td>
				Currency Code
			</td>
			<td class="txtl">
				<input type="text" name="currency_code" id="currency_code" value="">
			</td>
		</tr>
		
		<tr class="content_blacknormal_back_white_11">
			<td>
				Currency Location
			</td>
			<td class="txtl">
				<input type="text" name="currency_location" id="currency_location" value="">
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
    	String action = Config.get("SITE_PATH")+"/admin/CurrencyMasterServlet?cmId="+cmId;
    %>
    <form method="<%=method %>" action="<%=action%>" name="frm_currencymaster" onsubmit="return validate(this);">
    <table cellspacing="0" cellpadding="5" border="0" style="table-layout: fixed;" class="sortable logincss" id="table">
    	<tr style="height: 0px;" class="menuHeading">
			<td align="left" style="width: 20%; height: 2px; line-height: 4px;"></td>
			<td align="left"></td>
		</tr>
				
		<tr style="height: 24px;" class="menuHeading">
			<td align="left" colspan="2">
			Edit Currency :
			</td>
		</tr>
		<tr class="content_blacknormal_back_white_11">
			<td>
				Currency Name
			</td>
			<td class="txtl">
				<input type="text" name="currency_name" id="currency_name" value="<%=request.getAttribute("currency_name")%>">
			</td>
		</tr>
		
		<tr class="content_blacknormal_back_white_11">
			<td>
				Currency No
			</td>
			<td class="txtl">
				<input type="text" name="currency_no" id="currency_no" value="<%=request.getAttribute("currency_no")%>">
			</td>
		</tr>
		
		<tr class="content_blacknormal_back_white_11">
			<td>
				Currency Code
			</td>
			<td class="txtl">
				<input type="text" name="currency_code" id="currency_code" value="<%=request.getAttribute("currency_code")%>">
			</td>
		</tr>
		
		<tr class="content_blacknormal_back_white_11">
			<td>
				Currency Location
			</td>
			<td class="txtl">
				<input type="text" name="currency_location" id="currency_location" value="<%=request.getAttribute("currency_location")%>">
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