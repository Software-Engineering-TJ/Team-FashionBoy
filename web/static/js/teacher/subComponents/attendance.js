var Attendance = Vue.extend({
    props: ['experimentList'],
    data() {
        return {
            attendanceList:[
                {
                    attendanceName:"考勤一",
                    startTime:"2021-11-24 13:00",
                    endTime:"2021-11-24 13:05",
                    score:5,
                    status:"已完成"
                },
                {
                    attendanceName:"考勤二",
                    startTime:"2021-11-25 13:00",
                    endTime:"2021-11-25 13:05",
                    score:5,
                    status:"已完成"
                },
                {
                    attendanceName:"考勤三",
                    startTime:"2021-11-26 13:00",
                    endTime:"2021-11-26 13:05",
                    score:5,
                    status:"正在进行"
                }
            ]
        };
    },
    methods: {
        tableRowClassName({row, rowIndex}) {
            if (row.status === "已完成") {
                return 'warning-row';
            } else if (row.status === "正在进行") {
                return 'success-row';
            }
            return '';
        },
        releaseExperiment(row){
            this.$emit('release-experiment',row)
        }
    },
    template: `
     <div style="padding-top: 30px">
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
                                width="200">
                        </el-table-column>
                        <el-table-column
                                prop="startTime"
                                label="开始时间"
                                width="150">
                        </el-table-column>
                        <el-table-column
                                prop="endTime"
                                label="截止时间"
                                width="150">
                        </el-table-column>
                        <el-table-column
                                prop="score"
                                label="分数占比"
                                width="150">
                        </el-table-column>
                        <el-table-column
                                prop="status"
                                label="分数占比"
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
     </div>
    `
})