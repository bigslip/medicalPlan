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

    <div class="art-layout-cell layout-item-2" style="width: 100%">
        <p:row col="2">
            <p:input type="inputText" path="person.firstName" labelKey="firstname" required="false"/>
        </p:row>

        <p:row col="2">
            <p:input type="inputText" path="person.lastName" labelKey="lastName" required="false"/>
        </p:row>
        <p:row col="2">
            <p:input type="inputText" path="person.birthDate" labelKey="birthDate" required="false"
                     cssStyle="width:150px"/>
        </p:row>
        <p:row col="2">

            <p>
                <span id="gender" style="margin-right: 5px" ;>Gender</span>
                <input type="radio"
                       name="person.gender"
                       checked="checked" value="1"/>Male
                <input type="radio" name="person.gender" value="2"/>Female
            </p>
        </p:row>
        <p:row col="2">
            <p:input type="inputText" path="person.phone" labelKey="phone" required="false" cssClass="numerical"/>
        </p:row>
        <p:row col="2">
            <p:input type="inputText" path="person.mobile" labelKey="mobile" required="false" cssClass="numerical"/>
        </p:row>

        <p:row col="2">
            <p:input type="textarea" path="person.address" labelKey="address" required="true"/>
        </p:row>
        <p:row col="2">
            <p:input type="inputText" path="person.nationalId" labelKey="nationalId" required="true"
                     cssClass="numerical"/>
        </p:row>

    </div>


</div>

<script>
    $(".numerical").keypress(function (event) {
        if (event.charCode >= 47 && event.charCode <= 57) {
            return true;
        } else
            return false;
    });

</script>