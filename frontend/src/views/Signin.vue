<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center pt-3">
        <div class="col-12 justify-content-center d-flex flex-row pt-5">
          <div id="signin-div" class="flex-item border">
            <h2 class="pt-4">Sign-In</h2>
            <form @submit="signin" class="form-group pt-4 pl-4 pr-4">
              <div class="form-group">
                <label>Email</label>
                <input v-model="email" type="email" class="form-control" required>
              </div>
              <div class="form-group">
                <label>Password</label>
                <input v-model="password" type="password" class="form-control" required>
              </div>
              <button class="btn btn-primary mt-2 py-0">Continue</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
const sweetalert = require("sweetalert");
// deprecated
export default {
  name: "Signin",

  data(){
    return{
      baseURL: process.env.VUE_APP_BACKEND_BASE_URL,
      email: null,
      password: null
    }
  },

  methods:{
    // deprecated
    async signin(e){
      e.preventDefault();

      const user = {
        email: this.email,
        password: this.password
      }

      await axios.post(`${this.baseURL}user/signin`, user)
        .then((res) =>{
          localStorage.setItem('token', res.data.token)
          sweetalert({
            text: 'Login successful',
            icon: 'success'
          })
          this.$emit("fetchData")
          this.$router.push({name: 'Home'})
        })
        .catch(err => console.log(err))
    }
  }
}
</script>

<style scoped>
.btn-primary{
  background-color: #f0c14b;
  color: black;
}

@media screen {
  #signin-div{
    width: 40%;
  }
}
</style>