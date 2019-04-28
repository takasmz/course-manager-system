import Vue from 'vue'
import Vuex from 'vuex'
import * as actions from './actions'
import * as getters from './getters'

Vue.use(Vuex)

// 应用初始状态
const state = {
    count: 10
};

// 定义所需的 mutations
const mutations = {
    INCREMENT(state) {
        state.count++
    },
    DECREMENT(state) {
        state.count--
    }
};

// 创建 store 实例
export default new Vuex.Store({
    actions,
    getters,
    state:{
        token:'',
        menus:[],
    },

    mutations:{
        set_token(state, token) {
            state.token = token;
            sessionStorage.token = token
        },
        del_token(state) {
            state.token = '';
            sessionStorage.removeItem('token')
        },
        add_menu(state,menus){
            state.menus = menus;
            sessionStorage.menus = menus
        }
    }
})