var Calender = Vue.extend({
    mounted() {
        axios({
            url: '/SoftwareEngineering/userServlet?action=getSchedule',
            method: "Post",
        }).then(resp =>{
            this.schedule = resp.data
            this.attendanceDialogTableVisible = true;
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
                events: this.schedule
            });
            calendar.render();
        })
    },
    data() {
        return {
            schedule:[]
        };
    },
    template: `
        <el-card>
            <div id='calendar'></div>
        </el-card>
        `
});