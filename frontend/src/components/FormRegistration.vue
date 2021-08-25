<template>
    <v-row justify="center">
        <v-dialog v-model="dialog" max-width="350">
            <v-card>
                <v-card-title class="text-h5">Создать аккаунт</v-card-title>
                <v-card-text>
                    Нажимая кнопку "Создать аккаунт", вы принимаете наши
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
                                :disabled="submitCode !== ''"
                                label="Логин"
                                placeholder="Логин"
                                prepend-icon="fa-user"
                                required
                        ></v-text-field>
                        <v-text-field
                                id="phone"
                                name="phone"
                                v-model="phone"
                                :rules="phoneRules"
                                :disabled="submitCode !== ''"
                                type="tel"
                                label="Телефон"
                                placeholder="+7 (###) ###-##-##"
                                prepend-icon="fa-phone"
                                autocomplete="tel"
                                required
                        ></v-text-field>
                        <v-text-field
                                v-model="password"
                                :append-icon="showPassword ? 'fa-eye' : 'fa-eye-slash'"
                                :type="showPassword ? 'text' : 'password'"
                                :rules="passwordRules"
                                :disabled="submitCode !== ''"
                                label="Пароль"
                                placeholder="Пароль"
                                prepend-icon="fa-key"
                                @click:append="showPassword = !showPassword"
                                counter
                                required
                        ></v-text-field>
                        <v-switch
                                v-model="business"
                                label="Бизнес аккаунт"
                                :disabled="submitCode !== ''"
                        ></v-switch>
                        <v-text-field
                                v-if="submitCode !== ''"
                                v-model="validCode"
                                :rules="validCodeRules"
                                type="number"
                                label="Проверочный код"
                                placeholder="Проверочный код"
                                prepend-icon="fa-lock"
                                required
                        ></v-text-field>
                        <v-btn
                                :disabled="!valid || loading"
                                color="success"
                                class="mr-4"
                                @click="validate"
                        >
                            Создать аккаунт
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
                            @click="$emit('modal', 'login')"
                    >Войти</v-btn>
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
        name: 'FormRegistration',
        props: {
            dialog: Boolean
        },
        data: () => ({
            showPassword: false,
            valid: false,
            business: false,
            login: '',
            phone: '',
            password: '',
            submitCode: '',
            validCode: '',
            loginRules: [
                v => !!v || 'Укажите логин'
            ],
            phoneRules: [
                v => !!v || 'Укажите номер телефона'
            ],
            passwordRules: [
                v => !!v || 'Укажите пароль',
                v => (v && v.length >= 5) || 'Пароль должен быть больше 10 символов'
            ],
            validCodeRules: [
                v => !!v || 'Укажите проверочный код',
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

                this.submitCode = 'text'

                const formData = {
                    username: this.username,
                    phone: this.phone,
                    password: this.password,
                    code: this.validCode
                }

                const credentials = {
                    username: this.username,
                    password: this.password
                }

                await this.$store.dispatch('userSignUp', formData)
                    .then((response) => {
                        if (messages[response.data.message])
                            this.$message(messages[response.data.message])

                        if (response.status === 200) {
                            this.$store.dispatch('userSignIn', credentials)
                                .then(() => {
                                    this.$message(messages['welcome'])
                                    this.$emit('dialog', '')
                                    this.$router.push('/me')
                                })
                                .catch((error) => {
                                    this.$error(error.message)
                                })
                        }
                    })
                    .catch((error) => {
                        this.$error(error.message)
                    })
            }
        }
    }
</script>