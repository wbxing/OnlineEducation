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
      <el-form-item label="讲师头像">

        <!-- 头衔缩略图 -->
        <pan-thumb :image="teacher.avatar"/>
        <!-- 文件上传按钮 -->
        <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像</el-button>
        <!--
          v-show：是否显示上传组件
          :key：类似于id，如果一个页面多个图片上传控件，可以做区分
          :url：后台上传的url地址
          @close：关闭上传组件
          @crop-upload-success：上传成功后的回调
        -->
        <image-cropper
          v-show="imagecropperShow"
          :width="300"
          :height="300"
          :key="imagecropperKey"
          :url="BASE_API+'/oss/file/upload'"
          field="file"
          @close="close"
          @crop-upload-success="cropSuccess"/>
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
// 引用 teacher.js
import teacher from '@/api/edu/teacher'
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'

// 定义初始化变量
const defaultForm = {
  name: '',
  sort: 0,
  level: '',
  career: '',
  intro: '',
  avatar: 'https://my-online-learning.oss-cn-shanghai.aliyuncs.com/avatar/20200510172115.jpg'
}

export default {
  // 声明一下插件
  components: { ImageCropper, PanThumb },
  data() {
    return {
      teacher: defaultForm,
      saveBtnDisabled: false, // 不启用 disable，保存按钮为亮色
      BASE_API: process.env.BASE_API, // 接口API地址
      imagecropperShow: false, // 是否显示上传组件
      imagecropperKey: 0 // 上传组件id
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
    },
    close() {
      // 关闭上传框
      this.imagecropperShow = false // 是否显示上传组件
      // 改变框的 ID
      this.imagecropperKey = this.imagecropperKey + 1
    },
    // 保存图片
    cropSuccess(data) {
      this.teacher.avatar = data.url
      // 关闭上传框
      this.imagecropperShow = false // 是否显示上传组件
      // 改变框的 ID
      this.imagecropperKey = this.imagecropperKey + 1
    }
  }
}
</script>
