<template>
  <div id="classify-block">
    <div class="single-block">
      <div
        align="center"
        style="
          font-size: 270%;
          font-family: '楷体';
          font-weight: bold;
          margin-bottom: 10px;
        "
      >
        #分类-
      </div>
      <div class="block">
        <el-timeline>
          <el-timeline-item
            v-for="(activity, index) in activities"
            :key="index"
            :icon="activity.icon"
            :type="activity.type"
            :color="activity.color"
            :size="activity.size"
            :hollow="activity.hollow"
            @click="pushClassify(activity.content)"
          >
            {{ activity.content }}
          </el-timeline-item>
        </el-timeline>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "TotalClassify",
  data() {
    return {
      activities: [
        {
          content: "Java",
          size: "large",
          type: "primary",
          icon: "el-icon-more",
        },
        {
          content: "Springboot",
          color: "#0bbd87",
        },
        {
          content: "MySql",
          size: "large",
        },
        {
          content: "JVM",
          type: "primary",
          hollow: true,
        },
        {
          content: "SpringCloud",
        },
      ],
    };
  },
  methods: {
    pushClassify(content) {
      this.$router.push({
        name: "PartClassify",
        params: {
          category: content,
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
  box-shadow: border-box;
  background: rgba(255, 255, 255, 1);
  border-radius: 15px;
}
.block {
  margin-left: 30px;
}
</style>