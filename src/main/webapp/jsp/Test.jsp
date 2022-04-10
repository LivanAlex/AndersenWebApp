<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<%@ include file="../jsp/head.jsp" %>
<body>
<%@ include file="../jsp/menu.jsp" %>
<div>
    <p>"<c:out value='${car.regNum}'/>"</p>


    <form name="myForm" action="update" method="post" onsubmit="return validateForm()">


        <c:choose>
            <c:when test="${car =! null}">
                pizza.
                <br/>
            </c:when>
            <c:otherwise>
                pizzas.
                <br/>
            </c:otherwise>
        </c:choose>


        <div class="mdl-textfield mdl-js-textfield">
            <input class="mdl-textfield__input" type="text" name="manufacturer"
                   value="<c:out value='${car.manufacturer}'/>" id="manufacturer"/>
            <label class="mdl-textfield__label" for="manufacturer">Manufacturer</label>
        </div>

        <div class="mdl-textfield mdl-js-textfield">
            <input class="mdl-textfield__input" type="text" name="model"
                   value="<c:out value='${car.model}'/>" id="model"/>
            <label class="mdl-textfield__label" for="model">Model</label>
        </div>

        <div class="mdl-textfield mdl-js-textfield">
            <input class="mdl-textfield__input" type="text" name="year"
                   value="<c:out value='${car.year}'/>" id="year"/>
            <label class="mdl-textfield__label" for="year">Year</label>
        </div>

        <input type="submit"
               class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect"
               value="save"></form>
</div>
</body>
</html>
