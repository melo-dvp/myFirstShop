import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import Keycloak from "keycloak-js"
import store  from './store'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import VueUploadComponent from 'vue-upload-component'

import { library } from "@fortawesome/fontawesome-svg-core";
import { 
  faPhone, 
  faPlus, 
  faPencil, 
  faTrash, 
  faHeartCircleCheck, 
  faHeart, 
  faXmarkCircle, 
  faCheckCircle, 
  faExclamationCircle,
  faCheck,
  faCloudArrowDown,
  faCloudArrowUp
} from "@fortawesome/free-solid-svg-icons";

library.add(
  faPhone, 
  faPlus, 
  faPencil, 
  faTrash, 
  faHeartCircleCheck, 
  faHeart, 
  faXmarkCircle, 
  faCheckCircle, 
  faExclamationCircle,
  faCheck,
  faCloudArrowDown,
  faCloudArrowUp
);


const app = createApp(App);


let initOptions = {
  url: process.env.VUE_APP_KEYCLOAK_URL, 
  realm: process.env.VUE_APP_KEYCLOAK_REALM, 
  clientId: process.env.VUE_APP_KEYCLOAK_CLIENT_ID
}

const _keycloak = new Keycloak(initOptions)
store.state.keycloak.keycloak = _keycloak
app.config.globalProperties.$keycloak = _keycloak

_keycloak.init({checkLoginIframe: false})
  .then(() => {
    app
      .use(router)
      .use(store)
      .component("font-awesome-icon", FontAwesomeIcon)
      .component('file-upload', VueUploadComponent)
      .mount('#app')
  })


