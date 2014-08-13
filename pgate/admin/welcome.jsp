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
		<table cellspacing="0" cellpadding="5" border="0" style="table-layout: fixed;" class="sortable logincss" id="table">
	  <tr style="height: 172px;">
			<td align="left" style="width: 20%; height: 2px; line-height: 4px;">&nbsp;</td>
			<td align="left">&nbsp;</td>
		</tr>
		</table>
		<br>
	<br>
							
</td>
</tr>
</tbody></table>
<jsp:include page="footer.jsp" flush="true" />
