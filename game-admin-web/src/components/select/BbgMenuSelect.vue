<template>
<!--  <el-select v-model="selectValue"-->
<!--             placeholder=""-->
<!--             @change="handleChange"-->
<!--             clearable-->
<!--             filterable>-->
<!--    <el-option v-for="menu in menus"-->
<!--               :label="menu.title"-->
<!--               :value="menu.id.toString()">-->
<!--      <span style="float: left">{{ menu.title }}</span>-->
<!--    </el-option>-->
<!--  </el-select>-->
  <el-cascader-panel
      :options="menus"
      :props="cascaderProps"
      v-model="selectValue"
      @change="handleChange"
  />
</template>
<script setup>
import {http} from "@/core/axios/index.js";

const cascaderProps = {
  checkStrictly: true,
  emitPath: false
}


const emit = defineEmits(['update:value'])
const props = defineProps(
    {
      value: {
        type: String,
      }
    }
)
const menus = ref([])
const selectValue = ref(props.value)

watch(() => props.value, (newVal) => {
  selectValue.value = newVal
})

onMounted(() => {
  fetchData()
})

const fetchData = async () => {
  let apiRet;
  apiRet = await http.get('sysMenu/getSelectMenus')
  if (apiRet.ok) {
    menus.value = apiRet.data
    menus.value = convertToTree(apiRet.data)
  }
}

const handleChange = (value) => {
  emit('update:value', value)
}

 const convertToTree = (menuList) => {
  const map = {};
  const tree = [];
  menuList.forEach(item => {
    map[item.id] = {
      ...item,
      children: [],
      value: item.id.toString(),
      label: item.title
    };
  });
  menuList.forEach(item => {
    if (item.parentId !== 0 && item.parentId != null) {
      map[item.parentId].children.push(map[item.id]);
      map[item.parentId].children.sort((a, b) => a.sort - b.sort);
    } else {
      tree.push(map[item.id]);
    }
  });
  tree.sort((a, b) => a.sort - b.sort);
  return tree;
}
</script>
