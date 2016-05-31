<%--
  Created by hadi tayebi.
  email: hadi.tayebi@hotmail.com
  Date: 4/10/12
  Time: 12:00 AM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/views/commons/tagLib.jsp" %>

<p:form modelAttribute="patient">

        <p:table list="${patientAddedList}" bypassSelectCell="true">
            <p:column titleKey="firstName" property="person.firstName"/>
            <p:column titleKey="lastName" property="person.lastName"/>
            <p:column titleKey="nationalId" property="person.nationalId"/>
            <p:column titleKey="phone" property="person.phone"/>
            <p:column titleKey="birthDate" property="person.birthDate"/>

        </p:table>
 
    <p:button event="add" primary="true"
              valueKey="Add patient"/>
    <c:if test="${not empty patientAddedList}">
        <p:button event="plan" primary="true"
                  valueKey="plan"/>
    </c:if>

</p:form>

