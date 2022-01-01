var Attendance = Vue.extend({
    props: ['attendanceList','courseId', 'classId'],
    data() {
        return {
            //考勤发布窗口是否可见
            innerVisible:false,
            //考勤信息表单
            form:{
                attendanceName:'',
                endTime:'',
                percent:'25%'
            },
        };
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
        releaseAttendance(){
            let endDate = this.form.endTime.getFullYear()+'-'+(this.form.endTime.getMonth()+1)+'-'+this.form.endTime.getDate()+' '+this.form.endTime.getHours()+':'+this.form.endTime.getMinutes()+':'+this.form.endTime.getSeconds()
            axios({
                url: '/SoftwareEngineering/instructorServlet?action=releaseSignIn',
                method: "Post",
                data: {
                    courseID:this.$props.courseId,
                    classID:this.$props.classId,
                    attendanceName:this.form.attendanceName,
                    endTime:endDate,
                    percent:'2'
                },
            }).then(resp =>{
                if (resp.data.result === 0){
                    if (resp.data.msg === 2){
                        this.$message({
                            type: 'error',
                            message: '考勤命名重复，发布失败！'
                        });
                    }
                } else if (resp.data.result === 1){
                    this.$message({
                        type: 'success',
                        message: '发布成功！'
                    });
                }
            })
        }
    },
    template: `
     <div style="padding-top: 30px">
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
                                <el-button type="text" @click="releaseExperiment(scope.row)">查看考勤情况</el-button>
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