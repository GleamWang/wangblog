<template>
  <Background
    style="position: fixed; z-index: -1; width: 100%; height: 100%"
    pic="2.jpg"
  />
  <router-view />
</template>

<script>
import Background from "@/components/Background.vue";

export default {
  name: "Error",
  components: {
    Background,
  },
  data() {
    return {};
  },
};
</script>

<style>
</style>