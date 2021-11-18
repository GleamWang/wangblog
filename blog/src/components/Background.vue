<template>
  <div>
    <img
      :src="require(`../assets/background/${pic}`)"
      style="width: 100%; height: 100%"
      alt=""
    />
  </div>
</template>

<script>
export default {
  name: "Background",
  props: {
    pic: String,
  },
  data() {
    return {};
  },
};
</script>

<style scoped>
</style>