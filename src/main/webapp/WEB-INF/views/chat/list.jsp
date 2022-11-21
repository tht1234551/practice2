<%--
  Created by IntelliJ IDEA.
  User: JungHyungJin
  Date: 2022-11-19
  Time: 오후 3:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<table border="1">
    <tr>
        <td width="100">
            <table>
                <tr>
                    <td>전체접속자</td>
                </tr>
                <tr>
                    <td>
                        <select name="user" id="userID" size="10" style="width: 100%;">
                            <c:forEach items="${users}" var="user">
                                <option>${user}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <button id="sendMessage">쪽지보내기</button>
                    </td>
                </tr>
                <tr>
                    <td>채팅방목록</td>
                </tr>
                <tr>
                    <td>
                        <select name="user" id="room" size="10" style="width: 100%;">
                            <c:forEach items="${rooms}" var="room">
                                <option>${room}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <button id="joinRoom">채팅방참여</button>
                    </td>
                </tr>
                <tr>
                    <td>
                        <button id="makeRoom">방만들기</button>
                    </td>
                </tr>
            </table>

        </td>
        <td width="300">

            <table>
                <tr>
                    <td>
                        <textarea style="height: 436px; width: 507px;"></textarea>
                    </td>
                </tr>
                <tr>
                    <td style="display: flex; justify-content: center; align-items: center;">
                        <textarea style="width: 80%;"></textarea>
                        <button style="height: 100%; height: 36px;">전송</button>
                        <button style="height: 100%; height: 36px;">파일</button>
                    </td>
                </tr>

            </table>
        </td>
    </tr>
</table>

<button id="disconnect">disconnect</button>

</body>
<script>
    document.querySelector('#disconnect').addEventListener('click', function (e) {
        const queryString = '?id=${id}'
        location.href = '/chat/disconnect' + queryString;
    })
</script>
</html>
