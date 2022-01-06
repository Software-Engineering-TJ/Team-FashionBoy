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
            sectionDialogFormVisible: false,
            // 实验报告说明的弹窗是否可见
            reportDialogFormVisible: false,
            // 密码弹框是否可见
            passwordFormVisible: false,
            // 验证码弹窗是否可见
            verifyFromVisible: false,
            // 添加班级的课程
            addClassCourse: '',
            // 增加班级的弹窗是否可见
            addClassDialogFormVisible: false,
            verificationCode: '',
            password: {
                // 新密码
                newPassword: '',
                // 旧密码
                originPassword: '',
            },
            // 公告表单
            annoForm: {
                title: '',
                content: '',
            },
            // 添加班级表单
            addClassForm: {
                courseID: '',
                instructorNumber: '',
                day: '',
                time: '',
                number: ''
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
                attendanceWeight: 0,
                practiceWeight: 0
            },
            // 实验报告表单
            reportForm: {
                expName: '',
                reportName: '',
                reportDescription: '',
                endDate: '',
                reportType: [],
            },
            //实验表单验证规则
            rules: {
                desc: [
                    {required: true, message: '请填写本实验项目相关描述信息', trigger: 'blur'}
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
            //实验报告表单验证规则
            reportRules: {
                expName: [
                    {required: true, message: '请选择关联的实验', trigger: 'change'}
                ],
                reportName: [
                    {required: true, message: '请填写本实验报告标题', trigger: 'blur'}
                ],
                reportDescription: [
                    {required: true, message: '请填写本实验报告相关描述信息', trigger: 'blur'}
                ],
                endDate: [
                    {type: 'date', required: true, message: '请选择日期', trigger: 'change'}
                ],
                reportType: [
                    {required: true, message: '请选择文件类型', trigger: 'change'}
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
            courseList: [],
            // 课程相关公告信息
            noticeList: [],
            // 责任教师管理课程信息
            courseInfoList: [],
            sectionInfoList: [
                {
                    classID: '1',
                    sectionDate: '周三',
                    sectionTime: '第二节课',
                    instructorID: '23034',
                    instructorName: '金伟祖',
                },
                {
                    classID: '2',
                    sectionDate: '周三',
                    sectionTime: '第五节课',
                    instructorID: '23035',
                    instructorName: '夏波涌'
                },
                {
                    classID: '3',
                    sectionDate: '周四',
                    sectionTime: '第四节课',
                    instructorID: '23034',
                    instructorName: '金伟祖'
                }
            ],
            options: [{
                value: '.txt',
                label: '.txt'
            }, {
                value: '.doc',
                label: '.doc'
            }, {
                value: '.docx',
                label: '.docx'
            }, {
                value: '.pdf',
                label: '.pdf'
            }],
            experimentOptions: []
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
                    this.loadCourse()
                    this.defaultActive = '2'
                    break;
                case "3-1":
                    this.changeComponents = 'OpenCourse'
                    this.defaultActive = '3-1'
                    break;
                case "3-2":
                    this.changeComponents = 'CourseOverview'
                    this.loadDutyCourse()
                    this.defaultActive = '3-2'
                    break;
                default:
                    break;
            }
        },
        // 改变用户信息
        changeUserInfo() {
            this.user = JSON.parse(JSON.stringify(this.$data.userChange));
            axios({
                url: '/SoftwareEngineering/userServlet?action=changeUserInfo',
                method: "Post",
                data: {
                    userNumber: this.user.instructorNumber,
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
        // 加载该教师执教的所有课程,用户点击“我的课程”时触发
        loadCourse() {
            axios({
                url: '/SoftwareEngineering/instructorServlet?action=getSections',
                method: "Post",
                data: {
                    instructorNumber: this.user.instructorNumber,
                },
            }).then(resp => {
                vm.courseList = JSON.parse(JSON.stringify(resp.data));
            });
        },
        // 加载课程相关信息
        loadCourseInformation(courseID, title, classID) {
            // 通过课程ID来加载课程信息
            this.changeComponents = 'Course'
            this.courseID = courseID
            this.title = title
            this.classID = classID
            this.loadNotice()
        },
        loadNotice() {
            axios({
                url: '/SoftwareEngineering/studentServlet?action=getCourseNotice',
                method: "Post",
                data: {
                    courseID: this.courseID,
                    classID: this.classID
                },
            }).then(resp => {
                vm.noticeList = JSON.parse(JSON.stringify(resp.data));
            })
        },
        goBack() {
            this.defaultActive = '1'
            this.changeComponents = Calender
        },
        publishAnnouncement() {
            this.annoDialogFormVisible = true
        },
        releaseNotice() {
            axios({
                url: '/SoftwareEngineering/instructorServlet?action=releaseNotice',
                method: "Post",
                data: {
                    courseID: this.courseID,
                    classID: this.classID,
                    content: this.annoForm.content,
                    title: this.annoForm.title,
                    issuer: this.user.instructorNumber,
                },
            }).then(resp => {
                if (resp.data.result === 1) {
                    this.$message({
                        type: 'success',
                        message: '公告发布成功！'
                    });
                    this.experimentForm = {}
                    this.annoDialogFormVisible = false
                    this.loadNotice()
                } else {
                    this.$message({
                        showClose: true,
                        message: '发布失败！',
                        type: 'error'
                    })
                }
            })
        },
        submitExperimentForm(formName, loadName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    this.$refs.upload.submit();
                    axios({
                        url: '/SoftwareEngineering/instructorServlet?action=releaseExperiment',
                        method: "Post",
                        data: {
                            expName: this.experimentForm.title,
                            courseID: this.courseID,
                            classID: this.classID,
                            endDate: this.experimentForm.date1.getFullYear() + "-" + ((this.experimentForm.date1.getMonth() + 1) < 10 ? '0' + (this.experimentForm.date1.getMonth() + 1) : (this.experimentForm.date1.getMonth() + 1)) + "-" + (this.experimentForm.date1.getDate() < 10 ? '0' + this.experimentForm.date1.getDate() : this.experimentForm.date1.getDate()),
                            expInfo: this.experimentForm.desc
                        },
                    }).then(resp => {
                        if (resp.data.result === 1) {
                            vm.$refs.child.getExperimentInfo()
                            this.$message({
                                type: 'success',
                                message: '实验发布成功！'
                            });
                            this.experimentForm = {}
                            this.experimentDialogFormVisible = false
                        } else {
                            this.$message({
                                showClose: true,
                                message: '本实验的实验学习任务尚未发布，不能先发布实验报告提交说明！',
                                type: 'error'
                            })
                        }
                    });
                } else {
                    alert('实验信息格式不合格！');
                    return false;
                }
            });
        },
        submitReport(formName, loadName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    let reportType = ""
                    let now = new Date()
                    let startDate = now.getFullYear() + '-' + (now.getMonth() + 1) + '-' + (now.getDate() < 10 ? ('0' + now.getDate()) : now.getDate()) + ' ' + (now.getHours() < 10 ? ('0' + now.getHours()) : now.getHours()) + ':' + (now.getMinutes() < 10 ? ('0' + now.getMinutes()) : now.getMinutes())
                    let endDate = this.reportForm.endDate.getFullYear() + '-' + (this.reportForm.endDate.getMonth() + 1) + '-' + (this.reportForm.endDate.getDate() < 10 ? ('0' + this.reportForm.endDate.getDate()) : this.reportForm.endDate.getDate()) + ' ' + (this.reportForm.endDate.getHours() < 10 ? ('0' + this.reportForm.endDate.getHours()) : this.reportForm.endDate.getHours()) + ':' + (this.reportForm.endDate.getMinutes() < 10 ? ('0' + this.reportForm.endDate.getMinutes()) : this.reportForm.endDate.getMinutes())
                    for (let i = 0; i < this.reportForm.reportType.length; i++) {
                        reportType = reportType + this.reportForm.reportType[i]
                        if (i !== this.reportForm.reportType.length - 1) {
                            reportType = reportType + ","
                        }
                    }
                    axios({
                        url: '/SoftwareEngineering/instructorServlet?action=releaseReportDesc',
                        method: "Post",
                        data: {
                            expName: this.reportForm.expName,
                            courseID: this.courseID,
                            classID: this.classID,
                            reportInfo: {
                                reportName: this.reportForm.reportName,
                                reportDescription: this.reportForm.reportDescription,
                                startDate: startDate,
                                endDate: endDate,
                                reportType: reportType
                            }
                        },
                    }).then(resp => {
                        if (resp.data.result === 1) {
                            vm.$refs.child.getReportDesc()
                            this.$message({
                                type: 'success',
                                message: '实验报告说明发布成功！'
                            });
                            this.reportForm = {}
                            this.reportDialogFormVisible = false
                        } else {
                            this.$message({
                                showClose: true,
                                message: '信息填写不正确，发布失败！',
                                type: 'error'
                            })
                        }
                    });
                } else {
                    alert('实验信息格式不合格！');
                    return false;
                }
            });
        },
        releaseExperiment(row) {
            this.experimentForm.title = row.title
            this.experimentDialogFormVisible = true
        },
        addExperiment() {
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
            if (index !== -1) {
                this.courseForm.experimentForm.splice(index, 1)
            }
        },
        getSectionInfo(courseID) {
            axios({
                url: '/SoftwareEngineering/instructorServlet?action=getClassInfo',
                method: "Post",
                data: {
                    courseID: courseID,
                }
            }).then(resp => {
                this.addClassCourse = courseID
                this.sectionInfoList = resp.data
            })
            this.sectionDialogFormVisible = true
        },
        releaseReportDesc(experimentList) {
            this.experimentOptions = []
            for (let i = 0; i < experimentList.length; i++) {
                this.experimentOptions.push({
                    value: experimentList[i].title,
                    label: experimentList[i].title
                })
            }
            this.reportDialogFormVisible = true
        },
        getNoticeInfo() {
            this.changeComponents = Notice
        },
        withdrawNotice(date) {
            axios({
                url: '/SoftwareEngineering/instructorServlet?action=withdrawNotice',
                method: "Post",
                data: {
                    courseID: this.courseID,
                    classID: this.classID,
                    instructorNumber: this.user.instructorNumber,
                    date: date,
                },
            }).then(resp => {
                if (resp.data.result === 1) {
                    vm.$refs.child.changeComponents = 'Notice'
                    this.loadNotice()
                    this.$message({
                        type: 'success',
                        message: '撤回成功！'
                    });
                } else {
                    this.$message({
                        showClose: true,
                        message: '撤回失败！',
                        type: 'error'
                    })
                }
            })
        },
        withdrawReportDesc(expName, reportName) {
            axios({
                url: '/SoftwareEngineering/instructorServlet?action=withdrawReportDesc',
                method: "Post",
                data: {
                    courseID: this.courseID,
                    classID: this.classID,
                    expName: expName,
                    reportName: reportName
                },
            }).then(resp => {
                if (resp.data.result === 1) {
                    vm.$refs.child.changeComponents = 'ExperimentalReport'
                    vm.$refs.child.getReportDesc()
                    this.$message({
                        type: 'success',
                        message: '撤回成功！'
                    });
                } else {
                    this.$message({
                        showClose: true,
                        message: '撤回失败！',
                        type: 'error'
                    })
                }
            })
        },
        loadDutyCourse() {
            this.courseInfoList = []
            axios({
                url: '/SoftwareEngineering/administrationServlet?action=getTeachesByTeacherNumber',
                method: "Post",
                data: {
                    instructorNumber: this.user.instructorNumber,
                },
            }).then(resp => {
                let info = resp.data.sectionInformation
                for (let i = 0; i < info.length; i++) {
                    if (info[i].duty === "责任教师" || info[i].duty === "教师and责任教师") {
                        let judge = true
                        for (let index in this.courseInfoList){
                            if (this.courseInfoList[index].courseID === info[i].courseID){
                                judge = false
                                break
                            }
                        }
                        if (judge === true){
                            this.courseInfoList.push(info[i])
                        }

                    }
                }
                console.log(this.courseInfoList)
                if (this.courseInfoList.length === 0) {
                    this.$message({
                        showClose: true,
                        message: '您目前不是任何一节课的责任教师！',
                        type: 'warning'
                    })
                }
            })
        },
        changePassword() {
            axios({
                url: '/SoftwareEngineering/userServlet?action=sendEmail',
                method: "Post",
                data: {
                    userNumber: this.user.instructorNumber,
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
                }
            });
        },
        resetPassword() {
            axios({
                url: '/SoftwareEngineering/userServlet?action=changePassword',
                method: "Post",
                data: {
                    userNumber: this.user.instructorNumber,
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
        },
        deleteClass(row) {

        },
        addClass() {
            axios({
                url: '/SoftwareEngineering/instructorServlet?action=addSection',
                method: "Post",
                data: {
                    courseID: this.addClassCourse,
                    instructorNumber: this.addClassForm.instructorNumber,
                    day: Number.parseInt(this.addClassForm.day),
                    time: Number.parseInt(this.addClassForm.time),
                    number: this.addClassCourse.number
                },
            }).then(resp => {
                if (resp.data.result === '添加课程成功') {
                    this.$message({
                        message: '添加课程成功！',
                        type: 'success'
                    });
                    this.passwordFormVisible = false;
                } else {
                    this.$message({
                        message: '添加课程失败',
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
        Course,
        // 开设课程组件
        OpenCourse,
        // 课程概况组件
        CourseOverview,
        // 公告栏组件
        Notice,
    }
})

// 获取头像框
var avatarBlock = document.getElementById("avatarBlock");

// 点击头像弹出个人信息
avatarBlock.onclick = function () {
    vm.drawerVisible = true;
}