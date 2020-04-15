import Vue from 'vue'
import Vuetify from "vuetify";
import '@babel/polyfill'
import 'api/resource'
import router from "router/router";
import App from 'pages/App.vue'
import store from 'store/store'
import {connect} from "./util/ws"
import 'vuetify/dist/vuetify.min.css'
import * as Sentry from '@sentry/browser'
import * as Integrations from '@sentry/integrations'

Sentry.init({
    dsn: 'https://2ed8b3569ead4a25813c8e33649aa35a@o378443.ingest.sentry.io/5201843',
    integrations: [new Integrations.Vue({Vue, attachProps: true})],
});

Sentry.setUser({
    id: profile && profile.id,
    username: profile && profile.name
});


if (profile) {
    connect();
}

Vue.use(Vuetify);

new Vue({
    el: '#app',
    vuetify: new Vuetify(),
    store,
    router,
    render: a => a(App)
});