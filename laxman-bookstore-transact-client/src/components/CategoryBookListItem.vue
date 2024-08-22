<script setup lang="ts">
import {defineProps} from 'vue'
import type {BookItem} from '@/types'

import { useCartStore } from '@/stores/cart';
const cartStore = useCartStore();

const props = defineProps<{
  book: BookItem
}>()

const bookImagePrefix = `${import.meta.env.BASE_URL}/book-images`
const bookImageFileName = function (book: BookItem): string {
  let name = book.title.toLowerCase()
  name = name.replace(/ /g, '-')
  name = name.replace(/'/g, '')
  name = name.replace(/:/g, '')
  name = name.replace(/\//g, '-')
  return `${name}.jpg`


}
</script>
<style scoped>
.book-box {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  background-color: #beb1d2;
  padding: 1em;
  gap: 0.25em;
}
.book-title {
  font-weight: bold;
  max-width: 180px;
}

.book-price {
  font-weight: bold;
}

.book-author {
  font-weight: 500;
}

.read-now-button {
  position: absolute;
  top: 5%;
  left: 95%;
  transform: translate(-50%, -50%);
  background-color: #ffbdbd;
  color: #000000;
  padding: 0.25em 0.25em;
  border-radius: 5em;
  cursor: pointer;
  border-style: solid;
  border-color: #000000;
}

.book-image {
  display: flex;
  position: relative;
  justify-content: center;
}

.read-now-button:hover {
  background-color: #ff655a;
  color: white;
}
.button-color {
  background-color: yellow;
  text-align: left;
}
.button-color:hover {
  background-color: rgb(227, 227, 1);
}
.cart {
  float: right;
}
</style>

<template>
  <section class="dark-background">
  <ul>
    <li class="book-box">
      <div class="book-image">
        <img :src="`${bookImagePrefix}/${bookImageFileName(book)}`" :alt="book.title"/>
        <button v-if="book.isPublic" class="read-now-button"><i class="fa-solid fa-book-open-reader" ></i></button>
      </div>
      <div class="book-title">{{ book.title }}</div>
      <div class="book-author">{{ book.author }}</div>
      <div class="book-price">${{ (book.price / 100).toFixed(2) }}</div>
      <button class="button button-color" @click="cartStore.addToCart(props.book)" >
        Add to Cart<i class="fa-solid fa-cart-plus cart"></i>
      </button>
    </li>
  </ul>
  </section>
</template>
