<template>
    <div class="example-drag">
      <div class="upload">
        <ul v-if="files.length">
          <li v-for="file in files" :key="file.id">
            <span class="mr-3">
              <img class="image-thumb" :src="file.thumb" alt="">
            </span>
            <span>{{file.name}}</span>
            <span v-if="file.error">{{file.error}}</span>
            <span v-else-if="file.success" class="cloud-arrow-up ml-3">
              <font-awesome-icon icon="cloud-arrow-up" />
            </span>
            <span v-else-if="!file.success" class="cloud-arrow-down ml-3">
              <font-awesome-icon icon="cloud-arrow-down" />
            </span>
            <span v-else></span>
          </li>
        </ul>
        <ul v-else>
          <td colspan="7">
            <div class="text-center p-5">
              Ziehe die Bild-Datei in das Feld oder wähle es aus
            </div>
          </td>
        </ul>
  
        <div v-show="$refs.upload && $refs.upload.dropActive" class="drop-active">
          <h3>Lassen Sie zum Hochladen, die Datei los!</h3>
        </div>
  
        <div class="example-btn">
          <file-upload
            class="btn btn-primary"
            :multiple="false"
            :drop="true"
            :drop-directory="true"
            v-model="files"
            ref="upload">
            <i class="fa fa-plus"></i>
            Bild auswählen
          </file-upload>
          <button 
            type="button" 
            class="btn btn-success btn-right"
            :disabled="this.files.length === 0"
            v-if="!$refs.upload || !$refs.upload.active" @click.prevent="$refs.upload.active = true"
            @click="saveImage()"
          >
            <i class="fa fa-arrow-up" aria-hidden="true"></i>
            Bild Hochladen
          </button>
          <button 
            type="button" 
            class="btn btn-danger btn-right"  
            v-else @click.prevent="$refs.upload.active = false"
          >
            <i class="fa fa-stop" aria-hidden="true"></i>
            Hochladen Abbrechen
          </button>
        </div>
      </div>
    </div>
  </template>

<script>
import FileUpload from 'vue-upload-component'
const sweetalert = require("sweetalert");

export default {
    name: "FileUploadDragDrop",

    components: {
        FileUpload,
    },

    props: ['savedImage'],

    data() {
      return {
        files: [],
      }
    },

    mounted(){
      if(this.savedImage){
        let file = {
          active: true,
          success: true,
          file: this.savedImage,
          name: this.savedImage.name
        }
        this.files.push(file)
      }
    },

    methods:{
      saveImage(){
        if(this.files[0].file.size > 1000000){
          sweetalert({
            text: `Maximale Bildgröße überschritten`,
            icon: 'warning'
          })
          this.files = []
          return
        }
        this.addThumb()
        this.files[0].success = true
        this.$emit("uploadImage", this.files)
      },

      addThumb(){
        let URL = (window.URL || window.webkitURL)
        if (URL) {
          this.files[0].thumb = URL.createObjectURL(this.files[0].file)
        }
      }
    }
}
</script>


  <style>
  .example-drag label.btn {
    margin-bottom: 0;
    margin-right: 1rem;
  }
  .example-drag .drop-active {
    top: 0;
    bottom: 0;
    right: 0;
    left: 0;
    position: fixed;
    z-index: 9999;
    opacity: .6;
    text-align: center;
    background: #000;
  }
  .example-drag .drop-active h3 {
    margin: -.5em 0 0;
    position: absolute;
    top: 50%;
    left: 0;
    right: 0;
    -webkit-transform: translateY(-50%);
    -ms-transform: translateY(-50%);
    transform: translateY(-50%);
    font-size: 40px;
    color: #fff;
    padding: 0;
  }

  .btn-right{
    float: right;
  }

  .cloud-arrow-up{
    color: green;
  }

  .cloud-arrow-down{
    color: red;
  }

  .image-thumb {
    max-width: 4em;
    max-height: 4em;
  }
  </style>