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
            this.user.studentNumber = resp.data.userNumber
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
            // 密码弹框是否可见
            passwordFormVisible: false,
            // 验证码弹窗是否可见
            verifyFromVisible: false,
            verificationCode: '',
            password: {
                // 新密码
                newPassword: '',
                // 旧密码
                originPassword: '',
            },
            // 抽屉弹出方向
            drawerDirection: 'ltr',
            // 课程抽屉弹出方向
            drawerDirectionCourse: 'rtl',
            // 导航栏默认选中
            defaultActive: '1',
            // 用户信息对话框是否可见
            userDialogFormVisible: false,
            // 用户实体
            user: {
                name: '',
                sex: '',
                studentNumber: '',
                phoneNumber: '',
                email: '',
            },
            //某门课程下的学生职责
            duty:'',
            // 管理员临时实体，作为表单修改对象
            userChange: {},
            // 对话框中表单标签的宽度
            formLabelWidth: '120px',
            // 学生课程表
            courseList: [],
            // 课程相关公告信息
            noticeList: []
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
                    this.changeComponents = "Calender"
                    this.defaultActive = '1'
                    break;
                case "2":
                    this.drawerCourse = true
                    this.loadCourse()
                    this.defaultActive = '2'
                    break;
                default:
                    break;
            }
        },
        // 改变管理员信息
        changeUserInfo() {
            this.user = JSON.parse(JSON.stringify(this.$data.userChange));
            axios({
                url: '/SoftwareEngineering/userServlet?action=changeUserInfo',
                method: "Post",
                data: {
                    userNumber: this.user.studentNumber,
                    email: this.user.email,
                    phoneNumber: this.user.phoneNumber
                },
            }).then(resp => {
                if (resp.data.result === 1) {
                    this.$message({
                        type: 'success',
                        message: '个人信息修改成功'
                    });
                    this.userDialogFormVisible = false
                } else {
                    this.$message({
                        showClose: true,
                        message: '修改失败！',
                        type: 'error'
                    })
                }
            });
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
            axios({
                url: '/SoftwareEngineering/studentServlet?action=getTakes',
                method: "Post",
                data: {
                    studentNumber: this.user.studentNumber,
                },
            }).then(resp => {
                vm.courseList = JSON.parse(JSON.stringify(resp.data.sections));
                console.log(vm.courseList)
            });
        },
        // 加载课程相关信息
        loadCourseInformation(courseID, title, classID) {
            // 通过课程ID来加载课程信息
            this.changeComponents = 'Course'
            this.courseID = courseID
            this.title = title
            this.classID = classID
            axios({
                url: '/SoftwareEngineering/studentServlet?action=getCourseNotice',
                method: "Post",
                data: {
                    courseID: this.courseID,
                    classID: this.classID
                },
            }).then(resp => {
                vm.noticeList = JSON.parse(JSON.stringify(resp.data));
            });
            this.getDuty()
        },
        // 获取某个课程下学生的职务
        getDuty(){
            axios({
                url: '/SoftwareEngineering/studentServlet?action=getDuty',
                method: "Post",
                data: {
                    courseID: this.courseID,
                    classID: this.classID,
                    studentNumber:this.user.studentNumber
                },
            }).then(resp => {
                console.log(resp.data.Duty)
                this.duty = resp.data.Duty
            });
        },
        goBack() {
            this.defaultActive = '1'
            this.changeComponents = Calender
        },
        changePassword() {
            axios({
                url: '/SoftwareEngineering/userServlet?action=sendEmail',
                method: "Post",
                data: {
                    userNumber: this.user.studentNumber,
                    isResetPassword: "yes"
                },
            }).then(resp => {
                this.verifyFromVisible = true;
                if (resp.data.msg === 2) {
                    this.$message({
                        message: '验证码已发送到您的邮箱，填写到下列输入框后方可修改！',
                        type: 'success'
                    });
                }
            });
            this.$message({
                message: '验证码正在来的路上！',
            });
        },
        verify() {
            axios({
                url: '/SoftwareEngineering/userServlet?action=verify',
                method: "Post",
                data: {
                    verificationCode: this.verificationCode
                },
            }).then(resp => {
                if (resp.data.result === 1) {
                    this.verifyFromVisible = false;
                    this.verificationCode = '',
                        this.$message({
                            message: '验证成功！',
                            type: 'success'
                        });
                    this.password.newPassword = ''
                    this.password.originPassword = ''
                    this.passwordFormVisible = true;
                } else{
                    this.$message({
                        message: '验证码错误！',
                        type: 'error'
                    });
                }
            });
        },
        resetPassword() {
            axios({
                url: '/SoftwareEngineering/userServlet?action=changePassword',
                method: "Post",
                data: {
                    userNumber: this.user.studentNumber,
                    newPassword: this.password.newPassword,
                    oldPassword: this.password.originPassword
                },
            }).then(resp => {
                if (resp.data.result === 1) {
                    this.$message({
                        message: '密码重置成功！',
                        type: 'success'
                    });
                    this.passwordFormVisible = false;
                } else {
                    this.$message({
                        message: '新旧密码相同，重置失败！',
                        type: 'error'
                    });
                }
            });
        }
    },
    components: {
        // 日历组件
        Calender,
        // 课程组件
        Course
    }
})

// 获取头像框
var avatarBlock = document.getElementById("avatarBlock");

// 点击头像弹出个人信息
avatarBlock.onclick = function () {
    vm.drawerVisible = true;
}