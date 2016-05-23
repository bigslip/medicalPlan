<%--
  Created by IntelliJ IDEA.
  User: hadi
  Date: 8/11/13
  Time: 10:17 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/views/commons/tagLib.jsp" %>
<div class="container">

    <c:if test="${not empty param.log_exception_code}">
        <div class="alert alert-error">
            <button data-dismiss="alert" class="close">
                ×
            </button>
                <%--<strong>Error!</strong>--%>
            <h4><fmt:message key='errors.unknown_error'/></h4>
            <strong>
                <fmt:message key='errors.application.system_error_code'/>
                    ${param.log_exception_code}</strong>
        </div>

    </c:if>
</div>