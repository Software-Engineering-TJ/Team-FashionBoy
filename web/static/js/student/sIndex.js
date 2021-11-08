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

// Vue实例
var vm = new Vue({
    el: "#box",
    data() {
        return {
            // 页头logo
            logo: '../../static/img/logo2.png',
            // 头像路径
            squareUrl: 'https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png',
            // 动态组件切换
            changeComponents: 'calender',
            // 抽屉是否显示
            drawerVisible: false,
            // 抽屉弹出方向
            drawerDirection: 'ltr',
            // 管理员信息对话框是否可见
            userDialogFormVisible: false,
            // 管理员实体
            user: {
                name: '王文炯',
                sex: '男',
                studentNumber: '1953281',
                phoneNumber: '18886806666',
                email: 'chengrong@163.com',
            },
            // 管理员临时实体，作为表单修改对象
            userChange: {},
            // 对话框中表单标签的宽度
            formLabelWidth: '120px',

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
                .catch(_ => {});
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
        changeUserInfo() {
            this.$data.user = JSON.parse(JSON.stringify(this.$data.userChange));
            // axios.psot('http://canvas.tongji.edu.cn', this.$data.administratorChange);
            this.$data.userDialogFormVisible = false;
        },
        //切换管理员信息改变页面的状态
        switchUserDialogStatus() {
            if (this.$data.userDialogFormVisible === false) {
                this.$data.userChange = JSON.parse(JSON.stringify(this.$data.user));
                this.$data.userDialogFormVisible = true;
            } else {
                this.$data.userDialogFormVisible = false;
            }
        }
    },
    components: {
        // 日历组件
        Calender
    }
})

// 获取头像框
var avatarBlock = document.getElementById("avatarBlock");

// 点击头像弹出个人信息
avatarBlock.onclick = function() {
    vm.$data.drawerVisible = true;
}