var vm = new Vue({
    el: "#box",
    name: "answerFinish",
    data() {
        return {
            //学生答题分数
            score: 0,
            // 试卷总分
            totalScore: 0,
            studentNumber:"",
            classID:"",
            courseID:"",
            practiceName:""
        };
    },
    methods: {
        getQueryVariable(variable) {
            let query = window.location.search.substring(1);
            let vars = query.split("&");
            for (let i = 0; i < vars.length; i++) {
                let pair = vars[i].split("=");
                if (pair[0] == variable) {
                    return pair[1];
                }
            }
            return false;
        },
        goBackHome() {
            window.location.href = "/SoftwareEngineering/pages/student/sIndex.html"
        }
    },
    created() {
        this.score = this.getQueryVariable("score")
        this.totalScore = this.getQueryVariable("totalScore")
        this.studentNumber = this.getQueryVariable("studentNumber")
        this.classID = this.getQueryVariable("classID")
        this.courseID = this.getQueryVariable("courseID")
        this.practiceName=this.getQueryVariable("practiceName")
        let now = new Date()
        axios({
            url: '/SoftwareEngineering/practiceClient?action=getPracticeInfo',
            method: "Post",
            data: {
                score: this.score,
                finishTime: now.getFullYear() + '-' + ((now.getMonth() + 1) < 10 ? '0' + (now.getMonth() + 1) : (now.getMonth() + 1)) + '-' + (now.getDate() < 10 ? '0' + now.getDate() : now.getDate()) + ' ' + (now.getHours()<10?'0'+  now.getHours(): now.getHours())+ ':' + (now.getMinutes()<10?'0'+now.getMinutes():now.getMinutes()) + ':' + (now.getSeconds()<10?'0'+now.getSeconds():now.getSeconds()),
                studentNumber: this.studentNumber,
                courseID: this.courseID,
                classID: this.classID,
                practiceName: this.practiceName
            }
        })
    },
})