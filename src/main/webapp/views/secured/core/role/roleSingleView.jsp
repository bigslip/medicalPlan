<%--
  Created by hadi tayebi.
  email: hadi.tayebi@hotmail.com
  Date: 4/10/12
  Time: 12:00 AM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/views/commons/tagLib.jsp" %>

<div class="form" dir="rtl" style="direction: rtl;">
    <p:form modelAttribute="role">

        <p:row col="2">
            <p:input type="date" path="creationDate" labelKey="creationDate" required="false"/>
        </p:row>

        <%--

                    <table style="width: 100%">

                        <tr>
                            <td>
                                <p:label path="code" for="code" valueKey="role.code" required="true"/>

                            </td>
                            <td>

                                <p:inputText path="code"/>
                            </td>

                        </tr>

                        <tr>

                                <p:inputText path="name" titleKey="role.name"/>

                        </tr>

                        <tr>
                            <td>
                                <p:label path="active" for="active" valueKey="active" required="true"/>
                            </td>
                            <td>
                                <p:checkbox path="active"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <p:label path="application.id" valueKey="application" required="true"/>
                            </td>
                            <td>
                                <p:select path="application.id">
                                    <p:options items="${applicationList}" itemValue="id"
                                               itemLabel="name"/>
                                </p:select>
                            </td>
                        </tr>
                        <tr>
                            <p:textarea path="description" titleKey="description"/>
                        </tr>

                    </table>
                    <table>
                        <tr>
                            <td>
                                <p:button event="back" valueKey="back"/>
                            </td>
                            <td>
                                <p:button event="save" primaryButton="true" valueKey="save"/>
                            </td>
                            <td>
                                <p:button type="button" event="button" onclick="return testAjax();" valueKey="testAjax" />
                            </td>
                        </tr>
                    </table>
        --%>
        <p:button event="back" valueKey="back"/>
    </p:form>

</div>
<%--
<script type="text/javascript">
    function testAjax() {
        refreshForm('role', 'testAjax');
        return true;
    }


    function refreshForm(formId, eventId, methodType, fragments, successHandler, failHandler) {
        if (!successHandler) {
            successHandler = function (content) {
                $('#content').html(content);
            }
        }
        if (!failHandler) {
            failHandler = function (data) {
                alert('fail action');
            }
        }
        if (!fragments) {
            fragments = 'content';
        }
        if (!methodType) {
            methodType = 'POST';
        }
        var parameters = $('#' + formId).serialize();
        $.ajax({
            type: methodType,
            url: '${flowExecutionUrl}&_eventId=' + eventId + '&ajaxSource=true&fragments=' + fragments,
            data: parameters,
            dataType: "text",
            accepts: "text/html",
            success: successHandler,
            error: failHandler
        });
        return false;
    }
</script>--%>
