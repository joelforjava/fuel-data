/* eslint-disable no-console */

import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const loadFromLocalStorage = function () {
  const fromLocal = localStorage.getItem('userSession')
  // if (fromLocal) {
  //     return JSON.parse(fromLocal)
  // }

  return fromLocal ? JSON.parse(fromLocal) : false
}

export default new Vuex.Store({
  state: {
    session: loadFromLocalStorage()
  },
  mutations: {
    'SET_SESSION' (state, session) {
      state.session = session
      // TODO - this isn't a good long-term solution, but should be fine for starters
      if (session) {
        localStorage.setItem('userSession', JSON.stringify(session))
      } else {
        localStorage.removeItem('userSession')
      }
    }
  },
  getters: {
    session: state => state.session
  }
})
