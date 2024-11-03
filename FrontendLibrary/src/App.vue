<script setup>
import Home from './views/Home.vue'; // Import only Home.vue
</script>

<template>
  <div class="app-container min-h-screen bg-background text-neutral font-sans">
    <!-- Header Section -->
    <header class="text-center p-6 bg-primary text-white shadow-lg">
      <h1 class="text-3xl font-bold">Library Management System</h1>
    </header>

    <!-- Main Content Section -->
    <main class="container mx-auto p-8 flex flex-col gap-8">
      <!-- Render Home component only -->
      <Home />
    </main>

    <!-- Footer Section -->
    <footer class="text-center p-4 bg-primary text-white mt-8">
      <p class="text-sm">&copy; 2024 Library Management System. All rights reserved.</p>
    </footer>
  </div>
</template>

<style scoped>
/* Google Font Import */
@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap');

.app-container {
  font-family: 'Roboto', sans-serif;
}
</style>
