<template>
  me1: {{script}}<br>
  me2: {{props.value}}<br>
  <div class="monaco-editor" ref="monacoEditorRef"/>
</template>
<script setup>
import editorWorker from 'monaco-editor/esm/vs/editor/editor.worker?worker';
import jsonWorker from 'monaco-editor/esm/vs/language/json/json.worker?worker';
import cssWorker from 'monaco-editor/esm/vs/language/css/css.worker?worker';
import htmlWorker from 'monaco-editor/esm/vs/language/html/html.worker?worker';
import tsWorker from 'monaco-editor/esm/vs/language/typescript/ts.worker?worker';
import * as monaco from "monaco-editor";

self.MonacoEnvironment = {
  getWorker(_, label) {
    if (label === 'json') return new jsonWorker()
    if (label === 'css' || label === 'scss' || label === 'less') return new cssWorker()
    if (label === 'html' || label === 'handlebars' || label === 'razor') return new htmlWorker()
    if (['typescript', 'javascript'].includes(label)) return new tsWorker()
    return new editorWorker()
  },
}

const props = defineProps({
  value: {type: String},
  type: {type: String, default: "javascript"}
});
const script = ref(props.value)                     //脚本内容
const emit = defineEmits(['update:value'])
const monacoEditorRef = ref(null);                  //编辑器引用
let editor = ref(null); //编辑器实例

watchEffect(() => {
  if (editor.value) {
    toRaw(editor.value).setValue(script.value === undefined ? "" : script.value)
  }
})

onMounted(() => {
  initEditor();
})

function initEditor() {
  monaco.languages.typescript.javascriptDefaults.setDiagnosticsOptions({
    noSemanticValidation: true,
    noSyntaxValidation: false
  })
  monaco.languages.typescript.javascriptDefaults.setCompilerOptions({
    target: monaco.languages.typescript.ScriptTarget.Latest,
    allowNonTsExtensions: true
  })
  editor.value = monaco.editor.create(monacoEditorRef.value, {
    value: props.value, // 代码
    theme: "vs-dark", // 主题
    language: props.type,  // 语言
    folding: true, // 是否折叠
    foldingHighlight: true, // 折叠等高线
    foldingStrategy: "indentation", // 折叠方式  auto | indentation
    showFoldingControls: "always", // 是否一直显示折叠 always | mouseover
    disableLayerHinting: true, // 等宽优化
    emptySelectionClipboard: false, // 空选择剪切板
    selectionClipboard: false, // 选择剪切板
    automaticLayout: true, // 自动布局
    codeLens: false, // 代码镜头
    scrollBeyondLastLine: false, // 滚动完最后一行后再滚动一屏幕
    colorDecorators: true, // 颜色装饰器
    accessibilitySupport: "off", // 辅助功能支持  "auto" | "off" | "on"
    lineNumbers: "on", // 行号 取值： "on" | "off" | "relative" | "interval" | function
    lineNumbersMinChars: 5, // 行号最小字符   number
    readOnly: false, //是否只读  取值 true | false
  })
  editor.value.onDidChangeModelContent((val) => {
    emit('update:value', toRaw(editor.value).getValue())
  })
}
</script>
<style scoped>
.monaco-editor {
  width: 100%;
  height: 100%;
}
</style>