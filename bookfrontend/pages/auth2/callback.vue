<template>
  <div>認証中...</div>
</template>

<script>
import axios from 'axios';
import cookieManager from '@/utils/cookieManager.js'

export default {
  mixins: [cookieManager],
  mounted() {
    this.getAccessToken();
  },
  methods: {
    async getAccessToken() {
      const code = this.$route.query.code;
      if (code) {
        try {
          // accesstoken取得
          const response = await axios.get('http://localhost:8081/api/auth', { params: { code } });
          const { accessToken } = response.data;
          if (accessToken) {
            // accesstoken保存
            this.setCookie('accessToken', accessToken, 24);
            // 書籍検索画面へ遷移
            this.$router.push('/SearchPage');
          }
        } catch (error) {
          console.error('アクセストークンの取得中にエラーが発生しました。', error);
          // 書籍TOP画面へ遷移
          this.$router.push('/SearchTop');
        }
      }
    }
  }
}
</script>