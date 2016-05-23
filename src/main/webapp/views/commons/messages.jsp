<%--
  Created by IntelliJ IDEA.
  User: h.tayebi
  Date: 10/11/11
  Time: 4:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style type="text/css">
    .error-msg {
        color: #ff0000;
        font-weight: bold;
    }

    .warning-msg {
        color: #df8505;
        font-weight: bold;
    }

    .info-msg {
        color: #25a21f;
        font-weight: bold;
    }
</style>
<c:forEach items="${flowRequestContext.messageContext.allMessages}" var="message">
    <c:choose>
        <c:when test="${message.severity eq 'ERROR' || message.severity eq 'FATAL'}">
            <div class="alert alert-error alert-danger">
                <button data-dismiss="alert" class="close" type="button">×</button>
                <span>${message.text}</span>
            </div>
        </c:when>
        <c:when test="${message.severity eq 'WARNING'}">
            <div class="alert alert-warning">
                <button data-dismiss="alert" class="close" type="button">×</button>
                <span>${message.text}</span>
            </div>
        </c:when>
        <c:when test="${message.severity eq 'INFO'}">
            <div class="alert alert-info">
                <button data-dismiss="alert" class="close" type="button">×</button>
                <span>${message.text}</span>
            </div>
        </c:when>
    </c:choose>
</c:forEach>