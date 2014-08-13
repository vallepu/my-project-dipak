<%@ page import="app.Config"%>
<% Config.setAllConfig(); %>
<% String userRole = (String) session.getAttribute("userRole"); %>

<!-- Left Side Start --> 
<% if(userRole.equalsIgnoreCase("ADMIN")){  %>  
	<table width="100%" cellspacing="0" cellpadding="0" border="0" style="padding: 1px;" class="logincss">
		<tbody><tr>
			<td width="100%" valign="top" class="menu">
				<table width="100%" border="0" bgcolor="#ffffff">
					<tbody><tr class="trm_heading">
						<td class="menuMain">
						&nbsp;Action
						</td>
					</tr>
					<tr class="tr_sub">
						<td class="menuHeading">
						&nbsp;&nbsp;<a href="<%=Config.get("SITE_PATH")%>/admin/ViewMerchantMaster">Merchant</a>
						</td>
					</tr>
					<tr class="tr_sub">
						<td class="menuHeading">
						&nbsp;&nbsp;<a href="<%=Config.get("SITE_PATH")%>/admin/ViewCurrencyMaster">Currency</a></td>
					</tr>
					
					<tr class="tr_sub">
						<td class="menuHeading">
						&nbsp;&nbsp;<a href="<%=Config.get("SITE_PATH")%>/admin/ViewPgMaster">Payment Gateway</a></td>
					</tr>
					
					<tr class="tr_sub">
						<td class="menuHeading">
						&nbsp;&nbsp;<a href="<%=Config.get("SITE_PATH")%>/admin/ViewClientMaster">Clients</a></td>
					</tr>
					
					<tr class="tr_sub">
						<td class="menuHeading">
						&nbsp;&nbsp;<a href="<%=Config.get("SITE_PATH")%>/admin/ViewMidClientPg">View Mid Client Pg</a></td>
					</tr>
					
					<tr class="tr_sub">
						<td class="menuHeading">
						&nbsp;&nbsp;<a href="<%=Config.get("SITE_PATH")%>/admin/TransactionServlet">View Transaction</a></td>
					</tr>
					
					<tr class="tr_sub">
						<td class="menuHeading">
						&nbsp;&nbsp;<a href="<%=Config.get("SITE_PATH")%>/admin/Logout.do">LogOut</a></td>
					</tr>
									
				</tbody></table>
				</td>
			</tr>
	</tbody></table>
    
<% }else{ %>
	<table width="100%" cellspacing="0" cellpadding="0" border="0" style="padding: 1px;" class="logincss">
		<tbody><tr>
			<td width="100%" valign="top" class="menu">
				<table width="100%" border="0" bgcolor="#ffffff">
					<tbody><tr class="trm_heading">
						<td class="menuMain">
						&nbsp;Action
						</td>
					</tr>
			
					<tr class="tr_sub">
						<td class="menuHeading">
						&nbsp;&nbsp;<a href="<%=Config.get("SITE_PATH")%>/admin/TransactionServlet">View Transaction</a></td>
					</tr>
					
					<tr class="tr_sub">
						<td class="menuHeading">
						&nbsp;&nbsp;<a href="<%=Config.get("SITE_PATH")%>/admin/Logout.do">LogOut</a></td>
					</tr>
									
				</tbody></table>
				</td>
			</tr>
	</tbody></table>
<% }  %>
<!-- Left Side Ends --> 
