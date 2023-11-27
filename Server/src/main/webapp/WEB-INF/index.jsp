<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- c:out ; c:forEach etc. -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Formatting (dates) -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Product</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
<!-- <link rel="stylesheet" href="/css/main.css"> -->
<!-- change to match your file/naming structure -->
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<!-- <script type="text/javascript" src="/js/app.js"></script> -->
<!-- change to match your file/naming structure -->
</head>
<body>
	<div class="container">
		<h1>Site for selling buying artisanat</h1>
		<p>A place for buying selling on auction aritsanat .</p>

		<form:form action="/register" method="post" modelAttribute="register"
			enctype="multipart/form-data">

			<table>
				<thead>
					<tr>
						<td colspan="2">New Artisan</td>
					</tr>
				</thead>
				<thead>
					<tr>
						<td class="float-left">username:</td>
						<td class="float-left"><form:errors path="username"
								class="text-danger" /> <form:input class="input form-control"
								path="username" /></td>
					</tr>

					<tr>
						<td class="float-left">Email:</td>
						<td class="float-left"><form:errors path="email"
								class="text-danger" /> <form:input class="input form-control"
								path="email" /></td>
					</tr>
					<tr>
						<td class="float-left">Phone:</td>
						<td class="float-left"><form:errors path="phone"
								class="text-danger" /> <form:input class="input form-control"
								path="phone" /></td>
					</tr>
					<tr>
						<td class="float-left">Address:</td>
						<td class="float-left"><form:errors path="address"
								class="text-danger" /> <form:input class="input form-control"
								path="address" /></td>
					</tr>
					<tr>
						<td class="float-left">Password:</td>
						<td class="float-left"><form:errors path="password"
								class="text-danger" /> <form:input type="password" class="input form-control"
								path="password" /></td>
					</tr>
					<tr>
						<td class="float-left">Confirm PW:</td>
						<td class="float-left"><form:errors
								path="passwordConfirmation" class="text-danger" /> <form:input
								class="input form-control" type="password" path="passwordConfirmation" /></td>
					</tr>
					<tr>
						<td class="float-left">picture:</td>
						<td class="float-left"><form:errors path="file_path"
								class="text-danger" /> <input type="file"
							class="input form-control" name="file" accept=".jpg, .jpeg, .png" />
						</td>
					</tr>
					<tr>
						<td colspan=2><input class="input" type="submit"
							value="Submit" /></td>
					</tr>
				</thead>
			</table>
		</form:form>
		<hr>
		<form:form action="/login" method="post" modelAttribute="login">

			<table>
				<thead>
					<tr>
						<td colspan="2">Log In</td>
					</tr>
				</thead>
				<thead>
					<tr>
						<td class="float-left">Email:</td>
						<td class="float-left"><form:errors path="email"
								class="text-danger" /> <form:input class="input" path="email" />
						</td>
					</tr>
					<tr>
						<td class="float-left">Password:</td>
						<td class="float-left"><form:errors path="password"
								class="text-danger" /> <form:input type="password" class="input" path="password" />
						</td>
					</tr>
					<tr>
						<td colspan=2><input class="input" type="submit"
							value="Submit" /></td>
					</tr>
				</thead>
			</table>
		</form:form>
	</div>
</body>
</html>