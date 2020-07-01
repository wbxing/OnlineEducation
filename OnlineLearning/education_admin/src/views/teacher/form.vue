<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name" />
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input-number v-model="teacher.sort" controls-position="right" min="0" />
      </el-form-item>
      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level" clearable placeholder="请选择">
          <!--
            数据类型一定要和取出的json中的一致，否则没法回填
            因此，这里value使用动态绑定的值，保证其数据类型是number
          -->
          <el-option :value="1" label="高级讲师" />
          <el-option :value="2" label="首席讲师" />
        </el-select>
      </el-form-item>
      <el-form-item label="讲师资历">
        <el-input v-model="teacher.intro" />
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="teacher.career" :rows="10" type="textarea" />
      </el-form-item>

      <!-- 讲师头像：TODO -->

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
// 引用 teacher.js
import teacher from '@/api/edu/teacher'
// 定义初始化变量
const defaultForm = {
  name: '',
  sort: 0,
  level: '',
  career: '',
  intro: '',
  avatar: ''
}

export default {
  data() {
    return {
      teacher: defaultForm,
      saveBtnDisabled: false
    }
  },
  watch: {
    $route(to, from) {
      // 判断路由中是否存在 id 参数
      // 不存在说明是新增
      // 存在说明是修改
      this.init()
    }
  },
  created() {
    // 执行此方法获取参数
    this.init()
  },
  methods: {
    init() {
      if (this.$route.params && this.$route.params.id) {
        const id = this.$route.params.id
        this.selectById(id)
      } else {
        // 深拷贝和浅拷贝
        this.teacher = { ...defaultForm }
      }
    },
    saveOrUpdate() {
      this.saveBtnDisabled = true
      if (!this.teacher.id) {
        this.saveData()
      } else {
        this.updateData()
      }
    },
    saveData() {
      teacher.save(this.teacher)
        .then(response => {
          return this.$message({
            type: 'success',
            message: '保存成功!'
          })
        })
        .then(resposne => {
          this.$router.push({ path: '/teacher/list' })
        })
        .catch((response) => {
          // console.log(response)
          this.$message({
            type: 'error',
            message: '保存失败'
          })
        })
    },
    selectById(id) {
      teacher.selectById(id)
        .then(response => {
          this.teacher = response.data.teacher
        })
        .catch(response => {
          this.$message({
            type: 'error',
            message: '获取失败'
          })
        })
    },
    updateData() {
      teacher.updateById(this.teacher)
        .then(response => {
          return this.$message({
            type: 'success',
            message: '修改成功'
          })
        })
        .then(resposne => {
          this.$router.push({ path: '/teacher/list' })
        })
        .catch((response) => {
          this.$message({
            type: 'error',
            message: '修改失败'
          })
        })
    }
  }
}
</script>
