var QuestionBank = Vue.extend({
    props: ['attendanceList', 'courseId', 'classId'],
    data() {
        return {
            questionList: [],
            //考勤发布窗口是否可见
            innerVisible: false,
            questionForm: {
                choiceQuestion:"",
                choiceOptionA:"",
                choiceOptionB:"",
                choiceOptionC:"",
                choiceOptionD:"",
                choiceDifficulty:0.0,
                choiceAnswer:"",
                choiceAnalysis:"",
                choiceScore:0.0
            },
            stripe: true,
            currentPage: 1,
            pagesize: 5,
            total: 0,
            currentHeight: 522,
            // staff_id:'',
            tableData: [],
            loading: true,
            dialogVisible: false,

            // 好像是修改用的
            form: {
                // employee_id: '',
                attribute: '',
                newdata: '',
            },
            rowID: '',
            // 查询员工信息
            search_attribute: '',
            search_value: '',
            search_hint: '',

            //修改员工信息
            rewrite_hint: '',

            //新增员工
            dialogEmpAdd: false,
            form_add: {
                job: '',
                name: '',
                phone: '',
                ID_num: '',
            },
            empAdd_hint: '',

            //删除员工
            emp_del_staff_id: '',
            colors: ['#99A9BF', '#F7BA2A', '#FF9900']
        };
    },
    mounted() {
        axios({
            url: '/SoftwareEngineering/instructorServlet?action=getQuestion',
            method: "Post",
            data: {
                courseID: this.courseId,
                classID: this.classId,
            },
        }).then(resp => {
            this.questionList = resp.data
            this.loading = false
        });
    },
    methods: {
        addQuestion() {
            axios({
                url: '/SoftwareEngineering/instructorServlet?action=addQuestion',
                method: "Post",
                data: {
                    choiceQuestion:this.questionForm.choiceQuestion,
                    choiceOption:this.questionForm.choiceOptionA+"|"+this.questionForm.choiceOptionB+"|"+this.questionForm.choiceOptionC+"|"+this.questionForm.choiceOptionD,
                    choiceDifficulty:this.questionForm.choiceDifficulty,
                    choiceAnswer:this.questionForm.choiceAnswer,
                    choiceAnalysis:this.questionForm.choiceAnalysis,
                    choiceScore:Number.parseFloat(this.questionForm.choiceScore)
                },
            }).then(resp => {
                this.$message({
                    message: '添加题目成功！',
                    type: 'success'
                });
                this.innerVisible=false
            });
        },
        handleClose(done) {
            this.$confirm('确认关闭？')
                .then(_ => {
                    this.callOff();
                    done();
                })
                .catch(_ => {
                });
        },

        handleSizeChange(val) {
            this.pagesize = val;
            if (val == 5)
                this.currentHeight = 285;
            else if (val == 10)
                this.currentHeight = 522;
        },


        handleCurrentChange(val) {
            this.currentPage = val;
        },

        filterHandler(value, row, column) {
            const property = column['property'];
            return row[property] === value;
        },
        handleEdit(index, row) {
            console.log(index, row);
        },


        //显示所有的员工
        async showAllStaff() {
            this.$axios.get("/api/Employee/getALLEmployeeInfo", {
                params: {
                    // requester_id:this.requester_ID+'',
                }
            })
                .then(response => {
                    this.tableData = response.data;
                })
                .catch((error) => {
                    console.log(error);
                    alert("请求失败!");
                });
            this.loading = false;
        },

        //显示所有的问题
        async showAllQuestion() {
            this.$axios({
                url: "/testRelease/viewAllQuestion",
                method: "get",
                data: {},
            })
                .then(response => {
                    this.tableData = response.data.data;
                })
                .catch((error) => {
                    console.log(error);
                    alert("请求失败!");
                });
            this.loading = false;
        },


        // 测试
        async modifyRow(rows) {
            this.$router.push({
                path: "/setUpdateIngredient",
                query: {ingre_name: rows.ingre_name, buy_date: rows.buy_date,}
            });
        },

    },
    template: `
     <div style="padding-top: 30px">
        <el-dialog
                              width="30%"
                              title="增加题库"
                              :visible.sync="innerVisible"
                              append-to-body>
                              <el-form :model="questionForm">
                                <el-form-item label="题干" label-width="80px">
                                  <el-input
                                          style="width:270px"
                                          type="textarea"
                                          :rows="4"
                                          placeholder="请输入题干"
                                          v-model="questionForm.choiceQuestion">
                                        </el-input>
                                </el-form-item>
                                <el-form-item label="选项A" label-width="80px">
                                  <el-input v-model="questionForm.choiceOptionA" autocomplete="off" style="width: 80%"></el-input>
                                </el-form-item>
                                <el-form-item label="选项B" label-width="80px">
                                  <el-input v-model="questionForm.choiceOptionB" autocomplete="off" style="width: 80%"></el-input>
                                </el-form-item>
                                <el-form-item label="选项C" label-width="80px">
                                  <el-input v-model="questionForm.choiceOptionC" autocomplete="off" style="width: 80%"></el-input>
                                </el-form-item>
                                <el-form-item label="选项D" label-width="80px">
                                  <el-input v-model="questionForm.choiceOptionD" autocomplete="off" style="width: 80%"></el-input>
                                </el-form-item>
                                <el-form-item label="正确答案" label-width="80px">
                                  <el-input v-model="questionForm.choiceAnswer" autocomplete="off" style="width: 80%"></el-input>
                                </el-form-item>
                                <el-form-item label="解析" label-width="80px">
                                  <el-input
                                          style="width:270px"
                                          type="textarea"
                                          :rows="4"
                                          placeholder="请输入题目解析"
                                          v-model="questionForm.choiceAnalysis">
                                        </el-input>
                                </el-form-item>
                                <el-form-item label="题目分数" label-width="80px">
                                  <el-input v-model="questionForm.choiceScore" autocomplete="off" style="width: 80%"></el-input>
                                </el-form-item>
                                <el-form-item label="题目分数" label-width="80px">
                                  <el-rate
                                    v-model="questionForm.choiceDifficulty"
                                    :colors="colors">
                                  </el-rate>
                                </el-form-item>
                              </el-form>
                              <div slot="footer" class="dialog-footer">
                                <el-button @click="innerVisible = false">取 消</el-button>
                                <el-button type="primary" @click="addQuestion">增加到题库</el-button>
                              </div>
                       </el-dialog>
        <hr style="color: #C0C4CC;width: 94%;margin-left: 3%;margin-bottom: 6px">
        <div style="line-height: 30px">
      <el-card class="box-card" style="margin-top:40px;margin-left:50px;width:1020px;">

        <!-- <el-button type="success" size="medium" style="line-height:20px" @click="addNewQuestion()" class="addEmployee">新增题目</el-button> -->
        <div style="background-color:orange;height:0px;font-size:30px;font-family:KaiTi;"><span style="margin-left:40px;">题库情况整理</span></div>

        <div class="questionbankTable">
          <el-table
            v-loading="loading"
            element-loading-text="拼命加载中"
            element-loading-spinner="el-icon-loading"
            :data="questionList"
            :default-sort = "{prop: 'question_id', order: 'ascending'}"
            style="width: 90%;margin-bottom:20px;margin-left:50px;"
            max-height="1000"
            border
            stripe
          >
            <el-table-column prop="choiceId" label="题目编号" width="150%" align="center" sortable></el-table-column>
            <el-table-column prop="choiceScore" label="分值" width="150%" align="center" sortable></el-table-column>
            <el-table-column prop="choiceQuestion" label="题干"  align="center"></el-table-column>
            <el-table-column prop="choiceAnswer" label="答案" width="190%" align="center"></el-table-column>
            <el-table-column prop="choiceAnalysis" label="解析" width="200%" align="center" sortable></el-table-column>
          </el-table>

          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :page-sizes="[5, 10]"
            :page-size="pagesize"
            layout="total, sizes, prev, pager, next"
            :total="tableData.length"
          >
          </el-pagination>

        </div>

      </el-card>

    </div>
        <div style="display: inline-block;position: absolute;bottom: 30px;right: 30px">
                <el-button type="primary" plain @click="innerVisible = true">扩充题库</el-button>
        </div>
     </div>
    `
})