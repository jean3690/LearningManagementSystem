<template>
  <div>
    <!-- 查询区域 -->
    <div class="mb-4 mt-4">
      <label>报名id</label>
      <el-input
          placeholder="请输入报名id"
          v-model="queryParam.id"
          style="width: 300px;"
          class="mr-2"
      />
      <label>用户id</label>
        <el-input
            placeholder="请输入用户id"
            v-model="queryParam.userId"
            style="width: 300px;"
            class="mr-2"
        />
      <label>课程id</label>
        <el-input
            placeholder="请输入课程id"
            v-model="queryParam.courseId"
            style="width: 300px;"
            class="mr-2"
        />
        <label>报名日期</label>
        <el-date-picker
            v-model="queryParam.enrolledAt"
            type="date"
            placeholder="选择报名日期"
            style="width: 180px;"
            class="mr-2"
        />
        <label>完成日期</label>
        <el-date-picker
            v-model="queryParam.completedAt"
            type="date"
            placeholder="选择完成日期"
            style="width: 180px;"
            class="mr-2"
        />
      <label>是否有效</label>
        <el-select v-model="queryParam.isActive" placeholder="请选择是否有效" style="width: 150px;" class="mr-2">
            <el-option label="全部" :value="0" />
            <el-option label="有效" :value="1" />
            <el-option label="无效" :value="2" />
        </el-select>
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
      <el-table-column prop="id" label="报名id" align="center" width="120" />
        <el-table-column prop="userId" label="用户id" align="center" width="120" />
        <el-table-column prop="courseId" label="课程id" align="center" width="120" />
        <el-table-column prop="orderItemId" label="订单项id" align="center" width="120" />
        <el-table-column prop="enrolledAt" label="报名日期" align="center" width="150" />
        <el-table-column prop="completedAt" label="完成日期" align="center" width="150" />
        <el-table-column prop="completionPercentage" label="完成百分比" align="center" width="150" />
        <el-table-column prop="lastAccessedAt" label="最后访问时间" align="center" width="150" />
        <el-table-column prop="lastLessonId" label="最后课程id" align="center" width="120" />
        <el-table-column prop="isActive" label="是否有效" align="center" width="100">
          <template #default="scope">
            <span v-if="scope.row.isActive === 1">有效</span>
            <span v-else>无效</span>
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
  userId: null,
  courseId: null,
  enrolledAt: null,
  completedAt: null,
  isActive: 0
});
const pageQueryParam = reactive({
  page: 1,
  pageSize: 10
});

// 表格数据（示例）
const tableData = ref([
  {
    id: 1,
    userId: 1,
    courseId: 1,
    orderItemId: 1,
    enrolledAt: "2024-01-01",
    completedAt: "2024-01-01",
    completionPercentage: "10%",
    lastAccessedAt: "2024-01-01",
    lastLessonId: "2024-01-01",
    isActive: 1,
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
  console.log('新增');
  // TODO: 跳转或弹窗
};

// 编辑
const handleEdit = (index, row) => {
  console.log('编辑:', row);
  // TODO: 编辑逻辑
};

// 单个删除
const handleDeleteOne = (index, row) => {
  dialogTitle.value = `是否删除 ${row.id}？`;
  deleteType.value = 'single';
  currentIndex.value = index;
  dialogVisible.value = true;
  console.log('删除:', dialogVisible.value)
};

// 批量删除
const handleDeleteBatch = () => {
  if (selectedRows.value.length === 0) {
    ElMessage.warning('请先选择要删除的');
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