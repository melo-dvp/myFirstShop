<template>
  <div class="text-center">
    <h1>Success</h1>
  </div>
</template>

<script>
import VueJwtDecode from 'vue-jwt-decode'

import { cartClient } from "../../api/cartApi"

export default {
  name: "Success",

  props: ["baseURL"],

  data(){
    return{
      token: null,
      username: null
    }
  },

  mounted(){
    this.token = localStorage.getItem("keycloakToken")
    if(this.token){
      const decode_token = VueJwtDecode.decode(this.$keycloak.token)
      this.username = decode_token.preferred_username
    }
    this.deleteAllCarts()
  },

  methods: {
    async deleteAllCarts(){
      await cartClient.deleteAll(this.token)
    }
  }
}
</script>

<style scoped>

</style>