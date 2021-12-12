var Notice = Vue.extend({
    props:['noticeList'],
    data() {
        return {

        };
    },
    methods: {
        clickNotice(index) {
            this.$emit('click-notice',index)
        }
    },
    template: `
        <div style="line-height: initial;overflow: hidden;cursor: pointer;padding-top: 13px" >
            <transition-group name="el-fade-in-linear">
                <div v-for="(item,index) in noticeList" :key="item.date">
                    <div style="margin: 8px 0 8px 0" @click="clickNotice(index)">
                            <el-row style="border-radius: 4px;background-color: #909399;margin-bottom: 6px;width: 96%;margin-left: 2%">
                                <el-col :span="24">
                                    <div style="padding-top: 5px;margin-bottom: 10px">
                                        <h3 style="display: inline-block">{{item.title}}</h3>
                                    </div>
                                </el-col>
                                <div style="padding-top: 10px">
                                <el-col :span="24"><span style="padding-left: 12px">{{item.content}}</span></el-col>
                                </div>
                                <el-col :span="10" :offset="14">
                                    <div style="padding-bottom: 10px;padding-top: 10px">
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
                </div>
                </transition-group>
        </div>
        `
});