<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h4 class="pt-3">Edit Product</h4>
      </div>
    </div>
    <div class="row">
      <div class="col-3"></div>
      <div class="col-6">
        <form v-if="product">
           <div class="form-group">
              <label> Category</label>
              <select class="form-control" v-model="product.categoryId" required>
                  <option v-for="category of categories"
                          :key="category.id"
                          :value="category.id"> {{category.categoryName}}</option>

              </select>
          </div>
          <div class="form-group">
            <label>Product Name</label>
            <input type="text" class="form-control" v-model="product.name" required/>
          </div>
          <div class="form-group">
            <label>Description</label>
            <input type="text" class="form-control" v-model="product.description" required/>
          </div>
          <div class="form-group">
            <label>Image URL</label>
            <input type="text" class="form-control" v-model="product.imageUrl" required/>
          </div>
          <div class="form-group">
            <label>Price</label>
            <input type="text" class="form-control" v-model="product.price" required/>
          </div>
          <button type="button" class="btn btn-primary" @click="editProduct">Submit</button>
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
  name: "EditProduct",

  props: ["baseURL", "products", "categories"],

  data(){
    return{
      id: null,
      product: null
    }
  },

  methods:{
    async editProduct(){
      await axios({
        method: 'post',
        url: `${this.baseURL}product/update/${this.id}`,
        data: JSON.stringify(this.product),
        headers: {
          'Content-Type': 'application/json'
        }
      }).then(()=>{
        this.$emit("fetchData")
        sweetalert({
          text: `Product '${this.product.name}' updated successfully`,
          icon: 'success'
        })
        this.$router.push({name: 'Product'})
      }).catch(err => {
        console.log(err)
      })
    }
  },

  mounted(){
    this.id = this.$route.params.id
    this.product = this.products.find(product => product.id == this.id)
  }
}
</script>

<style scoped>

</style>