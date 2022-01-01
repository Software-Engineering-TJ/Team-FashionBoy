var Calender = Vue.extend({
    mounted() {
        let date = new Date()
        let today = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
        let calendarEl = document.getElementById('calendar');
        let calendar = new FullCalendar.Calendar(calendarEl, {
            initialView: 'dayGridMonth',
            // initialDate: today,
            headerToolbar: {
                left: 'prev,next today',
                center: 'title',
                right: 'dayGridMonth,timeGridWeek,timeGridDay'
            },
            events: [
                {
                    title: '批准教师开课申请',
                    start: '2021-12-06T23:59:00'
                },
                {
                    title: '导入学生信息',
                    start: '2021-12-07',
                    end: '2021-12-15'
                },
                {
                    title: '审核用户修改个人信息申请',
                    start: '2021-12-16',
                    end: '2021-12-21'
                },
                {
                    groupId: '999',
                    title: '发布VLAN实验学习任务',
                    start: '2021-12-16T16:00:00'
                },
                {
                    groupId: '999',
                    title: 'Repeating Event',
                    start: '2021-11-16T16:00:00'
                },
                {
                    title: 'Conference',
                    start: '2021-11-11',
                    end: '2021-11-13'
                },
                {
                    title: 'Meeting',
                    start: '2021-11-12T10:30:00',
                    end: '2021-11-12T12:30:00'
                },
                {
                    title: 'Lunch',
                    start: '2021-11-12T12:00:00'
                },
                {
                    title: 'Meeting',
                    start: '2021-11-12T14:30:00'
                },
                {
                    title: 'Birthday Party',
                    start: '2021-11-13T07:00:00'
                },
                {
                    title: 'Click for Google',
                    url: 'http://google.com/',
                    start: '2021-11-25'
                }
            ]
        });
        calendar.render();
    },
    template: `
        <el-card>
            <div id='calendar'></div>
        </el-card>
        `
});