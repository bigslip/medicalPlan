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
<p:form>
    <p:table>
        <p:column titleKey="startDate" property="startDate"/>
        <p:column titleKey="endDate" property="endDate"/>
        <p:column titleKey="period startDate" property="period.startDate"/>
        <p:column titleKey="period endDate" property="period.endDate"/>
        <p:column titleKey="doctor" property="period.doctor.person.firstName"/>
        <p:editCell/>

    </p:table>
    <security:authorize access="hasAnyAuthority('ROLE_PLAN')">
        <p:button event="add" primary="true"
                  valueKey="Add"/>

        <p:button event="plan" primary="true"
                  valueKey="plan" onclick="setSelectedRows('plan');"/>

    </security:authorize>
</p:form>
<script>

    function setSelectedRow(rowIndex) {
        $("#plan_selected_rows").val(rowIndex);
    }
</script>

