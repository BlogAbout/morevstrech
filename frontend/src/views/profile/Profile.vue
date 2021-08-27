<template>
    <v-container class="main-page-container margin-top-50px">
        <v-row>
            <v-col cols="4">
                <v-card>
                    <v-img
                            height="200px"
                            src="https://cdn.pixabay.com/photo/2020/07/12/07/47/bee-5396362_1280.jpg"
                    >
                        <v-app-bar
                                flat
                                color="rgba(0, 0, 0, 0)"
                        >
                            <v-app-bar-nav-icon color="white"></v-app-bar-nav-icon>

                            <v-toolbar-title class="text-h6 white--text pl-0">
                                Messages
                            </v-toolbar-title>

                            <v-spacer></v-spacer>

                            <v-btn
                                    color="white"
                                    icon
                            >
                                <v-icon>mdi-dots-vertical</v-icon>
                            </v-btn>
                        </v-app-bar>

                        <v-card-title class="white--text mt-8">
                            <v-avatar size="56">
                                <img
                                        alt="user"
                                        src="https://cdn.pixabay.com/photo/2020/06/24/19/12/cabbage-5337431_1280.jpg"
                                >
                            </v-avatar>
                            <p class="ml-3">
                                John Doe
                            </p>
                        </v-card-title>
                    </v-img>

                    <v-card-text>
                        <div class="font-weight-bold ml-8 mb-2">
                            Today
                        </div>

                        <v-timeline
                                align-top
                                dense
                        >
                            <v-timeline-item
                                    v-for="message in messages"
                                    :key="message.time"
                                    :color="message.color"
                                    small
                            >
                                <div>
                                    <div class="font-weight-normal">
                                        <strong>{{ message.from }}</strong> @{{ message.time }}
                                    </div>
                                    <div>{{ message.message }}</div>
                                </div>
                            </v-timeline-item>
                        </v-timeline>
                    </v-card-text>
                </v-card>
            </v-col>
            <v-col cols="4">
                <v-card color="#1F7087" dark>
                    <div class="d-flex flex-no-wrap justify-space-between">
                        <div>
                            <v-card-title class="text-h5">Supermodel</v-card-title>
                            <v-card-subtitle>Foster the People</v-card-subtitle>
                            <v-card-actions>
                                <v-btn
                                        class="ml-2 mt-3"
                                        fab
                                        icon
                                        height="40px"
                                        right
                                        width="40px"
                                >
                                    <v-icon>mdi-play</v-icon>
                                </v-btn>

                                <v-btn
                                        class="ml-2 mt-5"
                                        outlined
                                        rounded
                                        small
                                >
                                    START RADIO
                                </v-btn>
                            </v-card-actions>
                        </div>

                        <v-avatar
                                class="ma-3"
                                size="125"
                                tile
                        >
                            <v-img src="https://cdn.vuetifyjs.com/images/cards/foster.jpg"></v-img>
                        </v-avatar>
                    </div>
                </v-card>
            </v-col>
            <v-col cols="4">
                <v-item-group
                        v-model="selected"
                        multiple
                >
                    <v-row>
                        <v-col
                                v-for="(item, index) in images"
                                :key="'user-images-' + index"
                                cols="3"
                        >
                            <v-item>
                                <v-img :src="item" class="text-right"/>
                            </v-item>
                        </v-col>
                    </v-row>
                </v-item-group>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
    import { mapState } from 'vuex'

    export default {
        computed: {
            ...mapState
        },
        name: 'Profile',
        data: () => ({
            messages: [
                {
                    from: 'You',
                    message: `Sure, I'll see you later.`,
                    time: '10:42am',
                    color: 'deep-purple lighten-1',
                },
                {
                    from: 'John Doe',
                    message: 'Yeah, sure. Does 1:00pm work?',
                    time: '10:37am',
                    color: 'green',
                },
                {
                    from: 'You',
                    message: 'Did you still want to grab lunch today?',
                    time: '9:47am',
                    color: 'deep-purple lighten-1',
                },
            ],
            images: [
                'https://cdn.vuetifyjs.com/images/cards/foster.jpg',
                'https://cdn.vuetifyjs.com/images/cards/foster.jpg',
                'https://cdn.vuetifyjs.com/images/cards/foster.jpg',
                'https://cdn.vuetifyjs.com/images/cards/foster.jpg',
                'https://cdn.vuetifyjs.com/images/cards/foster.jpg',
                'https://cdn.vuetifyjs.com/images/cards/foster.jpg',
                'https://cdn.vuetifyjs.com/images/cards/foster.jpg',
                'https://cdn.vuetifyjs.com/images/cards/foster.jpg',
                'https://cdn.vuetifyjs.com/images/cards/foster.jpg'
            ]
        })
    }
</script>