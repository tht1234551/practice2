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

<form id="modelValues" style="display: none;">
    <input name="id" value="${id}">
    <input name="port" value="${port}">
    <input name="ip" value="${ip}">
</form>

<table border="1">
    <tr>
        <td width="100">
            <table>
                <tr>
                    <td>전체접속자</td>
                </tr>
                <tr>
                    <td>
                        <select id="users" size="10" style="width: 100%;">

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
                        <select name="room" id="rooms" size="10" style="width: 100%;">

                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <button id="joinRoom">채팅방참여</button>
                        <button id="exitRoom" style="display: none">방 나가기</button>
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
                        <textarea id="msg" style="height: 436px; width: 507px;" readonly></textarea>
                    </td>
                </tr>
                <tr>
                    <td>
                        <form style="display: flex; justify-content: center; align-items: center;">
                            <input id="sendMsg" type="text" style="width: 80%;" multiple>
                            <button id="sendChatting" style="height: 100%; height: 36px;">전송</button>
                            <button id="sendFile" style="height: 100%; height: 36px;">파일</button>
                        </form>
                    </td>
                </tr>

            </table>
        </td>
    </tr>
</table>

<script src="${pageContext.request.contextPath}/js/views/chat/list.js"></script>

</body>
</html>
