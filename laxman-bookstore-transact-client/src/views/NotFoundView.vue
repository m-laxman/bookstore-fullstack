<script setup lang="ts">
</script>

<style>
#app {
  background-color: #F8E7E7;
}

.text {
  text-align: center;
  font-weight: 600;
  font-size: 30px;
  margin-top: 3em;
  margin-bottom: 3em;

}
</style>

<template>
  <suspense>
    <div id="app">
      <the-header></the-header>
      <div class="text">Requested page not found!</div>
      <the-footer></the-footer>
    </div>
  </suspense>
</template>
