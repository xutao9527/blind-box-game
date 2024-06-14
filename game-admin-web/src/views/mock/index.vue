<template>
  <el-container class="mock">
    <el-header class="mock-header">
      <el-tabs tab-position="top" model-value="codeLogin">
        <el-tab-pane label="密码登录" name="pwdLogin" >
          <el-form label-position="right" :inline="true" size="small">
            <el-form-item label="手机号">
              <el-input v-model="loginMock.LoginPwdReq.mobile" placeholder="手机号"/>
            </el-form-item>
            <el-form-item label="密码">
              <el-input v-model="loginMock.LoginPwdReq.password" placeholder="密码"/>
            </el-form-item>
            <el-form-item>
              <el-button type="success" @click="loginMock.loginPwd">登 录</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="短信登录" name="codeLogin">
          <el-form label-position="right" :inline="true" size="small">
            <el-form-item label="手机号">
              <el-input v-model="loginMock.LoginCodeReq.mobile" placeholder="手机号"/>
            </el-form-item>
            <el-form-item label="验证码">
              <el-input v-model="loginMock.LoginCodeReq.code" placeholder="验证码"/>
            </el-form-item>
            <el-form-item>
              <el-button type="info" @click="loginMock.sendCode(loginMock.LoginCodeReq.mobile)">验 证 码</el-button>
              <el-button type="success" @click="loginMock.loginCode">登 录</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="注册" name="box">
          <el-form label-position="right" :inline="true" size="small">
            <el-form-item label="手机号">
              <el-input v-model="loginMock.RegisterReq.mobile" placeholder="手机号"/>
            </el-form-item>
            <el-form-item label="密码">
              <el-input v-model="loginMock.RegisterReq.password" placeholder="密码"/>
            </el-form-item>
            <el-form-item label="验证码">
              <el-input v-model="loginMock.RegisterReq.code" placeholder="验证码"/>
            </el-form-item>
            <el-form-item label="验证码">
              <el-input v-model="loginMock.RegisterReq.invitationCode" placeholder="邀请码(可选)"/>
            </el-form-item>
            <el-form-item>
              <el-button type="info" @click="loginMock.sendCode(loginMock.RegisterReq.mobile)">验 证 码</el-button>
              <el-button type="success" @click="loginMock.register">注 册</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
      <el-row>
        <el-col>
          <el-text type="success">用户信息</el-text>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="3">
          <el-text>手机: {{ mockGlobal.bizUser.mobile }}</el-text>
        </el-col>
        <el-col :span="3">
          <el-text>余额: {{ mockGlobal.bizUser.money }}</el-text>
        </el-col>
        <el-col :span="6">
          <el-text>token: {{ mockGlobal.bizToken }}</el-text>
        </el-col>
        <el-col :span="12">
          <el-button type="warning" size="small"  @click="loginMock.logout">登出</el-button>
        </el-col>

      </el-row>
    </el-header>
    <el-main class="mock-main">
      <el-tabs tab-position="right" model-value="roll">
        <el-tab-pane label="盲盒" name="box">
          <box></box>
        </el-tab-pane>
        <el-tab-pane label="追梦" name="dream">
          <dream></dream>
        </el-tab-pane>
        <el-tab-pane label="竞技场" name="battle">
          <battle></battle>
        </el-tab-pane>
        <el-tab-pane label="Roll房" name="roll">
          <roll></roll>
        </el-tab-pane>
        <el-tab-pane label="个人中心" name="userInfo">
          个人中心
        </el-tab-pane>
      </el-tabs>
    </el-main>
  </el-container>
</template>
<script setup>
import {loginMock} from "@/views/mock/js/LoginDto.js";
import {mockGlobal} from "./js/mockGlobal.js";
import box from "@/views/mock/box.vue";
import Dream from "@/views/mock/dream.vue";
import Battle from "@/views/mock/battle.vue";
import Roll from "@/views/mock/roll.vue";

onMounted(()=>{
  loginMock.getInfo();
})
</script>
<style lang="less" scoped>
.mock {
  .mock-header {
    height: 160px;
    border: 1px solid var(--el-border-color);
  }
  .mock-main {
    padding: 0;
    height: calc(100vh - 291px);
    border-left: 1px solid var(--el-border-color);
    border-right: 1px solid var(--el-border-color);
    border-bottom: 1px solid var(--el-border-color);
  }
}
</style>