<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>   --%>
<%-- <%@ include file="/WEB-INF/views/header.jsp"%> --%>
<!-- <title>displayInsert</title> -->
<!-- </head> -->
<!-- <body> -->
<!-- <h1>success!!</h1> -->

<!-- <form> -->
<!-- <div> -->
<!-- <label>전시그룹</label> -->
<!-- <select class="form-control"> -->
<!--   <option>1</option> -->
<!--   <option>2</option> -->
<!--   <option>3</option> -->
<!--   <option>4</option> -->
<!--   <option>5</option> -->
<!-- </select> -->
<!-- </div> -->
<!--   <div class="form-group"> -->
<!--     <label for="displayNM">전시명</label> -->
<!--     <input type="text" class="form-control" id="displayNM"> -->
<!--   </div> -->
<!--   <div class="form-group"> -->
<!--     <label for="exampleInputFile">파일 업로드</label> -->
<!--     <input type="file" id="exampleInputFile"> -->
<!--   </div> -->
<!--   <div class="checkbox"> -->
<!--     <label> -->
<!--       <input type="checkbox"> 전시여부 -->
<!--     </label> -->
<!--   </div> -->
<!--   <button type="submit" class="btn btn-default">제출</button> -->
<!-- </form> -->
<!-- </body> -->
<!-- </html> -->



<!DOCTYPE html>
<html>
<head>
<meta charset='utf-8' />
<link href='../fullcalendar/lib/main.css' rel='stylesheet' />
<script src='https://github.com/mozilla-comm/ical.js/releases/download/v1.4.0/ical.js'></script>
<script src='../fullcalendar/lib/main.js'></script>
<script src='../packages/icalendar/main.global.js'></script>
<script>

  document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
      displayEventTime: false,
      initialDate: '2021-02-01',
      headerToolbar: {
        left: 'prev,next today',
        center: 'title',
        right: 'dayGridMonth,listYear'
      },
      events: {
        url: 'ics/feed.ics',
        format: 'ics',
        failure: function() {
          document.getElementById('script-warning').style.display = 'block';
        }
      },
      loading: function(bool) {
        document.getElementById('loading').style.display =
          bool ? 'block' : 'none';
      }
    });

    calendar.render();
  });

</script>
<style>

  body {
    margin: 0;
    padding: 0;
    font-family: Arial, Helvetica Neue, Helvetica, sans-serif;
    font-size: 14px;
  }

  #script-warning {
    display: none;
    background: #eee;
    border-bottom: 1px solid #ddd;
    padding: 0 10px;
    line-height: 40px;
    text-align: center;
    font-weight: bold;
    font-size: 12px;
    color: red;
  }

  #loading {
    display: none;
    position: absolute;
    top: 10px;
    right: 10px;
  }

  #calendar {
    max-width: 1100px;
    margin: 40px auto;
    padding: 0 10px;
  }

</style>
</head>
<body>

  <div id='script-warning'>
    <code>ics/feed.ics</code> must be servable
  </div>

  <div id='loading'>loading...</div>

  <div id='calendar'></div>

</body>
</html>
