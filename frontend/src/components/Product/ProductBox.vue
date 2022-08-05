<template>
  <div class="card w-100 h-100">
    <img :src="product.imageUrl" class="card-img-top" alt="Card image cap" height="220" width="100">
    <div class="card-body">
      <router-link :to="{name: 'ShowDetails', params: {id: product.id}}">
        <h5 class="card-title">{{ product.name }}</h5>
      </router-link>
      <p class="card-text">{{ product.description }}</p>
      <p class="card-text">${{ product.price }}</p>
      <router-link :to="{name: 'EditProduct', params: {id: product.id}}" v-show="$route.name == 'Product'">
        <button class="btn btn-primary">Edit</button>
      </router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: "ProductBox",

  props: ["product"],
}
</script>

<style scoped>
.card-img-top{
  object-fit: cover;
}

a{
  text-decoration: none;
}

.card-title{
  color: #484848;
}
</style>