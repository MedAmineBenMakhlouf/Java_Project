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
	<div class="card shadow">
		<div class="card-header">
			<c:out value="${seller.username}"></c:out>
		</div>
		<div class="card-body">
			<p>

				<strong>Account created on:</strong> <fmt:formatDate value="${seller.createdAt }" pattern="MMM dd, yyyy" />
			</p>
			<div class="d-flex justify-content-between">
			
			<c:forEach items="${seller.products }" var="product">
				<div class="card shadow">
				<div class="card-header">
				
					<c:out value="${product.name }"></c:out>
				</div>
					<c:if test="${product.typeProduct==true }">
						<p>For auction</p>
					</c:if>
					<c:if test="${product.typeProduct==false }">
						<p>Not for auction</p>
					</c:if>
				</div>
			</c:forEach>
			</div>

			<c:forEach items="${seller.participations }" var="participation">
				<c:out value="${participation.amount }"></c:out>
			</c:forEach>
		</div>
	</div>

</body>
</html>