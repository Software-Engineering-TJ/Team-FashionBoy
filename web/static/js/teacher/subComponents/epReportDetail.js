var EpReportDetail = Vue.extend({
    props:['reportInfo'],
    data() {
        return {
            fileList: [],
        };
    },
    methods: {
        withdrawReportDesc(){
            console.log(this.$props.reportInfo)
            console.log('withdrawReportDesc')
            this.$emit('withdraw-report-desc',this.$props.reportInfo.expName,this.$props.reportInfo.reportName)
        },
        goBackReport(){
            this.$emit('go-back-report')
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
            <el-col :span="7">
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
                <div style="height: 310px;overflow: auto">
                    <p style="width: 90%;padding-left: 5%">{{reportInfo.reportDescription}}</p>
                </div>             
            </el-col>
        </el-row>
        
        <el-row :gutter="20">
                <el-col :span="8" :offset="15">
                    <el-button type="danger" plain @click="withdrawReportDesc">撤回此说明</el-button>
                    <el-button type="warning" plain>修改报告信息</el-button>
                    <el-button type="info" plain @click="goBackReport">返回</el-button>
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