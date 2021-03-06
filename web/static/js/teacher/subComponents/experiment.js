var Experiment = Vue.extend({
    props: ['experimentList','courseId','classId'],
    data() {
        return {
            expContent:'',
            isDisabled:true
        };
    },
    methods: {
        tableRowClassName({row, rowIndex}) {
            if (row.status === '未发布') {
                return 'warning-row';
            } else if (row.status === '已发布') {
                return 'success-row';
            }
            return '';
        },
        releaseExperiment(row) {
            if (row.status === "已发布"){
                this.$message({
                    showClose: true,
                    message: '此实验已发布，不能重复发布！',
                    type: 'warning'
                })
                return false
            }
            this.$emit('release-experiment', row)
        },
        viewInfo(row) {
            if (row.status === "未发布"){
                this.$message({
                    showClose: true,
                    message: '此实验尚未发布，无法查看详情！',
                    type: 'warning'
                })
                this.isDisabled=true
                return false
            }
            this.isDisabled=false
            axios({
                url: '/SoftwareEngineering/instructorServlet?action=examineExperimentInfo',
                method: "Post",
                data: {
                    courseID: this.courseId,
                    classID: this.classId,
                    expName:row.title
                }
            }).then(resp => {
                this.expContent = resp.data.expInfo
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
                            :data="experimentList"
                            :row-class-name="tableRowClassName"
                            style="width: 100%">
                        <el-table-column
                                fixed
                                prop="title"
                                label="实验项目"
                                width="200">
                        </el-table-column>
                        <el-table-column
                                prop="startDate"
                                label="发布时间"
                                width="150">
                        </el-table-column>
                        <el-table-column
                                prop="status"
                                label="状态"
                                width="150">
                        </el-table-column>
                        <el-table-column
                                prop="priority"
                                label="优先级"
                                width="150">
                        </el-table-column>
                        <el-table-column
                                prop="weight"
                                label="分数占比"
                                width="150">
                        </el-table-column>
                        <el-table-column
                                label="难度"
                                width="200">
                                <template slot-scope="scope">
                                    <el-rate
                                      v-model="scope.row.difficulty"
                                      disabled
                                      show-score
                                      text-color="#ff9900"
                                      score-template="{value}">
                                    </el-rate>
                                </template>
                        </el-table-column>
                        <el-table-column
                                fixed="right"
                                label="操作"
                                width="150">
                            <template slot-scope="scope">
                                <el-popover
                                    placement="bottom"
                                    title="实验详情"
                                    width="400"
                                    trigger="click"
                                    :disabled="isDisabled"
                                    :content="expContent">
                                    <el-button @click="viewInfo(scope.row)" type="text" size="small" slot="reference">查看详情</el-button>
                                </el-popover>
                                <el-button type="text" size="small" @click="releaseExperiment(scope.row)">发布</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </template>
            </el-col>
        </el-row>
     </div>
    `
})