var QuestionBank = Vue.extend({
    props: ['attendanceList', 'courseId', 'classId'],
    data() {
        return {
            //考勤发布窗口是否可见
            innerVisible: false,
            questionForm:{},
            stripe:true,
            currentPage:1,
            pagesize:5,
            total:0,
            currentHeight:522,
            // staff_id:'',
            tableData:[],
            loading: true,
            dialogVisible: false,

            // 好像是修改用的
            form: {
                // employee_id: '',
                attribute: '',
                newdata:'',
            },
            rowID:'',
            // 查询员工信息
            search_attribute:'',
            search_value:'',
            search_hint:'',

            //修改员工信息
            rewrite_hint:'',

            //新增员工
            dialogEmpAdd:false,
            form_add:{
                job:'',
                name:'',
                phone:'',
                ID_num:'',
            },
            empAdd_hint:'',

            //删除员工
            emp_del_staff_id:'',
        };
    },
    methods: {
        addQuestion(){},
        handleClose(done) {
            this.$confirm('确认关闭？')
                .then(_ => {
                    this.callOff();
                    done();
                })
                .catch(_ => {});
        },

        handleSizeChange(val) {
            this.pagesize = val;
            if(val==5)
                this.currentHeight = 285;
            else if(val==10)
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
        async  showAllStaff(){
            this.$axios.get("/api/Employee/getALLEmployeeInfo", {
                params: {
                    // requester_id:this.requester_ID+'',
                }
            })
                .then(response=> {
                    this.tableData=response.data;
                })
                .catch((error) => {
                    console.log(error);
                    alert("请求失败!");
                });
            this.loading = false;
        },

        //显示所有的问题
        async  showAllQuestion(){
            this.$axios({
                url: "/testRelease/viewAllQuestion",
                method: "get",
                data: {},
            })
                .then(response=> {
                    this.tableData=response.data.data;
                })
                .catch((error) => {
                    console.log(error);
                    alert("请求失败!");
                });
            this.loading = false;
        },



        // 测试
        async modifyRow(rows) {
            this.$router.push({path: "/setUpdateIngredient",query:{ingre_name:rows.ingre_name,buy_date:rows.buy_date,}});
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
                                <el-form-item label="考勤名称" label-width="80px">
                                  <el-input v-model="questionForm.attendanceName" autocomplete="off" style="width: 80%"></el-input>
                                </el-form-item>
                                <el-form-item label="截止时间" label-width="80px">
                                   <el-time-picker placeholder="选择时间" v-model="questionForm.endTime"
                                            style="width: 80%;"></el-time-picker>
                                 </el-form-item>
                              </el-form>
                              <div slot="footer" class="dialog-footer">
                                <el-button @click="innerVisible = false">取 消</el-button>
                                <el-button type="primary" @click="addQuestion">发 布</el-button>
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
            :data="tableData.slice((currentPage - 1) * pagesize,currentPage * pagesize)"
            :default-sort = "{prop: 'question_id', order: 'ascending'}"
            @sort-change="sortChange"
            style="width: 90%;margin-bottom:20px;margin-left:50px;"
            max-height="1000"
            border
            stripe
          >
            <el-table-column prop="question_id" label="题目编号" width="150%" align="center" sortable></el-table-column>
            <el-table-column prop="question_type" label="题型" width="150%" align="center" column-key="question_type" :filters="[{text: '选择题', value: '选择题'}, 
            {text: '填空题', value: '填空题'}, {text: '判断题', value: '判断题'}, ]"
        :filter-method="filterHandler"></el-table-column>
            <el-table-column prop="question_score" label="分值" width="150%" align="center" sortable></el-table-column>
            <el-table-column prop="question_content" label="题干"  align="center"></el-table-column>
            <el-table-column prop="question_answer" label="答案" width="190%" align="center"></el-table-column>
            <el-table-column prop="question_analysis" label="解析" width="200%" align="center" sortable></el-table-column>
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