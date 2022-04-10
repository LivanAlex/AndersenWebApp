<%@ page import="java.util.Calendar" %>
<%@ page isELIgnored="false" %>
<%--<%@ taglib prefix="tag" uri="http://www.adehermawan.com/dateFormatter" %>--%>
<header class="mdl-layout__header">
    <div class="mdl-layout__header-row">
        <%--    Title--%>
        <span class="mdl-layout-title">Cars</span>
        <div class="mdl-layout-spacer"></div>
<%--        <tag:formatDate date="<%= Calendar.getInstance().getTime()%>"--%>
<%--                        format="dd-MM-YYYY hh:mm"></tag:formatDate>--%>
        <nav class="mdl-navigation mdl-layout--large-screen-only">
            <a class="mdl-layout__link" href="/CAR/new">Add new car</a>
            <a class="mdl-layout__link" href="/CAR/new">List all cars</a>
        </nav>
    </div>
</header>
<div class="mdl-layout__drawer">
    <span class="mdl-layout-title">CAR</span>
    <nav class="mdl-navigation">
        <a class="mdl-layout__link" href="/CAR/new">Add new car</a>
        <a class="mdl-layout__link" href="/CAR/new">List all cars</a>
    </nav>
</div>
