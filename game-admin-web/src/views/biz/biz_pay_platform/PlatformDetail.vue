<template>
  <div class="bbg_sub_table">
    <el-form label-position="right" label-width="120" size="small">
      <el-row>
        <el-col :span="8">
          <el-row>
            <el-col :span="12">
              <el-form-item label="调用引擎:">
                {{ props.rowOjb.callEngine }}
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="调用引擎重载:">
                {{ props.rowOjb.callReload }}
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="22">
              <el-form-item label="调用参数:">
                <el-tooltip placement="top">
                  <template #content>
                    <el-scrollbar class="script-content-scrollbar" :max-height="200">
                      {{ props.rowOjb.callArg }}
                    </el-scrollbar>
                  </template>
                  <el-text line-clamp="2" class="script-content">
                    {{ props.rowOjb.callArg }}
                  </el-text>
                </el-tooltip>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="22">
              <el-form-item label="调用引擎脚本:">
                <el-tooltip placement="top">
                  <template #content>
                    <el-scrollbar class="script-content-scrollbar" :max-height="200">
                      {{ props.rowOjb.callContent }}
                    </el-scrollbar>
                  </template>
                  <el-text line-clamp="2" class="script-content">
                    {{ props.rowOjb.callContent }}
                  </el-text>
                </el-tooltip>
                <el-row justify="end" style="width: 100%">
                  <el-button size="small" type="success" link @click="callContentRef.editScript()">编辑代码
                  </el-button>
                </el-row>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="8">
          <el-row>
            <el-col :span="12">
              <el-form-item label="回调引擎:">
                {{ props.rowOjb.callbackEngine }}
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="回调引擎重载:">
                {{ props.rowOjb.callbackReload }}
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="22">
              <el-form-item label="回调参数:">
                <el-tooltip placement="top">
                  <template #content>
                    <el-scrollbar class="script-content-scrollbar" :max-height="200">
                      {{ props.rowOjb.callbackArg }}
                    </el-scrollbar>
                  </template>
                  <el-text line-clamp="2" class="script-content">
                    {{ props.rowOjb.callbackArg }}
                  </el-text>
                </el-tooltip>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="22">
              <el-form-item label="回调参数脚本:">
                <el-tooltip placement="top">
                  <template #content>
                    <el-scrollbar class="script-content-scrollbar" :max-height="200">
                      {{ props.rowOjb.callbackContent }}
                    </el-scrollbar>
                  </template>
                  <el-text line-clamp="2" class="script-content">
                    {{ props.rowOjb.callbackContent }}
                  </el-text>
                </el-tooltip>
                <el-row justify="end" style="width: 100%">
                  <el-button size="small" type="success" link @click="callContentRef.editScript()">编辑代码
                  </el-button>
                </el-row>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="8">
          <el-row>
            <el-col :span="12">
              <el-form-item label="查询引擎:">
                {{ props.rowOjb.queryEngine }}
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="查询引擎重载:">
                {{ props.rowOjb.queryReload }}
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="22">
              <el-form-item label="查询参数:">
                <el-tooltip placement="top">
                  <template #content>
                    <el-scrollbar class="script-content-scrollbar" :max-height="200">
                      {{ props.rowOjb.queryArg }}
                    </el-scrollbar>
                  </template>
                  <el-text line-clamp="2" class="script-content">
                    {{ props.rowOjb.queryArg }}
                  </el-text>
                </el-tooltip>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="22">
              <el-form-item label="查询引擎脚本:">
                <el-tooltip placement="top">
                  <template #content>
                    <el-scrollbar class="script-content-scrollbar" :max-height="200">
                      {{ props.rowOjb.queryContent }}
                    </el-scrollbar>
                  </template>
                  <el-text line-clamp="2" class="script-content">
                    {{ props.rowOjb.queryContent }}
                  </el-text>
                </el-tooltip>
                <el-row justify="end" style="width: 100%">
                  <el-button size="small" type="success" link @click="scriptEditRef.editScript(props.rowOjb,'queryContent')" >编辑代码</el-button>
                </el-row>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
    </el-form>
  </div>
  <ScriptEdit ref="scriptEditRef" :callback="scriptEditCallBack"></ScriptEdit>
</template>
<script setup>

import {http} from "@/core/axios/index.js";

const scriptEditRef = ref(null)
const scriptEditCallBack = async (rowOjb,editField) => {
  const request = {
    id: rowOjb.id.toString(),
    [editField]: rowOjb[editField]
  }
  // const apiRet = await http.post('/bizPayPlatform/update', request)
  // if (apiRet.ok) {
  //   ElMessage({type: 'success', message: apiRet.msg})
  // }
}

const contentWidth = computed(() => props.width - 20 + 'px')
const props = defineProps({
  rowOjb: {
    type: Object,
    required: true
  },
  width: {
    type: Number,
    default: 100
  }
})
</script>
<style scoped lang="less">
.bbg_sub_table {
  width: v-bind(contentWidth);
  margin: 10px;
  border-right: 1px solid var(--el-border-color);
}

.script-content-scrollbar{
  white-space: pre-wrap;
  padding-right: 8px;
  min-width: 400px;
  max-width: 500px;
}

.script-content {
  white-space: pre-wrap; /* 保留换行符和空白字符 */
  font-family: monospace; /* 使用等宽字体显示代码 */
  background-color: var(--el-fill-color-light); /* 可选：设置背景颜色 */
  padding: 1px; /* 可选：设置内边距 */
  border-radius: 1px; /* 可选：设置圆角边框 */
  border: 1px solid #ccc; /* 可选：设置边框 */
  width: 100%;
}
</style>