<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="utills.Dbconnect" %>
<%@ page import="Domain.User" %>
<%@ page import="Dao.UserDao" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<center>


    <body>
    <h1>Show data</h1>
    <table border="solid 2px" style="border-collapse: collapse;width: 50%;text-align: center;">
        <thead style="font-size: large;background-color: azure">
        <th style="padding: 10px">
            id
        </th>
        <th>
            name
        </th>
        <th>
            password
        </th>
        <th>
            email
        </th>
        <th>
            edit
        </th>
        </thead>
        <tbody>
        <%
            UserDao us = new UserDao();
            List<User> data = us.getAllUser();

        %>
        <% for (User rs:data) {
           int id = rs.getId();%>
        <tr >
            <td><%=id%>
            </td>
            <td><%=rs.getName()%>
            </td>
            <td><%=rs.getPassword()%>
            </td>
            <td><%=rs.getEmail()%>
            </td>
            <td>
                <a href='EditServlet?id=<%=id%>'>edit</a>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>

    <h1>Add Data</h1>
    <form  action="" method="post">
        <table>
            <tr>
                <td>Name:</td>
                <td><input type="text" name="name"/></td>

            </tr>
            <tr>
                <td>Password:</td>
                <td><input type="password" name="password"/></td>
            </tr>
            <tr>
                <td>Email:</td>
                <td><input type="email" name="email"/></td>
            </tr>

            <tr>
                <td colspan="5" ><input type="submit" value="Save"/></td>
            </tr>
        </table>
    </form>

    </body>
</center>
</html>