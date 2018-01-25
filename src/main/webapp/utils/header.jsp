<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/styles.css">
<script src="/js/bootstrap.min.js"></script>
<c:import url="/utils/mappings.jsp"/>
<h1 class="bg-success" align="center">Therap Services Online Meal Manager</h1>
<html>
<body>
<div class="row margin-top-20">
    <div class="col-md-12">
        <div class="col-md-4 col-xs-offset-5">
            <input type="button" class="btn btn-default" value="${showItems}"
                   onClick="window.location='${showItemsLink}';"/>
            <input type="button" class="btn btn-default" value="${showMeals}"
                   onClick="window.location='${showMealsLink}';"/>
            <input type="button" class="btn btn-default" value="${showItemMeals}"
                   onClick="window.location='${showItemMealsLink}';"/>
            <c:if test="${userRole == 'admin'}">
                <input type="button" class="btn btn-default" value="${create}"
                       onClick="window.location='${createItemMealsLink}';"/>
            </c:if>
        </div>
        <br/><br/>
        <div class="col-md-offset-8">
            <input type="button" class="btn btn-default" value="Logout"
                   onClick="window.location='/logout';"/>
        </div>
        <hr/>
    </div>
</div>
</body>
</html>