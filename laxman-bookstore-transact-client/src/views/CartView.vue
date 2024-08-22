<script setup lang="ts">
import CartTable from "@/components/CartTable.vue";
import { useCartStore } from "@/stores/cart";
const cartStore = useCartStore();
</script>
<style scoped>
.mg {
  padding: 2em 0;
  text-align: center;
}
.title {
  margin-bottom: 1em;
}
</style>

<template>
  <div class="dark-background mg">
    <h1 class="title">Shopping Cart</h1>
    <cart-table></cart-table>
  </div>
</template>