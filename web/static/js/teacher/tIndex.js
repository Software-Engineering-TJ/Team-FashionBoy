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
            //
            annoDialogFormVisible: false,
            // 公告表单
            annoForm:{
                title:'',
                content:'',
                date:'',
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
                    this.changeComponents = "Calender"
                    this.defaultActive = '1'
                    break;
                case "2":
                    this.drawerCourse = true
                    this.defaultActive = '2'
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