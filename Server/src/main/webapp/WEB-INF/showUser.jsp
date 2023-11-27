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
<link rel="stylesheet" href="/css/main.css">
<!-- change to match your file/naming structure -->
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/app.js"></script>
<!-- change to match your file/naming structure -->
</head>
<body>
	<div class="container mt-5">

		<div class="row">
			<a href="/dashboardUser">Go Back</a>
			<div class="col-6">
				<form:form action="/user/${loggedUser.id}/edit" method="post"
					modelAttribute="loggedUser">
					<input type="hidden" name="_method" value="put" />
					<table>
						<thead>
							<tr>
								<td colspan="2">Edit your profile</td>
							</tr>
						</thead>
						<tbody>
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
								<td colspan=2><input class="input btn btn-info"
									type="submit" value="Edit" /></td>
							</tr>
						</tbody>
					</table>
				</form:form>
			</div>
			<div class="col-6">
				<div class="row">
					<div class="col-5">
						<c:if test="${loggedUser.file_path !=null }">
							<c:url var="imageUrl" value="/uploads/${loggedUser.file_path}" />
							<img src="${imageUrl}" alt="User Image" width="200px"
								height="300px" />
						</c:if>
						<c:if test="${loggedUser.file_path ==null }">
							<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
								fill="currentColor" class="bi bi-person-circle"
								viewBox="0 0 16 16">
  <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z" />
  <path fill-rule="evenodd"
									d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z" />
</svg>
						</c:if>
					</div>
				</div>
				<div class="row mt-3">
					<form action="/user/${loggedUser.id }/editPic" method="post"
						enctype="multipart/form-data">
						<input type="hidden" name="_method" value="put" />
						<table>
							<tr>
								<td class="float-left"><input type="file"
									class="input form-control" name="file"
									accept=".jpg, .jpeg, .png" /></td>
							</tr>
						</table>

						<hr />
						<button class="btn btn-info">Change</button>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>