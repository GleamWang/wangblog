<template>
  <div class="total">
    <el-drawer
      title="请输入需要编辑的内容"
      v-model="dialog"
      direction="rtl"
      custom-class="demo-drawer"
      ref="drawer"
      @close="resetForm('form')"
    >
      <div class="demo-drawer__content">
        <el-form :model="form" ref="form">
          <el-form-item
            label="标题"
            :label-width="formLabelWidth"
            prop="title"
            :rules="[
              { required: true, message: '标题不能为空', trigger: 'blur' },
            ]"
          >
            <el-input
              v-model="form.title"
              autocomplete="off"
              placeholder="请输入标题"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="分类"
            :label-width="formLabelWidth"
            prop="category"
            :rules="[
              { required: true, message: '分类不能为空', trigger: 'blur' },
            ]"
          >
            <el-input
              v-model="form.category"
              autocomplete="off"
              placeholder="请输入分类"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="标签"
            :label-width="formLabelWidth"
            prop="tag"
            :rules="[
              { required: true, message: '标签不能为空', trigger: 'blur' },
            ]"
          >
            <el-input
              v-model="form.tag"
              autocomplete="off"
              placeholder="请输入标签"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="作者"
            :label-width="formLabelWidth"
            prop="author"
          >
            <el-input
              v-model="form.author"
              autocomplete="off"
              placeholder="请输入作者"
            ></el-input>
          </el-form-item>
          <el-form-item label="封面" :label-width="formLabelWidth" prop="img">
            <el-input
              type="textarea"
              v-model="form.img"
              autocomplete="off"
              placeholder="请输入封面"
            ></el-input>
          </el-form-item>
        </el-form>
        <div class="demo-drawer__footer" style="margin-left: 80px">
          <el-button @click="cancelForm">取 消</el-button>
          <el-button type="primary" @click="submitForm('form')"
            >确 定</el-button
          >
        </div>
      </div>
    </el-drawer>

    <div style="padding: 20px">
      <el-table
        :data="
          tableData.filter(
            (data) =>
              !search || data.title.toLowerCase().includes(search.toLowerCase())
          )
        "
        style="width: 100%"
        height="calc(100vh - 140px)"
        @selection-change="handleSelectionChange"
        stripe
      >
        <el-table-column type="selection" width="35"> </el-table-column>
        <el-table-column fixed label="标题" prop="title"></el-table-column>
        <el-table-column
          label="分类"
          prop="category"
          width="90"
        ></el-table-column>
        <el-table-column label="标签" prop="tag"></el-table-column>
        <el-table-column
          label="作者"
          prop="author"
          width="60"
        ></el-table-column>
        <el-table-column label="最后更新时间" prop="time2" sortable></el-table-column>
        <el-table-column label="封面" prop="img" width="250"></el-table-column>
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
            <el-button size="mini" @click="handleEdit(scope.row)"
              >编辑
            </el-button>
            <el-popconfirm
              title="确定删除吗？"
              @confirm="handleDelete(scope.row.id)"
            >
              <template #reference>
                <el-button size="mini" type="danger">删除 </el-button>
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
  name: "OfArticle",
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
        .get("http://" + window.server.ip + ":9090/article", {
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
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row));
      this.dialog = true;
      console.log(row);
      console.log(this.form);
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    listDelete() {
      var res = new Array();
      this.multipleSelection.forEach(function (item) {
        //把要删除的id加入到一个数组中去
        res.push(item.id);
      });
      //只能？传参，如果用params会报错
      request
        .delete(
          "http://" +
            window.server.ip +
            ":9090/article/deleteIds?selection=" +
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
    handleDelete(id) {
      request
        .delete("http://" + window.server.ip + ":9090/article/" + id)
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

    cancelForm() {
      this.dialog = false;
      this.resetFields();
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request
            .put("http://" + window.server.ip + ":9090/article", this.form)
            .then((res) => {
              console.log(res);
              if (res.code === "0") {
                this.$message({
                  type: "success",
                  message: "编辑成功",
                });
              } else {
                this.$message({
                  type: "error",
                  message: res.msg,
                });
              }
              this.load(); //刷新表格数据
              this.dialog = false; //关闭弹窗
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
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