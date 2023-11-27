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
	<h1>Create Product 📰</h1>
	<a href="/sellerDashboard">Go Back</a>
	<form:form action="/product/add" method="post" modelAttribute="product"
		enctype="multipart/form-data">

		<p>
			<form:label path="name" class="form-label">Name:</form:label>
			<form:errors path="name" />
			<form:input class="form-control" path="name" />
		</p>
		<p>
			<form:label path="description">Description:</form:label>
			<form:errors path="description" />
			<form:textarea path="description" class="form-control" />
		</p>
		<p>
			<form:label path="category">Description:</form:label>
			<form:errors path="category" />
			<form:select path="category" class="form-control">
				<option value="" selected>Choose...</option>
				<option value="textils">Textils</option>
				<option value="ceramics">Ceramics</option>
				<option value="accessories">Accessories</option>
				<option value="woodworking">WoodWorking</option>
				<option value="arts">Arts</option>
			</form:select>
		</p>
		<p>
			<form:label path="files">Pictures:</form:label>
			<form:errors path="files" class="text-danger" />
			<input type="file" class="input" name="file"
				accept=".jpg, .jpeg, .png" multiple="multiple" />
		</p>

		<div class="form-check">
			<form:checkbox class="form-check-input" id="flexCheckChecked"
				path="typeProduct" />
			<label class="form-check-label" for="flexCheckChecked"> Do
				you want to add your product as an auction? </label>
		</div>
		<p>
			<form:label path="price">Price:</form:label>
			<form:errors path="price" />
			<form:input type="number" min="0" step="0.001" path="price"
				class="form-control" />
		</p>
		<p>
			<form:label path="duration">Duration:</form:label>
			<form:errors path="duration" />
			<form:select path="duration" class="form-control">
				<option value="">Select duration of Bid</option>
				<option value="1">One Day</option>
				<option value="2">Two Days</option>
				<option value="4">Four Days</option>
			</form:select>
		</p>
		<button class="btn btn-success">Submit</button>

	</form:form>
</body>
</html>