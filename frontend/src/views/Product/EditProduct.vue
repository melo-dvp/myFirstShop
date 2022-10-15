<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h4 class="pt-3">Produkt bearbeiten</h4>
      </div>
    </div>
    <div class="row">
      <div class="col-3"></div>
      <div class="col-6">
        <form v-if="product">
           <div class="form-group">
              <label>Kategorie</label>
              <select class="form-control" v-model="product.categoryId" required>
                  <option v-for="category of categories"
                          :key="category.id"
                          :value="category.id"> {{category.categoryName}}</option>

              </select>
          </div>
          <div class="form-group">
            <label>Name</label>
            <input type="text" class="form-control" v-model="product.name" required/>
          </div>
          <div class="form-group">
            <label>Beschreibung</label>
            <input type="text" class="form-control" v-model="product.description" required/>
          </div>
          <div class="form-group">
            <label>Bild Url</label>
            <input type="text" class="form-control" v-model="product.imageUrl" required/>
          </div>
          <div class="form-group">
            <label>Preis</label>
            <input type="text" class="form-control" v-model="product.price" required/>
          </div>
        </form>
        <button type="button" class="btn btn-primary" @click="editProduct">Aktuallisieren</button>
        <button class="btn btn-danger" style="float: right;" data-toggle="modal" data-target="#deleteDialog"><font-awesome-icon icon="trash" /></button>
        <!-- The Modal -->
        <div class="modal" id="deleteDialog">
          <div class="modal-dialog">
            <div class="modal-content">

              <div class="modal-header" v-if="product">
                <h4 class="modal-title">Möchten Sie die Kategorie "{{ product.name }}" wirklich löschen?</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
              </div>

              <div class="modal-footer">
                <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Abbrechen</button>
                <button type="button" class="btn btn-danger" data-dismiss="modal" @click="deleteProduct()">Ja</button>
              </div>

            </div>
          </div>
        </div>
      </div>
      <div class="col-3"></div>
    </div>
  </div>
</template>

<script>
import { productClient } from '../../api/productApi'

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
      await productClient.update(this.product).then(()=>{
        this.$emit("fetchData")
        this.$router.push({name: 'Product'})
      }).catch((err) => productClient.errorHandling(err.response))
    },

    async deleteProduct(){
      await productClient.delete(this.id).then(() => {
        this.$emit("fetchData")
        this.$router.push({name: 'Product'})
      }).catch((err) => {
        productClient.errorHandling(err.response)
      });
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