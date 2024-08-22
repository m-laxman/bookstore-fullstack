<script setup lang="ts">
</script>

<style scoped>
footer {
  background: var(--footer-background-color);
  display: flex;
  justify-content: space-between;
}

.links,
.social-media-icons {
  display: flex;
  flex-direction: column;
  color: white;
  justify-content: center;
}

.social-media-icons {
  text-align: right;
  font-size: 1.75em;
}

.copyright {
  justify-content: left;
  color: white;
  float: left;
  font-size: 12px;

}
i {
  margin-left: 0.35rem;
}
</style>

<template>
  <footer class="container">
    <section class="copyright">
      <div>
      <i class="fa-regular fa-copyright"></i><span>
        BookEra (2023). <br> All rights reserved.</span
    >
      </div>
    </section>
    <section class="links">
      <div>
        <a href="#">Contact Us</a> <span>| </span>
        <a href="#">Directions</a>
      </div>
    </section>
    <section class="social-media-icons">
      <div>
        <a href="#"><i class="fa-brands fa-square-twitter"></i></a>
        <a href="#"><i class="fa-brands fa-square-facebook"></i></a>
        <a href="#"><i class="fa-brands fa-square-instagram"></i></a>
      </div>
    </section>
  </footer>
</template>
