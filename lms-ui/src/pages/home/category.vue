<template>
  <div>
    <!-- 查询区域 -->
    <div class="mb-4 mt-4">
      <label>分类Id: </label>
      <el-input
        placeholder="请输入分类id"
        v-model="queryParam.invoiceNumber"
        style="width: 300px;"
        class="mr-2"
      />
      <label>分类名称: </label>
        <el-input
            placeholder="请输入分类名称"
            v-model="queryParam.name"
            style="width: 300px;"
            class="mr-2"
        />
        <label>父分类Id: </label>
        <el-input
            placeholder="请输入父分类id"
            v-model="queryParam.parentId"
            style="width: 300px;"
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
      <el-table-column prop="id" align="center" label="分类Id" width="120" />
      <el-table-column prop="name" align="center" label="分类名称" width="180" />
        <el-table-column prop="description" align="center" label="分类描述" />
        <el-table-column prop="parentId" align="center" label="父分类Id" width="120" />
        <el-table-column prop="sortOrder" align="center" label="排序" width="100" />
        <el-table-column prop="isActive" align="center" label="是否激活" width="100" >
            <template #default="scope">
                <el-switch v-model="scope.row.isActive" active-color="#13ce66" inactive-color="#ff4949" />
            </template>
        </el-table-column>
      <el-table-column label="操作" align="center">
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
    name: null,
    parentId: null
});
const pageQueryParam = reactive({
    page: 1,
    pageSize: 10
});

// 表格数据（示例）
const tableData = ref([
  {
        id: 1,
        name: '办公用品',
        description: '各类办公用品',
        parentId: 0,
        sortOrder: 1,
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
  dialogTitle.value = `是否删除分类 ${row.id}？`;
  deleteType.value = 'single';
  currentIndex.value = index;
    dialogVisible.value = true;
  console.log('删除:', dialogVisible.value)
};

// 批量删除
const handleDeleteBatch = () => {
  if (selectedRows.value.length === 0) {
    ElMessage.warning('请先选择要删除的分类');
    return;
  }
  dialogTitle.value = `是否删除选中的 ${selectedRows.value.length} 条发票？`;
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