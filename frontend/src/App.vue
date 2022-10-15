<template>
  <Navbar :cartCount="cartCount" @resetCartCount="resetCartCount"/>
  <router-view v-if="categories && products" style="min-height: 60vh"
    :baseURL="baseURL"
    :categories="categories"
    :products="products"
    @fetchData="fetchData"
    @resetCartCount="resetCartCount"
  />
  <Footer/>
</template>

<script>
import Navbar from './components/Navbar.vue'
import Footer from './components/Footer.vue'
import VueJwtDecode from 'vue-jwt-decode'

import { categoryClient } from './api/categoryApi';
import { productClient } from './api/productApi';
import { cartClient } from './api/cartApi';

export default {
  name: 'App',

  components:{
    Navbar,
    Footer
  },

  data(){
    return{
      baseURL: process.env.VUE_APP_BACKEND_BASE_URL,
      token: null,
      username: null,
    }
  },

  mounted(){
    setTimeout(()=>{
      this.token = localStorage.getItem("keycloakToken")
      if(this.token){
        const decode_token = VueJwtDecode.decode(this.token)
        this.username = decode_token.preferred_username
      }
      this.fetchData();
    }, 100)
  },

  computed: {
    categories(){
      return this.$store.getters["category/categories"]
    },

    products(){
      return this.$store.getters["product/products"]
    },

    cartCount(){
      const cartItems = this.$store.getters["cart/cartItems"]
      return cartItems.length
    },
  },

  methods:{
    async fetchData(){
      await categoryClient.getAll()

      await productClient.getAll()

      // fetch cart item if token is present
      if(this.token){
        await cartClient.getAllCarts(this.token).catch(err => {
          this.cartClient.errorHandling(err.response)
        })
      }
    },

    resetCartCount(){
      this.cartCount = 0
    }
  }
}
</script>

<style>
html{
  overflow-y: scroll;
}
</style>
