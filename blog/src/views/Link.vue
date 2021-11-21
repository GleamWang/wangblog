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
    <div style="width: 70%">
        <div class="single-block">
        </div>
  
    </div>
    <div style="width: 30%">
      <div style="margin: 0 auto"><SelfBlock /><WarnBlock /><InfoBlock /><TagBlock /><TimeBlock /></div>
    </div>
  </div>
</template>

<script>
import Header from "@/components/Header";
import About from "@/components/About";
import SelfBlock from "@/components/SelfBlock";
import InfoBlock from "@/components/InfoBlock";
import WarnBlock from "@/components/WarnBlock";
import TimeBlock from "@/components/TimeBlock";
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
    TimeBlock,
    TagBlock,
    Background,
  },
  data() {
    return {
    };
  },
  created(){
    this.$store.commit("newStatus", 0);
  }
};
</script>

<style scoped>
.single-block {
  padding: 20px;
  height: 300px;
  margin: 20px 0;
  box-shadow: border-box;
  background: rgba(255, 255, 255, 1);
  border-radius: 15px;
}
</style>