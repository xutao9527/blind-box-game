<template>
  <el-container class="box">
    <el-main class="box-main">
      <el-scrollbar>
        <el-space wrap>
          <el-card style="width: 300px;height: 300px" v-for="box in boxMock.boxList">
            <template #header>
              <span>{{ box.name }}</span>
            </template>
            <el-scrollbar>
              <el-row style="height: 150px">
                <template v-for="good in box.csgoBoxGoods">
                  <el-tooltip placement="top">
                    <template #content>
                      name:{{ good.name }}<br/>
                      roll:{{ good.startRoundNumber }}~{{ good.endRoundNumber }}<br/>
                      price:{{ good.price }}<br/>
                    </template>
                    <el-col :span="8">
                      <el-image :src="good.imageUrl"/>
                    </el-col>
                  </el-tooltip>
                </template>
              </el-row>
            </el-scrollbar>
            <template #footer>
              <el-row>
                <el-col style="text-align: right;display: flex;align-items: center;justify-content: space-between">
                  {{ box.price }}
                  <el-button v-show="mockGlobal.bizToken !==null" @click="boxMock.openBox(box.id.toString())">打开</el-button>
                </el-col>
              </el-row>
            </template>
          </el-card>
        </el-space>
      </el-scrollbar>
    </el-main>
    <el-aside class="box-aside" width="400px">
      <el-row style="display: flex;align-items: center;justify-content: space-between">
        <el-text type="primary" size="large">开箱记录</el-text>
        <el-button  @click="boxMock.openBoxRecord = []" link>清除</el-button>
      </el-row>
      <el-scrollbar class="record_scrollbar">
        <p v-for="r in boxMock.openBoxRecord">
          获得=> [{{ r.name }}] , [{{ r.price }}]
        </p>
      </el-scrollbar>
    </el-aside>
  </el-container>
</template>
<script setup>
import {boxMock} from "@/views/mock/js/boxDto.js";
import {mockGlobal} from "@/views/mock/js/mockGlobal.js";

onMounted(() => {
  boxMock.getBoxList();
})
</script>
<style lang="less" scoped>
.box {
  .box-main {
    height: calc(100vh - 322px);
  }
  .box-aside {
    height: calc(100vh - 322px);
    border: 1px solid var(--el-border-color);
    .record_scrollbar {
      height: calc(100vh - 351px);
    }
  }
}
</style>