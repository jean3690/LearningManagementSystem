<template>
    <div>
        <!-- 查询区域 -->
        <div class="mb-4 mt-4">
            <label>报名id</label>
            <el-input placeholder="请输入报名id" v-model="queryParam.id" style="width: 300px;" class="mr-2" />
            <label>用户id</label>
            <el-input placeholder="请输入用户id" v-model="queryParam.userId" style="width: 300px;" class="mr-2" />
            <label>课程id</label>
            <el-input placeholder="请输入课程id" v-model="queryParam.courseId" style="width: 300px;" class="mr-2" />
            <label>报名日期</label>
            <el-date-picker v-model="queryParam.enrolledAt" type="date" placeholder="选择报名日期" style="width: 180px;"
                class="mr-2" />
            <label>完成日期</label>
            <el-date-picker v-model="queryParam.completedAt" type="date" placeholder="选择完成日期" style="width: 180px;"
                class="mr-2" />
            <label>是否有效</label>
            <el-select v-model="queryParam.isActive" placeholder="请选择是否有效" style="width: 150px;" class="mr-2">
                <el-option label="全部" :value="0" />
                <el-option label="有效" :value="1" />
                <el-option label="无效" :value="2" />
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
            <el-table-column prop="id" label="报名id" align="center" width="120" />
            <el-table-column prop="userId" label="用户id" align="center" width="120" />
            <el-table-column prop="courseId" label="课程id" align="center" width="120" />
            <el-table-column prop="orderItemId" label="订单项id" align="center" width="120" />
            <el-table-column prop="enrolledAt" label="报名日期" align="center" width="150" />
            <el-table-column prop="completedAt" label="完成日期" align="center" width="150" />
            <el-table-column prop="completionPercentage" label="完成百分比" align="center" width="150" />
            <el-table-column prop="lastAccessedAt" label="最后访问时间" align="center" width="150" />
            <el-table-column prop="lastLessonId" label="最后课程id" align="center" width="120" />
            <el-table-column prop="isActive" label="是否有效" align="center" width="100">
                <template #default="scope">
                    <span v-if="scope.row.isActive === 1">有效</span>
                    <span v-else>无效</span>
                </template>
            </el-table-column>
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
        <el-dialog v-model:visible="dialogAddVisible" title="新增" width="500px"> 
            <el-form ref="form" :model="enrollment" label-width="80px">
                <el-form-item label="用户id">
                    <el-input v-model="enrollment.userId" placeholder="请输入用户id" />
                </el-form-item>
                <el-form-item label="课程id">
                    <el-input v-model="enrollment.courseId" placeholder="请输入课程id" />
                </el-form-item>
                <el-form-item label="订单项id">
                    <el-input v-model="enrollment.orderItemId" placeholder="请输入订单项id" />
                </el-form-item>
                <el-form-item label="报名日期">
                    <el-date-picker v-model="enrollment.enrolledAt" type="date" placeholder="选择报名日期" />
                </el-form-item>
                <el-form-item label="完成日期">
                    <el-date-picker v-model="enrollment.completedAt" type="date" placeholder="选择完成日期" />
                </el-form-item>
                <el-form-item label="完成百分比">
                    <el-input v-model="enrollment.completionPercentage" placeholder="请输入完成百分比" />
                </el-form-item>
                <el-form-item label="最后访问时间">
                    <el-date-picker v-model="enrollment.lastAccessedAt" type="date" placeholder="选择最后访问时间" />
                </el-form-item>
                <el-form-item label="最后课程id">
                    <el-input v-model="enrollment.lastLessonId" placeholder="请输入最后课程id" />
                </el-form-item>
                <el-form-item label="是否有效">
                    <el-select v-model="enrollment.isActive" placeholder="请选择是否有效">
                        <el-option label="全部" :value="0" />
                        <el-option label="有效" :value="1" />
                        <el-option label="无效" :value="2" />
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="save">确定</el-button>
                    <el-button @click="cancel">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
        <el-dialog v-model:visible="dialogEditVisible" title="编辑" width="500px"> 
            <el-form ref="form" :model="enrollment" label-width="80px"> 
                <el-form-item label="用户id">
                    <el-input v-model="enrollment.userId" placeholder="请输入用户id" />
                </el-form-item>
                <el-form-item label="课程id">
                    <el-input v-model="enrollment.courseId" placeholder="请输入课程id" />
                </el-form-item>
                <el-form-item label="订单项id">
                    <el-input v-model="enrollment.orderItemId" placeholder="请输入订单项id" />
                </el-form-item>
                <el-form-item label="报名日期">
                    <el-date-picker v-model="enrollment.enrolledAt" type="date" placeholder="选择报名日期" />
                </el-form-item>
                <el-form-item label="完成日期">
                    <el-date-picker v-model="enrollment.completedAt" type="date" placeholder="选择完成日期" />
                </el-form-item>
                <el-form-item label="完成百分比">
                    <el-input v-model="enrollment.completionPercentage" placeholder="请输入完成百分比" />
                </el-form-item>
                <el-form-item label="最后访问时间">
                    <el-date-picker v-model="enrollment.lastAccessedAt" type="date" placeholder="选择最后访问时间" />
                </el-form-item>
                <el-form-item label="最后课程id">
                    <el-input v-model="enrollment.lastLessonId" placeholder="请输入最后课程id" />
                </el-form-item>
                <el-form-item label="是否有效">
                    <el-select v-model="enrollment.isActive" placeholder="请选择是否有效">
                        <el-option label="全部" :value="0" />
                        <el-option label="有效" :value="1" />
                        <el-option label="无效" :value="2" />
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="save">确定</el-button>
                    <el-button @click="cancel">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import Dialog from '@/components/dialog/index.vue';
import { enrollmentsAdd, enrollmentsDelete, enrollmentsPage, enrollmentsUpdate } from '../../api/enrollment/enrollment';
// 查询参数
const queryParam = ref({
    id: null,
    userId: null,
    courseId: null,
    enrolledAt: null,
    completedAt: null,
    isActive: 0
});
const enrollment = ref({
    id: null,
    userId: null,
    courseId: null,
    orderItemId: null,
    enrolledAt: null,
    completedAt: null,
    completionPercentage: null,
    lastAccessedAt: null,
    lastLessonId: null,
    isActive: null
})
const pageQueryParam = reactive({
    total: 0,
    pageNum: 1,
    pageSize: 10,
    enrollments: enrollment.value
});
// 表格数据（示例）
const tableData = ref([]);
const pageQuery = () => {
    enrollmentsPage(pageQueryParam).then(res => {
        console.log('查询结果:', res);
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
const cancel = () => { 
    if (currentEditIndex.value !== null) {
        Object.assign(enrollment.value, {
            id: null,
            userId: null,
            courseId: null,
            orderItemId: null,
            enrolledAt: null,
            completedAt: null,
            completionPercentage: null,
            lastAccessedAt: null,
            lastLessonId: null,
            isActive: null
        })
        dialogEditVisible = false
    } else {
        Object.assign(enrollment.value, {
            id: null,
            userId: null,
            courseId: null,
            orderItemId: null,
            enrolledAt: null,
            completedAt: null,
            completionPercentage: null,
            lastAccessedAt: null,
            lastLessonId: null,
            isActive: null
        })
        dialogAddVisible.value = false
    }
};

// 搜索
const onSearch = () => {
    // TODO: 调用 API 查询
    Object.assign(pageQueryParam.enrollments, queryParam.value);
    pageQuery();
};

// 新增
const handleAdd = () => {
    // TODO: 跳转或弹窗
    Object.assign(enrollment.value, {
        id: null,
        userId: null,
        courseId: null,
        orderItemId: null,
        enrolledAt: null,
        completedAt: null,
        completionPercentage: null,
        lastAccessedAt: null,
        lastLessonId: null,
        isActive: null
    });
    dialogAddVisible.value = true;
};

// 编辑
const handleEdit = (index, row) => {
    // TODO: 编辑逻辑
    currentEditIndex.value = index;
    Object.assign(enrollment.value, row);
    dialogEditVisible.value = true;
};
const save = () => { 
    if (currentEditIndex.value !== null) {
        enrollmentsUpdate(enrollment.value).then(res => {
            ElMessage.success('修改成功' + res);
            tableData.value[currentEditIndex.value] = enrollment.value;
            dialogEditVisible.value = false;
            currentEditIndex.value = null;
        })
    } else {
        enrollmentsAdd(enrollment.value).then(res => { 
            ElMessage.success('添加成功' + res);
            tableData.value.push(enrollment.value);
            dialogAddVisible.value = false;
        })
    }
    Object.assign(enrollment.value, {
        id: null,
        userId: null,
        courseId: null,
        orderItemId: null,
        enrolledAt: null,
        completedAt: null,
        completionPercentage: null,
        lastAccessedAt: null,
        lastLessonId: null,
        isActive: null
    });
};

// 单个删除
const handleDeleteOne = (index, row) => {
    dialogTitle.value = `是否删除 ${row.id}？`;
    deleteType.value = 'single';
    ids.value.push(row.id);
    currentIndex.value = index;
    dialogVisible.value = true;
};

// 批量删除
const handleDeleteBatch = () => {
    if (selectedRows.value.length === 0) {
        ElMessage.warning('请先选择要删除的');
        return;
    }
    dialogTitle.value = `是否删除选中的 ${selectedRows.value.length}`;
    deleteType.value = 'batch';
    dialogVisible.value = true;
};

// 确认删除
const confirmDelete = () => {
    if (ids.value.length === 0) {
        dialogVisible.value = false;
        return;
    }
    if (deleteType.value === 'single' && currentIndex.value !== null) {
        tableData.value.splice(currentIndex.value, 1);
        enrollmentsDelete(ids.value).then(res => {
            ElMessage.success('删除成功'+res);
        })
    } else if (deleteType.value === 'batch') {
        // 这里可以按 ID 过滤，或根据选中项删除
        const selectedIds = selectedRows.value.map(r => r.id);
        tableData.value = tableData.value.filter(row => !selectedIds.includes(row.id));
        enrollmentsDelete(selectedIds).then(res => {
            ElMessage.success('批量删除成功');   
        })
    }
    // 关闭弹窗
    ids.value = [];
    dialogVisible.value = false;
};
</script>