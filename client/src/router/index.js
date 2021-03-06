import Vue from 'vue'
import Router from 'vue-router'
import Welcome from '@/components/Welcome'
import FillUps from '@/components/FillUps'
import NewFillUp from '@/components/NewFillUp'
import NewGasStation from '@/components/NewGasStation'

Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/',
      name: 'Welcome',
      component: Welcome
    },
    {
      path: '/fills',
      name: 'Fill Ups',
      component: FillUps
    },
    {
      path: '/fills/add',
      name: 'Add Fill Up',
      component: NewFillUp
    },
    {
      path: '/station/add',
      name: 'Add Gas Station',
      component: NewGasStation
    }
  ]
})

export default router
