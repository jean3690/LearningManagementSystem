<template>
  <div>
    <!-- 查询区域 -->
    <div class="mb-4 mt-4">
      <label>用户Id: </label>
      <el-input
        placeholder="请输入用户id"
        v-model="queryParam.invoiceNumber"
        style="width: 300px;"
        class="mr-2"
      />
      <label>uuid:</label>
        <el-input
            placeholder="请输入uuid"
            v-model="queryParam.uuid"
            style="width: 300px;"
            class="mr-2"
        />
      <label>email:</label>
        <el-input
            placeholder="请输入email"
            v-model="queryParam.email"
            style="width: 300px;"
            class="mr-2"
        />
        <label>username:</label>
        <el-input
            placeholder="请输入username"
            v-model="queryParam.username"
            style="width: 300px;"
            class="mr-2"
        />
        <label>phone:</label>
        <el-input
            placeholder="请输入phone"
            v-model="queryParam.phone"
            style="width: 300px;"
            class="mr-2"
        />
        <label>isActive:</label>
        <el-select v-model="queryParam.isActive" placeholder="请选择是否激活" style="width: 150px;" class="mr-2">
            <el-option label="是" :value="true" />
            <el-option label="否" :value="false" />
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
        <el-table-column prop="id" align="center" label="用户Id" width="120" />
        <el-table-column prop="uuid" align="center" label="UUID" width="180" />
        <el-table-column prop="email" align="center" label="邮箱" width="200" />
        <el-table-column prop="username" align="center" label="用户名" width="120" />
        <el-table-column prop="phone" align="center" label="手机号" width="150" />
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
    <Dialog
      :title="dialogTitle"
      v-model:visible="dialogVisible"
      @confirm="confirmDelete"
    />
    <el-dialog v-model="dialogAddVisible" title="添加用户" width="500px">
      <el-form ref="form" :model="user" label-width="80px">
        <el-form-item label="邮箱">
          <el-input v-model="user.email" />
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="user.username" />
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="user.phone" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogAddVisible = false">取消</el-button>
        <el-button type="primary" @click="saveUser">确定</el-button>
      </template>
      </el-dialog>
      <el-dialog v-model="dialogEditVisible" title="编辑用户" width="500px">
      <el-form ref="form" :model="user" label-width="80px">
        <el-form-item label="邮箱">
          <el-input v-model="user.email" />
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="user.username" />
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="user.phone" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogEditVisible = false">取消</el-button>
        <el-button type="primary" @click="saveUser">确定</el-button>
      </template>
      </el-dialog>
  </div>
</template>

<script setup>
import { ref,watch,reactive, onMounted } from 'vue';
import Dialog from '@/components/dialog/index.vue';
import { userAdd, userDelete, userPage } from '../../api/user/user';
// 查询参数
const queryParam = ref({
    id: null,
    uuid: null,
    email: null,
    username: null,
    phone: null,
});
const user = ref({
  id: null,
  uuid: null,
  email: null,
  username: null,
  password: null,
  phone: null,
  token: null,
})
const pageQueryParam = reactive({
    total: 0,
    pageNum: 1,
    pageSize: 10,
    usersDto: user.value
});
// 表格数据（示例）
const tableData = ref([]);
const pageQuery = () => {
    userPage(pageQueryParam).then(res => { 
        pageQueryParam.total = res.total;
        pageQueryParam.pageNum = res.pageNum;
        pageQueryParam.pageSize = res.pageSize;
        tableData.value = res.list;
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
    Object.assign(pageQueryPara.usersDto, queryParam.value);
    pageQuery();
};
const saveUser = () => { 
    if (currentEditIndex.value == null) {
        userAdd(user.value)
        tableData.value.push(user.value)
        dialogAddVisible.value = false
    } else {
        selectedRows.value[currentEditIndex.value] = user.value
        dialogEditVisible.value = false
    }
    user.value.id = null
    user.value.uuid = null
    user.value.email = null
    user.value.username = null
    user.value.phone = null
}

// 新增
const handleAdd = () => {
    // TODO: 跳转或弹窗
  dialogAddVisible.value = true
};

// 编辑
const handleEdit = (index, row) => {
    user.value = row
    dialogEditVisible.value = true
    currentEditIndex.value = index
  // TODO: 编辑逻辑
};

// 单个删除
const handleDeleteOne = (index, row) => {
  dialogTitle.value = `是否删除用户 ${row.id}？`;
    deleteType.value = 'single';
  ids.value.push(row.id)
  currentIndex.value = index;
    dialogVisible.value = true;
  console.log('删除:', dialogVisible.value)
};

// 批量删除
const handleDeleteBatch = () => {
  if (selectedRows.value.length === 0) {
    ElMessage.warning('请先选择要删除的用户');
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
    userDelete(ids.value)
    ElMessage.success('删除成功');
  } else if (deleteType.value === 'batch') {
    // 这里可以按 ID 过滤，或根据选中项删除
    const selectedIds = selectedRows.value.map(r => r.id);
      tableData.value = tableData.value.filter(row => !selectedIds.includes(row.id));
    userDelete(selectedIds)
    ElMessage.success('批量删除成功');
  }
  // 关闭弹窗
  dialogVisible.value = false;
};
</script>