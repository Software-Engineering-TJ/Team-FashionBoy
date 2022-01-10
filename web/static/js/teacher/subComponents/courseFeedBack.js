var CourseFeedBack = Vue.extend({
    props: ['attendanceList', 'courseId', 'classId'],
    data() {
        return {
            feedBackList:[]
        }
    },
    mounted(){
        axios({
            url: '/SoftwareEngineering/instructorServlet?action=viewReflection',
            method: "Post",
            data: {
                courseID: this.courseId,
                classID: this.courseId,
            },
        }).then(resp => {
            console.log(resp.data)
            this.feedBackList=resp.data
        })
    },
    methods: {

    },
    template: `
     <div style="padding-top: 30px">
        <hr style="color: #C0C4CC;width: 94%;margin-left: 3%;margin-bottom: 6px">
        <el-row>
            <el-col :span="22" :offset="1">
                <template>
                    <el-table
                        :data="feedBackList"
                        style="width: 100%"
                        :default-sort = "{prop: 'date', order: 'descending'}"
                        >
                        <el-table-column
                          prop="content"
                          label="日期"
                          sortable
                          width="180">
                        </el-table-column>
                        <el-table-column
                          prop="studentNumber"
                          label="学号"
                          width="180">
                        </el-table-column>
                        <el-table-column
                          prop="studentName"
                          label="姓名"
                          width="180">
                        </el-table-column>
                        <el-table-column
                          prop="date"
                          label="反馈内容">
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