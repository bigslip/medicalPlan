<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag body-content="scriptless" display-name="row" description="write bootstrap div with row class" %>
<%@attribute name="col" type="java.lang.Integer" %>
<div class="row">
    <c:set var="_rowColumnCount" value="${col == null ? pageContext.servletContext.getInitParameter('defaultRowColumn') : 2 }"/>
    <jsp:doBody />

</div>
