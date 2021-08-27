<template>
    <v-app>
        <v-app-bar app color="primary" dark height="50px">
            <v-container>
                <v-row justify="center" no-gutters>
                    <v-col cols="3">
                        <v-img
                                alt="МореВстреч Сочи"
                                class="shrink mr-2"
                                contain
                                src="../assets/images/logo.png"
                                transition="scale-transition"
                                width="150px"
                        />
                    </v-col>
                    <v-spacer></v-spacer>
                    <v-col cols="6" class="text-right">
                        <v-btn icon class="white--text">
                            <v-icon>fab fa-searchengin</v-icon>
                        </v-btn>
                        <user-toggle-notifications />
                        <user-toggle-menu />
                        <v-btn icon class="white--text">
                            <v-icon>fas fa-cog</v-icon>
                        </v-btn>
                    </v-col>
                </v-row>
            </v-container>
        </v-app-bar>

        <v-main class="pt-0">
            <transition name="fade">
                <router-view />
            </transition>
        </v-main>

        <v-footer color="primary" padless>
            <v-container>
                <v-row justify="center" no-gutters>
                    <v-col class="primary py-4 text-center white--text" cols="12">
                        <ul class="footer-menu footer-menu-row">
                            <router-link
                                    v-for="(item, index) in footerLinks"
                                    :key="'footer-links-three-' + index"
                                    :to="item.url"
                                    custom
                                    v-slot="{ href, route, navigate, isActive, isExactActive }"
                            >
                                <li :class="[isActive && 'active', isExactActive && 'exact-active']">
                                    <a :href="href" @click="navigate" class="white--text">{{ item.title }}</a>
                                </li>
                            </router-link>
                        </ul>
                    </v-col>
                    <v-col class="primary py-4 text-center white--text" cols="12">
                        &copy; {{ new Date().getFullYear() }}. All rights reserved. МореВстреч Сочи.
                    </v-col>
                </v-row>
            </v-container>
        </v-footer>
    </v-app>
</template>

<script>
    //import messages from '@/utils/plugins/messages'
    import UserToggleMenu from "../components/UserToggleMenu"
    import UserToggleNotifications from "../components/UserToggleNotifications"

    export default {
        name: 'UserLayout',
        components: {
            UserToggleMenu,
            UserToggleNotifications
        },
        data: () => ({
            footerLinks: [
                { title: 'Презентация', url: '/information/presentation' },
                { title: 'Для Спонсоров', url: '/information/for-sponsor' },
                { title: 'Для Инвесторов', url: '/information/for-investor' },
                { title: 'Территория Сочи', url: '/information/territory' },
                { title: 'Полезные статьи', url: '/information/articles' },
                { title: 'Партнёрство', url: '/information/partners' },
                { title: 'Администрация', url: '/information/administration' },
                { title: 'Отель «Звезда»', url: '/information/hotel-star' }
            ]
        })
    }
</script>