<template>
  <el-container class="mock">
    <el-header class="mock-header">
      <el-form label-position="right" :inline="true">
        <el-form-item label="手机号">
          <el-input v-model="loginMock.loginReq.mobile" placeholder="手机号"/>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="loginMock.loginReq.password" placeholder="密码"/>
        </el-form-item>
        <el-form-item>
          <el-button @click="loginMock.login">登 录</el-button>
          <el-button @click="loginMock.logout">登出</el-button>
        </el-form-item>
      </el-form>
      <el-row>
        <el-col>
          <el-text type="success">用户信息</el-text>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="3">
          <el-text>手机: {{mockGlobal.bizUser.mobile}}</el-text>
        </el-col>
        <el-col :span="3">
          <el-text>余额: {{mockGlobal.bizUser.money}}</el-text>
        </el-col>
        <el-col :span="5">
          <el-text>token: {{mockGlobal.bizToken}}</el-text>
        </el-col>
      </el-row>
    </el-header>
    <el-main class="mock-main">

    </el-main>
  </el-container>
</template>
<script setup>
import {loginMock} from "@/views/mock/LoginDto.js";
import {mockGlobal} from "./mockGlobal.js";

</script>
<style lang="less" scoped>
.mock {
  .mock-header {
    padding-top: 10px;
    height: 150px;
    border: 1px solid var(--el-border-color);
  }

  .mock-main {
    height: calc(100vh - 281px);
    border-left: 1px solid var(--el-border-color);
    border-right: 1px solid var(--el-border-color);
    border-bottom: 1px solid var(--el-border-color);
  }
}
</style>