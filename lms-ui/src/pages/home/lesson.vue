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
      <el-table-column type="selection" width="55"/>
      <el-table-column prop="id" label="id" align="center" width="120"/>
      <el-table-column prop="moduleId" label="模块Id" align="center" width="120"/>
      <el-table-column prop="title" label="课时标题" align="center" width="200"/>
      <el-table-column prop="contentType" label="内容类型" align="center" width="120"/>
      <el-table-column prop="content" label="内容" align="center" width="300"/>
      <el-table-column prop="videoUrl" label="视频地址" align="center" width="300"/>
      <el-table-column prop="videoDuration" label="视频时长(秒)" align="center" width="150"/>
      <el-table-column prop="attachmentUrl" label="附件地址" align="center" width="300"/>
      <el-table-column prop="sortOrder" label="排序" align="center" width="100"/>
      <el-table-column prop="isPreviewAvailable" label="是否可预览" align="center" width="120">
        <template #default="scope">
          {{ scope.row.isPreviewAvailable === 0 ? "不可预览" : "可预览" }}
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
            <el-pagination v-model:current-page="pageQueryParam.pageNum" v-model:page-size="pageQueryParam.pageSize"
             background layout="prev, pager, next,jumper,->,total" v-model:total="pageQueryParam.total" />
        </div>
    <!-- 删除确认弹窗 -->
    <Dialog
        :title="dialogTitle"
        v-model:visible="dialogVisible"
        @confirm="confirmDelete"
    />
    <el-dialog v-model:visible="dialogAddVisible" title="新增课时">
        <el-form ref="form" :model="lesson" label-width="80px">
            <el-form-item label="模块Id">
                <el-input v-model="lesson.moduleId" placeholder="请输入模块Id" />
            </el-form-item>
            <el-form-item label="课时标题">
                <el-input v-model="lesson.title" placeholder="请输入课时标题" />
            </el-form-item>
            <el-form-item label="内容类型">
                <el-input v-model="lesson.contentType" placeholder="请输入内容类型" />
            </el-form-item>
            <el-form-item label="内容">
                <el-input v-model="lesson.content" placeholder="请输入内容" />
            </el-form-item>
            <el-form-item label="视频地址">
                <el-input v-model="lesson.videoUrl" placeholder="请输入视频地址" />
            </el-form-item>
            <el-form-item label="视频时长(秒)"> 
                <el-input v-model="lesson.videoDuration" placeholder="请输入视频时长(秒)" />
            </el-form-item>
            <el-form-item label="附件地址">
                <el-input v-model="lesson.attachmentUrl" placeholder="请输入附件地址" />
            </el-form-item>
            <el-form-item label="排序">
                <el-input v-model="lesson.sortOrder" placeholder="请输入排序" />
            </el-form-item>
            <el-form-item label="是否可预览">
                <el-switch v-model="lesson.isPreviewAvailable" />
            </el-form-item>
            <el-form-item label="课时进度"> 
                <el-input v-model="lesson.lessonProgress.enrollmentId" placeholder="请输入报名id" />
                <el-input v-model="lesson.lessonProgress.progressPercentage" placeholder="请输入课时进度" />
                <el-input v-model="lesson.lessonProgress.lastPosition" placeholder="请输入最后进度" />
                <el-input v-model="lesson.lessonProgress.status" placeholder="请输入状态" />
                <el-input v-model="lesson.lessonProgress.startedAt" placeholder="请输入开始时间" />
                <el-input v-model="lesson.lessonProgress.completedAt" placeholder="请输入完成进度" />
                <el-input v-model="lesson.lessonProgress.updatedAt" placeholder="请输入更新时间" />
            </el-form-item>
            <el-form-item label="报名id">
                <el-input v-model="lesson.lessonProgress.enrollmentId" placeholder="请输入报名id" />
            </el-form-item>
            <el-form-item label="用户名">
                <el-input v-model="lesson.lessonProgress.username" placeholder="请输入用户名" />
            </el-form-item>
            <el-form-item label="课时id">
                <el-input v-model="lesson.lessonProgress.lessonId" placeholder="请输入课时id" />
            </el-form-item>
            <el-form-item label="状态">
                <el-input v-model="lesson.lessonProgress.status" placeholder="请输入状态" />
            </el-form-item>
            <el-form-item label="课时进度百分比"> 
                <el-input v-model="lesson.lessonProgress.progressPercentage" placeholder="请输入课时进度百分比" />
            </el-form-item>
            <el-form-item label="最后进度"> 
                <el-input v-model="lesson.lessonProgress.lastPosition" placeholder="请输入最后进度" />
            </el-form-item>
            <el-form-item label="开始时间"> 
                <el-input v-model="lesson.lessonProgress.startedAt" placeholder="请输入开始时间" />
            </el-form-item>
            <el-form-item label="完成时间"> 
                <el-input v-model="lesson.lessonProgress.completedAt" placeholder="请输入完成时间" />
            </el-form-item>
            <el-form-item label="更新时间"> 
                <el-input v-model="lesson.lessonProgress.updatedAt" placeholder="请输入更新时间" />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="save">保存</el-button>
                <el-button @click="dialogAddVisible = false">取消</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>
    <el-dialog v-model:visible="dialogEditVisible" title="修改课时进度">
        <el-form ref="form" :model="lesson" label-width="80px">
            <el-form-item label="模块Id">
                <el-input v-model="lesson.moduleId" placeholder="请输入模块Id" />
            </el-form-item>
            <el-form-item label="课时标题">
                <el-input v-model="lesson.title" placeholder="请输入课时标题" />
            </el-form-item>
            <el-form-item label="内容类型">
                <el-input v-model="lesson.contentType" placeholder="请输入内容类型" />
            </el-form-item>
            <el-form-item label="内容">
                <el-input v-model="lesson.content" placeholder="请输入内容" />
            </el-form-item>
            <el-form-item label="视频地址">
                <el-input v-model="lesson.videoUrl" placeholder="请输入视频地址" />
            </el-form-item>
            <el-form-item label="视频时长(秒)"> 
                <el-input v-model="lesson.videoDuration" placeholder="请输入视频时长(秒)" />
            </el-form-item>
            <el-form-item label="附件地址">
                <el-input v-model="lesson.attachmentUrl" placeholder="请输入附件地址" />
            </el-form-item>
            <el-form-item label="排序">
                <el-input v-model="lesson.sortOrder" placeholder="请输入排序" />
            </el-form-item>
            <el-form-item label="是否可预览">
                <el-switch v-model="lesson.isPreviewAvailable" />
            </el-form-item>
            <el-form-item label="课时进度"> 
                <el-input v-model="lesson.lessonProgress.enrollmentId" placeholder="请输入报名id" />
                <el-input v-model="lesson.lessonProgress.progressPercentage" placeholder="请输入课时进度" />
                <el-input v-model="lesson.lessonProgress.lastPosition" placeholder="请输入最后进度" />
                <el-input v-model="lesson.lessonProgress.status" placeholder="请输入状态" />
                <el-input v-model="lesson.lessonProgress.startedAt" placeholder="请输入开始时间" />
                <el-input v-model="lesson.lessonProgress.completedAt" placeholder="请输入完成进度" />
                <el-input v-model="lesson.lessonProgress.updatedAt" placeholder="请输入更新时间" />
            </el-form-item>
            <el-form-item label="报名id">
                <el-input v-model="lesson.lessonProgress.enrollmentId" placeholder="请输入报名id" />
            </el-form-item>
            <el-form-item label="用户名">
                <el-input v-model="lesson.lessonProgress.username" placeholder="请输入用户名" />
            </el-form-item>
            <el-form-item label="课时id">
                <el-input v-model="lesson.lessonProgress.lessonId" placeholder="请输入课时id" />
            </el-form-item>
            <el-form-item label="状态">
                <el-input v-model="lesson.lessonProgress.status" placeholder="请输入状态" />
            </el-form-item>
            <el-form-item label="课时进度百分比"> 
                <el-input v-model="lesson.lessonProgress.progressPercentage" placeholder="请输入课时进度百分比" />
            </el-form-item>
            <el-form-item label="最后进度"> 
                <el-input v-model="lesson.lessonProgress.lastPosition" placeholder="请输入最后进度" />
            </el-form-item>
            <el-form-item label="开始时间"> 
                <el-input v-model="lesson.lessonProgress.startedAt" placeholder="请输入开始时间" />
            </el-form-item>
            <el-form-item label="完成时间"> 
                <el-input v-model="lesson.lessonProgress.completedAt" placeholder="请输入完成时间" />
            </el-form-item>
            <el-form-item label="更新时间"> 
                <el-input v-model="lesson.lessonProgress.updatedAt" placeholder="请输入更新时间" />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="save">保存</el-button>
                <el-button @click="dialogEditVisible = false">取消</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>
  </div>
</template>

<script setup>
import {onMounted, ref, toRaw} from 'vue';
import Dialog from '@/components/dialog/index.vue';
import { lessonAdd, lessonDelete, lessonPage, lessonUpdate } from '../../api/lesson/lesson';
// 查询参数
const queryParam = ref({
  id: null,
  moduleId: null,
  title: '',
  contentType: '',
  createdAt: null,
  updatedAt: null
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
const lesson = reactive({
  id: null,
  moduleId: null,
  title: null,
  contentType: null,
  content: null,
  videoUrl: null,
  videoDuration: null,
  attachmentUrl: null,
  sortOrder: null,
  isPreviewAvailable: false,
  lessonProgress: lessonProgress.value,
})
const pageQueryParam = reactive({
    total: 0,
  pageNum: 1,
    pageSize: 10,
  lessonsDto: lesson.value
});
// 表格数据（示例）
const tableData = ref([]);
const pageQuery = () => {
    lessonPage(pageQueryParam).then(res => { 
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
}, {deep: true})

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

// 搜索
const onSearch = () => {
    // TODO: 调用 API 查询
  Object.assign(pageQueryParam.lessonsDto, queryParam.value)
  pageQuery();
};

// 新增
const handleAdd = () => {
    // TODO: 跳转或弹窗
    const rawRow = toRaw(lessonProgress)
    Object.assign(lesson,{
        id: null,
        moduleId: null,
        title: null,
        contentType: null,
        content: null,
        videoUrl: null,
        videoDuration: null,
        attachmentUrl: null,
        sortOrder: null,
        isPreviewAvailable: false,
        lessonProgress: rawRow
    })
    dialogAddVisible.value = true
};

// 编辑
const handleEdit = (index, row) => {
    // TODO: 编辑逻辑
    currentEditIndex.value = index;
    const rawRow = toRaw(row);
    Object.assign(lesson, rawRow)
    dialogEditVisible.value = true
};
const save = () => { 
    if (currentEditIndex.value !== null) {
        lessonUpdate(lesson).then(res => { 
            ElMessage.success('修改成功')
            dialogEditVisible.value = false
        })
        tableData.value[currentEditIndex.value] = lesson;
        currentEditIndex.value = null;
        ids.value = [];
    } else {
        lessonAdd(lesson).then(res => { 
            ElMessage.success('添加成功')
            dialogAddVisible.value = false
        })
        tableData.value.push(lesson);
    }
    const rawRow = toRaw(lessonProgress)
    Object.assign(lesson,{
        id: null,
        moduleId: null,
        title: null,
        contentType: null,
        content: null,
        videoUrl: null,
        videoDuration: null,
        attachmentUrl: null,
        sortOrder: null,
        isPreviewAvailable: false,
        lessonProgress: rawRow
    })
}

// 单个删除
const handleDeleteOne = (index, row) => {
  dialogTitle.value = `是否删除课程评价 ${row.id}？`;
    deleteType.value = 'single';
  ids.value.push(row.id)
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
    lessonDelete(ids.value)
    ElMessage.success('删除成功');
  } else if (deleteType.value === 'batch') {
    // 这里可以按 ID 过滤，或根据选中项删除
    const selectedIds = selectedRows.value.map(r => r.id);
      tableData.value = tableData.value.filter(row => !selectedIds.includes(row.id));
    lessonDelete(selectedIds)
    ElMessage.success('批量删除成功');
  }
  // 关闭弹窗
  dialogVisible.value = false;
};
</script>