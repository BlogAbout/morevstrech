<template>
    <v-app>
        <component :is="layout">
            <transition name="fade">
                <router-view />
            </transition>
        </component>
    </v-app>
</template>

<script>
    import MainLayout from './layouts/MainLayout.vue'
    import UserLayout from "./layouts/UserLayout"

    export default {
        name: 'App',
        components: {
            MainLayout,
            UserLayout
        },
        computed: {
            layout() {
                return (this.$route.meta.layout || 'main') + '-layout'
            }
        }
    }
</script>