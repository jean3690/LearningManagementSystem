<template>
    <el-dialog :title="title" v-model="dialogVisible" width="30%">
        <span>{{ content }}</span>
        <template #footer>
            <el-button @click="handleClose">取 消</el-button>
            <el-button type="primary" @click="handleConfirm">确 定</el-button>
        </template>
    </el-dialog>
</template>

<script setup>
import { defineProps, defineEmits, computed } from 'vue';

const props = defineProps({
    title: { type: String, default: '提示' },
    visible: { type: Boolean, default: false },
    content: { type: String, default: '确定执行此操作？' }
});

const emit = defineEmits(['update:visible', 'confirm']);

// 创建一个计算属性来双向绑定 v-model
const dialogVisible = computed({
    get() {
        return props.visible;
    },
    set(value) {
        emit('update:visible', value);
    }
});

const handleClose = () => {
    emit('confirm');
    emit('update:visible', false);
};

const handleConfirm = () => {
    emit('confirm');
    emit('update:visible', false);
};
</script>