var vm = new Vue({
    el: "#box",
    name: "answerFinish",
    data() {
        return {
            //学生答题分数
            score: 0,
            // 试卷总分
            totalScore: 0,
        };
    },
    methods: {
        getQueryVariable(variable)
        {
            let query = window.location.search.substring(1);
            let vars = query.split("&");
            for (let i=0;i<vars.length;i++) {
                let pair = vars[i].split("=");
                if(pair[0] == variable){return pair[1];}
            }
            return false;
        },
        goBackHome(){
            window.location.href = "/SoftwareEngineering/pages/student/sIndex.html"
        }
    },
    created() {
        this.score = this.getQueryVariable("score")
        this.totalScore = this.getQueryVariable("totalScore")
    },
})