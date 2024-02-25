import cookieManager from '~/utils/cookieManager.js';

export const authManager = {
  mixins: [cookieManager],
  methods: {
    checkAccessToken() {
      const accessToken = this.getCookie('accessToken');
      if (!accessToken) {
        alert('認証に失敗しました');
        this.$router.push('/SearchTop');
        return false;
      }
      return true;
    }
  }
}
