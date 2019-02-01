import Vue from 'vue'
import Vuex from 'vuex'
import custom from './modules/custom'
import api from '@/api/index.js'
import {
  SHOW_BACKBLACK
} from './types'
Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    isLoading: false,
    openInfo: {
      huidiao: false,
      huidiaoInfo: '',
      huidiao2: false,
      huidiaoInfo2: '',
      huidiao3: false,
      huidiaoInfo3: '',
      huidiao4: false,
      huidiaoInfo4: ''
    }
  },
  mutations: {
    SHOW_BACKBLACK: (state, val) => {
      state.backblack.ishow = val
    }
  },
  getters: {
    isLoading: state => state.isLoading,
    openInfo: state => state.openInfo
  },
  actions: {
    backclick: ({
      commit
    }, val) => {
      commit(SHOW_BACKBLACK, val)
    },
    getcode: ({
      commit
    }) => {
      return api.myGotFetch('/api/getCaptcha')
    },
    loginConfirm: ({
      commit
    }, obj) => {
      return api.myPostFetch('/api/login', obj)
    },
    registerConfirm: ({
      commit
    }, obj) => {
      return api.myPostFetch('/api/register', obj)
    },
    logout: ({
      commit
    }, obj) => {
      return api.myPostFetch('/api/logout', obj)
    },
    getTopTabList: ({
      commit
    }) => {
      return api.myGotFetch('/api/getTopTabList')
    },
    getProductList: ({
      commit
    }, obj) => {
      return api.myGotFetch('/api/getProductList', obj)
    },
    getMyOrderList: ({
      commit
    }, obj) => {
      return api.myGotFetch('/api/getMyOrderList', obj)
    },
    getMydata: ({
      commit
    }, obj) => {
      return api.myGotFetch('/api/getMydata', obj)
    },
    getMyInfo: ({
      commit
    }, obj) => {
      return api.myGotFetch('/api/getMyInfo', obj)
    },
    getMyTeams: ({
      commit
    }, obj) => {
      return api.myGotFetch('/api/getMyTeams', obj)
    },
    myPromote: ({
      commit
    }, obj) => {
      return api.myGotFetch('/api/myPromote', obj)
    },
    getMyCarProfit: ({
      commit
    }, obj) => {
      return api.myGotFetch('/api/getMyCarProfit', obj)
    },
    getAllWelfare: ({
      commit
    }, obj) => {
      return api.myGotFetch('/api/getAllWelfare', obj)
    },
    getMyBuyOrderList: ({
      commit
    }, obj) => {
      return api.myGotFetch('/api/getMyBuyOrderList', obj)
    },
    getGlobalBonus: ({
      commit
    }, obj) => {
      return api.myGotFetch('/api/getGlobalBonus', obj)
    },
    getLeaderBonus: ({
      commit
    }, obj) => {
      return api.myGotFetch('/api/getLeaderBonus', obj)
    },
    getEspeciallyBonus: ({
      commit
    }, obj) => {
      return api.myGotFetch('/api/getEspeciallyBonus', obj)
    },
    getPaymentList: ({
      commit
    }, obj) => {
      return api.myGotFetch('/api/getPaymentList', obj)
    },
    payOrder: ({
      commit
    }, obj) => {
      return api.myPostFetch('/api/payOrder', obj)
    },
    getCompanyInfo: ({
      commit
    }, obj) => {
      return api.myGotFetch('/api/getCompanyInfo', obj)
    },
    getCustomerInfo: ({
      commit
    }, obj) => {
      return api.myGotFetch('/api/getCustomerInfo', obj)
    },
    getWithdrawInfo: ({
      commit
    }, obj) => {
      return api.myGotFetch('/api/getWithdrawInfo', obj)
    },
    getWithdrawDetails: ({
      commit
    }, obj) => {
      return api.myGotFetch('/api/getWithdrawDetails', obj)
    },
    withdrawSubmit: ({
      commit
    }, obj) => {
      return api.myPostFetch('/api/withdrawSubmit', obj)
    },
    rechargeSubmit: ({
      commit
    }, obj) => {
      return api.myPostFetch('/api/rechargeSubmit', obj)
    },
		getRechargeList: ({
		  commit
		}, obj) => {
		  return api.myGotFetch('/api/getRechargeList', obj)
		},
		submitRechargeCredential: ({
		  commit
		}, obj) => {
		  return api.myPostFetch('/api/submitRechargeCredential', obj)
		},
		submitOrderCredential: ({
		  commit
		}, obj) => {
		  return api.myPostFetch('/api/submitOrderCredential', obj)
		}
		
  },
  modules: {
    custom: custom
  }
})

export default store
