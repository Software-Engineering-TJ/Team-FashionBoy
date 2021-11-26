var Course = Vue.extend({
    props: ['studentNumber', 'courseName', 'courseID', 'classID', 'noticeList'],
    data() {
        return {
            changeComponents: 'Notice',
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
            reportList: [
                {
                    reportName: '实验一实验报告',
                    reportDescription: '请同学们认真完成实验报告！',
                    startDate: '2021.11.1',
                    endDate: '2021.12.1. 23：59',
                    reportType: 'doc.txt.pdf',
                    score: '暂未发布',
                    weight: '25%'
                },
                {
                    reportName: '实验二实验报告',
                    reportDescription: '请同学们认真完成实验报告！',
                    startDate: '2021.11.1',
                    endDate: '2021.12.1. 23：59',
                    reportType: 'doc.txt.pdf',
                    score: '暂未发布',
                    weight: '25%'
                },
                {
                    reportName: '实验三实验报告',
                    reportDescription: '请同学们认真完成实验报告！',
                    startDate: '2021.11.1',
                    endDate: '2021.12.1. 23：59',
                    reportType: 'doc.txt.pdf',
                    score: '暂未发布',
                    weight: '25%'
                }
            ],
            materialList: [
                {
                    name: 'food.jpeg',
                    url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
                },
                {
                    name: 'food.jpeg',
                    url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
                },
                {
                    name: 'food.jpeg',
                    url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
                }
            ],
            experimentList: [
                {
                    title: '组播路由实验',
                    difficulty: 3,
                    priority: '3',
                    date: '2021.12.11',
                    status: '已发布',
                    weight: '25%'
                },
                {
                    title: '帧中继实验',
                    difficulty: 4,
                    priority: '2',
                    date: '',
                    status: '未发布',
                    weight: '25%'
                },
                {
                    title: '静态路由实验',
                    difficulty: 4.5,
                    priority: '1',
                    date: '2021.12.11',
                    status: '已发布',
                    weight: '25%'
                },
            ]
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
                case "2":
                    this.changeComponents = "Experiment"
                    break;
                case "3":
                    this.changeComponents = "FileDownLoad"
                    // 在选择”参考文件“选项后，还需要向后端请求该课程下老师发布的所有文件
                    break;
                case "4":
                    this.changeComponents = "ExperimentalReport"
                    // 在选择”参考文件“选项后，还需要向后端请求该课程下老师发布的所有文件
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
        publishAnnouncement() {
            this.$emit('publish-announcement')
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
        // 实验组件
        Experiment
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
                            <span slot="title">成绩导入</span>
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
                        :material-list="materialList"
                        :experiment-list="experimentList"
                        @click-report="clickReport" 
                        @publish-announcement="publishAnnouncement"></component>
                    </keep-alive>
                </div>
            </el-col>
            <!-- 主显示区结束 -->
        </el-row>
    </el-row>
    `
})