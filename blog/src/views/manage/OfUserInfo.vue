<template>
  <div class="total">
    <div style="padding: 20px">
      <el-table
        :data="
          tableData.filter(
            (data) =>
              !search ||
              data.userid.toLowerCase().includes(search.toLowerCase())
          )
        "
        style="width: 100%"
        height="calc(100vh - 140px)"
        @selection-change="handleSelectionChange"
        stripe
      >
        <el-table-column type="selection" width="35"> </el-table-column>
        <el-table-column fixed label="账号" prop="userid"></el-table-column>
        <el-table-column fixed label="昵称" prop="username"></el-table-column>
        <el-table-column label="角色" prop="role" sortable></el-table-column>
        <el-table-column label="性别" prop="sex" sortable></el-table-column>
        <el-table-column label="生日" prop="birth" sortable></el-table-column>
        <el-table-column label="地址" prop="address"></el-table-column>
        <el-table-column fixed="right" width="150px">
          <template #header>
            <el-input
              v-model="search"
              size="mini"
              placeholder="输入关键字搜索"
              clearable
            />
          </template>
          <template #default="scope">
            <el-popconfirm
              title="确定删除吗？"
              @confirm="handleDelete(scope.row.userid)"
            >
              <template #reference>
                <el-button size="mini" type="danger" style="float: right"
                  >删除
                </el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <div style="display: flex">
        <div style="margin: 10px 0; flex: 1" align="center">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[5, 10, 20, 50, 100]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
          >
          </el-pagination>
        </div>
        <div style="width: 102px">
          <el-popconfirm title="确定删除吗？" @confirm="listDelete">
            <template #reference>
              <el-button type="danger" style="margin-top: 10px"
                >批量删除</el-button
              >
            </template>
          </el-popconfirm>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "OfUserInfo",
  components: {},
  data() {
    return {
      currentPage: 1,
      total: 0,
      tableData: [],
      search: "",
      pageSize: 10,
      form: {},
      dialog: false,
      formLabelWidth: "70px",
      multipleSelection: [],
    };
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      request
        .get("http://" + window.server.ip + ":9090/userinfo/page", {
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
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    listDelete() {
      var res = new Array();
      this.multipleSelection.forEach(function (item) {
        //把要删除的id加入到一个数组中去
        res.push(item.userid);
      });
      //只能？传参，如果用params会报错
      request
        .delete(
          "http://" +
            window.server.ip +
            ":9090/userinfo/deleteIds?selection=" +
            res
        )
        .then((res) => {
          if (res.code === "0") {
            this.$message({
              type: "success",
              message: "删除成功",
            });
          } else {
            this.$message({
              type: "error",
              message: res.msg,
            });
          }
          this.load();
        });
    },
    handleDelete(userid) {
      request
        .delete("http://" + window.server.ip + ":9090/userinfo/" + userid)
        .then((res) => {
          if (res.code === "0") {
            this.$message({
              type: "success",
              message: "删除成功",
            });
          } else {
            this.$message({
              type: "error",
              message: res.msg,
            });
          }
          this.load();
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
  },
};
</script>

<style scoped>
.total {
  width: calc(100vw - 200px);
  height: calc(100vh - 45px);
  background-color: rgb(240, 240, 240);
  opacity: 0.95;
}
</style>