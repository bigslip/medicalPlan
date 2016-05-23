<%--
  Created by IntelliJ IDEA.
  User: hadi
  Date: 12/26/2015
  Time: 4:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<head>
    <spring:eval expression="@propertiesFactoryBean['welcome.url']" var="welcomeUrl" />
    <meta http-equiv="refresh" content="0;URL='${pageContext.request.contextPath}/${welcomeUrl}'" />
</head>