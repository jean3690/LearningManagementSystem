<template>
    <div>
        <!-- 查询区域 -->
        <div class="mb-4 mt-4">
            <label>标题:</label>
            <el-input placeholder="请输入标题" v-model="queryParam.title" style="width: 100px" class="mr-2" />
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
            <el-table-column prop="id" label="id" align="center" />
            <el-table-column prop="courseId" label="课程id" align="center" />
            <el-table-column prop="title" label="标题" align="center" />
            <el-table-column prop="description" label="描述" align="center" />
            <el-table-column prop="sortOrder" label="排序" align="center" />
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
        <Dialog :title="dialogTitle" v-model:visible="dialogVisible" @confirm="confirmDelete" />
        <el-dialog v-model:visible="dialogAddVisible" title="添加">
            <el-form label-width="80px">
                <el-form-item label="课程id">
                    <el-input v-model="courseModule.courseId" placeholder="请输入课程id" />
                </el-form-item>
                <el-form-item label="标题">
                    <el-input v-model="courseModule.title" placeholder="请输入标题" />
                </el-form-item>
                <el-form-item label="描述">
                    <el-input v-model="courseModule.description" placeholder="请输入描述" />
                </el-form-item>
                <el-form-item label="排序">
                    <el-input v-model="courseModule.sortOrder" placeholder="请输入排序" />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="save">保存</el-button>
                    <el-button @click="cancel">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
        <el-dialog v-model:visible="dialogEditVisible" title="编辑">
            <el-form label-width="80px">
                <el-form-item label="课程id">
                    <el-input v-model="courseModule.courseId" placeholder="请输入课程id" />
                </el-form-item>
                <el-form-item label="标题">
                    <el-input v-model="courseModule.title" placeholder="请输入标题" />
                </el-form-item>
                <el-form-item label="描述">
                    <el-input v-model="courseModule.description" placeholder="请输入描述" />
                </el-form-item>
                <el-form-item label="排序">
                    <el-input v-model="courseModule.sortOrder" placeholder="请输入排序" />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="update">保存</el-button>
                    <el-button @click="cancel">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import Dialog from '@/components/dialog/index.vue';
import { courseModuleAdd, courseModuleDelete, courseModulePage, courseModuleUpdate } from '../../api/coursemodule/courseModule';
// 查询参数
const queryParam = ref({
    title: null,
});
const courseModule = ref({
    id: null,
    courseId: null,
    title: null,
    description: null,
    sortOrder: null,
})
const pageQueryParam = reactive({
    total: 0,
    pageNum: 1,
    pageSize: 10,
    courseModulesDto: courseModule.value
});
// 表格数据（示例）
const tableData = ref([]);
const pageQuery = () => {
    courseModulePage(pageQueryParam).then(res => {
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
const ids = ref([])
// 监听选择变化
const onSelectionChange = (val) => {
    selectedRows.value = val;
};

// 搜索
const onSearch = () => {
    // TODO: 调用 API 查询
    Object.assign(pageQueryParam.courseModulesDto, queryParam.value);
    pageQuery();
};
const cancel = () => {
    if (currentEditIndex.value !== null) {
        Object.assign(courseModule.value, {
            id: null,
            courseId: null,
            title: null,
            description: null,
            sortOrder: null,
        })
        dialogEditVisible = false
    } else {
        Object.assign(courseModule.value, {
            id: null,
            courseId: null,
            title: null,
            description: null,
            sortOrder: null,
        })
        dialogAddVisible = false
    }
}

// 新增
const handleAdd = () => {
    // TODO: 跳转或弹窗
    Object.assign(courseModule.value, {
        id: null,
        courseId: null,
        title: null,
        description: null,
        sortOrder: null,
    })
    dialogAddVisible.value = true;
};

// 编辑
const handleEdit = (index, row) => {
    // TODO: 编辑逻辑
    currentEditIndex.value = index;
    Object.assign(courseModule.value, row);
    dialogEditVisible.value = true;
};
const save = () => {
    if (currentEditIndex.value !== null) {
        courseModuleUpdate(courseModule.value).then(res => {
            ElMessage.success('更新成功');
            tableData.value[currentEditIndex.value] = courseModule.value;
            currentEditIndex.value = null;
            dialogEditVisible.value = false;
        })
    } else {
        courseModuleAdd(courseModule.value).then(res => {
            ElMessage.success('添加成功');
            tableData.value.push(courseModule.value);
            dialogAddVisible.value = false;
        })
    }
    Object.assign(courseModule.value, {
        id: null,
        courseId: null,
        title: null,
        description: null,
        sortOrder: null,
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
        courseModuleDelete(ids.value)
        ElMessage.success('删除成功');
    } else if (deleteType.value === 'batch') {
        // 这里可以按 ID 过滤，或根据选中项删除
        const selectedIds = selectedRows.value.map(r => r.id);
        tableData.value = tableData.value.filter(row => !selectedIds.includes(row.id));
        courseModuleDelete(selectedIds)
        ElMessage.success('批量删除成功');
    }
    // 关闭弹窗
    dialogVisible.value = false;
};
</script>