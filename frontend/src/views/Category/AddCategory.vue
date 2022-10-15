<template>
  <div class="container">
    <div class="row">
      <div class="text-center col-12 mb-4">
        <h3 class="pt-3">Kategorie hinzufügen</h3>
      </div>
    </div>
    <div class="row">
      <div class="col-3"></div>
      <div class="col-6">
        <form>
          <div class="form-group">
            <label>Name *</label>
            <input type="text" class="form-control" v-model="categoryName">          
          </div>
          <div class="form-group">
            <label>Beschreibung *</label>
            <textarea type="text" class="form-control" v-model="description"/>          
          </div>
          <div>
            <FileUploadDragDrop @uploadImage="onImageUpload($event)"/>
          </div>
          <div class="btn-save">
            <button type="button" class="btn btn-primary" @click="addCategory">Speichern</button>
          </div>
        </form>
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
  name: "AddCategory",

  components: {
    FileUploadDragDrop
  },

  data(){
    return{
      categoryName: "",
      description: "",
      formData: null,
      file: null
    }
  },

  methods:{
    async addCategory(){
      if(
        this.categoryName.length === 0 ||
        this.description.length === 0 ||
        this.file === null
      ){
        sweetalert({
          text: `Bitte fülle alle Pflichtfelder aus!`,
          icon: 'warning'
        })
        return
      }

      this.formData.append("categoryName", this.categoryName)
      this.formData.append("description", this.description)


      await categoryClient.create(this.formData).then(()=> {
        this.$emit("fetchData")
        this.categoryName = ''
        this.description = ''
        this.$router.push({name: 'Category'})
      })
    },

    onImageUpload(event){
      this.file = event[0].file
      this.formData = new FormData()
      this.formData.append("file", this.file)
    }
   
  }
}
</script>

<style scoped>
  .btn-save{
    display: flex;
    justify-content: center;
  }
</style>