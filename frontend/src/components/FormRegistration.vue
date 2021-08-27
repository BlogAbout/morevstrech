<template>
    <v-row justify="center">
        <v-dialog v-model="dialog" max-width="350">
            <v-card>
                <v-card-title class="text-h5">Создать аккаунт</v-card-title>
                <v-card-text>
                    Нажимая кнопку "Создать аккаунт", вы принимаете наши <router-link to="/terms-conditions">Условия</router-link>.
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
                                label="Проверочный код из СМС"
                                placeholder="Проверочный код из СМС"
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
            username: '',
            phone: '',
            password: '',
            submitCode: '',
            validCode: 0,
            usernameRules: [
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
                v => !!v || 'Укажите проверочный код из СМС',
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

                const formData = {
                    username: this.username,
                    phone: this.phone,
                    password: this.password,
                    business: this.business,
                    code: this.validCode
                }

                if (!this.submitCode) {
                    await this.$store.dispatch('userSignUp', formData)
                        .then((response) => {
                            if (response.status === 200) {
                                this.submitCode = response.data.validationCode
                                this.validCode = response.data.validationCode
                            } else {
                                if (messages[response.data.code])
                                    this.$message(messages[response.data.code])
                                else
                                    this.$message(response.data.message)
                            }
                        })
                        .catch((error) => {
                            this.$error(error.message)
                        })
                } else {
                    const credentials = {
                        username: this.username,
                        password: this.password
                    }

                    await this.$store.dispatch('userSignUp', formData)
                        .then((response) => {
                            if (messages[response.data.code])
                                this.$message(messages[response.data.code])
                            else
                                this.$message(response.data.message)

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
    }
</script>