<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!doctype html>
<html lang="en">
<%@ include file="../jsp/head.jsp" %>
<body>
<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
    <%@ include file="../jsp/menu.jsp" %>
    <main class="page-content">
        <div class="mdl-grid center-items">
            <div class="mdl-cell mdl-cell--4-col">
                <div class="mdl-card mdl-shadow--6dp">
                    <div class="mdl-card__title mdl-color--primary mdl-color-text--white">
                        <h2 class="mdl-card__title-text">
                            <c:if test="${car =! null}">Edit Car</c:if>
                            <c:if test="${car == null}">Add New Car</c:if>
                        </h2>
                    </div>
                    <div class="mdl-card__supporting-text">
                        <c:if test="${car =! null}">
                            <form name="myForm" action="update" method="post" onsubmit="return validateForm()">
                        </c:if>
                        <c:if test="${car == null}">
                            <form name="myForm" action="insert" method="post" onsubmit="return validateForm()">
                        </c:if>

                        <c:if test="${car =! null}">
                            <input type="hidden" name="regNum"
                                   value="<c:out value='${car.regNum}'/>"/>
                        </c:if>

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


                </div>

            </div>
        </div>
    </main>

</div>
<script type="text/javascript">
    function validateForm() {
        var x= document.forms["myForm"]["regNum"].value;
        if (x == ""){
            alert("regNum must be entered")
            return false;
        }
    }
</script>
</body>
</html>