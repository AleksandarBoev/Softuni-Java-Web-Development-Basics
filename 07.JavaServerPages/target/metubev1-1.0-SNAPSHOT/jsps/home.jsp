<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JSP Home</title>
    <style>
        <%@include file="/styles/styles.css" %>
    </style>
</head>
<body>
<h1>Hello, this page has been <br/>generated via JSP (Java Server Pages)!</h1>
<hr/>
<% List<Integer> numbers = new ArrayList<>();%>

<% numbers.add(1);%>
<% numbers.add(2);%>
<% numbers.add(3);%>
<% numbers.add(4);%>
<% numbers.add(5);%>
<% numbers.add(6);%>
<% numbers.add(7);%>

<ul>
    <% for (Integer number : numbers) { %>
    <% if (number % 2 != 0) {%>
    <li><%= number %>
    </li>
    <% } else {%>
    <li><%= "Even number "%></li>
    <% } %>
    <% } %>
</ul>

</body>
</html>
