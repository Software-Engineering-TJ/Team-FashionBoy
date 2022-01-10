var SPractice = Vue.extend({
    props: ['classId', 'courseId','studentNumber'],
    data() {
        return {
            form: {
                amount: 0,
                endTime: ''
            },
            practiceList:[],
            practiceName:"",
        };
    },
    methods: {
        clickNotice(index) {
            this.$emit('click-notice', index)
        },
        publishPractice() {
            window.location.href = "/SoftwareEngineering/pages/answer.html?"+"courseID="+this.courseId+"&"+"classID="+this.classId+"&"+"studentNumber="+this.studentNumber+"&"+"practiceName="+this.practiceName
        },
    },
    mounted(){
        axios({
            url: '/SoftwareEngineering/studentServlet?action=viewPracticeStu',
            method: "Post",
            data: {
                courseID: this.courseId,
                classID: this.classId,
                studentNumber:this.studentNumber
            },
        }).then(resp => {
            this.practiceList = resp.data
            for(let index in this.practiceList){
                if (this.practiceList[index].status === "正在进行"){
                    this.practiceName = this.practiceList[index].practiceName
                    break;
                }
            }
        });
    },
    template: `
        <div style="line-height: initial;overflow: hidden;cursor: pointer;padding-top: 13px;background-color: white" >
            <div style="height:35px;position: relative;margin-bottom: 5px">
                <h2 style="display: inline-block;position: absolute;left: 14px;padding-left: 10px">已发布对抗练习:</h2>
            </div>
             <hr style="color: #C0C4CC;width: 96%;margin-left: 2%;margin-bottom: 6px">
            <div style="height:35px;position: relative;margin: 10px 0 10px 0">
                <el-button type="primary" style="position: absolute;right: 23px" @click="publishPractice">参与对抗练习</el-button>
            </div>
            <div style="height: 550px;overflow: auto">
                <el-row>
                        <el-col :span="22" :offset="1">
                            <template>
                                <el-table
                                        height="500"
                                        :data="practiceList"
                                        style="width: 100%">
                                    <el-table-column
                                            fixed
                                            prop="practiceName"
                                            label="对抗练习标题"
                                            >
                                    </el-table-column>
                                    <el-table-column
                                            prop="startTime"
                                            label="开始时间"
                                            >
                                    </el-table-column>
                                    <el-table-column
                                            prop="endTime"
                                            label="截止时间"
                                            >
                                    </el-table-column>
                                     <el-table-column
                                            prop="grade"
                                            label="成绩"
                                            >
                                    </el-table-column>
                                     <el-table-column
                                            prop="status"
                                            label="状态"
                                            >
                                    </el-table-column>
                                </el-table>
                            </template>
                        </el-col>
                </el-row>
            </div>
        </div>
        `
})