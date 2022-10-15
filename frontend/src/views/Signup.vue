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
          <h2 class="pt-4 pl-4">Benutzer erstellen</h2>
          <form @submit="signup" class="pt-4 pl-4 pr-4">
            <div class="form-group">
              <label for="Email">Benutzername</label>
              <input v-model="username" type="text" class="form-control" required>
            </div>
            <div class="form-group">
              <label for="Email">E-Mail</label>
              <input v-model="email" type="email" class="form-control" required>
            </div>
            <div class="form-row">
              <div class="col">
                <div class="form-group">
                  <label>Vorname</label>
                  <input v-model="firstName" type="text" class="form-control" required>
                </div>
              </div>
              <div class="col">
                <div class="form-group">
                  <label>Nachname</label>
                  <input v-model="lastName" type="text" class="form-control" required>
                </div>
              </div>
            </div>
            <div class="form-group">
              <label for="Password">Passwort</label>
              <input v-model="password" type="password" class="form-control" required>
            </div>
            <div class="form-group">
              <label for="Password">Passwort wiederholen</label>
              <input v-model="passwordConfirm" type="password" class="form-control" required>
            </div>

            <button class="btn btn-primary mt-2">Benutzer erstellen</button>
          </form>
        </div>
      </div>
    </div>
    
  </div>
</template>

<script>
const sweetalert = require("sweetalert");
import { keycloakClient } from '../api/keycloakApi'

export default {
  name: "Signup",

  data(){
    return{
      username: null,
      email: null,
      firstName: null,
      lastName: null,
      password: null,
      passwordConfirm: null,
      token: null
    }
  },

  mounted(){
    this.token = localStorage.getItem("keycloakToken")
    if(this.token){
      sweetalert({
        text: 'Melde dich vorher ab, um einen neuen Benutzer zu erstellen',
        icon: 'error'
      })
      this.$router.push({name: 'Home'})
    }
  },

  methods:{
    async signup(e){
      e.preventDefault();
      if(this.password === this.passwordConfirm){
        // call signup api
        const user = {
          userName: this.username,
          emailId: this.email,
          firstName : this.firstName,
          lastName: this.lastName,
          password: this.password
        }

        await keycloakClient.add(user).then(() => {
          this.$router.replace('/')
        }).catch(err => {
          keycloakClient.errorHandling(err.response)
          this.password = ""
          this.passwordConfirm = ""
        })
      }else{
        sweetalert({
          text: 'Beide Passwörter stimmen nicht überein',
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