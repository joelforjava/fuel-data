<template>
  <div>
    <b-container class="login-form">
      <b-alert variant="success" v-if="success">
        OMG! You signed in!
      </b-alert>
      <h1>Log In</h1>
      <b-form>
        <b-form-group
          label="Username"
          label-for="username"
        >
          <b-form-input type="text" id="username" v-model="user.username"/>
        </b-form-group>
        <b-form-group
          label="Password"
          label-for="password"
        >
          <b-form-input type="password" id="password" v-model="user.password"/>
        </b-form-group>
        <button type="button" class="btn btn-primary btn-lg btn-block my-4" @click="submit">Login</button>
      </b-form>
    </b-container>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data () {
    return {
      user: {
        username: '',
        password: ''
      },
      success: false
    }
  },
  methods: {
    submit: function () {
      this.login()
    },
    login: function () {
      this.$auth.login(this.$data.user.username, this.$data.user.password)
        .then((res) => res.json())
        .then((data) => {
          console.log(data)
          if (data) {
            this.success = true
            if (data.access_token) {
              console.log('Setting session')
              this.$store.commit('SET_SESSION', data.access_token)
              this.$router.push(this.$route.query.from || '/vehicles')
            }
          }
        })
        .catch((err) => console.error(err))
    }
  }
}
</script>

<style scoped>

</style>
