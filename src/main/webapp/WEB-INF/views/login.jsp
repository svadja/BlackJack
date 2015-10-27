<%-- 
    Document   : login
    Created on : Jan 20, 2015, 9:23:57 AM
    Author     : sasav
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%> 
<html>
    <head>
        <title>Login Page</title>
        <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
    </head>
    <body onload='document.loginForm.username.focus();'>
        <div class="container">
<a href="${pageContext.request.contextPath}/init">Init test player </a> <h6>Test player: login - LuckyBoy, password - 123</h6>
            <div id="login-box" class="form-signin">
                <h3>SIGN IN</h3>
                <c:if test="${not empty error}">
                    <div class="error">${error}</div>
                </c:if>
                <c:if test="${not empty msg}">
                    <div class="msg">${msg}</div>
                </c:if>
                <form name="loginForm"
                      action="<c:url value='j_spring_security_check' />" method="POST">
                    <label for="username" class="sr-only">Login</label>
                    <input id = "username" class="form-control f-login" name="username" type="text" placeholder="Login" value='' >
                     <label for="password" class="sr-only">Password</label>
                    <input id = "password" class="form-control f-login"  name="password"  type="password" placeholder="Password" />
                    <input class="btn btn-lg btn-primary btn-block" name="submit" type="submit"	value="Sign in" />
                </form>
            </div>  
        </div>
    </body>
</html>
