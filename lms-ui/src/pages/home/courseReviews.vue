<template>
  <div>
    <!-- 查询区域 -->
    <div class="mb-4 mt-4">
      <label>课程id</label>
        <el-input
            placeholder="请输入课程id"
            v-model="queryParam.courseId"
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
        <label>评分</label>
        <el-input
            placeholder="请输入评分"
            v-model="queryParam.rating"
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
        <el-table-column prop="id" label="课程评价Id" align="center" width="120" />
        <el-table-column prop="courseId" label="课程Id" align="center" width="120" />
        <el-table-column prop="userId" label="用户Id" align="center" width="120" />
        <el-table-column prop="rating" label="评分" align="center" width="100" />
        <el-table-column prop="comment" label="评论内容" align="center" width="300" />
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
            <el-pagination v-model:current-page="pageQueryParam.pageNum" v-model:page-size="pageQueryParam.pageSize"
             background layout="prev, pager, next,jumper,->,total" v-model:total="pageQueryParam.total" />
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
import { onMounted, ref } from 'vue';
import Dialog from '@/components/dialog/index.vue';
import { courseReviewsDelete, courseReviewsPage } from '../../api/courseReviews/courseReviews';
// 查询参数
const queryParam = ref({
    id: null,
    courseId: null,
    userId: null,
    rating: null,
    createdAt: null,
    updatedAt: null,
});
const courseReviews = ref({
  id: null,
  courseId: null,
  userId: null,
  rating: null,
  comment: null,
  isApproved: 0,
  createdAt: null,
  updatedAt: null,
})
const pageQueryParam = reactive({
    total: 0,
    pageNum: 1,
    pageSize: 10,
    courseReviews: courseReviews.value
});
// 表格数据（示例）
const tableData = ref([
  {
        id: 1,
        courseId: 101,
        userId: 1001,
        rating: 5,
        comment: "Great course!",
        createdAt: "2023-10-01 10:00:00",
        updatedAt: "2023-10-01 10:00:00"
  }
]);
const pageQuery = () => {
    courseReviewsPage(pageQueryParam).then(res => {
        tableData.value = res.list;
        pageQueryParam.total = res.total;
        pageQueryParam.pageNum = res.pageNum;
        pageQueryParam.pageSize = res.pageSize;
    });
}
onMounted(() => {
    pageQuery();
})
watch(pageQueryParam, () => {
   console.log('页码或页大小变化:', pageQueryParam); 
}, { deep: true })

// 多选选中的行
const selectedRows = ref([]);

// 弹窗控制
const dialogVisible = ref(false);
const dialogAddVisible = ref(false);
const dialogEditVisible = ref(false);
const dialogTitle = ref('是否删除选中数据？');
const deleteType = ref(''); // 'batch' | 'single'
const currentIndex = ref(null);
const currentEditIndex = ref(null);
const ids = ref([]);
// 监听选择变化
const onSelectionChange = (val) => {
  selectedRows.value = val;
};
const cancel = () => { 
    if (currentEditIndex.value !== null) {
        Object.assign(courseReviews.value, {
            id: null,
            courseId: null,
            userId: null,
            rating: null,
            comment: null,
            isApproved: 0,
            createdAt: null,
            updatedAt: null,
        })
        dialogEditVisible = false
    } else {
        Object.assign(courseReviews.value, {
            id: null,
            courseId: null,
            userId: null,
            rating: null,
            comment: null,
            isApproved: 0,
            createdAt: null,
            updatedAt: null,
        })
        dialogAddVisible = false
    }
};

// 搜索
const onSearch = () => {
    // TODO: 调用 API 查询
  Object.assign(pageQueryParam.courseReviews, queryParam.value);
    pageQuery();
};

// 新增
const handleAdd = () => {
    // TODO: 跳转或弹窗
    Object.assign(courseReviews.value, {
    id: null,
    courseId: null,
    userId: null,
    rating: null,
    comment: null,
    isApproved: 0,
    createdAt: null,
    updatedAt: null,
    });
    dialogAddVisible.value = true;
};

// 编辑
const handleEdit = (index, row) => {
    // TODO: 编辑逻辑
    Object.assign(courseReviews.value, row);
    currentEditIndex.value = index;
    dialogEditVisible.value = true;
};

// 单个删除
const handleDeleteOne = (index, row) => {
  dialogTitle.value = `是否删除课程评价 ${row.id}？`;
  deleteType.value = 'single';
    currentIndex.value = index;
  ids.value.push(row.id)
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
    courseReviewsDelete(ids.value)
    ElMessage.success('删除成功');
  } else if (deleteType.value === 'batch') {
    // 这里可以按 ID 过滤，或根据选中项删除
    const selectedIds = selectedRows.value.map(r => r.id);
      tableData.value = tableData.value.filter(row => !selectedIds.includes(row.id));
    courseReviewsDelete(selectedIds)
    ElMessage.success('批量删除成功');
  }
  // 关闭弹窗
  dialogVisible.value = false;
};
</script>