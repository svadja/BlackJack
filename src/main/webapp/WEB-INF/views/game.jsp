<%-- 
    Document   : game
    Created on : Oct 25, 2015, 3:18:04 PM
    Author     : Vadim
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Black Jack</title>
        <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-md-2 form-group">
                    <label for="account">Account: </label>
                    <div id="account" class="dropdown">
                        <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">${accountAmount} <span class="caret"></span></button>
                        <ul class="dropdown-menu">
                            <li><a href="#">Deposit</a></li>
                            <li><a href="#">Withdraw funds</a></li>
                        </ul>
                    </div>
                </div>
                <div class="col-md-2 form-group">
                    <label for="bet">Bet: </label>
                    <div>
                        <input id = "bet" class="form-control"  type="text" readonly="true"/>
                    </div>
                </div>
                <div class="col-md-2 form-group pull-right">
                    <label for="login">Your login: </label>
                    <div id="login" class="dropdown">
                        <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Vadim <span class="caret"></span></button>
                        <ul class="dropdown-menu">
                            <li><a href="#">Log Out</a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <h1>Game!</h1>
        </div>
    </body>
</html>
