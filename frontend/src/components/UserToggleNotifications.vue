<template>
    <v-menu transition="slide-x-transition" bottom left offset-y rounded="0">
        <template v-slot:activator="{ on, attrs }">
            <v-badge light color="green" bordered bottom overlap content="6">
                <v-btn icon class="white--text">
                    <v-icon dark v-bind="attrs" v-on="on">far fa-bell</v-icon>
                </v-btn>
            </v-badge>
        </template>
        <v-list two-line>
            <v-list-item v-for="(item, index) in notifications" :key="'notification-' + index" ripple>
                <v-list-item-avatar>
                    <img :src="item.avatar" />
                </v-list-item-avatar>
                <v-list-item-content>
                    <v-list-item-title v-html="item.title"></v-list-item-title>
                    <v-list-item-subtitle v-html="item.subtitle"></v-list-item-subtitle>
                </v-list-item-content>
            </v-list-item>
        </v-list>
    </v-menu>
</template>

<script>
    export default {
        name: 'UserToggleNotifications',
        data: () => ({
            notifications: [
                {
                    avatar: 'https://picsum.photos/250/300?image=660',
                    title: 'Meeting @ Noon',
                    subtitle: `<span class="font-weight-bold">Spike Lee</span> &mdash; I'll be in your neighborhood`,
                },
                {
                    avatar: 'https://picsum.photos/250/300?image=821',
                    title: 'Summer BBQ <span class="grey--text text--lighten-1"></span>',
                    subtitle: '<span class="font-weight-bold">to Operations support</span> &mdash; Wish I could come.',
                },
                {
                    avatar: 'https://picsum.photos/250/300?image=783',
                    title: 'Yes yes',
                    subtitle: '<span class="font-weight-bold">Bella</span> &mdash; Do you have Paris recommendations',
                },
                {
                    avatar: 'https://picsum.photos/250/300?image=1006',
                    title: 'Dinner tonight?',
                    subtitle:
                        '<span class="font-weight-bold">LaToya</span> &mdash; Do you want to hang out?',
                },
                {
                    avatar: 'https://picsum.photos/250/300?image=146',
                    title: 'So long',
                    subtitle:
                        '<span class="font-weight-bold">Nancy</span> &mdash; Do you see what time it is?',
                }
            ]
        })
    }
</script>