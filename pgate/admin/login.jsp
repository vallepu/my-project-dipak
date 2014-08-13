
<jsp:include page="header.jsp" flush="true" />
<%@ page import="app.Config"%>
<% Config.setAllConfig(); %>
<br /><br />
<div id="body">
<!-- <script type="text/javascript">
	/* &lt;![CDATA[ */
	jQuery(function(){
		jQuery("#emp_code").focus();
		jQuery("#emp_code").validate({
			expression: "if (VAL) return true; else return false;",
			message: "Please enter your email address"
		});
		jQuery("#emp_code").validate({
			expression: "if (VAL.match(/^[^\\W][a-zA-Z0-9\\_\\-\\.]+([a-zA-Z0-9\\_\\-\\.]+)*\\@[a-zA-Z0-9_]+(\\.[a-zA-Z0-9_]+)*\\.[a-zA-Z]{2,4}$/)) return true; else return false;",
			//expression: "if (VAL.match(/^[a-zA-Z0-9\\_\\-]+$/)) return true; else return false;",
			message: "Please enter a valid email address"
		});
		jQuery("#emp_password").validate({
			expression: "if (VAL) return true; else return false;",
			message: "Please enter the Password"
		});
		jQuery("#emp_password").validate({
			expression: "if (VAL.match(/^[a-z0-9\\_\\-\\@\\!\\#\\$\\%\\^\\*]+$/)) return true; else return false;",
			message: "Please enter a valid Password"
		});
		jQuery("#emp_code").val(jQuery.trim(jQuery("#emp_code").val()));
		jQuery("#emp_password").val(jQuery.trim(jQuery("#emp_password").val()));
	});
	/* ]]&gt; */
</script> -->
<table height="300px;" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tbody><tr>
		<td align="center">
			<br><br>
			<form name="loginform" method="post" action="<%=Config.get("SITE_PATH")%>/admin/Login.do">
				<table width="50%" cellspacing="1" cellpadding="5" border="0" class="login_header_bgcolor logincss">
                    <tbody><tr>
                        <td align="left" colspan="2">
                                <h3>Login</h3>
								<hr style="color: rgb(128, 128, 128);">
                        </td>
                    </tr>
					                    <tr>
                        <td width="10%" align="left" class="content_blacknormal12">
                                Login Name:
                        </td>
                        <td width="40%" align="left" class="content_blacknormal12">
                                <input type="text" name="userName" value=""><!-- class="ErrorField"<span class="ValidationErrors">Please enter your email address</span> -->
                        </td>
                    </tr>
                    <tr>
                        <td align="left" class="content_blacknormal12">
                            Password:
                        </td>
                        <td align="left" class="content_blacknormal12">
                                <input type="password" name="password" value="">
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="content_blacknormal12">&nbsp;
                        </td>
                        <td align="left" class="content_blacknormal12">
                            <input type="submit" class="submit" value="Login" style="color: black; background-color: white;">&nbsp;&nbsp;
							<input type="reset" class="submit" value="Reset" style="color: black; background-color: white;">
                        </td>
                    </tr>
					<tr>
                        <td align="right" style="line-height: 10px;" colspan="2" class="content_blacknormal12"> &nbsp;</td>
                       </tr>
				</tbody></table>
			</form>		
			<br><br>
		</td>
	</tr>
</tbody></table>
</div>
<br /><br />

<jsp:include page="footer.jsp" flush="true" />
