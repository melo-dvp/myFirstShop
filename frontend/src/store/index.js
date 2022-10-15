import { createStore } from 'vuex'
import keyclaok from './keycloak.module'
import category from './category.module'


const context = require.context('./', true, /\.module\.js$/i);
let storeModules = {
    keyclaok,
    category
}

context.keys().forEach(file => {
    let name = file.replace(/\.module\.js$/i, "").replace("./", "");
    storeModules[name] = context(file).default || context(file)
    storeModules[name].namespaced = true    
});

const store = createStore({
    modules: storeModules
})

export default store;