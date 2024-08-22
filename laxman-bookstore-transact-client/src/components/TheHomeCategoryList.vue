<script setup lang="ts">
// import {inject} from 'vue'
import type { CategoryItem } from '@/types'
import { useCategoryStore } from "@/stores/category";
const categoryStore = useCategoryStore();
// const categoryList = inject('categoryList') as CategoryItem[]
const categoryImageFileName = function (category: CategoryItem): string {
  let name = category.name.toLowerCase()
  name = name.replace(/ /g, '-')
  name = name.replace(/'/g, '')
  return `${name}.jpg`
}
</script>

<style scoped>
.container-shop {
  text-align: center;
  justify-content: center;
  margin-bottom: 1em;
  font-size: 1.35em;
}

.welcome-text {
  width: 14em;
  font-size: 2em;
}

.category-image-items {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: center;
  gap: 2em;
}

.overlay-text {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: #fff;
  padding: 1em 1em;
  background-color: #000000;
}

.category-image-items a {
  text-align: center;
  cursor: pointer;
}

.button-home-background {
  display: inline-block;
  background: var(--primary-color-dark);
  color: black;
  padding: 0.5em 1em;
  cursor: pointer;
  border-radius: 10px;
}

.button-home-background:hover {
  background: #ff8282;
}

.category_name {
  background: black;
  color: white;
}

a:hover {
  .category_name {
    color: var(--primary-color-dark);
  }
}
</style>

<template>
  <section class="category-images container">
    <div class="container-shop">
      <router-link class="button-home-background" :to="{
      name: 'category-view',
      params: { name: categoryStore.defaultCategoryName },
    }"> Shop for Books!</router-link>
    </div>
    <div class="category-image-items dark-background">
      <a v-for="category in categoryStore.categoryList" :key="category.categoryId">
        <router-link :to="'../category/' + category.name">
          <img :src="'category-images/' + categoryImageFileName(category)" :alt="category.name + ' category'" />
          <div class="category_name">{{ category.name }}</div>
        </router-link>
      </a>


    </div>
  </section>
</template>
