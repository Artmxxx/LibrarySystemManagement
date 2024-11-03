<template>
    <div>
      <h1 class="text-3xl font-bold text-center text-primary my-8">Library Management System</h1>
      <div class="container mx-auto p-4 flex flex-col gap-8">
       
        <SearchBook />
        <BookManager />
        
        
        
      </div>
    </div>
  </template>
  
  <script>
  import SearchBook from '../components/SearchBook.vue';
  import BorrowBook from '../components/BorrowBook.vue';
  import ReturnBook from '../components/ReturnBook.vue';
  import CheckAvailability from '../components/CheckAvailability.vue';
  import BookManager from '../components/BookManager.vue';
  
  export default {
    components: {
      SearchBook,
      
      BookManager,
    },
  };
  </script>
  
  <style scoped>
  .container {
    max-width: 800px;
  }
  </style>
  