<template>
  <template v-if="isPanel">
    <el-cascader-panel
        :options="mutMenus"
        :props="cascaderProps"
        v-model="selectValue"
        @change="handleChange"
        clearable
        placeholder="菜单"
    />
  </template>
  <template v-else>
    <el-cascader
        :options="mutMenus"
        :props="cascaderProps"
        v-model="selectValue"
        @change="handleChange"
        clearable
        placeholder="菜单"
    />
  </template>
</template>
<script setup>
import {http} from "@/core/axios/index.js";

const cascaderProps = {

  emitPath: false
}

const emit = defineEmits(['update:value'])
const props = defineProps(
    {
      value: {
        type: String,
      },
      isPanel: {
        type: Boolean,
        default: false
      },
      isMenu: {
        type: String,
        default: "2"
      }
    }
)

const mutMenus = computed(() => {
  const copiedMenus = JSON.parse(JSON.stringify(menus.value))
  if(isMenu.value==='1'){
    copiedMenus.forEach(oneItem => {
      oneItem.children.forEach(twoItem => {
        twoItem.children = []
      })
    })
    return copiedMenus
  }else{
    copiedMenus.forEach(oneItem => {
      oneItem.children.forEach(twoItem => {
        twoItem.children.forEach(threeItem => {
          threeItem.children = []
        })
      })
    })
    return copiedMenus
  }
})
const menus = ref([])
const selectValue = ref(props.value)
const isPanel = ref(props.isPanel)
const isMenu = ref(props.isMenu)

watchEffect(() => {
  selectValue.value = props.value;
  isPanel.value = props.isPanel;
  isMenu.value = props.isMenu;
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
