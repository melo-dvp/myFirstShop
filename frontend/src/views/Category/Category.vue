<template>
  <div class="container">
    <div class="row">
      <div class="text-center col-12 mb-4">
        <h3 class="pt-3">Our Categories</h3>
        <button type="button" class="btn btn-outline-primary text-right" @click="newCategory">Add new Category</button>
      </div>
    </div>
    <div class="row">
      <div 
        class="col-xl-4 col-md-6 mb-5" 
        v-for="category of categories" 
        :key="category.id"
      >
        <CategoryBox :category="category"/>
      </div>
    </div>
  </div>
</template>

<script>
import CategoryBox from '../../components/Category/CategoryBox.vue'

export default {
  name: "Category",

  components: {
    CategoryBox
  },

  props: ['categories'],

  data(){
    return{
      baseURL: "http://localhost:8080",
    }
  },

  methods:{
    newCategory(){
      this.$router.push({name: 'AddCategory'})
    }
  },
}
</script>

<style scoped>
.text-right{
  float: right;
}
</style>