<template>
  <Navbar :cartCount="cartCount" @resetCartCount="resetCartCount"/>
  <router-view v-if="categories && products" style="min-height: 60vh"
    :baseURL="baseURL"
    :categories="categories"
    :products="products"
    @fetchData="fetchData"
  />
  <Footer/>
</template>

<script>
import axios from 'axios';
import Navbar from './components/Navbar.vue'
import Footer from './components/Footer.vue'

export default {
  name: 'App',

  components:{
    Navbar,
    Footer
  },

  data(){
    return{
      baseURL: "http://localhost:8080/",
      products: null,
      categories: null,
      token: null,
      cartCount: 0
    }
  },

  mounted(){
    this.token = localStorage.getItem("token")
    this.fetchData();
  },

  methods:{
    async fetchData(){
      await axios.get(this.baseURL + "category/getAll")
      .then(res =>{
        this.categories = res.data
      }).catch((err) => console.log("err", err));

      await axios.get(this.baseURL + "product/getAll")
      .then(res => {
        this.products = res.data
      }).catch(err => console.log("err", err))

      // fetch cart item if token is present
      if(this.token){
        await axios.get(`${this.baseURL}cart/getAll?token=${this.token}`).then(res => {
          const result = res.data
          this.cartCount = result.cartItems.length
        }).catch(err => console.log(err))
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
