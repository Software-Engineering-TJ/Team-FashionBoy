var SExperiment = Vue.extend({
    props: ['experimentList','courseId','classId'],
    data() {
        return {};
    },
    methods: {
        tableRowClassName({row, rowIndex}) {
            if (row.status === '正在进行') {
                return 'warning-row';
            } else if (row.status === '已结束') {
                return 'success-row';
            }
            return '';
        },
        viewInfo(row) {
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
                                prop="expName"
                                label="实验项目"
                                width="200">
                        </el-table-column>
                        <el-table-column
                                prop="startDate"
                                label="发布时间"
                                width="150">
                        </el-table-column>
                        <el-table-column
                                prop="endDate"
                                label="截止时间"
                                width="150">
                        </el-table-column>
                        <el-table-column
                                prop="status"
                                label="状态"
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
                                    :content="scope.row.expInfo">
                                    <el-button type="text" size="small" slot="reference">查看详情</el-button>
                                </el-popover>
                            </template>
                        </el-table-column>
                    </el-table>
                </template>
            </el-col>
        </el-row>
     </div>
    `
})