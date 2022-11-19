<%--
  Created by IntelliJ IDEA.
  User: JungHyungJin
  Date: 2022-11-05
  Time: 오후 1:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <button id="excelDownload">엑셀 다운로드</button>
        <button id="excelDownload2">엑셀2 다운로드</button>
    </body>

    <script>
        window.onload = function () {
            fileDownEvent("#excelDownload", '/excel/download');
            fileDownEvent("#excelDownload2", '/excel/download2');
        }

        function fileDownEvent(elementId, url) {
            let btn = document.querySelector(elementId);

            btn.addEventListener('click', () => {
                let a = document.createElement("a");
                a.href = url;
                a.click();
            });
        }
        

    </script>

</html>
