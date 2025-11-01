<template>
    <div>
        <!-- 查询区域 -->
        <div class="mb-4 mt-4">
            <label>优惠卷Id: </label>
            <el-input placeholder="请输入优惠卷id" v-model="queryParam.id" style="width: 300px;" class="mr-2" />
            <label>优惠卷code: </label>
            <el-input placeholder="请输入code" v-model="queryParam.code" style="width: 300px;" class="mr-2" />
            <label>有效期开始: </label>
            <el-date-picker v-model="queryParam.validFrom" type="date" placeholder="有效期开始" value-format="YYYY-MM-DD"
                class="mr-2" />
            <label>有效期结束: </label>
            <el-date-picker v-model="queryParam.validUtil" type="date" placeholder="有效期结束" value-format="YYYY-MM-DD" />
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
            <el-table-column prop="id" label="优惠卷Id" align="center" width="120" />
            <el-table-column prop="code" label="优惠卷code" align="center" width="180" />
            <el-table-column prop="description" align="center" label="优惠卷描述" width="100" />
            <el-table-column prop="discountType" align="center" label="优惠类型" width="100" />
            <el-table-column prop="discountValue" align="center" label="优惠值" width="100" />
            <el-table-column prop="maxDiscountAmount" align="center" label="最大优惠金额" width="120" />
            <el-table-column prop="minPurchaseAmount" align="center" label="最低消费金额" width="120" />
            <el-table-column prop="usageLimit" align="center" label="使用限制(每人)" width="120" />
            <el-table-column prop="usedCount" align="center" label="已使用次数" width="100" />
            <el-table-column prop="validFrom" align="center" label="有效期开始" width="120" />
            <el-table-column prop="validUtil" align="center" label="有效期结束" width="120" />
            <el-table-column prop="isActive" align="center" label="是否激活" width="100">
                <template #default="scope">
                    <el-switch v-model="scope.row.isActive" active-color="#13ce66" inactive-color="#ff4949" />
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
        <Dialog :title="dialogTitle" v-model:visible="dialogVisible" @confirm="confirmDelete" />
        <el-dialog v-model:visible="dialogAddVisible">
            <el-form ref="form" :model="coupon" label-width="120px">
                <el-form-item label="优惠卷Id">
                    <el-input v-model="coupon.id" />
                </el-form-item>
                <el-form-item label="优惠卷code">
                    <el-input v-model="coupon.code" />
                </el-form-item>
                <el-form-item label="优惠卷描述"> 
                    <el-input v-model="coupon.description" />
                </el-form-item>
                <el-form-item label="优惠类型"> 
                    <el-select v-model="coupon.discountType" placeholder="请选择">
                        <el-option label="满减" value="满减"></el-option>
                        <el-option label="折扣" value="折扣"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="优惠值"> 
                    <el-input v-model="coupon.discountValue" />
                </el-form-item>
                <el-form-item label="最大优惠金额"> 
                    <el-input v-model="coupon.maxDiscountAmount" />
                </el-form-item>
                <el-form-item label="最低消费金额"> 
                    <el-input v-model="coupon.minPurchaseAmount" />
                </el-form-item>
                <el-form-item label="使用限制(每人)"> 
                    <el-input v-model="coupon.usageLimit" />
                </el-form-item>
                <el-form-item label="已使用次数"> 
                    <el-input v-model="coupon.usedCount" />
                </el-form-item>
                <el-form-item label="有效期开始"> 
                    <el-date-picker v-model="coupon.validFrom" type="date" placeholder="有效期开始" value-format="YYYY-MM-DD" />
                </el-form-item>
                <el-form-item label="有效期结束"> 
                    <el-date-picker v-model="coupon.validUtil" type="date" placeholder="有效期结束" value-format="YYYY-MM-DD" />
                </el-form-item>
                <el-form-item label="是否激活"> 
                    <el-switch v-model="coupon.isActive" active-color="#13ce66" inactive-color="#ff4949" />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="save">提交</el-button>
                    <el-button @click="cancel">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog><el-dialog v-model:visible="dialogEditVisible">
            <el-form ref="form" :model="coupon" label-width="120px">
                <el-form-item label="优惠卷Id">
                    <el-input v-model="coupon.id" />
                </el-form-item>
                <el-form-item label="优惠卷code">
                    <el-input v-model="coupon.code" />
                </el-form-item>
                <el-form-item label="优惠卷描述"> 
                    <el-input v-model="coupon.description" />
                </el-form-item>
                <el-form-item label="优惠类型"> 
                    <el-select v-model="coupon.discountType" placeholder="请选择">
                        <el-option label="满减" value="满减"></el-option>
                        <el-option label="折扣" value="折扣"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="优惠值"> 
                    <el-input v-model="coupon.discountValue" />
                </el-form-item>
                <el-form-item label="最大优惠金额"> 
                    <el-input v-model="coupon.maxDiscountAmount" />
                </el-form-item>
                <el-form-item label="最低消费金额"> 
                    <el-input v-model="coupon.minPurchaseAmount" />
                </el-form-item>
                <el-form-item label="使用限制(每人)"> 
                    <el-input v-model="coupon.usageLimit" />
                </el-form-item>
                <el-form-item label="已使用次数"> 
                    <el-input v-model="coupon.usedCount" />
                </el-form-item>
                <el-form-item label="有效期开始"> 
                    <el-date-picker v-model="coupon.validFrom" type="date" placeholder="有效期开始" value-format="YYYY-MM-DD" />
                </el-form-item>
                <el-form-item label="有效期结束"> 
                    <el-date-picker v-model="coupon.validUtil" type="date" placeholder="有效期结束" value-format="YYYY-MM-DD" />
                </el-form-item>
                <el-form-item label="是否激活"> 
                    <el-switch v-model="coupon.isActive" active-color="#13ce66" inactive-color="#ff4949" />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="save">提交</el-button>
                    <el-button @click="cancel">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import Dialog from '@/components/dialog/index.vue';
import { couponsPage,couponsDelete,couponsAdd,couponsUpdate } from '@/api/coupons/coupons.js';
// 查询参数
const queryParam = ref({
    id: null,
    code: null,
    validFrom: null,
    validUtil: null,
});
const coupon = ref({
    id: null,
    code: null,
    description: null,
    discountType: null,
    discountValue: null,
    maxDiscountAmount: null,
    minPurchaseAmount: null,
    usageLimit: null,
    usedCount: null,
    validFrom: null,
    validUtil: null,
    isActive: null,
})
const pageQueryParam = reactive({
    total: 0,
    pageNum: 1,
    pageSize: 10,
    couponsDto: coupon.value
});
// 表格数据（示例）
const tableData = ref([]);
const pageQuery = () => { 
    couponsPage(pageQueryParam).then(res => {
        pageQueryParam.total = res.total
        pageQueryParam.pageNum = res.pageNum
        pageQueryParam.pageSize = res.pageSize
        tableData.value = res.list
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
    Object.assign(pageQueryParam.couponsDto, {
        id: queryParam.id,
        code: queryParam.code,
        validFrom: queryParam.validFrom,
        validUtil: queryParam.validUtil,
    });
    pageQuery();
};

// 新增
const handleAdd = () => {
    // TODO: 跳转或弹窗
    Object.assign(coupon.value, {
        id: null,
        code: null,
        description: null,
        discountType: null,
        discountValue: null,
        maxDiscountAmount: null,
        minPurchaseAmount: null,
        usageLimit: null,
        usedCount: null,
        validFrom: null,
        validUtil: null,
        isActive: null,
    });
    dialogAddVisible.value = true;
};

// 编辑
const handleEdit = (index, row) => {
    // TODO: 编辑逻辑
    currentEditIndex.value = index;
    Object.assign(coupon.value, row);
    dialogEditVisible.value = true;
};
const save = () => {
    if (currentEditIndex.value !== null) {
        couponsUpdate(coupon.value).then(res => {
            ElMessage.success('更新成功');
            tableData.value[currentEditIndex.value] = coupon.value;
            dialogEditVisible.value = false;
            currentEditIndex.value = null;
        })
    } else {
        couponsAdd(coupon.value).then(res => {
            ElMessage.success('添加成功');
            tableData.value.push(coupon.value);
            dialogAddVisible.value = false;
        })
    }
    Object.assign(coupon.value, {
        id: null,
        code: null,
        description: null,
        discountType: null,
        discountValue: null,
        maxDiscountAmount: null,
        minPurchaseAmount: null,
        usageLimit: null,
        usedCount: null,
        validFrom: null,
        validUtil: null,
        isActive: null,
    })
}
const cancel = () => { 
    Object.assign(coupon.value, {
            id: null,
            code: null,
            description: null,
            discountType: null,
            discountValue: null,
            maxDiscountAmount: null,
            minPurchaseAmount: null,
            usageLimit: null,
            usedCount: null,
            validFrom: null,
        })
    if (currentEditIndex.value !== null) {
        dialogEditVisible.value = false;
    } else {
        dialogAddVisible.value = false;
    }
}

// 单个删除
const handleDeleteOne = (index, row) => {
    dialogTitle.value = `是否删除优惠卷 ${row.id}？`;
    deleteType.value = 'single';
    ids.value.push(row.id);
    currentIndex.value = index;
    dialogVisible.value = true;
    console.log('删除:', dialogVisible.value)
};

// 批量删除
const handleDeleteBatch = () => {
    if (selectedRows.value.length === 0) {
        ElMessage.warning('请先选择要删除的优惠卷');
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
        couponsDelete(ids.value)
        ElMessage.success('删除成功');
    } else if (deleteType.value === 'batch') {
        // 这里可以按 ID 过滤，或根据选中项删除
        const selectedIds = selectedRows.value.map(r => r.id);
        tableData.value = tableData.value.filter(row => !selectedIds.includes(row.id));
        couponsDelete(selectedIds)
        ElMessage.success('批量删除成功');
    }
    // 关闭弹窗
    dialogVisible.value = false;
};
</script>