<%@ page import="metube_app.domain.enums.HttpError" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
    <style>
        <%@include file="/styles/metube_style.css"  %>
    </style>
</head>
<body>

<% HttpError error = (HttpError) request.getAttribute("error");%>

<main>
    <h1>Woops, there was an error!</h1>
    <hr/>
    <h2>Error code: <%= error.getCode()%></h2>
    <p>Error description: <%= error.getDescription()%></p>
    <hr/>
    <a href="/">Back to Home</a>
</main>
</body>
</html>
