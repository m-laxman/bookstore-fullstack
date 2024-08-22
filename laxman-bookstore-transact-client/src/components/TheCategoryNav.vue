<script setup lang="ts">
// import {inject} from 'vue'
// import type {CategoryItem} from '@/types'
// const categoryList = inject('categoryList') as CategoryItem[]
import { useCategoryStore } from "@/stores/category";
const categoryStore = useCategoryStore();
</script>

<style scoped>
.category-buttons {
  display: flex;
  flex-direction: row;
  text-align: center;
  justify-content: center;
  background-color: var(--neutral-color);
}

.button.category-button.router-link-active {
  background-color: #e3d0ff;
}

.button.category-button {
  margin: 0em 0.15em;
  background-color: #dfa2d4;
  border-radius: 10px;
}


.button.category-button:hover,
.button.category-button:active {
  background-color: #e3d0ff;
}

.button.selected-category-button {
  margin: 0em 0.15em;
  background-color: #e3d0ff;
  border-radius: 10px;
}

.button.unselected-category-button {
  margin: 0em 0.15em;
  background-color: #dfa2d4;
  border-radius: 10px;
}
</style>

<template>
  <nav class="category-nav">
    <ul class="category-buttons">
      <li v-for="category in categoryStore.categoryList" :key="category.categoryId">
        <router-link :to="'/category/' + category.name" class="button category-button">
          {{ category.name }}
        </router-link>
      </li>
    </ul>
  </nav>
</template>
