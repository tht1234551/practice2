<%--
  Created by IntelliJ IDEA.
  User: JungHyungJin
  Date: 2022-11-19
  Time: 오후 3:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
list
${list}
<button id="disconnect">disconnect</button>
</body>
<script>
    document.querySelector('#disconnect').addEventListener('click', function (e) {
        const queryString = '?id=${id}'
        location.href = '/chat/disconnect' + queryString;
    })
</script>
</html>
