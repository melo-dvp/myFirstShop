<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h3 class="pt-3">
          Shopping Cart
        </h3>
      </div>
    </div>
    <div 
      class="row mt-2 pt-3 justify-content-around"
      v-for="cartItem in cartItems"
      :key="cartItem.id"
    >
      <div class="col-2"></div>
      <div class="col-md-3 embed-responsive embed-responsive-16by9">
        <img 
          :src="cartItem.product.imageUrl" 
          alt="" 
          class="w-100 card-image-top embed-responsive-item"
          style="object-fit: cover"
        >
      </div>
      <div class="col-md-5 px-3">
        <div class="card-block px-3">
          <h6 class="card-title">
            <router-link 
              :to="{ name: 'ShowDetails', params: { id: cartItem.product.id } }"
            >
              {{ cartItem.product.name }}
            </router-link>
          </h6>
          <p class="mb-0 font-weight-bold" id="item-price">
            $ {{ cartItem.product.price }} per unit
          </p>
          <p class="mb-0" style="float: left">
            Quantity: {{ cartItem.quantity }}
          </p>
        </div>
        <p class="mb-0">
          Total: <span class="font-weight-bold">
            $ {{ cartItem.product.price * cartItem.quantity }}
          </span>
        </p>
        <button class="btn btn-danger mt-3" @click="deleteProduct(cartItem.id)">delete Product</button>
      </div>
      <div class="col-2"></div>
      <div class="col-12"><hr/></div>
    </div>
    <div class="total-cost pt-2 text-right">
      <h5>Total: $ {{ totalCost }}</h5>
      <button type="button" class="btn btn-primary confirm" @click="checkout">Confirm order</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
const sweetalert = require("sweetalert");

export default {
  name: "Cart",

  props: ['baseURL'],

  data(){
    return{
      cartItems: [],
      token: null,
      totalCost: 0
    }
  },

  mounted(){
    this.token = localStorage.getItem("token")
    this.listCartItems()
  },

  methods:{
    async listCartItems(){
      if(!this.token){
        sweetalert({
          text: "please log in to see your items",
          icon: "error"
        })
        return
      }
      await axios.get(`${this.baseURL}cart/getAll?token=${this.token}`).then(res => {
        const result = res.data
        this.cartItems = result.cartItems
        this.totalCost = result.totalCost
      }).catch(err => console.log(err))
    },

    async deleteProduct(id){
      if(!this.token){
        sweetalert({
          text: "please log in to see your items",
          icon: "error"
        })
        return
      }
      await axios.delete(`${this.baseURL}cart/delete/${id}?token=${this.token}`).then(()=>{
        sweetalert({
          text: "Product deleted successfully",
          icon: "success"
        })
        this.listCartItems()
        this.$emit("fetchData")
      }).catch(err => console.log(err))
    },

    checkout(){
      this.$router.push({name: "Checkout"})
    }
  }
}
</script>

<style scoped>
h4, h5 {
  color: #484848;
  font-size: 700;
}
</style>