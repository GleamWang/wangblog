<template>
  <div class="single-block">
    <div>
      <h3 style="margin-top: 8px">内容管理</h3>

      <div style="display: flex; margin-top: 20px; width: 100%">
        <div style="width: 10%"></div>
        <div style="display: 1; width: 70%">
          <el-input
            placeholder="请输入内容"
            v-model="input"
            size="mini"
            @keypress.enter="enterSearch"
            clearable
          >
          </el-input>
        </div>
        <div style="width: 30%; margin-left: 10px">
          <el-button
            type="primary"
            @click="load"
            size="mini"
            class="el-icon-search"
          >
            搜 索</el-button
          >
        </div>
      </div>

      <div style="margin-top: 20px">
        <el-card
          class="box-card"
          shadow="hover"
          v-for="item in tableData"
          :key="item.id"
          style="margin-bottom:2px"
        >
          <div style="display: flex">
            <div style="width: 20%" align="center">
              <el-image
                style="height: 50px"
                :src="item.img"
                fit="contain"
                @click="pushArticle(item)"
              ></el-image>
            </div>
            <div
              style="width: 40%; display: 1; font-size: 80%; margin-left: 10px"
            >
              <el-button
                type="text"
                style="color: black; margin-top: -10px"
                @click="pushArticle(item)"
                >{{ item.title }}</el-button
              >
            </div>
            <div style="width: 40%; font-size: 80%">
              <span style="float: right">{{ item.time1 }}</span>
              <br />
              <el-button
                type="text"
                style="color: black; float: right"
                @click="pushArticle(item)"
                >编辑</el-button
              >
            </div>
          </div>
        </el-card>
      </div>

      <el-pagination
        small
        layout="total, prev, pager, next, jumper"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[5, 10, 20, 50, 100]"
        :page-size="pageSize"
        :total="total"
        align="center"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Content",
  data() {
    return {
      input: "",
      username: "",
      tableData: [],
      currentPage: 1,
      total: 0,
      pageSize: 10,
    };
  },
  created() {
    this.enterSearch();
    //初始化用户昵称
    this.initUsername();
    this.load();
  },
  methods: {
    enterSearch() {
      document.onkeydown = (e) => {
        let _key = window.event.keyCode;
        if (_key === 13) {
          this.load();
        }
      };
    },
    load() {
      request
        .get("http://" + window.server.ip + ":9090/article", {
          params: {
            pageNum: this.currentPage,
            pageSize: this.pageSize,
            search: this.input,
          },
        })
        .then((res) => {
          this.tableData = res.data.records;
          this.total = res.data.total;
        });
    },
    //通过userid获取username并赋值
    initUsername() {
      let userid = JSON.parse(window.localStorage.getItem("access-userid"));
      request
        .get("http://" + window.server.ip + ":9090/userinfo/selectUsername", {
          params: { userid: userid },
        })
        .then((res) => {
          //初始化文章的作者
          this.username = res.username;
        });
    },
    handleCurrentChange(pageNum) {
      this.currentPage = pageNum;
      this.load();
    },
    pushArticle(item) {
      this.$router.push({
        name: "Modify",
        params: {
          id: item.id,
        },
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