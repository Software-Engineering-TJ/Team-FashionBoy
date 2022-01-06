var SAttendance = Vue.extend({
    props: ['attendanceList','studentNumber', 'courseName', 'courseId', 'classId'],
    data() {
        return {
        };
    },
    methods: {
        tableRowClassName({row, rowIndex}) {
            if (row.status === "未签到") {
                return 'warning-row';
            } else if (row.status === "已签到") {
                return 'success-row';
            }else if (row.status === "已过期"){
                return 'error-row';
            }
            return '';
        },
        signIn(row) {
            if (row.status === "已过期"){
                this.$message({
                    type: 'error',
                    message: '超出截止时间，签到失败！'
                });
                return false
            } else if (row.status === "已签到")
            {
                this.$message({
                    type: 'error',
                    message: '您已经签到，不能重复签到！'
                });
                return false
            }
            axios({
                url: '/SoftwareEngineering/studentServlet?action=signIn',
                method: "Post",
                data: {
                    courseID: this.$props.courseId,
                    classID: this.$props.classId,
                    studentNumber: this.$props.studentNumber,
                    AttendanceName: row.attendanceName
                },
            }).then(resp => {
                this.$emit('get-attendance-info-stu')
                if (resp.data.result === 0) {
                    this.$message({
                        type: 'error',
                        message: '超出截止时间，签到失败！'
                    });
                } else if (resp.data.result === 1) {
                    this.$message({
                        type: 'success',
                        message: '签到成功！'
                    });
                }
            })
        },
    },
    template: `
     <div style="padding-top: 30px">
        <hr style="color: #C0C4CC;width: 94%;margin-left: 3%;margin-bottom: 6px">
        <el-row>
            <el-col :span="22" :offset="1">
                <template>
                    <el-table
                            height="550"
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
                                label="考勤状态"
                                width="150">
                        </el-table-column>
                        <el-table-column
                                label="操作"
                                >
                            <template slot-scope="scope">
                                <el-button type="text" @click="signIn(scope.row)">签到</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </template>
            </el-col>
        </el-row>
     </div>
    `
})