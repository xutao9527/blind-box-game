
<template>
  <el-container class="box">
    <el-main class="box-main">
<!--    {{ JSON.stringify(rollMock.rollList,2,null) }}-->
      <el-scrollbar>
          <el-space wrap>
            <el-card style="width: 300px;height: 300px" v-for="roll in rollMock.rollList">
              <template #header>
                <span>{{ roll.rollTitle }}</span>
              </template>
              <el-scrollbar>
                <el-row style="height: 150px">
                  <template v-for="good in roll.rollGoods">
                    <el-tooltip placement="top">
                      <template #content>
                        name:{{ good.name }}<br/>
                        price:{{ good.goodPrice }}<br/>
                      </template>
                      <el-col :span="8">
                        <el-image :src="good.goodImage"/>
                      </el-col>
                    </el-tooltip>
                  </template>
                </el-row>
              </el-scrollbar>
              <template #footer>
                <el-row>
                  <el-col style="text-align: right;display: flex;align-items: center;justify-content: space-between">
                    &nbsp;
                    <el-button v-show="mockGlobal.bizToken !==null" @click="">加入</el-button>
                  </el-col>
                </el-row>
              </template>
            </el-card>
          </el-space>
      </el-scrollbar>
    </el-main>
  </el-container>
</template>
<script setup>
import {rollMock} from "@/views/mock/js/rollDto.js";
import {boxMock} from "@/views/mock/js/boxDto.js";
import {mockGlobal} from "@/views/mock/js/mockGlobal.js";

onMounted(() => {
  rollMock.getRollList();
})
</script>
<style scoped lang="less">

</style>