<template>
    <div class="book-search-chat">
      <h1>書籍検索chat</h1>
      <button @click="searchBooks">書籍を探す</button>
    </div>
  </template>
  
  <script>
  export default {
    name: 'BookSearchChat',
    methods: {
      searchBooks() {
        // ここに書籍検索のロジックを実装します
        console.log('書籍を探すボタンがクリックされました。');
      }
    }
  }
  </script>
  
  <style>
  .book-search-chat {
    text-align: center;
    margin-top: 50px;
  }
  
  .book-search-chat h1 {
    margin-bottom: 20px;
  }
  
  .book-search-chat button {
    padding: 10px 20px;
    font-size: 16px;
    cursor: pointer;
  }
  </style>