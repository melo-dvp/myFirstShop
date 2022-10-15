<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h3 class="pt-3">
          Warenkorb
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
            $ {{ cartItem.product.price }} pro Einheit
          </p>
          <ul class="list-style">
            <li>
              Menge: {{ cartItem.quantity }}
            </li>
            <li>
              Gesamt: <span class="font-weight-bold">
                $ {{ cartItem.product.price * cartItem.quantity }}
              </span>
            </li>
            <li>
              <button class="btn btn-danger mt-3" @click="deleteProduct(cartItem.id)" data-toggle="tooltip" title="löschen">
                <font-awesome-icon icon="trash"/>
              </button>
            </li>
          </ul>
        </div>
      </div>
      <div class="col-2"></div>
      <div class="col-12"><hr/></div>
    </div>
    <div class="total-cost pt-2 text-right">
      <h5>Gesamt: $ {{ totalCost }}</h5>
      <button type="button" class="btn btn-primary confirm" @click="checkout">Bestellung bestätigen</button>
    </div>
  </div>
</template>

<script>
const sweetalert = require("sweetalert");
import VueJwtDecode from 'vue-jwt-decode'

import { cartClient } from '../api/cartApi';

export default {
  name: "Cart",

  props: ['baseURL'],

  data(){
    return{
      token: null,
      username: null
    }
  },

  mounted(){
    setTimeout(()=>{
      this.token = localStorage.getItem("keycloakToken")
      if(this.token){
        const decode_token = VueJwtDecode.decode(this.token)
        this.username = decode_token.preferred_username
      }
      this.listCartItems()
    }, 100)
  },

  computed:{
    cartItems(){
      return this.$store.getters["cart/cartItems"]
    },
    totalCost(){
      return this.$store.getters["cart/totalCost"]
    }
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

      await cartClient.getAllCarts(this.token).catch(err => {
        this.cartClient.errorHandling(err.response)
      })
    },

    async deleteProduct(id){
      if(!this.token){
        sweetalert({
          text: "please log in to see your items",
          icon: "error"
        })
        return
      }
      await cartClient.delete(id, this.token).then(() => {
        this.listCartItems()
        this.$emit("fetchData")
      }).catch(err => cartClient.errorHandling(err.response))
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

.list-style{
  list-style: none;
  padding-left: 0px;
}
</style>