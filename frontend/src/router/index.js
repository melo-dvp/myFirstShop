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
import Success from '../views/payment/Succes.vue'
import Failed from '../views/payment/Failed.vue'
import Checkout from '../views/Checkout/Checkout.vue'

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
    component: Admin
  },

  {
    path: '/admin/category/add',
    name: 'AddCategory',
    component: AddCategory
  },

  {
    path: '/admin/category',
    name: 'Category',
    component: Category
  },

  {
    path: '/admin/category/:id',
    name: 'EditCategory',
    component: EditCategory
  },

  {
    path: '/admin/product',
    name: 'Product',
    component: Product
  },

  {
    path: '/admin/product/add',
    name: 'AddProduct',
    component: AddProduct
  },

  {
    path: '/admin/product/:id',
    name: 'EditProduct',
    component: EditProduct
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
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
