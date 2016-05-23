<%@tag description="draw filter sections on top of listview" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="p" uri="http://parsdeveloper.ir" %>

<%@attribute name="fields" type="java.lang.String" required="true" %>
<div class="row">
    <div class="col-md-12">
        <div class="panel-body">
            <div class="form-horizontal form-groups-bordered">
                <div class="form-group">
                    <c:forTokens items="${fields}" delims=";" var="field" varStatus="sts">
                        <c:choose>
                            <c:when test="${fn:contains(field,'[')}">
                                <label class="col-sm-2 control-label" for="${field}">
                                    <spring:message code="${fn:split(field, '[')[0]}"/>
                                </label>
                                <p:select path="${fn:split(field, '[')[0]}"
                                          items="${pageContext.findAttribute(fn:substringBefore(fn:split(field, '[')[1], ']'))}"
                                          cssClass="col-sm-2 " itemLabel="name" itemValue="id" required="true"
                                          addDefaultOption="true" noTdTag="true"/>
                            </c:when>
                            <c:otherwise>
                                <label class="col-sm-2 control-label" for="${field}">
                                    <spring:message code="${formObjectName}.${field}"/>
                                </label>
                                <div class="col-sm-2">
                                    <p:input type="text" path="${field}"  noTdTag="true"/>
                                </div>
                            </c:otherwise>
                        </c:choose>
                        <c:if test="${((sts.index + 1) mod 3) eq 0}">
                            <div class="clear"></div>
                            <br>
                        </c:if>
                    </c:forTokens>

                    <div class="clear"></div>
                    <br>

                    <div class="col-sm-12">
                        <button id="_eventId_search" class="btn-success btn-lg btn" value="search" type="submit"
                                onclick="thisWindow(this);" name="_eventId">
                            <fmt:message key="action.search"/>
                        </button>
                    </div>

                </div>
            </div>
        </div>

    </div>
</div>