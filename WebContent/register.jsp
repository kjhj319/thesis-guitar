<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
	<title>Register</title>
	
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<link rel="stylesheet" href="css/navbar.css">
	<link rel="stylesheet" href="css/style.css">
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</head>

<body>
	<div class="container-fluid"> 
		<nav class="navbar navbar-inverse " >
		<!-- Second navbar for sign in -->
			<div class="container">
		<!-- Brand and toggle get grouped for better mobile display -->
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed" 
						data-toggle="collapse" data-target="#navbar-collapse-2">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					
					<a class="navbar-brand" href="index.jsp">
						<img src="img/vmm4.png">
					</a>
				
				</div>
			
				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse" id="navbar-collapse-2">
				  <ul class="nav navbar-nav navbar-right">
					<li><a href="index.jsp">Home</a></li>
					<li><a href="upload.jsp">Upload Tab</a></li>
					<li><a href="AllTabsServlet">All Tabs</a></li>
					<c:choose>
							  <c:when test="${information[0] == null }">
							  	<li><a href = "login.jsp">Log in</a></li>
								<li><a href = "register.jsp">Register</a></li>
							  </c:when>
							  <c:otherwise>
							    <li><a href = "#"><c:out value="${information[0]}"/></a></li>
							    <li><a id = "proceedDashboard" 
									href = "dashboard.jsp">Edit Profile</a></li>
							    <li><a id = "proceedLogOut" 
									href = "LogoutServlet">
									Logout
									</a>
								</li>
							  </c:otherwise>
					</c:choose>
				
					<li>
					  <a class="btn btn-default btn-outline btn-circle collapsed"  
						data-toggle="collapse" href="#nav-search" aria-expanded="false" 
						aria-controls="nav-search">
						Search
					</a>
					</li>
				  </ul>
			   
				  <div class="collapse nav navbar-nav nav-collapse slide-down" 
					id="nav-search">
					<form class="navbar-form navbar-right" role="search" 
						action = "SearchServlet" method = "POST">
					  <div class="form-group">
					  	<select class="form-control" name="searchType">
					  		<option value="None">None</option>
					  		<option value="Artist">Artist</option>
					  		<option value="Title">Song Title</option>
					  		<option value="Difficulty">Difficulty(1-8)</option>
					  		<option value="Uploader">Uploader</option>
					  	</select>
						<input type="text" name="search" class="form-control" 
						placeholder="Search" />
					  </div>
					  <button type="submit" class="btn btn-danger">
						<span class="glyphicon glyphicon-search" 
							aria-hidden="true"></span>
					  </button>
					</form>
				  </div>
				</div><!-- /.navbar-collapse -->
			</div><!-- /.container -->
		</nav><!-- /.navbar -->
	
		<div class="maincont">
		
		
			<div class="login" id="formwhite"> 
				<form action="RegisterServlet" method="post">
					<div class="form-group">
						<label for="username-email">Username</label>
						<input value='' id="username-email" name="username" 
							placeholder="Username" type="text" 
							class="form-control" />
					</div>
					<div class="form-group">
						<label for="password">Password</label>
						<input id="password" name="password" value='' 
							placeholder="Password" type="password" class="form-control" />
					</div>
					<div class="form-group">
						<label for="username-email">E-mail Address</label>
						<input value='' id="username-email" name="email" 
							placeholder="E-mail" type="text" class="form-control" 
						/>
					</div>
					<div class="form-group text-center">
						<input type="submit" 
						class="btn btn-success btn-login-submit" value="Register" 
						/>
					</div>
					<div class="form-group text-center">
						Already have an account? <a href="login.jsp"> Log In </a>
					</div>
				</form> <!--./login form -->
			</div> <!--/.login container -->
		</div><!--/.container for main content -->
 	</div><!-- /.container-fluid -->
</body>
</html>