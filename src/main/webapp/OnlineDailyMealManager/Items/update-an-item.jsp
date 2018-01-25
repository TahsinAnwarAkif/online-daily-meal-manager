<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <c:import url="/utils/header.jsp"/>
    <%@include file="/utils/check-role-if-user.jsp" %>
    <title>Update An Item</title>

    <h2 class="page-header text-muted" align="center">Update An Item</h2>
</head>
<body>
<form class="col-md-12" action="${itemControllerLink}" method="POST">
    <input type="hidden" name="METHOD" value="PUT"/>
    <input type="hidden" name="id" value="${id}"/>

    <div class="col-md-8">
        <div class="form-group">
            <label class="col-xs-4">Old Item Name:</label>

            <div class="col-xs-8">
                <select class="form-control" id="oldName" name="oldName">
                    <option value="${name}" selected="selected">${name}</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4">New Item Name:</label>

            <div class="col-xs-8">
                <input type="text" class="form-control" id="newName" name="newName"
                       placeholder="New Item Name">
            </div>
        </div>
    </div>
    <div class="col-md-2">
        <c:if test="${error != null}">
            <div class="alert-danger">${error}</div>
        </c:if>
    </div>
    <div class="col-md-2">
        <input type="submit" class="btn btn-default" value="Update An Item"/>
    </div>
</form>
<br/>
<br/>

<div class="col-md-12 col-md-offset-10">
    <input type="button" class="btn btn-default" value="Go To Menu"
           onClick="window.location='${showMenuLink}';"/>
</div>
<hr/>
</body>
</html>
