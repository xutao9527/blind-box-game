<style lang="less">
.avatar-uploader .avatar {
  width: v-bind(uploadWidth);
  height: v-bind(uploadHeight);
  display: block;
}

.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: v-bind(uploadWidth);
  height: v-bind(uploadHeight);
  text-align: center;
}
</style>
<template>
  <el-upload
      class="avatar-uploader"
      :http-request="upLoadProps.upload"
      ref="upLoadRef"
      :show-file-list="false"
  >
    <img v-if="props.value" :src="props.value" class="avatar"  alt=""/>
    <el-icon v-else class="avatar-uploader-icon">
      <Plus/>
    </el-icon>
  </el-upload>
</template>
<script setup>
import {Plus} from "@element-plus/icons-vue";
import {http} from "@/core/axios/index.js";

const upLoadRef = ref(null)
const uploadWidth = computed(() => props.width + 'px')
const uploadHeight = computed(() => props.height + 'px')
const emit = defineEmits(['update:value'])
const props = defineProps(
    {
      value: {
        type: String,
      },
      width: {
        type: Number,
        default: 128
      },
      height: {
        type: Number,
        default: 128
      },
    }
)

const upLoadProps = reactive({
  sign: async (dir) => {
    const apiRet = await http.get(`/oss/sign?dir=${dir}`)
    if (apiRet.ok) {
      return apiRet.data
    }
    return null
  },
  getDataForm: (ossInfo, file) => {
    const getSuffix = fileName => '.' + fileName.split('.').pop();
    const filename = new Date().getTime() + getSuffix(file.name)
    const formData = new FormData()
    formData.append('key', ossInfo.ossDir + filename) // 存储在oss的文件路径
    formData.append('OSSAccessKeyId', ossInfo.accessId) // accessKeyId
    formData.append('policy', ossInfo.policy) // policy
    formData.append('Signature', ossInfo.signature) // 签名
    formData.append('file', file)
    formData.append('fileName', filename)
    return formData
  },
  upload: async (param) => {
    let file = param.file
    const fileDir = new Date().toLocaleDateString()
    let ossInfo = await upLoadProps.sign(fileDir)
    let dataForm = upLoadProps.getDataForm(ossInfo, file)
    await http.post(ossInfo.baseUrlPath, dataForm)
    let fileUrl = `${ossInfo.baseUrlPath}${fileDir}/${dataForm.get('fileName')}`
    emit('update:value', fileUrl)
  }
})

</script>
