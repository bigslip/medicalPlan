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
    <p:form modelAttribute="planTemplate">
        <div class="art-layout-cell layout-item-2" style="width: 100%">
            <fieldset>
                <legend>Doctor:</legend>
                <p:row col="2">
                    <p:select path="doctorId" items="${doctorList}" itemLabel="person.firstName" itemValue="id"
                              labelKey="doctor"/>
                </p:row>
            </fieldset>

            <fieldset>
                <legend>Period:</legend>
                <p:row col="2">
                    <p:input path="period.startDate" labelKey="startDate" type="date" cssStyle="width:150px"/>
                </p:row>

                <p:row col="2">
                    <p:input path="period.endDate" labelKey="endDate" type="date" cssStyle="width:150px"/>
                </p:row>



            </fieldset>

            <p:button event="save" valueKey="save" cssClass="art-button"/>

            <p:button event="back" valueKey="back" cssClass="art-button"/>
        </div>
    </p:form>

</div>
