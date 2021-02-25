<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <c:set var="id" value="${requestScope.candidate.id}" scope="session"/>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

    <title>Работа мечты</title>
</head>
<body>
<div class="container pt-3">
    <div class="row">
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/posts.do">Вакансии</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/candidates.do">Кандидаты</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/post/edit.do">Добавить вакансию</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/candidate/edit.do">Добавить кандидата</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp"> <c:out value="${user.name}"/> | Выйти</a>
            </li>
        </ul>
    </div>
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <c:if test="${requestScope.candidate.id == 0}">
                    Новый кандидат.
                </c:if>
                <c:if test="${requestScope.candidate.id != 0}">
                    Редактирование кандидата.
                </c:if>
            </div>
            <div class="card-body">
                <c:if test="${requestScope.candidate.id != 0}">
                    <a href="editAvatar.jsp?id=${requestScope.candidate.id}">
                        <img src="<c:url value='/candidate/download.do?id=${requestScope.candidate.id}'/>" width="200px" height="200px"/>
                    </a>
                </c:if>
                    <form action="<c:url value="/candidates.do?id=${requestScope.candidate.id}"/>" method="post" onsubmit="return validateInput()">
                        <div class="form-group">
                            <label for="nameInput">Имя</label>
                            <input id="nameInput" type="text" class="form-control" name="name" value="${requestScope.candidate.name}">
                        </div>
                        <div class="form-group">
                        <label for="cities">Город</label>
                        <select class="form-select" aria-label="Default select example" id="cities" name="city_id"></select>
                        </div>
                        <button type="submit" class="btn btn-primary">Сохранить</button>
                    </form>
                <c:if test="${requestScope.candidate.id != 0}">
                    <form action="delete.do?id=${requestScope.candidate.id}" method="post">
                        <button type="submit" class="btn btn-primary">Удалить кандидата</button>
                    </form>
                </c:if>
            </div>
        </div>
    </div>
    <input type="hidden" id="xID" name="x" value="${requestScope.candidate.cityID}">
</div>
<script src="https://code.jquery.com/jquery-3.4.1.min.js" ></script>
<script src="../edit.js"></script>
</body>
</html>