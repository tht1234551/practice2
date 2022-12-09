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
chat

<table border="1">
    <tr>
        <td width="100">Server Ip</td>
        <td width="200">
            <input name="ip" type="text" value="localhost">
        </td>
    </tr>
    <tr>
        <td>Server Port</td>
        <td>
            <input name="port" type="text" value="1234">
        </td>
    </tr>
    <tr>
        <td>ID</td>
        <td>
            <input name="id" type="text" value="web">
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <button id="connect" style="width: 100%">접 속</button>
        </td>
    </tr>
</table>
</body>
<script>
    document.querySelector("table #connect").addEventListener("click", e => {
        const a = document.createElement('a')

        const inputs = [...document.querySelectorAll("table input")];
        const queryString = "?" + inputs.map((input) => input.name + "=" +input.value).join("&");
        a.href = "/chat/connect" + queryString
        a.click();
    });
</script>
</html>
