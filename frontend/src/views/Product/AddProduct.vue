<template>
  <div class="container">
    <div class="row">
      <div class="text-center col-12 mb-4">
        <h3 class="pt-3">Neues Produkt hinzufügen</h3>
      </div>
    </div>
    <div class="row">
      <div class="col-3"></div>
      <div class="col-6">
        <form>
          <div class="form-group">
              <label>Kategorie</label>
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
            <label>Beschreibung</label>
            <textarea type="text" class="form-control" v-model="description"/>          
          </div>
          <div class="form-group">
            <label>Bild Url</label>
            <input type="text" class="form-control" v-model="imageUrl">          
          </div>
          <div class="form-group">
            <label>Preis</label>
            <input type="number" class="form-control" v-model="price"/>
          </div>
          <button type="button" class="btn btn-primary" @click="addProduct">Speichern</button>
        </form>
      </div>
      <div class="col-3"></div>
    </div>
  </div>
</template>

<script>
import { productClient } from "../../api/productApi";

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
    async addProduct(){
      const newProduct = {
        name: this.productName,
        description: this.description,
        imageUrl: this.imageUrl,
        price: this.price,
        categoryId: this.categoryId
      }

      await productClient.add(newProduct).then(()=>{
        this.$emit("fetchData")
        this.productName = "",
        this.description = "",
        this.imageUrl = "",
        this.price = "",
        this.categoryId = 0
        this.$router.push({name: 'Product'})
      })
    }
  }
}
</script>


<style scoped>

</style>