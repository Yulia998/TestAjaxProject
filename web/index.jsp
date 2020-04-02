<%@ page import="com.mykhailenko.entities.Student" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mykhailenko.dao.OracleDaoConnection" %><%--
  Created by IntelliJ IDEA.
  User: New User
  Date: 23.03.2020
  Time: 19:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>

    </head>
    <body>
        <%
            String name = request.getParameter("val");
            List<Student> students = OracleDaoConnection.getInstance().selectStudentsStart(name);
            if(name == null || name.trim().equals("")) { %>
                <p>Please, enter your name</p>
        <%  } else if (students.size() != 0) { %>
                <table border="1" cellpadding="2" width="100%">
                    <tr>
                        <th>ID</th>
                        <th>NAME</th>
                        <th>SCHOLARSHIP</th>
                    </tr>
                <% for(Student student : students) { %>
                    <tr>
                        <th><%= student.getId()%></th>
                        <th><%= student.getName()%></th>
                        <th><%= student.getScholarship()%></th>
                    </tr>
                    <% } %>
                </table>
        <% } else { %>
                <p>No record found!</p>
        <% } %>
    </body>
</html>
