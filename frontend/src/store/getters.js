const getters = {
    appTitle(state) {
        return state.appTitle
    },
    isAuthenticated(state) {
        return state.isAuthenticated === 'true'
    },
    getError(state) {
        return state.error
    },
    getLoading(state) {
        return state.loading
    }
};

export default getters;