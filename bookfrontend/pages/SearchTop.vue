<template>
  <div class="search-container">
    <div class="search-content">
      <h1>書籍検索chat</h1>
      <button @click="handleSearchBooks" class="search-button">書籍を探す</button>
    </div>
  </div>
</template>

<script>

export default {
  computed: {
    accessToken() {
      return this.$store.state.accessToken;
    }
  },
  methods: {
    handleSearchBooks() {
      // accessTokenがない場合
      if (!this.accessToken) {
        // 認証処理
        this.redirectToGoogleOAuth();
      } else {
        // 書籍検索画面へ遷移
        this.$router.push('/SearchPage'); 
      }
    },
    redirectToGoogleOAuth() {
      const clientId = this.$config.googleClientId;
      const redirectUri = this.$config.googleRedirectUri;
      const scope = 'email';
      const responseType = 'code';
      const authUrl = `https://accounts.google.com/o/oauth2/v2/auth?client_id=${clientId}&redirect_uri=${redirectUri}&response_type=${responseType}&scope=${scope}`;
      window.location.href = authUrl;
    }
  }
}
</script>

<style>
.search-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: white;
}

.search-content {
  text-align: center;
  color: black;
}

.search-button {
  padding: 10px 20px;
  margin: 10px;
  background-color: #eee;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
  color: black;
  text-decoration: none;
  display: inline-block;
}

.search-button:hover {
  background-color: #ccc;
}
</style>