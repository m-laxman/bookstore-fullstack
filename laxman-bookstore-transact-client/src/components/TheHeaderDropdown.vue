<script setup lang="ts">
// import { apiUrl } from "@/api"
// import type { CategoryItem } from "@/types";
import { useCategoryStore } from "@/stores/category";
const categoryStore = useCategoryStore();
// let response = await fetch(`${apiUrl}/categories/`);
// // let response = await fetch("http://localhost:8080/LaxmanBookstoreState/api/categories/");
// let data = await response.json();
// let categoryList = data as CategoryItem[];
</script>

<style scoped>
.header-dropdown {
  position: absolute;
  z-index: 1;
}

.categories-button {
  background-color: white;
  margin-top: 0.35em;
  text-align: left;
  width: 15.65em;
}

.header-dropdown ul {
  background-color: rgb(234, 234, 234);
  color: black;
  list-style: none;
  overflow: hidden;
  max-height: 0;
  transition: max-height 1s;
  border-radius: 15px;
  width: 100%;
}


.header-dropdown li {
  padding: 0.5em;

}

.header-dropdown a {
  display: block;
  color: black;
  text-decoration: none;
}

.header-dropdown li:hover {
  background-color: var(--primary-color-dark);
  border-radius: 15px;
}

i {
  float: right;
}

.header-dropdown:hover ul {
  position: absolute;
  cursor: pointer;
  border-radius: 15px;
  width: 15.65em;
  max-height: 10em;
}
</style>

<template>
  <div class="header-dropdown">
    <button class="button categories-button">Categories
      <i class="fa-solid fa-circle-chevron-down"></i>
    </button>
    <ul>
      <li v-for="category in categoryStore.categoryList" :key="category.categoryId">
        <router-link :to="'/category/' + category.name">
          {{ category.name }}
        </router-link>
      </li>
    </ul>
  </div>
</template>
