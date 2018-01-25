<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <c:import url="/utils/header.jsp"/>
    <%@include file="/utils/check-role-if-user.jsp" %>
    <title>Create An Item</title>
    <h2 class="page-header text-muted" align="center">Create An Item</h2>
</head>
<body>
<form class="col-md-12" action="${itemControllerLink}" method="POST">
    <input type="hidden" name="METHOD" value="POST"/>

    <div class="col-md-6">
        <div class="form-group">
            <label class="col-xs-4">Item Name:</label>

            <div class="col-xs-8">
                <input type="text" class="form-control" name="name" placeholder="Item Name">
            </div>
        </div>
    </div>
    <div class="col-md-2">
        <c:if test="${error != null}">
            <div class="alert-danger">${error}</div>
        </c:if>
    </div>
    <div class="col-md-4">
        <input type="submit" class="btn btn-default" value="Create An Item"/>
    </div>
</form>
<br/>
<br/>

<div class="col-md-12 col-md-offset-8">
    <input type="button" class="btn btn-default" value="Go To Menu"
           onClick="window.location='${showMenuLink}';"/>
</div>
<hr/>
</body>
</html>
