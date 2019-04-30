<template>
  <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-position="left" label-width="0px" class="demo-ruleForm login-container">
    <h3 class="component">系统登录</h3>
    <el-form-item prop="account">
      <el-input type="text" v-model="ruleForm2.account" auto-complete="off" placeholder="账号"></el-input>
    </el-form-item>
    <el-form-item prop="checkPass">
      <el-input type="password" v-model="ruleForm2.checkPass" auto-complete="off" placeholder="密码"></el-input>
    </el-form-item>
    <el-form-item prop="vercode">
      <el-input type="text" v-model="ruleForm2.vercode" auto-complete="off" placeholder="验证码">
        <template slot="append" >
          <img ref="captcha" src="" style="height: 31px;">
        </template>
      </el-input>
    </el-form-item>
    <el-radio-group v-model="loginType">
      <el-radio :label="0">学生</el-radio>
      <el-radio :label="1">教师</el-radio>
      <el-radio :label="2">管理员</el-radio>
    </el-radio-group>
    <br />
    <el-checkbox v-model="checked" checked class="remember">记住密码</el-checkbox>
    <el-form-item style="width:100%;">
      <el-button type="primary" style="width:100%;" @click.native.prevent="handleSubmit2" :loading="logining">登录</el-button>
      <!--<el-button @click.native.prevent="handleReset2">重置</el-button>-->
    </el-form-item>
  </el-form>
</template>

<script>
  import { requestLogin,captcha,menu } from '../api/api';
  //import NProgress from 'nprogress'

    var code;

  export default {
    data() {
        var checkCode = (rule, value, callback) => {
            if (!value) {
                return callback(new Error('验证码不能为空'));
            }
            setTimeout(() => {
                if (code === value) {
                    callback();
                } else {
                    callback(new Error('验证码错误'));
                }
            }, 1000);
        };
      return {
        logining: false,
        ruleForm2: {
          account: '2015326601098',
          checkPass: '123456',
            vercode:''
        },
        rules2: {
          account: [
            { required: true, message: '请输入账号', trigger: 'blur' },
            //{ validator: validaePass }
          ],
          checkPass: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            //{ validator: validaePass2 }
          ],
            vercode: [
              {
                validator: checkCode, trigger: 'blur'
              }
          ]
        },
          checked:true,
          loginType:0
      };
    },
    methods: {
      handleReset2() {
        this.$refs.ruleForm2.resetFields();
      },
        getCaptcha(){
            captcha().then(data => {
                code = data.headers.captcha;
                this.$refs.captcha.src = 'data:image/gif;base64,'+data.data;
            })
        },
        generateRoutesFromMenu (menu = []) {
          for (let i = 0, l = menu.length; i < l; i++) {
              let item = menu[i];
              if (item && item.component && item.path) {
                  var p;
                  if(item.path === '/'){
                      p = '../views/Home.vue';
                      this.generateRoutesFromMenu(item.children);
                  }else{
                      p = "../views"+item.path+".vue";
                  }
                  // const comp = () => Promise.resolve({ p })
                  // item.component = import('./comp.vue');
                  this.$router.options.routes.push(item);
              }
          }
          return menu;
      },
      handleSubmit2(ev) {
        var _this = this;
        this.$refs.ruleForm2.validate((valid) => {
          if (valid) {
            //_this.$router.replace('/table');
            this.logining = true;
            let formData = new FormData();
              formData.append('username', this.ruleForm2.account);
              formData.append('password', this.ruleForm2.checkPass);
              formData.append('vercode', this.ruleForm2.vercode);
              formData.append('loginType', this.loginType);
              let config = {
                  headers: {
                      'Content-Type': 'multipart/form-data'
                  }
              };
            var loginParams = {
                username: this.ruleForm2.account,
                password: this.ruleForm2.checkPass,
                vercode: this.ruleForm2.vercode
            };
            requestLogin(formData,config).then(req => {
              this.logining = false;
              //NProgress.done();
              let {code, msg, data } = req.data,token = data.password,userPath='';
              if (code !== 1) {
                this.$message({
                  message: msg,
                  type: 'error'
                });
              } else {
                sessionStorage.setItem('user', JSON.stringify(data));
                this.$store.commit('set_token', token);
                  menu().then(req => {
                  //     let menus = req.data.data,routes;
                  //     // sessionStorage.setItem('menus',JSON.stringify(menus[0].children));
                  //     if (this.$store.state.menus.length <= 0) { // 避免注销后在不刷新页面的情况下再登录重复加载路由
                  //         // 动态加载路由关键2行
                  //         routes = this.generateRoutesFromMenu(menus);
                  //         console.log(routes);
                  //         // this.$router.addRoutes(this.$router.options.routes);
                  //         this.$router.options.routes = routes;
                  //         this.$router.addRoutes(routes);
                  //         this.$store.commit('add_menu', routes);
                  //         console.log(this.$router)
                  //     }
                  //
                  });
                  this.$router.push({ path: '/courseHome/course_intro' });
              }
            });
          } else {
            return false;
          }
        });
      }
    },
      mounted() {
          this.getCaptcha();
      }
  }

</script>

<style lang="scss" scoped>
  .login-container {
    /*box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.06), 0 1px 0px 0 rgba(0, 0, 0, 0.02);*/
    -webkit-border-radius: 5px;
    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
    .component {
      margin: 0px auto 40px auto;
      text-align: center;
      color: #505458;
    }
    .remember {
      margin: 0px 0px 35px 0px;
    }
  }
</style>