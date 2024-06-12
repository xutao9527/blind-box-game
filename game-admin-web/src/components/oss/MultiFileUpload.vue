<template>
  <el-scrollbar max-height="450" height="450" min-size="450">
    <div>
    <el-upload
        list-type="picture-card"
        :auto-upload="false"
        multiple
        v-model:file-list="fileList"
        :limit="100"
        :on-exceed="handleExceed"
    >
      <el-icon>
        <Plus/>
      </el-icon>
      <template #file="{ file }">
        <div class="image-container">
          <el-image
              :ref="(el) => {setFileRefs(file,el)}"
              :src="file.url"
              :preview-src-list="[file.url]"
              fit="contain"
              preview-teleported/>
          <el-icon v-if="!isBlob(file)" class="check-icon">
            <Check/>
          </el-icon>
        </div>
        <div>
          <span class="el-upload-list__item-actions">
            <span
                class="el-upload-list__item-preview"
                @click="viewerPreview(file)">
              <el-icon><zoom-in/></el-icon>
            </span>
            <span
                class="el-upload-list__item-delete"
                @click="handleRemove(file)">
              <el-icon><Delete/></el-icon>
            </span>
          </span>
        </div>
      </template>
    </el-upload>
    </div>
  </el-scrollbar>
  <ElImageViewer
      v-if="viewerVisible"
      :url-list="viewerUrlList"
      @close="viewerClose"
  />
</template>
<script setup>
import {Check, Delete, Plus, ZoomIn} from "@element-plus/icons-vue";
import {http} from "@/core/axios/index.js";

const viewerVisible = ref(false)
const viewerUrlList = ref([])
const fileList = ref([])
const fileRefs = ref({})

const isBlob = computed(() => (file) => {
  return file.url.startsWith('blob:')
})

const setFileRefs = (file,el) => {
  fileRefs.value[file.uid] = el
}

const viewerPreview = (file) => {
  viewerUrlList.value = [
    file.url
  ]
  viewerVisible.value = true
}

const viewerClose = () => {
  viewerVisible.value = false
}

const handleRemove = (rmFile) => {
  fileList.value = fileList.value.filter(file => file.uid !== rmFile.uid)
}

const handleExceed = (files, fileList) => {
  ElMessage.warning(`当前限制选择 100 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
}

const upLoadProps = reactive({
  sign: async (dir) => {
    const apiRet = await http.get(`/oss/sign?dir=${dir}`)
    if (apiRet.ok) {
      return apiRet.data
    }
    return null
  },
  getDataForm: (ossInfo, file) => {
    const filename = file.name
    const formData = new FormData()
    formData.append('key', ossInfo.ossDir + filename)                       // 存储在oss的文件路径
    formData.append('OSSAccessKeyId', ossInfo.accessId)                           // accessKeyId
    formData.append('policy', ossInfo.policy)                                     // policy
    formData.append('Signature', ossInfo.signature)                               // 签名
    formData.append('file', file.raw)
    formData.append('fileName', filename)
    return formData
  },
  upload: async (file) => {
    const fileDir = 'profilePhoto'
    let ossInfo = await upLoadProps.sign(fileDir)
    let dataForm = upLoadProps.getDataForm(ossInfo, file)
    await http.post(ossInfo.baseUrlPath, dataForm)
    return `${ossInfo.baseUrlPath}${fileDir}/${dataForm.get('fileName')}`
  }
})

const uploadMultiFile = async (callBack) => {
  for (const file of fileList.value) {
    if (file.raw instanceof Blob) {
      let filePath = await upLoadProps.upload(file)
      if(callBack(filePath)){
        file.url = filePath
      }
    }
  }
}

const clearMultiFile = () => {
  fileList.value = []
}

defineExpose({
  uploadMultiFile,
  clearMultiFile
})

</script>
<style scoped lang="less">
.image-container {
  position: relative;
  display: inline-block;
}

.check-icon {
  position: absolute;
  top: 1px;
  right: 1px;
  font-size: 15px;
  color: green;
  background-color: rgba(255, 255, 255, 0.8); /* 半透明背景 */
  border-radius: 50%;
  padding: 2px;
}
</style>