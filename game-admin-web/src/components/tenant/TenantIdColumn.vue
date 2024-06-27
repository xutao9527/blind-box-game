
<template>
  <template v-if="TenantUtil.isSuperTenant()">
    <el-table-column prop="tenantId" label="所属租户">
      <template #default="scope">
        {{TenantUtil.isSuperTenant()}}
        <el-tooltip>
          <template #content>
            {{ scope.row.tenantId }}
          </template>
          {{ TenantUtil.getTenantName(scope.row.tenantId) }}
        </el-tooltip>
      </template>
    </el-table-column>
  </template>
</template>
<script setup>

</script>
