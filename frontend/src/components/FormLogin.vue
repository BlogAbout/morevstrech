<template>
    <v-row justify="center">
        <v-dialog v-model="dialog" max-width="350" @click:outside="$emit('modal', '')">
            <v-card>
                <v-card-title class="text-h5">Вход</v-card-title>
                <v-card-text>
                    Нажимая "Войти", вы принимаете наши
                    <router-link to="/terms-conditions" custom v-slot="{ href, route, navigate }">
                        <a :href="href" @click="navigate">Условия</a>
                    </router-link>.
                    Чтобы узнать, как мы обрабатываем ваши данные, ознакомьтесь с нашей
                    <router-link to="/privacy-policy" custom v-slot="{ href, route, navigate }">
                        <a :href="href" @click="navigate">Политикой конфиденциальности</a>
                    </router-link>.
                </v-card-text>
                <v-card-text>
                    <v-form
                            ref="form"
                            v-model="valid"
                            lazy-validation
                    >
                        <v-text-field
                                v-model="login"
                                :rules="loginRules"
                                label="Логин"
                                placeholder="Логин"
                                prepend-icon="fa-user"
                                required
                        ></v-text-field>
                        <v-text-field
                                v-model="password"
                                :rules="passwordRules"
                                label="Пароль"
                                placeholder="Пароль"
                                prepend-icon="fa-key"
                                required
                        ></v-text-field>
                        <v-btn
                                :disabled="!valid || loading"
                                color="success"
                                class="mr-4"
                                @click="validate"
                        >
                            Войти
                        </v-btn>
                        <v-text-field
                                v-if="loading"
                                color="success"
                                loading
                                disabled
                        ></v-text-field>
                    </v-form>
                </v-card-text>
                <v-card-actions>
                    <v-btn
                            text
                            @click="$emit('modal', 'registration')"
                    >Регистрация</v-btn>
                    <v-spacer></v-spacer>
                    <v-btn
                            text
                            @click="$emit('modal', 'forgot')"
                    >
                        Забыли пароль?
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </v-row>
</template>

<script>
    import messages from '@/utils/plugins/messages'
    import { mapGetters } from 'vuex'

    export default {
        name: 'FormLogin',
        props: {
            dialog: Boolean
        },
        data: () => ({
            valid: false,
            login: '',
            password: '',
            loginRules: [
                v => !!v || 'Укажите логин'
            ],
            passwordRules: [
                v => !!v || 'Укажите пароль',
                v => (v && v.length >= 5) || 'Пароль должен быть больше 5 символов'
            ]
        }),
        computed: {
            ...mapGetters({
                loading: 'getLoading'
            })
        },
        methods: {
            async validate() {
                if (!this.$refs.form.validate())
                    return

                const credentials = {
                    username: this.username,
                    password: this.password
                }

                await this.$store.dispatch('userSignIn', credentials)
                    .then((response) => {
                        if (messages[response.data.message])
                            this.$message(messages[response.data.message])

                        if (response.status === 200) {
                            this.$emit('dialog', '')
                            this.$router.push('/me')
                        }
                    })
                    .catch((error) => {
                        this.$error(error.message)
                    })
            }
        }
    }
</script>