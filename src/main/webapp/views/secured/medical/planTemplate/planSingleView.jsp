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
    <p:form modelAttribute="plan">
        <div class="art-layout-cell layout-item-2" style="width: 100%">


            <fieldset>
                <legend>Plan:</legend>
                <p:row col="2">
                    <p:input path="startDate" labelKey="startDate" type="inputText" cssStyle="width:150px"/>
                </p:row>

                <p:row col="2">
                    <p:input path="endDate" labelKey="endDate" type="inputText" cssStyle="width:150px"/>
                </p:row>
            </fieldset>

            <fieldset>
                <legend>Period:</legend>

                <p:row col="2">
                    <p:input path="period.startDate" labelKey="startDate" type="inputText" cssStyle="width:150px"/>
                </p:row>

                <p:row col="2">
                    <p:input path="period.endDate" labelKey="endDate" type="inputText" cssStyle="width:150px"/>
                </p:row>

                <p:textarea path="period.planTemplate.description" labelKey="description"/>
            </fieldset>
            <%--<security:authorize access="hasAnyAuthority('ROLE_PLAN')">--%>
                <p:button event="save" valueKey="save" cssClass="art-button"/>
            <%--</security:authorize>--%>
            <p:button event="back" valueKey="back" cssClass="art-button"/>
        </div>
    </p:form>

</div>
