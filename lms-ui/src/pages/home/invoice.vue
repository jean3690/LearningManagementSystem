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
            <el-pagination v-model:current-page="pageQueryParam.pageNum" v-model:page-size="pageQueryParam.pageSize"
                background layout="prev, pager, next,jumper,->,total" v-model:total="pageQueryParam.total" />
        </div>
        <!-- 删除确认弹窗 -->
        <Dialog :title="dialogTitle" v-model:visible="dialogVisible" @confirm="confirmDelete" />
        <el-dialog v-model:visible="dialogAddVisible" title="新增发票" @confirm="save">
            <el-form ref="form" :model="invoice" label-width="80px">
                <el-form-item label="发票号码">
                    <el-input v-model="invoice.invoiceNumber" placeholder="请输入发票号码" />
                </el-form-item>
                <el-form-item label="订单ID">
                    <el-input v-model="invoice.orderId" placeholder="请输入订单ID" />
                </el-form-item>
                <el-form-item label="用户ID">
                    <el-input v-model="invoice.userId" placeholder="请输入用户ID" />
                </el-form-item>
                <el-form-item label="开票日期">
                    <el-date-picker v-model="invoice.issueDate" type="date" placeholder="开票日期"
                        value-format="YYYY-MM-DD" />
                </el-form-item>
                <el-form-item label="到期日期">
                    <el-date-picker v-model="invoice.dueDate" type="date" placeholder="到期日期"
                        value-format="YYYY-MM-DD" />
                </el-form-item>
                <el-form-item label="发票金额">
                    <el-input v-model="invoice.amount" placeholder="请输入发票金额" />
                </el-form-item>
                <el-form-item label="税费金额">
                    <el-input v-model="invoice.taxAmount" placeholder="请输入税费金额" />
                </el-form-item>
                <el-form-item label="总金额">
                    <el-input v-model="invoice.totalAmount" placeholder="请输入总金额" />
                </el-form-item>
                <el-form-item label="发票状态">
                    <el-input v-model="invoice.status" placeholder="请输入发票状态" />
                </el-form-item>
                <el-form-item label="支付日期">
                    <el-date-picker v-model="invoice.paymentDate" type="date" placeholder="支付日期"
                        value-format="YYYY-MM-DD" />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="save">保存</el-button>
                    <el-button @click="dialogAddVisible = false">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
        <el-dialog v-model:visible="dialogEditVisible" title="编辑发票" @confirm="save">
            <el-form ref="form" :model="invoice" label-width="80px">
                <el-form-item label="发票号码">
                    <el-input v-model="invoice.invoiceNumber" placeholder="请输入发票号码" />
                </el-form-item>
                <el-form-item label="订单ID">
                    <el-input v-model="invoice.orderId" placeholder="请输入订单ID" />
                </el-form-item>
                <el-form-item label="用户ID">
                    <el-input v-model="invoice.userId" placeholder="请输入用户ID" />
                </el-form-item>
                <el-form-item label="开票日期">
                    <el-date-picker v-model="invoice.issueDate" type="date" placeholder="开票日期"
                        value-format="YYYY-MM-DD" />
                </el-form-item>
                <el-form-item label="到期日期">
                    <el-date-picker v-model="invoice.dueDate" type="date" placeholder="到期日期"
                        value-format="YYYY-MM-DD" />
                </el-form-item>
                <el-form-item label="发票金额">
                    <el-input v-model="invoice.amount" placeholder="请输入发票金额" />
                </el-form-item>
                <el-form-item label="税费金额">
                    <el-input v-model="invoice.taxAmount" placeholder="请输入税费金额" />
                </el-form-item>
                <el-form-item label="总金额">
                    <el-input v-model="invoice.totalAmount" placeholder="请输入总金额" />
                </el-form-item>
                <el-form-item label="发票状态">
                    <el-input v-model="invoice.status" placeholder="请输入发票状态" />
                </el-form-item>
                <el-form-item label="支付日期">
                    <el-date-picker v-model="invoice.paymentDate" type="date" placeholder="支付日期"
                        value-format="YYYY-MM-DD" />
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
import { ref, reactive, watch, onMounted } from 'vue';
import Dialog from '@/components/dialog/index.vue';
import { invoicesAdd, invoicesPage, invoicesUpdate } from '../../api/invoices/invoices';
// 查询参数
const queryParam = ref({
    invoiceNumber: null,
    issueDate: null,
    dueDate: null,
});
const invoice = ref({
    id: null,
    invoiceNumber: null,
    orderId: null,
    userId: null,
    issueDate: null,
    dueDate: null,
    amount: null,
    taxAmount: null,
    totalAmount: null,
    status: null,
    paymentDate: null,
})
const pageQueryParam = reactive({
    total: 0,
    pageNum: 1,
    pageSize: 10,
    invoicesDto: invoice.value
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
const pageQuery = () => {
    invoicesPage(pageQueryParam).then(res => {
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
    Object.assign(pageQueryParam, {
        invoicesDto: {
            invoiceNumber: queryParam.value.invoiceNumber,
            issueDate: queryParam.value.issueDate,
            dueDate: queryParam.value.dueDate,
        }
    });
    pageQuery();
};

// 新增
const handleAdd = () => {
    // TODO: 跳转或弹窗
    Object.assign(invoice.value, {
        id: null,
        invoiceNumber: null,
        orderId: null,
        userId: null,
        issueDate: null,
        dueDate: null,
        amount: null,
        taxAmount: null,
        totalAmount: null,
        status: null,
        paymentDate: null,
    });
    dialogAddVisible.value = true;
};

// 编辑
const handleEdit = (index, row) => {
    // TODO: 编辑逻辑
    currentEditIndex.value = index;
    Object.assign(invoice.value, row);
    dialogEditVisible.value = true;
};
const save = () => {
    if (currentEditIndex.value !== null) {
        invoicesUpdate(invoice.value).then(res => {
            ElMessage.success('更新成功');
            dialogEditVisible.value = false;
            tableData.value[currentEditIndex.value] = invoice.value;
            currentEditIndex.value = null;
        })
    } else {
        invoicesAdd(invoice.value).then(res => {
            ElMessage.success('添加成功');
            dialogAddVisible.value = false;
            tableData.value.push(invoice.value);
        })
    }
    Object.assign(invoice.value, {
        id: null,
        invoiceNumber: null,
        orderId: null,
        userId: null,
        issueDate: null,
        dueDate: null,
        amount: null,
        taxAmount: null,
        totalAmount: null,
        status: null,
        paymentDate: null,
    });
}

// 单个删除
const handleDeleteOne = (index, row) => {
    dialogTitle.value = `是否删除发票 ${row.invoiceNumber}？`;
    deleteType.value = 'single';
    ids.value.push(row.id);
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