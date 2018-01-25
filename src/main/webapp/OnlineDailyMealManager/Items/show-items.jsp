<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <c:import url="/utils/header.jsp"/>
    <title>Show All Items</title>

    <h2 class="page-header text-muted" align="center">Show Items</h2>
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
    <c:forEach var="item" items="${allItems}">
        <c:url var="updateItemLink" value="/OnlineDailyMealManager/Items" scope="request">
            <c:param name="id" value="${item.id}"/>
            <c:param name="name" value="${item.name}"/>
            <c:param name="METHOD" value="PUT"/>
        </c:url>
        <tr>
            <td>${item.name}</td>
            <c:if test="${userRole == 'admin'}">
                <td>
                    <form>
                        <input type="button" class="btn btn-default" value="${update}"
                               onClick="window.location='${updateItemLink}';"/>
                    </form>
                </td>
                <td>
                    <form action="${itemControllerLink}" method="POST">
                        <input type="hidden" name="METHOD" value="DELETE"/>
                        <input type="hidden" name="id" value="${item.id}"/s>
                        <input type="hidden" name="name" value="${item.name}"/>
                        <input type="submit" class="btn btn-default" value="${delete}"
                               onClick="if(!confirm('Are you sure you want to delete this item and all of its assigned meals?'))return false;"/>
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
               onClick="window.location='${createItemViewLink}';">
    </div>
</c:if>
<div class="col-md-offset-8">
    <input type="button" class="btn btn-default" value="Go To Menu"
           onClick="window.location='${showMenuLink}';"/>
</div>
<hr/>
</body>
</html>
