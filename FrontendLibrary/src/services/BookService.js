import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8080/api/books', // Ensure correct backend URL without trailing slash
  headers: {
    'Content-Type': 'application/json',
  },
});

export default {
  getAllBooks() {
    return apiClient.get(''); // Correcting the trailing slash issue
  },
  getBookById(bookId) {
    return apiClient.get(`/${bookId}`);
  },
  addBook(bookData) {
    return apiClient.post('', bookData);
  },
  updateBook(bookId, bookData) {
    return apiClient.put(`/${bookId}`, bookData);
  },
  deleteBook(bookId) {
    return apiClient.delete(`/${bookId}`);
  },
  searchBooks({ title, author, isbn }) {
    return apiClient.get('/search', {
      params: { title, author, isbn },
    });
  },
};
