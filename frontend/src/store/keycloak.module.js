const KeycloakModule = {
    state:{
        keycloak: {},
        jwt: {},
        userData: {
            firstname: "",
            lastname: "",
            email: "",
            roles: []
        },
        findUser: {},
        getAllUser: []
    },

    mutations: {
        KEYCLOAK(state, item){
            state.keycloak = item
        },
        STORE_USER(state, item){
            state.userData.firstname = item.firstName
            state.userData.lastname = item.lastName
            state.userData.email = item.email
        },
        STORE_ROLES(state, items){
            state.userData.roles = items
        },
        FIND_USER(state, item){
            state.findUser = item
        },
        CLEAR_FINDED_USER(state){
            state.findUser = {}
        },
        GET_ALL_USER(state, items){
            state.getAllUser = items
        }
    },

    actions: {
        keycloak({commit}, item){
            commit('KEYCLOAK', item)
        },
        storeUser({commit}, item){
            commit('STORE_USER', item)
        },
        storeRoles({commit}, items){
            commit('STORE_ROLES', items)
        },
        findUser({commit}, item){
            commit('FIND_USER', item)
        },
        clearFindedUser({commit}){
            commit('CLEAR_FINDED_USER')
        },
        getAllUser({commit}, items){
            commit('GET_ALL_USER', items)
        }
    },

    getters: {
        keycloak(state){
            let tmp = JSON.stringify(state.keycloak)
            return JSON.parse(tmp)
        },
        user(state){
            return state.userData
        },
        roles(state){
            return state.userData.roles
        },
        adminRole(state){
            return state.userData.roles.includes('admin')
        },
        findUser(state){
            return state.findUser
        },
        getAllUser(state){
            return state.getAllUser
        }
    }
}

export default KeycloakModule