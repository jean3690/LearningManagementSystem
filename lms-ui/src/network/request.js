import axios from "axios";
import { MessageUtil } from "../utils";
import router from "../router";

// 创建axios实例
const instance = axios.create({
    baseURL: import.meta.env.VITE_API_URL,
    timeout: 15000, // 适当延长超时时间
    headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
    }
});

// 请求取消控制器映射
const pendingRequests = new Map();

// 生成请求的唯一key
const generateRequestKey = (config) => {
    const { method, url, params, data } = config;
    return [method, url, JSON.stringify(params), JSON.stringify(data)].join("&");
};

// 添加请求拦截器
instance.interceptors.request.use(
    config => {
        // 请求日志
        if (import.meta.env.DEV) {
            console.log('请求发送:', config);
        }
        
        // 取消重复请求
        const requestKey = generateRequestKey(config);
        if (pendingRequests.has(requestKey)) {
            const cancel = pendingRequests.get(requestKey);
            cancel('取消重复请求');
            pendingRequests.delete(requestKey);
        }
        
        // 添加取消请求的控制器
        config.cancelToken = config.cancelToken || new axios.CancelToken(cancel => {
            if (!pendingRequests.has(requestKey)) {
                pendingRequests.set(requestKey, cancel);
            }
        });
        
        // 添加token
        const token = localStorage.getItem("token");
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        
        return config;
    },
    error => {
        console.error('请求错误:', error);
        return Promise.reject(error);
    }
);

// 添加响应拦截器
instance.interceptors.response.use(
    response => {
        // 请求完成后，从pendingRequests中移除
        const requestKey = generateRequestKey(response.config);
        pendingRequests.delete(requestKey);
        
        // 响应日志
        if (import.meta.env.DEV) {
            console.log('响应接收:', response);
        }
        
        // 检查业务状态码
        const { code, message, data } = response.data;
        
        // 如果业务状态码表示成功，直接返回数据
        if (code === 200 || code === 0) {
            return data;
        }
        
        // 处理特定业务错误
        if (code === 401) {
            // token过期或无效
            MessageUtil.confirm('您的登录状态已过期，请重新登录', '系统提示', '重新登录', '取消', 'warning').then(() => {
                localStorage.removeItem('token');
                router.push('/login');
            });
            return Promise.reject(new Error(message || '登录状态已过期'));
        } else if (code === 403) {
            // 权限不足
            router.push('/403');
            return Promise.reject(new Error(message || '权限不足'));
        } else {
            // 其他业务错误
            MessageUtil.error(message || '请求失败');
            return Promise.reject(new Error(message || '请求失败'));
        }
    },
    async error => {
        // 从pendingRequests中移除请求
        if (error.config) {
            const requestKey = generateRequestKey(error.config);
            pendingRequests.delete(requestKey);
        }
        
        // 请求取消的错误
        if (axios.isCancel(error)) {
            console.log('请求被取消:', error.message);
            return Promise.reject(error);
        }
        
        // 网络错误处理
        if (!error.response) {
            MessageUtil.error('网络连接失败，请检查网络设置');
            return Promise.reject(error);
        }
        
        // HTTP状态码错误处理
        const { status } = error.response;
        let errorMessage = '请求失败';
        
        switch (status) {
            case 400:
                errorMessage = '请求参数错误';
                break;
            case 401:
                errorMessage = '未授权，请登录';
                // 清除token并跳转到登录页
                localStorage.removeItem('token');
                router.push('/login');
                break;
            case 403:
                errorMessage = '拒绝访问';
                router.push('/403');
                break;
            case 404:
                errorMessage = '请求地址不存在';
                break;
            case 500:
                errorMessage = '服务器内部错误';
                break;
            case 502:
                errorMessage = '网关错误';
                break;
            case 503:
                errorMessage = '服务不可用';
                break;
            case 504:
                errorMessage = '网关超时';
                break;
            default:
                errorMessage = `请求失败 (${status})`;
        }
        
        MessageUtil.error(errorMessage);
        return Promise.reject(error);
    }
);

// 封装请求方法
const request = instance;

export default request;