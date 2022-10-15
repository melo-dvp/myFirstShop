<template>
  <div class="container">
    <div class="row">
      <div class="text-center col-12 mb-4">
        <h3 class="pt-3">Unsere Produkte</h3>
        <button type="button" class="btn btn-outline-primary text-right" @click="newProduct">
          Neues Produkt <font-awesome-icon icon="plus" />
        </button>
      </div>
    </div>
    <div class="row">
      <div 
        class="col-xl-4 col-md-6 mb-5" 
        v-for="product of products" 
        :key="product.id"
      >
        <ProductBox :product="product"/>
      </div>
    </div>
  </div>
</template>

<script>
import ProductBox from '../../components/Product/ProductBox.vue'

export default {
  name: "Product",

  components:{
    ProductBox
  },

  props: ['products'],

  methods:{
    newProduct(){
      this.$router.push({name: 'AddProduct'})
    }
  }
}
</script>

<style scoped>
.text-right{
  float: right;
}
</style>