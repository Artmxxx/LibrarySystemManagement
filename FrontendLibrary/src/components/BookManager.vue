<template>
    <div class="book-manager-container p-4 border border-primary rounded-lg shadow-lg">
      <h2 class="text-xl font-semibold text-primary mb-4">Manage Books</h2>
  
      <!-- Add/Edit Book Form -->
      <form @submit.prevent="addBook" class="flex flex-col gap-4 mb-6">
        <input v-model="newBook.title" placeholder="Title" class="input input-bordered w-full" required />
        <input v-model="newBook.author" placeholder="Author" class="input input-bordered w-full" required />
        <input v-model="newBook.isbn" placeholder="ISBN" class="input input-bordered w-full" required />
        <label class="flex items-center gap-2">
          <input type="checkbox" v-model="newBook.available" />
          <span>Available</span>
        </label>
        <button type="submit" class="btn btn-primary">{{ isEditing ? 'Update Book' : 'Add Book' }}</button>
      </form>
  
      <!-- List of Books in a Table -->
      <div v-if="books.length > 0">
        <h3 class="text-lg font-bold mb-4">Book List</h3>
        <table class="table-auto w-full border-collapse">
          <thead>
            <tr>
              <th class="border-b p-2">Title</th>
              <th class="border-b p-2">Author</th>
              <th class="border-b p-2">ISBN</th>
              <th class="border-b p-2">Available</th>
              <th class="border-b p-2">Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="book in books" :key="book.id">
              <td class="border-b p-2">{{ book.title }}</td>
              <td class="border-b p-2">{{ book.author }}</td>
              <td class="border-b p-2">{{ book.isbn }}</td>
              <td class="border-b p-2">{{ book.available ? 'Yes' : 'No' }}</td>
              <td class="border-b p-2">
                <button @click="editBook(book)" class="btn btn-secondary mr-2">Edit</button>
                <button @click="deleteBook(book.id)" class="btn btn-error">Delete</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
  
      <div v-else>
        <p>No books available. Please add some books to get started.</p>
      </div>
    </div>
  </template>
  
  <script>
  import BookService from '../services/BookService';
  
  export default {
    data() {
      return {
        books: [],
        newBook: {
          title: '',
          author: '',
          isbn: '',
          available: true, // Default value for 'available'
        },
        isEditing: false,
        editingBookId: null,
      };
    },
    methods: {
      async fetchBooks() {
        try {
          const response = await BookService.getAllBooks();
          this.books = response.data;
        } catch (error) {
          console.error('Error fetching books:', error);
        }
      },
      async addBook() {
        try {
          if (this.isEditing) {
            await BookService.updateBook(this.editingBookId, this.newBook);
          } else {
            await BookService.addBook(this.newBook);
          }
          this.resetForm();
          this.fetchBooks();
        } catch (error) {
          console.error('Error adding/updating book:', error);
        }
      },
      editBook(book) {
        this.newBook = { title: book.title, author: book.author, isbn: book.isbn, available: book.available };
        this.isEditing = true;
        this.editingBookId = book.id;
      },
      async deleteBook(bookId) {
        try {
          await BookService.deleteBook(bookId);
          this.fetchBooks();
        } catch (error) {
          console.error('Error deleting book:', error);
        }
      },
      resetForm() {
        this.newBook = { title: '', author: '', isbn: '', available: true };
        this.isEditing = false;
        this.editingBookId = null;
      },
    },
    mounted() {
      this.fetchBooks();
    },
  };
  </script>
  
  <style scoped>
  .book-manager-container {
    background-color: white;
  }
  
  table {
    width: 100%;
    border-spacing: 0;
  }
  
  th, td {
    padding: 8px;
    border: 1px solid #ccc;
  }
  
  thead th {
    background-color: #f7f7f7;
  }
  
  tbody tr:hover {
    background-color: #f9f9f9;
  }
  </style>
  