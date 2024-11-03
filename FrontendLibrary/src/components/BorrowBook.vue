<template>
  <div>
    <h2>Borrow Book</h2>
    <input v-model="userId" placeholder="User ID" />
    <input v-model="bookId" placeholder="Book ID" />
    <button @click="borrowBook">Borrow</button>
    <p v-if="message">{{ message }}</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      userId: '',
      bookId: '',
      message: '',
    };
  },
  methods: {
    async borrowBook() {
      try {
        const response = await axios.post(
          'http://localhost:8080/api/books/borrow',
          null,
          { params: { userId: this.userId, bookId: this.bookId } }
        );
        this.message = response.data;
      } catch (error) {
        this.message = error.response?.data || 'Error borrowing book.';
      }
    },
  },
};
</script>
