<template>
    <div>
        <!-- 查询区域 -->
        <div class="mb-4 mt-4">
            <label>发票号码: </label>
            <el-input placeholder="请输入发票号码" v-model="queryParam.invoiceNumber" style="width: 300px;" class="mr-2" />
            <label>开票日期: </label>
            <el-date-picker v-model="queryParam.issueDate" type="date" placeholder="开票日期" value-format="YYYY-MM-DD"
                class="mr-2" />
            <label>到期日期: </label>
            <el-date-picker v-model="queryParam.dueDate" type="date" placeholder="到期日期" value-format="YYYY-MM-DD"
                class="mr-2" />
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
            <el-table-column prop="id" label="ID" />
            <el-table-column prop="invoiceNumber" label="发票号码" />
            <el-table-column prop="orderId" label="订单ID" />
            <el-table-column prop="userId" label="用户ID" />
            <el-table-column prop="issueDate" label="开票日期" />
            <el-table-column prop="dueDate" label="到期日期" />
            <el-table-column prop="amount" label="发票金额" />
            <el-table-column prop="taxAmount" label="税费金额" />
            <el-table-column prop="totalAmount" label="总金额" />
            <el-table-column prop="status" label="发票状态" />
            <el-table-column prop="paymentDate" label="支付日期" />
            <el-table-column label="操作">
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
        <Dialog :title="dialogTitle" v-model:visible="dialogVisible" @confirm="confirmDelete" />
    </div>
</template>

<script setup>
import { ref, reactive,watch } from 'vue';
import Dialog from '@/components/dialog/index.vue';
// 查询参数
const queryParam = ref({
    invoiceNumber: null,
    issueDate: null,
    dueDate: null,
});
const pageQueryParam = reactive({
    page: 1,
    pageSize: 10
});

// 表格数据（示例）
const tableData = ref([
    {
        id: 1,
        invoiceNumber: 'INV20250001',
        orderId: 1001,
        userId: 2001,
        issueDate: '2025-01-15',
        dueDate: '2025-02-15',
        amount: 1000,
        taxAmount: 130,
        totalAmount: 1130,
        status: '已开票',
        paymentDate: '2025-01-20'
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
    dialogTitle.value = `是否删除发票 ${row.invoiceNumber}？`;
    deleteType.value = 'single';
    currentIndex.value = index;
    dialogVisible.value = true;
    console.log('删除:', dialogVisible.value)
};

// 批量删除
const handleDeleteBatch = () => {
    if (selectedRows.value.length === 0) {
        ElMessage.warning('请先选择要删除的发票');
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