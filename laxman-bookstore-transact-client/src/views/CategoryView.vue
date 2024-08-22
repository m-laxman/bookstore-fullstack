<script setup lang="ts">
// import { apiUrl } from "@/api";
// import type { BookItem } from "@/types";
import { watch } from "vue";
import TheCategoryNav from "@/components/TheCategoryNav.vue";
import TheCategoryBookList from "@/components/TheCategoryBookList.vue";
import { useRoute } from "vue-router";
import { useBookStore } from "@/stores/book";
import { useCategoryStore } from "@/stores/category";
import router from "@/router";
const categoryStore = useCategoryStore();
const bookStore = useBookStore();
const route = useRoute();
watch(
  () => route.params.name,
  async (newCategoryName) => {
    try {
      console.log(route.params.name, newCategoryName);
      await bookStore.fetchBooks(newCategoryName as string);
      categoryStore.setSelectedCategoryName(newCategoryName as string);
    } catch (e) {
      await router.push('/not-found')
    }
  },
  { immediate: true }
);
// bookStore.fetchBooks(route.params.name as string);
// let response = await fetch(`${apiUrl}/categories/name/${route.params.name}/books/`);
// let response = await fetch(`http://localhost:8080/LaxmanBookstoreState/api/categories/name/${route.params.name}/books/`);
// let data = await response.json();
// let bookList = data as BookItem[];


</script>

<style scoped></style>
<template>
  <div>
    <the-category-nav></the-category-nav>
    <the-category-book-list></the-category-book-list>
  </div>
</template>