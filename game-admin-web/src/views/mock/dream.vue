<template>
  <el-container class="box">
    <el-main class="box-main">
      <el-scrollbar>
      <el-card style="height: 560px">
        <el-row>
          <DreamGoodSelect v-model:good="dreamGood" ref="dreamGoodSelectRef"></DreamGoodSelect>
        </el-row>
        <el-row justify="space-between">
          <el-col :span="8">
            <el-card style="width: 300px; height: 250px" body-style="margin-top: 50px">
              <el-row  justify="center">
                <el-col :span="8" style="text-align: center">
                  <el-card style="height: 100px;width: 100px;" @click="dreamGoodSelectRef.selectData()">
                    <template v-if="dreamGood.id">
                      <el-image :src="dreamGood.imageUrl"></el-image>
                    </template>
                    <template v-else>
                      <el-icon size="50"><Minus /></el-icon>
                    </template>
                  </el-card>
                  <el-text>
                    <el-text type="warning" size="large">
                      {{dreamGood.price}}
                    </el-text>
                  </el-text>
                </el-col>
              </el-row>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card style="width: 300px; height: 250px;text-align: center;" body-style="margin-top: 60px">
              <el-text size="large" type="warning" style="margin-top: 500px">
                <h3>
                  {{ dreamRate }}%
                </h3>
              </el-text>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card style="width: 300px; height: 250px" body-style="margin-top: 50px">
              <el-row  justify="center">
                <el-col :span="8" style="text-align: center">
                  <el-card style="height: 100px;width: 100px;" >
                    <el-icon size="50"><QuestionFilled /></el-icon>
                  </el-card>
                  <el-text>
                    <el-text type="warning" size="large">
                      随机饰品
                    </el-text>
                  </el-text>
                </el-col>
              </el-row>
            </el-card>
          </el-col>
        </el-row>
        <el-row justify="center" style="margin-top: 20px">
          <el-col :span="20">
            <el-slider v-model="dreamRate" show-stops :min="5" :max="75" :step="5" size="large" :marks="dreamMarks"/>
          </el-col>
        </el-row>
        <el-row justify="center" style="margin-top: 50px">
          <el-button size="large"  @click="startDreamGood" type="success" v-show="mockGlobal.bizToken !==null">追梦({{ dreamGoodPrice }})</el-button>
        </el-row>
      </el-card>
      </el-scrollbar>
    </el-main>
    <el-aside class="box-aside" width="400px">
      <el-row style="display: flex;align-items: center;justify-content: space-between">
        <el-text type="primary" size="large">追梦记录</el-text>
        <el-button @click="dreamMock.dreamGoodRecord = []" link>清除</el-button>
      </el-row>
      <el-scrollbar class="record_scrollbar">
        <p v-for="r in dreamMock.dreamGoodRecord">
          获得=> [{{ r.goodName }}] , [{{ r.goodPrice }}]
        </p>
      </el-scrollbar>
    </el-aside>
  </el-container>
</template>
<script setup>
import DreamGoodSelect from "@/views/mock/dreamGoodSelect.vue";
import {Minus, Plus, QuestionFilled} from "@element-plus/icons-vue";
import {mockGlobal} from "@/views/mock/js/mockGlobal.js";
import {dreamMock} from "@/views/mock/js/dreamDto.js";

const dreamRate = ref(5)
const dreamMarks = reactive({
  5: '5%', 10: '10%', 15: '15%', 20: '20%', 25: '25%', 30: '30%', 35: '35%',
  40: '40%', 45: '45%', 50: '50%', 55: '55%', 60: '60%', 65: '65%', 70: '70%', 75: '75%',
})

const dreamGood = ref({})
const dreamGoodSelectRef = ref(null)
const dreamGoodPrice = computed(()=>{
  if(dreamGood.value.id){
    return ((dreamGood.value.price * dreamGood.value.rate * dreamRate.value) / 100).toFixed(2)
  }else{
    return '0.00'
  }
})

const startDreamGood =() =>{
  if(dreamGood.value.id){
    dreamMock.dreamGoodReq.boxGoodId = dreamGood.value.id
    dreamMock.dreamGoodReq.probability = dreamRate.value
    dreamMock.dreamGood()
  }else {
    ElMessage({type: 'warning', message: '请选择梦想装备!'})
  }
}

</script>
<style lang="less" scoped>
.box {
  .box-main {
    height: calc(100vh - 322px);
  }

  .box-aside {
    margin-top: 20px;
    height: calc(100vh - 322px);
    border: 1px solid var(--el-border-color);
    .record_scrollbar {
      height: calc(100vh - 351px);
    }
  }
}
</style>