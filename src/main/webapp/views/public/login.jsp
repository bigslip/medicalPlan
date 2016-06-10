<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@include file="/views/commons/tagLib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    <!-- General meta information -->
    <title>Login Form by Oussama Afellad</title>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="robots" content="index, follow"/>
    <meta charset="utf-8"/>
    <!-- // General meta information -->


    <!-- Load Javascript -->
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/scripts/core/login/jquery.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/scripts/core/login/jquery.query-2.1.7.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/scripts/core/login/rainbows.js"></script>
    <!-- // Load Javascipt -->

    <!-- Load stylesheets -->
    <link type="text/css" rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/styles/_core/login/style.css" media="screen"/>
    <!-- // Load stylesheets -->

    <script>


        $(document).ready(function () {

            $("#submit1").hover(
                    function () {
                        $(this).animate({"opacity": "0"}, "slow");
                    },
                    function () {
                        $(this).animate({"opacity": "1"}, "slow");
                    });
        });


    </script>

</head>
<body id="login">
<spring:message code="application.login" var="application_login"/>
<c:if test="${param.logout != null}">
    You have been logged out.
</c:if>
<c:if test="${param.error!=null}">
    <div id="fail" class="info_div" style="color: red;" dir="rtl">
                    <span class="ico_cancel"
                          style="padding-right:20px ">
                        <spring:message code='errors.application.authentication_failure'/> <br/>
                        <spring:message code='Username or password is incorrect'/>
                    </span>
    </div>
</c:if>
<c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
    <span>${SPRING_SECURITY_LAST_EXCEPTION.message}</span>
</c:if>
<div id="wrapper">
    <div id="wrappertop"></div>

    <div id="wrappermiddle">

        <h2>Login</h2>
        <form action="${pageContext.request.contextPath}/login" name="loginform" id="loginform" method="post">
            <security:csrfInput/>
            <div id="username_input">

                <div id="username_inputleft"></div>

                <div id="username_inputmiddle">

                    <input type="text" name="username" id="url" value="Username" onclick="this.value = ''">
                    <img id="url_user" src="${pageContext.request.contextPath}/resources/images/login/mailicon.png"
                         alt="">

                </div>

                <div id="username_inputright"></div>

            </div>

            <div id="password_input">

                <div id="password_inputleft"></div>

                <div id="password_inputmiddle">

                    <input type="password" name="password" id="url" value="Password" onclick="this.value = ''">
                    <img id="url_password"
                         src="${pageContext.request.contextPath}/resources/images/login/passicon.png"
                         alt="">

                </div>

                <div id="password_inputright"></div>

            </div>

            <div id="submit">

                <input type="image" src="${pageContext.request.contextPath}/resources/images/login/submit_hover.png"
                       id="submit1" value="Sign In">
                <input type="image" src="${pageContext.request.contextPath}/resources/images/login/submit.png"
                       id="submit2" value="Sign In">

            </div>

        </form>




    </div>

    <div id="wrapperbottom"></div>


</div>

</body>
</html>