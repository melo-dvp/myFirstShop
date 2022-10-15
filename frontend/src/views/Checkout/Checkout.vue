<template>
  <div class="div_class">
    <h3 class="mt-3">You will be redirected to payment page</h3>
    <div class="alert alert-primary">
      While making payment use card number 4242 4242 4242 4242 and enter random date and cvv (3 digit)
    </div>
    <button class="btn btn-primary" @click="goToCheckout">Make Payment</button>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: "Checkout",

  props:['baseURL'],

  data(){
    return{
      stripeAPIToken: "pk_test_51LDrDeGIB2TGYGGKukxLFYt5ZzJoMFcAM8VwZq69kL4rRvsHRORjcS3FSBwPEJ23UdrCNPHkX2XRW4RHZlbuTa1E00ljQ9Upnd",
      stripe: "",
      checkoutArray: [],
    }
  },

  mounted(){
    this.stripe = window.Stripe(this.stripeAPIToken);
    this.getAllItems()
  },

  computed: {
    cartItems(){
      return this.$store.getters["cart/cartItems"]
    },
  },

  methods: {

    getAllItems(){
      for(let item of this.cartItems){
        this.checkoutArray.push({
          price: item.product.price,
          quantity: item.quantity,
          productName: item.product.name,
          productId: item.id
        })
      }
    },

    goToCheckout(){
      axios.post(`${this.baseURL}order/create-checkout-session`, this.checkoutArray)
        .then(res => {
          localStorage.setItem('sessionId', res.data.sessionId)
          this.stripe.redirectToCheckout({sessionId: res.data.sessionId})
        }).catch(err => console.log(err))
    }
  }
}
</script>

<style scoped>
.alert{
  width: 50%;
}

.div_class{
  margin-top: 5%;
  margin-left: 30%;
}

</style>