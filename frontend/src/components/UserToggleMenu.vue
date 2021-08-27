<template>
    <v-menu bottom left offset-y rounded="0">
        <template v-slot:activator="{ on, attrs }">
            <v-btn icon>
                <v-icon v-bind="attrs" v-on="on">far fa-user</v-icon>
            </v-btn>
        </template>
        <v-card class="mx-auto" width="260" tile>
            <v-list>
                <v-list-item link to="/me">
                    <v-list-item-avatar>
                        <v-img src="https://cdn.vuetifyjs.com/images/john.png"></v-img>
                    </v-list-item-avatar>
                    <v-list-item-content>
                        <v-list-item-title class="text-h6">{{ fullName }}</v-list-item-title>
                        <v-list-item-subtitle>{{username}}</v-list-item-subtitle>
                    </v-list-item-content>
                </v-list-item>
                <v-list-item link to="/balance">
                    <v-list-item-content>
                        <v-list-item-title
                                class="text-h6 green--text text--darken-2"
                        >Баланс: {{ balance }}</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
            </v-list>
            <v-divider></v-divider>
            <v-list nav dense>
                <v-list-item
                        v-for="(item, index) in links"
                        :key="'profile-link-' + index"
                        :to="item.url"
                >
                    <v-list-item-icon>
                        <v-icon v-text="item.icon" />
                    </v-list-item-icon>
                    <v-list-item-content>
                        <v-list-item-title v-text="item.title" />
                    </v-list-item-content>
                </v-list-item>
                <v-list-item @click.prevent="userSignOut">
                    <v-list-item-icon>
                        <v-icon>fas fa-sign-out-alt</v-icon>
                    </v-list-item-icon>
                    <v-list-item-content>
                        <v-list-item-title>Выход</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
            </v-list>
        </v-card>
    </v-menu>
</template>

<script>
    import { mapGetters, mapActions } from 'vuex'

    export default {
        name: 'UserToggleMenu',
        computed: {
            ...mapGetters({
                fullName: 'getName',
                username: 'getUsername'
            })
        },
        data: () => ({
            balance: '153 020 руб.',
            links: [
                { title: 'Счета и транзакции', icon: 'far fa-comments', url: '/balance' },
                { title: 'Сообщения', icon: 'far fa-comments', url: '/messages' },
                { title: 'Фотографии', icon: 'fas fa-camera-retro', url: '/photos' },
                { title: 'Желания', icon: 'far fa-star', url: '/favorites' },
                { title: 'Встречи', icon: 'fas fa-icons', url: '/meetings' },
                { title: 'Подарки', icon: 'fas fa-gifts', url: '/gifts' },
                { title: 'Пары', icon: 'far fa-kiss-beam', url: '/pairs' },
                { title: 'Новости', icon: 'far fa-newspaper', url: '/news' },
                { title: 'Товары и услуги', icon: 'fab fa-servicestack', url: '/products' }
            ],
        }),
        methods: {
            ...mapActions(
                ['userSignOut']
            )
        }
    }
</script>