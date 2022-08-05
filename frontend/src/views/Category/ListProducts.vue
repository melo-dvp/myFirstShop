<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h4 class="pt-3">{{ category.categoryName }}</h4>
        <h5 style="font-weight: 300; color: #686868;">{{ msg }}</h5>
      </div>
    </div>

    <div class="row">
      <div
        v-for="product of listOfProducts"
        :key="product.id"
        class="col-md-6 col-xl-4 col-12 pt-3 justify-content-around d-flex"
      >
        <ProductBox :product="product"></ProductBox>
      </div>      
    </div>
  </div>
</template>

<script>
import ProductBox from '../../components/Product/ProductBox.vue'

export default {
  name: 'ListProducts',

  components: {
    ProductBox
  },

  props: ['categories', 'products'],

  data(){
    return{
      id: null,
      category: {},
      listOfProducts: [],
      msg: ''
    }
  },

  mounted(){
    this.id = this.$route.params.id
    this.category = this.categories.find((category) => category.id == this.id)
    this.listOfProducts = this.products.filter((product) => product.categoryId == this.id)

    let lengthOfProducts = Object.keys(this.listOfProducts).length

    if(lengthOfProducts <= 0){
      this.msg = 'no products found'
    }else if(lengthOfProducts === 1){
      this.msg = `Only One Product found`
    }else{
      this.msg = lengthOfProducts + " Products found"
    }
  }
}
</script>

<style scoped>

</style>