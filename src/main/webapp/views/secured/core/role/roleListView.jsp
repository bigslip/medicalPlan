<%--
  Created by hadi tayebi.
  email: hadi.tayebi@hotmail.com
  Date: 4/10/12
  Time: 12:00 AM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/views/commons/tagLib.jsp" %>

<label for="theme">change theme :</label>
<select id="theme" name="theme" onchange="testTheme()">
    <option value="">select</option>
    <option value="default">default</option>
    <option value="theme2">theme2</option>
</select>

<label for="locale">change locale :</label>

<select id="locale" name="locale" onchange="changelocale()">
    <option value="">select</option>
    <option value="en_US">en</option>
    <option value="fa_IR">fa</option>
</select>


<p:table bypassSelectCell="false" bypassRowNumber="false">
    <p:column property="code" titleKey="lbl.core.role.code"/>
    <p:column property="name" titleKey="lbl.core.role.name"/>
    <p:editCell/>
    <p:deleteCell/>
</p:table>

<p:button event="add" primary="true"
          valueKey="lbl.core.btn.add"/>
<p:button event="print" newWindow="true"
          valueKey="lbl.core.btn.print"/>
<p:button event="test"
          valueKey="test" onclick="return setSelectedRows('role');"/>

<script type="text/javascript">
   function testTheme(){
       setEventId("changetheme");

       document.cookie="theme="+$('#theme').find('option:selected').val();
       $("#role").submit();
   }
   function changelocale(){
       setEventId("changelocale");
       document.cookie="locale="+$('#locale').find('option:selected').val();
       $("#role").submit();
   }
</script>
