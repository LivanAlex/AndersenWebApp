<%@ page isELIgnored="false" %>
<header class="mdl-layout__header">
    <div class="mdl-layout__header-row">
        <div class="mdl-layout-spacer"></div>
<%--        <span class="mdl-layout-title">Cars</span>--%>
        <nav class="mdl-navigation mdl-layout--large-screen-only">
            <a class="mdl-navigation__link" href="/carOfTheDay">car of the day</a>
            <a class="mdl-navigation__link" href="/new">add new car</a>
            <a class="mdl-navigation__link" href="/">list all cars</a>
        </nav>
    </div>
</header>
<div class="mdl-layout__drawer">
<%--    <span class="mdl-layout-title">CAR</span>--%>
    <nav class="mdl-navigation">
        <a class="mdl-navigation__link" href="/carOfTheDay">car of the day</a>
        <a class="mdl-navigation__link" href="/new">add new car</a>
        <a class="mdl-navigation__link" href="/">list all cars</a>
    </nav>
</div>
