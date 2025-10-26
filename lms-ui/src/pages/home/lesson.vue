<template>
  <div>
    <!-- 查询区域 -->
    <div class="mb-4 mt-4">
      <label>lessonid</label>
        <el-input
            placeholder="请输入lessonid"
            v-model="queryParam.id"
            style="width: 300px;"
            class="mr-2"
        />
        <label>moduleId</label>
        <el-input
            placeholder="请输入moduleId"
            v-model="queryParam.moduleId"
            style="width: 300px;"
            class="mr-2"
        />
        <label>title</label>
        <el-input
            placeholder="请输入title"
            v-model="queryParam.title"
            style="width: 300px;"
            class="mr-2"
        />
        <label>contentType</label>
        <el-input
            placeholder="请输入contentType"
            v-model="queryParam.contentType"
            style="width: 300px;"
            class="mr-2"
        />
        <label>创建时间</label>
        <el-date-picker
            v-model="queryParam.createdAt"
            type="date"
            placeholder="创建时间"
            value-format="YYYY-MM-DD"
            class="mr-2"
        />
        <label>更新时间</label>
        <el-date-picker
            v-model="queryParam.updatedAt"
            type="date"
            placeholder="更新时间"
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
        <el-table-column prop="id" label="id" align="center" width="120" />
        <el-table-column prop="moduleId" label="模块Id" align="center" width="120" />
        <el-table-column prop="title" label="课时标题" align="center" width="200" />
        <el-table-column prop="contentType" label="内容类型" align="center" width="120" />
        <el-table-column prop="content" label="内容" align="center" width="300" />
        <el-table-column prop="videoUrl" label="视频地址" align="center" width="300" />
        <el-table-column prop="videoDuration" label="视频时长(秒)" align="center" width="150" />
        <el-table-column prop="attachmentUrl" label="附件地址" align="center" width="300" />
        <el-table-column prop="sortOrder" label="排序" align="center" width="100" />
        <el-table-column prop="isPreviewAvailable" label="是否可预览" align="center" width="120" />
        <el-table-column prop="createdAt" label="创建时间" align="center" width="180" />
        <el-table-column prop="updatedAt" label="更新时间" align="center" width="180" />
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
    moduleId: null,
    title: '',
    contentType: '',
    createdAt: null,
    updatedAt: null
});
const pageQueryParam = reactive({
    page: 1,
    pageSize: 10
});

// 表格数据（示例）
const tableData = ref([
  {
        id: 1,
        moduleId: 101,
        title: '第一课时',
        contentType: 'video',
        content: 'http://example.com/video1.mp4',
        videoUrl: 'http://example.com/video1.mp4',
        videoDuration: 600,
        attachmentUrl: null,
        sortOrder: 1,
        isPreviewAvailable: 1,
        createdAt: '2023-10-01 10:00:00',
        updatedAt: '2023-10-01 10:00:00'
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
  dialogTitle.value = `是否删除课程评价 ${row.id}？`;
  deleteType.value = 'single';
  currentIndex.value = index;
    dialogVisible.value = true;
  console.log('删除:', dialogVisible.value)
};

// 批量删除
const handleDeleteBatch = () => {
  if (selectedRows.value.length === 0) {
    ElMessage.warning('请先选择要删除的课程评价');
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