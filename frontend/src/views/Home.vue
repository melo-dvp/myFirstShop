<template>
  <div id="home">
    <div id="background-div" class="page-holder bg-cover">
      <div class="container py-5">
        <header class="text-left text-white py-5">
          <h3 class="mb-4" id="heading">
            <a href="#start-shopping" class="bg-white px-2 py-2 rounded">Start shopping</a>
          </h3>
          <p class="lead mb-0 bg-dark p-1 rounded">Demo Online Shop</p>
        </header>
      </div>
      <hr />
    </div>

    <div class="container mt-5">
      <div class="row">
        <div class="col-12 text-center">
          <h2 class="pt-3">Top Produkte</h2>
        </div>
      </div>
      <div class="row">
        <div
          v-for="index in productSize"
          :key="index"
          class="col-md-6 col-xl-4 col-12 pt-3 justify-content-around d-flex"
        >
          <ProductBox :product="products[index-1]"/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ProductBox from '../components/Product/ProductBox.vue';
import { productClient } from '../api/productApi';

export default {
  name: 'Home',
  components: {
    ProductBox
  },

  data(){
    return{
      categorySize: 0,
      productSize: 0,
    }
  },

  computed: {
    products(){
      return this.$store.getters["product/products"]
    }
  },

  async mounted(){
    await productClient.getAll()
 
    if(this.products){
      this.productSize = Math.min(8, this.products.length);
    }
  },
}
</script>

<style scoped>
.page-holder{
  min-height: 100vh;
}

.bg-cover{
  background-size: cover !important;
}

#background-div{
  background: url("../assets/home.png");
}

#heading{
  font-weight: 400;
}
</style>