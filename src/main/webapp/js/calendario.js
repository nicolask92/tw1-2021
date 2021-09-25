
document.addEventListener('DOMContentLoaded', function() {
    let calendarEl = document.getElementById('calendar');

    let calendar = new FullCalendar.Calendar(calendarEl, {
        plugins: [ 'dayGrid' ],
        locale: 'es',
        events: [
            {
                title:'BCG',
                start:'2020-06-26'
            },
            {
                title:'Meningococo',
                start:'2020-07-07'
            },
            {
                title:'Hepatitis A',
                start:'2020-07-20'
            },
            {
                title:'VPH',
                start:'2020-08-13',
                end:'2020-08-15'
            },
            {
                title:'Varicela',
                start:'2020-09-03'

            }


        ]
    });

    calendar.render();
});