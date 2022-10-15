<template>
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <!-- Navbar content -->
    <!--    Logo-->
    <router-link class="navbar-brand" :to="{ name: 'Home' }">
      <img id="logo" :src="require('../assets/logo2.png')" />
    </router-link>
    <!--    Burger Button-->
    <button
      class="navbar-toggler"
      type="button"
      data-toggle="collapse"
      data-target="#navbarSupportedContent"
      aria-controls="navbarSupportedContent"
      aria-expanded="false"
      aria-label="Toggle navigation"
    >
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <!--      Search Bar-->
      <form class="form-inline ml-auto mr-auto">
        <div class="input-group">
          <input
            size="100"
            type="text"
            class="form-control"
            placeholder="Search Items"
            aria-label="Username"
            aria-describedby="basic-addon1"
          />
          <div class="input-group-prepend">
            <span class="input-group-text" id="search-button-navbar">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="16"
                height="16"
                fill="currentColor"
                class="bi bi-search"
                viewBox="0 0 16 16"
              >
                <path
                  d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"
                />
              </svg>
            </span>
          </div>
        </div>
      </form>
      <ul class="navbar-nav ml-auto" style="height: 50px">
        <li class="nav-item dropdown">
          <a 
            href="#" 
            id="navbarAccount" 
            class="nav-link dropdown-toggle"
            data-toggle="dropdown"
          >
            Account
          </a>
          <div class="dropdown-menu" aria-labelledby="navbarAccount" :style="!token ? 'padding-top: 0px; position: initial;' : ''">
            <router-link :to="{ name: 'Signup' }" class="dropdown-item" v-if="!token">Benutzer erstellen</router-link>
            <a href="#" class="dropdown-item" v-if="!token" @click="signinKeycloak">Anmelden</a>
            <router-link 
              :to="{ name: 'Admin' }" 
              class="dropdown-item"
              v-if="hasAdminRole"
            >
              Admin
            </router-link>
            <router-link :to="{ name: 'WishList' }" class="dropdown-item" v-if="token">Wishlist</router-link>
            <a href="#" class="dropdown-item" v-if="token" @click="signoutKeycloak">Abmelden</a>
          </div>
        </li>
        <li class="nav-item" v-if="token" style="padding-bottom: 0px">
          <div id="cart" style="position: relative">
            <span id="nav-cart-count">
              {{ cartCount }}
            </span>
            <router-link :to="{ name: 'Cart' }" class="text-light">
              <i class="fa fa-shopping-cart" style="font-size: 36px"></i>
            </router-link>
          </div>
        </li>
        <li class="nav-item dropdown" v-if="token">
          <a 
            href="#" 
            id="navbarAccount" 
            class="nav-link dropdown-toggle"
            data-toggle="dropdown"
            style="position: relative; padding-top: 0px"
          >
            <span class="account-initials">{{ accountInitials }}</span>
            <div 
              class="dropdown-menu dropdown-menu-right" 
              aria-labelledby="navbarAccount" 
              style="padding-left: 30px; padding-right: 30px;"
            >
              <div>
                <i class="bi bi-person" style="font-size: 20px; padding-left:30px; margin-right: 5px;"></i>
                <span v-if="hasAdminRole" style="font-size: 12px; color: green">(admin)</span>
                <br>
                <span style="padding-left: 20px">{{ user.firstname }} {{ user.lastname }} </span>
              </div>
              <span style="font-size: 10px">{{ user.email }}</span>
            </div>
          </a>
        </li>
      </ul>
    </div>
  </nav>
</template>
<script>
import VueJwtDecode from 'vue-jwt-decode'
import { keycloakClient } from '../api/keycloakApi';

export default {
  name: "Navbar",

  props: ['cartCount'],

  data(){
    return{
      token: null,
      keycloakToken: null,
    }
  },

  mounted(){
    setTimeout(()=>{
      this.token = localStorage.getItem("keycloakToken")
      if(this.token){
        this.setUser()
      }
    }, 100)
  },

  computed:{
    user(){
      return this.$store.getters["keycloak/user"]
    },

    hasAdminRole(){
      return this.$store.getters["keycloak/adminRole"]
    },

    accountInitials(){
      let initials = ""
      if(this.token){
        initials = this.user.firstname[0] + this.user.lastname[0]
      }
      return initials
    },
  },

  methods: {
    signoutKeycloak(){
      this.keycloakToken = null
      localStorage.removeItem('keycloakToken')
      const basePath = process.env.VUE_APP_FRONTEND_BASE_URL
      this.$keycloak.logout({redirectUri: basePath})
    },
    signinKeycloak(){
      const basePath = window.location.toString()
      this.$keycloak.login({redirectUri: basePath})
    },
    async setUser(){
      const decode_token = VueJwtDecode.decode(this.token)
      this.keycloakToken = this.token
      this.$store.dispatch("keycloak/storeRoles", decode_token.realm_access.roles)
      await keycloakClient.getUser(decode_token.preferred_username)
    }
  },

};
</script>
<style scoped>
#logo {
  width: 80px;
  margin-left: 20px;
  margin-right: 20px;
}
a {
  color: white;
}

.nav-link{
  color: white !important;
}

.dropdown-item{
  color: black;
}

#search-button-navbar{
  background-color: #febd69;
  border-color: #febd69;
  border-top-right-radius: 2px;
  border-bottom-right-radius: 2px;
}

#nav-cart-count{
  background-color: red;
  color: white;
  border-radius: 50%;
  height: 15px;
  width: 15px;
  font-size: 15px;
  align-items: center;
  display: flex;
  justify-content: center;
  position: absolute;
  margin-left: 10px;
}

.bg-dark{
  background-color: rgb(104, 27, 3) !important;
}

.dropdown-toggle::after {
    display: none !important;
    width: 0;
    height: 0;
    margin-left: 0.255em;
    vertical-align: 0.255em;
    content: "";
    border-top: 0.3em solid;
    border-right: 0.3em solid transparent;
    border-bottom: 0;
    border-left: 0.3em solid transparent;
}

.account-initials{
  font-size: 28px; 
  margin-left: 10px;
  border-radius: 50%;
  border: rgb(246, 11, 11) solid 1.5px;
  padding: 5px;
  text-transform: uppercase;
}

</style>