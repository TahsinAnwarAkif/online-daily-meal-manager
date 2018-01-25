<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${userRole == 'user'}">
    <c:redirect url="${showMenuLink}"/>
</c:if>