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
<style>
img.scale-up {
	transform: scale(1.5);
}
</style>
</head>
<body>
	<div class="row">
		<div class="col-6">
			<h1>Create Product 📰</h1>
			<form:form action="/product/${product.id }/update" method="post"
				modelAttribute="product">
				<input type="hidden" name="_method" value="put" />
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
						<option value="">Current Category * 
							<c:out value="${product.category}"></c:out>
						</option>
						<option value="ceramics">ceramics</option>
						<option value="jewelry">jewelry</option>
						<option value="graphic arts">graphic arts</option>
						<option value="decoration">decoration</option>
					</form:select>
				</p>

				<button class="btn btn-success">Edit</button>

			</form:form>
		</div>
		<div class="col-6">
			<div class="row">

				<c:forEach items="${product.files }" var="file">
					<form action="/file/${product.id }/${file.id}/delete" method="post">
						<input type="hidden" name="_method" value="delete">
						<c:url var="imageUrl" value="/uploads/${file.path}" />
						<img src="${imageUrl}" alt="User Image" width="100px"
							height="100px" id="myImage"
							style="transition: transform 1s ease;" />
						<button class="btn btn-danger">Delete</button>
					</form>
				</c:forEach>
			</div>
			<div class="row">
				<form action="/file/${product.id }/add" method="post"
					enctype="multipart/form-data">
					<label>Pictures:</label> <input type="file" class="input"
						name="file" accept=".jpg, .jpeg, .png" multiple="multiple" />
					<button class="btn">submit</button>
				</form>
			</div>
		</div>
	</div>
	<script>
		document.getElementById("myImage").addEventListener("click",
				function() {
					this.classList.toggle("scale-up");
				});
	</script>
</body>
</html>