/* eslint-disable no-console */
/* eslint-disable no-unused-vars */
import Vue from 'vue'

Vue.mixin({
  beforeCreate () {
    const options = this.$options
    if (options.auth) {
      this.$auth = options.auth
    } else if (options.parent && options.parent.$auth) {
      this.$auth = options.parent.$auth
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
        body: data
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
