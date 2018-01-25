<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/styles.css">
    <script src="/js/bootstrap.min.js"></script>
    <c:import url="/utils/mappings.jsp"/>
    <h1 class="bg-success" align="center">Therap Services Online Meal Manager</h1>
    <h2 class="page-header text-muted" align="center">Login</h2>
    <title>Therap Services Online Meal Manager</title>
</head>
<body>
<form class="col-md-12" action="${loginControllerLink}" method="POST">
    <div class="col-md-6">
        <div class="form-group">
            <label class="col-xs-4">User Name:</label>

            <div class="col-xs-8">
                <input type="text" class="form-control" name="userName" placeholder="User Name">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4">Password:</label>

            <div class="col-xs-8">
                <input type="password" class="form-control" name="userPassword"
                       placeholder="Password">
            </div>
        </div>
    </div>
    <div class="col-md-1">
        <c:if test="${authError != null}">
            <div class="alert-danger">${authError}</div>
        </c:if>
    </div>
    <div class="col-md-5">
        <input type="submit" class="btn btn-default" value="Login"/>
    </div>
</form>
<hr/>
</body>
</html>