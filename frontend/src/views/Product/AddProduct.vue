<template>
  <div class="container">
    <div class="row">
      <div class="text-center col-12 mb-4">
        <h3 class="pt-3">Add New Product</h3>
      </div>
    </div>
    <div class="row">
      <div class="col-3"></div>
      <div class="col-6">
        <form>
          <div class="form-group">
              <label>Category</label>
              <select class="form-control" v-model="categoryId" required>
                  <option v-for="category in categories" :key="category.id"
                          :value="category.id">{{ category.categoryName }}</option>
              </select>
          </div>
          <div class="form-group">
            <label>Name</label>
            <input type="text" class="form-control" v-model="productName">          
          </div>
          <div class="form-group">
            <label>Description</label>
            <textarea type="text" class="form-control" v-model="description"/>          
          </div>
          <div class="form-group">
            <label>Image</label>
            <input type="text" class="form-control" v-model="imageUrl">          
          </div>
          <div class="form-group">
            <label>Price</label>
            <input type="number" class="form-control" v-model="price"/>
          </div>
          <button type="button" class="btn btn-primary" @click="addProduct">Submit</button>
        </form>
      </div>
      <div class="col-3"></div>
    </div>
  </div>
</template>

<script>
const axios = require("axios");
const sweetalert = require("sweetalert");

export default {
  name: "AddProduct",

  props: ['categories'],

  data(){
    return{
      productName: "",
      description: "",
      imageUrl: "",
      price: "",
      categoryId: 0
    }
  },

  methods: {
    addProduct(){
      const newProduct = {
        name: this.productName,
        description: this.description,
        imageUrl: this.imageUrl,
        price: this.price,
        categoryId: this.categoryId
      }

      const baseURL = "http://localhost:8080"

      axios({
        method: 'post',
        url: `${baseURL}/product/add`,
        data: JSON.stringify(newProduct),
        headers: {
          'Content-Type': 'application/json'
        }
      }).then(()=>{
        this.$emit("fetchData")
        sweetalert({
          text: 'Product added successfully',
          icon: 'success'
        })
        this.productName = "",
        this.description = "",
        this.imageUrl = "",
        this.price = "",
        this.categoryId = 0
        this.$router.push({name: 'Product'})
      }).catch(err => {
        console.log(err)
      })
    }
  }
}
</script>


<style scoped>

</style>