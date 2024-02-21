import CookieManager from '@/utils/CookieManager.js';

export const AuthManager = {
  mixins: [CookieManager],
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
