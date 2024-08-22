<script setup lang="ts"></script>

<style scoped>

.search-bar {
  padding: 0.5em;
  width: 13.15em;
  outline: none;
}

.search-button {
  border-radius: 50%;
  height: 35px;
  width: 35px;
  margin-left: 0.35em;
  text-align: center;
  padding: 0em;
}
</style>

<template>
  <!-- <form action="/"> -->
    <input type="text" class="search-bar" placeholder="Search by Author, Book"/>
    <button type="submit" class="button search-button">
      <i class="fa-solid fa-magnifying-glass"></i>
    </button>
  <!-- </form> -->
</template>
