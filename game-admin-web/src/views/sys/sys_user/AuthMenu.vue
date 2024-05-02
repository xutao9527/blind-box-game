<template>
  <el-drawer v-model="visible">
    <template #header>
      <h4>{{ currentRole.name }}-菜单权限</h4>
    </template>
    <template #default>
      <el-scrollbar>
        <el-tree
            :data="allMenu"
            show-checkbox
            default-expand-all
            node-key="id"
            ref="menuTreeRef"
        />
      </el-scrollbar>
    </template>
    <template #footer>
      <div style="flex: auto">
        <el-button type="primary" @click="save">保存</el-button>
        <el-button @click="close">关闭</el-button>
      </div>
    </template>
  </el-drawer>
</template>
<script setup>
import {http} from "@/core/axios/index.js";
import {convertToTree} from "@/store/userStore.js";
//显示
const visible = ref(false)
//当前角色
const currentRole = ref({})
//树引用
const menuTreeRef = ref(null)
//所有菜单
const allMenu = ref();


const authMenu = async (role) => {
  currentRole.value = role;
  visible.value = true;
  let apiRet = await http.post('/sysUser/getRoleMenu', {roleId: currentRole.value.id})
  if (apiRet.ok && apiRet.data) {
    menuTreeRef.value.setCheckedKeys([]);
    apiRet.data.forEach(item => {
      let node = menuTreeRef.value.getNode(item.menuId.toString())
      if (node.isLeaf) {
        menuTreeRef.value.setChecked(node, true)
      }
    })
  }
}

const save = async () => {
  let roleMenuList = [];
  const checkedMenu = menuTreeRef.value.getCheckedNodes(false, true).map(item => item.id)
  checkedMenu.forEach(item => {
    roleMenuList.push({roleId: currentRole.value.id, menuId: item})
  })
  let apiRet = await http.post('/sysUser/authRoleMenu', roleMenuList)
  if (apiRet.ok) {
    ElMessage({type: 'success', message: '授权菜单成功!'})
    visible.value = false;
  }
}

const close = () => {
  visible.value = false;
}

onMounted(async () => {
  let apiRet = await http.post('/sysMenu/list', {})
  if (apiRet.ok && apiRet.data) {
    apiRet.data.forEach(item => {
      item.label = item.title + ">" + item.id
    })
    allMenu.value = convertToTree(apiRet.data)
  }
})

defineExpose({
  authMenu
})
</script>
<style scoped>

</style>