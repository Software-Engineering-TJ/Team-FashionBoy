var OpenCourse = Vue.extend({
    props: ['courseForm','formLabelWidth'],
    data() {
        return {};
    },
    methods: {
        addExperiment(){
            this.$emit('add-experiment')
        },
        removeDomain(index){
            console.log('removeDomain')
            console.log(index)
            this.$emit('remove-domain',index)
        }
    },
    template: `
     <div style="overflow: auto;background-color: white;padding-top: 30px">
         <div style="text-align: left;padding:0 0 20px 30px">
            <h1>开设课程</h1>
         </div>
         <div style="width: 90%;height:600px;padding-left: 100px;text-align: left">
             <el-form label-position="left" :model="courseForm">
                <div style="width: 90%;"><el-divider content-position="left"><h3>基础信息</h3></el-divider></div>
                <el-form-item label="课程名称" :label-width="formLabelWidth">
                    <el-input style="width: 50%" v-model="courseForm.title" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="课程时间" :label-width="formLabelWidth">
                    <el-col :span="5">
                      <el-date-picker type="date" placeholder="选择开课日期" v-model="courseForm.startDate" style="width: 100%;"></el-date-picker>
                    </el-col>
                    <el-col class="line" :span="1"><div style="text-align: center;display: inline-block;width: 100%">-</div></el-col>
                    <el-col :span="5">
                      <el-date-picker type="date" placeholder="选择结课日期" v-model="courseForm.endDate" style="width: 100%;"></el-date-picker>
                    </el-col>
                </el-form-item>

                <div style="width: 90%;"><el-divider content-position="left"><h3>课程大纲</h3></el-divider></div>
                <template v-for="(item,index) in courseForm.experimentForm">
                    <el-card style="width: 50%;margin-bottom: 20px" shadow="hover">
                        <div slot="header" class="clearfix">
                            <i>实验项目{{index+1}}</i>
                            <el-button style="float: right; padding: 3px 0" type="text" @click.prevent="removeDomain(index)">删除</el-button>
                        </div>
                      <el-form-item label="实验项目" :label-width="formLabelWidth">
                            <el-input style="width: 50%" v-model="item.title" placeholder="请填写实验项目名称"></el-input>
                      </el-form-item>
                        <el-form-item label="教学优先级" :label-width="formLabelWidth">
                            <div style="padding-top: 10px">
                                <el-rate
                                        v-model="item.priority"
                                        show-score
                                        text-color="#ff9900"
                                        score-template="{value}">
                                </el-rate>
                            </div>
                        </el-form-item>
                        <el-form-item label="难度" :label-width="formLabelWidth">
                            <div style="padding-top: 10px">
                                <el-rate
                                        v-model="item.difficulty"
                                        show-score
                                        text-color="#ff9900"
                                        score-template="{value}">
                                </el-rate>
                            </div>
                        </el-form-item>
                        <el-form-item label="项目占比" :label-width="formLabelWidth">
                            <el-slider
                                    style="width: 50%"
                                    v-model="item.weight"
                                    :step="5"
                            >
                            </el-slider>
                        </el-form-item>
                    </el-card>
                </template>
                <el-row>
                    <el-col :span="2" :offset="18">
                        <el-button @click="addExperiment">添加实验项目</el-button>
                    </el-col>
                </el-row>

                <div style="width: 90%;"><el-divider content-position="left"><h3>成绩占比</h3></el-divider></div>
                <el-form-item label="考勤" :label-width="formLabelWidth">
                    <el-slider
                            style="width: 50%"
                            v-model="courseForm.attendanceWeight"
                            :step="5"
                            >
                    </el-slider>
                </el-form-item>
                <el-form-item label="对抗练习" :label-width="formLabelWidth">
                    <el-slider
                            style="width: 50%"
                            v-model="courseForm.practiceWeight"
                            :step="5"
                            >
                    </el-slider>
                </el-form-item>
            </el-form>
            <div class="dialog-footer" style="text-align: right;width: 90%;padding-bottom: 40px">
                <el-button type="primary" @click="courseDialogFormVisible = false">申 请</el-button>
            </div>
        </div>
        
    </div>
    `
})