<template>
  <div style="width: 80%">
    <div style="margin-bottom: 30px">编辑图书</div>
    <el-form :inline="true" :rules="rules" ref="ruleForm" :model="form" label-width="100px">
      <el-form-item label="名称" prop="name">
        <el-input v-model="form.name" placeholder="请输入名称"></el-input>
      </el-form-item>
      <el-form-item label="描述" prop="description">
        <el-input style="width: 400px" type="textarea" v-model.number="form.description" placeholder="请输入描述"></el-input>
      </el-form-item>
      <el-form-item label="出版日期" prop="publishDate">
        <el-date-picker
            style="width: 85%"
            v-model="form.publishDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择出版日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="作者" prop="author">
        <el-input v-model.number="form.author" placeholder="请输入作者"></el-input>
      </el-form-item>
      <el-form-item label="出版社" prop="publisher">
        <el-input v-model.number="form.publisher" placeholder="请输入出版社"></el-input>
      </el-form-item>
      <el-form-item label="分类" prop="category">
        <el-cascader
            style="width: 220px"
            :props="{ value:'name',label:'name'}"
            v-model="form.categories"
            :options="categories"></el-cascader>
      </el-form-item>
      <el-form-item label="标准码" prop="bookNo">
        <el-input v-model.number="form.bookNo" placeholder="请输入标准码"></el-input>
      </el-form-item>
      <el-form-item label="借书积分" prop="score">
        <el-input-number v-model="form.score" :min="10" :max="30" label="所需积分"></el-input-number>
      </el-form-item>
      <el-form-item label="封面" prop="cover">
        <el-input v-model.number="form.cover" placeholder="请输入封面"></el-input>
      </el-form-item>
    </el-form>

    <div style="text-align: center;margin-top: 30px">
      <el-button type="primary" @keyup.enter.native="save" @click="save" size="medium">提交</el-button>
    </div>
  </div>

</template>

<script>
import request from "@/untils/request";

export default {
  name: 'EditBook',
  data() {
    return {
      form: { score:10},
      categories:{},
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
  created() {
    request.get('/category/tree').then(res => {
      this.categories = res.data
    })

    const id = this.$route.query.id
    request.get("/book/" + id).then(res => {
      this.form = res.data
      if (this.form.category){
        this.form.categories = this.form.category.split(' > ')
      }
    })
  },
  methods: {
    save() {
      request.put('/book/update', this.form).then(res => {
        if (res.code === '200') {
          this.$notify.success('更新成功')
          this.$router.push("/bookList")
        } else {
          this.$notify.error(res.msg)
        }
      })
    }
  }
}
</script>
