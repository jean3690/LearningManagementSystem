<template>
    <div>
        <!-- 查询区域 -->
        <div class="mb-4 mt-4">
            <label>进度Id：</label>
            <el-input v-model="pageQueryParam.id" style="width: 300px;" placeholder="请输入进度Id" class="w-64 mr-4" />
            <label>订单号:</label>
            <el-input v-model="pageQueryParam.orderNumber" style="width: 300px;" placeholder="请输入订单号"
                class="w-64 mr-4" />
            <label>用户id:</label>
            <el-input v-model="pageQueryParam.userId" style="width: 100px;" placeholder="请输入用户id" class="w-64 mr-4" />
            <label>状态：</label>
            <el-select v-model="pageQueryParam.status" style="width: 100px;" placeholder="请选择状态" class="w-64 mr-4">
                <el-option label="已完成" :value="1" />
                <el-option label="未完成" :value="0" />
            </el-select>
            <label>支付方法:</label>
            <el-select v-model="pageQueryParam.payMethod" style="width: 300px;" placeholder="请选择支付方法" class="w-64 mr-4">
                <el-option label="支付宝" value="alipay" />
                <el-option label="微信" value="wechat" />
            </el-select>
            <label>创建时间:</label>
            <el-date-picker v-model="pageQueryParam.createTime" style="width: 300px;" type="date" placeholder="请选择创建时间"
                class="w-64 mr-4" />
            <label>更新时间:</label>
            <el-date-picker v-model="pageQueryParam.updateTime" style="width: 300px;" type="date" placeholder="请选择更新时间"
                class="w-64 mr-4" />
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
            <el-table-column prop="id" label="订单Id" align="center" width="120" />
            <el-table-column prop="orderNumber" label="订单号" align="center" width="120" />
            <el-table-column prop="userId" label="用户Id" align="center" width="120" />
            <el-table-column prop="totalAmount" label="订单总金额" align="center" width="120" />
            <el-table-column prop="discountAmount" label="折扣金额" align="center" width="120" />
            <el-table-column prop="taxAmount" label="税额" align="center" width="120" />
            <el-table-column prop="finalAmount" label="最终金额" align="center" width="120" />
            <el-table-column prop="couponId" label="优惠券Id" align="center" width="120" />
            <el-table-column prop="status" label="状态" align="center" width="120">
                <template #default="scope">
                    <span v-if="scope.row.status === 1">已完成</span>
                    <span v-else>未完成</span>
                </template>
            </el-table-column>
            <el-table-column prop="paymentMethod" label="支付方法" align="center" width="120">
                <template #default="scope">
                    <span v-if="scope.row.paymentMethod === 1">支付宝</span>
                    <span v-else>微信</span>
                </template>
            </el-table-column>
            <el-table-column prop="paymentId" label="支付Id" align="center" width="120" />
            <el-table-column prop="billingAddress" label="账单地址" align="center" width="120" />
            <el-table-column prop="createdAt" label="创建时间" align="center" width="120" />
            <el-table-column prop="updatedAt" label="更新时间" align="center" width="120" />
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
        <Dialog :title="dialogTitle" v-model:visible="dialogVisible" @confirm="confirmDelete" />
        <!-- 增加 -->
        <el-dialog v-model="dialogAddVisible" width="30%">
            <el-form ref="formRef" :model="orderDetail" label-width="80px">
                <el-form-item label="订单号">
                    <el-input v-model="orderDetail.orderNumber" placeholder="请输入订单" />
                </el-form-item>
                <el-form-item label="用户Id">
                    <el-input v-model="orderDetail.userId" placeholder="请输入用户Id" />
                </el-form-item>
                <el-form-item label="订单总金额">
                    <el-input v-model="orderDetail.totalAmount" placeholder="请输入订单总金额" />
                </el-form-item>
                <el-form-item label="折扣金额">
                    <el-input v-model="orderDetail.discountAmount" placeholder="请输入折扣金额" />
                </el-form-item>
                <el-form-item label="税额">
                    <el-input v-model="orderDetail.taxAmount" placeholder="请输入税额" />
                </el-form-item>
                <el-form-item label="最终金额">
                    <el-input v-model="orderDetail.finalAmount" placeholder="请输入最终金额" />
                </el-form-item>
                <el-form-item label="优惠券Id">
                    <el-input v-model="orderDetail.couponId" placeholder="请输入优惠券Id" />
                </el-form-item>
                <el-form-item label="状态">
                    <el-select v-model="orderDetail.status" placeholder="请选择状态">
                        <el-option label="已完成" value="1" />
                        <el-option label="未完成" value="0" />
                    </el-select>
                </el-form-item>
                <el-form-item label="支付方法">
                    <el-select v-model="orderDetail.paymentMethod" placeholder="请选择支付方法">
                        <el-option label="支付宝" value="1" />
                        <el-option label="微信" value="0" />
                    </el-select>
                </el-form-item>
                <el-form-item label="支付Id">
                    <el-input v-model="orderDetail.paymentId" placeholder="请输入支付Id" />
                </el-form-item>
                <el-form-item label="账单地址">
                    <el-input v-model="orderDetail.billingAddress" placeholder="请输入账单地址" />
                </el-form-item>
                <el-form-item label="创建时间">
                    <el-input v-model="orderDetail.createdAt" placeholder="请输入创建时间" />
                </el-form-item>
                <el-form-item label="更新时间">
                    <el-input v-model="orderDetail.updatedAt" placeholder="请输入更新时间" />
                </el-form-item>
                <el-form-item label="订单项">
                    <el-button type="primary" @click="addOrderItem">添加订单项</el-button>
                    <div v-for="(orderItem, index) in orderDetail.orderItemList" :key="index">
                        <el-form-item label="订单Id">
                            <el-input v-model="orderItem.orderId" placeholder="请输入订单Id" />
                        </el-form-item>
                        <el-form-item label="课程Id">
                            <el-input v-model="orderItem.courseId" placeholder="请输入商品Id" />
                        </el-form-item>
                        <el-form-item label="课程标题">
                            <el-input v-model="orderItem.courseTitle" placeholder="请输入课程标题" />
                        </el-form-item>
                        <el-form-item label="单价">
                            <el-input v-model="orderItem.unitPrice" placeholder="请输入单价" />
                        </el-form-item>
                        <el-form-item label="折扣金额">
                            <el-input v-model="orderItem.discountAmount" placeholder="请输入折扣金额" />
                        </el-form-item>
                        <el-form-item label="最终价格">
                            <el-input v-model="orderItem.finalAmount" placeholder="请输入最终价格" />
                        </el-form-item>
                        <el-form-item label="创建时间">
                            <el-date-picker v-model="orderItem.createdAt" placeholder="请选择创建时间" />
                        </el-form-item>
                    </div>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="save">提交</el-button>
                    <el-button @click="handleReset">重置</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
        <el-dialog v-model="dialogEditVisible" width="30%">
            <el-form ref="formRef" :model="orderDetail" label-width="80px"> 
                <el-form-item label="订单号">
                    <el-input v-model="orderDetail.orderNumber" placeholder="请输入订单" />
                </el-form-item>
                <el-form-item label="用户Id">
                    <el-input v-model="orderDetail.userId" placeholder="请输入用户Id" />
                </el-form-item>
                <el-form-item label="订单总金额">
                    <el-input v-model="orderDetail.totalAmount" placeholder="请输入订单总金额" />
                </el-form-item>
                <el-form-item label="折扣金额">
                    <el-input v-model="orderDetail.discountAmount" placeholder="请输入折扣金额" />
                </el-form-item>
                <el-form-item label="税额">
                    <el-input v-model="orderDetail.taxAmount" placeholder="请输入税额" />
                </el-form-item>
                <el-form-item label="最终金额">
                    <el-input v-model="orderDetail.finalAmount" placeholder="请输入最终金额" />
                </el-form-item>
                <el-form-item label="优惠券Id">
                    <el-input v-model="orderDetail.couponId" placeholder="请输入优惠券Id" />
                </el-form-item>
                <el-form-item label="状态">
                    <el-select v-model="orderDetail.status" placeholder="请选择状态">
                        <el-option label="已完成" value="1" />
                        <el-option label="未完成" value="0" />
                    </el-select>
                </el-form-item>
                <el-form-item label="支付方法">
                    <el-select v-model="orderDetail.paymentMethod" placeholder="请选择支付方法">
                        <el-option label="支付宝" value="1" />
                        <el-option label="微信" value="0" />
                    </el-select>
                </el-form-item>
                <el-form-item label="支付Id">
                    <el-input v-model="orderDetail.paymentId" placeholder="请输入支付Id" />
                </el-form-item>
                <el-form-item label="账单地址">
                    <el-input v-model="orderDetail.billingAddress" placeholder="请输入账单地址" />
                </el-form-item>
                <el-form-item label="创建时间">
                    <el-date-picker v-model="orderDetail.createdAt" placeholder="请选择创建时间" />
                </el-form-item>
                <el-form-item label="更新时间">
                    <el-date-picker v-model="orderDetail.updatedAt" placeholder="请选择更新时间" />
                </el-form-item>
                <el-form-item label="订单项"> 
                    <el-button type="primary" @click="addOrderItem">添加订单项</el-button>
                    <div v-for="(orderItem, index) in orderDetail.orderItemList" :key="index"> 
                        <el-form-item label="订单Id">
                            <el-input v-model="orderItem.orderId" placeholder="请输入订单Id" />
                        </el-form-item>
                        <el-form-item label="课程Id">
                            <el-input v-model="orderItem.courseId" placeholder="请输入商品Id" />
                        </el-form-item>
                        <el-form-item label="课程标题">
                            <el-input v-model="orderItem.courseTitle" placeholder="请输入课程标题" />
                        </el-form-item>
                        <el-form-item label="单价">
                            <el-input v-model="orderItem.unitPrice" placeholder="请输入单价" />
                        </el-form-item>
                        <el-form-item label="折扣金额">
                            <el-input v-model="orderItem.discountAmount" placeholder="请输入折扣金额" />
                        </el-form-item>
                        <el-form-item label="最终价格">
                            <el-input v-model="orderItem.finalAmount" placeholder="请输入最终价格" />
                        </el-form-item>
                        <el-form-item label="创建时间">
                            <el-date-picker v-model="orderItem.createdAt" placeholder="请选择创建时间" />
                        </el-form-item>
                    </div>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="save">提交</el-button>
                    <el-button @click="handleReset">重置</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script setup>
import { onMounted, reactive, ref, toRaw } from 'vue';
import Dialog from '@/components/dialog/index.vue';
import { ordersPage, ordersDelete, ordersUpdate, ordersAdd } from '@/api/orders/orders.js';
// 查询参数
const queryParam = ref({
    id: null,
    orderNumber: null,
    userId: null,
    status: null,
    paymentMethod: null,
    createdAt: null,
    updatedAt: null
});
const order = ref({
    id: null,
    orderNumber: null,
    userId: null,
    totalAmount: null,
    discountAmount: null,
    taxAmount: null,
    finalAmount: null,
    couponId: null,
    status: null,
    paymentMethod: null,
    paymentId: null,
    billingAddress: null,
    createdAt: null,
    updatedAt: null,
})
const orderItem = ref({
    id: null,
    orderId: null,
    courseId: null,
    courseTitle: null,
    unitPrice: null,
    discountAmount: null,
    finalAmount: null,
    createdAt: null,
})
const orderItemList = ref([])
const orderDetail = reactive({
    id: null,
    orderNumber: null,
    userId: null,
    totalAmount: null,
    discountAmount: null,
    taxAmount: null,
    finalAmount: null,
    couponId: null,
    status: null,
    paymentMethod: null,
    paymentId: null,
    billingAddress: null,
    createdAt: null,
    updatedAt: null,
    orderItemsList: orderItemList.value
})
const pageQueryParam = reactive({
    total: 0,
    pageNum: 1,
    pageSize: 10,
    ordersDto: order.value
});
// 表格数据（示例）
const tableData = ref([]);
const pageQuery = () => { 
    ordersPage(pageQueryParam).then(res => {
        pageQueryParam.total = res.total;
        pageQueryParam.pageNum = res.pageNum;
        pageQueryParam.pageSize = res.pageSize;
        tableData.value = res.list;
    })
}
onMounted(() => {
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
    Object.assign(pageQueryParam.ordersDto, { ...queryParam.value });
    pageQuery();
};

const addOrderItem = () => {
    // 推入一个全新的对象，避免引用同一个 orderItem
    orderItemList.value.push({
        id: null,
        orderId: null,
        courseId: null,
        courseTitle: null,
        unitPrice: null,
        discountAmount: null,
        finalAmount: null,
        createdAt: null,
    });
}
// 打开新增弹窗
const handleAdd = () => {
    // 创建一个全新的空订单对象作为临时数据
    Object.assign(orderDetail, {
        id: null,
        orderNumber: null,
        userId: null,
        totalAmount: null,
        discountAmount: null,
        taxAmount: null,
        finalAmount: null,
        couponId: null,
        status: null,
        paymentMethod: null,
        paymentId: null,
        billingAddress: null,
        createdAt: null,
        updatedAt: null,
        orderItemsList: []
    })
    orderItemList.value = [] // 清空订单项列表
    dialogAddVisible.value = true;
};

// 打开编辑弹窗
const handleEdit = (index, row) => {
    currentEditIndex.value = index;
    // 使用 toRaw 确保拿到原始数据，避免响应式污染
    const rawRow = toRaw(row);
    Object.assign(orderDetail, rawRow);
    // 同步 orderItemsList 到 orderItemList
    orderDetail.orderItemsList = rawRow.orderItemsList ? rawRow.orderItemsList.map(item => ({...item})) : [];
    dialogEditVisible.value = true;
};

// 重置按钮
const handleReset = () => {
    if (orderDetail.id) {
        // 如果是编辑模式，重置为原始数据（这里需要缓存原始数据，或从 tableData 重新获取）
        // 更推荐：在 handleEdit 时缓存原始数据
        const originalData = toRaw(tableData.value[currentEditIndex.value]);
        Object.assign(orderDetail, originalData);
        orderDetail.orderItemsList = originalData.orderItemsList ? originalData.orderItemsList.map(item => ({...item})) : [];
    } else {
        // 新增模式，重置为空
        handleAdd(); // 复用新增的逻辑
    }
};
const save = () => {
    // ✅ 关键：同步 orderItemList 到 orderDetail.orderItemsList
    orderDetail.orderItemsList = toRaw(orderItemList.value);

    if (orderDetail.id != null) {
        ordersUpdate(orderDetail)
            .then(res => {
                ElMessage.success('修改成功');
                // 更新表格中的数据
                Object.assign(tableData.value[currentEditIndex.value], toRaw(orderDetail));
                currentEditIndex.value = null;
                dialogEditVisible.value = false;
            })
    } else {
        ordersAdd(orderDetail)
            .then(res => {
                ElMessage.success('添加成功');
                tableData.value.push(toRaw(orderDetail));
                dialogAddVisible.value = false;
            })
    }
    // 无论成功与否，清空临时数据（可选)
    orderDetail.id = null;
    orderDetail.orderNumber = null;
    orderDetail.userId = null;
    orderDetail.totalAmount = null;
    orderDetail.discountAmount = null;
    orderDetail.taxAmount = null;
    orderDetail.finalAmount = null;
    orderDetail.couponId = null;
    orderDetail.status = null;
    orderDetail.paymentMethod = null;
    orderDetail.paymentId = null;
    orderDetail.billingAddress = null;
    orderDetail.createdAt = null;
    orderDetail.updatedAt = null;
    orderItemList.value = []
    orderDetail.orderItemsList = []
    orderItem.value = { ...orderItem }
    // handleReset() 或 手动清空
};

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
        ordersDelete(ids.value.toString)
        ElMessage.success('删除成功');
    } else if (deleteType.value === 'batch') {
        // 这里可以按 ID 过滤，或根据选中项删除
        const selectedIds = selectedRows.value.map(r => r.id);
        tableData.value = tableData.value.filter(row => !selectedIds.includes(row.id));
        ordersDelete(selectedIds.join(','))
        ElMessage.success('批量删除成功');
    }
    // 关闭弹窗
    dialogVisible.value = false;
};
</script>