import axios from 'axios';
import store from "../vuex/store";

// axios.defaults.withCredentials = true
let base = 'http://localhost:8086';
axios.defaults.headers.common['Authorization'] = store.state.token;

// 添加请求拦截器
axios.interceptors.request.use(config => {
// 在发送请求之前做些什么
//判断是否存在token，如果存在将每个页面header都添加token
    if(store.state.token){
        config.headers.common['Authorization']=store.state.token
    }
    return config;
}, error => {
// 对请求错误做些什么
    console.log("request error");
    return Promise.reject(error);
});

// http response 拦截器
axios.interceptors.response.use(
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

export const requestLogin = (params,config) => {console.log(config); return axios.post(`${base}/user/login`,  params ,config).then(res => res).catch(function (error) {
    if (error.response) {
        console.log('Error data : ', error.response.data);
        console.log('Error status : ', error.response.status);
        console.log('Error headers : ', error.response.headers);
    } else if (error.request) {
        console.log('Error request : ', error.request);
    } else {
        console.log('Error message : ', error.message);
    }
    console.log(error.config);
}); };

export const captcha = () => { return axios.get(`${base}/captcha`); };

export const menu = () => { return axios.get(`${base}/resource/menu`); };

export const getUserList = params => { return axios.get(`${base}/course/getCourseList`, { params: params }); };

export const getUserListPage = params => { return axios.get(`${base}/course/getCourseList`, { params: params }); };

export const removeUser = params => { return axios.get(`${base}/user/remove`, { params: params }); };

export const batchRemoveUser = params => { return axios.get(`${base}/user/batchremove`, { params: params }); };

export const editUser = params => { return axios.get(`${base}/user/edit`, { params: params }); };

export const addUser = params => { return axios.get(`${base}/user/add`, { params: params }); };