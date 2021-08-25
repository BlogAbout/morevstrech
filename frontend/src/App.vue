<template>
  <v-app>
    <v-app-bar app color="transparent" dark height="100px">
      <v-container>
        <v-row justify="center" no-gutters>
          <v-col cols="3">
            <v-img
              alt="МореВстреч Сочи"
              class="shrink mr-2"
              contain
              src="./assets/images/logo.png"
              transition="scale-transition"
              width="230px"
            />
          </v-col>
          <v-spacer></v-spacer>
          <v-col cols="2" class="text-right">
            <v-btn color="primary mr-5" dark @click.stop="dialogs.login = true">Войти</v-btn>
            <v-menu
                    transition="slide-x-transition"
                    bottom
                    left
                    offset-y
                    rounded="0"
            >
              <template v-slot:activator="{ on, attrs }">
                <v-btn icon>
                  <v-icon v-bind="attrs" v-on="on">fa-bars</v-icon>
                </v-btn>
              </template>
              <v-list>
                <router-link
                        v-for="(item, index) in headerLinks"
                        :key="'header-link-' + index"
                        :to="item.url"
                        custom
                        v-slot="{ href, route, navigate, isActive, isExactActive }"
                >
                  <v-list-item link :class="[isActive && 'active', isExactActive && 'exact-active']">
                    <v-list-item-title>
                      <a :href="href" @click="navigate">{{ item.title }}</a>
                    </v-list-item-title>
                  </v-list-item>
                </router-link>
              </v-list>
            </v-menu>
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
          <v-col class="primary py-4 white--text" cols="3">
            <h3>Информация</h3>
            <ul class="footer-menu">
              <router-link
                      v-for="(item, index) in footerLinks[0]"
                      :key="'footer-links-one-' + index"
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
          <v-col class="primary py-4 white--text" cols="3">
            <h3>Для пользователей</h3>
            <ul class="footer-menu">
              <router-link
                      v-for="(item, index) in footerLinks[1]"
                      :key="'footer-links-two-' + index"
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
          <v-col class="primary py-4 white--text" cols="3">
            <h3>Информация</h3>
            <ul class="footer-menu">
              <router-link
                      v-for="(item, index) in footerLinks[2]"
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
          <v-col class="primary py-4 text-right white--text" cols="3">
            <div class="subheading mb-5">Обретите новые знакомства в Сочи!</div>
            <v-spacer />
            <v-btn color="primary mr-5" dark @click.stop="dialogs.login = true">Войти</v-btn>
            <v-btn color="primary" dark @click.stop="dialogs.registration = true">Регистрация</v-btn>
            <v-spacer />
            <v-btn
                    v-for="(item, index) in footerSocial"
                    :key="'footer-social-' + index"
                    class="mx-4"
                    dark
                    icon
            >
              <a :href="item.link" target="_blank" class="white--text">
                <v-icon size="24px">{{ item.icon }}</v-icon>
              </a>
            </v-btn>
          </v-col>
          <v-col class="primary py-4 text-center white--text" cols="12">
            &copy; {{ new Date().getFullYear() }}. All rights reserved. МореВстреч Сочи.
          </v-col>
        </v-row>
      </v-container>
    </v-footer>

    <form-login :dialog="dialogs.login" @modal="showDialog" />
    <form-registration :dialog="dialogs.registration" @modal="showDialog" />
    <form-forgot :dialog="dialogs.forgot" @modal="showDialog" />
  </v-app>
</template>

<script>
  import FormLogin from './components/FormLogin'
  import FormRegistration from './components/FormRegistration'
  import FormForgot from './components/FormForgot'

  export default {
    name: 'App',
    components: {
      FormLogin,
      FormRegistration,
      FormForgot
    },
    data: () => ({
      dialogs: {
        login: false,
        registration: false,
        forgot: false,
      },
      headerLinks: [
        { title: 'О нас', url: '/about' },
        { title: 'Презентация', url: '/about' },
        { title: 'Обновления', url: '/contacts' },
        { title: 'Для Спонсоров', url: '/contacts' },
        { title: 'Для Инвесторов', url: '/about' },
        { title: 'Территория Сочи', url: '/contacts' },
        { title: 'Полезные статьи', url: '/about' },
        { title: 'Партнёрка', url: '/contacts' },
        { title: 'Администрация', url: '/about' },
        { title: 'Отель «Звезда»', url: '/contacts' }
      ],
      footerLinks: [
        [
          { title: 'Презентация', url: '/' },
          { title: 'Обновления', url: '/offer' },
          { title: 'Для Спонсоров', url: '/contacts' },
          { title: 'Для Инвесторов', url: '/policy' },
          { title: 'Территория Сочи', url: '/panel' },
          { title: 'Полезные статьи', url: '/offer' },
          { title: 'Партнёрство', url: '/contacts' },
          { title: 'Администрация', url: '/policy' },
          { title: 'Отель «Звезда»', url: '/panel' },
        ],
        [
          { title: 'Информация', url: '/' },
          { title: 'VIP аккаунты', url: '/' },
          { title: 'Инструкция', url: '/offer' },
          { title: 'Техническая поддержка', url: '/contacts' },
        ],
        [
          { title: 'Информация', url: '/' },
          { title: 'Кэшбек', url: '/offer' },
          { title: 'Партнёрство', url: '/offer' },
          { title: 'Инструкция', url: '/offer' },
          { title: 'Техническая поддержка', url: '/contacts' },
        ],
      ],
      footerSocial: [
        { icon: 'fa-facebook-f', link: '#' },
        { icon: 'fa-twitter', link: '#' },
        { icon: 'fa-linkedin-in', link: '#' },
        { icon: 'fa-instagram', link: '#' },
      ],
    }),
    methods: {
      showDialog: function(type) {
        this.dialogs.login = this.dialogs.registration = this.dialogs.forgot = false
        if (type)
          this.dialogs[type] = true
      }
    }
  };
</script>