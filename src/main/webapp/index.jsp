<!DOCTYPE html>
<html lang="en">
<head>
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
</head>
<body>

<div class="container">
	<div class="row">
		<h3>Post a Message</h3>
	</div>
	<%
		String error = request.getParameter( "error" );
		if ( error != null && error.length() > 0 )
		{
	%>
	<div class="row">
		<div class="alert alert-danger" role="alert">
			<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
			<span class="sr-only">Error:</span><%=error%>
		</div>
	</div>
	<%}%>
	<div class="row">
		<form action="/action/post">
			<div class="form-group">
				<label for="email">Email address</label>
				<input type="email" class="form-control" id="email" name="email" placeholder="Enter email">
			</div>
			<div class="form-group">
				<label for="password">Password</label>
				<input type="text" class="form-control" id="password" name="password" placeholder="Enter password">
			</div>
			<div class="form-group">
				<label for="message">Message</label>
				<textarea class="form-control" id="message" name="message" placeholder="Post a message" rows="3"></textarea>
			</div>
			<p>Don't have an account? <a href="/sign-up.jsp">Sign Up</a></p>
			<button type="submit" class="btn btn-default">Submit</button>
		</form>
	</div>
</div>

</body>
</html>
