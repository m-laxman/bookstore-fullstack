<script setup lang="ts">
import type { BookItem } from "@/types";
import { computed } from "vue";
import BookPrice from "./BookPrice.vue";
import { useCartStore } from "@/stores/cart";
import { useCategoryStore } from "@/stores/category";
import { asDollarsAndCents } from "../utils"
import { useBookStore } from "@/stores/book";
import type CategoryViewVue from "@/views/CategoryView.vue";
import { ShoppingCart } from "@/models/ShoppingCart";
import { useCounterStore } from "@/stores/counter";
const cartStore = useCartStore();
const bookStore = useBookStore();
const categoryStore = useCategoryStore();
const counterStore = useCounterStore();
const cart = computed(() => {
  return cartStore.cart;
});
const bookImageFileName = function (book: BookItem): string {
  let name = book.title.toLowerCase()
  name = name.replace(/ /g, '-')
  name = name.replace(/'/g, '')
  name = name.replace(/:/g, '')
  name = name.replace(/\//g, '-')
  return `${name}.jpg`
}
const bookImagePrefix = `${import.meta.env.BASE_URL}/book-images`;

const updateCart = function (book: BookItem, quantity: number) {
  cartStore.updateBookQuantity(book, quantity);
};

const clearCart = function () {
  cartStore.clearCart();
};
</script>

<style scoped>
.parent {
  width: fit-content;
  margin: 0 auto;
}

.cart-table {
  display: grid;
  grid-template-columns: max-content minmax(10em, 20em) repeat(3, max-content);
  row-gap: 1em;
  width: fit-content;
  margin: 0 auto;
  background-color: #fceffd;
}

ul {
  display: contents;
}

ul>li {
  display: contents;
}

.table-heading {
  background-color: #333;
  color: white;
}

.table-heading>* {
  background-color: #333;
  padding: 0.5em;
}

.heading-book {
  grid-column: 1 / 3;
}

.heading-price {
  grid-column: 3 / 5;
  text-align: right;
}

.heading-subtotal {
  text-align: right;
  grid-column: -2 / -1;
}

.cart-book-image {
  padding: 0 1em;
}

.cart-book-image>* {
  margin-left: auto;
  margin-right: 0;
}

img {
  display: block;
  width: 100px;
  height: auto;
}

.rect {
  background-color: var(--primary-color-dark);
}

.narrow-rect {
  width: 75px;
  height: 100px;
}

.square {
  width: 100px;
  height: 100px;
}

.wide-rect {
  width: 125px;
  height: 100px;
}

.cart-book-price {
  padding-left: 1em;
  text-align: right;
}

.cart-book-quantity {
  padding-left: 1em;
  padding-right: 1em;
}

.cart-book-subtotal {
  text-align: right;
  padding-left: 1em;
  padding-right: 1em;
  float: right;
}

/* Row separators in the table */

.line-sep {
  display: block;
  height: 2px;
  background-color: gray;
  grid-column: 1 / -1;
}

/* Increment/decrement buttons */

.icon-button {
  border: none;
  cursor: pointer;
  border-radius: 1;
  padding: 0;
  line-height: 0;
}

.inc-button {
  font-size: 1.125rem;
  color: #00b60c;
  margin-right: 0.5em;
}

.inc-button:hover {
  color: rgb(20, 113, 20);
}

.dec-button {
  font-size: 1.10rem;
  color: #fd8484;
}

.dec-button:hover {
  color: rgb(205, 0, 0);
}

/* Chevron buttons */

.dec-arrow-button,
.inc-arrow-button {
  font-size: 1rem;
  color: var(--primary-color);
}

.dec-arrow-button:hover,
.inc-arrow-button:hover {
  color: var(--primary-color-dark);
}

.button {
  color: black;
}

input[type="number"] {
  width: 4em;
}

select {
  background-color: var(--primary-color);
  color: white;
  border: 2px solid var(--primary-color-dark);
  border-radius: 3px;
}

.button-background {
  display: inline-block;
  background: var(--primary-color-dark);
  color: black;
  padding: 0.5em 1em;
  cursor: pointer;
  border-radius: 10px;
}

.button-background-cta {
  display: inline-block;
  background: rgb(224, 224, 18);
  text-shadow: true;
  color: black;
  padding: 0.5em 1em;
  cursor: pointer;
  margin: 1em 0.5em;
  border-radius: 10px;
}

.button-background-cta:hover {
  background-color: rgb(255, 255, 15);
}

.button-background:hover {
  background: #ff8282;
}

.button-background-clear {
  cursor: pointer;
  margin-top: 0.5em;
  padding: 0.5em;
  align-items: margin-left;
  background-color: #ff8282;
  color: black;
  border-radius: 5px;
  margin: 0.5em 0;
  float: left;
}

.button-background-clear:hover {
  background-color: rgb(205, 0, 0);
  color: white;
  border-radius: 5px;
}

.container-shop {
  justify-content: center;
  font-size: 20px;
  margin-top: 3em;
}

.price {
  font-weight: 600;
}

.items-text {
  margin-bottom: 1em;
}


.total {
  margin-right: auto;
  float: right;
  text-align: right;
}

.empty {
  margin-bottom: 0.5em;
}
</style>


<template>
  <div class="empty">
    <div v-if="cartStore.count > 1">
      Your shopping cart contains
      {{ cartStore.count }} books.
    </div>
    <div v-else-if="cartStore.count === 1">
      Your shopping cart contains
      {{ cartStore.count }} book.
    </div>
    <div v-else>Your shopping cart is empty.</div>

  </div>
  <div class="parent">
    <div class="cart-table">
      <ul>
        <li class="table-heading" v-if="cartStore.count > 0">
          <div class="heading-book">Book</div>
          <div class="heading-price">Price / Quantity</div>
          <div class="heading-subtotal">Amount</div>
        </li>
        <template v-for="item in cartStore.cart.items" :key="item.book.bookId">
          <li>
            <div class="cart-book-image">
              <img :src="`${bookImagePrefix}/${bookImageFileName(item.book)}`" :alt="item.book.title" width="100px"
                height="auto" />
            </div>
            <div class="cart-book-title">{{ item.book.title }}</div>
            <div class="cart-book-price">
              <BookPrice :amount="item.book.price" />
            </div>
            <div class="cart-book-quantity">
              <span class="quantity">{{ item.quantity }}</span>&nbsp;
              <button class="icon-button inc-button" @click="updateCart(item.book, item.quantity + 1)">
                <i class="fas fa-plus-circle"></i>
              </button>
              <button class="icon-button dec-button" @click="updateCart(item.book, item.quantity - 1)">
                <i class="fas fa-minus-circle"></i>
              </button>
            </div>
            <div class="cart-book-subtotal">
              <BookPrice :amount="item.quantity * item.book.price" />
            </div>
          </li>
          <li class="line-sep"></li>
        </template>
      </ul>
    </div>
    <div class="total">
      <div v-if="cartStore.count >= 1" class="item">
        Subtotal:
        <BookPrice :amount="cart.subtotal" />
      </div>
      <div v-if="cartStore.count >= 1" class="item">
        Total (Subtotal + surcharge(<BookPrice :amount="cart.surcharge" />)):
        <span class="price">
          <BookPrice :amount="cart.total" />
        </span>
      </div>
    </div>
    <button class="button-background-clear" @click="clearCart()" v-if="cartStore.count > 0">
      Clear Cart <i class="fa-solid fa-trash"></i>
    </button>
  </div>
  <section class="container-shop">
    <div>
    <router-link :to="{
      name: 'category-view',
      params: { name: categoryStore.selectedCategoryName },
    }">
      <button class="button-background">Continue Shopping</button>
    </router-link>
    <!-- <router-link :to="'../category/{{categoryStore.selectedCategoryName}}'">
      <button class="button-background">Continue Shopping</button>
    </router-link> -->
    <router-link :to="{ name: 'checkout-view' }" v-if="cartStore.count > 0">
      <button class="button-background-cta">Proceed to Checkout <i class="fa-regular fa-greater-than"></i> </button>
    </router-link>
  </div>
  </section>
</template>
