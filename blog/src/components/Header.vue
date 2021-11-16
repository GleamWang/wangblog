<template>
  <div
    :style="`z-index: 999;
      position: ${position};
      top: 0;
      width: 100%;
      box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);`"
    v-show="status"
  >
    <div style="height: 45px; display: flex" id="header">
      <div
        style="
          width: 150px;
          font-weight: bold;
          color: white;
          text-align: center;
          margin-top: 10px;
          font-size: 120%;
        "
      >
        {{ this.title }}
      </div>
      <div style="flex: 1"></div>
      <div style="margin-top: 6.5px; margin-right: 20px">
        <el-input
          placeholder="请输入内容"
          v-model="input"
          class="input-with-select"
        >
          <template #append>
            <el-button icon="el-icon-search" @click="setSearch"></el-button>
          </template>
        </el-input>
      </div>

      <!-- 未登录用户信息栏 上 -->
      <div class="popover" :style="style1">
        <el-popover
          placement="bottom"
          :width="200"
          trigger="hover"
          @show="change"
          @hide="hide"
          :offset="50"
        >
          <template #reference>
            <div class="block">
              <el-avatar
                v-show="show"
                :size="40"
                :src="circleUrl"
                style="margin-top: 2px"
              ></el-avatar>
            </div>
          </template>

          <div class="block" align="center" style="margin-top: -50px">
            <el-avatar v-show="!show" :size="70" :src="circleUrl"></el-avatar>
          </div>
          <div align="center" style="font-size: 150%; font-weight: bold">
            尚未登录
          </div>
          <div align="center">
            <el-tag type="danger" effect="dark">游客账号</el-tag>
          </div>
          <el-divider
            style="margin-top: 10px; margin-bottom: 10px"
          ></el-divider>
          <div align="center">
            没有账号？前往
            <el-button
              type="text"
              size="medium"
              @click="this.$router.push('/login')"
            >
              登录/注册</el-button
            >
          </div>
        </el-popover>
      </div>
      <!-- 未登录用户信息栏 下 -->

      <!-- 登录后的用户信息栏 上 -->
      <div class="popover" :style="style2">
        <el-popover
          placement="bottom"
          :width="200"
          trigger="hover"
          @show="change"
          @hide="hide"
          :offset="50"
        >
          <template #reference>
            <div class="block">
              <el-avatar
                v-show="show"
                :size="40"
                :src="this.userinfo.avatar"
                style="margin-top: 2px"
              ></el-avatar>
            </div>
          </template>

          <div class="block" align="center" style="margin-top: -50px">
            <el-avatar
              v-show="!show"
              :size="70"
              :src="this.userinfo.avatar"
            ></el-avatar>
          </div>
          <div align="center" style="font-size: 150%; font-weight: bold">
            {{ this.userinfo.username }}
          </div>
          <div align="center">
            <el-tag type="danger" effect="dark">
              {{ this.userinfo.role }}
            </el-tag>
          </div>
          <el-divider
            style="margin-top: 10px; margin-bottom: 10px"
          ></el-divider>
          <div>
            <el-button
              type="text"
              class="el-icon-user"
              size="medium"
              style="color: black"
              @click="this.$router.push('/space/profile')"
            >
              个人中心</el-button
            >
          </div>
          <div>
            <el-button
              type="text"
              class="el-icon-setting"
              size="medium"
              style="color: black"
              @click="this.$router.push('/login')"
            >
              安全退出</el-button
            >
          </div>
        </el-popover>
      </div>
      <!-- 登录后的用户信息栏 下 -->

      <div style="width: 430px; color: white">
        <nav>
          <ul>
            <li>
              <el-button
                type="text"
                class="el-icon-s-home"
                size="medium"
                @click="this.$router.push('/blogs')"
                style="color: white; font-size: 95%"
                >首页</el-button
              >
            </li>
            <li>
              <el-button
                type="text"
                class="el-icon-s-grid"
                size="medium"
                @click="this.$router.push('/classify')"
                style="color: white; font-size: 95%"
                >分类</el-button
              >
            </li>
            <li>
              <el-button
                type="text"
                class="el-icon-price-tag"
                size="medium"
                @click="this.$router.push('/tag')"
                style="color: white; font-size: 95%"
                >标签</el-button
              >
            </li>
            <li>
              <el-button
                disabled
                type="text"
                class="el-icon-star-on"
                size="medium"
                @click="this.$router.push('/animation')"
                style="color: white; font-size: 95%"
                >追番</el-button
              >
            </li>
            <li>
              <el-button
                type="text"
                class="el-icon-paperclip"
                size="medium"
                @click="this.$router.push('/link')"
                style="color: white; font-size: 95%"
                >友链</el-button
              >
            </li>
            <li>
              <el-button
                type="text"
                class="el-icon-info"
                size="medium"
                @click="this.$router.push('/author')"
                style="color: white; font-size: 95%"
                >关于作者</el-button
              >
            </li>
          </ul>
        </nav>
      </div>
      <div>
        <el-button
          type="danger"
          style="width: 90px; margin-top: 8px; margin-right: 20px"
          @click="this.$router.push('/creation')"
          >写 博 客</el-button
        >
      </div>
    </div>
  </div>
</template>

<script>
import request from "../utils/request";

export default {
  name: "Header",
  props: {
    position: String,
    title: String,
  },
  data() {
    return {
      user: {
        token: "",
        userid: "",
      },
      userinfo: {},
      style1: {
        display: "",
      },
      style2: {
        display: "",
      },
      filesUploadUrl: "",
      input: "",
      status: true, //v-show参数
      init: 0, //滚动条距离顶部的初始值
      circleUrl:
        "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
      show: true,
    };
  },
  created() {
    this.user.token = JSON.parse(window.localStorage.getItem("access-token"));
    this.user.userid = JSON.parse(window.localStorage.getItem("access-userid"));
    if (this.user.token === null || this.user.userid === null) {
      this.style2.display = "none";
    } else {
      this.style1.display = "none";
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
  mounted() {
    // window.addEventListener("mousewheel", this.handleWheel);
    // window.addEventListener("scroll", this.handleScroll, true);
  },
  methods: {
    uploadSuccess() {},
    // handleWheel(e) {
    //   let direction = e.deltaY > 0 ? "down" : "up"; //deltaY为正则滚轮向下，为负滚轮向上
    //   if (direction == "down" && e.deltaY >= 125) {
    //     this.status = false;
    //   }
    //   if (direction == "up" && e.deltaY <= -125) {
    //     this.status = true;
    //   }
    // },
    change() {
      this.show = false;
    },
    hide() {
      this.show = true;
    },
    setSearch() {
      this.$store.commit("newSearch", this.input);
      this.input = "";
      this.$router.push("/blogs");
    },
    // handleScroll(e) {
    //   // 滚动条距顶部距离
    //   let top = document.documentElement.scrollTop || document.body.scrollTop;
    //   if (top = this.init) {
    //     this.status = false;
    //   } else {
    //     this.status = true;
    //   }
    //   this.status = top;
    // },
  },
};
</script>

<style scoped>
.popover {
  width: 50px;
  margin-right: 5px;
}
ul {
  margin-top: 6px;
  list-style-type: none;
}
li {
  display: inline;
  margin: 0 8px;
}
</style>