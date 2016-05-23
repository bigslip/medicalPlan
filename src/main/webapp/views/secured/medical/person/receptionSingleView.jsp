<%--
  Created by hadi tayebi.
  email: hadi.tayebi@hotmail.com
  Date: 4/10/12
  Time: 12:00 AM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/views/commons/tagLib.jsp" %>
<style>
    label, #gender {
        color: #000000;
        font-family: Arial, 'Arial Unicode MS', Helvetica, Sans-Serif;
        text-transform: capitalize;

    }
</style>
<div class="form" dir="rtl" style="direction: rtl;">
    <p:form modelAttribute="patient">
        <%@include file="/views/secured/medical/commons/personSingleView.jsp" %>
        <p:button event="next" valueKey="next" cssClass="art-button"/>
    </p:form>
</div>
