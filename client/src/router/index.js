import Vue from 'vue'
import Router from 'vue-router'
import Welcome from '@/components/Welcome'
import FillUps from '@/components/FillUps'
import NewFillUp from '@/components/NewFillUp'
import NewGasStation from '@/components/NewGasStation'
import Vehicles from '@/components/Vehicles'
import Login from '../components/Login'
import state from '../state'

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
      component: FillUps,
      beforeEnter: (to, from, next) => {
        const session = state.getters.session
        if (!session) {
          next({ name: 'Login', query: { from: to.fullPath } })
        } else {
          next()
        }
      }
    },
    {
      path: '/fills/add',
      name: 'Add Fill Up',
      component: NewFillUp,
      beforeEnter: (to, from, next) => {
        const session = state.getters.session
        if (!session) {
          next({ name: 'Login', query: { from: to.fullPath } })
        } else {
          next()
        }
      }
    },
    {
      path: '/station/add',
      name: 'Add Gas Station',
      component: NewGasStation
    },
    {
      path: '/vehicles',
      name: 'Vehicles',
      component: Vehicles,
      beforeEnter: (to, from, next) => {
        const session = state.getters.session
        if (!session) {
          next({ name: 'Login', query: { from: to.fullPath } })
        } else {
          next()
        }
      }
    },
    {
      path: '/vehicles/:vehicleId/fills',
      name: 'Vehicle Fill Ups',
      component: FillUps,
      beforeEnter: (to, from, next) => {
        const session = state.getters.session
        if (!session) {
          next({ name: 'Login', query: { from: to.fullPath } })
        } else {
          next()
        }
      }
    },
    {
      path: '/vehicles/:vehicleId/fills/add',
      name: 'Add Vehicle Fill Up',
      component: NewFillUp,
      beforeEnter: (to, from, next) => {
        const session = state.getters.session
        if (!session) {
          next({ name: 'Login', query: { from: to.fullPath } })
        } else {
          next()
        }
      }
    },
    {
      path: '/login',
      name: 'Login',
      component: Login,
      beforeEnter: (to, from, next) => {
        const session = state.getters.session
        if (!session) {
          next({ query: { from: to.fullPath } })
        } else {
          next({ name: 'Vehicles' })
        }
      }
    }
  ]
})

export default router
