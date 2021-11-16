<template>
  <div>
    <div class="single-block">
      <div style="display: flex">
        <div>
          <el-avatar
            :size="100"
            fit="contain"
            :src="this.userinfo.avatar"
            style=""
            @mouseover="mouseOver"
          ></el-avatar>

          <el-avatar
            :size="100"
            fit="scale-down"
            :src="require('@/assets/icon/camera.png')"
            style="position: absolute; opacity: 0.5; margin-left: -100px"
            v-show="status"
            @mouseleave="mouseLeave"
            @click="centerDialogVisible = true"
          ></el-avatar>
        </div>
        <div style="margin-left: 20px">
          <div style="font-size: 150%; font-weight: bold; margin-top: 10px">
            {{ this.userinfo.username }}
          </div>
          <div>
            <el-tag type="danger" effect="dark" style="margin-top: 20px">
              {{ this.userinfo.role }}
            </el-tag>
          </div>
        </div>
      </div>
    </div>
    <div class="single-block">
      <el-descriptions title="用户信息" :column="1" size="medium">
        <template #extra>
          <el-button type="text" size="medium" class="el-icon-edit"
            >编辑</el-button
          >
        </template>
        <el-descriptions-item label="用户昵称">{{
          this.userinfo.username
        }}</el-descriptions-item>

        <el-descriptions-item label="用户ID">{{
          this.userinfo.userid
        }}</el-descriptions-item>

        <el-descriptions-item label="性别"></el-descriptions-item>

        <el-descriptions-item label="简介"></el-descriptions-item>

        <el-descriptions-item label="出生日期"></el-descriptions-item>

        <el-descriptions-item label="联系地址"
          >江苏省苏州市吴中区吴中大道 1188 号</el-descriptions-item
        >
      </el-descriptions>
    </div>
    <div class="single-block">
      <el-descriptions title="教育信息" :column="1" size="medium">
        <template #extra>
          <el-button type="text" size="medium" class="el-icon-edit"
            >编辑</el-button
          >
        </template>
        <el-descriptions-item label="学校名称"></el-descriptions-item>

        <el-descriptions-item label="专业"></el-descriptions-item>

        <el-descriptions-item label="入学时间"></el-descriptions-item>

        <el-descriptions-item label="学历"></el-descriptions-item>
      </el-descriptions>
    </div>
    <div class="single-block">
      <el-descriptions title="教育信息" :column="1" size="medium">
        <template #extra>
          <el-button type="text" size="medium" class="el-icon-edit"
            >编辑</el-button
          >
        </template>
        <el-descriptions-item label="学校名称"></el-descriptions-item>

        <el-descriptions-item label="专业"></el-descriptions-item>

        <el-descriptions-item label="入学时间"></el-descriptions-item>

        <el-descriptions-item label="学历"></el-descriptions-item>
      </el-descriptions>
    </div>
    <div class="single-block">
      <el-descriptions title="教育信息" :column="1" size="medium">
        <template #extra>
          <el-button type="text" size="medium" class="el-icon-edit"
            >编辑</el-button
          >
        </template>
        <el-descriptions-item label="学校名称"></el-descriptions-item>

        <el-descriptions-item label="专业"></el-descriptions-item>

        <el-descriptions-item label="入学时间"></el-descriptions-item>

        <el-descriptions-item label="学历"></el-descriptions-item>
      </el-descriptions>
    </div>
    <div class="single-block">
      <el-descriptions title="教育信息" :column="1" size="medium">
        <template #extra>
          <el-button type="text" size="medium" class="el-icon-edit"
            >编辑</el-button
          >
        </template>
        <el-descriptions-item label="学校名称"></el-descriptions-item>

        <el-descriptions-item label="专业"></el-descriptions-item>

        <el-descriptions-item label="入学时间"></el-descriptions-item>

        <el-descriptions-item label="学历"></el-descriptions-item>
      </el-descriptions>
    </div>

    <!-- 头像上传框 -->
    <el-dialog title="上传头像" v-model="centerDialogVisible" width="30%">
      <el-upload
        :action="filesUploadUrl"
        :on-success="uploadSuccess"
        ref="upload"
        align="center"
      >
        <el-button size="medium" type="danger">点击上传</el-button>
        <template #tip>
          <div class="el-upload__tip">
            jpg/png files with a size less than 500kb
          </div>
        </template>
      </el-upload>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="centerDialogVisible = false">取 消</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Profile",
  data() {
    return {
      user: {
        token: "",
        userid: "",
      },
      userinfo: {},
      status: 0,
      centerDialogVisible: false,
      filesUploadUrl: "http://" + window.server.ip + ":9090/userinfo/upload",
    };
  },
  created() {
    this.user.token = JSON.parse(window.localStorage.getItem("access-token"));
    this.user.userid = JSON.parse(window.localStorage.getItem("access-userid"));
    if (this.user.token === null || this.user.userid === null) {
      this.$router.push("/login");
    } else {
      request
        .get("http://" + window.server.ip + ":9090/userinfo", {
          params: {
            userid: this.user.userid,
          },
        })
        .then((res) => {
          this.userinfo = res;
        });
    }
  },
  methods: {
    mouseOver() {
      this.status = 1;
    },
    mouseLeave() {
      this.status = 0;
    },
    uploadSuccess(response) {
      this.userinfo.avatar = response.data;
      request
        .put("http://" + window.server.ip + ":9090/userinfo", this.userinfo)
        .then((res) => {
          if (res.code === "0") {
            this.$message({
              type: "success",
              message: " 上传成功",
            });
            this.$refs.upload.clearFiles();
            this.centerDialogVisible = false;
          } else {
            this.$message({
              type: "error",
              message: res.msg,
            });
          }
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