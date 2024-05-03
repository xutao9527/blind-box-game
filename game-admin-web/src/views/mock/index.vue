<template>
  <el-container class="mock">
    <el-header class="mock-header">
      <el-form label-position="right" :inline="true">
        <el-form-item label="手机号">
          <el-input v-model="LoginMock.loginReq.mobile" placeholder="手机号"/>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="LoginMock.loginReq.password" placeholder="密码"/>
        </el-form-item>
        <el-form-item>
          <el-button @click="LoginMock.login">登 录</el-button>
        </el-form-item>
      </el-form>
    </el-header>
    <el-main class="mock-main">

    </el-main>
  </el-container>
</template>
<script setup>
import {LoginMock} from "@/views/mock/LoginDto.js";

</script>
<style lang="less" scoped>
.mock {
  .mock-header {
    height: 100px;
    border: 1px solid var(--el-border-color);
  }
  .mock-main {
    height: calc(100vh - 231px);
    border-left: 1px solid var(--el-border-color);
    border-right: 1px solid var(--el-border-color);
    border-bottom: 1px solid var(--el-border-color);
  }
}
</style>