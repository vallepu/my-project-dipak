<%@ page language="java" %>
<%@ page import="java.util.*"%>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="app.Config"%>
<% Config.setAllConfig(); %>
<jsp:include page="header.jsp" flush="true" />
<script type="text/javascript">
	var SITE_PATH = '<%=Config.get("SITE_PATH")%>';
	function redirectUrl(){
		document.frmUpdtTxn.submit();
		//window.location = SITE_PATH+"/admin/TransactionServlet"
		//setTimeout('window.location = \''+SITE_PATH+'\/admin/TransactionServlet\'', 1000);
	}
</script>
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

  <!-- Content Start --> 
  <%
	if (request.getAttribute ("resultArray") != null) { 
	
		HashMap objField = null;
		ArrayList rows = new ArrayList (); 

		rows = (ArrayList)request.getAttribute ("resultArray");
		Iterator i = rows.iterator();
		objField = new HashMap ();
		objField.putAll((HashMap)i.next());
			//2010-10-11 14:58:33
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdfDate.format(objField.get("created_date"));
		String url = "/admin/TransactionServlet";
	
	%> 
    <form method="post" action="<%=Config.get("SITE_PATH")%>/admin/TransactionServlet" name="frmUpdtTxn" id="form_updtTxn">
    <table cellspacing="0" cellpadding="5" border="0" style="table-layout: fixed;" class="sortable logincss" id="table">
    	<tr style="height: 0px;" class="menuHeading">
			<td align="left" style="width: 20%; height: 2px; line-height: 4px;"></td>
			<td align="left"></td>
		</tr>
				
		<tr style="height: 24px;" class="menuHeading">
			<td align="left" colspan="2">
			Edit Transaction :
			</td>
		</tr>
		<tr class="content_blacknormal_back_white_11">
			<td>
				Transaction ID
			</td>
			<td class="txtl">
				<%= objField.get("txn_id")%>
			</td>
		</tr>
		<tr class="content_blacknormal_back_white_11">
			<td>
				Transaction Date
			</td>
			<td class="txtl">
				<%= date%>
			</td>
		</tr>
		<tr class="content_blacknormal_back_white_11">
			<td>
				Order ID
			</td>
			<td class="txtl">
				<%= objField.get("order_no")%>
			</td>
		</tr>
		<tr class="content_blacknormal_back_white_11">
			<td>
				Payment Gateway
			</td>
			<td class="txtl">
				<%= objField.get("pg_name")%>
			</td>
		</tr>
		<tr class="content_blacknormal_back_white_11">
			<td>
				Client Name
			</td>
			<td class="txtl">
				<%= objField.get("client_name")%>
			</td>
		</tr>
		<tr class="content_blacknormal_back_white_11">
			<td>
				Update Status
			</td>
			<td> 
				<% 
					String viewStstus = objField.get("status").toString(); 
					if(viewStstus.equalsIgnoreCase("APPROVED")){
				%>
					<input type="radio" name="updt_status" value="UNAPPROVED" />Unapproved 
				<%  }else if(viewStstus.equalsIgnoreCase("UNAPPROVED")){  %>
				<input type="radio" name="updt_status" value="APPROVED" />Approved &nbsp; 
				<%  }  %>
			</td>
		</tr>
		<tr class="content_blacknormal_back_white_11">
			<td>
				Comment
			</td>
			<td class="classTd"> <input type="text" name="updt_comment" class="text130"/>
		</tr>
		<input type="hidden" name="updt_txnid" value="<%= objField.get("txn_id")%>" />
		<input type="hidden" name="updt_pgmode" value="AUTHORIZATION" />
		<tr class="content_blacknormal_back_white_11">
			<td colspan=2 align="center">
				<input name="btnTxn" value="Update" type="button" id="btn_txn" onclick="redirectUrl();">
				<input name="btnCancel" value="Cancel" type="button" id="btn_txn_cancel" onclick="javascript:window.location='<%=Config.get("SITE_PATH")%>/admin/TransactionServlet'">
				
			</td>
		</tr>
	
	</table>

</form>
<%  }  %>
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

<jsp:include page="footer.jsp" flush="true" />
