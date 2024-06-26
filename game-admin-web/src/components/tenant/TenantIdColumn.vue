
<template>
  <template v-if="TenantUtil.isSuperTenant()">
    <el-table-column prop="tenantId" label="所属租户">
      <template #default="scope">
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

import TenantUtil from "@/core/tenant/index.js";
</script>
