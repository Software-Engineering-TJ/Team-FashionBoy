var vm = new Vue({
    el: "#box",
    name: "answer",
    data() {
        return {
            problem: {
                choiceProblem: [],
            },
            // 计时器
            timer: "",
            //学生答案
            answer: [],
            // 截至时间
            endTime: "",
            // 考试编号
            test_id: "",
            // 总题目数
            allLength: 0,
            // 学生的选择题答案
            choiceAnswer: [],
            // 学生的填空题答案
            fillAnswer: [],
            // 学生的判断答案
            judgeAnswer: [],
            // 选择题数
            choiceLength: 0,
            // 填空题数
            fillLength: 0,
            // 判断题数
            judgeLength: 0,
            // 是否被标记
            tag: [],
            // 是否答题
            written: [],
            // 学生完成的题目数量
            finished: 0,
            // 选择题列表
            choiceList: [],
            //填空题列表
            fillList: [],
            //判断题列表
            judgeList: [],
            //学生答题分数
            score: 0,
            // 试卷总分
            totalScore: 0,
            // 是否完成
            isFinished: false,
            // 小时
            h: 0,
            // 分钟
            m: 0,
            //秒
            s: 0,
        };
    },
    methods: {
        //返回主界面
        goBack() {
            this.$router.push("home")
        },
        setWritten(val, index) {
            console.log(this.choiceAnswer);
            console.log(this.fillAnswer);
            console.log(this.judgeAnswer);
            let fillIndex = index - this.choiceLength;
            if (val !== null && val !== "") {
                if (this.finished < this.allLength && !this.written[index]) {
                    if (
                        index > this.choiceLength - 1 &&
                        index < this.choiceLength + this.fillLength - 1
                    ) {
                        for (let i = 0; i < this.fillList[fillIndex].fillSpaceNumber; i++) {
                            if (this.fillAnswer[fillIndex][i] === "") {
                                return;
                            }
                        }
                    }
                    this.finished++;
                }
                this.written[index] = true;
                this.$forceUpdate();
            } else if (
                this.written[index] &&
                index > this.choiceLength - 1 &&
                index <= this.choiceLength + this.fillLength - 1
            ) {
                for (let i = 0; i < this.fillList[fillIndex].fillSpaceNumber; i++) {
                    if (this.fillAnswer[fillIndex][i] === "") {
                        this.finished--;
                        this.written[index] = false;
                        this.$forceUpdate();
                        return;
                    }
                }
            }
        },
        setSign(index) {
            this.tag[index] = this.tag[index] !== true;
            this.$forceUpdate();
        },
        // 计算倒计时，时间到后提交试卷
        countTime() {
            //获取当前时间
            let date = new Date();
            let now = date.getTime();
            //设置截止时间
            let endDate = new Date(
                this.$route.query.test_date + " " + this.endTime + ":00"
            );
            let end = endDate.getTime();
            //时间差
            let leftTime = end - now;
            //定义变量 d,h,m,s保存倒计时的时间
            if (leftTime >= 0) {
                this.h = Math.floor((leftTime / 1000 / 60 / 60) % 24);
                this.m = Math.floor((leftTime / 1000 / 60) % 60);
                this.s = Math.floor((leftTime / 1000) % 60);
                //递归每秒调用countTime方法，显示动态时间效果
                this.timer = setTimeout(this.countTime, 1000);
            } else {
                this.submitTestPaper();
            }
        },
        //提交试卷
        submitTestPaper() {
            if (this.h !== 0 || this.m !== 0 || this.s !== 0) {
                this.$confirm("考试结束时间未到,是否提前交卷", "友情提示", {
                    confirmButtonText: "立即交卷",
                    cancelButtonText: "再检查一下",
                    type: "warning",
                }).then(() => {
                    //  计算选择题总分
                    clearTimeout(this.timer);
                    for (let i = 0; i < this.choiceLength; i++) {
                        console.log("计算选择题总分");
                        this.totalScore = this.totalScore + this.choiceList[i].choiceScore;
                        let choiceRightAnswer;
                        switch (this.choiceList[i].choiceAnswer) {
                            case "A":
                                choiceRightAnswer = 0;
                                break;
                            case "B":
                                choiceRightAnswer = 1;
                                break;
                            case "C":
                                choiceRightAnswer = 2;
                                break;
                            case "D":
                                choiceRightAnswer = 3;
                                break;
                        }
                        if (this.choiceAnswer[i] === choiceRightAnswer)
                            this.score = this.score + this.choiceList[i].choiceScore;
                    }
                    //  计算填空题总分
                    for (let i = 0; i < this.fillLength; i++) {
                        console.log("计算填空题总分");
                        this.totalScore = this.totalScore + this.fillList[i].fillScore;
                        for (let j = 0; j < this.fillList[i].fillSpaceNumber; j++) {
                            if (this.fillAnswer[i][j] === this.fillList[i].fillAnswer[j]) {
                                this.score =
                                    this.score +
                                    this.fillList[i].fillScore / this.fillList[i].fillSpaceNumber;
                            }
                        }
                    }
                    //  计算判断题总分
                    for (let i = 0; i < this.judgeList.length; i++) {
                        this.totalScore =
                            this.totalScore + this.judgeList[i].judgementScore;
                        let rightAnswer =
                            this.judgeList[i].judgementAnswer === "对" ? 0 : 1;
                        if (this.judgeAnswer[i] === rightAnswer) {
                            this.score = this.score + this.judgeList[i].judgementScore;
                        }
                    }
                    this.savePaper();
                });
            } else {
                for (let i = 0; i < this.choiceLength; i++) {
                    console.log("计算选择题总分");
                    this.totalScore = this.totalScore + this.choiceList[i].choiceScore;
                    let choiceRightAnswer;
                    switch (this.choiceList[i].choiceAnswer) {
                        case "A":
                            choiceRightAnswer = 0;
                            break;
                        case "B":
                            choiceRightAnswer = 1;
                            break;
                        case "C":
                            choiceRightAnswer = 2;
                            break;
                        case "D":
                            choiceRightAnswer = 3;
                            break;
                    }
                    if (this.choiceAnswer[i] === choiceRightAnswer)
                        this.score = this.score + this.choiceList[i].choiceScore;
                }
                //  计算填空题总分
                for (let i = 0; i < this.fillLength; i++) {
                    console.log("计算填空题总分");
                    this.totalScore = this.totalScore + this.fillList[i].fillScore;
                    for (let j = 0; j < this.fillList[i].fillSpaceNumber; j++) {
                        if (this.fillAnswer[i][j] === this.fillList[i].fillAnswer[j]) {
                            this.score =
                                this.score +
                                this.fillList[i].fillScore / this.fillList[i].fillSpaceNumber;
                        }
                    }
                }
                //  计算判断题总分
                for (let i = 0; i < this.judgeList.length; i++) {
                    this.totalScore = this.totalScore + this.judgeList[i].judgementScore;
                    let rightAnswer = this.judgeList[i].judgementAnswer === "对" ? 0 : 1;
                    if (this.judgeAnswer[i] === rightAnswer) {
                        this.score = this.score + this.judgeList[i].judgementScore;
                    }
                }
                this.savePaper();
            }
        },
        savePaper() {
            let submitPaper = [];
            for (let i = 0; i < this.allLength; i++) {
                submitPaper.push({});
            }
            for (let i = 0; i < this.choiceList.length; i++) {
                submitPaper[i]["question_type"] = "选择题";
                submitPaper[i]["question_id"] = this.choiceList[i].choiceId;
                let saveAnswer = "";
                switch (this.choiceAnswer[i]) {
                    case 0:
                        saveAnswer = "A";
                        break;
                    case 1:
                        saveAnswer = "B";
                        break;
                    case 2:
                        saveAnswer = "C";
                        break;
                    case 3:
                        saveAnswer = "D";
                        break;
                }
                submitPaper[i]["my_answer"] = saveAnswer;
                submitPaper[i]["my_question_score"] =
                    (this.choiceList[i].choiceAnswer === saveAnswer
                        ? this.choiceList[i].choiceScore
                        : 0);
            }
            for (let i = 0; i < this.fillList.length; i++) {
                submitPaper[i + this.choiceLength]["question_type"] = "填空题";
                submitPaper[i + this.choiceLength]["question_id"] =
                    this.fillList[i].fillId;
                let saveFillAnswer = "";
                for (let j = 0; j < this.fillAnswer[i].length; j++) {
                    saveFillAnswer = saveFillAnswer + this.fillAnswer[i][j];
                    if (j < this.fillAnswer[i].length - 1) {
                        saveFillAnswer = saveFillAnswer + "|";
                    }
                }
                submitPaper[i + this.choiceLength]["my_answer"] = saveFillAnswer;
                submitPaper[i + this.choiceLength]["my_question_score"] =
                    (this.fillList[i].fillAnswer === saveFillAnswer
                        ? this.fillList[i].fillScore
                        : 0);
            }
            for (let i = 0; i < this.judgeList.length; i++) {
                submitPaper[i + this.choiceLength + this.fillLength]["question_type"] =
                    "判断题";
                submitPaper[i + this.choiceLength + this.fillLength]["question_id"] =
                    this.judgeList[i].judgementId;
                submitPaper[i + this.choiceLength + this.fillLength]["my_answer"] =
                    this.judgeAnswer[i] === 0 ? "正确" : "错误";
                submitPaper[i + this.choiceLength + this.fillLength][
                    "my_question_score"
                    ] =
                    (this.judgeList[i].judgementAnswer === (this.judgeAnswer[i] === 0 ? "对" : "错")
                        ? this.judgeList[i].judgementScore
                        : 0);
            }
            this.$axios({
                url: "/savePaper",
                method: "post",
                data: {
                    test_id: this.test_id,
                    stu_id: this.$cookies.get("stu_id"),
                    my_test_score: this.score,
                    answerList: submitPaper
                },
            }).then(resp => {
                this.$router.push({
                    path: "/answerFinish",
                    query: {
                        totalScore: this.totalScore.toString(),
                        score: this.score.toString(),
                        test_id: this.test_id,
                        stu_id: this.$cookies.get("stu_id"),
                    },
                });
            });
        },
    },
    mounted() {
        this.test_id = this.$route.query.test_id;
        this.endTime = this.$route.query.test_endtime;
        if (this.test_id !== undefined) {
            this.$axios({
                url: "/getProblem",
                method: "post",
                data: {
                    test_id: parseInt(this.test_id),
                },
            }).then((resp) => {
                if (resp.data.data !== undefined) {
                    let data = resp.data.data;
                    this.problem.choiceProblem = data.choiceQuestionList;
                    this.problem.fillProblem = data.fillBlankQuestionList;
                    this.problem.judgeProblem = data.judgementQuestionList;
                    for (let i = 0; i < this.problem.choiceProblem.length; i++) {
                        this.problem.choiceProblem[i]["answer"] =
                            this.problem.choiceProblem[i].choiceOption.split("|");
                    }
                    this.choiceList = this.problem.choiceProblem;
                    this.fillList = this.problem.fillProblem;
                    this.judgeList = this.problem.judgeProblem;
                    this.choiceLength = this.problem.choiceProblem.length;
                    this.fillLength = this.problem.fillProblem.length;
                    this.judgeLength = this.problem.judgeProblem.length;
                    this.allLength = this.choiceLength + this.fillLength + this.judgeLength;
                    for (let i = 0; i < this.fillLength; i++) {
                        let child = [];
                        for (let j = 0; j < this.fillList[i].fillSpaceNumber; j++) {
                            child.push("");
                        }
                        this.fillAnswer.push(child);
                    }
                    for (let i = 0; i < this.allLength; i++) {
                        this.written[i] = false;
                    }
                }
            });
        } else {
            this.$axios({
                url: "/getProblem2",
                method: "post",
                data: {
                    paper_id: parseInt(this.$route.query.paper_id),
                },
            }).then((resp) => {
                if (resp.data.data !== undefined) {
                    let data = resp.data.data;
                    this.problem.choiceProblem = data.choiceQuestionList;
                    this.problem.fillProblem = data.fillBlankQuestionList;
                    this.problem.judgeProblem = data.judgementQuestionList;
                    for (let i = 0; i < this.problem.choiceProblem.length; i++) {
                        this.problem.choiceProblem[i]["answer"] =
                            this.problem.choiceProblem[i].choiceOption.split("|");
                    }
                    this.choiceList = this.problem.choiceProblem;
                    this.fillList = this.problem.fillProblem;
                    this.judgeList = this.problem.judgeProblem;
                    this.choiceLength = this.problem.choiceProblem.length;
                    this.fillLength = this.problem.fillProblem.length;
                    this.judgeLength = this.problem.judgeProblem.length;
                    this.allLength = this.choiceLength + this.fillLength + this.judgeLength;
                    for (let i = 0; i < this.fillLength; i++) {
                        let child = [];
                        for (let j = 0; j < this.fillList[i].fillSpaceNumber; j++) {
                            child.push("");
                        }
                        this.fillAnswer.push(child);
                    }
                    for (let i = 0; i < this.allLength; i++) {
                        this.written[i] = false;
                    }
                }
            });
        }

        if (this.$route.query.isFinished === undefined) {
            this.countTime();
        } else {
            this.isFinished = true;
            if (this.$route.query.test_id !== undefined) {
                this.$axios({
                    url: "/viewFinishedPaper",
                    method: "post",
                    data: {
                        stu_id: Number.parseInt(this.$route.query.stu_id),
                        test_id: Number.parseInt(this.$route.query.test_id)
                    }
                }).then(resp => {
                    let finishedAnswer = resp.data.data;
                    let i = 0;
                    let j = 0;
                    while (finishedAnswer[i].questionType === "选择题") {
                        this.choiceAnswer[j] = finishedAnswer[i].myAnswer;
                        i++;
                        j++;
                    }
                    ;
                    j = 0;
                    while (finishedAnswer[i].questionType === "填空题") {
                        this.fillAnswer[j] = finishedAnswer[i].myAnswer;
                        i++;
                        j++;
                    }
                    ;
                    j = 0;
                    while (i < finishedAnswer.length && finishedAnswer[i].questionType === "判断题") {
                        this.judgeAnswer[j] = finishedAnswer[i].myAnswer;
                        i++;
                        j++;
                    }
                });
            } else {
                this.$axios({
                    url: "/viewFinishedPaper2",
                    method: "post",
                    data: {
                        stu_id: Number.parseInt(this.$cookies.get("stu_id")),
                        paper_id: Number.parseInt(this.$route.query.paper_id)
                    }
                }).then(resp => {
                    console.log("2:" + resp.data.data)
                    let finishedAnswer = resp.data.data;
                    let i = 0;
                    let j = 0;
                    console.log(finishedAnswer)//正常
                    while (finishedAnswer[i].questionType === "选择题") {
                        this.choiceAnswer[j] = finishedAnswer[i].myAnswer;
                        i++;
                        j++;
                    }
                    ;
                    j = 0;
                    while (finishedAnswer[i].questionType === "填空题") {
                        this.fillAnswer[j] = finishedAnswer[i].myAnswer;
                        i++;
                        j++;
                    }
                    ;
                    j = 0;
                    while (i < finishedAnswer.length && finishedAnswer[i].questionType === "判断题") {
                        this.judgeAnswer[j] = finishedAnswer[i].myAnswer;
                        i++;
                        j++;
                    }
                });
            }
        }
    },
})