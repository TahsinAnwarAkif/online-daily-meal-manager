<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <c:import url="/utils/header.jsp"/>
    <title>Show All Meals</title>

    <h2 class="page-header text-muted" align="center">Search in Menu</h2>
    <c:import url="/utils/success-alert.jsp"/>
</head>
<body>
<form id="viewForm" class="col-md-12" action="${itemMealControllerLink}" method="GET">
    <div class="col-md-9">
        <div class="form-group">
            <label class="col-xs-4">Day:</label>

            <div class="col-xs-8">
                <select class="form-control" name="day">
                    <c:if test="${selectedDay != null}">
                        <option value="${selectedDay}" selected="selected">${selectedDay}</option>
                    </c:if>
                    <c:forEach var="day" items="${allDays}">
                        <c:if test="${day != selectedDay}">
                            <option value="${day}">${day}</option>
                        </c:if>
                    </c:forEach>
                </select>
                <%--<input type="text" class="form-control" name="day" placeholder="Day">--%>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4">Meal Name:</label>

            <div class="col-xs-8">
                <select class="form-control" name="mealName">
                    <option value="all">all</option>
                    <c:if test="${selectedMealName != null && selectedMealName != 'all'}">
                        <option value="${selectedMealName}" selected="selected">${selectedMealName}</option>
                    </c:if>
                    <c:forEach var="meal" items="${allMeals}">
                        <c:if test="${meal.name != selectedMealName}">
                            <option value="${meal.name}">${meal.name}</option>
                        </c:if>
                    </c:forEach>
                </select>
                <%--<input type="text" class="form-control" name="mealName" placeholder="Meal Name">--%>
            </div>
        </div>
    </div>
    <div class="col-md-1">
        <c:if test="${error != null}">
            <div class="alert-danger">${error}</div>
        </c:if>
    </div>
    <div class="col-md-2">
        <input type="submit" class="btn btn-default" value="Find Meals"/>

        <form id="createForm" action="${mealItemControllerLink}" method="POST">
            <input type="hidden" name="day" value="${selectedDay}"/>
            <input type="hidden" name="mealName" value="${selectedMealName}"/>
            <c:if test="${userRole == 'admin'}">
                <button type="submit" class="btn btn-default" name="METHOD" value="POST">${create}</button>
            </c:if>
        </form>
    </div>
</form>
<br/>
<br/>
<table border="3" class="table-default" align="center">
    <tr>
        <th class="tg-i6eq">Name</th>
        <th class="tg-i6eq">For:</th>
        <c:if test="${userRole == 'admin'}">
            <th class="tg-i6eq">Delete</th>
        </c:if>
    </tr>
    <form id="deleteForm" action="${itemMealControllerLink}" method="POST">
        <input type="hidden" name="METHOD" value="DELETE"/>
        <input type="hidden" name="day" value="${selectedDay}"/>
        <input type="hidden" name="mealName" value="${selectedMealName}"/>
        <c:forEach var="itemMeal" items="${allItemMeals}">
            <tr>
                <td><input style="border: 0" value="${itemMeal.item.name}" readonly/></td>
                <td><input style="border: 0" value="${itemMeal.meal.name}" readonly/></td>
                <c:if test="${userRole == 'admin'}">
                    <td>
                        <input type="checkbox" name="toDeletes" value="${itemMeal.id}"/>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
    </form>
</table>
<br/><br/>
<c:if test="${userRole == 'admin'}">
    <div class="text-center">
        <input type="submit" class="btn btn-default" value="${delete}" form="deleteForm"
               onClick="if(!confirm('Are you sure you want to delete these meals?'))return false;"/>
    </div>
</c:if>
<div class="col-md-offset-10">
    <input type="button" class="btn btn-default" value="Go To Menu"
           onClick="window.location='${showMenuLink}';"/>
</div>
<hr/>
</body>
</html>