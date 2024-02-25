<template>
  <div class="search-page-container">
    <div class="question-input">
      <label for="question">探したい開発技術書籍の質問</label>
      <input id="question" v-model="question" type="text" placeholder="質問を入力してください" @keyup.enter="focusSubmitButton">
      <button ref="submitButton" @click="submitQuestion">送信</button>
    </div>
    <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>
    <div class="answer-output">
      <textarea v-model="answer" readonly rows="10"></textarea>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { authManager } from '@/utils/authManager.js';

export default {
  middleware: 'auth',
  mixins: [authManager],
  data() {
    return {
      question: '',
      answer: '',
      errorMessage: ''
    };
  },
  methods: {
    checkInput() {
      if (!this.question.trim()) {
        this.answer = '質問を入力してください。';
        return false;
      }
      return true;
    },
    focusSubmitButton() {
      this.$refs.submitButton.focus();
    },
    async submitQuestion() {
      // アクセストークンのチェック
      if (!this.checkAccessToken()) return;
      // 入力値チェック
      //if (!this.checkInput()) return;
      try {
        const response = await axios.get('http://localhost:8081/bookSearch', { 
          params: { question: this.question }
        });
        this.answer = response.data.answer;
        this.errorMessage = '';
      } catch (error) {
        // エラーレスポンスから特定のメッセージを抽出して表示
        if (error.response && error.response.data) {
          // エラーメッセージがオブジェクト形式であることを想定
          const errorMessages = error.response.data;
          // 最初のエラーメッセージの値を取得
          const firstErrorMessage = errorMessages[Object.keys(errorMessages)[0]];
          // errorMessageに設定
          this.errorMessage = firstErrorMessage;
        } else {
          // 予期せぬエラーの場合は汎用メッセージを表示
          this.errorMessage = 'エラーが発生しました。';
        }
      }
    }
  }
}
</script>

<style>
.search-page-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}

.question-input,
.answer-output {
  width: calc(100% - 40px);
  margin-bottom: 20px;
}

.question-input input,
.answer-output textarea {
  width: 100%;
  border: 1px solid black;
}

.question-input button {
  display: block;
  width: auto;
  padding: 5px 15px;
  background-color: #b0b0b0;
  border: 1px solid black;
  margin: 10px auto;
}

.answer-output textarea {
  height: auto;
  min-height: 150px;
  overflow-y: auto;
}

.error-message {
  color: red;
}
</style>