var ExperimentalReport = Vue.extend({
    props:['reportList'],
    data() {
        return {

        };
    },
    methods: {
        clickReport(index){
            this.$emit('click-report',index)
        },
        checkSubmission(){
            console.log('checkSubmission被调用了')
        },
        releaseReportDesc(){
            this.$emit('release-report-desc')
        }
    },
    template: `
         <div style="padding: 20px">
                <el-row :gutter="0" style="margin-bottom: 0px">
                    <el-col :span="1">
                        <div class="grid-content bg-purple" style="border-radius: 0;background-color: white"></div>
                    </el-col>
                    <el-col :span="4">
                        <div class="grid-content bg-purple" style="border-radius: 0;background-color: white;line-height: 36px">实验报告名</div>
                    </el-col>
                    <el-col :span="5">
                        <div class="grid-content bg-purple" style="border-radius: 0;background-color: white"></div>
                    </el-col>
                    <el-col :span="2">
                        <div class="grid-content bg-purple" style="border-radius: 0;line-height: 36px;background-color: white">文件格式</div>
                    </el-col>
                    <el-col :span="2">
                        <div class="grid-content bg-purple" style="border-radius: 0;line-height: 36px;background-color: white"></div>
                    </el-col>
                    <el-col :span="2">
                        <div class="grid-content bg-purple" style="border-radius: 0;line-height: 36px;background-color: white">截止日期</div>
                    </el-col>
                    <el-col :span="2">
                        <div class="grid-content bg-purple" style="border-radius: 0;line-height: 36px;background-color: white"></div>
                    </el-col>
                    <el-col :span="2">
                        <div class="grid-content bg-purple" style="border-radius: 0;line-height: 36px;background-color: white">分数占比</div>
                    </el-col>
                    <el-col :span="4">
                        <div class="grid-content bg-purple" style="border-radius: 0;line-height: 36px;background-color: white">操作</div>
                    </el-col>   
                </el-row>
                <hr style="margin-bottom: 5px;color: #C0C4CC">
            <template v-for="(item,index) in reportList" style="color: black">
                <div style="cursor: pointer;" @click="clickReport(index)">
                    <el-row :gutter="0" style="margin-bottom: 5px">
                        <el-col :span="1">
                            <div class="my-bg" style="border-radius: 4px 0 0 4px;line-height: 37px">
                                <i class="el-icon-document-checked" style="font-size: 30px;padding-top: 10px"></i>
                            </div>
                        </el-col>
                        <el-col :span="4">
                            <div class="my-bg" style="line-height: 48px">{{item.reportName}}</div>
                        </el-col>
                        <el-col :span="5">
                            <div class="my-bg"></div>
                        </el-col>
                        <el-col :span="2">
                            <div class="my-bg">{{item.reportType}}</div>
                        </el-col>
                        <el-col :span="1">
                            <div class="my-bg"></div>
                        </el-col>
                        <el-col :span="5">
                            <div class="my-bg">{{item.endDate}}</div>
                        </el-col>
                        <el-col :span="2">
                            <div class="my-bg">{{item.weight}}</div>
                        </el-col>
                        <el-col :span="4">
                            <div class="my-bg" style="border-radius: 0 4px 4px 0"><el-button @click.stop="checkSubmission" type="text">查看提交情况</el-button></div>
                        </el-col>
                    </el-row>
                </div>
            </template>
            <div style="display: inline-block;position: absolute;bottom: 30px;right: 30px">
                <el-button type="primary" plain @click="releaseReportDesc">上传实验报告说明</el-button>
            </div>
        </div>
        `
});