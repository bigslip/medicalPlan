<%--
  Created by IntelliJ IDEA.
  User: hadi
  Date: 8/11/13
  Time: 10:17 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@include file="/views/commons/tagLib.jsp" %>
<!DOCTYPE html >
<html class="no-js">
<head>
    <title>login</title>
    <meta charset="UTF-8"/>

    <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    <%--<link href="${contextPath}/resources/styles/bootstrap/css/bootstrap.css" rel="stylesheet" media="screen"/>--%>
    <%--<link href="${contextPath}/resources/styles/bootstrap/css/bootstrap-responsive.css" rel="stylesheet"--%>
          <%--media="screen"/>--%>
    <%--<link href="${contextPath}/resources/styles/bootstrap/bootstrap-rtl.css" rel="stylesheet" media="screen"/>--%>


    <%--<link href="${contextPath}/resources/styles/core/base.css" rel="stylesheet" media="screen"/>--%>

    <%--<script src="${contextPath}/resources/scripts/bootstrap/modernizr-2.6.2-respond-1.1.0.min.js"></script>--%>

    <%--<script src="${contextPath}/resources/scripts/jquery/jquery-2.1.1.min.js"></script>--%>
    <%--<script src="${contextPath}/resources/scripts/jquery/jquery-ui-1.10.4.custom.min.js"></script>--%>

    <%--<script src="${contextPath}/resources/scripts/bootstrap/bootstrap.min.js"></script>--%>
    <%--<script src="${contextPath}/resources/scripts/assets.js"></script>--%>

</head>
<body id="login">


<spring:message code="application.login" var="application_login"/>
<div id="login_container">

    <div class="container">
        <c:if test="${param.logout != null}">
            You have been logged out.
        </c:if>
        <c:if test="${param.error!=null}">
            <div id="fail" class="info_div" style="color: red;" dir="rtl">
                    <span class="ico_cancel"
                          style="padding-right:20px ">
                        <spring:message code='errors.application.authentication_failure'/> <br/>
                        <spring:message code='errors.application.user_and_password_incorrect'/>
                    </span>
            </div>
        </c:if>
        <c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
            <span>${SPRING_SECURITY_LAST_EXCEPTION.message}</span>
        </c:if>

        <form class="form-signin" action="${pageContext.request.contextPath}/login" name="loginform" id="loginform" method="post" dir="rtl">
            <security:csrfInput/>
            <h2 class="form-signin-heading"><br/>
            </h2>
            <table>
                <%--<tr>
                    <td><label for="username"><spring:message code="user.username"/></label></td>
                </tr>--%>
                <tr>
                    <td><input type="text" class="input-block-level"
                               placeholder="<spring:message code="lbl.core.user.username"/>"
                               name="username"
                               value="admin"
                               id="username" size="30"/></td>
                </tr>
                <%--<tr>
                    <td><label for="password"><spring:message code="user.password"/> </label></td>
                </tr>--%>
                <tr>

                    <td>
                        <input type="password" class="input-block-level" placeholder="password" name="password"
                               value="123"
                               id="password" size="30"/>
                    </td>
                </tr>
                <tr>
                    <%--<td></td>--%>
                    <td>
                        <label class="checkbox">
                            <input id="remember" name="j_spring_security_remember_me" style="float: right"
                                   type="checkbox"
                                   value="true">
                            <strong><label for="remember"> <span style="margin-right: 20px">
                    <spring:message code="application.remember_me"/>  </span>
                            </label>
                            </strong>

                        </label>
                    </td>
                </tr>
                <tr>

                    <td>
                        <button class="btn btn-large btn-primary" type="submit">${application_login}</button>
                    </td>
                </tr>

            </table>

        </form>

    </div>

</div>
</body>
<script type="text/javascript">
    document.loginform.username.focus();
    document.loginform.username.select();
</script>
</html>