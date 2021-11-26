var Notice = Vue.extend({
    props:['noticeList'],
    data() {
        return {

        };
    },
    methods: {
        clickNotice(index) {
            this.$emit('click-notice',index)
        },
        publishAnnouncement(){
            this.$emit('publish-announcement')
        }
    },
    template: `
        <div style="line-height: initial;overflow: hidden;cursor: pointer;padding-top: 13px;" >
            <div style="height:35px;position: relative;margin-bottom: 5px">
                <h2 style="display: inline-block;position: absolute;left: 14px;padding-left: 10px">已发布公告:</h2>
            </div>
             <hr style="color: #C0C4CC;width: 96%;margin-left: 2%;margin-bottom: 6px">
            <div style="height:35px;position: relative;margin: 10px 0 10px 0">
                <el-button type="primary" style="position: absolute;right: 23px" @click="publishAnnouncement">发布新公告</el-button>
            </div>
            <div style="height: 550px;overflow: auto">
                <template v-for="(item,index) in noticeList">
                    <div style="margin: 8px 0 8px 0" @click="clickNotice(index)">
                        
                            <el-row style="border-radius: 4px;background-color: #909399;margin-bottom: 6px;width: 96%;margin-left: 2%">
                                <el-col :span="2">
                                    <div style="padding-top: 5px">
                                        <h3 style="display: inline-block">{{item.title}}</h3>
                                    </div>
                                </el-col>
                                <el-col :span="24"><span style="padding-left: 12px">{{item.content}}</span></el-col>
                                <el-col :span="7" :offset="17">
                                    <div style="padding-bottom: 5px">
                                        <span style="margin-left:30px">
                                            <h5 style="display: inline-block">发布时间：</h5>
                                            {{item.date}}
                                        </span>
                                        <span style="margin-left:5px">
                                            <h5 style="display: inline-block">发布人：</h5>
                                            {{item.issuer}}
                                        </span>
                                    </div>
                                </el-col>
                            </el-row>
                    </div>
                </template>
            </div>
        </div>
        `
});