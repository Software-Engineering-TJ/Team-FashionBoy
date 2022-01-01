var ClassInfo = Vue.extend({
    props: ['classInfoList'],
    data() {
        return {};
    },
    methods: {
        tableRowClassName({row, rowIndex}) {
            if (row.duty === '任课教师') {
                return 'warning-row';
            } else if (row.duty === '学生' || row.duty === '助教') {
                return 'success-row';
            }
            return '';
        },
    },
    template: `
     <div style="padding-top: 30px">
        <hr style="color: #C0C4CC;width: 94%;margin-left: 3%;margin-bottom: 6px">
        <el-row>
            <el-col :span="22" :offset="1">
                <template>
                    <el-table
                            :data="classInfoList"
                            :row-class-name="tableRowClassName"
                            style="width: 100%">
                        <el-table-column
                                fixed
                                prop="userNumber"
                                label="学号/工号">
                        </el-table-column>
                        <el-table-column
                                prop="userName"
                                label="姓名">
                        </el-table-column>
                        <el-table-column
                                prop="duty"
                                label="身份">
                        </el-table-column>
                    </el-table>
                </template>
            </el-col>
        </el-row>
     </div>
    `
})