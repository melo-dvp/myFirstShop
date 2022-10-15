<template>
  <div class="container mt-5">
    <router-link :to="{name: 'Category'}" class="mt-5">
        <button class="btn btn-primary btn-lg"> Admin Kategorien</button>
    </router-link>
    <br />
    <router-link :to="{name: 'Product'}" >
        <button class="btn btn-primary btn-lg"> Admin Produkte</button>
    </router-link>
    <br />
    <router-link :to="{name: 'SearchUser'}" >
        <button class="btn btn-primary btn-lg"> Admin User</button>
    </router-link>
  </div>
</template>
<script>
export default {
  name: "Admin",
}
</script>
<style>
.btn {
  margin-bottom: 20px;
}
</style>