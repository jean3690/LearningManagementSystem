<template>
  <div>
    <!-- 查询区域 -->
    <div class="mb-4 mt-4">
        <label>进度Id：</label>
        <el-input v-model="pageQueryParam.id" placeholder="请输入进度Id" class="w-64 mr-4" />
        <label>订单号:</label>
        <el-input v-model="pageQueryParam.orderNumber"
                  placeholder="请输入订单号"
                  class="w-64 mr-4"
        />
        <label>用户id:</label>
        <el-input v-model="pageQueryParam.userId"
                  placeholder="请输入用户id"
                  class="w-64 mr-4"
        />
        <label>状态：</label>
        <el-select v-model="pageQueryParam.status" placeholder="请选择状态" class="w-64 mr-4">
          <el-option label="已完成" :value="1" />
          <el-option label="未完成" :value="0" />
        </el-select>
        <label>支付方法:</label>
        <el-select v-model="pageQueryParam.payMethod" placeholder="请选择支付方法" class="w-64 mr-4">
          <el-option label="支付宝" value="alipay" />
          <el-option label="微信" value="wechat" />
        </el-select>
        <label>创建时间:</label>
        <el-date-picker v-model="pageQueryParam.createTime" type="date" placeholder="请选择创建时间" class="w-64 mr-4" />
        <label>更新时间:</label>
        <el-date-picker v-model="pageQueryParam.updateTime" type="date" placeholder="请选择更新时间" class="w-64 mr-4" />
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
import { ref } from 'vue';
import Dialog from '@/components/dialog/index.vue';
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
const pageQueryParam = reactive({
    page: 1,
    pageSize: 10
});

// 表格数据（示例）
const tableData = ref([
  {
        id: 1,
        orderNumber: '20231001',
        userId: 1001,
        totalAmount: 100.0,
        discountAmount: 10.0,
        taxAmount: 0.0,
        finalAmount: 90.0,
        couponId: 1,
        status: 1,
        paymentMethod: 1,
        paymentId: '1234567890',
        billingAddress: '123 Main St, City, State, Country',
        createdAt: '2023-10-01',
        updatedAt: '2023-10-01'
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
  console.log('新增');
  // TODO: 跳转或弹窗
};

// 编辑
const handleEdit = (index, row) => {
  console.log('编辑:', row);
  // TODO: 编辑逻辑
};

// 单个删除
const handleDeleteOne = (index, row) => {
  dialogTitle.value = `是否删除课程评价 ${row.id}？`;
  deleteType.value = 'single';
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