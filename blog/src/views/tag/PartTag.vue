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
        #标签-{{ this.tag }}
      </div>
      <div class="block">
        <el-timeline>
          <el-timeline-item
            v-for="tagItem in tagList"
            :key="tagItem.id"
            :timestamp="tagItem.time1"
            placement="top"
            color='#0bbd87'
            :hollow='true'
          >
            <el-card @click="pushArticle(tagItem.id)">
              <h3>{{ tagItem.title }}</h3>
              <span class="el-icon-price-tag" style="margin-top: 5px">{{
                tagItem.tag
              }}</span>
              <p style="margin-top: 5px">
                {{ tagItem.content1.slice(0, 190) + "..." }}
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
        :page-sizes="[5, 10, 20, 50, 250]"
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
  name: "PartTag",
  props: ["tag"],
  data() {
    return {
      tagList: [],
      currentPage: 1,
      total: 0,
      pageSize: 5,
    };
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      request
        .get("http://" + window.server.ip + ":9090/article/selectTag", {
          params: {
            pageNum: this.currentPage,
            pageSize: this.pageSize,
            tag: this.tag,
          },
        })
        .then((res) => {
          this.tagList = res.data.records;
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