<template>
  <el-container class="box">
    <el-main class="box-main">
      <el-scrollbar>

      </el-scrollbar>
    </el-main>
    <el-aside class="box-aside" width="400px">
      <el-row style="margin-top: 10px">
        <el-text type="info" size="small">对战盲盒：</el-text>
      </el-row>
      <el-row>
        <el-checkbox-group v-model="battleMock.createRoomReq.boxesId">
          <el-checkbox-button v-for="box in battleMock.boxList" style="margin-top: 5px;margin-left: 5px"
                              :value="box.id">
            <template #default>
              <el-tooltip placement="top">
                <template #content>
                  <template v-for="good in box.csgoBoxGoods">
                    name:{{ good.name }}<br/>
                    roll:{{ good.startRoundNumber }}~{{ good.endRoundNumber }}<br/>
                    price:{{ good.price }}<br/>
                  </template>
                </template>
                <div style="width: 50px;height: 50px;padding: 0;">
                  <el-text type="warning" size="small">
                    <br>
                    {{ box.name }}<br>
                    {{ box.price }}
                  </el-text>
                </div>
              </el-tooltip>
            </template>
          </el-checkbox-button>
        </el-checkbox-group>
      </el-row>
      <el-row style="margin-top: 10px">
        <el-text type="info" size="small">机器人：</el-text>
      </el-row>
      <el-row style="margin-top: 10px">
        <el-checkbox-group v-model="battleMock.createRoomReq.robotsId">
          <el-checkbox-button style="margin-left: 5px;margin-top: 5px" v-for="robot in battleMock.robotList"
                              :value="robot.id">
            <template #default>
              <el-text size="small">{{ robot.name }}</el-text>
            </template>
          </el-checkbox-button>
        </el-checkbox-group>
      </el-row>
      <el-row style="margin-top: 10px">
        <el-text type="info" size="small">模式：
        </el-text>
        <el-radio-group v-model="battleMock.createRoomReq.battleModel">
          <el-radio value="1">欧皇</el-radio>
          <el-radio value="2">非酋</el-radio>
        </el-radio-group>
      </el-row>
      <el-row style="margin-top: 10px">
        <el-text type="info" size="small">人数：</el-text>
        <el-radio-group v-model="battleMock.createRoomReq.peopleNumber">
          <el-radio value="2">2人</el-radio>
          <el-radio value="3">3人</el-radio>
          <el-radio value="4">4人</el-radio>
        </el-radio-group>
      </el-row>
      <el-row style="margin-top: 50px" justify="center">
        <el-button size="large" type="primary" @click="battleMock.createRoom">创建房间</el-button>
      </el-row>
    </el-aside>
  </el-container>
</template>
<script setup>
import {battleMock} from "@/views/mock/js/battleDto.js";


onMounted(() => {
  battleMock.getBoxList()
  battleMock.getRobotList()
  // 每秒钟获得一次房间列表
  setInterval(() => {
    battleMock.getRoomList()
  }, 1000)
})
</script>

<style lang="less" scoped>
.box {
  .box-main {
    height: calc(100vh - 322px);
  }

  .box-aside {
    margin-top: 0px;
    height: calc(100vh - 322px);
    border-left: 1px solid var(--el-border-color);

    .record_scrollbar {
      height: calc(100vh - 351px);
    }
  }
}
</style>