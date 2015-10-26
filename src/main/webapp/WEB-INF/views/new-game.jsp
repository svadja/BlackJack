<%-- 
    Document   : game
    Created on : Oct 25, 2015, 3:18:04 PM
    Author     : Vadim
--%>
<%@taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Black Jack</title>
        <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/resources/css/jquery.bootstrap-touchspin.min.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/jquery.bootstrap-touchspin.min.js"></script>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-md-2 form-group">
                    <label for="account">Account: </label>
                    <div id="account" class="dropdown">
                        <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">${accountAmount} <span class="caret"></span></button>
                        <ul class="dropdown-menu">
                            <li><a href="${pageContext.request.contextPath}/deposit">Deposit</a></li>
                            <li><a href="#">Withdraw funds</a></li>
                        </ul>
                    </div>
                </div>
                <div class="col-md-2 form-group">
                    <label for="bet">Bet: </label>
                    <div>
                        <input id = "bet" class="form-control bfh-number" type="text"/>
                    </div>
                </div>
                <div class="col-md-2 form-group pull-right">
                    <label for="login">Your login: </label>
                    <div id="login" class="dropdown">
                        <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">${pageContext.request.userPrincipal.name} <span class="caret"></span></button>
                        <ul class="dropdown-menu">
                            <li> <a href="<c:url value='j_spring_security_logout'/>" > Logout</a> </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="row">
                <div  class="col-md-2 col-md-offset-5">
                    <h1>${message}</h1>
                </div>
            </div> 
            <div class="row row-new-game">
                <div  class="col-md-2 col-md-offset-5">
                    <button id="btn-new-game" class="btn btn-primary btn-lg" type="button" data-toggle="dropdown">START NEW GAME</button>
                </div>
            </div> 
        </div>
    </body>

    <script>var ctx = "${pageContext.request.contextPath}"</script>
    <script src="${pageContext.request.contextPath}/resources/js/new-game.js"></script>  

</html>
