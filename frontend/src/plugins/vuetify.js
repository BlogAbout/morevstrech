import Vue from 'vue';
import Vuetify from 'vuetify/lib/framework';

Vue.use(Vuetify);

export default new Vuetify({
    theme: {
        themes: {
            light: {
                primary: '#315da6',
                secondary: '#fff',
                accent: '#ffe222',
                error: '#b71c1c',
                info: '#2196F3',
                success: '#315da6',
                warning: '#FFC107'
            },
        },
    },
});