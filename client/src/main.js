// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import state from './state'
import * as uiv from 'uiv'
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import './assets/css/grails.css'
import './assets/css/main.css'
import {ApiService, LoginService} from './services'

Vue.config.productionTip = false

Vue.use(BootstrapVue)
Vue.use(IconsPlugin)
Vue.use(uiv)
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store: state,
  apiService: new ApiService(process.env.SERVER_URL),
  auth: new LoginService(process.env.SERVER_URL),
  components: { App },
  template: '<App/>'
})
