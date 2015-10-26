<%-- 
    Document   : login
    Created on : Jan 20, 2015, 9:23:57 AM
    Author     : sasav
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%> 
<html>
    <head>
        <title>Login Page</title>
        <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="container">
            <div id="deposit-box" class="form-deposit">
                <h3>DEPOSIT ACCOUNT</h3>
                <form:form action="${pageContext.request.contextPath}/deposit" id="deposit-form" method="post" modelAttribute="depositAcc">

                    <div>
                        <label>Account: </label>
                    </div>
                    <div  class="form-group">
                        <input  class="form-control" id="account-number" readonly="true" value="${depositAcc.account.number}"/>
                    </div>
                    <div>
                        <label>Amount: </label>
                    </div>
                    <div  class="form-group">
                        <form:input  class="form-control" path="amount"/>
                    </div>
                    <button type="submit" class="btn btn-lg btn-primary btn-block">Send</button>
                </form:form>
            </div>  
        </div>
    </body>
</html>
