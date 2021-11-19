import { createStore } from 'vuex'

export default createStore({
  state: {
    search:'',
    inputStatus: 1,
  },
  mutations: {
    newSearch(state,msg){
      state.search = msg
    },
    newStatus(state,msg){
      state.inputStatus = msg
    }
  },
  actions: {
  },
  modules: {
  }
})
