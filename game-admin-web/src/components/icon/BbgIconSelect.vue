<template>
  <el-select v-model="iconName"
             placeholder=""
             @change="handleChange"
             filterable
  >
    <el-option v-for="icon in bbgConf.iconList"
               :label="icon"
               :value="icon"
    >
      <span style="float: left">{{ icon }}</span>
      <component :is="icon" style="float:right; width:14px; height:14px;"></component>
    </el-option>
    <template #prefix>
      <component :is="iconName" style="width: 14px;height: 14px"></component>
    </template>
  </el-select>

</template>

<script setup>
import {bbgConf} from "@/config/index.js";

const emit = defineEmits(['update:value'])
const props = defineProps(
    {
      value: {
        type: String,
      }
    }
)

const iconName = ref(props.value)
watch(() => props.value, (newVal) => {
  iconName.value = newVal
})

const handleChange = (value) => {
  emit('update:value', value)
}
</script>
<style scoped>

</style>