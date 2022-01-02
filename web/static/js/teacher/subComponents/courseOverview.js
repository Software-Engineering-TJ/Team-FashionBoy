var CourseOverview = Vue.extend({
    props: ['courseInfoList'],
    data() {
        return {};
    },
    methods: {
        getSectionInfo(row){
            this.$emit('get-section-info',row.courseID)
        },
        getNoticeInfo(){
            this.$emit('get-notice-info')
        },
        deleteCourse(){

        }
    },
    template: `
     <div style="padding:30px 0 30px 0;background-color: white">
        <div style="width: 90%;margin-left: 5%">
            <template>
                  <el-table
                    :data="courseInfoList"
                    height="600"
                    style="width: 100%">
                    <el-table-column
                      prop="title"
                      label="课程名"
                      fixed
                      width="600">
                      <template slot-scope="scope">
                         <h3 style="margin-left: 10px"><i>{{ scope.row.title }}</i></h3>
                      </template>
                    </el-table-column>
                    <el-table-column
                      prop="courseID"
                      label="课程编号"
                      width="150">
                    </el-table-column>
                    <el-table-column
                          fixed="right"
                          label="操作"
                          width="400">
                          <template slot-scope="scope">
                            <el-button @click="getSectionInfo(scope.row)" plain type="primary" size="large">查看班级信息</el-button>
                            <el-button @click="getNoticeInfo(scope.row)" plain type="primary" size="large">查看公告信息</el-button>
                            <el-button @click="deleteCourse(scope.row)" plain type="danger" size="large">删除课程</el-button>
                          </template>
                    </el-table-column>
                  </el-table>
            </template>
        </div>
     </div>
    `
})