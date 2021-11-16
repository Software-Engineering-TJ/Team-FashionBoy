var Notice = Vue.extend({
    props:['noticeList'],
    data() {
        return {

        };
    },
    methods: {
        clickNotice(index) {
            console.log("clickNotice")
            this.$emit('click-notice',index)
        }
    },
    template: `
        <div style="line-height: initial;overflow: hidden;cursor: pointer;padding-top: 13px" >
            <template v-for="(item,index) in noticeList">
                <div style="margin: 8px 0 8px 0" @click="clickNotice(index)">
                    <hr style="color: #C0C4CC;width: 96%;margin-left: 2%;margin-bottom: 6px">
                        <el-row style="border-radius: 4px;background-color: #909399;margin-bottom: 6px;width: 96%;margin-left: 2%">
                            <el-col :span="2">
                                <h3 style="display: inline-block">{{item.title}}</h3>
                            </el-col>
                            <el-col :span="24"><span style="padding-left: 12px">{{item.content}}</span></el-col>
                            <el-col :span="7" :offset="17"><span style="padding-right:10px">时间：{{item.date}}</span></el-col>
                        </el-row>
                </div>
            </template>
        </div>
        `
});