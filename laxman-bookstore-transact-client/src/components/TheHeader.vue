<script setup lang="ts">
import TheHeaderDropdown from '@/components/TheHeaderDropdown.vue'
import TheHeaderSearchBar from "@/components/TheHeaderSearchBar.vue";

import { useCartStore } from '@/stores/cart';
const cartStore = useCartStore();
</script>

<style scoped>
header {
  background: var(--secondary-background-color);
  display: flex;
  justify-content: space-between;
  padding: 1em;
  gap: 1em;
}

main {
  display: flex;
  flex-direction: column;
}

.bookstore-logo {
  display: flex;
  text-align: left;
}

search-bar-and-header-dropdown {
  margin-bottom: 1.75em;
}

.cart-and-login {
  text-align: right;
}

.text-logo {
  font-family: var(--title-font-family);
  text-decoration: none;
  color: var(--title-color);
  font-size: 38px;
}

.user {
  margin-left: 0.35em;
}

@media (max-width: 800px) {
  header {
    flex-direction: column;
  }
}

.cart {
  width: 80px;
}
</style>

<template>
  <header class="container">
    <section class="bookstore-logo">
      <router-link to="/">
        <img src="/site-images/logo_2.png" alt="BookEra" width="100" height="auto" />
      </router-link>
      <h1>
        <router-link to="/" class="text-logo">BookEra</router-link>
      </h1>

    </section>

    <section class="search-bar-and-header-dropdown">

      <the-header-search-bar></the-header-search-bar>
      <the-header-dropdown></the-header-dropdown>


    </section>

    <section class="cart-and-login">
      <router-link to="/cart">
        <button class="button cart"><i class="fa-solid fa-cart-shopping"></i> {{ cartStore.count }}</button>
      </router-link>
      <button class="button user"><i class="fa-solid fa-user"></i> Laxman</button>
    </section>
  </header>
</template>
