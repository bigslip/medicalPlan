<%@page pageEncoding="UTF-8" %>
<%@include file="/views/commons/tagLib.jsp" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<title>new core</title>

<meta name="Description" content="Information architecture, Web Design, Web Standards."/>
<meta name="Keywords" content="cms,mycms"/>
<meta name="Distribution" content="Global"/>
<meta name="Author" content="hadi tayebi"/>
<meta name="Robots" content="index,follow"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta name="_csrf" content="${_csrf.token}"/>
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<script src="${pageContext.request.contextPath}/resources/scripts/medical/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/scripts/core/common.js"></script>
<script src="${pageContext.request.contextPath}/resources/scripts/medical/script.js"></script>
<script src="${pageContext.request.contextPath}/resources/scripts/medical/script.responsive.js"></script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/medical/style.css" media="screen">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/medical/style.ie7.css" media="screen"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/medical/style.responsive.css"
      media="all">


<style>.art-content .art-postcontent-0 .layout-item-0 {
    border-top-width: 1px;
    border-top-style: solid;
    border-top-color: #B9DBFE;
    margin-top: 0px;
    margin-bottom: 0px;
}

.art-content .art-postcontent-0 .layout-item-1 {
    margin-top: 0px;
    margin-bottom: 0px;
}

.art-content .art-postcontent-0 .layout-item-2 {
    border-right-style: solid;
    border-bottom-style: solid;
    border-top-width: 500px;
    border-right-width: 0px;

    border-right-color: #B9DBFE;
    border-bottom-color: #B9DBFE;
    padding-top: 0px;
    padding-right: 3px;
    padding-bottom: 0px;
    padding-left: 3px;
}

.ie7 .art-post .art-layout-cell {
    border: none !important;
    padding: 0 !important;
}

.ie6 .art-post .art-layout-cell {
    border: none !important;
    padding: 0 !important;
}

</style>
<nav class="art-nav">
    <%--<ul class="art-hmenu">--%>
    <%--<li><a href="home.html" class="">Home</a></li>--%>
    <%--<li><a href="departments.html" class="">Departments</a></li>--%>
    <%--<li><a href="about-us.html" class="">About Us</a></li>--%>
    <%--<li><a href="contact-us.html" class="">Contact Us</a></li>--%>
    <%--<li><a href="reception.html" class="active">Reception</a></li>--%>
    <%--</ul>--%>
</nav>
<header class="art-header">

    <div class="art-shapes">

    </div>

    <h1 class="art-headline">
        <a href="/">CYROX HOSPITAL</a>
    </h1>
    <h2 class="art-slogan">For more information, please call at 555-123-4567</h2>


</header>
