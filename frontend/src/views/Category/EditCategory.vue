<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h4 class="pt-3">Edit Category</h4>
      </div>
    </div>
    <div class="row">
      <div class="col-3"></div>
      <div class="col-6">
        <form v-if="category">
          <div class="form-group">
            <label>Category Name</label>
            <input type="text" class="form-control" v-model="category.categoryName" required/>
          </div>
          <div class="form-group">
            <label>Description</label>
            <input type="text" class="form-control" v-model="category.description" required/>
          </div>
          <div class="form-group">
            <label>Image URL</label>
            <input type="text" class="form-control" v-model="category.imageUrl" required/>
          </div>
          <button type="button" class="btn btn-primary" @click="editCategory">Submit</button>
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
  name: "EditCategory",

  data(){
    return{
      id: null,
      category: null
    }
  },

  props: ["baseURL", "categories"],

  methods:{
    async editCategory(){
      await axios({
        method: 'post',
        url: `${this.baseURL}category/update/${this.id}`,
        data: JSON.stringify(this.category),
        headers: {
          'Content-Type': 'application/json'
        }
      }).then(()=>{
        this.$emit("fetchData")
        sweetalert({
          text: `Category '${this.category.categoryName}' updated successfully`,
          icon: 'success'
        })
        this.$router.push({name: 'Category'})
      }).catch(err => {
        console.log(err)
      })
    }
  },

  mounted(){
    this.id = this.$route.params.id
    this.category = this.categories.find(category => category.id == this.id)
  }
}
</script>

<style scoped>

</style>