<template>
    <div>
        <!-- 查询区域 -->
        <div class="mb-4 mt-4">
            <label>id: </label>
            <el-input v-model="queryParam.id" placeholder="请输入ID" class="mr-2 mb-1" style="width: 150px;" />
            <label>标题: </label>
            <el-input v-model="queryParam.title" placeholder="请输入标题" class="w-40 mr-2  mb-1" style="width: 200px;" />
            <label>讲师ID: </label>
            <el-input v-model="queryParam.instructorId" placeholder="请输入讲师ID" class="w-40 mr-2  mb-1"
                style="width: 150px;" />
            <label>分类ID: </label>
            <el-input v-model="queryParam.categoryId" placeholder="请输入分类ID" class="w-40 mr-2  mb-1"
                style="width: 150px;" />
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
            <el-input v-model="queryParam.rating" placeholder="请输入评分" class="w-40 mr-2  mb-1" style="width: 150px;" />
            <label>是否免费: </label>
            <el-select v-model="queryParam.isFree" placeholder="请选择" class="w-40 mr-2  mb-1" style="width: 200px;">
                <el-option :label="'是'" :value="true" />
                <el-option :label="'否'" :value="false" />
                <el-option :label="'不限'" :value="null" />
            </el-select>
            <label>发证: </label>
            <el-select v-model="queryParam.willIssueCertificate" placeholder="请选择" class="w-40 mr-2  mb-1"
                style="width: 200px;">
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
            <el-pagination v-model:current-page="pageQueryParam.pageNum" v-model:page-size="pageQueryParam.pageSize"
                background layout="prev, pager, next,jumper,->,total" v-model:total="pageQueryParam.total" />
        </div>
        <!-- 删除确认弹窗 -->
        <Dialog :title="dialogTitle" v-model:visible="dialogVisible" @confirm="confirmDelete" />
        <el-dialog v-model:visible="dialogAddVisible" title="添加"> 
            <el-form ref="form" :model="courses" label-width="80px"> 
                <el-form-item label="标题"> 
                    <el-input v-model="courses.title" placeholder="请输入标题" /> 
                </el-form-item>
                <el-form-item label="副标题"> 
                    <el-input v-model="courses.subtitle" placeholder="请输入副标题" /> 
                </el-form-item>
                <el-form-item label="描述"> 
                    <el-input v-model="courses.description" placeholder="请输入描述" /> 
                </el-form-item>
                <el-form-item label="讲师ID"> 
                    <el-input v-model="courses.instructorId" placeholder="请输入讲师ID" /> 
                </el-form-item>
                <el-form-item label="分类ID"> 
                    <el-input v-model="courses.categoryId" placeholder="请输入分类ID" /> 
                </el-form-item>
                <el-form-item label="难度级别"> 
                    <el-input v-model="courses.level" placeholder="请输入难度级别" /> 
                </el-form-item>
                <el-form-item label="语言"> 
                    <el-input v-model="courses.language" placeholder="请输入语言" /> 
                </el-form-item>
                <el-form-item label="缩略图URL"> 
                    <el-input v-model="courses.thumbnailUrl" placeholder="请输入缩略图URL" /> 
                </el-form-item>
                <el-form-item label="视频预览URL"> 
                    <el-input v-model="courses.videoPreviewUrl" placeholder="请输入视频预览URL" /> 
                </el-form-item>
                <el-form-item label="视频时长(小时)"> 
                    <el-input v-model="courses.durationHours" placeholder="请输入视频时长(小时)" /> 
                </el-form-item>
                <el-form-item label="草稿"> 
                    <el-switch v-model="courses.isDraft" /> 
                </el-form-item>
                <el-form-item label="审核通过"> 
                    <el-switch v-model="courses.isApproved" /> 
                </el-form-item>
                <el-form-item label="发证"> 
                    <el-switch v-model="courses.willIssueCertificate" /> 
                </el-form-item>
                <el-form-item label="价格"> 
                    <el-input v-model="courses.price" placeholder="请输入价格" /> 
                </el-form-item>
                <el-form-item label="折扣价"> 
                    <el-input v-model="courses.discountPrice" placeholder="请输入折扣价" /> 
                </el-form-item>
                <el-form-item label="免费"> 
                    <el-switch v-model="courses.isFree" /> 
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="save">保存</el-button>
                    <el-button @click="cancel">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
        <el-dialog v-model:visible="dialogEditVisible" title="编辑"> 
            <el-form ref="form" :model="courses" label-width="80px"> 
                <el-form-item label="标题"> 
                    <el-input v-model="courses.title" placeholder="请输入标题" /> 
                </el-form-item>
                <el-form-item label="副标题"> 
                    <el-input v-model="courses.subtitle" placeholder="请输入副标题" /> 
                </el-form-item>
                <el-form-item label="描述"> 
                    <el-input v-model="courses.description" placeholder="请输入描述" /> 
                </el-form-item>
                <el-form-item label="讲师ID"> 
                    <el-input v-model="courses.instructorId" placeholder="请输入讲师ID" /> 
                </el-form-item>
                <el-form-item label="分类ID"> 
                    <el-input v-model="courses.categoryId" placeholder="请输入分类ID" /> 
                </el-form-item>
                <el-form-item label="难度级别"> 
                    <el-input v-model="courses.level" placeholder="请输入难度级别" /> 
                </el-form-item>
                <el-form-item label="语言"> 
                    <el-input v-model="courses.language" placeholder="请输入语言" /> 
                </el-form-item>
                <el-form-item label="缩略图URL"> 
                    <el-input v-model="courses.thumbnailUrl" placeholder="请输入缩略图URL" /> 
                </el-form-item>
                <el-form-item label="视频预览URL"> 
                    <el-input v-model="courses.videoPreviewUrl" placeholder="请输入视频预览URL" /> 
                </el-form-item>
                <el-form-item label="视频时长(小时)"> 
                    <el-input v-model="courses.durationHours" placeholder="请输入视频时长(小时)" /> 
                </el-form-item>
                <el-form-item label="草稿"> 
                    <el-switch v-model="courses.isDraft" /> 
                </el-form-item>
                <el-form-item label="审核通过"> 
                    <el-switch v-model="courses.isApproved" /> 
                </el-form-item>
                <el-form-item label="发证"> 
                    <el-switch v-model="courses.willIssueCertificate" /> 
                </el-form-item>
                <el-form-item label="价格"> 
                    <el-input v-model="courses.price" placeholder="请输入价格" /> 
                </el-form-item>
                <el-form-item label="折扣价"> 
                    <el-input v-model="courses.discountPrice" placeholder="请输入折扣价" /> 
                </el-form-item>
                <el-form-item label="免费"> 
                    <el-switch v-model="courses.isFree" /> 
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="save">保存</el-button>
                    <el-button @click="cancel">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script setup>
import { ref, watch, reactive, onMounted } from 'vue';
import Dialog from '@/components/dialog/index.vue';
import { coursesAdd, coursesDelete, coursesPage, coursesUpdate } from '../../api/courses/course';
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
const courses = ref({
    id: null,
    title: '',
    subtitle: null,
    description: null,
    instructorId: null,
    categoryId: null,
    level: null,
    language: null,
    thumbnailUrl: null,
    videoPreviewUrl: null,
    durationHours: null,
    isDraft: null,
    isApproved: null,
    approvalDate: null,
    rating: null,
    totalRatings: null,
    totalEnrollments: null,
    price: null,
    discountPrice: null,
    isFree: null,
    willIssueCertificate: null,
    requirements: null,
    learngingOutcomes: null,
    targetAudience: null,
})
const pageQueryParam = reactive({
    total: 0,
    pageNum: 1,
    pageSize: 10,
    coursesDto: courses.value
});

// 表格数据（示例）
const tableData = ref([]);
const pageQuery = () => { 
    coursesPage(pageQueryParam).then(res => {
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
const ids = ref([]);

// 监听选择变化
const onSelectionChange = (val) => {
    selectedRows.value = val;
};

// 搜索
const onSearch = () => {
    // TODO: 调用 API 查询
    Object.assign(pageQueryParam.coursesDto, pageQueryParam);
    pageQuery();
};

// 重置
const onReset = () => {
    Object.assign(pageQueryParam.coursesDto, {
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
    pageQuery();
};

// 新增
const handleAdd = () => {
    // TODO: 跳转或弹窗
    Object.assign(courses.value, {
        id: null,
        title: '',
        subtitle: null,
        description: null,
        instructorId: null,
        categoryId: null,
        level: null,
        language: null,
        thumbnailUrl: null,
        videoPreviewUrl: null,
        durationHours: null,
        isDraft: null,
        isApproved: null,
    })
    dialogAddVisible.value = true;
};

// 编辑
const handleEdit = (index, row) => {
    // TODO: 编辑逻辑
    currentEditIndex.value = index;
    Object.assign(courses.value, row)
    dialogEditVisible.value = true;
};
const save = () => {
    if (currentEditIndex.value !== null) {
        coursesUpdate(courses.value).then(res => { 
            ElMessage.success('更新成功');
            tableData.value[currentEditIndex.value] = courses.value;
            dialogEditVisible.value = false;
            currentEditIndex.value = null;
        })
    } else {
        coursesAdd(courses.value).then(res => { 
            ElMessage.success('添加成功');
            tableData.value.push(courses.value);
            dialogAddVisible.value = false;
        })
    }
    Object.assign(courses.value, {
        id: null,
        title: '',
        subtitle: null,
        description: null,
        instructorId: null,
        categoryId: null,
        level: null,
        language: null,
        thumbnailUrl: null,
        videoPreviewUrl: null,
        durationHours: null,
        isDraft: null,
        isApproved: null,
    })
}
const cancel = () => { 
    Object.assign(courses.value, {
            id: null,
            title: '',
            subtitle: null,
            description: null,
            instructorId: null,
            categoryId: null,
            level: null,
            language: null,
            thumbnailUrl: null,
            videoPreviewUrl: null,
        })
    if (currentEditIndex.value !== null) { 
        dialogEditVisible.value = false;
    } else { 
        dialogAddVisible.value = false;
    }
}

// 单个删除
const handleDeleteOne = (index, row) => {
    dialogTitle.value = `是否删除课程信息 ${row.id}？`;
    deleteType.value = 'single';
    ids.value.push(row.id);
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
        coursesDelete(ids.value);
        ElMessage.success('删除成功');
    } else if (deleteType.value === 'batch') {
        // 这里可以按 ID 过滤，或根据选中项删除
        const selectedIds = selectedRows.value.map(r => r.id);
        tableData.value = tableData.value.filter(row => !selectedIds.includes(row.id));
        coursesDelete(selectedIds);
        ElMessage.success('批量删除成功');
    }
    // 关闭弹窗
    dialogVisible.value = false;
};
</script>