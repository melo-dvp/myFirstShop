<template>
  <div class="card w-100 h-100">
    <img :src="category.imageUrl" class="card-img-top" alt="Card image cap" height="220" width="100">
    <div class="card-body">
      <router-link :to="{name: 'ListProducts', params: {id: category.id}}">
        <h5 class="card-title">{{ category.categoryName }}</h5>      
      </router-link>
      <p class="card-text">{{ category.description }}</p>
      <router-link :to="{name: 'EditCategory', params: {id: category.id}}" v-show="$route.name == 'Category'">
        <button class="btn btn-primary">Edit</button>
      </router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: "CategoryBox",

  props: ["category"],

  methods:{}
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