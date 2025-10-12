<template>
  <div>
    <!-- 查询区域 -->
    <div class="mb-4 mt-4">
      <label>优惠卷Id: </label>
      <el-input
        placeholder="请输入优惠卷id"
        v-model="queryParam.id"
        style="width: 300px;"
        class="mr-2"
      />
      <label>优惠卷code: </label>
        <el-input
            placeholder="请输入code"
            v-model="queryParam.code"
            style="width: 300px;"
            class="mr-2"
        />
        <label>有效期开始: </label>
        <el-date-picker
            v-model="queryParam.validFrom"
            type="date"
            placeholder="有效期开始"
            value-format="YYYY-MM-DD"
            class="mr-2"
        />
        <label>有效期结束: </label>
        <el-date-picker
            v-model="queryParam.validUtil"
            type="date"
            placeholder="有效期结束"
            value-format="YYYY-MM-DD"
        />
      <el-button type="primary" @click="onSearch">搜索</el-button>
    </div>

    <!-- 操作按钮 -->
    <div class="mb-4">
      <el-button type="primary" @click="handleAdd">新增</el-button>
      <el-button type="danger" @click="handleDeleteBatch" class="ml-2">删除</el-button>
    </div>

    <!-- 表格 -->
    <el-table :data="tableData" @selection-change="onSelectionChange">
      <el-table-column type="selection" width="55" />
      <el-table-column prop="id" label="优惠卷Id" align="center" width="120" />
      <el-table-column prop="code" label="优惠卷code"   align="center" width="180" />
        <el-table-column prop="description" align="center" label="优惠卷描述" width="100" />
        <el-table-column prop="discountType" align="center" label="优惠类型" width="100" />
        <el-table-column prop="discountValue" align="center" label="优惠值" width="100" />
        <el-table-column prop="maxDiscountAmount" align="center" label="最大优惠金额" width="120" />
        <el-table-column prop="minPurchaseAmount" align="center" label="最低消费金额" width="120" />
        <el-table-column prop="usageLimit" align="center" label="使用限制(每人)" width="120" />
        <el-table-column prop="usedCount" align="center" label="已使用次数" width="100" />
        <el-table-column prop="validFrom" align="center" label="有效期开始" width="120" />
        <el-table-column prop="validUtil" align="center" label="有效期结束" width="120" />
        <el-table-column prop="isActive" align="center" label="是否激活" width="100" >
            <template #default="scope">
                <el-switch v-model="scope.row.isActive" active-color="#13ce66" inactive-color="#ff4949" />
            </template>
        </el-table-column>
      <el-table-column label="操作" width="150" align="center">
        <template #default="scope">
          <el-button type="text" size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button type="text" size="mini" @click="handleDeleteOne(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="flex justify-center">
            <el-pagination v-model:current-page="pageQueryParam.page" v-model:page-size="pageQueryParam.pageSize"
             background layout="prev, pager, next" :total="100" />
        </div>
    <!-- 删除确认弹窗 -->
    <Dialog
      :title="dialogTitle"
      v-model:visible="dialogVisible"
      @confirm="confirmDelete"
    />
  </div>
</template>

<script setup>
import { ref } from 'vue';
import Dialog from '@/components/dialog/index.vue';
// 查询参数
const queryParam = ref({
    id: null,
    code: null,
    validFrom: null,
    validUtil: null,
});
const pageQueryParam = reactive({
    page: 1,
    pageSize: 10
});

// 表格数据（示例）
const tableData = ref([
  {
        id: 1,
        code: "fssd",
        description: "办公用品",
        discountType: "满减",
        discountValue: "100",
        maxDiscountAmount: "500",
        minPurchaseAmount: "1000",
        usageLimit: "1",
        usedCount: "0",
        validFrom: "2023-01-01",
        validUtil: "2023-12-31",
        isActive: true
  }
]);
watch(pageQueryParam, () => {
   console.log('页码或页大小变化:', pageQueryParam); 
}, { deep: true })

// 多选选中的行
const selectedRows = ref([]);

// 弹窗控制
const dialogVisible = ref(false);
const dialogTitle = ref('是否删除选中数据？');
const deleteType = ref(''); // 'batch' | 'single'
const currentIndex = ref(null);

// 监听选择变化
const onSelectionChange = (val) => {
  selectedRows.value = val;
};

// 搜索
const onSearch = () => {
  console.log('查询条件:', queryParam.value);
  // TODO: 调用 API 查询
};

// 新增
const handleAdd = () => {
  console.log('新增发票');
  // TODO: 跳转或弹窗
};

// 编辑
const handleEdit = (index, row) => {
  console.log('编辑:', row);
  // TODO: 编辑逻辑
};

// 单个删除
const handleDeleteOne = (index, row) => {
  dialogTitle.value = `是否删除优惠卷 ${row.id}？`;
  deleteType.value = 'single';
  currentIndex.value = index;
    dialogVisible.value = true;
  console.log('删除:', dialogVisible.value)
};

// 批量删除
const handleDeleteBatch = () => {
  if (selectedRows.value.length === 0) {
    ElMessage.warning('请先选择要删除的优惠卷');
    return;
  }
  dialogTitle.value = `是否删除选中的 ${selectedRows.value.length}`;
  deleteType.value = 'batch';
  dialogVisible.value = true;
};

// 确认删除
const confirmDelete = () => {
  if (deleteType.value === 'single' && currentIndex.value !== null) {
    tableData.value.splice(currentIndex.value, 1);
    ElMessage.success('删除成功');
  } else if (deleteType.value === 'batch') {
    // 这里可以按 ID 过滤，或根据选中项删除
    const selectedIds = selectedRows.value.map(r => r.id);
    tableData.value = tableData.value.filter(row => !selectedIds.includes(row.id));
    ElMessage.success('批量删除成功');
  }
  // 关闭弹窗
  dialogVisible.value = false;
};
</script>