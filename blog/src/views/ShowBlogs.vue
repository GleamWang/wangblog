<template>
  <div>
    <div id="show-blogs">
      <div v-for="item in tableData" class="single-blog" :key="item.id" @click="pushArticle(item)">
        <div style="display: flex">
          <div style="width: 30%">
            <el-image style="" :src="item.img">
              <template #error>
                <div class="image-slot">
                  <i class="el-icon-picture-outline"></i>
                </div>
              </template>
            </el-image>
          </div>
          <div style="margin-left: 20px; width: 70%">
            <h2 class="title">{{ item.title }}</h2>
            <div style="margin-top: 10px">
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
          <span>{{ item.content1.slice(0, 200) + "..." }}</span>
          <!-- <span v-text="item.content1"></span> -->
          <!-- <span v-html="item.content2"></span> -->
        </div>
      </div>
    </div>
    <div style="margin: 10px 0" align="center">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[5, 10, 20, 50, 250]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
import request from "../utils/request";

export default {
  name: "ShowBlogs",
  data() {
    return {
      blogs: [],
      form: {},
      currentPage: 1,
      total: 0,
      tableData: [],
      pageSize: 10,
    };
  },
  created() {
    this.load();
  },
  computed: {
    search() {
      return this.$store.state.search
    }
  },
  watch:{
    search:'load'
  },
  methods: {
    load() {
      request
        .get("http://" + window.server.ip + ":9090/article", {
          params: {
            pageNum: this.currentPage,
            pageSize: this.pageSize,
            search: this.search,
          },
        })
        .then((res) => {
          this.tableData = res.data.records;
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
    pushArticle(item){
        this.$router.push({
        name: "Article",
        params: {
          id: item.id,
        },
      });
    }
  },
};
</script>

<style scoped>
#show-blogs {
  margin: 0 auto;
}
.single-blog {
  padding: 30px;
  margin: 20px 0;
  box-shadow: border-box;
  background: rgba(255, 255, 255, 1);
  border-radius: 15px;
}
</style>