import axios from "axios";

const instance = axios.create({
    baseURL: import.meta.env.VITE_API_URL,
    timeout: 10000,
})
instance.interceptors.request.use(
    config=>{
        const token = localStorage.getItem("token")
        if(token){
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    error=>{
        return Promise.reject(error);
    }
)
instance.interceptors.response.use(
    res=>{
        return res.data;
    },
    error=>{
        if(error.response?.status === 401){
            window.location.href='/login';
        }
        return Promise.reject(error)
    }
)
export default instance
