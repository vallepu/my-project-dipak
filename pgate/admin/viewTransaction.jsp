<%@ page language="java" %>
<%@ page import="java.util.*"%>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="app.Config"%>
<% Config.setAllConfig(); %>
<jsp:include page="header.jsp" flush="true" />

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
    <form action="" method="post" id="frm" name="frm">
	<table cellspacing="0" cellpadding="5" border="0" style="table-layout: fixed;" class="sortable logincss" id="table">
	<!--<tr style="height: 38px;" class="menuMain">
		<td colspan="9" align="center">Transaction Details</td>
	</tr>-->
	<tr class="menuMain" style="height: 38px;">
		<td width="3%"> Txn Id </td>
		<td width="7%"> Transaction Date </td>
		<td> Order Id </td>
		<td width="6%"> Payment Gateway </td>
		<td width="10%"> Mode </td>
		<td> Txn Amount </td>
		<td width="12%"> Client Name </td>
		<td width="10%"> Status </td>
		<td width="3%"> Link </td>
	</tr>
<%
	if (request.getAttribute ("resultArray") != null) { 
		int shrows = 1;
		HashMap objField = null;
		ArrayList rows = new ArrayList (); 

		rows = (ArrayList)request.getAttribute ("resultArray");
		Iterator i = rows.iterator();
		objField = new HashMap ();
		while (i.hasNext()){
			objField.putAll((HashMap)i.next());
			//2010-10-11 14:58:33
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdfDate.format(objField.get("created_date"));
		String url = "/admin/TransactionServlet";
		String stat = (String) objField.get("status");
		String userRole = (String) session.getAttribute("user_role");
	
		String showRows = null;
		if(shrows%2 == 0){
			showRows = "evenrow";
		}else{
			showRows = "oddrow";
		}
	%>
			<tr style="color: black;" class="<%= showRows  %>">
				<td> <%= objField.get("txn_id")%> </td>
				<td> <%= date%> </td>
				<td> <%= objField.get("order_no")%> </td>
				<td> <%= objField.get("pg_name")%> </td>
				<td> <%= objField.get("pg_mode")%> </td>
				<td> <%= objField.get("currency_no")%>&nbsp;<%= objField.get("txn_amount")%> </td>
				<td> <%= objField.get("client_name")%> </td>
				<td> <span style="color:#7E2217"> <%= objField.get("status")%></span> </td>
				<td> 
				<% if(stat.equalsIgnoreCase("FAILURE")){ %>
					&nbsp;
                <% }else{  %>
                	<a href="<%=Config.get("SITE_PATH")%>/admin/TransactionServlet?updtTxnId=<%=objField.get("txn_id") %>" >
                        <img src="<%=Config.get("SITE_PATH")%>/admin/images/icon_edit.gif" border="0" alt="Edit" />
                    </a> 
                <%  }  %>
                </td>
				
			</tr>
	
	<%
	shrows++;	}
	}else{
%>

	<tr>
		<td colspan="8"> No Transaction Records Found! </td>
	</tr>
<%
	}
%>
</table>
</form>  </table>
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


<!-- Footer Table Starts -->
</div>
<jsp:include page="footer.jsp" flush="true" />
