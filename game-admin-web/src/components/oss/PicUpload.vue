<template>
  <el-upload
      class="avatar-uploader"
  >
    <img v-if="upLoadProps.imageUrl" :src="upLoadProps.imageUrl" class="avatar" alt="12"/>
    <el-icon v-else class="avatar-uploader-icon">
      <Plus/>
    </el-icon>
  </el-upload>
</template>
<script setup>
import {Plus} from "@element-plus/icons-vue";

const upLoadProps = reactive({
  imageUrl: '',

})

</script>
<style lang="less">
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
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
  width: 168px;
  height: 168px;
  text-align: center;
}
</style>