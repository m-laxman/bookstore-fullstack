<script setup lang="ts">
// import type {BookItem} from '@/types'
import CategoryBookListItem from '@/components/CategoryBookListItem.vue'

// const props = defineProps<{
//   bookList: BookItem[]
// }>()
import { useBookStore } from '@/stores/book';
const bookStore = useBookStore()
</script>

<style scoped>
#book-boxes {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  padding: 1em;
  gap: 1.5em;
}


</style>

<template>
  <div class="dark-background">
  <ul  id="book-boxes">
  <template v-for="book in bookStore.bookList" :key="book.bookId">
    <category-book-list-item :book="book"></category-book-list-item>
  </template>
</ul>
  </div>
</template>
