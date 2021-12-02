<template>
  <div>
    <Background
      style="position: fixed; z-index: -1; width: 100%; height: 100%"
      pic="2.jpg"
    />
    <Header position="fixed" title="Animation" />
    <About name="追 番" />
  </div>
  <div style="display: flex; width: 70%; margin: 0 auto">
    <div style="width: 70%">
      <div id="classify-block">
        <div class="single-block">
          <div
            align="center"
            style="font-size: 200%; font-family: '楷体'; margin-bottom: 10px"
          >
            看番记录
          </div>
          <div align=center>
          <el-divider><i class="el-icon-monitor"></i></el-divider>
          <span>统计了作者本人看过的番剧，单纯记录，按照看完的时间正序排序</span>
          <el-divider><i class="el-icon-mobile-phone"></i></el-divider>
          </div>
          <div class="block">
            <el-timeline>
              <el-timeline-item
                v-for="animation in animationList"
                :key="animation.id"
                :timestamp="animation.time"
                placement="top"
                color="#0bbd36"
                :hollow="true"
              >
                <el-card>
                  <h3>{{ animation.name }}</h3>
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
    </div>
    <div style="width: 30%">
      <div style="margin: 0 auto"><SelfBlock /><InfoBlock /><TagBlock /><TimeBlock /></div>
    </div>
  </div>
  <Footer />
</template>

<script>
import request from "../utils/request";
import Header from "@/components/Header";
import About from "@/components/About";
import Footer from "@/components/Footer";
import SelfBlock from "@/components/SelfBlock";
import InfoBlock from "@/components/InfoBlock";
import TimeBlock from "@/components/TimeBlock";
import TagBlock from "@/components/TagBlock";
import Background from "../components/Background.vue";

export default {
  name: "Animation",
  components: {
    Header,
    About,
    Footer,
    SelfBlock,
    InfoBlock,
    TagBlock,
    TimeBlock,
    Background,
  },
  data() {
    return {
      animationList: [],
      currentPage: 1,
      total: 0,
      pageSize: 10,
      search: "",
    };
  },
  created() {
    this.$store.commit("newStatus", 0);
    this.load();
  },
  methods: {
    load() {
      request
        .get("http://" + window.server.ip + ":9090/animation", {
          params: {
            pageNum: this.currentPage,
            pageSize: this.pageSize,
            search: this.search,
          },
        })
        .then((res) => {
          this.animationList = res.data.records;
          this.total = res.data.total;
        });
    },
    handleCurrentChange(pageNum) {
      this.currentPage = pageNum;
      this.load();
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
  box-shadow: border-box;
  background: rgba(255, 255, 255, 1);
  border-radius: 15px;
}
</style>