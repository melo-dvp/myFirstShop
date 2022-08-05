<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center pt-3">
        <!-- Logo -->
      </div>
    </div>
    <div class="row">
      <div class="col-12 justify-content-center d-flex pt-3">
        <div id="signup-div" class="flex-item border">
          <h2 class="pt-4 pl-4">Create Account</h2>
          <form @submit="signup" class="pt-4 pl-4 pr-4">
            <div class="form-group">
              <label for="Email">Email</label>
              <input v-model="email" type="email" class="form-control" required>
            </div>
            <div class="form-row">
              <div class="col">
                <div class="form-group">
                  <label>First Name</label>
                  <input v-model="firstName" type="text" class="form-control" required>
                </div>
              </div>
              <div class="col">
                <div class="form-group">
                  <label>Last Name</label>
                  <input v-model="lastName" type="text" class="form-control" required>
                </div>
              </div>
            </div>
            <div class="form-group">
              <label for="Password">Password</label>
              <input v-model="password" type="password" class="form-control" required>
            </div>
            <div class="form-group">
              <label for="Password">Confirm password</label>
              <input v-model="passwordConfirm" type="password" class="form-control" required>
            </div>

            <button class="btn btn-primary mt-2">Create Account</button>
          </form>
        </div>
      </div>
    </div>
    
  </div>
</template>

<script>
import axios from 'axios'
const sweetalert = require("sweetalert");

export default {
  name: "Signup",

  //props: ['baseURL'],

  data(){
    return{
      email: null,
      firstName: null,
      lastName: null,
      password: null,
      passwordConfirm: null,
      baseURL: 'http://localhost:8080/'
    }
  },

  methods:{
    async signup(e){
      e.preventDefault();
      if(this.password === this.passwordConfirm){
        // call signup api
        const user = {
          email: this.email,
          firstName : this.firstName,
          lastName: this.lastName,
          password: this.password
        }

        await axios.post(`${this.baseURL}user/signup`, user)
          .then(() => {
            this.$router.replace('/')
            sweetalert({
              text: 'User signup successful, please login',
              icon: 'success'
            })
          })
          .catch(err => console.log(err))
      }else{
        sweetalert({
          text: 'password dont match',
          icon: 'error'
        })
      }
    }
  }
}
</script>

<style scoped>
.btn-primary{
  background-color: #f0c14b;
  color: #000;
}

@media screen and (min-width: 992px){
  #signup-div{
    width: 50%;
  }
}
</style>