const router = new VueRouter({})
var vm = new Vue({
    el: "#box",
    data() {
        var validateAccount = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('账号不能为空！'));
            }
            callback();
        };
        var validatePass = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('密码不能为空！'));
            }
            callback();
        };
        var validateIdentify = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('验证码不能为空！'));
            }
            callback();
        };
        return {
            ruleForm: {
                account: '',
                password: '',
                identify: '',
                judge: false,
            },
            rules: {
                account: [{
                    validator: validateAccount,
                    trigger: 'blur'
                }],
                password: [{
                    validator: validatePass,
                    trigger: 'blur'
                }],
                identify: [{
                    validator: validateIdentify,
                    trigger: 'blur'
                }],
            },
            btnStatus: false,
        };
    },
    methods: {
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    axios({
                        url: '/SoftwareEngineering/userServlet?action=getUserStatus',
                        method: "Post",
                        data: {
                            userNumber: this.ruleForm.account
                        },
                    }).then(resp => {
                        if (resp.data.userNumber === undefined) {
                            alert("该账号不存在！请重新输入！")
                            this.ruleForm.account = "";
                            this.ruleForm.password = "";
                        } else if (resp.data.status === "0") {
                            alert("您的账号还未激活！请先激活账号！")
                        } else if (resp.data.password === this.ruleForm.password) {
                            window.location.href = "/SoftwareEngineering/pages/administrator/aIndex.html"
                        } else {
                            alert("您输入的密码错误！请重新输入！")
                            this.ruleForm.password = "";
                        }
                    });
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
            if (this.ruleForm.judge === true) {
                this.ruleForm.judge = false;
                this.btnStatus = false;
            }
        },
        changeStatus() {
            if (this.ruleForm.email === '') {
                alert('您的账号不能为空');
                return false;
            }
            axios({
                url: '/SoftwareEngineering/userServlet?action=sendEmail',
                method: "Post",
                data: {
                    userNumber: this.ruleForm.account
                },
            }).then(resp => {
                if (resp.data.userNumber === undefined) {
                    alert("该账号不存在！请重新输入！")
                    this.ruleForm.account = "";
                    this.ruleForm.password = "";
                } else if (resp.data.status === "0") {
                    alert("您的账号还未激活！请先激活账号！")
                } else if (resp.data.password === this.ruleForm.password) {
                    window.location.href = "/SoftwareEngineering/pages/administrator/aIndex.html"
                } else {
                    alert("您输入的密码错误！请重新输入！")
                    this.ruleForm.password = "";
                }
            });
            this.ruleForm.judge = true;
            this.btnStatus = true;
        }
    }
})

