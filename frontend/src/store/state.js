const state = {
    isAuthenticated: localStorage.getItem('AuthState'),
    error: null,
    loading: false
};

export default state;