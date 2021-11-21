<template>
  <div>
    <Background
      style="position: fixed; z-index: -1; width: 100%; height: 100%"
      pic="2.jpg"
    />
    <!-- <Topvideo /> -->
    <Header position="fixed" title="Search" />
  </div>

  <div style="height: 280px; display: flex; padding: 130px">
    <div style="width: 30%; margin-right: 15px">
      <span class="search">我的搜索</span>
    </div>
    <div style="display: 1; width: 40%">
      <el-input
        placeholder="请输入内容"
        v-model="input"
        size="medium"
        @keypress.enter="enterSearch"
        clearable
      >
      </el-input>
    </div>
    <div style="width: 30%; margin-left: 15px">
      <el-button
        type="primary"
        @click="load"
        size="medium"
        class="el-icon-search"
      >
        搜 索</el-button
      >
    </div>
  </div>

  <div class="single-blogs" style="width: 70%; margin: 0 auto">
    <el-tabs v-model="activeName" @tab-click="handleClick" type="border-card">
      <el-tab-pane v-for="item in items" :key="item.num" :name="item.num">
        <template #label>
          <span><i :class="item.icon"></i> {{ item.name }}</span>
        </template>

        <div style="width: 80%">
          <div>
            <div id="show-blogs">
              <div
                v-for="item in tableData"
                class="single-blog"
                :key="item.id"
                @click="pushArticle(item)"
              >
                <div style="display: flex">
                  <div style="width: 30%" align="center">
                    <el-image style="height: 100px" :src="item.img">
                      <template #error>
                        <div class="image-slot">
                          <i :class="el - icon - picture - outline"></i>
                        </div>
                      </template>
                    </el-image>
                  </div>
                  <div style="margin-left: 20px; width: 70%">
                    <h1 class="title">{{ item.title }}</h1>
                    <div style="margin-top: 15px">
                      <span class="el-icon-date">发表于{{ item.time1 }}</span>
                      <el-divider direction="vertical"></el-divider>
                      <span class="el-icon-user">{{ item.author }}</span>
                    </div>
                    <div>
                      <span class="el-icon-receiving">{{ item.category }}</span>
                      <el-divider direction="vertical"></el-divider>
                      <span class="el-icon-price-tag">{{ item.tag }}</span>
                    </div>
                  </div>
                </div>

                <div style="margin-top: 20px">
                  <span>{{ item.content1.slice(0, 300) + "..." }}</span>
                </div>
              </div>
            </div>
            <div style="margin: 10px 0" align="center">
              <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="currentPage"
                :page-sizes="[5, 10, 20, 50, 100]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total"
              >
              </el-pagination>
            </div>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import request from "../utils/request";
import Topvideo from "@/components/Topvideo";
import Header from "@/components/Header";
import Background from "../components/Background.vue";

export default {
  name: "Result",
  components: {
    Topvideo,
    Header,
    Background,
  },
  data() {
    return {
      blogs: [],
      form: {},
      currentPage: 1,
      total: 0,
      tableData: [],
      pageSize: 10,
      input: "",
      activeName: "first",
      temp: "",
      items: [
        { name: "综 合", num: "first", icon: "el-icon-s-data" },
        { name: "分 类", num: "second", icon: "el-icon-s-grid" },
        { name: "标 签", num: "third", icon: "el-icon-price-tag" },
        { name: "作 者", num: "fourth", icon: "el-icon-user" },
      ],
    };
  },
  created() {
    this.$store.commit("newStatus", 0);
    this.enterSearch();
    this.input = this.search;
    this.load();
  },
  computed: {
    search() {
      return this.$store.state.search;
    },
  },
  watch: {
    search: "load",
  },
  methods: {
    //回车搜索
    enterSearch() {
      document.onkeydown = (e) => {
        let _key = window.event.keyCode;
        if (_key === 13) {
          this.load();
        }
      };
    },
    handleClick(tab, event) {
      //   console.log(tab.props.name); 标签页的name属性
      switch (tab.props.name) {
        case "first":
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
          break;

        case "second":
          request
            .get(
              "http://" + window.server.ip + ":9090/article/selectCategory",
              {
                params: {
                  pageNum: this.currentPage,
                  pageSize: this.pageSize,
                  category: this.temp,
                },
              }
            )
            .then((res) => {
              this.tableData = res.data.records;
              this.total = res.data.total;
            });
          break;

        case "third":
          request
            .get("http://" + window.server.ip + ":9090/article/selectTag", {
              params: {
                pageNum: this.currentPage,
                pageSize: this.pageSize,
                tag: this.temp,
              },
            })
            .then((res) => {
              this.tableData = res.data.records;
              this.total = res.data.total;
            });
          break;

        case "fourth":
          request
            .get("http://" + window.server.ip + ":9090/article/selectAuthor", {
              params: {
                pageNum: this.currentPage,
                pageSize: this.pageSize,
                author: this.temp,
              },
            })
            .then((res) => {
              this.tableData = res.data.records;
              this.total = res.data.total;
            });
          break;
      }
    },
    load() {
      this.temp = this.input;
      this.activeName = "first";
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
          //   console.log(this.tableData);
          //   console.log(this.tableData.length);
          this.total = res.data.total;
        });
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum) {
      this.currentPage = pageNum;
      this.load();
    },
    pushArticle(item) {
      this.$router.push({
        name: "Article",
        params: {
          id: item.id,
        },
      });
    },
  },
};
</script>

<style scoped>
#show-blogs {
  margin: 0 auto;
}
.single-blogs {
  padding: 30px;
  margin: 20px 0;
  box-shadow: border-box;
  background: rgba(255, 255, 255, 1);
  border-radius: 15px;
}
.single-blog {
  padding: 30px;
  margin: 20px 0;
  margin-left: 10px;
  box-shadow: border-box;
  background: rgba(255, 255, 255, 1);
  border-radius: 15px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.2);
}
.search {
  float: right;
  color: white;
  font-weight: bold;
  margin-top: 5px;
  font-size: 150%;
  font-family: "楷体";
}
</style>