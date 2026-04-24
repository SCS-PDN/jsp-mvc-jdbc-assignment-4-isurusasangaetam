<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Available Courses</title>
</head>
<body>

<div class="container">
    <div class="header">
        <h2>Welcome, ${studentName}!</h2>
        <a href="${pageContext.request.contextPath}/login">Logout</a>
    </div>

    <h3>Available Courses</h3>

    <c:if test="${not empty error}">
        <div>${error}</div>
    </c:if>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Course Name</th>
                <th>Instructor</th>
                <th>Credits</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="course" items="${courses}">
                <tr>
                    <td>${course.course_id}</td>
                    <td>${course.name}</td>
                    <td>${course.instructor}</td>
                    <td>${course.credits}</td>
                    <td>
                        <form action="${pageContext.request.contextPath}/register/${course.course_id}" method="post" style="margin: 0;">
                            <button type="submit">Register</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>