var SEpReportDetail = Vue.extend({
    props: ['reportInfo', 'studentNumber', 'courseName', 'courseId', 'classId'],
    data() {
        return {
            fileList: [],
            beforeUpload: false,
            isDisabled:true,
            grade: '',
            comment:''
        };
    },
    created() {
        let customTime = this.$props.reportInfo.endDate
        let currentTime = new Date();
        customTime = customTime.replace("-", "/");//替换字符，变成标准格式
        customTime = new Date(Date.parse(customTime));
        this.beforeUpload = currentTime >= customTime;
    },
    mounted() {
        axios({
            url: '/SoftwareEngineering/studentServlet?action=getGrade',
            method: "Post",
            data: {
                courseID: this.$props.courseId,
                classID: this.$props.courseId,
                studentNumber: this.$props.studentNumber,
                expName: this.reportInfo.expName
            },
        }).then(resp => {
            this.grade = resp.data.grade
            this.comment = resp.data.comment
        })
    },
    updated() {
        let customTime = this.$props.reportInfo.endDate
        let currentTime = new Date();
        customTime = customTime.replace("-", "/");//替换字符，变成标准格式
        customTime = new Date(Date.parse(customTime));
        this.beforeUpload = currentTime >= customTime;
        axios({
            url: '/SoftwareEngineering/studentServlet?action=getGrade',
            method: "Post",
            data: {
                courseID: this.$props.courseId,
                classID: this.$props.courseId,
                studentNumber: this.$props.studentNumber,
                expName: this.reportInfo.expName
            },
        }).then(resp => {
            this.grade = resp.data.grade
            this.comment = resp.data.comment
        })
    },
    methods: {
        goBackReport() {
            this.$emit('go-back-report-s')
        },
        beforeRemove(file, fileList) {
            return this.$confirm(`确定移除 ${file.name}？`);
        },
        handleExceed(files, fileList) {
            this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
        },
        uploadSuccess() {
            this.$message({
                type: 'success',
                message: '文件上传成功！'
            });
        },
        judge() {
            if (this.beforeUpload === true) {
                this.$message({
                    message: '已超过截止时间，不可提交！',
                    type: 'error'
                });
            }
        },
        viewComment() {
            if (this.grade === "报告尚未提交") {
                this.$message({
                    showClose: true,
                    message: '您尚未上传实验报告，无法查看评语！',
                    type: 'warning'
                })
                return false
            } else if (this.grade === "助教尚未批改") {
                this.$message({
                    showClose: true,
                    message: '您的实验报告尚未被批改，无法查看评语！',
                    type: 'warning'
                })
                return false
            }
            this.isDisabled=false;
        }
    },
    template: `
        <div style="padding-top: 20px">
        <!-- 装饰用 -->
        <el-row :gutter="20">
            <el-col :span="22" :offset="1">
                <div class="new-report"></div>
            </el-col>
        </el-row>
  
        <el-row :gutter="20">
            <el-col :span="24">
                <div>
                    <h1>{{reportInfo.reportName}}</h1>
                </div>             
            </el-col>
        </el-row>
        
        <el-row :gutter="20" >
            <el-col :span="24">
                <div style="width: 90%;padding-left: 5%">
                    <hr style="color: #F2F6FC">
                </div>             
            </el-col>
        </el-row>
        
        <el-row :gutter="0">
            <el-col :span="5" :offset="1">
                <div>
                    <div>
                        <h4 style="display: inline-block">截止日期</h4>
                        ：{{reportInfo.endDate}}
                    </div>
                </div>             
            </el-col>
            <el-col :span="4">
                <div>
                    <div>
                        <h4 style="display: inline-block">得分</h4>
                        ：<h4 style="display: inline-block">{{grade}}</h4>
                    </div>
                </div>             
            </el-col>
            <el-col :span="5">
                <div>
                    <div>
                        <h4 style="display: inline-block">发布时间</h4>
                        ：{{reportInfo.startDate}}
                    </div>
                </div>             
            </el-col>
            <el-col :span="5">
                <div>
                    <div>
                        <h4 style="display: inline-block">文件格式</h4>
                        ：{{reportInfo.reportType}}
                    </div>
                </div>             
            </el-col>
            <el-col :span="3">
                <div>
                    <div>
                        <h4 style="display: inline-block">成绩占比</h4>
                        ：{{reportInfo.weight}}
                    </div>
                </div>             
            </el-col>
        </el-row>
        
        <el-row :gutter="20">
            <el-col :span="24">
                <div style="width: 90%;padding-left: 5%">
                    <hr style="color: #F2F6FC">
                </div>             
            </el-col>
        </el-row>
        
        <el-row :gutter="20">
            <el-col :span="24">
                <div style="height: 260px">
                    <p style="width: 90%;padding-left: 5%">{{reportInfo.reportDescription}}</p>
                </div>             
            </el-col>
        </el-row>
        
        <el-row :gutter="20">
            <el-col :span="20" :offset="2">
                <div style="display: inline-block;margin-right: 10px;float: left;height: 76px;width: 250px" >
                    <el-upload
                        class="upload-demo"
                        ref="upload"
                        :auto-upload="true"
                        :disabled="beforeUpload"
                        action="/SoftwareEngineering/fileServlet?action=uploadFile"
                        :data="{
                                courseID:this.$props.courseId,
                                classID:this.$props.classId,
                                userNumber:this.$props.studentNumber,
                                expname:this.$props.reportInfo.expName
                            }"
                        :on-success="uploadSuccess"
                        :file-list="fileList">
                        <el-button type="primary" @click="judge">点击上传实验报告</el-button>
                    </el-upload>
                </div>
                <div style="display: inline-block;float: right;margin-top: 40px" ><el-button type="info" plain @click="goBackReport">返回</el-button></div>
                <div style="display: inline-block;float: right;margin: 40px 20px 0 0" ><el-popover
                                    placement="bottom"
                                    title="查看评语"
                                    width="400"
                                    trigger="click"
                                    :disabled="isDisabled"
                                    :content="comment">
                                    <el-button @click="viewComment()" type="success" plain slot="reference">查看评语</el-button>
                                </el-popover></div>
            </el-col>
        </el-row>
        
        <!-- 装饰用 -->
        <el-row :gutter="20">
            <el-col :span="22" :offset="1">
                <div class="new-report"></div>
            </el-col>
        </el-row>
        
    </div>
        `
});