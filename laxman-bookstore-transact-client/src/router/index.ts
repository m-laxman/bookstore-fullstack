import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import CategoryView from '@/views/CategoryView.vue'
import CartView from '@/views/CartView.vue'
import CheckoutView from '@/views/CheckoutView.vue'
import ConfirmationView from '@/views/ConfirmationView.vue'
import NotFoundView from '@/views/NotFoundView.vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
      alias: ['/home', '/index.html']

    },
    {
      path: '/category/:name',
      name: 'category-view',
      component: CategoryView,
      props: true
    },
    {
      path: '/category',
      redirect: '/category/Thriller'
    },
    {
      path: '/cart',
      name: 'cart-view',
      component: CartView
    },
    {
      path: '/checkout',
      name: 'checkout-view',
      component: CheckoutView
    },
    {
      path: '/confirmation',
      name: 'confirmation-view',
      component: ConfirmationView
    },
    {
      path: "/:pathMatch(.*)*",
      name: "not-found",
      component: NotFoundView,
    },
  ]
})

export default router
