import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import AddCategory from '../views/Category/AddCategory.vue'
import Category from '../views/Category/Category.vue'
import EditCategory from '../views/Category/EditCategory.vue'
import Admin from '../views/Admin.vue'
import Product from '../views/Product/Product.vue'
import AddProduct from '../views/Product/AddProduct.vue'
import EditProduct from '../views/Product/EditProduct.vue'
import ShowDetails from '../views/Product/ShowDetails.vue'
import WishList from '../views/Product/WishList.vue'
import ListProducts from '../views/Category/ListProducts.vue'
import Signup from '../views/Signup.vue'
import Signin from '../views/Signin.vue'
import Cart from '../views/Cart.vue'
import SearchUser from '../views/Admin/SearchUser.vue'
import Success from '../views/payment/Succes.vue'
import Failed from '../views/payment/Failed.vue'
import Checkout from '../views/Checkout/Checkout.vue'
import Unauthorized from '../views/Unauthorized.vue'

import store from '../store'

const ROLE_ADMIN = "admin"

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },

  {
    path: '/admin',
    name: 'Admin',
    component: Admin,
    meta:{
      isAuthenticated: true
    }
  },

  {
    path: '/admin/category/add',
    name: 'AddCategory',
    component: AddCategory,
    meta:{
      isAuthenticated: true
    }
  },

  {
    path: '/admin/category',
    name: 'Category',
    component: Category,
    meta:{
      isAuthenticated: true
    }
  },

  {
    path: '/admin/category/:id',
    name: 'EditCategory',
    component: EditCategory,
    meta:{
      isAuthenticated: true
    }
  },

  {
    path: '/admin/product',
    name: 'Product',
    component: Product,
    meta:{
      isAuthenticated: true
    }
  },

  {
    path: '/admin/product/add',
    name: 'AddProduct',
    component: AddProduct,
    meta:{
      isAuthenticated: true
    }
  },

  {
    path: '/admin/product/:id',
    name: 'EditProduct',
    component: EditProduct,
    meta:{
      isAuthenticated: true
    }
  },

  {
    path: '/product/show/:id',
    name: 'ShowDetails',
    component: ShowDetails
  },

  {
    path: '/category/show/:id',
    name: 'ListProducts',
    component: ListProducts
  },

  {
    path: '/signup',
    name: 'Signup',
    component: Signup
  },

  {
    path: '/signin',
    name: 'Signin',
    component: Signin
  },

  {
    path: '/wishlist',
    name: 'WishList',
    component: WishList
  },

  {
    path: '/cart',
    name: 'Cart',
    component: Cart
  },

  {
    path: '/searchUser',
    name: 'SearchUser',
    component: SearchUser
  },

  {
    path: '/payment/success',
    name: 'PaymentSuccess',
    component: Success
  },

  {
    path: '/payment/failed',
    name: 'PaymentFailed',
    component: Failed
  },

  {
    path: '/checkout',
    name: 'Checkout',
    component: Checkout
  },

  {
    path: '/unauthorized',
    name: 'Unauthorized',
    component: Unauthorized
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to, from, next) => {
  const keycloak = store.state.keycloak.keycloak
  const basePath = window.location.toString()
  const token = localStorage.getItem("keycloakToken")
  if(keycloak.authenticated){
    localStorage.setItem("keycloakToken", keycloak.token)
  }else{
    if(token !== null){
      keycloak.login({redirectUri: basePath})
    }
  }
  if(keycloak.isTokenExpired){
    keycloak.updateToken(70)
      .then(() => {
        localStorage.setItem("keycloakToken", keycloak.token)
      })
      .catch((err => {
        console.log(err)
      }))
  }
  if(to.meta.isAuthenticated){
    if(!keycloak.authenticated){
      keycloak.login({redirectUri: basePath})
    }else if(keycloak.realmAccess.roles.includes(ROLE_ADMIN)){
      store.state.keycloak.jwt = { 
        headers: {
          'Authorization': 'Bearer ' + keycloak.token
        },
        withCredentials: true
      }
      localStorage.setItem("keycloakToken", keycloak.token)
      keycloak.updateToken(70)
        .then(() => {
          localStorage.setItem("keycloakToken", keycloak.token)
          next()
        })
        .catch((err => {
          console.log(err)
        }))
    }else {
      next({name: 'Unauthorized'})
    }
  }else{
    next()
  }
})
  


export default router
