<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url var="showMenuLink" value="/OnlineDailyMealManager/show-menu.jsp" scope="session"/>
<c:url var="showItemsLink" value="/OnlineDailyMealManager/Items" scope="session">
    <c:param name="METHOD" value="GET"/>
</c:url>
<c:url var="createItemViewLink" value="/OnlineDailyMealManager/Items/create-an-item.jsp" scope="session"/>
<c:url var="createMealViewLink" value="/OnlineDailyMealManager/Meals/create-a-meal.jsp" scope="session"/>
<c:url var="showMealsLink" value="/OnlineDailyMealManager/Meals" scope="session">
    <c:param name="METHOD" value="GET"/>
</c:url>
<c:url var="showItemMealsLink" value="/OnlineDailyMealManager/ItemMeals" scope="session">
    <c:param name="METHOD" value="GET"/>
</c:url>
<c:url var="createItemMealsLink" value="/OnlineDailyMealManager/ItemMeals" scope="session">
    <c:param name="METHOD" value="POST"/>
</c:url>
<%--<c:url var="createItemMealViewLink" value="/OnlineDailyMealManager/ItemMeals/create-an-item-meal.jsp" scope="session"/>--%>
<c:url var="loginControllerLink" value="/login" scope="session"/>
<c:url var="itemControllerLink" value="/OnlineDailyMealManager/Items" scope="session"/>
<c:url var="mealControllerLink" value="/OnlineDailyMealManager/Meals" scope="session"/>
<c:url var="mealItemControllerLink" value="/OnlineDailyMealManager/ItemMeals" scope="session"/>