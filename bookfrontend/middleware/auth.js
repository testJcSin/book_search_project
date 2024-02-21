export default function ({ req, redirect }) {
  const getCookie = (name, req) => {
    let cookieValue = null;
    if (process.server) {
      const cookieString = req.headers.cookie;
      if (cookieString) {
        const cookies = cookieString.split(';').map(cookie => cookie.trim());
        const cookie = cookies.find(cookie => cookie.startsWith(`${name}=`));
        if (cookie) {
          cookieValue = cookie.split('=')[1];
        }
      }
    } else if (process.client) {
      const cookieStr = document.cookie;
      const cookies = cookieStr ? cookieStr.split('; ') : [];
      const cookie = cookies.find(c => c.startsWith(`${name}=`));
      cookieValue = cookie ? decodeURIComponent(cookie.split('=')[1]) : null;
    }
    return cookieValue;
  };

  // CookieからaccessTokenを取得
  const accessToken = getCookie('accessToken', req);

  // accessTokenがない場合、SearchTopページにリダイレクト
  if (!accessToken) {
    return redirect('/SearchTop');
  }
}
