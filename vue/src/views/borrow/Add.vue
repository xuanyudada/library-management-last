<template>
  <div style="width: 80%">
    <div style="margin-bottom: 30px">新增借书记录</div>
    <el-form :inline="true" :rules="rules" ref="ruleForm" :model="form" label-width="100px">
      <el-form-item label="图书标准码" prop="description">
        <el-select v-model="form.bookNo" clearable filterable placeholder="请选择" @change="selBook" >
          <el-option
              v-for="item in books"
              :key="item.id"
              :label="item.bookNo"
              :value="item.bookNo">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="图书名称" prop="name">
        <el-input v-model="form.bookName" disabled placeholder="请输入名称"></el-input>
      </el-form-item>
      <el-form-item label="所需积分" prop="cover">
        <el-input v-model="form.score" disabled ></el-input>
      </el-form-item>
      <el-form-item label="用户ID" prop="userId">
        <el-select v-model="form.userId" filterable placeholder="请选择">
          <el-option
              v-for="item in users"
              :key="item.id"
              :label="item.name"
              :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="用户名称" prop="userName">
        <el-input v-model="form.userName" ></el-input>
      </el-form-item>
      <el-form-item label="用户联系方式" prop="userPhone">
        <el-input v-model="form.userPhone" ></el-input>
      </el-form-item>
    </el-form>

    <div style="text-align: center;margin-top: 30px">
      <el-button type="primary" @click="save" size="medium">提交</el-button>
    </div>
  </div>
</template>

<script>
import request from "@/untils/request";

export default {
  name: 'AddBook',
  data() {
    return {
      form: {},
      books:[],
      users:[],
      rules: {
        name: [
          { required: true, message: '请输入图书名称', trigger: 'blur'},
        ],
        bookNo: [
          { required: true, message: '请输入图书的标准码', trigger: 'blur'},
        ],
        score: [
          { required: true, message: '请输入借书积分', trigger: 'blur'},
        ],
      }
    }
  },
  created(){
    request.get('/book/list').then(res => {
      this.books = res.data
    })

    request.get('/user/list').then(res => {
      this.users = res.data
    })
  },
  methods: {
    save() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          request.post('/book/save', this.form).then(res => {
            if (res.code === '200') {
              this.$notify.success('新增成功')
              this.$refs['ruleForm'].resetFields()
            } else {
              this.$notify.error(res.msg)
            }
          })
        }
      })
    },
    selBook(){
      const book = this.books.find(v => v.bookNo === this.form.bookNo)
      this.form.bookName = book.name
      this.form.score = book.score
    }
  }
}
</script>
