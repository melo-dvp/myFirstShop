<template>
  <div class="text-center">
    <h1>Success</h1>
  </div>
</template>

<script>
import axios from "axios"

export default {
  name: "Success",

  props: ["baseURL"],

  data(){
    return{
      token: null
    }
  },

  mounted(){
    this.token = localStorage.getItem("token")
    this.deleteAllCarts()
  },

  methods: {
    async deleteAllCarts(){
      await axios.delete(`${this.baseURL}cart/deleteAll?token=${this.token}`)
    }
  }
}
</script>

<style scoped>

</style>