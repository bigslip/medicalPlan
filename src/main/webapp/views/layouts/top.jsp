<%@include file="/views/commons/tagLib.jsp" %>
<security:authorize ifAnyGranted="ROLE_ADMIN" >
    <a href="${pageContext.request.contextPath}/logout">Log Out</a>
</security:authorize>