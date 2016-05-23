<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container-fluid">
            <a data-target=".nav-collapse" data-toggle="collapse" class="btn btn-navbar"> <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>
            <a href="home" class="brand">Admin Panel2</a>

            <div class="nav-collapse collapse">
                <ul class="nav pull-right">
                    <li class="dropdown">
                        <a data-toggle="dropdown" class="dropdown-toggle" role="button" href="#"> <i
                                class="icon-user"></i> Vincent Gabriel <i class="caret"></i>

                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="#" tabindex="-1">Profile</a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="login.html" tabindex="-1">Logout</a>
                            </li>
                        </ul>
                    </li>
                </ul>
                <ul class="nav">
                    <li class="active">
                        <a href="#">Dashboard</a>
                    </li>
                    <c:forEach items="${sessionScope.MENU_LIST}" var="menu" varStatus="stateVAR">
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">${menu.name}<b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <c:forEach items="${menu.subMenu}" var="submenu">
                                    <li>
                                        <a href="${contextPath}/${submenu.url}">${submenu.name}</a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:forEach>
                    <%--
                     <li class="dropdown">
                         <a class="dropdown-toggle" data-toggle="dropdown" href="#">Settings <b class="caret"></b>

                         </a>
                         <ul id="menu1" class="dropdown-menu">
                             <li>
                                 <a href="#">Tools <i class="icon-arrow-right"></i>

                                 </a>
                                 <ul class="dropdown-menu sub-menu">
                                     <li>
                                         <a href="#">Reports</a>
                                     </li>
                                     <li>
                                         <a href="#">Logs</a>
                                     </li>
                                     <li>
                                         <a href="#">Errors</a>
                                     </li>
                                 </ul>
                             </li>
                             <li>
                                 <a href="#">SEO Settings</a>
                             </li>
                             <li>
                                 <a href="#">Other Link</a>
                             </li>
                             <li class="divider"></li>
                             <li>
                                 <a href="#">Other Link</a>
                             </li>
                             <li>
                                 <a href="#">Other Link</a>
                             </li>
                         </ul>
                     </li>
                     <li class="dropdown">
                         <a data-toggle="dropdown" class="dropdown-toggle" role="button" href="#">Content <i
                                 class="caret"></i>

                         </a>
                         <ul class="dropdown-menu">
                             <li>
                                 <a href="#" tabindex="-1">Blog</a>
                             </li>
                             <li>
                                 <a href="#" tabindex="-1">News</a>
                             </li>
                             <li>
                                 <a href="#" tabindex="-1">Custom Pages</a>
                             </li>
                             <li>
                                 <a href="#" tabindex="-1">Calendar</a>
                             </li>
                             <li class="divider"></li>
                             <li>
                                 <a href="#" tabindex="-1">FAQ</a>
                             </li>
                         </ul>
                     </li>
                     <li class="dropdown">
                         <a data-toggle="dropdown" class="dropdown-toggle" role="button" href="#">Users <i
                                 class="caret"></i>

                         </a>
                         <ul class="dropdown-menu">
                             <li>
                                 <a href="#" tabindex="-1">User List</a>
                             </li>
                             <li>
                                 <a href="#" tabindex="-1">Search</a>
                             </li>
                             <li>
                                 <a href="#" tabindex="-1">Permissions</a>
                             </li>
                         </ul>
                     </li>--%>
                </ul>
            </div>
            <!--/.nav-collapse -->
        </div>
    </div>
</div>