<%@ page import="metube_app.domain.models.service.TubeServiceModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Details</title>
    <style>
        <%@include file="/styles/metube_style.css" %>
    </style>
</head>
<body>

<% TubeServiceModel tubeServiceModel = (TubeServiceModel)request.getAttribute("tube_service_model");%>

<main>
    <h1><%= tubeServiceModel.getName()%></h1>
    <hr/>
    <p><%= tubeServiceModel.getDescription()%></p>
    <hr/>
    <a href="<%= tubeServiceModel.getYoutubeLink()%>" class="half_screen">Link to Video.</a>
    <p style="display: inline-block"><%= tubeServiceModel.getUploader()%></p>
    <hr/>
    <a href="/">Back to Home</a>
</main>
</body>
</html>
