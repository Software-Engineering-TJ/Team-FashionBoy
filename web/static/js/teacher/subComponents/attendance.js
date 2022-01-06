var Attendance = Vue.extend({
    props: ['attendanceList', 'courseId', 'classId'],
    data() {
        return {
            //考勤发布窗口是否可见
            innerVisible: false,
            //考勤信息表单
            form: {
                attendanceName: '',
                endTime: '',
                percent: '25%'
            },
            attendanceDialogTableVisible: false,
            attendanceStudentList: [],
            unStudentList: [],
        };
    },
    computed: {
        percentage: function () {
            let oriPercentage = this.attendanceStudentList.length / (this.unStudentList.length + this.attendanceStudentList.length) * 100;
            return Number.parseFloat(oriPercentage.toString().substr(0, 5));
        }
    },
    methods: {
        tableRowClassName({row, rowIndex}) {
            if (row.status === "已结束") {
                return 'warning-row';
            } else if (row.status === "正在进行") {
                return 'success-row';
            }
            return '';
        },
        releaseAttendance() {
            let endDate = this.form.endTime.getFullYear() + '-' + ((this.form.endTime.getMonth() + 1) < 10 ? '0' + (this.form.endTime.getMonth() + 1) : (this.form.endTime.getMonth() + 1)) + '-' + (this.form.endTime.getDate() < 10 ? '0' + this.form.endTime.getDate() : this.form.endTime.getDate()) + ' ' + (this.form.endTime.getHours()<10?'0'+  this.form.endTime.getHours(): this.form.endTime.getHours())+ ':' + (this.form.endTime.getMinutes()<10?'0'+this.form.endTime.getMinutes():this.form.endTime.getMinutes()) + ':' + (this.form.endTime.getSeconds()<10?'0'+this.form.endTime.getSeconds():this.form.endTime.getSeconds())
            axios({
                url: '/SoftwareEngineering/instructorServlet?action=releaseSignIn',
                method: "Post",
                data: {
                    courseID: this.$props.courseId,
                    classID: this.$props.classId,
                    attendanceName: this.form.attendanceName,
                    endTime: endDate,
                    percent: '2'
                },
            }).then(resp => {
                if (resp.data.result === 0) {
                    this.$message({
                        type: 'error',
                        message: '考勤命名重复，发布失败！'
                    });
                } else if (resp.data.result === 1) {
                    this.$message({
                        type: 'success',
                        message: '发布成功！'
                    });
                    this.innerVisible =false
                    this.$emit('get-attendance-info')
                }
            })
        },
        checkAttendance(row) {
            console.log(row)
            axios({
                url: '/SoftwareEngineering/instructorServlet?action=viewAttendance',
                method: "Post",
                data: {
                    AttendanceName: row.attendanceName,
                    courseID: this.$props.courseId,
                    classID: this.$props.classId,
                }
            }).then(resp =>{
                this.attendanceStudentList = resp.data.submitted
                this.unStudentList = resp.data.unSubmitted
            })
            this.attendanceDialogTableVisible = true;
        }
    },
    template: `
     <div style="padding-top: 30px">
     <el-dialog title="考勤提交情况" :visible.sync="attendanceDialogTableVisible" :fullscreen="true">
                      <el-divider content-position="left"><h3>提交率</h3></el-divider>
                      <el-progress style="margin: 50px 0 60px 0" :text-inside="true" :stroke-width="24" :percentage="percentage"></el-progress>
                   
                      <el-tabs value="first">
                            <el-tab-pane label="已提交名单" name="first">
                                <el-divider content-position="left"><h3>已提交学生人数：{{attendanceStudentList.length}}</h3></el-divider>
                                      
                                      <el-table :data="attendanceStudentList" row-class-name="success-row" max-height="600">
                                             <el-table-column property="studentNumber" label="学号"></el-table-column>
                                             <el-table-column property="studentName" label="姓名"></el-table-column>
                                      </el-table>
                            </el-tab-pane>
                            <el-tab-pane label="未提交名单" name="second">
                                <el-divider content-position="left"><h3>未提交学生人数：{{unStudentList.length}}</h3></el-divider>
                                <el-table :data="unStudentList" row-class-name="warning-row" max-height="600">
                                    <el-table-column property="studentNumber" label="学号" ></el-table-column>
                                    <el-table-column property="studentName" label="姓名" ></el-table-column>
                                </el-table> 
                            </el-tab-pane>
                      </el-tabs>                 
               </el-dialog>
        <el-dialog
                              width="30%"
                              title="考勤发布"
                              :visible.sync="innerVisible"
                              append-to-body>
                              <el-form :model="form">
                                <el-form-item label="考勤名称" label-width="80px">
                                  <el-input v-model="form.attendanceName" autocomplete="off" style="width: 80%"></el-input>
                                </el-form-item>
                                <el-form-item label="截止时间" label-width="80px">
                                   <el-time-picker placeholder="选择时间" v-model="form.endTime"
                                            style="width: 80%;"></el-time-picker>
                                 </el-form-item>
                              </el-form>
                              <div slot="footer" class="dialog-footer">
                                <el-button @click="innerVisible = false">取 消</el-button>
                                <el-button type="primary" @click="releaseAttendance">发 布</el-button>
                              </div>
                       </el-dialog>
        <hr style="color: #C0C4CC;width: 94%;margin-left: 3%;margin-bottom: 6px">
        <el-row>
            <el-col :span="22" :offset="1">
                <template>
                    <el-table
                            height="500"
                            :data="attendanceList"
                            :row-class-name="tableRowClassName"
                            style="width: 100%">
                        <el-table-column
                                fixed
                                prop="attendanceName"
                                label="考勤项目"
                                width="250">
                        </el-table-column>
                        <el-table-column
                                prop="startTime"
                                label="开始时间"
                                width="200">
                        </el-table-column>
                        <el-table-column
                                prop="endTime"
                                label="截止时间"
                                width="200">
                        </el-table-column>
                        <el-table-column
                                prop="status"
                                label="状态"
                                width="150">
                        </el-table-column>
                        <el-table-column
                                label="操作"
                                >
                            <template slot-scope="scope">
                                <el-button type="text" @click="checkAttendance(scope.row)">查看考勤情况</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </template>
            </el-col>
        </el-row>
        <div style="display: inline-block;position: absolute;bottom: 30px;right: 30px">
                <el-button type="primary" plain @click="innerVisible = true">发布考勤</el-button>
        </div>
     </div>
    `
})