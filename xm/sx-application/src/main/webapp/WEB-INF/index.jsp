<%--
  Created by IntelliJ IDEA.
  User: hp
  Date: 2019/9/2
  Time: 15:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>

</head>
<body>
    <div id="app">
        <span>Hello World</span>
        {{key}}
        <div>
            ${user}
        </div>
        <div>
            <table border="1">
                <thead>
                <tr>
                    <th>id</th>
                    <th>content</th>
                    <th>other</th>
                    <th>icon</th>
                    <th>create_time</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="comment" items="${comments}" varStatus="index">
                    <tr>
                        <td>${comment.id}</td>
                        <td>${comment.content}</td>
                        <td>${comment.other}</td>
                        <td>${comment.icon}</td>
                        <td>${comment.create_time}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </div>
    </div>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
    $(() =>{
        $.ajax({
            type:"POST",
            url:"/test/json/db",
            data:{
                username:"admins"
            },
            dataType:"json"
            ,
            success:function(resp){
                console.log(resp)




















            },
            error:(err) =>{
                console.log(err)
            }
        })
    })
</script>
</body>
</html>
