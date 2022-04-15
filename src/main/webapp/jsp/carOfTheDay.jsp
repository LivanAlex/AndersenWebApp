<%@ page import="model.Car2" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html lang="en">
<%@ include file="../jsp/head.jsp" %>
<body>
<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
    <%@ include file="../jsp/menu.jsp" %>

    <main class="mdl-layout__content">
        <div class="page-content">
            <div class="mdl-grid center-items">
                <div class="mdl-cell mdl-cell--4-col">
                    <div>
                        <%
                            List<Car2> listCar = (List<Car2>) request.getAttribute("listCar");
                            if (listCar != null){
                        %>

                        <h2>Машина дня:</h2>
                        <table class="mdl-data-table mdl-js-data-table mdl-data--selectable mdl-shadow--2dp">
                            <thead>
                            <tr>
                                <th>номер</th>
                                <th>марка</th>
                                <th>модель</th>
                                <th>год</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td><c:out value="${listCar[0].regNum}"/></td>
                                <td><c:out value="${listCar[0].manufacturer}"/></td>
                                <td><c:out value="${listCar[0].model}"/></td>
                                <td><c:out value="${listCar[0].year}"/></td>
                            </tr>
                            </tbody>
                        </table>

                        <h3>В прошлый раз машиной дня была:</h3>
                        <table class="mdl-data-table mdl-js-data-table mdl-data--selectable mdl-shadow--2dp">
                            <thead>
                            <tr>
                                <th>номер</th>
                                <th>марка</th>
                                <th>модель</th>
                                <th>год</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td><c:out value="${listCar[1].regNum}"/></td>
                                <td><c:out value="${listCar[1].manufacturer}"/></td>
                                <td><c:out value="${listCar[1].model}"/></td>
                                <td><c:out value="${listCar[1].year}"/></td>
                            </tr>
                            </tbody>
                        </table>

                        <%
                            } else {
                        %>

                        <h3>Ой! что-то пошло не так...</h3>

                        <%
                            };
                        %>


                    </div>
                </div>
            </div>
        </div>
    </main>
</div>
</body>
</html>