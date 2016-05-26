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
        <p:column titleKey="firstName" property="person.firstName"/>
        <p:editCell/>

    </p:table>
    <p:button event="add" primary="true"
              valueKey="Add"/>
</p:form>
<script>

    function setSelectedRow(rowIndex) {
        $("#doctor_selected_rows").val(rowIndex);
    }
</script>
