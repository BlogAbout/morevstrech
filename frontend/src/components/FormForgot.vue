<template>
    <v-row justify="center">
        <v-dialog v-model="dialog" max-width="350" @click:outside="$emit('modal', '')">
            <v-card>
                <v-card-title class="text-h5">Восстановление пароля</v-card-title>
                <v-card-text>
                    Для восстановления пароля, укажите номер телефона, к которому привязан аккаунт, на него придет код подтверждения.
                </v-card-text>
                <v-card-text>
                    <v-form
                            ref="form"
                            v-model="valid"
                            lazy-validation
                    >
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
                        >Получить код</v-btn>
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
                            @click="$emit('modal', 'registration')"
                    >Регистрация</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </v-row>
</template>

<script>
    import messages from '@/utils/plugins/messages'
    import { mapGetters } from 'vuex'

    export default {
        name: 'FormForgot',
        props: {
            dialog: Boolean
        },
        data: () => ({
            valid: false,
            phone: '',
            submitCode: '',
            validCode: '',
            phoneRules: [
                v => !!v || 'Укажите номер телефона'
            ],
            validCodeRules: [

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

                this.$message(messages['welcome'])
            }
        }
    }
</script>