// Vue实例
var vm = new Vue({
    el: "#box",
    mounted: function () {
        axios({
            url: '/SoftwareEngineering/userServlet?action=getUserInfo',
            method: "Get",
        }).then(resp => {
            this.user.name = resp.data.name
            this.user.sex = resp.data.sex
            this.user.email = resp.data.email
            this.user.phoneNumber = resp.data.phoneNumber
            this.user.instructorNumber = resp.data.userNumber
        })
    },
    data() {
        return {
            // 页头logo
            logo: '../../static/img/logo2.png',
            // 课程编号
            courseID: '',
            // 课程名
            title: '',
            // 班级号
            classID: '',
            // 头像路径
            squareUrl: 'https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png',
            // 动态组件切换
            changeComponents: 'Calender',
            // 用户信息抽屉是否显示
            drawerVisible: false,
            // 课程信息抽屉是否显示
            drawerCourse: false,
            // 抽屉弹出方向
            drawerDirection: 'ltr',
            // 课程抽屉弹出方向
            drawerDirectionCourse: 'rtl',
            // 导航栏默认选中
            defaultActive: '1',
            // 用户信息对话框是否可见
            userDialogFormVisible: false,
            // 发布通知的对话框
            annoDialogFormVisible: false,
            // 发布实验的对话框
            experimentDialogFormVisible: false,
            // 班级信息的弹窗是否可见
            sectionDialogFormVisible:false,
            // 公告表单
            annoForm: {
                title: '',
                content: '',
                date: '',
            },
            // 实验表单
            experimentForm: {
                title: '',
                desc: '',
                date1: '',
                date2: '',
                region: '',
                fileList: []
            },
            // 课程表单
            courseForm: {
                title: '',
                courseID: '',
                startDate: '',
                endDate: '',
                experimentForm: [
                    {
                        title: '',
                        difficulty: 0,
                        priority: 0,
                        weight: 0
                    }
                ],
                experimentWeight: 0,
                attendanceWeight: 0,
                practiceWeight: 0
            },
            //实验表单验证规则
            rules: {
                desc: [
                    {required: true, message: '请填写本实验项目相关描述信息', trigger: 'change'}
                ],
                date1: [
                    {type: 'date', required: true, message: '请选择日期', trigger: 'change'}
                ],
                date2: [
                    {type: 'date', required: true, message: '请选择时间', trigger: 'change'}
                ],
                region: [
                    {required: true, message: '请选择发布范围', trigger: 'change'}
                ]
            },
            // 用户实体
            user: {
                name: '',
                sex: '',
                instructorNumber: '',
                phoneNumber: '',
                email: '',
            },
            // 管理员临时实体，作为表单修改对象
            userChange: {},
            // 对话框中表单标签的宽度
            formLabelWidth: '120px',
            // 学生课程表
            courseList: [
                {
                    title: '计算机网络实验',
                    courseID: '23654',
                    duty: '责任教师、教师',
                    classID: '1'
                },
                {
                    title: '计算机组成原理实验',
                    courseID: '23653',
                    duty: '教师',
                    classID: '2'
                },
            ],
            // 课程相关公告信息
            noticeList: [
                {
                    title: '公告一',
                    content: '请同学们完成实验一',
                    issuer: '黄杰',
                    date: '2021.11.1'
                }, {
                    title: '公告二',
                    content: '请同学们完成实验二',
                    issuer: '张晶',
                    date: '2021.11.15'
                }, {
                    title: '公告三',
                    content: '请同学们完成实验三',
                    issuer: '金伟祖',
                    date: '2021.12.12'
                }
            ],
            // 责任教师管理课程信息
            courseInfoList:[
                {
                    title:'计算机网络实验',
                    courseID:'12345',
                    date:'2021.9.1-2022.1.14',

                },
                {
                    title:'计算机网络实验',
                    courseID:'12345',
                    date:'2021.9.1-2022.1.14',

                },
                {
                    title:'计算机网络实验',
                    courseID:'12345',
                    date:'2021.9.1-2022.1.14',

                }
            ],
            sectionInfoList:[
                {
                    classID:'1',
                    sectionDate:'周三',
                    sectionTime:'第二节课',
                    instructorID:'23034',
                    instructorName:'金伟祖',
                },
                {
                    classID:'2',
                    sectionDate:'周三',
                    sectionTime:'第五节课',
                    instructorID:'23035',
                    instructorName:'夏波涌'
                },
                {
                    classID:'3',
                    sectionDate:'周四',
                    sectionTime:'第四节课',
                    instructorID:'23034',
                    instructorName:'金伟祖'
                }
            ]
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
                    this.changeComponents = 'Calender'
                    this.defaultActive = '1'
                    break;
                case "2":
                    this.drawerCourse = true
                    this.defaultActive = '2'
                    break;
                case "3-1":
                    this.changeComponents = 'OpenCourse'
                    this.defaultActive = '3-1'
                    break;
                case "3-2":
                    this.changeComponents ='CourseOverview'
                    this.defaultActive = '3-2'
                    break;
                default:
                    break;
            }
        },
        // 改变管理员信息
        changeUserInfo() {
            this.user = JSON.parse(JSON.stringify(this.$data.userChange));
            // axios.psot('http://canvas.tongji.edu.cn', this.$data.administratorChange);
            this.userDialogFormVisible = false;
        },
        //切换管理员信息改变页面的状态
        switchUserDialogStatus() {
            if (this.userDialogFormVisible === false) {
                this.userChange = JSON.parse(JSON.stringify(this.$data.user));
                this.userDialogFormVisible = true;
            } else {
                this.userDialogFormVisible = false;
            }
        },
        // 加载该学生所有课程,用户点击“我的课程”时触发
        loadCourse() {

        },
        // 加载课程相关信息
        loadCourseInformation(courseID, title, classID) {
            // 通过课程ID来加载课程信息
            this.changeComponents = 'Course'
            this.courseID = courseID
            this.title = title
            this.classID = classID
            console.log(courseID)
        },
        goBack() {
            this.defaultActive = '1'
            this.changeComponents = Calender
        },
        publishAnnouncement() {
            console.log("tIndex-publishAnnouncement被调用了")
            this.annoDialogFormVisible = true
        },
        submitExperimentForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    alert('submit!');
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        releaseExperiment(row) {
            this.experimentDialogFormVisible = true
        },
        addExperiment() {
            console.log('addExperiment被调用了')
            this.courseForm.experimentForm.push(
                {
                    title: '',
                    difficulty: 0,
                    priority: 0,
                    weight: 0
                }
            )
        },
        removeDomain(index) {
            console.log(index)
            if (index !== -1) {
                this.courseForm.experimentForm.splice(index, 1)
            }
        },
        getSectionInfo(courseID){
            console.log(courseID)
            this.sectionDialogFormVisible=true
        }
    },
    components: {
        // 日历组件
        Calender,
        // 课程组件
        Course,
        // 开设课程组件
        OpenCourse,
        // 课程概况组件
        CourseOverview
    }
})

// 获取头像框
var avatarBlock = document.getElementById("avatarBlock");

// 点击头像弹出个人信息
avatarBlock.onclick = function () {
    vm.drawerVisible = true;
}