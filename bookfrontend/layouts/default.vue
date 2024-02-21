<template>
  <div id="app">
    <main>
      <div class="container">
        <Nuxt />
      </div>
    </main>
  </div>
</template>

<script>
export default {
  name: 'DefaultLayout',
  data() {
    return {
    };
  },
}
</script>

<style>
.container {
  max-width: 960px;
  margin: 0 auto;
  padding: 20px;
}
</style>