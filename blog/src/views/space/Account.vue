<template>
  <div class="single-block">
    <div>
      <h3 style="margin-top: 8px">账号设置</h3>
      <div style="margin-top: 22px; margin-left: 10px">
        密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码<el-button
          type="text"
          style="float: right; margin-top: -8px"
          size="medium"
          @click="
            this.status1 = 1;
            this.status2 = 0;
            this.status3 = 0;
          "
          >修改密码</el-button
        >
      </div>
      <el-divider></el-divider>
      <div style="margin-top: 22px; margin-left: 10px">
        邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱<el-button
          type="text"
          style="float: right; margin-top: -8px"
          size="medium"
          @click="
            this.status1 = 0;
            this.status2 = 1;
            this.status3 = 0;
          "
          >更改邮箱</el-button
        >
      </div>
      <el-divider></el-divider>
      <div style="margin-top: 22px; margin-left: 10px">
        账号注销<el-button
          type="text"
          style="float: right; margin-top: -8px"
          size="medium"
          @click="
            this.status1 = 0;
            this.status2 = 0;
            this.status3 = 1;
          "
          >立即注销</el-button
        >
      </div>
    </div>
  </div>
  <div class="single-block" v-show="status1" id="password">
    <div style="display: flex">
      <h4 style="margin-top: 8px">修改密码</h4>
      <span style="flex: 1"></span>
      <el-button
        type="text"
        style="float: right"
        size="medium"
        @click="this.status1 = 0"
        >取&nbsp;&nbsp;&nbsp;消</el-button
      >
    </div>
    <el-form
      :model="ruleForm"
      status-icon
      :rules="rules"
      ref="ruleForm"
      label-width="25%"
      class="demo-ruleForm"
      style="margin-top: 30px"
    >
      <el-form-item label="新的密码" prop="password">
        <el-input
          type="password"
          v-model="ruleForm.password"
          autocomplete="off"
          style="width: 80%"
        ></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="checkPass">
        <el-input
          type="password"
          v-model="ruleForm.checkPass"
          autocomplete="off"
          style="width: 80%"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitFormPass('ruleForm')"
          >提交</el-button
        >
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
  <div class="single-block" v-show="status2" id="email">
    <div style="display: flex">
      <h4 style="margin-top: 8px">更改邮箱</h4>
      <span style="flex: 1"></span>
      <el-button
        type="text"
        style="float: right"
        size="medium"
        @click="this.status2 = 0"
        >取&nbsp;&nbsp;&nbsp;消</el-button
      >
    </div>
    <el-form
      :model="dynamicValidateForm"
      ref="dynamicValidateForm"
      label-width="35%"
      class="demo-dynamic"
      style="margin-top: 30px"
    >
      <el-form-item
        prop="email"
        label="新的邮箱"
        :rules="[
          { required: true, message: '请输入邮箱地址', trigger: 'blur' },
          {
            type: 'email',
            message: '请输入正确的邮箱地址',
            trigger: ['blur', 'change'],
          },
        ]"
        style="width: 80%"
      >
        <el-input v-model="dynamicValidateForm.email"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          @click="submitFormEmail('dynamicValidateForm')"
          >提交</el-button
        >
        <el-button @click="resetForm('dynamicValidateForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
  <div class="single-block" v-show="status3" id="logout">
    <div style="display: flex">
      <h4 style="margin-top: 8px">账号注销</h4>
      <span style="flex: 1"></span>
      <el-button
        type="text"
        style="float: right"
        size="medium"
        @click="this.status3 = 0"
        >取&nbsp;&nbsp;&nbsp;消</el-button
      >
    </div>
    <div align="center" style="margin-top: 10px">
      <el-button type="danger" size="medium" @click="deleteUser"
        >确 认 注 销</el-button
      >
    </div>
  </div>
  <div class="single-block">
    <div>
      <h3 style="margin-top: 8px; color: mediumseagreen">
        <span class="el-icon-s-opportunity"></span> 友情提示
        <span class="el-icon-s-opportunity"></span>
      </h3>
      <div style="margin-top: 25px">
        <span
          class="el-icon-s-promotion"
          style="color: rgb(64, 158, 255)"
        ></span>
        如果忘记了密码，可以联系作者找回
      </div>
      <div style="margin-top: 20px">
        <span class="el-icon-warning" style="color: rgb(252, 85, 49)"></span>
        账号注销为不可逆操作，确认后无法找回
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Account",
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.ruleForm.checkPass !== "") {
          this.$refs.ruleForm.validateField("checkPass");
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.ruleForm.password) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      status1: 0,
      status2: 0,
      status3: 0,
      ruleForm: {
        password: "",
        checkPass: "",
      },
      rules: {
        password: [{ validator: validatePass, trigger: "blur" }],
        checkPass: [{ validator: validatePass2, trigger: "blur" }],
      },
      dynamicValidateForm: {
        email: "",
      },
      user: {},
    };
  },
  created() {
    this.user.token = JSON.parse(window.localStorage.getItem("access-token"));
    this.user.userid = JSON.parse(window.localStorage.getItem("access-userid"));
    if (this.user.token === null || this.user.userid === null) {
      this.$router.push("/login");
    }
  },
  methods: {
    submitFormPass(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.user.password = this.ruleForm.password;
          request
            .put("http://" + window.server.ip + ":9090/user", this.user)
            .then((res) => {
              if (res.code === "0") {
                this.$message({
                  type: "success",
                  message: " 修改密码成功",
                });
                this.status1 = 0;
                alert("密码修改成功，请重新登录！");
                this.$router.push("/login");
              } else {
                this.$message({
                  type: "error",
                  message: res.msg,
                });
              }
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    submitFormEmail(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.user.email = this.dynamicValidateForm.email;
          request
            .put("http://" + window.server.ip + ":9090/user", this.user)
            .then((res) => {
              if (res.code === "0") {
                this.$message({
                  type: "success",
                  message: " 更改邮箱成功",
                });
                this.status2 = 0;
              } else {
                this.$message({
                  type: "error",
                  message: res.msg,
                });
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
    deleteUser() {
      this.$confirm("此操作将永久删除账号, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          request
            .delete(
              "http://" + window.server.ip + ":9090/user/" + this.user.userid
            )
            .then((res) => {
              if (res.code === "0") {
                this.$message({
                  type: "success",
                  message: "注销成功",
                });
                this.$router.push('/login')
              } else {
                this.$message({
                  type: "error",
                  message: res.msg,
                });
              }
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消注销",
          });
        });
    },
  },
};
</script>

<style scoped>
.single-block {
  margin-top: 10px;
  margin-left: 10px;
  width: 80%;
  box-shadow: border-box;
  background: rgba(255, 255, 255, 1);
  border-radius: 10px;
  padding: 20px;
  float: left;
}
</style>