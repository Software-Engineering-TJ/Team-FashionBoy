var Answer = Vue.extend({
    props: ['classId', 'courseId', 'practiceList'],
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
            nPercentage:0.0
        };
    },
    methods: {
        //返回主界面
        goBack() {
            this.$router.push("home")
        },
        setWritten(val, index) {
            this.finished++;
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
            let endDate = new Date();
            let min=endDate.getMinutes();
            endDate.setMinutes(min+10);
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
    computed: {
        myPercentage: function () {
            let oriPercentage = this.finished / (this.allLength) * 100;
            console.log(oriPercentage)
            this.nPercentage=oriPercentage
            return Number.parseFloat(oriPercentage.toString().substr(0, 5));
        }
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
                    this.allLength = this.choiceLength;
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
    template: `
        <div class="my-body">
    <div class="top-nav">
      <el-row
          style="
          position: fixed;
          top: 0px;
          background-color: white;
          overflow: hidden;
          width: 1520px;
          z-index: 20 !important;
        "
      >
        <el-col :span="2"><h2 style="margin: 18px 0 18px 0">LOGO</h2></el-col>
        <el-col :span="4" :offset="2"
        ><h2 style="margin: 18px 0 18px 0">对抗练习二</h2></el-col
        >
        <el-col :span="2" :offset="12">
          <div style="line-height: 60px">
            <el-button plain type="primary">退出对抗练习</el-button>
          </div>
        </el-col>
      </el-row>
    </div>
    <!--  左侧答题卡导航栏  -->
    <el-card class="left-nav">
      <div id="no-use">
        <el-card
            style="
            position: fixed;
            top: 610px;
            height: 60px;
            width: 240px;
            left: 80px;
          "
        >
          <div class="left">
            <ul class="l-top">
              <li>
                <a href="javascript:;"></a>
                <span>未答</span>
              </li>
              <li>
                <a href="javascript:;"></a>
                <span>已答</span>
              </li>
              <li>
                <a href="javascript:;"></a>
                <span>标记</span>
              </li>
            </ul>
          </div>
        </el-card>
      </div>
      <div class="nav-title">
        <div class="nav-title-div"></div>
        <span>答题卡</span>
      </div>

      <div class="l-bottom">
        <div class="item">
          <div class="my-item">
            <h4 style="margin: 18px 0 18px 0">单选题</h4>
            <ul>
              <li v-for="indexA in choiceLength" :key="indexA" class="tag-li">
                <a
                    :href="'#my-index' + (indexA - 1)"
                    :class="{ bg: written[indexA - 1] === true }"
                >
                  <span :class="{ mark: tag[indexA - 1] }"></span>
                  {{ indexA }}
                </a>
              </li>
            </ul>
          </div>
        </div>
        <div class="item">
          <div class="my-item">
            <h4 style="margin: 18px 0 18px 0">填空题</h4>
            <ul>
              <li v-for="indexA in fillLength" :key="indexA" class="tag-li">
                <a
                    :href="'#my-index' + (indexA - 1 + choiceLength)"
                    :class="{ bg: written[indexA - 1 + choiceLength] === true }"
                >
                  <span
                      :class="{ mark: tag[indexA - 1 + choiceLength] }"
                  ></span>
                  {{ indexA + choiceLength }}
                </a>
              </li>
            </ul>
          </div>
        </div>
        <div class="item">
          <div class="my-item">
            <h4 style="margin: 18px 0 18px 0">判断题</h4>
            <ul>
              <li v-for="indexA in judgeLength" :key="indexA" class="tag-li">
                <a
                    :href="'#my-index' + (indexA - 1 + fillLength + choiceLength)"
                    :class="{
                    bg:
                      written[indexA - 1 + fillLength + choiceLength] === true,
                  }"
                >
                  <span
                      :class="{
                      mark: tag[indexA - 1 + fillLength + choiceLength],
                    }"
                  ></span>
                  {{ indexA + fillLength + choiceLength }}
                </a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </el-card>

    <!-- 答题区域   -->
    <el-card shadow="never" class="main">
      <transition name="slider-fade">
        <div class="right">
          <!--   选择题   -->
          <div class="myChoice" style="font-weight: bolder">单选题</div>
          <div v-for="(item, indexA) in problem.choiceProblem" :key="indexA">
            <div class="my-content">
              <!--    标题    -->
              <p class="topic" :id="'my-index' + indexA">
                <span class="number">{{ indexA + 1 }}</span
                >{{ item.choiceQuestion }}
                <el-tooltip
                    class="item"
                    effect="dark"
                    content="标记"
                    placement="top"
                    style="float: right"
                >
                  <el-button
                      size="medium"
                      type="text"
                      icon="el-icon-s-flag"
                      @click="setSign(indexA)"
                  ></el-button>
                </el-tooltip>
              </p>

              <!--    选项    -->
              <div>
                <el-radio-group
                    :disabled="isFinished"
                    v-model="choiceAnswer[indexA]"
                    @change="setWritten(choiceAnswer[indexA], indexA)"
                >
                  <el-radio :label="0">{{ item.answer[0] }}</el-radio>
                  <el-radio :label="1">{{ item.answer[1] }}</el-radio>
                  <el-radio :label="2">{{ item.answer[2] }}</el-radio>
                  <el-radio :label="3">{{ item.answer[3] }}</el-radio>
                </el-radio-group>
              </div>

              <!--    正确答案及解析    -->
              <div class="analysis" v-if="isFinished">
                <ul>
                  <li style="text-align: left">
                    <el-tag type="info">您的答案：</el-tag>
                    <span class="right">{{ choiceAnswer[indexA] }}</span>
                  </li>
                  <li style="text-align: left">
                    <el-tag type="success">正确答案：</el-tag>
                    <span class="right">{{ item.choiceAnswer }}</span>
                  </li>
                  <li>
                    <el-tag>题目解析：</el-tag>
                  </li>
                  <li>
                    {{
                      item.choiceAnalysis == null
                          ? "暂无解析"
                          : item.choiceAnalysis
                    }}
                  </li>
                </ul>
              </div>
              <el-divider></el-divider>
            </div>
          </div>

          <!--   填空题   -->
          <div class="myChoice" style="font-weight: bolder">填空题</div>
          <div class="my-content">
            <!--    答题区域    -->
            <!--    标题    -->

            <div v-for="(item, indexB) in problem.fillProblem" :key="indexB">
              <p class="topic" :id="'my-index' + (indexB + choiceLength)">
                <span class="number">{{ indexB + choiceLength + 1 }}</span
                >{{ item.fillQuestion }}
                <el-tooltip
                    class="item"
                    effect="dark"
                    content="标记"
                    placement="top"
                    style="float: right"
                >
                  <el-button
                      size="medium"
                      type="text"
                      icon="el-icon-s-flag"
                      @click="setSign(indexB + choiceLength)"
                  ></el-button>
                </el-tooltip>
              </p>
              <div class="fill">
                <div
                    v-for="indexInner in item.fillSpaceNumber"
                    :key="indexInner"
                    style="margin-bottom: 20px"
                >
                  <span class="my-input">{{ indexInner }}</span>
                  <div style="display: inline-block">
                    <el-input
                        style="width: 200px"
                        :disabled="isFinished"
                        v-model="fillAnswer[indexB][indexInner - 1]"
                        placeholder="请填在此处"
                        clearable
                        @blur="
                        setWritten(
                          fillAnswer[indexB][indexInner - 1],
                          indexB + choiceLength
                        )
                      "
                    ></el-input>
                  </div>
                </div>
              </div>

              <!--    正确答案及解析    -->
              <div class="analysis" v-if="isFinished">
                <ul>
                  <li style="text-align: left">
                    <el-tag type="info">您的答案：</el-tag>
                    <span class="right">{{ fillAnswer[indexB] }}</span>
                  </li>
                  <li>
                    <el-tag type="success">正确答案：</el-tag>
                    <span class="right">{{ item.fillAnswer }}</span>
                  </li>
                  <li>
                    <el-tag>题目解析：</el-tag>
                  </li>
                  <li>
                    {{
                      item.fillAnalysis == null ? "暂无解析" : item.fillAnalysis
                    }}
                  </li>
                </ul>
              </div>
              <el-divider></el-divider>
            </div>
          </div>

          <!--   判断题   -->
          <div class="myChoice" style="font-weight: bolder">判断题</div>
          <div class="my-content">
            <!--    标题    -->

            <div v-for="(item, indexC) in problem.judgeProblem" :key="indexC">
              <p
                  class="topic"
                  :id="'my-index' + (indexC + choiceLength + fillLength)"
              >
                <span class="number">{{
                    indexC + choiceLength + fillLength + 1
                  }}</span
                >{{ item.judgementQuestion }}
                <el-tooltip
                    class="item"
                    effect="dark"
                    content="标记"
                    placement="top"
                    style="float: right"
                >
                  <el-button
                      size="medium"
                      type="text"
                      icon="el-icon-s-flag"
                      @click="setSign(indexC + choiceLength + fillLength)"
                  ></el-button>
                </el-tooltip>
              </p>

              <!--    选项    -->
              <div class="judge">
                <el-radio-group
                    :disabled="isFinished"
                    v-model="judgeAnswer[indexC]"
                    @change="
                    setWritten(
                      judgeAnswer[indexC],
                      indexC + choiceLength + fillLength
                    )
                  "
                >
                  <el-radio :label="0">正确</el-radio>
                  <el-radio :label="1">错误</el-radio>
                </el-radio-group>
              </div>

              <!--    正确答案及解析    -->
              <div class="analysis" v-if="isFinished">
                <ul>
                  <li style="text-align: left">
                    <el-tag type="info">您的答案：</el-tag>
                    <span class="right">{{
                        judgeAnswer[indexC] ==="正确"?"√":"X"
                      }}</span>
                  </li>
                  <li>
                    <el-tag type="success">正确答案：</el-tag>
                    <span class="right">{{
                        item.judgementAnswer === 0 ? "X" : "√"
                      }}</span>
                  </li>
                  <li>
                    <el-tag>题目解析：</el-tag>
                  </li>
                  <li>
                    {{
                      item.judgementAnalysis == null
                          ? "暂无解析"
                          : item.judgementAnalysis
                    }}
                  </li>
                </ul>
              </div>
              <el-divider></el-divider>
            </div>
          </div>
        </div>
      </transition>
    </el-card>

    <!--  右侧提示区  -->
    <el-card class="right-nav">
      <el-row>
        <div>剩余时间</div>
        <div
            style="
            border-bottom: 1px rgb(222, 222, 222) solid;
            margin-bottom: 5px;
            padding-bottom: 15px;
          "
        >
          <div class="my-timer">{{ h + ":" + m + ":" + s }}</div>
        </div>
      </el-row>
      <el-row>
        <div>当前进度</div>
        <div
            style="
            border-bottom: 1px rgb(222, 222, 222) solid;
            margin-bottom: 5px;
            padding-bottom: 15px;
          "
        >
          <div class="my-progress">{{ finished + " / " + allLength }}</div>
          <el-progress
              :show-text="false"
              :percentage="(finished / allLength) * 100"
          ></el-progress>
        </div>
      </el-row>
    </el-card>

    <div class="my-button">
      <el-button type="primary" @click="submitTestPaper" v-if="!isFinished">提交对抗练习</el-button>
    </div>
    <div class="my-button">
      <el-button type="info" @click="goBack" v-if="isFinished">返回主页</el-button>
    </div>
  </div>
        `
})