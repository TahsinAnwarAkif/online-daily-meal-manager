<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <c:import url="/utils/header.jsp"/>
    <%@include file="/utils/check-role-if-user.jsp" %>
    <h2 class="page-header text-muted" align="center">Insert in Menu</h2>
    <title>Create A Meal</title>
</head>
<body>
<form class="col-md-12" action="${mealItemControllerLink}" method="POST">
    <input type="hidden" name="METHOD" value="POST"/>

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
            <div class="form-group">
                <label class="col-xs-4">Available Items:</label>

                <div class="col-xs-8">
                    <select class="form-control" name="itemsToBeAdded" multiple>
                        <c:forEach var="item" items="${allItems}">
                            <option value="${item.name}">${item.name}</option>
                        </c:forEach>
                    </select>
                    <%--<input type="text" class="form-control" name="itemsToBeAdded" placeholder="Item Name">--%>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-1">
        <c:if test="${error != null}">
            <div class="alert-danger">${error}</div>
        </c:if>
    </div>
    <div class="col-md-2">
        <input type="submit" class="btn btn-default" value="Create Meal"/>
    </div>
</form>
<br/>
<br/>

<div class="col-md-offset-10">
    <input type="button" class="btn btn-default" value="Go To Menu"
           onClick="window.location='${showMenuLink}';"/>
</div>
<hr/>
</body>
</html>