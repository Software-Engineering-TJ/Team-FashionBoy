var ExperimentalReport = Vue.extend({
        props: ['reportList', 'courseId', 'classId'],
        data() {
            return {
                studentNumber: '',
                submitStudentList: [],
                unSubmitStudentList: [],
                form: {
                    score: 0,
                    comment: ''
                },
                submitDialogTableVisible: false,
                innerVisible: false,
                expname: '',
            };
        },
        computed: {
            percentage: function () {
                let oriPercentage = this.submitStudentList.length / (this.unSubmitStudentList.length + this.submitStudentList.length) * 100;
                return Number.parseFloat(oriPercentage.toString().substr(0, 5));
            }
        },
        methods: {
            clickReport(index) {
                this.$emit('click-report', index)
            },
            checkSubmission(expName) {
                this.expname = expName
                axios({
                    url: '/SoftwareEngineering/instructorServlet?action=viewSubmission',
                    method: "Post",
                    data: {
                        expname: expName,
                        courseID: this.$props.courseId,
                        classID: this.$props.classId,
                    }
                }).then(resp => {
                    console.log(resp.data.submitted)
                    this.submitStudentList = resp.data.submitted
                    for (let index in this.submitStudentList) {
                        if (this.submitStudentList[index].score === -1) {
                            this.submitStudentList[index].score = "暂无"
                            this.submitStudentList[index].isCorrect = "否"
                        } else {
                            this.submitStudentList[index].isCorrect = "是"
                        }
                    }
                    this.unSubmitStudentList = resp.data.unSubmitted
                })
                this.submitDialogTableVisible = true;
            },
            releaseReportDesc() {
                this.$emit('release-report-desc')
            },
            fileDownLoad(row) {
                console.log(row)
            },
            registerMarks(row) {
                this.studentNumber = row.studentNumber
                this.innerVisible = true;
            },
            registerGrade() {
                axios({
                    url: '/SoftwareEngineering/instructorServlet?action=registerGrade',
                    method: "Post",
                    data: {
                        courseID: this.$props.courseId,
                        classID: this.$props.classId,
                        studentNumber: this.studentNumber,
                        expname: this.expname,
                        score: this.form.score,
                        comment: this.form.comment
                    },
                }).then(resp => {
                    this.$message({
                        message: '成绩录入成功！',
                        type: 'success'
                    });
                    this.innerVisible = fasle;
                });
            }
        },
        template: `
         <div style="padding: 20px">
                <el-dialog title="实验报告提交情况" :visible.sync="submitDialogTableVisible" :fullscreen="true">
                       <el-dialog
                              width="30%"
                              title="录入成绩"
                              :visible.sync="innerVisible"
                              append-to-body>
                              <el-form :model="form">
                                <el-form-item label="该生成绩" label-width="80px">
                                  <el-input v-model="form.score" autocomplete="off" style="width: 80%"></el-input>
                                </el-form-item>
                                <el-form-item label="评语" label-width="80px">
                                  <el-input v-model="form.comment" autocomplete="off" style="width: 80%"></el-input>
                                </el-form-item>
                              </el-form>
                              <div slot="footer" class="dialog-footer">
                                <el-button @click="innerVisible = false">取 消</el-button>
                                <el-button type="primary" @click="registerGrade">确 定</el-button>
                              </div>
                       </el-dialog>
                      <el-divider content-position="left"><h3>提交率</h3></el-divider>
                      <el-progress style="margin: 50px 0 60px 0" :text-inside="true" :stroke-width="24" :percentage="percentage"></el-progress>
                      
                      <el-tabs value="first">
                            <el-tab-pane label="已提交名单" name="first">
                                <el-divider content-position="left"><h3>已提交学生人数：{{submitStudentList.length}}</h3></el-divider>
                                      <el-row><el-col :span="3" :offset="18"><el-button>导出所有实验报告</el-button></el-col><el-col :span="3"><el-button>批量录入成绩</el-button></el-col></el-row>
                                      <el-table :data="submitStudentList" row-class-name="success-row" max-height="600">
                                             <el-table-column property="studentNumber" label="学号" width="250"></el-table-column>
                                             <el-table-column property="studentName" label="姓名" width="250"></el-table-column>
                                             <el-table-column property="isCorrect" label="成绩是否录入" width="250"></el-table-column>
                                             <el-table-column property="score" label="分数" width="250"></el-table-column>
                                             <el-table-column
                                                    label="操作">
                                                  <template slot-scope="scope">
                                                    <el-link :underline="false" type="text" :href="scope.row.fileUrl" target="_blank">下载实验报告</el-link>
                                                    <el-button @click="registerMarks(scope.row)" type="text" size="small">成绩录入</el-button>
                                                  </template>
                                            </el-table-column>
                                      </el-table>
                            </el-tab-pane>
                            <el-tab-pane label="未提交名单" name="second">
                            
                                <el-divider content-position="left"><h3>未提交学生人数：{{unSubmitStudentList.length}}</h3></el-divider>
                                <el-row><el-col :span="3" :offset="21"><el-button>批量录入成绩</el-button></el-col></el-row>
                                <el-table :data="unSubmitStudentList" row-class-name="warning-row" max-height="600">
                                    <el-table-column property="studentNumber" label="学号" ></el-table-column>
                                    <el-table-column property="studentName" label="姓名" ></el-table-column>
                                    <el-table-column property="isCorrect" label="成绩是否录入" width="250"></el-table-column>
                                    <el-table-column property="score" label="分数" width="250"></el-table-column>
                                    <el-table-column
                                                    label="操作">
                                                  <template slot-scope="scope">
                                                    <el-button @click="registerMarks(scope.row)" type="text" size="small">成绩录入</el-button>
                                                  </template>
                                    </el-table-column>
                                </el-table> 
                            </el-tab-pane>
                      </el-tabs>                 
               </el-dialog>
                <el-row :gutter="0" style="margin-bottom: 0px">
                    <el-col :span="1">
                        <div class="grid-content bg-purple" style="border-radius: 0;background-color: white"></div>
                    </el-col>
                    <el-col :span="4">
                        <div class="grid-content bg-purple" style="border-radius: 0;background-color: white;line-height: 36px">实验报告名</div>
                    </el-col>
                    <el-col :span="3">
                        <div class="grid-content bg-purple" style="border-radius: 0;background-color: white"></div>
                    </el-col>
                    <el-col :span="3">
                        <div class="grid-content bg-purple" style="border-radius: 0;line-height: 36px;background-color: white">文件格式</div>
                    </el-col>
                    <el-col :span="2">
                        <div class="grid-content bg-purple" style="border-radius: 0;line-height: 36px;background-color: white"></div>
                    </el-col>
                    <el-col :span="3">
                        <div class="grid-content bg-purple" style="border-radius: 0;line-height: 36px;background-color: white">截止日期</div>
                    </el-col>
                    <el-col :span="2">
                        <div class="grid-content bg-purple" style="border-radius: 0;line-height: 36px;background-color: white"></div>
                    </el-col>
                    <el-col :span="2">
                        <div class="grid-content bg-purple" style="border-radius: 0;line-height: 36px;background-color: white">分数占比</div>
                    </el-col>
                    <el-col :span="4">
                        <div class="grid-content bg-purple" style="border-radius: 0;line-height: 36px;background-color: white">操作</div>
                    </el-col>   
                </el-row>
                <hr style="margin-bottom: 5px;color: #C0C4CC">
            <transition-group name="el-fade-in-linear">
            <div v-for="(item,index) in reportList" style="color: black" :key="item.reportName">
                <div style="cursor: pointer;" @click="clickReport(index)">
                    <el-row :gutter="0" style="margin-bottom: 5px">
                        <el-col :span="1">
                            <div class="my-bg" style="border-radius: 4px 0 0 4px;line-height: 37px">
                                <i class="el-icon-document-checked" style="font-size: 30px;padding-top: 10px"></i>
                            </div>
                        </el-col>
                        <el-col :span="4">
                            <div class="my-bg" style="line-height: 48px">{{item.reportName}}</div>
                        </el-col>
                        <el-col :span="3">
                            <div class="my-bg"></div>
                        </el-col>
                        <el-col :span="2">
                            <div class="my-bg">{{item.reportType}}</div>
                        </el-col>
                        <el-col :span="1">
                            <div class="my-bg"></div>
                        </el-col>
                        <el-col :span="7">
                            <div class="my-bg">{{item.endDate}}</div>
                        </el-col>
                        <el-col :span="2">
                            <div class="my-bg">{{item.weight}}</div>
                        </el-col>
                        <el-col :span="4">
                            <div class="my-bg" style="border-radius: 0 4px 4px 0">
                                <el-button @click.stop="checkSubmission(item.expName)" type="text">查看提交情况&成绩录入
                                </el-button>
                            </div>
                        </el-col>
                    </el-row>
                </div>
            </div>
            </transition-group>
            <div style="display: inline-block;position: absolute;bottom: 30px;right: 30px">
                <el-button type="primary" plain @click="releaseReportDesc">上传实验报告说明</el-button>
            </div>
        </div>
        `
    })
;