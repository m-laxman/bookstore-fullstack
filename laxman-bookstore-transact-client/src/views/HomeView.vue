<script setup lang="ts">
import TheHomeCategoryList from '@/components/TheHomeCategoryList.vue'
</script>

<style scoped>

.welcome-text {
  width: 14em;
  font-size: 2em;
}


.image-container {
  position: relative;
  display: inline-block;
  padding: 0%;
}

.image-container img {
  display: block;
  width: 75rem;
  height: auto;
}

.overlay-text {
  position: absolute;
  top: 50%;
  left: 50%;
  transform:translate(-50%, -50%);
  color: #fff;
  padding: 1em 1em;
  background-color: #000000;
}

</style>
<template>
  <div class="home-page dark-background">
    <section>
      <div class="image-container">
        <img src="/site-images/books.png" alt="Books" />
        <div class="overlay-text">
          <h1 class="welcome-text">BookEra</h1>
          <h2> Find the Latest and Greatest reads</h2>
        </div>
      </div>
    </section>

      <the-home-category-list></the-home-category-list>
  </div>
</template>
