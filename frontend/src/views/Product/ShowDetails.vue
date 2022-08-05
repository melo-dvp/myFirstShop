<template>
  <div class="container">
    <div class="row pt-5">
      <div class="col-md-1"></div>
      <div class="col-md-4 col-12">
        <img :src="product.imageUrl" class="img-fluid">
      </div>
      <div class="col-md-6 col-12 pt-3 pt-md-0">
        <h4>{{ product.name }}</h4>
        <h6 class="category font-italic">{{ category.categoryName }}</h6>
        <h6 class="font-weight-bold">$ {{ product.price }}</h6>
        <p>
          {{ product.description }}
        </p>
        <div class="d-flex flex-row justify-content-between">
          <div class="input-group col-md-3 col-4 p-0">
            <div class="input-group-prepend">
              <span class="input-group-text">Quantity</span>
            </div>
            <input type="number" class="form-control" v-model="quantity">
          </div>
          <div class="input-group col-md-3 col-4 p-0">
            <button id="add-to-cart-button" class="btn" @click="addToCart">
              Add to Cart
            </button>
          </div>
        </div>
        <div class="feature pt-3">
          <h5><strong>Features</strong></h5>
          <ul>
            <li>Lorem ipsum dolor sit amet, consetetur sadipscing elitr</li>
            <li>sed diam nonumy eirmod tempor invidunt ut labore et dolore magna </li>
            <li>aliquyam erat, sed diam voluptua. </li>
            <li>At vero eos et accusam et justo duo dolores et ea rebum.</li>
          </ul>
        </div>
        <button 
          id="wishlist-button" 
          class="btn mr-3 p-1 py-0"
          @click="addToWishList()"
        >
          {{ wishListString }}
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
const sweetalert = require("sweetalert");

export default {
  name: 'ShowDetails',

  props: ['baseURL','products', 'categories'],

  data(){
    return{
      product: {},
      category: {},
      wishListString: "Add to wishlist",
      quantity: 1
    }
  },

  mounted(){
    this.id = this.$route.params.id;
    this.product = this.products.find((product) => product.id == this.id)
    this.category = this.categories.find((category) => category.id == this.product.categoryId)
    this.token = localStorage.getItem("token")
  },

  methods: {
    async addToWishList(){
      if(!this.token){
        // user ist not logged in
        sweetalert({
          text: "please log in to add item in wishlist",
          icon: "error"
        })
        return
      }
      // add item to wishlist
      await axios.post(`${this.baseURL}wishlist/add?token=${this.token}`, this.product)
       .then(() => {
        sweetalert({
          text: "item added successfully to wishlist",
          icon: "success"
        })
        this.wishListString = "Added to Wishlist"
       }).catch(err => console.log(err))
    },

    async addToCart(){
      if(!this.token){
        sweetalert({
          text: "please log in to add item in cart",
          icon: "error"
        })
        return
      }

      const requestBody = {
        productId : this.id,
        quantity: this.quantity
      }
      await axios.post(`${this.baseURL}cart/add?token=${this.token}`, requestBody)
        .then(()=>{
          sweetalert({
            text: "Product added in cart",
            icon: "success"
          })
          this.$emit("fetchData")
        })
        .catch(err => console.log(err))
    }
  }
}
</script>

<style scoped>
.category{
  font-weight: 400;
}

#wishlist-button{
  background-color: #b9b9b9;
}

#add-to-cart-button{
  background-color: #febd69;
}
</style>