<!DOCTYPE html >
<%@ page import="ir.parsdeveloper.commons.Constants" %>
<%@include file="/views/commons/tagLib.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%
    pageContext.setAttribute("maxUploadSize", Constants.MAX_UPLOAD_SIZE);
    pageContext.setAttribute("acceptImageMimeTypes", Constants.ACCEPT_IMAGE_MIME_TYPES);
%>


<html class="no-js">
<div id="art-main">
    <head lang="fa">
        <tiles:insertAttribute name="header"/>
    </head>
    <body>

    <div class="art-sheet clearfix">
        <div class="art-layout-wrapper">
            <div class="art-content-layout">
                <div class="art-content-layout-row">
                    <div class="art-layout-cell art-content">
                        <article class="art-post art-article">
                            <h2 class="art-postheader"><span class="art-postheadericon">Reception</span></h2>

                            <div class="art-postcontent art-postcontent-0 clearfix">
                                <div class="art-content-layout-br layout-item-0">
                                </div>
                                <div class="art-content-layout-wrapper layout-item-1">

                                        <tiles:insertAttribute name="content"/>

                                    <input type='hidden' id='_eventId' name='_eventId'/>
                                </div>
                                <div class="art-content-layout-br layout-item-0">
                                </div>
                            </div>


                        </article>
                    </div>
                    <div class="art-layout-cell art-sidebar1">
                        <div class="art-vmenublock clearfix">
                            <div class="art-vmenublockheader">
                                <h3 class="t">Navigation</h3>
                            </div>
                            <div class="art-vmenublockcontent">
                                <ul class="art-vmenu">
                                    <li><a href="departments.html" class="">Departments</a></li>
                                    <li><a href="training.html" class="">Training</a></li>
                                    <li><a href="resources.html" class="">Resources</a></li>
                                    <li><a href="about-us.html" class="">About Us</a></li>
                                    <li><a href="contact-us.html" class="">Contact Us</a></li>
                                    <li><a href="reception.html" class="active">Reception</a></li>
                                </ul>

                            </div>
                        </div>
                        <div class="art-block clearfix">
                            <div class="art-blockheader">
                                <h3 class="t">Register a Doctor</h3>
                            </div>
                            <div class="art-blockcontent"><p style="text-align:center;"><img alt="" height="160"
                                                                                             src="${pageContext.request.contextPath}/resources/images/shutterstock_15714391.jpg"
                                                                                             width="120" class=""></p>

                                <p style="text-align:center;"><br>
                                    Phasellus elit dolor, porttitor id consec tetur sit ame.<br>
                                    <br>
                                    <a href="departments.html" class="art-button">Registration</a></p></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <%--<tiles:insertAttribute name="footer"/>--%>

    </body>
</div>
</html>

<script type="text/javascript">
    formObjectName = '${formObjectName}';
</script>