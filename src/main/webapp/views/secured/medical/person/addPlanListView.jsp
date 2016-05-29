<%--
  Created by hadi tayebi.
  email: hadi.tayebi@hotmail.com
  Date: 4/10/12
  Time: 12:00 AM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/views/commons/tagLib.jsp" %>

<p:form modelAttribute="patient">
    <div id="searchPlan">
        <p:row col="2">
            <p:input path="person.nationalId" labelKey="nationalId" required="false"/>
            <p:button event="search" valueKey="search" cssClass="art-button"/>
        </p:row>

    </div>

    <p:table list="${tempPatientList}">
        <p:column titleKey="firstName" property="person.firstName"/>
        <p:column titleKey="lastName" property="person.lastName"/>
        <p:column titleKey="nationalId" property="person.nationalId"/>
        <p:column titleKey="phone" property="person.phone"/>
        <p:column titleKey="birthDate" property="person.birthDate"/>
        <p:column titleKey="select">
            <p:button event="select" primary="true" cssClass="select"
                      valueKey="select" onclick="setSelectedRow(this);"/>
        </p:column>
    </p:table>
    <p:button event="back" primary="true" cssClass="back"
              valueKey="back" onclick="setSelectedRow(this);"/>

</p:form>

<script>

    $(".select").click(function () {
        var patientId = $(this).closest("tr").attr("id");
        $("#patient_selected_rows").val(patientId);
    });


</script>
