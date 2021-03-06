<template>
    <v-row justify="center">
        <v-dialog v-model="dialog" max-width="350" @click:outside="$emit('modal', '')">
            <v-card>
                <v-card-title class="text-h5">Вход</v-card-title>
                <v-card-text>
                    Нажимая "Войти", вы принимаете наши <router-link to="/terms-conditions">Условия</router-link>.
                    Чтобы узнать, как мы обрабатываем ваши данные, ознакомьтесь с нашей
                    <router-link to="/privacy-policy">Политикой конфиденциальности</router-link>.
                </v-card-text>
                <v-card-text>
                    <v-form
                            ref="form"
                            v-model="valid"
                            lazy-validation
                    >
                        <v-text-field
                                v-model="username"
                                :rules="usernameRules"
                                label="Логин"
                                placeholder="Логин"
                                prepend-icon="fa-user"
                                required
                        ></v-text-field>
                        <v-text-field
                                v-model="password"
                                :append-icon="showPassword ? 'fa-eye' : 'fa-eye-slash'"
                                :type="showPassword ? 'text' : 'password'"
                                :rules="passwordRules"
                                label="Пароль"
                                placeholder="Пароль"
                                prepend-icon="fa-key"
                                @click:append="showPassword = !showPassword"
                                counter
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
            showPassword: false,
            valid: false,
            username: '',
            password: '',
            usernameRules: [
                v => !!v || 'Укажите логин'
            ],
            passwordRules: [
                v => !!v || 'Укажите пароль',
                v => (v && v.length >= 5) || 'Пароль должен быть больше 10 символов'
            ],
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
                        if (messages[response.data.code])
                            this.$message(messages[response.data.code])
                        else
                            this.$message(response.data.message)

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