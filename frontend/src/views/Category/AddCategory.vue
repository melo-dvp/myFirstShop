<template>
  <div class="container">
    <div class="row">
      <div class="text-center col-12 mb-4">
        <h3 class="pt-3">Add Category</h3>
      </div>
    </div>
    <div class="row">
      <div class="col-3"></div>
      <div class="col-6">
        <form>
          <div class="form-group">
            <label>Name</label>
            <input type="text" class="form-control" v-model="categoryName">          
          </div>
          <div class="form-group">
            <label>Description</label>
            <textarea type="text" class="form-control" v-model="description"/>          
          </div>
          <div class="form-group">
            <label>Image</label>
            <input type="text" class="form-control" v-model="imageUrl">          
          </div>
          <button type="button" class="btn btn-primary" @click="addCategory">Submit</button>
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
  name: "AddCategory",

  data(){
    return{
      categoryName: "",
      description: "",
      imageUrl: ""
    }
  },

  methods:{
    addCategory(){
      const newCategory = {
        categoryName: this.categoryName,
        description: this.description,
        imageUrl: this.imageUrl
      }

      const baseURL = "http://localhost:8080"

      axios({
        method: 'post',
        url: `${baseURL}/category/create`,
        data: JSON.stringify(newCategory),
        headers: {
          'Content-Type': 'application/json'
        }
      }).then(()=>{
        this.$emit("fetchData")
        sweetalert({
          text: 'Category added successfully',
          icon: 'success'
        })
        this.categoryName = ''
        this.description = ''
        this.imageUrl = ''
        this.$router.push({name: 'Category'})
      }).catch(err => {
        console.log(err)
      })

    }
  }
}
</script>

<style scoped>

</style>