<template>
  <div class="time_block">
    <div>
 <span style="font-size:110%"> {{ date }}</span>
    </div>
    <div>
      <span style="float:right;font-size:110%"> {{ time }}</span>
    </div>
    <div class="text-container">
      <div class="Line"></div>
    </div>
  </div>
</template>

<script>
export default {
  name: "TimeBlock",
  data() {
    return {
      date: "",
      time: "",
    };
  },
  mounted() {
    this.getdate();
    this.timer = setInterval(() => {
      this.getdate();
    }, 1000);
  },
  methods: {
    getdate: function () {
      let weeks = [
        "星期日",
        "星期一",
        "星期二",
        "星期三",
        "星期四",
        "星期五",
        "星期六",
      ];
      let time = new Date();
      let year = time.getFullYear();
      let month = time.getMonth() + 1;
      let day = time.getDate();
      let week = weeks[time.getDay()];
      this.getTime();
      this.date = year + "/" + month + "/" + day + " " + week;
    },
    getTime: function () {
      let time = new Date();
      let hour = time.getHours();
      let minutes = time.getMinutes();
      let seconds = time.getSeconds();
      if (minutes < 10) {
        minutes = "0" + minutes;
      }
      if (seconds < 10) {
        seconds = "0" + seconds;
      }
      this.time = hour + "：" + minutes + "：" + seconds;
    },
  },
};
</script>

<style scoped>
.time_block {
  margin-top: 20px;
  margin-left: 20px;
  width: 300px;
  box-shadow: border-box;
  background: rgba(255, 255, 255, 1);
  border-radius: 10px;
  padding: 20px 20px 0 20px;
  float: left;
}
.text-container {
  position: relative;
  margin: 80px auto;
  margin-top:90px;
  margin-bottom: 0;
  width: 150.5px;
  height: 100px;
  font-size: 50px;
  background-color: black;
  transform: scale(2);
  line-height: 100px;
  border-radius: 0 0 6px 6px;
}

.Line {
  position: absolute;
  width: 100%;
  height: 1px;
  background: #000;
  z-index: 1;
  animation: LineMove 5s ease-out infinite;
}

.text-container::before {
  position: absolute;
  /* content: attr(data-attr); */
  content: "Gleam";
  top: 0;
  left: 0px;
  color: red;
  text-shadow: 1px 0 0 red;
  animation: move 0.95s infinite;
}

.text-container::after {
  position: absolute;
  /* content: attr(data-time); */
  content: "Gleam";
  top: 0;
  left: -3px;
  color: cyan;
  text-shadow: -1px 0 0 cyan;
  animation: move 1.1s infinite 0.2s;
  mix-blend-mode: lighten;
}

@keyframes LineMove {
  9% {
    top: 38px;
  }

  14% {
    top: 8px;
  }

  18% {
    top: 42px;
  }

  22% {
    top: 1px;
  }

  32% {
    top: 32px;
  }

  34% {
    top: 12px;
  }

  40% {
    top: 26px;
  }

  43% {
    top: 7px;
  }

  99% {
    top: 30px;
  }
}

@keyframes move {
  10% {
    top: -0.4px;
    left: -1.1px;
  }

  20% {
    top: 0.4px;
    left: -0.2px;
  }

  30% {
    left: 0.5px;
  }

  40% {
    top: -0.3px;
    left: -0.7px;
  }

  50% {
    left: 0.2px;
  }

  60% {
    top: 1.8px;
    left: -1.2px;
  }

  70% {
    top: -1px;
    left: 0.1px;
  }

  80% {
    top: -0.4px;
    left: -0.9px;
  }

  90% {
    left: 1.2px;
  }

  100% {
    left: -1.2px;
  }
}
</style>