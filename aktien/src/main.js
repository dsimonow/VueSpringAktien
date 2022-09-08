import Vue from 'vue'
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import App from './App.vue'
import VueRouter from 'vue-router'
import Vuex from 'vuex'

import Manage from './components/Manage.vue'
import Overview from './components/Overview.vue'
import Profil from './components/Profil.vue'
import Add from './components/Add.vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'


Vue.use(VueRouter)
// Install BootstrapVue
Vue.use(BootstrapVue)
// Optionally install the BootstrapVue icon components plugin
Vue.use(IconsPlugin)
Vue.use(Vuex)

// Router konfigurieren
const router = new VueRouter({
  routes: [
    { path: '/', component: Overview },
    // Pfad "/list" --> Komponente "TodoList" anzeigen
    { path: '/manage', component: Manage },
    // Pfad "/about" --> Komponente "About" anzeigen
    { path: '/overview', component: Overview },
    { path: '/add', component: Add },
    { path: '/profil', component: Profil }
  ]
})

const store = new Vuex.Store({
  state: {
    auth: ""
  },
  getters: {
    getCurrentState: state => {
      return state.auth
    }
  },
  mutations: {
    authenticate (authkey) {
      this.auth = authkey;
    }
  },
  actions: {
   // 
  }
})

Vue.config.productionTip = false

new Vue({
  render: h => h(App),
  router,
  store,
  computed: {
    authkey () {
      return store.state.auth;
    }
  }
}).$mount('#app')


