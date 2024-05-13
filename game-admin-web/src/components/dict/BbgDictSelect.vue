<template>
  <el-select v-model="dictName"
             placeholder=""
             @change="handleChange"
             clearable
             filterable>
    <el-option v-for="dict in bizDictDetails"
               :label="dict.label"
               :value="dict.value">
      <span style="float: left">{{ dict.label }}</span>
    </el-option>
  </el-select>
</template>

<script setup>
import {http} from "@/core/axios/index.js";

const emit = defineEmits(['update:value'])
const props = defineProps(
    {
      value: {
        type: String,
      },
      tag:{
        required:true
      }
    }
)

const bizDict = ref({})
const bizDictDetails = ref([])

const fetchData = async () =>{
  let apiRet = await http.get(`/bizDict/getDict/${props.tag}`)
  if(apiRet.ok){
    bizDict.value = apiRet.data
    if(bizDict.value && bizDict.value.bizDictDetails){
      bizDictDetails.value = bizDict.value.bizDictDetails
    }
  }
}

onMounted(()=>{
  fetchData()
})

const dictName = ref(props.value)
watch(() => props.value, (newVal) => {
  dictName.value = newVal
})

const handleChange = (value) => {
  emit('update:value', value)
}

const getLabel = (value)=>{
  const foundElement = bizDictDetails.value.find(obj => obj.value === value)
  if(foundElement){
    return foundElement.label
  }else{
    return ""
  }
}

defineExpose({
  getLabel
})
</script>
