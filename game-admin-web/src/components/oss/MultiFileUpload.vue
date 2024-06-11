<template>
  <el-upload
      list-type="picture-card"
      :auto-upload="false"
      multiple
      v-model:file-list="fileList"
  >
    <el-icon>
      <Plus/>
    </el-icon>
    <template #file="{ file }">
      <el-image :src="file.url" :preview-src-list="[file.url]" fit="contain" preview-teleported/>
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
  <ElImageViewer
      v-if="viewerVisible"
      :url-list="viewerUrlList"
      @close="viewerClose"
  />
</template>
<script setup>
import {Delete, Plus, ZoomIn} from "@element-plus/icons-vue";

const viewerVisible = ref(false)
const viewerUrlList = ref([])
const fileList = ref([])

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
  fileList.value = fileList.value.filter(file => file !== rmFile)
}

</script>
<style scoped lang="less">

</style>