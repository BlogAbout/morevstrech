export default {
    install(Vue) {
        Vue.prototype.$message = function(html) {
            console.log(html)
        }

        Vue.prototype.$error = function(html) {
            console.error(html)
        }
    }
}