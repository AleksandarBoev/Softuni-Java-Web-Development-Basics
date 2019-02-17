<%@ page import="metube_app.domain.models.view.AllTubesModel" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: sa6o6
  Date: 17-Feb-19
  Time: 8:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Tubes</title>
    <style>
        <%@include file="/styles/metube_style.css" %>
    </style>
</head>

<% List<AllTubesModel> allTubesModels = (List<AllTubesModel>)request.getAttribute("all_tubes");%>

<body>
<main>
    <h1>All Tubes</h1>
    <hr/>
    <h2>Check out tubes below.</h2>
    <hr/>

    <% if (allTubesModels.isEmpty()) { %>
    <p>No tubes, yet!</p>
    <a href="/create">Create some</a>
    <% } else {%>
    <ul>
        <% for (AllTubesModel currentTube : allTubesModels) {%>
        <li><a href="/details?name=<%=currentTube.getName()%>"><%= currentTube.getName()%></a></li>
        <br/>
        <% } %>
    </ul>
    <hr/>
    <% } %>
    <a href="/">Back to Home</a>
</main>
</body>
</html>
