<template>
  <div>
    <!-- 查询区域 -->
    <div class="mb-4 mt-4">
        <label>id: </label>
        <el-input v-model="queryParam.id" placeholder="请输入ID" class="mr-2 mb-1" style="width: 150px;" />
        <label>标题: </label>
        <el-input v-model="queryParam.title" placeholder="请输入标题" class="w-40 mr-2  mb-1" style="width: 200px;" />
        <label>讲师ID: </label>
        <el-input v-model="queryParam.instructorId" placeholder="请输入讲师ID" class="w-40 mr-2  mb-1" style="width: 150px;" />
        <label>分类ID: </label>
        <el-input v-model="queryParam.categoryId" placeholder="请输入分类ID" class="w-40 mr-2  mb-1" style="width: 150px;" />
        <label>难度级别: </label>
        <el-input v-model="queryParam.level" placeholder="请输入难度级别" class="w-40 mr-2  mb-1" style="width: 200px;" />
        <label>语言: </label>
        <el-input v-model="queryParam.language" placeholder="请输入语言" class="w-40 mr-2  mb-1" style="width: 150px;" />
        <label>是否草稿: </label>
        <el-select v-model="queryParam.isDraft" placeholder="请选择" class="w-40 mr-2  mb-1" style="width: 100px;">
            <el-option :label="'是'" :value="true" />
            <el-option :label="'否'" :value="false" />
            <el-option :label="'不限'" :value="null" />
        </el-select>
        <label>审核通过: </label>
        <el-select v-model="queryParam.isApproved" placeholder="请选择" class="w-40 mr-2  mb-1" style="width: 200px;">
            <el-option :label="'是'" :value="true" />
            <el-option :label="'否'" :value="false" />
            <el-option :label="'不限'" :value="null" />
        </el-select>
        <label>评分: </label>
        <el-input v-model="queryParam.rating" placeholder="请输入评分" class="w-40 mr-2  mb-1" style="width: 150px;"/>
        <label>是否免费: </label>
        <el-select v-model="queryParam.isFree" placeholder="请选择" class="w-40 mr-2  mb-1" style="width: 200px;">
            <el-option :label="'是'" :value="true" />
            <el-option :label="'否'" :value="false" />
            <el-option :label="'不限'" :value="null" />
        </el-select>
        <label>发证: </label>
        <el-select v-model="queryParam.willIssueCertificate" placeholder="请选择" class="w-40 mr-2  mb-1" style="width: 200px;">
            <el-option :label="'是'" :value="true" />
            <el-option :label="'否'" :value="false" />
            <el-option :label="'不限'" :value="null" />
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
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="标题" width="180" />
        <el-table-column prop="subtitle" label="副标题" width="180" />
        <el-table-column prop="instructorId" label="讲师ID" width="100" />
        <el-table-column prop="categoryId" label="分类ID" width="100" />
        <el-table-column prop="level" label="难度级别" width="100" />
        <el-table-column prop="language" label="语言" width="100" />
        <el-table-column prop="durationHours" label="时长(小时)" width="100" />
        <el-table-column prop="isDraft" label="草稿" width="80">
            <template #default="scope">
                <span>{{ scope.row.isDraft ? '是' : '否' }}</span>
            </template>
        </el-table-column>
        <el-table-column prop="isApproved" label="审核通过" width="100">
            <template #default="scope">
                <span>{{ scope.row.isApproved ? '是' : '否' }}</span>
            </template>
        </el-table-column>
        <el-table-column prop="approvalDate" label="审核日期" width="120" />
        <el-table-column prop="rating" label="评分" width="80" />
        <el-table-column prop="totalEnrollments" label="总报名数" width="100" />
        <el-table-column prop="price" label="价格" width="100" />
        <el-table-column prop="discountPrice" label="折扣价" width="100" />
        <el-table-column prop="isFree" label="免费" width="80">
            <template #default="scope">
                <span>{{ scope.row.isFree ? '是' : '否' }}</span>
            </template>
        </el-table-column>
        <el-table-column prop="willIssueCertificate" label="发证" width="100">
            <template #default="scope">
                <span>{{ scope.row.willIssueCertificate ? '是' : '否' }}</span>
            </template>
        </el-table-column>
      <el-table-column label="操作" align="center" width="150">
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
import { ref,watch,reactive } from 'vue';
import Dialog from '@/components/dialog/index.vue';
// 查询参数
const queryParam = ref({
    id: null,
    title: '',
    instructorId: null,
    categoryId: null,
    level: '',
    language: '',
    isDraft: null,
    isApproved: null,
    rating: null,
    isFree: null,
    willIssueCertificate: null,
});
const pageQueryParam = reactive({
    page: 1,
    pageSize: 10
});

// 表格数据（示例）
const tableData = ref([
  {
        id: 1,
        title: 'kfsfd',
        subtitle: 'sdfsdf',
        description: 'sdfsdf',
        instructorId: 1,
        categoruId: 1,
        level: '初级',
        language: '中文',
        thumbnailUrl: 'https://example.com/image1.jpg',
        videoPreviewUrl: 'https://example.com/preview1.mp4',
        durationHours: 5,
        isDraft: false,
        isApproved: true,
        approvalDate: '2023-01-01',
        rating: 4.5,
        totalEnrollments: 100,
        price: 99.99,
        discountPrice: 49.99,
        isFree: false,
        willIssueCertificate: true,
        requirements: '无',
        learningOutcomes: '掌握基础知识',
        targetAudience: '初学者'
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
  dialogTitle.value = `是否删除课程信息 ${row.id}？`;
  deleteType.value = 'single';
  currentIndex.value = index;
    dialogVisible.value = true;
  console.log('删除:', dialogVisible.value)
};

// 批量删除
const handleDeleteBatch = () => {
  if (selectedRows.value.length === 0) {
    ElMessage.warning('请先选择要删除的课程信息');
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