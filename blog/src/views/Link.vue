<template>
  <div>
    <Background
      style="position: fixed; z-index: -1; width: 100%; height: 100%"
      pic="3.jpg"
    />
    <Header position="fixed" title="Link" />
    <About name="友情链接" />
  </div>
  <div style="display: flex; width: 70%; margin: 0 auto">
    <div style="width: 60%">
      <div id="classify-block">
        <div class="single-block">
          <div
            align="center"
            style="
              font-size: 200%;
              font-family: '楷体';
              margin-bottom: 10px;
            "
          >
            
          </div>
          <div class="block">

          </div>
        </div>
      </div>
    </div>
    <div style="width: 40%">
      <div style="margin: 0 auto"><SelfBlock /><WarnBlock /><InfoBlock /><TagBlock /></div>
    </div>
  </div>
</template>

<script>
import Header from "@/components/Header";
import About from "@/components/About";
import SelfBlock from "@/components/SelfBlock";
import InfoBlock from "@/components/InfoBlock";
import WarnBlock from "@/components/WarnBlock";
import TagBlock from "@/components/TagBlock";
import Background from "../components/Background.vue";

export default {
  name: "Link",
  components: {
    Header,
    About,
    SelfBlock,
    InfoBlock,
    WarnBlock,
    TagBlock,
    Background,
  },
  data() {
    return {
    };
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