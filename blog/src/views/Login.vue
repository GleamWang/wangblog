<template>
  <Background
    style="position: fixed; z-index: -1; width: 100%; height: 100%"
    pic="2.jpg"
  />
  <div
    style="
      height: 100vh;
      display: flex;
      justify-content: center;
      align-items: center;
    "
  >
    <div class="shell">
      <div class="box-left">
        <h2 align=center>Login</h2>
        <span align=center
          >Welcome to the login interface_(:зゝ∠)_</span
        >
        <div align=center>
        <el-link icon="el-icon-bottom-right" type="primary" @click="this.$router.push('/')">直接前往博客主页</el-link>
        </div>
      </div>
      <div class="box-right">
        <div>
          <div style="text-align: center; font-size: 200%; margin-top:50px">
            欢迎登录
          </div>
          <el-form
            ref="form"
            :model="form"
            label-width="70px"
            style="margin-top: 30px"
          >
            <el-form-item>
              <el-input
                prefix-icon="el-icon-user-solid"
                v-model="form.userid"
                style="width: 250px"
              ></el-input>
            </el-form-item>
            <el-form-item>
              <el-input
                prefix-icon="el-icon-lock"
                v-model="form.password"
                show-password
                style="width: 250px"
              ></el-input>
            </el-form-item>
            <el-form-item>
              <el-button
                type="primary"
                round
                style="width: 100px"
                @click="login"
              >
                登 录
              </el-button>
              <el-button
                type="danger"
                round
                style="width: 100px; margin-left: 50px"
                @click="register"
              >
                注 册
              </el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
  </div>
  <Footer />
</template>

<script>
import request from "../utils/request";
import Footer from "@/components/Footer";
import Background from "../components/Background.vue";

export default {
  name: "Login",
  components: {
    Background,
    Footer,
  },
  data() {
    return {
      form: {},
    };
  },
  methods: {
    login() {
      request
        .post("http://" + window.server.ip + ":9090/user/login", this.form)
        .then((res) => {
          if (res.code === "-1") {
            this.$message({
              type: "error",
              message: res.msg,
            });
          } else {
            if (res.data.status == 0) {
              this.$message({
                type: "error",
                message: "该账号已被封禁！如有疑问请联系管理员",
              });
              return false;
            } else {
              this.$message({
                type: "success",
                message: "登录成功！欢迎你，" + this.form.userid + "！",
              });
              localStorage.setItem("access-token", JSON.stringify(res.data.token));
              localStorage.setItem("access-userid", JSON.stringify(res.data.userid));
              this.$router.push("/blogs");
            }
          }
        });
    },
    register() {
      this.$router.push("/register");
    },
  },
};
</script>

<style scoped>
* {
  padding: 0;
  margin: 0;
}

.shell {
  width: 640px;
  height: 320px;
  display: flex;
}

.box-left {
  background-color: #fff;
  height: 280px;
  top: 20px;
  position: relative;
  width: 50%;
  opacity: 0.8;
  border-radius: 15px 0 0 15px;
}

.box-left h2 {
  font: 900 50px "";
  margin: 50px 40px 40px;
}

.box-left span {
  display: block;
  color: #999;
  font-style: 14px;
  margin: 40px;
}

.box-right {
  background-color: #474a59;
  box-shadow: 0 0 40px 16px rgba(0, 0, 0, 0.2);
  color: #f1f1f2;
  width: 80%;
  border-radius: 15px;
  opacity: 0.9;
}
</style>