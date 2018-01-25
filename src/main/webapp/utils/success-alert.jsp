<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${alertNotificationForSuccess != null}">
    <div class="col-md-offset-5">
        <div class="alert alert-success alert-dismissable text-center" style="width: 300px">
            <strong>${alertNotificationForSuccess}</strong>
            <span class="close" onclick="this.parentElement.style.display='none';">&times;</span>
        </div>
    </div>
</c:if>
