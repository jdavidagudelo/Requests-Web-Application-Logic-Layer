<!-- <!doctype html>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		                                                             	                                                             
		<link type="text/css" rel="stylesheet" href="Solicitudes.css">
	     
		<title>Wrapper HTML for Solicitudes</title>

		                                         
		<script language="javascript" src="co.edu.udea.iw.rtf.Solicitudes/co.edu.udea.iw.rtf.Solicitudes.nocache.js"></script>
		
	</head>

	                                         
	                                         
	<body>

		<iframe id="__gwt_historyFrame" style="width:0;height:0;border:0"></iframe>

	</body>
</html> 
 -->
<!doctype html>

<%@page import="co.edu.udea.iw.rtf.client.dto.UsuarioSingleton"%>
<%@page import="co.edu.udea.iw.rtf.client.dto.UsuarioListado"%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="Solicitudes.css">
	    

<title>Solicitudes Online</title>
</head>
<body>

	<%
		if (session.getAttribute("UsuarioConectado") != null) {
			UsuarioSingleton user = (UsuarioSingleton) session
					.getAttribute("UsuarioConectado");
	%>

	<script type="text/javascript">
  		var userInSession = {
  			"login" : "<%=user.getLogin() == null ? "" : user.getLogin()%>",
  	  		"isAdministrador" : "<%=user.getIsAdministrador()%>"
		}
	</script>

	<script type="text/javascript" language="javascript"
		src="co.edu.udea.iw.rtf.Solicitudes/co.edu.udea.iw.rtf.Solicitudes.nocache.js"></script>

	<iframe src="javascript:''" id="__gwt_historyFrame" tabIndex='-1'
		style="position: absolute; width: 0; height: 0; border: 0"></iframe>

	<noscript>
		<div
			style="width: 22em; position: absolute; left: 50%; margin-left: -11em; color: red; background-color: white; border: 1px solid red; padding: 4px; font-family: sans-serif">
			Your web browser must have JavaScript enabled in order for this
			application to display correctly.</div>
	</noscript>
	<table border="0" cellspacing="0" cellpadding="0" align="center">
		<tr>
			<td align="right"><%=user.getLogin() == null ? "Invitado" : user
						.getLogin()%>(<a
				href="logout">Salir</a>)</td>
		</tr>
		<tr>
			<td align="center">
				<div id="contenido"></div>
			</td>
		</tr>
	</table>

	<%
		} else {
	%>

	<table border="0" cellspacing="0" cellpadding="0" align="center">

		<tr>
			<td>
				<form action="login" method="post">
					<table border="0">
						<tr>
							<td><span class="label">Login: </span></td>
							<td><input type="text" name="usuario" value=""></td>
						</tr>
						<tr>
							<td><span class="label">Clave: </span></td>
							<td><input type="password" name="pws" value=""></td>
						</tr>
						<%
							if (session.getAttribute("DatosInvalidos") != null) {
						%>
						<tr>
							<td></td>
							<td><%=(String) session.getAttribute("DatosInvalidos")%></td>
						</tr>

						<%
							}
						%>
						<tr>
							<td />
							<td align="center"><input type=submit value="Login">
							</td>
						</tr>
					</table>
				</form>
				<form action="guest" method="post">
					<table border="0">
						<tr>
							<td><td align="center"><input type=submit value="Realizar solicitud">
							</td>
						</tr>
					</table>
				</form>
				
			</td>
		</tr>
	
	</table>

	<%
		}
	%></body>
</html>