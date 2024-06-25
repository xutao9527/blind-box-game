<template>

  <el-container class="header-dynamic-height">
    <el-aside style="display: flex; align-items: center" :style="{'width':menuWidth+'px'}">
      <el-text tag="b" type="primary" size="large" style="margin-left: 30px">B B G</el-text>
    </el-aside>
    <el-main style="padding: 0; display: grid; align-items: center">
      <el-row>
        <el-col :span="6">
          <el-button icon="Operation" @click="menuCollapse"/>
        </el-col>
        <el-col :span="18" style="text-align: right;display: flex;align-items: center;justify-content: end">
          <el-switch style="margin-right: 10px"
              v-model="isDark"
              inline-prompt
              :active-icon="Sunny"
              :inactive-icon="Moon"
          />
          <el-dropdown style="margin-right: 20px">
            <el-button size="large" text >
              {{ user == null ? "" : user.account }}
              <el-icon class="el-icon--right">
                <arrow-down/>
              </el-icon>
            </el-button>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="handleLogout">Logout</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </el-col>
      </el-row>
    </el-main>
  </el-container>
</template>
<script setup>
import emitter from "@/core/mitt/index.js";
import {ArrowDown, Moon, Sunny} from "@element-plus/icons-vue";
import {useUserStore} from "@/store/userStore.js";
import {http} from "@/core/axios/index.js";
import {useDark} from "@vueuse/core";
import {useTabsStore} from "@/store/tabsStore.js";
import TenantUtil from "@/core/tenant/index.js";

const isDark = useDark()
const user = ref({})
const menuWidth = ref(0)
const userStore = useUserStore()
const tabsStore = useTabsStore()
const router = useRouter()
const menuCollapse = () => {
  emitter.emit('menuCollapse');
}

onMounted(async ()=>{
  menuWidth.value = parseFloat(getComputedStyle(document.documentElement).getPropertyValue('--left-width'));
  user.value = await userStore.getUser
  TenantUtil.currentUser = user.value
})

const handleLogout = async () =>{
  await http.get('/sysUser/logout')
  userStore.setToken(null)
  tabsStore.clearTab()
  await router.push({path: '/login'})
}

</script>
<style scoped lang="less">
.header-dynamic-height {
  height: var(--header-height)
}
</style>