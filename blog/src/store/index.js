import { createStore } from 'vuex'

export default createStore({
  state: {
    search:''
  },
  mutations: {
    newSearch(state,msg){
      state.search = msg
    }
  },
  actions: {
  },
  modules: {
  }
})
