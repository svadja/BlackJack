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

                        <c:choose>
                            <c:when test="${userGame.status == 'PROCESS'}">
                                <input class="form-control" type="text" value="${userGame.bet}" readonly="true"/> 
                            </c:when>
                            <c:otherwise> 
                                <input id = "bet" class="form-control bfh-number" type="text" value="${userGame.bet}"/> 
                            </c:otherwise>
                        </c:choose>   

                    </div>
                </div>
                <div class="col-md-2 form-group pull-right">
                    <label for="login">Your login: </label>
                    <div id="login" class="dropdown">
                        <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">${pageContext.request.userPrincipal.name} <span class="caret"></span></button>
                        <ul class="dropdown-menu">
                            <li> <a href="<c:url value='j_spring_security_logout'/>"> Logout</a> </li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="row zone-dealer">
                <div class="col-md-4 col-md-offset-4">

                    <c:forEach var="card" items="${userGame.dealerSet}">
                        <img src="${pageContext.request.contextPath}/resources/img/cards/${card.suit}${card.rank}.png">
                    </c:forEach>
                </div>  

            </div> 
            <div class="row zone-game">

                <c:choose>
                    <c:when test="${userGame.status == 'PROCESS'}">

                        <div  class="col-md-2 col-md-offset-5">
                            <h3>GOOD LACK</h3>
                        </div>
                        <div  class="col-md-2 col-md-offset-5">
                            <button id="btn-hit" class="btn btn-primary btn-lg" type="button">HIT</button>
                            <button id="btn-stand" class="btn btn-primary btn-lg" type="button">STAND</button>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div  class="col-md-2 col-md-offset-5">
                            <c:choose>
                                <c:when test="${userGame.status ==  'NEW'}"><h3>WELCOME</h3></c:when>
                                <c:when test="${userGame.status ==  'PLAYER_WIN'}"><h3>YOUR WIN</h3></c:when>
                                <c:when test="${userGame.status ==  'PLAYER_LOST'}"><h3>YOU LOST</h3></c:when>
                                <c:when test="${userGame.status ==  'PUSH'}"><h3>PUSH</h3></c:when>
                                <c:when test="${userGame.status ==  'PLAYER_BJ'}"><h3>BLACK JACK</h3></c:when>
                            </c:choose>
                        </div>
                        <div  class="col-md-2 col-md-offset-5">
                            <button id="btn-new-game" class="btn btn-primary btn-lg" type="button">START NEW GAME</button>
                        </div>    
                    </c:otherwise>
                </c:choose>           


            </div> 
            <div class="row zone-player">
                <div class="col-md-4 col-md-offset-4">
                    <c:forEach var="card" items="${userGame.playerSet}">
                        <img src="${pageContext.request.contextPath}/resources/img/cards/${card.suit}${card.rank}.png">
                    </c:forEach>
                </div>
            </div>             


        </div>
    </body>

    <script>var ctx = "${pageContext.request.contextPath}"</script>
    <script src="${pageContext.request.contextPath}/resources/js/game.js"></script>  

</html>
