<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h4 class="pt-3">Kategorie bearbeiten</h4>
      </div>
    </div>
    <div class="row">
      <div class="col-3"></div>
      <div class="col-6">
        <form v-if="category">
          <div class="form-group">
            <label>Name *</label>
            <input type="text" class="form-control" v-model="category.categoryName" required/>
          </div>
          <div class="form-group">
            <label>Beschreibung *</label>
            <input type="text" class="form-control" v-model="category.description" required/>
          </div>
          <div>
            <FileUploadDragDrop @uploadImage="onImageUpload($event)" :savedImage="file"/>
          </div>
        </form>
        <button type="button" class="btn btn-primary" @click="editCategory">Aktuallisieren</button>
        <button class="btn btn-danger" style="float: right;" data-toggle="modal" data-target="#deleteDialog"><font-awesome-icon icon="trash" /></button>
        <!-- The Modal -->
        <div class="modal" id="deleteDialog">
          <div class="modal-dialog">
            <div class="modal-content">

              <div class="modal-header" v-if="category">
                <h4 class="modal-title">Möchten Sie die Kategorie "{{ category.categoryName }}" wirklich löschen?</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
              </div>

              <div class="modal-footer">
                <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Abbrechen</button>
                <button type="button" class="btn btn-danger" data-dismiss="modal" @click="deleteCategory()">Ja</button>
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
import FileUploadDragDrop from '../../components/FileUpload/FileUploadDragDrop.vue'

import { categoryClient } from "../../api/categoryApi";
const sweetalert = require("sweetalert");

export default {
  name: "EditCategory",

  components: {
    FileUploadDragDrop
  },

  data(){
    return{
      id: null,
      category: null,
      formData: null,
      file: null
    }
  },

  methods:{
    async editCategory(){
      if(
        this.category.categoryName.length === 0 ||
        this.category.description.length === 0 ||
        this.file === null
      ){
        sweetalert({
          text: `Bitte fülle alle Pflichtfelder aus!`,
          icon: 'warning'
        })
        return
      }

      this.formData.append("categoryName", this.category.categoryName)
      this.formData.append("description", this.category.description)
      this.formData.append("fileId", this.category.fileUploadDto.id)

      await categoryClient.update(this.formData, this.id).then(()=>{
        this.$emit("fetchData")
        this.$router.push({name: 'Category'})
      }).catch((err) => categoryClient.errorHandling(err.response))
    },

    onImageUpload(event){
      this.file = event[0].file
      this.formData = new FormData()
      this.formData.append("file", this.file)
    },

    async deleteCategory(){
      await categoryClient.delete(this.id).then(()=>{
        this.$emit("fetchData")
        this.$router.push({name: 'Category'})
      }).catch((err) => {
        categoryClient.errorHandling(err.response)
      });
    },

    async getAllCategories(){
      await categoryClient.getAll()
    }
  },

  computed: {
    categories(){
      return this.$store.getters["category/categories"]
    }
  },

  mounted(){
    this.getAllCategories()
    this.id = this.$route.params.id
    this.category = this.categories.find(category => category.id == this.id)
    this.file = this.category.fileUploadDto
  }
}
</script>

<style scoped>

</style>