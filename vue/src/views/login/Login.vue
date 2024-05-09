<template>
  <div style="height: 100vh;overflow: hidden ;position: relative">
    <el-card class="cover" v-if="loginAdmin.id">
      <slide-verify :l="42"
                    :r="10"
                    :w="310"
                    :h="155"
                    :accuracy="5"
                    slider-text="向右滑动"
                    @success="onSuccess"
                    @fail="onFail"
                    @refresh="onRefresh"
      ></slide-verify>
    </el-card>

    <div style="width: 500px; height: 400px; background-color: white; border-radius: 10px;
    margin:150px auto ;padding: 50px">
      <div style="margin:30px;text-align: center;font-size: 30px;color: dodgerblue">登录</div>
      <el-form :model="admin" :rules="rules" ref="loginForm">
        <el-form-item>
      <!--   用element后要用@keyup.enter.native  -->
          <el-input placeholder="请输入账号" prefix-icon="el-icon-user" size="medium" v-model="admin.username" @keyup.enter.native="login"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input placeholder="请输入密码" show-password  prefix-icon="el-icon-lock" size="medium" v-model="admin.password" @keyup.enter.native="login"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%" size="medium" type="primary" @click="login">登录</el-button>
        </el-form-item>
      </el-form>

    </div>
</div>
</template>

<script>
import request from "@/untils/request";
import Cookies  from "js-cookie";

export default {
  name: "LOGIN",
  data() {
    return {
      loginAdmin:{},
      admin:{},
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min:3,max:10,message: '长度在3-10个字符',trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码 ', trigger: 'blur'},
          {min:3,max:10,message: '长度在3-10个字符',trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    login(){
      this.$refs['loginForm'].validate((valid) =>{
        if (valid){
          request.post('/admin/login', this.admin).then(res =>{
            if (res.code === '200'){
              this.loginAdmin = res.data;
            } else {
              this.$notify.error(res.mes);
              // 在登录失败时清空 loginAdmin 对象
              this.loginAdmin = {};
            }
          }).catch(err => {
            console.error('登录请求出错：', err);
            this.$notify.error('登录请求出错，请稍后重试');
            // 在登录请求出错时清空 loginAdmin 对象
            this.loginAdmin = {};
          });
        }
      });
    },
    onSuccess() {   //滑块验证后触发
      Cookies.set('admin',JSON.stringify(this.loginAdmin))
      this.$notify.success("登录成功")
      this.$router.push('/')
    },
    onFail() {
    console.log('onFail')
    },
    onRefresh() {
      console.log('refresh')
    }
  }
}
</script>

<style>
.cover {
  width: fit-content;
  background-color: white;
  position: absolute;
  top:50%;
  left:50%;
  transform: translate(-50%, -50%);
  z-index: 1000;
}
</style>
