var SPractice = Vue.extend({
    props: ['classId', 'courseId', 'practiceList'],
    data() {
        return {
            innerVisible: false,
            form: {
                amount: 0,
                endTime: ''
            }
        };
    },
    methods: {
        clickNotice(index) {
            this.$emit('click-notice', index)
        },
        publishPractice() {
            this.innerVisible = true
        },
        releasePractice() {
            window.location.href = "/SoftwareEngineering/pages/answer.html"

        }
    },
    template: `
        <div style="line-height: initial;overflow: hidden;cursor: pointer;padding-top: 13px;background-color: white" >
            <el-dialog
                              width="30%"
                              title="填写对抗练习参数"
                              :visible.sync="innerVisible"
                              append-to-body>
                              <el-form :model="form">
                                <el-form-item label="题目数量" label-width="80px">
                                  <el-input v-model="form.amount" autocomplete="off" style="width: 80%"></el-input>
                                </el-form-item>
                                <el-form-item label="截止时间" label-width="80px">
                                  <el-input v-model="form.endTime" autocomplete="off" style="width: 80%"></el-input>
                                </el-form-item>
                              </el-form>
                              <div slot="footer" class="dialog-footer">
                                <el-button @click="innerVisible = false">取 消</el-button>
                                <el-button type="primary" @click="releasePractice">发 布</el-button>
                              </div>
                       </el-dialog>
            <div style="height:35px;position: relative;margin-bottom: 5px">
                <h2 style="display: inline-block;position: absolute;left: 14px;padding-left: 10px">已发布对抗练习:</h2>
            </div>
             <hr style="color: #C0C4CC;width: 96%;margin-left: 2%;margin-bottom: 6px">
            <div style="height:35px;position: relative;margin: 10px 0 10px 0">
                <el-button type="primary" style="position: absolute;right: 23px" @click="publishPractice">发布对抗练习</el-button>
            </div>
            <div style="height: 550px;overflow: auto">
                <transition-group name="el-fade-in-linear">
                <div v-for="(item,index) in practiceList" :key="item.date">
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
        </div>
        `
})