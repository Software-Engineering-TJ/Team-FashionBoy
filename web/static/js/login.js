var vm = new Vue({
    el: "#box",
    data() {
        var validateAccount = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('邮箱不能为空！'));
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
                email: '',
                password: '',
                identify: '',
                judge: false,
            },
            rules: {
                email: [{
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
                    console.log(this.$refs[formName].model);
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
                alert('您的邮箱不能为空');
                return false;
            }
            this.ruleForm.judge = true;
            this.btnStatus = true;
        }
    }
})