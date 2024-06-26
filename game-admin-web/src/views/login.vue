<template>
  <div id="userLayout">
    <div class="login_panel">
      <div class="login_panel_form">
        <div class="login_panel_form_title">
<!--          <img-->
<!--              class="login_panel_form_title_logo"-->
<!--              src="@/assets/images/logo.png"-->
<!--              alt-->
<!--          >-->
          <p class="login_panel_form_title_p">BBG-Admin</p>
        </div>
        <el-form size="small"
                 ref="loginFormRef"
                 :model="loginData"
                 :rules="rules"
                 @keyup.enter.native="submitLogin(loginFormRef)"
          >
          <el-form-item label-placement="left" prop="account">
            <el-input v-model="loginData.account" size="large"/>
          </el-form-item>
          <el-form-item label-placement="left" prop="password">
            <el-input v-model="loginData.password" size="large" type="password" show-password/>
          </el-form-item>
          <el-form-item>
            <el-button
                type="primary"
                size="large"
                style="width: 50%; margin-left: 25%"
                @click="submitLogin(loginFormRef)"
            >
              登 录
            </el-button>
          </el-form-item>
        </el-form>
      </div>
      <div class="login_panel_right"/>
      <div class="login_panel_foot">
        <div class="links">
          <img src="@/assets/docs.png" class="link-icon" alt="文档">
          &nbsp;
          <img src="@/assets/kefu.png" class="link-icon" alt="客服">
          &nbsp;
          <img src="@/assets/github.png" class="link-icon" alt="github">
          &nbsp;
          <img src="@/assets/video.png" class="link-icon" alt="视频站">
        </div>
        <div class="copyright">
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {http} from "@/core/axios/index.js";
import {useUserStore} from "@/store/userStore.js"
import {useRouter} from "vue-router";
import TenantUtil from "@/core/tenant/index.js";

const loginFormRef = ref();
const loginData = reactive({
  account: 'admin',
  password: 'admin',
})
const rules = {
  account: [
    { required: true, message: 'Please input account', trigger: 'blur' },
  ],
  password: [
    { required: true, message: 'Please input password', trigger: 'blur' },
  ],
}
const store = useUserStore()
const router = useRouter()

const submitLogin = async (formEl) => {
  if (!formEl) return
  await formEl.validate(async (valid, fields) => {
    if (valid) {
      const apiRet = await http.post('/sysUser/login', loginData)
      if (apiRet.ok){
        store.setToken(apiRet.data)
        await store.asyncUserInfo()
        await store.asyncRouters()
        if(store.menus && store.menus.length > 0){
          ElMessage({type: 'success', message: '登录成功!'})
          await router.push({path: '/'})
        }else{
          ElMessage({type: 'success', message: '没有任何权限,请联系超管!'})
        }

      }else{
        ElMessage({type: 'error', message: apiRet.msg})
      }
    }
  })
}

</script>
<style lang="less" scoped>
@import "@/config/style/newLogin.less";
</style>
