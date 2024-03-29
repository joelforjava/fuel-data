/* eslint-disable no-console */
/* eslint-disable no-unused-vars */
import Vue from 'vue'
import state from '../state'

Vue.mixin({
  beforeCreate () {
    const options = this.$options
    if (options.auth) {
      this.$auth = options.auth
    } else if (options.parent && options.parent.$auth) {
      this.$auth = options.parent.$auth
    }
    if (options.apiService) {
      this.$apiService = options.apiService
    } else if (options.parent && options.parent.$apiService) {
      this.$apiService = options.parent.$apiService
    }
  }
})

class BaseService {
  constructor (url) {
    this.baseUrl = url
    console.debug(`Service created with URL: ${url}`)
    this.headers = new Headers()
  }

  addHeader (name, value) {
    this.headers.set(name, value)
  }

  removeHeader (name) {
    if (this.headers.has(name)) {
      this.headers.delete(name)
    }
  }

  request (method, route, data) {
    const url = `${this.baseUrl}${route}`
    return new Promise((resolve, reject) => {
      fetch(url, {
        method: method,
        body: data,
        headers: this.headers
      }).then((res) => {
        if (res.status === 200) {
          resolve(res) // TODO - is there a way to dynamically replace res.text() with res.json()?? Could just resolve(res).
        } else {
          resolve(res) // TODO - should we resolve non-OK differently?
        }
      }).catch(err => {
        reject(err)
      })
    })
  }

  get (route, data) {
    // TODO - convert data into query params?
    //      - see this: https://fetch.spec.whatwg.org/#fetch-api
    return this.request('GET', route, data)
  }

  // NOTE: Grails doesn't seem to like the use of the HEAD HTTP Method, so not sure if this will actually be useful.
  head (route) {
    return this.request('HEAD', route)
  }

  post (route, data) {
    return this.request('POST', route, data)
  }

  put (route, data) {
    return this.request('PUT', route, data)
  }

  delete (route, data) {
    return this.request('DELETE', route, data)
  }

  buildFormData (formData, data, parentKey) {
    // console.debug(`Calling buildFormData with parent key of ${parentKey}`)
    if (data && typeof data === 'object' && !(data instanceof Date) && !(data instanceof File)) {
      Object.keys(data).forEach(key => {
        this.buildFormData(formData, data[key], parentKey ? `${parentKey}.${key}` : key)
      })
    } else {
      const value = data == null ? '' : data

      formData.append(parentKey, value)
    }
  }
}

export class LoginService extends BaseService {
  login (username, password) {
    const creds = {
      'username': username,
      'password': password
    }
    return this.post('/api/login', JSON.stringify(creds))
  }

  logout () {
    return this.post('/api/logout')
  }
}

export class ApiService extends BaseService {
  constructor (url) {
    super(url)
    this.addHeader('Content-Type', 'application/json')
    this.addHeader('Accept', 'application/json')
    // I feel like this isn't the best place for this, but I'll leave it until I have more time to research
    if (state.getters.session) {
      this.addHeader('Authorization', `Bearer ${state.getters.session}`)
    }
    state.watch((state, getters) => getters.session,
      (newValue, oldValue) => {
        if (newValue) {
          this.addHeader('Authorization', `Bearer ${newValue}`)
        } else {
          this.removeHeader('Authorization')
        }
      })
  }
  getVehicles () {
    // TODO - we currently just access vehicles with no regard of the user
    //      - at some point, we may have to change that if we decide this app is thrilling enough
    return this.get('/vehicles')
  }
  getFillUps (vehicleId) {
    return this.get(`/vehicles/${vehicleId}/fill-ups`)
  }
  getGasStations () {
    return this.get('/gas-stations')
  }
  saveGasStation (data) {
    return this.post('/gas-stations', JSON.stringify(data))
  }
  saveFillUp (vehicleId, data) {
    return this.post(`/vehicles/${vehicleId}/fill-ups`, JSON.stringify(data))
  }
}
