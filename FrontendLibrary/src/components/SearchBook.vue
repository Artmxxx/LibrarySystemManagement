<template>
  <div class="search-container p-4 border border-primary rounded-lg shadow-lg">
    <h2 class="text-xl font-semibold text-primary mb-4">Search Books</h2>
    <div class="flex flex-col gap-4">
      <input v-model="title" placeholder="Search by Title" class="input input-bordered w-full" />
      <input v-model="author" placeholder="Search by Author" class="input input-bordered w-full" />
      <input v-model="isbn" placeholder="Search by ISBN" class="input input-bordered w-full" />
      <button @click="searchBooks" class="btn btn-primary">Search</button>
    </div>

    <div v-if="results.length > 0" class="mt-6">
      <h3 class="text-lg font-bold">Search Results</h3>
      <ul class="list-disc pl-5 mt-2">
        <li v-for="book in results" :key="book.id" @click="selectBook(book)" class="cursor-pointer text-primary hover:underline">
          {{ book.title }} by {{ book.author }}
        </li>
      </ul>
    </div>

    <div v-if="results.length === 0 && searchAttempted" class="mt-6">
      <p class="text-lg text-red-600">No books found for the search criteria.</p>
    </div>

    <div v-if="selectedBook" class="mt-6 p-4 border-t border-primary">
      <h3 class="text-lg font-semibold text-primary">Book Details</h3>
      <p><strong>Title:</strong> {{ selectedBook.title }}</p>
      <p><strong>Author:</strong> {{ selectedBook.author }}</p>
      <p><strong>ISBN:</strong> {{ selectedBook.isbn }}</p>
      <p><strong>Available:</strong> {{ selectedBook.available ? 'Yes' : 'No' }}</p>
    </div>
  </div>
</template>

<script>
import BookService from '../services/BookService';

export default {
  data() {
    return {
      title: '',
      author: '',
      isbn: '',
      results: [],
      selectedBook: null,
      searchAttempted: false,
    };
  },
  methods: {
    async searchBooks() {
      this.searchAttempted = true; // Set search attempt flag to true
      try {
        const response = await BookService.searchBooks({
          title: this.title,
          author: this.author,
          isbn: this.isbn,
        });
        this.results = response.data;
        this.selectedBook = null; // Reset selected book on new search
      } catch (error) {
        console.error('Error searching books:', error);
      }
    },
    selectBook(book) {
      this.selectedBook = book;
    },
  },
};
</script>

<style scoped>
.search-container {
  background-color: white;
}
</style>
