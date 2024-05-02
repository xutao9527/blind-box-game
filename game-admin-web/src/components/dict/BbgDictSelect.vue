<template>
  <el-select v-model="dictName"
             placeholder=""
             @change="handleChange"
             clearable
             filterable>
    <el-option v-for="dict in bizDictDetail"
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
      dictId:{
        required:true
      }
    }
)

const bizDictDetail = ref([])

const fetchData = async () =>{
  let apiRet = await http.post('/bizDictDetail/list', {dictId: props.dictId})
  if(apiRet.ok){
    bizDictDetail.value = apiRet.data
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
  const foundElement = bizDictDetail.value.find(obj => obj.value === value)
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
