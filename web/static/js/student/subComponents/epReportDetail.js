var EpReportDetail = Vue.extend({
    props:['reportInfo','studentNumber', 'courseName', 'courseId', 'classId'],
    data() {
        return {
            fileList: []
        };
    },
    methods: {
        goBackReport(){
            this.$emit('go-back-report')
        },
        beforeRemove(file, fileList) {
            return this.$confirm(`确定移除 ${ file.name }？`);
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
            <el-col :span="3">
                <div>
                    <div>
                        <h4 style="display: inline-block">得分</h4>
                        ：<h3 style="display: inline-block">90</h3:sty>
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
            <el-col :span="5">
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
                <div style="display: inline-block;margin-right: 10px;float: left;height: 76px;width: 250px">
                    <el-upload
                        class="upload-demo"
                        ref="upload"
                        :auto-upload="true"
                        action="/SoftwareEngineering/fileServlet?action=uploadFile"
                        :data="{
                                courseID:this.$props.courseId,
                                classID:this.$props.classId,
                                userNumber:this.$props.studentNumber,
                                expname:this.$props.reportInfo.expName
                            }"
                        :on-success="uploadSuccess"
                        :file-list="fileList">
                        <el-button type="primary">点击上传实验报告</el-button>
                    </el-upload>
                </div>
                <div style="display: inline-block;float: right;margin-top: 40px" ><el-button type="info" plain @click="goBackReport">返回</el-button></div>
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