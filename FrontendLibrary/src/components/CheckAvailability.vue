<template>
  <div>
    <h2>Check Book Availability</h2>
    <input v-model="bookId" placeholder="Book ID" />
    <button @click="checkAvailability">Check Availability</button>
    <p v-if="message">{{ message }}</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      bookId: '',
      message: '',
    };
  },
  methods: {
    async checkAvailability() {
      try {
        const response = await axios.get('http://localhost:8080/api/books/check-availability', {
          params: { bookId: this.bookId },
        });
        this.message = response.data;
      } catch (error) {
        this.message = 'Error checking availability.';
      }
    },
  },
};
</script>
