import axios from 'axios';

// axios.defaults.withCredentials = true
let base = 'http://127.0.0.1:8086';

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

export const getUserList = params => { return axios.get(`${base}/user/list`, { params: params }); };

export const getUserListPage = params => { return axios.get(`${base}/user/listpage`, { params: params }); };

export const removeUser = params => { return axios.get(`${base}/user/remove`, { params: params }); };

export const batchRemoveUser = params => { return axios.get(`${base}/user/batchremove`, { params: params }); };

export const editUser = params => { return axios.get(`${base}/user/edit`, { params: params }); };

export const addUser = params => { return axios.get(`${base}/user/add`, { params: params }); };