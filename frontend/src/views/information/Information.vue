<template>
    <v-container class="main-page-container margin-top-50px">
        <h1>Информация</h1>
        <v-card class="mx-auto" tile>
            <v-list-item
                    two-line
                    v-for="item of links"
                    :key="item.url"
            >
                <v-list-item-content>
                    <router-link :to="item.url" custom v-slot="{ href, route, navigate }">
                        <v-list-item-title>
                            <a :href="href" @click="navigate">{{ item.title }}</a>
                        </v-list-item-title>
                    </router-link>
                    <v-list-item-subtitle>{{ item.desc }}</v-list-item-subtitle>
                </v-list-item-content>
            </v-list-item>
        </v-card>
    </v-container>
</template>

<script>
    export default {
        name: 'Information',
        data: () => ({
            links: [
                { title: 'Презентация', url: '/information/presentation', desc: 'Ознакомьтесь с нашей презентацией сервиса' },
                { title: 'Для Спонсоров', url: '/information/for-sponsor', desc: 'Узнайте, как стать спонсором' },
                { title: 'Для Инвесторов', url: '/information/for-investor', desc: 'Станьте инвестором и получайте прибыль' },
                { title: 'Территория Сочи', url: '/information/territory', desc: 'Покрытие территории Сочи сервисом' },
                { title: 'Полезные статьи', url: '/information/articles', desc: 'Полезные и интересные статьи о сервисе' },
                { title: 'Партнёрство', url: '/information/partners', desc: 'Как стать парнёром и заработать на сервисе' },
                { title: 'Администрация', url: '/information/administration', desc: 'Свяжитесь с администрацией сервиса' },
                { title: 'Отель «Звезда»', url: '/information/hotel-star', desc: 'Уникальное предложение от отеля "Звезда"' }
            ]
        })
    }
</script>