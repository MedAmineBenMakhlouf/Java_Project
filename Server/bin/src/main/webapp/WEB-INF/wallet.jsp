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
	Your balance is: <strong> <c:out value="${wallet.balance }"></c:out> </strong>how much do you want to add to your wallet?
	<form action="/wallet/update/${wallet.id}" method="post" modelAttribute="wallet">
		<input type="hidden" name="_method" value="put"/>
		<p>
			<label path="balance" class="form-label">Balance</label>
			
			<input type="number" class="form-control" path="balance" min="0" step="0.001" name="balance"/>
		</p>
		<button>Add</button>
	</form>
</body>
</html>