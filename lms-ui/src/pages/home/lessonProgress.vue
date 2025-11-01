<template>
  <div>
    <!-- 查询区域 -->
    <div class="mb-4 mt-4">
      <label>进度id</label>
        <el-input
            placeholder="请输入进度id"
            v-model="queryParam.id"
            style="width: 300px;"
            class="mr-2"
        />
        <label>用户名:</label>
        <el-input
            placeholder="请输入用户名"
            v-model="queryParam.username"
            style="width: 300px;"
            class="mr-2"
        />
        <label>课程id:</label>
        <el-input
            placeholder="请输入课程id"
            v-model="queryParam.lessonId"
            style="width: 300px;"
            class="mr-2"
        />
        <label>状态:</label>
        <el-select v-model="queryParam.status" placeholder="请选择状态" style="width: 300px;" class="mr-2">
            <el-option label="已完成" value="1" />
            <el-option label="未完成" value="0" />
        </el-select>
        <label>开始时间:</label>
        <el-date-picker
            v-model="queryParam.startedAt"
            type="date"
            placeholder="选择开始时间"
            style="width: 300px;"
            ></el-date-picker
        >
        <label>结束时间:</label>
        <el-date-picker
            v-model="queryParam.completedAt"
            type="date"
            placeholder="选择结束时间"
            style="width: 300px;"
        >
        </el-date-picker>
        <label>修改时间:</label>
        <el-date-picker
            v-model="queryParam.updatedAt"
            type="date"
            placeholder="选择修改时间"
            style="width: 300px;"
        >
        </el-date-picker>
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
        <el-table-column prop="id" label="进度Id" align="center" width="120" />
        <el-table-column prop="enrollmentId" label="报名Id" align="center" width="120" />
        <el-table-column prop="username" label="用户名" align="center" width="120" />
        <el-table-column prop="lessonId" label="课程Id" align="center" width="120" />
        <el-table-column prop="status" label="状态" align="center" width="120">
            <template #default="scope">
                <span v-if="scope.row.status === 1">已完成</span>
                <span v-else>未完成</span>
            </template>
        </el-table-column>
        <el-table-column prop="progressPercentage" label="进度百分比" align="center" width="120" />
        <el-table-column prop="lastPosition" label="最后位置" align="center" width="120" />
        <el-table-column prop="startedAt" label="开始时间" align="center" width="180" />
        <el-table-column prop="completedAt" label="完成时间" align="center" width="180" />
        <el-table-column prop="updatedAt" label="修改时间" align="center" width="180" />
      <el-table-column label="操作" width="150" align="center">
        <template #default="scope">
          <el-button type="text" size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button type="text" size="small" @click="handleDeleteOne(scope.$index, scope.row)">删除</el-button>
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
    <el-dialog v-model:visible="dialogAddVisible" title="添加" width="500px">
        <el-form ref="form" :model="lessonProgress" label-width="80px">
            <el-form-item label="报名Id"> 
                <el-input v-model="lessonProgress.enrollmentId" placeholder="请输入报名id" />
            </el-form-item>
            <el-form-item label="用户名"> 
                <el-input v-model="lessonProgress.username" placeholder="请输入用户名" />
            </el-form-item>
            <el-form-item label="课程Id"> 
                <el-input v-model="lessonProgress.lessonId" placeholder="请输入课程id" />
            </el-form-item>
            <el-form-item label="状态"> 
                <el-select v-model="lessonProgress.status" placeholder="请选择状态">
                    <el-option label="已完成" value="1" />
                    <el-option label="未完成" value="0" />
                </el-select>
            </el-form-item>
            <el-form-item label="进度百分比"> 
                <el-input v-model="lessonProgress.progressPercentage" placeholder="请输入进度百分比" />
            </el-form-item>
            <el-form-item label="最后位置"> 
                <el-input v-model="lessonProgress.lastPosition" placeholder="请输入最后位置" />
            </el-form-item>
            <el-form-item label="开始时间"> 
                <el-date-picker
                    v-model="lessonProgress.startedAt"
                    type="date"
                    placeholder="选择开始时间"
                >
                </el-date-picker>
            </el-form-item>
            <el-form-item label="完成时间"> 
                <el-date-picker
                    v-model="lessonProgress.completedAt"
                    type="date"
                    placeholder="选择完成时间"
                >
                </el-date-picker>
            </el-form-item>
            <el-form-item label="修改时间"> 
                <el-date-picker
                    v-model="lessonProgress.updatedAt"
                    type="date"
                    placeholder="选择修改时间"
                >
                </el-date-picker>
            </el-form-item>
            <el-button type="primary" @click="save">确定</el-button>
            <el-button @click="dialogAddVisible = false">取消</el-button>
        </el-form>
        </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import Dialog from '@/components/dialog/index.vue';
import { lessonProgressPage } from '../../api/lessonprogress/lessonprogress';
// 查询参数
const queryParam = ref({
    id: null,
    username: null,
    lessonId: null,
    status: null,
    startedAt: null,
    completedAt: null,
    updateAt: null
});
const lessonProgress = ref({
  id: null,
  enrollmentId: null,
  username: null,
  lessonId: null,
  status: null,
  progressPercentage: null,
  lastPosition: null,
  startedAt: null,
  completedAt: null,
  updatedAt: null,
})
const pageQueryParam = reactive({
    total: 0,
    pageNum: 1,
    pageSize: 10,
    lessonProgress: lessonProgress.value
});
// 表格数据（示例）
const tableData = ref([]);
const pageQuery = () => {
    lessonProgressPage(pageQueryParam).then(res => {
        tableData.value = res.list;
        pageQueryParam.total = res.total;
        pageQueryParam.pageNum = res.pageNum;
        pageQueryParam.pageSize = res.pageSize;
     })
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

// 监听选择变化
const onSelectionChange = (val) => {
  selectedRows.value = val;
};

// 搜索
const onSearch = () => {
    // TODO: 调用 API 查询
    Object.assign(pageQueryParam.lessonProgress, queryParam.value);
    pageQuery();
};

// 新增
const handleAdd = () => {
    // TODO: 跳转或弹窗
    Object.assign(lessonProgress.value, {
        id: null,
        enrollmentId: null,
        username: null,
        lessonId: null,
        status: null,
        progressPercentage: null,
        lastPosition: null,
        startedAt: null,
        completedAt: null,
        updatedAt: null,
    });
    dialogAddVisible.value = true;
};

// 编辑
const handleEdit = (index, row) => {
    currentEditIndex.value = index;
    const rawRow = toRaw(row);
    Object.assign(lessonProgress.value, rawRow);
    dialogEditVisible.value = true;
  // TODO: 编辑逻辑
};

// 单个删除
const handleDeleteOne = (index, row) => {
  dialogTitle.value = `是否删除 ${row.id}？`;
  deleteType.value = 'single';
  currentIndex.value = index;
    dialogVisible.value = true;
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