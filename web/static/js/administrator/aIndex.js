// 局部组件1：权限管理卡片组件
var AuthorityTabs = Vue.extend({
    props: ['studentDataAu', 'teacherDataAu'],
    methods: {
        loadDataAu() {
            this.$emit('load-user-data-au')
        },
        searchStudentAu() {
            this.$emit('search-student-au', this.student)
        },
        searchTeacherAu() {
            this.$emit('search-teacher-au', this.teacher)
        },
        changeStudentDuty(row, studentNumber) {
            console.log(studentNumber);
            this.$confirm('你是否想要更改当前课程下该学生的身份?', '确认', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                if (row.duty === "普通学生") {
                    row.duty = "助教";
                } else {
                    row.duty = "普通学生";
                }
                this.$message({
                    type: 'success',
                    message: '您已成功将该学生的身份改为' + '<' + row.duty + '>'
                });
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消修改'
                });
            });
        },
        changeTeacherDuty(row, teacherNumber) {
            console.log(teacherNumber);
            this.$confirm('你是否想要更改当前课程下该老师的职务?', '确认', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                if (row.duty === '教师') {
                    row.duty = '责任教师';
                } else {
                    row.duty = '教师';
                }
                this.$message({
                    type: 'success',
                    message: '您已成功将该老师的职务修改为' + '<' + row.duty + '>'
                });
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消修改'
                });
            });
        }
    },
    data() {
        return {
            student: {id: null},
            teacher: {id: null}
        };
    },
    template: `
    <el-tabs type="border-card">
    <el-tab-pane label="学生管理">  
       <el-row>
            <el-col :span="8" :offset="16">
                <el-form :inline="true" :model="student" class="demo-form-inline">
                    <el-form-item label="学生学号:">
                        <el-input v-model="student.id"></el-input>
                    </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="searchStudentAu">查询</el-button>
                </el-form-item>
          </el-form>
            </el-col>
       </el-row>          
       <el-row>
            <el-col :span="24">
            <ul class="infinite-list" v-infinite-scroll="loadDataAu" style="overflow:auto;height: 550px;" infinite-scroll-distance=10>
            <li v-for="item in studentDataAu" class="infinite-list-item">
                <el-collapse>
                    <el-collapse-item :title="'学号：'+item.studentNumber+' 姓名：'+item.name" :name="item.studentNumber">
                    <el-table :data=item.info border style="width: 100%">
                    <el-table-column
                      prop="courseId"
                      label="课程编号"
                      width="150">
                    </el-table-column>
                    <el-table-column
                      prop="title"
                      label="课程名"
                      width="150">
                    </el-table-column>
                    <el-table-column
                      prop="classId"
                      label="班级号"
                      width="120">
                    </el-table-column>
                    <el-table-column
                      prop="day"
                      label="日期"
                      width="120">
                    </el-table-column>
                    <el-table-column
                      prop="time"
                      label="上课时间"
                      width="120">
                    </el-table-column>
                    <el-table-column
                      prop="chargingTeacher"
                      label="责任教师"
                      width="120">
                    </el-table-column>
                    <el-table-column
                      prop="teacher"
                      label="教师"
                      width="120">
                    </el-table-column>
                    <el-table-column
                    prop="duty"
                    label="职务"
                    width="120">
                  </el-table-column>
                    <el-table-column
                      label="操作"
                      width="100">
                      <template slot-scope="scope">
                        <el-button @click="changeStudentDuty(scope.row,item.studentNumber)" type="text" size="small">更改权限</el-button>
                      </template>
              </el-table-column>
              </el-table>
                    </el-collapse-item>
                </el-collapse>
            </li>
       </ul>
            </el-col>
        </el-row>                    
    </el-tab-pane>
        <el-tab-pane label="教师管理">
        <el-row>
            <el-col :span="8" :offset="16">
                <el-form :inline="true" :model="teacher" class="demo-form-inline">
                    <el-form-item label="教师工号:">
                        <el-input v-model="teacher.id"></el-input>
                    </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="searchTeacherAu">查询</el-button>
                </el-form-item>
          </el-form>
            </el-col>
       </el-row>
        <el-row>
            <el-col :span="24">
            <ul class="infinite-list" v-infinite-scroll="loadDataAu" style="overflow:auto;;height: 550px;" infinite-scroll-distance=10>
                <li v-for="item in teacherDataAu" class="infinite-list-item">
                    <el-collapse>
                        <el-collapse-item :title="'工号：'+item.teacherNumber+' 姓名：'+item.name" :name="item.teacherNumber">
                        <el-table :data=item.info border style="width: 100%">
                        <el-table-column
                          prop="courseId"
                          label="课程编号"
                          width="150">
                        </el-table-column>
                        <el-table-column
                          prop="title"
                          label="课程名"
                          width="150">
                        </el-table-column>
                        <el-table-column
                          prop="classId"
                          label="班级号"
                          width="120">
                        </el-table-column>
                        <el-table-column
                          prop="day"
                          label="日期"
                          width="120">
                        </el-table-column>
                        <el-table-column
                          prop="time"
                          label="上课时间"
                          width="120">
                        </el-table-column>
                        <el-table-column
                          prop="duty"
                          label="职务"
                          width="120">
                        </el-table-column>
                        <el-table-column
                          label="操作"
                          width="100">
                          <template slot-scope="scope">
                            <el-button @click="changeTeacherDuty(scope.row,item.teacherNumber)" type="text" size="small">更改权限</el-button>
                          </template>
                  </el-table-column>
                  </el-table>
                        </el-collapse-item>
                    </el-collapse>
                </li>
            </ul>
            </el-col>
        </el-row> 
        </el-tab-pane>
    </el-tabs>`
});

// 局部组件2：日历组件
var Calender = Vue.extend({
    template: `
        <el-calendar>
            <!-- 这里使用的是 2.5 slot 语法，对于新项目请使用 2.6 slot 语法-->
            <template slot="dateCell" slot-scope="{date, data}">
              <p :class="data.isSelected ? 'is-selected' : ''">
                {{ data.day.split('-').slice(1).join('-') }} {{ data.isSelected ? '✔️' : ''}}
              </p>
            </template>
        </el-calendar>`
});

// 局部组件3：账号信息组件
var AccountInfoTabs = Vue.extend({
    props: ['studentDataAc', 'teacherDataAc'],
    methods: {
        loadDataAc() {
            this.$emit('load-user-data-ac');
        },
        searchStudentAc() {
            this.$emit('search-student-ac', this.student);
            console.log("seacherStudent被调用了");
        },
        searchTeacherAc() {
            this.$emit('search-teacher-ac', this.teacher);
            console.log("seacherTeacher被调用了");
        }
    },
    data() {
        return {
            student: {id: null},
            teacher: {id: null}
        };
    },
    template: `
    <el-tabs type="border-card">
    <el-tab-pane label="学生信息">  
       <el-row>
            <el-col :span="8" :offset="16">
                <el-form :inline="true" :model="student" class="demo-form-inline">
                    <el-form-item label="学生学号:">
                        <el-input v-model="student.id"></el-input>
                    </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="searchStudentAc">查询</el-button>
                </el-form-item>
          </el-form>
            </el-col>
       </el-row>          
       <el-row>
            <el-col :span="24">
            <ul class="infinite-list" v-infinite-scroll="loadDataAc" style="overflow:auto;height: 550px;" infinite-scroll-distance=10>
            <li v-for="item in studentDataAc" class="infinite-list-item">
                <el-collapse>
                    <el-collapse-item :title="'学号：'+item[0].studentNumber+' 姓名：'+item[0].name" :name="item[0].studentNumber">
                    <el-table :data=item border style="width: 100%">
                    <el-table-column
                      prop="studentNumber"
                      label="学号"
                      width="150">
                    </el-table-column>
                    <el-table-column
                      prop="name"
                      label="姓名"
                      width="120">
                    </el-table-column>
                    <el-table-column
                    prop="sex"
                    label="性别"
                    width="150">
                  </el-table-column>
                    <el-table-column
                      prop="phoneNumber"
                      label="电话"
                      width="120">
                    </el-table-column>
                    <el-table-column
                      prop="email"
                      label="邮箱"
                      width="200">
                    </el-table-column>
                    <el-table-column
                      label="操作"
                      width="100">
                      <template slot-scope="scope">
                        <el-button type="text" size="small">修改</el-button>
                        <el-button type="text" size="small">注销</el-button>
                      </template>
              </el-table-column>
              </el-table>
                    </el-collapse-item>
                </el-collapse>
            </li>
       </ul>
            </el-col>
        </el-row>                    
    </el-tab-pane>
        <el-tab-pane label="教师信息">
        <el-row>
            <el-col :span="8" :offset="16">
                <el-form :inline="true" :model="teacher" class="demo-form-inline">
                    <el-form-item label="教师工号:">
                        <el-input v-model="teacher.id"></el-input>
                    </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="searchTeacherAc">查询</el-button>
                </el-form-item>
          </el-form>
            </el-col>
       </el-row>
        <el-row>
            <el-col :span="24">
            <ul class="infinite-list" v-infinite-scroll="loadDataAc" style="overflow:auto;;height: 550px;" infinite-scroll-distance=20>
                <li v-for="item in teacherDataAc" class="infinite-list-item">
                    <el-collapse>
                        <el-collapse-item :title="'工号：'+item[0].teacherNumber+' 姓名：'+item[0].name" :name="item[0].teacherNumber" name="1">
                        <el-table :data=item border style="width: 100%">
                        <el-table-column
                      prop="teacherNumber"
                      label="工号"
                      width="150">
                    </el-table-column>
                    <el-table-column
                      prop="name"
                      label="姓名"
                      width="120">
                    </el-table-column>
                    <el-table-column
                    prop="sex"
                    label="性别"
                    width="150">
                  </el-table-column>
                    <el-table-column
                      prop="phoneNumber"
                      label="电话"
                      width="120">
                    </el-table-column>
                    <el-table-column
                      prop="email"
                      label="邮箱"
                      width="200">
                    </el-table-column>
                    <el-table-column
                      label="操作"
                      width="100">
                      <template slot-scope="scope">
                        <el-button type="text" size="small">修改</el-button>
                        <el-button type="text" size="small">注销</el-button>
                      </template>
              </el-table-column>
                  </el-table>
                        </el-collapse-item>
                    </el-collapse>
                </li>
            </ul>
            </el-col>
        </el-row> 
        </el-tab-pane>
    </el-tabs>`
});

// Vue实例
var vm = new Vue({
    el: "#box",
    data() {
        return {
            // 动态组件切换
            changeComponents: 'calender',
            // 抽屉是否显示
            drawerVisible: false,
            // 抽屉弹出方向
            drawerDirection: 'ltr',
            // 头像路径
            squareUrl: 'https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png',
            // 管理员信息对话框是否可见
            dialogFormVisible: false,
            // 学生信息对话框是否可见
            studentDialogFormVisible: false,
            // 教师信息对话框是否可见
            teacherDialogFormVisible: false,
            // 管理员实体
            administrator: {
                id: '',
                name: '陈荣',
                teacherNumber: '239784',
                phoneNumber: '18886806666',
                email: 'chengrong@163.com',
            },
            // 管理员临时实体，作为表单修改对象
            administratorChange: {},
            // 添加学生账号的表单属性
            studentForm: {
                name: '',
                sex: '',
                studentNumber: '',
                phoneNumber: '',
                email: ''
            },
            // 添加教师账号的表单属性
            teacherForm: {
                name: '',
                sex: '',
                teacherNumber: '',
                phoneNumber: '',
                email: ''
            },
            // 对话框中表单标签的宽度
            formLabelWidth: '120px',
            // 学生账号列表
            studentListAc: [],
            // 教师账号列表
            teacherListAc: [],
            // 保存数据用教师账号列表
            teacherTempListAc: [],
            // 学生权限列表
            studentListAu: [],
            // 教师权限列表
            teacherListAu: [],
            // 页头logo
            logo: '../../static/img/logo2.png'
        };
    },
    methods: {
        // 打开折叠导航栏触发
        handleOpenNavMenu(key, keyPath) {
            console.log(key);
            // this.$data.activeMenu = key.toString();
        },
        // 关闭折叠导航栏触发
        handleCloseNavMenu(key, keyPath) {
            console.log(key, keyPath);
        },
        // 关闭用户信息抽屉触发
        handleCloseDrawer(done) {
            this.$confirm('确认关闭？')
                .then(_ => {
                    done();
                })
                .catch(_ => {
                });
        },
        // 选择对应的导航栏项触发
        selectMenuItem(key, keyPath) {
            switch (key) {
                case "1":
                    this.$data.changeComponents = "Calender"
                    break;
                case "3":
                    this.$data.changeComponents = "AuthorityTabs"
                    break;
                case "2-2":
                    this.$data.changeComponents = "AccountInfoTabs"
                    break;
                case "2-1-1":
                    this.$data.studentDialogFormVisible = true;
                    break;
                case "2-1-2":
                    this.$data.teacherDialogFormVisible = true;
                    break;
                default:
                    break;
            }
        },
        // 改变管理员信息
        changeAdminInfo() {
            this.administrator = JSON.parse(JSON.stringify(this.$data.administratorChange));
            // axios.psot('http://canvas.tongji.edu.cn', this.$data.administratorChange);
            this.dialogFormVisible = false;
        },
        //切换管理员信息改变页面的状态
        switchAdminDialogStatus() {
            if (this.dialogFormVisible === false) {
                this.administratorChange = JSON.parse(JSON.stringify(this.administrator));
                this.dialogFormVisible = true;
            } else {
                this.dialogFormVisible = false;
            }
        },
        // 加载学生和老师账号的信息，滚动滚动条触发
        loadDataAc() {
            this.studentListAc.push([{
                name: '王文炯',
                sex: '男',
                studentNumber: '1953281',
                phoneNumber: '18586722001',
                email: '982567656@qq.com'
            }]),
                this.teacherListAc.push([{
                    name: '黄杰',
                    sex: '男',
                    teacherNumber: '2300256',
                    phoneNumber: '18586722001',
                    email: 'huangjie@163.com'
                }])
        },
        // 加载学生和老师权限的信息，滚动滚动条触发
        loadDataAu() {
            this.studentListAu.push({
                studentNumber: '1953281',
                name: '王文炯',
                info: [{
                    courseId: '144567',
                    title: '计算机网络实验',
                    classId: '1',
                    day: '周一',
                    time: '1',
                    chargingTeacher: '金伟祖',
                    teacher: '金伟祖',
                    duty: '普通学生'
                }, {
                    courseId: '144579',
                    title: '计算机组成原理实验',
                    classId: '2',
                    day: '周二',
                    time: '3',
                    chargingTeacher: '张晶',
                    teacher: '张晶',
                    duty: '助教'
                }]
            }),
                this.teacherListAu.push({
                    teacherNumber: '2021313',
                    name: '黄杰',
                    info: [{
                        courseId: '144579',
                        title: '计算机组成原理实验',
                        day: '周二',
                        time: '3',
                        classId: '2',
                        duty: '普通教师'
                    }, {
                        courseId: '143479',
                        title: '软件工程实验',
                        day: '周二',
                        time: '3',
                        classId: '2',
                        duty: '责任教师'
                    }]
                })
        },
        // 搜索学生账号
        searchStudentAc(student) {
            // 清空学生列表
            this.studentListAc.length = 0;
            // 查找学生
            axios({
                url: '/SoftwareEngineering/administrationServlet?action=getTeacherByTeacherNumber',
                method: "Post",
                data: {
                    instructorNumber: teacher.id,
                },
            }).then(resp => {
                console.log(resp.data)
                // 重新添加教师
                this.teacherListAc.push([{
                    name: resp.data.name,
                    sex: resp.data.sex,
                    teacherNumber: resp.data.instructorNumber,
                    email: resp.data.email,
                    phoneNumber: resp.data.phoneNumber
                }])
            })
        },
        // 搜索教师账号
        searchTeacherAc(teacher) {
            // 清空教师列表
            this.teacherTempListAc = JSON.parse(JSON.stringify(this.teacherTempListAc));
            this.teacherListAc.length = 0;
            // 查找教师
            axios({
                url: '/SoftwareEngineering/administrationServlet?action=getTeacherByTeacherNumber',
                method: "Post",
                data: {
                    instructorNumber: teacher.id,
                },
            }).then(resp => {
                console.log(resp.data)
                // 重新添加教师
                this.teacherListAc.push([{
                    name: resp.data.name,
                    sex: resp.data.sex,
                    teacherNumber: resp.data.instructorNumber,
                    email: resp.data.email,
                    phoneNumber: resp.data.phoneNumber
                }])
            })
        },
        searchStudentAu(student) {
            // 清空学生列表
            this.studentListAc.length = 0;
            // 查找学生
            axios({
                url: '/SoftwareEngineering/administrationServlet?action=getTakesByStudentNumber',
                method: "Post",
                data: {
                    studentNumber: student.id,
                },
            }).then(resp => {
                console.log(resp.data)
                // 重新添加学生
                this.studentListAc.push([{
                    name: '王文炯',
                    sex: '男',
                    studentNumber: '1953281',
                    email: '982567656@qq.com',
                    phoneNumber: '18586722001'
                }])
            })
        },
        // 搜索教师账号
        searchTeacherAu(teacher) {
            // 清空学生列表
            this.teacherListAu.length = 0;
            // 查找学生
            axios({
                url: '/SoftwareEngineering/administrationServlet?action=getTeachesByTeacherNumber',
                method: "Post",
                data: {
                    instructorNumber: teacher.id,
                },
            }).then(resp => {
                console.log(resp.data)
                // 重新添加教师
                this.teacherListAu.push([{
                    id: 1,
                    name: '黄杰',
                    sex: '男',
                    teacherNumber: '2300256',
                    email: 'huangjie@163.com',
                    phoneNumber: '18586722001'
                }])
            })
        },
        // 添加新学生
        addNewStudent() {
            axios({
                url: '/SoftwareEngineering/administrationServlet?action=createStudent',
                method: "Post",
                data: {
                    studentNumber: this.studentForm.studentNumber,
                    name: this.studentForm.name,
                    sex: this.studentForm.sex,
                    phoneNumber: this.studentForm.phoneNumber,
                    email: this.studentForm.email
                },
            }).then(resp => {
                if (resp.data.msg === 'success') {
                    this.$message({
                        showClose: true,
                        message: '添加学生成功！',
                        type: 'success'
                    });
                    for (let key in this.studentForm) {
                        this.studentForm[key] = ''
                    }
                    this.studentDialogFormVisible = false
                } else {
                    this.$message({
                        showClose: true,
                        message: '该学号已存在！',
                        type: 'error'
                    })
                }
            });

        },
        // 添加新教师
        addNewTeacher() {
            axios({
                url: '/SoftwareEngineering/administrationServlet?action=createTeacher',
                method: "Post",
                data: {
                    instructorNumber: this.teacherForm.teacherNumber,
                    name: this.teacherForm.name,
                    sex: this.teacherForm.sex,
                    phoneNumber: this.teacherForm.phoneNumber,
                    email: this.teacherForm.email
                },
            }).then(resp => {
                if (resp.data.msg === 'success') {
                    this.$message({
                        showClose: true,
                        message: '添加新教师账号成功！',
                        type: 'success'
                    });
                    for (let key in this.teacherForm) {
                        this.teacherForm[key] = ''
                    }
                    this.teacherDialogFormVisible = false
                } else {
                    this.$message({
                        showClose: true,
                        message: '该工号已存在！',
                        type: 'error'
                    })
                }
            });
        }
    },
    components: {
        // 日历组件
        Calender,
        // 权限管理卡组件
        AuthorityTabs,
        // 账号信息组件
        AccountInfoTabs
    }
})

// 获取头像框
var avatarBlock = document.getElementById("avatarBlock");

// 点击头像弹出个人信息
avatarBlock.onclick = function () {
    vm.$data.drawerVisible = true;
}