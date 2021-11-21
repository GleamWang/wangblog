<template>
  <div id="classify-block">
    <div class="single-block">
      <div
        align="center"
        style="
          font-size: 150%;
          font-family: '楷体';
          font-weight: bold;
          margin-bottom: 10px;
        "
      >
        #分类-{{ this.category }}
      </div>
      <div class="block">
        <el-timeline>
          <el-timeline-item
            v-for="categoryItem in categoryList"
            :key="categoryItem.id"
            :timestamp="categoryItem.time1"
            placement="top"
            color='#0bbd87'
            :hollow='true'
          >
            <el-card @click="pushArticle(categoryItem.id)">
              <h3>{{ categoryItem.title }}</h3>
              <span class="el-icon-price-tag" style="margin-top: 5px">{{
                categoryItem.tag
              }}</span>
              <p style="margin-top: 5px">
                {{ categoryItem.content1.slice(0, 190) + "..." }}
              </p>
            </el-card>
          </el-timeline-item>
        </el-timeline>
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
import request from "../../utils/request";

export default {
  name: "PartClassify",
  props: ["category"],
  data() {
    return {
      categoryList: [],
      currentPage: 1,
      total: 0,
      pageSize: 5,
    };
  },
  created() {
    this.$store.commit("newStatus", 1);
    this.load();
  },
  methods: {
    load() {
      request
        .get("http://" + window.server.ip + ":9090/article/selectCategory", {
          params: {
            pageNum: this.currentPage,
            pageSize: this.pageSize,
            category: this.category,
          },
        })
        .then((res) => {
          this.categoryList = res.data.records;
          this.total = res.data.total;
        });
    },
    handleCurrentChange(pageNum) {
      this.currentPage = pageNum;
      this.load();
    },
    pushArticle(id) {
      this.$router.push({
        name: "Article",
        params: {
          id: id,
        },
      });
    },
  },
};
</script>

<style scoped>
#classify-block {
  margin: 0 auto;
}
.single-block {
  padding: 20px;
  margin: 20px 0;
  margin-bottom: 200px;
  box-shadow: border-box;
  background: rgba(255, 255, 255, 1);
  border-radius: 15px;
}
.tag {
  margin: 10px;
}
</style>