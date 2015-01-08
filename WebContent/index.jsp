<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Mobile Recommendation</title>
</head>
<body>	
	<form action="hello.action" method="post">
		<div  style="  left: 30px; width: 1240px; height: 50px;">
		<h4>Enter Your Name: <input type="text" name="names" /> </h4>
		 </div>
		 <div style="  left: 3px; width: 240px; height: 150px;">
		<input type="submit" value="Go" /> <br/>
		</div>
	</form>
</body>
</html>