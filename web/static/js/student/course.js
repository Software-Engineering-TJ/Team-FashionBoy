var Course = Vue.extend({
    props: ['studentNumber', 'courseName', 'courseId', 'classId', 'noticeList'],
    data() {
        return {
            changeComponents: 'notice',
            noticeInfo: {},
            reportInfo: {},
            fileList: [
                {
                    fileName: 'grade.pdf',
                    fileURL: '/static/file/1953281-王文炯-学习心得.pdf',
                    fileSize: '23k',
                    upLoadDate: '2021.9.27',
                    upLoadUser: '黄杰'
                },
                {
                    fileName: 'class.txt',
                    fileURL: '/1953281-王文炯-学习心得.pdf',
                    fileSize: '46k',
                    upLoadDate: '2021.10.3',
                    upLoadUser: '张晶'
                },
                {
                    fileName: '实验一参考资料.doc',
                    fileURL: '/1953281-王文炯-学习心得.pdf',
                    fileSize: '78k',
                    upLoadDate: '2021.11.2',
                    upLoadUser: '金伟祖'
                },
            ],
            reportList: [],
        }
    },
    methods: {
        goBack() {
            this.$emit('go-back')
        },
        goBackNotice() {
            this.changeComponents = 'Notice'
        },
        goBackReport() {
            this.changeComponents = 'ExperimentalReport'
        },
        // 选择对应的导航栏项触发
        selectMenuItem(key, keyPath) {
            switch (key) {
                case "1":
                    this.changeComponents = "Notice"
                    break;
                case "3":
                    this.changeComponents = "FileDownLoad"
                    // 在选择”参考文件“选项后，还需要向后端请求该课程下老师发布的所有文件
                    break;
                case "4":
                    this.getReportDesc()
                    this.changeComponents = "ExperimentalReport"
                    // 在选择”参考文件“选项后，还需要向后端请求该课程下老师发布的所有文件
                    break;
                case "6":
                    this.changeComponents = "SAttendance"
                    break;
                case "8":
                    this.changeComponents = "Grade"
                    break;
                default:
                    break;
            }
        },
        clickNotice(index) {
            this.noticeInfo = JSON.parse(JSON.stringify(this.noticeList[index]))
            this.changeComponents = 'NoticeDetail'
        },
        clickReport(index) {
            console.log(index)
            this.reportInfo = JSON.parse(JSON.stringify(this.reportList[index]))
            this.changeComponents = 'EpReportDetail'
        },
        getReportDesc() {
            console.log(this.$props.courseId, this.$props.classId)
            let course = this
            axios({
                url: '/SoftwareEngineering/userServlet?action=getReportDesc',
                method: "Post",
                data: {
                    courseID: this.$props.courseId,
                    classID: this.$props.classId,
                }
            }).then(resp => {
                course.reportList = JSON.parse(JSON.stringify(resp.data));
            })
        },
        getFileList() {
            let course = this
            axios({
                url: '/SoftwareEngineering/userServlet?action=getReportDesc',
                method: "Post",
                data: {
                    courseID: this.$props.courseId,
                    classID: this.$props.classId,
                }
            }).then(resp => {
                course.reportList = JSON.parse(JSON.stringify(resp.data));
            })
        }
    },
    components: {
        // 公告板组件
        Notice,
        // 公告板详细信息组件
        NoticeDetail,
        // 参考资料组件
        FileDownLoad,
        // 实验报告组件
        ExperimentalReport,
        // 实验报告详情组件
        EpReportDetail,
        // 我的成绩组件
        Grade,
        // 考勤组件
        SAttendance
    },
    template: `
    <el-row class="tac">
    
        <!-- 页头开始 -->
        <el-row>
            <el-col>        
                <div style="margin-bottom: 10px">
                    <el-page-header @back="goBack" >
                        <template slot="content">
                            <h4>{{courseName}}</h4>
                        </template>
                    </el-page-header>
                </div>
                <hr style="margin-bottom: 7px;color: #C0C4CC">
            </el-col>
        </el-row>
        <!-- 页头结束 -->
        
        <el-row :gutter="15">
            <!-- 侧边栏开始 -->
            <el-col :span="3">
                <div style="height: 650px;background-color: white">
                    <el-menu
                            default-active="1"
                            @select="selectMenuItem"
                            class="el-menu-vertical-demo">
                        <el-menu-item index="1">
                            <span slot="title">公告</span>
                        </el-menu-item>
                        <el-menu-item index="2">
                            <span slot="title">实验</span>
                        </el-menu-item>
                        <el-menu-item index="3">
                            <span slot="title">参考资料</span>
                        </el-menu-item>
                        <el-menu-item index="4">
                            <span slot="title">实验报告</span>
                        </el-menu-item>
                        <el-menu-item index="5">
                            <span slot="title">对抗练习</span>
                        </el-menu-item>
                        <el-menu-item index="6">
                            <span slot="title">考勤</span>
                        </el-menu-item>
                        <el-menu-item index="7">
                            <span slot="title">班级信息</span>
                        </el-menu-item>
                        <el-menu-item index="8">
                            <span slot="title">我的成绩</span>
                        </el-menu-item>
                    </el-menu>
                </div>
            </el-col>
            <!-- 侧边栏结束 -->
            
            <!-- 主显示区开始 -->
            <el-col :span="21">
                <div id="subMainBox" style="background-color: white;height: 650px">
                    <keep-alive>
                        <component :is="changeComponents" 
                        @click-notice="clickNotice" 
                        @go-back-notice="goBackNotice" 
                        @go-back-report="goBackReport" 
                        :noticeList="noticeList" 
                        :noticeInfo="noticeInfo" 
                        :file-list="fileList" 
                        :report-list="reportList" 
                        :report-info="reportInfo" 
                        @click-report="clickReport"
                        ></component>
                    </keep-alive>
                </div>
            </el-col>
            <!-- 主显示区结束 -->
        </el-row>
    </el-row>
    `
})