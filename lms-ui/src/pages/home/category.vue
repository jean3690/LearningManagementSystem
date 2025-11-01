<template>
    <div>
        <!-- 查询区域 -->
        <div class="mb-4 mt-4">
            <label>分类Id: </label>
            <el-input placeholder="请输入分类id" v-model="queryParam.invoiceNumber" style="width: 300px;" class="mr-2" />
            <label>分类名称: </label>
            <el-input placeholder="请输入分类名称" v-model="queryParam.name" style="width: 300px;" class="mr-2" />
            <label>父分类Id: </label>
            <el-input placeholder="请输入父分类id" v-model="queryParam.parentId" style="width: 300px;" />
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
            <el-table-column prop="isActive" align="center" label="是否激活" width="100">
                <template #default="scope">
                    <el-switch v-model="scope.row.isActive" active-color="#13ce66" inactive-color="#ff4949" />
                </template>
            </el-table-column>
            <el-table-column label="操作" align="center">
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
        <Dialog :title="dialogTitle" v-model:visible="dialogVisible" @confirm="confirmDelete" />
        <el-dialog v-model:visible="dialogAddVisible" title="新增分类" @confirm="save" @cancel="cancel">
            <el-form ref="formRef" :model="category" label-width="80px">
                <el-form-item label="分类名称">
                    <el-input v-model="category.name" placeholder="请输入分类名称" />
                </el-form-item>
                <el-form-item label="分类描述">
                    <el-input v-model="category.description" placeholder="请输入分类描述" />
                </el-form-item>
                <el-form-item label="父分类Id">
                    <el-input v-model="category.parentId" placeholder="请输入父分类Id" />
                </el-form-item>
                <el-form-item label="排序">
                    <el-input v-model="category.sortOrder" placeholder="请输入排序" />
                </el-form-item>
                <el-form-item label="是否激活">
                    <el-switch v-model="category.isActive" active-color="#13ce66" inactive-color="#ff4949" />
                </el-form-item>
                <el-form-item label="操作">
                    <el-button type="primary" @click="save">保存</el-button>
                    <el-button @click="cancel">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
        <el-dialog v-model:visible="dialogEditVisible" title="编辑分类" @confirm="save" @cancel="cancel">
            <el-form ref="formRef" :model="category" label-width="80px">
                <el-form-item label="分类名称">
                    <el-input v-model="category.name" placeholder="请输入分类名称" />
                </el-form-item>
                <el-form-item label="分类描述">
                    <el-input v-model="category.description" placeholder="请输入分类描述" />
                </el-form-item>
                <el-form-item label="父分类Id">
                    <el-input v-model="category.parentId" placeholder="请输入父分类Id" />
                </el-form-item>
                <el-form-item label="排序">
                    <el-input v-model="category.sortOrder" placeholder="请输入排序" />
                </el-form-item>
                <el-form-item label="是否激活">
                    <el-switch v-model="category.isActive" active-color="#13ce66" inactive-color="#ff4949" />
                </el-form-item>
                <el-form-item label="操作">
                    <el-button type="primary" @click="save">保存</el-button>
                    <el-button @click="cancel">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script setup>
import { onMounted, ref, toRaw } from 'vue';
import Dialog from '@/components/dialog/index.vue';
import { categoryPage, categoryAdd, categoryUpdate } from '@/api/category/category.js';
// 查询参数
const queryParam = ref({
    id: null,
    name: null,
    parentId: null
});
const category = ref({
    id: null,
    name: null,
    description: null,
    parentId: null,
    sortOrder: null,
    isActive: null,
})
const pageQueryParam = reactive({
    total: 0,
    pageNum: 1,
    pageSize: 10,
    categoriesDto: toRaw(category.value)
});
const pageQuery = () => {
    categoryPage(pageQueryParam).then(res => {
        pageQueryParam.total = res.total
        pageQueryParam.pageNum = res.pageNum
        pageQueryParam.pageSize = res.pageSize
        tableData.value = res.list
    });
}
// 表格数据（示例）
const tableData = ref([]);
onMounted(() => {
    // console.log(import.meta.env.VITE_API_URL)
    pageQuery();
})

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
    Object.assign(pageQueryParam.categoriesDto, {
        id: queryParam.value.id,
        name: queryParam.value.name,
        parentId: queryParam.value.parentId
    })
    pageQuery();
};

// 新增
const handleAdd = () => {
    // TODO: 跳转或弹窗
    Object.assign(category.value, {
        id: null,
        name: null,
        description: null,
        parentId: null,
        sortOrder: null,
        isActive: null,
    })
    dialogAddVisible.value = true;
};

// 编辑
const handleEdit = (index, row) => {
    // TODO: 编辑逻辑
    currentEditIndex.value = index;
    Object.assign(category.value, row)
    dialogEditVisible.value = true;
};
const save = () => {
    if (currentEditIndex.value !== null) {
        categoryUpdate(category.value).then(res => {
            ElMessage.success('更新成功')
            tableData.value[currentEditIndex.value] = category.value
            dialogEditVisible.value = false
            currentEditIndex.value = null
        })
    } else {
        categoryAdd(category.value).then(res => {
            ElMessage.success('添加成功')
            tableData.value.push(category.value)
            dialogAddVisible.value = false
        })
    }
    Object.assign(category.value, {
        id: null,
        name: null,
        description: null,
        parentId: null,
        sortOrder: null,
        isActive: null,
    })
}
const cancel = () => {
    Object.assign(category.value, {
        id: null,
        name: null,
        description: null,
        parentId: null,
        sortOrder: null,
        isActive: null,
    })
    dialogAddVisible.value = false
    dialogEditVisible.value = false
}
// 单个删除
const handleDeleteOne = (index, row) => {
    dialogTitle.value = `是否删除分类 ${row.id}？`;
    ids.value = [row.id];
    deleteType.value = 'single';
    currentIndex.value = index;
    dialogVisible.value = true;
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
        categoryDelete(ids.value).then(res => {
            ElMessage.success('删除成功');
        })
    } else if (deleteType.value === 'batch') {
        // 这里可以按 ID 过滤，或根据选中项删除
        const selectedIds = selectedRows.value.map(r => r.id);
        tableData.value = tableData.value.filter(row => !selectedIds.includes(row.id));
        categoryDelete(selectedIds).then(res => {
            ElMessage.success('批量删除成功');
        })
    }
    ids.value = [];
    selectedRows.value = [];
    currentIndex.value = null;
    // 关闭弹窗
    dialogVisible.value = false;
};
</script>