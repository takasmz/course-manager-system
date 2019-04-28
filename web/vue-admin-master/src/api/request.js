import axios from "axios";


/****** 创建axios实例 ******/
const service = axios.create({
    baseURL: 'http://localhost:8086',  // api的base_url
    timeout: 5000  // 请求超时时间
});


/****** request拦截器==>对请求参数做处理 ******/
// 添加请求拦截器
service.interceptors.request.use(config => {
// 在发送请求之前做些什么
//判断是否存在token，如果存在将每个页面header都添加token
    if(app.store.state.token){
        config.headers.common['Authorization']=app.store.state.token
    }
    return config;
}, error => {
// 对请求错误做些什么
    console.log("request error");
    return Promise.reject(error);
});

// http response 拦截器
service.interceptors.response.use(
    response => {
        return response;
    },
    error => {
        if (error.response) {
            console.log("response error");
            switch (error.response.status) {
                case 401:
                    this.$store.commit('del_token');
                    router.replace({
                        path: '/login',
                        query: {redirect: router.currentRoute.fullPath}//登录成功后跳入浏览的当前页面
                    })
            }
        }
        return Promise.reject(error)
    });
export default service;