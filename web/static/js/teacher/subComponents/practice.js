var Practice = Vue.extend({
    props:['classId','courseId'],
    data() {
        return {
            innerVisible:false,
            form:{
                title:"",
                amount:0,
                endTime:''
            },
            practiceList:[{
                practiceName:"对抗练习一",
                startTime:"2021-12-23",
                endTime:"2021-12-25"
            }]
        };
    },
    methods: {
        clickNotice(index) {
            this.$emit('click-notice',index)
        },
        publishPractice(){
            this.innerVisible=true
        },
        releasePractice(){
            axios({
                url: '/SoftwareEngineering/practiceServer?action=initPracticeServer',
                method: "Post",
                data: {
                    courseID: this.courseId,
                    classID: this.classId,
                    size:this.form.amount,
                    endTime:this.form.endTime
                },
            }).then(resp => {
                axios({
                    url: '/SoftwareEngineering/practiceServer?action=startServer',
                    method: "Post",
                }).then(resp => {});
                this.$message({
                    showClose: true,
                    message: '对抗练习发布成功！',
                    type: 'success'
                })
                this.innerVisible = false;
            });
        }
    },
    mounted(){
        axios({
            url: '/SoftwareEngineering/instructorServlet?action=getPractice',
            method: "Post",
            data: {
                courseID: this.courseId,
                classID: this.classId,
            },
        }).then(resp => {
            this.practiceList = resp.data
        });
    },
    template: `
        <div style="line-height: initial;overflow: hidden;cursor: pointer;padding-top: 13px;background-color: white" >
            <el-dialog
                              width="30%"
                              title="填写对抗练习参数"
                              :visible.sync="innerVisible"
                              append-to-body>
                              <el-form :model="form">
                              <el-form-item label="对抗练习标题" label-width="80px">
                                  <el-input v-model="form.title" autocomplete="off" style="width: 80%"></el-input>
                                </el-form-item>
                                <el-form-item label="题目数量" label-width="80px">
                                  <el-input v-model="form.amount" autocomplete="off" style="width: 80%"></el-input>
                                </el-form-item>
                                <el-form-item prop="form.endTime">
                                    <el-date-picker type="date" placeholder="截止时间" v-model="form.endTime"
                                            style="width: 100%;"></el-date-picker>
                                </el-form-item>
                              </el-form>
                              <div slot="footer" class="dialog-footer">
                                <el-button @click="innerVisible = false">取 消</el-button>
                                <el-button type="primary" @click="releasePractice">发 布</el-button>
                              </div>
                       </el-dialog>
            <div style="height:35px;position: relative;margin-bottom: 5px">
                <h2 style="display: inline-block;position: absolute;left: 14px;padding-left: 10px">已发布对抗练习:</h2>
            </div>
             <hr style="color: #C0C4CC;width: 96%;margin-left: 2%;margin-bottom: 6px">
            <div style="height:35px;position: relative;margin: 10px 0 10px 0">
                <el-button type="primary" style="position: absolute;right: 23px" @click="publishPractice">发布对抗练习</el-button>
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
                                </el-table>
                            </template>
                        </el-col>
                    </el-row>
            </div>
        </div>
        `
})