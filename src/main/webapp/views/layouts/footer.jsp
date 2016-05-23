<%@ page import="java.util.Locale" %>
<%@ page import="org.springframework.format.datetime.DateFormatter" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="ir.parsdeveloper.commons.utils.DateUtil" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.io.InputStream" %>
<%--
    @auther hadi tayebi
    @since  1.0
 --%>
<%@include file="/views/commons/tagLib.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<script type="text/javascript" src="${contextPath}/resources/scripts/jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${contextPath}/resources/scripts/jquery/jquery-ui-1.10.4.custom.min.js"></script>

<script type="text/javascript" src="${contextPath}/resources/scripts/bootstrap/bootstrap.js"></script>

<script type="text/javascript" src="${contextPath}/resources/scripts/core/common.js?<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="${contextPath}/resources/scripts/core/commonUtils.js"></script>


<%
    String implementationVersion = (String) pageContext.getAttribute("implementationVersion", PageContext.APPLICATION_SCOPE);
    String builtDate = (String) pageContext.getAttribute("builtDate", PageContext.APPLICATION_SCOPE);
    String builtTime = (String) pageContext.getAttribute("builtTime", PageContext.APPLICATION_SCOPE);

    if (builtDate == null || builtTime == null || implementationVersion == null) {
        InputStream inputStream = null;
        try {

            java.util.jar.Manifest manifest = new java.util.jar.Manifest();

            inputStream = pageContext.getServletContext().getResourceAsStream("/META-INF/MANIFEST.MF");
            manifest.read(inputStream);
            java.util.jar.Attributes attributes = manifest.getMainAttributes();
            String builtDateTime = attributes.getValue("Built-Time");
            implementationVersion = attributes.getValue("Implementation-Version");
            DateFormatter dateFormatter = new DateFormatter("yyyy-MM-dd HH:mm");

            try {
                Date dateTime = dateFormatter.parse(builtDateTime, Locale.ENGLISH);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateTime);
                builtDate = DateUtil.gregorianToJalali(calendar.getTime());
                builtTime = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);

                pageContext.setAttribute("implementationVersion", implementationVersion, PageContext.APPLICATION_SCOPE);
                pageContext.setAttribute("builtDate", builtDate, PageContext.APPLICATION_SCOPE);
                pageContext.setAttribute("builtTime", builtTime, PageContext.APPLICATION_SCOPE);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
%>
<div class="row footer">
   <span> builtDate :=   ${builtDate}
   </span><br/>
    <span>
    builtTime :=   ${builtTime}
    </span><br/>
    <span>
    implementationVersion := ${implementationVersion}
    </span><br/>
    <span>
    onlineUserCount := ${onlineUserHandler.onlineUserCount}
    </span><br/>

</div>