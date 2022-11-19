
<script>

//   document.addEventListener('DOMContentLoaded', function() {
//     var calendarEl = document.getElementById('calendar');

//     var calendar = new FullCalendar.Calendar(calendarEl, {
//       displayEventTime: false,
//       initialDate: '2021-02-01',
//       headerToolbar: {
//         left: 'prev,next today',
//         center: 'title',
//         right: 'dayGridMonth,listYear'
//       },
//       events: {
//         url: 'ics/feed.ics',
//         format: 'ics',
//         failure: function() {
//           document.getElementById('script-warning').style.display = 'block';
//         }
//       },
//       loading: function(bool) {
//         document.getElementById('loading').style.display =
//           bool ? 'block' : 'none';
//       }
//     });

//     calendar.render();
//   });


	var openPop = {
					
		small : function(url, popNm){
			var option="width = 100, height = 300, top = 100, left = 200, location = no";
			window.open(url, popNm, option);
		},
		
		medium : function(url, popNm){
			var option="width = 500, height = 700, top = 100, left = 200, location = no";
			window.open(url, popNm, option);
		},
		
		large : function(url, popNm){
			var option="width = 700, height = 1000, top = 100, left = 200, location = no";
			window.open(url, popNm, option);
		}
	};

</script>