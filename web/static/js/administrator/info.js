var Info = Vue.extend({
    props:[],
    data() {
        return {
            infoList:[]
        };
    },
    created(){
        axios({
            url: '/SoftwareEngineering/administrationServlet?action=getCourseApplied',
            method: "Post",
        }).then(resp => {
           this.infoList = resp.data;
        })
    },
    methods: {
        correct(row) {
            axios({
                url: '/SoftwareEngineering/administrationServlet?action=AuditCourse',
                method: "Post",
                data:{
                    courseID:row.courseID,
                    result:'yes'
                }
            }).then(resp => {
                this.$message({
                    showClose: true,
                    message: '已批准开课申请！',
                    type: 'success'
                })
            })
        },
        unCorrect(row) {
            axios({
                url: '/SoftwareEngineering/administrationServlet?action=AuditCourse',
                method: "Post",
                data:{
                    courseID:row.courseID,
                    result:'no'
                }
            }).then(resp => {
                this.$message({
                    showClose: true,
                    message: '已拒绝开课申请！',
                    type: 'error'
                })
            })
        }
    },
    template: `
        <div style="padding-top: 30px">
        <hr style="color: #C0C4CC;width: 94%;margin-left: 3%;margin-bottom: 6px">
        <el-row>
            <el-col :span="22" :offset="1">
                <template>
                    <el-table
                            height="550"
                            :data="infoList"
                            :row-class-name="tableRowClassName"
                            style="width: 100%">
                        <el-table-column
                                fixed
                                prop="title"
                                label="课程名"
                                width="250">
                        </el-table-column>
                        <el-table-column
                                prop="courseID"
                                label="课程ID"
                                width="200">
                        </el-table-column>
                        <el-table-column
                                prop="instructorNumber"
                                label="教师工号"
                                width="200">
                        </el-table-column>
                        <el-table-column
                                prop="instructorName"
                                label="教师姓名"
                                width="150">
                        </el-table-column>
                        <el-table-column
                                label="操作"
                                >
                            <template slot-scope="scope">
                                <el-button type="text" @click="correct(scope.row)">批 准</el-button>
                                <el-button type="text" @click="unCorrect(scope.row)">否 定</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </template>
            </el-col>
        </el-row>
     </div>
        `
});