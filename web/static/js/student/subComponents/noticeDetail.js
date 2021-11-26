var NoticeDetail = Vue.extend({
    props: ['noticeInfo'],
    data() {
        return {};
    },
    methods: {
        goBack(){
            this.$emit('go-back-notice')
        }
    },
    template: `
     <div style="padding-top: 20px">
        <el-row :gutter="20">
            <el-col :span="22" :offset="1">
                <div class="report" style="min-height: 36px"></div>
            </el-col>
        </el-row>
        <el-row :gutter="20">
            <el-col :span="24">
                <div>
                    <h1>{{noticeInfo.title}}</h1>
                </div>             
            </el-col>
        </el-row>
        <el-row :gutter="20">
            <el-col :span="24">
                <div style="height: 340px">
                    <p style="width: 90%;padding-left: 5%">{{noticeInfo.content}}</p>
                </div>             
            </el-col>
        </el-row>
        <el-row :gutter="20">
            <el-col :span="24">
                <div>
                    <div style="width: 30%;padding-left: 70%">
                        <h4 style="display: inline-block">发布时间</h4>
                        ：{{noticeInfo.date}}
                    </div>
                </div>             
            </el-col>
        </el-row>
        <el-row :gutter="20">
            <el-col :span="2" :offset="22">
                <el-button type="info" plain @click="goBack">返回</el-button>
            </el-col>
        </el-row>
        <el-row :gutter="20">
            <el-col :span="22" :offset="1">
                <div class="report" style="min-height: 36px"></div>
            </el-col>
        </el-row>
    </div>
    `
});