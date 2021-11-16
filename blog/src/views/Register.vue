<template>
  <Background
    style="position: fixed; z-index: -1; width: 100%; height: 100%"
    pic="4.jpg"
  />
  <div
    style="
      height: 100vh;
      display: flex;
      justify-content: center;
      align-items: center;
      opacity: 0.9;
    "
  >
    <div style="width: 400px; height: 420px; background-color: #474a59">
      <div
        style="
          text-align: center;
          font-size: 200%;
          margin-top: 40px;
          color: white;
        "
      >
        欢迎注册
      </div>
      <el-form
        ref="ruleForm"
        :model="ruleForm"
        status-icon
        :rules="rules"
        class="demo-ruleForm"
        style="margin-top: 30px"
        label-width="73px"
      >
        <el-form-item prop="userid">
          <el-input
            prefix-icon="el-icon-user-solid"
            v-model="ruleForm.userid"
            type="text"
            autocomplete="off"
            placeholder="请输入用户名"
            style="width: 250px"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            prefix-icon="el-icon-lock"
            v-model="ruleForm.password"
            type="password"
            autocomplete="off"
            placeholder="请输入密码"
            style="width: 250px"
          ></el-input>
        </el-form-item>
        <el-form-item prop="checkPass">
          <el-input
            prefix-icon="el-icon-connection"
            v-model="ruleForm.checkPass"
            type="password"
            autocomplete="off"
            placeholder="请再次输入密码"
            style="width: 250px"
          ></el-input>
        </el-form-item>
        <!-- <el-form-item prop="email">
          <el-input
            prefix-icon="el-icon-paperclip"
            v-model="ruleForm.email"
            type="email"
            autocomplete="off"
            placeholder="请输入邮箱地址"
            style="width: 250px"
          ></el-input>
        </el-form-item> -->
        <el-form-item>
          <el-button
            type="primary"
            @click="submitForm('ruleForm')"
            round
            style="width: 100px"
            >注 册</el-button
          >
          <el-button
            @click="resetForm('ruleForm')"
            round
            style="width: 100px; margin-left: 45px"
            >重 置</el-button
          >
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import request from "../utils/request";
import Background from "../components/Background.vue";

export default {
  name: "Register",
  components: {
      Background,
  },
  data() {
    const checkUserid = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入用户名"));
      } else {
        if (this.ruleForm.userid !== "") {
          this.$refs.ruleForm.validateField("userid");
        }
        callback();
      }
    };
    const validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.ruleForm.checkPass !== "") {
          this.$refs.ruleForm.validateField("checkPass");
        }
        callback();
      }
    };
    const validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.ruleForm.password) {
        callback(new Error("两次密码不一致！"));
      } else {
        callback();
      }
    };

    return {
      ruleForm: {
        userid: "",
        password: "",
        checkPass: "",
      },
      userTable: {},
      rules: {
        userid: [{ validator: checkUserid, trigger: "blur" }],
        password: [{ validator: validatePass, trigger: "blur" }],
        checkPass: [{ validator: validatePass2, trigger: "blur" }],
      },
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request
            .get(
              "http://" + window.server.ip + ":9090/user/selectUserid",
              {
                params: {
                  userid: this.ruleForm.userid,
                },
              }
            )
            .then((res) => {
              if (res == null || res == "" || res == undefined) {
                request
                  .post(
                    "http://" + window.server.ip + ":9090/user/insert",
                    this.ruleForm
                  )
                  .then((res) => {
                    if (res.code === "0") {
                      this.$message({
                        type: "success",
                        message: "注册成功",
                      });
                      this.$router.push("/login");
                    } else {
                      this.$message({
                        type: "error",
                        message: res.msg,
                      });
                    }
                  });
              } else {
                this.$message({
                  type: "error",
                  message: "用户名已存在，请重新输入！",
                });
                this.ruleForm.userid = "";
                return false;
              }
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
};
</script>

<style scoped>
</style>