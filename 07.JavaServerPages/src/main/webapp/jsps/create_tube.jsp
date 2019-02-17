<%--
  Created by IntelliJ IDEA.
  User: sa6o6
  Date: 16-Feb-19
  Time: 4:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Create Tube</title>
    <style>
        <%@include file="/styles/metube_style.css" %>
    </style>
</head>
<body>

<main>
<h1>Create Tube!</h1>
<hr/>
<form action="/create" method="post">
    <label for="tube_title">
        Title<br/>
        <input type="text" name="title" id="tube_title">
    </label>

    <hr/>

    <label for="tube_description">
        Description<br/>
        <textarea rows="5" name="description" id="tube_description"></textarea>
    </label>

    <hr/>

    <label for="tube_link">
        YouTube Link<br/>
        <input type="text" name="link" id="tube_link">
    </label>

    <hr/>

    <label for="tube_uploader">
        Uploader<br/>
        <input type="text" name="uploader" id="tube_uploader">
    </label>

    <hr/>

    <button type="submit" class="blue_button">Create Tube</button>
</form>

<a href="/">Back to Home.</a>
</main>

</body>
</html>
