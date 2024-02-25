export const state = () => ({
    accessToken: null,
  });
  
  export const mutations = {
    setAccessToken(state, token) {
      state.accessToken = token;
    },
  };
  
  export const actions = {
    initAuth({ commit }) {
      const token = this.$cookies.get('accessToken');
      if (token) {
        commit('setAccessToken', token)
      }
    },
    setAccessToken({ commit }, { accessToken, expiresIn }) {
      commit('setAccessToken', accessToken);
      this.$cookies.set('accessToken', accessToken, {
        path: '/',
        maxAge: 60 * 60 * expiresIn,
      });
    }, 
  };