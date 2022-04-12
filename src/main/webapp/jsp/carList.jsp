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
                        <table class="mdl-data-table mdl-js-data-table mdl-data--selectable mdl-shadow--2dp">
                            <thead>
                            <tr>
                                <th class="mdl-data-table__cell--non-numeric">#</th>
                                <th>номер</th>
                                <th>марка</th>
                                <th>модель</th>
                                <th>год</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:set var="count" value="0" scope="page"/>
                            <c:forEach var="car" items="${listCar}">
                                <c:set var="count" value="${count + 1}" scope="page"/>
                                <tr>
                                    <td class="mdl-data-table__cell--non-numeric">
                                        <c:out value="${count}"/>
                                    </td>
                                    <td><c:out value="${car.regNum}"/></td>
                                    <td><c:out value="${car.manufacturer}"/></td>
                                    <td><c:out value="${car.model}"/></td>
                                    <td><c:out value="${car.year}"/></td>
                                    <td>
                                        <a href="/edit?regNum=<c:out value='${car.regNum}'/>">edit</a>&nbsp;&nbsp;&nbsp;&nbsp;
                                        <a href="/delete?regNum=<c:out value='${car.regNum}'/>">delete</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </main>
</div>
</body>
</html>
