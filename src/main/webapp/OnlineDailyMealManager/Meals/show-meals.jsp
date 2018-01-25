<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <c:import url="/utils/header.jsp"/>
    <title>Show All Items</title>

    <h2 class="page-header text-muted" align="center">Show Meals</h2>
    <c:import url="/utils/success-alert.jsp"/>
</head>
<body>
<table border="3" class="table-default" align="center">
    <tr>
        <th class="tg-i6eq">Name</th>
        <c:if test="${userRole == 'admin'}">
            <th class="tg-i6eq">Update</th>
            <th class="tg-i6eq">Delete</th>
        </c:if>
    </tr>
    <c:forEach var="meal" items="${allMeals}">
        <c:url var="updateMealLink" value="${mealControllerLink}" scope="request">
            <c:param name="id" value="${meal.id}"/>
            <c:param name="name" value="${meal.name}"/>
            <c:param name="METHOD" value="PUT"/>
        </c:url>
        <tr>
            <td>${meal.name}</td>
            <c:if test="${userRole == 'admin'}">
                <td>
                    <form>
                        <input type="button" class="btn btn-default" value="${update}"
                               onClick="window.location='${updateMealLink}';"/>
                    </form>
                </td>
                <td>
                    <form action="${mealControllerLink}" method="POST">
                        <input type="hidden" name="METHOD" value="DELETE"/>
                        <input type="hidden" name="id" value="${meal.id}"/>
                        <input type="hidden" name="name" value="${meal.name}"/>
                        <input type="submit" class="btn btn-default" value="${delete}"
                               onClick="if(!confirm('Are you sure you want to delete this meal?'))return false;"/>
                    </form>
                </td>
            </c:if>
        </tr>
    </c:forEach>
</table>
<br/><br/>
<c:if test="${userRole == 'admin'}">
    <div class="text-center">
        <input type="button" class="btn btn-default" value="${create}"
               onClick="window.location='${createMealViewLink}';">
    </div>
</c:if>
<div class="col-md-offset-8">
    <input type="button" class="btn btn-default" value="Go To Menu"
           onClick="window.location='${showMenuLink}';"/>
</div>
<hr/>
</body>
</html>
