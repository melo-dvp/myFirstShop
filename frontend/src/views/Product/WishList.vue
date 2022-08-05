<template>
  <div class="container mt-3">
    <div class="row">
      <div class="col-12 text-center">
        <h4 class="pt-3">Your WishList</h4>
      </div>
    </div>
    <div class="row mt-3">
      <div 
        class="col-xl-4 col-md-6 col-12 pt-3 justify-content-around d-flex" 
        v-for="product of products" 
        :key="product.id"
      >
        <ProductBox :product="product"/>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import ProductBox from '../../components/Product/ProductBox.vue'

export default {
  name: "WishList",

  components: {
    ProductBox
  },

  props: ['baseURL'],

  data(){
    return{
      token: "",
      products: []
    }
  },

  mounted(){
    this.token = localStorage.getItem("token")
    this.fetchWishList()
  },

  methods: {
    async fetchWishList(){
      await axios.get(`${this.baseURL}wishlist/${this.token}`).then((res)=> {
        this.products = res.data
      }).catch(err => console.log(err))
    }
  }
}
</script>

<style scoped>

</style>